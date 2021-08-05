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

import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;

import java.awt.event.ActionEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;

import de.cismet.belis.broker.CidsBroker;

import de.cismet.belis2.server.action.ProtokollAktion.AbstractProtokollServerAction;
import de.cismet.belis2.server.action.ProtokollAktion.ProtokollMauerlaschePruefungServerAction;

import de.cismet.cids.custom.beans.belis2.ArbeitsprotokollCustomBean;
import de.cismet.cids.custom.beans.belis2.DmsUrlCustomBean;

import de.cismet.cids.server.actions.ServerActionParameter;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
@org.openide.util.lookup.ServiceProvider(service = AbstractArbeitsprotokollWizard.class)
public class MauerlaschePruefungWizard extends AbstractArbeitsprotokollWizard {

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXDatePicker dapPruefung;
    private de.cismet.belis.gui.utils.DocumentPanel documentPanel1;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form VorschaltgeraetwechselWizard.
     */
    public MauerlaschePruefungWizard() {
        initComponents();
        final List<DmsUrlCustomBean> list = new ArrayList();
        final ObservableList<DmsUrlCustomBean> observableList = ObservableCollections.observableList(list);
        documentPanel1.setDokumente(observableList);
        documentPanel1.setEditable(true);
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
        dapPruefung = new org.jdesktop.swingx.JXDatePicker();
        documentPanel1 = new de.cismet.belis.gui.utils.DocumentPanel();

        setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            jLabel1,
            org.openide.util.NbBundle.getMessage(
                MauerlaschePruefungWizard.class,
                "MauerlaschePruefungWizard.jLabel1.text")); // NOI18N
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
        add(dapPruefung, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(documentPanel1, gridBagConstraints);
    }                                                       // </editor-fold>//GEN-END:initComponents

    @Override
    public ArbeitsprotokollCustomBean.ChildType getEntityClass() {
        return ArbeitsprotokollCustomBean.ChildType.MAUERLASCHE;
    }

    @Override
    public String getTitle() {
        return "Prüfung";
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
        dapPruefung.setDate(null);
        documentPanel1.getDokumente().clear();
    }

    @Override
    protected Object executeAktion(final ArbeitsprotokollCustomBean protokoll) throws Exception {
        final Collection<ServerActionParameter> serverActionParameters = new ArrayList<ServerActionParameter>();

        serverActionParameters.add(new ServerActionParameter(
                AbstractProtokollServerAction.ParameterType.PROTOKOLL_ID.toString(),
                (protokoll != null) ? Integer.toString(protokoll.getId()) : null));
        serverActionParameters.add(new ServerActionParameter(
                ProtokollMauerlaschePruefungServerAction.ParameterType.PRUEFDATUM.toString(),
                (dapPruefung.getDate() != null) ? Long.toString(dapPruefung.getDate().getTime()) : null));

        for (final DmsUrlCustomBean dokument : documentPanel1.getDokumente()) {
            final String url = dokument.toUri().toString();
            final String beschreibung = dokument.getBeschreibung();
            final String urlMitBeschreibung = url + "\n" + beschreibung;
            serverActionParameters.add(new ServerActionParameter(
                    ProtokollMauerlaschePruefungServerAction.ParameterType.DOKUMENT.toString(),
                    urlMitBeschreibung));
        }

        return CidsBroker.getInstance()
                    .executeServerAction(new ProtokollMauerlaschePruefungServerAction().getTaskName(),
                        null,
                        serverActionParameters.toArray(new ServerActionParameter[0]));
    }
}
