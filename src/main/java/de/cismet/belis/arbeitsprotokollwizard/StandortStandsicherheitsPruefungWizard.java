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

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.Action;

import de.cismet.cids.custom.beans.belis2.ArbeitsprotokollCustomBean;
import de.cismet.cids.custom.beans.belis2.ArbeitsprotokollaktionCustomBean;
import de.cismet.cids.custom.beans.belis2.TdtaStandortMastCustomBean;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
@org.openide.util.lookup.ServiceProvider(service = AbstractArbeitsprotokollWizard.class)
public class StandortStandsicherheitsPruefungWizard extends AbstractArbeitsprotokollWizard {

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXDatePicker dapNaechstesPruefdatum;
    private org.jdesktop.swingx.JXDatePicker dapStandsicherheitspruefung;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField txtVerfahren;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form VorschaltgeraetwechselWizard.
     */
    public StandortStandsicherheitsPruefungWizard() {
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

        jLabel1 = new javax.swing.JLabel();
        dapStandsicherheitspruefung = new org.jdesktop.swingx.JXDatePicker();
        jLabel3 = new javax.swing.JLabel();
        txtVerfahren = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        dapNaechstesPruefdatum = new org.jdesktop.swingx.JXDatePicker();

        setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            jLabel1,
            org.openide.util.NbBundle.getMessage(
                StandortStandsicherheitsPruefungWizard.class,
                "StandortStandsicherheitsPruefungWizard.jLabel1.text")); // NOI18N
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
        add(dapStandsicherheitspruefung, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            jLabel3,
            org.openide.util.NbBundle.getMessage(
                StandortStandsicherheitsPruefungWizard.class,
                "StandortStandsicherheitsPruefungWizard.jLabel3.text")); // NOI18N
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
        add(txtVerfahren, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            jLabel2,
            org.openide.util.NbBundle.getMessage(
                StandortStandsicherheitsPruefungWizard.class,
                "StandortStandsicherheitsPruefungWizard.jLabel2.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(jLabel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(dapNaechstesPruefdatum, gridBagConstraints);
    }                                                                    // </editor-fold>//GEN-END:initComponents

    @Override
    public ArbeitsprotokollCustomBean.ChildType getEntityClass() {
        return ArbeitsprotokollCustomBean.ChildType.STANDORT;
    }

    @Override
    public String getTitle() {
        return "Standsicherheitsprüfung";
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

    @Override
    protected void clear() {
        dapStandsicherheitspruefung.setDate(null);
        txtVerfahren.setText(null);
        dapNaechstesPruefdatum.setDate(null);
    }

    @Override
    protected Collection<ArbeitsprotokollaktionCustomBean> executeAktionen() {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        final TdtaStandortMastCustomBean standort = getProtokoll().getFk_standort();

        final Date altStandsicherheitspruefung = standort.getStandsicherheitspruefung();
        final String altVerfahren = standort.getVerfahren();
        final Date altNaechstesPruefdatum = standort.getNaechstes_pruefdatum();
        final Date neuStandsicherheitspruefung = dapStandsicherheitspruefung.getDate();
        final String neuVerfahren = txtVerfahren.getText();
        final Date neuNaechstesPruefdatum = dapNaechstesPruefdatum.getDate();

        standort.setStandsicherheitspruefung(neuStandsicherheitspruefung);
        standort.setVerfahren(neuVerfahren);
        standort.setNaechstes_pruefdatum(neuNaechstesPruefdatum);

        final ArbeitsprotokollaktionCustomBean standsicherheitspruefungAktion = ArbeitsprotokollaktionCustomBean
                    .createNew();
        standsicherheitspruefungAktion.setAenderung("Standsicherheitsprüfung");
        standsicherheitspruefungAktion.setAlt((altStandsicherheitspruefung != null)
                ? dateFormat.format(altStandsicherheitspruefung) : null);
        standsicherheitspruefungAktion.setNeu((neuStandsicherheitspruefung != null)
                ? dateFormat.format(neuStandsicherheitspruefung) : null);

        final ArbeitsprotokollaktionCustomBean verfahrenAktion = ArbeitsprotokollaktionCustomBean.createNew();
        verfahrenAktion.setAenderung("Verfahren");
        verfahrenAktion.setAlt(altVerfahren);
        verfahrenAktion.setNeu(neuVerfahren);

        final ArbeitsprotokollaktionCustomBean naechstesPruefdatumAktion = ArbeitsprotokollaktionCustomBean.createNew();
        naechstesPruefdatumAktion.setAenderung("Nächstes Prüfdatum");
        naechstesPruefdatumAktion.setAlt((altNaechstesPruefdatum != null) ? dateFormat.format(altNaechstesPruefdatum)
                                                                          : null);
        naechstesPruefdatumAktion.setNeu((neuNaechstesPruefdatum != null) ? dateFormat.format(neuNaechstesPruefdatum)
                                                                          : null);

        final Collection<ArbeitsprotokollaktionCustomBean> aktionen = new ArrayList<ArbeitsprotokollaktionCustomBean>();
        aktionen.add(standsicherheitspruefungAktion);
        aktionen.add(verfahrenAktion);
        aktionen.add(naechstesPruefdatumAktion);

        return aktionen;
    }
}
