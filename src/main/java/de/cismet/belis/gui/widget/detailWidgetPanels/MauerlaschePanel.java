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

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import de.cismet.belis.gui.widget.DetailWidget;

import de.cismet.belis.util.RendererTools;

import de.cismet.cids.custom.beans.belis2.MaterialMauerlascheCustomBean;
import de.cismet.cids.custom.beans.belis2.MauerlascheCustomBean;
import de.cismet.cids.custom.beans.belis2.TkeyStrassenschluesselCustomBean;

/**
 * DOCUMENT ME!
 *
 * @author   Gilles Baatz
 * @version  $Revision$, $Date$
 */
public final class MauerlaschePanel extends AbstractDetailWidgetPanel<MauerlascheCustomBean> {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(MauerlaschePanel.class);

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbxMauerlascheMaterial;
    private javax.swing.JComboBox cbxMauerlascheStrassenschluessel;
    private javax.swing.JComboBox cbxMauerlascheStrassenschluesselNr;
    private org.jdesktop.swingx.JXDatePicker dapMauerlascheErstellungsjahr;
    private org.jdesktop.swingx.JXDatePicker dapPruefdatum;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblMauerlascheBemerkung;
    private javax.swing.JLabel lblMauerlascheErstellungsjahr;
    private javax.swing.JLabel lblMauerlascheLaufendenummer;
    private javax.swing.JLabel lblMauerlascheMaterial;
    private javax.swing.JLabel lblMauerlascheStrassenschluessel;
    private javax.swing.JLabel lblPruefdatum;
    private javax.swing.JScrollPane scpSchaltstelleBemerkung;
    private javax.swing.JTextArea txaMauerlascheBemerkung;
    private javax.swing.JTextField txfMauerlascheLaufendenummer;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form MauerlaschePanel.
     */
    public MauerlaschePanel() {
        super("MAUERLASCHE_PANEL");
        initComponents();
        initComponentToLabelMap();
        initPanel();
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public JLabel getTabLabel() {
        return jLabel2;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
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
        lblPruefdatum = new javax.swing.JLabel();
        dapPruefdatum = new org.jdesktop.swingx.JXDatePicker();
        lblMauerlascheBemerkung = new javax.swing.JLabel();
        scpSchaltstelleBemerkung = new javax.swing.JScrollPane();
        txaMauerlascheBemerkung = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();

        jLabel2.setFont(new java.awt.Font("DejaVu Sans", 1, 13));                              // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/16/mauerlasche.png"))); // NOI18N
        jLabel2.setText("Mauerlasche");                                                        // NOI18N

        setLayout(new java.awt.GridBagLayout());

        jPanel3.setLayout(new java.awt.GridBagLayout());

        lblMauerlascheStrassenschluessel.setText("Straßenschlüssel:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblMauerlascheStrassenschluessel, gridBagConstraints);

        lblMauerlascheLaufendenummer.setText("Laufende Nr.:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblMauerlascheLaufendenummer, gridBagConstraints);

        lblMauerlascheErstellungsjahr.setText("Montage:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblMauerlascheErstellungsjahr, gridBagConstraints);

        lblMauerlascheMaterial.setText("Material:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblMauerlascheMaterial, gridBagConstraints);

        txfMauerlascheLaufendenummer.setEnabled(false);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.laufendeNummer}"),
                txfMauerlascheLaufendenummer,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txfMauerlascheLaufendenummer, gridBagConstraints);

        dapMauerlascheErstellungsjahr.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.erstellungsjahr}"),
                dapMauerlascheErstellungsjahr,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setValidator(new DateValidator());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(dapMauerlascheErstellungsjahr, gridBagConstraints);

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
                    } else if (value instanceof de.cismet.cids.custom.beans.belis2.MaterialMauerlascheCustomBean) {
                        final de.cismet.cids.custom.beans.belis2.MaterialMauerlascheCustomBean mm =
                            (de.cismet.cids.custom.beans.belis2.MaterialMauerlascheCustomBean)value;
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
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(cbxMauerlascheMaterial, gridBagConstraints);

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
                    } else if (value instanceof de.cismet.cids.custom.beans.belis2.TkeyStrassenschluesselCustomBean) {
                        final de.cismet.cids.custom.beans.belis2.TkeyStrassenschluesselCustomBean ss =
                            (de.cismet.cids.custom.beans.belis2.TkeyStrassenschluesselCustomBean)value;
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
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(cbxMauerlascheStrassenschluesselNr, gridBagConstraints);

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
                    } else if (value instanceof de.cismet.cids.custom.beans.belis2.TkeyStrassenschluesselCustomBean) {
                        final de.cismet.cids.custom.beans.belis2.TkeyStrassenschluesselCustomBean ss =
                            (de.cismet.cids.custom.beans.belis2.TkeyStrassenschluesselCustomBean)value;
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
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(cbxMauerlascheStrassenschluessel, gridBagConstraints);

        lblPruefdatum.setText("Prüfung:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblPruefdatum, gridBagConstraints);

        dapPruefdatum.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.pruefdatum}"),
                dapPruefdatum,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(dapPruefdatum, gridBagConstraints);

        lblMauerlascheBemerkung.setText("Bemerkung:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblMauerlascheBemerkung, gridBagConstraints);

        txaMauerlascheBemerkung.setColumns(20);
        txaMauerlascheBemerkung.setRows(5);
        txaMauerlascheBemerkung.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.bemerkung}"),
                txaMauerlascheBemerkung,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        scpSchaltstelleBemerkung.setViewportView(txaMauerlascheBemerkung);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(scpSchaltstelleBemerkung, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        add(jPanel3, gridBagConstraints);

        final javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                572,
                Short.MAX_VALUE));
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                86,
                Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        add(jPanel2, gridBagConstraints);

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
    void initPanel() {
        bindingGroup.addBindingListener(new PanelBindingListener());
        fillComboBoxWithKeyTableValuesAndAddListener(
            cbxMauerlascheStrassenschluessel,
            TkeyStrassenschluesselCustomBean.TABLE);
        cbxMauerlascheStrassenschluessel.setSelectedItem(null);

        AutoCompleteDecorator.decorate(cbxMauerlascheStrassenschluessel, new ObjectToKeyStringConverter());
        fillComboBoxWithKeyTableValuesAndAddListener(
            cbxMauerlascheStrassenschluesselNr,
            TkeyStrassenschluesselCustomBean.TABLE,
            true);
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
        componentToLabelMap.put(dapPruefdatum, lblPruefdatum);
        componentToLabelMap.put(txaMauerlascheBemerkung, lblMauerlascheBemerkung);
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
        RendererTools.setEditable(cbxMauerlascheStrassenschluessel, isEditable);
        RendererTools.setEditable(cbxMauerlascheStrassenschluesselNr, isEditable);
        RendererTools.setEditable(cbxMauerlascheMaterial, isEditable);
        RendererTools.setEditable(dapMauerlascheErstellungsjahr, isEditable);
        RendererTools.setEditable(txfMauerlascheLaufendenummer, isEditable);
        RendererTools.setEditable(dapPruefdatum, isEditable);
        RendererTools.setEditable(txaMauerlascheBemerkung, isEditable);
    }

    /**
     * DOCUMENT ME!
     */
    @Override
    public void commitEdits() {
        try {
            dapMauerlascheErstellungsjahr.getEditor().commitEdit();
        } catch (ParseException ex) {
            LOG.warn("Error while commiting edits: " + ex);
        }
        try {
            dapPruefdatum.getEditor().commitEdit();
        } catch (ParseException ex) {
            LOG.warn("Error while commiting edits: " + ex);
        }
    }

    @Override
    protected BindingGroup getBindingGroup() {
        return bindingGroup;
    }
}
