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

import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Converter;
import org.jdesktop.beansbinding.Validator;
import org.jdesktop.beansbinding.Validator.Result;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import java.awt.BorderLayout;
import java.awt.Component;

import java.beans.PropertyChangeEvent;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.tree.TreePath;

import de.cismet.belis.broker.BelisBroker;
import de.cismet.belis.broker.CidsBroker;

import de.cismet.belis.gui.documentpanel.DocumentPanel;
import de.cismet.belis.gui.widget.detailWidgetPanels.ObjectToKeyStringConverter;
import de.cismet.belis.gui.widget.detailWidgetPanels.ObjectToPkConverter;
import de.cismet.belis.gui.widget.detailWidgetPanels.SchaltstellePanel;
import de.cismet.belis.gui.widget.detailWidgetPanels.StandortPanel;

import de.cismet.belisEE.exception.ActionNotSuccessfulException;

import de.cismet.belisEE.util.CriteriaStringComparator;

import de.cismet.cids.custom.beans.belis.AbzweigdoseCustomBean;
import de.cismet.cids.custom.beans.belis.BauartCustomBean;
import de.cismet.cids.custom.beans.belis.DmsUrlCustomBean;
import de.cismet.cids.custom.beans.belis.LeitungCustomBean;
import de.cismet.cids.custom.beans.belis.LeitungstypCustomBean;
import de.cismet.cids.custom.beans.belis.MaterialLeitungCustomBean;
import de.cismet.cids.custom.beans.belis.MaterialMauerlascheCustomBean;
import de.cismet.cids.custom.beans.belis.MauerlascheCustomBean;
import de.cismet.cids.custom.beans.belis.QuerschnittCustomBean;
import de.cismet.cids.custom.beans.belis.SchaltstelleCustomBean;
import de.cismet.cids.custom.beans.belis.TdtaLeuchtenCustomBean;
import de.cismet.cids.custom.beans.belis.TdtaStandortMastCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyBezirkCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyDoppelkommandoCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyEnergielieferantCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyKennzifferCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyLeuchtentypCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyStrassenschluesselCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyUnterhLeuchteCustomBean;

import de.cismet.commons.server.interfaces.DocumentContainer;

import de.cismet.commons2.architecture.widget.DefaultWidget;

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

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(DetailWidget.class);

    //~ Instance fields --------------------------------------------------------

    protected Object currentEntity = null;
    private Collection<TkeyStrassenschluesselCustomBean> allStrassenschluessel;
    // ToDo configurable
    private int maxStringLength = 250;
    // private final GregorianCalendar calender = new GregorianCalendar();
    // private final Date smallestAllowedDate = new Date(0);
    private final String comboBoxNullValue = "Wert auswählen...";
    private Collection<Binding> validationState = new HashSet<Binding>();
    private Collection<LeitungstypCustomBean> leitungstypen = new HashSet<LeitungstypCustomBean>();
    private boolean isTriggerd = false;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox cboLeuchteVerrechnungseinheit;
    private javax.swing.JCheckBox cboLeuchteZaehler;
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
    private org.jdesktop.swingx.JXDatePicker dapLeuchteInbetriebnahme;
    private org.jdesktop.swingx.JXDatePicker dapMauerlascheErstellungsjahr;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
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
    private javax.swing.JPanel panAbzweidose;
    private javax.swing.JPanel panContent;
    private javax.swing.JPanel panContent1;
    private de.cismet.belis.gui.documentpanel.DocumentPanel panDokumente;
    private javax.swing.JPanel panLeitung;
    private javax.swing.JPanel panLeuchte;
    private javax.swing.JPanel panMain;
    private javax.swing.JPanel panMauerlasche;
    private javax.swing.JScrollPane scpLeuchteBemerkung;
    private javax.swing.JScrollPane scpMain;
    private javax.swing.JSeparator sprLeitung;
    private javax.swing.JSeparator sprLeuchte;
    private javax.swing.JSeparator sprLeuchte1;
    private javax.swing.JSpinner sprLeuchteDoppelkommando1Anzahl;
    private javax.swing.JSpinner sprLeuchteDoppelkommando2Anzahl;
    private javax.swing.JSeparator sprMauerlasche;
    private javax.swing.JTextArea txaLeuchteBemerkung;
    private javax.swing.JTextField txfLeuchteLaufendenummer;
    private javax.swing.JTextField txfLeuchteMontagefirma;
    private javax.swing.JTextField txfLeuchteStandortAngabe;
    private javax.swing.JTextField txfMauerlascheLaufendenummer;
    private javax.swing.JTextField txtLeuchteLeuchtennummer;
    private javax.swing.JTextField txtLeuchteRundsteuer;
    private javax.swing.JTextField txtLeuchteSchaltstelle;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new DetailWidget object.
     *
     * @param  broker  DOCUMENT ME!
     */
    public DetailWidget(final BelisBroker broker) {
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
            for (final LeitungstypCustomBean curLeitungstyp : leitungstypen) {
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
        if (LOG.isDebugEnabled()) {
            LOG.debug("PropertyChange: " + evt);
            LOG.debug("PropertyChange: " + evt.getPropertyName());
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("PropertyChange: " + evt.getOldValue());
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("PropertyChange: " + evt.getNewValue());
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void initContent() {
        try {
            allStrassenschluessel = CidsBroker.getInstance().getAllStrassenschluessel();
            if (LOG.isDebugEnabled()) {
                LOG.debug("Strassenschluessel size: " + allStrassenschluessel.size());
            }
        } catch (Exception ex) {
            LOG.error("Error while initializing all strassenschlussel.");
            allStrassenschluessel = new HashSet();
        }

        initLeuchtePanel();
        initLeitungPanel();
        initMauerlaschePanel();
        initAbzweigdosePanel();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  col  DOCUMENT ME!
     * @param  box  DOCUMENT ME!
     */
    private void createSortedCBoxModelFromCollection(final Collection<?> col, final JComboBox box) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("sorting collection: " + col);
        }
        try {
            if (box != null) {
                if (col != null) {
                    final Object[] objArr = col.toArray();
                    Arrays.sort(objArr, CriteriaStringComparator.getInstance());
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("sorted Collection:" + objArr);
                    }
                    box.setModel(new DefaultComboBoxModel(objArr));
                } else {
                    box.setModel(new DefaultComboBoxModel());
                }
            }
        } catch (Exception ex) {
            LOG.error("error while sorting collection", ex);
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void initLeitungPanel() {
        try {
            final Collection<MaterialLeitungCustomBean> material = CidsBroker.getInstance().getAllMaterialLeitung();
            createSortedCBoxModelFromCollection(material, cbxLeitungMaterial);
        } catch (ActionNotSuccessfulException ex) {
            cbxLeitungMaterial.setModel(new DefaultComboBoxModel());
        }
        cbxLeitungMaterial.setSelectedItem(null);

        try {
            leitungstypen = CidsBroker.getInstance().getAllLeitungstypen();
            createSortedCBoxModelFromCollection(leitungstypen, cbxLeitungLeitungstyp);
        } catch (ActionNotSuccessfulException ex) {
            cbxLeitungLeitungstyp.setModel(new DefaultComboBoxModel());
        }
        cbxLeitungLeitungstyp.setSelectedItem(null);

        try {
            final Collection<QuerschnittCustomBean> querschnitt = CidsBroker.getInstance().getAllQuerschnitte();
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
    private void refreshCbxLeuchteLeuchtentyp() {
        try {
            final Collection<TkeyLeuchtentypCustomBean> leuchtentypen = CidsBroker.getInstance().getAllLeuchtentypen();
            createSortedCBoxModelFromCollection(leuchtentypen, cbxLeuchteLeuchtentyp);
        } catch (ActionNotSuccessfulException ex) {
            cbxLeuchteLeuchtentyp.setModel(new DefaultComboBoxModel());
        }
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
        AutoCompleteDecorator.decorate(cbxLeuchteStrassenschluesselNr, new ObjectToPkConverter("pk"));

        try {
            final Collection<TkeyKennzifferCustomBean> kennziffern = CidsBroker.getInstance().getAllKennziffer();
            createSortedCBoxModelFromCollection(kennziffern, cbxLeuchteKennziffer);
        } catch (ActionNotSuccessfulException ex) {
            cbxLeuchteKennziffer.setModel(new DefaultComboBoxModel());
        }
        cbxLeuchteKennziffer.setSelectedItem(null);
        try {
            final Collection<TkeyEnergielieferantCustomBean> lieferanten = CidsBroker.getInstance()
                        .getAllEnergielieferanten();
            createSortedCBoxModelFromCollection(lieferanten, cbxLeuchteEnergielieferant);
        } catch (ActionNotSuccessfulException ex) {
            cbxLeuchteEnergielieferant.setModel(new DefaultComboBoxModel());
        }
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
                            ((BelisBroker)broker).setDefaultUnterhaltLeuchte(curUnterhaltLeuchte);
                        }
                    }
                }
            } catch (Exception ex) {
                LOG.warn("Error while determining default UnterhaltLeuchte: ", ex);
            }
            createSortedCBoxModelFromCollection(unterhalt, cbxLeuchteUnterhalt);
        } catch (ActionNotSuccessfulException ex) {
            cbxLeuchteUnterhalt.setModel(new DefaultComboBoxModel());
        }

        CidsBroker.getInstance().addListenerForBeanChange("tkey_leuchtentyp", new BeanChangedListener() {

                @Override
                public void beanChanged() {
                    bindingGroup.unbind();
                    refreshCbxLeuchteLeuchtentyp();
                    bindingGroup.bind();
                }
            });

        refreshCbxLeuchteLeuchtentyp();
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
                            ((BelisBroker)broker).setDefaultDoppelkommando1(curDoppelkommando);
                        }
                    }
                }
            } catch (Exception ex) {
                LOG.warn("Error while determining default Doppelkommando1: ", ex);
            }
            createSortedCBoxModelFromCollection(dk1, cbxLeuchteDoppelkommando1);
        } catch (ActionNotSuccessfulException ex) {
            cbxLeuchteDoppelkommando1.setModel(new DefaultComboBoxModel());
        }
        cbxLeuchteDoppelkommando1.setSelectedItem(null);
        try {
            final Collection<TkeyDoppelkommandoCustomBean> dk2 = CidsBroker.getInstance().getAllDoppelkommando();
            createSortedCBoxModelFromCollection(dk2, cbxLeuchteDoppelkommando2);
        } catch (ActionNotSuccessfulException ex) {
            cbxLeuchteDoppelkommando2.setModel(new DefaultComboBoxModel());
        }
        cbxLeuchteDoppelkommando2.setSelectedItem(null);
        cbxLeuchteUnterhalt.setSelectedItem(null);
        cbxLeuchteEnergielieferant.setSelectedItem(null);

        // Virtual properties
        try {
            final Collection<TkeyBezirkCustomBean> bezirke = CidsBroker.getInstance().getAllStadtbezirke();
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
        AutoCompleteDecorator.decorate(cbxMauerlascheStrassenschluesselNr, new ObjectToPkConverter("pk"));
        try {
            final Collection<MaterialMauerlascheCustomBean> material = CidsBroker.getInstance()
                        .getAllMaterialMauerlasche();
            createSortedCBoxModelFromCollection(material, cbxMauerlascheMaterial);
        } catch (ActionNotSuccessfulException ex) {
            cbxMauerlascheMaterial.setModel(new DefaultComboBoxModel());
        }
        cbxMauerlascheMaterial.setSelectedItem(null);
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
        if (LOG.isDebugEnabled()) {
            LOG.debug("setCurrentEntity", new CurrentStackTrace());
        }
        final Object oldCurrentEntity = this.currentEntity;
        this.currentEntity = currentEntity;
        // Attention there is another block for the visiblity of the document panel
        // this must be here because the set must be created before it is set in the documentpanel
        if ((currentEntity != null) && (currentEntity instanceof DocumentContainer)
                    && (((DocumentContainer)currentEntity).getDokumente() == null)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Entity is DocumentContainer and Set == null. Creating Set");
            }
            ((DocumentContainer)currentEntity).setDokumente(new ArrayList<DmsUrlCustomBean>());

//            ((DocumentContainer)currentEntity).setDokumente(new TreeSet(
//                    new ReverseComparator(new EntityComparator(new ReverseComparator(new LeuchteComparator())))));
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
            if (LOG.isDebugEnabled()) {
                LOG.debug("current Entity is null");
            }
            StandortPanel.getInstance().setVisible(false);
            panLeuchte.setVisible(false);
            this.repaint();
            return;
        }
        // validateTest();
        if (currentEntity instanceof TdtaStandortMastCustomBean) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("CurrentEntity is Standort");
            }
            // panMain.add(panStandort,BorderLayout.CENTER);
            final StandortPanel standortPanel = StandortPanel.getInstance();
            standortPanel.setCurrentEntity((TdtaStandortMastCustomBean)currentEntity);
            standortPanel.setElementsNull();

            panMain.add(standortPanel, BorderLayout.CENTER);
            standortPanel.setVisible(true);
        } else if (currentEntity instanceof TdtaLeuchtenCustomBean) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("CurrentEntity is Leuchte");
            }
            // panStandort.setVisible(true);
            // panMain.add(panLeuchte,BorderLayout.CENTER);
            panMain.add(panLeuchte, BorderLayout.CENTER);
            LOG.info("ParentNode: " + ((TdtaLeuchtenCustomBean)currentEntity).getStandort());
            if (((BelisBroker)broker).getWorkbenchWidget().isParentNodeMast(
                            ((BelisBroker)broker).getWorkbenchWidget().getSelectedTreeNode().getLastPathComponent())) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("ParentNode ist Mast");
                }
                setInheritedMastPropertiesEnabled(false);
            } else {
                if (broker.isInEditMode()) {
                    setInheritedMastPropertiesEnabled(true);
                }
            }
            panLeuchte.setVisible(true);
            if (((TdtaLeuchtenCustomBean)currentEntity).getStrassenschluessel() == null) {
                cbxLeuchteStrassenschluessel.setSelectedItem(null);
            }
            if (((TdtaLeuchtenCustomBean)currentEntity).getKennziffer() == null) {
                cbxLeuchteKennziffer.setSelectedItem(null);
            }
//            docPanel.setFileList();
        } else if (currentEntity instanceof LeitungCustomBean) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("CurrentEntity is Leitung");
            }
            panMain.add(panLeitung, BorderLayout.CENTER);
            panLeitung.setVisible(true);
        } else if (currentEntity instanceof AbzweigdoseCustomBean) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("CurrentEntity is Abzweigdose");
            }
            panMain.add(panAbzweidose, BorderLayout.CENTER);
            panLeitung.setVisible(true);
        } else if (currentEntity instanceof MauerlascheCustomBean) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("CurrentEntity is Mauerlasche");
            }
            if (((MauerlascheCustomBean)currentEntity).getStrassenschluessel() == null) {
                cbxMauerlascheStrassenschluessel.setSelectedItem(null);
                cbxMauerlascheStrassenschluesselNr.setSelectedItem(null);
            }
            panMain.add(panMauerlasche, BorderLayout.CENTER);
            panMauerlasche.setVisible(true);
        } else if (currentEntity instanceof SchaltstelleCustomBean) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("CurrentEntity is Schaltstelle");
            }

            final SchaltstellePanel schaltstellePanel = SchaltstellePanel.getInstance();

            schaltstellePanel.setCurrentEntity((SchaltstelleCustomBean)currentEntity);
            schaltstellePanel.setElementsNull();

            panMain.add(schaltstellePanel, BorderLayout.CENTER);
            schaltstellePanel.setVisible(true);
        } else {
            LOG.warn("no panel for entity available");
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
        StandortPanel.getInstance().setVisible(visible);
        panLeuchte.setVisible(visible);
        panLeitung.setVisible(visible);
        panMauerlasche.setVisible(visible);
        SchaltstellePanel.getInstance().setVisible(visible);
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
        StandortPanel.getInstance().setPanelEditable(isEditable);

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
        SchaltstellePanel.getInstance().setPanelEditable(isEditable);

        // doc panel
        panDokumente.setEditable(isEditable);
    }

    /**
     * DOCUMENT ME!
     */
    private void commitEdits() {
        if (currentEntity != null) {
            if (currentEntity instanceof TdtaLeuchtenCustomBean) {
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
            } else if (currentEntity instanceof TdtaStandortMastCustomBean) {
                StandortPanel.getInstance().commitEdits();
            } else if (currentEntity instanceof SchaltstelleCustomBean) {
                SchaltstellePanel.getInstance().commitEdits();
            } else if (currentEntity instanceof MauerlascheCustomBean) {
                try {
                    dapMauerlascheErstellungsjahr.getEditor().commitEdit();
                } catch (ParseException ex) {
                    LOG.warn("Error while commiting edits: " + ex);
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

        jLabel2.setFont(new java.awt.Font("DejaVu Sans", 1, 13));                              // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/22/mauerlasche.png"))); // NOI18N
        jLabel2.setText("Mauerlasche");                                                        // NOI18N

        lblMauerlascheStrassenschluessel.setText("Straßenschlüssel:"); // NOI18N

        lblMauerlascheLaufendenummer.setText("Laufende Nr.:"); // NOI18N

        lblMauerlascheErstellungsjahr.setText("Erstellungsjahr:"); // NOI18N

        lblMauerlascheMaterial.setText("Material:"); // NOI18N

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
                    } else if (value instanceof de.cismet.cids.custom.beans.belis.MaterialMauerlascheCustomBean) {
                        final de.cismet.cids.custom.beans.belis.MaterialMauerlascheCustomBean mm =
                            (de.cismet.cids.custom.beans.belis.MaterialMauerlascheCustomBean)value;
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
                    } else if (value instanceof de.cismet.cids.custom.beans.belis.TkeyStrassenschluesselCustomBean) {
                        final de.cismet.cids.custom.beans.belis.TkeyStrassenschluesselCustomBean ss =
                            (de.cismet.cids.custom.beans.belis.TkeyStrassenschluesselCustomBean)value;
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
                        22,
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

        jLabel1.setFont(new java.awt.Font("DejaVu Sans", 1, 13));                          // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/22/leitung.png"))); // NOI18N
        jLabel1.setText("Leitung");                                                        // NOI18N

        lblLeitungMaterial.setText("Material:"); // NOI18N

        lblLeitungLeitungstyp.setText("Leitungstyp:"); // NOI18N

        lblLeitungQuerschnitt.setText("Querschnitt:"); // NOI18N

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
                    } else if (value instanceof de.cismet.cids.custom.beans.belis.LeitungstypCustomBean) {
                        final de.cismet.cids.custom.beans.belis.LeitungstypCustomBean lt =
                            (de.cismet.cids.custom.beans.belis.LeitungstypCustomBean)value;
                        setText(lt.getBezeichnung());
                    }
                    return this;
                }
            });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.fk_leitungstyp}"),
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
                    } else if (value instanceof de.cismet.cids.custom.beans.belis.MaterialLeitungCustomBean) {
                        final de.cismet.cids.custom.beans.belis.MaterialLeitungCustomBean mt =
                            (de.cismet.cids.custom.beans.belis.MaterialLeitungCustomBean)value;
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
                    } else if (value instanceof de.cismet.cids.custom.beans.belis.QuerschnittCustomBean) {
                        final de.cismet.cids.custom.beans.belis.QuerschnittCustomBean qt =
                            (de.cismet.cids.custom.beans.belis.QuerschnittCustomBean)value;
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

        lblLeuchteLeuchtennummer.setText("Leuchtennummer:"); // NOI18N

        txtLeuchteLeuchtennummer.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
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

        lblAbzweigdose.setFont(new java.awt.Font("DejaVu Sans", 1, 13));                       // NOI18N
        lblAbzweigdose.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/22/abzweigdose.png"))); // NOI18N
        lblAbzweigdose.setText("Abzweigdose/Zugkasten");                                       // NOI18N

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
    private void cbxMauerlascheMaterialActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbxMauerlascheMaterialActionPerformed
// TODO add your handling code here:
    } //GEN-LAST:event_cbxMauerlascheMaterialActionPerformed

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
            LOG.warn("failuire while updating strassenschluessel ", ex);
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
            LOG.warn("failuire while updating strassenschluessel ", ex);
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
            if (LOG.isDebugEnabled()) {
                LOG.debug("NotNullValidatorcheck: " + value);
            }
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
    public class LeuchtennummerValidator extends Validator<Integer> {

        //~ Methods ------------------------------------------------------------

        @Override
        public Result validate(final Integer value) {
            if (value != null) {
                if (value.shortValue() > -1) {
                    final TreePath pathToEntity = ((BelisBroker)broker).getWorkbenchWidget()
                                .getTreeTableModel()
                                .getPathForUserObject(currentEntity);
                    if ((pathToEntity != null) && (pathToEntity.getLastPathComponent() != null)) {
                        final TdtaStandortMastCustomBean parentStandort = ((BelisBroker)broker).getWorkbenchWidget()
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
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("plz valide");
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
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Postleitzahl is empty", ex);
                        }
                    } else {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Postleitzahl no number", ex);
                        }
                        return new Result("code", "Postleitzahl darf nur aus Ziffern bestehen.");
                    }
                }
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("plz valide");
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
}
