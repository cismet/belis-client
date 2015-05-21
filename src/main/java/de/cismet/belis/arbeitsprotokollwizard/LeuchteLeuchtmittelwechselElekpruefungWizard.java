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

import java.awt.event.ActionEvent;

import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.Action;

import de.cismet.belis.broker.BelisBroker;
import de.cismet.belis.broker.CidsBroker;

import de.cismet.belis2.server.action.ProtokollAction;
import de.cismet.belis2.server.action.leuchte.LeuchtmittelwechselElekpruefungProtokollAction;

import de.cismet.cids.custom.beans.belis2.ArbeitsprotokollCustomBean;
import de.cismet.cids.custom.beans.belis2.LeuchtmittelCustomBean;

import de.cismet.cids.server.actions.ServerActionParameter;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
@org.openide.util.lookup.ServiceProvider(service = AbstractArbeitsprotokollWizard.class)
public class LeuchteLeuchtmittelwechselElekpruefungWizard extends AbstractArbeitsprotokollWizard {

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbxLeuchtmittel;
    private javax.swing.JCheckBox chkErdungIO;
    private org.jdesktop.swingx.JXDatePicker dapLeuchteLeuchtmittelwechsel;
    private org.jdesktop.swingx.JXDatePicker dapStandortElekPruefung;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField txtLebensdauer;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form VorschaltgeraetwechselWizard.
     */
    public LeuchteLeuchtmittelwechselElekpruefungWizard() {
        initComponents();
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public ArbeitsprotokollCustomBean.ChildType getEntityClass() {
        return ArbeitsprotokollCustomBean.ChildType.LEUCHTE;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel1 = new javax.swing.JLabel();
        dapStandortElekPruefung = new org.jdesktop.swingx.JXDatePicker();
        chkErdungIO = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        dapLeuchteLeuchtmittelwechsel = new org.jdesktop.swingx.JXDatePicker();
        jLabel2 = new javax.swing.JLabel();
        cbxLeuchtmittel = BelisBroker.createKeyTableComboBox(LeuchtmittelCustomBean.TABLE);
        jLabel3 = new javax.swing.JLabel();
        txtLebensdauer = new javax.swing.JTextField();

        setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            jLabel1,
            org.openide.util.NbBundle.getMessage(
                LeuchteLeuchtmittelwechselElekpruefungWizard.class,
                "LeuchteLeuchtmittelwechselElekpruefungWizard.jLabel1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(jLabel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(dapStandortElekPruefung, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            chkErdungIO,
            org.openide.util.NbBundle.getMessage(
                LeuchteLeuchtmittelwechselElekpruefungWizard.class,
                "LeuchteLeuchtmittelwechselElekpruefungWizard.chkErdungIO.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(chkErdungIO, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            jLabel4,
            org.openide.util.NbBundle.getMessage(
                LeuchteLeuchtmittelwechselElekpruefungWizard.class,
                "LeuchteLeuchtmittelwechselElekpruefungWizard.jLabel4.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(jLabel4, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(dapLeuchteLeuchtmittelwechsel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            jLabel2,
            org.openide.util.NbBundle.getMessage(
                LeuchteLeuchtmittelwechselElekpruefungWizard.class,
                "LeuchteLeuchtmittelwechselElekpruefungWizard.jLabel2.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(jLabel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(cbxLeuchtmittel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            jLabel3,
            org.openide.util.NbBundle.getMessage(
                LeuchteLeuchtmittelwechselElekpruefungWizard.class,
                "LeuchteLeuchtmittelwechselElekpruefungWizard.jLabel3.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(jLabel3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(txtLebensdauer, gridBagConstraints);
    }                                                                          // </editor-fold>//GEN-END:initComponents

    @Override
    public String getTitle() {
        return "Leuchtmittelwechsel (mit EP)";
    }

    @Override
    public Action getAction() {
        return new AbstractAction(getTitle()) {

                @Override
                public void actionPerformed(final ActionEvent e) {
                    clear();
                    showDialog();
                }
            };
    }

    /**
     * DOCUMENT ME!
     */
    @Override
    protected void clear() {
        dapLeuchteLeuchtmittelwechsel.setDate(null);
        cbxLeuchtmittel.setSelectedItem(null);
        chkErdungIO.setSelected(false);
        txtLebensdauer.setText(null);
        dapStandortElekPruefung.setDate(new Date());
    }

    @Override
    protected void executeAktion(final ArbeitsprotokollCustomBean protokoll) throws Exception {
        Double lebensdauer = null;
        try {
            lebensdauer = Double.parseDouble(txtLebensdauer.getText());
        } catch (Exception e) {
            lebensdauer = null;
        }

        CidsBroker.getInstance()
                .executeServerAction(new LeuchtmittelwechselElekpruefungProtokollAction().getTaskName(),
                    null,
                    new ServerActionParameter(
                        ProtokollAction.ParameterType.PROTOKOLL_ID.toString(),
                        Integer.toString(protokoll.getId())),
                    new ServerActionParameter(
                        LeuchtmittelwechselElekpruefungProtokollAction.ParameterType.PRUEFDATUM.toString(),
                        Long.toString(dapStandortElekPruefung.getDate().getTime())),
                    new ServerActionParameter(
                        LeuchtmittelwechselElekpruefungProtokollAction.ParameterType.ERDUNG_IN_ORDNUNG.toString(),
                        chkErdungIO.isSelected() ? "ja" : "nein"),
                    new ServerActionParameter(
                        LeuchtmittelwechselElekpruefungProtokollAction.ParameterType.WECHSELDATUM.toString(),
                        Long.toString(dapLeuchteLeuchtmittelwechsel.getDate().getTime())),
                    new ServerActionParameter(
                        LeuchtmittelwechselElekpruefungProtokollAction.ParameterType.LEUCHTMITTEL.toString(),
                        Integer.toString(((LeuchtmittelCustomBean)cbxLeuchtmittel.getSelectedItem()).getId())),
                    new ServerActionParameter(
                        LeuchtmittelwechselElekpruefungProtokollAction.ParameterType.LEBENSDAUER.toString(),
                        Double.toString(lebensdauer)));
    }
}
