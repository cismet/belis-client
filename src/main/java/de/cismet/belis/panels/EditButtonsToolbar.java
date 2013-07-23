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
package de.cismet.belis.panels;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import de.cismet.belis.broker.BelisBroker;

import de.cismet.tools.CurrentStackTrace;

/**
 * DOCUMENT ME!
 *
 * @author   Gilles Baatz
 * @version  $Revision$, $Date$
 */
public class EditButtonsToolbar extends javax.swing.JPanel {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(EditButtonsToolbar.class);

    //~ Instance fields --------------------------------------------------------

    BelisBroker broker = BelisBroker.getInstance();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAcceptChanges;
    private javax.swing.JButton btnDiscardChanges;
    private javax.swing.JButton btnSwitchInCreateMode;
    private javax.swing.JButton btnSwitchInEditmode;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form EditButtonsPanel.
     */
    public EditButtonsToolbar() {
        initComponents();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        btnSwitchInEditmode = new javax.swing.JButton();
        btnSwitchInCreateMode = new javax.swing.JButton();
        btnAcceptChanges = new javax.swing.JButton();
        btnDiscardChanges = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(110, 27));
        setMinimumSize(new java.awt.Dimension(110, 27));
        setPreferredSize(new java.awt.Dimension(110, 27));
        setLayout(new java.awt.GridBagLayout());

        btnSwitchInEditmode.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/22/editMode.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnSwitchInEditmode, "");
        btnSwitchInEditmode.setToolTipText("Editormodus");
        btnSwitchInEditmode.setBorderPainted(false);
        btnSwitchInEditmode.setFocusable(false);
        btnSwitchInEditmode.setMaximumSize(new java.awt.Dimension(23, 23));
        btnSwitchInEditmode.setMinimumSize(new java.awt.Dimension(23, 23));
        btnSwitchInEditmode.setPreferredSize(new java.awt.Dimension(23, 23));
        btnSwitchInEditmode.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnSwitchInEditmodeActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 2);
        add(btnSwitchInEditmode, gridBagConstraints);

        btnSwitchInCreateMode.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/22/createMode.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnSwitchInCreateMode, "");
        btnSwitchInCreateMode.setToolTipText("Anlegenmodus");
        btnSwitchInCreateMode.setBorderPainted(false);
        btnSwitchInCreateMode.setFocusable(false);
        btnSwitchInCreateMode.setLabel(org.openide.util.NbBundle.getMessage(
                EditButtonsToolbar.class,
                "EditButtonsToolbar.btnSwitchInCreateMode.label"));                           // NOI18N
        btnSwitchInCreateMode.setMaximumSize(new java.awt.Dimension(23, 23));
        btnSwitchInCreateMode.setMinimumSize(new java.awt.Dimension(23, 23));
        btnSwitchInCreateMode.setPreferredSize(new java.awt.Dimension(23, 23));
        btnSwitchInCreateMode.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnSwitchInCreateModeActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        add(btnSwitchInCreateMode, gridBagConstraints);

        btnAcceptChanges.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/22/accept.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnAcceptChanges, "");
        btnAcceptChanges.setToolTipText("Änderungen annehmen");
        btnAcceptChanges.setBorderPainted(false);
        btnAcceptChanges.setFocusable(false);
        btnAcceptChanges.setLabel(org.openide.util.NbBundle.getMessage(
                EditButtonsToolbar.class,
                "EditButtonsToolbar.btnAcceptChanges.label"));                            // NOI18N
        btnAcceptChanges.setMaximumSize(new java.awt.Dimension(23, 23));
        btnAcceptChanges.setMinimumSize(new java.awt.Dimension(23, 23));
        btnAcceptChanges.setPreferredSize(new java.awt.Dimension(23, 23));
        btnAcceptChanges.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnAcceptChangesActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        add(btnAcceptChanges, gridBagConstraints);

        btnDiscardChanges.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/22/cancel.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnDiscardChanges, "");
        btnDiscardChanges.setToolTipText("Änderungen Abbrechen");
        btnDiscardChanges.setBorderPainted(false);
        btnDiscardChanges.setFocusable(false);
        btnDiscardChanges.setLabel(org.openide.util.NbBundle.getMessage(
                EditButtonsToolbar.class,
                "EditButtonsToolbar.btnDiscardChanges.label"));                           // NOI18N
        btnDiscardChanges.setMaximumSize(new java.awt.Dimension(23, 23));
        btnDiscardChanges.setMinimumSize(new java.awt.Dimension(23, 23));
        btnDiscardChanges.setPreferredSize(new java.awt.Dimension(23, 23));
        btnDiscardChanges.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnDiscardChangesActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 3);
        add(btnDiscardChanges, gridBagConstraints);
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnSwitchInEditmodeActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnSwitchInEditmodeActionPerformed
        if (LOG.isDebugEnabled()) {
            LOG.debug("Versuche in Editiermodus zu wechseln: ");
        }
        try {
            broker.switchEditMode();

            btnSwitchInEditmode.setEnabled(false);
            btnSwitchInCreateMode.setEnabled(false);

            btnAcceptChanges.setEnabled(true);
            btnDiscardChanges.setEnabled(true);
            broker.setTitleBarComponentpainter(BelisBroker.EDIT_MODE_COLOR); //
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Fehler beim anlegen der Sperre", ex);
            }
        }

        broker.getMappingComponent().setReadOnly(false);
        if (LOG.isDebugEnabled()) {
            LOG.debug("ist im Editiermodus: " + broker.isInEditMode());
        }
    } //GEN-LAST:event_btnSwitchInEditmodeActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnSwitchInCreateModeActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnSwitchInCreateModeActionPerformed
        if (LOG.isDebugEnabled()) {
            LOG.debug("try to switch in createmode", new CurrentStackTrace());
        }
        try {
            broker.isPendingForCreateMode.set(true);
            broker.switchEditMode();

            btnSwitchInEditmode.setEnabled(false);
            btnSwitchInCreateMode.setEnabled(false);

            btnAcceptChanges.setEnabled(true);
            btnDiscardChanges.setEnabled(true);
            broker.setTitleBarComponentpainter(BelisBroker.EDIT_MODE_COLOR); //
            // ToDo CreateFlag
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Fehler beim anlegen der Sperre", ex);
            }
        }

        broker.getMappingComponent().setReadOnly(false);
        if (LOG.isDebugEnabled()) {
            LOG.debug("ist im Editiermodus: " + broker.isInEditMode());
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("ist im Createmodus: " + broker.isInCreateMode());
        }
    } //GEN-LAST:event_btnSwitchInCreateModeActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnAcceptChangesActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnAcceptChangesActionPerformed
        try {
            if (broker.isInEditMode()) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Versuche aus Editiermodus heraus zu wechseln: ");
                }
                final boolean isValid = broker.validateWidgets();
                if (isValid) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Alle Änderungen sind valide: " + isValid);
                    }
                    // ToDo make generic
                    final int answer = JOptionPane.showConfirmDialog(
                            broker.getParentComponent(),
                            "Wollen Sie die gemachten Änderungen speichern?",
                            "Belis Änderungen",
                            JOptionPane.YES_NO_OPTION);
                    if (answer == JOptionPane.YES_OPTION) {
                        // LagisBroker.getInstance().saveCurrentFlurstueck();
                        broker.fireSaveStartedAndExecuteSaveCancelWorker();
                    } else {
                        return;
                    }
                } else {
                    final String reason = broker.getCurrentValidationErrorMessage();
                    if (LOG.isDebugEnabled()) {
                        LOG.debug(
                            "Es kann nicht gespeichert werden, da nicht alle Komponenten valide sind. Grund:\n"
                                    + reason);
                    }
                    JOptionPane.showMessageDialog(
                        broker.getParentComponent(),
                        "Änderungen können nur gespeichert werden, wenn alle Inhalte korrekt sind:\n\n"
                                + reason
                                + "\n\nBitte berichtigen Sie die Inhalte oder machen Sie die jeweiligen Änderungen rückgängig.",
                        "Fehler",
                        JOptionPane.WARNING_MESSAGE);
                }
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("ist im Editiermodus: " + broker.isInEditMode());
            }
        } catch (Exception ex) {
            LOG.error("Fehler beim akzeptieren von Änderungen: ", ex);
            broker.showSaveErrorDialog();
        }
    } //GEN-LAST:event_btnAcceptChangesActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnDiscardChangesActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnDiscardChangesActionPerformed
        if (broker.isInEditMode()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Versuche aus Editiermodus heraus zu wechseln: ");
            }
            // ToDo make generic
            final int answer = JOptionPane.showConfirmDialog(
                    broker.getParentComponent(),
                    "Wollen Sie die gemachten Änderungen verwerfen?",
                    "Belis Änderungen",
                    JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.NO_OPTION) {
                return;
            }

            try {
                broker.fireCancelStartedAndExecuteSaveCancelWorker();
                // ((DefaultFeatureCollection)LagisBroker.getInstance().getMappingComponent().getFeatureCollection()).setAllFeaturesEditable(false);
                // TODO TEST IT!!!!
                // TODO EDT
            } catch (Exception ex) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Fehler beim lösen der Sperre", ex);
                }
            }
            if (LOG.isDebugEnabled()) {
                // btnOpenWizard.setEnabled(true);
                // LagisBroker.getInstance().reloadFlurstueck();
                LOG.debug("ist im Editiermodus: " + broker.isInEditMode());
            }
        }
    } //GEN-LAST:event_btnDiscardChangesActionPerformed
}
