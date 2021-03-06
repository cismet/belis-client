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

import de.cismet.belis.util.BelisIcons;

import de.cismet.commons.architecture.util.ArchitectureUtils;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public class MapSearchControl extends javax.swing.JPanel implements SearchControl {

    //~ Instance fields --------------------------------------------------------

    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JButton cmdOk;
    // End of variables declaration//GEN-END:variables

    private final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(MapSearchControl.class);
    private BelisBroker broker;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form NewJPanel.
     *
     * @param  broker  DOCUMENT ME!
     */
    public MapSearchControl(final BelisBroker broker) {
        this.broker = broker;
        initComponents();
        cmdOk.setIcon(BelisIcons.icoSearch22);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        final java.awt.GridBagConstraints gridBagConstraints;

        cmdOk = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 3, 0, 0));
        setOpaque(false);
        setLayout(new java.awt.GridBagLayout());

        cmdOk.setFont(new java.awt.Font("DejaVu Sans", 1, 13));                           // NOI18N
        cmdOk.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/22/search.png"))); // NOI18N
        cmdOk.setMnemonic('P');
        cmdOk.setText("Karteninhalt");
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
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(2, 3, 2, 3);
        add(cmdOk, gridBagConstraints);
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     */
    public void startMapSearch() {
        cmdOk.doClick();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cmdOkActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cmdOkActionPerformed
        if (log.isDebugEnabled()) {
            log.debug("Search with boundingBox");
        }
        broker.fireSearchStarted();
        broker.search(broker.getMappingComponent().getCurrentBoundingBoxFromCamera());
    }                                                                         //GEN-LAST:event_cmdOkActionPerformed

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
