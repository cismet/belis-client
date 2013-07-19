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
    private javax.swing.JSeparator sprLeuchte;
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
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

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
        lblLeuchte = new javax.swing.JLabel();
        sprLeuchte = new javax.swing.JSeparator();

        lblLeuchteLeuchtennummer.setText("Leuchtennummer:"); // NOI18N

        txtLeuchteLeuchtennummer.setEnabled(false);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.leuchtennummer}"),
                txtLeuchteLeuchtennummer,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setValidator(new LeuchtennummerValidator());
        bindingGroup.addBinding(binding);

        lblLeuchteEnergielieferant.setText("Energielieferant:"); // NOI18N

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

        lblLeuchteSchaltstelle.setText("Schaltstelle:"); // NOI18N

        txtLeuchteSchaltstelle.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.schaltstelle}"),
                txtLeuchteSchaltstelle,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setValidator(new StringMaxLengthValidator());
        bindingGroup.addBinding(binding);

        lblLeuchteRundsteuer.setText("Rundsteuerempfänger:"); // NOI18N

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

        lblLeuchteLeuchtentyp.setText("Leuchtentyp:"); // NOI18N

        lblLeuchteUnterhalt.setText("Unterhalt Leuchte:"); // NOI18N

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

        lblLeuchteZaehler.setText("Zähler vorhanden:"); // NOI18N

        cboLeuchteZaehler.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.zaehler}"),
                cboLeuchteZaehler,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        bindingGroup.addBinding(binding);

        lblLeuchteInbetriebnahme.setText("Inbetriebnahme:"); // NOI18N

        dapLeuchteInbetriebnahme.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.inbetriebnahmeLeuchte}"),
                dapLeuchteInbetriebnahme,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setValidator(new DateValidator());
        bindingGroup.addBinding(binding);

        lblLeuchteStrassenschluessel.setText("Straßenschlüssel:"); // NOI18N

        lblLeuchteLaufendenummer.setText("Laufende Nr.:"); // NOI18N

        txfLeuchteLaufendenummer.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.laufendeNummer}"),
                txfLeuchteLaufendenummer,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

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

        lblLeuchteKenziffer.setText("Kennziffer:"); // NOI18N

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

        lblLeuchteDoppelkommando1.setText("Doppelkomando 1:"); // NOI18N

        lblLeuchteDoppelkommando2.setText("Doppelkomando 2:"); // NOI18N

        sprLeuchteDoppelkommando1Anzahl.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.anzahl1DK}"),
                sprLeuchteDoppelkommando1Anzahl,
                org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setSourceNullValue(0);
        bindingGroup.addBinding(binding);

        sprLeuchteDoppelkommando2Anzahl.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.anzahl2DK}"),
                sprLeuchteDoppelkommando2Anzahl,
                org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setSourceNullValue(0);
        bindingGroup.addBinding(binding);

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

        lblLeuchteMontagefirma.setText("Montagefirma:"); // NOI18N

        txfLeuchteMontagefirma.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.montageFirmaLeuchte}"),
                txfLeuchteMontagefirma,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setValidator(new StringMaxLengthValidator());
        bindingGroup.addBinding(binding);

        lblLeuchteBemerkung.setText("Bemerkung:"); // NOI18N

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

        lblLeuchteStadtbezirk.setText("Stadtbezik:"); // NOI18N

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

        lblLeuchteStandortangabe.setText("Standortangabe:"); // NOI18N

        txfLeuchteStandortAngabe.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.standort.standortangabe}"),
                txfLeuchteStandortAngabe,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        lblLeuchteVerrechnungseinheit.setText("V-Einheit:");                 // NOI18N
        lblLeuchteVerrechnungseinheit.setToolTipText("Verrechnungseinheit"); // NOI18N

        cboLeuchteVerrechnungseinheit.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.standort.verrechnungseinheit}"),
                cboLeuchteVerrechnungseinheit,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        bindingGroup.addBinding(binding);

        final javax.swing.GroupLayout panContentLayout = new javax.swing.GroupLayout(panContent);
        panContent.setLayout(panContentLayout);
        panContentLayout.setHorizontalGroup(
            panContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                panContentLayout.createSequentialGroup().addContainerGap().addGroup(
                    panContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                        panContentLayout.createSequentialGroup().addComponent(lblLeuchteUnterhalt).addPreferredGap(
                            javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
                            cbxLeuchteUnterhalt,
                            0,
                            251,
                            Short.MAX_VALUE)).addGroup(
                        panContentLayout.createSequentialGroup().addGroup(
                            panContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblLeuchteRundsteuer).addComponent(lblLeuchteLeuchtentyp)
                                        .addComponent(lblLeuchteZaehler).addComponent(lblLeuchteInbetriebnahme)
                                        .addComponent(lblLeuchteDoppelkommando1).addComponent(
                                lblLeuchteDoppelkommando2).addComponent(lblLeuchteMontagefirma).addComponent(
                                lblLeuchteSchaltstelle).addComponent(lblLeuchteStrassenschluessel).addComponent(
                                lblLeuchteKenziffer).addComponent(lblLeuchteLaufendenummer).addComponent(
                                lblLeuchteLeuchtennummer).addComponent(lblLeuchteEnergielieferant)).addPreferredGap(
                            javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                            panContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                                panContentLayout.createSequentialGroup().addComponent(
                                    cbxLeuchteStrassenschluesselNr,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    90,
                                    javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(
                                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
                                    cbxLeuchteStrassenschluessel,
                                    0,
                                    155,
                                    Short.MAX_VALUE)).addComponent(cbxLeuchteKennziffer, 0, 251, Short.MAX_VALUE)
                                        .addComponent(
                                            txfLeuchteLaufendenummer,
                                            javax.swing.GroupLayout.Alignment.TRAILING,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            251,
                                            Short.MAX_VALUE).addComponent(
                                txtLeuchteLeuchtennummer,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                251,
                                Short.MAX_VALUE).addComponent(cbxLeuchteEnergielieferant, 0, 251, Short.MAX_VALUE)
                                        .addComponent(
                                            txtLeuchteSchaltstelle,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            251,
                                            Short.MAX_VALUE).addComponent(
                                txtLeuchteRundsteuer,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                251,
                                Short.MAX_VALUE).addComponent(cbxLeuchteLeuchtentyp, 0, 251, Short.MAX_VALUE)
                                        .addComponent(
                                            cboLeuchteZaehler,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            251,
                                            Short.MAX_VALUE).addComponent(
                                dapLeuchteInbetriebnahme,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                175,
                                Short.MAX_VALUE).addComponent(
                                txfLeuchteMontagefirma,
                                javax.swing.GroupLayout.Alignment.TRAILING,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                251,
                                Short.MAX_VALUE).addGroup(
                                javax.swing.GroupLayout.Alignment.TRAILING,
                                panContentLayout.createSequentialGroup().addGroup(
                                    panContentLayout.createParallelGroup(
                                        javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                                        cbxLeuchteDoppelkommando2,
                                        0,
                                        198,
                                        Short.MAX_VALUE).addComponent(
                                        cbxLeuchteDoppelkommando1,
                                        javax.swing.GroupLayout.Alignment.TRAILING,
                                        0,
                                        198,
                                        Short.MAX_VALUE)).addPreferredGap(
                                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                                    panContentLayout.createParallelGroup(
                                        javax.swing.GroupLayout.Alignment.LEADING,
                                        false).addComponent(
                                        sprLeuchteDoppelkommando2Anzahl,
                                        javax.swing.GroupLayout.Alignment.TRAILING).addComponent(
                                        sprLeuchteDoppelkommando1Anzahl,
                                        javax.swing.GroupLayout.Alignment.TRAILING,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        47,
                                        Short.MAX_VALUE))))).addGroup(
                        javax.swing.GroupLayout.Alignment.TRAILING,
                        panContentLayout.createSequentialGroup().addComponent(
                            lblLeuchteBemerkung,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            147,
                            Short.MAX_VALUE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(
                                        scpLeuchteBemerkung,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        251,
                                        Short.MAX_VALUE)).addGroup(
                        panContentLayout.createSequentialGroup().addComponent(lblLeuchteVerrechnungseinheit).addGap(
                            98,
                            98,
                            98).addGroup(
                            panContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(
                                            txfLeuchteStandortAngabe,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            251,
                                            Short.MAX_VALUE).addComponent(
                                cbxLeuchteStadtbezirk,
                                0,
                                251,
                                Short.MAX_VALUE).addComponent(
                                cboLeuchteVerrechnungseinheit,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                251,
                                Short.MAX_VALUE))).addComponent(lblLeuchteStandortangabe).addComponent(
                        lblLeuchteStadtbezirk)).addContainerGap()));

        panContentLayout.linkSize(
            javax.swing.SwingConstants.HORIZONTAL,
            new java.awt.Component[] {
                lblLeuchteBemerkung,
                lblLeuchteDoppelkommando1,
                lblLeuchteDoppelkommando2,
                lblLeuchteEnergielieferant,
                lblLeuchteInbetriebnahme,
                lblLeuchteKenziffer,
                lblLeuchteLaufendenummer,
                lblLeuchteLeuchtennummer,
                lblLeuchteLeuchtentyp,
                lblLeuchteMontagefirma,
                lblLeuchteRundsteuer,
                lblLeuchteSchaltstelle,
                lblLeuchteStrassenschluessel,
                lblLeuchteUnterhalt,
                lblLeuchteZaehler
            });

        panContentLayout.setVerticalGroup(
            panContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                panContentLayout.createSequentialGroup().addContainerGap().addGroup(
                    panContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblLeuchteStrassenschluessel).addComponent(
                        cbxLeuchteStrassenschluesselNr,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        19,
                        javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(
                        cbxLeuchteStrassenschluessel,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        21,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    panContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblLeuchteKenziffer).addComponent(
                        cbxLeuchteKennziffer,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        21,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    panContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblLeuchteLaufendenummer).addComponent(
                        txfLeuchteLaufendenummer,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        22,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    panContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblLeuchteLeuchtennummer).addComponent(
                        txtLeuchteLeuchtennummer,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        21,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    panContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblLeuchteEnergielieferant).addComponent(
                        cbxLeuchteEnergielieferant,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        22,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    panContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblLeuchteSchaltstelle).addComponent(
                        txtLeuchteSchaltstelle,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        21,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    panContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblLeuchteRundsteuer).addComponent(
                        txtLeuchteRundsteuer,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        22,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    panContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblLeuchteLeuchtentyp).addComponent(
                        cbxLeuchteLeuchtentyp,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        21,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    panContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblLeuchteUnterhalt).addComponent(
                        cbxLeuchteUnterhalt,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        22,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    panContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(
                        lblLeuchteZaehler).addComponent(cboLeuchteZaehler)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    panContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblLeuchteInbetriebnahme).addComponent(
                        dapLeuchteInbetriebnahme,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        22,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    panContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblLeuchteDoppelkommando1).addComponent(
                        sprLeuchteDoppelkommando1Anzahl,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        22,
                        javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(
                        cbxLeuchteDoppelkommando1,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        20,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    panContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblLeuchteDoppelkommando2).addComponent(
                        sprLeuchteDoppelkommando2Anzahl,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        22,
                        javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(
                        cbxLeuchteDoppelkommando2,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        19,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    panContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblLeuchteMontagefirma).addComponent(
                        txfLeuchteMontagefirma,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        22,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(
                    panContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                        lblLeuchteBemerkung).addComponent(
                        scpLeuchteBemerkung,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        101,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(
                    panContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblLeuchteStandortangabe).addComponent(
                        txfLeuchteStandortAngabe,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        22,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    panContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblLeuchteStadtbezirk).addComponent(
                        cbxLeuchteStadtbezirk,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    panContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                        lblLeuchteVerrechnungseinheit).addComponent(cboLeuchteVerrechnungseinheit)).addContainerGap(
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE)));

        panContentLayout.linkSize(
            javax.swing.SwingConstants.VERTICAL,
            new java.awt.Component[] {
                cboLeuchteZaehler,
                cbxLeuchteDoppelkommando1,
                cbxLeuchteDoppelkommando2,
                cbxLeuchteEnergielieferant,
                cbxLeuchteKennziffer,
                cbxLeuchteUnterhalt,
                sprLeuchteDoppelkommando1Anzahl,
                sprLeuchteDoppelkommando2Anzahl,
                txfLeuchteLaufendenummer,
                txtLeuchteLeuchtennummer,
                txtLeuchteRundsteuer,
                txtLeuchteSchaltstelle
            });

        lblLeuchte.setFont(new java.awt.Font("DejaVu Sans", 1, 13));                       // NOI18N
        lblLeuchte.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/22/leuchte.png"))); // NOI18N
        lblLeuchte.setText("Leuchte");                                                     // NOI18N

        final javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                javax.swing.GroupLayout.Alignment.TRAILING,
                layout.createSequentialGroup().addContainerGap().addGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(
                        panContent,
                        javax.swing.GroupLayout.Alignment.LEADING,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        Short.MAX_VALUE).addComponent(lblLeuchte, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(
                                    sprLeuchte,
                                    javax.swing.GroupLayout.Alignment.LEADING,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    434,
                                    Short.MAX_VALUE)).addContainerGap()));
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                layout.createSequentialGroup().addContainerGap().addComponent(lblLeuchte).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
                    sprLeuchte,
                    javax.swing.GroupLayout.PREFERRED_SIZE,
                    10,
                    javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
                    panContent,
                    javax.swing.GroupLayout.PREFERRED_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap(
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE)));

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    @Override
    BindingGroup getBindingGroup() {
        return bindingGroup;
    }

    @Override
    final void initPanel() {
        bindingGroup.addBindingListener(new PanelBindingListener());
        createSortedCBoxModelFromCollection(allStrassenschluessel, cbxLeuchteStrassenschluessel);
        cbxLeuchteStrassenschluessel.setSelectedItem(null);
        AutoCompleteDecorator.decorate(cbxLeuchteStrassenschluessel, new ObjectToKeyStringConverter());
        createSortedCBoxModelFromCollection(allStrassenschluessel, cbxLeuchteStrassenschluesselNr);
        cbxLeuchteStrassenschluesselNr.setSelectedItem(null);
        AutoCompleteDecorator.decorate(cbxLeuchteStrassenschluesselNr, new ObjectToPkConverter("pk"));

        fillComboBoxWithKeyTableValuesAndAddListener(cbxLeuchteKennziffer, TkeyKennzifferCustomBean.TABLE);
        cbxLeuchteKennziffer.setSelectedItem(null);

        fillComboBoxWithKeyTableValuesAndAddListener(cbxLeuchteEnergielieferant, TkeyEnergielieferantCustomBean.TABLE);

        try {
            final Collection<TkeyUnterhLeuchteCustomBean> unterhalt = CidsBroker.getInstance().getAllUnterhaltLeuchte();
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
            final Collection<TkeyDoppelkommandoCustomBean> dk1 = CidsBroker.getInstance().getAllDoppelkommando();
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
