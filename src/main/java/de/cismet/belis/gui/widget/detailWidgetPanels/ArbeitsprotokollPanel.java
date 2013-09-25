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

    private static ArbeitsprotokollPanel instance = null;

    //~ Instance fields --------------------------------------------------------

    private BelisBroker belisBroker = BelisBroker.getInstance();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblArbeitsauftrag;
    private javax.swing.JPanel panContent;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form LeuchtePanel.
     */
    private ArbeitsprotokollPanel() {
        super("ARBEITSAUFTRAG_PANEL");
        initComponents();
        initComponentToLabelMap();
        initPanel();
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public JLabel getTabLabel() {
        return lblArbeitsauftrag;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static ArbeitsprotokollPanel getInstance() {
        if (instance == null) {
            synchronized (ArbeitsprotokollPanel.class) {
                if (instance == null) {
                    instance = new ArbeitsprotokollPanel();
                }
            }
        }
        return instance;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        lblArbeitsauftrag = new javax.swing.JLabel();
        panContent = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();

        lblArbeitsauftrag.setFont(new java.awt.Font("DejaVu Sans", 1, 13));                         // NOI18N
        lblArbeitsauftrag.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/16/arbeitsprotokoll.png"))); // NOI18N
        lblArbeitsauftrag.setText("Arbeitsprotokoll");                                              // NOI18N

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
    BindingGroup getBindingGroup() {
        return null;
    }

    @Override
    final void initPanel() {
    }

    /**
     * DOCUMENT ME!
     */
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
}
