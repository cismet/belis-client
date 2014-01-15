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

import de.cismet.belis.broker.CidsBroker;

import de.cismet.cids.custom.beans.belis2.ArbeitsprotokollaktionCustomBean;
import de.cismet.cids.custom.beans.belis2.RundsteuerempfaengerCustomBean;
import de.cismet.cids.custom.beans.belis2.TdtaLeuchtenCustomBean;

import de.cismet.cids.editors.DefaultBindableReferenceCombo;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
@org.openide.util.lookup.ServiceProvider(service = AbstractArbeitsprotokollWizard.class)
public class LeuchteRundsteuerempfaengerwechselWizard extends AbstractArbeitsprotokollWizard {

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbxRundsteuerempfaenger;
    private org.jdesktop.swingx.JXDatePicker dapEinbaudatum;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form VorschaltgeraetwechselWizard.
     */
    public LeuchteRundsteuerempfaengerwechselWizard() {
        initComponents();
        ((DefaultBindableReferenceCombo)cbxRundsteuerempfaenger).setMetaClass(CidsBroker.getInstance()
                    .getBelisMetaClass(RundsteuerempfaengerCustomBean.TABLE));
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
        dapEinbaudatum = new org.jdesktop.swingx.JXDatePicker();
        jLabel2 = new javax.swing.JLabel();
        cbxRundsteuerempfaenger = new DefaultBindableReferenceCombo();

        setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            jLabel1,
            org.openide.util.NbBundle.getMessage(
                LeuchteRundsteuerempfaengerwechselWizard.class,
                "LeuchteRundsteuerempfaengerwechselWizard.jLabel1.text")); // NOI18N
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
        add(dapEinbaudatum, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            jLabel2,
            org.openide.util.NbBundle.getMessage(
                LeuchteRundsteuerempfaengerwechselWizard.class,
                "LeuchteRundsteuerempfaengerwechselWizard.jLabel2.text")); // NOI18N
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
        add(cbxRundsteuerempfaenger, gridBagConstraints);
    } // </editor-fold>//GEN-END:initComponents

    @Override
    public Class getEntityClass() {
        return TdtaLeuchtenCustomBean.class;
    }

    @Override
    public String getTitle() {
        return "Rundsteuerempfängerwechsel";
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
        dapEinbaudatum.setDate(null);
        cbxRundsteuerempfaenger.setSelectedItem(null);
    }

    @Override
    protected Collection<ArbeitsprotokollaktionCustomBean> executeAktionen() {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        final TdtaLeuchtenCustomBean leuchte = getProtokoll().getFk_leuchte();

        final RundsteuerempfaengerCustomBean altRundsteuerempfaenger = leuchte.getRundsteuerempfaenger();
        final Date altEinbaudatum = leuchte.getEinbaudatum();

        final Date neuEinbaudatum = dapEinbaudatum.getDate();
        final RundsteuerempfaengerCustomBean neuRundsteuerempfaenger = (RundsteuerempfaengerCustomBean)
            cbxRundsteuerempfaenger.getSelectedItem();

        leuchte.setRundsteuerempfaenger(neuRundsteuerempfaenger);
        leuchte.setWechseldatum(neuEinbaudatum);

        final ArbeitsprotokollaktionCustomBean einbaudatumAktion = ArbeitsprotokollaktionCustomBean.createNew();
        einbaudatumAktion.setAenderung("Einbaudatum");
        einbaudatumAktion.setAlt((altEinbaudatum != null) ? dateFormat.format(altEinbaudatum) : null);
        einbaudatumAktion.setNeu(dateFormat.format(neuEinbaudatum));

        final ArbeitsprotokollaktionCustomBean rundsteuerempfaengerAktion = ArbeitsprotokollaktionCustomBean
                    .createNew();
        rundsteuerempfaengerAktion.setAenderung("Rundsteuerempfänger");
        rundsteuerempfaengerAktion.setAlt((altRundsteuerempfaenger != null) ? altRundsteuerempfaenger.getKeyString()
                                                                            : null);
        rundsteuerempfaengerAktion.setNeu((neuRundsteuerempfaenger != null) ? neuRundsteuerempfaenger.getKeyString()
                                                                            : null);

        final Collection<ArbeitsprotokollaktionCustomBean> aktionen = new ArrayList<ArbeitsprotokollaktionCustomBean>();
        aktionen.add(einbaudatumAktion);
        aktionen.add(rundsteuerempfaengerAktion);

        return aktionen;
    }
}
