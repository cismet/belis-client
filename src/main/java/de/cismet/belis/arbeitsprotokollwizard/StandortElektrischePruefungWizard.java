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

import java.sql.Timestamp;

import java.util.Collection;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.Action;

import de.cismet.belis.broker.CidsBroker;

import de.cismet.belis2.server.action.ProtokollAction;
import de.cismet.belis2.server.action.standort.ElektrischePruefungProtokollAction;

import de.cismet.cids.custom.beans.belis2.ArbeitsprotokollCustomBean;
import de.cismet.cids.custom.beans.belis2.ArbeitsprotokollaktionCustomBean;
import de.cismet.cids.custom.beans.belis2.TdtaStandortMastCustomBean;

import de.cismet.cids.server.actions.ServerActionParameter;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
@org.openide.util.lookup.ServiceProvider(service = AbstractArbeitsprotokollWizard.class)
public class StandortElektrischePruefungWizard extends AbstractArbeitsprotokollWizard {

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chkErdungIO;
    private org.jdesktop.swingx.JXDatePicker dapStandortElekPruefung;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form VorschaltgeraetwechselWizard.
     */
    public StandortElektrischePruefungWizard() {
        initComponents();
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public ArbeitsprotokollCustomBean.ChildType getEntityClass() {
        return ArbeitsprotokollCustomBean.ChildType.STANDORT;
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

        setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            jLabel1,
            org.openide.util.NbBundle.getMessage(
                StandortElektrischePruefungWizard.class,
                "StandortElektrischePruefungWizard.jLabel1.text")); // NOI18N
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
                StandortElektrischePruefungWizard.class,
                "StandortElektrischePruefungWizard.chkErdungIO.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(chkErdungIO, gridBagConstraints);
    }                                                                   // </editor-fold>//GEN-END:initComponents

    @Override
    public String getTitle() {
        return "Elektrische Prüfung";
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
        chkErdungIO.setSelected(false);
        dapStandortElekPruefung.setDate(new Date());
    }

    @Override
    protected void executeAktion(final ArbeitsprotokollCustomBean protokoll) throws Exception {
        CidsBroker.getInstance()
                .executeServerAction(new ElektrischePruefungProtokollAction().getTaskName(),
                    null,
                    new ServerActionParameter(
                        ProtokollAction.ParameterType.PROTOKOLL_ID.toString(),
                        Integer.toString(protokoll.getId())),
                    new ServerActionParameter(
                        ElektrischePruefungProtokollAction.ParameterType.PRUEFDATUM.toString(),
                        Long.toString(dapStandortElekPruefung.getDate().getTime())),
                    new ServerActionParameter(
                        ElektrischePruefungProtokollAction.ParameterType.ERDUNG_IN_ORDNUNG.toString(),
                        chkErdungIO.isSelected() ? "ja" : "nein"));

        final TdtaStandortMastCustomBean standort = protokoll.getFk_standort();

        final Collection<ArbeitsprotokollaktionCustomBean> aktionen = protokoll.getN_aktionen();
        aktionen.add(createAktion(
                "Elektrische Prüfung",
                standort,
                TdtaStandortMastCustomBean.PROP__ELEK_PRUEFUNG,
                new Timestamp(dapStandortElekPruefung.getDate().getTime())));
        aktionen.add(createAktion(
                "Erdung in Ordnung",
                standort,
                TdtaStandortMastCustomBean.PROP__ERDUNG,
                chkErdungIO.isSelected()));
    }
}
