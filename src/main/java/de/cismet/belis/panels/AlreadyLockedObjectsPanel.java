/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * AlreadyLockedObjectsPanel.java
 *
 * Created on 31. März 2009, 12:40
 */
package de.cismet.belis.panels;

import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Converter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.JDialog;
import javax.swing.table.DefaultTableModel;

import de.cismet.cids.custom.beans.belis2.SperreCustomBean;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public class AlreadyLockedObjectsPanel extends javax.swing.JPanel {

    //~ Static fields/initializers ---------------------------------------------

    public static final String PROP_LOCKED_OBJECTS = "lockedObjects";

    //~ Instance fields --------------------------------------------------------

    protected PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    BindingGroup bindingGroup2 = new BindingGroup();

    private ArrayList<SperreCustomBean> lockedObjects;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXTable jXTable1;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form AlreadyLockedObjectsPanel.
     *
     * @param  lockedObjects  DOCUMENT ME!
     */
    public AlreadyLockedObjectsPanel(final ArrayList<SperreCustomBean> lockedObjects) {
        this.lockedObjects = lockedObjects;
        initComponents();
        final org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${lockedObjects}"),
                jXTable1,
                org.jdesktop.beansbinding.ELProperty.create("${model}"));
        binding.setConverter(new LockConverter());
        bindingGroup2.addBinding(binding);
        bindingGroup2.bind();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * Add PropertyChangeListener.
     *
     * @param  listener  DOCUMENT ME!
     */
    @Override
    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove PropertyChangeListener.
     *
     * @param  listener  DOCUMENT ME!
     */
    @Override
    public void removePropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jXTable1 = new org.jdesktop.swingx.JXTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jButton1ActionPerformed(evt);
                }
            });

        jScrollPane1.setViewportView(jXTable1);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/48/error.png"))); // NOI18N

        jLabel2.setText(
            "<html><table width=\"250\" border=\"0\"><tr><td>Es gibt bereis gesperrte Objekte. Bitte  veringern Sie Ihre Auswahl oder kontaktieren  Sie den entsprechenden Sachbearbeiter.</td></tr></table></html>");

        final javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                layout.createSequentialGroup().addContainerGap().addGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                        javax.swing.GroupLayout.Alignment.TRAILING,
                        layout.createSequentialGroup().addComponent(
                            jLabel1,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            113,
                            Short.MAX_VALUE).addGap(18, 18, 18).addComponent(
                            jLabel2,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            278,
                            javax.swing.GroupLayout.PREFERRED_SIZE)).addComponent(
                        jButton1,
                        javax.swing.GroupLayout.Alignment.TRAILING,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        72,
                        javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(
                        jScrollPane1,
                        javax.swing.GroupLayout.Alignment.TRAILING,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        409,
                        Short.MAX_VALUE)).addContainerGap()));
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                javax.swing.GroupLayout.Alignment.TRAILING,
                layout.createSequentialGroup().addGap(23, 23, 23).addGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel2)
                                .addComponent(
                                    jLabel1,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    68,
                                    javax.swing.GroupLayout.PREFERRED_SIZE)).addGap(23, 23, 23).addComponent(
                    jScrollPane1,
                    javax.swing.GroupLayout.PREFERRED_SIZE,
                    207,
                    javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jButton1).addContainerGap()));
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jButton1ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButton1ActionPerformed
        ((JDialog)this.getRootPane().getParent()).dispose();
    }                                                                            //GEN-LAST:event_jButton1ActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public ArrayList<SperreCustomBean> getLockedObjects() {
        return lockedObjects;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  lockedObjects  DOCUMENT ME!
     */
    public void setLockedObjects(final ArrayList<SperreCustomBean> lockedObjects) {
        this.lockedObjects = lockedObjects;
        propertyChangeSupport.firePropertyChange(PROP_LOCKED_OBJECTS, null, lockedObjects);
    }
}
/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
class LockConverter extends Converter<ArrayList<SperreCustomBean>, DefaultTableModel> {

    //~ Instance fields --------------------------------------------------------

    private ArrayList<SperreCustomBean> lockedObjects;

    //~ Methods ----------------------------------------------------------------

    @Override
    public DefaultTableModel convertForward(final ArrayList<SperreCustomBean> lockedObjects) {
        this.lockedObjects = lockedObjects;
        Object[][] rows = null;
        if ((lockedObjects != null) && (lockedObjects.size() != 0)) {
            rows = new Object[lockedObjects.size()][2];
        } else {
            rows = new Object[0][2];
        }
        int row = 0;
        for (final SperreCustomBean curLock : lockedObjects) {
            rows[row][0] = curLock.getUserString();
            rows[row][1] = curLock.getTimestamp();
            row++;
        }
        return new javax.swing.table.DefaultTableModel(
                rows,
                new String[] { "Benutzer", "Datum" }) {

                Class[] types = new Class[] { java.lang.String.class, Date.class };

                @Override
                public Class getColumnClass(final int columnIndex) {
                    return types[columnIndex];
                }

                @Override
                public boolean isCellEditable(final int row, final int column) {
                    return false;
                }
            };
    }

    @Override
    public ArrayList<SperreCustomBean> convertReverse(final DefaultTableModel arg0) {
        return lockedObjects;
    }
}
