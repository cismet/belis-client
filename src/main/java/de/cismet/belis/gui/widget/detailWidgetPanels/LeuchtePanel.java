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
import org.jdesktop.beansbinding.Validator;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.autocomplete.ObjectToStringConverter;

import java.awt.Component;

import java.text.ParseException;

import java.util.Collection;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.tree.TreePath;

import de.cismet.belis.broker.BelisBroker;
import de.cismet.belis.broker.CidsBroker;

import de.cismet.belis.util.RendererTools;

import de.cismet.belisEE.exception.ActionNotSuccessfulException;

import de.cismet.cids.custom.beans.belis2.LeuchtmittelCustomBean;
import de.cismet.cids.custom.beans.belis2.RundsteuerempfaengerCustomBean;
import de.cismet.cids.custom.beans.belis2.TdtaLeuchtenCustomBean;
import de.cismet.cids.custom.beans.belis2.TdtaStandortMastCustomBean;
import de.cismet.cids.custom.beans.belis2.TkeyBezirkCustomBean;
import de.cismet.cids.custom.beans.belis2.TkeyDoppelkommandoCustomBean;
import de.cismet.cids.custom.beans.belis2.TkeyEnergielieferantCustomBean;
import de.cismet.cids.custom.beans.belis2.TkeyKennzifferCustomBean;
import de.cismet.cids.custom.beans.belis2.TkeyLeuchtentypCustomBean;
import de.cismet.cids.custom.beans.belis2.TkeyStrassenschluesselCustomBean;
import de.cismet.cids.custom.beans.belis2.TkeyUnterhLeuchteCustomBean;

/**
 * DOCUMENT ME!
 *
 * @author   Gilles Baatz
 * @version  $Revision$, $Date$
 */
public class LeuchtePanel extends AbstractDetailWidgetPanel<TdtaLeuchtenCustomBean> {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(LeuchtePanel.class);

    //~ Instance fields --------------------------------------------------------

    private BelisBroker belisBroker = BelisBroker.getInstance();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox cboLeuchteVerrechnungseinheit;
    private javax.swing.JCheckBox cboLeuchteZaehler;
    private javax.swing.JComboBox cbxLeuchteDoppelkommando1;
    private javax.swing.JComboBox cbxLeuchteDoppelkommando2;
    private javax.swing.JComboBox cbxLeuchteEnergielieferant;
    private javax.swing.JComboBox cbxLeuchteKennziffer;
    private javax.swing.JComboBox cbxLeuchteLeuchtentyp;
    private javax.swing.JComboBox cbxLeuchteStadtbezirk;
    private javax.swing.JComboBox cbxLeuchteStrassenschluessel;
    private javax.swing.JComboBox cbxLeuchteStrassenschluesselNr;
    private javax.swing.JComboBox cbxLeuchteUnterhalt;
    private javax.swing.JComboBox cbxLeuchtmittel;
    private javax.swing.JComboBox cbxRundsteuerempfaenger;
    private org.jdesktop.swingx.JXDatePicker dapEinbaudatum;
    private org.jdesktop.swingx.JXDatePicker dapLeuchteInbetriebnahme;
    private org.jdesktop.swingx.JXDatePicker dapLeuchtmittelwechsel;
    private org.jdesktop.swingx.JXDatePicker dapNaechsterWechsel;
    private org.jdesktop.swingx.JXDatePicker dapSonderturnus;
    private org.jdesktop.swingx.JXDatePicker dapWechselVorschaltgeraet;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblAnschlussleistung1DK;
    private javax.swing.JLabel lblAnschlussleistung2DK;
    private javax.swing.JLabel lblEinbaudatum;
    private javax.swing.JLabel lblLebensdauer;
    private javax.swing.JLabel lblLeuchte;
    private javax.swing.JLabel lblLeuchteBemerkung;
    private javax.swing.JLabel lblLeuchteDoppelkommando1;
    private javax.swing.JLabel lblLeuchteDoppelkommando2;
    private javax.swing.JLabel lblLeuchteEnergielieferant;
    private javax.swing.JLabel lblLeuchteInbetriebnahme;
    private javax.swing.JLabel lblLeuchteKenziffer;
    private javax.swing.JLabel lblLeuchteLaufendenummer;
    private javax.swing.JLabel lblLeuchteLeuchtennummer;
    private javax.swing.JLabel lblLeuchteLeuchtentyp;
    private javax.swing.JLabel lblLeuchteMontagefirma;
    private javax.swing.JLabel lblLeuchteRundsteuer;
    private javax.swing.JLabel lblLeuchteSchaltstelle;
    private javax.swing.JLabel lblLeuchteStadtbezirk;
    private javax.swing.JLabel lblLeuchteStandortangabe;
    private javax.swing.JLabel lblLeuchteStrassenschluessel;
    private javax.swing.JLabel lblLeuchteUnterhalt;
    private javax.swing.JLabel lblLeuchteVerrechnungseinheit;
    private javax.swing.JLabel lblLeuchteZaehler;
    private javax.swing.JLabel lblLeuchtmittel;
    private javax.swing.JLabel lblLeuchtmittelwechsel;
    private javax.swing.JLabel lblNaechsterWechsel;
    private javax.swing.JLabel lblSonderturnus;
    private javax.swing.JLabel lblVorschaltgeraet;
    private javax.swing.JLabel lblWechselVorschaltgeraet;
    private javax.swing.JPanel panContent;
    private javax.swing.JScrollPane scpLeuchteBemerkung;
    private javax.swing.JSpinner sprLeuchteDoppelkommando1Anzahl;
    private javax.swing.JSpinner sprLeuchteDoppelkommando2Anzahl;
    private javax.swing.JTextArea txaLeuchteBemerkung;
    private javax.swing.JTextField txfLeuchteLaufendenummer;
    private javax.swing.JTextField txfLeuchteMontagefirma;
    private javax.swing.JTextField txfLeuchteStandortAngabe;
    private javax.swing.JTextField txfVorschaltgeraet;
    private javax.swing.JFormattedTextField txtAnschlussleistung1DK;
    private javax.swing.JFormattedTextField txtAnschlussleistung2DK;
    private javax.swing.JTextField txtLebensdauer;
    private javax.swing.JTextField txtLeuchteLeuchtennummer;
    private javax.swing.JTextField txtLeuchteSchaltstelle;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form LeuchtePanel.
     */
    public LeuchtePanel() {
        super("LEUCHTE_PANEL");
        initComponents();
        initComponentToLabelMap();
        initPanel();
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public JLabel getTabLabel() {
        return lblLeuchte;
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

        lblLeuchte = new javax.swing.JLabel();
        panContent = new javax.swing.JPanel();
        lblLeuchteStrassenschluessel = new javax.swing.JLabel();
        cbxLeuchteStrassenschluesselNr = new javax.swing.JComboBox();
        cbxLeuchteStrassenschluessel = new javax.swing.JComboBox();
        lblLeuchteKenziffer = new javax.swing.JLabel();
        cbxLeuchteKennziffer = new javax.swing.JComboBox();
        lblLeuchteLaufendenummer = new javax.swing.JLabel();
        txfLeuchteLaufendenummer = new javax.swing.JTextField();
        lblLeuchteLeuchtennummer = new javax.swing.JLabel();
        txtLeuchteLeuchtennummer = new javax.swing.JTextField();
        lblLeuchteEnergielieferant = new javax.swing.JLabel();
        cbxLeuchteEnergielieferant = new javax.swing.JComboBox();
        lblLeuchteSchaltstelle = new javax.swing.JLabel();
        txtLeuchteSchaltstelle = new javax.swing.JTextField();
        lblLeuchteRundsteuer = new javax.swing.JLabel();
        cbxRundsteuerempfaenger = new javax.swing.JComboBox();
        lblEinbaudatum = new javax.swing.JLabel();
        dapEinbaudatum = new org.jdesktop.swingx.JXDatePicker();
        lblLeuchteLeuchtentyp = new javax.swing.JLabel();
        cbxLeuchteLeuchtentyp = new javax.swing.JComboBox();
        lblLeuchteUnterhalt = new javax.swing.JLabel();
        cbxLeuchteUnterhalt = new javax.swing.JComboBox();
        lblLeuchteZaehler = new javax.swing.JLabel();
        cboLeuchteZaehler = new javax.swing.JCheckBox();
        lblLeuchteInbetriebnahme = new javax.swing.JLabel();
        dapLeuchteInbetriebnahme = new org.jdesktop.swingx.JXDatePicker();
        lblLeuchteDoppelkommando1 = new javax.swing.JLabel();
        cbxLeuchteDoppelkommando1 = new javax.swing.JComboBox();
        sprLeuchteDoppelkommando1Anzahl = new javax.swing.JSpinner();
        lblLeuchteDoppelkommando2 = new javax.swing.JLabel();
        cbxLeuchteDoppelkommando2 = new javax.swing.JComboBox();
        sprLeuchteDoppelkommando2Anzahl = new javax.swing.JSpinner();
        lblAnschlussleistung1DK = new javax.swing.JLabel();
        txtAnschlussleistung1DK = new javax.swing.JFormattedTextField();
        lblAnschlussleistung2DK = new javax.swing.JLabel();
        txtAnschlussleistung2DK = new javax.swing.JFormattedTextField();
        lblLeuchtmittel = new javax.swing.JLabel();
        cbxLeuchtmittel = new javax.swing.JComboBox();
        lblLebensdauer = new javax.swing.JLabel();
        txtLebensdauer = new javax.swing.JTextField();
        lblLeuchtmittelwechsel = new javax.swing.JLabel();
        dapLeuchtmittelwechsel = new org.jdesktop.swingx.JXDatePicker();
        lblNaechsterWechsel = new javax.swing.JLabel();
        dapNaechsterWechsel = new org.jdesktop.swingx.JXDatePicker();
        lblSonderturnus = new javax.swing.JLabel();
        dapSonderturnus = new org.jdesktop.swingx.JXDatePicker();
        lblWechselVorschaltgeraet = new javax.swing.JLabel();
        dapWechselVorschaltgeraet = new org.jdesktop.swingx.JXDatePicker();
        lblVorschaltgeraet = new javax.swing.JLabel();
        txfVorschaltgeraet = new javax.swing.JTextField();
        lblLeuchteMontagefirma = new javax.swing.JLabel();
        txfLeuchteMontagefirma = new javax.swing.JTextField();
        lblLeuchteBemerkung = new javax.swing.JLabel();
        scpLeuchteBemerkung = new javax.swing.JScrollPane();
        txaLeuchteBemerkung = new javax.swing.JTextArea();
        lblLeuchteStandortangabe = new javax.swing.JLabel();
        txfLeuchteStandortAngabe = new javax.swing.JTextField();
        lblLeuchteStadtbezirk = new javax.swing.JLabel();
        cbxLeuchteStadtbezirk = new javax.swing.JComboBox();
        lblLeuchteVerrechnungseinheit = new javax.swing.JLabel();
        cboLeuchteVerrechnungseinheit = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();

        lblLeuchte.setFont(new java.awt.Font("DejaVu Sans", 1, 13));                       // NOI18N
        lblLeuchte.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/16/leuchte.png"))); // NOI18N
        lblLeuchte.setText("Leuchte");                                                     // NOI18N

        setLayout(new java.awt.GridBagLayout());

        panContent.setLayout(new java.awt.GridBagLayout());

        lblLeuchteStrassenschluessel.setText("Straßenschlüssel:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblLeuchteStrassenschluessel, gridBagConstraints);

        cbxLeuchteStrassenschluesselNr.setEnabled(false);
        cbxLeuchteStrassenschluesselNr.setRenderer(new DefaultListCellRenderer() {

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
        cbxLeuchteStrassenschluesselNr.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbxLeuchteStrassenschluesselNrActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(cbxLeuchteStrassenschluesselNr, gridBagConstraints);

        cbxLeuchteStrassenschluessel.setEnabled(false);
        cbxLeuchteStrassenschluessel.setRenderer(new DefaultListCellRenderer() {

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
                cbxLeuchteStrassenschluessel,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setValidator(new NotNullValidator("Strassenschlüssel"));
        bindingGroup.addBinding(binding);

        cbxLeuchteStrassenschluessel.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbxLeuchteStrassenschluesselActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(cbxLeuchteStrassenschluessel, gridBagConstraints);

        lblLeuchteKenziffer.setText("Kennziffer:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblLeuchteKenziffer, gridBagConstraints);

        cbxLeuchteKennziffer.setEnabled(false);
        cbxLeuchteKennziffer.setRenderer(new DefaultListCellRenderer() {

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
                    } else if (value instanceof de.cismet.cids.custom.beans.belis2.TkeyKennzifferCustomBean) {
                        final de.cismet.cids.custom.beans.belis2.TkeyKennzifferCustomBean kzf =
                            (de.cismet.cids.custom.beans.belis2.TkeyKennzifferCustomBean)value;
                        setText(kzf.getKeyString());
                    }
                    return this;
                }
            });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.kennziffer}"),
                cbxLeuchteKennziffer,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"),
                "");
        binding.setValidator(new NotNullValidator("Kennziffer"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(cbxLeuchteKennziffer, gridBagConstraints);

        lblLeuchteLaufendenummer.setText("Laufende Nr.:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblLeuchteLaufendenummer, gridBagConstraints);

        txfLeuchteLaufendenummer.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.laufendeNummer}"),
                txfLeuchteLaufendenummer,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txfLeuchteLaufendenummer, gridBagConstraints);

        lblLeuchteLeuchtennummer.setText("Leuchtennummer:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblLeuchteLeuchtennummer, gridBagConstraints);

        txtLeuchteLeuchtennummer.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.leuchtennummer}"),
                txtLeuchteLeuchtennummer,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setValidator(new LeuchtennummerValidator());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txtLeuchteLeuchtennummer, gridBagConstraints);

        lblLeuchteEnergielieferant.setText("Energielieferant:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblLeuchteEnergielieferant, gridBagConstraints);

        cbxLeuchteEnergielieferant.setEnabled(false);
        cbxLeuchteEnergielieferant.setRenderer(new DefaultListCellRenderer() {

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
                    } else if (value instanceof de.cismet.cids.custom.beans.belis2.TkeyEnergielieferantCustomBean) {
                        final de.cismet.cids.custom.beans.belis2.TkeyEnergielieferantCustomBean el =
                            (de.cismet.cids.custom.beans.belis2.TkeyEnergielieferantCustomBean)value;
                        setText(el.getEnergielieferant());
                    }
                    return this;
                }
            });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.energielieferant}"),
                cbxLeuchteEnergielieferant,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(cbxLeuchteEnergielieferant, gridBagConstraints);

        lblLeuchteSchaltstelle.setText("Schaltstelle:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblLeuchteSchaltstelle, gridBagConstraints);

        txtLeuchteSchaltstelle.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.schaltstelle}"),
                txtLeuchteSchaltstelle,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setValidator(new StringMaxLengthValidator());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txtLeuchteSchaltstelle, gridBagConstraints);

        lblLeuchteRundsteuer.setText("Rundsteuerempfänger:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblLeuchteRundsteuer, gridBagConstraints);

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
                    } else if (value instanceof de.cismet.cids.custom.beans.belis2.TkeyEnergielieferantCustomBean) {
                        final de.cismet.cids.custom.beans.belis2.TkeyEnergielieferantCustomBean el =
                            (de.cismet.cids.custom.beans.belis2.TkeyEnergielieferantCustomBean)value;
                        setText(el.getEnergielieferant());
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
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(cbxRundsteuerempfaenger, gridBagConstraints);

        lblEinbaudatum.setText("Einbaudatum:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblEinbaudatum, gridBagConstraints);

        dapEinbaudatum.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.einbaudatum}"),
                dapEinbaudatum,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(dapEinbaudatum, gridBagConstraints);

        lblLeuchteLeuchtentyp.setText("Leuchtentyp:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblLeuchteLeuchtentyp, gridBagConstraints);

        cbxLeuchteLeuchtentyp.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.leuchtentyp}"),
                cbxLeuchteLeuchtentyp,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(cbxLeuchteLeuchtentyp, gridBagConstraints);

        lblLeuchteUnterhalt.setText("Unterhalt Leuchte:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblLeuchteUnterhalt, gridBagConstraints);

        cbxLeuchteUnterhalt.setEnabled(false);
        cbxLeuchteUnterhalt.setRenderer(new DefaultListCellRenderer() {

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
                    } else if (value instanceof de.cismet.cids.custom.beans.belis2.TkeyUnterhLeuchteCustomBean) {
                        final de.cismet.cids.custom.beans.belis2.TkeyUnterhLeuchteCustomBean ul =
                            (de.cismet.cids.custom.beans.belis2.TkeyUnterhLeuchteCustomBean)value;
                        setText(ul.getUnterhaltspflichtigeLeuchte());
                    }
                    return this;
                }
            });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.unterhaltspflichtLeuchte}"),
                cbxLeuchteUnterhalt,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(cbxLeuchteUnterhalt, gridBagConstraints);

        lblLeuchteZaehler.setText("Zähler vorhanden:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblLeuchteZaehler, gridBagConstraints);

        cboLeuchteZaehler.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.zaehler}"),
                cboLeuchteZaehler,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(cboLeuchteZaehler, gridBagConstraints);

        lblLeuchteInbetriebnahme.setText("Inbetriebnahme:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblLeuchteInbetriebnahme, gridBagConstraints);

        dapLeuchteInbetriebnahme.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.inbetriebnahmeLeuchte}"),
                dapLeuchteInbetriebnahme,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setValidator(new DateValidator());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(dapLeuchteInbetriebnahme, gridBagConstraints);

        lblLeuchteDoppelkommando1.setText("Doppelkomando 1:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblLeuchteDoppelkommando1, gridBagConstraints);

        cbxLeuchteDoppelkommando1.setEnabled(false);
        cbxLeuchteDoppelkommando1.setRenderer(new DefaultListCellRenderer() {

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
                    } else if (value instanceof de.cismet.cids.custom.beans.belis2.TkeyDoppelkommandoCustomBean) {
                        final de.cismet.cids.custom.beans.belis2.TkeyDoppelkommandoCustomBean dk =
                            (de.cismet.cids.custom.beans.belis2.TkeyDoppelkommandoCustomBean)value;
                        setText(dk.getPk() + " - " + dk.getBeschreibung());
                    }
                    return this;
                }
            });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.dk1}"),
                cbxLeuchteDoppelkommando1,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(cbxLeuchteDoppelkommando1, gridBagConstraints);

        sprLeuchteDoppelkommando1Anzahl.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.anzahl1DK}"),
                sprLeuchteDoppelkommando1Anzahl,
                org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setSourceNullValue(0);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(sprLeuchteDoppelkommando1Anzahl, gridBagConstraints);

        lblLeuchteDoppelkommando2.setText("Doppelkomando 2:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblLeuchteDoppelkommando2, gridBagConstraints);

        cbxLeuchteDoppelkommando2.setEnabled(false);
        cbxLeuchteDoppelkommando2.setRenderer(new DefaultListCellRenderer() {

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
                    } else if (value instanceof de.cismet.cids.custom.beans.belis2.TkeyDoppelkommandoCustomBean) {
                        final de.cismet.cids.custom.beans.belis2.TkeyDoppelkommandoCustomBean dk =
                            (de.cismet.cids.custom.beans.belis2.TkeyDoppelkommandoCustomBean)value;
                        setText(dk.getPk() + " - " + dk.getBeschreibung());
                    }
                    return this;
                }
            });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.dk2}"),
                cbxLeuchteDoppelkommando2,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(cbxLeuchteDoppelkommando2, gridBagConstraints);

        sprLeuchteDoppelkommando2Anzahl.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.anzahl2DK}"),
                sprLeuchteDoppelkommando2Anzahl,
                org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setSourceNullValue(0);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(sprLeuchteDoppelkommando2Anzahl, gridBagConstraints);

        lblAnschlussleistung1DK.setText("Anschlussleistung 1DK:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblAnschlussleistung1DK, gridBagConstraints);

        txtAnschlussleistung1DK.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtAnschlussleistung1DK.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.anschlussleistung_1dk}"),
                txtAnschlussleistung1DK,
                org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txtAnschlussleistung1DK, gridBagConstraints);

        lblAnschlussleistung2DK.setText("Anschlussleistung 2DK:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblAnschlussleistung2DK, gridBagConstraints);

        txtAnschlussleistung2DK.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtAnschlussleistung2DK.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txtAnschlussleistung2DK, gridBagConstraints);

        lblLeuchtmittel.setText("Leuchtmittel:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblLeuchtmittel, gridBagConstraints);

        cbxLeuchtmittel.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.leuchtmittel}"),
                cbxLeuchtmittel,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(cbxLeuchtmittel, gridBagConstraints);

        lblLebensdauer.setText("Lebensdauer:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblLebensdauer, gridBagConstraints);

        txtLebensdauer.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.lebensdauer}"),
                txtLebensdauer,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txtLebensdauer, gridBagConstraints);

        lblLeuchtmittelwechsel.setText("Leuchtmittelwechsel:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblLeuchtmittelwechsel, gridBagConstraints);

        dapLeuchtmittelwechsel.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.wechseldatum}"),
                dapLeuchtmittelwechsel,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(dapLeuchtmittelwechsel, gridBagConstraints);

        lblNaechsterWechsel.setText("nächster Wechsel:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblNaechsterWechsel, gridBagConstraints);

        dapNaechsterWechsel.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.naechster_wechsel}"),
                dapNaechsterWechsel,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(dapNaechsterWechsel, gridBagConstraints);

        lblSonderturnus.setText("Sonderturnus:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblSonderturnus, gridBagConstraints);

        dapSonderturnus.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.wartungszyklus}"),
                dapSonderturnus,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(dapSonderturnus, gridBagConstraints);

        lblWechselVorschaltgeraet.setText("Erneuerung Vorschaltgerät:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblWechselVorschaltgeraet, gridBagConstraints);

        dapWechselVorschaltgeraet.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.wechselvorschaltgeraet}"),
                dapWechselVorschaltgeraet,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(dapWechselVorschaltgeraet, gridBagConstraints);

        lblVorschaltgeraet.setText("Vorschaltgerät:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblVorschaltgeraet, gridBagConstraints);

        txfVorschaltgeraet.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.vorschaltgeraet}"),
                txfVorschaltgeraet,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txfVorschaltgeraet, gridBagConstraints);

        lblLeuchteMontagefirma.setText("Montagefirma:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblLeuchteMontagefirma, gridBagConstraints);

        txfLeuchteMontagefirma.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.montageFirmaLeuchte}"),
                txfLeuchteMontagefirma,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setValidator(new StringMaxLengthValidator());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txfLeuchteMontagefirma, gridBagConstraints);

        lblLeuchteBemerkung.setText("Bemerkung:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblLeuchteBemerkung, gridBagConstraints);

        txaLeuchteBemerkung.setColumns(20);
        txaLeuchteBemerkung.setRows(5);
        txaLeuchteBemerkung.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.bemerkungen}"),
                txaLeuchteBemerkung,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setValidator(new StringMaxLengthValidator());
        bindingGroup.addBinding(binding);

        scpLeuchteBemerkung.setViewportView(txaLeuchteBemerkung);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(scpLeuchteBemerkung, gridBagConstraints);

        lblLeuchteStandortangabe.setText("Standortangabe:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblLeuchteStandortangabe, gridBagConstraints);

        txfLeuchteStandortAngabe.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.standort.standortangabe}"),
                txfLeuchteStandortAngabe,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txfLeuchteStandortAngabe, gridBagConstraints);

        lblLeuchteStadtbezirk.setText("Stadtbezik:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblLeuchteStadtbezirk, gridBagConstraints);

        cbxLeuchteStadtbezirk.setEnabled(false);
        cbxLeuchteStadtbezirk.setRenderer(new DefaultListCellRenderer() {

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
                    } else if (value instanceof de.cismet.cids.custom.beans.belis2.TkeyBezirkCustomBean) {
                        final de.cismet.cids.custom.beans.belis2.TkeyBezirkCustomBean sb =
                            (de.cismet.cids.custom.beans.belis2.TkeyBezirkCustomBean)value;
                        setText(sb.getBezirk());
                    }
                    return this;
                }
            });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.standort.stadtbezirk}"),
                cbxLeuchteStadtbezirk,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(cbxLeuchteStadtbezirk, gridBagConstraints);

        lblLeuchteVerrechnungseinheit.setText("V-Einheit:");                 // NOI18N
        lblLeuchteVerrechnungseinheit.setToolTipText("Verrechnungseinheit"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblLeuchteVerrechnungseinheit, gridBagConstraints);

        cboLeuchteVerrechnungseinheit.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.standort.verrechnungseinheit}"),
                cboLeuchteVerrechnungseinheit,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(cboLeuchteVerrechnungseinheit, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        add(panContent, gridBagConstraints);

        final javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                550,
                Short.MAX_VALUE));
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                145,
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
            cbxLeuchteStrassenschluessel,
            TkeyStrassenschluesselCustomBean.TABLE);
        fillComboBoxWithKeyTableValuesAndAddListener(
            cbxLeuchteStrassenschluesselNr,
            TkeyStrassenschluesselCustomBean.TABLE,
            true);
        fillComboBoxWithKeyTableValuesAndAddListener(cbxLeuchteKennziffer, TkeyKennzifferCustomBean.TABLE);
        fillComboBoxWithKeyTableValuesAndAddListener(cbxLeuchteEnergielieferant, TkeyEnergielieferantCustomBean.TABLE);
        fillComboBoxWithKeyTableValuesAndAddListener(cbxRundsteuerempfaenger, RundsteuerempfaengerCustomBean.TABLE);
        fillComboBoxWithKeyTableValuesAndAddListener(cbxLeuchteLeuchtentyp, TkeyLeuchtentypCustomBean.TABLE);
        fillComboBoxWithKeyTableValuesAndAddListener(cbxLeuchteDoppelkommando2, TkeyDoppelkommandoCustomBean.TABLE);
        fillComboBoxWithKeyTableValuesAndAddListener(cbxLeuchteStadtbezirk, TkeyBezirkCustomBean.TABLE);
        fillComboBoxWithKeyTableValuesAndAddListener(cbxLeuchtmittel, LeuchtmittelCustomBean.TABLE);

        try {
            final Collection<TkeyUnterhLeuchteCustomBean> unterhalt = CidsBroker.getInstance()
                        .getAll(TkeyUnterhLeuchteCustomBean.TABLE);
            try {
                if ((unterhalt != null) && (unterhalt.size() > 0)) {
                    for (final TkeyUnterhLeuchteCustomBean curUnterhaltLeuchte : unterhalt) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Leuchte.DEFAULT_UNTERHALT " + TdtaLeuchtenCustomBean.DEFAULT_UNTERHALT);
                        }
                        if (TdtaLeuchtenCustomBean.DEFAULT_UNTERHALT.equals(curUnterhaltLeuchte)
                                    && TdtaLeuchtenCustomBean.DEFAULT_UNTERHALT.getUnterhaltspflichtigeLeuchte().equals(
                                        curUnterhaltLeuchte.getUnterhaltspflichtigeLeuchte())) {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("Setting defaultUnterhaltLeuchte to: " + curUnterhaltLeuchte);
                            }
                            BelisBroker.setDefaultUnterhaltLeuchte(curUnterhaltLeuchte);
                        }
                    }
                }
            } catch (Exception ex) {
                LOG.warn("Error while determining default UnterhaltLeuchte: ", ex);
            }
            fillComboBoxWithKeyTableValuesAndAddListener(cbxLeuchteUnterhalt, TkeyUnterhLeuchteCustomBean.TABLE);
        } catch (ActionNotSuccessfulException ex) {
            cbxLeuchteUnterhalt.setModel(new DefaultComboBoxModel());
        }

        try {
            final Collection<TkeyDoppelkommandoCustomBean> dk1 = CidsBroker.getInstance()
                        .getAll(TkeyDoppelkommandoCustomBean.TABLE);
            try {
                if ((dk1 != null) && (dk1.size() > 0)) {
                    for (final TkeyDoppelkommandoCustomBean curDoppelkommando : dk1) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Leuchte.DEFAULT_DOPPELKOMMANDO "
                                        + TdtaLeuchtenCustomBean.DEFAULT_DOPPELKOMMANDO);
                        }
                        if (TdtaLeuchtenCustomBean.DEFAULT_DOPPELKOMMANDO.equals(curDoppelkommando)
                                    && TdtaLeuchtenCustomBean.DEFAULT_DOPPELKOMMANDO.getBeschreibung().equals(
                                        curDoppelkommando.getBeschreibung())) {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("Setting defaultUnterhaltLeuchte to: " + curDoppelkommando);
                            }
                            BelisBroker.setDefaultDoppelkommando1(curDoppelkommando);
                        }
                    }
                }
            } catch (Exception ex) {
                LOG.warn("Error while determining default Doppelkommando1: ", ex);
            }
            fillComboBoxWithKeyTableValuesAndAddListener(cbxLeuchteDoppelkommando1, TkeyDoppelkommandoCustomBean.TABLE);
        } catch (ActionNotSuccessfulException ex) {
            cbxLeuchteDoppelkommando1.setModel(new DefaultComboBoxModel());
        }

        cbxLeuchteStrassenschluessel.setSelectedItem(null);
        cbxLeuchteStrassenschluesselNr.setSelectedItem(null);
        cbxLeuchteKennziffer.setSelectedItem(null);
        cbxRundsteuerempfaenger.setSelectedItem(null);
        cbxLeuchteLeuchtentyp.setSelectedItem(null);
        cbxLeuchteDoppelkommando1.setSelectedItem(null);
        cbxLeuchteDoppelkommando2.setSelectedItem(null);
        cbxLeuchteUnterhalt.setSelectedItem(null);
        cbxLeuchteEnergielieferant.setSelectedItem(null);
        cbxLeuchteStadtbezirk.setSelectedItem(null);
        cbxLeuchtmittel.setSelectedItem(null);

        AutoCompleteDecorator.decorate(cbxLeuchteStrassenschluessel, new ObjectToKeyStringConverter());
        AutoCompleteDecorator.decorate(cbxLeuchteStrassenschluesselNr, new ObjectToPkConverter("pk"));
        AutoCompleteDecorator.decorate(cbxRundsteuerempfaenger, new ObjectToPkConverter("rs_typ"));
        AutoCompleteDecorator.decorate(cbxLeuchteLeuchtentyp, new ObjectToStringConverter() {

                @Override
                public String getPreferredStringForItem(final Object item) {
                    if ((item != null) && (item instanceof TkeyLeuchtentypCustomBean)) {
                        return ((TkeyLeuchtentypCustomBean)item).getKeyString();
                    } else {
                        return null;
                    }
                }
            });
    }

    /**
     * DOCUMENT ME!
     */
    @Override
    public void commitEdits() {
        try {
            dapLeuchteInbetriebnahme.getEditor().commitEdit();
        } catch (ParseException ex) {
            LOG.warn("Error while commiting edits: " + ex);
        }
        try {
            sprLeuchteDoppelkommando1Anzahl.commitEdit();
        } catch (ParseException ex) {
            LOG.warn("Error while commiting edits: " + ex);
        }
        try {
            sprLeuchteDoppelkommando2Anzahl.commitEdit();
        } catch (ParseException ex) {
            LOG.warn("Error while commiting edits: " + ex);
        }
        try {
            dapNaechsterWechsel.commitEdit();
        } catch (ParseException ex) {
            LOG.warn("Error while commiting edits: " + ex);
        }
        try {
            dapLeuchtmittelwechsel.commitEdit();
        } catch (ParseException ex) {
            LOG.warn("Error while commiting edits: " + ex);
        }
        try {
            dapSonderturnus.commitEdit();
        } catch (ParseException ex) {
            LOG.warn("Error while commiting edits: " + ex);
        }
        try {
            dapWechselVorschaltgeraet.commitEdit();
        } catch (ParseException ex) {
            LOG.warn("Error while commiting edits: " + ex);
        }
        try {
            dapEinbaudatum.commitEdit();
        } catch (ParseException ex) {
            LOG.warn("Error while commiting edits: " + ex);
        }
    }

    @Override
    final void initComponentToLabelMap() {
        componentToLabelMap.put(cboLeuchteZaehler, lblLeuchteZaehler);
        componentToLabelMap.put(cbxLeuchteDoppelkommando1, lblLeuchteDoppelkommando1);
        componentToLabelMap.put(cbxLeuchteDoppelkommando2, lblLeuchteDoppelkommando2);
        componentToLabelMap.put(cbxLeuchteEnergielieferant, lblLeuchteEnergielieferant);
        componentToLabelMap.put(cbxLeuchteKennziffer, lblLeuchteKenziffer);
        componentToLabelMap.put(cbxLeuchteLeuchtentyp, lblLeuchteLeuchtentyp);
        componentToLabelMap.put(cbxLeuchteStrassenschluessel, lblLeuchteStrassenschluessel);
        componentToLabelMap.put(cbxLeuchteStrassenschluesselNr, lblLeuchteStrassenschluessel);
        componentToLabelMap.put(cbxLeuchteUnterhalt, lblLeuchteUnterhalt);
        componentToLabelMap.put(dapLeuchteInbetriebnahme, lblLeuchteInbetriebnahme);
        componentToLabelMap.put(sprLeuchteDoppelkommando1Anzahl, lblLeuchteDoppelkommando1);
        componentToLabelMap.put(sprLeuchteDoppelkommando2Anzahl, lblLeuchteDoppelkommando2);
        componentToLabelMap.put(txaLeuchteBemerkung, lblLeuchteBemerkung);
        componentToLabelMap.put(txfLeuchteLaufendenummer, lblLeuchteLaufendenummer);
        componentToLabelMap.put(txfLeuchteMontagefirma, lblLeuchteMontagefirma);
        componentToLabelMap.put(txtLeuchteLeuchtennummer, lblLeuchteLeuchtennummer);
        componentToLabelMap.put(cbxRundsteuerempfaenger, lblLeuchteRundsteuer);
        componentToLabelMap.put(txtLeuchteSchaltstelle, lblLeuchteSchaltstelle);
        componentToLabelMap.put(txtAnschlussleistung1DK, lblAnschlussleistung1DK);
        componentToLabelMap.put(txtAnschlussleistung2DK, lblAnschlussleistung2DK);
        componentToLabelMap.put(cbxLeuchtmittel, lblLeuchtmittel);
        componentToLabelMap.put(txtLebensdauer, lblLebensdauer);
        componentToLabelMap.put(dapLeuchtmittelwechsel, lblLeuchtmittelwechsel);
        componentToLabelMap.put(dapNaechsterWechsel, lblNaechsterWechsel);
        componentToLabelMap.put(dapSonderturnus, lblSonderturnus);
        componentToLabelMap.put(dapWechselVorschaltgeraet, lblWechselVorschaltgeraet);
        componentToLabelMap.put(dapEinbaudatum, lblEinbaudatum);
        componentToLabelMap.put(txfVorschaltgeraet, lblVorschaltgeraet);
    }

    @Override
    public void setElementsNull() {
        if (((TdtaLeuchtenCustomBean)currentEntity).getStrassenschluessel() == null) {
            cbxLeuchteStrassenschluessel.setSelectedItem(null);
        }
        if (((TdtaLeuchtenCustomBean)currentEntity).getKennziffer() == null) {
            cbxLeuchteKennziffer.setSelectedItem(null);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isEditable  DOCUMENT ME!
     */
    public void setInheritedMastPropertiesEnabled(final boolean isEditable) {
        lblLeuchteStandortangabe.setVisible(isEditable);
        lblLeuchteVerrechnungseinheit.setVisible(isEditable);
        lblLeuchteStadtbezirk.setVisible(isEditable);
        cbxLeuchteStadtbezirk.setVisible(isEditable);
        txfLeuchteStandortAngabe.setVisible(isEditable);
        cboLeuchteVerrechnungseinheit.setVisible(isEditable);

        RendererTools.setEditable(cbxLeuchteStrassenschluessel, isEditable);
        RendererTools.setEditable(cbxLeuchteStrassenschluesselNr, isEditable);
        RendererTools.setEditable(cbxLeuchteKennziffer, isEditable);
//        RendererTools.setEditable(cbxLeuchteStadtbezirk, isEditable);
//        RendererTools.setEditable(txfLeuchteStandortAngabe, isEditable);
//        RendererTools.setEditable(cboLeuchteVerrechnungseinheit, isEditable);
//        RendererTools.setEditable(txtAnschlussleistung1DK, isEditable);
//        RendererTools.setEditable(txtAnschlussleistung2DK, isEditable);
//        RendererTools.setEditable(cbxLeuchtmittel, isEditable);
//        RendererTools.setEditable(txtLebensdauer, isEditable);
//        RendererTools.setEditable(dapLeuchtmittelwechsel, isEditable);
//        RendererTools.setEditable(dapNaechsterWechsel, isEditable);
//        RendererTools.setEditable(dapSonderturnus, isEditable);
//        RendererTools.setEditable(dapWechselVorschaltgeraet, isEditable);
//        RendererTools.setEditable(txfVorschaltgeraet, isEditable);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbxLeuchteStrassenschluesselNrActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbxLeuchteStrassenschluesselNrActionPerformed
        try {
            if (!isTriggerd) {
                isTriggerd = true;
                cbxLeuchteStrassenschluessel.setSelectedItem(cbxLeuchteStrassenschluesselNr.getSelectedItem());
            }
        } catch (Exception ex) {
            LOG.warn("failuire while updating strassenschluessel ", ex);
        } finally {
            isTriggerd = false;
        }
    }                                                                                                  //GEN-LAST:event_cbxLeuchteStrassenschluesselNrActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbxLeuchteStrassenschluesselActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbxLeuchteStrassenschluesselActionPerformed
        try {
            if (!isTriggerd) {
                isTriggerd = true;
                cbxLeuchteStrassenschluesselNr.setSelectedItem(cbxLeuchteStrassenschluessel.getSelectedItem());
            }
        } catch (Exception ex) {
            LOG.warn("failuire while updating strassenschluessel ", ex);
        } finally {
            isTriggerd = false;
        }
    }                                                                                                //GEN-LAST:event_cbxLeuchteStrassenschluesselActionPerformed

    @Override
    public void setPanelEditable(final boolean isEditable) {
        RendererTools.setEditable(txtLeuchteLeuchtennummer, isEditable);
        RendererTools.setEditable(cbxLeuchteEnergielieferant, isEditable);
        RendererTools.setEditable(txtLeuchteSchaltstelle, isEditable);
        RendererTools.setEditable(cbxRundsteuerempfaenger, isEditable);
        RendererTools.setEditable(cbxLeuchteUnterhalt, isEditable);
        RendererTools.setEditable(cboLeuchteZaehler, isEditable);
        RendererTools.setEditable(dapLeuchteInbetriebnahme, isEditable);
        RendererTools.setEditable(cbxLeuchteLeuchtentyp, isEditable);
        RendererTools.setEditable(cbxLeuchteDoppelkommando1, isEditable);
        RendererTools.setEditable(cbxLeuchteDoppelkommando2, isEditable);
        RendererTools.setEditable(sprLeuchteDoppelkommando1Anzahl, isEditable);
        RendererTools.setEditable(sprLeuchteDoppelkommando2Anzahl, isEditable);
        RendererTools.setEditable(txfLeuchteMontagefirma, isEditable);
        RendererTools.setEditable(txaLeuchteBemerkung, isEditable);
        if ((belisBroker.getWorkbenchWidget() != null)
                    && !((belisBroker.getWorkbenchWidget().getSelectedTreeNode() != null)
                        && belisBroker.getWorkbenchWidget().isParentNodeMast(
                            belisBroker.getWorkbenchWidget().getSelectedTreeNode().getLastPathComponent()))) {
            RendererTools.setEditable(cbxLeuchteKennziffer, isEditable);
            RendererTools.setEditable(cbxLeuchteStrassenschluessel, isEditable);
            RendererTools.setEditable(cbxLeuchteStrassenschluesselNr, isEditable);
        }
        RendererTools.setEditable(cbxLeuchteStadtbezirk, isEditable);
        RendererTools.setEditable(txfLeuchteStandortAngabe, isEditable);
        RendererTools.setEditable(cboLeuchteVerrechnungseinheit, isEditable);
        RendererTools.setEditable(txtAnschlussleistung1DK, isEditable);
        RendererTools.setEditable(txtAnschlussleistung2DK, isEditable);
        RendererTools.setEditable(cbxLeuchtmittel, isEditable);
        RendererTools.setEditable(txtLebensdauer, isEditable);
        RendererTools.setEditable(dapLeuchtmittelwechsel, isEditable);
        RendererTools.setEditable(dapNaechsterWechsel, isEditable);
        RendererTools.setEditable(dapSonderturnus, isEditable);
        RendererTools.setEditable(dapWechselVorschaltgeraet, isEditable);
        RendererTools.setEditable(txfVorschaltgeraet, isEditable);
        RendererTools.setEditable(dapEinbaudatum, isEditable);
    }

    @Override
    protected BindingGroup getBindingGroup() {
        return bindingGroup;
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public class LeuchtennummerValidator extends Validator<Integer> {

        //~ Methods ------------------------------------------------------------

        @Override
        public Validator.Result validate(final Integer value) {
            if (value != null) {
                if (value.shortValue() > -1) {
                    final TreePath pathToEntity = belisBroker.getWorkbenchWidget()
                                .getTreeTableModel()
                                .getPathForUserObject(currentEntity);
                    if ((pathToEntity != null) && (pathToEntity.getLastPathComponent() != null)) {
                        final TdtaStandortMastCustomBean parentStandort = belisBroker.getWorkbenchWidget()
                                    .getParentMast(pathToEntity.getLastPathComponent());
                        if ((parentStandort != null) && parentStandort.isStandortMast()
                                    && (parentStandort.getLeuchten().size() > 1)) {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug(
                                    "Standort is Mast checking leuchten for entries with the same leuchtennummer.");
                            }
                            for (final TdtaLeuchtenCustomBean curLeuchte : parentStandort.getLeuchten()) {
                                if (LOG.isDebugEnabled()) {
                                    LOG.debug("checking leuchte: " + curLeuchte);
                                }
                                if ((curLeuchte.getLeuchtennummer() != null)
                                            && curLeuchte.getLeuchtennummer().equals(value)
                                            && !curLeuchte.equals(currentEntity)) {
                                    if (LOG.isDebugEnabled()) {
                                        LOG.debug("found another leuchte with the same leuchtennumber");
                                    }
                                    return new Validator.Result(
                                            "code",
                                            "Es darf nicht zwei Leuchten mit der selben Leuchtennummer geben.");
                                }
                            }
                        }
                    }
                    return null;
                } else {
                    return new Validator.Result("code", "Leuchtennummer darf nicht negativ sein.");
                }
            } else {
                return new Validator.Result("code", "Leuchtennummer muss gesetzt sein.");
            }
        }
    }
}
