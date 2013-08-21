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
package de.cismet.belis.gui.widget.windowsearchwidget;

import Sirius.navigator.search.dynamic.SearchControlListener;
import Sirius.navigator.search.dynamic.SearchControlPanel;

import Sirius.server.middleware.types.MetaClass;

import com.vividsolutions.jts.geom.Geometry;

import java.awt.Cursor;
import java.awt.Dimension;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;

import de.cismet.belis.broker.BelisBroker;

import de.cismet.belis.server.search.BelisSearchStatement;

import de.cismet.cids.server.search.MetaObjectNodeServerSearch;

import de.cismet.cids.tools.search.clientstuff.CidsWindowSearch;

import de.cismet.cismap.commons.CrsTransformer;
import de.cismet.cismap.commons.XBoundingBox;
import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.interaction.CismapBroker;

import de.cismet.cismap.navigatorplugin.GeoSearchButton;

/**
 * DOCUMENT ME!
 *
 * @author   mroncoroni
 * @version  $Revision$, $Date$
 */
//@org.openide.util.lookup.ServiceProvider(service = CidsWindowSearch.class)
public class BelisWindowSearch extends javax.swing.JPanel implements CidsWindowSearch,
    SearchControlListener,
    PropertyChangeListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(BelisWindowSearch.class);

    //~ Enums ------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public enum Mode {

        //~ Enum constants -----------------------------------------------------

        ABZEIGDOSE, LEITUNG, MAUERLASCHE, SCHALTESTELLE, STANDORT
    }

    //~ Instance fields --------------------------------------------------------

    private MetaClass mc = null;
    private ImageIcon icon;
    private MappingComponent mappingComponent;
    private boolean geoSearchEnabled;
    private JComboBox lstStreets;
    private JComboBox lstNumbers;
    private SearchControlPanel pnlSearchCancel;
    private GeoSearchButton btnGeoSearch;
    private Geometry mapsearchgeom;
    private final String name;
    private final Mode mode;
    private String searchListenerName;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chkAbzweigdose;
    private javax.swing.JCheckBox chkLeitung;
    private javax.swing.JCheckBox chkMap;
    private javax.swing.JCheckBox chkMauerlasche;
    private javax.swing.JCheckBox chkSchaltstelle;
    private javax.swing.JCheckBox chkStandort;
    private javax.swing.JCheckBox chkStreetAndNumber;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel panArt;
    private javax.swing.JPanel panCommand;
    private javax.swing.JPanel panPosition;
    private javax.swing.JPanel panSearch;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new BelisWindowSearch object.
     *
     * @throws  RuntimeException  DOCUMENT ME!
     */
    public BelisWindowSearch() {
        this(null, "Allgemeine Suche");
        try {
            initWithThisSpecificPanel(null);
        } catch (final Throwable ex) {
            LOG.error("error while initialiazing BelisWindowSearch", ex);
            throw new RuntimeException(ex);
        }
    }

    /**
     * Creates new form LandParcelWindowSearch.
     *
     * @param  mode  DOCUMENT ME!
     * @param  name  DOCUMENT ME!
     */
    protected BelisWindowSearch(final Mode mode, final String name) {
        this.name = name;
        this.mode = mode;
        mappingComponent = BelisBroker.getInstance().getMappingComponent();

        if (mode != null) {
            switch (mode) {
                case ABZEIGDOSE: {
                    searchListenerName = MappingComponent.CREATE_SEARCH_POLYGON + "_ABZEIGDOSE";
                }
                break;
                case LEITUNG: {
                    searchListenerName = MappingComponent.CREATE_SEARCH_POLYGON + "_LEITUNG";
                }
                break;
                case MAUERLASCHE: {
                    searchListenerName = MappingComponent.CREATE_SEARCH_POLYGON + "_MAUERLASCHE";
                }
                break;
                case SCHALTESTELLE: {
                    searchListenerName = MappingComponent.CREATE_SEARCH_POLYGON + "_SCHALTESTELLE";
                }
                break;
                case STANDORT: {
                    searchListenerName = MappingComponent.CREATE_SEARCH_POLYGON + "_STANDORT";
                }
                break;
            }
            final BelisCreateSearchGeometryListener listener = new BelisCreateSearchGeometryListener(
                    mappingComponent,
                    this,
                    searchListenerName);
            mappingComponent.addInputListener(searchListenerName, listener);
            mappingComponent.putCursor(searchListenerName, new Cursor(Cursor.CROSSHAIR_CURSOR));
        }
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        panArt = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        chkStandort = new javax.swing.JCheckBox();
        chkSchaltstelle = new javax.swing.JCheckBox();
        chkMauerlasche = new javax.swing.JCheckBox();
        chkAbzweigdose = new javax.swing.JCheckBox();
        chkLeitung = new javax.swing.JCheckBox();
        panSearch = new javax.swing.JPanel();
        panPosition = new javax.swing.JPanel();
        chkStreetAndNumber = new javax.swing.JCheckBox();
        jComboBox1 = new javax.swing.JComboBox();
        jComboBox2 = new javax.swing.JComboBox();
        chkMap = new javax.swing.JCheckBox();
        panCommand = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();

        panArt.setBorder(javax.swing.BorderFactory.createTitledBorder(
                org.openide.util.NbBundle.getMessage(
                    BelisWindowSearch.class,
                    "BelisWindowSearch.panArt.border.title"))); // NOI18N
        panArt.setLayout(new java.awt.GridBagLayout());

        jPanel3.setLayout(new java.awt.GridLayout(0, 2));

        org.openide.awt.Mnemonics.setLocalizedText(
            chkStandort,
            org.openide.util.NbBundle.getMessage(BelisWindowSearch.class, "BelisWindowSearch.chkStandort.text")); // NOI18N
        jPanel3.add(chkStandort);

        org.openide.awt.Mnemonics.setLocalizedText(
            chkSchaltstelle,
            org.openide.util.NbBundle.getMessage(BelisWindowSearch.class, "BelisWindowSearch.chkSchaltstelle.text")); // NOI18N
        jPanel3.add(chkSchaltstelle);

        org.openide.awt.Mnemonics.setLocalizedText(
            chkMauerlasche,
            org.openide.util.NbBundle.getMessage(BelisWindowSearch.class, "BelisWindowSearch.chkMauerlasche.text")); // NOI18N
        jPanel3.add(chkMauerlasche);

        org.openide.awt.Mnemonics.setLocalizedText(
            chkAbzweigdose,
            org.openide.util.NbBundle.getMessage(BelisWindowSearch.class, "BelisWindowSearch.chkAbzweigdose.text")); // NOI18N
        jPanel3.add(chkAbzweigdose);

        org.openide.awt.Mnemonics.setLocalizedText(
            chkLeitung,
            org.openide.util.NbBundle.getMessage(BelisWindowSearch.class, "BelisWindowSearch.chkLeitung.text")); // NOI18N
        jPanel3.add(chkLeitung);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panArt.add(jPanel3, gridBagConstraints);

        setLayout(new java.awt.BorderLayout());

        panSearch.setLayout(new java.awt.GridBagLayout());

        panPosition.setBorder(javax.swing.BorderFactory.createTitledBorder(
                org.openide.util.NbBundle.getMessage(
                    BelisWindowSearch.class,
                    "BelisWindowSearch.panPosition.border.title"))); // NOI18N
        panPosition.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            chkStreetAndNumber,
            org.openide.util.NbBundle.getMessage(BelisWindowSearch.class, "BelisWindowSearch.chkStreetAndNumber.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panPosition.add(chkStreetAndNumber, gridBagConstraints);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                chkStreetAndNumber,
                org.jdesktop.beansbinding.ELProperty.create("${selected}"),
                jComboBox1,
                org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panPosition.add(jComboBox1, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                chkStreetAndNumber,
                org.jdesktop.beansbinding.ELProperty.create("${selected}"),
                jComboBox2,
                org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panPosition.add(jComboBox2, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            chkMap,
            org.openide.util.NbBundle.getMessage(BelisWindowSearch.class, "BelisWindowSearch.chkMap.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panPosition.add(chkMap, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panSearch.add(panPosition, gridBagConstraints);
        panPosition.getAccessibleContext()
                .setAccessibleName(org.openide.util.NbBundle.getMessage(
                        BelisWindowSearch.class,
                        "BelisWindowSearch.panPosition.AccessibleContext.accessibleName")); // NOI18N

        panCommand.setLayout(new javax.swing.BoxLayout(panCommand, javax.swing.BoxLayout.LINE_AXIS));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panSearch.add(panCommand, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.weighty = 1.0;
        panSearch.add(jPanel1, gridBagConstraints);

        add(panSearch, java.awt.BorderLayout.CENTER);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    @Override
    public JComponent getSearchWindowComponent() {
        return this;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  geometry  DOCUMENT ME!
     */
    public void searchWithThisGeometry(final Geometry geometry) {
        mapsearchgeom = geometry;
        pnlSearchCancel.startSearch();
        mapsearchgeom = null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   searchGeom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected BelisSearchStatement createSearchStatement(final Geometry searchGeom) {
        return new BelisSearchStatement(chkStandort.isSelected(),
                chkSchaltstelle.isSelected(),
                chkMauerlasche.isSelected(),
                chkLeitung.isSelected(),
                chkAbzweigdose.isSelected(),
                searchGeom);
    }

    @Override
    public MetaObjectNodeServerSearch getServerSearch() {
        final Geometry searchGeom;
        if ((mapsearchgeom == null) && chkMap.isSelected()) {
            searchGeom = ((XBoundingBox)CismapBroker.getInstance().getMappingComponent().getCurrentBoundingBox())
                        .getGeometry();
        } else {
            searchGeom = mapsearchgeom;
        }
        if (searchGeom != null) {
            final Geometry transformed = CrsTransformer.transformToDefaultCrs(searchGeom);
            // Damits auch mit -1 funzt:
            transformed.setSRID(CismapBroker.getInstance().getDefaultCrsAlias());
        }
        return createSearchStatement(searchGeom);
    }

    @Override
    public ImageIcon getIcon() {
        return icon;
    }

    @Override
    public MetaObjectNodeServerSearch assembleSearch() {
        return getServerSearch();
    }

    @Override
    public void searchStarted() {
    }

    @Override
    public void searchDone(final int numberOfResults) {
    }

    @Override
    public void searchCanceled() {
    }

    @Override
    public boolean suppressEmptyResultMessage() {
        return false;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
    }

    /**
     * DOCUMENT ME!
     *
     * @param  panel  DOCUMENT ME!
     */
    protected void initWithThisSpecificPanel(JPanel panel) {
//        final AddressSearchControl searchControl = new AddressSearchControl(null);
//        BelisBroker.getInstance().getConfigManager().addConfigurable(searchControl);
//        BelisBroker.getInstance().getConfigManager().configure(searchControl);

        mappingComponent = CismapBroker.getInstance().getMappingComponent();

        // mc = ClassCacheMultiple.getMetaClass(CidsBroker.BELIS_DOMAIN, TdtaStandortMastCustomBean.TABLE);
        // icon = new ImageIcon(mc.getIconData());

// lstStreets = (JComboBox)searchControl.getListComponentByName("cboAllStreets");
// lstNumbers = (JComboBox)searchControl.getListComponentByName("cboNumbersOfAStreet");

        initComponents();
        if (panel == null) {
            panel = panArt;
        }
        add(panel, java.awt.BorderLayout.NORTH);

        pnlSearchCancel = new SearchControlPanel(this);
        final Dimension max = pnlSearchCancel.getMaximumSize();
        final Dimension min = pnlSearchCancel.getMinimumSize();
        final Dimension pre = pnlSearchCancel.getPreferredSize();
        pnlSearchCancel.setMaximumSize(new java.awt.Dimension(
                new Double(max.getWidth()).intValue(),
                new Double(max.getHeight() + 5).intValue()));
        pnlSearchCancel.setMinimumSize(new java.awt.Dimension(
                new Double(min.getWidth()).intValue(),
                new Double(min.getHeight() + 5).intValue()));
        pnlSearchCancel.setPreferredSize(new java.awt.Dimension(
                new Double(pre.getWidth() + 6).intValue(),
                new Double(pre.getHeight() + 5).intValue()));
        panCommand.add(pnlSearchCancel);
        panCommand.add(Box.createHorizontalStrut(5));

        geoSearchEnabled = mappingComponent != null;
        if (geoSearchEnabled) {
            btnGeoSearch = new GeoSearchButton(
                    searchListenerName,
                    mappingComponent,
                    null,
                    "Standorte suchen");
            panCommand.add(btnGeoSearch);
        }

        if (mode != null) {
            switch (mode) {
                case ABZEIGDOSE: {
                    chkAbzweigdose.setSelected(true);
                }
                break;
                case LEITUNG: {
                    chkLeitung.setSelected(true);
                }
                break;
                case MAUERLASCHE: {
                    chkMauerlasche.setSelected(true);
                }
                break;
                case SCHALTESTELLE: {
                    chkSchaltstelle.setSelected(true);
                }
                break;
                case STANDORT: {
                    chkStandort.setSelected(true);
                }
                break;
            }
        } else {
            chkAbzweigdose.setSelected(true);
            chkLeitung.setSelected(true);
            chkMauerlasche.setSelected(true);
            chkSchaltstelle.setSelected(true);
            chkStandort.setSelected(true);
        }
    }
}
