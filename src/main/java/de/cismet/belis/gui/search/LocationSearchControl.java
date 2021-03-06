/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * NewJPanel.java
 *
 * Created on 6. März 2009, 13:27
 */
package de.cismet.belis.gui.search;

import de.cismet.belis.broker.BelisBroker;

import de.cismet.cismap.commons.wfsforms.WFSFormFeature;

import de.cismet.commons.architecture.util.ArchitectureUtils;

//ToDo visualisation of working Progressbar --> cooler if there were something central for such events
/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public class LocationSearchControl extends javax.swing.JPanel implements SearchControl {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(LocationSearchControl.class);

    //~ Instance fields --------------------------------------------------------

    private BelisBroker broker;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JButton cmdOk;
    protected javax.swing.JPanel panEmpty1;
    private javax.swing.JTextField txfKennziffer;
    private javax.swing.JTextField txfLaufendenummer;
    private javax.swing.JTextField txfStrassenschluessel;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form NewJPanel.
     *
     * @param  broker  DOCUMENT ME!
     */
    public LocationSearchControl(final BelisBroker broker) {
        this.broker = broker;
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
        java.awt.GridBagConstraints gridBagConstraints;

        panEmpty1 = new javax.swing.JPanel();
        txfLaufendenummer = new javax.swing.JTextField();
        cmdOk = new javax.swing.JButton();
        txfStrassenschluessel = new javax.swing.JTextField();
        txfKennziffer = new javax.swing.JTextField();

        panEmpty1.setMinimumSize(new java.awt.Dimension(1, 19));
        panEmpty1.setOpaque(false);

        final javax.swing.GroupLayout panEmpty1Layout = new javax.swing.GroupLayout(panEmpty1);
        panEmpty1.setLayout(panEmpty1Layout);
        panEmpty1Layout.setHorizontalGroup(
            panEmpty1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                116,
                Short.MAX_VALUE));
        panEmpty1Layout.setVerticalGroup(
            panEmpty1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                25,
                Short.MAX_VALUE));

        setOpaque(false);
        setLayout(new java.awt.GridBagLayout());

        txfLaufendenummer.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txfLaufendenummer.setMaximumSize(new java.awt.Dimension(2147483647, 18));
        txfLaufendenummer.setMinimumSize(new java.awt.Dimension(30, 18));
        txfLaufendenummer.setPreferredSize(new java.awt.Dimension(45, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 2);
        add(txfLaufendenummer, gridBagConstraints);

        cmdOk.setFont(new java.awt.Font("DejaVu Sans", 1, 13));                           // NOI18N
        cmdOk.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/22/search.png"))); // NOI18N
        cmdOk.setMnemonic('P');
        cmdOk.setText("Objektschlüssel");
        cmdOk.setBorder(null);
        cmdOk.setBorderPainted(false);
        cmdOk.setFocusable(false);
        cmdOk.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cmdOkActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 3);
        add(cmdOk, gridBagConstraints);

        txfStrassenschluessel.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txfStrassenschluessel.setMaximumSize(new java.awt.Dimension(2147483647, 18));
        txfStrassenschluessel.setMinimumSize(new java.awt.Dimension(50, 18));
        txfStrassenschluessel.setPreferredSize(new java.awt.Dimension(65, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 3, 0, 2);
        add(txfStrassenschluessel, gridBagConstraints);

        txfKennziffer.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txfKennziffer.setMaximumSize(new java.awt.Dimension(2147483647, 18));
        txfKennziffer.setMinimumSize(new java.awt.Dimension(25, 18));
        txfKennziffer.setPreferredSize(new java.awt.Dimension(30, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 2);
        add(txfKennziffer, gridBagConstraints);
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cmdOkActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cmdOkActionPerformed
        broker.fireSearchStarted();

        final String strassenschluessel = (txfStrassenschluessel.getText().isEmpty()) ? null
                                                                                      : txfStrassenschluessel.getText();
        Integer kennziffer = null;
        try {
            kennziffer = (txfKennziffer.getText().isEmpty()) ? null : Integer.parseInt(txfKennziffer.getText());
        } catch (final Exception ex) {
            LOG.info("error while parsing kennziffer", ex);
        }
        Integer laufendeNummer = null;
        try {
            laufendeNummer = (txfLaufendenummer.getText().isEmpty()) ? null
                                                                     : Integer.parseInt(txfLaufendenummer.getText());
        } catch (final Exception ex) {
            LOG.info("error while parsing laufende Nummer", ex);
        }
        broker.search(strassenschluessel, kennziffer, laufendeNummer);
    } //GEN-LAST:event_cmdOkActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  component  DOCUMENT ME!
     * @param  value      DOCUMENT ME!
     */
    public void requestRefresh(final String component, final WFSFormFeature value) {
    }

    @Override
    public void searchFinished() {
//        setSearchEnabled(true);
    }

    @Override
    public void searchStarted() {
//        setSearchEnabled(false);
    }

    @Override
    public void setSearchEnabled(final boolean isSearchEnabled) {
        ArchitectureUtils.enableContainerRecursivley(this, isSearchEnabled);
    }
}
