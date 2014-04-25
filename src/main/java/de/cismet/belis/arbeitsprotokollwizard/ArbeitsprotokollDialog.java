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
package de.cismet.belis.arbeitsprotokollwizard;

import java.awt.BorderLayout;

import de.cismet.belis.broker.BelisBroker;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class ArbeitsprotokollDialog extends javax.swing.JDialog {

    //~ Instance fields --------------------------------------------------------

    private final AbstractArbeitsprotokollWizard wizardPanel;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdAbortAktion;
    private javax.swing.JButton cmdExecuteAktion;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel panWizardWrapper;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form AbstractArbeitsprotokollDialog.
     *
     * @param  wizardPanel  DOCUMENT ME!
     * @param  parent       DOCUMENT ME!
     * @param  modal        DOCUMENT ME!
     */
    public ArbeitsprotokollDialog(final AbstractArbeitsprotokollWizard wizardPanel,
            final java.awt.Frame parent,
            final boolean modal) {
        super(parent, modal);
        initComponents();
        this.wizardPanel = wizardPanel;
        panWizardWrapper.add(wizardPanel, BorderLayout.CENTER);
        setTitle(wizardPanel.getTitle());
        pack();
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

        jPanel1 = new javax.swing.JPanel();
        panWizardWrapper = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        cmdAbortAktion = new javax.swing.JButton();
        cmdExecuteAktion = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setLayout(new java.awt.GridBagLayout());

        panWizardWrapper.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(panWizardWrapper, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            cmdAbortAktion,
            org.openide.util.NbBundle.getMessage(
                ArbeitsprotokollDialog.class,
                "ArbeitsprotokollDialog.cmdAbortAktion.text")); // NOI18N
        cmdAbortAktion.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cmdAbortAktionActionPerformed(evt);
                }
            });
        jPanel2.add(cmdAbortAktion);

        org.openide.awt.Mnemonics.setLocalizedText(
            cmdExecuteAktion,
            org.openide.util.NbBundle.getMessage(
                ArbeitsprotokollDialog.class,
                "ArbeitsprotokollDialog.cmdExecuteAktion.text")); // NOI18N
        cmdExecuteAktion.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cmdExecuteAktionActionPerformed(evt);
                }
            });
        jPanel2.add(cmdExecuteAktion);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jPanel1.add(jPanel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(jPanel1, gridBagConstraints);

        pack();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cmdExecuteAktionActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cmdExecuteAktionActionPerformed
        wizardPanel.executeAktion();
        dispose();
        BelisBroker.getInstance().getDetailWidget().getArbeitsprotokollPanel().refreshAktionen();
    }                                                                                    //GEN-LAST:event_cmdExecuteAktionActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cmdAbortAktionActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cmdAbortAktionActionPerformed
        dispose();
    }                                                                                  //GEN-LAST:event_cmdAbortAktionActionPerformed
}
