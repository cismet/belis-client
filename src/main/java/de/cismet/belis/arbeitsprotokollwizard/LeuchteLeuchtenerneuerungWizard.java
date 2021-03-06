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

import javax.swing.AbstractAction;
import javax.swing.Action;

import de.cismet.belis.broker.BelisBroker;
import de.cismet.belis.broker.CidsBroker;

import de.cismet.belis.gui.widget.detailWidgetPanels.ObjectToKeyStringConverter;

import de.cismet.belis2.server.action.ProtokollAktion.AbstractProtokollServerAction;
import de.cismet.belis2.server.action.ProtokollAktion.ProtokollLeuchteLeuchtenerneuerungServerAction;

import de.cismet.cids.custom.beans.belis2.ArbeitsprotokollCustomBean;
import de.cismet.cids.custom.beans.belis2.TkeyLeuchtentypCustomBean;

import de.cismet.cids.server.actions.ServerActionParameter;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
@org.openide.util.lookup.ServiceProvider(service = AbstractArbeitsprotokollWizard.class)
public class LeuchteLeuchtenerneuerungWizard extends AbstractArbeitsprotokollWizard {

    //~ Static fields/initializers ---------------------------------------------

    protected static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            LeuchteLeuchtenerneuerungWizard.class);

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
    public ArbeitsprotokollCustomBean.ChildType getEntityClass() {
        return ArbeitsprotokollCustomBean.ChildType.LEUCHTE;
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
    protected Object executeAktion(final ArbeitsprotokollCustomBean protokoll) throws Exception {
        return CidsBroker.getInstance()
                    .executeServerAction(new ProtokollLeuchteLeuchtenerneuerungServerAction().getTaskName(),
                        null,
                        new ServerActionParameter(
                            AbstractProtokollServerAction.ParameterType.PROTOKOLL_ID.toString(),
                            (protokoll != null) ? Integer.toString(protokoll.getId()) : null),
                        new ServerActionParameter(
                            ProtokollLeuchteLeuchtenerneuerungServerAction.ParameterType.INBETRIEBNAHMEDATUM.toString(),
                            (dapInbetriebnahme.getDate() != null) ? Long.toString(
                                dapInbetriebnahme.getDate().getTime()) : null),
                        new ServerActionParameter(
                            ProtokollLeuchteLeuchtenerneuerungServerAction.ParameterType.LEUCHTENTYP.toString(),
                            (cbxLeuchtentyp.getSelectedItem() != null)
                                ? Integer.toString(
                                    ((TkeyLeuchtentypCustomBean)cbxLeuchtentyp.getSelectedItem()).getId()) : null));
    }
}
