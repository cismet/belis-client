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

import java.awt.BorderLayout;

import java.beans.PropertyChangeEvent;

import java.util.ArrayList;

import de.cismet.belis.broker.BelisBroker;

import de.cismet.belis.gui.documentpanel.DocumentPanel;
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

import de.cismet.commons2.architecture.widget.DefaultWidget;

import de.cismet.tools.CurrentStackTrace;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public class DetailWidget extends DefaultWidget {

    //~ Static fields/initializers ---------------------------------------------

    public static final String PROP_CURRENT_ENTITY = "currentEntity";
    public static final Short ANZAHL_DK_NULL_VALUE = 0;

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(DetailWidget.class);

    //~ Instance fields --------------------------------------------------------

    protected Object currentEntity = null;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.belis.gui.documentpanel.DocumentPanel panDokumente;
    private javax.swing.JPanel panMain;
    private javax.swing.JScrollPane scpMain;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new DetailWidget object.
     *
     * @param  broker  DOCUMENT ME!
     */
    public DetailWidget(final BelisBroker broker) {
        super(broker);
        initComponents();
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
        panMain.removeAll();

        setAllPanelsVisible(false);

        if (currentEntity == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("current Entity is null");
            }
            StandortPanel.getInstance().setVisible(false);
            LeuchtePanel.getInstance().setVisible(false);
            this.repaint();
            return;
        }
        if (currentEntity instanceof TdtaStandortMastCustomBean) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("CurrentEntity is Standort");
            }
            final StandortPanel standortPanel = StandortPanel.getInstance();
            standortPanel.setCurrentEntity((TdtaStandortMastCustomBean)currentEntity);
            standortPanel.setElementsNull();

            panMain.add(standortPanel, BorderLayout.CENTER);
            standortPanel.setVisible(true);
        } else if (currentEntity instanceof TdtaLeuchtenCustomBean) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("CurrentEntity is Leuchte");
            }
            final LeuchtePanel leuchtePanel = LeuchtePanel.getInstance();
            panMain.add(leuchtePanel, BorderLayout.CENTER);

            LOG.info("ParentNode: " + ((TdtaLeuchtenCustomBean)currentEntity).getStandort());
            if (((BelisBroker)broker).getWorkbenchWidget().isParentNodeMast(
                            ((BelisBroker)broker).getWorkbenchWidget().getSelectedTreeNode().getLastPathComponent())) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("ParentNode ist Mast");
                }
                leuchtePanel.setInheritedMastPropertiesEnabled(false);
            } else {
                if (broker.isInEditMode()) {
                    leuchtePanel.setInheritedMastPropertiesEnabled(true);
                }
            }

            leuchtePanel.setCurrentEntity((TdtaLeuchtenCustomBean)currentEntity);
            leuchtePanel.setVisible(true);
            leuchtePanel.setElementsNull();
        } else if (currentEntity instanceof LeitungCustomBean) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("CurrentEntity is Leitung");
            }
            final LeitungPanel leitungPanel = LeitungPanel.getInstance();

            leitungPanel.setCurrentEntity((LeitungCustomBean)currentEntity);
            leitungPanel.setElementsNull();

            panMain.add(leitungPanel, BorderLayout.CENTER);
            leitungPanel.setVisible(true);
        } else if (currentEntity instanceof AbzweigdoseCustomBean) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("CurrentEntity is Abzweigdose");
            }
            panMain.add(AbzweigdosePanel.getInstance(), BorderLayout.CENTER);
            AbzweigdosePanel.getInstance().setVisible(true);
        } else if (currentEntity instanceof MauerlascheCustomBean) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("CurrentEntity is Mauerlasche");
            }

            final MauerlaschePanel mauerlaschePanel = MauerlaschePanel.getInstance();

            mauerlaschePanel.setCurrentEntity((MauerlascheCustomBean)currentEntity);
            mauerlaschePanel.setElementsNull();

            panMain.add(mauerlaschePanel, BorderLayout.CENTER);
            mauerlaschePanel.setVisible(true);
        } else if (currentEntity instanceof SchaltstelleCustomBean) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("CurrentEntity is Schaltstelle");
            }

            final SchaltstellePanel schaltstellePanel = SchaltstellePanel.getInstance();

            schaltstellePanel.setCurrentEntity((SchaltstelleCustomBean)currentEntity);
            schaltstellePanel.setElementsNull();

            panMain.add(schaltstellePanel, BorderLayout.CENTER);
            schaltstellePanel.setVisible(true);
        } else {
            LOG.warn("no panel for entity available");
        }
        if (currentEntity instanceof DocumentContainer) {
            final DocumentContainer dc = (DocumentContainer)currentEntity;
            panMain.add(panDokumente, BorderLayout.SOUTH);
            panDokumente.setVisible(true);
        } else {
            panDokumente.setVisible(false);
        }
        this.repaint();
    }
    /**
     * private void validateTest() { for (Binding curBinding : bindingGroup.getBindings()) { if (currentEntity
     * instanceof Standort && panStandort.isAncestorOf((Component) curBinding.getTargetObject())) { log.debug("checking
     * binding"); if (curBinding.getValidator() != null) { try { log.debug("Validator available. Property to check: " +
     * curBinding.getTargetProperty()); log.debug("Property value: " +
     * curBinding.getTargetProperty().getValue(curBinding.getTargetObject())); final Result valResult =
     * curBinding.getValidator().validate(curBinding.getTargetProperty().getValue(curBinding.getTargetObject()));
     * log.debug("Validation result: " + valResult); if (valResult != null) { for (BindingListener bindingListener :
     * bindingGroup.getBindingListeners()) { bindingListener.syncFailed(curBinding,
     * SyncFailure.validationFailure(valResult)); } } } catch (Exception ex) { log.error("Error while validating: ",
     * ex); } } } } }
     *
     * @param  visible  DOCUMENT ME!
     */
    private void setAllPanelsVisible(final boolean visible) {
        StandortPanel.getInstance().setVisible(visible);
        LeuchtePanel.getInstance().setVisible(visible);
        LeitungPanel.getInstance().setVisible(visible);
        MauerlaschePanel.getInstance().setVisible(visible);
        SchaltstellePanel.getInstance().setVisible(visible);
        AbzweigdosePanel.getInstance().setVisible(visible);
    }

    @Override
    public void clearComponent() {
        super.clearComponent();
        setCurrentEntity(null);
        // setAllPanelsVisible(false);
    }

    @Override
    public void setWidgetEditable(final boolean isEditable) {
        super.setWidgetEditable(isEditable);
        // Standort fields
        StandortPanel.getInstance().setPanelEditable(isEditable);

        // Leuchte fields
        LeuchtePanel.getInstance().setPanelEditable(isEditable);

        // Leitungs fields
        LeitungPanel.getInstance().setPanelEditable(isEditable);

        // Mauerlasche fields
        MauerlaschePanel.getInstance().setPanelEditable(isEditable);

        // Schaltstelle fields
        SchaltstellePanel.getInstance().setPanelEditable(isEditable);

        // doc panel
        panDokumente.setEditable(isEditable);
    }

    /**
     * DOCUMENT ME!
     */
    private void commitEdits() {
        if (currentEntity != null) {
            if (currentEntity instanceof TdtaLeuchtenCustomBean) {
                LeuchtePanel.getInstance().commitEdits();
            } else if (currentEntity instanceof TdtaStandortMastCustomBean) {
                StandortPanel.getInstance().commitEdits();
            } else if (currentEntity instanceof SchaltstelleCustomBean) {
                SchaltstellePanel.getInstance().commitEdits();
            } else if (currentEntity instanceof MauerlascheCustomBean) {
                MauerlaschePanel.getInstance().commitEdits();
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

        setLayout(new java.awt.BorderLayout());

        panMain.setLayout(new java.awt.BorderLayout());
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
