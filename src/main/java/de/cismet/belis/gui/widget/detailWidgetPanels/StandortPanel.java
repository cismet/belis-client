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

import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.BindingListener;
import org.jdesktop.beansbinding.PropertyStateEvent;
import org.jdesktop.beansbinding.Validator;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import java.awt.Color;
import java.awt.Component;

import java.text.ParseException;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComponent;
import javax.swing.JList;

import de.cismet.belis.broker.BelisBroker;
import de.cismet.belis.broker.CidsBroker;

import de.cismet.belisEE.exception.ActionNotSuccessfulException;

import de.cismet.cids.custom.beans.belis.TdtaStandortMastCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyBezirkCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyKennzifferCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyKlassifizierungCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyMastartCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyMasttypCustomBean;
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

    private static final HashMap<JComponent, JComponent> componentToLabelMap = new HashMap<JComponent, JComponent>();

    //~ Instance fields --------------------------------------------------------

    private Collection<Binding> validationState = new HashSet<Binding>();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox cboStandortVerrechnungseinheit;
    private javax.swing.JComboBox cbxStandortKennziffer;
    private javax.swing.JComboBox cbxStandortKlassifizierung;
    private javax.swing.JComboBox cbxStandortMastart;
    private javax.swing.JComboBox cbxStandortMasttyp;
    private javax.swing.JComboBox cbxStandortStadtbezirk;
    private javax.swing.JComboBox cbxStandortStrassenschluessel;
    private javax.swing.JComboBox cbxStandortStrassenschluesselNr;
    private javax.swing.JComboBox cbxStandortUnterhalt;
    private org.jdesktop.swingx.JXDatePicker dapStandortInbetriebnahme;
    private org.jdesktop.swingx.JXDatePicker dapStandortLetzteAenderung;
    private org.jdesktop.swingx.JXDatePicker dapStandortMastanstrich;
    private org.jdesktop.swingx.JXDatePicker dapStandortMastschutz;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblStandort;
    private javax.swing.JLabel lblStandortBemerkung;
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
    private javax.swing.JLabel lblStandortPLZ;
    private javax.swing.JLabel lblStandortStadtbezirk;
    private javax.swing.JLabel lblStandortStandortangabe;
    private javax.swing.JLabel lblStandortStrassenschluessel;
    private javax.swing.JLabel lblStandortUnterhalt;
    private javax.swing.JLabel lblStandortVerrechnungseinheit;
    private javax.swing.JScrollPane scpStandortBemerkung;
    private javax.swing.JSeparator sprStandort;
    private javax.swing.JTextArea txaStandortBemerkung;
    private javax.swing.JTextField txfStandortHausnummer;
    private javax.swing.JTextField txfStandortLaufendenummer;
    private javax.swing.JTextField txfStandortMontagefirma;
    private javax.swing.JTextField txfStandortStandortAngabe;
    private javax.swing.JTextField txtStandortPLZ;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form StandortPanel.
     */
    private StandortPanel() {
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
        sprStandort = new javax.swing.JSeparator();

        lblStandort.setFont(new java.awt.Font("DejaVu Sans", 1, 13));                       // NOI18N
        lblStandort.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/22/standort.png"))); // NOI18N
        lblStandort.setText("Standort");                                                    // NOI18N

        lblStandortStadtbezirk.setText("Stadtbezik:"); // NOI18N

        lblStandortPLZ.setText("Postleitzahl:"); // NOI18N

        txtStandortPLZ.setEnabled(false);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.plz}"),
                txtStandortPLZ,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setValidator(new PLZValidator());
        bindingGroup.addBinding(binding);

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

        lblStandortHausnummer.setText("Hausnummer:"); // NOI18N

        txfStandortHausnummer.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.hausnummer}"),
                txfStandortHausnummer,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setValidator(new StringMaxLengthValidator(5));
        bindingGroup.addBinding(binding);

        lblStandortMastart.setText("Mastart:"); // NOI18N

        lblStandortMasttyp.setText("Mast Typ:"); // NOI18N

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

        lblStandortKlassifizierung.setText("Klassifizierung:"); // NOI18N

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

        lblStandortMastanstrich.setText("Mastanstrich:"); // NOI18N

        dapStandortMastanstrich.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.mastanstrich}"),
                dapStandortMastanstrich,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setValidator(new DateValidator());
        bindingGroup.addBinding(binding);

        lblStandortMastschutz.setText("Mastschutz:"); // NOI18N

        dapStandortMastschutz.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.mastschutz}"),
                dapStandortMastschutz,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setValidator(new DateValidator());
        bindingGroup.addBinding(binding);

        lblStandortInbetriebnahme.setText("Inbetriebnahme:"); // NOI18N

        dapStandortInbetriebnahme.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.inbetriebnahmeMast}"),
                dapStandortInbetriebnahme,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setValidator(new DateValidator());
        bindingGroup.addBinding(binding);

        lblStandortLetzteAenderung.setText("Letze Änderung:"); // NOI18N

        dapStandortLetzteAenderung.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.letzteAenderung}"),
                dapStandortLetzteAenderung,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setValidator(new DateValidator());
        bindingGroup.addBinding(binding);

        lblStandortUnterhalt.setText("Unterhalt:"); // NOI18N

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

        lblStandortMontagefirma.setText("Montagefirma:"); // NOI18N

        txfStandortMontagefirma.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.montagefirma}"),
                txfStandortMontagefirma,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setValidator(new StringMaxLengthValidator());
        bindingGroup.addBinding(binding);

        lblStandortBemerkung.setText("Bemerkung:"); // NOI18N

        cboStandortVerrechnungseinheit.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.verrechnungseinheit}"),
                cboStandortVerrechnungseinheit,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        cboStandortVerrechnungseinheit.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cboStandortVerrechnungseinheitActionPerformed(evt);
                }
            });

        txaStandortBemerkung.setColumns(20);
        txaStandortBemerkung.setRows(5);
        txaStandortBemerkung.setEnabled(false);
        txaStandortBemerkung.setPreferredSize(new java.awt.Dimension(50, 50));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.bemerkungen}"),
                txaStandortBemerkung,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setValidator(new StringMaxLengthValidator());
        bindingGroup.addBinding(binding);

        scpStandortBemerkung.setViewportView(txaStandortBemerkung);

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

        lblStandortStrassenschluessel.setText("Straßenschlüssel:"); // NOI18N

        lblStandortKenziffer.setText("Kennziffer:"); // NOI18N

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

        txfStandortLaufendenummer.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.laufendeNummer}"),
                txfStandortLaufendenummer,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        lblStandortLaufendenummer.setText("Laufende Nr.:"); // NOI18N

        lblStandortVerrechnungseinheit.setText("V-Einheit:");                 // NOI18N
        lblStandortVerrechnungseinheit.setToolTipText("Verrechnungseinheit"); // NOI18N

        lblStandortStandortangabe.setText("Standortangabe:"); // NOI18N

        txfStandortStandortAngabe.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.standortangabe}"),
                txfStandortStandortAngabe,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setValidator(new StringMaxLengthValidator());
        bindingGroup.addBinding(binding);

        txfStandortStandortAngabe.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    txfStandortStandortAngabeActionPerformed(evt);
                }
            });

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

        final javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                        lblStandortLaufendenummer).addGroup(
                        javax.swing.GroupLayout.Alignment.TRAILING,
                        jPanel1Layout.createSequentialGroup().addGroup(
                            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                                lblStandortStandortangabe).addComponent(lblStandortMastart).addComponent(
                                lblStandortMasttyp).addComponent(
                                lblStandortKlassifizierung,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE).addComponent(lblStandortUnterhalt).addComponent(
                                lblStandortMastschutz,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE).addComponent(lblStandortInbetriebnahme).addComponent(
                                lblStandortLetzteAenderung).addComponent(
                                lblStandortMastanstrich,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE).addComponent(lblStandortMontagefirma).addComponent(
                                lblStandortHausnummer).addComponent(lblStandortVerrechnungseinheit).addComponent(
                                lblStandortBemerkung,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE).addComponent(lblStandortPLZ).addComponent(
                                lblStandortStrassenschluessel).addComponent(lblStandortKenziffer).addComponent(
                                lblStandortStadtbezirk)).addPreferredGap(
                            javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                                cbxStandortStadtbezirk,
                                javax.swing.GroupLayout.Alignment.TRAILING,
                                0,
                                299,
                                Short.MAX_VALUE).addComponent(cbxStandortKennziffer, 0, 299, Short.MAX_VALUE)
                                        .addComponent(
                                            txfStandortLaufendenummer,
                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                            299,
                                            Short.MAX_VALUE).addComponent(
                                txtStandortPLZ,
                                javax.swing.GroupLayout.Alignment.TRAILING,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                299,
                                Short.MAX_VALUE).addComponent(
                                scpStandortBemerkung,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                299,
                                Short.MAX_VALUE).addComponent(cbxStandortMastart, 0, 299, Short.MAX_VALUE).addComponent(
                                cbxStandortMasttyp,
                                0,
                                299,
                                Short.MAX_VALUE).addComponent(cbxStandortKlassifizierung, 0, 299, Short.MAX_VALUE)
                                        .addComponent(cbxStandortUnterhalt, 0, 299, Short.MAX_VALUE).addComponent(
                                dapStandortMastschutz,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                212,
                                Short.MAX_VALUE).addComponent(
                                dapStandortInbetriebnahme,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                299,
                                Short.MAX_VALUE).addComponent(
                                dapStandortLetzteAenderung,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                299,
                                Short.MAX_VALUE).addComponent(
                                dapStandortMastanstrich,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                299,
                                Short.MAX_VALUE).addComponent(
                                txfStandortMontagefirma,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                299,
                                Short.MAX_VALUE).addComponent(
                                cboStandortVerrechnungseinheit,
                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                163,
                                javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(
                                txfStandortHausnummer,
                                javax.swing.GroupLayout.Alignment.TRAILING,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                299,
                                Short.MAX_VALUE).addComponent(
                                txfStandortStandortAngabe,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                299,
                                Short.MAX_VALUE).addGroup(
                                jPanel1Layout.createSequentialGroup().addComponent(
                                    cbxStandortStrassenschluesselNr,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    102,
                                    javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(
                                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
                                    cbxStandortStrassenschluessel,
                                    0,
                                    191,
                                    Short.MAX_VALUE))))).addContainerGap()));

        jPanel1Layout.linkSize(
            javax.swing.SwingConstants.HORIZONTAL,
            new java.awt.Component[] {
                lblStandortBemerkung,
                lblStandortHausnummer,
                lblStandortInbetriebnahme,
                lblStandortKlassifizierung,
                lblStandortLetzteAenderung,
                lblStandortMastanstrich,
                lblStandortMastart,
                lblStandortMastschutz,
                lblStandortMasttyp,
                lblStandortMontagefirma,
                lblStandortPLZ,
                lblStandortStadtbezirk,
                lblStandortUnterhalt
            });

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblStandortStrassenschluessel).addComponent(
                        cbxStandortStrassenschluesselNr,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        19,
                        javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(
                        cbxStandortStrassenschluessel,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        21,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblStandortKenziffer).addComponent(
                        cbxStandortKennziffer,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        21,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblStandortLaufendenummer).addComponent(
                        txfStandortLaufendenummer,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        22,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblStandortStadtbezirk).addComponent(
                        cbxStandortStadtbezirk,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        21,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblStandortPLZ).addComponent(
                        txtStandortPLZ,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        22,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblStandortStandortangabe).addComponent(
                        txfStandortStandortAngabe,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        22,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblStandortHausnummer).addComponent(
                        txfStandortHausnummer,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        22,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblStandortMastart).addComponent(
                        cbxStandortMastart,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        22,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblStandortMasttyp).addComponent(
                        cbxStandortMasttyp,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        22,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblStandortKlassifizierung).addComponent(cbxStandortKlassifizierung, 0, 22, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblStandortUnterhalt).addComponent(
                        cbxStandortUnterhalt,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        22,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblStandortMastschutz).addComponent(
                        dapStandortMastschutz,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        22,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblStandortInbetriebnahme).addComponent(
                        dapStandortInbetriebnahme,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        22,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblStandortLetzteAenderung).addComponent(
                        dapStandortLetzteAenderung,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        22,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblStandortMastanstrich).addComponent(
                        dapStandortMastanstrich,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        22,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblStandortMontagefirma).addComponent(
                        txfStandortMontagefirma,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        22,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(
                        cboStandortVerrechnungseinheit).addComponent(lblStandortVerrechnungseinheit)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                        scpStandortBemerkung,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        151,
                        javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(lblStandortBemerkung)).addContainerGap()));

        jPanel1Layout.linkSize(
            javax.swing.SwingConstants.VERTICAL,
            new java.awt.Component[] {
                cboStandortVerrechnungseinheit,
                cbxStandortKennziffer,
                cbxStandortKlassifizierung,
                cbxStandortMastart,
                cbxStandortMasttyp,
                cbxStandortStadtbezirk,
                cbxStandortStrassenschluessel,
                cbxStandortUnterhalt,
                dapStandortInbetriebnahme,
                dapStandortLetzteAenderung,
                dapStandortMastanstrich,
                dapStandortMastschutz,
                txfStandortHausnummer,
                txfStandortLaufendenummer,
                txfStandortMontagefirma,
                txtStandortPLZ
            });

        jPanel1Layout.linkSize(
            javax.swing.SwingConstants.VERTICAL,
            new java.awt.Component[] {
                lblStandortBemerkung,
                lblStandortHausnummer,
                lblStandortInbetriebnahme,
                lblStandortKlassifizierung,
                lblStandortLetzteAenderung,
                lblStandortMastanstrich,
                lblStandortMastart,
                lblStandortMastschutz,
                lblStandortMasttyp,
                lblStandortMontagefirma,
                lblStandortPLZ,
                lblStandortStadtbezirk,
                lblStandortUnterhalt
            });

        final javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                javax.swing.GroupLayout.Alignment.TRAILING,
                layout.createSequentialGroup().addContainerGap().addGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(
                        jPanel1,
                        javax.swing.GroupLayout.Alignment.LEADING,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        Short.MAX_VALUE).addComponent(
                        sprStandort,
                        javax.swing.GroupLayout.Alignment.LEADING,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        448,
                        Short.MAX_VALUE).addComponent(lblStandort, javax.swing.GroupLayout.Alignment.LEADING))
                            .addContainerGap()));
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                layout.createSequentialGroup().addContainerGap().addComponent(lblStandort).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
                    sprStandort,
                    javax.swing.GroupLayout.PREFERRED_SIZE,
                    10,
                    javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
                    jPanel1,
                    javax.swing.GroupLayout.PREFERRED_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap(
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE)));

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     */
    private void initComponentToLabelMap() {
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
    }

    /**
     * DOCUMENT ME!
     */
    @Override
    final void initPanel() {
        createSortedCBoxModelFromCollection(allStrassenschluessel, cbxStandortStrassenschluessel);
        bindingGroup.addBindingListener(new BindingListener() {

                @Override
                public void bindingBecameBound(final Binding binding) {
                    // log.debug("binding became bound");
                }

                @Override
                public void bindingBecameUnbound(final Binding binding) {
                    // log.debug("binding became unbound");
                }

                @Override
                public void syncWarning(final Binding binding, final Binding.SyncFailure failure) {
                }

                @Override
                public void syncFailed(final Binding binding, final Binding.SyncFailure failure) {
                    // log.debug("syncFailed");
                    final Object target = binding.getTargetObject();
                    // log.debug("Target: " + target);
                    if (target instanceof JComponent) { // && !(target instanceof JComboBox)) {
                        final JComponent c = (JComponent)target;
                        final JComponent associatedLabel = componentToLabelMap.get(c);
                        if (associatedLabel != null) {
                            associatedLabel.setForeground(Color.red);
                        } else {
                            c.setForeground(Color.red);
                        }
                        try {
                            if (associatedLabel != null) {
                                associatedLabel.setToolTipText(failure.getValidationResult().getDescription());
                            }
                            c.setToolTipText(failure.getValidationResult().getDescription());
                        } catch (Exception ex) {
                            // log.debug("Error while setting tooltip", ex);
                            c.setToolTipText(null);
                        }
                    } else {
                        LOG.error("keine JCOmponent");
                    }
                    if ((currentEntity instanceof TdtaStandortMastCustomBean)
                                && StandortPanel.getInstance().isAncestorOf((Component)binding.getTargetObject())) {
                        validationState.add(binding);
                        // log.debug("Validation state changed. Errorcount: "+validationState.size());
                    }
                    // ToDo message with complete text to user;
                    // lblStrassenschluesselValidation.setIcon(BelisIcons.icoCancel22);
                }

                @Override
                public void synced(final Binding binding) {
                    // log.debug("synced: source: "+binding.getSourceObject()+" target: "+binding.getTargetObject(),new
                    // CurrentStackTrace()); log.debug("sync: " + cbxLeuchteStrassenschluessel.getSelectedItem());
                    // lblStrassenschluesselValidation.setIcon(BelisIcons.icoAccept22);
                    final Object target = binding.getTargetObject();
                    if (target instanceof JComponent) { // && !(target instanceof JComboBox)) {
                        final JComponent c = (JComponent)target;
                        final JComponent associatedLabel = componentToLabelMap.get(c);
                        if (associatedLabel != null) {
                            associatedLabel.setForeground(Color.black);
                        } else {
                            c.setForeground(Color.black);
                        }
                        c.setToolTipText(null);
                    } else {
                        LOG.error("keine JCOmponent");
                    }
                    if ((currentEntity instanceof TdtaStandortMastCustomBean)
                                && StandortPanel.getInstance().isAncestorOf((Component)binding.getTargetObject())) {
                        validationState.remove(binding);
                        // log.debug("Validation state changed. Errorcount: "+validationState.size());
                    }
//                String bindingName = binding.getName();
//                if (bindingName != null) {
//                    Collection<String> additionalValidationBindings = validationDependencies.get(binding.getName());
//                    log.debug("for binding " + bindingName + "-->> dependencies: " + additionalValidationBindings);
//                    if (additionalValidationBindings != null) {
//
//                        for (String name : additionalValidationBindings) {
//                            Binding b = bindingGroup.getBinding(name);
//                            if (b != 06694null) {
//                                log.debug("CheckAgain: " + name);
//                                b.saveAndNotify();
//                            }
//                        }
//                    }
//
//                }
                }

                @Override
                public void sourceChanged(final Binding binding, final PropertyStateEvent event) {
                }

                @Override
                public void targetChanged(final Binding binding, final PropertyStateEvent event) {
                }
            });
        cbxStandortStrassenschluessel.setSelectedItem(null);
        AutoCompleteDecorator.decorate(cbxStandortStrassenschluessel, new ObjectToKeyStringConverter());
//        cbxStandortStrassenschluessel.setEditable(true);
//        cbxStandortStrassenschluessel.setEditor(new AutoCompleteEditor(cbxStandortStrassenschluessel, false, new ObjectToKeyStringConverter()));
        cbxStandortStrassenschluesselNr.setSelectedItem(null);
        createSortedCBoxModelFromCollection(allStrassenschluessel, cbxStandortStrassenschluesselNr);
        AutoCompleteDecorator.decorate(cbxStandortStrassenschluesselNr, new ObjectToPkConverter("pk"));
        try {
            final Collection<TkeyKennzifferCustomBean> kennziffern = CidsBroker.getInstance().getAllKennziffer();
            createSortedCBoxModelFromCollection(kennziffern, cbxStandortKennziffer);
        } catch (ActionNotSuccessfulException ex) {
            cbxStandortKennziffer.setModel(new DefaultComboBoxModel());
        }
        cbxStandortKennziffer.setSelectedItem(null);
        try {
            final Collection<TkeyBezirkCustomBean> bezirke = CidsBroker.getInstance().getAllStadtbezirke();
            createSortedCBoxModelFromCollection(bezirke, cbxStandortStadtbezirk);
        } catch (ActionNotSuccessfulException ex) {
            cbxStandortStadtbezirk.setModel(new DefaultComboBoxModel());
        }
        try {
            final Collection<TkeyKlassifizierungCustomBean> klassifizierungen = CidsBroker.getInstance()
                        .getAllKlassifizierungen();
            createSortedCBoxModelFromCollection(klassifizierungen, cbxStandortKlassifizierung);
        } catch (ActionNotSuccessfulException ex) {
            cbxStandortKlassifizierung.setModel(new DefaultComboBoxModel());
        }
        try {
            final Collection<TkeyMastartCustomBean> mastarten = CidsBroker.getInstance().getAllMastarten();
            createSortedCBoxModelFromCollection(mastarten, cbxStandortMastart);
        } catch (ActionNotSuccessfulException ex) {
            cbxStandortMastart.setModel(new DefaultComboBoxModel());
        }
        try {
            final Collection<TkeyMasttypCustomBean> masttypen = CidsBroker.getInstance().getAllMasttypen();
            createSortedCBoxModelFromCollection(masttypen, cbxStandortMasttyp);
        } catch (ActionNotSuccessfulException ex) {
            cbxStandortMasttyp.setModel(new DefaultComboBoxModel());
        }
        AutoCompleteDecorator.decorate(cbxStandortMasttyp, new ObjectToKeyStringConverter());
        try {
            final Collection<TkeyUnterhMastCustomBean> unterhaltMast = CidsBroker.getInstance().getAllUnterhaltMast();
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
            createSortedCBoxModelFromCollection(unterhaltMast, cbxStandortUnterhalt);
        } catch (ActionNotSuccessfulException ex) {
            cbxStandortUnterhalt.setModel(new DefaultComboBoxModel());
        }
        cbxStandortStadtbezirk.setSelectedItem(null);
        cbxStandortKlassifizierung.setSelectedItem(null);
        cbxStandortMastart.setSelectedItem(null);
        cbxStandortMasttyp.setSelectedItem(null);
        cbxStandortUnterhalt.setSelectedItem(null);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cboStandortVerrechnungseinheitActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cboStandortVerrechnungseinheitActionPerformed
// TODO add your handling code here:
    } //GEN-LAST:event_cboStandortVerrechnungseinheitActionPerformed

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
    private void txfStandortStandortAngabeActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_txfStandortStandortAngabeActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_txfStandortStandortAngabeActionPerformed

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
