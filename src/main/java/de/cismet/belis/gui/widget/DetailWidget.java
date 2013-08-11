/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * EntityDetailWidget.java
 *
 * Created on 23. März 2009, 11:27
 */
package de.cismet.belis.gui.widget;

import org.jdesktop.beansbinding.BindingGroup;

import java.awt.CardLayout;

import java.beans.PropertyChangeEvent;

import java.util.ArrayList;

import de.cismet.belis.gui.documentpanel.DocumentPanel;
import de.cismet.belis.gui.widget.detailWidgetPanels.AbstractDetailWidgetPanel;
import de.cismet.belis.gui.widget.detailWidgetPanels.AbzweigdosePanel;
import de.cismet.belis.gui.widget.detailWidgetPanels.LeitungPanel;
import de.cismet.belis.gui.widget.detailWidgetPanels.LeuchtePanel;
import de.cismet.belis.gui.widget.detailWidgetPanels.MauerlaschePanel;
import de.cismet.belis.gui.widget.detailWidgetPanels.SchaltstellePanel;
import de.cismet.belis.gui.widget.detailWidgetPanels.StandortPanel;

import de.cismet.cids.custom.beans.belis.AbzweigdoseCustomBean;
import de.cismet.cids.custom.beans.belis.DmsUrlCustomBean;
import de.cismet.cids.custom.beans.belis.LeitungCustomBean;
import de.cismet.cids.custom.beans.belis.MauerlascheCustomBean;
import de.cismet.cids.custom.beans.belis.SchaltstelleCustomBean;
import de.cismet.cids.custom.beans.belis.TdtaLeuchtenCustomBean;
import de.cismet.cids.custom.beans.belis.TdtaStandortMastCustomBean;

import de.cismet.commons.server.interfaces.DocumentContainer;

import de.cismet.tools.CurrentStackTrace;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
@org.openide.util.lookup.ServiceProvider(service = BelisWidget.class)
public class DetailWidget extends BelisWidget {

    //~ Static fields/initializers ---------------------------------------------

    public static final String PROP_CURRENT_ENTITY = "currentEntity";
    public static final Short ANZAHL_DK_NULL_VALUE = 0;

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(DetailWidget.class);

    private static final String DOCUMENT_PANEL = "document panel card";

    //~ Instance fields --------------------------------------------------------

    protected Object currentEntity = null;

    private StandortPanel standortPanel = StandortPanel.getInstance();
    private LeuchtePanel leuchtePanel = LeuchtePanel.getInstance();
    private LeitungPanel leitungPanel = LeitungPanel.getInstance();
    private AbzweigdosePanel abzweigdosePanel = AbzweigdosePanel.getInstance();
    private MauerlaschePanel mauerlaschePanel = MauerlaschePanel.getInstance();
    private SchaltstellePanel schaltstellePanel = SchaltstellePanel.getInstance();
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.belis.gui.documentpanel.DocumentPanel panDokumente;
    private javax.swing.JPanel panEmpty;
    private javax.swing.JPanel panMain;
    private javax.swing.JScrollPane scpMain;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new DetailWidget object.
     */
    public DetailWidget() {
        setWidgetName("Details");
        initComponents();
        setPanelsToCardLayout();
        panMain.add(panDokumente, DOCUMENT_PANEL);
        // binding
        final org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.dokumente}"),
                panDokumente,
                org.jdesktop.beansbinding.BeanProperty.create("dokumente"));
        bindingGroup = new BindingGroup();
        bindingGroup.addBinding(binding);
        bindingGroup.bind();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     */
    private void setPanelsToCardLayout() {
        addDetailWidgetPanelToPanMain(standortPanel);
        addDetailWidgetPanelToPanMain(leuchtePanel);
        addDetailWidgetPanelToPanMain(leitungPanel);
        addDetailWidgetPanelToPanMain(abzweigdosePanel);
        addDetailWidgetPanelToPanMain(mauerlaschePanel);
        addDetailWidgetPanelToPanMain(schaltstellePanel);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  panel  DOCUMENT ME!
     */
    private void addDetailWidgetPanelToPanMain(final AbstractDetailWidgetPanel panel) {
        panMain.add(panel, panel.PANEL_CARD_NAME);
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("PropertyChange: " + evt);
            LOG.debug("PropertyChange: " + evt.getPropertyName());
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("PropertyChange: " + evt.getOldValue());
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("PropertyChange: " + evt.getNewValue());
        }
    }

    /**
     * Get the value of currentEntity.
     *
     * @return  the value of currentEntity
     */
    public Object getCurrentEntity() {
        return currentEntity;
    }

    /**
     * Set the value of currentEntity TODO change parameter type to DocumentContainer or BaseEntity.
     *
     * @param  currentEntity  new value of currentEntity
     */
    public void setCurrentEntity(final Object currentEntity) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("setCurrentEntity", new CurrentStackTrace());
        }
        final Object oldCurrentEntity = this.currentEntity;
        this.currentEntity = currentEntity;
        // Attention there is another block for the visiblity of the document panel
        // this must be here because the set must be created before it is set in the documentpanel
        if ((currentEntity != null) && (currentEntity instanceof DocumentContainer)
                    && (((DocumentContainer)currentEntity).getDokumente() == null)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Entity is DocumentContainer and Set == null. Creating Set");
            }
            ((DocumentContainer)currentEntity).setDokumente(new ArrayList<DmsUrlCustomBean>());
        }

        firePropertyChange(PROP_CURRENT_ENTITY, oldCurrentEntity, currentEntity);
        bindingGroup.unbind();
        bindingGroup.bind();

        if (currentEntity == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("current Entity is null");
            }
            final CardLayout cl = (CardLayout)(panMain.getLayout());
            cl.show(panMain, "EMPTY_PANEL");
            return;
        }
        if (currentEntity instanceof TdtaStandortMastCustomBean) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("CurrentEntity is Standort");
            }
            standortPanel.setCurrentEntity((TdtaStandortMastCustomBean)currentEntity);
            standortPanel.setElementsNull();

            showPanel(standortPanel);
        } else if (currentEntity instanceof TdtaLeuchtenCustomBean) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("CurrentEntity is Leuchte");
            }
            LOG.info("ParentNode: " + ((TdtaLeuchtenCustomBean)currentEntity).getStandort());
            if (getBroker().getWorkbenchWidget().isParentNodeMast(
                            getBroker().getWorkbenchWidget().getSelectedTreeNode().getLastPathComponent())) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("ParentNode ist Mast");
                }
                leuchtePanel.setInheritedMastPropertiesEnabled(false);
            } else {
                if (getBroker().isInEditMode()) {
                    leuchtePanel.setInheritedMastPropertiesEnabled(true);
                }
            }

            leuchtePanel.setCurrentEntity((TdtaLeuchtenCustomBean)currentEntity);
            leuchtePanel.setElementsNull();
            showPanel(leuchtePanel);
        } else if (currentEntity instanceof LeitungCustomBean) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("CurrentEntity is Leitung");
            }

            leitungPanel.setCurrentEntity((LeitungCustomBean)currentEntity);
            leitungPanel.setElementsNull();

            showPanel(leitungPanel);
        } else if (currentEntity instanceof AbzweigdoseCustomBean) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("CurrentEntity is Abzweigdose");
            }
            showPanel(abzweigdosePanel);
        } else if (currentEntity instanceof MauerlascheCustomBean) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("CurrentEntity is Mauerlasche");
            }

            mauerlaschePanel.setCurrentEntity((MauerlascheCustomBean)currentEntity);
            mauerlaschePanel.setElementsNull();

            showPanel(mauerlaschePanel);
        } else if (currentEntity instanceof SchaltstelleCustomBean) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("CurrentEntity is Schaltstelle");
            }

            schaltstellePanel.setCurrentEntity((SchaltstelleCustomBean)currentEntity);
            schaltstellePanel.setElementsNull();

            showPanel(schaltstellePanel);
        } else {
            LOG.warn("no panel for entity available");
        }
        if (currentEntity instanceof DocumentContainer) {
            final DocumentContainer dc = (DocumentContainer)currentEntity;
            final CardLayout cl = (CardLayout)(panMain.getLayout());
            cl.show(panMain, DOCUMENT_PANEL);
        }
        this.repaint();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  panel  DOCUMENT ME!
     */
    private void showPanel(final AbstractDetailWidgetPanel panel) {
        final CardLayout cl = (CardLayout)(panMain.getLayout());
        cl.show(panMain, panel.PANEL_CARD_NAME);
    }

    @Override
    public void clearComponent() {
        super.clearComponent();
        setCurrentEntity(null);
    }

    @Override
    public void setWidgetEditable(final boolean isEditable) {
        super.setWidgetEditable(isEditable);
        standortPanel.setPanelEditable(isEditable);
        leuchtePanel.setPanelEditable(isEditable);
        leitungPanel.setPanelEditable(isEditable);
        mauerlaschePanel.setPanelEditable(isEditable);
        schaltstellePanel.setPanelEditable(isEditable);
        panDokumente.setEditable(isEditable);
    }

    /**
     * DOCUMENT ME!
     */
    private void commitEdits() {
        if (currentEntity != null) {
            if (currentEntity instanceof TdtaLeuchtenCustomBean) {
                leuchtePanel.commitEdits();
            } else if (currentEntity instanceof TdtaStandortMastCustomBean) {
                standortPanel.commitEdits();
            } else if (currentEntity instanceof SchaltstelleCustomBean) {
                schaltstellePanel.commitEdits();
            } else if (currentEntity instanceof MauerlascheCustomBean) {
                mauerlaschePanel.commitEdits();
            }
        }
    }

    /**
     * Creates new form EntityDetailWidget.
     */
    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        panDokumente = new de.cismet.belis.gui.documentpanel.DocumentPanel();
        scpMain = new javax.swing.JScrollPane();
        panMain = new javax.swing.JPanel();
        panEmpty = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        panMain.setLayout(new java.awt.CardLayout());
        panMain.add(panEmpty, "EMPTY_PANEL");

        scpMain.setViewportView(panMain);

        add(scpMain, java.awt.BorderLayout.CENTER);
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public DocumentPanel getPanDokumente() {
        return panDokumente;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  panDokumente  DOCUMENT ME!
     */
    public void setPanDokumente(final DocumentPanel panDokumente) {
        this.panDokumente = panDokumente;
        panMain.add(panDokumente, DOCUMENT_PANEL);
    }

    @Override
    public BindingGroup getBindingGroup() {
        return bindingGroup;
    }

    @Override
    public void setBindingGroup(final BindingGroup bindingGroup) {
        this.bindingGroup = bindingGroup;
    }

    @Override
    public int getStatus() {
        commitEdits();
        return super.getStatus();
    }
}
