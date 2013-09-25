/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * CreateToolBar.java
 *
 * Created on 22. April 2009, 09:55
 */
package de.cismet.belis.panels;

import org.apache.log4j.Logger;

import javax.swing.JOptionPane;

import de.cismet.belis.broker.BelisBroker;

import de.cismet.cids.custom.beans.belis.TdtaLeuchtenCustomBean;
import de.cismet.cids.custom.beans.belis.TdtaStandortMastCustomBean;

import de.cismet.commons.architecture.interfaces.Editable;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public class CreateToolBar extends javax.swing.JPanel implements Editable {

    //~ Static fields/initializers ---------------------------------------------

    public static final String PROP_CURRENT_ENTITY = "currentEntity";

    private static final Logger LOG = org.apache.log4j.Logger.getLogger(CreateToolBar.class);

    //~ Instance fields --------------------------------------------------------

    protected Object currentEntity = null;
    private BelisBroker broker;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNewAbzweigdose;
    private javax.swing.JButton btnNewArbeitsauftrag;
    private javax.swing.JButton btnNewLeitung;
    private javax.swing.JButton btnNewLeuchte;
    private javax.swing.JButton btnNewMauerlasche;
    private javax.swing.JButton btnNewSchaltstelle;
    private javax.swing.JButton btnNewStandort;
    private javax.swing.JButton btnNewVeranlassung;
    private javax.swing.JButton btnRemove;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form CreateToolBar.
     *
     * @param  broker  DOCUMENT ME!
     */
    public CreateToolBar(final BelisBroker broker) {
        this.broker = broker;
        initComponents();
        setWidgetEditable(false);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void setWidgetEditable(final boolean isEditable) {
        if (!isEditable) {
            setAllButtonsEnabled(isEditable);
        } else {
            checkSetButtonState();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isEnabled  DOCUMENT ME!
     */
    private void setAllButtonsEnabled(final boolean isEnabled) {
        btnNewLeitung.setEnabled(isEnabled);
        btnNewLeuchte.setEnabled(isEnabled);
        btnNewMauerlasche.setEnabled(isEnabled);
        btnNewSchaltstelle.setEnabled(isEnabled);
        btnNewStandort.setEnabled(isEnabled);
        btnNewAbzweigdose.setEnabled(isEnabled);
        btnRemove.setEnabled(isEnabled);
        btnNewVeranlassung.setEnabled(isEnabled);
        btnNewArbeitsauftrag.setEnabled(isEnabled);
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
     * Set the value of currentEntity.
     *
     * @param  currentEntity  new value of currentEntity
     */
    public void setCurrentEntity(final Object currentEntity) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("setCurrentEntity");
        }
        final Object oldCurrentEntity = this.currentEntity;
        this.currentEntity = currentEntity;
        firePropertyChange(PROP_CURRENT_ENTITY, oldCurrentEntity, currentEntity);
        if (!broker.isInEditMode()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("not in edit mode");
            }
        } else {
            checkSetButtonState();
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void checkSetButtonState() {
        if (broker.isInCreateMode()) {
            if (currentEntity == null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("current entity is null. No Entity is selected");
                }
                setAllButtonsEnabled(true);
                // btnNewLeuchte.setEnabled(false);
                btnRemove.setEnabled(false);
            } else if (currentEntity instanceof TdtaStandortMastCustomBean) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Current entity is Standort");
                }
                setAllButtonsEnabled(true);
            } else if (currentEntity instanceof TdtaLeuchtenCustomBean) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("CurrentEntity is Leuchte");
                }
                setAllButtonsEnabled(true);
            } else {
                LOG.warn("Current Entity is: " + currentEntity);
                setAllButtonsEnabled(true);
                if (!(currentEntity instanceof BaseEntity)) {
                    btnRemove.setEnabled(false);
                }
                // btnNewLeuchte.setEnabled(false);
            }
        } else {
            if (currentEntity == null) {
                btnRemove.setEnabled(false);
            } else {
                if (!(currentEntity instanceof BaseEntity)) {
                    btnRemove.setEnabled(false);
                } else {
                    btnRemove.setEnabled(true);
                }
                if ((currentEntity instanceof TdtaStandortMastCustomBean)
                            || ((currentEntity instanceof TdtaLeuchtenCustomBean)
                                && broker.getWorkbenchWidget().isParentNodeMast(
                                    broker.getWorkbenchWidget().getSelectedTreeNode().getLastPathComponent()))) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug(
                            "current selected entity is either mast or leuchte of mast.Enabling adding of new leuchten.");
                    }
                    btnNewLeuchte.setEnabled(true);
                } else {
                    btnNewLeuchte.setEnabled(false);
                }
            }
        }
        this.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        btnNewStandort = new javax.swing.JButton();
        btnNewLeuchte = new javax.swing.JButton();
        btnNewSchaltstelle = new javax.swing.JButton();
        btnNewMauerlasche = new javax.swing.JButton();
        btnNewLeitung = new javax.swing.JButton();
        btnNewAbzweigdose = new javax.swing.JButton();
        btnNewVeranlassung = new javax.swing.JButton();
        btnNewArbeitsauftrag = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();

        setFocusable(false);
        setOpaque(false);
        setLayout(new java.awt.GridBagLayout());

        btnNewStandort.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/belis/resource/icon/22/standort.png"))); // NOI18N
        btnNewStandort.setToolTipText("Neuer Mast hinzufügen");
        btnNewStandort.setBorder(null);
        btnNewStandort.setBorderPainted(false);
        btnNewStandort.setFocusPainted(false);
        btnNewStandort.setMargin(new java.awt.Insets(0, 5, 0, 5));
        btnNewStandort.setMaximumSize(new java.awt.Dimension(23, 23));
        btnNewStandort.setMinimumSize(new java.awt.Dimension(23, 23));
        btnNewStandort.setPreferredSize(new java.awt.Dimension(23, 23));
        btnNewStandort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewStandortActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        add(btnNewStandort, gridBagConstraints);

        btnNewLeuchte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/belis/resource/icon/22/leuchte.png"))); // NOI18N
        btnNewLeuchte.setToolTipText("Neue Leuchte hinzufügen");
        btnNewLeuchte.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnNewLeuchte.setBorderPainted(false);
        btnNewLeuchte.setFocusPainted(false);
        btnNewLeuchte.setMaximumSize(new java.awt.Dimension(23, 23));
        btnNewLeuchte.setMinimumSize(new java.awt.Dimension(23, 23));
        btnNewLeuchte.setPreferredSize(new java.awt.Dimension(23, 23));
        btnNewLeuchte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewLeuchteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 2);
        add(btnNewLeuchte, gridBagConstraints);

        btnNewSchaltstelle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/belis/resource/icon/22/schaltstelle.png"))); // NOI18N
        btnNewSchaltstelle.setToolTipText("Neue Schaltstelle hinzufügen");
        btnNewSchaltstelle.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnNewSchaltstelle.setBorderPainted(false);
        btnNewSchaltstelle.setFocusPainted(false);
        btnNewSchaltstelle.setMaximumSize(new java.awt.Dimension(23, 23));
        btnNewSchaltstelle.setMinimumSize(new java.awt.Dimension(23, 23));
        btnNewSchaltstelle.setPreferredSize(new java.awt.Dimension(23, 23));
        btnNewSchaltstelle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewSchaltstelleActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 2);
        add(btnNewSchaltstelle, gridBagConstraints);

        btnNewMauerlasche.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/belis/resource/icon/22/mauerlasche.png"))); // NOI18N
        btnNewMauerlasche.setToolTipText("Neue Mauerlasche hinzufügen");
        btnNewMauerlasche.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnNewMauerlasche.setBorderPainted(false);
        btnNewMauerlasche.setFocusPainted(false);
        btnNewMauerlasche.setMaximumSize(new java.awt.Dimension(23, 23));
        btnNewMauerlasche.setMinimumSize(new java.awt.Dimension(23, 23));
        btnNewMauerlasche.setPreferredSize(new java.awt.Dimension(23, 23));
        btnNewMauerlasche.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewMauerlascheActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 2);
        add(btnNewMauerlasche, gridBagConstraints);

        btnNewLeitung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/belis/resource/icon/22/leitung.png"))); // NOI18N
        btnNewLeitung.setToolTipText("Neue Leitung hinzufügen");
        btnNewLeitung.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnNewLeitung.setBorderPainted(false);
        btnNewLeitung.setFocusPainted(false);
        btnNewLeitung.setMaximumSize(new java.awt.Dimension(23, 23));
        btnNewLeitung.setMinimumSize(new java.awt.Dimension(23, 23));
        btnNewLeitung.setPreferredSize(new java.awt.Dimension(23, 23));
        btnNewLeitung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewLeitungActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 2);
        add(btnNewLeitung, gridBagConstraints);

        btnNewAbzweigdose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/belis/resource/icon/22/abzweigdose.png"))); // NOI18N
        btnNewAbzweigdose.setToolTipText("Neue Abzweigdose/Zugkasten hinzufügen");
        btnNewAbzweigdose.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnNewAbzweigdose.setBorderPainted(false);
        btnNewAbzweigdose.setFocusPainted(false);
        btnNewAbzweigdose.setMaximumSize(new java.awt.Dimension(23, 23));
        btnNewAbzweigdose.setMinimumSize(new java.awt.Dimension(23, 23));
        btnNewAbzweigdose.setPreferredSize(new java.awt.Dimension(23, 23));
        btnNewAbzweigdose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewAbzweigdoseActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 2);
        add(btnNewAbzweigdose, gridBagConstraints);

        btnNewVeranlassung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/belis/resource/icon/22/veranlassung.png"))); // NOI18N
        btnNewVeranlassung.setToolTipText("Neue Veranlassung hinzufügen");
        btnNewVeranlassung.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnNewVeranlassung.setBorderPainted(false);
        btnNewVeranlassung.setFocusPainted(false);
        btnNewVeranlassung.setMaximumSize(new java.awt.Dimension(23, 23));
        btnNewVeranlassung.setMinimumSize(new java.awt.Dimension(23, 23));
        btnNewVeranlassung.setPreferredSize(new java.awt.Dimension(23, 23));
        btnNewVeranlassung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewVeranlassungActionPerformed(evt);
            }
        });
        add(btnNewVeranlassung, new java.awt.GridBagConstraints());

        btnNewArbeitsauftrag.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/belis/resource/icon/22/arbeitsauftrag.png"))); // NOI18N
        btnNewArbeitsauftrag.setToolTipText("Neuen Arbeitsauftrag hinzufügen");
        btnNewArbeitsauftrag.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnNewArbeitsauftrag.setBorderPainted(false);
        btnNewArbeitsauftrag.setFocusPainted(false);
        btnNewArbeitsauftrag.setMaximumSize(new java.awt.Dimension(23, 23));
        btnNewArbeitsauftrag.setMinimumSize(new java.awt.Dimension(23, 23));
        btnNewArbeitsauftrag.setPreferredSize(new java.awt.Dimension(23, 23));
        btnNewArbeitsauftrag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewArbeitsauftragActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 2);
        add(btnNewArbeitsauftrag, gridBagConstraints);

        btnRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/belis/resource/icon/22/trash.png"))); // NOI18N
        btnRemove.setToolTipText("Ausgewähltes Objekt entfernen");
        btnRemove.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnRemove.setBorderPainted(false);
        btnRemove.setEnabled(false);
        btnRemove.setFocusPainted(false);
        btnRemove.setMaximumSize(new java.awt.Dimension(23, 23));
        btnRemove.setMinimumSize(new java.awt.Dimension(23, 23));
        btnRemove.setPreferredSize(new java.awt.Dimension(23, 23));
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });
        add(btnRemove, new java.awt.GridBagConstraints());
    }// </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnNewStandortActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewStandortActionPerformed
        try {
            if (!checkIfCreationPossible()) {
                return;
            }
            broker.setVetoCheckEnabled(false);
            broker.addNewStandort();
        } finally {
            broker.setVetoCheckEnabled(true);
        }
    }//GEN-LAST:event_btnNewStandortActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnNewLeuchteActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewLeuchteActionPerformed
        if (!checkIfCreationPossible()) {
            return;
        }
        try {
            broker.setVetoCheckEnabled(false);
            if ((currentEntity == null)
                        || ((currentEntity instanceof TdtaLeuchtenCustomBean)
                            && broker.getWorkbenchWidget().isNodeHaengeLeuchte(
                                broker.getWorkbenchWidget().getSelectedTreeNode().getLastPathComponent()))
                        || !((currentEntity instanceof TdtaStandortMastCustomBean)
                            || (currentEntity instanceof TdtaLeuchtenCustomBean))) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Leuchte will be created without Mast");
                }
                broker.addNewLeuchte();
            } else if (((currentEntity instanceof TdtaStandortMastCustomBean)
                            && ((TdtaStandortMastCustomBean)currentEntity).isStandortMast())
                        || (currentEntity instanceof TdtaLeuchtenCustomBean)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Leuchte will be appended to Standort");
                }
                broker.addNewLeuchte(currentEntity);
            } else {
                LOG.warn(
                    "Creation of Leuchte not possible selected object must either be of Standort or Leuchte or non entity: "
                            + currentEntity);
            }
        } finally {
            broker.setVetoCheckEnabled(true);
        }
    }//GEN-LAST:event_btnNewLeuchteActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnNewSchaltstelleActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewSchaltstelleActionPerformed
        if (!checkIfCreationPossible()) {
            return;
        }
        try {
            broker.setVetoCheckEnabled(false);
            broker.addNewSchaltstelle();
        } finally {
            broker.setVetoCheckEnabled(true);
        }
    }//GEN-LAST:event_btnNewSchaltstelleActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnNewMauerlascheActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewMauerlascheActionPerformed
        if (!checkIfCreationPossible()) {
            return;
        }
        try {
            broker.setVetoCheckEnabled(false);
            broker.addNewMauerlasche();
        } finally {
            broker.setVetoCheckEnabled(true);
        }
    }//GEN-LAST:event_btnNewMauerlascheActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnNewLeitungActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewLeitungActionPerformed
        if (!checkIfCreationPossible()) {
            return;
        }
        try {
            broker.setVetoCheckEnabled(false);
            broker.addNewLeitung();
        } finally {
            broker.setVetoCheckEnabled(true);
        }
    }//GEN-LAST:event_btnNewLeitungActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnRemoveActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        if (!checkIfCreationPossible()) {
            return;
        }
        try {
            broker.setVetoCheckEnabled(false);
//            broker.removeEntity(currentEntity);
            broker.removeSelectedEntity();
        } finally {
            broker.setVetoCheckEnabled(true);
        }
    }//GEN-LAST:event_btnRemoveActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnNewAbzweigdoseActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewAbzweigdoseActionPerformed
        if (!checkIfCreationPossible()) {
            return;
        }
        try {
            broker.setVetoCheckEnabled(false);
            broker.addNewAbzweigdose();
        } finally {
            broker.setVetoCheckEnabled(true);
        }
    }//GEN-LAST:event_btnNewAbzweigdoseActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnNewVeranlassungActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewVeranlassungActionPerformed
        if (!checkIfCreationPossible()) {
            return;
        }
        try {
            broker.setVetoCheckEnabled(false);
            broker.addNewVeranlassung();
        } finally {
            broker.setVetoCheckEnabled(true);
        }
    }//GEN-LAST:event_btnNewVeranlassungActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnNewArbeitsauftragActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewArbeitsauftragActionPerformed
        if (!checkIfCreationPossible()) {
            return;
        }
        try {
            broker.setVetoCheckEnabled(false);
            broker.addNewArbeitsauftrag();
        } finally {
            broker.setVetoCheckEnabled(true);
        }
    }//GEN-LAST:event_btnNewArbeitsauftragActionPerformed

   /**
     * could directly be placed in broker.
     *
     * @return  DOCUMENT ME!
     */
    private boolean checkIfCreationPossible() {
        if (broker.isVetoCheckEnabled() && !broker.validateWidgets()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("creationCheck: One or more widgets are invalid. Informing user.");
            }
            final int anwser = broker.askUser();
            if (anwser == JOptionPane.YES_OPTION) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("creationCheck: User wants to cancel changes and create new Objekt.");
                }
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("creationCheck: User wants to correct validation, not creating new objekt.");
                }
                return false;
            }
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("creationCheck: No problem all Widgets are valid.");
            }
        }
        return true;
    }
}
