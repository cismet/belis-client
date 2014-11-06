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

import java.text.ParseException;

import javax.swing.JLabel;

import de.cismet.belis.broker.BelisBroker;

import de.cismet.belis.gui.TimestampToDateConverter;

import de.cismet.belis.util.RendererTools;

import de.cismet.cids.custom.beans.belis2.AnlagengruppeCustomBean;
import de.cismet.cids.custom.beans.belis2.TdtaStandortMastCustomBean;
import de.cismet.cids.custom.beans.belis2.TkeyBezirkCustomBean;
import de.cismet.cids.custom.beans.belis2.TkeyKennzifferCustomBean;
import de.cismet.cids.custom.beans.belis2.TkeyKlassifizierungCustomBean;
import de.cismet.cids.custom.beans.belis2.TkeyMastartCustomBean;
import de.cismet.cids.custom.beans.belis2.TkeyMasttypCustomBean;
import de.cismet.cids.custom.beans.belis2.TkeyStrassenschluesselCustomBean;
import de.cismet.cids.custom.beans.belis2.TkeyUnterhMastCustomBean;

/**
 * DOCUMENT ME!
 *
 * @author   Gilles Baatz
 * @version  $Revision$, $Date$
 */
public class StandortPanel extends AbstractDetailWidgetPanel<TdtaStandortMastCustomBean> {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(StandortPanel.class);

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Mastschutz;
    private javax.swing.JCheckBox cboErdung;
    private javax.swing.JCheckBox cboVerrechnungseinheit;
    private javax.swing.JComboBox cbxAnlagengruppe;
    private javax.swing.JComboBox cbxKennziffer;
    private javax.swing.JComboBox cbxKlassifizierung;
    private javax.swing.JComboBox cbxMastart;
    private javax.swing.JComboBox cbxMasttyp;
    private javax.swing.JComboBox cbxStadtbezirk;
    private javax.swing.JComboBox cbxStrassenschluessel;
    private javax.swing.JComboBox cbxStrassenschluesselNr;
    private javax.swing.JComboBox cbxUnterhalt;
    private org.jdesktop.swingx.JXDatePicker dapElekPruefung;
    private org.jdesktop.swingx.JXDatePicker dapInbetriebnahme;
    private org.jdesktop.swingx.JXDatePicker dapLetzteAenderung;
    private org.jdesktop.swingx.JXDatePicker dapMastanstrich;
    private org.jdesktop.swingx.JXDatePicker dapMastschutz;
    private org.jdesktop.swingx.JXDatePicker dapNaechstesPruefdatum;
    private org.jdesktop.swingx.JXDatePicker dapRevision;
    private org.jdesktop.swingx.JXDatePicker dapStandsicherheitspruefung;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblAnbauten;
    private javax.swing.JLabel lblAnlagengruppe;
    private javax.swing.JLabel lblElekPruefung;
    private javax.swing.JLabel lblErdung;
    private javax.swing.JLabel lblGruendung;
    private javax.swing.JLabel lblHausnummer;
    private javax.swing.JLabel lblInbetriebnahme;
    private javax.swing.JLabel lblKenziffer;
    private javax.swing.JLabel lblKlassifizierung;
    private javax.swing.JLabel lblLaufendenummer;
    private javax.swing.JLabel lblLetzteAenderung;
    private javax.swing.JLabel lblMastanstrich;
    private javax.swing.JLabel lblMastart;
    private javax.swing.JLabel lblMastschutz;
    private javax.swing.JLabel lblMasttyp;
    private javax.swing.JLabel lblMontagefirma;
    private javax.swing.JLabel lblMonteur;
    private javax.swing.JLabel lblNaechstesPruefdatum;
    private javax.swing.JLabel lblRevision;
    private javax.swing.JLabel lblStadtbezirk;
    private javax.swing.JLabel lblStandort;
    private javax.swing.JLabel lblStandortAnstrichfarbe;
    private javax.swing.JLabel lblStandortBemerkung;
    private javax.swing.JLabel lblStandortangabe;
    private javax.swing.JLabel lblStandsicherheitspruefung;
    private javax.swing.JLabel lblStrassenschluessel;
    private javax.swing.JLabel lblUnterhalt;
    private javax.swing.JLabel lblVerfahren;
    private javax.swing.JLabel lblVerrechnungseinheit;
    private javax.swing.JPanel panContent;
    private javax.swing.JPanel panElekPruefung;
    private javax.swing.JPanel panInbetriebnahme;
    private javax.swing.JPanel panMastanstrich;
    private javax.swing.JPanel panSpacer0;
    private javax.swing.JPanel panSpacer1;
    private javax.swing.JPanel panSpacerBottom;
    private javax.swing.JPanel panSpacerBottom1;
    private javax.swing.JPanel panSpacerLeft;
    private javax.swing.JPanel panStandsicherheitspruefung;
    private javax.swing.JPanel panStrassenschluessel;
    private javax.swing.JScrollPane scpStandortBemerkung;
    private javax.swing.JTextArea txaStandortBemerkung;
    private javax.swing.JTextField txfAnbauten;
    private javax.swing.JTextField txfGruendung;
    private javax.swing.JTextField txfHausnummer;
    private javax.swing.JTextField txfLaufendenummer;
    private javax.swing.JTextField txfMontagefirma;
    private javax.swing.JTextField txfMonteur;
    private javax.swing.JTextField txfStandortAngabe;
    private javax.swing.JTextField txfStandortAnstrichfarbe;
    private javax.swing.JTextField txfVerfahren;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form StandortPanel.
     */
    public StandortPanel() {
        super("STANDORT_PANEL");
        initComponents();
        initComponentToLabelMap();
        initPanel();
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public JLabel getTabLabel() {
        return lblStandort;
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

        lblStandort = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        panContent = new javax.swing.JPanel();
        panSpacer0 = new javax.swing.JPanel();
        panSpacer1 = new javax.swing.JPanel();
        lblStrassenschluessel = new javax.swing.JLabel();
        panStrassenschluessel = new javax.swing.JPanel();
        cbxStrassenschluesselNr = BelisBroker.createStrassenschluesselNummerComboBox();
        cbxStrassenschluessel = BelisBroker.createKeyTableComboBox(TkeyStrassenschluesselCustomBean.TABLE);
        lblKenziffer = new javax.swing.JLabel();
        cbxKennziffer = BelisBroker.createKeyTableComboBox(TkeyKennzifferCustomBean.TABLE);
        lblLaufendenummer = new javax.swing.JLabel();
        txfLaufendenummer = new javax.swing.JTextField();
        lblHausnummer = new javax.swing.JLabel();
        txfHausnummer = new javax.swing.JTextField();
        lblStandortangabe = new javax.swing.JLabel();
        txfStandortAngabe = new javax.swing.JTextField();
        lblStadtbezirk = new javax.swing.JLabel();
        cbxStadtbezirk = BelisBroker.createKeyTableComboBox(TkeyBezirkCustomBean.TABLE);
        lblMastart = new javax.swing.JLabel();
        cbxMastart = BelisBroker.createKeyTableComboBox(TkeyMastartCustomBean.TABLE);
        lblMasttyp = new javax.swing.JLabel();
        cbxMasttyp = BelisBroker.createKeyTableComboBox(TkeyMasttypCustomBean.TABLE);
        lblKlassifizierung = new javax.swing.JLabel();
        cbxKlassifizierung = BelisBroker.createKeyTableComboBox(TkeyKlassifizierungCustomBean.TABLE);
        lblUnterhalt = new javax.swing.JLabel();
        cbxUnterhalt = BelisBroker.createKeyTableComboBox(TkeyUnterhMastCustomBean.TABLE);
        lblInbetriebnahme = new javax.swing.JLabel();
        panInbetriebnahme = new javax.swing.JPanel();
        dapInbetriebnahme = new org.jdesktop.swingx.JXDatePicker();
        lblVerrechnungseinheit = new javax.swing.JLabel();
        cboVerrechnungseinheit = new javax.swing.JCheckBox();
        lblMastanstrich = new javax.swing.JLabel();
        panMastanstrich = new javax.swing.JPanel();
        txfStandortAnstrichfarbe = new javax.swing.JTextField();
        lblStandortAnstrichfarbe = new javax.swing.JLabel();
        dapMastanstrich = new org.jdesktop.swingx.JXDatePicker();
        lblMontagefirma = new javax.swing.JLabel();
        txfMontagefirma = new javax.swing.JTextField();
        lblGruendung = new javax.swing.JLabel();
        txfGruendung = new javax.swing.JTextField();
        lblStandsicherheitspruefung = new javax.swing.JLabel();
        panStandsicherheitspruefung = new javax.swing.JPanel();
        lblNaechstesPruefdatum = new javax.swing.JLabel();
        dapNaechstesPruefdatum = new org.jdesktop.swingx.JXDatePicker();
        dapStandsicherheitspruefung = new org.jdesktop.swingx.JXDatePicker();
        lblVerfahren = new javax.swing.JLabel();
        txfVerfahren = new javax.swing.JTextField();
        lblElekPruefung = new javax.swing.JLabel();
        panElekPruefung = new javax.swing.JPanel();
        dapElekPruefung = new org.jdesktop.swingx.JXDatePicker();
        lblErdung = new javax.swing.JLabel();
        cboErdung = new javax.swing.JCheckBox();
        lblMonteur = new javax.swing.JLabel();
        txfMonteur = new javax.swing.JTextField();
        lblMastschutz = new javax.swing.JLabel();
        Mastschutz = new javax.swing.JPanel();
        dapRevision = new org.jdesktop.swingx.JXDatePicker();
        lblRevision = new javax.swing.JLabel();
        dapMastschutz = new org.jdesktop.swingx.JXDatePicker();
        lblAnlagengruppe = new javax.swing.JLabel();
        cbxAnlagengruppe = BelisBroker.createKeyTableComboBox(AnlagengruppeCustomBean.TABLE);
        lblAnbauten = new javax.swing.JLabel();
        txfAnbauten = new javax.swing.JTextField();
        lblStandortBemerkung = new javax.swing.JLabel();
        scpStandortBemerkung = new javax.swing.JScrollPane();
        txaStandortBemerkung = new javax.swing.JTextArea();
        lblLetzteAenderung = new javax.swing.JLabel();
        dapLetzteAenderung = new org.jdesktop.swingx.JXDatePicker();
        panSpacerBottom = new javax.swing.JPanel();
        panSpacerLeft = new javax.swing.JPanel();
        panSpacerBottom1 = new javax.swing.JPanel();

        lblStandort.setFont(new java.awt.Font("DejaVu Sans", 1, 13));                       // NOI18N
        lblStandort.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/16/standort.png"))); // NOI18N
        lblStandort.setText("Standort");                                                    // NOI18N

        setLayout(new java.awt.GridBagLayout());

        jPanel1.setLayout(new java.awt.GridBagLayout());

        panContent.setLayout(new java.awt.GridBagLayout());

        panSpacer0.setMinimumSize(DIMENSION_KEYSPACER);
        panSpacer0.setPreferredSize(DIMENSION_KEYSPACER);

        final javax.swing.GroupLayout panSpacer0Layout = new javax.swing.GroupLayout(panSpacer0);
        panSpacer0.setLayout(panSpacer0Layout);
        panSpacer0Layout.setHorizontalGroup(
            panSpacer0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                160,
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

        final javax.swing.GroupLayout panSpacer1Layout = new javax.swing.GroupLayout(panSpacer1);
        panSpacer1.setLayout(panSpacer1Layout);
        panSpacer1Layout.setHorizontalGroup(
            panSpacer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                422,
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

        cbxStrassenschluesselNr.setEnabled(false);
        cbxStrassenschluesselNr.setMinimumSize(new java.awt.Dimension(80, 27));
        cbxStrassenschluesselNr.setPreferredSize(new java.awt.Dimension(80, 27));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.strassenschluessel}"),
                cbxStrassenschluesselNr,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"),
                "strassenschluesselnr"); // NOI18N
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        cbxStrassenschluesselNr.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbxStrassenschluesselNrActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStrassenschluessel.add(cbxStrassenschluesselNr, gridBagConstraints);

        cbxStrassenschluessel.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.strassenschluessel}"),
                cbxStrassenschluessel,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        binding.setValidator(new NotNullValidator("Straßenschlüssel"));
        bindingGroup.addBinding(binding);

        cbxStrassenschluessel.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbxStrassenschluesselActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStrassenschluessel.add(cbxStrassenschluessel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panContent.add(panStrassenschluessel, gridBagConstraints);

        lblKenziffer.setText("Kennziffer:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblKenziffer, gridBagConstraints);

        cbxKennziffer.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.kennziffer}"),
                cbxKennziffer,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
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

        txfLaufendenummer.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.laufendeNummer}"),
                txfLaufendenummer,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txfLaufendenummer, gridBagConstraints);

        lblHausnummer.setText("Hausnummer:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblHausnummer, gridBagConstraints);

        txfHausnummer.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.hausnummer}"),
                txfHausnummer,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setValidator(new StringMaxLengthValidator(5));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txfHausnummer, gridBagConstraints);

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
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.standortangabe}"),
                txfStandortAngabe,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setValidator(new StringMaxLengthValidator());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txfStandortAngabe, gridBagConstraints);

        lblStadtbezirk.setText("Stadtbezirk:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblStadtbezirk, gridBagConstraints);

        cbxStadtbezirk.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.stadtbezirk}"),
                cbxStadtbezirk,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(cbxStadtbezirk, gridBagConstraints);

        lblMastart.setText("Mastart:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblMastart, gridBagConstraints);

        cbxMastart.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.mastart}"),
                cbxMastart,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(cbxMastart, gridBagConstraints);

        lblMasttyp.setText("Mast Typ:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblMasttyp, gridBagConstraints);

        cbxMasttyp.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.masttyp}"),
                cbxMasttyp,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(cbxMasttyp, gridBagConstraints);

        lblKlassifizierung.setText("Klassifizierung:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblKlassifizierung, gridBagConstraints);

        cbxKlassifizierung.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.klassifizierung}"),
                cbxKlassifizierung,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(cbxKlassifizierung, gridBagConstraints);

        lblUnterhalt.setText("Unterhalt:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblUnterhalt, gridBagConstraints);

        cbxUnterhalt.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.unterhaltspflichtMast}"),
                cbxUnterhalt,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(cbxUnterhalt, gridBagConstraints);

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
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.inbetriebnahme_mast}"),
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

        lblVerrechnungseinheit.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblVerrechnungseinheit.setText("V-Einheit:");                 // NOI18N
        lblVerrechnungseinheit.setToolTipText("Verrechnungseinheit"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInbetriebnahme.add(lblVerrechnungseinheit, gridBagConstraints);

        cboVerrechnungseinheit.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.verrechnungseinheit}"),
                cboVerrechnungseinheit,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        binding.setSourceUnreadableValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInbetriebnahme.add(cboVerrechnungseinheit, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panContent.add(panInbetriebnahme, gridBagConstraints);

        lblMastanstrich.setText("Mastanstrich:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblMastanstrich, gridBagConstraints);

        panMastanstrich.setLayout(new java.awt.GridBagLayout());

        txfStandortAnstrichfarbe.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.anstrichfarbe}"),
                txfStandortAnstrichfarbe,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMastanstrich.add(txfStandortAnstrichfarbe, gridBagConstraints);

        lblStandortAnstrichfarbe.setText("Anstrichfarbe:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panMastanstrich.add(lblStandortAnstrichfarbe, gridBagConstraints);

        dapMastanstrich.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.mastanstrich}"),
                dapMastanstrich,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setConverter(new TimestampToDateConverter());
        binding.setValidator(new DateValidator());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMastanstrich.add(dapMastanstrich, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panContent.add(panMastanstrich, gridBagConstraints);

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
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.montagefirma}"),
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

        lblGruendung.setText("Gründung:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblGruendung, gridBagConstraints);

        txfGruendung.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.gruendung}"),
                txfGruendung,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txfGruendung, gridBagConstraints);

        lblStandsicherheitspruefung.setText("Standsicherheitsprfg:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblStandsicherheitspruefung, gridBagConstraints);

        panStandsicherheitspruefung.setLayout(new java.awt.GridBagLayout());

        lblNaechstesPruefdatum.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblNaechstesPruefdatum.setText("Nächstes Prüfdatum:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panStandsicherheitspruefung.add(lblNaechstesPruefdatum, gridBagConstraints);

        dapNaechstesPruefdatum.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.naechstes_pruefdatum}"),
                dapNaechstesPruefdatum,
                org.jdesktop.beansbinding.BeanProperty.create("date"),
                "");
        binding.setConverter(new TimestampToDateConverter());
        binding.setValidator(new DateValidator());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStandsicherheitspruefung.add(dapNaechstesPruefdatum, gridBagConstraints);

        dapStandsicherheitspruefung.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.standsicherheitspruefung}"),
                dapStandsicherheitspruefung,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setConverter(new TimestampToDateConverter());
        binding.setValidator(new DateValidator());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStandsicherheitspruefung.add(dapStandsicherheitspruefung, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panContent.add(panStandsicherheitspruefung, gridBagConstraints);

        lblVerfahren.setText("Verfahren:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblVerfahren, gridBagConstraints);

        txfVerfahren.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.verfahren}"),
                txfVerfahren,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txfVerfahren, gridBagConstraints);

        lblElekPruefung.setText("Elektrische Prüfung:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblElekPruefung, gridBagConstraints);

        panElekPruefung.setLayout(new java.awt.GridBagLayout());

        dapElekPruefung.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.elek_pruefung}"),
                dapElekPruefung,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setConverter(new TimestampToDateConverter());
        binding.setValidator(new DateValidator());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panElekPruefung.add(dapElekPruefung, gridBagConstraints);

        lblErdung.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblErdung.setText("Erdung i.O.:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panElekPruefung.add(lblErdung, gridBagConstraints);

        cboErdung.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.erdung}"),
                cboErdung,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        binding.setSourceUnreadableValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panElekPruefung.add(cboErdung, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panContent.add(panElekPruefung, gridBagConstraints);

        lblMonteur.setText("Monteur:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblMonteur, gridBagConstraints);

        txfMonteur.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.monteur}"),
                txfMonteur,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txfMonteur, gridBagConstraints);

        lblMastschutz.setText("Mastschutz:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblMastschutz, gridBagConstraints);

        Mastschutz.setLayout(new java.awt.GridBagLayout());

        dapRevision.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.revision}"),
                dapRevision,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setConverter(new TimestampToDateConverter());
        binding.setValidator(new DateValidator());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        Mastschutz.add(dapRevision, gridBagConstraints);

        lblRevision.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblRevision.setText("Revision:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        Mastschutz.add(lblRevision, gridBagConstraints);

        dapMastschutz.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.mastschutz}"),
                dapMastschutz,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setConverter(new TimestampToDateConverter());
        binding.setValidator(new DateValidator());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        Mastschutz.add(dapMastschutz, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panContent.add(Mastschutz, gridBagConstraints);

        lblAnlagengruppe.setText("Anlagengruppe:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblAnlagengruppe, gridBagConstraints);

        cbxAnlagengruppe.setEnabled(false);
        cbxAnlagengruppe.setMinimumSize(new java.awt.Dimension(350, 27));
        cbxAnlagengruppe.setName(""); // NOI18N
        cbxAnlagengruppe.setPreferredSize(new java.awt.Dimension(350, 27));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.anlagengruppe}"),
                cbxAnlagengruppe,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(cbxAnlagengruppe, gridBagConstraints);

        lblAnbauten.setText("Anbauten:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblAnbauten, gridBagConstraints);

        txfAnbauten.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.anbauten}"),
                txfAnbauten,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txfAnbauten, gridBagConstraints);

        lblStandortBemerkung.setText("Bemerkung:"); // NOI18N
        lblStandortBemerkung.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblStandortBemerkung, gridBagConstraints);

        scpStandortBemerkung.setEnabled(false);
        scpStandortBemerkung.setMinimumSize(new java.awt.Dimension(100, 23));

        txaStandortBemerkung.setColumns(20);
        txaStandortBemerkung.setRows(5);
        txaStandortBemerkung.setMinimumSize(new java.awt.Dimension(260, 17));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.bemerkungen}"),
                txaStandortBemerkung,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setValidator(new StringMaxLengthValidator());
        bindingGroup.addBinding(binding);

        scpStandortBemerkung.setViewportView(txaStandortBemerkung);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(scpStandortBemerkung, gridBagConstraints);

        lblLetzteAenderung.setText("Letze Änderung:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblLetzteAenderung, gridBagConstraints);

        dapLetzteAenderung.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.letzte_aenderung}"),
                dapLetzteAenderung,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setConverter(new TimestampToDateConverter());
        binding.setValidator(new DateValidator());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(dapLetzteAenderung, gridBagConstraints);

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
                582,
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

        final javax.swing.GroupLayout panSpacerLeftLayout = new javax.swing.GroupLayout(panSpacerLeft);
        panSpacerLeft.setLayout(panSpacerLeftLayout);
        panSpacerLeftLayout.setHorizontalGroup(
            panSpacerLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                0,
                Short.MAX_VALUE));
        panSpacerLeftLayout.setVerticalGroup(
            panSpacerLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                0,
                Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        add(panSpacerLeft, gridBagConstraints);

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

    /**
     * DOCUMENT ME!
     */
    @Override
    final void initComponentToLabelMap() {
        componentToLabelMap.put(cboVerrechnungseinheit, lblVerrechnungseinheit);
        componentToLabelMap.put(cbxKennziffer, lblKenziffer);
        componentToLabelMap.put(cbxKlassifizierung, lblKlassifizierung);
        componentToLabelMap.put(cbxMastart, lblMastart);
        componentToLabelMap.put(cbxMasttyp, lblMasttyp);
        componentToLabelMap.put(cbxStadtbezirk, lblStadtbezirk);
        componentToLabelMap.put(cbxStrassenschluessel, lblStrassenschluessel);
        componentToLabelMap.put(cbxStrassenschluesselNr, lblStrassenschluessel);
        componentToLabelMap.put(cbxUnterhalt, lblUnterhalt);
        componentToLabelMap.put(dapInbetriebnahme, lblInbetriebnahme);
        componentToLabelMap.put(dapLetzteAenderung, lblLetzteAenderung);
        componentToLabelMap.put(dapMastanstrich, lblMastanstrich);
        componentToLabelMap.put(dapMastschutz, lblMastschutz);
        componentToLabelMap.put(txaStandortBemerkung, lblStandortBemerkung);
        componentToLabelMap.put(txfHausnummer, lblHausnummer);
        componentToLabelMap.put(txfLaufendenummer, lblLaufendenummer);
        componentToLabelMap.put(txfMontagefirma, lblMontagefirma);
        componentToLabelMap.put(txfStandortAngabe, lblStandortangabe);
        componentToLabelMap.put(dapElekPruefung, lblElekPruefung);
        componentToLabelMap.put(dapNaechstesPruefdatum, lblNaechstesPruefdatum);
        componentToLabelMap.put(dapRevision, lblRevision);
        componentToLabelMap.put(dapStandsicherheitspruefung, lblStandsicherheitspruefung);
        componentToLabelMap.put(txfStandortAnstrichfarbe, lblStandortAnstrichfarbe);
        componentToLabelMap.put(txfGruendung, lblGruendung);
        componentToLabelMap.put(txfLaufendenummer, lblLaufendenummer);
        componentToLabelMap.put(txfMontagefirma, lblMontagefirma);
        componentToLabelMap.put(txfMonteur, lblMonteur);
        componentToLabelMap.put(txfVerfahren, lblVerfahren);
        componentToLabelMap.put(cbxAnlagengruppe, lblAnlagengruppe);
        componentToLabelMap.put(txfAnbauten, lblAnbauten);
    }

    /**
     * DOCUMENT ME!
     */
    @Override
    final void initPanel() {
        bindingGroup.addBindingListener(new PanelBindingListener());

        AutoCompleteDecorator.decorate(cbxStrassenschluesselNr, new ObjectToPkConverter("pk"));
        AutoCompleteDecorator.decorate(cbxStrassenschluessel, new ObjectToKeyStringConverter());
        AutoCompleteDecorator.decorate(cbxMasttyp, new ObjectToKeyStringConverter());
    }

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbxStrassenschluesselActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbxStrassenschluesselActionPerformed
        try {
            if (!isTriggerd) {
                isTriggerd = true;
                cbxStrassenschluesselNr.setSelectedItem(cbxStrassenschluessel.getSelectedItem());
            }
        } catch (Exception ex) {
            LOG.warn("failuire while strassenschluessel ", ex);
        } finally {
            isTriggerd = false;
        }
    }                                                                                         //GEN-LAST:event_cbxStrassenschluesselActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbxStrassenschluesselNrActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbxStrassenschluesselNrActionPerformed
        try {
            if (!isTriggerd) {
                isTriggerd = true;
                cbxStrassenschluessel.setSelectedItem(cbxStrassenschluesselNr.getSelectedItem());
            }
        } catch (Exception ex) {
            LOG.warn("failuire while updating strassenschluessel ", ex);
        } finally {
            isTriggerd = false;
        }
    }                                                                                           //GEN-LAST:event_cbxStrassenschluesselNrActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  isEditable  DOCUMENT ME!
     */
    @Override
    public void setPanelEditable(final boolean isEditable) {
        RendererTools.setEditable(cbxStrassenschluessel, isEditable);
        RendererTools.setEditable(cbxStrassenschluesselNr, isEditable);
        RendererTools.setEditable(cbxKennziffer, isEditable);
        RendererTools.setEditable(cbxStadtbezirk, isEditable);
        RendererTools.setEditable(txfStandortAngabe, isEditable);
        RendererTools.setEditable(txfHausnummer, isEditable);
        RendererTools.setEditable(cbxMastart, isEditable);
        RendererTools.setEditable(cbxMasttyp, isEditable);
        RendererTools.setEditable(cbxUnterhalt, isEditable);
        RendererTools.setEditable(cbxKlassifizierung, isEditable);
        RendererTools.setEditable(dapInbetriebnahme, isEditable);
        RendererTools.setEditable(dapLetzteAenderung, isEditable);
        RendererTools.setEditable(dapMastanstrich, isEditable);
        RendererTools.setEditable(dapMastschutz, isEditable);
        RendererTools.setEditable(txfMontagefirma, isEditable);
        RendererTools.setEditable(txaStandortBemerkung, isEditable);
        RendererTools.setEditable(cboVerrechnungseinheit, isEditable);
        RendererTools.setEditable(dapElekPruefung, isEditable);
        RendererTools.setEditable(dapNaechstesPruefdatum, isEditable);
        RendererTools.setEditable(dapRevision, isEditable);
        RendererTools.setEditable(dapStandsicherheitspruefung, isEditable);
        RendererTools.setEditable(txfStandortAnstrichfarbe, isEditable);
        RendererTools.setEditable(txfGruendung, isEditable);
        RendererTools.setEditable(txfLaufendenummer, isEditable);
        RendererTools.setEditable(txfMontagefirma, isEditable);
        RendererTools.setEditable(txfMonteur, isEditable);
        RendererTools.setEditable(txfVerfahren, isEditable);
        RendererTools.setEditable(txfAnbauten, isEditable);
        RendererTools.setEditable(cboErdung, isEditable);
        RendererTools.setEditable(cbxAnlagengruppe, isEditable);
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
            dapInbetriebnahme.getEditor().commitEdit();
        } catch (ParseException ex) {
            LOG.warn("Error while commiting edits: " + ex);
        }
        try {
            dapLetzteAenderung.getEditor().commitEdit();
        } catch (ParseException ex) {
            LOG.warn("Error while commiting edits: " + ex);
        }
        try {
            dapMastanstrich.getEditor().commitEdit();
        } catch (ParseException ex) {
            LOG.warn("Error while commiting edits: " + ex);
        }
        try {
            dapMastschutz.getEditor().commitEdit();
        } catch (ParseException ex) {
            LOG.warn("Error while commiting edits: " + ex);
        }
        try {
            dapElekPruefung.getEditor().commitEdit();
        } catch (ParseException ex) {
            LOG.warn("Error while commiting edits: " + ex);
        }
        try {
            dapStandsicherheitspruefung.getEditor().commitEdit();
        } catch (ParseException ex) {
            LOG.warn("Error while commiting edits: " + ex);
        }
        try {
            dapRevision.getEditor().commitEdit();
        } catch (ParseException ex) {
            LOG.warn("Error while commiting edits: " + ex);
        }
        try {
            dapNaechstesPruefdatum.getEditor().commitEdit();
        } catch (ParseException ex) {
            LOG.warn("Error while commiting edits: " + ex);
        }
    }

    @Override
    public void setElementsNull() {
        if (currentEntity.getStrassenschluessel() == null) {
            cbxStrassenschluessel.setSelectedItem(null);
            cbxStrassenschluesselNr.setSelectedItem(null);
        }
        if (currentEntity.getKennziffer() == null) {
            cbxKennziffer.setSelectedItem(null);
        }
    }

    @Override
    protected BindingGroup getBindingGroup() {
        return bindingGroup;
    }
}
