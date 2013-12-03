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
package de.cismet.belis.gui.widget.detailWidgetPanels;

import org.jdesktop.beansbinding.BindingGroup;

import javax.swing.JLabel;

import de.cismet.belis.broker.BelisBroker;

import de.cismet.belis.gui.DateToStringConverter;
import de.cismet.belis.gui.widget.DetailWidget;

import de.cismet.cids.custom.beans.belis.ArbeitsprotokollCustomBean;

/**
 * DOCUMENT ME!
 *
 * @author   Gilles Baatz
 * @version  $Revision$, $Date$
 */
public class ArbeitsprotokollPanel extends AbstractDetailWidgetPanel<ArbeitsprotokollCustomBean> {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ArbeitsprotokollPanel.class);

    //~ Instance fields --------------------------------------------------------

    private BelisBroker belisBroker = BelisBroker.getInstance();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblArbeitsprotokoll;
    private javax.swing.JPanel panContent;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form ArbeitsprotokollPanel.
     *
     * @param  detailWidget  DOCUMENT ME!
     */
    public ArbeitsprotokollPanel(final DetailWidget detailWidget) {
        super("ARBEITSROTOKOLL_PANEL", detailWidget);
        initComponents();
        initComponentToLabelMap();
        initPanel();
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public JLabel getTabLabel() {
        return lblArbeitsprotokoll;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        lblArbeitsprotokoll = new javax.swing.JLabel();
        panContent = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();

        lblArbeitsprotokoll.setFont(new java.awt.Font("DejaVu Sans", 1, 13));                       // NOI18N
        lblArbeitsprotokoll.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/16/arbeitsprotokoll.png"))); // NOI18N
        lblArbeitsprotokoll.setText("Arbeitsprotokoll");                                            // NOI18N

        setLayout(new java.awt.GridBagLayout());

        panContent.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        add(panContent, gridBagConstraints);

        final javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                450,
                Short.MAX_VALUE));
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                660,
                Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        add(jPanel2, gridBagConstraints);
    } // </editor-fold>//GEN-END:initComponents

    @Override
    final void initPanel() {
    }

    /**
     * DOCUMENT ME!
     */
    @Override
    public void commitEdits() {
    }

    @Override
    final void initComponentToLabelMap() {
    }

    @Override
    public void setElementsNull() {
    }

    @Override
    public void setPanelEditable(final boolean isEditable) {
//        txtNummer.setEnabled(isEditable);
    }

    @Override
    protected BindingGroup getBindingGroup() {
        return null;
    }
}
