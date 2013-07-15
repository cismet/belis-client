/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.belis.gui.widget.detailWidgetPanels;

import org.jdesktop.beansbinding.BindingGroup;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import de.cismet.belis.broker.CidsBroker;

import de.cismet.belisEE.util.CriteriaStringComparator;

import de.cismet.cids.custom.beans.belis.TkeyStrassenschluesselCustomBean;

/**
 * DOCUMENT ME!
 *
 * @author   Gilles Baatz
 * @version  $Revision$, $Date$
 */
public abstract class AbstractDetailWidgetPanel<T> extends JPanel {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            AbstractDetailWidgetPanel.class);
    public static final String PROP_CURRENT_ENTITY = "currentEntity";

    //~ Instance fields --------------------------------------------------------

    protected T currentEntity = null;

    Collection<TkeyStrassenschluesselCustomBean> allStrassenschluessel = null;
    final String comboBoxNullValue = "Wert auswählen...";
    boolean isTriggerd = false;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbstractDetailWidgetPanel object.
     */
    public AbstractDetailWidgetPanel() {
        if (allStrassenschluessel == null) {
            try {
                allStrassenschluessel = CidsBroker.getInstance().getAllStrassenschluessel();
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Strassenschluessel size: " + allStrassenschluessel.size());
                }
            } catch (Exception ex) {
                LOG.error("Error while initializing all strassenschlussel.");
                allStrassenschluessel = new HashSet();
            }
        }
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    abstract BindingGroup getBindingGroup();

    /**
     * DOCUMENT ME!
     */
    abstract void initPanel();
    
    /**
     * DOCUMENT ME!
     */
    public abstract void setElementsNull();
    
    /**
     * DOCUMENT ME!
     *
     * @param  isEditable  DOCUMENT ME!
     */
    public abstract void setPanelEditable(final boolean isEditable);
    
    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Object getCurrentEntity() {
        return currentEntity;
    }
    /**
     * DOCUMENT ME!
     *
     * @param  currentEntity  DOCUMENT ME!
     */
    public void setCurrentEntity(final T currentEntity) {
        final Object oldCurrentEntity = this.currentEntity;
        this.currentEntity = currentEntity;
        firePropertyChange(PROP_CURRENT_ENTITY, oldCurrentEntity, currentEntity);
        getBindingGroup().unbind();
        getBindingGroup().bind();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  col  DOCUMENT ME!
     * @param  box  DOCUMENT ME!
     */
    void createSortedCBoxModelFromCollection(final Collection<?> col, final JComboBox box) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("sorting collection: " + col);
        }
        try {
            if (box != null) {
                if (col != null) {
                    final Object[] objArr = col.toArray();
                    Arrays.sort(objArr, CriteriaStringComparator.getInstance());
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("sorted Collection:" + objArr);
                    }
                    box.setModel(new DefaultComboBoxModel(objArr));
                } else {
                    box.setModel(new DefaultComboBoxModel());
                }
            }
        } catch (Exception ex) {
            LOG.error("error while sorting collection", ex);
        }
    }
}
