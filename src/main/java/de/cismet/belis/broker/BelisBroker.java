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
import Sirius.navigator.plugin.ui.PluginMenuItem;
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

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;
import Sirius.server.middleware.types.MetaObjectNode;
import Sirius.server.middleware.types.Node;

import com.vividsolutions.jts.geom.Geometry;

import net.infonode.docking.RootWindow;
import net.infonode.gui.componentpainter.GradientComponentPainter;

import org.apache.commons.collections.comparators.ReverseComparator;
import org.apache.log4j.PropertyConfigurator;

import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.ComponentAdapter;
import org.jdesktop.swingx.decorator.CompoundHighlighter;
import org.jdesktop.swingx.decorator.HighlightPredicate;
import org.jdesktop.swingx.decorator.Highlighter;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.error.ErrorInfo;
import org.jdesktop.swingx.treetable.AbstractMutableTreeTableNode;

import org.jdom.Element;

import org.openide.util.Exceptions;
import org.openide.util.Lookup;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.tree.TreePath;

import de.cismet.belis.arbeitsprotokollwizard.AbstractArbeitsprotokollWizard;

import de.cismet.belis.gui.reports.ArbeitsauftraegeReportDownload;
import de.cismet.belis.gui.search.LocationSearchControl;
import de.cismet.belis.gui.search.MapSearchControl;
import de.cismet.belis.gui.search.SearchControl;
import de.cismet.belis.gui.search.SearchController;
import de.cismet.belis.gui.widget.BelisWidget;
import de.cismet.belis.gui.widget.DetailWidget;
import de.cismet.belis.gui.widget.ExtendedNavigatorAttributeEditorGui;
import de.cismet.belis.gui.widget.KeyTableListener;
import de.cismet.belis.gui.widget.MapWidget;
import de.cismet.belis.gui.widget.WorkbenchWidget;

import de.cismet.belis.panels.AlreadyLockedObjectsPanel;
import de.cismet.belis.panels.CancelWaitDialog;
import de.cismet.belis.panels.CopyPasteToolbar;
import de.cismet.belis.panels.CreateToolBar;
import de.cismet.belis.panels.EditButtonsToolbar;
import de.cismet.belis.panels.FilterToolBar;
import de.cismet.belis.panels.LockWaitDialog;
import de.cismet.belis.panels.ReleaseWaitDialog;
import de.cismet.belis.panels.SaveWaitDialog;
import de.cismet.belis.panels.SearchWaitDialog;

import de.cismet.belis.todo.CustomMutableTreeTableNode;
import de.cismet.belis.todo.CustomTreeTableModel;
import de.cismet.belis.todo.RetrieveWorker;

import de.cismet.belis.util.BelisIcons;

import de.cismet.belis2.server.search.ArbeitsauftragSearchStatement;
import de.cismet.belis2.server.search.BelisLocationSearchStatement;
import de.cismet.belis2.server.search.BelisSearchStatement;
import de.cismet.belis2.server.search.BelisTopicSearchStatement;

import de.cismet.belisEE.exception.ActionNotSuccessfulException;
import de.cismet.belisEE.exception.LockAlreadyExistsException;

import de.cismet.belisEE.util.EntityComparator;

import de.cismet.cids.custom.beans.belis2.AbzweigdoseCustomBean;
import de.cismet.cids.custom.beans.belis2.ArbeitsauftragCustomBean;
import de.cismet.cids.custom.beans.belis2.ArbeitsprotokollCustomBean;
import de.cismet.cids.custom.beans.belis2.GeometrieCustomBean;
import de.cismet.cids.custom.beans.belis2.LeitungCustomBean;
import de.cismet.cids.custom.beans.belis2.LeitungstypCustomBean;
import de.cismet.cids.custom.beans.belis2.MauerlascheCustomBean;
import de.cismet.cids.custom.beans.belis2.SchaltstelleCustomBean;
import de.cismet.cids.custom.beans.belis2.SperreCustomBean;
import de.cismet.cids.custom.beans.belis2.TdtaLeuchtenCustomBean;
import de.cismet.cids.custom.beans.belis2.TdtaStandortMastCustomBean;
import de.cismet.cids.custom.beans.belis2.TkeyDoppelkommandoCustomBean;
import de.cismet.cids.custom.beans.belis2.TkeyStrassenschluesselCustomBean;
import de.cismet.cids.custom.beans.belis2.TkeyUnterhLeuchteCustomBean;
import de.cismet.cids.custom.beans.belis2.TkeyUnterhMastCustomBean;
import de.cismet.cids.custom.beans.belis2.VeranlassungCustomBean;
import de.cismet.cids.custom.beans.belis2.WorkbenchEntity;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultBindableReferenceCombo;

import de.cismet.cids.navigator.utils.SimpleMemoryMonitoringToolbarWidget;

import de.cismet.cismap.commons.BoundingBox;
import de.cismet.cismap.commons.features.DefaultFeatureCollection;
import de.cismet.cismap.commons.features.Feature;
import de.cismet.cismap.commons.features.FeatureCollection;
import de.cismet.cismap.commons.features.StyledFeature;
import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.gui.statusbar.StatusBar;
import de.cismet.cismap.commons.interaction.CismapBroker;
import de.cismet.cismap.commons.interaction.StatusListener;
import de.cismet.cismap.commons.interaction.events.StatusEvent;
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
import de.cismet.tools.gui.downloadmanager.ByteArrayDownload;
import de.cismet.tools.gui.downloadmanager.DownloadManager;
import de.cismet.tools.gui.downloadmanager.DownloadManagerDialog;

import de.cismet.veto.VetoException;

/**
 * DOCUMENT ME!
 *
 * @author   Puhl
 * @version  $Revision$, $Date$
 */
//Logging
public class BelisBroker implements SearchController, PropertyChangeListener, Configurable {

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

    protected JButton btnDiscardChanges;
    protected JButton btnAcceptChanges;
    protected JButton btnSwitchInCreateMode;
    protected JButton cmdPrint = new javax.swing.JButton();
    protected JButton cmdAAPrint = new javax.swing.JButton();
    protected JButton cmdCsvExport = new javax.swing.JButton();
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
    // ToDo improve mechanismn
    /** Creates a new instance of LagisBroker. */
    protected StatusBar statusBar;
    protected ExecutorService execService = null;
    protected String currentValidationErrorMessage = null;
    private WorkbenchWidget workbenchWidget = null;
    private final CreateToolBar panCreate;
    private final FilterToolBar panFilter;
    private final CopyPasteToolbar copyPasteToolbar;
    private final ArrayList<SearchControl> searchControls = new ArrayList<SearchControl>();
    private final EntityClipboard entityClipboard;
    private LayoutManager layoutManager;
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
    private Set<BaseEntity> currentSearchResults = new TreeSet(new ReverseComparator(new EntityComparator()));
//    public static final String PROP_NEW_OBJECTS = "currentSearchResults";
    private boolean inCreateMode;
    private MapSearchControl mscPan = null;
    private SaveWaitDialog saveWaitDialog = null;
    private CancelWaitDialog cancelWaitDialog = null;
    private LockWaitDialog lockWaitDialog = null;
    private ReleaseWaitDialog releaseWaitDialog = null;
    private RetrieveWorker lastSearch = null;
    private DetailWidget detailWidget;
    private LeitungstypCustomBean lastLeitungstyp = null;
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
    private Collection<AbstractArbeitsprotokollWizard> leuchtenWizards =
        new ArrayList<AbstractArbeitsprotokollWizard>();
    private Collection<AbstractArbeitsprotokollWizard> standorteWizards =
        new ArrayList<AbstractArbeitsprotokollWizard>();
    private Collection<AbstractArbeitsprotokollWizard> mauerlascheWizards =
        new ArrayList<AbstractArbeitsprotokollWizard>();
    private Collection<AbstractArbeitsprotokollWizard> leitungWizards = new ArrayList<AbstractArbeitsprotokollWizard>();
    private Collection<AbstractArbeitsprotokollWizard> abzweigdoseWizards =
        new ArrayList<AbstractArbeitsprotokollWizard>();
    private Collection<AbstractArbeitsprotokollWizard> schaltstelleWizards =
        new ArrayList<AbstractArbeitsprotokollWizard>();
    private Collection<AbstractArbeitsprotokollWizard> allgemeineWizards =
        new ArrayList<AbstractArbeitsprotokollWizard>();

    private boolean filterNormal = true;
    private boolean filterVeranlassung = false;
    private boolean filterArbeitsauftrag = false;
    private SperreCustomBean sperre = null;

    //~ Constructors -----------------------------------------------------------

    /**
     * ToDo FIX.
     */
    private BelisBroker() {
        execService = Executors.newCachedThreadPool();

        entityClipboard = new EntityClipboard(this);

        panCreate = new CreateToolBar(this);
        panFilter = new FilterToolBar(this);
        copyPasteToolbar = new CopyPasteToolbar(this);
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
     *
     * @param   entity  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Collection<AbstractArbeitsprotokollWizard> getWizardsActionsForEntity(
            final ArbeitsprotokollCustomBean.ChildType entity) {
        if (entity == null) {
            return (Collection<AbstractArbeitsprotokollWizard>)allgemeineWizards;
        } else {
            switch (entity) {
                case ABZWEIGDOSE: {
                    return (Collection<AbstractArbeitsprotokollWizard>)abzweigdoseWizards;
                }
                case STANDORT: {
                    return (Collection<AbstractArbeitsprotokollWizard>)standorteWizards;
                }
                case LEUCHTE: {
                    return (Collection<AbstractArbeitsprotokollWizard>)leuchtenWizards;
                }
                case MAUERLASCHE: {
                    return (Collection<AbstractArbeitsprotokollWizard>)mauerlascheWizards;
                }
                case SCHALTSTELLE: {
                    return (Collection<AbstractArbeitsprotokollWizard>)schaltstelleWizards;
                }
                case LEITUNG: {
                    return (Collection<AbstractArbeitsprotokollWizard>)leitungWizards;
                }
                default: {
                    return null;
                }
            }
        }
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
                        setWidgetsEditable(isEditable);
                    }
                });
        } else {
            for (final BelisWidget curWidget : widgets) {
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
                curWidget.setWidgetEditable(isEditable);
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
            if (isInCreateMode()) {
                workbenchWidget.clearNewNode();
            } else {
                workbenchWidget.clearEditNode();
            }
            new SwingWorker<Void, Void>() {

                    @Override
                    protected Void doInBackground() throws Exception {
                        fireReleaseStarted();
                        releaseLock();
                        return null;
                    }

                    @Override
                    protected void done() {
                        try {
                            setInEditMode(false);
                            getMappingComponent().setReadOnly(true);
                        } finally {
                            fireReleaseFinished();
                        }
                    }
                }.execute();
        } else {
            setTitleBarComponentpainter(BelisBroker.EDIT_MODE_COLOR);
            editButtonsToolbar.enableSwitchToModeButtons(false);
            switchInEditMode(true);
            new SwingWorker<Void, Void>() {

                    @Override
                    protected Void doInBackground() throws Exception {
                        fireLockStarted();
                        acquireLock();
                        return null;
                    }

                    @Override
                    protected void done() {
                        try {
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
                        } finally {
                            fireLockFinished();
                        }
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
        cmdAAPrint.setEnabled(!isSwitchedInEditMode);
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
        PropertyManager.getManager().setEditable(true);

        final SearchResultsTree searchResultsTree = new SearchResultsTree() {

                @Override
                public void setResultNodes(final Node[] nodes,
                        final boolean append,
                        final PropertyChangeListener listener,
                        final boolean simpleSort,
                        final boolean sortActive) {
                    super.setResultNodes(nodes, append, listener, simpleSort, false);
                }
            };

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
            if (((component instanceof PluginMenuItem)
                            && ((PluginMenuItem)component).getId().equals(
                                "Sirius.navigator.ui.MutablePopupMenu$EditObjectMethod"))
                        || (component instanceof JSeparator)
                        || ((component instanceof JMenuItem) && (((JMenuItem)component).getActionCommand() != null)
                            && (((JMenuItem)component).getActionCommand().equals("cmdSearch")
                                || ((JMenuItem)component).getActionCommand().equals("treecommand")
                                || ((((JMenuItem)component).getAccelerator() != null)
                                    && ((JMenuItem)component).getAccelerator().equals(
                                        KeyStroke.getKeyStroke("F5")))))) {
                toRemoveComponents.add(component);
            }
        }
        for (final Component toRemoveComponent : toRemoveComponents) {
            popupMenu.remove(toRemoveComponent);
        }

        final DefaultPopupMenuListener cataloguePopupMenuListener = new DefaultPopupMenuListener(popupMenu);
        final Node[] roots = SessionManager.getProxy().getRoots("BELIS2");
        final RootTreeNode rootTreeNode = new RootTreeNode(roots);
        while (roots.length != rootTreeNode.getChildCount()) {
            Thread.sleep(100);
        }
        final MetaCatalogueTree metaCatalogueTree = new MetaCatalogueTree(
                rootTreeNode,
                PropertyManager.getManager().isEditable(),
                true,
                PropertyManager.getManager().getMaxConnections());
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

        searchResultsTree.addPropertyChangeListener("browse", new PropertyChangeListener() {

                @Override
                public void propertyChange(final PropertyChangeEvent evt) {
                    final List<Node> nodes = searchResultsTree.getResultNodes();
                    if (nodes != null) {
                        final SearchWaitDialog swd = SearchWaitDialog.getInstance();
                        swd.init(nodes.size());
                        SwingUtilities.invokeLater(new Runnable() {

                                @Override
                                public void run() {
                                    StaticSwingTools.showDialog(swd);
                                }
                            });

                        new SwingWorker<TreeSet<BaseEntity>, Void>() {

                            @Override
                            protected TreeSet<BaseEntity> doInBackground() throws Exception {
                                final Collection<BaseEntity> entities = new ArrayList<BaseEntity>();
                                int count = 0;
                                for (final Node node : nodes) {
                                    if (swd.isCanceled()) {
                                        entities.clear();
                                        break;
                                    }
                                    count++;
                                    if ((node != null) && (node instanceof MetaObjectNode)) {
                                        final MetaObjectNode moNode = (MetaObjectNode)node;
                                        final MetaObject mo;
                                        if (moNode.getObject() != null) {
                                            mo = moNode.getObject();
                                        } else {
                                            mo = CidsBroker.getInstance()
                                                        .getMetaObject(
                                                                moNode.getClassId(),
                                                                moNode.getObjectId(),
                                                                "BELIS2");
                                        }
                                        if (mo != null) {
                                            swd.setValue(count);
                                            final CidsBean bean = mo.getBean();
                                            if (bean instanceof BaseEntity) {
                                                ((BaseEntity)bean).init();
                                                entities.add((BaseEntity)bean);
                                            }
                                        }
                                    }
                                }
                                final TreeSet<BaseEntity> results = new TreeSet<BaseEntity>(
                                        new ReverseComparator(new EntityComparator()));
                                results.addAll(entities);
                                return results;
                            }

                            @Override
                            protected void done() {
                                TreeSet<BaseEntity> results = null;
                                try {
                                    results = get();
                                } catch (final Exception ex) {
                                    LOG.warn("exeption whil building search result treeset", ex);
                                }
                                setSearchResult(results);
                                SwingUtilities.invokeLater(new Runnable() {

                                        @Override
                                        public void run() {
                                            swd.setVisible(false);
                                        }
                                    });
                                enableSearch();
                                fireSearchFinished();
                            }
                        }.execute();
                    }
                }
            }); // NOI18N

        setComponentRegistry(ComponentRegistry.getRegistry());
    }

    /**
     * DOCUMENT ME!
     */
    public void lookupWidgets() {
        try {
            for (final BelisWidget widget : Lookup.getDefault().lookupAll(BelisWidget.class)) {
                if (widget.isAllowedToShow()) {
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
            }
        } catch (Throwable ex) {
            LOG.error("Error while lookup of widgets", ex);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  arbeitsauftragNode  DOCUMENT ME!
     * @param  protokoll           DOCUMENT ME!
     * @param  basic               DOCUMENT ME!
     */
    public void addNewProtokollToAuftragNode(final CustomMutableTreeTableNode arbeitsauftragNode,
            final ArbeitsprotokollCustomBean protokoll,
            final CidsBean basic) {
        final CustomTreeTableModel treeModel = BelisBroker.getInstance().getWorkbenchWidget().getTreeTableModel();

        final CustomMutableTreeTableNode newBasicNode = new CustomMutableTreeTableNode(basic, true);
        final CustomMutableTreeTableNode newProtokollNode = new CustomMutableTreeTableNode(
                protokoll,
                true);
        treeModel.insertNodeIntoAsLastChild(
            newProtokollNode,
            arbeitsauftragNode);
        treeModel.insertNodeIntoAsLastChild(
            newBasicNode,
            newProtokollNode);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  veranlassungNode  DOCUMENT ME!
     * @param  basic             DOCUMENT ME!
     */
    public void addNewBasicToVeranlassungNode(final CustomMutableTreeTableNode veranlassungNode, final CidsBean basic) {
        final CustomTreeTableModel treeModel = BelisBroker.getInstance().getWorkbenchWidget().getTreeTableModel();
        final CustomMutableTreeTableNode newNode = new CustomMutableTreeTableNode(basic, true);
        treeModel.insertNodeIntoAsLastChild(newNode, veranlassungNode);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   basic  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public ArbeitsprotokollCustomBean createProtokollFromBasic(final CidsBean basic) {
        final ArbeitsprotokollCustomBean protokoll = ArbeitsprotokollCustomBean.createNew();
        if (basic instanceof AbzweigdoseCustomBean) {
            protokoll.setFk_abzweigdose((AbzweigdoseCustomBean)basic);
        } else if (basic instanceof MauerlascheCustomBean) {
            protokoll.setFk_mauerlasche((MauerlascheCustomBean)basic);
        } else if (basic instanceof LeitungCustomBean) {
            protokoll.setFk_leitung((LeitungCustomBean)basic);
        } else if (basic instanceof SchaltstelleCustomBean) {
            protokoll.setFk_schaltstelle((SchaltstelleCustomBean)basic);
        } else if (basic instanceof TdtaLeuchtenCustomBean) {
            protokoll.setFk_leuchte((TdtaLeuchtenCustomBean)basic);
        } else if (basic instanceof TdtaStandortMastCustomBean) {
            protokoll.setFk_standort((TdtaStandortMastCustomBean)basic);
        } else if (basic instanceof GeometrieCustomBean) {
            protokoll.setFk_geometrie((GeometrieCustomBean)basic);
        }
        return protokoll;
    }

    /**
     * DOCUMENT ME!
     */
    public void lookupProtokollWizards() {
        try {
            for (final AbstractArbeitsprotokollWizard wizard
                        : Lookup.getDefault().lookupAll(AbstractArbeitsprotokollWizard.class)) {
                try {
                    if (wizard.getEntityClass() == null) {
                        allgemeineWizards.add(wizard);
                    } else if (wizard.getEntityClass().equals(ArbeitsprotokollCustomBean.ChildType.LEUCHTE)) {
                        leuchtenWizards.add(wizard);
                    } else if (wizard.getEntityClass().equals(ArbeitsprotokollCustomBean.ChildType.STANDORT)) {
                        standorteWizards.add(wizard);
                    } else if (wizard.getEntityClass().equals(ArbeitsprotokollCustomBean.ChildType.ABZWEIGDOSE)) {
                        abzweigdoseWizards.add(wizard);
                    } else if (wizard.getEntityClass().equals(ArbeitsprotokollCustomBean.ChildType.MAUERLASCHE)) {
                        mauerlascheWizards.add(wizard);
                    } else if (wizard.getEntityClass().equals(ArbeitsprotokollCustomBean.ChildType.SCHALTSTELLE)) {
                        schaltstelleWizards.add(wizard);
                    } else if (wizard.getEntityClass().equals(ArbeitsprotokollCustomBean.ChildType.LEITUNG)) {
                        leitungWizards.add(wizard);
                    }
                } catch (Exception ex) {
                    LOG.error("Error while initializing wizard", ex);
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
        metaSearchComponentFactory.setCustomGeoSearch(new BelisTopicSearchStatement());
        setMetaSearchComponentFactory(metaSearchComponentFactory);

        CismapBroker.getInstance().addStatusListener(new StatusListener() {

                @Override
                public void statusValueChanged(final StatusEvent e) {
                    if (e.getName().equals(StatusEvent.MAPPING_MODE)) {
                        if (e.getValue().equals(MappingComponent.CREATE_SEARCH_POLYGON)) {
                            metaSearchComponentFactory.getCmdPluginSearch().setSelected(true);
                            getMapWidget().setCustomMapMode();
                        } else {
                            metaSearchComponentFactory.getCmdPluginSearch().setSelected(false);
                        }
                    }
                }
            });
    }

    @Override
    public void masterConfigure(final Element parent) {
        initToolbar();
        try {
            saveWaitDialog = new SaveWaitDialog(StaticSwingTools.getParentFrame(getParentComponent()), true);
            cancelWaitDialog = new CancelWaitDialog(StaticSwingTools.getParentFrame(getParentComponent()), true);
            lockWaitDialog = new LockWaitDialog(StaticSwingTools.getParentFrame(getParentComponent()), true);
            releaseWaitDialog = new ReleaseWaitDialog(StaticSwingTools.getParentFrame(getParentComponent()), true);
        } catch (final Exception ex) {
            LOG.warn("Error while creating search and wait dialog", ex);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("masterConfigure: " + BelisBroker.class.getName());
        }

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
            if (LOG.isDebugEnabled()) {
                LOG.debug("masterConfigure: " + BelisBroker.class.getName());
            }
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

        final int defaultFontSize = 16;
        LOG.warn("Error setting fontsize for map objects. Setting font to default: " + defaultFontSize);
        IconUtils.setFont(new Font("Courier", Font.PLAIN, defaultFontSize));

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
     * DOCUMENT ME!
     *
     * @param  layoutManager  DOCUMENT ME!
     */
    public void setLayoutManager(final LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
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
     * @param  btnSwitchInCreateMode  DOCUMENT ME!
     */
    public void setBtnSwitchInCreateMode(final JButton btnSwitchInCreateMode) {
        this.btnSwitchInCreateMode = btnSwitchInCreateMode;
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
            final Dimension size = new Dimension(30, 23);
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

            cmdAAPrint.setIcon(new javax.swing.ImageIcon(
                    getClass().getResource("/de/cismet/belis/resource/icon/22/printAA.png"))); // NOI18N

            cmdAAPrint.setToolTipText("Arbeitsauftrags-Report");
            cmdAAPrint.setBorderPainted(false);
            cmdAAPrint.setFocusable(false);
            cmdAAPrint.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            cmdAAPrint.setPreferredSize(size);
            cmdAAPrint.setMinimumSize(size);
            cmdAAPrint.setMaximumSize(size);
            cmdAAPrint.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            cmdAAPrint.addActionListener(new java.awt.event.ActionListener() {

                    @Override
                    public void actionPerformed(final java.awt.event.ActionEvent evt) {
                        printReport();
                    }
                });

            cmdCsvExport.setIcon(new javax.swing.ImageIcon(
                    getClass().getResource("/de/cismet/belis/resource/icon/16/table-import.png"))); // NOI18N

            cmdCsvExport.setToolTipText("Nach CSV Exportieren");
            cmdCsvExport.setBorderPainted(false);
            cmdCsvExport.setFocusable(false);
            cmdCsvExport.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            cmdCsvExport.setPreferredSize(size);
            cmdCsvExport.setMinimumSize(size);
            cmdCsvExport.setMaximumSize(size);
            cmdCsvExport.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            cmdCsvExport.addActionListener(new java.awt.event.ActionListener() {

                    @Override
                    public void actionPerformed(final java.awt.event.ActionEvent evt) {
                        exportCsv();
                    }
                });

            final JPanel printPanel = new JPanel();
            printPanel.setLayout(new java.awt.GridBagLayout());
            printPanel.setMaximumSize(new Dimension(90, 23));
            printPanel.setMinimumSize(new Dimension(90, 23));
            printPanel.setPreferredSize(new Dimension(90, 23));
            final java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.anchor = GridBagConstraints.WEST;
            printPanel.add(cmdPrint, gridBagConstraints);
            gridBagConstraints.gridx = 1;
            printPanel.add(cmdAAPrint, gridBagConstraints);
            gridBagConstraints.gridx = 2;
            printPanel.add(cmdCsvExport, gridBagConstraints);
            toolbar.add(printPanel);

            addSeparatorToToolbar();
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Exception while initializing toolbar");
            }
            LOG.error("Exception while initializing toolbar.", ex);
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void exportCsv() {
        final CsvExportBackend backend = CsvExportBackend.getInstance();

        final Collection<TreePath> paths = getWorkbenchWidget().getSelectedTreeNodes();
        if (paths == null) {
            JOptionPane.showMessageDialog(StaticSwingTools.getParentFrame(getParentComponent()),
                "Es muss mindestens ein Objekt im Arbeitsbereich selektiert sein.",
                "keine Objekte selektiert",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            // hauptobjekte auch aus protokolle, veranlassungen, arbeitsaufträgen heraus
            final Collection<CidsBean> mainBeans = new ArrayList<CidsBean>();
            for (final TreePath path : paths) {
                final CustomMutableTreeTableNode node = (CustomMutableTreeTableNode)path.getLastPathComponent();
                if (node != null) {
                    final Object object = node.getUserObject();
                    if (object instanceof ArbeitsauftragCustomBean) {
                        final ArbeitsauftragCustomBean auftrag = (ArbeitsauftragCustomBean)object;
                        for (final ArbeitsprotokollCustomBean protokoll : auftrag.getAr_protokolle()) {
                            final CidsBean childBean = protokoll.getChildEntity();
                            if (!mainBeans.contains(childBean)) {
                                mainBeans.add(childBean);
                            }
                        }
                    } else if (object instanceof ArbeitsprotokollCustomBean) {
                        final ArbeitsprotokollCustomBean protokoll = (ArbeitsprotokollCustomBean)object;
                        final CidsBean childBean = protokoll.getChildEntity();
                        if (!mainBeans.contains(childBean)) {
                            mainBeans.add(childBean);
                        }
                    } else if (object instanceof VeranlassungCustomBean) {
                        final VeranlassungCustomBean veranlassung = (VeranlassungCustomBean)object;
                        final Collection<CidsBean> allChildBeans = new ArrayList<CidsBean>();
                        allChildBeans.addAll(veranlassung.getAr_abzweigdosen());
                        allChildBeans.addAll(veranlassung.getAr_leitungen());
                        allChildBeans.addAll(veranlassung.getAr_leuchten());
                        allChildBeans.addAll(veranlassung.getAr_mauerlaschen());
                        allChildBeans.addAll(veranlassung.getAr_schaltstellen());
                        allChildBeans.addAll(veranlassung.getAr_standorte());
                        for (final CidsBean childBean : allChildBeans) {
                            if (!mainBeans.contains(childBean)) {
                                mainBeans.add(childBean);
                            }
                        }
                    } else if (object instanceof CidsBean) {
                        if (!mainBeans.contains((CidsBean)object)) {
                            mainBeans.add((CidsBean)object);
                        }
                    }
                }
            }

            // Sonderbehandlung: leuchten erzeugen auch standort und umgekehrt
            final Collection<CidsBean> beans = new ArrayList<CidsBean>();
            for (final CidsBean mainBean : mainBeans) {
                if (mainBean instanceof TdtaStandortMastCustomBean) {
                    final TdtaStandortMastCustomBean standort = (TdtaStandortMastCustomBean)mainBean;
                    if (!beans.contains(standort)) {
                        beans.add(standort);
                    }

                    if (standort.getLeuchten() != null) {
                        for (final CidsBean cidsBean : standort.getLeuchten()) {
                            if (!beans.contains(cidsBean)) {
                                beans.add(cidsBean);
                            }
                        }
                    }
                } else if (mainBean instanceof TdtaLeuchtenCustomBean) {
                    final TdtaLeuchtenCustomBean leuchte = (TdtaLeuchtenCustomBean)mainBean;
                    if (!beans.contains(leuchte)) {
                        beans.add(leuchte);
                    }

                    final TdtaStandortMastCustomBean standort = leuchte.getFk_standort();
                    if (standort != null) {
                        if (!beans.contains(standort)) {
                            beans.add(standort);
                        }
                    }
                } else {
                    if (!beans.contains(mainBean)) {
                        beans.add(mainBean);
                    }
                }
            }

            final Map<MetaClass, String> csvStringMap = backend.toCsvStrings(beans);
            if (!csvStringMap.isEmpty()) {
                if (DownloadManagerDialog.showAskingForUserTitle(getRootWindow())) {
                    for (final MetaClass metaClass : csvStringMap.keySet()) {
                        final String title = metaClass.getName();
                        final String body = csvStringMap.get(metaClass);

                        DownloadManager.instance()
                                .add(new ByteArrayDownload(
                                        body.getBytes(),
                                        title,
                                        DownloadManagerDialog.getJobname(),
                                        title,
                                        ".csv"));
                    }
                    final DownloadManagerDialog downloadManagerDialog = DownloadManagerDialog.instance(getRootWindow());
                    downloadManagerDialog.pack();
                    StaticSwingTools.showDialog(downloadManagerDialog);
                }
            }
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
    protected void fireLockFinished() {
        EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    lockWaitDialog.setVisible(false);
                }
            });
    }

    /**
     * DOCUMENT ME!
     */
    protected void fireLockStarted() {
        EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    lockWaitDialog.setLocationRelativeTo(StaticSwingTools.getParentFrame(getParentComponent()));
                    lockWaitDialog.setVisible(true);
                }
            });
    }

    /**
     * DOCUMENT ME!
     */
    protected void fireReleaseFinished() {
        EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    releaseWaitDialog.setVisible(false);
                }
            });
    }

    /**
     * DOCUMENT ME!
     */
    protected void fireReleaseStarted() {
        EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    releaseWaitDialog.setLocationRelativeTo(StaticSwingTools.getParentFrame(getParentComponent()));
                    releaseWaitDialog.setVisible(true);
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
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public Runnable saveWorkbench() throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("save");
            LOG.debug(workbenchWidget.getObjectsToPersist().size() + " Objects to save");
            LOG.debug(workbenchWidget.getObjectsToDelete().size() + " Objects to delete");
        }

        final Collection<BaseEntity> persistedObjects = CidsBroker.getInstance()
                    .saveObjects(workbenchWidget.getObjectsToPersist());
        getCurrentSearchResults().addAll(persistedObjects);
        if (!isInCreateMode()) {
            final Collection<BaseEntity> objectsToDelete = workbenchWidget.getObjectsToDelete();
            CidsBroker.getInstance().deleteEntities(objectsToDelete);
            getCurrentSearchResults().removeAll(objectsToDelete);
        }
        for (final BaseEntity entity : getCurrentSearchResults()) {
            entity.storeBackup();
        }
        workbenchWidget.getObjectsToDelete().clear();
        if (isInCreateMode()) {
            workbenchWidget.clearNewNode();
        } else {
            workbenchWidget.clearEditNode();
        }

        final Runnable runnable = new Runnable() {

                @Override
                public void run() {
                    workbenchWidget.refreshAll();
                    refreshMap();
                }
            };

        return runnable;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    protected Runnable cancelWorkbench() throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("cancel");
        }

        if (!isInCreateMode()) {
            getCurrentSearchResults().addAll(workbenchWidget.getObjectsToDelete());
            for (final BaseEntity entity : workbenchWidget.getObjectsToPersist()) {
                entity.loadBackup();
            }
            getCurrentSearchResults().addAll(workbenchWidget.getObjectsToPersist());
        }
        workbenchWidget.getObjectsToDelete().clear();
        if (isInCreateMode()) {
            workbenchWidget.clearNewNode();
        } else {
            workbenchWidget.clearEditNode();
        }

        final Runnable cancelEDTRun = new Runnable() {

                @Override
                public void run() {
                    workbenchWidget.refreshAll();
                    refreshMap();
                }
            };
        return cancelEDTRun;
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
     *
     * @param  ex  DOCUMENT ME!
     */
    public void showSaveErrorDialog(final Exception ex) {
        final ErrorInfo ei = new ErrorInfo(
                "Fehler beim speichern...",
                "<html><table width=\"250\" border=\"0\"><tr><td>Fehler beim speichern der Objekte. Ihre Änderungen konnten nicht gespeichert werden.</td></tr></table></html>",
                null,
                null,
                ex,
                Level.SEVERE,
                null);
        JXErrorPane.showDialog(getParentComponent(), ei);
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
        final SimpleMemoryMonitoringToolbarWidget memTB = new SimpleMemoryMonitoringToolbarWidget();
        if (memTB.isVisible()) {
            getToolbar().add(memTB);
            getToolbar().add(createToolBarSeperator());
        }
        addCreateToolBar();
        addCopyPasteToolBar();
        addFilterToolbar();
        addLocationSearch();
        getToolbar().add(metaSearchComponentFactory.getCmdPluginSearch());
        addSeparatorToToolbar();
        addMapSearchControl();
        btnAcceptChanges.setIcon(BelisIcons.icoAccept22);
        btnDiscardChanges.setIcon(BelisIcons.icoCancel22);
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
        this.currentSearchResults = currentSearchResults;
        if ((this.currentSearchResults != null) && (currentSearchResults != null)
                    && this.currentSearchResults.equals(currentSearchResults)) {
            LOG.warn("Sets are equals no propertyChange doing manually refresh --> ToDo fix me");
            refreshWidgets(currentSearchResults);
        }
        // ToDo why is it not working if I use null there is no equals check ???
        propertyChangeSupport.firePropertyChange(PROP_CURRENT_SEARCH_RESULTS, old, currentSearchResults);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  set  DOCUMENT ME!
     */
    public void setSearchResult(final Set<BaseEntity> set) {
        new SwingWorker<Void, Void>() {

                @Override
                protected Void doInBackground() throws Exception {
                    if (set != null) {
                        for (final BaseEntity entity : set) {
                            entity.storeBackup();
                        }
                    }
                    return null;
                }

                @Override
                protected void done() {
                    if (set != null) {
                        setCurrentSearchResults(set);
                        refreshMap();
                        getMappingComponent().zoomToFullFeatureCollectionBounds();
                    } else {
                        clearMap();
                    }
                }
            }.execute();
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
     * @param   veranlassungsnummer  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Collection searchForArbeitsprotokolleOfVeralassung(final String veranlassungsnummer) {
        final ArbeitsauftragSearchStatement searchStatement = new ArbeitsauftragSearchStatement();
        searchStatement.setVeranlassungsNummer(veranlassungsnummer);
        searchStatement.setActiveObjectsOnly(false);
        searchStatement.setWorkedoffObjectsOnly(false);
        try {
            final Collection res = SessionManager.getProxy()
                        .customServerSearch(SessionManager.getSession().getUser(), searchStatement);
            return res;
        } catch (final Exception ex) {
            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  bb  DOCUMENT ME!
     */
    public void search(final BoundingBox bb) {
        try {
            disableSearch();
            final BelisSearchStatement belisSearchStatement = new BelisSearchStatement(
                    isFilterNormal(),
                    false,
                    isFilterNormal(),
                    isFilterNormal(),
                    isFilterNormal(),
                    isFilterNormal(),
                    isFilterVeranlassung(),
                    isFilterArbeitsauftrag());
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
                    featuresToAdd.addAll(addEntityRecursiveToMap(veranlassungCustomBean.getAr_geometrien()));
                    featuresToAdd.addAll(addEntityRecursiveToMap(veranlassungCustomBean.getAr_abzweigdosen()));
                    featuresToAdd.addAll(addEntityRecursiveToMap(veranlassungCustomBean.getAr_leitungen()));
                    featuresToAdd.addAll(addEntityRecursiveToMap(veranlassungCustomBean.getAr_leuchten()));
                    featuresToAdd.addAll(addEntityRecursiveToMap(veranlassungCustomBean.getAr_mauerlaschen()));
                    featuresToAdd.addAll(addEntityRecursiveToMap(veranlassungCustomBean.getAr_schaltstellen()));
                    featuresToAdd.addAll(addEntityRecursiveToMap(veranlassungCustomBean.getAr_standorte()));
                } else if ((currentResult instanceof ArbeitsauftragCustomBean)) {
                    final ArbeitsauftragCustomBean arbeitsauftragCustomBean = (ArbeitsauftragCustomBean)currentResult;
                    for (final ArbeitsprotokollCustomBean protokoll : arbeitsauftragCustomBean.getAr_protokolle()) {
                        if (protokoll.getFk_abzweigdose() != null) {
                            featuresToAdd.add(protokoll.getFk_abzweigdose());
                        }
                        if (protokoll.getFk_leitung() != null) {
                            featuresToAdd.add(protokoll.getFk_leitung());
                        }
                        if (protokoll.getFk_leuchte() != null) {
                            featuresToAdd.add(protokoll.getFk_leuchte());
                        }
                        if (protokoll.getFk_mauerlasche() != null) {
                            featuresToAdd.add(protokoll.getFk_mauerlasche());
                        }
                        if (protokoll.getFk_schaltstelle() != null) {
                            featuresToAdd.add(protokoll.getFk_schaltstelle());
                        }
                        if (protokoll.getFk_standort() != null) {
                            featuresToAdd.add(protokoll.getFk_standort());
                        }
                        if (protokoll.getFk_geometrie() != null) {
                            featuresToAdd.add(protokoll.getFk_geometrie());
                        }
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
                        final Collection<WorkbenchEntity> currentEntities = new ArrayList<WorkbenchEntity>();
                        WorkbenchEntity parentEntity = null;

                        boolean first = true;
                        boolean allSameParent = true;
                        boolean allSameType = true;
                        CustomMutableTreeTableNode allSameParentNode = null;
                        Object allSameMainNode = null;
                        Class allSameTypeClass = null;
                        if (((WorkbenchWidget)evt.getSource()).getSelectedTreeNodes() != null) {
                            for (final TreePath treePath : ((WorkbenchWidget)evt.getSource()).getSelectedTreeNodes()) {
                                final Object currentMainNode = treePath.getPathComponent(1);
                                final CustomMutableTreeTableNode currentParentNode = (CustomMutableTreeTableNode)
                                    treePath.getParentPath().getLastPathComponent();
                                final Object currentUserObject =
                                    ((CustomMutableTreeTableNode)treePath.getLastPathComponent()).getUserObject();
                                final Class currentClass = currentUserObject.getClass();
                                if (first) {
                                    allSameParentNode = currentParentNode;
                                    allSameMainNode = currentMainNode;
                                    allSameTypeClass = currentClass;
                                    first = false;
                                }
                                if (currentParentNode != allSameParentNode) {
                                    allSameParent = false;
                                }
                                if ((currentClass != allSameTypeClass)) {
                                    allSameType = false;
                                }
                                if (currentUserObject instanceof WorkbenchEntity) {
                                    currentEntities.add((WorkbenchEntity)currentUserObject);
                                }
                            }
                        }

                        if (!allSameType || (allSameTypeClass == null)
                                    || ((currentEntities.size() > 1)
                                        && !allSameTypeClass.equals(ArbeitsprotokollCustomBean.class))) {
                            currentEntities.clear();
                        }
                        if (!currentEntities.isEmpty() && allSameParent && (allSameParentNode != null)
                                    && (allSameParentNode.getUserObject() instanceof WorkbenchEntity)) {
                            parentEntity = (WorkbenchEntity)allSameParentNode.getUserObject();
                        }

                        final boolean isFromNewNode = (allSameMainNode != null)
                                    && allSameMainNode.equals(((WorkbenchWidget)evt.getSource()).getNewObjectsNode());
                        final boolean isFromEditNode = (allSameMainNode != null)
                                    && allSameMainNode.equals(((WorkbenchWidget)evt.getSource()).getEditObjectsNode());

                        try {
                            detailWidget.setCurrentEntities(
                                currentEntities,
                                parentEntity,
                                isFromNewNode
                                        || isFromEditNode);
                        } catch (VetoException ex) {
                            return;
                        }
                        copyPasteToolbar.clipboardChanged();

                        final WorkbenchEntity selectedSingleEntity;
                        if (currentEntities.size() == 1) {
                            final Object selectedObject = currentEntities.iterator().next();
                            if ((selectedObject != null) && (selectedObject instanceof WorkbenchEntity)) {
                                selectedSingleEntity = (WorkbenchEntity)selectedObject;
                            } else {
                                selectedSingleEntity = null;
                            }
                        } else {
                            selectedSingleEntity = null;
                        }

                        if (isInEditMode()) {
                            if (isFromNewNode || isFromEditNode) {
                                panCreate.setSelectedEntity(selectedSingleEntity);
                            } else {
                                panCreate.setSelectedEntity(null);
                            }
                        } else {
                            panCreate.setSelectedEntity(null);
                        }
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
                releaseLock();
                sperre = CidsBroker.getInstance().lockEntities(currentSearchResults, getAccountName());
            } catch (ActionNotSuccessfulException ex) {
                LOG.error("Error while creating lock:", ex);
                isPendingForCreateMode.set(false);
                throw new Exception(
                    "Sperren konnten nicht angelegt werden, wechseln in Editmodus nicht möglich");
            } catch (LockAlreadyExistsException ex) {
                LOG.info("Some of the objects are already locked", ex);
                isPendingForCreateMode.set(false);
                final Collection<SperreCustomBean> alreadyLocked = ex.getAlreadyExisingLocks();
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Count of already locked objects: " + alreadyLocked.size());
                }
                fireLockFinished();
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
            try {
                if (sperre != null) {
                    CidsBroker.getInstance().unlock(sperre);
                    sperre = null;
                }
            } catch (ActionNotSuccessfulException ex) {
                LOG.error("Error while unlocking locked objects:", ex);
                throw new Exception("Angelegte sperren konnten nicht gelöst werden.");
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
     * @param  locks  DOCUMENT ME!
     */
    private void showObjectsLockedDialog(final Collection<SperreCustomBean> locks) {
        final JDialog dialog = new JDialog(StaticSwingTools.getParentFrame(getParentComponent()),
                "Gesperrte Objekte...",
                true);
        if (lockPanel == null) {
            lockPanel = new AlreadyLockedObjectsPanel(locks);
        } else {
            lockPanel.setLocks(locks);
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
        final TdtaStandortMastCustomBean newStandort = TdtaStandortMastCustomBean.createNew();
        newStandort.setVerrechnungseinheit(true);

        if (BelisBroker.getDefaultUnterhaltMast() != null) {
            newStandort.setUnterhaltspflichtMast(BelisBroker.getDefaultUnterhaltMast());
        }
        newStandort.addPropertyChangeListener(workbenchWidget);
        workbenchWidget.addNewEntity(newStandort);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  relatedObject  DOCUMENT ME!
     */
    public void addNewLeuchte(final Object relatedObject) {
        workbenchWidget.addNewLeuchte(relatedObject);
    }

    /**
     * DOCUMENT ME!
     */
    public void addNewLeuchte() {
        workbenchWidget.addNewLeuchte();
    }

    /**
     * DOCUMENT ME!
     */
    public void addNewMauerlasche() {
        final MauerlascheCustomBean newMauerlasche = MauerlascheCustomBean.createNew();
        newMauerlasche.setStrassenschluessel(getLastMauerlascheStrassenschluessel());
        newMauerlasche.addPropertyChangeListener(this);
        newMauerlasche.addPropertyChangeListener(workbenchWidget);
        workbenchWidget.addNewEntity(newMauerlasche);
    }

    /**
     * DOCUMENT ME!
     */
    public void addNewSchaltstelle() {
        final SchaltstelleCustomBean newSchaltstelle = SchaltstelleCustomBean.createNew();
        newSchaltstelle.addPropertyChangeListener(workbenchWidget);
        workbenchWidget.addNewEntity(newSchaltstelle);
    }

    /**
     * DOCUMENT ME!
     */
    public void addNewLeitung() {
        final LeitungCustomBean newLeitung = LeitungCustomBean.createNew();
        newLeitung.setLeitungstyp(getLastLeitungstyp());
        newLeitung.addPropertyChangeListener(this);
        newLeitung.addPropertyChangeListener(workbenchWidget);
        workbenchWidget.addNewEntity(newLeitung);
    }

    /**
     * DOCUMENT ME!
     */
    public void addNewAbzweigdose() {
        final AbzweigdoseCustomBean newAbzweigdose = AbzweigdoseCustomBean.createNew();
        workbenchWidget.addNewEntity(newAbzweigdose);
    }

    /**
     * DOCUMENT ME!
     */
    public void addNewVeranlassung() {
        final VeranlassungCustomBean newVeranlassung = VeranlassungCustomBean.createNew();
        workbenchWidget.addNewEntity(newVeranlassung);
    }

    /**
     * DOCUMENT ME!
     */
    public void addNewGeometrie() {
        workbenchWidget.addNewGeometrie();
    }

    /**
     * DOCUMENT ME!
     */
    public void addNewArbeitsauftrag() {
        final ArbeitsauftragCustomBean newArbeitsauftrag = ArbeitsauftragCustomBean.createNew();
        workbenchWidget.addNewEntity(newArbeitsauftrag);
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
        editButtonsToolbar.enableSwitchToModeButtons(!isInCreateMode() && !isInEditMode());
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
        editButtonsToolbar.enableSwitchToModeButtons(false);

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

    /**
     * DOCUMENT ME!
     *
     * @param   keyTableClassname  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static DefaultBindableReferenceCombo createKeyTableComboBox(final String keyTableClassname) {
        final DefaultBindableReferenceCombo comboBox = new ScrollableComboBox(CidsBroker.getInstance()
                        .getBelisMetaClass(keyTableClassname)) {
            };
        comboBox.setNullable(true);
        comboBox.setNullValueRepresentation("<html><i>Wert auswählen...</i></html>");
        addComboBoxToKeyTableValuesListener(comboBox, keyTableClassname);
        return comboBox;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static JComboBox createStrassenschluesselNummerComboBox() {
        final DefaultBindableReferenceCombo comboBox = new DefaultBindableReferenceCombo(CidsBroker.getInstance()
                        .getBelisMetaClass(TkeyStrassenschluesselCustomBean.TABLE),
                "pk");
        comboBox.setNullable(true);
        comboBox.setNullValueRepresentation("<html><i>Wert auswählen...</i></html>");
        addComboBoxToKeyTableValuesListener(comboBox, TkeyStrassenschluesselCustomBean.TABLE);
        comboBox.setRenderer(new DefaultListCellRenderer() {

                @Override
                public Component getListCellRendererComponent(final JList list,
                        final Object value,
                        final int index,
                        final boolean isSelected,
                        final boolean cellHasFocus) {
                    final Component ret = super.getListCellRendererComponent(
                            list,
                            value,
                            index,
                            isSelected,
                            cellHasFocus);
                    if ((value == null) && (ret instanceof JLabel)) {
                        ((JLabel)ret).setText(comboBox.getNullValueRepresentation());
                    } else if ((value instanceof TkeyStrassenschluesselCustomBean) && (ret instanceof JLabel)) {
                        ((JLabel)ret).setText(((TkeyStrassenschluesselCustomBean)value).getPk());
                    }
                    return ret;
                }
            });
        return comboBox;
    }

    /**
     * DOCUMENT ME!
     */
    public void printReport() {
        final ArrayList<ArbeitsauftragCustomBean> beans = new ArrayList<ArbeitsauftragCustomBean>();
        final Collection<TreePath> paths = getWorkbenchWidget().getSelectedTreeNodes();
        if (paths != null) {
            for (final TreePath path : paths) {
                final CustomMutableTreeTableNode node = (CustomMutableTreeTableNode)path.getLastPathComponent();
                if (node != null) {
                    final Object object = node.getUserObject();
                    if (object instanceof ArbeitsauftragCustomBean) {
                        beans.add((ArbeitsauftragCustomBean)object);
                    }
                }
            }
        }

        if (beans.isEmpty()) {
            JOptionPane.showMessageDialog(StaticSwingTools.getParentFrame(getParentComponent()),
                "Es muss mindestens ein Arbeitsauftrag im Arbeitsbereich selektiert sein.",
                "kein Arbeitsauftrag selektiert",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
//            new SwingWorker<ArbeitsauftraegeReportDownload, Void>() {
//
//                    final ReportSwingWorkerDialog dialog = new ReportSwingWorkerDialog(StaticSwingTools.getParentFrame(
//                                getParentComponent()),
//                            true);
//
//                    @Override
//                    protected ArbeitsauftraegeReportDownload doInBackground() throws Exception {
//                        SwingUtilities.invokeLater(new Runnable() {
//
//                                @Override
//                                public void run() {
//                                    StaticSwingTools.showDialog(dialog);
//                                }
//                            });
//
//                        try {
            final ArbeitsauftraegeReportDownload reportDownload = new ArbeitsauftraegeReportDownload(beans);
//                            return reportDownload;
//                        } catch (Exception ex) {
//                            LOG.error("error while creating ArbeitsauftragsReport", ex);
//                            return null;
//                        }
//                    }
//
//                    @Override
//                    protected void done() {
//                        try {
//                            final ArbeitsauftraegeReportDownload download = get();
            final DownloadManagerDialog downloadManagerDialog = DownloadManagerDialog.instance(getRootWindow());
            if (DownloadManagerDialog.showAskingForUserTitle(getRootWindow())) {
                DownloadManager.instance().add(reportDownload);

                downloadManagerDialog.pack();
                StaticSwingTools.showDialog(downloadManagerDialog);
            }
//                        } catch (final Exception ex) {
//                            if (LOG.isDebugEnabled()) {
//                                LOG.debug("exeption while downloading Report", ex);
//                            }
//                            JOptionPane.showMessageDialog(StaticSwingTools.getParentFrame(getParentComponent()),
//                                "Beim Generieren des Arbeitsauftrag-Reports ist ein Fehler aufgetreten.",
//                                "Fehler beim Generieren des Reports",
//                                JOptionPane.ERROR_MESSAGE);
//                        } finally {
//                            dialog.setVisible(false);
//                        }
//                    }
//                }.execute();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  comboBox           DOCUMENT ME!
     * @param  keyTableClassname  DOCUMENT ME!
     */
    private static void addComboBoxToKeyTableValuesListener(final DefaultBindableReferenceCombo comboBox,
            final String keyTableClassname) {
        CidsBroker.getInstance().addListenerForKeyTableChange(keyTableClassname, new KeyTableListener() {

                @Override
                public void keyTableChanged() {
                    try {
                        comboBox.reload(true);
                    } catch (final Exception ex) {
                        LOG.warn("exception while comboBox.reload(true)", ex);
                    }
                }
            });
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
                return saveWorkbench();
            } else if (mode == CANCEL_MODE) {
                return cancelWorkbench();
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
                editButtonsToolbar.enableSwitchToModeButtons(true);
                btnAcceptChanges.setEnabled(false);
                btnDiscardChanges.setEnabled(false);
                setTitleBarComponentpainter(DEFAULT_MODE_COLOR);
                if (mode == SAVE_MODE) {
                    fireSaveFinished();
                } else if (mode == CANCEL_MODE) {
                    fireCancelFinished();
                }
            } catch (final Exception ex) {
                LOG.error("Failure during saving/refresh results", ex);
                if (mode == SAVE_MODE) {
                    showSaveErrorDialog(ex);
                } else if (mode == CANCEL_MODE) {
                    // cancelFailed();
                }
            } finally {
                if (mode == SAVE_MODE) {
                    fireSaveFinished();
                } else if (mode == CANCEL_MODE) {
                    fireCancelFinished();
                }
            }
        }
    }
}
