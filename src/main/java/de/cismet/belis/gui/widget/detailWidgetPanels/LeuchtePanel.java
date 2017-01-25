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

import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.JLabel;
import javax.swing.tree.TreePath;

import de.cismet.belis.broker.BelisBroker;

import de.cismet.belis.gui.TimestampToDateConverter;

import de.cismet.belis.util.RendererTools;

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
    private javax.swing.JCheckBox cboLeuchteZaehler;
    private javax.swing.JCheckBox cboVerrechnungseinheit;
    private javax.swing.JComboBox cbxDoppelkommando1;
    private javax.swing.JComboBox cbxDoppelkommando2;
    private javax.swing.JComboBox cbxEnergielieferant;
    private javax.swing.JComboBox cbxKennziffer;
    private javax.swing.JComboBox cbxLeuchteStrassenschluessel;
    private javax.swing.JComboBox cbxLeuchteStrassenschluesselNr;
    private javax.swing.JComboBox cbxLeuchtentyp;
    private javax.swing.JComboBox cbxLeuchtmittel;
    private javax.swing.JComboBox cbxRundsteuerempfaenger;
    private javax.swing.JComboBox cbxStadtbezirk;
    private javax.swing.JComboBox cbxUnterhalt;
    private org.jdesktop.swingx.JXDatePicker dapEinbaudatum;
    private org.jdesktop.swingx.JXDatePicker dapInbetriebnahme;
    private org.jdesktop.swingx.JXDatePicker dapLeuchtmittelwechsel;
    private org.jdesktop.swingx.JXDatePicker dapNaechsterWechsel;
    private org.jdesktop.swingx.JXDatePicker dapSonderturnus;
    private org.jdesktop.swingx.JXDatePicker dapWechselVorschaltgeraet;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblAnschlussleistung1DK;
    private javax.swing.JLabel lblAnschlussleistung2DK;
    private javax.swing.JLabel lblBemerkung;
    private javax.swing.JLabel lblDoppelkommando1;
    private javax.swing.JLabel lblDoppelkommando2;
    private javax.swing.JLabel lblEinbaudatum;
    private javax.swing.JLabel lblEnergielieferant;
    private javax.swing.JLabel lblInbetriebnahme;
    private javax.swing.JLabel lblKennziffer;
    private javax.swing.JLabel lblLaufendenummer;
    private javax.swing.JLabel lblLebensdauer;
    private javax.swing.JLabel lblLeuchte;
    private javax.swing.JLabel lblLeuchteLeuchtennummer;
    private javax.swing.JLabel lblLeuchteZaehler;
    private javax.swing.JLabel lblLeuchtentyp;
    private javax.swing.JLabel lblLeuchtmittel;
    private javax.swing.JLabel lblLeuchtmittelwechsel;
    private javax.swing.JLabel lblMontagefirma;
    private javax.swing.JLabel lblNaechsterWechsel;
    private javax.swing.JLabel lblRundsteuer;
    private javax.swing.JLabel lblSchaltstelle;
    private javax.swing.JLabel lblSonderturnus;
    private javax.swing.JLabel lblStadtbezirk;
    private javax.swing.JLabel lblStandortangabe;
    private javax.swing.JLabel lblStrassenschluessel;
    private javax.swing.JLabel lblUnterhalt;
    private javax.swing.JLabel lblVerrechnungseinheit;
    private javax.swing.JLabel lblVorschaltgeraet;
    private javax.swing.JLabel lblWechselVorschaltgeraet;
    private javax.swing.JPanel panContent;
    private javax.swing.JPanel panDoppelkommando1;
    private javax.swing.JPanel panDoppelkommando2;
    private javax.swing.JPanel panInbetriebnahme;
    private javax.swing.JPanel panLaufendeNummer;
    private javax.swing.JPanel panLeuchtmittelwechsel;
    private javax.swing.JPanel panSpacer0;
    private javax.swing.JPanel panSpacer1;
    private javax.swing.JPanel panSpacerBottom;
    private javax.swing.JPanel panSpacerBottom1;
    private javax.swing.JPanel panSpacerRight;
    private javax.swing.JPanel panStrassenschluessel;
    private javax.swing.JScrollPane scpBemerkung;
    private javax.swing.JTextArea txaBemerkung;
    private javax.swing.JTextField txfLeuchteLaufendenummer;
    private javax.swing.JTextField txfMontagefirma;
    private javax.swing.JTextField txfStandortAngabe;
    private javax.swing.JTextField txfVorschaltgeraet;
    private javax.swing.JFormattedTextField txtAnschlussleistung1DK;
    private javax.swing.JFormattedTextField txtAnschlussleistung2DK;
    private javax.swing.JFormattedTextField txtDoppelkommando1Anzahl;
    private javax.swing.JFormattedTextField txtDoppelkommando2Anzahl;
    private javax.swing.JFormattedTextField txtLebensdauer;
    private javax.swing.JTextField txtLeuchteLeuchtennummer;
    private javax.swing.JTextField txtSchaltstelle;
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
        NumberFormat.getIntegerInstance().setMaximumFractionDigits(0);
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
        jPanel1 = new javax.swing.JPanel();
        panContent = new javax.swing.JPanel();
        panSpacer0 = new javax.swing.JPanel();
        panSpacer1 = new javax.swing.JPanel();
        lblStrassenschluessel = new javax.swing.JLabel();
        panStrassenschluessel = new javax.swing.JPanel();
        cbxLeuchteStrassenschluesselNr = BelisBroker.createStrassenschluesselNummerComboBox();
        cbxLeuchteStrassenschluessel = BelisBroker.createKeyTableComboBox(TkeyStrassenschluesselCustomBean.TABLE);
        lblKennziffer = new javax.swing.JLabel();
        cbxKennziffer = BelisBroker.createKeyTableComboBox(TkeyKennzifferCustomBean.TABLE);
        lblLaufendenummer = new javax.swing.JLabel();
        panLaufendeNummer = new javax.swing.JPanel();
        txfLeuchteLaufendenummer = new javax.swing.JTextField();
        lblLeuchteLeuchtennummer = new javax.swing.JLabel();
        txtLeuchteLeuchtennummer = new javax.swing.JTextField();
        lblLeuchtentyp = new javax.swing.JLabel();
        cbxLeuchtentyp = BelisBroker.createKeyTableComboBox(TkeyLeuchtentypCustomBean.TABLE);
        lblInbetriebnahme = new javax.swing.JLabel();
        panInbetriebnahme = new javax.swing.JPanel();
        dapInbetriebnahme = new org.jdesktop.swingx.JXDatePicker();
        lblLeuchteZaehler = new javax.swing.JLabel();
        cboLeuchteZaehler = new javax.swing.JCheckBox();
        lblMontagefirma = new javax.swing.JLabel();
        txfMontagefirma = new javax.swing.JTextField();
        lblEnergielieferant = new javax.swing.JLabel();
        cbxEnergielieferant = BelisBroker.createKeyTableComboBox(TkeyEnergielieferantCustomBean.TABLE);
        lblSchaltstelle = new javax.swing.JLabel();
        txtSchaltstelle = new javax.swing.JTextField();
        lblRundsteuer = new javax.swing.JLabel();
        cbxRundsteuerempfaenger = BelisBroker.createKeyTableComboBox(RundsteuerempfaengerCustomBean.TABLE);
        lblEinbaudatum = new javax.swing.JLabel();
        dapEinbaudatum = new org.jdesktop.swingx.JXDatePicker();
        lblDoppelkommando1 = new javax.swing.JLabel();
        panDoppelkommando1 = new javax.swing.JPanel();
        cbxDoppelkommando1 = BelisBroker.createKeyTableComboBox(TkeyDoppelkommandoCustomBean.TABLE);
        txtDoppelkommando1Anzahl = new javax.swing.JFormattedTextField();
        lblAnschlussleistung1DK = new javax.swing.JLabel();
        txtAnschlussleistung1DK = new javax.swing.JFormattedTextField();
        lblDoppelkommando2 = new javax.swing.JLabel();
        panDoppelkommando2 = new javax.swing.JPanel();
        cbxDoppelkommando2 = BelisBroker.createKeyTableComboBox(TkeyDoppelkommandoCustomBean.TABLE);
        txtDoppelkommando2Anzahl = new javax.swing.JFormattedTextField();
        lblAnschlussleistung2DK = new javax.swing.JLabel();
        txtAnschlussleistung2DK = new javax.swing.JFormattedTextField();
        lblUnterhalt = new javax.swing.JLabel();
        cbxUnterhalt = BelisBroker.createKeyTableComboBox(TkeyUnterhLeuchteCustomBean.TABLE);
        lblLeuchtmittelwechsel = new javax.swing.JLabel();
        panLeuchtmittelwechsel = new javax.swing.JPanel();
        dapLeuchtmittelwechsel = new org.jdesktop.swingx.JXDatePicker();
        lblNaechsterWechsel = new javax.swing.JLabel();
        dapNaechsterWechsel = new org.jdesktop.swingx.JXDatePicker();
        lblLeuchtmittel = new javax.swing.JLabel();
        cbxLeuchtmittel = BelisBroker.createKeyTableComboBox(LeuchtmittelCustomBean.TABLE);
        lblLebensdauer = new javax.swing.JLabel();
        txtLebensdauer = new javax.swing.JFormattedTextField();
        lblSonderturnus = new javax.swing.JLabel();
        dapSonderturnus = new org.jdesktop.swingx.JXDatePicker();
        lblVorschaltgeraet = new javax.swing.JLabel();
        txfVorschaltgeraet = new javax.swing.JTextField();
        lblWechselVorschaltgeraet = new javax.swing.JLabel();
        dapWechselVorschaltgeraet = new org.jdesktop.swingx.JXDatePicker();
        lblBemerkung = new javax.swing.JLabel();
        scpBemerkung = new javax.swing.JScrollPane();
        txaBemerkung = new javax.swing.JTextArea();
        lblStandortangabe = new javax.swing.JLabel();
        txfStandortAngabe = new javax.swing.JTextField();
        lblStadtbezirk = new javax.swing.JLabel();
        cbxStadtbezirk = BelisBroker.createKeyTableComboBox(TkeyBezirkCustomBean.TABLE);
        lblVerrechnungseinheit = new javax.swing.JLabel();
        cboVerrechnungseinheit = new javax.swing.JCheckBox();
        panSpacerBottom = new javax.swing.JPanel();
        panSpacerRight = new javax.swing.JPanel();
        panSpacerBottom1 = new javax.swing.JPanel();

        lblLeuchte.setFont(new java.awt.Font("DejaVu Sans", 1, 13));                       // NOI18N
        lblLeuchte.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/16/leuchte.png"))); // NOI18N
        lblLeuchte.setText("Leuchte");                                                     // NOI18N

        setLayout(new java.awt.GridBagLayout());

        jPanel1.setLayout(new java.awt.GridBagLayout());

        panContent.setName(""); // NOI18N
        panContent.setLayout(new java.awt.GridBagLayout());

        panSpacer0.setMinimumSize(DIMENSION_KEYSPACER);
        panSpacer0.setPreferredSize(DIMENSION_KEYSPACER);

        final javax.swing.GroupLayout panSpacer0Layout = new javax.swing.GroupLayout(panSpacer0);
        panSpacer0.setLayout(panSpacer0Layout);
        panSpacer0Layout.setHorizontalGroup(
            panSpacer0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                163,
                Short.MAX_VALUE));
        panSpacer0Layout.setVerticalGroup(
            panSpacer0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                10,
                Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panContent.add(panSpacer0, gridBagConstraints);

        panSpacer1.setName(""); // NOI18N
        panSpacer1.setPreferredSize(new java.awt.Dimension(420, 0));

        final javax.swing.GroupLayout panSpacer1Layout = new javax.swing.GroupLayout(panSpacer1);
        panSpacer1.setLayout(panSpacer1Layout);
        panSpacer1Layout.setHorizontalGroup(
            panSpacer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                405,
                Short.MAX_VALUE));
        panSpacer1Layout.setVerticalGroup(
            panSpacer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                0,
                Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        panContent.add(panSpacer1, gridBagConstraints);

        lblStrassenschluessel.setText("Straßenschlüssel:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblStrassenschluessel, gridBagConstraints);

        panStrassenschluessel.setLayout(new java.awt.GridBagLayout());

        cbxLeuchteStrassenschluesselNr.setEnabled(false);
        cbxLeuchteStrassenschluesselNr.setMinimumSize(new java.awt.Dimension(80, 27));
        cbxLeuchteStrassenschluesselNr.setPreferredSize(new java.awt.Dimension(80, 27));
        cbxLeuchteStrassenschluesselNr.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbxLeuchteStrassenschluesselNrActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStrassenschluessel.add(cbxLeuchteStrassenschluesselNr, gridBagConstraints);

        cbxLeuchteStrassenschluessel.setEnabled(false);

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
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStrassenschluessel.add(cbxLeuchteStrassenschluessel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panContent.add(panStrassenschluessel, gridBagConstraints);

        lblKennziffer.setText("Kennziffer:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblKennziffer, gridBagConstraints);

        cbxKennziffer.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.kennziffer}"),
                cbxKennziffer,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"),
                "kennziffer");
        binding.setValidator(new NotNullValidator("Kennziffer"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(cbxKennziffer, gridBagConstraints);

        lblLaufendenummer.setText("Laufende Nr.:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblLaufendenummer, gridBagConstraints);

        panLaufendeNummer.setLayout(new java.awt.GridBagLayout());

        txfLeuchteLaufendenummer.setEnabled(false);
        txfLeuchteLaufendenummer.setMinimumSize(new java.awt.Dimension(100, 27));
        txfLeuchteLaufendenummer.setPreferredSize(new java.awt.Dimension(100, 27));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.laufendeNummer}"),
                txfLeuchteLaufendenummer,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panLaufendeNummer.add(txfLeuchteLaufendenummer, gridBagConstraints);

        lblLeuchteLeuchtennummer.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblLeuchteLeuchtennummer.setText("Leuchtennummer:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panLaufendeNummer.add(lblLeuchteLeuchtennummer, gridBagConstraints);

        txtLeuchteLeuchtennummer.setEnabled(false);
        txtLeuchteLeuchtennummer.setMinimumSize(new java.awt.Dimension(50, 27));
        txtLeuchteLeuchtennummer.setPreferredSize(new java.awt.Dimension(50, 27));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.leuchtennummer}"),
                txtLeuchteLeuchtennummer,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setValidator(new LeuchtennummerValidator());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panLaufendeNummer.add(txtLeuchteLeuchtennummer, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panContent.add(panLaufendeNummer, gridBagConstraints);

        lblLeuchtentyp.setText("Leuchtentyp:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblLeuchtentyp, gridBagConstraints);

        cbxLeuchtentyp.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.leuchtentyp}"),
                cbxLeuchtentyp,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(cbxLeuchtentyp, gridBagConstraints);

        lblInbetriebnahme.setText("Inbetriebnahme:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblInbetriebnahme, gridBagConstraints);

        panInbetriebnahme.setLayout(new java.awt.GridBagLayout());

        dapInbetriebnahme.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.inbetriebnahmeLeuchte}"),
                dapInbetriebnahme,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setConverter(new TimestampToDateConverter());
        binding.setValidator(new DateValidator());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInbetriebnahme.add(dapInbetriebnahme, gridBagConstraints);

        lblLeuchteZaehler.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblLeuchteZaehler.setText("Zähler vorhanden:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInbetriebnahme.add(lblLeuchteZaehler, gridBagConstraints);

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
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInbetriebnahme.add(cboLeuchteZaehler, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panContent.add(panInbetriebnahme, gridBagConstraints);

        lblMontagefirma.setText("Montagefirma:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblMontagefirma, gridBagConstraints);

        txfMontagefirma.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.montageFirmaLeuchte}"),
                txfMontagefirma,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setValidator(new StringMaxLengthValidator());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txfMontagefirma, gridBagConstraints);

        lblEnergielieferant.setText("Energielieferant:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblEnergielieferant, gridBagConstraints);

        cbxEnergielieferant.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.energielieferant}"),
                cbxEnergielieferant,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(cbxEnergielieferant, gridBagConstraints);

        lblSchaltstelle.setText("Schaltstelle:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblSchaltstelle, gridBagConstraints);

        txtSchaltstelle.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.schaltstelle}"),
                txtSchaltstelle,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setValidator(new StringMaxLengthValidator());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txtSchaltstelle, gridBagConstraints);

        lblRundsteuer.setText("Rundsteuerempf.:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblRundsteuer, gridBagConstraints);

        cbxRundsteuerempfaenger.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.rundsteuerempfaenger}"),
                cbxRundsteuerempfaenger,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(cbxRundsteuerempfaenger, gridBagConstraints);

        lblEinbaudatum.setText("Einbaudatum:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblEinbaudatum, gridBagConstraints);

        dapEinbaudatum.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.einbaudatum}"),
                dapEinbaudatum,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setConverter(new TimestampToDateConverter());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(dapEinbaudatum, gridBagConstraints);

        lblDoppelkommando1.setText("Doppelkomando 1:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblDoppelkommando1, gridBagConstraints);

        panDoppelkommando1.setLayout(new java.awt.GridBagLayout());

        cbxDoppelkommando1.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.dk1}"),
                cbxDoppelkommando1,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panDoppelkommando1.add(cbxDoppelkommando1, gridBagConstraints);

        txtDoppelkommando1Anzahl.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                new de.cismet.belis.gui.utils.IntegerNumberFormatter()));
        txtDoppelkommando1Anzahl.setEnabled(false);
        txtDoppelkommando1Anzahl.setMinimumSize(new java.awt.Dimension(40, 27));
        txtDoppelkommando1Anzahl.setPreferredSize(new java.awt.Dimension(40, 27));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.anzahl1DK}"),
                txtDoppelkommando1Anzahl,
                org.jdesktop.beansbinding.BeanProperty.create("value"),
                "anzahl1DK");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panDoppelkommando1.add(txtDoppelkommando1Anzahl, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panContent.add(panDoppelkommando1, gridBagConstraints);

        lblAnschlussleistung1DK.setText("Anschlussleistung:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblAnschlussleistung1DK, gridBagConstraints);

        txtAnschlussleistung1DK.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                new de.cismet.belis.gui.utils.DoubleNumberFormatter()));
        txtAnschlussleistung1DK.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.anschlussleistung_1dk}"),
                txtAnschlussleistung1DK,
                org.jdesktop.beansbinding.BeanProperty.create("value"),
                "anschlussleistung_1dk");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txtAnschlussleistung1DK, gridBagConstraints);

        lblDoppelkommando2.setText("Doppelkomando 2:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblDoppelkommando2, gridBagConstraints);

        panDoppelkommando2.setLayout(new java.awt.GridBagLayout());

        cbxDoppelkommando2.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.dk2}"),
                cbxDoppelkommando2,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panDoppelkommando2.add(cbxDoppelkommando2, gridBagConstraints);

        txtDoppelkommando2Anzahl.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                new de.cismet.belis.gui.utils.IntegerNumberFormatter()));
        txtDoppelkommando2Anzahl.setEnabled(false);
        txtDoppelkommando2Anzahl.setMinimumSize(new java.awt.Dimension(40, 27));
        txtDoppelkommando2Anzahl.setPreferredSize(new java.awt.Dimension(40, 27));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.anzahl2DK}"),
                txtDoppelkommando2Anzahl,
                org.jdesktop.beansbinding.BeanProperty.create("value"),
                "anzahl2DK");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panDoppelkommando2.add(txtDoppelkommando2Anzahl, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panContent.add(panDoppelkommando2, gridBagConstraints);

        lblAnschlussleistung2DK.setText("Anschlussleistung:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblAnschlussleistung2DK, gridBagConstraints);

        txtAnschlussleistung2DK.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                new de.cismet.belis.gui.utils.DoubleNumberFormatter()));
        txtAnschlussleistung2DK.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.anschlussleistung_2dk}"),
                txtAnschlussleistung2DK,
                org.jdesktop.beansbinding.BeanProperty.create("value"),
                "anschlussleistung_2dk");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txtAnschlussleistung2DK, gridBagConstraints);

        lblUnterhalt.setText("Unterhalt Leuchte:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblUnterhalt, gridBagConstraints);

        cbxUnterhalt.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.unterhaltspflichtLeuchte}"),
                cbxUnterhalt,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(cbxUnterhalt, gridBagConstraints);

        lblLeuchtmittelwechsel.setText("Leuchtmittelwechsel:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblLeuchtmittelwechsel, gridBagConstraints);

        panLeuchtmittelwechsel.setLayout(new java.awt.GridBagLayout());

        dapLeuchtmittelwechsel.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.wechseldatum}"),
                dapLeuchtmittelwechsel,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setConverter(new TimestampToDateConverter());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panLeuchtmittelwechsel.add(dapLeuchtmittelwechsel, gridBagConstraints);

        lblNaechsterWechsel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblNaechsterWechsel.setText("Nächster Wechsel:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panLeuchtmittelwechsel.add(lblNaechsterWechsel, gridBagConstraints);

        dapNaechsterWechsel.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.naechster_wechsel}"),
                dapNaechsterWechsel,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setConverter(new TimestampToDateConverter());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panLeuchtmittelwechsel.add(dapNaechsterWechsel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panContent.add(panLeuchtmittelwechsel, gridBagConstraints);

        lblLeuchtmittel.setText("Leuchtmittel:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblLeuchtmittel, gridBagConstraints);

        cbxLeuchtmittel.setEnabled(false);
        cbxLeuchtmittel.setLightWeightPopupEnabled(false);
        cbxLeuchtmittel.setMinimumSize(new java.awt.Dimension(300, 27));
        cbxLeuchtmittel.setPreferredSize(new java.awt.Dimension(300, 27));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.leuchtmittel}"),
                cbxLeuchtmittel,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(cbxLeuchtmittel, gridBagConstraints);

        lblLebensdauer.setText("Lebensdauer:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblLebensdauer, gridBagConstraints);

        txtLebensdauer.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                new de.cismet.belis.gui.utils.DoubleNumberFormatter()));
        txtLebensdauer.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.lebensdauer}"),
                txtLebensdauer,
                org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txtLebensdauer, gridBagConstraints);

        lblSonderturnus.setText("Sonderturnus:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblSonderturnus, gridBagConstraints);

        dapSonderturnus.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.wartungszyklus}"),
                dapSonderturnus,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setConverter(new TimestampToDateConverter());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(dapSonderturnus, gridBagConstraints);

        lblVorschaltgeraet.setText("Vorschaltgerät:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
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
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txfVorschaltgeraet, gridBagConstraints);

        lblWechselVorschaltgeraet.setText("Erneuerung VG:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblWechselVorschaltgeraet, gridBagConstraints);

        dapWechselVorschaltgeraet.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.wechselvorschaltgeraet}"),
                dapWechselVorschaltgeraet,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setConverter(new TimestampToDateConverter());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(dapWechselVorschaltgeraet, gridBagConstraints);

        lblBemerkung.setText("Bemerkung:"); // NOI18N
        lblBemerkung.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblBemerkung, gridBagConstraints);

        txaBemerkung.setColumns(20);
        txaBemerkung.setRows(5);
        txaBemerkung.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.bemerkungen}"),
                txaBemerkung,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setValidator(new StringMaxLengthValidator());
        bindingGroup.addBinding(binding);

        scpBemerkung.setViewportView(txaBemerkung);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(scpBemerkung, gridBagConstraints);

        lblStandortangabe.setText("Standortangabe:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblStandortangabe, gridBagConstraints);

        txfStandortAngabe.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.standort.standortangabe}"),
                txfStandortAngabe,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txfStandortAngabe, gridBagConstraints);

        lblStadtbezirk.setText("Stadtbezik:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblStadtbezirk, gridBagConstraints);

        cbxStadtbezirk.setEnabled(false);
        cbxStadtbezirk.setMinimumSize(new java.awt.Dimension(350, 27));
        cbxStadtbezirk.setPreferredSize(new java.awt.Dimension(350, 27));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.standort.stadtbezirk}"),
                cbxStadtbezirk,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(cbxStadtbezirk, gridBagConstraints);

        lblVerrechnungseinheit.setText("V-Einheit:");                 // NOI18N
        lblVerrechnungseinheit.setToolTipText("Verrechnungseinheit"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblVerrechnungseinheit, gridBagConstraints);

        cboVerrechnungseinheit.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.standort.verrechnungseinheit}"),
                cboVerrechnungseinheit,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(cboVerrechnungseinheit, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel1.add(panContent, gridBagConstraints);

        panSpacerBottom.setMinimumSize(DIMENSION_CONTENTSPACER);
        panSpacerBottom.setPreferredSize(DIMENSION_CONTENTSPACER);

        final javax.swing.GroupLayout panSpacerBottomLayout = new javax.swing.GroupLayout(panSpacerBottom);
        panSpacerBottom.setLayout(panSpacerBottomLayout);
        panSpacerBottomLayout.setHorizontalGroup(
            panSpacerBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                568,
                Short.MAX_VALUE));
        panSpacerBottomLayout.setVerticalGroup(
            panSpacerBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                0,
                Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(panSpacerBottom, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        add(jPanel1, gridBagConstraints);

        final javax.swing.GroupLayout panSpacerRightLayout = new javax.swing.GroupLayout(panSpacerRight);
        panSpacerRight.setLayout(panSpacerRightLayout);
        panSpacerRightLayout.setHorizontalGroup(
            panSpacerRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                0,
                Short.MAX_VALUE));
        panSpacerRightLayout.setVerticalGroup(
            panSpacerRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                0,
                Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        add(panSpacerRight, gridBagConstraints);

        final javax.swing.GroupLayout panSpacerBottom1Layout = new javax.swing.GroupLayout(panSpacerBottom1);
        panSpacerBottom1.setLayout(panSpacerBottom1Layout);
        panSpacerBottom1Layout.setHorizontalGroup(
            panSpacerBottom1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                0,
                Short.MAX_VALUE));
        panSpacerBottom1Layout.setVerticalGroup(
            panSpacerBottom1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                0,
                Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        add(panSpacerBottom1, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    @Override
    final void initPanel() {
        bindingGroup.addBindingListener(new PanelBindingListener());

        AutoCompleteDecorator.decorate(cbxLeuchteStrassenschluessel, new ObjectToKeyStringConverter());
        AutoCompleteDecorator.decorate(cbxLeuchteStrassenschluesselNr, new ObjectToPkConverter("pk"));
        AutoCompleteDecorator.decorate(cbxRundsteuerempfaenger, new ObjectToPkConverter("rs_typ"));
        AutoCompleteDecorator.decorate(cbxLeuchtentyp, new ObjectToStringConverter() {

                @Override
                public String getPreferredStringForItem(final Object item) {
                    if ((item != null) && (item instanceof TkeyLeuchtentypCustomBean)) {
                        return ((TkeyLeuchtentypCustomBean)item).toString();
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
            dapInbetriebnahme.getEditor().commitEdit();
        } catch (ParseException ex) {
            LOG.warn("Error while commiting edits: " + ex);
        }
        try {
            txtDoppelkommando1Anzahl.commitEdit();
        } catch (ParseException ex) {
            LOG.warn("Error while commiting edits: " + ex);
        }
        try {
            txtDoppelkommando2Anzahl.commitEdit();
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
        componentToLabelMap.put(cbxDoppelkommando1, lblDoppelkommando1);
        componentToLabelMap.put(cbxDoppelkommando2, lblDoppelkommando2);
        componentToLabelMap.put(cbxEnergielieferant, lblEnergielieferant);
        componentToLabelMap.put(cbxKennziffer, lblKennziffer);
        componentToLabelMap.put(cbxLeuchtentyp, lblLeuchtentyp);
        componentToLabelMap.put(cbxLeuchteStrassenschluessel, lblStrassenschluessel);
        componentToLabelMap.put(cbxLeuchteStrassenschluesselNr, lblStrassenschluessel);
        componentToLabelMap.put(cbxUnterhalt, lblUnterhalt);
        componentToLabelMap.put(dapInbetriebnahme, lblInbetriebnahme);
        componentToLabelMap.put(txtDoppelkommando1Anzahl, lblDoppelkommando1);
        componentToLabelMap.put(txtDoppelkommando2Anzahl, lblDoppelkommando2);
        componentToLabelMap.put(txaBemerkung, lblBemerkung);
        componentToLabelMap.put(txfLeuchteLaufendenummer, lblLaufendenummer);
        componentToLabelMap.put(txfMontagefirma, lblMontagefirma);
        componentToLabelMap.put(txtLeuchteLeuchtennummer, lblLeuchteLeuchtennummer);
        componentToLabelMap.put(cbxRundsteuerempfaenger, lblRundsteuer);
        componentToLabelMap.put(txtSchaltstelle, lblSchaltstelle);
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
            cbxKennziffer.setSelectedItem(null);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isEditable  DOCUMENT ME!
     */
    public void setInheritedMastPropertiesEnabled(final boolean isEditable) {
        lblStandortangabe.setVisible(isEditable);
        lblVerrechnungseinheit.setVisible(isEditable);
        lblStadtbezirk.setVisible(isEditable);
        cbxStadtbezirk.setVisible(isEditable);
        txfStandortAngabe.setVisible(isEditable);
        cboVerrechnungseinheit.setVisible(isEditable);

        RendererTools.setEditable(cbxLeuchteStrassenschluessel, isEditable);
        RendererTools.setEditable(cbxLeuchteStrassenschluesselNr, isEditable);
        RendererTools.setEditable(cbxKennziffer, isEditable);
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
        RendererTools.setEditable(cbxEnergielieferant, isEditable);
        RendererTools.setEditable(txtSchaltstelle, isEditable);
        RendererTools.setEditable(cbxRundsteuerempfaenger, isEditable);
        RendererTools.setEditable(cbxUnterhalt, isEditable);
        RendererTools.setEditable(cboLeuchteZaehler, isEditable);
        RendererTools.setEditable(dapInbetriebnahme, isEditable);
        RendererTools.setEditable(cbxLeuchtentyp, isEditable);
        RendererTools.setEditable(cbxDoppelkommando1, isEditable);
        RendererTools.setEditable(cbxDoppelkommando2, isEditable);
        RendererTools.setEditable(txtDoppelkommando1Anzahl, isEditable);
        RendererTools.setEditable(txtDoppelkommando2Anzahl, isEditable);
        RendererTools.setEditable(txfMontagefirma, isEditable);
        RendererTools.setEditable(txfLeuchteLaufendenummer, isEditable);
        RendererTools.setEditable(txaBemerkung, isEditable);
        if ((belisBroker.getWorkbenchWidget() != null)
                    && !((belisBroker.getWorkbenchWidget().getSelectedTreeNode() != null)
                        && belisBroker.getWorkbenchWidget().isParentNodeMast(
                            belisBroker.getWorkbenchWidget().getSelectedTreeNode().getLastPathComponent()))) {
            RendererTools.setEditable(cbxKennziffer, isEditable);
            RendererTools.setEditable(cbxLeuchteStrassenschluessel, isEditable);
            RendererTools.setEditable(cbxLeuchteStrassenschluesselNr, isEditable);
        }
        RendererTools.setEditable(cbxStadtbezirk, isEditable);
        RendererTools.setEditable(txfStandortAngabe, isEditable);
        RendererTools.setEditable(cboVerrechnungseinheit, isEditable);
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
