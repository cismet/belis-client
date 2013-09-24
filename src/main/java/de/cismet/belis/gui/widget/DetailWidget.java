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

import Sirius.server.middleware.types.MetaObject;

import org.jdesktop.beansbinding.BindingGroup;

import java.beans.PropertyChangeEvent;

import de.cismet.belis.broker.BelisBroker;

import de.cismet.belis.gui.widget.detailWidgetPanels.AbstractDetailWidgetPanel;
import de.cismet.belis.gui.widget.detailWidgetPanels.AbzweigdosePanel;
import de.cismet.belis.gui.widget.detailWidgetPanels.ArbeitsauftragPanel;
import de.cismet.belis.gui.widget.detailWidgetPanels.LeitungPanel;
import de.cismet.belis.gui.widget.detailWidgetPanels.LeuchtePanel;
import de.cismet.belis.gui.widget.detailWidgetPanels.MauerlaschePanel;
import de.cismet.belis.gui.widget.detailWidgetPanels.SchaltstellePanel;
import de.cismet.belis.gui.widget.detailWidgetPanels.StandortPanel;
import de.cismet.belis.gui.widget.detailWidgetPanels.VeranlassungPanel;

import de.cismet.cids.custom.beans.belis.AbzweigdoseCustomBean;
import de.cismet.cids.custom.beans.belis.ArbeitsauftragCustomBean;
import de.cismet.cids.custom.beans.belis.LeitungCustomBean;
import de.cismet.cids.custom.beans.belis.MauerlascheCustomBean;
import de.cismet.cids.custom.beans.belis.SchaltstelleCustomBean;
import de.cismet.cids.custom.beans.belis.TdtaLeuchtenCustomBean;
import de.cismet.cids.custom.beans.belis.TdtaStandortMastCustomBean;
import de.cismet.cids.custom.beans.belis.VeranlassungCustomBean;

import de.cismet.cids.dynamics.CidsBean;

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

    //~ Instance fields --------------------------------------------------------

    protected Object currentEntity = null;

    private StandortPanel standortPanel = StandortPanel.getInstance();
    private LeuchtePanel leuchtePanel = LeuchtePanel.getInstance();
    private LeitungPanel leitungPanel = LeitungPanel.getInstance();
    private AbzweigdosePanel abzweigdosePanel = AbzweigdosePanel.getInstance();
    private MauerlaschePanel mauerlaschePanel = MauerlaschePanel.getInstance();
    private SchaltstellePanel schaltstellePanel = SchaltstellePanel.getInstance();
    private VeranlassungPanel veranlassungPanel = VeranlassungPanel.getInstance();
    private ArbeitsauftragPanel arbeitsauftragPanel = ArbeitsauftragPanel.getInstance();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.belis.gui.documentpanel.DocumentPanel panDokumente;
    private javax.swing.JTabbedPane panMain;
    private javax.swing.JScrollPane scpMain;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new DetailWidget object.
     */
    public DetailWidget() {
        setWidgetName("Details");
        initComponents();
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("PropertyChange: " + evt);
            LOG.debug("PropertyChange: " + evt.getPropertyName());
            LOG.debug("PropertyChange: " + evt.getOldValue());
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

        firePropertyChange(PROP_CURRENT_ENTITY, oldCurrentEntity, currentEntity);
        bindingGroup.unbind();
        bindingGroup.bind();

        boolean isEditable = false;
        if ((currentEntity != null) && (currentEntity instanceof CidsBean)) {
            final CidsBean currentBean = (CidsBean)currentEntity;
            if (BelisBroker.getInstance().isInCreateMode()) {
                isEditable = currentBean.getMetaObject().getStatus() == MetaObject.NEW;
            } else if (BelisBroker.getInstance().isInEditMode()) {
                isEditable = currentBean.getMetaObject().getStatus() != MetaObject.NEW;
            }
        }
        setWidgetEditable(isEditable);

        if (currentEntity == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("current Entity is null");
            }
            showPanel(null);
            panDokumente.setDokumente(null);
            return;
        }
        if (currentEntity instanceof DocumentContainer) {
            panDokumente.setDokumente(((DocumentContainer)currentEntity).getDokumente());
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
        } else if (currentEntity instanceof VeranlassungCustomBean) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("CurrentEntity is Veranlassung");
            }

            veranlassungPanel.setCurrentEntity((VeranlassungCustomBean)currentEntity);
            veranlassungPanel.setElementsNull();

            showPanel(veranlassungPanel);
        } else if (currentEntity instanceof ArbeitsauftragPanel) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("CurrentEntity is Arbeitsauftrag");
            }

            arbeitsauftragPanel.setCurrentEntity((ArbeitsauftragCustomBean)currentEntity);
            arbeitsauftragPanel.setElementsNull();

            showPanel(arbeitsauftragPanel);
        } else {
            LOG.info("no panel for entity available");
            showPanel(null);
            panDokumente.setDokumente(null);
        }
        this.repaint();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  panel  DOCUMENT ME!
     */
    private void showPanel(final AbstractDetailWidgetPanel panel) {
        super.clearComponent();
        if (panel != null) {
            scpMain.setViewportView(panel);
            panMain.setTabComponentAt(0, panel.getTabLabel());
            panMain.setSelectedComponent(scpMain);
            panMain.setVisible(true);
        } else {
            panMain.setVisible(false);
        }
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
        veranlassungPanel.setPanelEditable(isEditable);
        arbeitsauftragPanel.setPanelEditable(isEditable);
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
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        panMain = new javax.swing.JTabbedPane();
        scpMain = new javax.swing.JScrollPane();
        panDokumente = new de.cismet.belis.gui.documentpanel.DocumentPanel();

        setLayout(new java.awt.BorderLayout());

        scpMain.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        panMain.addTab("", scpMain);

        final org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.dokumente}"),
                panDokumente,
                org.jdesktop.beansbinding.BeanProperty.create("dokumente"));
        bindingGroup.addBinding(binding);

        panMain.addTab("Dokumente", panDokumente);

        add(panMain, java.awt.BorderLayout.CENTER);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

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
