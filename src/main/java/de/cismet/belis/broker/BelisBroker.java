/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * LagisBroker.java
 *
 * Created on 20. April 2007, 13:16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package de.cismet.belis.broker;

import org.apache.commons.collections.comparators.ReverseComparator;

import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingx.search.TreeSearchable;

import org.jdom.Element;

import java.awt.EventQueue;
import java.awt.Font;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
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

import de.cismet.belisEE.entity.Doppelkommando;
import de.cismet.belisEE.entity.Leitung;
import de.cismet.belisEE.entity.Leitungstyp;
import de.cismet.belisEE.entity.Leuchte;
import de.cismet.belisEE.entity.Lock;
import de.cismet.belisEE.entity.Mauerlasche;
import de.cismet.belisEE.entity.Standort;
import de.cismet.belisEE.entity.Strassenschluessel;
import de.cismet.belisEE.entity.UnterhaltLeuchte;
import de.cismet.belisEE.entity.UnterhaltMast;

import de.cismet.belisEE.exception.ActionNotSuccessfulException;
import de.cismet.belisEE.exception.LockAlreadyExistsException;

import de.cismet.belisEE.util.EntityComparator;
import de.cismet.belisEE.util.LeuchteComparator;

import de.cismet.cismap.commons.BoundingBox;
import de.cismet.cismap.commons.features.DefaultFeatureCollection;
import de.cismet.cismap.commons.features.Feature;
import de.cismet.cismap.commons.features.FeatureCollection;
import de.cismet.cismap.commons.features.StyledFeature;
import de.cismet.cismap.commons.tools.IconUtils;

import de.cismet.commons.architecture.broker.AdvancedPluginBroker;
import de.cismet.commons.architecture.exception.LockingNotSuccessfulException;
import de.cismet.commons.architecture.exception.UnlockingNotSuccessfulException;
import de.cismet.commons.architecture.interfaces.Widget;
import de.cismet.commons.architecture.widget.MapWidget;

import de.cismet.commons.server.entity.GeoBaseEntity;

import de.cismet.tools.CurrentStackTrace;

import de.cismet.tools.gui.StaticSwingTools;

import de.cismet.veto.VetoException;
import de.cismet.veto.VetoListener;

/**
 * DOCUMENT ME!
 *
 * @author   Puhl
 * @version  $Revision$, $Date$
 */
public class BelisBroker extends AdvancedPluginBroker implements SearchController,
    PropertyChangeListener,
    VetoListener { // implements FlurstueckChangeObserver, Configurable {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(BelisBroker.class);
    public static final String PROP_CURRENT_SEARCH_RESULTS = "currentSearchResults";
    private static UnterhaltMast defaultUnterhaltMast = null;
    private static UnterhaltLeuchte defaultUnterhaltLeuchte = null;
    private static Doppelkommando defaultDoppelkommando1 = null;
    public static final String[] jxDatePickerFormats = new String[] { "dd.MM.yyyy", "ddMMyy", "ddMMyyyy" };

    //~ Instance fields --------------------------------------------------------

    protected JButton btnSwitchInCreateMode;
    final CreateToolBar panCreate = new CreateToolBar(this);
    WorkbenchWidget workbenchWidget = null;
    final ArrayList<SearchControl> searchControls = new ArrayList<SearchControl>();
    private AlreadyLockedObjectsPanel lockPanel = null;
    // Todo maybe deliver mapWidget by default so the derived broker has direct access and don't have to search
    private MapWidget mapWidget = null;
    private Set currentSearchResults = new TreeSet(new ReverseComparator(
                new EntityComparator(new ReverseComparator(new LeuchteComparator()))));
    private Set newObjects = new TreeSet(new ReverseComparator(
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
    private Leitungstyp lastLeitungstyp = null;
    private boolean vetoCheckEnabled = true;
    private Strassenschluessel lastMauerlascheStrassenschluessel;
    private BindingGroup bindingGroup2 = new BindingGroup();
    // I Think the single components should register the properties they want to bind and broker only binds them that
    // would be fine bind Workbench to Details maybe if another applications needs the same feature or if it is used
    // more often throughout the application ToDo if there is need for more special binding generalize this with static
    // binding methods (e.g. Master/Slave or other mechanismn)
    private final ArrayList<GeoBaseEntity> currentFeatures = new ArrayList<GeoBaseEntity>();
    // this is ugly there should be a default toolbar ......
    private boolean isPendingForCreateMode = false;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new BelisBroker object.
     */
    public BelisBroker() {
        super();
        if (log.isDebugEnabled()) {
            log.debug("Constructor:" + BelisBroker.class.getName());
        }
    }

    //~ Methods ----------------------------------------------------------------

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
        if (log.isDebugEnabled()) {
            log.debug("setSearchResults");
        }
        if ((this.currentSearchResults != null) && (currentSearchResults != null)
                    && this.currentSearchResults.equals(currentSearchResults)) {
            log.fatal("Sets are equals no propertyChange doing manually refresh --> ToDo fix me");
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
    public Set search(final String strassenschluessel, final Short kennziffer, final Short laufendenummer)
            throws ActionNotSuccessfulException {
        // ToDo remove method
        clearMap();
        final Set result = EJBroker.getInstance().getObjectsByKey(strassenschluessel, kennziffer, laufendenummer);
        // ToDo should be checked if is in EDT
        if (result != null) {
            if (log.isDebugEnabled()) {
                log.debug("Search results: " + result.size());
            }
        } else {
            if (log.isDebugEnabled()) {
                log.debug("Search results delivered no result");
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
            result = EJBroker.getInstance().getObjectsByBoundingBox(bb);
            if (result != null) {
                if (log.isDebugEnabled()) {
                    log.debug("Search results: " + result.size());
                }
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("Search results delivered no result");
                }
            }
            final Set tmp = result;
            EventQueue.invokeAndWait(new Runnable() {

                    @Override
                    public void run() {
                        if ((tmp != null) && !IsGreaterMaxSearchResults(tmp.size())) {
                            if (log.isDebugEnabled()) {
                                log.debug(tmp);
                            }
                            setCurrentSearchResults(tmp);
                            // ToDo PropertyChangeListener;
                            refreshMap();
                        }
                    }
                });
        } catch (Exception ex) {
            log.error("Exception while searching boundingbox: ", ex);
            return new TreeSet();
        }
        return result;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  searchResults  DOCUMENT ME!
     */
    private void addEntityRecursiveToMap(final Set searchResults) {
        final ArrayList<Feature> featuresToAdd = new ArrayList<Feature>();
        if (searchResults != null) {
            for (final Object currentResult : searchResults) {
                if ((currentResult instanceof StyledFeature)
                            && (((StyledFeature)currentResult).getGeometry() != null)) {
                    if (log.isDebugEnabled()) {
                        log.debug("SearchResult is Styled Feature and geometry != null --> adding feature to map.");
                    }
                    featuresToAdd.add((StyledFeature)currentResult);
                    // getMappingComponent().getFeatureCollection().addFeature((StyledFeature) currentResult);
                    if ((currentResult instanceof Standort) && (((Standort)currentResult).getLeuchten() != null)) {
                        if (log.isDebugEnabled()) {
                            log.debug(
                                "SearchResult is instance of Standort and owns Leuchte objects --> also adding to Map");
                        }
                        addEntityRecursiveToMap(((Standort)currentResult).getLeuchten());
                    }
                    if (currentResult instanceof Leitung) {
                        if (log.isDebugEnabled()) {
                            log.debug("SearchResult is instance of Leitung. Setting PropertyChangeListener");
                        }
                        ((Leitung)currentResult).addPropertyChangeListener(this);
                    }
                    if (currentResult instanceof Mauerlasche) {
                        if (log.isDebugEnabled()) {
                            log.debug("SearchResult is instance of Leitung. Setting PropertyChangeListener");
                        }
                        ((Mauerlasche)currentResult).addPropertyChangeListener(this);
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
            log.error("Error while clearing map: ", ex);
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void refreshMap() {
        if (getCurrentSearchResults() != null) {
            if (log.isDebugEnabled()) {
                log.debug("refreshMap (features): " + getCurrentSearchResults().size());
            }
            if (log.isDebugEnabled()) {
                log.debug("refreshMap featureCollection: "
                            + getMappingComponent().getFeatureCollection().getFeatureCount());
            }
        } else {
            if (log.isDebugEnabled()) {
                log.debug("refreshMap no results");
            }
        }
        try {
            if (EventQueue.isDispatchThread()) {
                getMappingComponent().getFeatureCollection().removeAllFeatures();
                addEntityRecursiveToMap(getCurrentSearchResults());
//                if(getMappingComponent().getFeatureCollection().getAllFeatures() != null){
//                    for(Feature curFeatrue:getMappingComponent().getFeatureCollection().getAllFeatures()){
//                        getMappingComponent().getFeatureCollection().reconsiderFeature(curFeatrue);
//                    }
//                }
            } else {
                EventQueue.invokeAndWait(new Runnable() {

                        @Override
                        public void run() {
                            refreshMap();
                        }
                    });
            }
        } catch (Exception ex) {
            log.error("Error while refreshińg map: ", ex);
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

    @Override
    public void masterConfigure(final Element parent) {
        System.out.println("conifgure glassfish");
        try {
            final Element prefs = parent.getChild("GlassfishSetup");
            try {
                if (log.isDebugEnabled()) {
                    log.debug("Glassfishhost: " + prefs.getChildText("Host"));
                }
                System.setProperty("org.omg.CORBA.ORBInitialHost", prefs.getChildText("Host"));
            } catch (Exception ex) {
                log.warn("Fehler beim lesen des Glassfish Hosts", ex);
            }
            try {
                if (log.isDebugEnabled()) {
                    log.debug("Glassfisport: " + prefs.getChildText("OrbPort"));
                }
                System.setProperty("org.omg.CORBA.ORBInitialPort", prefs.getChildText("OrbPort"));
            } catch (Exception ex) {
                log.warn("Fehler beim lesen des Glassfish Ports", ex);
            }
        } catch (Exception ex) {
            log.warn("Error while configuring Glassfish");
        }
        try {
            final Element prefs = parent.getChild("Options");
            try {
                if (log.isDebugEnabled()) {
                    log.debug("MaxSearchResults: " + prefs.getChildText("MaxSearchResults"));
                }
                maxSearchResults = Integer.valueOf(prefs.getChildText("MaxSearchResults"));
            } catch (Exception ex) {
                log.warn("Error while reading max value for search results.Using default: " + maxSearchResults, ex);
            }
        } catch (Exception ex) {
            log.warn("Error while reading options");
        }
        try {
            searchWaitDialog = new SearchWaitDialog(StaticSwingTools.getParentFrame(getParentComponent()), true);
            saveWaitDialog = new SaveWaitDialog(StaticSwingTools.getParentFrame(getParentComponent()), true);
            cancelWaitDialog = new CancelWaitDialog(StaticSwingTools.getParentFrame(getParentComponent()), true);
        } catch (Exception ex) {
            log.warn("Error while creating search and wait dialog");
        }
        super.masterConfigure(parent);
        if (log.isDebugEnabled()) {
            log.debug("masterConfigure:" + BelisBroker.class.getName());
        }
        if (log.isDebugEnabled()) {
            log.debug("Configure Belis specific borker");
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
            log.warn("Error setting fontsize for map objects. Setting font to default: " + defaultFontSize);
            IconUtils.setFont(new Font("Courier", Font.PLAIN, defaultFontSize));
        }
    } // ToDo

    /**
     * DOCUMENT ME!
     */
    private void doBelisBinding() {
        if (log.isDebugEnabled()) {
            log.debug("DoBelisBinding()");
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
            log.warn("Workbench Widget could not be bound to Detail Widget because one of them == null");
        }

        final BindingGroup bindingGroup = new BindingGroup();

//         BindingGroup bindingGroup2 = new BindingGroup();

//        //SearchResultWidgets binding
//        for(Widget curWidget:getWidgets()){
//            if(curWidget instanceof SearchResultWidget){
//                log.debug("Bind SearchResultWidget: "+curWidget.getWidgetName()+" to currentSearchResultProperty of BelisBroker");
//                  final org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${currentSearchResults}"), ((SearchResultWidget)curWidget), org.jdesktop.beansbinding.BeanProperty.create("${searchResults}"));
//                  bindingGroup.addBinding(binding);
//            }
//        }
        // Editbutton binding
//        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${currentSearchResults != null}"),btnSwitchInEditmode, org.jdesktop.beansbinding.BeanProperty.create("visible"));
//        bindingGroup2.addBinding(binding);
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

    // ToDo more than ugly with the isPendingForCreate must somehow inserted in Framework
    @Override
    public void acquireLock() throws LockingNotSuccessfulException {
        super.acquireLock();
        // ToDo should be some notification for edit mode this is not the right place
        if (isPendingForCreateMode) {
            setInCreateMode(true);
            isPendingForCreateMode = false;
        }
        if (!isInCreateMode()) {
            try {
                EJBroker.getInstance().lockEntity(currentSearchResults, getAccountName());
            } catch (ActionNotSuccessfulException ex) {
                log.error("Error while creating lock:", ex);
                isPendingForCreateMode = false;
                throw new LockingNotSuccessfulException(
                    "Sperren konnten nicht angelegt werden, wechseln in Editmodus nicht möglich");
            } catch (LockAlreadyExistsException ex) {
                log.info("Some of the objects are already locked", ex);
                isPendingForCreateMode = false;
                final ArrayList<Lock> alreadyLocked = ex.getAlreadyExisingLocks();
                if (log.isDebugEnabled()) {
                    log.debug("Count of already locked objects: " + alreadyLocked.size());
                }
                showObjectsLockedDialog(alreadyLocked);
                isPendingForCreateMode = false;
                throw new LockingNotSuccessfulException(
                    "Einige der Objekte konnten nicht gesperrt werden, weil sie schon gesperrt sind");
            }
        } else {
            log.info("Create Modus no locks necessary");
        }
    }

    @Override
    public void releaseLock() throws UnlockingNotSuccessfulException {
        super.releaseLock();
        setInCreateMode(false);
        if (!isInCreateMode()) {
            final Set unsuccessfulObjects = null;
            try {
                // ToDo problem with unlocking of entities not locked by this user
                EJBroker.getInstance().unlockEntity(currentSearchResults);
            } catch (ActionNotSuccessfulException ex) {
                log.error("Error while unlocking locked objects:", ex);
                throw new UnlockingNotSuccessfulException("Angelegte sperren konnten nicht gelöst werden.");
            }
            if ((unsuccessfulObjects != null) && (unsuccessfulObjects.size() != 0)) {
                // ToDo what to do ? Error to the user and go
                log.error("Some of the objects couldn't be unlocked posting to the user");
            }
        } else {
            log.info("Create Modus no locks necessary");
        }
        // ToDo should be some notification for edit mode this is not the right place
    }

    @Override
    protected void switchInEditMode(final boolean isSwitchedInEditMode) {
        super.switchInEditMode(isSwitchedInEditMode);
        if (isSwitchedInEditMode) {
            disableSearch();
            if (isInCreateMode()) {
                setAllFeaturesSelectable(false);
            }
        } else {
            enableSearch();
            // if (isInCreateMode()) {
            setAllFeaturesSelectable(true);
            // }
            // ToDo disabled Functionality 04.05.2009
            // workbenchWidget.clearNewObjects();
        }
    }
    /**
     * ToDo better over currentSearch results.
     *
     * @param  selectable  DOCUMENT ME!
     */
    private void setAllFeaturesSelectable(final boolean selectable) {
        if (log.isDebugEnabled()) {
            log.debug("setAllFeaturesNotEditable()");
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
            if (log.isDebugEnabled()) {
                log.debug("restoring features");
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
    private void showObjectsLockedDialog(final ArrayList<Lock> lockedObjects) {
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
        StaticSwingTools.showDialog(dialog);
    }

    @Override
    protected void showSaveErrorDialog() {
        super.showSaveErrorDialog();
        final JDialog dialog = new JDialog(StaticSwingTools.getParentFrame(getParentComponent()),
                "Fehler beim speichern...",
                true);
        dialog.setIconImage(((ImageIcon)BelisIcons.icoError16).getImage());
        dialog.add(new SaveErrorDialogPanel());
        dialog.pack();
        StaticSwingTools.showDialog(dialog);
    }

    @Override
    protected void fireCancelFinished() {
        EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    cancelWaitDialog.setVisible(false);
                }
            });
    }

    @Override
    protected void fireCancelStarted() {
        EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    StaticSwingTools.showDialog(cancelWaitDialog);
                }
            });
    }

    @Override
    protected void fireSaveFinished() {
        saveWaitDialog.setVisible(false);
    }

    @Override
    protected void fireSaveStarted() {
        EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    StaticSwingTools.showDialog(saveWaitDialog);
                }
            });
    }

    // ToDo idea mabey a method where the instance above collects the objects to save Set objectsToSave
    // ToDo in Background
    // ToDo would be couler if the swingworker would get the runnable and execute it in edt
    @Override
    public Runnable save() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("save");
        }
        final Runnable superRun = super.save();
        if (isInCreateMode()) {
            if (log.isDebugEnabled()) {
                log.debug(workbenchWidget.getNewObjects().size() + " Objects to Save");
            }
            EJBroker.getInstance().saveObjects(workbenchWidget.getNewObjects(), getAccountName());
        } else {
            if (log.isDebugEnabled()) {
                log.debug(getCurrentSearchResults().size() + " Objects to Save");
            }
            EJBroker.getInstance().saveObjects(getCurrentSearchResults(), getAccountName());
        }

        EJBroker.getInstance().deleteEntities(workbenchWidget.getObjectsToRemove(), getAccountName());
        if (log.isDebugEnabled()) {
            log.debug("Changes are saved");
        }
        // EJBroker.getInstance().refreshObjects(workbenchWidget.getNewObjects());
        final Runnable saveEDTRun = new Runnable() {

                @Override
                public void run() {
                    if (superRun != null) {
                        superRun.run();
                    }
                }
            };

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
            if (log.isDebugEnabled()) {
                log.debug("refreshing SearchResults. Doing mapsearch");
            }
            try {
                search(getMappingComponent().getCurrentBoundingBox());
            } catch (ActionNotSuccessfulException ex) {
                log.error("Error while doing mapsearch");
            }
        } else {
            if (log.isDebugEnabled()) {
                log.debug("not in createmode");
            }
        }
        return saveEDTRun;
    }

//    //ToDo ugly
//    public void startMapSearch(){
//        mscPan.startMapSearch();
//    }
    // ToDo PropertyChangeListner + backgroundworker
    @Override
    protected Runnable cancel() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("cancel");
        }
        try {
            workbenchWidget.saveSelectedElementAndUnselectAll();
            final Runnable superRun = super.cancel();
//            Set tmpResults = null;
            if (!isInCreateMode() && (lastSearch != null)) {
                if (log.isDebugEnabled()) {
                    log.debug("is not in create mode. Refreshing search results with last search");
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
                        if (superRun != null) {
                            superRun.run();
                        }
                        if (!isInCreateMode()) {
                            // ToDo After this statement there is a exception console (case there are search results and
                            // the edit modus is cancled) setCurrentSearchResults(refreshedSearchResults);
                            workbenchWidget.restoreRemovedObjects();
                        } else {
                            if (log.isDebugEnabled()) {
                                log.debug("is in create mode");
                            }
                            workbenchWidget.clearNewObjects();
                        }
                        // refreshMap();
                        workbenchWidget.restoreSelectedElementIfPossible();
                        if (log.isDebugEnabled()) {
                            log.debug("Objects are refreshed");
                        }
                    }
                };
            // runOrEDTDispatch(cancelEDTRun);
            return cancelEDTRun;
        } catch (ActionNotSuccessfulException ex) {
            log.error("Error while refreshing objects: " + ex);
            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  runnable  DOCUMENT ME!
     */
    public static void runOrEDTDispatch(final Runnable runnable) {
        if (runnable != null) {
            if (EventQueue.isDispatchThread()) {
                if (log.isDebugEnabled()) {
                    log.debug("Thread is EDT. Execute run");
                }
                runnable.run();
            } else {
                log.info("Thread is not EDT. InvokeLater");
                EventQueue.invokeLater(runnable);
            }
        }
    }

    @Override
    protected void cancelFailed() {
        super.cancelFailed();
    }

    // ToDo maybe better to catch exceptions in save and rethrow them ? than this method
    @Override
    protected void saveFailed() {
        super.saveFailed();
    }

    /**
     * DOCUMENT ME!
     */
    private void customizeMapWidget() {
        if (log.isDebugEnabled()) {
            log.debug("Customizing MapWidget");
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
                if (log.isDebugEnabled()) {
                    log.debug("MapWidget found");
                }
                this.mapWidget = mapWidget;
//                addBelisSpecificControlsToMapWidget();
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("Can't customize MapWidget not found");
                }
                return;
            }
        } else {
            log.warn("There are no widgets");
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

//    private void addBelisSpecificControlsToMapWidget() {
//        if (mapWidget != null) {
//            log.debug("adding Belis specific controls");
//            final JToolBar mapWidgetToolbar = mapWidget.getMapWidgetToolbar();
//
//
////            //addLeuchte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/commons/architecture/resource/icon/toolbar/newPoly.png"))); // NOI18N
////            btnLeuchte.setToolTipText("Neue Leuchte");
////            btnLeuchte.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 3, 1, 3));
////            //addLeuchte.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/commons/architecture/resource/icon/toolbar/newPoly_selected.png"))); // NOI18N
////            btnLeuchte.addActionListener(new java.awt.event.ActionListener() {
////
////                public void actionPerformed(java.awt.event.ActionEvent evt) {
////                    setDigitizeMode();
////                    btnLeuchte.setSelected(true);
////                    setCorrectFeatureClass();
////                }
////            });
////            mapWidgetToolbar.add(btnLeuchte);
////
////            //addLeuchte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/commons/architecture/resource/icon/toolbar/newPoly.png"))); // NOI18N
////            btnStandort.setToolTipText("Neuer Standort");
////            btnStandort.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 3, 1, 3));
////            //addLeuchte.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/commons/architecture/resource/icon/toolbar/newPoly_selected.png"))); // NOI18N
////            btnStandort.addActionListener(new java.awt.event.ActionListener() {
////
////                public void actionPerformed(java.awt.event.ActionEvent evt) {
////                    setDigitizeMode();
////                    btnStandort.setSelected(true);
////                    setCorrectFeatureClass();
////                }
////            });
////            mapWidgetToolbar.add(btnStandort);
//        } else {
//            log.warn("adding of Belis specific controls not possible beaucse mapwidget not found !");
//        }
//    }
//        private void setDigitizeMode() {
//        mapWidget.removeMainGroupSelection();
//        getMappingComponent().setInteractionMode(getMappingComponent().NEW_POLYGON);
//    }

//    private void setCorrectFeatureClass() {
////        Class f = null;
////        if (btnLeuchte.isSelected()) {
////            ((CreateGeometryListener) getMappingComponent().getInputListener(getMappingComponent().NEW_POLYGON)).setMode(CreateGeometryListener.POINT);
////            f = Leuchte.class;
////        } else if (btnStandort.isSelected()) {
////            ((CreateGeometryListener) getMappingComponent().getInputListener(getMappingComponent().NEW_POLYGON)).setMode(CreateGeometryListener.POINT);
////            f = Standort.class;
////        } else {
////            f = Leuchte.class;
////        }
////        ((CreateGeometryListener) getMappingComponent().getInputListener(getMappingComponent().NEW_POLYGON)).setGeometryFeatureClass(f);
//        bindingGroup2.bind();
//        bindingGroup2.unbind();
//    }
    // Toolbar
    @Override
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
            if (log.isDebugEnabled()) {
                log.debug("searchControl is null can't be add.");
            }
        }
    }

    @Override
    public synchronized void fireSearchFinished() {
        if (log.isDebugEnabled()) {
            log.debug("fireSearchFinished");
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
        if (log.isDebugEnabled()) {
            log.debug("fireSearchStarted");
        }
        EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    StaticSwingTools.showDialog(searchWaitDialog);
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
            if (log.isDebugEnabled()) {
                log.debug("searchControl is null can't be removed.");
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
        if (log.isDebugEnabled()) {
            log.debug("setSearchEnabled " + isSearchEnabled);
        }
        for (final SearchControl curSearchControl : searchControls) {
            curSearchControl.setSearchEnabled(isSearchEnabled);
        }
    } // public Set getNewObjects() {

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if (log.isDebugEnabled()) {
            log.debug("PropertyChangeEvent");
        }
        if ((evt != null) && (evt.getPropertyName() != null)) {
            if (evt.getPropertyName().equals(Leitung.PROP_LEITUNGSTYP) && (evt.getSource() != null)
                        && (evt.getSource() instanceof Leitung)) {
                if (log.isDebugEnabled()) {
                    log.debug("LeitungsTyp Changed");
                }
                lastLeitungstyp = (Leitungstyp)evt.getNewValue();
                getMappingComponent().getFeatureCollection().reconsiderFeature((Leitung)evt.getSource());
            } else if (evt.getPropertyName().equals(Leuchte.PROP_LEUCHTENNUMMER) && (evt.getSource() != null)
                        && (evt.getSource() instanceof Leuchte)) {
                if (log.isDebugEnabled()) {
                    log.debug("Leuchtennummer changed");
                }
                final TreePath pathToLeuchte = workbenchWidget.getTreeTableModel()
                            .getPathForUserObject(evt.getSource());
                Standort parentMast = null;
                if (pathToLeuchte != null) {
                    parentMast = workbenchWidget.getParentMast(pathToLeuchte.getLastPathComponent());
                }
                if (parentMast != null) {
                    getMappingComponent().getFeatureCollection().reconsiderFeature(parentMast);
                } else {
                    final Standort virtualStandort = workbenchWidget.getVirtualStandortForLeuchte((Leuchte)
                            evt.getSource());
                    if (virtualStandort != null) {
                        getMappingComponent().getFeatureCollection().reconsiderFeature(virtualStandort);
                    } else {
                        log.warn("Leuchte is neither Hängeleuchte nor attached to a mast. Can't update label in map");
                    }
                }
            } else if (evt.getPropertyName().equals(Mauerlasche.PROP_STRASSENSCHLUESSEL) && (evt.getSource() != null)
                        && (evt.getSource() instanceof Mauerlasche)) {
                if (log.isDebugEnabled()) {
                    log.debug("Mauerlasche Straßßenschlüssel Changed");
                }
                lastMauerlascheStrassenschluessel = (Strassenschluessel)evt.getNewValue();
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("PropertyChange not recognized from BelisBroker.");
                }
            }
        } else {
            log.warn("PropertyChangeEvent or PropertyName == null");
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Strassenschluessel getLastMauerlascheStrassenschluessel() {
        return lastMauerlascheStrassenschluessel;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  lastMauerlascheStrassenschluessel  DOCUMENT ME!
     */
    public void setLastMauerlascheStrassenschluessel(final Strassenschluessel lastMauerlascheStrassenschluessel) {
        this.lastMauerlascheStrassenschluessel = lastMauerlascheStrassenschluessel;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Leitungstyp getLastLeitungstyp() {
        return lastLeitungstyp;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  lastLeitungstyp  DOCUMENT ME!
     */
    public void setLastLeitungstyp(final Leitungstyp lastLeitungstyp) {
        this.lastLeitungstyp = lastLeitungstyp;
    }

    @Override
    protected ArrayList<JButton> getEditButtons() {
        final ArrayList parentButtons = super.getEditButtons();
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
                    if (log.isDebugEnabled()) {
                        log.debug("try to switch in createmode", new CurrentStackTrace());
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
                        if (log.isDebugEnabled()) {
                            log.debug("Fehler beim anlegen der Sperre", ex);
                        }
                    }

                    getMappingComponent().setReadOnly(false);
                    if (log.isDebugEnabled()) {
                        log.debug("ist im Editiermodus: " + isInEditMode());
                    }
                    if (log.isDebugEnabled()) {
                        log.debug("ist im Createmodus: " + isInCreateMode());
                    }
                }
            });
        parentButtons.add(1, btnSwitchInCreateMode);
        return parentButtons;
    }

    // should only be one mechanismn not three broker,creationtoolbar,worbenchwidget (Method)
    @Override
    public void veto() throws VetoException {
        if ((isInCreateMode() || isInEditMode()) && isVetoCheckEnabled()) {
            if (log.isDebugEnabled()) {
                log.debug(
                    "selectionVetoCheck: User is in edit mode and wants to change the current object over the map.",
                    new CurrentStackTrace());
            }
            // ToDo create convenience method isInValidState or areAllWidgetValid
            if (!validateWidgets()) {
                if (log.isDebugEnabled()) {
                    log.debug("selectionVetoCheck: One or more widgets are invalid. Informing user.");
                }
                final int answer = askUser();
                if (log.isDebugEnabled()) {
                    log.debug("answer: " + answer);
                }
                if (answer == JOptionPane.YES_OPTION) {
                    if (log.isDebugEnabled()) {
                        log.debug("selectionVetoCheck: User wants to cancel changes.");
                    }
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("selectionVetoCheck: User wants to correct validation, throwing veto exception.");
                    }
                    throw new VetoException();
                }
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("selectionVetoCheck: No veto all Widgets are valid.");
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
        if (log.isDebugEnabled()) {
            log.debug("setVetoCheckto: " + vetoCheckEnabled, new CurrentStackTrace());
        }
        this.vetoCheckEnabled = vetoCheckEnabled;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static UnterhaltLeuchte getDefaultUnterhaltLeuchte() {
        return defaultUnterhaltLeuchte;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  defaultUnterhaltLeuchte  DOCUMENT ME!
     */
    public static void setDefaultUnterhaltLeuchte(final UnterhaltLeuchte defaultUnterhaltLeuchte) {
        BelisBroker.defaultUnterhaltLeuchte = defaultUnterhaltLeuchte;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static UnterhaltMast getDefaultUnterhaltMast() {
        return defaultUnterhaltMast;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  defaultUnterhaltMast  DOCUMENT ME!
     */
    public static void setDefaultUnterhaltMast(final UnterhaltMast defaultUnterhaltMast) {
        BelisBroker.defaultUnterhaltMast = defaultUnterhaltMast;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static Doppelkommando getDefaultDoppelkommando1() {
        return defaultDoppelkommando1;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  defaultDoppelkommando1  DOCUMENT ME!
     */
    public static void setDefaultDoppelkommando1(final Doppelkommando defaultDoppelkommando1) {
        BelisBroker.defaultDoppelkommando1 = defaultDoppelkommando1;
    }
//    @Override
//    public void fireListSelectionVeto() throws EventVetoedException {
//        log.debug("VetoSelection");
//    }
//        return newObjects;
//    }
//
//    public void setNewObjects(Set newObjects) {
//        this.newObjects = newObjects;
//        propertyChangeSupport.firePropertyChange(PROP_NEW_OBJECTS, null, newObjects);
//    }
}
