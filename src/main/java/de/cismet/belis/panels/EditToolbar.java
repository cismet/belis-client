/*
 * CreateToolBar.java
 *
 * Created on 22. April 2009, 09:55
 */
package de.cismet.belis.panels;

import de.cismet.belis.broker.BelisBroker;
import de.cismet.belis.util.BelisIcons;
import de.cismet.belisEE.entity.Leuchte;
import de.cismet.belisEE.entity.Standort;
import de.cismet.commons.architecture.interfaces.Editable;
import de.cismet.commons.server.entity.BaseEntity;
import org.apache.log4j.Logger;

/**
 *
 * @author  spuhl
 */
public class EditToolbar extends javax.swing.JPanel {

    //private boolean isEditable = false;
    private BelisBroker broker;
    
    
    /** Creates new form CreateToolBar */
    public EditToolbar(BelisBroker broker) {
        this.broker = broker;
        initComponents();         
    }

    public void setEnabled(boolean isEnabled){

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnCreateMode = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(120, 2147483647));
        setLayout(new java.awt.GridBagLayout());

        btnCreateMode.setText("A");
        btnCreateMode.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnCreateMode.setBorderPainted(false);
        btnCreateMode.setEnabled(false);
        btnCreateMode.setFocusPainted(false);
        btnCreateMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateModeActionPerformed(evt);
            }
        });
        add(btnCreateMode, new java.awt.GridBagConstraints());
    }// </editor-fold>//GEN-END:initComponents

    private void btnCreateModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateModeActionPerformed
    //broker.removeEntity(currentEntity);
}//GEN-LAST:event_btnCreateModeActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCreateMode;
    // End of variables declaration//GEN-END:variables
}
