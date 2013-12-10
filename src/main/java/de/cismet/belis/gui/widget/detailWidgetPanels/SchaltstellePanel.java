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

import de.cismet.cids.custom.beans.belis2.BauartCustomBean;
import de.cismet.cids.custom.beans.belis2.RundsteuerempfaengerCustomBean;
import de.cismet.cids.custom.beans.belis2.SchaltstelleCustomBean;
import de.cismet.cids.custom.beans.belis2.TkeyStrassenschluesselCustomBean;

/**
 * DOCUMENT ME!
 *
 * @author   Gilles Baatz
 * @version  $Revision$, $Date$
 */
public class SchaltstellePanel extends AbstractDetailWidgetPanel<SchaltstelleCustomBean> {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(SchaltstellePanel.class);

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbxRundsteuerempfaenger;
    private javax.swing.JComboBox cbxSchaltstelleBauart;
    private javax.swing.JComboBox cbxSchaltstelleStrassenschluessel;
    private javax.swing.JComboBox cbxSchaltstelleStrassenschluesselNr;
    private org.jdesktop.swingx.JXDatePicker dapEinbaudatum;
    private org.jdesktop.swingx.JXDatePicker dapPruefdatum;
    private org.jdesktop.swingx.JXDatePicker dapSchaltstelleErstellungsjahr;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblEinbaudatum;
    private javax.swing.JLabel lblPruefdatum;
    private javax.swing.JLabel lblRundsteuerempfaenger;
    private javax.swing.JLabel lblSchaltstelleBauart;
    private javax.swing.JLabel lblSchaltstelleBemerkung;
    private javax.swing.JLabel lblSchaltstelleErstellungsjahr;
    private javax.swing.JLabel lblSchaltstelleHausnummer;
    private javax.swing.JLabel lblSchaltstelleLaufendenummer;
    private javax.swing.JLabel lblSchaltstelleNummer;
    private javax.swing.JLabel lblSchaltstelleStandortbezeichnung;
    private javax.swing.JLabel lblSchaltstelleStrassenschluessel;
    private javax.swing.JScrollPane scpSchaltstelleBemerkung;
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
    public SchaltstellePanel() {
        super("SCHALTSTELLE_PANEL");
        initComponents();
        initPanel();
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public JLabel getTabLabel() {
        return jLabel3;
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
        java.awt.GridBagConstraints gridBagConstraints;
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lblSchaltstelleStrassenschluessel = new javax.swing.JLabel();
        cbxSchaltstelleStrassenschluesselNr = new javax.swing.JComboBox();
        cbxSchaltstelleStrassenschluessel = new javax.swing.JComboBox();
        lblSchaltstelleLaufendenummer = new javax.swing.JLabel();
        txfSchaltstelleLaufendenummer = new javax.swing.JTextField();
        lblSchaltstelleHausnummer = new javax.swing.JLabel();
        txfSchaltstelleHausnummer = new javax.swing.JTextField();
        lblSchaltstelleNummer = new javax.swing.JLabel();
        txfSchaltstelleNummer = new javax.swing.JTextField();
        lblSchaltstelleErstellungsjahr = new javax.swing.JLabel();
        dapSchaltstelleErstellungsjahr = new org.jdesktop.swingx.JXDatePicker();
        lblSchaltstelleStandortbezeichnung = new javax.swing.JLabel();
        txfSchaltstelleStandortbezeichnung = new javax.swing.JTextField();
        lblSchaltstelleBauart = new javax.swing.JLabel();
        cbxSchaltstelleBauart = new javax.swing.JComboBox();
        lblPruefdatum = new javax.swing.JLabel();
        dapPruefdatum = new org.jdesktop.swingx.JXDatePicker();
        lblRundsteuerempfaenger = new javax.swing.JLabel();
        cbxRundsteuerempfaenger = new javax.swing.JComboBox();
        lblEinbaudatum = new javax.swing.JLabel();
        dapEinbaudatum = new org.jdesktop.swingx.JXDatePicker();
        lblSchaltstelleBemerkung = new javax.swing.JLabel();
        scpSchaltstelleBemerkung = new javax.swing.JScrollPane();
        txaSchaltstelleBemerkung = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();

        jLabel3.setFont(new java.awt.Font("DejaVu Sans", 1, 13));                               // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/16/schaltstelle.png"))); // NOI18N
        jLabel3.setText("Schaltstelle");                                                        // NOI18N

        setLayout(new java.awt.GridBagLayout());

        jPanel4.setLayout(new java.awt.GridBagLayout());

        lblSchaltstelleStrassenschluessel.setText("Straßenschlüssel:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblSchaltstelleStrassenschluessel, gridBagConstraints);

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
                    } else if (value instanceof de.cismet.cids.custom.beans.belis2.TkeyStrassenschluesselCustomBean) {
                        final de.cismet.cids.custom.beans.belis2.TkeyStrassenschluesselCustomBean ss =
                            (de.cismet.cids.custom.beans.belis2.TkeyStrassenschluesselCustomBean)value;
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
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(cbxSchaltstelleStrassenschluesselNr, gridBagConstraints);

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
                    } else if (value instanceof de.cismet.cids.custom.beans.belis2.TkeyStrassenschluesselCustomBean) {
                        final de.cismet.cids.custom.beans.belis2.TkeyStrassenschluesselCustomBean ss =
                            (de.cismet.cids.custom.beans.belis2.TkeyStrassenschluesselCustomBean)value;
                        setText(ss.getKeyString());
                    }
                    return this;
                }
            });

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
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
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(cbxSchaltstelleStrassenschluessel, gridBagConstraints);

        lblSchaltstelleLaufendenummer.setText("Laufende Nr.:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblSchaltstelleLaufendenummer, gridBagConstraints);

        txfSchaltstelleLaufendenummer.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.laufendeNummer}"),
                txfSchaltstelleLaufendenummer,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txfSchaltstelleLaufendenummer, gridBagConstraints);

        lblSchaltstelleHausnummer.setText("Haus Nr.:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblSchaltstelleHausnummer, gridBagConstraints);

        txfSchaltstelleHausnummer.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.hausnummer}"),
                txfSchaltstelleHausnummer,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setValidator(new StringMaxLengthValidator(5));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txfSchaltstelleHausnummer, gridBagConstraints);

        lblSchaltstelleNummer.setText("Schaltstellen Nr.:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblSchaltstelleNummer, gridBagConstraints);

        txfSchaltstelleNummer.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.schaltstellenNummer}"),
                txfSchaltstelleNummer,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txfSchaltstelleNummer, gridBagConstraints);

        lblSchaltstelleErstellungsjahr.setText("Erstellungsjahr:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblSchaltstelleErstellungsjahr, gridBagConstraints);

        dapSchaltstelleErstellungsjahr.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.erstellungsjahr}"),
                dapSchaltstelleErstellungsjahr,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setValidator(new DateValidator());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(dapSchaltstelleErstellungsjahr, gridBagConstraints);

        lblSchaltstelleStandortbezeichnung.setText("Standortbezeichnung:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblSchaltstelleStandortbezeichnung, gridBagConstraints);

        txfSchaltstelleStandortbezeichnung.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.zusaetzlicheStandortbezeichnung}"),
                txfSchaltstelleStandortbezeichnung,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setValidator(new StringMaxLengthValidator());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txfSchaltstelleStandortbezeichnung, gridBagConstraints);

        lblSchaltstelleBauart.setText("Bauart:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblSchaltstelleBauart, gridBagConstraints);

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
                    } else if (value instanceof de.cismet.cids.custom.beans.belis2.BauartCustomBean) {
                        final de.cismet.cids.custom.beans.belis2.BauartCustomBean ba =
                            (de.cismet.cids.custom.beans.belis2.BauartCustomBean)value;
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
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"),
                "");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(cbxSchaltstelleBauart, gridBagConstraints);

        lblPruefdatum.setText("Prüfung:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblPruefdatum, gridBagConstraints);

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
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(dapPruefdatum, gridBagConstraints);

        lblRundsteuerempfaenger.setText("Rundsteuerempfänger:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblRundsteuerempfaenger, gridBagConstraints);

        cbxRundsteuerempfaenger.setEnabled(false);
        cbxRundsteuerempfaenger.setRenderer(new DefaultListCellRenderer() {

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
                    } else if (value instanceof de.cismet.cids.custom.beans.belis2.BauartCustomBean) {
                        final de.cismet.cids.custom.beans.belis2.BauartCustomBean ba =
                            (de.cismet.cids.custom.beans.belis2.BauartCustomBean)value;
                        setText(ba.getBezeichnung());
                    }
                    return this;
                }
            });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.rundsteuerempfaenger}"),
                cbxRundsteuerempfaenger,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(cbxRundsteuerempfaenger, gridBagConstraints);

        lblEinbaudatum.setText("Einbaudatum:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblEinbaudatum, gridBagConstraints);

        dapEinbaudatum.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.einbaudatum_rs}"),
                dapEinbaudatum,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(dapEinbaudatum, gridBagConstraints);

        lblSchaltstelleBemerkung.setText("Bemerkung:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblSchaltstelleBemerkung, gridBagConstraints);

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

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(scpSchaltstelleBemerkung, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        add(jPanel4, gridBagConstraints);

        final javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                475,
                Short.MAX_VALUE));
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                137,
                Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        add(jPanel2, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    @Override
    final void initPanel() {
        bindingGroup.addBindingListener(new PanelBindingListener());

        fillComboBoxWithKeyTableValuesAndAddListener(
            cbxSchaltstelleStrassenschluessel,
            TkeyStrassenschluesselCustomBean.TABLE);
        fillComboBoxWithKeyTableValuesAndAddListener(
            cbxSchaltstelleStrassenschluesselNr,
            TkeyStrassenschluesselCustomBean.TABLE,
            true);
        fillComboBoxWithKeyTableValuesAndAddListener(cbxSchaltstelleBauart, BauartCustomBean.TABLE);
        fillComboBoxWithKeyTableValuesAndAddListener(cbxRundsteuerempfaenger, RundsteuerempfaengerCustomBean.TABLE);

        cbxSchaltstelleStrassenschluessel.setSelectedItem(null);
        cbxSchaltstelleStrassenschluesselNr.setSelectedItem(null);
        cbxSchaltstelleBauart.setSelectedItem(null);
        cbxRundsteuerempfaenger.setSelectedItem(null);

        AutoCompleteDecorator.decorate(cbxSchaltstelleStrassenschluessel, new ObjectToKeyStringConverter());
        AutoCompleteDecorator.decorate(cbxSchaltstelleStrassenschluesselNr, new ObjectToPkConverter("pk"));
        AutoCompleteDecorator.decorate(cbxRundsteuerempfaenger, new ObjectToPkConverter("rs_typ"));
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
    @Override
    public void commitEdits() {
        try {
            dapSchaltstelleErstellungsjahr.getEditor().commitEdit();
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
        cbxRundsteuerempfaenger.setEnabled(isEditable);
        txfSchaltstelleHausnummer.setEnabled(isEditable);
        txfSchaltstelleLaufendenummer.setEnabled(isEditable);
        txfSchaltstelleNummer.setEnabled(isEditable);
        txfSchaltstelleStandortbezeichnung.setEnabled(isEditable);
        dapSchaltstelleErstellungsjahr.setEnabled(isEditable);
        txaSchaltstelleBemerkung.setEnabled(isEditable);
        dapPruefdatum.setEnabled(isEditable);
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
        componentToLabelMap.put(dapPruefdatum, lblPruefdatum);
        componentToLabelMap.put(cbxRundsteuerempfaenger, lblRundsteuerempfaenger);
    }

    @Override
    protected BindingGroup getBindingGroup() {
        return bindingGroup;
    }
}
