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

import de.cismet.belis.broker.BelisBroker;
import de.cismet.belis.broker.CidsBroker;

import de.cismet.belisEE.exception.ActionNotSuccessfulException;

import de.cismet.cids.custom.beans.belis.AnlagengruppeCustomBean;
import de.cismet.cids.custom.beans.belis.TdtaStandortMastCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyBezirkCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyKennzifferCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyKlassifizierungCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyMastartCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyMasttypCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyStrassenschluesselCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyUnterhMastCustomBean;

/**
 * DOCUMENT ME!
 *
 * @author   Gilles Baatz
 * @version  $Revision$, $Date$
 */
public class StandortPanel extends AbstractDetailWidgetPanel<TdtaStandortMastCustomBean> {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(StandortPanel.class);

    private static StandortPanel instance = null;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox cboStandortErdung;
    private javax.swing.JCheckBox cboStandortVerrechnungseinheit;
    private javax.swing.JComboBox cbxStandortAnlagengruppe;
    private javax.swing.JComboBox cbxStandortKennziffer;
    private javax.swing.JComboBox cbxStandortKlassifizierung;
    private javax.swing.JComboBox cbxStandortMastart;
    private javax.swing.JComboBox cbxStandortMasttyp;
    private javax.swing.JComboBox cbxStandortStadtbezirk;
    private javax.swing.JComboBox cbxStandortStrassenschluessel;
    private javax.swing.JComboBox cbxStandortStrassenschluesselNr;
    private javax.swing.JComboBox cbxStandortUnterhalt;
    private org.jdesktop.swingx.JXDatePicker dapStandortElekPruefung;
    private org.jdesktop.swingx.JXDatePicker dapStandortInbetriebnahme;
    private org.jdesktop.swingx.JXDatePicker dapStandortLetzteAenderung;
    private org.jdesktop.swingx.JXDatePicker dapStandortMastanstrich;
    private org.jdesktop.swingx.JXDatePicker dapStandortMastschutz;
    private org.jdesktop.swingx.JXDatePicker dapStandortNaechstesPruefdatum;
    private org.jdesktop.swingx.JXDatePicker dapStandortRevision;
    private org.jdesktop.swingx.JXDatePicker dapStandortStandsicherheitspruefung;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblStandort;
    private javax.swing.JLabel lblStandortAnlagengruppe;
    private javax.swing.JLabel lblStandortAnstrichfarbe;
    private javax.swing.JLabel lblStandortBemerkung;
    private javax.swing.JLabel lblStandortElekPruefung;
    private javax.swing.JLabel lblStandortErdung;
    private javax.swing.JLabel lblStandortGruendung;
    private javax.swing.JLabel lblStandortHausnummer;
    private javax.swing.JLabel lblStandortInbetriebnahme;
    private javax.swing.JLabel lblStandortKenziffer;
    private javax.swing.JLabel lblStandortKlassifizierung;
    private javax.swing.JLabel lblStandortLaufendenummer;
    private javax.swing.JLabel lblStandortLetzteAenderung;
    private javax.swing.JLabel lblStandortMastanstrich;
    private javax.swing.JLabel lblStandortMastart;
    private javax.swing.JLabel lblStandortMastschutz;
    private javax.swing.JLabel lblStandortMasttyp;
    private javax.swing.JLabel lblStandortMontagefirma;
    private javax.swing.JLabel lblStandortMonteur;
    private javax.swing.JLabel lblStandortNaechstesPruefdatum;
    private javax.swing.JLabel lblStandortPLZ;
    private javax.swing.JLabel lblStandortRevision;
    private javax.swing.JLabel lblStandortStadtbezirk;
    private javax.swing.JLabel lblStandortStandortangabe;
    private javax.swing.JLabel lblStandortStandsicherheitspruefung;
    private javax.swing.JLabel lblStandortStrassenschluessel;
    private javax.swing.JLabel lblStandortUnterhalt;
    private javax.swing.JLabel lblStandortVerfahren;
    private javax.swing.JLabel lblStandortVerrechnungseinheit;
    private javax.swing.JScrollPane scpStandortBemerkung;
    private javax.swing.JTextArea txaStandortBemerkung;
    private javax.swing.JTextField txfStandortAnstrichfarbe;
    private javax.swing.JTextField txfStandortGruendung;
    private javax.swing.JTextField txfStandortHausnummer;
    private javax.swing.JTextField txfStandortLaufendenummer;
    private javax.swing.JTextField txfStandortMontagefirma;
    private javax.swing.JTextField txfStandortMonteur;
    private javax.swing.JTextField txfStandortStandortAngabe;
    private javax.swing.JTextField txfStandortVerfahren;
    private javax.swing.JTextField txtStandortPLZ;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form StandortPanel.
     */
    private StandortPanel() {
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
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static StandortPanel getInstance() {
        if (instance == null) {
            synchronized (StandortPanel.class) {
                if (instance == null) {
                    instance = new StandortPanel();
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

        lblStandort = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblStandortStadtbezirk = new javax.swing.JLabel();
        lblStandortPLZ = new javax.swing.JLabel();
        txtStandortPLZ = new javax.swing.JTextField();
        cbxStandortStadtbezirk = new javax.swing.JComboBox();
        lblStandortHausnummer = new javax.swing.JLabel();
        txfStandortHausnummer = new javax.swing.JTextField();
        lblStandortMastart = new javax.swing.JLabel();
        lblStandortMasttyp = new javax.swing.JLabel();
        cbxStandortMastart = new javax.swing.JComboBox();
        cbxStandortMasttyp = new javax.swing.JComboBox();
        lblStandortKlassifizierung = new javax.swing.JLabel();
        cbxStandortKlassifizierung = new javax.swing.JComboBox();
        lblStandortMastanstrich = new javax.swing.JLabel();
        dapStandortMastanstrich = new org.jdesktop.swingx.JXDatePicker();
        lblStandortMastschutz = new javax.swing.JLabel();
        dapStandortMastschutz = new org.jdesktop.swingx.JXDatePicker();
        lblStandortInbetriebnahme = new javax.swing.JLabel();
        dapStandortInbetriebnahme = new org.jdesktop.swingx.JXDatePicker();
        lblStandortLetzteAenderung = new javax.swing.JLabel();
        dapStandortLetzteAenderung = new org.jdesktop.swingx.JXDatePicker();
        lblStandortUnterhalt = new javax.swing.JLabel();
        cbxStandortUnterhalt = new javax.swing.JComboBox();
        lblStandortMontagefirma = new javax.swing.JLabel();
        txfStandortMontagefirma = new javax.swing.JTextField();
        lblStandortBemerkung = new javax.swing.JLabel();
        cboStandortVerrechnungseinheit = new javax.swing.JCheckBox();
        scpStandortBemerkung = new javax.swing.JScrollPane();
        txaStandortBemerkung = new javax.swing.JTextArea();
        cbxStandortStrassenschluessel = new javax.swing.JComboBox();
        lblStandortStrassenschluessel = new javax.swing.JLabel();
        lblStandortKenziffer = new javax.swing.JLabel();
        cbxStandortKennziffer = new javax.swing.JComboBox();
        txfStandortLaufendenummer = new javax.swing.JTextField();
        lblStandortLaufendenummer = new javax.swing.JLabel();
        lblStandortVerrechnungseinheit = new javax.swing.JLabel();
        lblStandortStandortangabe = new javax.swing.JLabel();
        txfStandortStandortAngabe = new javax.swing.JTextField();
        cbxStandortStrassenschluesselNr = new javax.swing.JComboBox();
        lblStandortElekPruefung = new javax.swing.JLabel();
        dapStandortElekPruefung = new org.jdesktop.swingx.JXDatePicker();
        txfStandortGruendung = new javax.swing.JTextField();
        lblStandortGruendung = new javax.swing.JLabel();
        lblStandortErdung = new javax.swing.JLabel();
        cboStandortErdung = new javax.swing.JCheckBox();
        lblStandortMonteur = new javax.swing.JLabel();
        txfStandortMonteur = new javax.swing.JTextField();
        lblStandortStandsicherheitspruefung = new javax.swing.JLabel();
        dapStandortStandsicherheitspruefung = new org.jdesktop.swingx.JXDatePicker();
        lblStandortVerfahren = new javax.swing.JLabel();
        txfStandortVerfahren = new javax.swing.JTextField();
        lblStandortNaechstesPruefdatum = new javax.swing.JLabel();
        dapStandortNaechstesPruefdatum = new org.jdesktop.swingx.JXDatePicker();
        lblStandortRevision = new javax.swing.JLabel();
        dapStandortRevision = new org.jdesktop.swingx.JXDatePicker();
        lblStandortAnstrichfarbe = new javax.swing.JLabel();
        txfStandortAnstrichfarbe = new javax.swing.JTextField();
        lblStandortAnlagengruppe = new javax.swing.JLabel();
        cbxStandortAnlagengruppe = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();

        lblStandort.setFont(new java.awt.Font("DejaVu Sans", 1, 13));                       // NOI18N
        lblStandort.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/22/standort.png"))); // NOI18N
        lblStandort.setText("Standort");                                                    // NOI18N

        setLayout(new java.awt.GridBagLayout());

        jPanel1.setLayout(new java.awt.GridBagLayout());

        lblStandortStadtbezirk.setText("Stadtbezirk:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblStandortStadtbezirk, gridBagConstraints);

        lblStandortPLZ.setText("Postleitzahl:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblStandortPLZ, gridBagConstraints);

        txtStandortPLZ.setEnabled(false);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.plz}"),
                txtStandortPLZ,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setValidator(new PLZValidator());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtStandortPLZ, gridBagConstraints);

        cbxStandortStadtbezirk.setEnabled(false);
        cbxStandortStadtbezirk.setRenderer(new DefaultListCellRenderer() {

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
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.stadtbezirk}"),
                cbxStandortStadtbezirk,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbxStandortStadtbezirk, gridBagConstraints);

        lblStandortHausnummer.setText("Hausnummer:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblStandortHausnummer, gridBagConstraints);

        txfStandortHausnummer.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.hausnummer}"),
                txfStandortHausnummer,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setValidator(new StringMaxLengthValidator(5));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txfStandortHausnummer, gridBagConstraints);

        lblStandortMastart.setText("Mastart:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblStandortMastart, gridBagConstraints);

        lblStandortMasttyp.setText("Mast Typ:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblStandortMasttyp, gridBagConstraints);

        cbxStandortMastart.setEnabled(false);
        cbxStandortMastart.setRenderer(new DefaultListCellRenderer() {

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
                    } else if (value instanceof de.cismet.cids.custom.beans.belis.TkeyMastartCustomBean) {
                        final de.cismet.cids.custom.beans.belis.TkeyMastartCustomBean ma =
                            (de.cismet.cids.custom.beans.belis.TkeyMastartCustomBean)value;
                        setText(ma.getMastart());
                    }
                    return this;
                }
            });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.mastart}"),
                cbxStandortMastart,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbxStandortMastart, gridBagConstraints);

        cbxStandortMasttyp.setEnabled(false);
        cbxStandortMasttyp.setRenderer(new DefaultListCellRenderer() {

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
                    } else if (value instanceof de.cismet.cids.custom.beans.belis.TkeyMasttypCustomBean) {
                        final de.cismet.cids.custom.beans.belis.TkeyMasttypCustomBean mt =
                            (de.cismet.cids.custom.beans.belis.TkeyMasttypCustomBean)value;
                        setText(mt.getMasttyp());
                    }
                    return this;
                }
            });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.masttyp}"),
                cbxStandortMasttyp,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbxStandortMasttyp, gridBagConstraints);

        lblStandortKlassifizierung.setText("Klassifizierung:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblStandortKlassifizierung, gridBagConstraints);

        cbxStandortKlassifizierung.setEnabled(false);
        cbxStandortKlassifizierung.setRenderer(new DefaultListCellRenderer() {

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
                    } else if (value instanceof de.cismet.cids.custom.beans.belis.TkeyKlassifizierungCustomBean) {
                        final de.cismet.cids.custom.beans.belis.TkeyKlassifizierungCustomBean kl =
                            (de.cismet.cids.custom.beans.belis.TkeyKlassifizierungCustomBean)value;
                        setText(kl.getKlassifizierung());
                    }
                    return this;
                }
            });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.klassifizierung}"),
                cbxStandortKlassifizierung,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbxStandortKlassifizierung, gridBagConstraints);

        lblStandortMastanstrich.setText("Mastanstrich:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblStandortMastanstrich, gridBagConstraints);

        dapStandortMastanstrich.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.mastanstrich}"),
                dapStandortMastanstrich,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setValidator(new DateValidator());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(dapStandortMastanstrich, gridBagConstraints);

        lblStandortMastschutz.setText("Mastschutz:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblStandortMastschutz, gridBagConstraints);

        dapStandortMastschutz.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.mastschutz}"),
                dapStandortMastschutz,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setValidator(new DateValidator());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(dapStandortMastschutz, gridBagConstraints);

        lblStandortInbetriebnahme.setText("Inbetriebnahme:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblStandortInbetriebnahme, gridBagConstraints);

        dapStandortInbetriebnahme.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.inbetriebnahmeMast}"),
                dapStandortInbetriebnahme,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setValidator(new DateValidator());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(dapStandortInbetriebnahme, gridBagConstraints);

        lblStandortLetzteAenderung.setText("Letze Änderung:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblStandortLetzteAenderung, gridBagConstraints);

        dapStandortLetzteAenderung.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.letzteAenderung}"),
                dapStandortLetzteAenderung,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setValidator(new DateValidator());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(dapStandortLetzteAenderung, gridBagConstraints);

        lblStandortUnterhalt.setText("Unterhalt:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblStandortUnterhalt, gridBagConstraints);

        cbxStandortUnterhalt.setEnabled(false);
        cbxStandortUnterhalt.setRenderer(new DefaultListCellRenderer() {

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
                    } else if (value instanceof de.cismet.cids.custom.beans.belis.TkeyUnterhMastCustomBean) {
                        final de.cismet.cids.custom.beans.belis.TkeyUnterhMastCustomBean um =
                            (de.cismet.cids.custom.beans.belis.TkeyUnterhMastCustomBean)value;
                        setText(um.getUnterhaltMast());
                    }
                    return this;
                }
            });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.unterhaltspflichtMast}"),
                cbxStandortUnterhalt,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbxStandortUnterhalt, gridBagConstraints);

        lblStandortMontagefirma.setText("Montagefirma:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblStandortMontagefirma, gridBagConstraints);

        txfStandortMontagefirma.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.montagefirma}"),
                txfStandortMontagefirma,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setValidator(new StringMaxLengthValidator());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txfStandortMontagefirma, gridBagConstraints);

        lblStandortBemerkung.setText("Bemerkung:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 27;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblStandortBemerkung, gridBagConstraints);

        cboStandortVerrechnungseinheit.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.verrechnungseinheit}"),
                cboStandortVerrechnungseinheit,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cboStandortVerrechnungseinheit, gridBagConstraints);

        scpStandortBemerkung.setEnabled(false);

        txaStandortBemerkung.setColumns(20);
        txaStandortBemerkung.setRows(5);

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
        gridBagConstraints.gridy = 27;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(scpStandortBemerkung, gridBagConstraints);

        cbxStandortStrassenschluessel.setEnabled(false);
        cbxStandortStrassenschluessel.setRenderer(new DefaultListCellRenderer() {

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
                cbxStandortStrassenschluessel,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setValidator(new NotNullValidator("Straßenschlüssel"));
        bindingGroup.addBinding(binding);

        cbxStandortStrassenschluessel.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbxStandortStrassenschluesselActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbxStandortStrassenschluessel, gridBagConstraints);

        lblStandortStrassenschluessel.setText("Straßenschlüssel:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblStandortStrassenschluessel, gridBagConstraints);

        lblStandortKenziffer.setText("Kennziffer:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblStandortKenziffer, gridBagConstraints);

        cbxStandortKennziffer.setEnabled(false);
        cbxStandortKennziffer.setRenderer(new DefaultListCellRenderer() {

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
                cbxStandortKennziffer,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setValidator(new NotNullValidator("de.cismet.cids.custom.beans.belis.TkeyKennzifferCustomBean"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbxStandortKennziffer, gridBagConstraints);

        txfStandortLaufendenummer.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.laufendeNummer}"),
                txfStandortLaufendenummer,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txfStandortLaufendenummer, gridBagConstraints);

        lblStandortLaufendenummer.setText("Laufende Nr.:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblStandortLaufendenummer, gridBagConstraints);

        lblStandortVerrechnungseinheit.setText("V-Einheit:");                 // NOI18N
        lblStandortVerrechnungseinheit.setToolTipText("Verrechnungseinheit"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblStandortVerrechnungseinheit, gridBagConstraints);

        lblStandortStandortangabe.setText("Standortangabe:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblStandortStandortangabe, gridBagConstraints);

        txfStandortStandortAngabe.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.standortangabe}"),
                txfStandortStandortAngabe,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setValidator(new StringMaxLengthValidator());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txfStandortStandortAngabe, gridBagConstraints);

        cbxStandortStrassenschluesselNr.setEnabled(false);
        cbxStandortStrassenschluesselNr.setRenderer(new DefaultListCellRenderer() {

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

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.strassenschluessel}"),
                cbxStandortStrassenschluesselNr,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"),
                "strassenschluesselnr"); // NOI18N
        bindingGroup.addBinding(binding);

        cbxStandortStrassenschluesselNr.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbxStandortStrassenschluesselNrActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbxStandortStrassenschluesselNr, gridBagConstraints);

        lblStandortElekPruefung.setText("Elektrische Prüfung:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblStandortElekPruefung, gridBagConstraints);

        dapStandortElekPruefung.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.elek_pruefung}"),
                dapStandortElekPruefung,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setValidator(new DateValidator());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(dapStandortElekPruefung, gridBagConstraints);

        txfStandortGruendung.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.gruendung}"),
                txfStandortGruendung,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txfStandortGruendung, gridBagConstraints);

        lblStandortGruendung.setText("Gründung:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblStandortGruendung, gridBagConstraints);

        lblStandortErdung.setText("Erdung:");                    // NOI18N
        lblStandortErdung.setToolTipText("Verrechnungseinheit"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblStandortErdung, gridBagConstraints);

        cboStandortErdung.setText("i.O.");
        cboStandortErdung.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cboStandortErdung, gridBagConstraints);

        lblStandortMonteur.setText("Monteur:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblStandortMonteur, gridBagConstraints);

        txfStandortMonteur.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.monteur}"),
                txfStandortMonteur,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txfStandortMonteur, gridBagConstraints);

        lblStandortStandsicherheitspruefung.setText("Standsicherheitsprüfung:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 21;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblStandortStandsicherheitspruefung, gridBagConstraints);

        dapStandortStandsicherheitspruefung.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.standsicherheitspruefung}"),
                dapStandortStandsicherheitspruefung,
                org.jdesktop.beansbinding.BeanProperty.create("date"),
                "");
        binding.setValidator(new DateValidator());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 21;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(dapStandortStandsicherheitspruefung, gridBagConstraints);

        lblStandortVerfahren.setText("Verfahren:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblStandortVerfahren, gridBagConstraints);

        txfStandortVerfahren.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.verfahren}"),
                txfStandortVerfahren,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txfStandortVerfahren, gridBagConstraints);

        lblStandortNaechstesPruefdatum.setText("Nächstes Prüfdatum"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 23;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblStandortNaechstesPruefdatum, gridBagConstraints);

        dapStandortNaechstesPruefdatum.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.naechstes_pruefdatum}"),
                dapStandortNaechstesPruefdatum,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setValidator(new DateValidator());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 23;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(dapStandortNaechstesPruefdatum, gridBagConstraints);

        lblStandortRevision.setText("Revision"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 25;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblStandortRevision, gridBagConstraints);

        dapStandortRevision.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.revision}"),
                dapStandortRevision,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setValidator(new DateValidator());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 25;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(dapStandortRevision, gridBagConstraints);

        lblStandortAnstrichfarbe.setText("Anstrichfarbe:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblStandortAnstrichfarbe, gridBagConstraints);

        txfStandortAnstrichfarbe.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.anstrichfarbe}"),
                txfStandortAnstrichfarbe,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txfStandortAnstrichfarbe, gridBagConstraints);

        lblStandortAnlagengruppe.setText("Anlagengruppe:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 26;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblStandortAnlagengruppe, gridBagConstraints);

        cbxStandortAnlagengruppe.setEnabled(false);
        cbxStandortAnlagengruppe.setRenderer(new DefaultListCellRenderer() {

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
                    } else if (value instanceof de.cismet.cids.custom.beans.belis.AnlagengruppeCustomBean) {
                        final de.cismet.cids.custom.beans.belis.AnlagengruppeCustomBean ag =
                            (de.cismet.cids.custom.beans.belis.AnlagengruppeCustomBean)value;
                        setText(ag.getNummer() + " - " + ag.getBezeichnung());
                    }
                    return this;
                }
            });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.anlagengruppe}"),
                cbxStandortAnlagengruppe,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 26;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cbxStandortAnlagengruppe, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(jPanel1, gridBagConstraints);

        final javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                479,
                Short.MAX_VALUE));
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                204,
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
     */
    @Override
    final void initComponentToLabelMap() {
        componentToLabelMap.put(cboStandortVerrechnungseinheit, lblStandortVerrechnungseinheit);
        componentToLabelMap.put(cbxStandortKennziffer, lblStandortKenziffer);
        componentToLabelMap.put(cbxStandortKlassifizierung, lblStandortKlassifizierung);
        componentToLabelMap.put(cbxStandortMastart, lblStandortMastart);
        componentToLabelMap.put(cbxStandortMasttyp, lblStandortMasttyp);
        componentToLabelMap.put(cbxStandortStadtbezirk, lblStandortStadtbezirk);
        componentToLabelMap.put(cbxStandortStrassenschluessel, lblStandortStrassenschluessel);
        componentToLabelMap.put(cbxStandortStrassenschluesselNr, lblStandortStrassenschluessel);
        componentToLabelMap.put(cbxStandortUnterhalt, lblStandortUnterhalt);
        componentToLabelMap.put(dapStandortInbetriebnahme, lblStandortInbetriebnahme);
        componentToLabelMap.put(dapStandortLetzteAenderung, lblStandortLetzteAenderung);
        componentToLabelMap.put(dapStandortMastanstrich, lblStandortMastanstrich);
        componentToLabelMap.put(dapStandortMastschutz, lblStandortMastschutz);
        componentToLabelMap.put(txaStandortBemerkung, lblStandortBemerkung);
        componentToLabelMap.put(txfStandortHausnummer, lblStandortHausnummer);
        componentToLabelMap.put(txfStandortLaufendenummer, lblStandortLaufendenummer);
        componentToLabelMap.put(txfStandortMontagefirma, lblStandortMontagefirma);
        componentToLabelMap.put(txtStandortPLZ, lblStandortPLZ);
        componentToLabelMap.put(txfStandortStandortAngabe, lblStandortStandortangabe);
        componentToLabelMap.put(dapStandortElekPruefung, lblStandortElekPruefung);
        componentToLabelMap.put(dapStandortNaechstesPruefdatum, lblStandortNaechstesPruefdatum);
        componentToLabelMap.put(dapStandortRevision, lblStandortRevision);
        componentToLabelMap.put(dapStandortStandsicherheitspruefung, lblStandortStandsicherheitspruefung);
        componentToLabelMap.put(txfStandortAnstrichfarbe, lblStandortAnstrichfarbe);
        componentToLabelMap.put(txfStandortGruendung, lblStandortGruendung);
        componentToLabelMap.put(txfStandortLaufendenummer, lblStandortLaufendenummer);
        componentToLabelMap.put(txfStandortMontagefirma, lblStandortMontagefirma);
        componentToLabelMap.put(txfStandortMonteur, lblStandortMonteur);
        componentToLabelMap.put(txfStandortVerfahren, lblStandortVerfahren);
        componentToLabelMap.put(cboStandortErdung, lblStandortErdung);
        componentToLabelMap.put(cbxStandortAnlagengruppe, lblStandortAnlagengruppe);
    }

    /**
     * DOCUMENT ME!
     */
    @Override
    final void initPanel() {
        bindingGroup.addBindingListener(new PanelBindingListener());
        fillComboBoxWithKeyTableValuesAndAddListener(
            cbxStandortStrassenschluessel,
            TkeyStrassenschluesselCustomBean.TABLE);
        cbxStandortStrassenschluessel.setSelectedItem(null);
        AutoCompleteDecorator.decorate(cbxStandortStrassenschluessel, new ObjectToKeyStringConverter());

        cbxStandortStrassenschluesselNr.setSelectedItem(null);
        fillComboBoxWithKeyTableValuesAndAddListener(
            cbxStandortStrassenschluesselNr,
            TkeyStrassenschluesselCustomBean.TABLE,
            true);
        AutoCompleteDecorator.decorate(cbxStandortStrassenschluesselNr, new ObjectToPkConverter("pk"));

        fillComboBoxWithKeyTableValuesAndAddListener(cbxStandortKennziffer, TkeyKennzifferCustomBean.TABLE);
        cbxStandortKennziffer.setSelectedItem(null);

        fillComboBoxWithKeyTableValuesAndAddListener(cbxStandortStadtbezirk, TkeyBezirkCustomBean.TABLE);
        cbxStandortStadtbezirk.setSelectedItem(null);

        fillComboBoxWithKeyTableValuesAndAddListener(cbxStandortKlassifizierung, TkeyKlassifizierungCustomBean.TABLE);
        cbxStandortKlassifizierung.setSelectedItem(null);

        fillComboBoxWithKeyTableValuesAndAddListener(cbxStandortMastart, TkeyMastartCustomBean.TABLE);
        cbxStandortMastart.setSelectedItem(null);

        fillComboBoxWithKeyTableValuesAndAddListener(cbxStandortMasttyp, TkeyMasttypCustomBean.TABLE);
        cbxStandortMasttyp.setSelectedItem(null);

        fillComboBoxWithKeyTableValuesAndAddListener(cbxStandortAnlagengruppe, AnlagengruppeCustomBean.TABLE);
        cbxStandortAnlagengruppe.setSelectedItem(null);

        AutoCompleteDecorator.decorate(cbxStandortMasttyp, new ObjectToKeyStringConverter());
        try {
            final Collection<TkeyUnterhMastCustomBean> unterhaltMast = CidsBroker.getInstance()
                        .getAll(TkeyUnterhMastCustomBean.TABLE);
            try {
                if ((unterhaltMast != null) && (unterhaltMast.size() > 0)) {
                    for (final TkeyUnterhMastCustomBean curUnterhaltMast : unterhaltMast) {
                        if (TdtaStandortMastCustomBean.DEFAULT_UNTERHALT.equals(curUnterhaltMast)
                                    && TdtaStandortMastCustomBean.DEFAULT_UNTERHALT.getUnterhaltMast().equals(
                                        curUnterhaltMast.getUnterhaltMast())) {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("Setting defaultUnterhaltMast to: " + curUnterhaltMast);
                            }
                            BelisBroker.setDefaultUnterhaltMast(curUnterhaltMast);
                        }
                    }
                }
            } catch (Exception ex) {
                LOG.warn("Error while determining default UnterhaltMast: ", ex);
            }
            fillComboBoxWithKeyTableValuesAndAddListener(cbxStandortUnterhalt, TkeyUnterhMastCustomBean.TABLE);
        } catch (ActionNotSuccessfulException ex) {
            cbxStandortUnterhalt.setModel(new DefaultComboBoxModel());
        }
        cbxStandortUnterhalt.setSelectedItem(null);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbxStandortStrassenschluesselActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbxStandortStrassenschluesselActionPerformed
        try {
            if (!isTriggerd) {
                isTriggerd = true;
                cbxStandortStrassenschluesselNr.setSelectedItem(cbxStandortStrassenschluessel.getSelectedItem());
            }
        } catch (Exception ex) {
            LOG.warn("failuire while strassenschluessel ", ex);
        } finally {
            isTriggerd = false;
        }
    }                                                                                                 //GEN-LAST:event_cbxStandortStrassenschluesselActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbxStandortStrassenschluesselNrActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbxStandortStrassenschluesselNrActionPerformed
        try {
            if (!isTriggerd) {
                isTriggerd = true;
                cbxStandortStrassenschluessel.setSelectedItem(cbxStandortStrassenschluesselNr.getSelectedItem());
            }
        } catch (Exception ex) {
            LOG.warn("failuire while updating strassenschluessel ", ex);
        } finally {
            isTriggerd = false;
        }
    }                                                                                                   //GEN-LAST:event_cbxStandortStrassenschluesselNrActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  isEditable  DOCUMENT ME!
     */
    @Override
    public void setPanelEditable(final boolean isEditable) {
        cbxStandortStrassenschluessel.setEnabled(isEditable);
        cbxStandortStrassenschluesselNr.setEnabled(isEditable);
        cbxStandortKennziffer.setEnabled(isEditable);
        cbxStandortStadtbezirk.setEnabled(isEditable);
        txfStandortStandortAngabe.setEnabled(isEditable);
        txtStandortPLZ.setEnabled(isEditable);
        txfStandortHausnummer.setEnabled(isEditable);
        cbxStandortMastart.setEnabled(isEditable);
        cbxStandortMasttyp.setEnabled(isEditable);
        cbxStandortUnterhalt.setEnabled(isEditable);
        cbxStandortKlassifizierung.setEnabled(isEditable);
        dapStandortInbetriebnahme.setEnabled(isEditable);
        dapStandortLetzteAenderung.setEnabled(isEditable);
        dapStandortMastanstrich.setEnabled(isEditable);
        dapStandortMastschutz.setEnabled(isEditable);
        txfStandortMontagefirma.setEnabled(isEditable);
        txaStandortBemerkung.setEnabled(isEditable);
        cboStandortVerrechnungseinheit.setEnabled(isEditable);
        dapStandortElekPruefung.setEnabled(isEditable);
        dapStandortNaechstesPruefdatum.setEnabled(isEditable);
        dapStandortRevision.setEnabled(isEditable);
        dapStandortStandsicherheitspruefung.setEnabled(isEditable);
        txfStandortAnstrichfarbe.setEnabled(isEditable);
        txfStandortGruendung.setEnabled(isEditable);
        txfStandortLaufendenummer.setEnabled(isEditable);
        txfStandortMontagefirma.setEnabled(isEditable);
        txfStandortMonteur.setEnabled(isEditable);
        txfStandortVerfahren.setEnabled(isEditable);
        cboStandortErdung.setEnabled(isEditable);
        cbxStandortAnlagengruppe.setEnabled(isEditable);
    }

    /**
     * DOCUMENT ME!
     */
    public void commitEdits() {
        try {
            dapStandortInbetriebnahme.getEditor().commitEdit();
        } catch (ParseException ex) {
            LOG.warn("Error while commiting edits: " + ex);
        }
        try {
            dapStandortInbetriebnahme.getEditor().commitEdit();
        } catch (ParseException ex) {
            LOG.warn("Error while commiting edits: " + ex);
        }
        try {
            dapStandortLetzteAenderung.getEditor().commitEdit();
        } catch (ParseException ex) {
            LOG.warn("Error while commiting edits: " + ex);
        }
        try {
            dapStandortMastanstrich.getEditor().commitEdit();
        } catch (ParseException ex) {
            LOG.warn("Error while commiting edits: " + ex);
        }
        try {
            dapStandortMastschutz.getEditor().commitEdit();
        } catch (ParseException ex) {
            LOG.warn("Error while commiting edits: " + ex);
        }
        try {
            dapStandortElekPruefung.getEditor().commitEdit();
        } catch (ParseException ex) {
            LOG.warn("Error while commiting edits: " + ex);
        }
        try {
            dapStandortStandsicherheitspruefung.getEditor().commitEdit();
        } catch (ParseException ex) {
            LOG.warn("Error while commiting edits: " + ex);
        }
        try {
            dapStandortRevision.getEditor().commitEdit();
        } catch (ParseException ex) {
            LOG.warn("Error while commiting edits: " + ex);
        }
        try {
            dapStandortNaechstesPruefdatum.getEditor().commitEdit();
        } catch (ParseException ex) {
            LOG.warn("Error while commiting edits: " + ex);
        }
    }

    @Override
    public void setElementsNull() {
        if (currentEntity.getStrassenschluessel() == null) {
            cbxStandortStrassenschluessel.setSelectedItem(null);
            cbxStandortStrassenschluesselNr.setSelectedItem(null);
        }
        if (currentEntity.getKennziffer() == null) {
            cbxStandortKennziffer.setSelectedItem(null);
        }
    }

    @Override
    BindingGroup getBindingGroup() {
        return bindingGroup;
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public class PLZValidator extends Validator<String> {

        //~ Methods ------------------------------------------------------------

        @Override
        public Validator.Result validate(final String value) {
            if (value != null) {
                try {
                    if (Integer.parseInt(value) > -1) {
                        if (value.length() == 5) {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("plz valide");
                            }
                            return null;
                        } else {
                            return new Validator.Result("code", "Postleitzahl muss genau 5 Stellen haben.");
                        }
                    } else {
                        return new Validator.Result("code", "Postleitzahl darf nicht negativ sein.");
                    }
                } catch (NumberFormatException ex) {
                    if (value.equals("")) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Postleitzahl is empty", ex);
                        }
                    } else {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Postleitzahl no number", ex);
                        }
                        return new Validator.Result("code", "Postleitzahl darf nur aus Ziffern bestehen.");
                    }
                }
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("plz valide");
            }
            return null;
        }
    }
}
