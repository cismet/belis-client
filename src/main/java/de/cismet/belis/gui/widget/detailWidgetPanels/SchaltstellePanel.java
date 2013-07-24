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

import de.cismet.cids.custom.beans.belis.BauartCustomBean;
import de.cismet.cids.custom.beans.belis.SchaltstelleCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyStrassenschluesselCustomBean;

/**
 * DOCUMENT ME!
 *
 * @author   Gilles Baatz
 * @version  $Revision$, $Date$
 */
public class SchaltstellePanel extends AbstractDetailWidgetPanel<SchaltstelleCustomBean> {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(SchaltstellePanel.class);
    private static SchaltstellePanel instance = null;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbxSchaltstelleBauart;
    private javax.swing.JComboBox cbxSchaltstelleStrassenschluessel;
    private javax.swing.JComboBox cbxSchaltstelleStrassenschluesselNr;
    private org.jdesktop.swingx.JXDatePicker dapSchaltstelleErstellungsjahr;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblSchaltstelleBauart;
    private javax.swing.JLabel lblSchaltstelleBemerkung;
    private javax.swing.JLabel lblSchaltstelleErstellungsjahr;
    private javax.swing.JLabel lblSchaltstelleHausnummer;
    private javax.swing.JLabel lblSchaltstelleLaufendenummer;
    private javax.swing.JLabel lblSchaltstelleNummer;
    private javax.swing.JLabel lblSchaltstelleStandortbezeichnung;
    private javax.swing.JLabel lblSchaltstelleStrassenschluessel;
    private javax.swing.JScrollPane scpSchaltstelleBemerkung;
    private javax.swing.JSeparator sprSchaltstelle;
    private javax.swing.JTextArea txaSchaltstelleBemerkung;
    private javax.swing.JTextField txfSchaltstelleHausnummer;
    private javax.swing.JTextField txfSchaltstelleLaufendenummer;
    private javax.swing.JTextField txfSchaltstelleNummer;
    private javax.swing.JTextField txfSchaltstelleStandortbezeichnung;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form SchaltstellePanel.
     */
    private SchaltstellePanel() {
        initComponents();
        initPanel();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static SchaltstellePanel getInstance() {
        if (instance == null) {
            synchronized (StandortPanel.class) {
                if (instance == null) {
                    instance = new SchaltstellePanel();
                }
            }
        }
        return instance;
    }

    /**
     * Creates new form EntityDetailWidget.
     */
    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lblSchaltstelleStrassenschluessel = new javax.swing.JLabel();
        lblSchaltstelleLaufendenummer = new javax.swing.JLabel();
        lblSchaltstelleHausnummer = new javax.swing.JLabel();
        lblSchaltstelleNummer = new javax.swing.JLabel();
        lblSchaltstelleErstellungsjahr = new javax.swing.JLabel();
        lblSchaltstelleStandortbezeichnung = new javax.swing.JLabel();
        lblSchaltstelleBauart = new javax.swing.JLabel();
        lblSchaltstelleBemerkung = new javax.swing.JLabel();
        txfSchaltstelleLaufendenummer = new javax.swing.JTextField();
        txfSchaltstelleHausnummer = new javax.swing.JTextField();
        txfSchaltstelleNummer = new javax.swing.JTextField();
        dapSchaltstelleErstellungsjahr = new org.jdesktop.swingx.JXDatePicker();
        txfSchaltstelleStandortbezeichnung = new javax.swing.JTextField();
        cbxSchaltstelleBauart = new javax.swing.JComboBox();
        scpSchaltstelleBemerkung = new javax.swing.JScrollPane();
        txaSchaltstelleBemerkung = new javax.swing.JTextArea();
        cbxSchaltstelleStrassenschluesselNr = new javax.swing.JComboBox();
        cbxSchaltstelleStrassenschluessel = new javax.swing.JComboBox();
        sprSchaltstelle = new javax.swing.JSeparator();

        jLabel3.setFont(new java.awt.Font("DejaVu Sans", 1, 13));                               // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/22/schaltstelle.png"))); // NOI18N
        jLabel3.setText("Schaltstelle");                                                        // NOI18N

        lblSchaltstelleStrassenschluessel.setText("Straßenschlüssel:"); // NOI18N

        lblSchaltstelleLaufendenummer.setText("Laufende Nr.:"); // NOI18N

        lblSchaltstelleHausnummer.setText("Haus Nr.:"); // NOI18N

        lblSchaltstelleNummer.setText("Schaltstellen Nr.:"); // NOI18N

        lblSchaltstelleErstellungsjahr.setText("Erstellungsjahr:"); // NOI18N

        lblSchaltstelleStandortbezeichnung.setText("Standortbezeichnung:"); // NOI18N

        lblSchaltstelleBauart.setText("Bauart:"); // NOI18N

        lblSchaltstelleBemerkung.setText("Bemerkung:"); // NOI18N

        txfSchaltstelleLaufendenummer.setEnabled(false);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.laufendeNummer}"),
                txfSchaltstelleLaufendenummer,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txfSchaltstelleHausnummer.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.hausnummer}"),
                txfSchaltstelleHausnummer,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setValidator(new StringMaxLengthValidator(5));
        bindingGroup.addBinding(binding);

        txfSchaltstelleNummer.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.schaltstellenNummer}"),
                txfSchaltstelleNummer,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        dapSchaltstelleErstellungsjahr.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.erstellungsjahr}"),
                dapSchaltstelleErstellungsjahr,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setValidator(new DateValidator());
        bindingGroup.addBinding(binding);

        txfSchaltstelleStandortbezeichnung.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.zusaetzlicheStandortbezeichnung}"),
                txfSchaltstelleStandortbezeichnung,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setValidator(new StringMaxLengthValidator());
        bindingGroup.addBinding(binding);

        cbxSchaltstelleBauart.setEnabled(false);
        cbxSchaltstelleBauart.setRenderer(new DefaultListCellRenderer() {

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
                    } else if (value instanceof de.cismet.cids.custom.beans.belis.BauartCustomBean) {
                        final de.cismet.cids.custom.beans.belis.BauartCustomBean ba =
                            (de.cismet.cids.custom.beans.belis.BauartCustomBean)value;
                        setText(ba.getBezeichnung());
                    }
                    return this;
                }
            });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.bauart}"),
                cbxSchaltstelleBauart,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        txaSchaltstelleBemerkung.setColumns(20);
        txaSchaltstelleBemerkung.setRows(5);
        txaSchaltstelleBemerkung.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.bemerkung}"),
                txaSchaltstelleBemerkung,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setValidator(new StringMaxLengthValidator());
        bindingGroup.addBinding(binding);

        scpSchaltstelleBemerkung.setViewportView(txaSchaltstelleBemerkung);

        cbxSchaltstelleStrassenschluesselNr.setEnabled(false);
        cbxSchaltstelleStrassenschluesselNr.setRenderer(new DefaultListCellRenderer() {

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
        cbxSchaltstelleStrassenschluesselNr.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbxSchaltstelleStrassenschluesselNrActionPerformed(evt);
                }
            });

        cbxSchaltstelleStrassenschluessel.setEnabled(false);
        cbxSchaltstelleStrassenschluessel.setRenderer(new DefaultListCellRenderer() {

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
                cbxSchaltstelleStrassenschluessel,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setValidator(new NotNullValidator("Straßenschlüssel"));
        bindingGroup.addBinding(binding);

        cbxSchaltstelleStrassenschluessel.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbxSchaltstelleStrassenschluesselActionPerformed(evt);
                }
            });

        final javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                jPanel4Layout.createSequentialGroup().addContainerGap().addGroup(
                    jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                        lblSchaltstelleStandortbezeichnung).addComponent(lblSchaltstelleBauart).addComponent(
                        lblSchaltstelleErstellungsjahr).addComponent(lblSchaltstelleNummer).addComponent(
                        lblSchaltstelleStrassenschluessel).addComponent(lblSchaltstelleLaufendenummer).addComponent(
                        lblSchaltstelleHausnummer).addComponent(lblSchaltstelleBemerkung)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                        txfSchaltstelleStandortbezeichnung,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        262,
                        Short.MAX_VALUE).addComponent(
                        txfSchaltstelleHausnummer,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        262,
                        Short.MAX_VALUE).addComponent(
                        txfSchaltstelleNummer,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        262,
                        Short.MAX_VALUE).addComponent(
                        dapSchaltstelleErstellungsjahr,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        181,
                        Short.MAX_VALUE).addComponent(cbxSchaltstelleBauart, 0, 262, Short.MAX_VALUE).addComponent(
                        scpSchaltstelleBemerkung,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        0,
                        Short.MAX_VALUE).addGroup(
                        jPanel4Layout.createSequentialGroup().addComponent(
                            cbxSchaltstelleStrassenschluesselNr,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            102,
                            javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(
                            javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
                            cbxSchaltstelleStrassenschluessel,
                            0,
                            154,
                            Short.MAX_VALUE)).addComponent(
                        txfSchaltstelleLaufendenummer,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        262,
                        Short.MAX_VALUE)).addContainerGap()));
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                jPanel4Layout.createSequentialGroup().addContainerGap().addGroup(
                    jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblSchaltstelleStrassenschluessel).addComponent(
                        cbxSchaltstelleStrassenschluesselNr,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        19,
                        javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(
                        cbxSchaltstelleStrassenschluessel,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        21,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblSchaltstelleLaufendenummer).addComponent(
                        txfSchaltstelleLaufendenummer,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        22,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblSchaltstelleHausnummer).addComponent(
                        txfSchaltstelleHausnummer,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        22,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblSchaltstelleNummer).addComponent(
                        txfSchaltstelleNummer,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        22,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblSchaltstelleErstellungsjahr).addComponent(
                        dapSchaltstelleErstellungsjahr,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        21,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblSchaltstelleStandortbezeichnung).addComponent(
                        txfSchaltstelleStandortbezeichnung,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        22,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblSchaltstelleBauart).addComponent(
                        cbxSchaltstelleBauart,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        22,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                        lblSchaltstelleBemerkung).addComponent(
                        scpSchaltstelleBemerkung,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap(
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE)));

        final javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                javax.swing.GroupLayout.Alignment.TRAILING,
                layout.createSequentialGroup().addContainerGap().addGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(
                        jPanel4,
                        javax.swing.GroupLayout.Alignment.LEADING,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        Short.MAX_VALUE).addComponent(
                        sprSchaltstelle,
                        javax.swing.GroupLayout.Alignment.LEADING,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        439,
                        Short.MAX_VALUE).addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING))
                            .addContainerGap()));
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                layout.createSequentialGroup().addContainerGap().addComponent(jLabel3).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
                    sprSchaltstelle,
                    javax.swing.GroupLayout.PREFERRED_SIZE,
                    10,
                    javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
                    jPanel4,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE).addContainerGap()));

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    @Override
    final void initPanel() {
        fillComboBoxWithKeyTableValuesAndAddListener(
            cbxSchaltstelleStrassenschluessel,
            TkeyStrassenschluesselCustomBean.TABLE);
        bindingGroup.addBindingListener(new PanelBindingListener());
        cbxSchaltstelleStrassenschluessel.setSelectedItem(null);
        AutoCompleteDecorator.decorate(cbxSchaltstelleStrassenschluessel, new ObjectToKeyStringConverter());

        fillComboBoxWithKeyTableValuesAndAddListener(
            cbxSchaltstelleStrassenschluesselNr,
            TkeyStrassenschluesselCustomBean.TABLE,
            true);
        cbxSchaltstelleStrassenschluesselNr.setSelectedItem(null);
        AutoCompleteDecorator.decorate(cbxSchaltstelleStrassenschluesselNr, new ObjectToPkConverter("pk"));

        fillComboBoxWithKeyTableValuesAndAddListener(cbxSchaltstelleBauart, BauartCustomBean.TABLE);
        cbxSchaltstelleBauart.setSelectedItem(null);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbxSchaltstelleStrassenschluesselNrActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbxSchaltstelleStrassenschluesselNrActionPerformed
        try {
            if (!isTriggerd) {
                isTriggerd = true;
                cbxSchaltstelleStrassenschluessel.setSelectedItem(cbxSchaltstelleStrassenschluesselNr
                            .getSelectedItem());
            }
        } catch (Exception ex) {
            LOG.warn("failuire while updating strassenschluessel ", ex);
        } finally {
            isTriggerd = false;
        }
    }                                                                                                       //GEN-LAST:event_cbxSchaltstelleStrassenschluesselNrActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbxSchaltstelleStrassenschluesselActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbxSchaltstelleStrassenschluesselActionPerformed
        try {
            if (!isTriggerd) {
                isTriggerd = true;
                cbxSchaltstelleStrassenschluesselNr.setSelectedItem(cbxSchaltstelleStrassenschluessel
                            .getSelectedItem());
            }
        } catch (Exception ex) {
            LOG.warn("failuire while updating strassenschluessel ", ex);
        } finally {
            isTriggerd = false;
        }
    }                                                                                                     //GEN-LAST:event_cbxSchaltstelleStrassenschluesselActionPerformed

    /**
     * DOCUMENT ME!
     */
    public void commitEdits() {
        try {
            dapSchaltstelleErstellungsjahr.getEditor().commitEdit();
        } catch (ParseException ex) {
            LOG.warn("Error while commiting edits: " + ex);
        }
    }

    @Override
    public void setElementsNull() {
        if (currentEntity.getStrassenschluessel() == null) {
            cbxSchaltstelleStrassenschluessel.setSelectedItem(null);
            cbxSchaltstelleStrassenschluesselNr.setSelectedItem(null);
        }
    }

    @Override
    public void setPanelEditable(final boolean isEditable) {
        cbxSchaltstelleStrassenschluessel.setEnabled(isEditable);
        cbxSchaltstelleStrassenschluesselNr.setEnabled(isEditable);
        cbxSchaltstelleBauart.setEnabled(isEditable);
        txfSchaltstelleHausnummer.setEnabled(isEditable);
        txfSchaltstelleLaufendenummer.setEnabled(isEditable);
        txfSchaltstelleNummer.setEnabled(isEditable);
        txfSchaltstelleStandortbezeichnung.setEnabled(isEditable);
        dapSchaltstelleErstellungsjahr.setEnabled(isEditable);
        txaSchaltstelleBemerkung.setEnabled(isEditable);
    }

    @Override
    BindingGroup getBindingGroup() {
        return bindingGroup;
    }

    @Override
    void initComponentToLabelMap() {
        componentToLabelMap.put(cbxSchaltstelleBauart, lblSchaltstelleBauart);
        componentToLabelMap.put(cbxSchaltstelleStrassenschluessel, lblSchaltstelleStrassenschluessel);
        componentToLabelMap.put(cbxSchaltstelleStrassenschluesselNr, lblSchaltstelleStrassenschluessel);
        componentToLabelMap.put(dapSchaltstelleErstellungsjahr, lblSchaltstelleErstellungsjahr);
        componentToLabelMap.put(txaSchaltstelleBemerkung, lblSchaltstelleBemerkung);
        componentToLabelMap.put(txfSchaltstelleHausnummer, lblSchaltstelleHausnummer);
        componentToLabelMap.put(txfSchaltstelleLaufendenummer, lblSchaltstelleLaufendenummer);
        componentToLabelMap.put(txfSchaltstelleNummer, lblSchaltstelleNummer);
        componentToLabelMap.put(txfSchaltstelleStandortbezeichnung, lblSchaltstelleStandortbezeichnung);
    }
}
