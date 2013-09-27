/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.belis.broker;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.event.CatalogueActivationListener;
import Sirius.navigator.event.CatalogueSelectionListener;
import Sirius.navigator.resource.PropertyManager;
import Sirius.navigator.search.CidsSearchExecutor;
import Sirius.navigator.search.dynamic.SearchDialog;
import Sirius.navigator.types.treenode.RootTreeNode;
import Sirius.navigator.ui.ComponentRegistry;
import Sirius.navigator.ui.DescriptionPane;
import Sirius.navigator.ui.DescriptionPaneFS;
import Sirius.navigator.ui.LayoutedContainer;
import Sirius.navigator.ui.MutableMenuBar;
import Sirius.navigator.ui.MutablePopupMenu;
import Sirius.navigator.ui.MutableToolBar;
import Sirius.navigator.ui.attributes.AttributeViewer;
import Sirius.navigator.ui.attributes.editor.AttributeEditor;
import Sirius.navigator.ui.tree.MetaCatalogueTree;
import Sirius.navigator.ui.tree.SearchResultsTree;

import Sirius.server.middleware.types.MetaObject;
import Sirius.server.middleware.types.MetaObjectNode;
import Sirius.server.middleware.types.Node;

import com.vividsolutions.jts.geom.Geometry;

import net.infonode.docking.RootWindow;
import net.infonode.gui.componentpainter.GradientComponentPainter;

import org.apache.commons.collections.comparators.ReverseComparator;
import org.apache.log4j.PropertyConfigurator;

import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.ComponentAdapter;
import org.jdesktop.swingx.decorator.CompoundHighlighter;
import org.jdesktop.swingx.decorator.HighlightPredicate;
import org.jdesktop.swingx.decorator.Highlighter;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.treetable.AbstractMutableTreeTableNode;

import org.jdom.Element;

import org.openide.util.Lookup;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;

import de.cismet.belis.gui.search.AddressSearchControl;
import de.cismet.belis.gui.search.LocationSearchControl;
import de.cismet.belis.gui.search.MapSearchControl;
import de.cismet.belis.gui.search.SearchControl;
import de.cismet.belis.gui.search.SearchController;
import de.cismet.belis.gui.widget.BelisWidget;
import de.cismet.belis.gui.widget.DetailWidget;
import de.cismet.belis.gui.widget.ExtendedNavigatorAttributeEditorGui;
import de.cismet.belis.gui.widget.MapWidget;
import de.cismet.belis.gui.widget.WorkbenchWidget;

import de.cismet.belis.panels.AlreadyLockedObjectsPanel;
import de.cismet.belis.panels.CancelWaitDialog;
import de.cismet.belis.panels.CopyPasteToolbar;
import de.cismet.belis.panels.CreateToolBar;
import de.cismet.belis.panels.EditButtonsToolbar;
import de.cismet.belis.panels.FilterToolBar;
import de.cismet.belis.panels.SaveErrorDialogPanel;
import de.cismet.belis.panels.SaveWaitDialog;

import de.cismet.belis.server.search.BelisLocationSearchStatement;
import de.cismet.belis.server.search.BelisSearchStatement;

import de.cismet.belis.todo.CustomMutableTreeTableNode;
import de.cismet.belis.todo.RetrieveWorker;

import de.cismet.belis.util.BelisIcons;

import de.cismet.belisEE.exception.ActionNotSuccessfulException;
import de.cismet.belisEE.exception.LockAlreadyExistsException;

import de.cismet.belisEE.util.EntityComparator;
import de.cismet.belisEE.util.LeuchteComparator;

import de.cismet.cids.custom.beans.belis.ArbeitsauftragCustomBean;
import de.cismet.cids.custom.beans.belis.ArbeitsprotokollCustomBean;
import de.cismet.cids.custom.beans.belis.LeitungCustomBean;
import de.cismet.cids.custom.beans.belis.LeitungstypCustomBean;
import de.cismet.cids.custom.beans.belis.MauerlascheCustomBean;
import de.cismet.cids.custom.beans.belis.SperreCustomBean;
import de.cismet.cids.custom.beans.belis.TdtaLeuchtenCustomBean;
import de.cismet.cids.custom.beans.belis.TdtaStandortMastCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyDoppelkommandoCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyStrassenschluesselCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyUnterhLeuchteCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyUnterhMastCustomBean;
import de.cismet.cids.custom.beans.belis.VeranlassungCustomBean;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cismap.commons.BoundingBox;
import de.cismet.cismap.commons.features.DefaultFeatureCollection;
import de.cismet.cismap.commons.features.Feature;
import de.cismet.cismap.commons.features.FeatureCollection;
import de.cismet.cismap.commons.features.StyledFeature;
import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.gui.statusbar.StatusBar;
import de.cismet.cismap.commons.interaction.CismapBroker;
import de.cismet.cismap.commons.tools.IconUtils;

import de.cismet.cismap.navigatorplugin.MetaSearchHelper;

import de.cismet.commons.architecture.exception.LockingNotSuccessfulException;
import de.cismet.commons.architecture.geometrySlot.GeometrySlot;
import de.cismet.commons.architecture.geometrySlot.GeometrySlotInformation;
import de.cismet.commons.architecture.geometrySlot.GeometrySlotProvider;
import de.cismet.commons.architecture.interfaces.Clearable;
import de.cismet.commons.architecture.interfaces.Editable;
import de.cismet.commons.architecture.interfaces.FeatureSelectionChangedListener;
import de.cismet.commons.architecture.interfaces.ObjectChangeListener;
import de.cismet.commons.architecture.plugin.AbstractPlugin;
import de.cismet.commons.architecture.validation.Validatable;

import de.cismet.commons.server.entity.BaseEntity;
import de.cismet.commons.server.entity.GeoBaseEntity;

import de.cismet.commons2.architecture.layout.LayoutManager;

import de.cismet.tools.CurrentStackTrace;

import de.cismet.tools.configuration.Configurable;
import de.cismet.tools.configuration.ConfigurationManager;
import de.cismet.tools.configuration.NoWriteError;

import de.cismet.tools.gui.DefaultPopupMenuListener;
import de.cismet.tools.gui.StaticSwingTools;

import de.cismet.veto.VetoException;
import de.cismet.veto.VetoListener;

/**
 * DOCUMENT ME!
 *
 * @author   Puhl
 * @version  $Revision$, $Date$
 */
//Logging
public class BelisBroker implements SearchController, PropertyChangeListener, VetoListener, Configurable {

    //~ Static fields/initializers ---------------------------------------------

    private static BelisBroker INSTANCE;
    protected static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(BelisBroker.class);
    // ToDo depper ?
    // COLORS
    // ToDo another class
    public static final Color yellow = new Color(231, 223, 84);
    public static final Color red = new Color(219, 96, 96);
    public static final Color blue = new Color(124, 160, 221);
    public static final String PROP_IN_EDIT_MODE = "inEditMode";
    public static final String PROP_FILTER_NORMAL = "filterNormal";
    public static final String PROP_FILTER_VERANLASSUNG = "filterVeranlassung";
    public static final String PROP_FILTER_ARBEITSAUFTRAG = "filterArbeitsauftrag";

    // ToDo check
    public static final Color gray = Color.LIGHT_GRAY;
    // JXTable
    public static final int alphaValue = 255;
    // TODO Perhaps a bit (blasser) brighter public static Color ODD_ROW_DEFAULT_COLOR = new
    // Color(blue.getRed()+119,blue.getGreen()+88,blue.getBlue()+33,alphaValue);
    public static final Color ODD_ROW_DEFAULT_COLOR = new Color(blue.getRed() + 113,
            blue.getGreen()
                    + 79,
            blue.getBlue()
                    + 14,
            alphaValue);
    // public static final Color ODD_ROW_DEFAULT_COLOR = new Color(,,,alphaValue); public static final Color
    // ODD_ROW_DEFAULT_COLOR = new Color(blue.getRed()+119,blue.getGreen()+82,blue.getBlue()+34,alphaValue); public
    // static Color ODD_ROW_EDIT_COLOR = new Color(red.getRed()+36,red.getGreen()+146,red.getBlue()+152,alphaValue);
    public static final Color ODD_ROW_EDIT_COLOR = new Color(red.getRed() + 25,
            red.getGreen()
                    + 143,
            red.getBlue()
                    + 143,
            alphaValue);
    public static final Color ODD_ROW_LOCK_COLOR = new Color(yellow.getRed() + 23,
            yellow.getGreen()
                    + 31,
            yellow.getBlue()
                    + 134,
            alphaValue);
    // public static final Color ODD_ROW_COLOR = new Color(252,84,114,120);
    public static final Color EVEN_ROW_COLOR = Color.WHITE;
    public static final Color FOREGROUND_ROW_COLOR = Color.BLACK;
    public static Highlighter ALTERNATE_ROW_HIGHLIGHTER = HighlighterFactory.createAlternateStriping(
            ODD_ROW_DEFAULT_COLOR,
            EVEN_ROW_COLOR);
    // ToDo more general configurable public static final AlternateRowHighlighter ALTERNATE_ROW_HIGHLIGHTER_EDIT = new
    // AlternateRowHighlighter(ODD_ROW_EDIT_COLOR, EVEN_ROW_COLOR, FOREGROUND_ROW_COLOR); TitleColors
    public static final Color EDIT_MODE_COLOR = red;
    public static final Color DEFAULT_MODE_COLOR = blue;
    // ToDo outsource
    private static GregorianCalendar calender = new GregorianCalendar();
    public static final String PROP_CURRENT_SEARCH_RESULTS = "currentSearchResults";
    private static TkeyUnterhMastCustomBean defaultUnterhaltMast = null;
    private static TkeyUnterhLeuchteCustomBean defaultUnterhaltLeuchte = null;
    private static TkeyDoppelkommandoCustomBean defaultDoppelkommando1 = null;
    public static final String[] jxDatePickerFormats = new String[] { "dd.MM.yyyy", "ddMMyy", "ddMMyyyy" };

    //~ Instance fields --------------------------------------------------------

    // this is ugly there should be a default toolbar ......
    public AtomicBoolean isPendingForCreateMode = new AtomicBoolean(false);

    protected JButton btnSwitchInEditmode;
    protected JButton btnDiscardChanges;
    protected JButton btnAcceptChanges;
    protected JButton btnSwitchInCreateMode;
    protected JButton cmdPrint = new javax.swing.JButton();
    protected JButton btnReload = new javax.swing.JButton();
    protected PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    protected final Collection<Clearable> clearAndDisableListeners = new ArrayList<Clearable>();
    protected MapWidget mapWidget = null;
    protected final Collection<BelisWidget> widgets = new ArrayList<BelisWidget>();
    // ToDo make proper --> syncron with widgets
    protected final Collection<Editable> editables = new ArrayList<Editable>();
    protected JComponent parentComponent;
    protected JFrame parentFrame;
    // Permissions
    protected boolean isFullReadOnlyMode = true;
    protected boolean isCoreReadOnlyMode = true;
    // ToDo improve mechanismn
    protected HashMap<String, Boolean> permissions = new HashMap<String, Boolean>();
    /** Creates a new instance of LagisBroker. */
    protected StatusBar statusBar;
    protected ExecutorService execService = null;
    protected String currentValidationErrorMessage = null;
    final CreateToolBar panCreate = new CreateToolBar(this);
    final FilterToolBar panFilter = new FilterToolBar(this);
    WorkbenchWidget workbenchWidget = null;
    final ArrayList<SearchControl> searchControls = new ArrayList<SearchControl>();
    private final EntityClipboard entityClipboard = new EntityClipboard(this);
    private final CopyPasteToolbar copyPasteToolbar = new CopyPasteToolbar(this);
    private AddressSearchControl asPan;
    private final LayoutManager layoutManager = new LayoutManager();
    private ConfigurationManager configManager;
    private JToolBar toolbar;
    private String applicationName;
    private StatusBar statusbar;
    private boolean statusBarEnabled = true;
    private String account;
    private String loggingProperties;
    // public static final Color grey = new Color(225, 226, 225);
    // ToDo perhaps outsource
    private boolean inEditMode = false;
    private ArrayList<FeatureSelectionChangedListener> featureSelectionChangedIgnoredWidgets =
        new ArrayList<FeatureSelectionChangedListener>();
    private AlreadyLockedObjectsPanel lockPanel = null;
    // Todo maybe deliver mapWidget by default so the derived broker has direct access and don't have to search
    private Set<BaseEntity> currentSearchResults = new TreeSet(new ReverseComparator(
                new EntityComparator(new ReverseComparator(new LeuchteComparator()))));
//    public static final String PROP_NEW_OBJECTS = "currentSearchResults";
    private boolean inCreateMode;
    private MapSearchControl mscPan = null;
    private int maxSearchResults = 50;
    private SaveWaitDialog saveWaitDialog = null;
    private CancelWaitDialog cancelWaitDialog = null;
    private RetrieveWorker lastSearch = null;
    private DetailWidget detailWidget;
    private LeitungstypCustomBean lastLeitungstyp = null;
    private boolean vetoCheckEnabled = true;
    private TkeyStrassenschluesselCustomBean lastMauerlascheStrassenschluessel;
    // I Think the single components should register the properties they want to bind and broker only binds them that
    // would be fine bind Workbench to Details maybe if another applications needs the same feature or if it is used
    // more often throughout the application ToDo if there is need for more special binding generalize this with static
    // binding methods (e.g. Master/Slave or other mechanismn)
    private final ArrayList<GeoBaseEntity> currentFeatures = new ArrayList<GeoBaseEntity>();
    // Todo outsource in panel, the advantage is that it is easier to edit --> you can use the gui builder
    // And don't know if it is good to have fix item in the toolbar
    private EditButtonsToolbar editButtonsToolbar;
    private MetaSearchHelper metaSearchComponentFactory;
    private ComponentRegistry componentRegistry;

    private boolean filterNormal = true;
    private boolean filterVeranlassung = false;
    private boolean filterArbeitsauftrag = false;

    //~ Constructors -----------------------------------------------------------

    /**
     * ToDo FIX.
     */
    private BelisBroker() {
        execService = Executors.newCachedThreadPool();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static BelisBroker getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BelisBroker();
        }
        return INSTANCE;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  metaSearchComponentFactory  DOCUMENT ME!
     */
    public void setMetaSearchComponentFactory(final MetaSearchHelper metaSearchComponentFactory) {
        this.metaSearchComponentFactory = metaSearchComponentFactory;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public MetaSearchHelper getMetaSearchComponentFactory() {
        return metaSearchComponentFactory;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  widget  DOCUMENT ME!
     */
    public void addWidget(final BelisWidget widget) {
        widgets.add(widget);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Collection<BelisWidget> getWidgets() {
        return widgets;
    }

    /**
     * DOCUMENT ME!
     */
    public void resetWidgets() {
        if (!EventQueue.isDispatchThread()) {
            EventQueue.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Lagis Broker : Reset widgets");
                        }
                        for (final BelisWidget tmp : widgets) {
                            tmp.clearComponent();
                            tmp.setWidgetEditable(false);
                        }
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Lagis Broker : Reset widgets durch");
                        }
                    }
                });
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Lagis Broker : Reset widgets");
        }
        for (final BelisWidget tmp : widgets) {
            tmp.clearComponent();
            tmp.setWidgetEditable(false);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Lagis Broker : Reset widgets durch");
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isEditable  DOCUMENT ME!
     */
    public synchronized void setComponentsEditable(final boolean isEditable) {
        try {
            if (EventQueue.isDispatchThread()) {
                setWidgetsEditable(isEditable);
                for (final Editable curEditable : editables) {
                    curEditable.setWidgetEditable(isEditable);
                }
            } else {
                EventQueue.invokeAndWait(new Runnable() {

                        @Override
                        public void run() {
                            setComponentsEditable(isEditable);
                        }
                    });
            }
        } catch (Exception ex) {
            LOG.error("Error while setting Components editable: ", ex);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  editable  DOCUMENT ME!
     */
    public void addEditable(final Editable editable) {
        editables.add(editable);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  editable  DOCUMENT ME!
     */
    public void removeEditable(final Editable editable) {
        editables.remove(editable);
    }

    /**
     * ToDo --> no double code see BelisBroker or use Framework.
     *
     * @param  isEditable  DOCUMENT ME!
     */
    public synchronized void setWidgetsEditable(final boolean isEditable) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Setze Widgets editable: " + isEditable);
        }
        if (!EventQueue.isDispatchThread()) {
            EventQueue.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        for (final BelisWidget curWidget : widgets) {
                            // ToDo locking
                            if (isEditable) {
                                ((ColorHighlighter)
                                    (((CompoundHighlighter)ALTERNATE_ROW_HIGHLIGHTER).getHighlighters()[0]))
                                        .setBackground(ODD_ROW_EDIT_COLOR);
                            } else {
                                ((ColorHighlighter)
                                    (((CompoundHighlighter)ALTERNATE_ROW_HIGHLIGHTER).getHighlighters()[0]))
                                        .setBackground(ODD_ROW_DEFAULT_COLOR);
                            }
                            if (isEditable) {
                                curWidget.setWidgetEditable(isEditable);
                            } else {
                                curWidget.setWidgetEditable(isEditable);
                            }
                        }
                    }
                });
        } else {
            for (final BelisWidget curWidget : widgets) {
                // ToDo locking
                if (isEditable) {
                    // ALTERNATE_ROW_HIGHLIGHTER = HighlighterFactory.createAlternateStriping(ODD_ROW_EDIT_COLOR,
                    // EVEN_ROW_COLOR);
                    ((ColorHighlighter)(((CompoundHighlighter)ALTERNATE_ROW_HIGHLIGHTER).getHighlighters()[0]))
                            .setBackground(ODD_ROW_EDIT_COLOR);
                    // ALTERNATE_ROW_HIGHLIGHTER.setOddRowBackground(ODD_ROW_EDIT_COLOR);
                } else {
                    // ALTERNATE_ROW_HIGHLIGHTER = HighlighterFactory.createAlternateStriping(ODD_ROW_DEFAULT_COLOR,
                    // EVEN_ROW_COLOR); ALTERNATE_ROW_HIGHLIGHTER.setOddRowBackground(ODD_ROW_DEFAULT_COLOR);
                    ((ColorHighlighter)(((CompoundHighlighter)ALTERNATE_ROW_HIGHLIGHTER).getHighlighters()[0]))
                            .setBackground(ODD_ROW_DEFAULT_COLOR);
                }
                if (isEditable) {
                    curWidget.setWidgetEditable(isEditable);
                } else {
                    curWidget.setWidgetEditable(isEditable);
                }
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   geom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public GeometrySlotInformation assignGeometry(final Geometry geom) {
        final GeometrySlotInformation[] openSlots = collectGeometrySlots();
        switch (openSlots.length) {
            case 0: {
                JOptionPane.showMessageDialog(StaticSwingTools.getParentFrame(getMappingComponent()),
                    "Es ist kein Element vorhanden dem eine Fläche zugeordnet werden kann\noder die entsprechenden Rechte sind nicht ausreichend",
                    "Geometrie zuordnen",
                    JOptionPane.INFORMATION_MESSAGE);
                return null;
            }
            case 1: {
                final int anwser = JOptionPane.showConfirmDialog(StaticSwingTools.getParentFrame(getMappingComponent()),
                        "Es ist genau ein Element vorhanden, dem eine Fläche zugeordnet werden kann:\n\n"
                                + "    "
                                + openSlots[0]
                                + "\n\nSoll die Geometrie diesem dem Element hinzugefügt werden ?",
                        "Geometrie zuordnen",
                        JOptionPane.YES_NO_OPTION);
                if (anwser == JOptionPane.YES_OPTION) {
                    final GeometrySlot slotGeom = openSlots[0].getOpenSlot();
                    if (slotGeom != null) {
                        slotGeom.setGeometry(geom);
                    } else {
                        // TODO create concept how to determine the color of geomentities
                        // slotGeom = new Geom();
                        slotGeom.setGeometry(geom);
                        // openSlots[0].getOpenSlot().setGeometrie(slotGeom);
                    }
                    return openSlots[0];
                } else {
                    return null;
                }
            }
            default: {
                final GeometrySlotInformation selectedSlot = (GeometrySlotInformation)JOptionPane.showInputDialog(
                        StaticSwingTools.getParentFrame(getMappingComponent()),
                        "Bitte wählen Sie das Element, dem Sie die Geometrie zuordnen möchten:\n",
                        "Geometrie zuordnen",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        openSlots,
                        openSlots[0]);
                if (selectedSlot != null) {
                    final GeometrySlot slotGeom = selectedSlot.getOpenSlot();
                    if (slotGeom != null) {
                        slotGeom.setGeometry(geom);
                    } else {
                        // TODO create concept how to determine the color of geomentities
                        // slotGeom = new Geom();
                        slotGeom.setGeometry(geom);
                        // selectedSlot.getOpenSlot().setGeometrie(slotGeom);
                    }
                    return selectedSlot;
                } else {
                    return null;
                }
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected GeometrySlotInformation[] collectGeometrySlots() {
        final Collection<GeometrySlotInformation> openSlots = new ArrayList<GeometrySlotInformation>();
        for (final BelisWidget curWidget : widgets) {
            if (curWidget instanceof GeometrySlotProvider) {
                openSlots.addAll(((GeometrySlotProvider)curWidget).getSlotInformation());
            }
        }
        return openSlots.toArray(new GeometrySlotInformation[openSlots.size()]);
    }

    /**
     * TODO REFACTOR real event Implement proper events not direkt Collection ToDo good ChangeObserver.
     *
     * @param  event  DOCUMENT ME!
     */
    public void fireChangeEvent(final Object event) {
        for (final BelisWidget curWidget : widgets) {
            if ((curWidget instanceof FeatureSelectionChangedListener) && (event instanceof Collection)) {
                if (!featureSelectionChangedIgnoredWidgets.contains((FeatureSelectionChangedListener)curWidget)) {
                    ((FeatureSelectionChangedListener)curWidget).featureSelectionChanged((Collection<Feature>)event);
                } else {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Widget: " + curWidget.getWidgetName()
                                    + " is in ignoredList no featureSelectionChanged");
                    }
                }
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  ignore  DOCUMENT ME!
     */
    public void addFeatureSelectionChangeIgnore(final FeatureSelectionChangedListener ignore) {
        if (ignore != null) {
            featureSelectionChangedIgnoredWidgets.add(ignore);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  ignore  DOCUMENT ME!
     */
    public void removeFeatureSelectionChangeIgnore(final FeatureSelectionChangedListener ignore) {
        if (ignore != null) {
            featureSelectionChangedIgnoredWidgets.remove(ignore);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   ignore  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean isFeatureSelectionChangeIgnoreRegistered(final FeatureSelectionChangedListener ignore) {
        return featureSelectionChangedIgnoredWidgets.contains(ignore);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public MappingComponent getMappingComponent() {
        return CismapBroker.getInstance().getMappingComponent();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  aMappingComponent  DOCUMENT ME!
     */
    public void setMappingComponent(final MappingComponent aMappingComponent) {
        CismapBroker.getInstance().setMappingComponent(aMappingComponent);
    }

    /**
     * ToDo boolean method parameter.
     *
     * @throws  Exception  LockingNotSuccessfulException DOCUMENT ME!
     */
    public void switchEditMode() throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("switchEditMode");
        }
        if (isInEditMode()) {
            switchInEditMode(false);
            setComponentsEditable(false);
            new SwingWorker<Void, Void>() {

                    @Override
                    protected Void doInBackground() throws Exception {
                        releaseLock();
                        return null;
                    }

                    @Override
                    protected void done() {
                        setInEditMode(false);
                        getMappingComponent().setReadOnly(true);
                    }
                }.execute();
        } else {
            setTitleBarComponentpainter(BelisBroker.EDIT_MODE_COLOR);
            editButtonsToolbar.enableSwitchToModeButtons(false);
            switchInEditMode(true);
            new SwingWorker<Void, Void>() {

                    @Override
                    protected Void doInBackground() throws Exception {
                        acquireLock();
                        return null;
                    }

                    @Override
                    protected void done() {
                        try {
                            get();
                        } catch (Exception e) {
                            LOG.error("Problem while switching to Edit Mode", e);
                            switchInEditMode(false);
                            setTitleBarComponentpainter(BelisBroker.DEFAULT_MODE_COLOR);
                            editButtonsToolbar.enableSwitchToModeButtons(true);
                            return;
                        }
                        setInEditMode(true);
                        setComponentsEditable(true);
                        getMappingComponent().setReadOnly(false);
                        btnAcceptChanges.setEnabled(true);
                        btnDiscardChanges.setEnabled(true);
                    }
                }.execute();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isSwitchedInEditMode  DOCUMENT ME!
     */
    protected void switchInEditMode(final boolean isSwitchedInEditMode) {
        if (isSwitchedInEditMode) {
            disableSearch();
            if (isInCreateMode()) {
                setAllFeaturesSelectable(false);
            }
        } else {
            enableSearch();
            setAllFeaturesSelectable(true);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  inEditMode  DOCUMENT ME!
     */
    public void setInEditMode(final boolean inEditMode) {
        final boolean old = this.inEditMode;
        this.inEditMode = inEditMode;
        propertyChangeSupport.firePropertyChange(PROP_IN_EDIT_MODE, old, inEditMode);
    }
    // ToDo to specific I think

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean isInEditMode() {
        // return currentSperre != null;
        return inEditMode;
    }

    /**
     * ToDo change getValidationMessage not the right method how should the developer know.
     *
     * @return  DOCUMENT ME!
     */
    public boolean validateWidgets() {
        for (final BelisWidget currentWidget : widgets) {
            if (currentWidget.getStatus() == Validatable.ERROR) {
                currentValidationErrorMessage = currentWidget.getValidationMessage();
                if (currentValidationErrorMessage == null) {
                    currentValidationErrorMessage = "Kein Fehlertext vorhanden";
                }
                return false;
            }
        }
        return true;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public JComponent getParentComponent() {
        return parentComponent;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  parentComponent  DOCUMENT ME!
     */
    public void setParentComponent(final JComponent parentComponent) {
        this.parentComponent = parentComponent;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  refreshingObject  DOCUMENT ME!
     */
    public void refreshWidgets(final Object refreshingObject) {
        for (final BelisWidget curRefreshable : widgets) {
            curRefreshable.refresh(refreshingObject);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   date  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Date getDateWithoutTime(final Date date) {
        calender.setTime(date);
        calender.set(GregorianCalendar.HOUR, 0);
        calender.set(GregorianCalendar.MINUTE, 0);
        calender.set(GregorianCalendar.SECOND, 0);
        calender.set(GregorianCalendar.MILLISECOND, 0);
        calender.set(GregorianCalendar.AM_PM, GregorianCalendar.AM);
        return calender.getTime();
    }

    /**
     * TODO nächsten 6 methoden Sinnvoll ?? public String getUsername() { return username; } public void
     * setUsername(String aUsername) { username = aUsername; } public String getGroup() { return group; } public void
     * setGroup(String aGroup) { group = aGroup; } public String getDomain() { return domain; } public void
     * setDomain(String aDomain) { domain = aDomain; } TODO is fullqualified username toDo refactor
     *
     * @return  DOCUMENT ME!
     */
    public boolean isFullReadOnlyMode() {
        return isFullReadOnlyMode;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFullReadOnlyMode  DOCUMENT ME!
     */
    public void setFullReadOnlyMode(final boolean isFullReadOnlyMode) {
        this.isFullReadOnlyMode = isFullReadOnlyMode;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean isCoreReadOnlyMode() {
        return isCoreReadOnlyMode;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isCoreReadOnlyMode  DOCUMENT ME!
     */
    public void setCoreReadOnlyMode(final boolean isCoreReadOnlyMode) {
        this.isCoreReadOnlyMode = isCoreReadOnlyMode;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public HashMap<String, Boolean> getPermissions() {
        return permissions;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  permissions  DOCUMENT ME!
     */
    public void setPermissions(final HashMap<String, Boolean> permissions) {
        this.permissions = permissions;
    }

    @Override
    public Element getConfiguration() throws NoWriteError {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public ComponentRegistry getComponentRegistry() {
        return componentRegistry;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  componentRegistry  DOCUMENT ME!
     */
    public void setComponentRegistry(final ComponentRegistry componentRegistry) {
        this.componentRegistry = componentRegistry;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   frame  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public void initComponentRegistry(final JFrame frame) throws Exception {
        final SearchResultsTree searchResultsTree = new SearchResultsTree();
        final MutableToolBar toolBar = new MutableToolBar();
        final MutableMenuBar menuBar = new MutableMenuBar();
        final LayoutedContainer container = new LayoutedContainer(toolBar, menuBar, true);
        final AttributeViewer attributeViewer = new AttributeViewer();
        final AttributeEditor attributeEditor = new ExtendedNavigatorAttributeEditorGui();
        final SearchDialog searchDialog = null;

        final DescriptionPane descriptionPane = new DescriptionPaneFS();
        final MutablePopupMenu popupMenu = new MutablePopupMenu();

        final Collection<Component> toRemoveComponents = new ArrayList<Component>();
        for (final Component component : popupMenu.getComponents()) {
            if ((component instanceof JSeparator)
                        || ((component instanceof JMenuItem)
                            && (((JMenuItem)component).getActionCommand() != null)
                            && (((JMenuItem)component).getActionCommand().equals("cmdSearch")
                                || ((JMenuItem)component).getActionCommand().equals("treecommand")))) {
                toRemoveComponents.add(component);
            }
        }
        for (final Component toRemoveComponent : toRemoveComponents) {
            popupMenu.remove(toRemoveComponent);
        }

        final DefaultPopupMenuListener cataloguePopupMenuListener = new DefaultPopupMenuListener(popupMenu);
        final RootTreeNode rootTreeNode = new RootTreeNode(SessionManager.getProxy().getRoots());
        final MetaCatalogueTree metaCatalogueTree = new MetaCatalogueTree(
                rootTreeNode,
                PropertyManager.getManager().isEditable(),
                true,
                5);
        final CatalogueSelectionListener catalogueSelectionListener = new CatalogueSelectionListener(
                attributeViewer,
                descriptionPane);
        final CatalogueActivationListener catalogueActivationListener = new CatalogueActivationListener(
                metaCatalogueTree,
                attributeViewer,
                descriptionPane);

        metaCatalogueTree.addMouseListener(cataloguePopupMenuListener);
        metaCatalogueTree.addTreeSelectionListener(catalogueSelectionListener);
        metaCatalogueTree.addComponentListener(catalogueActivationListener);

        ComponentRegistry.registerComponents(
            frame,
            container,
            menuBar,
            toolBar,
            popupMenu,
            metaCatalogueTree,
            searchResultsTree,
            attributeViewer,
            attributeEditor,
            searchDialog,
            descriptionPane);

        searchResultsTree.getModel().addTreeModelListener(new TreeModelListener() {

                @Override
                public void treeNodesChanged(final TreeModelEvent e) {
                }

                @Override
                public void treeNodesInserted(final TreeModelEvent e) {
                }

                @Override
                public void treeNodesRemoved(final TreeModelEvent e) {
                }

                @Override
                public void treeStructureChanged(final TreeModelEvent e) {
                    SwingUtilities.invokeLater(new Thread() {

                            @Override
                            public void run() {
                                final List<Node> nodes = searchResultsTree.getResultNodes();
                                final Collection<BaseEntity> entities = new ArrayList<BaseEntity>();
                                if ((nodes != null) && (nodes.size() > 0)) {
                                    for (final Node node : nodes) {
                                        if ((node != null) && (node instanceof MetaObjectNode)) {
                                            final MetaObjectNode moNode = (MetaObjectNode)node;
                                            final MetaObject mo = moNode.getObject();
                                            if (mo != null) {
                                                final CidsBean bean = mo.getBean();
                                                if (bean instanceof BaseEntity) {
                                                    entities.add((BaseEntity)bean);
                                                }
                                            }
                                        }
                                    }

                                    final TreeSet<BaseEntity> results = new TreeSet<BaseEntity>(
                                            new ReverseComparator(
                                                new EntityComparator(new ReverseComparator(new LeuchteComparator()))));
                                    CidsBroker.addCollectionToSortedSet(results, entities);

                                    setSearchResult(results);
                                    enableSearch();
                                    fireSearchFinished();
                                }
                            }
                        });
                }
            });

        setComponentRegistry(ComponentRegistry.getRegistry());
    }

    /**
     * DOCUMENT ME!
     */
    public void lookupWidgets() {
        try {
            for (final BelisWidget widget : Lookup.getDefault().lookupAll(BelisWidget.class)) {
                try {
                    widget.setBroker(this);
                    if (widget instanceof MapWidget) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Mapwidget found");
                        }
                        mapWidget = (MapWidget)widget;
                    }

                    addWidget(widget);
                } catch (Exception ex) {
                    LOG.error("Error while initializing widget", ex);
                }
            }
        } catch (Throwable ex) {
            LOG.error("Error while lookup of widgets", ex);
        }
    }

    /**
     * DOCUMENT ME!
     */
    public void initMappingComponent() {
        final MappingComponent mappingComponent = new MappingComponent();
        final MetaSearchHelper metaSearchComponentFactory = MetaSearchHelper.createNewInstance(
                true,
                MappingComponent.CREATE_SEARCH_POLYGON,
                mappingComponent,
                null);

        setMappingComponent(mappingComponent);
        setMetaSearchComponentFactory(metaSearchComponentFactory);
    }

    @Override
    public void masterConfigure(final Element parent) {
        initToolbar();
        try {
            final Element prefs = parent.getChild("Options");
            try {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("MaxSearchResults: " + prefs.getChildText("MaxSearchResults"));
                }
                maxSearchResults = Integer.valueOf(prefs.getChildText("MaxSearchResults"));
            } catch (Exception ex) {
                LOG.warn("Error while reading max value for search results.Using default: " + maxSearchResults, ex);
            }
        } catch (Exception ex) {
            LOG.warn("Error while reading options");
        }
        try {
            saveWaitDialog = new SaveWaitDialog(StaticSwingTools.getParentFrame(getParentComponent()), true);
            cancelWaitDialog = new CancelWaitDialog(StaticSwingTools.getParentFrame(getParentComponent()), true);
        } catch (Exception ex) {
            LOG.warn("Error while creating search and wait dialog");
        }

        System.out.println("masterConfigure: " + BelisBroker.class.getName());

        /*
         * <emailConfiguration username="" password="" senderAddress="sebastian.puhl@cismet.de"
         * smtpHost="smtp.uni-saarland.de"> <neuesKommunalesFinanzmanagement>
         * <receiver>sebastian.puhl@cismet.de</receiver> </neuesKommunalesFinanzmanagement> <failures>
         * <receiver>sebastian.puhl@cismet.de</receiver> </failures> </emailConfiguration>
         */

        try {
            try {
                final Element loggingConf = parent.getChild("Logging");
                loggingProperties = loggingConf.getChildText("LoggingProperties");
                initLog4J();
            } catch (final Exception ex) {
                LOG.error("Error while configuring logging", ex);
            }

            System.out.println("masterConfigure: " + BelisBroker.class.getName());
            configManager.addConfigurable(layoutManager);
            configManager.configure(layoutManager);
            configManager.addConfigurable(metaSearchComponentFactory);
            configManager.configure(metaSearchComponentFactory);
            for (final BelisWidget widget : getWidgets()) {
                configManager.addConfigurable(widget);
                configManager.configure(widget);
            }
            customizeApplication();
        } catch (Exception ex) {
            LOG.error("Fehler beim konfigurieren des Lagis Brokers: ", ex);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("masterConfigure:" + BelisBroker.class.getName());
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Configure Belis specific borker");
        }
        doBelisBinding();
        customizeMapWidget();
        ((DefaultFeatureCollection)getMappingComponent().getFeatureCollection()).addVetoableSelectionListener(this);
        // Configure Font for map object labels (keystring)
        // ToDo make configurable;
        try {
            final Element prefs = parent.getChild("Options");
            IconUtils.setFont(new Font(
                    "Courier",
                    Font.PLAIN,
                    Integer.parseInt(prefs.getChildText("MapObjectFontSize"))));
        } catch (Exception ex) {
            final int defaultFontSize = 15;
            LOG.warn("Error setting fontsize for map objects. Setting font to default: " + defaultFontSize);
            IconUtils.setFont(new Font("Courier", Font.PLAIN, defaultFontSize));
        }

        showMainApplication();
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public MapWidget getMapWidget() {
        return mapWidget;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  mapWidget  DOCUMENT ME!
     */
    public void setMapWidget(final MapWidget mapWidget) {
        this.mapWidget = mapWidget;
    }

    @Override
    public void configure(final Element parent) {
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getCurrentValidationErrorMessage() {
        return currentValidationErrorMessage;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public StatusBar getStatusBar() {
        return statusBar;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  statusBar  DOCUMENT ME!
     */
    public void setStatusBar(final StatusBar statusBar) {
        this.statusBar = statusBar;
    }

    /**
     * TODO configurieren ob es ausgeführt werden soll oder nicht z.B. boolean
     */
    public void warnIfThreadIsNotEDT() {
        if (!EventQueue.isDispatchThread()) {
            LOG.fatal("current Thread is not EDT, but should be --> look", new CurrentStackTrace());
        }
    }

    /**
     * DOCUMENT ME!
     */
    public void warnIfThreadIsEDT() {
        if (EventQueue.isDispatchThread()) {
            LOG.fatal("current Thread is EDT, but should not --> look", new CurrentStackTrace());
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  workerThread  DOCUMENT ME!
     */
    public void execute(final SwingWorker workerThread) {
        try {
            execService.submit(workerThread);
            if (LOG.isDebugEnabled()) {
                LOG.debug("SwingWorker an Threadpool übermittelt");
            }
        } catch (Exception ex) {
            LOG.fatal("Fehler beim starten eines Swingworkers", ex);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   changeListener  DOCUMENT ME!
     *
     * @throws  UnsupportedOperationException  DOCUMENT ME!
     */
    public void fireChangeFinished(final ObjectChangeListener changeListener) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * DOCUMENT ME!
     */
    private void initLog4J() {
        try {
            PropertyConfigurator.configure(BelisBroker.class.getResource(loggingProperties));
            LOG.info("Log4J System erfolgreich konfiguriert");
        } catch (Exception ex) {
            System.err.println("Fehler bei Log4J Initialisierung");
        }
    }

    /**
     * Add PropertyChangeListener.
     *
     * @param  listener  DOCUMENT ME!
     */
    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove PropertyChangeListener.
     *
     * @param  listener  DOCUMENT ME!
     */
    public void removePropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    /**
     * public JFrame getParentFrame() { return parentFrame; } public void setParentFrame(JFrame parentFrame) {
     * this.parentFrame = parentFrame; }.
     */
    public void showMainApplication() {
        final Thread t = new Thread() {

                @Override
                public void run() {
                    Frame frame = StaticSwingTools.getParentFrame(getParentComponent());
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("MainApplication frame: " + frame);
                    }
                    while ((frame == null)
                                || ((frame instanceof AbstractPlugin) && !((AbstractPlugin)frame).isReadyToShow())) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("frame is null or not ready going to sleep");
                        }
                        try {
                            this.sleep(1000);
                            frame = StaticSwingTools.getParentFrame(getParentComponent());
                        } catch (InterruptedException ex) {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("Sleep was interuppted running again.");
                            }
                            run();
                        }
                    }
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("frame available and ready to show");
                        // toDo has to be here because it must be sure that the login is over
                        LOG.debug("check read mode");
                    }
                    if (isFullReadOnlyMode()) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("is inFullReadOnlyMode disable edit buttions");
                        }
                        btnSwitchInEditmode.setEnabled(false);
                        btnSwitchInCreateMode.setEnabled(false);
                    }
                    mapWidget.setInteractionMode();
                    frame.setVisible(true);
                }
            };
        t.start();
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getAccountName() {
        if (account == null) {
            LOG.fatal("Benutzername unvollständig: " + account);
        }
        return account;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  aAccount  DOCUMENT ME!
     */
    public void setAccountName(final String aAccount) {
        account = aAccount;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public ConfigurationManager getConfigManager() {
        return configManager;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  applicationName  DOCUMENT ME!
     */
    public void setApplicatioName(final String applicationName) {
        this.applicationName = applicationName;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getApplicationName() {
        return applicationName;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  configManager  DOCUMENT ME!
     */
    public void setConfigManager(final ConfigurationManager configManager) {
        this.configManager = configManager;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public LayoutManager getLayoutManager() {
        return layoutManager;
    }

    /**
     * if there is more need --> Listener.
     */
    public void pluginConstructionDone() {
        try {
            final Runnable layoutRun = new Runnable() {

                    @Override
                    public void run() {
                        layoutManager.configureInfoNodeDocking();
                        layoutManager.doLayoutInfoNodeDefaultFile();
                    }
                };
            if (EventQueue.isDispatchThread()) {
                layoutRun.run();
            } else {
                EventQueue.invokeLater(layoutRun);
            }
        } catch (Exception ex) {
            LOG.error("Error while setting layout: ", ex);
        }
        // getMappingComponent().unlock();
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public RootWindow getRootWindow() {
        return layoutManager.getRootWindow();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  color  DOCUMENT ME!
     */
    public void setTitleBarComponentpainter(final Color color) {
        getRootWindow().getRootWindowProperties()
                .getViewProperties()
                .getViewTitleBarProperties()
                .getNormalProperties()
                .getShapedPanelProperties()
                .setComponentPainter(new GradientComponentPainter(
                        color,
                        new Color(236, 233, 216),
                        color,
                        new Color(236, 233, 216)));
    }

    /**
     * DOCUMENT ME!
     *
     * @param  left   DOCUMENT ME!
     * @param  right  DOCUMENT ME!
     */
    public void setTitleBarComponentpainter(final Color left, final Color right) {
        getRootWindow().getRootWindowProperties()
                .getViewProperties()
                .getViewTitleBarProperties()
                .getNormalProperties()
                .getShapedPanelProperties()
                .setComponentPainter(new GradientComponentPainter(left, right, left, right));
    } // ToDo outsource in Toolbarmanager

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public JButton getBtnAcceptChanges() {
        return btnAcceptChanges;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  btnAcceptChanges  DOCUMENT ME!
     */
    public void setBtnAcceptChanges(final JButton btnAcceptChanges) {
        this.btnAcceptChanges = btnAcceptChanges;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public JButton getBtnDiscardChanges() {
        return btnDiscardChanges;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  btnDiscardChanges  DOCUMENT ME!
     */
    public void setBtnDiscardChanges(final JButton btnDiscardChanges) {
        this.btnDiscardChanges = btnDiscardChanges;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public JButton getBtnReloadFlurstueck() {
        return btnReload;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  btnReloadFlurstueck  DOCUMENT ME!
     */
    public void setBtnReloadFlurstueck(final JButton btnReloadFlurstueck) {
        this.btnReload = btnReloadFlurstueck;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public JButton getBtnSwitchInEditmode() {
        return btnSwitchInEditmode;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  btnSwitchInEditmode  DOCUMENT ME!
     */
    public void setBtnSwitchInEditmode(final JButton btnSwitchInEditmode) {
        this.btnSwitchInEditmode = btnSwitchInEditmode;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public JButton getBtnSwitchInCreateMode() {
        return btnSwitchInCreateMode;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  btnSwitchInCreateMode  DOCUMENT ME!
     */
    public void setBtnSwitchInCreateMode(final JButton btnSwitchInCreateMode) {
        this.btnSwitchInCreateMode = btnSwitchInCreateMode;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public JButton getCmdPrint() {
        return cmdPrint;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cmdPrint  DOCUMENT ME!
     */
    public void setCmdPrint(final JButton cmdPrint) {
        this.cmdPrint = cmdPrint;
    }
    /**
     * DOCUMENT ME!
     */
    private void initToolbar() {
        try {
            toolbar = new javax.swing.JToolBar();
            toolbar.setBorderPainted(false);
            toolbar.setRollover(true);

            editButtonsToolbar = new EditButtonsToolbar();
            getToolbar().add(editButtonsToolbar);
            addSeparatorToToolbar();

            cmdPrint.setIcon(new javax.swing.ImageIcon(
                    getClass().getResource("/de/cismet/commons/architecture/resource/icon/toolbar/frameprint.png"))); // NOI18N

            cmdPrint.setToolTipText("Drucken");
            cmdPrint.setBorderPainted(false);
            cmdPrint.setFocusable(false);
            cmdPrint.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            final Dimension size = new Dimension(23, 23);
            cmdPrint.setPreferredSize(size);
            cmdPrint.setMinimumSize(size);
            cmdPrint.setMaximumSize(size);
            cmdPrint.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            cmdPrint.addActionListener(new java.awt.event.ActionListener() {

                    @Override
                    public void actionPerformed(final java.awt.event.ActionEvent evt) {
                        final String oldMode = getMappingComponent().getInteractionMode();
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("oldInteractionMode:" + oldMode);
                        }
                        // Enumeration en = cmdGroupPrimaryInteractionMode.getElements();
                        // togInvisible.setSelected(true);
                        getMappingComponent().showPrintingSettingsDialog(oldMode);
                    }
                });

            final JPanel printPanel = new JPanel();
            printPanel.setLayout(new java.awt.GridBagLayout());
            printPanel.setMaximumSize(new Dimension(29, 23));
            printPanel.setMinimumSize(new Dimension(29, 23));
            printPanel.setPreferredSize(new Dimension(29, 23));
            final java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            printPanel.add(cmdPrint, gridBagConstraints);
            toolbar.add(printPanel);

            addSeparatorToToolbar();
        } catch (Exception ex) {
            System.out.println("Exception while initializing toolbar");
            LOG.error("Exception while initializing toolbar.", ex);
        }
    }

    /**
     * DOCUMENT ME!
     */
    public void fireSaveStartedAndExecuteSaveCancelWorker() {
        fireSaveStarted();
        execute(new SaveCancelWorker(SaveCancelWorker.SAVE_MODE));
    }

    /**
     * DOCUMENT ME!
     */
    public void fireCancelStartedAndExecuteSaveCancelWorker() {
        fireCancelStarted();
        execute(new SaveCancelWorker(SaveCancelWorker.CANCEL_MODE));
    }

    /**
     * DOCUMENT ME!
     */
    protected void fireSaveFinished() {
        saveWaitDialog.setVisible(false);
    }

    /**
     * DOCUMENT ME!
     */
    protected void fireCancelFinished() {
        EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    cancelWaitDialog.setVisible(false);
                }
            });
    }

    /**
     * DOCUMENT ME!
     */
    protected void fireCancelStarted() {
        EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    cancelWaitDialog.setLocationRelativeTo(StaticSwingTools.getParentFrame(getParentComponent()));
                    cancelWaitDialog.setVisible(true);
                }
            });
    }

    /**
     * DOCUMENT ME!
     */
    protected void fireSaveStarted() {
        EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    saveWaitDialog.setLocationRelativeTo(StaticSwingTools.getParentFrame(getParentComponent()));
                    saveWaitDialog.setVisible(true);
                }
            });
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public JToolBar getToolbar() {
        return toolbar;
    }

    /**
     * DOCUMENT ME!
     */
    public void addSeparatorToToolbar() {
        getToolbar().add(createToolBarSeperator());
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public JSeparator createToolBarSeperator() {
        final JSeparator tmpSeperator = new JSeparator();
        tmpSeperator.setOrientation(javax.swing.SwingConstants.VERTICAL);
        tmpSeperator.setMaximumSize(new Dimension(2, 32767));
        tmpSeperator.setMinimumSize(new Dimension(2, 25));
        tmpSeperator.setPreferredSize(new Dimension(2, 23));
        return tmpSeperator;
    }

    /**
     * ToDo!!! use Backgroundworker
     *
     * @return  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    // ToDo idea mabey a method where the instance above collects the objects to save Set objectsToSave
    // ToDo in Background
    // ToDo would be couler if the swingworker would get the runnable and execute it in edt
    public Runnable save() throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("save");
        }
        if (isInCreateMode()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(workbenchWidget.getNewObjects().size() + " Objects to Save");
            }
            CidsBroker.getInstance().saveObjects(workbenchWidget.getNewObjects(), getAccountName());
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug(getCurrentSearchResults().size() + " Objects to Save");
            }
            CidsBroker.getInstance().saveObjects(getCurrentSearchResults(), getAccountName());
        }

        CidsBroker.getInstance().deleteEntities(workbenchWidget.getObjectsToRemove(), getAccountName());
        if (LOG.isDebugEnabled()) {
            LOG.debug("Changes are saved");
        }

        final Runnable runnable = new Runnable() {

                @Override
                public void run() {
                    workbenchWidget.objectsRemoved();
                    if (isInCreateMode()) {
                        workbenchWidget.clearNewObjects();
                        // ToDo search Map for Objects
                    } else {
                    }
                    // ToDo disabled Functionality 04.05.2009
                    // workbenchWidget.moveNewObjectsAfterSave();
                    if (isInCreateMode()) {
                        setCurrentSearchResults(new TreeSet());
                    }
                }
            };

        if (SwingUtilities.isEventDispatchThread()) {
            runnable.run();
        } else {
            EventQueue.invokeAndWait(runnable);
        }

        if (isInCreateMode()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("refreshing SearchResults. Doing mapsearch");
            }
            search(getMappingComponent().getCurrentBoundingBox());
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("not in createmode");
            }
        }
        return null;
    }

    /**
     * DOCUMENT ME!
     */
    protected void saveFailed() {
        showSaveErrorDialog();
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    protected Runnable cancel() throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("cancel");
        }
//        try {
        workbenchWidget.saveSelectedElementAndUnselectAll();
//            Set tmpResults = null;
        if (!isInCreateMode() && (lastSearch != null)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("is not in create mode. Refreshing search results with last search");
            }
//                if (lastSearch.getBoundingBox() != null) {
            search(lastSearch.getBoundingBox());
//                } else {
//                    search(lastSearch.getStrassenschluessel(),
//                        lastSearch.getKennziffer(),
//                        lastSearch.getLaufendenummer());
//                }
        }
//            final Set refreshedSearchResults = tmpResults;

        // ToDo Refresh the already persited new objects;
        // result.add(new Standort((int)(10000*Math.random())));
        final Runnable cancelEDTRun = new Runnable() {

                @Override
                public void run() {
                    if (!isInCreateMode()) {
                        // ToDo After this statement there is a exception console (case there are search results and
                        // the edit modus is cancled) setCurrentSearchResults(refreshedSearchResults);
                        workbenchWidget.restoreRemovedObjects();
                    } else {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("is in create mode");
                        }
                        workbenchWidget.clearNewObjects();
                    }
                    // refreshMap();
                    workbenchWidget.restoreSelectedElementIfPossible();
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Objects are refreshed");
                    }
                }
            };
        // runOrEDTDispatch(cancelEDTRun);
        return cancelEDTRun;
//        } catch (ActionNotSuccessfulException ex) {
//            LOG.error("Error while refreshing objects: " + ex);
//            return null;
//        }
    }

    /**
     * DOCUMENT ME!
     */
    protected void cancelFailed() {
        // Todo cancel failed
    }

    /**
     * DOCUMENT ME!
     *
     * @param   ttable  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public JXTreeTable decorateWithAlternateHighlighting(final JXTreeTable ttable) {
        ttable.addHighlighter(ALTERNATE_ROW_HIGHLIGHTER);
        return ttable;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   ttable  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public JXTreeTable decorateWithNoGeometryHighlighter(final JXTreeTable ttable) {
        final HighlightPredicate noGeometryPredicate = new HighlightPredicate() {

                @Override
                public boolean isHighlighted(final Component renderer, final ComponentAdapter componentAdapter) {
                    // int displayedIndex = componentAdapter.row;
                    // nt modelIndex = ((JXTreeTable) ttable).getFilters().convertRowIndexToModel(displayedIndex);
                    try {
                        final Object userObj =
                            ((AbstractMutableTreeTableNode)ttable.getPathForRow(componentAdapter.row)
                                        .getLastPathComponent()).getUserObject();
                        if ((userObj != null) && (userObj instanceof GeoBaseEntity)) {
                            return ((GeoBaseEntity)userObj).getGeometry() == null;
                        }
                    } catch (Exception ex) {
                        LOG.error("Exception in Highlighter: ", ex);
                    }
                    return false;
                        // ReBe r = model.get//tableModel.getReBeAtRow(modelIndex);
                        // return r != null && r.getGeometry() == null;
                }
            };

        final Highlighter noGeometryHighlighter = new ColorHighlighter(noGeometryPredicate, BelisBroker.gray, null);
        ttable.addHighlighter(noGeometryHighlighter);
        return ttable;
    }

    /**
     * DOCUMENT ME!
     */
    public void showSaveErrorDialog() {
        final JDialog dialog = new JDialog(StaticSwingTools.getParentFrame(getParentComponent()),
                "Fehler beim speichern...",
                true);
        dialog.setIconImage(((ImageIcon)BelisIcons.icoError16).getImage());
        dialog.add(new SaveErrorDialogPanel());
        dialog.pack();
        dialog.setLocationRelativeTo(getParentComponent());
        dialog.setVisible(true);
    }

    /**
     * DOCUMENT ME!
     */
    private void customizeApplication() {
        customizeApplicationToolbar();
    }

    /**
     * DOCUMENT ME!
     */
    public void customizeApplicationToolbar() {
        addCreateToolBar();
        addFilterToolbar();
        addCopyPasteToolBar();
        addAddressSearch();
        addLocationSearch();
        getToolbar().add(metaSearchComponentFactory.getCmdPluginSearch());
        addSeparatorToToolbar();
        addMapSearchControl();
        btnAcceptChanges.setIcon(BelisIcons.icoAccept22);
        btnDiscardChanges.setIcon(BelisIcons.icoCancel22);
        btnSwitchInEditmode.setIcon(BelisIcons.icoEdit22);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public StatusBar getStatusbar() {
        return statusbar;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  statusbar  DOCUMENT ME!
     */
    public void setStatusbar(final StatusBar statusbar) {
        this.statusbar = statusbar;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean isStatusBarEnabled() {
        return statusBarEnabled;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isStatusBarEnabled  DOCUMENT ME!
     */
    public void setStatusBarEnabled(final boolean isStatusBarEnabled) {
        this.statusBarEnabled = isStatusBarEnabled;
    }

    /**
     * final JButton btnStandort = new JButton("ST");
     *
     * @return  DOCUMENT ME!
     */
    public Set<BaseEntity> getCurrentSearchResults() {
        return currentSearchResults;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  currentSearchResults  DOCUMENT ME!
     */
    public void setCurrentSearchResults(final Set<BaseEntity> currentSearchResults) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("setSearchResults");
        }
        final Set<BaseEntity> old = this.currentSearchResults;
        if ((this.currentSearchResults != null) && (currentSearchResults != null)
                    && this.currentSearchResults.equals(currentSearchResults)) {
            LOG.warn("Sets are equals no propertyChange doing manually refresh --> ToDo fix me");
            this.currentSearchResults = currentSearchResults;
            refreshWidgets(getCurrentSearchResults());
        } else {
            this.currentSearchResults = currentSearchResults;
        }
        // ToDo why is it not working if I use null there is no equals check ???
        propertyChangeSupport.firePropertyChange(PROP_CURRENT_SEARCH_RESULTS, old, currentSearchResults);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  set  DOCUMENT ME!
     */
    public void setSearchResult(final Set set) {
        if (set != null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Search results: " + set.size());
            }
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Search results delivered no result");
            }
        }

        clearMap();
        EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    if ((set != null) && !IsGreaterMaxSearchResults(set.size())) {
                        setCurrentSearchResults(set);
                        // ToDo PropertyChangeListener;
                        refreshMap();
                        getMappingComponent().zoomToFullFeatureCollectionBounds();
                    }
                }
            });
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public RetrieveWorker getLastSearch() {
        return lastSearch;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  lastSearch  DOCUMENT ME!
     */
    public void setLastSearch(final RetrieveWorker lastSearch) {
        this.lastSearch = lastSearch;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public DetailWidget getDetailWidget() {
        return detailWidget;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  detailWidget  DOCUMENT ME!
     */
    public void setDetailWidget(final DetailWidget detailWidget) {
        this.detailWidget = detailWidget;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   searchCount  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean IsGreaterMaxSearchResults(final int searchCount) {
        if (searchCount > maxSearchResults) {
            JOptionPane.showMessageDialog(StaticSwingTools.getParentFrame(getParentComponent()),
                "Es wurden zu viele Ergebnisse gefunden: "
                        + searchCount
                        + ". Zulässige Anzahl sind: "
                        + maxSearchResults
                        + ".\nBitte verändern Sie Ihre Auswahl.",
                "Zu viele Ergebnisse",
                JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        return false;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  bb  DOCUMENT ME!
     */
    public void search(final BoundingBox bb) {
        try {
            disableSearch();
            final BelisSearchStatement belisSearchStatement = new BelisSearchStatement();
            belisSearchStatement.setGeometry(bb.getGeometry(-1));
            CidsSearchExecutor.searchAndDisplayResultsWithDialog(belisSearchStatement);
        } catch (Exception ex) {
            LOG.error("Exception while searching boundingbox: ", ex);
            enableSearch();
            fireSearchFinished();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  strassenschluessel  DOCUMENT ME!
     * @param  kennziffer          DOCUMENT ME!
     * @param  laufendeNummer      DOCUMENT ME!
     */
    public void search(final String strassenschluessel, final Integer kennziffer, final Integer laufendeNummer) {
        try {
            disableSearch();
            final BelisLocationSearchStatement searchStatement = new BelisLocationSearchStatement(
                    strassenschluessel,
                    kennziffer,
                    laufendeNummer);
            CidsSearchExecutor.searchAndDisplayResultsWithDialog(searchStatement);
        } catch (Exception ex) {
            LOG.error("Exception while searching by location: ", ex);
            enableSearch();
            fireSearchFinished();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   searchResults  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private Collection<Feature> addEntityRecursiveToMap(final Collection searchResults) {
        final Collection<Feature> featuresToAdd = new HashSet<Feature>();
        if (searchResults != null) {
            for (final Object currentResult : searchResults) {
                if ((currentResult instanceof VeranlassungCustomBean)) {
                    final VeranlassungCustomBean veranlassungCustomBean = (VeranlassungCustomBean)currentResult;
                    if (veranlassungCustomBean.getGeometry() != null) {
                        featuresToAdd.add((StyledFeature)currentResult);
                    }
                    featuresToAdd.addAll(addEntityRecursiveToMap(veranlassungCustomBean.getAr_abzweigdosen()));
                    featuresToAdd.addAll(addEntityRecursiveToMap(veranlassungCustomBean.getAr_leitungen()));
                    featuresToAdd.addAll(addEntityRecursiveToMap(veranlassungCustomBean.getAr_leuchten()));
                    featuresToAdd.addAll(addEntityRecursiveToMap(veranlassungCustomBean.getAr_mauerlaschen()));
                    featuresToAdd.addAll(addEntityRecursiveToMap(veranlassungCustomBean.getAr_schaltstellen()));
                    featuresToAdd.addAll(addEntityRecursiveToMap(veranlassungCustomBean.getAr_standorte()));
                } else if ((currentResult instanceof ArbeitsauftragCustomBean)) {
                    final ArbeitsauftragCustomBean arbeitsauftragCustomBean = (ArbeitsauftragCustomBean)currentResult;
                    for (final ArbeitsprotokollCustomBean protokoll : arbeitsauftragCustomBean.getN_protokolle()) {
                        featuresToAdd.add(protokoll.getFk_abzweigdose());
                    }
                } else if ((currentResult instanceof StyledFeature)
                            && (((StyledFeature)currentResult).getGeometry() != null)) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("SearchResult is Styled Feature and geometry != null --> adding feature to map.");
                    }
                    featuresToAdd.add((StyledFeature)currentResult);
                    // getMappingComponent().getFeatureCollection().addFeature((StyledFeature) currentResult);
                    if ((currentResult instanceof TdtaStandortMastCustomBean)
                                && (((TdtaStandortMastCustomBean)currentResult).getLeuchten() != null)) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug(
                                "SearchResult is instance of Standort and owns Leuchte objects --> also adding to Map");
                        }
                        featuresToAdd.addAll(addEntityRecursiveToMap(
                                ((TdtaStandortMastCustomBean)currentResult).getLeuchten()));
                    }
                    if (currentResult instanceof LeitungCustomBean) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("SearchResult is instance of Leitung. Setting PropertyChangeListener");
                        }
                        ((LeitungCustomBean)currentResult).addPropertyChangeListener(this);
                    }
                    if (currentResult instanceof MauerlascheCustomBean) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("SearchResult is instance of Leitung. Setting PropertyChangeListener");
                        }
                        ((MauerlascheCustomBean)currentResult).addPropertyChangeListener(this);
                    }
                }
            }
        }
        return featuresToAdd;
    }

    /**
     * DOCUMENT ME!
     */
    private void clearMap() {
        try {
            if (EventQueue.isDispatchThread()) {
                getMappingComponent().getFeatureCollection().removeAllFeatures();
            } else {
                EventQueue.invokeAndWait(new Runnable() {

                        @Override
                        public void run() {
                            clearMap();
                        }
                    });
            }
        } catch (Exception ex) {
            LOG.error("Error while clearing map: ", ex);
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void refreshMap() {
        if (getCurrentSearchResults() != null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("refreshMap (features): " + getCurrentSearchResults().size());
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("refreshMap featureCollection: "
                            + getMappingComponent().getFeatureCollection().getFeatureCount());
            }
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("refreshMap no results");
            }
        }
        try {
            if (EventQueue.isDispatchThread()) {
                getMappingComponent().getFeatureCollection().removeAllFeatures();
                getMappingComponent().getFeatureCollection()
                        .substituteFeatures(addEntityRecursiveToMap(getCurrentSearchResults()));
            } else {
                EventQueue.invokeAndWait(new Runnable() {

                        @Override
                        public void run() {
                            refreshMap();
                        }
                    });
            }
        } catch (Exception ex) {
            LOG.error("Error while refreshińg map: ", ex);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public WorkbenchWidget getWorkbenchWidget() {
        return workbenchWidget;
    }

    /**
     * DOCUMENT ME!
     */
    private void doBelisBinding() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("DoBelisBinding()");
        }
        for (final BelisWidget curWidget : getWidgets()) {
            if ((workbenchWidget != null) && (detailWidget != null)) {
                break;
            } else if (curWidget instanceof WorkbenchWidget) {
                workbenchWidget = (WorkbenchWidget)curWidget;
                workbenchWidget.addPropertyChangeListener(new PropertyChangeListener() {

                        @Override
                        public void propertyChange(final PropertyChangeEvent evt) {
                            if (evt.getPropertyName().equals(WorkbenchWidget.PROP_SELECTEDTREENODES)) {
                                copyPasteToolbar.clipboardChanged();
                            }
                        }
                    });
            } else if (curWidget instanceof DetailWidget) {
                detailWidget = (DetailWidget)curWidget;
            }
        }
        if ((workbenchWidget == null) || (detailWidget == null)) {
            LOG.warn("Workbench Widget could not be bound to Detail Widget because one of them == null");
        }

        final PropertyChangeListener listener = new PropertyChangeListener() {

                @Override
                public void propertyChange(final PropertyChangeEvent evt) {
                    if (evt.getPropertyName().equals(WorkbenchWidget.PROP_SELECTEDTREENODES)) {
                        Object currentEntity = null;
                        final TreePath treePath = ((WorkbenchWidget)evt.getSource()).getSelectedTreeNode();
                        if (treePath != null) {
                            final CustomMutableTreeTableNode customMutableTreeTableNode = (CustomMutableTreeTableNode)
                                treePath.getLastPathComponent();
                            if (customMutableTreeTableNode != null) {
                                currentEntity = (customMutableTreeTableNode).getUserObject();
                            }
                        }
                        detailWidget.setCurrentEntity(currentEntity);
                        panCreate.setCurrentEntity(currentEntity);
                    }
                }
            };
        if (workbenchWidget != null) {
            workbenchWidget.addPropertyChangeListener(listener);
        }
    }

    /**
     * ToDo more than ugly with the isPendingForCreate must somehow inserted in Framework.
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public void acquireLock() throws Exception {
        // ToDo should be some notification for edit mode this is not the right place
        if (isPendingForCreateMode.get()) {
            setInCreateMode(true);
            isPendingForCreateMode.set(false);
        }
        if (!isInCreateMode()) {
            try {
                CidsBroker.getInstance().lockEntity(currentSearchResults, getAccountName());
            } catch (ActionNotSuccessfulException ex) {
                LOG.error("Error while creating lock:", ex);
                isPendingForCreateMode.set(false);
                throw new Exception(
                    "Sperren konnten nicht angelegt werden, wechseln in Editmodus nicht möglich");
            } catch (LockAlreadyExistsException ex) {
                LOG.info("Some of the objects are already locked", ex);
                isPendingForCreateMode.set(false);
                final ArrayList<SperreCustomBean> alreadyLocked = ex.getAlreadyExisingLocks();
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Count of already locked objects: " + alreadyLocked.size());
                }
                showObjectsLockedDialog(alreadyLocked);
                isPendingForCreateMode.set(false);
                throw new LockingNotSuccessfulException(
                    "Einige der Objekte konnten nicht gesperrt werden, weil sie schon gesperrt sind");
            }
        } else {
            LOG.info("Create Modus no locks necessary");
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public void releaseLock() throws Exception {
        setInCreateMode(false);
        if (!isInCreateMode()) {
            final Set unsuccessfulObjects = null;
            try {
                // ToDo problem with unlocking of entities not locked by this user
                CidsBroker.getInstance().unlockEntity(currentSearchResults);
            } catch (ActionNotSuccessfulException ex) {
                LOG.error("Error while unlocking locked objects:", ex);
                throw new Exception("Angelegte sperren konnten nicht gelöst werden.");
            }
            if ((unsuccessfulObjects != null) && !unsuccessfulObjects.isEmpty()) {
                // ToDo what to do ? Error to the user and go
                LOG.error("Some of the objects couldn't be unlocked posting to the user");
            }
        } else {
            LOG.info("Create Modus no locks necessary");
        }
        // ToDo should be some notification for edit mode this is not the right place
    }

    /**
     * ToDo better over currentSearch results.
     *
     * @param  selectable  DOCUMENT ME!
     */
    private void setAllFeaturesSelectable(final boolean selectable) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("setAllFeaturesNotEditable()");
        }
        if (!selectable) {
            getMappingComponent().getFeatureCollection().unselectAll();
            currentFeatures.clear();
            final FeatureCollection featureCollection = getMappingComponent().getFeatureCollection();
            if (featureCollection != null) {
                for (final Feature curFeature : featureCollection.getAllFeatures()) {
                    if (curFeature instanceof GeoBaseEntity) {
                        ((GeoBaseEntity)curFeature).setSelectable(selectable);
                        getMappingComponent().getFeatureCollection().reconsiderFeature(curFeature);
                        currentFeatures.add((GeoBaseEntity)curFeature);
                    }
                }
            }
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("restoring features");
            }
            if (currentFeatures.size() > 0) {
                for (final GeoBaseEntity curFeature : currentFeatures) {
                    curFeature.setSelectable(selectable);
                    getMappingComponent().getFeatureCollection().reconsiderFeature(curFeature);
                }
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  lockedObjects  DOCUMENT ME!
     */
    private void showObjectsLockedDialog(final ArrayList<SperreCustomBean> lockedObjects) {
        final JDialog dialog = new JDialog(StaticSwingTools.getParentFrame(getParentComponent()),
                "Gesperrte Objekte...",
                true);
        if (lockPanel == null) {
            lockPanel = new AlreadyLockedObjectsPanel(lockedObjects);
        } else {
            lockPanel.setLockedObjects(lockedObjects);
        }
        dialog.setIconImage(((ImageIcon)BelisIcons.icoError16).getImage());
        dialog.add(lockPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(getParentComponent());
        dialog.setVisible(true);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  runnable  DOCUMENT ME!
     */
    public static void runOrEDTDispatch(final Runnable runnable) {
        if (runnable != null) {
            if (EventQueue.isDispatchThread()) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Thread is EDT. Execute run");
                }
                runnable.run();
            } else {
                LOG.info("Thread is not EDT. InvokeLater");
                EventQueue.invokeLater(runnable);
            }
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void customizeMapWidget() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Customizing MapWidget");
        }
        final Collection<BelisWidget> widgets = getWidgets();
        MapWidget mapWidget = null;
        if (widgets != null) {
            for (final BelisWidget curWidget : widgets) {
                if ((curWidget != null) && (curWidget instanceof MapWidget)) {
                    mapWidget = (MapWidget)curWidget;
                }
            }
            if (mapWidget != null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("MapWidget found");
                }
                this.mapWidget = mapWidget;
//                addBelisSpecificControlsToMapWidget();
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Can't customize MapWidget not found");
                }
            }
        } else {
            LOG.warn("There are no widgets");
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean isInCreateMode() {
        return inCreateMode;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isInCreateMode  DOCUMENT ME!
     */
    public void setInCreateMode(final boolean isInCreateMode) {
        this.inCreateMode = isInCreateMode;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public AddressSearchControl getAddressSearch() {
        return asPan;
    }

    /**
     * DOCUMENT ME!
     */
    private void addAddressSearch() {
        asPan = new AddressSearchControl(this);
        // ToDo should haben in Panels itself;
        addSearchControl(asPan);
        asPan.setMappingComponent(getMappingComponent());
        getConfigManager().addConfigurable(asPan);
        getConfigManager().configure(asPan);
        asPan.setFocusable(false);
        final Dimension size = new Dimension(456, 23);
        asPan.setPreferredSize(size);
        asPan.setMinimumSize(size);
        asPan.setMaximumSize(size);
        getToolbar().add(asPan);
        addSeparatorToToolbar();
    }

    /**
     * DOCUMENT ME!
     */
    private void addLocationSearch() {
        final LocationSearchControl lsPan = new LocationSearchControl(this);
        lsPan.setFocusable(false);
        addSearchControl(lsPan);
        getToolbar().add(lsPan);
        addSeparatorToToolbar();
    }

    /**
     * DOCUMENT ME!
     */
    private void addMapSearchControl() {
        mscPan = new MapSearchControl(this);
        addSearchControl(mscPan);
        mscPan.setFocusable(false);
        mscPan.setPreferredSize(new Dimension(150, 23));
        getToolbar().add(mscPan);
    }

    /**
     * DOCUMENT ME!
     */
    private void addCreateToolBar() {
        addEditable(panCreate);
        getToolbar().add(panCreate);
        getToolbar().add(createToolBarSeperator());
    }

    /**
     * DOCUMENT ME!
     */
    private void addFilterToolbar() {
        getToolbar().add(panFilter);
        getToolbar().add(createToolBarSeperator());
    }

    // ToDo mayby better to operate on the

    /**
     * DOCUMENT ME!
     */
    private void addCopyPasteToolBar() {
        addEditable(copyPasteToolbar);
        getToolbar().add(copyPasteToolbar);
        getToolbar().add(createToolBarSeperator());
    }

    /**
     * DOCUMENT ME!
     */
    public void addNewStandort() {
        workbenchWidget.selectNode(workbenchWidget.addNewStandort());
    }

    /**
     * DOCUMENT ME!
     *
     * @param  relatedObject  DOCUMENT ME!
     */
    public void addNewLeuchte(final Object relatedObject) {
        workbenchWidget.selectNode(workbenchWidget.addNewLeuchte(relatedObject));
    }

    /**
     * DOCUMENT ME!
     */
    public void addNewLeuchte() {
        workbenchWidget.selectNode(workbenchWidget.addNewLeuchte());
    }

    /**
     * DOCUMENT ME!
     */
    public void addNewMauerlasche() {
        workbenchWidget.selectNode(workbenchWidget.addNewMauerlasche());
    }

    /**
     * DOCUMENT ME!
     */
    public void addNewSchaltstelle() {
        workbenchWidget.selectNode(workbenchWidget.addNewSchaltstelle());
    }

    /**
     * DOCUMENT ME!
     */
    public void addNewLeitung() {
        workbenchWidget.selectNode(workbenchWidget.addNewLeitung());
    }

    /**
     * DOCUMENT ME!
     */
    public void addNewAbzweigdose() {
        workbenchWidget.selectNode(workbenchWidget.addNewAbzweigdose());
    }

    /**
     * DOCUMENT ME!
     */
    public void addNewVeranlassung() {
        workbenchWidget.selectNode(workbenchWidget.addNewVeranlassung());
    }

    /**
     * DOCUMENT ME!
     */
    public void addNewArbeitsauftrag() {
        workbenchWidget.selectNode(workbenchWidget.addNewArbeitsauftrag());
    }

    /**
     * DOCUMENT ME!
     *
     * @param  relatedObject  DOCUMENT ME!
     */
    public void addNewArbeitsprotokoll(final Object relatedObject) {
        workbenchWidget.selectNode(workbenchWidget.addNewArbeitsprotokoll(relatedObject));
    }

    /**
     * DOCUMENT ME!
     */
    public void removeSelectedEntity() {
        workbenchWidget.removeSelectedEntity();
    }

    @Override
    public synchronized void addSearchControl(final SearchControl searchControl) {
        if (searchControl != null) {
            searchControls.add(searchControl);
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("searchControl is null can't be add.");
            }
        }
    }

    @Override
    public synchronized void fireSearchFinished() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("fireSearchFinished");
        }
        if (!isFullReadOnlyMode) {
            btnSwitchInEditmode.setEnabled(true);
            btnSwitchInCreateMode.setEnabled(true);
        }
        btnReload.setEnabled(true);
        cmdPrint.setEnabled(true);
        for (final SearchControl curSearchControl : searchControls) {
            curSearchControl.searchFinished();
        }
    }

    @Override
    public synchronized void fireSearchStarted() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("fireSearchStarted");
        }
        btnSwitchInEditmode.setEnabled(false);
        btnSwitchInCreateMode.setEnabled(false);

        btnReload.setEnabled(false);
        cmdPrint.setEnabled(false);
        for (final SearchControl curSearchControl : searchControls) {
            curSearchControl.searchStarted();
        }
    }

    @Override
    public synchronized void removeSearchControl(final SearchControl searchControl) {
        if (searchControl != null) {
            searchControls.remove(searchControl);
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("searchControl is null can't be removed.");
            }
        }
    }

    @Override
    public synchronized void disableSearch() {
        setSearchEnabled(false);
    }

    @Override
    public synchronized void enableSearch() {
        setSearchEnabled(true);
    }

    @Override
    public synchronized void setSearchEnabled(final boolean isSearchEnabled) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("setSearchEnabled " + isSearchEnabled);
        }
//        for (final SearchControl curSearchControl : searchControls) {
//            curSearchControl.setSearchEnabled(isSearchEnabled);
//        }
    } // public Set getNewObjects() {

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("PropertyChangeEvent");
        }
        if ((evt != null) && (evt.getPropertyName() != null)) {
            if (evt.getPropertyName().equals(LeitungCustomBean.PROP__FK_LEITUNGSTYP) && (evt.getSource() != null)
                        && (evt.getSource() instanceof LeitungCustomBean)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("LeitungsTyp Changed");
                }
                lastLeitungstyp = (LeitungstypCustomBean)evt.getNewValue();
                getMappingComponent().getFeatureCollection().reconsiderFeature((LeitungCustomBean)evt.getSource());
            } else if (evt.getPropertyName().equals(TdtaLeuchtenCustomBean.PROP__LEUCHTENNUMMER)
                        && (evt.getSource() != null)
                        && (evt.getSource() instanceof TdtaLeuchtenCustomBean)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Leuchtennummer changed");
                }
                final TreePath pathToLeuchte = workbenchWidget.getTreeTableModel()
                            .getPathForUserObject(evt.getSource());
                TdtaStandortMastCustomBean parentMast = null;
                if (pathToLeuchte != null) {
                    parentMast = workbenchWidget.getParentMast(pathToLeuchte.getLastPathComponent());
                }
                if (parentMast != null) {
                    getMappingComponent().getFeatureCollection().reconsiderFeature(parentMast);
                } else {
                    final TdtaStandortMastCustomBean virtualStandort = workbenchWidget.getVirtualStandortForLeuchte(
                            (TdtaLeuchtenCustomBean)evt.getSource());
                    if (virtualStandort != null) {
                        getMappingComponent().getFeatureCollection().reconsiderFeature(virtualStandort);
                    } else {
                        LOG.warn("Leuchte is neither Hängeleuchte nor attached to a mast. Can't update label in map");
                    }
                }
            } else if (evt.getPropertyName().equals(MauerlascheCustomBean.PROP__FK_STRASSENSCHLUESSEL)
                        && (evt.getSource() != null)
                        && (evt.getSource() instanceof MauerlascheCustomBean)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Mauerlasche Straßßenschlüssel Changed");
                }
                lastMauerlascheStrassenschluessel = (TkeyStrassenschluesselCustomBean)evt.getNewValue();
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("PropertyChange not recognized from BelisBroker.");
                }
            }
        } else {
            LOG.warn("PropertyChangeEvent or PropertyName == null");
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public TkeyStrassenschluesselCustomBean getLastMauerlascheStrassenschluessel() {
        return lastMauerlascheStrassenschluessel;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  lastMauerlascheStrassenschluessel  DOCUMENT ME!
     */
    public void setLastMauerlascheStrassenschluessel(
            final TkeyStrassenschluesselCustomBean lastMauerlascheStrassenschluessel) {
        this.lastMauerlascheStrassenschluessel = lastMauerlascheStrassenschluessel;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public LeitungstypCustomBean getLastLeitungstyp() {
        return lastLeitungstyp;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  lastLeitungstyp  DOCUMENT ME!
     */
    public void setLastLeitungstyp(final LeitungstypCustomBean lastLeitungstyp) {
        this.lastLeitungstyp = lastLeitungstyp;
    }

    // should only be one mechanismn not three broker,creationtoolbar,worbenchwidget (Method)
    @Override
    public void veto() throws VetoException {
        if ((isInCreateMode() || isInEditMode()) && isVetoCheckEnabled()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(
                    "selectionVetoCheck: User is in edit mode and wants to change the current object over the map.",
                    new CurrentStackTrace());
            }
            // ToDo create convenience method isInValidState or areAllWidgetValid
            if (!validateWidgets()) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("selectionVetoCheck: One or more widgets are invalid. Informing user.");
                }
                final int answer = askUser();
                if (LOG.isDebugEnabled()) {
                    LOG.debug("answer: " + answer);
                }
                if (answer == JOptionPane.YES_OPTION) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("selectionVetoCheck: User wants to cancel changes.");
                    }
                } else {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("selectionVetoCheck: User wants to correct validation, throwing veto exception.");
                    }
                    throw new VetoException();
                }
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("selectionVetoCheck: No veto all Widgets are valid.");
                }
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public int askUser() {
        final String string1 = "Ja";
        final String string2 = "Nein";

        final Object[] options = { string1, string2 };
        return JOptionPane.showOptionDialog(StaticSwingTools.getParentFrame(getParentComponent()),
                "<html><table width=\"400\" border=\"0\"><tr><td>Nicht alle Inhalte des ausgewählten Objektes sind korrekt.<p><br>"
                        + "Grund:<br>"
                        + "<b>"
                        + getCurrentValidationErrorMessage()
                        + "</b><p><br>"
                        + "Wenn Sie das Objekt wechseln, werden die ungültigen Änderungen nicht übernommen. Möchten Sie trotzdem wechseln ?</td></tr></table></html>",
                "Achtung! Es gibt falsche Inhalte",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                options,
                string2);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean isVetoCheckEnabled() {
        return vetoCheckEnabled;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  vetoCheckEnabled  DOCUMENT ME!
     */
    public void setVetoCheckEnabled(final boolean vetoCheckEnabled) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("setVetoCheckto: " + vetoCheckEnabled, new CurrentStackTrace());
        }
        this.vetoCheckEnabled = vetoCheckEnabled;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static TkeyUnterhLeuchteCustomBean getDefaultUnterhaltLeuchte() {
        return defaultUnterhaltLeuchte;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  defaultUnterhaltLeuchte  DOCUMENT ME!
     */
    public static void setDefaultUnterhaltLeuchte(final TkeyUnterhLeuchteCustomBean defaultUnterhaltLeuchte) {
        BelisBroker.defaultUnterhaltLeuchte = defaultUnterhaltLeuchte;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static TkeyUnterhMastCustomBean getDefaultUnterhaltMast() {
        return defaultUnterhaltMast;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  defaultUnterhaltMast  DOCUMENT ME!
     */
    public static void setDefaultUnterhaltMast(final TkeyUnterhMastCustomBean defaultUnterhaltMast) {
        BelisBroker.defaultUnterhaltMast = defaultUnterhaltMast;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static TkeyDoppelkommandoCustomBean getDefaultDoppelkommando1() {
        return defaultDoppelkommando1;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  defaultDoppelkommando1  DOCUMENT ME!
     */
    public static void setDefaultDoppelkommando1(final TkeyDoppelkommandoCustomBean defaultDoppelkommando1) {
        BelisBroker.defaultDoppelkommando1 = defaultDoppelkommando1;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public EntityClipboard getEntityClipboard() {
        return entityClipboard;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean isFilterNormal() {
        return filterNormal;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  filterNormal  DOCUMENT ME!
     */
    public void setFilterNormal(final boolean filterNormal) {
        final boolean old = this.filterNormal;
        this.filterNormal = filterNormal;
        propertyChangeSupport.firePropertyChange(PROP_FILTER_NORMAL, old, filterNormal);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean isFilterVeranlassung() {
        return filterVeranlassung;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  filterVeranlassung  DOCUMENT ME!
     */
    public void setFilterVeranlassung(final boolean filterVeranlassung) {
        final boolean old = this.filterVeranlassung;
        this.filterVeranlassung = filterVeranlassung;
        propertyChangeSupport.firePropertyChange(PROP_FILTER_VERANLASSUNG, old, filterVeranlassung);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean isFilterArbeitsauftrag() {
        return filterArbeitsauftrag;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  filterArbeitsauftrag  DOCUMENT ME!
     */
    public void setFilterArbeitsauftrag(final boolean filterArbeitsauftrag) {
        final boolean old = this.filterArbeitsauftrag;
        this.filterArbeitsauftrag = filterArbeitsauftrag;
        propertyChangeSupport.firePropertyChange(PROP_FILTER_ARBEITSAUFTRAG, old, filterArbeitsauftrag);
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    class SaveCancelWorker extends SwingWorker<Runnable, Void> {

        //~ Static fields/initializers -----------------------------------------

        public static final int SAVE_MODE = 0;
        public static final int CANCEL_MODE = 1;

        //~ Instance fields ----------------------------------------------------

        private int mode;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new SaveCancelWorker object.
         *
         * @param  mode  DOCUMENT ME!
         */
        SaveCancelWorker(final int mode) {
            this.mode = mode;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        protected Runnable doInBackground() throws Exception {
            if (mode == SAVE_MODE) {
                return save();
            } else if (mode == CANCEL_MODE) {
                return BelisBroker.this.cancel();
            } else {
                LOG.warn("Mode is unkown.");
                return null;
            }
        }

        @Override
        protected void done() {
            if (isCancelled()) {
                LOG.warn("SaveUpdateWorker is canceled --> nothing to do in method done()");
                return;
            }
            try {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("done");
                }
                final Runnable resultRun = get();
                if (resultRun != null) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("result runnable != null will be executed");
                    }
                    resultRun.run();
                } else {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("result runnable == null. Can't execute");
                    }
                }
                getMappingComponent().setReadOnly(true);
                switchEditMode();
                if (LOG.isDebugEnabled()) {
                    LOG.debug("enable buttons");
                }
                if (!isFullReadOnlyMode()) {
                    btnSwitchInEditmode.setEnabled(true);
                    btnSwitchInCreateMode.setEnabled(true);
                }
                btnAcceptChanges.setEnabled(false);
                btnDiscardChanges.setEnabled(false);
                setTitleBarComponentpainter(DEFAULT_MODE_COLOR);
                if (mode == SAVE_MODE) {
                    fireSaveFinished();
                } else if (mode == CANCEL_MODE) {
                    fireCancelFinished();
                }
            } catch (Exception ex) {
                LOG.error("Failure during saving/refresh results", ex);
                if (mode == SAVE_MODE) {
                    saveFailed();
                    fireSaveFinished();
                } else if (mode == CANCEL_MODE) {
                    cancelFailed();
                    fireCancelFinished();
                }
            }
        }
    }
}
