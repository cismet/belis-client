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
package de.cismet.belis.gui.widget.windowsearchwidget;

import com.vividsolutions.jts.geom.Geometry;

import de.cismet.belis.server.search.BelisSearchStatement;
import de.cismet.belis.server.search.MauerlascheSearchStatement;

/**
 * DOCUMENT ME!
 *
 * @author   mroncoroni
 * @version  $Revision$, $Date$
 */
//@org.openide.util.lookup.ServiceProvider(service = BelisWindowSearch.class)
public class MauerlascheWindowSearch extends BelisWindowSearch {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(MauerlascheWindowSearch.class);

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel panTest;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form LandParcelWindowSearch.
     */
    public MauerlascheWindowSearch() {
        super(Mode.MAUERLASCHE, "Mauerlaschen");
        initComponents();
        initWithThisSpecificPanel(panTest);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    protected BelisSearchStatement createSearchStatement(final Geometry searchGeom) {
        return new MauerlascheSearchStatement("2013-07-17", searchGeom);
    }
    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        final java.awt.GridBagConstraints gridBagConstraints;

        panTest = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        panTest.setBorder(javax.swing.BorderFactory.createTitledBorder(
                org.openide.util.NbBundle.getMessage(
                    MauerlascheWindowSearch.class,
                    "MauerlascheWindowSearch.panTest.border.title"))); // NOI18N
        panTest.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            jLabel1,
            org.openide.util.NbBundle.getMessage(
                MauerlascheWindowSearch.class,
                "MauerlascheWindowSearch.jLabel1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panTest.add(jLabel1, gridBagConstraints);

        setLayout(null);
    } // </editor-fold>//GEN-END:initComponents
}