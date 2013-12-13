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

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.AbstractAction;
import javax.swing.Action;

import de.cismet.cids.custom.beans.belis2.ArbeitsprotokollaktionCustomBean;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
@org.openide.util.lookup.ServiceProvider(service = AbstractArbeitsprotokollWizard.class)
public class FortfuehrungsantragWizard extends AbstractArbeitsprotokollWizard {

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form VorschaltgeraetwechselWizard.
     */
    public FortfuehrungsantragWizard() {
        initComponents();
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public Class getEntityClass() {
        return null;
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            jLabel1,
            org.openide.util.NbBundle.getMessage(
                FortfuehrungsantragWizard.class,
                "FortfuehrungsantragWizard.jLabel1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(jLabel1, gridBagConstraints);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(jScrollPane1, gridBagConstraints);
    } // </editor-fold>//GEN-END:initComponents

    @Override
    public String getTitle() {
        return "Fortführungsantrag";
    }

    @Override
    public Action getAction() {
        return new AbstractAction("Fortführungsantrag") {

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
    private void clear() {
        jTextArea1.setText(null);
    }

    @Override
    protected Collection<ArbeitsprotokollaktionCustomBean> executeAktionen() {
        final ArbeitsprotokollaktionCustomBean aktion = ArbeitsprotokollaktionCustomBean.createNew();
        aktion.setAenderung("Fortführungsantrag");
        aktion.setAlt(null);
        aktion.setNeu(jTextArea1.getText());

        final Collection<ArbeitsprotokollaktionCustomBean> aktionen = new ArrayList<ArbeitsprotokollaktionCustomBean>();
        aktionen.add(aktion);
        return aktionen;
    }
}