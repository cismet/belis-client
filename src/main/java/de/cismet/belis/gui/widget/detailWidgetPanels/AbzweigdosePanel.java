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

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * DOCUMENT ME!
 *
 * @author   Gilles Baatz
 * @version  $Revision$, $Date$
 */
public final class AbzweigdosePanel extends AbstractDetailWidgetPanel<Object> {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(AbzweigdosePanel.class);

    private static AbzweigdosePanel instance = null;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblAbzweigdose;
    private javax.swing.JPanel panContent1;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form AbzweigdosePanel.
     */
    private AbzweigdosePanel() {
        super("ABZWEIGDOSE_PANEL");
        initComponents();
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public JLabel getTabLabel() {
        return lblAbzweigdose;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static AbzweigdosePanel getInstance() {
        if (instance == null) {
            synchronized (AbzweigdosePanel.class) {
                if (instance == null) {
                    instance = new AbzweigdosePanel();
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

        lblAbzweigdose = new javax.swing.JLabel();
        panContent1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();

        lblAbzweigdose.setFont(new java.awt.Font("DejaVu Sans", 1, 13));                       // NOI18N
        lblAbzweigdose.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/22/abzweigdose.png"))); // NOI18N
        lblAbzweigdose.setText("Abzweigdose/Zugkasten");                                       // NOI18N

        setLayout(new java.awt.GridBagLayout());

        final javax.swing.GroupLayout panContent1Layout = new javax.swing.GroupLayout(panContent1);
        panContent1.setLayout(panContent1Layout);
        panContent1Layout.setHorizontalGroup(
            panContent1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                411,
                Short.MAX_VALUE));
        panContent1Layout.setVerticalGroup(
            panContent1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                232,
                Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(panContent1, gridBagConstraints);

        final javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                431,
                Short.MAX_VALUE));
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                80,
                Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        add(jPanel2, gridBagConstraints);
    } // </editor-fold>//GEN-END:initComponents

    @Override
    BindingGroup getBindingGroup() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void initPanel() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    void initComponentToLabelMap() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setElementsNull() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setPanelEditable(final boolean isEditable) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
