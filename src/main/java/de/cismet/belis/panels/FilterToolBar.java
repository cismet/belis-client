/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * CreateToolBar.java
 *
 * Created on 22. April 2009, 09:55
 */
package de.cismet.belis.panels;

import org.apache.log4j.Logger;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import de.cismet.belis.broker.BelisBroker;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public class FilterToolBar extends javax.swing.JPanel {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger LOG = org.apache.log4j.Logger.getLogger(FilterToolBar.class);

    //~ Instance fields --------------------------------------------------------

    private BelisBroker broker;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton tglFilterArbeitsauftrag;
    private javax.swing.JToggleButton tglFilterNormal;
    private javax.swing.JToggleButton tglFilterVeranlassung;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form CreateToolBar.
     *
     * @param  broker  DOCUMENT ME!
     */
    public FilterToolBar(final BelisBroker broker) {
        this.broker = broker;
        initComponents();
        broker.addPropertyChangeListener(new PropertyChangeListener() {

                @Override
                public void propertyChange(final PropertyChangeEvent evt) {
                    if ((evt.getSource() != null) && evt.getSource().equals(broker)
                                && (evt.getPropertyName() != null)) {
                        if (evt.getPropertyName().equals(BelisBroker.PROP_FILTER_NORMAL)) {
                            tglFilterNormal.setSelected(broker.isFilterNormal());
                        } else if (evt.getPropertyName().equals(BelisBroker.PROP_FILTER_VERANLASSUNG)) {
                            tglFilterVeranlassung.setSelected(broker.isFilterVeranlassung());
                        } else if (evt.getPropertyName().equals(BelisBroker.PROP_FILTER_ARBEITSAUFTRAG)) {
                            tglFilterArbeitsauftrag.setSelected(broker.isFilterArbeitsauftrag());
                        }
                    }
                }
            });

        tglFilterNormal.setSelected(broker.isFilterNormal());
        tglFilterVeranlassung.setSelected(broker.isFilterVeranlassung());
        tglFilterArbeitsauftrag.setSelected(broker.isFilterArbeitsauftrag());
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

        tglFilterNormal = new javax.swing.JToggleButton();
        tglFilterVeranlassung = new javax.swing.JToggleButton();
        tglFilterArbeitsauftrag = new javax.swing.JToggleButton();

        setFocusable(false);
        setOpaque(false);
        setLayout(new java.awt.GridBagLayout());

        tglFilterNormal.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/22/filterNormalUnselected.png"))); // NOI18N
        tglFilterNormal.setToolTipText("Nach normalen Objekten Filtern");
        tglFilterNormal.setBorder(null);
        tglFilterNormal.setBorderPainted(false);
        tglFilterNormal.setContentAreaFilled(false);
        tglFilterNormal.setFocusPainted(false);
        tglFilterNormal.setFocusable(false);
        tglFilterNormal.setMaximumSize(new java.awt.Dimension(23, 23));
        tglFilterNormal.setMinimumSize(new java.awt.Dimension(23, 23));
        tglFilterNormal.setPreferredSize(new java.awt.Dimension(23, 23));
        tglFilterNormal.setRequestFocusEnabled(false);
        tglFilterNormal.setRolloverEnabled(false);
        tglFilterNormal.setSelectedIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/22/filterNormal.png")));           // NOI18N
        tglFilterNormal.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    tglFilterNormalActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 1);
        add(tglFilterNormal, gridBagConstraints);

        tglFilterVeranlassung.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/22/filterVeranlassungUnselected.png"))); // NOI18N
        tglFilterVeranlassung.setToolTipText("Nach Veranlassungen Filtern");
        tglFilterVeranlassung.setBorder(null);
        tglFilterVeranlassung.setBorderPainted(false);
        tglFilterVeranlassung.setContentAreaFilled(false);
        tglFilterVeranlassung.setFocusPainted(false);
        tglFilterVeranlassung.setFocusable(false);
        tglFilterVeranlassung.setMaximumSize(new java.awt.Dimension(23, 23));
        tglFilterVeranlassung.setMinimumSize(new java.awt.Dimension(23, 23));
        tglFilterVeranlassung.setPreferredSize(new java.awt.Dimension(23, 23));
        tglFilterVeranlassung.setRequestFocusEnabled(false);
        tglFilterVeranlassung.setRolloverEnabled(false);
        tglFilterVeranlassung.setSelectedIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/22/filterVeranlassung.png")));           // NOI18N
        tglFilterVeranlassung.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    tglFilterVeranlassungActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 1, 0, 1);
        add(tglFilterVeranlassung, gridBagConstraints);

        tglFilterArbeitsauftrag.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/22/filterArbeitsauftragUnselected.png"))); // NOI18N
        tglFilterArbeitsauftrag.setToolTipText("Nach Arbeitsaufträgen Filtern");
        tglFilterArbeitsauftrag.setBorder(null);
        tglFilterArbeitsauftrag.setBorderPainted(false);
        tglFilterArbeitsauftrag.setContentAreaFilled(false);
        tglFilterArbeitsauftrag.setFocusPainted(false);
        tglFilterArbeitsauftrag.setFocusable(false);
        tglFilterArbeitsauftrag.setMaximumSize(new java.awt.Dimension(23, 23));
        tglFilterArbeitsauftrag.setMinimumSize(new java.awt.Dimension(23, 23));
        tglFilterArbeitsauftrag.setPreferredSize(new java.awt.Dimension(23, 23));
        tglFilterArbeitsauftrag.setRequestFocusEnabled(false);
        tglFilterArbeitsauftrag.setRolloverEnabled(false);
        tglFilterArbeitsauftrag.setSelectedIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/22/filterArbeitsauftrag.png")));           // NOI18N
        tglFilterArbeitsauftrag.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    tglFilterArbeitsauftragActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 1, 0, 2);
        add(tglFilterArbeitsauftrag, gridBagConstraints);
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void tglFilterNormalActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_tglFilterNormalActionPerformed
        broker.setFilterNormal(tglFilterNormal.isSelected());
    }                                                                                   //GEN-LAST:event_tglFilterNormalActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void tglFilterVeranlassungActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_tglFilterVeranlassungActionPerformed
        broker.setFilterVeranlassung(tglFilterVeranlassung.isSelected());
    }                                                                                         //GEN-LAST:event_tglFilterVeranlassungActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void tglFilterArbeitsauftragActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_tglFilterArbeitsauftragActionPerformed
        broker.setFilterArbeitsauftrag(tglFilterArbeitsauftrag.isSelected());
    }                                                                                           //GEN-LAST:event_tglFilterArbeitsauftragActionPerformed
}
