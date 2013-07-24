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
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import java.awt.Component;

import java.text.ParseException;

import java.util.Collection;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import de.cismet.belis.broker.CidsBroker;

import de.cismet.belisEE.exception.ActionNotSuccessfulException;

import de.cismet.cids.custom.beans.belis.MaterialMauerlascheCustomBean;
import de.cismet.cids.custom.beans.belis.MauerlascheCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyStrassenschluesselCustomBean;

/**
 * DOCUMENT ME!
 *
 * @author   Gilles Baatz
 * @version  $Revision$, $Date$
 */
public final class MauerlaschePanel extends AbstractDetailWidgetPanel<MauerlascheCustomBean> {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(MauerlaschePanel.class);
    private static MauerlaschePanel instance = null;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbxMauerlascheMaterial;
    private javax.swing.JComboBox cbxMauerlascheStrassenschluessel;
    private javax.swing.JComboBox cbxMauerlascheStrassenschluesselNr;
    private org.jdesktop.swingx.JXDatePicker dapMauerlascheErstellungsjahr;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblMauerlascheErstellungsjahr;
    private javax.swing.JLabel lblMauerlascheLaufendenummer;
    private javax.swing.JLabel lblMauerlascheMaterial;
    private javax.swing.JLabel lblMauerlascheStrassenschluessel;
    private javax.swing.JSeparator sprMauerlasche;
    private javax.swing.JTextField txfMauerlascheLaufendenummer;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form MauerlaschePanel.
     */
    private MauerlaschePanel() {
        initComponents();
        initComponentToLabelMap();
        initPanel();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static MauerlaschePanel getInstance() {
        if (instance == null) {
            synchronized (MauerlaschePanel.class) {
                if (instance == null) {
                    instance = new MauerlaschePanel();
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
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblMauerlascheStrassenschluessel = new javax.swing.JLabel();
        lblMauerlascheLaufendenummer = new javax.swing.JLabel();
        lblMauerlascheErstellungsjahr = new javax.swing.JLabel();
        lblMauerlascheMaterial = new javax.swing.JLabel();
        txfMauerlascheLaufendenummer = new javax.swing.JTextField();
        dapMauerlascheErstellungsjahr = new org.jdesktop.swingx.JXDatePicker();
        cbxMauerlascheMaterial = new javax.swing.JComboBox();
        cbxMauerlascheStrassenschluesselNr = new javax.swing.JComboBox();
        cbxMauerlascheStrassenschluessel = new javax.swing.JComboBox();
        sprMauerlasche = new javax.swing.JSeparator();

        jLabel2.setFont(new java.awt.Font("DejaVu Sans", 1, 13));                              // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/22/mauerlasche.png"))); // NOI18N
        jLabel2.setText("Mauerlasche");                                                        // NOI18N

        lblMauerlascheStrassenschluessel.setText("Straßenschlüssel:"); // NOI18N

        lblMauerlascheLaufendenummer.setText("Laufende Nr.:"); // NOI18N

        lblMauerlascheErstellungsjahr.setText("Erstellungsjahr:"); // NOI18N

        lblMauerlascheMaterial.setText("Material:"); // NOI18N

        txfMauerlascheLaufendenummer.setEnabled(false);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.laufendeNummer}"),
                txfMauerlascheLaufendenummer,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        dapMauerlascheErstellungsjahr.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.erstellungsjahr}"),
                dapMauerlascheErstellungsjahr,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setValidator(new DateValidator());
        bindingGroup.addBinding(binding);

        cbxMauerlascheMaterial.setEnabled(false);
        cbxMauerlascheMaterial.setRenderer(new DefaultListCellRenderer() {

                @Override
                public Component getListCellRendererComponent(
                        final JList list,
                        final Object value,
                        final int index,
                        final boolean isSelected,
                        final boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value == null) {
                        setText(comboBoxNullValue);
                    } else if (value instanceof de.cismet.cids.custom.beans.belis.MaterialMauerlascheCustomBean) {
                        final de.cismet.cids.custom.beans.belis.MaterialMauerlascheCustomBean mm =
                            (de.cismet.cids.custom.beans.belis.MaterialMauerlascheCustomBean)value;
                        setText(mm.getBezeichnung());
                    }
                    return this;
                }
            });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.material}"),
                cbxMauerlascheMaterial,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbxMauerlascheMaterial.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbxMauerlascheMaterialActionPerformed(evt);
                }
            });

        cbxMauerlascheStrassenschluesselNr.setEnabled(false);
        cbxMauerlascheStrassenschluesselNr.setRenderer(new DefaultListCellRenderer() {

                @Override
                public Component getListCellRendererComponent(
                        final JList list,
                        final Object value,
                        final int index,
                        final boolean isSelected,
                        final boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value == null) {
                        setText(comboBoxNullValue);
                    } else if (value instanceof de.cismet.cids.custom.beans.belis.TkeyStrassenschluesselCustomBean) {
                        final de.cismet.cids.custom.beans.belis.TkeyStrassenschluesselCustomBean ss =
                            (de.cismet.cids.custom.beans.belis.TkeyStrassenschluesselCustomBean)value;
                        setText(ss.getPk());
                    }
                    return this;
                }
            });
        cbxMauerlascheStrassenschluesselNr.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbxMauerlascheStrassenschluesselNrActionPerformed(evt);
                }
            });

        cbxMauerlascheStrassenschluessel.setEnabled(false);
        cbxMauerlascheStrassenschluessel.setRenderer(new DefaultListCellRenderer() {

                @Override
                public Component getListCellRendererComponent(
                        final JList list,
                        final Object value,
                        final int index,
                        final boolean isSelected,
                        final boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value == null) {
                        setText(comboBoxNullValue);
                    } else if (value instanceof de.cismet.cids.custom.beans.belis.TkeyStrassenschluesselCustomBean) {
                        final de.cismet.cids.custom.beans.belis.TkeyStrassenschluesselCustomBean ss =
                            (de.cismet.cids.custom.beans.belis.TkeyStrassenschluesselCustomBean)value;
                        setText(ss.getKeyString());
                    }
                    return this;
                }
            });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.strassenschluessel}"),
                cbxMauerlascheStrassenschluessel,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setValidator(new NotNullValidator("Straßenschlüssel"));
        bindingGroup.addBinding(binding);

        cbxMauerlascheStrassenschluessel.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbxMauerlascheStrassenschluesselActionPerformed(evt);
                }
            });

        final javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                jPanel3Layout.createSequentialGroup().addContainerGap().addGroup(
                    jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                        lblMauerlascheStrassenschluessel).addComponent(lblMauerlascheLaufendenummer).addComponent(
                        lblMauerlascheErstellungsjahr).addComponent(lblMauerlascheMaterial)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                        dapMauerlascheErstellungsjahr,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        212,
                        Short.MAX_VALUE).addComponent(
                        txfMauerlascheLaufendenummer,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        166,
                        Short.MAX_VALUE).addComponent(cbxMauerlascheMaterial, 0, 166, Short.MAX_VALUE).addGroup(
                        jPanel3Layout.createSequentialGroup().addComponent(
                            cbxMauerlascheStrassenschluesselNr,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            77,
                            javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(
                            javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
                            cbxMauerlascheStrassenschluessel,
                            0,
                            83,
                            Short.MAX_VALUE))).addContainerGap()));
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                jPanel3Layout.createSequentialGroup().addContainerGap().addGroup(
                    jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblMauerlascheStrassenschluessel).addComponent(
                        cbxMauerlascheStrassenschluesselNr,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        19,
                        javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(
                        cbxMauerlascheStrassenschluessel,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        21,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblMauerlascheLaufendenummer).addComponent(
                        txfMauerlascheLaufendenummer,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        22,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblMauerlascheErstellungsjahr).addComponent(
                        dapMauerlascheErstellungsjahr,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        22,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false).addComponent(
                        cbxMauerlascheMaterial,
                        0,
                        0,
                        Short.MAX_VALUE).addComponent(
                        lblMauerlascheMaterial,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        22,
                        Short.MAX_VALUE)).addContainerGap(115, Short.MAX_VALUE)));

        jPanel3Layout.linkSize(
            javax.swing.SwingConstants.VERTICAL,
            new java.awt.Component[] {
                cbxMauerlascheMaterial,
                dapMauerlascheErstellungsjahr,
                txfMauerlascheLaufendenummer
            });

        final javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                javax.swing.GroupLayout.Alignment.TRAILING,
                layout.createSequentialGroup().addContainerGap().addGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(
                        jPanel3,
                        javax.swing.GroupLayout.Alignment.LEADING,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        Short.MAX_VALUE).addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                        sprMauerlasche,
                        javax.swing.GroupLayout.Alignment.LEADING,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        315,
                        Short.MAX_VALUE)).addContainerGap()));
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                layout.createSequentialGroup().addContainerGap().addComponent(jLabel2).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
                    sprMauerlasche,
                    javax.swing.GroupLayout.PREFERRED_SIZE,
                    10,
                    javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
                    jPanel3,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE).addContainerGap()));

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbxMauerlascheMaterialActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbxMauerlascheMaterialActionPerformed
// TODO add your handling code here:
    } //GEN-LAST:event_cbxMauerlascheMaterialActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbxMauerlascheStrassenschluesselNrActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbxMauerlascheStrassenschluesselNrActionPerformed
        try {
            if (!isTriggerd) {
                isTriggerd = true;
                cbxMauerlascheStrassenschluessel.setSelectedItem(cbxMauerlascheStrassenschluesselNr.getSelectedItem());
            }
        } catch (Exception ex) {
            LOG.warn("failuire while updating strassenschluessel ", ex);
        } finally {
            isTriggerd = false;
        }
    }                                                                                                      //GEN-LAST:event_cbxMauerlascheStrassenschluesselNrActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbxMauerlascheStrassenschluesselActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbxMauerlascheStrassenschluesselActionPerformed
        try {
            if (!isTriggerd) {
                isTriggerd = true;
                cbxMauerlascheStrassenschluesselNr.setSelectedItem(cbxMauerlascheStrassenschluessel.getSelectedItem());
            }
        } catch (Exception ex) {
            LOG.warn("failuire while updating strassenschluessel ", ex);
        } finally {
            isTriggerd = false;
        }
    }                                                                                                    //GEN-LAST:event_cbxMauerlascheStrassenschluesselActionPerformed

    @Override
    BindingGroup getBindingGroup() {
        return bindingGroup;
    }

    @Override
    void initPanel() {
        bindingGroup.addBindingListener(new PanelBindingListener());
        fillComboBoxWithKeyTableValuesAndAddListener(
            cbxMauerlascheStrassenschluessel,
            TkeyStrassenschluesselCustomBean.TABLE);
        cbxMauerlascheStrassenschluessel.setSelectedItem(null);

        AutoCompleteDecorator.decorate(cbxMauerlascheStrassenschluessel, new ObjectToKeyStringConverter());
        fillComboBoxWithKeyTableValuesAndAddListener(
            cbxMauerlascheStrassenschluesselNr,
            TkeyStrassenschluesselCustomBean.TABLE);
        cbxMauerlascheStrassenschluesselNr.setSelectedItem(null);
        AutoCompleteDecorator.decorate(cbxMauerlascheStrassenschluesselNr, new ObjectToPkConverter("pk"));

        fillComboBoxWithKeyTableValuesAndAddListener(cbxMauerlascheMaterial, MaterialMauerlascheCustomBean.TABLE);
        cbxMauerlascheMaterial.setSelectedItem(null);
    }

    @Override
    void initComponentToLabelMap() {
        componentToLabelMap.put(cbxMauerlascheMaterial, lblMauerlascheMaterial);
        componentToLabelMap.put(cbxMauerlascheStrassenschluessel, lblMauerlascheStrassenschluessel);
        componentToLabelMap.put(cbxMauerlascheStrassenschluesselNr, lblMauerlascheStrassenschluessel);
        componentToLabelMap.put(dapMauerlascheErstellungsjahr, lblMauerlascheErstellungsjahr);
        componentToLabelMap.put(txfMauerlascheLaufendenummer, lblMauerlascheLaufendenummer);
    }

    @Override
    public void setElementsNull() {
        if (((MauerlascheCustomBean)currentEntity).getStrassenschluessel() == null) {
            cbxMauerlascheStrassenschluessel.setSelectedItem(null);
            cbxMauerlascheStrassenschluesselNr.setSelectedItem(null);
        }
    }

    @Override
    public void setPanelEditable(final boolean isEditable) {
        cbxMauerlascheStrassenschluessel.setEnabled(isEditable);
        cbxMauerlascheStrassenschluesselNr.setEnabled(isEditable);
        cbxMauerlascheMaterial.setEnabled(isEditable);
        dapMauerlascheErstellungsjahr.setEnabled(isEditable);
        txfMauerlascheLaufendenummer.setEnabled(isEditable);
    }

    /**
     * DOCUMENT ME!
     */
    public void commitEdits() {
        try {
            dapMauerlascheErstellungsjahr.getEditor().commitEdit();
        } catch (ParseException ex) {
            LOG.warn("Error while commiting edits: " + ex);
        }
    }
}
