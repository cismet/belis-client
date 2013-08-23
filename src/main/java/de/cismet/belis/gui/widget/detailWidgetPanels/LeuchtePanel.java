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

import de.cismet.belisEE.exception.ActionNotSuccessfulException;

import de.cismet.cids.custom.beans.belis.TdtaLeuchtenCustomBean;
import de.cismet.cids.custom.beans.belis.TdtaStandortMastCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyBezirkCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyDoppelkommandoCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyEnergielieferantCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyKennzifferCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyLeuchtentypCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyStrassenschluesselCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyUnterhLeuchteCustomBean;

/**
 * DOCUMENT ME!
 *
 * @author   Gilles Baatz
 * @version  $Revision$, $Date$
 */
public class LeuchtePanel extends AbstractDetailWidgetPanel<TdtaLeuchtenCustomBean> {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(LeuchtePanel.class);

    private static LeuchtePanel instance = null;

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
    private org.jdesktop.swingx.JXDatePicker dapLeuchteInbetriebnahme;
    private javax.swing.JPanel jPanel2;
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
    private javax.swing.JPanel panContent;
    private javax.swing.JScrollPane scpLeuchteBemerkung;
    private javax.swing.JSpinner sprLeuchteDoppelkommando1Anzahl;
    private javax.swing.JSpinner sprLeuchteDoppelkommando2Anzahl;
    private javax.swing.JTextArea txaLeuchteBemerkung;
    private javax.swing.JTextField txfLeuchteLaufendenummer;
    private javax.swing.JTextField txfLeuchteMontagefirma;
    private javax.swing.JTextField txfLeuchteStandortAngabe;
    private javax.swing.JTextField txtLeuchteLeuchtennummer;
    private javax.swing.JTextField txtLeuchteRundsteuer;
    private javax.swing.JTextField txtLeuchteSchaltstelle;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form LeuchtePanel.
     */
    private LeuchtePanel() {
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
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static LeuchtePanel getInstance() {
        if (instance == null) {
            synchronized (LeuchtePanel.class) {
                if (instance == null) {
                    instance = new LeuchtePanel();
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
        java.awt.GridBagConstraints gridBagConstraints;
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        lblLeuchte = new javax.swing.JLabel();
        panContent = new javax.swing.JPanel();
        lblLeuchteLeuchtennummer = new javax.swing.JLabel();
        txtLeuchteLeuchtennummer = new javax.swing.JTextField();
        lblLeuchteEnergielieferant = new javax.swing.JLabel();
        cbxLeuchteEnergielieferant = new javax.swing.JComboBox();
        lblLeuchteSchaltstelle = new javax.swing.JLabel();
        txtLeuchteSchaltstelle = new javax.swing.JTextField();
        lblLeuchteRundsteuer = new javax.swing.JLabel();
        txtLeuchteRundsteuer = new javax.swing.JTextField();
        lblLeuchteLeuchtentyp = new javax.swing.JLabel();
        lblLeuchteUnterhalt = new javax.swing.JLabel();
        cbxLeuchteUnterhalt = new javax.swing.JComboBox();
        lblLeuchteZaehler = new javax.swing.JLabel();
        cboLeuchteZaehler = new javax.swing.JCheckBox();
        lblLeuchteInbetriebnahme = new javax.swing.JLabel();
        dapLeuchteInbetriebnahme = new org.jdesktop.swingx.JXDatePicker();
        lblLeuchteStrassenschluessel = new javax.swing.JLabel();
        lblLeuchteLaufendenummer = new javax.swing.JLabel();
        txfLeuchteLaufendenummer = new javax.swing.JTextField();
        cbxLeuchteKennziffer = new javax.swing.JComboBox();
        lblLeuchteKenziffer = new javax.swing.JLabel();
        cbxLeuchteLeuchtentyp = new javax.swing.JComboBox();
        lblLeuchteDoppelkommando1 = new javax.swing.JLabel();
        lblLeuchteDoppelkommando2 = new javax.swing.JLabel();
        sprLeuchteDoppelkommando1Anzahl = new javax.swing.JSpinner();
        sprLeuchteDoppelkommando2Anzahl = new javax.swing.JSpinner();
        cbxLeuchteDoppelkommando1 = new javax.swing.JComboBox();
        cbxLeuchteDoppelkommando2 = new javax.swing.JComboBox();
        lblLeuchteMontagefirma = new javax.swing.JLabel();
        txfLeuchteMontagefirma = new javax.swing.JTextField();
        lblLeuchteBemerkung = new javax.swing.JLabel();
        scpLeuchteBemerkung = new javax.swing.JScrollPane();
        txaLeuchteBemerkung = new javax.swing.JTextArea();
        cbxLeuchteStrassenschluesselNr = new javax.swing.JComboBox();
        cbxLeuchteStrassenschluessel = new javax.swing.JComboBox();
        lblLeuchteStadtbezirk = new javax.swing.JLabel();
        cbxLeuchteStadtbezirk = new javax.swing.JComboBox();
        lblLeuchteStandortangabe = new javax.swing.JLabel();
        txfLeuchteStandortAngabe = new javax.swing.JTextField();
        lblLeuchteVerrechnungseinheit = new javax.swing.JLabel();
        cboLeuchteVerrechnungseinheit = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();

        lblLeuchte.setFont(new java.awt.Font("DejaVu Sans", 1, 13));                       // NOI18N
        lblLeuchte.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/22/leuchte.png"))); // NOI18N
        lblLeuchte.setText("Leuchte");                                                     // NOI18N

        setLayout(new java.awt.GridBagLayout());

        panContent.setLayout(new java.awt.GridBagLayout());

        lblLeuchteLeuchtennummer.setText("Leuchtennummer:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 36;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 12, 0, 0);
        panContent.add(lblLeuchteLeuchtennummer, gridBagConstraints);

        txtLeuchteLeuchtennummer.setEnabled(false);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.leuchtennummer}"),
                txtLeuchteLeuchtennummer,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setValidator(new LeuchtennummerValidator());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 241;
        gridBagConstraints.ipady = -3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 12, 0, 12);
        panContent.add(txtLeuchteLeuchtennummer, gridBagConstraints);

        lblLeuchteEnergielieferant.setText("Energielieferant:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 44;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 12, 0, 0);
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
                    } else if (value instanceof de.cismet.cids.custom.beans.belis.TkeyEnergielieferantCustomBean) {
                        final de.cismet.cids.custom.beans.belis.TkeyEnergielieferantCustomBean el =
                            (de.cismet.cids.custom.beans.belis.TkeyEnergielieferantCustomBean)value;
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
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 210;
        gridBagConstraints.ipady = -3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 12, 0, 12);
        panContent.add(cbxLeuchteEnergielieferant, gridBagConstraints);

        lblLeuchteSchaltstelle.setText("Schaltstelle:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 76;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 12, 0, 0);
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
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 241;
        gridBagConstraints.ipady = -3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 12, 0, 12);
        panContent.add(txtLeuchteSchaltstelle, gridBagConstraints);

        lblLeuchteRundsteuer.setText("Rundsteuerempfänger:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 12, 0, 0);
        panContent.add(lblLeuchteRundsteuer, gridBagConstraints);

        txtLeuchteRundsteuer.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.rundsteuerempfaenger}"),
                txtLeuchteRundsteuer,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setValidator(new StringMaxLengthValidator());
        bindingGroup.addBinding(binding);

        txtLeuchteRundsteuer.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    txtLeuchteRundsteuerActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 241;
        gridBagConstraints.ipady = -3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 12, 0, 12);
        panContent.add(txtLeuchteRundsteuer, gridBagConstraints);

        lblLeuchteLeuchtentyp.setText("Leuchtentyp:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 70;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 12, 0, 0);
        panContent.add(lblLeuchteLeuchtentyp, gridBagConstraints);

        lblLeuchteUnterhalt.setText("Unterhalt Leuchte:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 28;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 12, 0, 0);
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
                    } else if (value instanceof de.cismet.cids.custom.beans.belis.TkeyUnterhLeuchteCustomBean) {
                        final de.cismet.cids.custom.beans.belis.TkeyUnterhLeuchteCustomBean ul =
                            (de.cismet.cids.custom.beans.belis.TkeyUnterhLeuchteCustomBean)value;
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
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 210;
        gridBagConstraints.ipady = -3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 12, 0, 12);
        panContent.add(cbxLeuchteUnterhalt, gridBagConstraints);

        lblLeuchteZaehler.setText("Zähler vorhanden:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 35;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(13, 12, 0, 0);
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
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 229;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 12, 0, 12);
        panContent.add(cboLeuchteZaehler, gridBagConstraints);

        lblLeuchteInbetriebnahme.setText("Inbetriebnahme:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 48;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(9, 12, 0, 0);
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
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 121;
        gridBagConstraints.ipady = -5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 12, 0, 12);
        panContent.add(dapLeuchteInbetriebnahme, gridBagConstraints);

        lblLeuchteStrassenschluessel.setText("Straßenschlüssel:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 39;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(14, 12, 0, 0);
        panContent.add(lblLeuchteStrassenschluessel, gridBagConstraints);

        lblLeuchteLaufendenummer.setText("Laufende Nr.:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 68;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 12, 0, 0);
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
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 241;
        gridBagConstraints.ipady = -3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 12, 0, 12);
        panContent.add(txfLeuchteLaufendenummer, gridBagConstraints);

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
                    } else if (value instanceof de.cismet.cids.custom.beans.belis.TkeyKennzifferCustomBean) {
                        final de.cismet.cids.custom.beans.belis.TkeyKennzifferCustomBean kzf =
                            (de.cismet.cids.custom.beans.belis.TkeyKennzifferCustomBean)value;
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
        binding.setValidator(new NotNullValidator("de.cismet.cids.custom.beans.belis.TkeyKennzifferCustomBean"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 210;
        gridBagConstraints.ipady = -3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 12, 0, 12);
        panContent.add(cbxLeuchteKennziffer, gridBagConstraints);

        lblLeuchteKenziffer.setText("Kennziffer:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 85;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 12, 0, 0);
        panContent.add(lblLeuchteKenziffer, gridBagConstraints);

        cbxLeuchteLeuchtentyp.setEnabled(false);
        cbxLeuchteLeuchtentyp.setRenderer(new DefaultListCellRenderer() {

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
                    } else if (value instanceof de.cismet.cids.custom.beans.belis.TkeyLeuchtentypCustomBean) {
                        final de.cismet.cids.custom.beans.belis.TkeyLeuchtentypCustomBean lt =
                            (de.cismet.cids.custom.beans.belis.TkeyLeuchtentypCustomBean)value;
                        setText(lt.getLeuchtentyp());
                    }
                    return this;
                }
            });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.leuchtentyp}"),
                cbxLeuchteLeuchtentyp,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 210;
        gridBagConstraints.ipady = -6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 12, 0, 12);
        panContent.add(cbxLeuchteLeuchtentyp, gridBagConstraints);

        lblLeuchteDoppelkommando1.setText("Doppelkomando 1:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 32;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 12, 0, 0);
        panContent.add(lblLeuchteDoppelkommando1, gridBagConstraints);

        lblLeuchteDoppelkommando2.setText("Doppelkomando 2:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 32;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 12, 0, 0);
        panContent.add(lblLeuchteDoppelkommando2, gridBagConstraints);

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
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 7;
        gridBagConstraints.ipady = -4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 12);
        panContent.add(sprLeuchteDoppelkommando1Anzahl, gridBagConstraints);

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
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 7;
        gridBagConstraints.ipady = -4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 12);
        panContent.add(sprLeuchteDoppelkommando2Anzahl, gridBagConstraints);

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
                    } else if (value instanceof de.cismet.cids.custom.beans.belis.TkeyDoppelkommandoCustomBean) {
                        final de.cismet.cids.custom.beans.belis.TkeyDoppelkommandoCustomBean dk =
                            (de.cismet.cids.custom.beans.belis.TkeyDoppelkommandoCustomBean)value;
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
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 157;
        gridBagConstraints.ipady = -3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 12, 0, 0);
        panContent.add(cbxLeuchteDoppelkommando1, gridBagConstraints);

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
                    } else if (value instanceof de.cismet.cids.custom.beans.belis.TkeyDoppelkommandoCustomBean) {
                        final de.cismet.cids.custom.beans.belis.TkeyDoppelkommandoCustomBean dk =
                            (de.cismet.cids.custom.beans.belis.TkeyDoppelkommandoCustomBean)value;
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
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 157;
        gridBagConstraints.ipady = -3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 12, 0, 0);
        panContent.add(cbxLeuchteDoppelkommando2, gridBagConstraints);

        lblLeuchteMontagefirma.setText("Montagefirma:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 26;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 61;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(9, 12, 0, 0);
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
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 26;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 241;
        gridBagConstraints.ipady = -5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 12, 0, 12);
        panContent.add(txfLeuchteMontagefirma, gridBagConstraints);

        lblLeuchteBemerkung.setText("Bemerkung:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 28;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 79;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 0);
        panContent.add(lblLeuchteBemerkung, gridBagConstraints);

        txaLeuchteBemerkung.setColumns(20);
        txaLeuchteBemerkung.setRows(5);
        txaLeuchteBemerkung.setEnabled(false);
        txaLeuchteBemerkung.setPreferredSize(new java.awt.Dimension(50, 50));

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
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 28;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 232;
        gridBagConstraints.ipady = 82;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 12);
        panContent.add(scpLeuchteBemerkung, gridBagConstraints);

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
                    } else if (value instanceof de.cismet.cids.custom.beans.belis.TkeyStrassenschluesselCustomBean) {
                        final de.cismet.cids.custom.beans.belis.TkeyStrassenschluesselCustomBean ss =
                            (de.cismet.cids.custom.beans.belis.TkeyStrassenschluesselCustomBean)value;
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
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 49;
        gridBagConstraints.ipady = -8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(13, 12, 0, 0);
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
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 114;
        gridBagConstraints.ipady = -6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 6, 0, 12);
        panContent.add(cbxLeuchteStrassenschluessel, gridBagConstraints);

        lblLeuchteStadtbezirk.setText("Stadtbezik:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 32;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 12, 0, 0);
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
                    } else if (value instanceof de.cismet.cids.custom.beans.belis.TkeyBezirkCustomBean) {
                        final de.cismet.cids.custom.beans.belis.TkeyBezirkCustomBean sb =
                            (de.cismet.cids.custom.beans.belis.TkeyBezirkCustomBean)value;
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
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 32;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 217;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 5, 0, 12);
        panContent.add(cbxLeuchteStadtbezirk, gridBagConstraints);

        lblLeuchteStandortangabe.setText("Standortangabe:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 30;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 12, 0, 0);
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
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 30;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 248;
        gridBagConstraints.ipady = -5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 5, 0, 12);
        panContent.add(txfLeuchteStandortAngabe, gridBagConstraints);

        lblLeuchteVerrechnungseinheit.setText("V-Einheit:");                 // NOI18N
        lblLeuchteVerrechnungseinheit.setToolTipText("Verrechnungseinheit"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 34;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 12, 0, 0);
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
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 34;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 236;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 5, 12, 12);
        panContent.add(cboLeuchteVerrechnungseinheit, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(panContent, gridBagConstraints);

        final javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                477,
                Short.MAX_VALUE));
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                68,
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
    BindingGroup getBindingGroup() {
        return bindingGroup;
    }

    @Override
    final void initPanel() {
        bindingGroup.addBindingListener(new PanelBindingListener());
        fillComboBoxWithKeyTableValuesAndAddListener(
            cbxLeuchteStrassenschluessel,
            TkeyStrassenschluesselCustomBean.TABLE);
        cbxLeuchteStrassenschluessel.setSelectedItem(null);
        AutoCompleteDecorator.decorate(cbxLeuchteStrassenschluessel, new ObjectToKeyStringConverter());

        fillComboBoxWithKeyTableValuesAndAddListener(
            cbxLeuchteStrassenschluesselNr,
            TkeyStrassenschluesselCustomBean.TABLE,
            true);
        cbxLeuchteStrassenschluesselNr.setSelectedItem(null);
        AutoCompleteDecorator.decorate(cbxLeuchteStrassenschluesselNr, new ObjectToPkConverter("pk"));

        fillComboBoxWithKeyTableValuesAndAddListener(cbxLeuchteKennziffer, TkeyKennzifferCustomBean.TABLE);
        cbxLeuchteKennziffer.setSelectedItem(null);

        fillComboBoxWithKeyTableValuesAndAddListener(cbxLeuchteEnergielieferant, TkeyEnergielieferantCustomBean.TABLE);

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

        fillComboBoxWithKeyTableValuesAndAddListener(cbxLeuchteLeuchtentyp, TkeyLeuchtentypCustomBean.TABLE);
        cbxLeuchteLeuchtentyp.setSelectedItem(null);

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
        cbxLeuchteDoppelkommando1.setSelectedItem(null);

        fillComboBoxWithKeyTableValuesAndAddListener(cbxLeuchteDoppelkommando2, TkeyDoppelkommandoCustomBean.TABLE);
        cbxLeuchteDoppelkommando2.setSelectedItem(null);
        cbxLeuchteUnterhalt.setSelectedItem(null);
        cbxLeuchteEnergielieferant.setSelectedItem(null);

        // Virtual properties
        fillComboBoxWithKeyTableValuesAndAddListener(cbxLeuchteStadtbezirk, TkeyBezirkCustomBean.TABLE);
        cbxLeuchteStadtbezirk.setSelectedItem(null);
    }

    /**
     * DOCUMENT ME!
     */
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
        componentToLabelMap.put(txtLeuchteRundsteuer, lblLeuchteRundsteuer);
        componentToLabelMap.put(txtLeuchteSchaltstelle, lblLeuchteSchaltstelle);
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
        cbxLeuchteStrassenschluessel.setEnabled(isEditable);
        cbxLeuchteStrassenschluesselNr.setEnabled(isEditable);
        cbxLeuchteKennziffer.setEnabled(isEditable);
        lblLeuchteStandortangabe.setVisible(isEditable);
        lblLeuchteVerrechnungseinheit.setVisible(isEditable);
        lblLeuchteStadtbezirk.setVisible(isEditable);
        cbxLeuchteStadtbezirk.setVisible(isEditable);
        txfLeuchteStandortAngabe.setVisible(isEditable);
        cboLeuchteVerrechnungseinheit.setVisible(isEditable);
        cbxLeuchteStadtbezirk.setEnabled(isEditable);
        txfLeuchteStandortAngabe.setEnabled(isEditable);
        cboLeuchteVerrechnungseinheit.setEnabled(isEditable);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void txtLeuchteRundsteuerActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_txtLeuchteRundsteuerActionPerformed
// TODO add your handling code here:
    } //GEN-LAST:event_txtLeuchteRundsteuerActionPerformed

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
        txtLeuchteLeuchtennummer.setEnabled(isEditable);
        cbxLeuchteEnergielieferant.setEnabled(isEditable);
        txtLeuchteSchaltstelle.setEnabled(isEditable);
        txtLeuchteRundsteuer.setEnabled(isEditable);
        cbxLeuchteUnterhalt.setEnabled(isEditable);
        cboLeuchteZaehler.setEnabled(isEditable);
        dapLeuchteInbetriebnahme.setEnabled(isEditable);
        cbxLeuchteLeuchtentyp.setEnabled(isEditable);
        cbxLeuchteDoppelkommando1.setEnabled(isEditable);
        cbxLeuchteDoppelkommando2.setEnabled(isEditable);
        sprLeuchteDoppelkommando1Anzahl.setEnabled(isEditable);
        sprLeuchteDoppelkommando2Anzahl.setEnabled(isEditable);
        txfLeuchteMontagefirma.setEnabled(isEditable);
        txaLeuchteBemerkung.setEnabled(isEditable);
        if (!((belisBroker.getWorkbenchWidget().getSelectedTreeNode() != null)
                        && belisBroker.getWorkbenchWidget().isParentNodeMast(
                            belisBroker.getWorkbenchWidget().getSelectedTreeNode().getLastPathComponent()))) {
            cbxLeuchteKennziffer.setEnabled(isEditable);
            cbxLeuchteStrassenschluessel.setEnabled(isEditable);
            cbxLeuchteStrassenschluesselNr.setEnabled(isEditable);
            cbxLeuchteStadtbezirk.setEnabled(isEditable);
            txfLeuchteStandortAngabe.setEnabled(isEditable);
            cboLeuchteVerrechnungseinheit.setEnabled(isEditable);
        }
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
