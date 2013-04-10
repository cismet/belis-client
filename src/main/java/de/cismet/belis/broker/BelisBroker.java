/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.belis.broker;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.plugin.context.PluginContext;

import com.vividsolutions.jts.geom.Geometry;

import net.infonode.docking.RootWindow;
import net.infonode.gui.componentpainter.GradientComponentPainter;

import org.apache.commons.collections.comparators.ReverseComparator;
import org.apache.log4j.PropertyConfigurator;

import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.ComponentAdapter;
import org.jdesktop.swingx.decorator.CompoundHighlighter;
import org.jdesktop.swingx.decorator.HighlightPredicate;
import org.jdesktop.swingx.decorator.Highlighter;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.treetable.AbstractMutableTreeTableNode;

import org.jdom.Element;

import org.jfree.util.Log;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagLayout;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;
import javax.swing.tree.TreePath;

import de.cismet.belis.gui.search.AddressSearchControl;
import de.cismet.belis.gui.search.LocationSearchControl;
import de.cismet.belis.gui.search.MapSearchControl;
import de.cismet.belis.gui.search.SearchControl;
import de.cismet.belis.gui.search.SearchController;
import de.cismet.belis.gui.widget.DetailWidget;
import de.cismet.belis.gui.widget.WorkbenchWidget;

import de.cismet.belis.panels.AlreadyLockedObjectsPanel;
import de.cismet.belis.panels.CancelWaitDialog;
import de.cismet.belis.panels.CreateToolBar;
import de.cismet.belis.panels.SaveErrorDialogPanel;
import de.cismet.belis.panels.SaveWaitDialog;
import de.cismet.belis.panels.SearchWaitDialog;

import de.cismet.belis.todo.RetrieveWorker;

import de.cismet.belis.util.BelisIcons;

import de.cismet.belisEE.exception.ActionNotSuccessfulException;
import de.cismet.belisEE.exception.LockAlreadyExistsException;

import de.cismet.belisEE.util.EntityComparator;
import de.cismet.belisEE.util.LeuchteComparator;

import de.cismet.cids.custom.beans.belis.LeitungCustomBean;
import de.cismet.cids.custom.beans.belis.LeitungstypCustomBean;
import de.cismet.cids.custom.beans.belis.MauerlascheCustomBean;
import de.cismet.cids.custom.beans.belis.SperreCustomBean;
import de.cismet.cids.custom.beans.belis.TdtaLeuchteCustomBean;
import de.cismet.cids.custom.beans.belis.TdtaStandortMastCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyDoppelkommandoCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyKennzifferCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyStrassenschluesselCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyUnterhLeuchteCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyUnterhMastCustomBean;

import de.cismet.cismap.commons.BoundingBox;
import de.cismet.cismap.commons.features.DefaultFeatureCollection;
import de.cismet.cismap.commons.features.Feature;
import de.cismet.cismap.commons.features.FeatureCollection;
import de.cismet.cismap.commons.features.StyledFeature;
import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.gui.statusbar.StatusBar;
import de.cismet.cismap.commons.tools.IconUtils;

import de.cismet.commons.architecture.exception.LockingNotSuccessfulException;
import de.cismet.commons.architecture.geometrySlot.GeometrySlot;
import de.cismet.commons.architecture.geometrySlot.GeometrySlotInformation;
import de.cismet.commons.architecture.geometrySlot.GeometrySlotProvider;
import de.cismet.commons.architecture.interfaces.Clearable;
import de.cismet.commons.architecture.interfaces.Editable;
import de.cismet.commons.architecture.interfaces.FeatureSelectionChangedListener;
import de.cismet.commons.architecture.interfaces.ObjectChangeListener;
import de.cismet.commons.architecture.interfaces.Refreshable;
import de.cismet.commons.architecture.interfaces.Widget;
import de.cismet.commons.architecture.plugin.AbstractPlugin;
import de.cismet.commons.architecture.validation.Validatable;

import de.cismet.commons.server.entity.GeoBaseEntity;

import de.cismet.commons2.architecture.layout.LayoutManager;
import de.cismet.commons2.architecture.widget.MapWidget;

import de.cismet.tools.CurrentStackTrace;

import de.cismet.tools.configuration.Configurable;
import de.cismet.tools.configuration.ConfigurationManager;
import de.cismet.tools.configuration.NoWriteError;

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
    // ToDo check
    public static final Color gray = Color.LIGHT_GRAY;
    // JXTable
    public static final int alphaValue = 255;
    // TODO Perhaps a bit (blasser) brighter public static Color ODD_ROW_DEFAULT_COLOR = new
    // Color(blue.getRed()+119,blue.getGreen()+88,blue.getBlue()+33,alphaValue);
    public static Color ODD_ROW_DEFAULT_COLOR = new Color(blue.getRed() + 113,
            blue.getGreen()
                    + 79,
            blue.getBlue()
                    + 14,
            alphaValue);
    // public static final Color ODD_ROW_DEFAULT_COLOR = new Color(,,,alphaValue); public static final Color
    // ODD_ROW_DEFAULT_COLOR = new Color(blue.getRed()+119,blue.getGreen()+82,blue.getBlue()+34,alphaValue); public
    // static Color ODD_ROW_EDIT_COLOR = new Color(red.getRed()+36,red.getGreen()+146,red.getBlue()+152,alphaValue);
    public static Color ODD_ROW_EDIT_COLOR = new Color(red.getRed() + 25,
            red.getGreen()
                    + 143,
            red.getBlue()
                    + 143,
            alphaValue);
    public static Color ODD_ROW_LOCK_COLOR = new Color(yellow.getRed() + 23,
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

    protected JButton btnSwitchInEditmode = new javax.swing.JButton();
    protected JButton btnDiscardChanges = new javax.swing.JButton();
    protected JButton btnAcceptChanges = new javax.swing.JButton();
    protected JButton cmdPrint = new javax.swing.JButton();
    protected JButton btnReload = new javax.swing.JButton();
    protected PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    protected final Vector<Clearable> clearAndDisableListeners = new Vector<Clearable>();
    protected MappingComponent mappingComponent;
    protected MapWidget mapWidget = null;
    protected final Vector<Widget> widgets = new Vector<Widget>();
    // ToDo make proper --> syncron with widgets
    protected final Vector<Editable> editables = new Vector<Editable>();
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
    protected ArrayList<JButton> editControls = new ArrayList<JButton>();
    protected String currentValidationErrorMessage = null;
    protected JButton btnSwitchInCreateMode;
    final CreateToolBar panCreate = new CreateToolBar(this);
    WorkbenchWidget workbenchWidget = null;
    final ArrayList<SearchControl> searchControls = new ArrayList<SearchControl>();
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
    private Set currentSearchResults = new TreeSet(new ReverseComparator(
                new EntityComparator(new ReverseComparator(new LeuchteComparator()))));
//    public static final String PROP_NEW_OBJECTS = "currentSearchResults";
    private boolean inCreateMode;
    private MapSearchControl mscPan = null;
    private int maxSearchResults = 50;
    private SearchWaitDialog searchWaitDialog = null;
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
    // this is ugly there should be a default toolbar ......
    private boolean isPendingForCreateMode = false;

    //~ Constructors -----------------------------------------------------------

    /**
     * ToDo FIX.
     */
    private BelisBroker() {
        System.out.println("constructor: " + BelisBroker.class.getName());
        execService = Executors.newCachedThreadPool();

        System.out.println("Constructor: " + BelisBroker.class.getName());
        initToolbar();
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
     * @param  widget  DOCUMENT ME!
     */
    public void addWidget(final Widget widget) {
        widgets.add(widget);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  widgets  DOCUMENT ME!
     */
    public void addWidgets(final Vector widgets) {
        final Iterator<Widget> it = widgets.iterator();
        while (it.hasNext()) {
            this.widgets.add(it.next());
        }
        // widgets.;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Vector<Widget> getWidgets() {
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
                        final Iterator<Widget> it = widgets.iterator();
                        while (it.hasNext()) {
                            final Widget tmp = it.next();
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
        final Iterator<Widget> it = widgets.iterator();
        while (it.hasNext()) {
            final Widget tmp = it.next();
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
    public void addEdtiable(final Editable editable) {
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
                        for (final Widget curWidget : widgets) {
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
            for (final Widget curWidget : widgets) {
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
                JOptionPane.showMessageDialog(StaticSwingTools.getParentFrame(mappingComponent),
                    "Es ist kein Element vorhanden dem eine Fläche zugeordnet werden kann\noder die entsprechenden Rechte sind nicht ausreichend",
                    "Geometrie zuordnen",
                    JOptionPane.INFORMATION_MESSAGE);
                return null;
            }
            case 1: {
                final int anwser = JOptionPane.showConfirmDialog(StaticSwingTools.getParentFrame(mappingComponent),
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
                        StaticSwingTools.getParentFrame(mappingComponent),
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
        final Vector<GeometrySlotInformation> openSlots = new Vector<GeometrySlotInformation>();
        final Iterator<Widget> it = widgets.iterator();
        while (it.hasNext()) {
            final Widget curWidget = it.next();
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
        final Iterator<Widget> it = widgets.iterator();
        while (it.hasNext()) {
            final Widget curWidget = it.next();
            // TODO HARDCORE UGLY
            if ((curWidget instanceof FeatureSelectionChangedListener) && (event instanceof Collection)) {
                if (!featureSelectionChangedIgnoredWidgets.contains(curWidget)) {
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
        return mappingComponent;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  aMappingComponent  DOCUMENT ME!
     */
    public void setMappingComponent(final MappingComponent aMappingComponent) {
        mappingComponent = aMappingComponent;
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
            setComponentsEditable(false);
            releaseLock();
            setInEditMode(false);
            getMappingComponent().setReadOnly(true);
            switchInEditMode(false);
        } else {
            acquireLock();
            setInEditMode(true);
            getMappingComponent().setReadOnly(false);
            setComponentsEditable(true);
            switchInEditMode(true);
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
        this.inEditMode = inEditMode;
        propertyChangeSupport.firePropertyChange(PROP_IN_EDIT_MODE, null, inEditMode);
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
        final Iterator<Widget> it = widgets.iterator();
        while (it.hasNext()) {
            final Widget currentWidget = it.next();
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
     * @param   refreshClass  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Refreshable getRefreshableByClass(final Class<?> refreshClass) {
        final Iterator<Widget> it = widgets.iterator();
        while (it.hasNext()) {
            final Refreshable curRefreshable = it.next();
            if (curRefreshable.getClass().equals(refreshClass)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("ein Refreshable gefunden");
                }
                return curRefreshable;
            }
        }
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  refreshingObject  DOCUMENT ME!
     */
    public void refreshWidgets(final Object refreshingObject) {
        final Iterator<Widget> it = widgets.iterator();
        while (it.hasNext()) {
            final Refreshable curRefreshable = it.next();
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

    @Override
    public void masterConfigure(final Element parent) {
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
            searchWaitDialog = new SearchWaitDialog(StaticSwingTools.getParentFrame(getParentComponent()), true);
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
            } catch (Exception ex) {
                System.out.println("Error while configuring logging");
                ex.printStackTrace();
            }

        // Initialize Widgets
        try {
            final Element widgets = parent.getChild("Widgets");
            if (widgets != null) {
                for (final Element curWidget : (List<Element>)widgets.getChildren()) {
                    try {
                        addWidget(createWidget(curWidget));
                    } catch (Throwable ex) {
                        // ToDo proper print out of Widget
                        LOG.error("Error while initializing widget: " + curWidget, ex);
                    }
                }
            } else {
                LOG.warn("No widgets available (widgets=null)");
            }
        } catch (Exception ex) {
            LOG.error("Error while initializing widgets", ex);
        }

            System.out.println("masterConfigure: " + BelisBroker.class.getName());
            configManager.addConfigurable(layoutManager);
            configManager.configure(layoutManager);

            for (final Widget widget : getWidgets()) {
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
     * @param   widgetElement  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ClassNotFoundException     DOCUMENT ME!
     * @throws  NoSuchMethodException      DOCUMENT ME!
     * @throws  InstantiationException     DOCUMENT ME!
     * @throws  IllegalAccessException     DOCUMENT ME!
     * @throws  IllegalArgumentException   DOCUMENT ME!
     * @throws  InvocationTargetException  DOCUMENT ME!
     */
    private Widget createWidget(final Element widgetElement) throws ClassNotFoundException,
        NoSuchMethodException,
        InstantiationException,
        IllegalAccessException,
        IllegalArgumentException,
        InvocationTargetException {
        final String widgetName = widgetElement.getChildText("WidgetName");
        final String widgetClassName = widgetElement.getChildText("WidgetClass");
        final String widgetIconPath = widgetElement.getChildText("WidgetIcon");
        if (LOG.isDebugEnabled()) {
            LOG.debug("WidgetName: " + widgetName);
            LOG.debug("WidgetIcon: " + widgetIconPath);
            LOG.debug("Try to find class: " + widgetClassName + " for Widget: " + widgetName);
        }
        final Class widgetClass = Class.forName(widgetClassName);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Try to create instance of Class: " + widgetClass.getName());
        }
        final Constructor constructor = widgetClass.getConstructor(BelisBroker.class);
        final Widget createdWidget = (Widget)constructor.newInstance(this);
        createdWidget.setWidgetName(widgetName);
        createdWidget.setWidgetIcon(widgetIconPath);
        // ToDo set attributes isCoreWidget etc
        // ToDo flag and interface for MapWidget (Abstraction)
        if (createdWidget instanceof MapWidget) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Mapwidget found");
            }
            mapWidget = (MapWidget)createdWidget;
        }
        return createdWidget;
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
            ex.printStackTrace();
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
                        for (final JButton curButton : editControls) {
                            curButton.setEnabled(false);
                        }
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
     * @param  editControl  DOCUMENT ME!
     */
    public void addEditControl(final JButton editControl) {
        if (editControl != null) {
            editControls.add(editControl);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  editControl  DOCUMENT ME!
     */
    public void removeEditControl(final JButton editControl) {
        if (editControl != null) {
            editControls.remove(editControl);
        }
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
    // Todo outsource in panel, the advantage is that it is easier to edit --> you can use the gui builder
    // And don't know if it is good to have fix item in the toolbar

    /**
     * DOCUMENT ME!
     */
    private void initToolbar() {
        try {
            java.awt.GridBagConstraints gridBagConstraints;
            toolbar = new javax.swing.JToolBar();

            final JPanel editPan = new JPanel(new GridBagLayout());
            getToolbar().setRollover(true);
            getToolbar().setMinimumSize(new java.awt.Dimension(496, 33));
            final ArrayList<JButton> editButtons = getEditButtons();
            for (int i = 0; i < editButtons.size(); i++) {
                final JButton curButton = editButtons.get(i);
                gridBagConstraints = new java.awt.GridBagConstraints();
                if (!(i == 0)) {
                    gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 0);
                } else {
                    gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 0);
                }
                editPan.add(curButton, gridBagConstraints);
                // getToolbar().add(curButton);
            }
            editPan.setOpaque(false);
            getToolbar().add(editPan);
            addSeparatorToToolbar();

            cmdPrint.setIcon(new javax.swing.ImageIcon(
                    getClass().getResource("/de/cismet/commons/architecture/resource/icon/toolbar/frameprint.png"))); // NOI18N

            cmdPrint.setToolTipText("Drucken");
            cmdPrint.setBorderPainted(false);
            cmdPrint.setFocusable(false);
            cmdPrint.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            cmdPrint.setPreferredSize(new java.awt.Dimension(23, 23));
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
            getToolbar().add(cmdPrint);

            addSeparatorToToolbar();
        } catch (Exception ex) {
            System.out.println("Exception while initializing toolbar");
            ex.printStackTrace();
            LOG.error("Exception while initializing toolbar.", ex);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected ArrayList<JButton> getEditButtons() {
        final ArrayList<JButton> editButtons = new ArrayList<JButton>();
        btnSwitchInEditmode.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/commons/architecture/resource/icon/toolbar/editmode.png"))); // NOI18N
        // btnSwitchInEditmode.setEnabled(false);
        btnSwitchInEditmode.setToolTipText("Editormodus");
        btnSwitchInEditmode.setBorderPainted(false);
        btnSwitchInEditmode.setFocusable(false);
        btnSwitchInEditmode.setPreferredSize(new java.awt.Dimension(23, 23));
        btnSwitchInEditmode.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Versuche in Editiermodus zu wechseln: ");
                    }
                    try {
                        switchEditMode();
                        for (final JButton curControl : editControls) {
                            curControl.setEnabled(false);
                        }
                        btnAcceptChanges.setEnabled(true);
                        btnDiscardChanges.setEnabled(true);
                        setTitleBarComponentpainter(EDIT_MODE_COLOR); //
                    } catch (Exception ex) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Fehler beim anlegen der Sperre", ex);
                        }
                    }

                    getMappingComponent().setReadOnly(false);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("ist im Editiermodus: " + isInEditMode());
                    }
                }
            });
        editControls.add(btnSwitchInEditmode);

        editButtons.add(btnSwitchInEditmode);
        btnDiscardChanges.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/commons/architecture/resource/icon/toolbar/cancel.png"))); // NOI18N

        btnDiscardChanges.setToolTipText("Änderungen Abbrechen");
        btnDiscardChanges.setBorderPainted(false);
        btnDiscardChanges.setFocusable(false);
        btnDiscardChanges.setEnabled(false);
        btnDiscardChanges.setPreferredSize(new java.awt.Dimension(23, 23));
        btnDiscardChanges.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    if (isInEditMode()) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Versuche aus Editiermodus heraus zu wechseln: ");
                        }
                        // ToDo make generic
                        final int answer = JOptionPane.showConfirmDialog(
                                getParentComponent(),
                                "Wollen Sie die gemachten Änderungen verwerfen?",
                                "Belis Änderungen",
                                JOptionPane.YES_NO_OPTION);
                        if (answer == JOptionPane.NO_OPTION) {
                            return;
                        }

                        try {
                            fireCancelStarted();
                            execute(new SaveCancelWorker(SaveCancelWorker.CANCEL_MODE));
                            // ((DefaultFeatureCollection)LagisBroker.getInstance().getMappingComponent().getFeatureCollection()).setAllFeaturesEditable(false);
                            // TODO TEST IT!!!!
                            // TODO EDT
                        } catch (Exception ex) {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("Fehler beim lösen der Sperre", ex);
                            }
                        }
                        if (LOG.isDebugEnabled()) {
                            // btnOpenWizard.setEnabled(true);
                            // LagisBroker.getInstance().reloadFlurstueck();
                            LOG.debug("ist im Editiermodus: " + isInEditMode());
                        }
                    }
                }
            });
        editButtons.add(btnDiscardChanges);
        btnAcceptChanges.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/commons/architecture/resource/icon/toolbar/ok.png"))); // NOI18N

        btnAcceptChanges.setToolTipText("Änderungen annehmen");
        btnAcceptChanges.setBorderPainted(false);
        btnAcceptChanges.setFocusable(false);
        btnAcceptChanges.setEnabled(false);
        btnAcceptChanges.setPreferredSize(new java.awt.Dimension(23, 23));
        btnAcceptChanges.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    try {
                        if (isInEditMode()) {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("Versuche aus Editiermodus heraus zu wechseln: ");
                            }
                            final boolean isValid = validateWidgets();
                            if (isValid) {
                                if (LOG.isDebugEnabled()) {
                                    LOG.debug("Alle Änderungen sind valide: " + isValid);
                                }
                                // ToDo make generic
                                final int answer = JOptionPane.showConfirmDialog(
                                        getParentComponent(),
                                        "Wollen Sie die gemachten Änderungen speichern?",
                                        "Belis Änderungen",
                                        JOptionPane.YES_NO_OPTION);
                                if (answer == JOptionPane.YES_OPTION) {
                                    // LagisBroker.getInstance().saveCurrentFlurstueck();
                                    fireSaveStarted();
                                    execute(new SaveCancelWorker(SaveCancelWorker.SAVE_MODE));
                                } else {
                                    return;
                                }
                            } else {
                                final String reason = getCurrentValidationErrorMessage();
                                if (LOG.isDebugEnabled()) {
                                    LOG.debug(
                                        "Es kann nicht gespeichert werden, da nicht alle Komponenten valide sind. Grund:\n"
                                                + reason);
                                }
                                JOptionPane.showMessageDialog(
                                    getParentComponent(),
                                    "Änderungen können nur gespeichert werden, wenn alle Inhalte korrekt sind:\n\n"
                                            + reason
                                            + "\n\nBitte berichtigen Sie die Inhalte oder machen Sie die jeweiligen Änderungen rückgängig.",
                                    "Fehler",
                                    JOptionPane.WARNING_MESSAGE);
                            }
                        }
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("ist im Editiermodus: " + isInEditMode());
                        }
                    } catch (Exception ex) {
                        LOG.error("Fehler beim akzeptieren von Änderungen: ", ex);
                        showSaveErrorDialog();
                    }
                }
            });
        editButtons.add(btnAcceptChanges);

        btnSwitchInCreateMode = new javax.swing.JButton();
        editControls.add(btnSwitchInCreateMode);
        btnSwitchInCreateMode.setIcon(BelisIcons.icoCreate22); // NOI18N
        // btnSwitchInEditmode.setEnabled(false);
        btnSwitchInCreateMode.setToolTipText("Anlegenmodus");
        btnSwitchInCreateMode.setBorderPainted(false);
        btnSwitchInCreateMode.setFocusable(false);
        btnSwitchInCreateMode.setPreferredSize(new java.awt.Dimension(23, 23));
        btnSwitchInCreateMode.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("try to switch in createmode", new CurrentStackTrace());
                    }
                    try {
                        isPendingForCreateMode = true;
                        switchEditMode();
                        for (final JButton curControl : editControls) {
                            curControl.setEnabled(false);
                        }
                        btnAcceptChanges.setEnabled(true);
                        btnDiscardChanges.setEnabled(true);
                        setTitleBarComponentpainter(EDIT_MODE_COLOR); //
                        // ToDo CreateFlag
                    } catch (Exception ex) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Fehler beim anlegen der Sperre", ex);
                        }
                    }

                    getMappingComponent().setReadOnly(false);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("ist im Editiermodus: " + isInEditMode());
                    }
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("ist im Createmodus: " + isInCreateMode());
                    }
                }
            });
        editButtons.add(1, btnSwitchInCreateMode);

        return editButtons;
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
        tmpSeperator.setMaximumSize(new java.awt.Dimension(2, 32767));
        tmpSeperator.setMinimumSize(new java.awt.Dimension(2, 25));
        tmpSeperator.setPreferredSize(new java.awt.Dimension(2, 23));
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

        EventQueue.invokeAndWait(new Runnable() {

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
            });
        if (isInCreateMode()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("refreshing SearchResults. Doing mapsearch");
            }
            try {
                search(getMappingComponent().getCurrentBoundingBox());
            } catch (ActionNotSuccessfulException ex) {
                LOG.error("Error while doing mapsearch");
            }
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
        try {
            workbenchWidget.saveSelectedElementAndUnselectAll();
//            Set tmpResults = null;
            if (!isInCreateMode() && (lastSearch != null)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("is not in create mode. Refreshing search results with last search");
                }
                if (lastSearch.getBoundingBox() != null) {
                    search(lastSearch.getBoundingBox());
                } else {
                    search(lastSearch.getStrassenschluessel(),
                        lastSearch.getKennziffer(),
                        lastSearch.getLaufendenummer());
                }
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
        } catch (ActionNotSuccessfulException ex) {
            LOG.error("Error while refreshing objects: " + ex);
            return null;
        }
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

        final Highlighter noGeometryHighlighter = new ColorHighlighter(noGeometryPredicate, this.gray, null);
        // ((JXTable) tReBe).setHighlighters(LagisBroker.ALTERNATE_ROW_HIGHLIGHTER,noGeometryHighlighter);
        ttable.addHighlighter(noGeometryHighlighter);
        return ttable;
    }

    /**
     * DOCUMENT ME!
     */
    protected void showSaveErrorDialog() {
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
        addAddressSearch();
        addLocationSearch();
        addMapSearchControl();
        addCreateToolBar();
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
    public Set getCurrentSearchResults() {
        return currentSearchResults;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  currentSearchResults  DOCUMENT ME!
     */
    public void setCurrentSearchResults(final Set currentSearchResults) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("setSearchResults");
        }
        if ((this.currentSearchResults != null) && (currentSearchResults != null)
                    && this.currentSearchResults.equals(currentSearchResults)) {
            LOG.fatal("Sets are equals no propertyChange doing manually refresh --> ToDo fix me");
            this.currentSearchResults = currentSearchResults;
            refreshWidgets(getCurrentSearchResults());
        } else {
            this.currentSearchResults = currentSearchResults;
        }
        // ToDo why is it not working if I use null there is no equals check ???
        propertyChangeSupport.firePropertyChange(PROP_CURRENT_SEARCH_RESULTS, null, currentSearchResults);
    }
    // ToDo refactor
    // ToDo Backgroundthtread;

    /**
     * DOCUMENT ME!
     *
     * @param   strassenschluessel  DOCUMENT ME!
     * @param   kennziffer          DOCUMENT ME!
     * @param   laufendenummer      DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    public Set search(final String strassenschluessel, final Integer kennziffer, final Integer laufendenummer)
            throws ActionNotSuccessfulException {
        // ToDo remove method
        clearMap();
        final Set result = CidsBroker.getInstance().getObjectsByKey(strassenschluessel, kennziffer, laufendenummer);
        // ToDo should be checked if is in EDT
        if (result != null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Search results: " + result.size());
            }
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Search results delivered no result");
            }
        }
        final Set tmp = result;
        EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    if ((tmp != null) && !IsGreaterMaxSearchResults(tmp.size())) {
                        setCurrentSearchResults(tmp);
                        // ToDo PropertyChangeListener;
                        refreshMap();
                        mappingComponent.zoomToFullFeatureCollectionBounds();
                    }
                }
            });
        return result;
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
     * @param   bb  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    public Set search(final BoundingBox bb) throws ActionNotSuccessfulException {
        Set result = null;
        try {
            clearMap();
            result = CidsBroker.getInstance().getObjectsByBoundingBox(bb);
            if (result != null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Search results: " + result.size());
                }
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Search results delivered no result");
                }
            }
            final Set tmp = result;
            EventQueue.invokeAndWait(new Runnable() {

                    @Override
                    public void run() {
                        if ((tmp != null) && !IsGreaterMaxSearchResults(tmp.size())) {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug(tmp);
                            }
                            setCurrentSearchResults(tmp);
                            // ToDo PropertyChangeListener;
                            refreshMap();
                        }
                    }
                });
        } catch (Exception ex) {
            LOG.error("Exception while searching boundingbox: ", ex);
            return new TreeSet();
        }
        return result;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  searchResults  DOCUMENT ME!
     */
    private void addEntityRecursiveToMap(final Collection searchResults) {
        final ArrayList<Feature> featuresToAdd = new ArrayList<Feature>();
        if (searchResults != null) {
            for (final Object currentResult : searchResults) {
                if ((currentResult instanceof StyledFeature)
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
                        addEntityRecursiveToMap(((TdtaStandortMastCustomBean)currentResult).getLeuchten());
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
            if (featuresToAdd.size() > 0) {
                getMappingComponent().getFeatureCollection().substituteFeatures(featuresToAdd);
            }
        }
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
                addEntityRecursiveToMap(getCurrentSearchResults());
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
     *
     * @param  workbenchWidget  DOCUMENT ME!
     */
    public void setWorkbenchWidget(final WorkbenchWidget workbenchWidget) {
        this.workbenchWidget = workbenchWidget;
    }

    /**
     * DOCUMENT ME!
     */
    private void doBelisBinding() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("DoBelisBinding()");
        }
        WorkbenchWidget wWidget = null;
        DetailWidget dWidget = null;
        for (final Widget curWidget : getWidgets()) {
            if ((wWidget != null) && (dWidget != null)) {
                break;
            } else if (curWidget instanceof WorkbenchWidget) {
                workbenchWidget = (WorkbenchWidget)curWidget;
                wWidget = (WorkbenchWidget)curWidget;
            } else if (curWidget instanceof DetailWidget) {
                dWidget = (DetailWidget)curWidget;
                detailWidget = (DetailWidget)curWidget;
            }
        }
        if ((wWidget == null) || (dWidget == null)) {
            LOG.warn("Workbench Widget could not be bound to Detail Widget because one of them == null");
        }

        final BindingGroup bindingGroup = new BindingGroup();

        // ToDo should only be read
        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                wWidget,
                org.jdesktop.beansbinding.ELProperty.create("${selectedTreeNode.lastPathComponent.userObject}"),
                dWidget,
                org.jdesktop.beansbinding.BeanProperty.create("currentEntity"));
//        org.jdesktop.beansbinding.Binding binding2 = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, wWidget, org.jdesktop.beansbinding.ELProperty.create("${selectedTreeNode.lastPathComponent.userObject.class}"),mappingComponent, org.jdesktop.beansbinding.BeanProperty.create("inputEventListener["+getMappingComponent().NEW_POLYGON+"].geometryFeatureClass"));
//        getMappingComponent().getInputEventListener();
        binding.setSourceUnreadableValue(null);
//        binding2.setSourceUnreadableValue(null);
        wWidget.addPropertyChangeListener("selectedTreeNode", dWidget);
//        bindingGroup.addBinding(binding2);
        bindingGroup.addBinding(binding);
        // CreateToolbar binding
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                wWidget,
                org.jdesktop.beansbinding.ELProperty.create("${selectedTreeNode.lastPathComponent.userObject}"),
                panCreate,
                org.jdesktop.beansbinding.BeanProperty.create("currentEntity"));
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        bindingGroup.bind();
//        bindingGroup2.bind();
    }

    /**
     * ToDo more than ugly with the isPendingForCreate must somehow inserted in Framework.
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public void acquireLock() throws Exception {
        // ToDo should be some notification for edit mode this is not the right place
        if (isPendingForCreateMode) {
            setInCreateMode(true);
            isPendingForCreateMode = false;
        }
        if (!isInCreateMode()) {
            try {
                CidsBroker.getInstance().lockEntity(currentSearchResults, getAccountName());
            } catch (ActionNotSuccessfulException ex) {
                LOG.error("Error while creating lock:", ex);
                isPendingForCreateMode = false;
                throw new Exception(
                    "Sperren konnten nicht angelegt werden, wechseln in Editmodus nicht möglich");
            } catch (LockAlreadyExistsException ex) {
                LOG.info("Some of the objects are already locked", ex);
                isPendingForCreateMode = false;
                final ArrayList<SperreCustomBean> alreadyLocked = ex.getAlreadyExisingLocks();
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Count of already locked objects: " + alreadyLocked.size());
                }
                showObjectsLockedDialog(alreadyLocked);
                isPendingForCreateMode = false;
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
            if ((unsuccessfulObjects != null) && (unsuccessfulObjects.size() != 0)) {
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
        final Vector<Widget> widgets = getWidgets();
        MapWidget mapWidget = null;
        if (widgets != null) {
            for (final Widget curWidget : widgets) {
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
                return;
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
    private void addAddressSearch() {
        final AddressSearchControl asPan = new AddressSearchControl(this);
        // ToDo should haben in Panels itself;
        addSearchControl(asPan);
        asPan.setMappingComponent(getMappingComponent());
        getConfigManager().addConfigurable(asPan);
        getConfigManager().configure(asPan);
        asPan.setFocusable(false);
        asPan.setPreferredSize(new java.awt.Dimension(450, 23));
        getToolbar().add(asPan);
        // ToDo ugly winning;
        addSeparatorToToolbar();
    }

    /**
     * DOCUMENT ME!
     */
    private void addLocationSearch() {
        final LocationSearchControl lsPan = new LocationSearchControl(this);
        lsPan.setFocusable(false);
        addSearchControl(lsPan);
        lsPan.setPreferredSize(new java.awt.Dimension(250, 23));
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
        mscPan.setPreferredSize(new java.awt.Dimension(150, 23));
        getToolbar().add(mscPan);
    }

    /**
     * DOCUMENT ME!
     */
    private void addCreateToolBar() {
        addEdtiable(panCreate);
        panCreate.setFocusable(false);
        getToolbar().add(panCreate, 4);
        getToolbar().add(createToolBarSeperator(), 5);
    }
    // ToDo mayby better to operate on the

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
     *
     * @param  entity  DOCUMENT ME!
     */
    public void removeEntity(final Object entity) {
        workbenchWidget.removeEntity(entity);
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
        EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    searchWaitDialog.setVisible(false);
                }
            });
        if (!isFullReadOnlyMode) {
            for (final JButton curControl : editControls) {
                curControl.setEnabled(true);
            }
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
        EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    searchWaitDialog.setLocationRelativeTo(StaticSwingTools.getParentFrame(getParentComponent()));
                    searchWaitDialog.setVisible(true);
                }
            });
        for (final JButton curControl : editControls) {
            curControl.setEnabled(false);
        }
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
        for (final SearchControl curSearchControl : searchControls) {
            curSearchControl.setSearchEnabled(isSearchEnabled);
        }
    } // public Set getNewObjects() {

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("PropertyChangeEvent");
        }
        if ((evt != null) && (evt.getPropertyName() != null)) {
            if (evt.getPropertyName().equals(LeitungCustomBean.PROP_LEITUNGSTYP) && (evt.getSource() != null)
                        && (evt.getSource() instanceof LeitungCustomBean)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("LeitungsTyp Changed");
                }
                lastLeitungstyp = (LeitungstypCustomBean)evt.getNewValue();
                getMappingComponent().getFeatureCollection().reconsiderFeature((LeitungCustomBean)evt.getSource());
            } else if (evt.getPropertyName().equals(TdtaLeuchteCustomBean.PROP_LEUCHTENNUMMER)
                        && (evt.getSource() != null)
                        && (evt.getSource() instanceof TdtaLeuchteCustomBean)) {
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
                            (TdtaLeuchteCustomBean)evt.getSource());
                    if (virtualStandort != null) {
                        getMappingComponent().getFeatureCollection().reconsiderFeature(virtualStandort);
                    } else {
                        LOG.warn("Leuchte is neither Hängeleuchte nor attached to a mast. Can't update label in map");
                    }
                }
            } else if (evt.getPropertyName().equals(MauerlascheCustomBean.PROP_STRASSENSCHLUESSEL)
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
                    for (final JButton curControl : editControls) {
                        curControl.setEnabled(true);
                    }
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
                return;
            }
        }
    }
}
