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

import java.util.Collection;

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
public class StandortRevisionWizard extends AbstractArbeitsprotokollWizard {

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form VorschaltgeraetwechselWizard.
     */
    public StandortRevisionWizard() {
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
        final javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 400, Short.MAX_VALUE));
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 300, Short.MAX_VALUE));
    } // </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    @Override
    public Class getEntityClass() {
        return TdtaStandortMastCustomBean.class;
    }

    @Override
    public String getTitle() {
        return "Revision";
    }

    @Override
    public Action getAction() {
        return new AbstractAction("Revision") {

                @Override
                public void actionPerformed(final ActionEvent e) {
                    throw new UnsupportedOperationException("Not supported yet."); // To change body of generated
                                                                                   // methods, choose Tools | Templates.
                }
            };
    }

    @Override
    protected Collection<ArbeitsprotokollaktionCustomBean> executeAktionen() {
        throw new UnsupportedOperationException("Not supported yet.");    // To change body of generated methods, choose
                                                                          // Tools | Templates.
    }
}
