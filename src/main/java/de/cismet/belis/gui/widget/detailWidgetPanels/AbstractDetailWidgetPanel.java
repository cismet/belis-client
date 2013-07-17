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

import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.BindingListener;
import org.jdesktop.beansbinding.PropertyStateEvent;

import java.awt.Color;
import java.awt.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;

import de.cismet.belis.broker.CidsBroker;

import de.cismet.belisEE.util.CriteriaStringComparator;

import de.cismet.cids.custom.beans.belis.TdtaStandortMastCustomBean;
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
    final HashMap<JComponent, JComponent> componentToLabelMap = new HashMap<JComponent, JComponent>();

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
    abstract void initComponentToLabelMap();

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
        final T oldCurrentEntity = this.currentEntity;
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

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    class PanelBindingListener implements BindingListener {

        //~ Instance fields ----------------------------------------------------

        private Collection<Binding> validationState = new HashSet<Binding>();

        //~ Methods ------------------------------------------------------------

        @Override
        public void bindingBecameBound(final Binding binding) {
            // log.debug("binding became bound");
        }

        @Override
        public void bindingBecameUnbound(final Binding binding) {
            // log.debug("binding became unbound");
        }

        @Override
        public void syncWarning(final Binding binding, final Binding.SyncFailure failure) {
        }

        @Override
        public void syncFailed(final Binding binding, final Binding.SyncFailure failure) {
            // log.debug("syncFailed");
            final Object target = binding.getTargetObject();
            // log.debug("Target: " + target);
            if (target instanceof JComponent) { // && !(target instanceof JComboBox)) {
                final JComponent c = (JComponent)target;
                final JComponent associatedLabel = componentToLabelMap.get(c);
                if (associatedLabel != null) {
                    associatedLabel.setForeground(Color.red);
                } else {
                    c.setForeground(Color.red);
                }
                try {
                    if (associatedLabel != null) {
                        associatedLabel.setToolTipText(failure.getValidationResult().getDescription());
                    }
                    c.setToolTipText(failure.getValidationResult().getDescription());
                } catch (Exception ex) {
                    // log.debug("Error while setting tooltip", ex);
                    c.setToolTipText(null);
                }
            } else {
                LOG.error("keine JCOmponent");
            }
            if ((currentEntity instanceof TdtaStandortMastCustomBean)
                        && StandortPanel.getInstance().isAncestorOf((Component)binding.getTargetObject())) {
                validationState.add(binding);
                // log.debug("Validation state changed. Errorcount: "+validationState.size());
            }
            // ToDo message with complete text to user;
            // lblStrassenschluesselValidation.setIcon(BelisIcons.icoCancel22);
        }

        @Override
        public void synced(final Binding binding) {
            // log.debug("synced: source: "+binding.getSourceObject()+" target: "+binding.getTargetObject(),new
            // CurrentStackTrace()); log.debug("sync: " + cbxLeuchteStrassenschluessel.getSelectedItem());
            // lblStrassenschluesselValidation.setIcon(BelisIcons.icoAccept22);
            final Object target = binding.getTargetObject();
            if (target instanceof JComponent) { // && !(target instanceof JComboBox)) {
                final JComponent c = (JComponent)target;
                final JComponent associatedLabel = componentToLabelMap.get(c);
                if (associatedLabel != null) {
                    associatedLabel.setForeground(Color.black);
                } else {
                    c.setForeground(Color.black);
                }
                c.setToolTipText(null);
            } else {
                LOG.error("keine JCOmponent");
            }
            if ((currentEntity instanceof TdtaStandortMastCustomBean)
                        && StandortPanel.getInstance().isAncestorOf((Component)binding.getTargetObject())) {
                validationState.remove(binding);
                // log.debug("Validation state changed. Errorcount: "+validationState.size());
            }
//                String bindingName = binding.getName();
//                if (bindingName != null) {
//                    Collection<String> additionalValidationBindings = validationDependencies.get(binding.getName());
//                    log.debug("for binding " + bindingName + "-->> dependencies: " + additionalValidationBindings);
//                    if (additionalValidationBindings != null) {
//
//                        for (String name : additionalValidationBindings) {
//                            Binding b = bindingGroup.getBinding(name);
//                            if (b != 06694null) {
//                                log.debug("CheckAgain: " + name);
//                                b.saveAndNotify();
//                            }
//                        }
//                    }
//
//                }
        }

        @Override
        public void sourceChanged(final Binding binding, final PropertyStateEvent event) {
        }

        @Override
        public void targetChanged(final Binding binding, final PropertyStateEvent event) {
        }
    }
}
