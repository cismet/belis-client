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

import de.cismet.cids.custom.beans.belis2.ArbeitsprotokollaktionCustomBean;
import de.cismet.cids.custom.beans.belis2.TdtaStandortMastCustomBean;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
@org.openide.util.lookup.ServiceProvider(service = AbstractArbeitsprotokollWizard.class)
public class StandortAnstricharbeitenWizard extends AbstractArbeitsprotokollWizard {

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXDatePicker dapMastanstrich;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField txtAnstrichfarbe;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form VorschaltgeraetwechselWizard.
     */
    public StandortAnstricharbeitenWizard() {
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
        dapMastanstrich = new org.jdesktop.swingx.JXDatePicker();
        jLabel3 = new javax.swing.JLabel();
        txtAnstrichfarbe = new javax.swing.JTextField();

        setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            jLabel1,
            org.openide.util.NbBundle.getMessage(
                StandortAnstricharbeitenWizard.class,
                "StandortAnstricharbeitenWizard.jLabel1.text")); // NOI18N
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
        add(dapMastanstrich, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            jLabel3,
            org.openide.util.NbBundle.getMessage(
                StandortAnstricharbeitenWizard.class,
                "StandortAnstricharbeitenWizard.jLabel3.text")); // NOI18N
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
        add(txtAnstrichfarbe, gridBagConstraints);
    }                                                            // </editor-fold>//GEN-END:initComponents

    @Override
    public Class getEntityClass() {
        return TdtaStandortMastCustomBean.class;
    }

    @Override
    public String getTitle() {
        return "Anstricharbeiten";
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
        dapMastanstrich.setDate(null);
        txtAnstrichfarbe.setText(null);
    }

    @Override
    protected Collection<ArbeitsprotokollaktionCustomBean> executeAktionen() {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        final TdtaStandortMastCustomBean standort = getProtokoll().getFk_standort();

        final Date altMastanstrich = standort.getMastanstrich();
        final String altAnstrichfarbe = standort.getAnstrichfarbe();
        final Date neuMastanstrich = dapMastanstrich.getDate();
        final String neuAnstrichfarbe = txtAnstrichfarbe.getText();

        standort.setMastanstrich(neuMastanstrich);
        standort.setAnstrichfarbe(neuAnstrichfarbe);

        final ArbeitsprotokollaktionCustomBean mastanstrichAktion = ArbeitsprotokollaktionCustomBean.createNew();
        mastanstrichAktion.setAenderung("Mastanstrich");
        mastanstrichAktion.setAlt((altMastanstrich != null) ? dateFormat.format(altMastanstrich) : null);
        mastanstrichAktion.setNeu((neuMastanstrich != null) ? dateFormat.format(neuMastanstrich) : null);

        final ArbeitsprotokollaktionCustomBean anstrichfarbeAktion = ArbeitsprotokollaktionCustomBean.createNew();
        anstrichfarbeAktion.setAenderung("Anstrichfarbe");
        anstrichfarbeAktion.setAlt(altAnstrichfarbe);
        anstrichfarbeAktion.setNeu(neuAnstrichfarbe);

        final Collection<ArbeitsprotokollaktionCustomBean> aktionen = new ArrayList<ArbeitsprotokollaktionCustomBean>();
        aktionen.add(mastanstrichAktion);
        aktionen.add(anstrichfarbeAktion);

        return aktionen;
    }
}
