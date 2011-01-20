/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * EntityDetailWidget.java
 *
 * Created on 23. März 2009, 11:27
 */
package de.cismet.belis.gui.widget;

import org.apache.commons.collections.comparators.ReverseComparator;
import org.apache.log4j.Logger;

import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.Binding.SyncFailure;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.BindingListener;
import org.jdesktop.beansbinding.Converter;
import org.jdesktop.beansbinding.PropertyStateEvent;
import org.jdesktop.beansbinding.Validator;
import org.jdesktop.beansbinding.Validator.Result;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.autocomplete.ObjectToStringConverter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import java.beans.PropertyChangeEvent;

import java.text.ParseException;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.SpinnerDateModel;
import javax.swing.tree.TreePath;

import de.cismet.belis.broker.BelisBroker;
import de.cismet.belis.broker.EJBroker;

import de.cismet.belis.gui.documentpanel.DocumentPanel;

import de.cismet.belisEE.entity.Abzweigdose;
import de.cismet.belisEE.entity.Bauart;
import de.cismet.belisEE.entity.Doppelkommando;
import de.cismet.belisEE.entity.Energielieferant;
import de.cismet.belisEE.entity.Kennziffer;
import de.cismet.belisEE.entity.Klassifizierung;
import de.cismet.belisEE.entity.Leitung;
import de.cismet.belisEE.entity.Leitungstyp;
import de.cismet.belisEE.entity.Leuchte;
import de.cismet.belisEE.entity.Leuchtentyp;
import de.cismet.belisEE.entity.Mastart;
import de.cismet.belisEE.entity.Masttyp;
import de.cismet.belisEE.entity.MaterialLeitung;
import de.cismet.belisEE.entity.MaterialMauerlasche;
import de.cismet.belisEE.entity.Mauerlasche;
import de.cismet.belisEE.entity.Querschnitt;
import de.cismet.belisEE.entity.Schaltstelle;
import de.cismet.belisEE.entity.Stadtbezirk;
import de.cismet.belisEE.entity.Standort;
import de.cismet.belisEE.entity.Strassenschluessel;
import de.cismet.belisEE.entity.UnterhaltLeuchte;
import de.cismet.belisEE.entity.UnterhaltMast;

import de.cismet.belisEE.exception.ActionNotSuccessfulException;

import de.cismet.belisEE.util.BelisEEUtils;
import de.cismet.belisEE.util.CriteriaStringComparator;
import de.cismet.belisEE.util.EntityComparator;
import de.cismet.belisEE.util.LeuchteComparator;

import de.cismet.commons.architecture.broker.AdvancedPluginBroker;
import de.cismet.commons.architecture.widget.DefaultWidget;

import de.cismet.commons.server.entity.BaseEntity;
import de.cismet.commons.server.interfaces.DocumentContainer;

import de.cismet.tools.CurrentStackTrace;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public class DetailWidget extends DefaultWidget {

    //~ Static fields/initializers ---------------------------------------------

    public static final String PROP_CURRENT_ENTITY = "currentEntity";
    public static final Short ANZAHL_DK_NULL_VALUE = 0;
    private static final Calendar earliestDate = new GregorianCalendar(1950, Calendar.JANUARY, 1);
    private static final HashMap<JComponent, JComponent> componentToLabelMap = new HashMap<JComponent, JComponent>();

    //~ Instance fields --------------------------------------------------------

    protected Object currentEntity = null;

    private final Logger log = org.apache.log4j.Logger.getLogger(this.getClass());
    private Set<?> allStrassenschluessel;
    // ToDo configurable
    private int maxStringLength = 250;
    // private final GregorianCalendar calender = new GregorianCalendar();
    // private final Date smallestAllowedDate = new Date(0);
    private final String comboBoxNullValue = "Wert auswählen...";
    private Set<Binding> validationState = new HashSet<Binding>();
    private Set<Leitungstyp> leitungstypen = new HashSet<Leitungstyp>();
    private boolean isTriggerd = false;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox cboLeuchteVerrechnungseinheit;
    private javax.swing.JCheckBox cboLeuchteZaehler;
    private javax.swing.JCheckBox cboStandortVerrechnungseinheit;
    private javax.swing.JComboBox cbxLeitungLeitungstyp;
    private javax.swing.JComboBox cbxLeitungMaterial;
    private javax.swing.JComboBox cbxLeitungQuerschnitt;
    private javax.swing.JComboBox cbxLeuchteDoppelkommando1;
    private javax.swing.JComboBox cbxLeuchteDoppelkommando2;
    private javax.swing.JComboBox cbxLeuchteEnergielieferant;
    private javax.swing.JComboBox cbxLeuchteKennziffer;
    private javax.swing.JComboBox cbxLeuchteLeuchtentyp;
    private javax.swing.JComboBox cbxLeuchteStadtbezirk;
    private javax.swing.JComboBox cbxLeuchteStrassenschluessel;
    private javax.swing.JComboBox cbxLeuchteStrassenschluesselNr;
    private javax.swing.JComboBox cbxLeuchteUnterhalt;
    private javax.swing.JComboBox cbxMauerlascheMaterial;
    private javax.swing.JComboBox cbxMauerlascheStrassenschluessel;
    private javax.swing.JComboBox cbxMauerlascheStrassenschluesselNr;
    private javax.swing.JComboBox cbxSchaltstelleBauart;
    private javax.swing.JComboBox cbxSchaltstelleStrassenschluessel;
    private javax.swing.JComboBox cbxSchaltstelleStrassenschluesselNr;
    private javax.swing.JComboBox cbxStandortKennziffer;
    private javax.swing.JComboBox cbxStandortKlassifizierung;
    private javax.swing.JComboBox cbxStandortMastart;
    private javax.swing.JComboBox cbxStandortMasttyp;
    private javax.swing.JComboBox cbxStandortStadtbezirk;
    private javax.swing.JComboBox cbxStandortStrassenschluessel;
    private javax.swing.JComboBox cbxStandortStrassenschluesselNr;
    private javax.swing.JComboBox cbxStandortUnterhalt;
    private org.jdesktop.swingx.JXDatePicker dapLeuchteInbetriebnahme;
    private org.jdesktop.swingx.JXDatePicker dapMauerlascheErstellungsjahr;
    private org.jdesktop.swingx.JXDatePicker dapSchaltstelleErstellungsjahr;
    private org.jdesktop.swingx.JXDatePicker dapStandortInbetriebnahme;
    private org.jdesktop.swingx.JXDatePicker dapStandortLetzteAenderung;
    private org.jdesktop.swingx.JXDatePicker dapStandortMastanstrich;
    private org.jdesktop.swingx.JXDatePicker dapStandortMastschutz;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblAbzweigdose;
    private javax.swing.JLabel lblLeitungLeitungstyp;
    private javax.swing.JLabel lblLeitungMaterial;
    private javax.swing.JLabel lblLeitungQuerschnitt;
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
    private javax.swing.JLabel lblMauerlascheErstellungsjahr;
    private javax.swing.JLabel lblMauerlascheLaufendenummer;
    private javax.swing.JLabel lblMauerlascheMaterial;
    private javax.swing.JLabel lblMauerlascheStrassenschluessel;
    private javax.swing.JLabel lblSchaltstelleBauart;
    private javax.swing.JLabel lblSchaltstelleBemerkung;
    private javax.swing.JLabel lblSchaltstelleErstellungsjahr;
    private javax.swing.JLabel lblSchaltstelleHausnummer;
    private javax.swing.JLabel lblSchaltstelleLaufendenummer;
    private javax.swing.JLabel lblSchaltstelleNummer;
    private javax.swing.JLabel lblSchaltstelleStandortbezeichnung;
    private javax.swing.JLabel lblSchaltstelleStrassenschluessel;
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
    private javax.swing.JPanel panAbzweidose;
    private javax.swing.JPanel panContent;
    private javax.swing.JPanel panContent1;
    private de.cismet.belis.gui.documentpanel.DocumentPanel panDokumente;
    private javax.swing.JPanel panLeitung;
    private javax.swing.JPanel panLeuchte;
    private javax.swing.JPanel panMain;
    private javax.swing.JPanel panMauerlasche;
    private javax.swing.JPanel panSchaltstelle;
    private javax.swing.JPanel panStandort;
    private javax.swing.JScrollPane scpLeuchteBemerkung;
    private javax.swing.JScrollPane scpMain;
    private javax.swing.JScrollPane scpSchaltstelleBemerkung;
    private javax.swing.JScrollPane scpStandortBemerkung;
    private javax.swing.JSeparator sprLeitung;
    private javax.swing.JSeparator sprLeuchte;
    private javax.swing.JSeparator sprLeuchte1;
    private javax.swing.JSpinner sprLeuchteDoppelkommando1Anzahl;
    private javax.swing.JSpinner sprLeuchteDoppelkommando2Anzahl;
    private javax.swing.JSeparator sprMauerlasche;
    private javax.swing.JSeparator sprSchaltstelle;
    private javax.swing.JSeparator sprStandort;
    private javax.swing.JTextArea txaLeuchteBemerkung;
    private javax.swing.JTextArea txaSchaltstelleBemerkung;
    private javax.swing.JTextArea txaStandortBemerkung;
    private javax.swing.JTextField txfLeuchteLaufendenummer;
    private javax.swing.JTextField txfLeuchteMontagefirma;
    private javax.swing.JTextField txfLeuchteStandortAngabe;
    private javax.swing.JTextField txfMauerlascheLaufendenummer;
    private javax.swing.JTextField txfSchaltstelleHausnummer;
    private javax.swing.JTextField txfSchaltstelleLaufendenummer;
    private javax.swing.JTextField txfSchaltstelleNummer;
    private javax.swing.JTextField txfSchaltstelleStandortbezeichnung;
    private javax.swing.JTextField txfStandortHausnummer;
    private javax.swing.JTextField txfStandortLaufendenummer;
    private javax.swing.JTextField txfStandortMontagefirma;
    private javax.swing.JTextField txfStandortStandortAngabe;
    private javax.swing.JTextField txtLeuchteLeuchtennummer;
    private javax.swing.JTextField txtLeuchteRundsteuer;
    private javax.swing.JTextField txtLeuchteSchaltstelle;
    private javax.swing.JTextField txtStandortPLZ;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new DetailWidget object.
     *
     * @param  broker  DOCUMENT ME!
     */
    public DetailWidget(final AdvancedPluginBroker broker) {
        super(broker);
        initComponents();
        initContent();
        // binding
        final org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.dokumente}"),
                panDokumente,
                org.jdesktop.beansbinding.BeanProperty.create("dokumente"));
        bindingGroup.addBinding(binding);
        bindingGroup.bind();
        // ToDo ugly workaround
        if ((leitungstypen != null) && (leitungstypen.size() > 0)) {
            for (final Leitungstyp curLeitungstyp : leitungstypen) {
                if (curLeitungstyp.getId().equals(1L)) {
                    ((BelisBroker)broker).setLastLeitungstyp(curLeitungstyp);
                }
            }
        }
        // panMain.add(panStandort,BorderLayout.CENTER);
        // setAllPanelsVisible(false);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if (log.isDebugEnabled()) {
            log.debug("PropertyChange: " + evt);
            log.debug("PropertyChange: " + evt.getPropertyName());
        }
        if (log.isDebugEnabled()) {
            log.debug("PropertyChange: " + evt.getOldValue());
        }
        if (log.isDebugEnabled()) {
            log.debug("PropertyChange: " + evt.getNewValue());
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void initContent() {
        try {
            allStrassenschluessel = EJBroker.getInstance().getAllStrassenschluessel();
            if (log.isDebugEnabled()) {
                log.debug("Strassenschluessel size: " + allStrassenschluessel.size());
            }
        } catch (Exception ex) {
            log.error("Error while initializing all strassenschlussel.");
            allStrassenschluessel = new HashSet();
        }
        initComponentToLabelHashMap();
        initLeuchtePanel();
        initStandortPanel();
        initLeitungPanel();
        initMauerlaschePanel();
        initSchaltstellePanel();
        initAbzweigdosePanel();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  col  DOCUMENT ME!
     * @param  box  DOCUMENT ME!
     */
    private void createSortedCBoxModelFromCollection(final Collection<?> col, final JComboBox box) {
        if (log.isDebugEnabled()) {
            log.debug("sorting collection: " + col);
        }
        try {
            if (box != null) {
                if (col != null) {
                    final Object[] objArr = col.toArray();
                    Arrays.sort(objArr, CriteriaStringComparator.getInstance());
                    if (log.isDebugEnabled()) {
                        log.debug("sorted Collection:" + objArr);
                    }
                    box.setModel(new DefaultComboBoxModel(objArr));
                } else {
                    box.setModel(new DefaultComboBoxModel());
                }
            }
        } catch (Exception ex) {
            log.error("error while sorting collection", ex);
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void initLeitungPanel() {
        try {
            final Set<MaterialLeitung> material = EJBroker.getInstance().getAllMaterialLeitung();
            createSortedCBoxModelFromCollection(material, cbxLeitungMaterial);
        } catch (ActionNotSuccessfulException ex) {
            cbxLeitungMaterial.setModel(new DefaultComboBoxModel());
        }
        cbxLeitungMaterial.setSelectedItem(null);

        try {
            leitungstypen = EJBroker.getInstance().getAllLeitungstypen();
            createSortedCBoxModelFromCollection(leitungstypen, cbxLeitungLeitungstyp);
        } catch (ActionNotSuccessfulException ex) {
            cbxLeitungLeitungstyp.setModel(new DefaultComboBoxModel());
        }
        cbxLeitungLeitungstyp.setSelectedItem(null);

        try {
            final Set<Querschnitt> querschnitt = EJBroker.getInstance().getAllQuerschnitte();
            createSortedCBoxModelFromCollection(querschnitt, cbxLeitungQuerschnitt);
        } catch (ActionNotSuccessfulException ex) {
            cbxLeitungQuerschnitt.setModel(new DefaultComboBoxModel());
        }
        cbxLeitungQuerschnitt.setSelectedItem(null);
    }

    /**
     * DOCUMENT ME!
     */
    private void initAbzweigdosePanel() {
    }

    /**
     * DOCUMENT ME!
     */
    private void initLeuchtePanel() {
        createSortedCBoxModelFromCollection(allStrassenschluessel, cbxLeuchteStrassenschluessel);
        cbxLeuchteStrassenschluessel.setSelectedItem(null);
        AutoCompleteDecorator.decorate(cbxLeuchteStrassenschluessel, new ObjectToKeyStringConverter());
        createSortedCBoxModelFromCollection(allStrassenschluessel, cbxLeuchteStrassenschluesselNr);
        cbxLeuchteStrassenschluesselNr.setSelectedItem(null);
        AutoCompleteDecorator.decorate(cbxLeuchteStrassenschluesselNr, new ObjectToIDConverter());

        try {
            final Set<Kennziffer> kennziffern = EJBroker.getInstance().getAllKennziffer();
            createSortedCBoxModelFromCollection(kennziffern, cbxLeuchteKennziffer);
        } catch (ActionNotSuccessfulException ex) {
            cbxLeuchteKennziffer.setModel(new DefaultComboBoxModel());
        }
        cbxLeuchteKennziffer.setSelectedItem(null);
        try {
            final Set<Energielieferant> lieferanten = EJBroker.getInstance().getAllEnergielieferanten();
            createSortedCBoxModelFromCollection(lieferanten, cbxLeuchteEnergielieferant);
        } catch (ActionNotSuccessfulException ex) {
            cbxLeuchteEnergielieferant.setModel(new DefaultComboBoxModel());
        }
        try {
            final Set<UnterhaltLeuchte> unterhalt = EJBroker.getInstance().getAllUnterhaltLeuchte();
            try {
                if ((unterhalt != null) && (unterhalt.size() > 0)) {
                    for (final UnterhaltLeuchte curUnterhaltLeuchte : unterhalt) {
                        if (log.isDebugEnabled()) {
                            log.debug("Leuchte.DEFAULT_UNTERHALT " + Leuchte.DEFAULT_UNTERHALT);
                        }
                        if (Leuchte.DEFAULT_UNTERHALT.equals(curUnterhaltLeuchte)
                                    && Leuchte.DEFAULT_UNTERHALT.getUnterhaltspflichtigeLeuchte().equals(
                                        curUnterhaltLeuchte.getUnterhaltspflichtigeLeuchte())) {
                            if (log.isDebugEnabled()) {
                                log.debug("Setting defaultUnterhaltLeuchte to: " + curUnterhaltLeuchte);
                            }
                            ((BelisBroker)broker).setDefaultUnterhaltLeuchte(curUnterhaltLeuchte);
                        }
                    }
                }
            } catch (Exception ex) {
                log.warn("Error while determining default UnterhaltLeuchte: ", ex);
            }
            createSortedCBoxModelFromCollection(unterhalt, cbxLeuchteUnterhalt);
        } catch (ActionNotSuccessfulException ex) {
            cbxLeuchteUnterhalt.setModel(new DefaultComboBoxModel());
        }
        try {
            final Set<Leuchtentyp> leuchtentypen = EJBroker.getInstance().getAllLeuchtentypen();
            createSortedCBoxModelFromCollection(leuchtentypen, cbxLeuchteLeuchtentyp);
        } catch (ActionNotSuccessfulException ex) {
            cbxLeuchteLeuchtentyp.setModel(new DefaultComboBoxModel());
        }
        cbxLeuchteLeuchtentyp.setSelectedItem(null);
        try {
            final Set<Doppelkommando> dk1 = EJBroker.getInstance().getAllDoppelkommando();
            try {
                if ((dk1 != null) && (dk1.size() > 0)) {
                    for (final Doppelkommando curDoppelkommando : dk1) {
                        if (log.isDebugEnabled()) {
                            log.debug("Leuchte.DEFAULT_DOPPELKOMMANDO " + Leuchte.DEFAULT_DOPPELKOMMANDO);
                        }
                        if (Leuchte.DEFAULT_DOPPELKOMMANDO.equals(curDoppelkommando)
                                    && Leuchte.DEFAULT_DOPPELKOMMANDO.getBeschreibung().equals(
                                        curDoppelkommando.getBeschreibung())) {
                            if (log.isDebugEnabled()) {
                                log.debug("Setting defaultUnterhaltLeuchte to: " + curDoppelkommando);
                            }
                            ((BelisBroker)broker).setDefaultDoppelkommando1(curDoppelkommando);
                        }
                    }
                }
            } catch (Exception ex) {
                log.warn("Error while determining default Doppelkommando1: ", ex);
            }
            createSortedCBoxModelFromCollection(dk1, cbxLeuchteDoppelkommando1);
        } catch (ActionNotSuccessfulException ex) {
            cbxLeuchteDoppelkommando1.setModel(new DefaultComboBoxModel());
        }
        cbxLeuchteDoppelkommando1.setSelectedItem(null);
        try {
            final Set<Doppelkommando> dk2 = EJBroker.getInstance().getAllDoppelkommando();
            createSortedCBoxModelFromCollection(dk2, cbxLeuchteDoppelkommando2);
        } catch (ActionNotSuccessfulException ex) {
            cbxLeuchteDoppelkommando2.setModel(new DefaultComboBoxModel());
        }
        cbxLeuchteDoppelkommando2.setSelectedItem(null);
        cbxLeuchteUnterhalt.setSelectedItem(null);
        cbxLeuchteEnergielieferant.setSelectedItem(null);

        // Virtual properties
        try {
            final Set<Stadtbezirk> bezirke = EJBroker.getInstance().getAllStadtbezirke();
            createSortedCBoxModelFromCollection(bezirke, cbxLeuchteStadtbezirk);
        } catch (ActionNotSuccessfulException ex) {
            cbxLeuchteStadtbezirk.setModel(new DefaultComboBoxModel());
        }
        cbxLeuchteStadtbezirk.setSelectedItem(null);
    }

    /**
     * DOCUMENT ME!
     */
    private void initMauerlaschePanel() {
        createSortedCBoxModelFromCollection(allStrassenschluessel, cbxMauerlascheStrassenschluessel);
        cbxMauerlascheStrassenschluessel.setSelectedItem(null);
        AutoCompleteDecorator.decorate(cbxMauerlascheStrassenschluessel, new ObjectToKeyStringConverter());
        createSortedCBoxModelFromCollection(allStrassenschluessel, cbxMauerlascheStrassenschluesselNr);
        cbxMauerlascheStrassenschluesselNr.setSelectedItem(null);
        AutoCompleteDecorator.decorate(cbxMauerlascheStrassenschluesselNr, new ObjectToIDConverter());
        try {
            final Set<MaterialMauerlasche> material = EJBroker.getInstance().getAllMaterialMauerlasche();
            createSortedCBoxModelFromCollection(material, cbxMauerlascheMaterial);
        } catch (ActionNotSuccessfulException ex) {
            cbxMauerlascheMaterial.setModel(new DefaultComboBoxModel());
        }
        cbxMauerlascheMaterial.setSelectedItem(null);
    }

    /**
     * DOCUMENT ME!
     */
    private void initSchaltstellePanel() {
        createSortedCBoxModelFromCollection(allStrassenschluessel, cbxSchaltstelleStrassenschluessel);
        cbxSchaltstelleStrassenschluessel.setSelectedItem(null);
        AutoCompleteDecorator.decorate(cbxSchaltstelleStrassenschluessel, new ObjectToKeyStringConverter());
        createSortedCBoxModelFromCollection(allStrassenschluessel, cbxSchaltstelleStrassenschluesselNr);
        cbxSchaltstelleStrassenschluesselNr.setSelectedItem(null);
        AutoCompleteDecorator.decorate(cbxSchaltstelleStrassenschluesselNr, new ObjectToIDConverter());
        try {
            final Set<Bauart> bauarten = EJBroker.getInstance().getAllBauarten();
            createSortedCBoxModelFromCollection(bauarten, cbxSchaltstelleBauart);
        } catch (ActionNotSuccessfulException ex) {
            cbxSchaltstelleBauart.setModel(new DefaultComboBoxModel());
        }
        cbxSchaltstelleBauart.setSelectedItem(null);
    }

    /**
     * DOCUMENT ME!
     */
    private void initStandortPanel() {
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
                public void syncWarning(final Binding binding, final SyncFailure failure) {
                }

                @Override
                public void syncFailed(final Binding binding, final SyncFailure failure) {
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
                        log.error("keine JCOmponent");
                    }
                    if ((currentEntity instanceof Standort)
                                && panStandort.isAncestorOf((Component)binding.getTargetObject())) {
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
                        log.error("keine JCOmponent");
                    }
                    if ((currentEntity instanceof Standort)
                                && panStandort.isAncestorOf((Component)binding.getTargetObject())) {
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
        AutoCompleteDecorator.decorate(cbxStandortStrassenschluesselNr, new ObjectToIDConverter());
        try {
            final Set<Kennziffer> kennziffern = EJBroker.getInstance().getAllKennziffer();
            createSortedCBoxModelFromCollection(kennziffern, cbxStandortKennziffer);
        } catch (ActionNotSuccessfulException ex) {
            cbxStandortKennziffer.setModel(new DefaultComboBoxModel());
        }
        cbxStandortKennziffer.setSelectedItem(null);
        try {
            final Set<Stadtbezirk> bezirke = EJBroker.getInstance().getAllStadtbezirke();
            createSortedCBoxModelFromCollection(bezirke, cbxStandortStadtbezirk);
        } catch (ActionNotSuccessfulException ex) {
            cbxStandortStadtbezirk.setModel(new DefaultComboBoxModel());
        }
        try {
            final Set<Klassifizierung> klassifizierungen = EJBroker.getInstance().getAllKlassifizierungen();
            createSortedCBoxModelFromCollection(klassifizierungen, cbxStandortKlassifizierung);
        } catch (ActionNotSuccessfulException ex) {
            cbxStandortKlassifizierung.setModel(new DefaultComboBoxModel());
        }
        try {
            final Set<Mastart> mastarten = EJBroker.getInstance().getAllMastarten();
            createSortedCBoxModelFromCollection(mastarten, cbxStandortMastart);
        } catch (ActionNotSuccessfulException ex) {
            cbxStandortMastart.setModel(new DefaultComboBoxModel());
        }
        try {
            final Set<Masttyp> masttypen = EJBroker.getInstance().getAllMasttypen();
            createSortedCBoxModelFromCollection(masttypen, cbxStandortMasttyp);
        } catch (ActionNotSuccessfulException ex) {
            cbxStandortMasttyp.setModel(new DefaultComboBoxModel());
        }
        AutoCompleteDecorator.decorate(cbxStandortMasttyp, new ObjectToKeyStringConverter());
        try {
            final Set<UnterhaltMast> unterhaltMast = EJBroker.getInstance().getAllUnterhaltMast();
            try {
                if ((unterhaltMast != null) && (unterhaltMast.size() > 0)) {
                    for (final UnterhaltMast curUnterhaltMast : unterhaltMast) {
                        if (Standort.DEFAULT_UNTERHALT.equals(curUnterhaltMast)
                                    && Standort.DEFAULT_UNTERHALT.getUnterhaltMast().equals(
                                        curUnterhaltMast.getUnterhaltMast())) {
                            if (log.isDebugEnabled()) {
                                log.debug("Setting defaultUnterhaltMast to: " + curUnterhaltMast);
                            }
                            ((BelisBroker)broker).setDefaultUnterhaltMast(curUnterhaltMast);
                        }
                    }
                }
            } catch (Exception ex) {
                log.warn("Error while determining default UnterhaltMast: ", ex);
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
     * @param  enabled  DOCUMENT ME!
     */
    private void setInheritedMastPropertiesEnabled(final boolean enabled) {
        cbxLeuchteStrassenschluessel.setEnabled(enabled);
        cbxLeuchteStrassenschluesselNr.setEnabled(enabled);
        cbxLeuchteKennziffer.setEnabled(enabled);
        lblLeuchteStandortangabe.setVisible(enabled);
        lblLeuchteVerrechnungseinheit.setVisible(enabled);
        lblLeuchteStadtbezirk.setVisible(enabled);
        cbxLeuchteStadtbezirk.setVisible(enabled);
        txfLeuchteStandortAngabe.setVisible(enabled);
        cboLeuchteVerrechnungseinheit.setVisible(enabled);
        cbxLeuchteStadtbezirk.setEnabled(enabled);
        txfLeuchteStandortAngabe.setEnabled(enabled);
        cboLeuchteVerrechnungseinheit.setEnabled(enabled);
    }

    /**
     * Get the value of currentEntity.
     *
     * @return  the value of currentEntity
     */
    public Object getCurrentEntity() {
        return currentEntity;
    }

    /**
     * Set the value of currentEntity TODO change parameter type to DocumentContainer or BaseEntity.
     *
     * @param  currentEntity  new value of currentEntity
     */
    public void setCurrentEntity(final Object currentEntity) {
        if (log.isDebugEnabled()) {
            log.debug("setCurrentEntity", new CurrentStackTrace());
        }
        final Object oldCurrentEntity = this.currentEntity;
        this.currentEntity = currentEntity;
        // Attention there is another block for the visiblity of the document panel
        // this must be here because the set must be created before it is set in the documentpanel
        if ((currentEntity != null) && (currentEntity instanceof DocumentContainer)
                    && (((DocumentContainer)currentEntity).getDokumente() == null)) {
            if (log.isDebugEnabled()) {
                log.debug("Entity is DocumentContainer and Set == null. Creating Set");
            }
            ((DocumentContainer)currentEntity).setDokumente(new TreeSet(
                    new ReverseComparator(new EntityComparator(new ReverseComparator(new LeuchteComparator())))));
        }
        firePropertyChange(PROP_CURRENT_ENTITY, oldCurrentEntity, currentEntity);
        bindingGroup.unbind();
        bindingGroup.bind();
        panMain.removeAll();
        // TODO: TESTING ONLY...to REMOVE!
        // panMain.add(docPanel, BorderLayout.SOUTH);
        setAllPanelsVisible(false);
        // panMain.removeAll();
        // panMain.remove(panStandort);
        // panMain.remove(panLeuchte);
        // panMain.setLayout(new BorderLayout());
        if (currentEntity == null) {
            if (log.isDebugEnabled()) {
                log.debug("current Entity is null");
            }
            panStandort.setVisible(false);
            panLeuchte.setVisible(false);
            this.repaint();
            return;
        }
        // validateTest();
        if (currentEntity instanceof Standort) {
            if (log.isDebugEnabled()) {
                log.debug("CurrentEntity is Standort");
            }
            // panMain.add(panStandort,BorderLayout.CENTER);
            panMain.add(panStandort, BorderLayout.CENTER);
            if (((Standort)currentEntity).getStrassenschluessel() == null) {
                cbxStandortStrassenschluessel.setSelectedItem(null);
                cbxSchaltstelleStrassenschluesselNr.setSelectedItem(null);
            }
            if (((Standort)currentEntity).getKennziffer() == null) {
                cbxStandortKennziffer.setSelectedItem(null);
            }
            panStandort.setVisible(true);
        } else if (currentEntity instanceof Leuchte) {
            if (log.isDebugEnabled()) {
                log.debug("CurrentEntity is Leuchte");
            }
            // panStandort.setVisible(true);
            // panMain.add(panLeuchte,BorderLayout.CENTER);
            panMain.add(panLeuchte, BorderLayout.CENTER);
            log.info("ParentNode: " + ((Leuchte)currentEntity).getStandort());
            if (((BelisBroker)broker).getWorkbenchWidget().isParentNodeMast(
                            ((BelisBroker)broker).getWorkbenchWidget().getSelectedTreeNode().getLastPathComponent())) {
                if (log.isDebugEnabled()) {
                    log.debug("ParentNode ist Mast");
                }
                setInheritedMastPropertiesEnabled(false);
            } else {
                if (broker.isInEditMode()) {
                    setInheritedMastPropertiesEnabled(true);
                }
            }
            panLeuchte.setVisible(true);
            if (((Leuchte)currentEntity).getStrassenschluessel() == null) {
                cbxLeuchteStrassenschluessel.setSelectedItem(null);
            }
            if (((Leuchte)currentEntity).getKennziffer() == null) {
                cbxLeuchteKennziffer.setSelectedItem(null);
            }
//            docPanel.setFileList();
        } else if (currentEntity instanceof Leitung) {
            if (log.isDebugEnabled()) {
                log.debug("CurrentEntity is Leitung");
            }
            panMain.add(panLeitung, BorderLayout.CENTER);
            panLeitung.setVisible(true);
        } else if (currentEntity instanceof Abzweigdose) {
            if (log.isDebugEnabled()) {
                log.debug("CurrentEntity is Abzweigdose");
            }
            panMain.add(panAbzweidose, BorderLayout.CENTER);
            panLeitung.setVisible(true);
        } else if (currentEntity instanceof Mauerlasche) {
            if (log.isDebugEnabled()) {
                log.debug("CurrentEntity is Mauerlasche");
            }
            if (((Mauerlasche)currentEntity).getStrassenschluessel() == null) {
                cbxMauerlascheStrassenschluessel.setSelectedItem(null);
                cbxMauerlascheStrassenschluesselNr.setSelectedItem(null);
            }
            panMain.add(panMauerlasche, BorderLayout.CENTER);
            panMauerlasche.setVisible(true);
        } else if (currentEntity instanceof Schaltstelle) {
            if (log.isDebugEnabled()) {
                log.debug("CurrentEntity is Schaltstelle");
            }
            if (((Schaltstelle)currentEntity).getStrassenschluessel() == null) {
                cbxSchaltstelleStrassenschluessel.setSelectedItem(null);
                cbxSchaltstelleStrassenschluesselNr.setSelectedItem(null);
            }
            panMain.add(panSchaltstelle, BorderLayout.CENTER);
            panSchaltstelle.setVisible(true);
        } else {
            log.warn("no panel for entity available");
        }
        if (currentEntity instanceof DocumentContainer) {
            final DocumentContainer dc = (DocumentContainer)currentEntity;
            panMain.add(panDokumente, BorderLayout.SOUTH);
            panDokumente.setVisible(true);
        } else {
            panDokumente.setVisible(false);
        }
        this.repaint();
    }
    /**
     * private void validateTest() { for (Binding curBinding : bindingGroup.getBindings()) { if (currentEntity
     * instanceof Standort && panStandort.isAncestorOf((Component) curBinding.getTargetObject())) { log.debug("checking
     * binding"); if (curBinding.getValidator() != null) { try { log.debug("Validator available. Property to check: " +
     * curBinding.getTargetProperty()); log.debug("Property value: " +
     * curBinding.getTargetProperty().getValue(curBinding.getTargetObject())); final Result valResult =
     * curBinding.getValidator().validate(curBinding.getTargetProperty().getValue(curBinding.getTargetObject()));
     * log.debug("Validation result: " + valResult); if (valResult != null) { for (BindingListener bindingListener :
     * bindingGroup.getBindingListeners()) { bindingListener.syncFailed(curBinding,
     * SyncFailure.validationFailure(valResult)); } } } catch (Exception ex) { log.error("Error while validating: ",
     * ex); } } } } }
     *
     * @param  visible  DOCUMENT ME!
     */
    private void setAllPanelsVisible(final boolean visible) {
        panStandort.setVisible(visible);
        panLeuchte.setVisible(visible);
        panLeitung.setVisible(visible);
        panMauerlasche.setVisible(visible);
        panSchaltstelle.setVisible(visible);
    }

    @Override
    public void clearComponent() {
        super.clearComponent();
        setCurrentEntity(null);
        // setAllPanelsVisible(false);
    }

    @Override
    public void setWidgetEditable(final boolean isEditable) {
        super.setWidgetEditable(isEditable);
        // Standort fields
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

        // Leuchte fields
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
        if (!((((BelisBroker)broker).getWorkbenchWidget().getSelectedTreeNode() != null)
                        && ((BelisBroker)broker).getWorkbenchWidget().isParentNodeMast(
                            ((BelisBroker)broker).getWorkbenchWidget().getSelectedTreeNode().getLastPathComponent()))) {
            cbxLeuchteKennziffer.setEnabled(isEditable);
            cbxLeuchteStrassenschluessel.setEnabled(isEditable);
            cbxLeuchteStrassenschluesselNr.setEnabled(isEditable);
            cbxLeuchteStadtbezirk.setEnabled(isEditable);
            txfLeuchteStandortAngabe.setEnabled(isEditable);
            cboLeuchteVerrechnungseinheit.setEnabled(isEditable);
        }

        // Leitungs fields
        cbxLeitungLeitungstyp.setEnabled(isEditable);
        cbxLeitungMaterial.setEnabled(isEditable);
        cbxLeitungQuerschnitt.setEnabled(isEditable);

        // Mauerlasche fields
        cbxMauerlascheStrassenschluessel.setEnabled(isEditable);
        cbxMauerlascheStrassenschluesselNr.setEnabled(isEditable);
        cbxMauerlascheMaterial.setEnabled(isEditable);
        dapMauerlascheErstellungsjahr.setEnabled(isEditable);
        txfMauerlascheLaufendenummer.setEnabled(isEditable);

        // Schaltstelle fields
        cbxSchaltstelleStrassenschluessel.setEnabled(isEditable);
        cbxSchaltstelleStrassenschluesselNr.setEnabled(isEditable);
        cbxSchaltstelleBauart.setEnabled(isEditable);
        txfSchaltstelleHausnummer.setEnabled(isEditable);
        txfSchaltstelleLaufendenummer.setEnabled(isEditable);
        txfSchaltstelleNummer.setEnabled(isEditable);
        txfSchaltstelleStandortbezeichnung.setEnabled(isEditable);
        dapSchaltstelleErstellungsjahr.setEnabled(isEditable);
        txaSchaltstelleBemerkung.setEnabled(isEditable);

        // doc panel
        panDokumente.setEditable(isEditable);
    }

    /**
     * DOCUMENT ME!
     */
    private void initComponentToLabelHashMap() {
        componentToLabelMap.put(cboLeuchteZaehler, lblLeuchteZaehler);
        componentToLabelMap.put(cboStandortVerrechnungseinheit, lblStandortVerrechnungseinheit);
        componentToLabelMap.put(cbxLeitungLeitungstyp, lblLeitungLeitungstyp);
        componentToLabelMap.put(cbxLeitungMaterial, lblLeitungMaterial);
        componentToLabelMap.put(cbxLeitungQuerschnitt, lblLeitungQuerschnitt);
        componentToLabelMap.put(cbxLeuchteDoppelkommando1, lblLeuchteDoppelkommando1);
        componentToLabelMap.put(cbxLeuchteDoppelkommando2, lblLeuchteDoppelkommando2);
        componentToLabelMap.put(cbxLeuchteEnergielieferant, lblLeuchteEnergielieferant);
        componentToLabelMap.put(cbxLeuchteKennziffer, lblLeuchteKenziffer);
        componentToLabelMap.put(cbxLeuchteLeuchtentyp, lblLeuchteLeuchtentyp);
        componentToLabelMap.put(cbxLeuchteStrassenschluessel, lblLeuchteStrassenschluessel);
        componentToLabelMap.put(cbxLeuchteStrassenschluesselNr, lblLeuchteStrassenschluessel);
        componentToLabelMap.put(cbxLeuchteUnterhalt, lblLeuchteUnterhalt);
        componentToLabelMap.put(cbxMauerlascheMaterial, lblMauerlascheMaterial);
        componentToLabelMap.put(cbxMauerlascheStrassenschluessel, lblMauerlascheStrassenschluessel);
        componentToLabelMap.put(cbxMauerlascheStrassenschluesselNr, lblMauerlascheStrassenschluessel);
        componentToLabelMap.put(cbxSchaltstelleBauart, lblSchaltstelleBauart);
        componentToLabelMap.put(cbxSchaltstelleStrassenschluessel, lblSchaltstelleStrassenschluessel);
        componentToLabelMap.put(cbxSchaltstelleStrassenschluesselNr, lblSchaltstelleStrassenschluessel);
        componentToLabelMap.put(cbxStandortKennziffer, lblStandortKenziffer);
        componentToLabelMap.put(cbxStandortKlassifizierung, lblStandortKlassifizierung);
        componentToLabelMap.put(cbxStandortMastart, lblStandortMastart);
        componentToLabelMap.put(cbxStandortMasttyp, lblStandortMasttyp);
        componentToLabelMap.put(cbxStandortStadtbezirk, lblStandortStadtbezirk);
        componentToLabelMap.put(cbxStandortStrassenschluessel, lblStandortStrassenschluessel);
        componentToLabelMap.put(cbxStandortStrassenschluesselNr, lblStandortStrassenschluessel);
        componentToLabelMap.put(cbxStandortUnterhalt, lblStandortUnterhalt);
        componentToLabelMap.put(dapLeuchteInbetriebnahme, lblLeuchteInbetriebnahme);
        componentToLabelMap.put(dapMauerlascheErstellungsjahr, lblMauerlascheErstellungsjahr);
        componentToLabelMap.put(dapSchaltstelleErstellungsjahr, lblSchaltstelleErstellungsjahr);
        componentToLabelMap.put(dapStandortInbetriebnahme, lblStandortInbetriebnahme);
        componentToLabelMap.put(dapStandortLetzteAenderung, lblStandortLetzteAenderung);
        componentToLabelMap.put(dapStandortMastanstrich, lblStandortMastanstrich);
        componentToLabelMap.put(dapStandortMastschutz, lblStandortMastschutz);
        componentToLabelMap.put(sprLeuchteDoppelkommando1Anzahl, lblLeuchteDoppelkommando1);
        componentToLabelMap.put(sprLeuchteDoppelkommando2Anzahl, lblLeuchteDoppelkommando2);
        componentToLabelMap.put(txaLeuchteBemerkung, lblLeuchteBemerkung);
        componentToLabelMap.put(txaSchaltstelleBemerkung, lblSchaltstelleBemerkung);
        componentToLabelMap.put(txaStandortBemerkung, lblStandortBemerkung);
        componentToLabelMap.put(txfLeuchteLaufendenummer, lblLeuchteLaufendenummer);
        componentToLabelMap.put(txfLeuchteMontagefirma, lblLeuchteMontagefirma);
        componentToLabelMap.put(txfMauerlascheLaufendenummer, lblMauerlascheLaufendenummer);
        componentToLabelMap.put(txfSchaltstelleHausnummer, lblSchaltstelleHausnummer);
        componentToLabelMap.put(txfSchaltstelleLaufendenummer, lblSchaltstelleLaufendenummer);
        componentToLabelMap.put(txfSchaltstelleNummer, lblSchaltstelleNummer);
        componentToLabelMap.put(txfSchaltstelleStandortbezeichnung, lblSchaltstelleStandortbezeichnung);
        componentToLabelMap.put(txfStandortHausnummer, lblStandortHausnummer);
        componentToLabelMap.put(txfStandortLaufendenummer, lblStandortLaufendenummer);
        componentToLabelMap.put(txfStandortMontagefirma, lblStandortMontagefirma);
        componentToLabelMap.put(txtLeuchteLeuchtennummer, lblLeuchteLeuchtennummer);
        componentToLabelMap.put(txtLeuchteRundsteuer, lblLeuchteRundsteuer);
        componentToLabelMap.put(txtLeuchteSchaltstelle, lblLeuchteSchaltstelle);
        componentToLabelMap.put(txtStandortPLZ, lblStandortPLZ);
        componentToLabelMap.put(txfStandortStandortAngabe, lblStandortStandortangabe);
    }

    /**
     * DOCUMENT ME!
     */
    private void commitEdits() {
        if (currentEntity != null) {
            if (currentEntity instanceof Leuchte) {
                try {
                    dapLeuchteInbetriebnahme.getEditor().commitEdit();
                } catch (ParseException ex) {
                    log.warn("Error while commiting edits: " + ex);
                }
                try {
                    sprLeuchteDoppelkommando1Anzahl.commitEdit();
                } catch (ParseException ex) {
                    log.warn("Error while commiting edits: " + ex);
                }
                try {
                    sprLeuchteDoppelkommando2Anzahl.commitEdit();
                } catch (ParseException ex) {
                    log.warn("Error while commiting edits: " + ex);
                }
            } else if (currentEntity instanceof Standort) {
                try {
                    dapStandortInbetriebnahme.getEditor().commitEdit();
                } catch (ParseException ex) {
                    log.warn("Error while commiting edits: " + ex);
                }
                try {
                    dapStandortInbetriebnahme.getEditor().commitEdit();
                } catch (ParseException ex) {
                    log.warn("Error while commiting edits: " + ex);
                }
                try {
                    dapStandortLetzteAenderung.getEditor().commitEdit();
                } catch (ParseException ex) {
                    log.warn("Error while commiting edits: " + ex);
                }
                try {
                    dapStandortMastanstrich.getEditor().commitEdit();
                } catch (ParseException ex) {
                    log.warn("Error while commiting edits: " + ex);
                }
                try {
                    dapStandortMastschutz.getEditor().commitEdit();
                } catch (ParseException ex) {
                    log.warn("Error while commiting edits: " + ex);
                }
            } else if (currentEntity instanceof Schaltstelle) {
                try {
                    dapSchaltstelleErstellungsjahr.getEditor().commitEdit();
                } catch (ParseException ex) {
                    log.warn("Error while commiting edits: " + ex);
                }
            } else if (currentEntity instanceof Mauerlasche) {
                try {
                    dapMauerlascheErstellungsjahr.getEditor().commitEdit();
                } catch (ParseException ex) {
                    log.warn("Error while commiting edits: " + ex);
                }
            }
        }
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

        panMauerlasche = new javax.swing.JPanel();
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
        sprMauerlasche = new javax.swing.JSeparator();
        panSchaltstelle = new javax.swing.JPanel();
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
        panLeitung = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblLeitungMaterial = new javax.swing.JLabel();
        lblLeitungLeitungstyp = new javax.swing.JLabel();
        lblLeitungQuerschnitt = new javax.swing.JLabel();
        cbxLeitungLeitungstyp = new javax.swing.JComboBox();
        cbxLeitungMaterial = new javax.swing.JComboBox();
        cbxLeitungQuerschnitt = new javax.swing.JComboBox();
        sprLeitung = new javax.swing.JSeparator();
        panStandort = new javax.swing.JPanel();
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
        panLeuchte = new javax.swing.JPanel();
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
        panAbzweidose = new javax.swing.JPanel();
        panContent1 = new javax.swing.JPanel();
        lblAbzweigdose = new javax.swing.JLabel();
        sprLeuchte1 = new javax.swing.JSeparator();
        panDokumente = new de.cismet.belis.gui.documentpanel.DocumentPanel();
        scpMain = new javax.swing.JScrollPane();
        panMain = new javax.swing.JPanel();

        jLabel2.setFont(new java.awt.Font("DejaVu Sans", 1, 13));
        jLabel2.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/22/mauerlasche.png"))); // NOI18N
        jLabel2.setText("Mauerlasche");

        lblMauerlascheStrassenschluessel.setText("Stra\u00dfenschl\u00fcssel:");

        lblMauerlascheLaufendenummer.setText("Laufende Nr.:");

        lblMauerlascheErstellungsjahr.setText("Erstellungsjahr:");

        lblMauerlascheMaterial.setText("Material:");

        txfMauerlascheLaufendenummer.setEnabled(false);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.laufendeNummer}"),
                txfMauerlascheLaufendenummer,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        dapMauerlascheErstellungsjahr.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.erstellungsjahr}"),
                dapMauerlascheErstellungsjahr,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setValidator(new DateValidator());
        bindingGroup.addBinding(binding);

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
                    } else if (value instanceof MaterialMauerlasche) {
                        final MaterialMauerlasche mm = (MaterialMauerlasche)value;
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
                    } else if (value instanceof Strassenschluessel) {
                        final Strassenschluessel ss = (Strassenschluessel)value;
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
                    } else if (value instanceof Strassenschluessel) {
                        final Strassenschluessel ss = (Strassenschluessel)value;
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

        final javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                jPanel3Layout.createSequentialGroup().addContainerGap().addGroup(
                    jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                        lblMauerlascheStrassenschluessel).addComponent(lblMauerlascheLaufendenummer).addComponent(
                        lblMauerlascheErstellungsjahr).addComponent(lblMauerlascheMaterial)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                        dapMauerlascheErstellungsjahr,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        166,
                        Short.MAX_VALUE).addComponent(
                        txfMauerlascheLaufendenummer,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        166,
                        Short.MAX_VALUE).addComponent(cbxMauerlascheMaterial, 0, 166, Short.MAX_VALUE).addGroup(
                        jPanel3Layout.createSequentialGroup().addComponent(
                            cbxMauerlascheStrassenschluesselNr,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            77,
                            javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(
                            javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
                            cbxMauerlascheStrassenschluessel,
                            0,
                            83,
                            Short.MAX_VALUE))).addContainerGap()));
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                jPanel3Layout.createSequentialGroup().addContainerGap().addGroup(
                    jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblMauerlascheStrassenschluessel).addComponent(
                        cbxMauerlascheStrassenschluesselNr,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        19,
                        javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(
                        cbxMauerlascheStrassenschluessel,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        21,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblMauerlascheLaufendenummer).addComponent(
                        txfMauerlascheLaufendenummer,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        22,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblMauerlascheErstellungsjahr).addComponent(
                        dapMauerlascheErstellungsjahr,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        22,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false).addComponent(
                        cbxMauerlascheMaterial,
                        0,
                        0,
                        Short.MAX_VALUE).addComponent(
                        lblMauerlascheMaterial,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        Short.MAX_VALUE)).addContainerGap(25, Short.MAX_VALUE)));

        jPanel3Layout.linkSize(
            javax.swing.SwingConstants.VERTICAL,
            new java.awt.Component[] {
                cbxMauerlascheMaterial,
                dapMauerlascheErstellungsjahr,
                txfMauerlascheLaufendenummer
            });

        final javax.swing.GroupLayout panMauerlascheLayout = new javax.swing.GroupLayout(panMauerlasche);
        panMauerlasche.setLayout(panMauerlascheLayout);
        panMauerlascheLayout.setHorizontalGroup(
            panMauerlascheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                javax.swing.GroupLayout.Alignment.TRAILING,
                panMauerlascheLayout.createSequentialGroup().addContainerGap().addGroup(
                    panMauerlascheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(
                        jPanel3,
                        javax.swing.GroupLayout.Alignment.LEADING,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        Short.MAX_VALUE).addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                        sprMauerlasche,
                        javax.swing.GroupLayout.Alignment.LEADING,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        315,
                        Short.MAX_VALUE)).addContainerGap()));
        panMauerlascheLayout.setVerticalGroup(
            panMauerlascheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                panMauerlascheLayout.createSequentialGroup().addContainerGap().addComponent(jLabel2).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
                    sprMauerlasche,
                    javax.swing.GroupLayout.PREFERRED_SIZE,
                    10,
                    javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
                    jPanel3,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE).addContainerGap()));

        jLabel3.setFont(new java.awt.Font("DejaVu Sans", 1, 13));
        jLabel3.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/22/schaltstelle.png"))); // NOI18N
        jLabel3.setText("Schaltstelle");

        lblSchaltstelleStrassenschluessel.setText("Stra\u00dfenschl\u00fcssel:");

        lblSchaltstelleLaufendenummer.setText("Laufende Nr.:");

        lblSchaltstelleHausnummer.setText("Haus Nr.:");

        lblSchaltstelleNummer.setText("Schaltstellen Nr.:");

        lblSchaltstelleErstellungsjahr.setText("Erstellungsjahr:");

        lblSchaltstelleStandortbezeichnung.setText("Standortbezeichnung:");

        lblSchaltstelleBauart.setText("Bauart:");

        lblSchaltstelleBemerkung.setText("Bemerkung:");

        txfSchaltstelleLaufendenummer.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
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
                    } else if (value instanceof Bauart) {
                        final Bauart ba = (Bauart)value;
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
                    } else if (value instanceof Strassenschluessel) {
                        final Strassenschluessel ss = (Strassenschluessel)value;
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
                    } else if (value instanceof Strassenschluessel) {
                        final Strassenschluessel ss = (Strassenschluessel)value;
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
                        262,
                        Short.MAX_VALUE).addComponent(cbxSchaltstelleBauart, 0, 262, Short.MAX_VALUE).addComponent(
                        scpSchaltstelleBemerkung).addGroup(
                        jPanel4Layout.createSequentialGroup().addPreferredGap(
                            javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
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
                                Short.MAX_VALUE)))).addContainerGap()));
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

        final javax.swing.GroupLayout panSchaltstelleLayout = new javax.swing.GroupLayout(panSchaltstelle);
        panSchaltstelle.setLayout(panSchaltstelleLayout);
        panSchaltstelleLayout.setHorizontalGroup(
            panSchaltstelleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                javax.swing.GroupLayout.Alignment.TRAILING,
                panSchaltstelleLayout.createSequentialGroup().addContainerGap().addGroup(
                    panSchaltstelleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(
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
        panSchaltstelleLayout.setVerticalGroup(
            panSchaltstelleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                panSchaltstelleLayout.createSequentialGroup().addContainerGap().addComponent(jLabel3).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
                    sprSchaltstelle,
                    javax.swing.GroupLayout.PREFERRED_SIZE,
                    10,
                    javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
                    jPanel4,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    300,
                    Short.MAX_VALUE).addContainerGap()));

        jLabel1.setFont(new java.awt.Font("DejaVu Sans", 1, 13));
        jLabel1.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/22/leitung.png"))); // NOI18N
        jLabel1.setText("Leitung");

        lblLeitungMaterial.setText("Material:");

        lblLeitungLeitungstyp.setText("Leitungstyp:");

        lblLeitungQuerschnitt.setText("Querschnitt:");

        cbxLeitungLeitungstyp.setEnabled(false);
        cbxLeitungLeitungstyp.setRenderer(new DefaultListCellRenderer() {

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
                    } else if (value instanceof Leitungstyp) {
                        final Leitungstyp lt = (Leitungstyp)value;
                        setText(lt.getBezeichnung());
                    }
                    return this;
                }
            });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.leitungstyp}"),
                cbxLeitungLeitungstyp,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbxLeitungMaterial.setEnabled(false);
        cbxLeitungMaterial.setRenderer(new DefaultListCellRenderer() {

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
                    } else if (value instanceof MaterialLeitung) {
                        final MaterialLeitung mt = (MaterialLeitung)value;
                        setText(mt.getBezeichnung());
                    }
                    return this;
                }
            });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.material}"),
                cbxLeitungMaterial,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbxLeitungQuerschnitt.setEnabled(false);
        cbxLeitungQuerschnitt.setRenderer(new DefaultListCellRenderer() {

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
                    } else if (value instanceof Querschnitt) {
                        final Querschnitt qt = (Querschnitt)value;
                        if (qt.getGroesse() != null) {
                            setText(qt.getGroesse().toString());
                        } else {
                            setText("");
                        }
                    }
                    return this;
                }
            });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.querschnitt}"),
                cbxLeitungQuerschnitt,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        final javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                jPanel2Layout.createSequentialGroup().addContainerGap().addGroup(
                    jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                        lblLeitungLeitungstyp).addComponent(lblLeitungMaterial).addComponent(lblLeitungQuerschnitt))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                        cbxLeitungQuerschnitt,
                        0,
                        156,
                        Short.MAX_VALUE).addComponent(cbxLeitungMaterial, 0, 156, Short.MAX_VALUE).addComponent(
                        cbxLeitungLeitungstyp,
                        javax.swing.GroupLayout.Alignment.TRAILING,
                        0,
                        156,
                        Short.MAX_VALUE)).addContainerGap()));
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                jPanel2Layout.createSequentialGroup().addContainerGap().addGroup(
                    jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblLeitungLeitungstyp).addComponent(
                        cbxLeitungLeitungstyp,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        21,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblLeitungMaterial).addComponent(
                        cbxLeitungMaterial,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        22,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblLeitungQuerschnitt).addComponent(
                        cbxLeitungQuerschnitt,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        22,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap(
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE)));

        jPanel2Layout.linkSize(
            javax.swing.SwingConstants.VERTICAL,
            new java.awt.Component[] { cbxLeitungLeitungstyp, cbxLeitungMaterial, cbxLeitungQuerschnitt });

        final javax.swing.GroupLayout panLeitungLayout = new javax.swing.GroupLayout(panLeitung);
        panLeitung.setLayout(panLeitungLayout);
        panLeitungLayout.setHorizontalGroup(
            panLeitungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                javax.swing.GroupLayout.Alignment.TRAILING,
                panLeitungLayout.createSequentialGroup().addContainerGap().addGroup(
                    panLeitungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(
                        jPanel2,
                        javax.swing.GroupLayout.Alignment.LEADING,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        Short.MAX_VALUE).addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                        sprLeitung,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        270,
                        Short.MAX_VALUE)).addContainerGap()));
        panLeitungLayout.setVerticalGroup(
            panLeitungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                panLeitungLayout.createSequentialGroup().addContainerGap().addComponent(jLabel1).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
                    sprLeitung,
                    javax.swing.GroupLayout.PREFERRED_SIZE,
                    10,
                    javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
                    jPanel2,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE).addContainerGap()));

        lblStandort.setFont(new java.awt.Font("DejaVu Sans", 1, 13));
        lblStandort.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/22/standort.png"))); // NOI18N
        lblStandort.setText("Standort");

        lblStandortStadtbezirk.setText("Stadtbezik:");

        lblStandortPLZ.setText("Postleitzahl:");

        txtStandortPLZ.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
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
                    } else if (value instanceof Stadtbezirk) {
                        final Stadtbezirk sb = (Stadtbezirk)value;
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

        lblStandortHausnummer.setText("Hausnummer:");

        txfStandortHausnummer.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.hausnummer}"),
                txfStandortHausnummer,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setValidator(new StringMaxLengthValidator(5));
        bindingGroup.addBinding(binding);

        lblStandortMastart.setText("Mastart:");

        lblStandortMasttyp.setText("Mast Typ:");

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
                    } else if (value instanceof Mastart) {
                        final Mastart ma = (Mastart)value;
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
                    } else if (value instanceof Masttyp) {
                        final Masttyp mt = (Masttyp)value;
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

        lblStandortKlassifizierung.setText("Klassifizierung:");

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
                    } else if (value instanceof Klassifizierung) {
                        final Klassifizierung kl = (Klassifizierung)value;
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

        lblStandortMastanstrich.setText("Mastanstrich:");

        dapStandortMastanstrich.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.mastanstrich}"),
                dapStandortMastanstrich,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setValidator(new DateValidator());
        bindingGroup.addBinding(binding);

        lblStandortMastschutz.setText("Mastschutz:");

        dapStandortMastschutz.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.mastschutz}"),
                dapStandortMastschutz,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setValidator(new DateValidator());
        bindingGroup.addBinding(binding);

        lblStandortInbetriebnahme.setText("Inbetriebnahme:");

        dapStandortInbetriebnahme.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.inbetriebnahmeMast}"),
                dapStandortInbetriebnahme,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setValidator(new DateValidator());
        bindingGroup.addBinding(binding);

        lblStandortLetzteAenderung.setText("Letze \u00c4nderung:");

        dapStandortLetzteAenderung.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.letzteAenderung}"),
                dapStandortLetzteAenderung,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setValidator(new DateValidator());
        bindingGroup.addBinding(binding);

        lblStandortUnterhalt.setText("Unterhalt:");

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
                    } else if (value instanceof UnterhaltMast) {
                        final UnterhaltMast um = (UnterhaltMast)value;
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

        lblStandortMontagefirma.setText("Montagefirma:");

        txfStandortMontagefirma.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.montagefirma}"),
                txfStandortMontagefirma,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setValidator(new StringMaxLengthValidator());
        bindingGroup.addBinding(binding);

        lblStandortBemerkung.setText("Bemerkung:");

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
                    } else if (value instanceof Strassenschluessel) {
                        final Strassenschluessel ss = (Strassenschluessel)value;
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

        lblStandortStrassenschluessel.setText("Stra\u00dfenschl\u00fcssel:");

        lblStandortKenziffer.setText("Kennziffer:");

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
                    } else if (value instanceof Kennziffer) {
                        final Kennziffer kzf = (Kennziffer)value;
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
        binding.setValidator(new NotNullValidator("Kennziffer"));
        bindingGroup.addBinding(binding);

        txfStandortLaufendenummer.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.laufendeNummer}"),
                txfStandortLaufendenummer,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        lblStandortLaufendenummer.setText("Laufende Nr.:");

        lblStandortVerrechnungseinheit.setText("V-Einheit:");
        lblStandortVerrechnungseinheit.setToolTipText("Verrechnungseinheit");

        lblStandortStandortangabe.setText("Standortangabe:");

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
                    } else if (value instanceof Strassenschluessel) {
                        final Strassenschluessel ss = (Strassenschluessel)value;
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
                "strassenschluesselnr");
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
                                105,
                                Short.MAX_VALUE).addComponent(lblStandortUnterhalt).addComponent(
                                lblStandortMastschutz,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                105,
                                Short.MAX_VALUE).addComponent(lblStandortInbetriebnahme).addComponent(
                                lblStandortLetzteAenderung).addComponent(
                                lblStandortMastanstrich,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                105,
                                Short.MAX_VALUE).addComponent(lblStandortMontagefirma).addComponent(
                                lblStandortHausnummer).addComponent(lblStandortVerrechnungseinheit).addComponent(
                                lblStandortBemerkung,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                105,
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
                                299,
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
                        lblStandortKlassifizierung).addComponent(cbxStandortKlassifizierung, 0, 0, Short.MAX_VALUE))
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

        final javax.swing.GroupLayout panStandortLayout = new javax.swing.GroupLayout(panStandort);
        panStandort.setLayout(panStandortLayout);
        panStandortLayout.setHorizontalGroup(
            panStandortLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                javax.swing.GroupLayout.Alignment.TRAILING,
                panStandortLayout.createSequentialGroup().addContainerGap().addGroup(
                    panStandortLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(
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
        panStandortLayout.setVerticalGroup(
            panStandortLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                panStandortLayout.createSequentialGroup().addContainerGap().addComponent(lblStandort).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
                    sprStandort,
                    javax.swing.GroupLayout.PREFERRED_SIZE,
                    10,
                    javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
                    jPanel1,
                    javax.swing.GroupLayout.PREFERRED_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap(17, Short.MAX_VALUE)));

        lblLeuchteLeuchtennummer.setText("Leuchtennummer:");

        txtLeuchteLeuchtennummer.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.leuchtennummer}"),
                txtLeuchteLeuchtennummer,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setValidator(new LeuchtennummerValidator());
        bindingGroup.addBinding(binding);

        lblLeuchteEnergielieferant.setText("Energielieferant:");

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
                    } else if (value instanceof Energielieferant) {
                        final Energielieferant el = (Energielieferant)value;
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

        lblLeuchteSchaltstelle.setText("Schaltstelle:");

        txtLeuchteSchaltstelle.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.schaltstelle}"),
                txtLeuchteSchaltstelle,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setValidator(new StringMaxLengthValidator());
        bindingGroup.addBinding(binding);

        lblLeuchteRundsteuer.setText("Rundsteuerempf\u00e4nger:");

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

        lblLeuchteLeuchtentyp.setText("Leuchtentyp:");

        lblLeuchteUnterhalt.setText("Unterhalt Leuchte:");

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
                    } else if (value instanceof UnterhaltLeuchte) {
                        final UnterhaltLeuchte ul = (UnterhaltLeuchte)value;
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

        lblLeuchteZaehler.setText("Z\u00e4hler vorhanden:");

        cboLeuchteZaehler.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.zaehler}"),
                cboLeuchteZaehler,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        lblLeuchteInbetriebnahme.setText("Inbetriebnahme:");

        dapLeuchteInbetriebnahme.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.inbetriebnahmeLeuchte}"),
                dapLeuchteInbetriebnahme,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setValidator(new DateValidator());
        bindingGroup.addBinding(binding);

        lblLeuchteStrassenschluessel.setText("Stra\u00dfenschl\u00fcssel:");

        lblLeuchteLaufendenummer.setText("Laufende Nr.:");

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
                    } else if (value instanceof Kennziffer) {
                        final Kennziffer kzf = (Kennziffer)value;
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
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setValidator(new NotNullValidator("Kennziffer"));
        bindingGroup.addBinding(binding);

        lblLeuchteKenziffer.setText("Kennziffer:");

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
                    } else if (value instanceof Leuchtentyp) {
                        final Leuchtentyp lt = (Leuchtentyp)value;
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

        lblLeuchteDoppelkommando1.setText("Doppelkomando 1:");

        lblLeuchteDoppelkommando2.setText("Doppelkomando 2:");

        sprLeuchteDoppelkommando1Anzahl.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.anzahl1DK}"),
                sprLeuchteDoppelkommando1Anzahl,
                org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setSourceNullValue(0);
        binding.setConverter(new ShortIntegerConverter());
        bindingGroup.addBinding(binding);

        sprLeuchteDoppelkommando2Anzahl.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.anzahl2DK}"),
                sprLeuchteDoppelkommando2Anzahl,
                org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setSourceNullValue(0);
        binding.setConverter(new ShortIntegerConverter());
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
                    } else if (value instanceof Doppelkommando) {
                        final Doppelkommando dk = (Doppelkommando)value;
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
                    } else if (value instanceof Doppelkommando) {
                        final Doppelkommando dk = (Doppelkommando)value;
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

        lblLeuchteMontagefirma.setText("Montagefirma:");

        txfLeuchteMontagefirma.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.montageFirmaLeuchte}"),
                txfLeuchteMontagefirma,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setValidator(new StringMaxLengthValidator());
        bindingGroup.addBinding(binding);

        lblLeuchteBemerkung.setText("Bemerkung:");

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
                    } else if (value instanceof Strassenschluessel) {
                        final Strassenschluessel ss = (Strassenschluessel)value;
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
                    } else if (value instanceof Strassenschluessel) {
                        final Strassenschluessel ss = (Strassenschluessel)value;
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

        lblLeuchteStadtbezirk.setText("Stadtbezik:");

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
                    } else if (value instanceof Stadtbezirk) {
                        final Stadtbezirk sb = (Stadtbezirk)value;
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

        lblLeuchteStandortangabe.setText("Standortangabe:");

        txfLeuchteStandortAngabe.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.standort.standortangabe}"),
                txfLeuchteStandortAngabe,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        lblLeuchteVerrechnungseinheit.setText("V-Einheit:");
        lblLeuchteVerrechnungseinheit.setToolTipText("Verrechnungseinheit");

        cboLeuchteVerrechnungseinheit.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.standort.verrechnungseinheit}"),
                cboLeuchteVerrechnungseinheit,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        final javax.swing.GroupLayout panContentLayout = new javax.swing.GroupLayout(panContent);
        panContent.setLayout(panContentLayout);
        panContentLayout.setHorizontalGroup(
            panContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                panContentLayout.createSequentialGroup().addGroup(
                    panContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                        panContentLayout.createSequentialGroup().addContainerGap().addGroup(
                            panContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                                panContentLayout.createSequentialGroup().addComponent(lblLeuchteUnterhalt)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(cbxLeuchteUnterhalt, 0, 251, Short.MAX_VALUE)).addGroup(
                                panContentLayout.createSequentialGroup().addGroup(
                                    panContentLayout.createParallelGroup(
                                        javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                                        lblLeuchteRundsteuer).addComponent(lblLeuchteLeuchtentyp).addComponent(
                                        lblLeuchteZaehler).addComponent(lblLeuchteInbetriebnahme).addComponent(
                                        lblLeuchteDoppelkommando1).addComponent(lblLeuchteDoppelkommando2).addComponent(
                                        lblLeuchteMontagefirma).addComponent(lblLeuchteSchaltstelle).addComponent(
                                        lblLeuchteStrassenschluessel).addComponent(lblLeuchteKenziffer).addComponent(
                                        lblLeuchteLaufendenummer).addComponent(lblLeuchteLeuchtennummer).addComponent(
                                        lblLeuchteEnergielieferant)).addPreferredGap(
                                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                                    panContentLayout.createParallelGroup(
                                        javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                                        panContentLayout.createSequentialGroup().addComponent(
                                            cbxLeuchteStrassenschluesselNr,
                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                            90,
                                            javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(
                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
                                            cbxLeuchteStrassenschluessel,
                                            0,
                                            155,
                                            Short.MAX_VALUE)).addComponent(
                                        cbxLeuchteKennziffer,
                                        0,
                                        251,
                                        Short.MAX_VALUE).addComponent(
                                        txfLeuchteLaufendenummer,
                                        javax.swing.GroupLayout.Alignment.TRAILING,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        251,
                                        Short.MAX_VALUE).addComponent(
                                        txtLeuchteLeuchtennummer,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        251,
                                        Short.MAX_VALUE).addComponent(
                                        cbxLeuchteEnergielieferant,
                                        0,
                                        251,
                                        Short.MAX_VALUE).addComponent(
                                        txtLeuchteSchaltstelle,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        251,
                                        Short.MAX_VALUE).addComponent(
                                        txtLeuchteRundsteuer,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        251,
                                        Short.MAX_VALUE).addComponent(
                                        cbxLeuchteLeuchtentyp,
                                        0,
                                        251,
                                        Short.MAX_VALUE).addComponent(
                                        cboLeuchteZaehler,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        251,
                                        Short.MAX_VALUE).addComponent(
                                        dapLeuchteInbetriebnahme,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        251,
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
                                    Short.MAX_VALUE).addPreferredGap(
                                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
                                    scpLeuchteBemerkung,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    251,
                                    Short.MAX_VALUE)))).addGroup(
                        javax.swing.GroupLayout.Alignment.TRAILING,
                        panContentLayout.createSequentialGroup().addContainerGap().addGroup(
                            panContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                                panContentLayout.createSequentialGroup().addComponent(
                                    lblLeuchteVerrechnungseinheit).addGap(98, 98, 98).addGroup(
                                    panContentLayout.createParallelGroup(
                                        javax.swing.GroupLayout.Alignment.LEADING).addComponent(
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
                                lblLeuchteStadtbezirk)))).addContainerGap()));

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

        lblLeuchte.setFont(new java.awt.Font("DejaVu Sans", 1, 13));
        lblLeuchte.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/22/leuchte.png"))); // NOI18N
        lblLeuchte.setText("Leuchte");

        final javax.swing.GroupLayout panLeuchteLayout = new javax.swing.GroupLayout(panLeuchte);
        panLeuchte.setLayout(panLeuchteLayout);
        panLeuchteLayout.setHorizontalGroup(
            panLeuchteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                javax.swing.GroupLayout.Alignment.TRAILING,
                panLeuchteLayout.createSequentialGroup().addContainerGap().addGroup(
                    panLeuchteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(
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
        panLeuchteLayout.setVerticalGroup(
            panLeuchteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                panLeuchteLayout.createSequentialGroup().addContainerGap().addComponent(lblLeuchte).addPreferredGap(
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

        final javax.swing.GroupLayout panContent1Layout = new javax.swing.GroupLayout(panContent1);
        panContent1.setLayout(panContent1Layout);
        panContent1Layout.setHorizontalGroup(
            panContent1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                411,
                Short.MAX_VALUE));
        panContent1Layout.setVerticalGroup(
            panContent1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                517,
                Short.MAX_VALUE));

        lblAbzweigdose.setFont(new java.awt.Font("DejaVu Sans", 1, 13));
        lblAbzweigdose.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/22/abzweigdose.png"))); // NOI18N
        lblAbzweigdose.setText("Abzweigdose/Zugkasten");

        final javax.swing.GroupLayout panAbzweidoseLayout = new javax.swing.GroupLayout(panAbzweidose);
        panAbzweidose.setLayout(panAbzweidoseLayout);
        panAbzweidoseLayout.setHorizontalGroup(
            panAbzweidoseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                panAbzweidoseLayout.createSequentialGroup().addContainerGap().addGroup(
                    panAbzweidoseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                        panContent1,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        Short.MAX_VALUE).addComponent(lblAbzweigdose).addComponent(
                        sprLeuchte1,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        411,
                        Short.MAX_VALUE)).addContainerGap()));
        panAbzweidoseLayout.setVerticalGroup(
            panAbzweidoseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                panAbzweidoseLayout.createSequentialGroup().addContainerGap().addComponent(lblAbzweigdose)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
                    sprLeuchte1,
                    javax.swing.GroupLayout.PREFERRED_SIZE,
                    10,
                    javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
                    panContent1,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE).addContainerGap()));

        setLayout(new java.awt.BorderLayout());

        panMain.setLayout(new java.awt.BorderLayout());
        scpMain.setViewportView(panMain);

        add(scpMain, java.awt.BorderLayout.CENTER);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

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
    private void cboStandortVerrechnungseinheitActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cboStandortVerrechnungseinheitActionPerformed
// TODO add your handling code here:
    } //GEN-LAST:event_cboStandortVerrechnungseinheitActionPerformed

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
            log.warn("failuire while updating strassenschluessel ", ex);
        } finally {
            isTriggerd = false;
        }
    }                                                                                                   //GEN-LAST:event_cbxStandortStrassenschluesselNrActionPerformed

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
            log.warn("failuire while strassenschluessel ", ex);
        } finally {
            isTriggerd = false;
        }
    }                                                                                                 //GEN-LAST:event_cbxStandortStrassenschluesselActionPerformed

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
            log.warn("failuire while updating strassenschluessel ", ex);
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
            log.warn("failuire while updating strassenschluessel ", ex);
        } finally {
            isTriggerd = false;
        }
    }                                                                                                     //GEN-LAST:event_cbxSchaltstelleStrassenschluesselActionPerformed

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
            log.warn("failuire while updating strassenschluessel ", ex);
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
            log.warn("failuire while updating strassenschluessel ", ex);
        } finally {
            isTriggerd = false;
        }
    }                                                                                                    //GEN-LAST:event_cbxMauerlascheStrassenschluesselActionPerformed

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
            log.warn("failuire while updating strassenschluessel ", ex);
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
            log.warn("failuire while updating strassenschluessel ", ex);
        } finally {
            isTriggerd = false;
        }
    }                                                                                                //GEN-LAST:event_cbxLeuchteStrassenschluesselActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public DocumentPanel getPanDokumente() {
        return panDokumente;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  panDokumente  DOCUMENT ME!
     */
    public void setPanDokumente(final DocumentPanel panDokumente) {
        this.panDokumente = panDokumente;
    }

    @Override
    public BindingGroup getBindingGroup() {
        return bindingGroup;
    }

    @Override
    public void setBindingGroup(final BindingGroup bindingGroup) {
        this.bindingGroup = bindingGroup;
    }

    @Override
    public int getStatus() {
        commitEdits();
        return super.getStatus();
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    class ShortIntegerConverter extends Converter<Short, Integer> {

        //~ Methods ------------------------------------------------------------

        @Override
        public Integer convertForward(final Short value) {
            if (value != null) {
                return value.intValue();
            } else {
                return null;
            }
        }

        @Override
        public Short convertReverse(final Integer value) {
            if (value != null) {
                return value.shortValue();
            } else {
                return null;
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public class NotNullValidator extends Validator {

        //~ Instance fields ----------------------------------------------------

        String elementname = "Element";

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new NotNullValidator object.
         */
        public NotNullValidator() {
        }

        /**
         * Creates a new NotNullValidator object.
         *
         * @param  elementname  DOCUMENT ME!
         */
        public NotNullValidator(final String elementname) {
            this.elementname = elementname;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public Result validate(final Object value) {
            log.fatal("NotNullValidatorcheck: " + value);
            if (value != null) {
                return null;
            } else {
                return new Result("code", elementname + " muss gesetzt werden.");
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public class LeuchtennummerValidator extends Validator<Short> {

        //~ Methods ------------------------------------------------------------

        @Override
        public Result validate(final Short value) {
            if (value != null) {
                if (value.shortValue() > -1) {
                    final TreePath pathToEntity = ((BelisBroker)broker).getWorkbenchWidget()
                                .getTreeTableModel()
                                .getPathForUserObject(currentEntity);
                    if ((pathToEntity != null) && (pathToEntity.getLastPathComponent() != null)) {
                        final Standort parentStandort = ((BelisBroker)broker).getWorkbenchWidget()
                                    .getParentMast(pathToEntity.getLastPathComponent());
                        if ((parentStandort != null) && parentStandort.isStandortMast()
                                    && (parentStandort.getLeuchten().size() > 1)) {
                            if (log.isDebugEnabled()) {
                                log.debug(
                                    "Standort is Mast checking leuchten for entries with the same leuchtennummer.");
                            }
                            for (final Leuchte curLeuchte : parentStandort.getLeuchten()) {
                                if (log.isDebugEnabled()) {
                                    log.debug("checking leuchte: " + curLeuchte);
                                }
                                if ((curLeuchte.getLeuchtennummer() != null)
                                            && curLeuchte.getLeuchtennummer().equals(value)
                                            && !curLeuchte.equals(currentEntity)) {
                                    if (log.isDebugEnabled()) {
                                        log.debug("found another leuchte with the same leuchtennumber");
                                    }
                                    return new Result(
                                            "code",
                                            "Es darf nicht zwei Leuchten mit der selben Leuchtennummer geben.");
                                }
                            }
                        }
                    }
                    return null;
                } else {
                    return new Result("code", "Leuchtennummer darf nicht negativ sein.");
                }
            } else {
                return new Result("code", "Leuchtennummer muss gesetzt sein.");
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public class StringMaxLengthValidator extends Validator<String> {

        //~ Instance fields ----------------------------------------------------

        private int maxLength;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new StringMaxLengthValidator object.
         */
        public StringMaxLengthValidator() {
            this.maxLength = maxStringLength;
        }

        /**
         * Creates a new StringMaxLengthValidator object.
         *
         * @param  maxLength  DOCUMENT ME!
         */
        public StringMaxLengthValidator(final int maxLength) {
            this.maxLength = maxLength;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public Result validate(final String value) {
            if ((value != null) && (value.length() > maxLength)) {
                return new Result("code", "Der Text darf nicht länger als " + maxLength + " Zeichen sein.");
            }
            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public class PLZValidator extends Validator<String> {

        //~ Methods ------------------------------------------------------------

        @Override
        public Result validate(final String value) {
            if (value != null) {
                try {
                    if (Integer.parseInt(value) > -1) {
                        if (value.length() == 5) {
                            if (log.isDebugEnabled()) {
                                log.debug("plz valide");
                            }
                            return null;
                        } else {
                            return new Result("code", "Postleitzahl muss genau 5 Stellen haben.");
                        }
                    } else {
                        return new Result("code", "Postleitzahl darf nicht negativ sein.");
                    }
                } catch (NumberFormatException ex) {
                    if (value.equals("")) {
                        if (log.isDebugEnabled()) {
                            log.debug("Postleitzahl is empty", ex);
                        }
                    } else {
                        if (log.isDebugEnabled()) {
                            log.debug("Postleitzahl no number", ex);
                        }
                        return new Result("code", "Postleitzahl darf nur aus Ziffern bestehen.");
                    }
                }
            }
            if (log.isDebugEnabled()) {
                log.debug("plz valide");
            }
            return null;
        }
    }
    /**
     * ToDo JXDatePicker Visualisierung.
     *
     * @version  $Revision$, $Date$
     */
    public class DateValidator extends Validator<Date> {

        //~ Methods ------------------------------------------------------------

        @Override
        public Result validate(final Date value) {
            if ((value != null) && (value.compareTo(earliestDate.getTime()) < 0)) {
                return new Result("code", "Datum muss nach dem 01.01.1950 sein.");
            }
            return null;
        }
    }
    /**
     * ToDo place in utils.
     *
     * @version  $Revision$, $Date$
     */
    class ObjectToKeyStringConverter extends ObjectToStringConverter {

        //~ Methods ------------------------------------------------------------

        @Override
        public String getPreferredStringForItem(final Object item) {
            if ((item != null) && (item instanceof BaseEntity)) {
                return ((BaseEntity)item).getKeyString();
            } else {
                return null;
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    class ObjectToIDConverter extends ObjectToStringConverter {

        //~ Methods ------------------------------------------------------------

        @Override
        public String getPreferredStringForItem(final Object item) {
            try {
                if ((item != null) && (item instanceof BaseEntity) && (BelisEEUtils.getEntityId(item) != null)) {
                    return BelisEEUtils.getEntityId(item).toString();
                } else {
                    return null;
                }
            } catch (ActionNotSuccessfulException ex) {
                return null;
            }
        }
    }
//    /*         * AutoCompleteEditor.java       */
//    public class AutoCompleteEditor extends BasicComboBoxEditor {
//
//        private JTextField editor = null;
//
//        public AutoCompleteEditor(JComboBox combo, boolean caseSensitive, ObjectToStringConverter converter) {
//            super();
//            editor = new AutoCompleteEditorComponent(combo, caseSensitive, converter);
//        }
//
//        /**
//         * overrides BasicComboBox's getEditorComponent to return custom TextField
//        (AutoCompleteEditorComponent)          */
//        public Component getEditorComponent() {
//            return editor;
//        }
//    }
//    /*         * AutoCompleteEditorComponent.java      */
//    public class AutoCompleteEditorComponent extends JTextField {
//
//        JComboBox combo = null;
//        boolean caseSensitive = false;
//        ObjectToStringConverter converter;
//
//        public AutoCompleteEditorComponent(JComboBox combo, boolean caseSensitive, final ObjectToStringConverter converter) {
//            super();
//            this.combo = combo;
//            this.caseSensitive = caseSensitive;
//            this.converter = converter;
//        }
//
//        /**
//         * overwritten to return custom PlainDocument which does the work*/
//        protected Document createDefaultModel() {
//            return new PlainDocument() {
//
//                public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
//                    if (str == null || str.length() == 0 || converter == null) {
//                        return;
//                    }
//                    int size = combo.getItemCount();
//                    String text = getText(0, getLength());
//                    for (int i = 0; i < size; i++) {
//                        String item = converter.getPreferredStringForItem(combo.getItemAt(i));
//                        if (getLength() + str.length() > item.length()) {
//                            continue;
//                        }
//                        if (!caseSensitive) {
//                            if ((text + str).equalsIgnoreCase(item)) {
//                                combo.setSelectedIndex(i);
//                                //if (!combo.isPopupVisible())
//                                //    combo.setPopupVisible(true);
//                                super.remove(0, getLength());
//                                super.insertString(0, item, a);
//                                return;
//                            } else if (item.substring(0, getLength() + str.length()).equalsIgnoreCase(text + str)) {
//                                combo.setSelectedIndex(i);
//                                if (!combo.isPopupVisible()) {
//                                    combo.setPopupVisible(true);
//                                }
//                                super.remove(0, getLength());
//                                super.insertString(0, item, a);
//                                return;
//                            }
//                        } else if (caseSensitive) {
//                            if ((text + str).equals(item)) {
//                                combo.setSelectedIndex(i);
//                                if (!combo.isPopupVisible()) {
//                                    combo.setPopupVisible(true);
//                                }
//                                super.remove(0, getLength());
//                                super.insertString(0, item, a);
//                                return;
//                            } else if (item.substring(0, getLength() + str.length()).equals(text + str)) {
//                                combo.setSelectedIndex(i);
//                                if (!combo.isPopupVisible()) {
//                                    combo.setPopupVisible(true);
//                                }
//                                super.remove(0, getLength());
//                                super.insertString(0, item, a);
//                                return;
//                            }
//                        }
//                    }
//                }
//            };
//        }
//    }
}
