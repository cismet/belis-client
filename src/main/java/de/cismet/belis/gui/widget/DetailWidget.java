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

import org.openide.util.Exceptions;

import java.awt.Dimension;

import java.beans.PropertyChangeEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.swing.JOptionPane;

import de.cismet.belis.broker.BelisBroker;

import de.cismet.belis.gui.widget.detailWidgetPanels.AbstractDetailWidgetPanel;
import de.cismet.belis.gui.widget.detailWidgetPanels.AbzweigdosePanel;
import de.cismet.belis.gui.widget.detailWidgetPanels.ArbeitsauftragPanel;
import de.cismet.belis.gui.widget.detailWidgetPanels.ArbeitsprotokollPanel;
import de.cismet.belis.gui.widget.detailWidgetPanels.GeometriePanel;
import de.cismet.belis.gui.widget.detailWidgetPanels.LeitungPanel;
import de.cismet.belis.gui.widget.detailWidgetPanels.LeuchtePanel;
import de.cismet.belis.gui.widget.detailWidgetPanels.MauerlaschePanel;
import de.cismet.belis.gui.widget.detailWidgetPanels.SchaltstellePanel;
import de.cismet.belis.gui.widget.detailWidgetPanels.StandortPanel;
import de.cismet.belis.gui.widget.detailWidgetPanels.VeranlassungPanel;

import de.cismet.cids.custom.beans.belis2.AbzweigdoseCustomBean;
import de.cismet.cids.custom.beans.belis2.ArbeitsauftragCustomBean;
import de.cismet.cids.custom.beans.belis2.ArbeitsprotokollCustomBean;
import de.cismet.cids.custom.beans.belis2.GeometrieCustomBean;
import de.cismet.cids.custom.beans.belis2.LeitungCustomBean;
import de.cismet.cids.custom.beans.belis2.MauerlascheCustomBean;
import de.cismet.cids.custom.beans.belis2.SchaltstelleCustomBean;
import de.cismet.cids.custom.beans.belis2.TdtaLeuchtenCustomBean;
import de.cismet.cids.custom.beans.belis2.TdtaStandortMastCustomBean;
import de.cismet.cids.custom.beans.belis2.VeranlassungCustomBean;

import de.cismet.commons.architecture.validation.Validatable;

import de.cismet.commons.server.entity.WorkbenchEntity;
import de.cismet.commons.server.interfaces.DocumentContainer;

import de.cismet.veto.VetoException;

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

    protected Collection<WorkbenchEntity> currentEntities;
    protected WorkbenchEntity currentEntity = null;

    private final StandortPanel standortPanel;
    private final LeuchtePanel leuchtePanel;
    private final LeitungPanel leitungPanel;
    private final AbzweigdosePanel abzweigdosePanel;
    private final MauerlaschePanel mauerlaschePanel;
    private final SchaltstellePanel schaltstellePanel;
    private final VeranlassungPanel veranlassungPanel;
    private final ArbeitsauftragPanel arbeitsauftragPanel;
    private final ArbeitsprotokollPanel arbeitsprotokollPanel;
    private final GeometriePanel geometriePanel;

    private AbstractDetailWidgetPanel currentDetailWidgetPanel = null;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel labDokumente;
    private de.cismet.belis.gui.utils.DocumentPanel panDokumente;
    private javax.swing.JTabbedPane panMain;
    private javax.swing.JScrollPane scpMain;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new DetailWidget object.
     */
    public DetailWidget() {
        try {
            setWidgetName("Details");
            initComponents();

            standortPanel = new StandortPanel();
            leuchtePanel = new LeuchtePanel();
            leitungPanel = new LeitungPanel();
            abzweigdosePanel = new AbzweigdosePanel();
            mauerlaschePanel = new MauerlaschePanel();
            schaltstellePanel = new SchaltstellePanel();
            geometriePanel = new GeometriePanel();
            veranlassungPanel = new VeranlassungPanel();
            arbeitsauftragPanel = new ArbeitsauftragPanel();
            arbeitsprotokollPanel = new ArbeitsprotokollPanel();
        } catch (final RuntimeException ex) {
            LOG.fatal("error while initializing DetailWidget", ex);
            throw ex;
        }
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
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public WorkbenchEntity getCurrentEntity() {
        return currentEntity;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   currentEntity  DOCUMENT ME!
     *
     * @throws  VetoException  DOCUMENT ME!
     */
    public void setCurrentEntity(final WorkbenchEntity currentEntity) throws VetoException {
        final WorkbenchEntity oldCurrentEntity = this.currentEntity;
        try {
            ((de.cismet.cids.dynamics.CidsBean)currentEntity).setProperty("ausdehnung_wgs84", null);
        } catch (final Exception ex) {
        }

        if (oldCurrentEntity != currentEntity) {
            if (!getBroker().validateWidgets()) {
                final int anwser = getBroker().askUser();
                if (anwser != JOptionPane.YES_OPTION) {
                    BelisBroker.getInstance().getWorkbenchWidget().setTreeSelectionVeto(true);
                    throw new VetoException();
                }
            }

            bindingGroup.unbind();
            this.currentEntity = currentEntity;
            bindingGroup.bind();
            firePropertyChange(PROP_CURRENT_ENTITY, oldCurrentEntity, currentEntity);
        }
    }

    /**
     * Get the value of currentEntity.
     *
     * @return  the value of currentEntity
     */
    public Collection<WorkbenchEntity> getCurrentEntities() {
        return currentEntities;
    }

    /**
     * Set the value of currentEntity TODO change parameter type to DocumentContainer or BaseEntity.
     *
     * @param   currentEntities  new value of currentEntity
     * @param   parentEntity     DOCUMENT ME!
     * @param   wantsToEdit      isEditable DOCUMENT ME!
     *
     * @throws  VetoException  DOCUMENT ME!
     */
    public void setCurrentEntities(final Collection<WorkbenchEntity> currentEntities,
            final WorkbenchEntity parentEntity,
            final boolean wantsToEdit) throws VetoException {
        final boolean isAllowedToEdit;

        this.currentEntities = currentEntities;

        final WorkbenchEntity selectedEntity;
        if ((currentEntities != null) && !currentEntities.isEmpty()) {
            selectedEntity = currentEntities.iterator().next();
        } else {
            selectedEntity = null;
        }

        if (selectedEntity == null) {
            currentDetailWidgetPanel = null;
            isAllowedToEdit = false;
            setCurrentEntity(null);
        } else {
//            final boolean basicEditEnabled = CidsBroker.getInstance().checkForEditBasic();
//            final boolean veranlassungEditEnabled = CidsBroker.getInstance().checkForEditVeranlassung();
//            final boolean arbeitsauftragEditEnabled = CidsBroker.getInstance().checkForEditArbeitsauftrag();

            if (selectedEntity instanceof TdtaStandortMastCustomBean) {
                setCurrentEntity(selectedEntity);
                standortPanel.setCurrentEntity((TdtaStandortMastCustomBean)currentEntity);
                currentDetailWidgetPanel = standortPanel;
                isAllowedToEdit = ((TdtaStandortMastCustomBean)currentEntity).isEditAllowed();
            } else if (selectedEntity instanceof TdtaLeuchtenCustomBean) {
                setCurrentEntity(selectedEntity);
                if (getBroker().getWorkbenchWidget().isParentNodeMast(
                                getBroker().getWorkbenchWidget().getSelectedTreeNode().getLastPathComponent())) {
                    leuchtePanel.setInheritedMastPropertiesEnabled(false);
                } else {
                    if (getBroker().isInEditMode()) {
                        leuchtePanel.setInheritedMastPropertiesEnabled(true);
                    }
                }
                leuchtePanel.setCurrentEntity((TdtaLeuchtenCustomBean)currentEntity);
                currentDetailWidgetPanel = leuchtePanel;
                isAllowedToEdit = ((TdtaLeuchtenCustomBean)currentEntity).isEditAllowed();
            } else if (selectedEntity instanceof LeitungCustomBean) {
                setCurrentEntity(selectedEntity);
                leitungPanel.setCurrentEntity((LeitungCustomBean)currentEntity);
                currentDetailWidgetPanel = leitungPanel;
                isAllowedToEdit = ((LeitungCustomBean)currentEntity).isEditAllowed();
            } else if (selectedEntity instanceof AbzweigdoseCustomBean) {
                setCurrentEntity(selectedEntity);
                abzweigdosePanel.setCurrentEntity((AbzweigdoseCustomBean)currentEntity);
                currentDetailWidgetPanel = abzweigdosePanel;
                isAllowedToEdit = ((AbzweigdoseCustomBean)currentEntity).isEditAllowed();
            } else if (selectedEntity instanceof MauerlascheCustomBean) {
                setCurrentEntity(selectedEntity);
                mauerlaschePanel.setCurrentEntity((MauerlascheCustomBean)currentEntity);
                currentDetailWidgetPanel = mauerlaschePanel;
                isAllowedToEdit = ((MauerlascheCustomBean)currentEntity).isEditAllowed();
            } else if (selectedEntity instanceof SchaltstelleCustomBean) {
                setCurrentEntity(selectedEntity);
                schaltstellePanel.setCurrentEntity((SchaltstelleCustomBean)currentEntity);
                currentDetailWidgetPanel = schaltstellePanel;
                isAllowedToEdit = ((SchaltstelleCustomBean)currentEntity).isEditAllowed();
            } else if (selectedEntity instanceof VeranlassungCustomBean) {
                setCurrentEntity(selectedEntity);
                veranlassungPanel.setCurrentEntity((VeranlassungCustomBean)currentEntity);
                currentDetailWidgetPanel = veranlassungPanel;
                isAllowedToEdit = ((VeranlassungCustomBean)currentEntity).isEditAllowed();
            } else if (selectedEntity instanceof GeometrieCustomBean) {
                setCurrentEntity(selectedEntity);
                geometriePanel.setCurrentEntity((GeometrieCustomBean)currentEntity);
                currentDetailWidgetPanel = geometriePanel;
                isAllowedToEdit = ((GeometrieCustomBean)currentEntity).isEditAllowed();
            } else if (selectedEntity instanceof ArbeitsauftragCustomBean) {
                setCurrentEntity(selectedEntity);
                arbeitsauftragPanel.setCurrentEntity((ArbeitsauftragCustomBean)currentEntity);
                currentDetailWidgetPanel = arbeitsauftragPanel;
                isAllowedToEdit = ((ArbeitsauftragCustomBean)currentEntity).isEditAllowed();
            } else if (selectedEntity instanceof ArbeitsprotokollCustomBean) {
                if (parentEntity instanceof ArbeitsauftragCustomBean) {
                    setCurrentEntity(parentEntity);
                    arbeitsauftragPanel.setCurrentEntity((ArbeitsauftragCustomBean)parentEntity);
                    arbeitsauftragPanel.setSelectedProtokolle((Collection)currentEntities);
                    currentDetailWidgetPanel = arbeitsauftragPanel;
                    isAllowedToEdit = ((ArbeitsauftragCustomBean)parentEntity).isEditAllowed();
                } else {
                    setCurrentEntity(null);
                    currentDetailWidgetPanel = null;
                    isAllowedToEdit = false;
                }
            } else {
                setCurrentEntity(null);
                currentDetailWidgetPanel = null;
                isAllowedToEdit = false;
            }

            if (currentEntity instanceof DocumentContainer) {
                panDokumente.setDokumente(((DocumentContainer)currentEntity).getDokumente());
                panMain.setTabComponentAt(1, labDokumente);
            }
        }

        final boolean isEnabled = wantsToEdit && isAllowedToEdit;
        if (currentDetailWidgetPanel == null) {
            panDokumente.setDokumente(null);
        } else {
            currentDetailWidgetPanel.setElementsNull();
            currentDetailWidgetPanel.setPanelEditable(isEnabled);
        }
        setWidgetEditable(isEnabled);

        showPanel(currentDetailWidgetPanel);
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
        try {
            setCurrentEntities(null, null, false);
        } catch (VetoException ex) {
        }
    }

    @Override
    public void setWidgetEditable(final boolean isEditable) {
        super.setWidgetEditable(isEditable);
        if (currentDetailWidgetPanel != null) {
            currentDetailWidgetPanel.setPanelEditable(isEditable);
        }
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
        java.awt.GridBagConstraints gridBagConstraints;
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        labDokumente = new javax.swing.JLabel();
        panMain = new javax.swing.JTabbedPane();
        scpMain = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        panDokumente = new de.cismet.belis.gui.utils.DocumentPanel();
        jPanel3 = new javax.swing.JPanel();

        labDokumente.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/16/copy.png"))); // NOI18N
        labDokumente.setText("Dokumente");

        setLayout(new java.awt.BorderLayout());

        scpMain.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        scpMain.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panMain.addTab("", scpMain);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        final org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.dokumente}"),
                panDokumente,
                org.jdesktop.beansbinding.BeanProperty.create("dokumente"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPanel2.add(panDokumente, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.weighty = 1.0;
        jPanel2.add(jPanel3, gridBagConstraints);

        panMain.addTab("", jPanel2);

        add(panMain, java.awt.BorderLayout.CENTER);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    @Override
    public int getStatus() {
        if (currentDetailWidgetPanel != null) {
            final int status = currentDetailWidgetPanel.getStatus();
            validationMessage = currentDetailWidgetPanel.getValidationMessage();
            return status;
        } else {
            validationMessage = "";
            return Validatable.VALID;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public StandortPanel getStandortPanel() {
        return standortPanel;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public LeuchtePanel getLeuchtePanel() {
        return leuchtePanel;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public LeitungPanel getLeitungPanel() {
        return leitungPanel;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public AbzweigdosePanel getAbzweigdosePanel() {
        return abzweigdosePanel;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public MauerlaschePanel getMauerlaschePanel() {
        return mauerlaschePanel;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public SchaltstellePanel getSchaltstellePanel() {
        return schaltstellePanel;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public VeranlassungPanel getVeranlassungPanel() {
        return veranlassungPanel;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public ArbeitsauftragPanel getArbeitsauftragPanel() {
        return arbeitsauftragPanel;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public ArbeitsprotokollPanel getArbeitsprotokollPanel() {
        return arbeitsprotokollPanel;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public GeometriePanel getGeometriePanel() {
        return geometriePanel;
    }
}
