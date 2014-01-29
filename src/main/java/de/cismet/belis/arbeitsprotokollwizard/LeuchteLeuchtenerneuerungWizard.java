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

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import java.awt.event.ActionEvent;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.Action;

import de.cismet.belis.broker.BelisBroker;

import de.cismet.belis.gui.widget.detailWidgetPanels.ObjectToKeyStringConverter;

import de.cismet.cids.custom.beans.belis2.ArbeitsprotokollaktionCustomBean;
import de.cismet.cids.custom.beans.belis2.TdtaLeuchtenCustomBean;
import de.cismet.cids.custom.beans.belis2.TkeyLeuchtentypCustomBean;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
@org.openide.util.lookup.ServiceProvider(service = AbstractArbeitsprotokollWizard.class)
public class LeuchteLeuchtenerneuerungWizard extends AbstractArbeitsprotokollWizard {

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbxLeuchtentyp;
    private org.jdesktop.swingx.JXDatePicker dapInbetriebnahme;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form VorschaltgeraetwechselWizard.
     */
    public LeuchteLeuchtenerneuerungWizard() {
        initComponents();

        AutoCompleteDecorator.decorate(cbxLeuchtentyp, new ObjectToKeyStringConverter());
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
        dapInbetriebnahme = new org.jdesktop.swingx.JXDatePicker();
        jLabel2 = new javax.swing.JLabel();
        cbxLeuchtentyp = BelisBroker.createKeyTableComboBox(TkeyLeuchtentypCustomBean.TABLE);

        setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            jLabel1,
            org.openide.util.NbBundle.getMessage(
                LeuchteLeuchtenerneuerungWizard.class,
                "LeuchteLeuchtenerneuerungWizard.jLabel1.text")); // NOI18N
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
        add(dapInbetriebnahme, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            jLabel2,
            org.openide.util.NbBundle.getMessage(
                LeuchteLeuchtenerneuerungWizard.class,
                "LeuchteLeuchtenerneuerungWizard.jLabel2.text")); // NOI18N
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
        add(cbxLeuchtentyp, gridBagConstraints);
    } // </editor-fold>//GEN-END:initComponents

    @Override
    public Class getEntityClass() {
        return TdtaLeuchtenCustomBean.class;
    }

    @Override
    public String getTitle() {
        return "Leuchtenerneuerung";
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
        dapInbetriebnahme.setDate(null);
        cbxLeuchtentyp.setSelectedItem(null);
    }

    @Override
    protected Collection<ArbeitsprotokollaktionCustomBean> executeAktionen() {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        final TdtaLeuchtenCustomBean leuchte = getProtokoll().getFk_leuchte();

        final Date altInbetriebnahme = leuchte.getInbetriebnahmeLeuchte();
        final TkeyLeuchtentypCustomBean altLeuchtentyp = leuchte.getLeuchtentyp();

        final Date neuInbetriebnahme = dapInbetriebnahme.getDate();
        final TkeyLeuchtentypCustomBean neuLeuchtentyp = (TkeyLeuchtentypCustomBean)cbxLeuchtentyp.getSelectedItem();

        leuchte.setInbetriebnahmeLeuchte(neuInbetriebnahme);
        leuchte.setLeuchtentyp(neuLeuchtentyp);

        final ArbeitsprotokollaktionCustomBean leuchtentypAktion = ArbeitsprotokollaktionCustomBean.createNew();
        leuchtentypAktion.setAenderung("Leuchtentyp");
        leuchtentypAktion.setAlt((altLeuchtentyp != null) ? altLeuchtentyp.getKeyString() : null);
        leuchtentypAktion.setNeu((neuLeuchtentyp != null) ? neuLeuchtentyp.getKeyString() : null);

        final ArbeitsprotokollaktionCustomBean inbetriebnahmeAktion = ArbeitsprotokollaktionCustomBean.createNew();
        inbetriebnahmeAktion.setAenderung("Inbetriebnahme");
        inbetriebnahmeAktion.setAlt((altInbetriebnahme != null) ? dateFormat.format(altInbetriebnahme) : null);
        inbetriebnahmeAktion.setNeu((neuInbetriebnahme != null) ? dateFormat.format(neuInbetriebnahme) : null);

        final Collection<ArbeitsprotokollaktionCustomBean> aktionen = new ArrayList<ArbeitsprotokollaktionCustomBean>();
        aktionen.add(inbetriebnahmeAktion);
        aktionen.add(leuchtentypAktion);

        return aktionen;
    }
}
