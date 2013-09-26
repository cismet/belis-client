/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * HitWidget.java
 *
 * Created on 17. März 2009, 15:35
 */
package de.cismet.belis.gui.widget;

import org.apache.commons.collections.comparators.ReverseComparator;
import org.apache.log4j.Logger;

import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.ComponentAdapter;
import org.jdesktop.swingx.decorator.HighlightPredicate;
import org.jdesktop.swingx.decorator.Highlighter;
import org.jdesktop.swingx.treetable.AbstractMutableTreeTableNode;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;

import org.jdom.Element;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import de.cismet.belis.broker.BelisBroker;
import de.cismet.belis.broker.CidsBroker;

import de.cismet.belis.commons.constants.BelisMetaClassConstants;

import de.cismet.belis.gui.renderer.WorkbenchTreeTableRenderer;

import de.cismet.belis.todo.CustomMutableTreeTableNode;
import de.cismet.belis.todo.CustomTreeTableModel;

import de.cismet.belis.util.BelisIcons;

import de.cismet.belisEE.util.EntityComparator;
import de.cismet.belisEE.util.LeuchteComparator;

import de.cismet.cids.custom.beans.belis.AbzweigdoseCustomBean;
import de.cismet.cids.custom.beans.belis.ArbeitsauftragCustomBean;
import de.cismet.cids.custom.beans.belis.ArbeitsprotokollCustomBean;
import de.cismet.cids.custom.beans.belis.LeitungCustomBean;
import de.cismet.cids.custom.beans.belis.MauerlascheCustomBean;
import de.cismet.cids.custom.beans.belis.SchaltstelleCustomBean;
import de.cismet.cids.custom.beans.belis.TdtaLeuchtenCustomBean;
import de.cismet.cids.custom.beans.belis.TdtaStandortMastCustomBean;
import de.cismet.cids.custom.beans.belis.VeranlassungCustomBean;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cismap.commons.features.AbstractNewFeature;
import de.cismet.cismap.commons.features.Feature;
import de.cismet.cismap.commons.features.FeatureCollection;
import de.cismet.cismap.commons.features.FeatureCollectionEvent;
import de.cismet.cismap.commons.features.FeatureCollectionListener;
import de.cismet.cismap.commons.features.PureNewFeature;
import de.cismet.cismap.commons.features.StyledFeature;
import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.CreateGeometryListener;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.CreateNewGeometryListener;

import de.cismet.commons.architecture.interfaces.FeatureSelectionChangedListener;
import de.cismet.commons.architecture.validation.Validatable;

import de.cismet.commons.server.entity.GeoBaseEntity;

import de.cismet.tools.CurrentStackTrace;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
@org.openide.util.lookup.ServiceProvider(service = BelisWidget.class)
public class WorkbenchWidget extends BelisWidget implements TreeSelectionListener,
    FeatureSelectionChangedListener,
    PropertyChangeListener,
    FeatureCollectionListener {

    //~ Static fields/initializers ---------------------------------------------

    public static final String PROP_SEARCH_RESULTS = "searchResults";

    // ToDo idea insert the results seperated --> user don't have to wait to long
    private static final Logger LOG = org.apache.log4j.Logger.getLogger(WorkbenchWidget.class);
    public static final String PROP_SELECTEDTREENODES = "selectedTreeNodes";
    // private final DefaultMutableTreeTableNode root = new DefaultMutableTreeTableNode(null, true);
// private final DefaultMutableTreeTableNode searchResults = new DefaultMutableTreeTableNode(null, true);
// private final DefaultMutableTreeTableNode processedObjects = new DefaultMutableTreeTableNode(null, true);
    public static final String PROP_TREE_TABLE_MODEL = "treeTableModel";
    private static final String TEMP_FEATURE_CREATED_MODE = "temporayFeatureInMapCreated";
    public static final String BELIS_CREATE_MODE = "BELIS_CREATE_MODE";
    public static final int VIEW_MODE = 0;
    public static final int CREATE_MODE = 1;
    public static final int EDIT_MODE = 2;
    public static final int REFRESH_SEARCH_RESULTS = 0;
    // ToDo disabled Functionality 04.05.2009
    // public static final int REFRESH_PROCESSED_OBJECTS = 1;
    public static final int REFRESH_NEW_OBJECTS = 2;
    public static final int REFRESH_ALL = 4;
    public static final int CLEAR_NEW_OBJECTS = 3;
    public static final int MOVE_NEW_TO_SAVED_OBJECTS = 5;

    //~ Instance fields --------------------------------------------------------

    BindingGroup bindingGroup2 = new BindingGroup();

    private boolean ignoreFeatureSelection = false;
//    protected TreePath[] selectedTreeNodes = null;
//    public static final String PROP_SELECTEDTREENODE = "selectedTreeNodes";
    private Collection<TreePath> selectedTreeNodes = null;
    private final CustomMutableTreeTableNode rootNode = new CustomMutableTreeTableNode(null, true);
    private final CustomMutableTreeTableNode searchResultsNode = new CustomMutableTreeTableNode(null, true);
    private final CustomMutableTreeTableNode newObjectsNode = new CustomMutableTreeTableNode(null, true);
    private CustomTreeTableModel treeTableModel = null;
    private HashMap<TdtaLeuchtenCustomBean, TdtaStandortMastCustomBean> leuchteToVirtualStandortMap = new HashMap();
    private HashMap<TdtaStandortMastCustomBean, ArrayList<TdtaLeuchtenCustomBean>> leuchtenRemovedFromMastMap =
        new HashMap<TdtaStandortMastCustomBean, ArrayList<TdtaLeuchtenCustomBean>>();
    private JButton btnAttachMode = new JButton();
    private boolean isAlreadyDisabled = false;
    private boolean tmpProcessStarted = false;
    private boolean isSwitchTriggerEnabled = false;
    private Collection currentSearchResults = new TreeSet(new ReverseComparator(
                new EntityComparator(new ReverseComparator(new LeuchteComparator()))));
//    public Set getCurrentSearchResults() {
//        return currentSearchResults;
//    }
//
//    public void setCurrentSearchResults(Set currentSearchResults) {
//        this.currentSearchResults = currentSearchResults;
//        firePropertyChange(PROP_CURRENT_SEARCH_RESULTS, null, currentSearchResults);
//    }
    // There is no need to hold the objects in an extra set only convenience
    private Set newObjects = new TreeSet(new ReverseComparator(
                new EntityComparator(new ReverseComparator(new LeuchteComparator()))));
    // private Set savedObjects = new HashSet();
    // private Set processedObjects = new HashSet();
    private final Set removedObjects = new TreeSet(new ReverseComparator(
                new EntityComparator(new ReverseComparator(new LeuchteComparator()))));
    private int currentMode = 0;
    private Feature newlyAddedFeature = null;
    // End of variables declaration
    private boolean isSelectedOverMap = false;
    // ToDo Workaround because there will be exceptions if a selectedNode is moved. Should normaly not be done in the
    // model
    private TreePath selectedElement = null;
    private Feature selectedFeature = null;
    private Set searchResults = null;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXTreeTable jttHitTable;
    private javax.swing.JPanel panMain;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form HitWidget.
     */
    public WorkbenchWidget() {
        setWidgetName("Arbeitsbereich");
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void setBroker(final BelisBroker broker) {
        super.setBroker(broker);

        initComponents();
        broker.getMappingComponent()
                .getInputEventListener()
                .put(
                    BELIS_CREATE_MODE,
                    new CreateNewBelisObjectListener(broker.getMappingComponent(), PureNewFeature.class));
        broker.getMappingComponent()
                .putCursor(BELIS_CREATE_MODE, broker.getMappingComponent().getCursor(MappingComponent.ZOOM));
        final FeatureCollection collection = broker.getMappingComponent().getFeatureCollection();
        if (collection != null) {
            collection.addFeatureCollectionListener(this);
        } else {
            LOG.warn("No feature Collection to set Listener on.");
        }
        jttHitTable.getTreeSelectionModel().addTreeSelectionListener(this);

        // DefaultTreeTableModel vModel = new DefaultTreeTableModel(root);
        // jttHitTable.setTreeTableModel(vModel);
        jttHitTable.setEditable(false); //
        jttHitTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jttHitTable.setTreeCellRenderer(new WorkbenchTreeTableRenderer());
        treeTableModel = new CustomTreeTableModel(getBroker(), rootNode, searchResultsNode, newObjectsNode);
        jttHitTable.setTreeTableModel(treeTableModel);

        broker.decorateWithAlternateHighlighting(jttHitTable);
        // broker.decorateWithNoGeometryHighlighter(jttHitTable);
        final HighlightPredicate noGeometryPredicate = new HighlightPredicate() {

                @Override
                public boolean isHighlighted(final Component renderer, final ComponentAdapter componentAdapter) {
                    // int displayedIndex = componentAdapter.row;
                    // nt modelIndex = ((JXTreeTable) ttable).getFilters().convertRowIndexToModel(displayedIndex);
                    try {
                        final Object userObj =
                            ((AbstractMutableTreeTableNode)jttHitTable.getPathForRow(componentAdapter.row)
                                        .getLastPathComponent()).getUserObject();
                        if (userObj != null) {
                            if (userObj instanceof TdtaLeuchtenCustomBean) {
                                final TdtaStandortMastCustomBean virtualStandort = leuchteToVirtualStandortMap.get(
                                        (TdtaLeuchtenCustomBean)userObj);

                                if (virtualStandort != null) {
                                    return virtualStandort.getGeometry() == null;
                                }
                            } else if (userObj instanceof VeranlassungCustomBean) {
                                final VeranlassungCustomBean veranlassungCustomBean = (VeranlassungCustomBean)userObj;
                                return veranlassungCustomBean.getAr_abzweigdosen().isEmpty()
                                            && veranlassungCustomBean.getAr_leitungen()
                                            .isEmpty()
                                            && veranlassungCustomBean.getAr_leuchten()
                                            .isEmpty()
                                            && veranlassungCustomBean.getAr_mauerlaschen()
                                            .isEmpty()
                                            && veranlassungCustomBean.getAr_schaltstellen()
                                            .isEmpty()
                                            && veranlassungCustomBean.getAr_standorte()
                                            .isEmpty()
                                            && (veranlassungCustomBean.getGeometrie() == null);
                            } else if (userObj instanceof GeoBaseEntity) {
                                return ((GeoBaseEntity)userObj).getGeometry() == null;
                            }
                        }
                    } catch (Exception ex) {
                        LOG.error("Exception in Highlighter: ", ex);
                    }
                    return false;
                        // ReBe r = model.get//tableModel.getReBeAtRow(modelIndex);
                        // return r != null && r.getGeometry() == null;
                }
            };

        final Highlighter noGeometryHighlighter = new ColorHighlighter(noGeometryPredicate, broker.red, null);
        jttHitTable.addHighlighter(noGeometryHighlighter);
        /*ToDo workaround because I can not change JXTreeTable selection models (Tree, table part does work)
         * ToDo disable logging because there are a lot of events
         */
        Toolkit.getDefaultToolkit()
                .addAWTEventListener(new AWTEventListener() {

                        @Override
                        public void eventDispatched(final AWTEvent event) {
                            if (broker.isVetoCheckEnabled()
                                && (broker.isInCreateMode() || broker.isInEditMode())
                                && (((MouseEvent)event).getSource() instanceof JXTreeTable)
                                && ((((MouseEvent)event).getID() == MouseEvent.MOUSE_PRESSED)
                                    || (((MouseEvent)event).getID() == MouseEvent.MOUSE_CLICKED)
                                    || (((MouseEvent)event).getID() == MouseEvent.MOUSE_RELEASED)
                                    || (((MouseEvent)event).getID() == MouseEvent.MOUSE_DRAGGED))) {
                                if (LOG.isDebugEnabled()) {
                                    LOG.debug("eventDispatched: Mouse event intercepted from JXTreeTable in edit mode");
                                }
                                final int clickedRow = jttHitTable.rowAtPoint(((MouseEvent)event).getPoint());
                                final int selectedRow = jttHitTable.getSelectedRow();
                                if (LOG.isDebugEnabled()) {
                                    LOG.debug("eventDispatched: clickedRow: " + clickedRow);
                                    LOG.debug("eventDispatched: selectedRow: " + selectedRow);
                                }
                                if (clickedRow != selectedRow) {
                                    if (!broker.validateWidgets()) {
                                        if (LOG.isDebugEnabled()) {
                                            LOG.debug(
                                                "eventDispatched: One or more widgets are invalid. Informing user.");
                                        }
                                        final int anwser = broker.askUser();
                                        if (anwser == JOptionPane.YES_OPTION) {
                                            if (LOG.isDebugEnabled()) {
                                                LOG.debug("User wants to cancel changes.");
                                            }
                                        } else {
                                            if (LOG.isDebugEnabled()) {
                                                LOG.debug("User wants to correct validation, consuming event.");
                                            }
                                            ((MouseEvent)event).consume();
                                        }
                                    } else {
                                        if (LOG.isDebugEnabled()) {
                                            LOG.debug("eventDispatched: Not consuming event. All Widgets are valid");
                                        }
                                    }
                                } else {
                                    if (LOG.isDebugEnabled()) {
                                        LOG.debug(
                                            "eventDispatched: Not consuming event. No Selection changed would be happen.");
                                    }
                                }
                            }
                        }
                    },
                    AWTEvent.MOUSE_EVENT_MASK
                    | AWTEvent.MOUSE_MOTION_EVENT_MASK);
        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {

                @Override
                public void eventDispatched(final AWTEvent event) {
                    if (broker.isVetoCheckEnabled()
                                && (broker.isInCreateMode() || broker.isInEditMode())
                                && (((KeyEvent)event).getSource() instanceof JXTreeTable)
                                && ((((KeyEvent)event).getID() == KeyEvent.KEY_PRESSED)
                                    || (((KeyEvent)event).getID() == KeyEvent.KEY_RELEASED)
                                    || (((KeyEvent)event).getID() == KeyEvent.KEY_TYPED))) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("eventDispatched: Key event intercepted from JXTreeTable in edit mode");
                        }
                        final int keyCode = ((KeyEvent)event).getKeyCode();
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("eventDispatched: keycode: " + keyCode);
                        }
                        if ((keyCode == KeyEvent.VK_DOWN) || (keyCode == KeyEvent.VK_UP)) {
                            if (!broker.validateWidgets()) {
                                if (LOG.isDebugEnabled()) {
                                    LOG.debug("eventDispatched: One or more widgets are invalid. Informing user.");
                                }
                                final int anwser = broker.askUser();
                                if (anwser == JOptionPane.YES_OPTION) {
                                    if (LOG.isDebugEnabled()) {
                                        LOG.debug("User wants to cancel changes.");
                                    }
                                } else {
                                    if (LOG.isDebugEnabled()) {
                                        LOG.debug("User wants to correct validation, consuming event.");
                                    }
                                    ((KeyEvent)event).consume();
                                }
                            } else {
                                if (LOG.isDebugEnabled()) {
                                    LOG.debug("eventDispatched: Not consuming event. All Widgets are valid");
                                }
                            }
                        } else {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("eventDispatched: neither up nor down arrow key");
                            }
                        }
                    }
                }
            }, AWTEvent.KEY_EVENT_MASK);
        // jttHitTable.setTransferHandler(new WorkbenchTransferHandler());
    }

    /**
     * ToDo not necessary.
     *
     * @return  DOCUMENT ME!
     */
    public CustomTreeTableModel getTreeTableModel() {
        return treeTableModel;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  treeTableModel  DOCUMENT ME!
     */
    public void setTreeTableModel(final CustomTreeTableModel treeTableModel) {
        this.treeTableModel = treeTableModel;
        firePropertyChange(PROP_TREE_TABLE_MODEL, null, treeTableModel);
    }
//    private DefaultTreeTableModel model = new DefaultTreeTableModel();
//    public static final String PROP_MODEL = "model";
//
//    public DefaultTreeTableModel getModel() {
//        return model;
//    }
//
//    public void setModel(DefaultTreeTableModel model) {
//        this.model = model;
//
//        propertyChangeSupport.firePropertyChange(PROP_MODEL, null, model);
//    }

    /**
     * Get the value of selectedTreeNode.
     *
     * @return  the value of selectedTreeNode
     */
    public Collection<TreePath> getSelectedTreeNodes() {
        return selectedTreeNodes;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public TreePath getSelectedTreeNode() {
        final Collection<TreePath> selectedTreeNodes = getSelectedTreeNodes();
        final TreePath selectedTreeNode;
        if ((selectedTreeNodes != null) && (selectedTreeNodes.size() == 1)) {
            selectedTreeNode = selectedTreeNodes.iterator().next();
        } else {
            selectedTreeNode = null;
        }
        return selectedTreeNode;
    }

    /**
     * Set the value of selectedTreeNode.
     *
     * @param  selectedTreeNodes  new value of selectedTreeNode
     */
    public void setSelectedTreeNodes(final Collection<TreePath> selectedTreeNodes) {
        final Collection<TreePath> oldSelectedTreeNodes = this.selectedTreeNodes;
        if ((this.selectedTreeNodes != null) && (selectedTreeNodes != null)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("both treenodes != null");
            }
            if (this.selectedTreeNodes.equals(selectedTreeNodes)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("treebnodes equals");
                }
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("treebnodes not equals");
                }
            }
        } else {
            if ((this.selectedTreeNodes == null) && (selectedTreeNodes == null)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("both treenodes are null");
                }
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("one of the treenodes is null");
                }
            }
        }
        this.selectedTreeNodes = selectedTreeNodes;
        firePropertyChange(PROP_SELECTEDTREENODES, oldSelectedTreeNodes, selectedTreeNodes);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public int getCurrentMode() {
        return currentMode;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  currentMode  DOCUMENT ME!
     */
    public void setCurrentMode(final int currentMode) {
        this.currentMode = currentMode;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  nodeToSelect  DOCUMENT ME!
     */
    public void selectNode(final CustomMutableTreeTableNode nodeToSelect) {
        if (nodeToSelect != null) {
            final TreePath pathToRoot = new TreePath(treeTableModel.getPathToRoot(nodeToSelect));
            if (LOG.isDebugEnabled()) {
                LOG.debug("selecting: " + pathToRoot);
            }
            jttHitTable.getTreeSelectionModel().setSelectionPath(pathToRoot);
        }
        // DefaultListSelectionModel
    }

    @Override
    public void setWidgetEditable(final boolean isEditable) {
        super.setWidgetEditable(isEditable);
        try {
            treeTableModel.removeNodeFromParent(newObjectsNode);
        } catch (final Exception exception) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("treeTableModel.removeNodeFromParent(newObjectsNode);", exception);
            }
        }
        try {
            treeTableModel.removeNodeFromParent(searchResultsNode);
        } catch (final Exception exception) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("treeTableModel.removeNodeFromParent(searchResultsNode);", exception);
            }
        }

        if (isEditable) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Setting " + getWidgetName() + "editable");
            }
            configureMapModeAccordingToSelection();
            if (getBroker().isInCreateMode()) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Configuring Workbench for CreateMode. Removing search hits.");
                }
                setCurrentMode(CREATE_MODE);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("removing node:" + searchResultsNode);
                }
                treeTableModel.insertNodeIntoAsLastChild(newObjectsNode, rootNode);
                jttHitTable.expandPath(new TreePath(treeTableModel.getPathToRoot(newObjectsNode)));
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Configuring Workbench for EditMode");
                }
                setCurrentMode(EDIT_MODE);
            }
            treeTableModel.insertNodeIntoAsLastChild(searchResultsNode, rootNode);
            jttHitTable.expandPath(new TreePath(treeTableModel.getPathToRoot(searchResultsNode)));
        } else {
            treeTableModel.insertNodeIntoAsLastChild(searchResultsNode, rootNode);
            if (getCurrentMode() == CREATE_MODE) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Was in create mode switching to view mode.");
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("removing node:" + newObjectsNode);
                }
                jttHitTable.expandPath(new TreePath(treeTableModel.getPathToRoot(searchResultsNode)));
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Was in edit mode switching to view mode.");
                    // treeTableModel.insertNodeIntoAsLastChild(newObjectsNode, rootNode);
                }
            }

            setCurrentMode(VIEW_MODE);
            refreshTreeArtifacts(REFRESH_SEARCH_RESULTS);
        }
    }

    @Override
    public void masterConfigure(final Element parent) {
        addButtonToMapWidget();
        // //binding
        // Notification in piccolo works over callback methods simply declare a method and recieve the notification
        // no overwriding
        getBroker().getMappingComponent().addPropertyChangeListener(this);
        if (LOG.isDebugEnabled()) {
//        PNotificationCenter.defaultCenter().addListener(this,
//                null,
//                CreateGeometryListener.GEOMETRY_CREATED_NOTIFICATION,
//                null);
            LOG.debug("Configure binding TreeTableModel <--> SearchResults");
        }
        final org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                getBroker(),
                org.jdesktop.beansbinding.ELProperty.create("${currentSearchResults}"),
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentSearchResults}"));
        // binding.setConverter(new SearchResultConverter());
        bindingGroup2.addBinding(binding);
//        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, getBroker(), org.jdesktop.beansbinding.ELProperty.create("${newObjects}"), jttHitTable, org.jdesktop.beansbinding.ELProperty.create("${treeTableModel.newObjects}"));
//        bindingGroup2.addBinding(binding);
        bindingGroup2.bind();
    }

//    public void temporayFeatureInMapCreated(PNotification notification) {
//        if (EventQueue.isDispatchThread()) {
//            log.debug("is in EDT");
//        }
//        log.debug("TMP FeatureInMapCreated created: " + notification.getObject());
//        if (notification.getObject() != null && notification.getObject() instanceof PureNewFeature && checkConstraintForMapModeSwitch()) {
//            log.debug("all prequisites fulfied for adding newly created geometry");
//            try {
//                tmpProcessStarted = true;
//                broker.addFeatureSelectionChangeIgnore(this);
//                ((BelisBroker) getBroker()).setVetoCheckEnabled(false);
//                PureNewFeature newFeature = (PureNewFeature) notification.getObject();
//                newFeature.setPrimaryAnnotationVisible(false);
//                broker.getMappingComponent().reconsiderFeature(newFeature);
//                final Object tmpObject = ((CustomMutableTreeTableNode) getSelectedTreeNode().getLastPathComponent()).getUserObject();
//                StyledFeature currentSelectedFeature = null;
//                if (tmpObject instanceof StyledFeature) {
//                    currentSelectedFeature = (StyledFeature) tmpObject;
//                } else if (tmpObject instanceof Leuchte) {
//                    currentSelectedFeature = leuchteToVirtualStandortMap.get((Leuchte) tmpObject);
//                    if (currentSelectedFeature == null) {
//                        log.warn("Leuchte has no virtual standort.");
//                    }
//                }
//                currentSelectedFeature.setGeometry(newFeature.getGeometry());
//                broker.getMappingComponent().getFeatureCollection().removeFeature(newFeature);
//                newlyAddedFeature = currentSelectedFeature;
//                broker.getMappingComponent().getFeatureCollection().addFeature(currentSelectedFeature);
//                btnAttachMode.setEnabled(false);
//                //broker.getMappingComponent().setInteractionMode(MappingComponent.SELECT);
//                btnAttachMode.setEnabled(false);
//                broker.getMapWidget().setToLastInteractionMode();
//                broker.getMappingComponent().getFeatureCollection().select(currentSelectedFeature);
//            } finally {
//                ((BelisBroker) getBroker()).setVetoCheckEnabled(true);
//                broker.removeFeatureSelectionChangeIgnore(this);
////                isSwitchTriggerEnabled = true;
////                if (!tmpProcessStarted) {
////                    log.debug("tmpProcess finished switching back");
////                    //ToDo why is the node multiple times selected I think
////                    //maybe there is a error earlier and this code could be simplified
////                    switchListener();
////                } else {
////                    log.debug("tmpProcess not finished --> switch will be done by listener");
////                }
//            }
//        } else {
//            log.debug("Not all prequisites fulfied for adding newly created geometry");
//        }
//    }
    @Override
    public void updateUIPropertyChange() {
        super.updateUIPropertyChange();
        jttHitTable.repaint();
    }
    /**
     * private void tryToChangeVetoCheckEnabled(boolean isEnabled){ // if(!isEnabled){ // log.debug("disable veto
     * mode"); // if(!broker.isVetoCheckEnabled()){ // log.debug("vetomode already disabled"); //
     * if(!isAlreadyDisabled){ // isAlreadyDisabled=true; // } // //only for savety //
     * //broker.setVetoCheckEnabled(false); // } else { // log.debug("disabling vetomode"); // //ToDo it would be cool
     * to make belis widegts for the core widgets so no dump casting. // broker.setVetoCheckEnabled(false); // } // }
     * else { // if(isAlreadyDisabled){ // log.info("not enabling veto check because there was an action before which
     * disabled it"); // } else { // log.debug("reanabling vetocheck and reseting flag"); //
     * broker.setVetoCheckEnabled(true); // isAlreadyDisabled=false; // } // } broker.setVetoCheckEnabled(isEnabled); }
     *
     * @return  DOCUMENT ME!
     */
    public boolean checkConstraintForMapModeSwitch() {
        TdtaStandortMastCustomBean virtualStandort = null;

        if (getBroker().isInEditMode()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Application is in Editmode");
            }
            if ((getSelectedTreeNode() != null) && (getSelectedTreeNode().getLastPathComponent() != null)) {
                if (getSelectedTreeNode().getLastPathComponent() instanceof CustomMutableTreeTableNode) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Chosen object is CustomMutableTreeTableNode");
                    }
                    final Object userObject = ((CustomMutableTreeTableNode)getSelectedTreeNode().getLastPathComponent())
                                .getUserObject();
                    if (userObject != null) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("userObject != null");
                        }
                        if ((userObject instanceof TdtaLeuchtenCustomBean)
                                    && ((virtualStandort = leuchteToVirtualStandortMap.get(
                                                    (TdtaLeuchtenCustomBean)userObject))
                                        != null)
                                    && (virtualStandort instanceof TdtaStandortMastCustomBean)
                                    && !virtualStandort.isStandortMast()) {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("UserObject is Leuchte with virtual standort");
                            }
                            if (virtualStandort.getGeometry() == null) {
                                if (LOG.isDebugEnabled()) {
                                    LOG.debug(
                                        "Geometry of virtual standort is null, swithcing mode of Mapping Compoenent");
                                }
                                return true;
                            } else {
                                if (LOG.isDebugEnabled()) {
                                    LOG.debug("Geometry of virtual standort != null nothing to do");
                                }
                            }
                        } else if (userObject instanceof GeoBaseEntity) {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("UserObject is instance of GeoBaseEntity");
                            }
                            if (((GeoBaseEntity)userObject).getGeometry() == null) {
                                if (LOG.isDebugEnabled()) {
                                    LOG.debug("Geometry is null swicht mode of Mapping Component");
                                }
                                return true;
                                    // broker.get.removeMainGroupSelection();
                            } else {
                                if (LOG.isDebugEnabled()) {
                                    LOG.debug("Geometry != null nothing to do");
                                }
                            }
                        } else {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("UserObject is not instance of GeoBaseEntity or Leuchte.");
                            }
                        }
                    } else {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("userObject == null");
                        }
                    }
                } else {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("No instance of CustomMutableTreeNode");
                    }
                }
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("LastPathComponent == null");
                }
            }
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Application is not in Editmode");
            }
        }
        return false;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   path  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static Object getUserObjectForTreePath(final TreePath path) {
        try {
            return ((DefaultMutableTreeTableNode)path.getLastPathComponent()).getUserObject();
        } catch (Exception ex) {
            LOG.warn("Error while accessing user object. ", ex);
            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   node  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static Object getUserObjectForTreeNode(final Object node) {
        try {
            return ((CustomMutableTreeTableNode)node).getUserObject();
        } catch (Exception ex) {
            LOG.warn("Error while accessing user object. ", ex);
            return null;
        }
    }
    /**
     * ToDo setSelected dosen't work.
     */
    private void addButtonToMapWidget() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("adding attachMode button to MapWidget");
        }

        btnAttachMode.setIcon(BelisIcons.icoAttachGeometry16); // NOI18N
        btnAttachMode.setSelectedIcon(BelisIcons.icoAttachGeometrySelected16);
        btnAttachMode.setToolTipText("Georeferenziere Objekt");
        btnAttachMode.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 3, 1, 3));
        btnAttachMode.setIconTextGap(8);
        btnAttachMode.setMargin(new java.awt.Insets(10, 14, 10, 14));
        btnAttachMode.setEnabled(false);
        btnAttachMode.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(final ActionEvent e) {
                    configureMapModeAccordingToSelection();
                }
            });
        getBroker().getMapWidget().addCustomButton(btnAttachMode);
    }

    /**
     * DOCUMENT ME!
     */
    private void configureMapModeAccordingToSelection() {
        if (checkConstraintForMapModeSwitch()) {
            try {
                getBroker().setVetoCheckEnabled(false);
                getBroker().addFeatureSelectionChangeIgnore(this);
                ignoreFeatureSelection = true;
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Constraints for ModeSwitch fullfied --> switching.", new CurrentStackTrace());
                }
                btnAttachMode.setEnabled(true);
                getBroker().getMapWidget().setLastMapMode(getBroker().getMapWidget().getCurrentMapMode());
                getBroker().getMapWidget().removeMainGroupSelection();
                getBroker().getMappingComponent().setInteractionMode(BELIS_CREATE_MODE);
                btnAttachMode.setSelected(true);
                final Object userObject = ((CustomMutableTreeTableNode)getSelectedTreeNode().getLastPathComponent())
                            .getUserObject();
                if (userObject instanceof TdtaLeuchtenCustomBean) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Instance is Leuchte --> geometry == point");
                    }
                    ((CreateGeometryListener)getBroker().getMappingComponent().getInputListener(BELIS_CREATE_MODE))
                            .setMode(
                                CreateGeometryListener.POINT);
                } else if (userObject instanceof TdtaStandortMastCustomBean) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Instance is Standort --> geometry == point");
                        LOG.debug(userObject);
                    }
                    ((CreateGeometryListener)getBroker().getMappingComponent().getInputListener(BELIS_CREATE_MODE))
                            .setMode(
                                CreateGeometryListener.POINT);
                } else if (userObject instanceof MauerlascheCustomBean) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Instance is Mauerlasche --> geometry == point");
                        LOG.debug(userObject);
                    }
                    ((CreateGeometryListener)getBroker().getMappingComponent().getInputListener(BELIS_CREATE_MODE))
                            .setMode(
                                CreateGeometryListener.POINT);
                } else if (userObject instanceof SchaltstelleCustomBean) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Instance is Schaltstelle --> geometry == point");
                        LOG.debug(userObject);
                    }
                    ((CreateGeometryListener)getBroker().getMappingComponent().getInputListener(BELIS_CREATE_MODE))
                            .setMode(
                                CreateGeometryListener.POINT);
                } else if (userObject instanceof LeitungCustomBean) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Instance is Leitung --> geometry == line");
                        LOG.debug(userObject);
                    }
                    ((CreateGeometryListener)getBroker().getMappingComponent().getInputListener(BELIS_CREATE_MODE))
                            .setMode(
                                CreateGeometryListener.LINESTRING);
                } else if (userObject instanceof AbzweigdoseCustomBean) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Instance is Abzweigdose --> geometry == point");
                        LOG.debug(userObject);
                    }
                    ((CreateGeometryListener)getBroker().getMappingComponent().getInputListener(BELIS_CREATE_MODE))
                            .setMode(
                                CreateGeometryListener.POINT);
                }
            } catch (Exception ex) {
                LOG.error("Error while configuring map. Setting map back: ", ex);
                getBroker().setVetoCheckEnabled(true);
            } finally {
                getBroker().setVetoCheckEnabled(true);
                getBroker().removeFeatureSelectionChangeIgnore(this);
                ignoreFeatureSelection = false;
            }
        } else {
            try {
                getBroker().setVetoCheckEnabled(false);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Constraints for ModeSwitch not fullfied");
                }
                if (getBroker().getMappingComponent().getInteractionMode().equals(BELIS_CREATE_MODE)) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Is already in: " + BELIS_CREATE_MODE + " mode. Switching back");
                    }
                    // ToDo save last mode; check if the map is in this mode --> switch to select TODO maybe an
                    // intelligent mode switching policy
                    btnAttachMode.setEnabled(false);
                    if ((getBroker().getMappingComponent().getInteractionMode() != null)
                                && getBroker().getMappingComponent().getInteractionMode().equals(BELIS_CREATE_MODE)) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug(
                                "False map mode setting is at the moment temp feature create. Mode will be set to lastmode: "
                                        + getBroker().getMapWidget().getLastMapMode());
                        }
                        getBroker().getMapWidget().setToLastInteractionMode();
                        // broker.getMappingComponent().setInteractionMode(MappingComponent.SELECT);
                    }
                }
            } finally {
                getBroker().setVetoCheckEnabled(true);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        panMain = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jttHitTable = new org.jdesktop.swingx.JXTreeTable();

        jScrollPane1.setViewportView(jttHitTable);

        final javax.swing.GroupLayout panMainLayout = new javax.swing.GroupLayout(panMain);
        panMain.setLayout(panMainLayout);
        panMainLayout.setHorizontalGroup(
            panMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                panMainLayout.createSequentialGroup().addContainerGap().addComponent(
                    jScrollPane1,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    376,
                    Short.MAX_VALUE).addContainerGap()));
        panMainLayout.setVerticalGroup(
            panMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                panMainLayout.createSequentialGroup().addContainerGap().addComponent(
                    jScrollPane1,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    210,
                    Short.MAX_VALUE).addContainerGap()));

        final javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                panMain,
                javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.DEFAULT_SIZE,
                Short.MAX_VALUE));
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                panMain,
                javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.DEFAULT_SIZE,
                Short.MAX_VALUE));
    } // </editor-fold>//GEN-END:initComponents

    // ToDo if application switches in Edit Mode it must be checked if there is a entry selected and the mode must be
    // switched
    @Override
    public void valueChanged(final TreeSelectionEvent e) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("JTreeTable selection changend");
        }
        SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    try {
                        try {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug(
                                    "DetailWidget is valid: "
                                            + (Validatable.VALID == getBroker().getDetailWidget().getStatus()));
                            }
                        } catch (Exception ex) {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("Error while checking validation state of detailWidget: ", ex);
                            }
                        }

                        final Collection<TreePath> paths = new ArrayList<TreePath>();
                        for (int i = 0; i < jttHitTable.getSelectedRowCount(); i++) {
                            paths.add(jttHitTable.getPathForRow(jttHitTable.getSelectedRows()[i]));
                        }

                        if (!paths.isEmpty()) {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("Path is added");
                            }
                            setSelectedTreeNodes(paths);
                            configureMapModeAccordingToSelection();

                            if (isSelectedOverMap) {
                                if (LOG.isDebugEnabled()) {
                                    LOG.debug("feature was selected over map. No need to select it in map.");
                                }
                            } else {
                                getBroker().setVetoCheckEnabled(false);
                                if (LOG.isDebugEnabled()) {
                                    LOG.debug("feature was selected over table. Going to select feature in map.");
                                }

                                // ToDo method for extraction bad performance
                                final Collection<Feature> featuresToSelect = new ArrayList();
                                for (final TreePath path : paths) {
                                    final Object currentUserObject = getUserObjectForTreePath(path);
                                    if ((currentUserObject != null) && (currentUserObject instanceof StyledFeature)
                                                && (((StyledFeature)currentUserObject).getGeometry() != null)) {
                                        if (LOG.isDebugEnabled()) {
                                            LOG.debug(
                                                "UserObject != null and instance of StyledFeature and geometry available --> select Feature");
                                        }
                                        featuresToSelect.add((StyledFeature)currentUserObject);
                                    } else if (isParentNodeMast(path.getLastPathComponent())) {
                                        if (LOG.isDebugEnabled()) {
                                            LOG.debug("Leuchte from mast is selected in table.");
                                        }
                                        final TdtaStandortMastCustomBean parentMast = getParentMast(
                                                path.getLastPathComponent());
                                        if ((getBroker().getMappingComponent().getFeatureCollection()
                                                        .getSelectedFeatures() != null)
                                                    && (getBroker().getMappingComponent().getFeatureCollection()
                                                        .getSelectedFeatures().size() == 1)
                                                    && getBroker().getMappingComponent().getFeatureCollection()
                                                    .getSelectedFeatures().contains(parentMast)) {
                                            if (LOG.isDebugEnabled()) {
                                                LOG.debug("Doing nothing mast is already selected");
                                            }
                                        } else {
                                            if (LOG.isDebugEnabled()) {
                                                LOG.debug("Selecting Mast in map.");
                                            }
                                            featuresToSelect.add((StyledFeature)parentMast);
                                        }
                                    } else if (isNodeHaengeLeuchte(path.getLastPathComponent())) {
                                        if (LOG.isDebugEnabled()) {
                                            LOG.debug(
                                                "current selected node is haengeleuchte. Selecting corresponding standort in map: ");
                                        }
                                        featuresToSelect.add(leuchteToVirtualStandortMap.get(currentUserObject));
                                    } else if (currentUserObject instanceof VeranlassungCustomBean) {
                                        final VeranlassungCustomBean veranlassungCustomBean = (VeranlassungCustomBean)
                                            currentUserObject;
                                        featuresToSelect.addAll(veranlassungCustomBean.getAr_abzweigdosen());
                                        featuresToSelect.addAll(veranlassungCustomBean.getAr_leitungen());
                                        featuresToSelect.addAll(veranlassungCustomBean.getAr_leuchten());
                                        featuresToSelect.addAll(veranlassungCustomBean.getAr_mauerlaschen());
                                        featuresToSelect.addAll(veranlassungCustomBean.getAr_schaltstellen());
                                        featuresToSelect.addAll(veranlassungCustomBean.getAr_standorte());
                                    }
                                }
                                if (featuresToSelect.isEmpty()) {
                                    if (LOG.isDebugEnabled()) {
                                        LOG.debug("no geometry to select --> unselect");
                                    }
                                    getBroker().addFeatureSelectionChangeIgnore(WorkbenchWidget.this);
                                    getBroker().getMappingComponent().getFeatureCollection().unselectAll();
                                } else {
                                    getBroker().addFeatureSelectionChangeIgnore(WorkbenchWidget.this);
                                    final Runnable runnable = new Runnable() {

                                            @Override
                                            public void run() {
                                                ignoreFeatureSelection = true;
                                                getBroker().getMappingComponent()
                                                        .getFeatureCollection()
                                                        .select(featuresToSelect);
                                                ignoreFeatureSelection = false;
                                            }
                                        };
                                    SwingUtilities.invokeLater(runnable);
                                }
                            }
                            return;
                        } else {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("Path is removed");
                                // ToDo if a current selected node is removed
                            }
                        }
                        setSelectedTreeNodes(null);
                        configureMapModeAccordingToSelection();
                    } finally {
                        getBroker().setVetoCheckEnabled(true);
                        getBroker().removeFeatureSelectionChangeIgnore(WorkbenchWidget.this);
                        isSelectedOverMap = false;
                    }
                }
            });
    }

    /**
     * DOCUMENT ME!
     *
     * @param   node  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean isUserObjectLeuchte(final Object node) {
        if ((node != null) && (node instanceof CustomMutableTreeTableNode)) {
            final Object userObject = getUserObjectForTreeNode(node);
            if ((userObject != null) && (userObject instanceof TdtaLeuchtenCustomBean)) {
                return true;
            }
        }
        return false;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   node  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean isUserObjectMast(final Object node) {
        if ((node != null) && (node instanceof CustomMutableTreeTableNode)) {
            final Object userObject = getUserObjectForTreeNode(node);
            if ((userObject != null) && (userObject instanceof TdtaStandortMastCustomBean)
                        && ((TdtaStandortMastCustomBean)userObject).isStandortMast()) {
                return true;
            }
        }
        return false;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   node  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean isParentNodeMast(final Object node) {
        try {
            return getParentMast(node).isStandortMast();
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Exception why checking if node is mast, therefore is no mast", ex);
            }
        }
        return false;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   node  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean isNodeHaengeLeuchte(final Object node) {
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug("instance of Leuchte: "
                            + (((CustomMutableTreeTableNode)node).getUserObject() instanceof TdtaLeuchtenCustomBean));
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("Parent is searchnode: "
                            + ((CustomMutableTreeTableNode)((CustomMutableTreeTableNode)node).getParent()).equals(
                                searchResultsNode));
            }
            return (((CustomMutableTreeTableNode)node).getUserObject() instanceof TdtaLeuchtenCustomBean)
                        && (((CustomMutableTreeTableNode)((CustomMutableTreeTableNode)node).getParent()).equals(
                                searchResultsNode)
                            || ((CustomMutableTreeTableNode)((CustomMutableTreeTableNode)node).getParent()).equals(
                                newObjectsNode));
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Exception why checking if node is haengeleuchte, therefore is no haengeleuchte", ex);
            }
        }
        return false;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   node  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public TdtaStandortMastCustomBean getParentMast(final Object node) {
        try {
            return ((TdtaStandortMastCustomBean)
                    ((CustomMutableTreeTableNode)((CustomMutableTreeTableNode)node).getParent()).getUserObject());
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Exception while getting parent mast. Does not exist", ex);
            }
        }
        return null;
    }

    // ToDo better Event with code --> EntityChanged --> bind specific so you can use the normal functionallity
    @Override
    public void refresh(final Object refreshedObject) {
        bindingGroup2.unbind();
        bindingGroup2.bind();
        // Try to make binding work todo propetychange listener
// if(refreshedObject instanceof Set){
// log.debug("update TreeMode, new SearchResults.");
// treeTableModel.setCurrentSearchResults((Set)refreshedObject);
// }
    }

    @Override
    public void featureSelectionChanged(final Collection<Feature> features) {
        if (!ignoreFeatureSelection) {
            if (features.isEmpty()) {
                return;
            }
            try {
                getBroker().setVetoCheckEnabled(false);
                final Collection<TreePath> paths = new ArrayList<TreePath>();

                for (final Feature feature : features) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("currentFeature: " + feature);
                    }
                    if ((feature instanceof GeoBaseEntity)
                                && getBroker().getMappingComponent().getFeatureCollection().isSelected(feature)) {
                        final TreePath path;
                        if ((feature instanceof TdtaStandortMastCustomBean)
                                    && !((TdtaStandortMastCustomBean)feature).isStandortMast()
                                    && (((TdtaStandortMastCustomBean)feature).getLeuchten() != null)
                                    && (((TdtaStandortMastCustomBean)feature).getLeuchten().size() > 0)) {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("virtual Standort selected, selecting depending leuchte");
                            }
                            path = treeTableModel.getPathForUserObject(((TdtaStandortMastCustomBean)feature)
                                            .getLeuchten().iterator().next());
                        } else {
                            path = treeTableModel.getPathForUserObject(feature);
                        }

                        if (path != null) {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("Path is available");
                                LOG.debug("selected over map", new CurrentStackTrace());
                            }
                            paths.add(path);
                        }
                    }
                }

                jttHitTable.getSelectionMapper().getViewSelectionModel().clearSelection();
                final TreePath[] patharr = paths.toArray(new TreePath[0]);
                if (paths.isEmpty()) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("No Path for feature available");
                    }
                } else {
                    isSelectedOverMap = true;
                    try {
                        jttHitTable.getTreeSelectionModel().setSelectionPaths(patharr);
                        final Rectangle tmp = jttHitTable.getCellRect(jttHitTable.getSelectedRow(), 0, true);
                        if (tmp != null) {
                            jttHitTable.scrollRectToVisible(tmp);
                        }
                    } catch (Exception ex) {
                        LOG.warn("couldn't set selection", ex);
                    }
                }
            } finally {
                getBroker().setVetoCheckEnabled(true);
            }
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("FeatureSelection ignored", new CurrentStackTrace());
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  artifactType  DOCUMENT ME!
     */
    public void refreshTreeArtifacts(final int artifactType) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Is in EDT: " + EventQueue.isDispatchThread());
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("refreshTreeArtifacts");
        }
        saveSelectedElementAndUnselectAll();
        switch (artifactType) {
            case REFRESH_SEARCH_RESULTS: {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Refreshing searchResults");
                }
//                Enumeration<MutableTreeTableNode> oldSearch = (Enumeration<MutableTreeTableNode>) searchResults.children();
//                if (oldSearch != null) {
//                    while (oldSearch.hasMoreElements()) {
//                        final TreeNode currentNode = oldSearch.nextElement();
//                        log.debug("removing element: "+currentNode);
//                        final int index = searchResults.getIndex(currentNode);
//                        searchResults.remove(index);
//                        modelSupport.fireChildRemoved(new TreePath(getPathToRoot(searchResults)),index,currentNode);
//                    }
                // searchResultsNode.removeAllChildren();
                // modelSupport.fireTreeStructureChanged(new TreePath(getPathToRoot(searchResultsNode)));
                treeTableModel.removeAllChildrenFromNode(searchResultsNode, false);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("adding new SearchResults");
                }
                if (searchResultsNode != null) {
                    for (final Object curObject : currentSearchResults) {
                        if (curObject instanceof TdtaStandortMastCustomBean) {
                            final CustomMutableTreeTableNode standortNode = new CustomMutableTreeTableNode(
                                    curObject,
                                    true);
                            final Collection<TdtaLeuchtenCustomBean> leuchten = ((TdtaStandortMastCustomBean)curObject)
                                        .getLeuchten();
                            if (searchResultsNode != null) {
                                if (((TdtaStandortMastCustomBean)curObject).isStandortMast()) {
                                    if (LOG.isDebugEnabled()) {
                                        LOG.debug("Current Object is mast adding node to tree");
                                    }
                                    treeTableModel.insertNodeIntoAsLastChild(standortNode, searchResultsNode);
                                    ((TdtaStandortMastCustomBean)curObject).addPropertyChangeListener(this);
                                } else {
                                    if (LOG.isDebugEnabled()) {
                                        LOG.debug(
                                            "Current Object is standort for Hängeleuchte (virtual) not adding to tree");
                                    }
                                }
                                if (leuchten != null) {
                                    for (final TdtaLeuchtenCustomBean curLeuchte : leuchten) {
                                        final CustomMutableTreeTableNode leuchteNode = new CustomMutableTreeTableNode(
                                                curLeuchte,
                                                false);
                                        curLeuchte.addPropertyChangeListener(this);
                                        // leuchteNode.setParent(standortNode);
                                        // standortNode.add(leuchteNode);
                                        if (((TdtaStandortMastCustomBean)curObject).isStandortMast()) {
                                            treeTableModel.insertNodeIntoAsLastChild(leuchteNode, standortNode);
                                            ((TdtaStandortMastCustomBean)curObject).addPropertyChangeListener(
                                                curLeuchte);
                                        } else {
                                            treeTableModel.insertNodeIntoAsLastChild(leuchteNode, searchResultsNode);
                                            leuchteToVirtualStandortMap.put(
                                                curLeuchte,
                                                (TdtaStandortMastCustomBean)curObject);
                                        }
                                    }
//                                    searchResultsNode.add(standortNode);
//                                    final int index = searchResultsNode.getIndex(standortNode);
//                                    modelSupport.fireChildAdded(new TreePath(getPathToRoot(searchResultsNode)), index, standortNode);
                                }
                            }
                        } else if (curObject instanceof VeranlassungCustomBean) {
                            final VeranlassungCustomBean veranlassungCustomBean = (VeranlassungCustomBean)curObject;
                            final CustomMutableTreeTableNode veranlassungNode = new CustomMutableTreeTableNode(
                                    veranlassungCustomBean,
                                    true);
                            treeTableModel.insertNodeIntoAsLastChild(veranlassungNode, searchResultsNode);

                            for (final TdtaStandortMastCustomBean standort : veranlassungCustomBean.getAr_standorte()) {
                                final CustomMutableTreeTableNode standortNode = new CustomMutableTreeTableNode(
                                        standort,
                                        false);
                                treeTableModel.insertNodeIntoAsLastChild(standortNode, veranlassungNode);
                            }
                            for (final TdtaLeuchtenCustomBean leuchte : veranlassungCustomBean.getAr_leuchten()) {
                                final CustomMutableTreeTableNode leuchteNode = new CustomMutableTreeTableNode(
                                        leuchte,
                                        false);
                                treeTableModel.insertNodeIntoAsLastChild(leuchteNode, veranlassungNode);
                            }
                            for (final SchaltstelleCustomBean schaltstelle
                                        : veranlassungCustomBean.getAr_schaltstellen()) {
                                final CustomMutableTreeTableNode schaltstelleNode = new CustomMutableTreeTableNode(
                                        schaltstelle,
                                        false);
                                treeTableModel.insertNodeIntoAsLastChild(schaltstelleNode, veranlassungNode);
                            }
                            for (final MauerlascheCustomBean mauerlasche : veranlassungCustomBean.getAr_mauerlaschen()) {
                                final CustomMutableTreeTableNode mauerlascheNode = new CustomMutableTreeTableNode(
                                        mauerlasche,
                                        false);
                                treeTableModel.insertNodeIntoAsLastChild(mauerlascheNode, veranlassungNode);
                            }
                            for (final LeitungCustomBean leitung : veranlassungCustomBean.getAr_leitungen()) {
                                final CustomMutableTreeTableNode leitungNode = new CustomMutableTreeTableNode(
                                        leitung,
                                        false);
                                treeTableModel.insertNodeIntoAsLastChild(leitungNode, veranlassungNode);
                            }
                            for (final AbzweigdoseCustomBean abzweigdose : veranlassungCustomBean.getAr_abzweigdosen()) {
                                final CustomMutableTreeTableNode abzweigdoseNode = new CustomMutableTreeTableNode(
                                        abzweigdose,
                                        false);
                                treeTableModel.insertNodeIntoAsLastChild(abzweigdoseNode, veranlassungNode);
                            }
                        } else if (curObject instanceof ArbeitsauftragCustomBean) {
                            final ArbeitsauftragCustomBean arbeitsauftragCustomBean = (ArbeitsauftragCustomBean)
                                curObject;
                            final CustomMutableTreeTableNode arbeitsauftragNode = new CustomMutableTreeTableNode(
                                    arbeitsauftragCustomBean,
                                    true);
                            treeTableModel.insertNodeIntoAsLastChild(arbeitsauftragNode, searchResultsNode);
                            for (final ArbeitsprotokollCustomBean protokoll
                                        : arbeitsauftragCustomBean.getN_protokolle()) {
                                final CustomMutableTreeTableNode protokollNode = new CustomMutableTreeTableNode(
                                        protokoll,
                                        true);
                                treeTableModel.insertNodeIntoAsLastChild(protokollNode, arbeitsauftragNode);
                                final AbzweigdoseCustomBean abzweigdose = protokoll.getFk_abzweigdose();
                                final LeitungCustomBean leitung = protokoll.getFk_leitung();
                                final TdtaLeuchtenCustomBean leuchte = protokoll.getFk_leuchte();
                                final TdtaStandortMastCustomBean standort = protokoll.getFk_standort();
                                final MauerlascheCustomBean mauerlasche = protokoll.getFk_mauerlasche();
                                final SchaltstelleCustomBean schaltstelle = protokoll.getFk_schaltstelle();
                                if (abzweigdose != null) {
                                    final CustomMutableTreeTableNode node = new CustomMutableTreeTableNode(
                                            abzweigdose,
                                            false);
                                    treeTableModel.insertNodeIntoAsLastChild(node, protokollNode);
                                } else if (leitung != null) {
                                    final CustomMutableTreeTableNode node = new CustomMutableTreeTableNode(
                                            leitung,
                                            false);
                                    treeTableModel.insertNodeIntoAsLastChild(node, protokollNode);
                                } else if (leuchte != null) {
                                    final CustomMutableTreeTableNode node = new CustomMutableTreeTableNode(
                                            leuchte,
                                            false);
                                    treeTableModel.insertNodeIntoAsLastChild(node, protokollNode);
                                } else if (standort != null) {
                                    final CustomMutableTreeTableNode node = new CustomMutableTreeTableNode(
                                            standort,
                                            false);
                                    treeTableModel.insertNodeIntoAsLastChild(node, protokollNode);
                                } else if (mauerlasche != null) {
                                    final CustomMutableTreeTableNode node = new CustomMutableTreeTableNode(
                                            mauerlasche,
                                            false);
                                    treeTableModel.insertNodeIntoAsLastChild(node, protokollNode);
                                } else if (schaltstelle != null) {
                                    final CustomMutableTreeTableNode node = new CustomMutableTreeTableNode(
                                            schaltstelle,
                                            false);
                                    treeTableModel.insertNodeIntoAsLastChild(node, protokollNode);
                                }
                            }
                        } else {
                            final CustomMutableTreeTableNode foundObject = new CustomMutableTreeTableNode(
                                    curObject,
                                    true);
                            treeTableModel.insertNodeIntoAsLastChild(foundObject, searchResultsNode);
                        }
                    }
                    jttHitTable.expandPath(new TreePath(treeTableModel.getPathToRoot(searchResultsNode)));
                } else {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("searchResults == null");
                    }
                }
//                } else {
//                    log.debug("children enum == null");
//                }

                break;
            }
            case REFRESH_NEW_OBJECTS: {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Refreshing newObjects (newly created & newly saved)");
//                Enumeration<MutableTreeTableNode> oldSearch = (Enumeration<MutableTreeTableNode>) searchResults.children();
//                if (oldSearch != null) {
//                    while (oldSearch.hasMoreElements()) {
//                        final TreeNode currentNode = oldSearch.nextElement();
//                        log.debug("removing element: "+currentNode);
//                        final int index = searchResults.getIndex(currentNode);
//                        searchResults.remove(index);
//                        modelSupport.fireChildRemoved(new TreePath(getPathToRoot(searchResults)),index,currentNode);
//                    }
                    // searchResultsNode.removeAllChildren();
                    // modelSupport.fireTreeStructureChanged(new TreePath(getPathToRoot(searchResultsNode)));
                    LOG.debug("remove all children of new objects");
                }
                treeTableModel.removeAllChildrenFromNode(newObjectsNode, false);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("adding new Objects to node");
                }
                if (newObjects != null) {
                    for (final Object curObject : newObjects) {
                        if (curObject instanceof TdtaStandortMastCustomBean) {
                            final CustomMutableTreeTableNode standortNode = new CustomMutableTreeTableNode(
                                    curObject,
                                    true);
                            ((TdtaStandortMastCustomBean)curObject).addPropertyChangeListener(this);
                            final Collection<TdtaLeuchtenCustomBean> leuchten = ((TdtaStandortMastCustomBean)curObject)
                                        .getLeuchten();
                            if (newObjectsNode != null) {
                                treeTableModel.insertNodeIntoAsLastChild(standortNode, newObjectsNode);
                                if (leuchten != null) {
                                    for (final TdtaLeuchtenCustomBean curLeuchte : leuchten) {
                                        final CustomMutableTreeTableNode leuchteNode = new CustomMutableTreeTableNode(
                                                curLeuchte,
                                                false);
                                        // leuchteNode.setParent(standortNode);
                                        // standortNode.add(leuchteNode);
                                        treeTableModel.insertNodeIntoAsLastChild(leuchteNode, standortNode);
                                    }
//                                    searchResultsNode.add(standortNode);
//                                    final int index = searchResultsNode.getIndex(standortNode);
//                                    modelSupport.fireChildAdded(new TreePath(getPathToRoot(searchResultsNode)), index, standortNode);
                                }
                            }
                        } else if (curObject instanceof ArbeitsauftragCustomBean) {
                            final ArbeitsauftragCustomBean arbeitsauftragCustomBean = (ArbeitsauftragCustomBean)
                                curObject;
                            final CustomMutableTreeTableNode arbeitsauftragNode = new CustomMutableTreeTableNode(
                                    arbeitsauftragCustomBean,
                                    true);
                            treeTableModel.insertNodeIntoAsLastChild(arbeitsauftragNode, newObjectsNode);
                            for (final ArbeitsprotokollCustomBean protokoll
                                        : arbeitsauftragCustomBean.getN_protokolle()) {
                                final CustomMutableTreeTableNode protokollNode = new CustomMutableTreeTableNode(
                                        protokoll,
                                        true);
                                treeTableModel.insertNodeIntoAsLastChild(protokollNode, arbeitsauftragNode);
                                final AbzweigdoseCustomBean abzweigdose = protokoll.getFk_abzweigdose();
                                final LeitungCustomBean leitung = protokoll.getFk_leitung();
                                final TdtaLeuchtenCustomBean leuchte = protokoll.getFk_leuchte();
                                final TdtaStandortMastCustomBean standort = protokoll.getFk_standort();
                                final MauerlascheCustomBean mauerlasche = protokoll.getFk_mauerlasche();
                                final SchaltstelleCustomBean schaltstelle = protokoll.getFk_schaltstelle();
                                if (abzweigdose != null) {
                                    final CustomMutableTreeTableNode node = new CustomMutableTreeTableNode(
                                            abzweigdose,
                                            false);
                                    treeTableModel.insertNodeIntoAsLastChild(node, protokollNode);
                                } else if (leitung != null) {
                                    final CustomMutableTreeTableNode node = new CustomMutableTreeTableNode(
                                            leitung,
                                            false);
                                    treeTableModel.insertNodeIntoAsLastChild(node, protokollNode);
                                } else if (leuchte != null) {
                                    final CustomMutableTreeTableNode node = new CustomMutableTreeTableNode(
                                            leuchte,
                                            false);
                                    treeTableModel.insertNodeIntoAsLastChild(node, protokollNode);
                                } else if (standort != null) {
                                    final CustomMutableTreeTableNode node = new CustomMutableTreeTableNode(
                                            standort,
                                            false);
                                    treeTableModel.insertNodeIntoAsLastChild(node, protokollNode);
                                } else if (mauerlasche != null) {
                                    final CustomMutableTreeTableNode node = new CustomMutableTreeTableNode(
                                            mauerlasche,
                                            false);
                                    treeTableModel.insertNodeIntoAsLastChild(node, protokollNode);
                                } else if (schaltstelle != null) {
                                    final CustomMutableTreeTableNode node = new CustomMutableTreeTableNode(
                                            schaltstelle,
                                            false);
                                    treeTableModel.insertNodeIntoAsLastChild(node, protokollNode);
                                }
                            }
                        } else if (curObject instanceof VeranlassungCustomBean) {
                            final VeranlassungCustomBean veranlassungCustomBean = (VeranlassungCustomBean)curObject;
                            final CustomMutableTreeTableNode veranlassungNode = new CustomMutableTreeTableNode(
                                    veranlassungCustomBean,
                                    true);
                            treeTableModel.insertNodeIntoAsLastChild(veranlassungNode, newObjectsNode);

                            for (final TdtaStandortMastCustomBean standort : veranlassungCustomBean.getAr_standorte()) {
                                final CustomMutableTreeTableNode standortNode = new CustomMutableTreeTableNode(
                                        standort,
                                        false);
                                treeTableModel.insertNodeIntoAsLastChild(standortNode, veranlassungNode);
                            }
                            for (final TdtaLeuchtenCustomBean leuchte : veranlassungCustomBean.getAr_leuchten()) {
                                final CustomMutableTreeTableNode leuchteNode = new CustomMutableTreeTableNode(
                                        leuchte,
                                        false);
                                treeTableModel.insertNodeIntoAsLastChild(leuchteNode, veranlassungNode);
                            }
                            for (final SchaltstelleCustomBean schaltstelle
                                        : veranlassungCustomBean.getAr_schaltstellen()) {
                                final CustomMutableTreeTableNode schaltstelleNode = new CustomMutableTreeTableNode(
                                        schaltstelle,
                                        false);
                                treeTableModel.insertNodeIntoAsLastChild(schaltstelleNode, veranlassungNode);
                            }
                            for (final MauerlascheCustomBean mauerlasche : veranlassungCustomBean.getAr_mauerlaschen()) {
                                final CustomMutableTreeTableNode mauerlascheNode = new CustomMutableTreeTableNode(
                                        mauerlasche,
                                        false);
                                treeTableModel.insertNodeIntoAsLastChild(mauerlascheNode, veranlassungNode);
                            }
                            for (final LeitungCustomBean leitung : veranlassungCustomBean.getAr_leitungen()) {
                                final CustomMutableTreeTableNode leitungNode = new CustomMutableTreeTableNode(
                                        leitung,
                                        false);
                                treeTableModel.insertNodeIntoAsLastChild(leitungNode, veranlassungNode);
                            }
                            for (final AbzweigdoseCustomBean abzweigdose : veranlassungCustomBean.getAr_abzweigdosen()) {
                                final CustomMutableTreeTableNode abzweigdoseNode = new CustomMutableTreeTableNode(
                                        abzweigdose,
                                        false);
                                treeTableModel.insertNodeIntoAsLastChild(abzweigdoseNode, veranlassungNode);
                            }
                        } else {
                            final CustomMutableTreeTableNode foundObject = new CustomMutableTreeTableNode(
                                    curObject,
                                    true);
                            treeTableModel.insertNodeIntoAsLastChild(foundObject, newObjectsNode);
                        }
                    }
                } else {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("new objects == null");
                    }
                }
//                log.debug("adding aldready saved objects to node");
//                if (savedObjects != null) {
//                    for (Object curObject : savedObjects) {
//                        if (curObject instanceof Standort) {
//                            final CustomMutableTreeTableNode standortNode = new CustomMutableTreeTableNode(curObject, true);
//                            Collection<Leuchte> leuchten = ((Standort) curObject).getLeuchten();
//                            if (newObjectsNode != null) {
//                                treeTableModel.insertNodeInto(standortNode, newObjectsNode, newObjectsNode.getChildCount());
//                                if (leuchten != null) {
//                                    for (Leuchte curLeuchte : leuchten) {
//                                        final CustomMutableTreeTableNode leuchteNode = new CustomMutableTreeTableNode(curLeuchte, false);
//                                        //leuchteNode.setParent(standortNode);
//                                        //standortNode.add(leuchteNode);
//                                        treeTableModel.insertNodeInto(leuchteNode, standortNode, standortNode.getChildCount());
//                                    }
////                                    searchResultsNode.add(standortNode);
////                                    final int index = searchResultsNode.getIndex(standortNode);
////                                    modelSupport.fireChildAdded(new TreePath(getPathToRoot(searchResultsNode)), index, standortNode);
//                                }
//                            }
//                        } else {
//                            final CustomMutableTreeTableNode foundObject = new CustomMutableTreeTableNode(curObject, true);
//                            treeTableModel.insertNodeInto(foundObject, newObjectsNode, newObjectsNode.getChildCount());
//                        }
//                    }
//                } else {
//                    log.debug("new objects == null");
//                }
//                } else {
//                    log.debug("children enum == null");
//                }

                break;
            }
            case REFRESH_ALL: {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("refresh all");
                }
                refreshTreeArtifacts(REFRESH_NEW_OBJECTS);
                refreshTreeArtifacts(REFRESH_SEARCH_RESULTS);
                break;
            }
            // ToDo disabled Functionality 04.05.2009 ToDo is not realy an refresh is more a special function case
            // REFRESH_PROCESSED_OBJECTS: log.debug("Refreshing processed objects"); if (newObjectsNode.getChildCount()
            // > 0) { log.debug("Child index count: " + newObjectsNode.getChildCount()); final int childIndexCount =
            // newObjectsNode.getChildCount(); for (int curChildIndex = 0; curChildIndex < childIndexCount;
            // curChildIndex++) { final MutableTreeTableNode curChild = (MutableTreeTableNode)
            // newObjectsNode.getChildAt(0); removeNodeFromParent(curChild); log.debug("check if node is placeholder
            // node"); CustomMutableTreeTableNode realStandortNode = null; if ((realStandortNode =
            // leuchtePlaceholderNodeToActualStandort.get(curChild)) != null) { log.debug("current node is
            // placeholdernode. Creating placeholderStandort"); //Problem --> what about the leuchten wich are not
            // processed is it ok + what if the standort is changed and a leuchte is added attention final
            // CustomMutableTreeTableNode clonedStandortNode = cloneNodeWithSameUserObjects(realStandortNode);
            // insertNodeInto(clonedStandortNode, processedObjectsNode, processedObjectsNode.getChildCount()); } else {
            // log.debug("current node is no placeholdernode."); insertNodeInto(curChild, processedObjectsNode,
            // processedObjectsNode.getChildCount()); } } processedObjects.add(newObjects); newObjects.clear(); } else {
            // log.debug("no new objects to move to processed objects"); } return; case REFRESH_NEW_OBJECTS:
            // log.debug("Refreshing new objects");
            //
            // return;
            case CLEAR_NEW_OBJECTS: {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Clear new objects");
                }
//                    int childCount = newObjectsNode.getChildCount();
//                    for (int i = 0; i < childCount; i++) {
//                        final CustomMutableTreeTableNode currrentNode = (CustomMutableTreeTableNode) newObjectsNode.getChildAt(i);
//                        try {
//                            log.debug("currentUserObject: "+currrentNode.getUserObject());
//                            if (currrentNode.getUserObject() != null && BelisEEUtils.getEntityId(currrentNode.getUserObject()) != null) {
//                                log.debug("Entity has ID is not new in this editmode and will not be removed");
//                                continue;
//                            } else {
//                                log.debug("no id will be removed");
//                                removeNodeFromParent((CustomMutableTreeTableNode) newObjectsNode.getChildAt(i));
//                                //ToDo really ugly if a child is removed the loop is reseted bad performance
//                                childCount=newObjectsNode.getChildCount();
//                                i=0;
//                            }
//                        } catch (ActionNotSuccessfulException ex) {
//                            log.debug("Entity has no id field");
//                        }
//                    }
                treeTableModel.removeAllChildrenFromNode(newObjectsNode, true);
                newObjects.clear();
                // ToDo remove all propertyChangeListener
                leuchteToVirtualStandortMap.clear();
                break;
            }
//            case MOVE_NEW_TO_SAVED_OBJECTS:
//                log.debug("move new to saved");
//                if(newObjects.size()>0){
//                    for(Object curObject:newObjects){
//                        savedObjects.add(curObject);
//                        newObjects.remove(curObject);
//                    }
//                }
//                return;
            default: {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Type is unkown, nothing to refresh.");
                }

                break;
            }
        }
        restoreSelectedElementIfPossible();
    }
    /**
     * ToDo remove propretychangelistener.
     */
    public void removeSelectedEntity() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("remove Entity");
        }
//        if (entity != null) {

        final TreePath pathToNodeToRemove = BelisBroker.getInstance().getWorkbenchWidget().getSelectedTreeNode();
        final Object entity = ((CustomMutableTreeTableNode)pathToNodeToRemove.getLastPathComponent()).getUserObject();

        if ((pathToNodeToRemove != null) && (pathToNodeToRemove.getLastPathComponent() != null)
                    && (pathToNodeToRemove.getLastPathComponent() instanceof CustomMutableTreeTableNode)) {
            final CustomMutableTreeTableNode nodeToRemove = (CustomMutableTreeTableNode)
                pathToNodeToRemove.getLastPathComponent();
            saveSelectedElementAndUnselectAll();
            if (LOG.isDebugEnabled()) {
                LOG.debug("removing node:" + nodeToRemove);
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("instance leuchte: " + (nodeToRemove.getUserObject() instanceof TdtaLeuchtenCustomBean));
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("parent != null: " + (nodeToRemove.getParent() != null));
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("parent instance standort: "
                            + (nodeToRemove.getParent() instanceof TdtaStandortMastCustomBean));
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("parent childcoutn" + nodeToRemove.getParent().getChildCount());
            }
            // ToDo getParentMast for Leuchte Method
            if ((nodeToRemove.getUserObject() != null)
                        && (nodeToRemove.getUserObject() instanceof TdtaLeuchtenCustomBean)
                        && (nodeToRemove.getParent() != null)
                        && (nodeToRemove.getParent() instanceof CustomMutableTreeTableNode)
                        && (((CustomMutableTreeTableNode)nodeToRemove.getParent()) != null)
                        && (((CustomMutableTreeTableNode)nodeToRemove.getParent()).getUserObject() != null)
                        && (((CustomMutableTreeTableNode)nodeToRemove.getParent()).getUserObject()
                            instanceof TdtaStandortMastCustomBean)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Leuchte is removed from Mast");
                }
                final TdtaStandortMastCustomBean mast = (TdtaStandortMastCustomBean)
                    ((CustomMutableTreeTableNode)nodeToRemove.getParent()).getUserObject();
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Leuchte: " + (TdtaLeuchtenCustomBean)nodeToRemove.getUserObject() + " from Mast: "
                                + mast);
                }
                mast.getLeuchten().remove((TdtaLeuchtenCustomBean)nodeToRemove.getUserObject());
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Leuchten of Mast: " + mast.getLeuchten());
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("other mast leuchten ref: "
                                + ((TdtaStandortMastCustomBean)((CustomMutableTreeTableNode)nodeToRemove.getParent())
                                    .getUserObject()).getLeuchten());
                }
                ArrayList<TdtaLeuchtenCustomBean> alreadyRemovedLeuchten = leuchtenRemovedFromMastMap.get(mast);
                if (alreadyRemovedLeuchten == null) {
                    alreadyRemovedLeuchten = new ArrayList<TdtaLeuchtenCustomBean>();
                    leuchtenRemovedFromMastMap.put(mast, alreadyRemovedLeuchten);
                }
                alreadyRemovedLeuchten.add((TdtaLeuchtenCustomBean)nodeToRemove.getUserObject());
                if (mast.getLeuchten().isEmpty()) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("The leucht was the last leuchte of the mast refreshing icon");
                    }
                    getBroker().getMappingComponent()
                            .getFeatureCollection()
                            .reconsiderFeature(((TdtaStandortMastCustomBean)
                                    ((CustomMutableTreeTableNode)nodeToRemove.getParent()).getUserObject()));
                }
            } else if (isNodeHaengeLeuchte(nodeToRemove)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Node which will be removed is a haengeleuchte. Removing also virtual Standort");
                }
                final TdtaStandortMastCustomBean virtualStandort = leuchteToVirtualStandortMap.get(
                        (TdtaLeuchtenCustomBean)nodeToRemove.getUserObject());
                if (virtualStandort != null) {
                    getBroker().getMappingComponent().getFeatureCollection().removeFeature(virtualStandort);
                    removedObjects.add(virtualStandort);
                } else {
                    LOG.warn("No virtual standort found for leuchte");
                }
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Neither Mast Leuchte nor Hängeleuchte.");
                }
            }
            treeTableModel.removeNodeFromParent(nodeToRemove);
            // removedNodes.add(nodeToRemove);
            if ((entity instanceof GeoBaseEntity) && (((GeoBaseEntity)entity).getGeometrie() != null)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Entity has a geometry. Removing geometry from map");
                }
                getBroker().getMappingComponent().getFeatureCollection().removeFeature((GeoBaseEntity)entity);
            }
//                try {
//                    if (BelisEEUtils.getEntityId(entity) != null) {
//                        log.debug("Id of object is set, therefore the entity must already be persisted. The Entity will be deleted when the state is saved");
//                        removedObjects.add(entity);
//                    } else {
//                        log.debug("No id set for the given entity, therefore was never persisted will be simply removed");
//                    }
//                } catch (Exception ex) {
//                    log.warn("This object has no id field, hence it is no entity.");
//                }
            if (!(entity instanceof TdtaLeuchtenCustomBean)) {
                removedObjects.add(entity);
            }
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Can't remove object from Tree, because there is no path to the node.");
            }
        }
        newObjects.remove(entity);
//        } else {
//            if (LOG.isDebugEnabled()) {
//                LOG.debug("Can't remove object. The userobject == null.");
//            }
//        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Collection getCurrentSearchResults() {
        return currentSearchResults;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  currentSearchResults  DOCUMENT ME!
     */
    public void setCurrentSearchResults(final Collection currentSearchResults) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("setSearchResults");
        }
        this.currentSearchResults = currentSearchResults;
        if (getBroker().isInCreateMode()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("nothing to refresh because is in Create Mode");
            }
        } else {
            if ((currentSearchResults != null) && (currentSearchResults.isEmpty())) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("0 Search results selecting search node");
                }
                selectNode(searchResultsNode);
            }
            refreshTreeArtifacts(REFRESH_SEARCH_RESULTS);
        }
        // propertyChangeSupport.firePropertyChange(PROP_CURRENT_SEARCH_RESULTS, null, currentSearchResults);
    }
    /**
     * public Set getSavedObjects() { return savedObjects; } public void setSavedObjects(Set savedObjects) {
     * log.debug("setSavedObjects"); this.savedObjects = savedObjects; refreshTreeArtifacts(REFRESH_NEW_OBJECTS); }.
     *
     * @return  DOCUMENT ME!
     */
    public Set getNewObjects() {
        return newObjects;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  newObjects  DOCUMENT ME!
     */
    public void setNewObjects(final Set newObjects) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("setNewObjects");
        }
        this.newObjects = newObjects;
        // refreshTreeArtifacts(REFRESH_PROCESSED_OBJECTS);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Set getObjectsToRemove() {
        return removedObjects;
    }
    /**
     * public void moveNewObjectsAfterSave(Set newlySaved) { //refreshTreeArtifacts(MOVE_NEW_TO_SAVED_OBJECTS); //ToDo
     * ugly //remove and add to map // for (Object curObject : newObjects) { //
     * broker.getMappingComponent().getFeatureCollection().removeFeature((Feature) curObject); // }
     * broker.getMappingComponent().getFeatureCollection().removeFeatures(newObjects); newObjects.clear(); if
     * (newlySaved != null) { savedObjects.addAll(newlySaved); // for (Object curObject : newlySaved) { //
     * broker.getMappingComponent().getFeatureCollection().addFeature((Feature) curObject); // }
     * broker.getMappingComponent().getFeatureCollection().addFeatures(newlySaved); }
     * refreshTreeArtifacts(REFRESH_NEW_OBJECTS); }
     */
    public void restoreRemovedObjects() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("restoreRemovedObjects");
        }
        // ToDo I think this is no longer needed because if the search result modification is canceld --> refresh
        // And new objects dosen't matter because they will be eraesd
        if (leuchtenRemovedFromMastMap.keySet().size() > 0) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("restoring removed leuchten");
            }
            for (final TdtaStandortMastCustomBean curMast : leuchtenRemovedFromMastMap.keySet()) {
                curMast.getLeuchten().addAll(leuchtenRemovedFromMastMap.get(curMast));
            }
        }
        leuchtenRemovedFromMastMap.clear();
        if (!getBroker().isInCreateMode()) {
            refreshTreeArtifacts(REFRESH_SEARCH_RESULTS);
            removedObjects.clear();
        }
    }
    /**
     * ToDo find a better name.
     */
    public void objectsRemoved() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("objectsRemoved");
        }
        if (!getBroker().isInCreateMode()) {
            if (removedObjects.size() > 0) {
                for (final Object curObjectToRemove : removedObjects) {
                    currentSearchResults.remove(curObjectToRemove);
                    newObjects.remove(curObjectToRemove);
                }
            }
            leuchtenRemovedFromMastMap.clear();
            removedObjects.clear();
        }
        // ToDo clearThe nodes for restoring;
    }
    /**
     * public void refreshSavedObjects(Set refreshedSaved){ if(refreshedSaved != null){ savedObjects.cle } }.
     */
    public void clearNewObjects() {
        refreshTreeArtifacts(CLEAR_NEW_OBJECTS);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public CustomMutableTreeTableNode addNewStandort() {
        final TdtaStandortMastCustomBean newStandort = TdtaStandortMastCustomBean.createNew();
        newStandort.setVerrechnungseinheit(true);

        if (getBroker().getDefaultUnterhaltMast() != null) {
            newStandort.setUnterhaltspflichtMast(getBroker().getDefaultUnterhaltMast());
        }
        newStandort.addPropertyChangeListener(this);
        final CustomMutableTreeTableNode newStandortNode = new CustomMutableTreeTableNode(newStandort, true);
        // newObjectsNode.add(newStandortNode);
        newObjects.add(newStandortNode.getUserObject());
        // final int index = newObjectsNode.getIndex(newStandortNode);
        // modelSupport.fireChildAdded(new TreePath(getPathToRoot(newObjectsNode)), index, newStandortNode);
        treeTableModel.insertNodeIntoAsLastChild(newStandortNode, newObjectsNode);
        return newStandortNode;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public CustomMutableTreeTableNode addNewLeuchte() {
        final TdtaStandortMastCustomBean newStandort = TdtaStandortMastCustomBean.createNew();
        newStandort.addPropertyChangeListener(this);
        newStandort.setVirtuellerStandort(true);
        newStandort.setVerrechnungseinheit(true);
        // final CustomMutableTreeTableNode newStandortNode = new CustomMutableTreeTableNode(newStandort, true);
        newObjects.add(newStandort);
        // treeTableModel.insertNodeIntoAsLastChild(newStandortNode, newObjectsNode);
        return addNewLeuchte(newStandort);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   relatedObject  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public CustomMutableTreeTableNode addNewLeuchte(final Object relatedObject) {
        CustomMutableTreeTableNode nodeToAddLeuchte = null;
        TdtaStandortMastCustomBean parent = null;
        try {
            final TreePath pathToRelatedObejct = treeTableModel.getPathForUserObject(relatedObject);
            if ((pathToRelatedObejct != null)
                        && ((pathToRelatedObejct.getLastPathComponent() != null)
                            && (pathToRelatedObejct.getLastPathComponent() instanceof CustomMutableTreeTableNode)
                            && (((CustomMutableTreeTableNode)pathToRelatedObejct.getLastPathComponent())
                                .getUserObject() instanceof TdtaLeuchtenCustomBean))
                        && (pathToRelatedObejct.getParentPath() != null)
                        && (pathToRelatedObejct.getParentPath().getLastPathComponent()
                            instanceof CustomMutableTreeTableNode)
                        && (((CustomMutableTreeTableNode)pathToRelatedObejct.getParentPath().getLastPathComponent())
                            .getUserObject() instanceof TdtaStandortMastCustomBean)) {
                parent = (TdtaStandortMastCustomBean)
                    ((CustomMutableTreeTableNode)pathToRelatedObejct.getParentPath().getLastPathComponent())
                            .getUserObject();
                nodeToAddLeuchte = ((CustomMutableTreeTableNode)pathToRelatedObejct.getParentPath()
                                .getLastPathComponent());
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Related object is Leuchte (sibling)");
                }
            } else if ((pathToRelatedObejct != null) && (pathToRelatedObejct.getLastPathComponent() != null)
                        && (pathToRelatedObejct.getLastPathComponent() instanceof CustomMutableTreeTableNode)
                        && (((CustomMutableTreeTableNode)pathToRelatedObejct.getLastPathComponent()).getUserObject()
                            instanceof TdtaStandortMastCustomBean)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Related object is Standort (parent)");
                }
                parent = (TdtaStandortMastCustomBean)
                    ((CustomMutableTreeTableNode)pathToRelatedObejct.getLastPathComponent()).getUserObject();
                nodeToAddLeuchte = ((CustomMutableTreeTableNode)pathToRelatedObejct.getLastPathComponent());
            } else if (((pathToRelatedObejct == null) && (relatedObject != null)
                            && (relatedObject instanceof TdtaStandortMastCustomBean)
                            && ((((TdtaStandortMastCustomBean)relatedObject).isVirtuellerStandort() != null)
                                && ((TdtaStandortMastCustomBean)relatedObject).isVirtuellerStandort()))
                        || !((TdtaStandortMastCustomBean)relatedObject).isStandortMast()) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Leuchte has virtual standort will be added directly to tree");
                }
                nodeToAddLeuchte = newObjectsNode;
                parent = (TdtaStandortMastCustomBean)relatedObject;
            } else {
                LOG.warn("Can't add Leuchte relatedObject is neither Leuchte nor Standort.");
            }
        } catch (Exception ex) {
            LOG.error("Error while trying to get node for related object", ex);
        }
        if ((nodeToAddLeuchte == null) || (parent == null)) {
            LOG.warn("Can't add Leuchte no node or standort found. Returning.");
            return null;
        }
        final TdtaLeuchtenCustomBean newLeuchte = TdtaLeuchtenCustomBean.createNew();
        if (BelisBroker.getDefaultUnterhaltLeuchte() != null) {
            newLeuchte.setUnterhaltspflichtLeuchte(BelisBroker.getDefaultUnterhaltLeuchte());
        }
        if (BelisBroker.getDefaultDoppelkommando1() != null) {
            newLeuchte.setDk1(BelisBroker.getDefaultDoppelkommando1());
        }
        newLeuchte.addPropertyChangeListener(this);
        newLeuchte.addPropertyChangeListener(getBroker());
        if (!parent.isStandortMast()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Adding propterychange listener for virtual standort");
            }
            newLeuchte.addPropertyChangeListener(parent);
            leuchteToVirtualStandortMap.put(newLeuchte, parent);
            newLeuchte.setLeuchtennummer((Integer)0);
        } else {
            parent.addPropertyChangeListener(newLeuchte);
            newLeuchte.setLeuchtennummer(getNextLeuchtennummer(parent));
        }
        parent.getLeuchten().add(newLeuchte);
        // ToDo must also be set if these attributes are changed in the parent standort
        newLeuchte.setFk_strassenschluessel(parent.getStrassenschluessel());
        newLeuchte.setKennziffer(parent.getKennziffer());
        newLeuchte.setLaufendeNummer(parent.getLaufendeNummer());
        // ToDo attention if a new Leuchte is added to an already existing Standort it will be saved automatically
        // newObjects.add(newLeuchte);
        final CustomMutableTreeTableNode newLeuchteNode = new CustomMutableTreeTableNode(newLeuchte, false);
        // nodeToAddLeuchte.add(newLeuchteNode);
        // newLeuchteNode.setParent(nodeToAddLeuchte);
        final Collection<TdtaLeuchtenCustomBean> tmpLeuchten = parent.getLeuchten();
        tmpLeuchten.add(newLeuchte);
        if (parent.getGeometry() != null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(
                    "standort has geometry. Reconsidering feature because the icon must be switched from standort without leuchte to standort with leuchte.");
            }
            getBroker().getMappingComponent().getFeatureCollection().reconsiderFeature(parent);
        }
        // final int index = nodeToAddLeuchte.getIndex(newLeuchteNode);
        // modelSupport.fireChildChanged(, index, root);
        // modelSupport.fireChildAdded(new TreePath(getPathToRoot(nodeToAddLeuchte)), index, newLeuchteNode);
// if (parent.getId() != null) {
// log.debug("the parent node is already persisted --> adding new Leuchte directly to the persisted");
// treeTableModel.insertNodeIntoAsLastChild(newLeuchteNode, nodeToAddLeuchte);
// final CustomMutableTreeTableNode leuchtePlaceholderNode = new CustomMutableTreeTableNode(newLeuchte, false);
// treeTableModel.insertNodeIntoAsLastChild(leuchtePlaceholderNode, newObjectsNode);
// //leuchtePlaceholderNodeToActualStandort.put(leuchtePlaceholderNode, nodeToAddLeuchte);
// } else {
// log.debug("partent is unpersisted adding new leiuchte to this node");
        treeTableModel.insertNodeIntoAsLastChild(newLeuchteNode, nodeToAddLeuchte);
//        }
        return newLeuchteNode;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   standort  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getNextLeuchtennummer(final TdtaStandortMastCustomBean standort) {
        if ((standort != null) && (standort.getLeuchten() != null) && (standort.getLeuchten().size() > 0)) {
            Integer max = 0;
            for (final TdtaLeuchtenCustomBean curLeuchte : standort.getLeuchten()) {
                if ((curLeuchte.getLeuchtennummer() != null) && (curLeuchte.getLeuchtennummer() > max)) {
                    max = curLeuchte.getLeuchtennummer();
                }
            }
            return (Integer)(max + 1);
        }
        return (Integer)0;
    }
    /**
     * ToDo make generic.
     *
     * @return  DOCUMENT ME!
     */
    public CustomMutableTreeTableNode addNewMauerlasche() {
        final MauerlascheCustomBean newMauerlasche = MauerlascheCustomBean.createNew();
        newMauerlasche.setStrassenschluessel(getBroker().getLastMauerlascheStrassenschluessel());
        newMauerlasche.addPropertyChangeListener(getBroker());
        newMauerlasche.addPropertyChangeListener(this);
        final CustomMutableTreeTableNode newMauerlascheNode = new CustomMutableTreeTableNode(newMauerlasche, true);
        newObjects.add(newMauerlascheNode.getUserObject());
        treeTableModel.insertNodeIntoAsLastChild(newMauerlascheNode, newObjectsNode);
        return newMauerlascheNode;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public CustomMutableTreeTableNode addNewSchaltstelle() {
        final SchaltstelleCustomBean newSchaltstelle = SchaltstelleCustomBean.createNew();
        newSchaltstelle.addPropertyChangeListener(this);
        final CustomMutableTreeTableNode newSchaltstelleNode = new CustomMutableTreeTableNode(newSchaltstelle, true);
        newObjects.add(newSchaltstelleNode.getUserObject());
        treeTableModel.insertNodeIntoAsLastChild(newSchaltstelleNode, newObjectsNode);
        return newSchaltstelleNode;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public CustomMutableTreeTableNode addNewLeitung() {
        final LeitungCustomBean newLeitung = LeitungCustomBean.createNew();
        newLeitung.setLeitungstyp(getBroker().getLastLeitungstyp());
        newLeitung.addPropertyChangeListener(getBroker());
        newLeitung.addPropertyChangeListener(this);
        final CustomMutableTreeTableNode newLeitungNode = new CustomMutableTreeTableNode(newLeitung, true);
        newObjects.add(newLeitungNode.getUserObject());
        treeTableModel.insertNodeIntoAsLastChild(newLeitungNode, newObjectsNode);
        return newLeitungNode;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public CustomMutableTreeTableNode addNewVeranlassung() {
        final CidsBean newVeranlassung = VeranlassungCustomBean.createNew();
        final CustomMutableTreeTableNode newVeranlassungNode = new CustomMutableTreeTableNode(newVeranlassung, true);
        newObjects.add(newVeranlassungNode.getUserObject());
        treeTableModel.insertNodeIntoAsLastChild(newVeranlassungNode, newObjectsNode);
        return newVeranlassungNode;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public CustomMutableTreeTableNode addNewArbeitsauftrag() {
        final CidsBean newArbeitsauftrag = CidsBroker.getInstance()
                    .getBelisMetaClass(BelisMetaClassConstants.MC_ARBEITSAUFTRAG)
                    .getEmptyInstance()
                    .getBean();
        final CustomMutableTreeTableNode newArbeitsauftragNode = new CustomMutableTreeTableNode(
                newArbeitsauftrag,
                true);
        newObjects.add(newArbeitsauftragNode.getUserObject());
        treeTableModel.insertNodeIntoAsLastChild(newArbeitsauftragNode, newObjectsNode);
        return newArbeitsauftragNode;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   relatedObject  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public CustomMutableTreeTableNode addNewArbeitsprotokoll(final Object relatedObject) {
        final CidsBean newArbeitsauftrag = CidsBroker.getInstance()
                    .getBelisMetaClass(BelisMetaClassConstants.MC_ARBEITSPROTOKOLL)
                    .getEmptyInstance()
                    .getBean();
        final CustomMutableTreeTableNode newArbeitsprotokollNode = new CustomMutableTreeTableNode(
                newArbeitsauftrag,
                true);

        final TreePath pathToRelatedObejct = treeTableModel.getPathForUserObject(relatedObject);
        final CustomMutableTreeTableNode nodeToAddProtokoll = ((CustomMutableTreeTableNode)
                pathToRelatedObejct.getLastPathComponent());
//        final ArbeitsauftragCustomBean parent = (ArbeitsauftragCustomBean)
//            ((CustomMutableTreeTableNode)pathToRelatedObejct.getParentPath().getLastPathComponent()).getUserObject();

        newObjects.add(newArbeitsprotokollNode.getUserObject());
        treeTableModel.insertNodeIntoAsLastChild(newArbeitsprotokollNode, nodeToAddProtokoll);
        return newArbeitsprotokollNode;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public CustomMutableTreeTableNode addNewAbzweigdose() {
        final AbzweigdoseCustomBean newAbzweigdose = AbzweigdoseCustomBean.createNew();
        final CustomMutableTreeTableNode newAbzweigdoseNode = new CustomMutableTreeTableNode(newAbzweigdose, true);
        newObjects.add(newAbzweigdoseNode.getUserObject());
        treeTableModel.insertNodeIntoAsLastChild(newAbzweigdoseNode, newObjectsNode);
        return newAbzweigdoseNode;
    }

    /**
     * DOCUMENT ME!
     */
    public void saveSelectedElementAndUnselectAll() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("storing selection");
        }
        try {
            final TreePath tmpSelectedElement = jttHitTable.getTreeSelectionModel().getSelectionPath();
            jttHitTable.getTreeSelectionModel().clearSelection();
            selectedElement = tmpSelectedElement;
        } catch (Exception ex) {
            LOG.warn("Error while clearing selection");
        }
    }

    /**
     * DOCUMENT ME!
     */
    public void restoreSelectedElementIfPossible() {
        if (selectedElement != null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("restoring selection");
            }
            try {
                jttHitTable.getTreeSelectionModel().setSelectionPath(selectedElement);
            } catch (Exception ex) {
                LOG.warn("Error while restoring selection (Maybe The objects doesn't exist anymore)", ex);
                try {
                    jttHitTable.getTreeSelectionModel().clearSelection();
                } catch (Exception ex2) {
                    LOG.warn("Error while clearing selection", ex2);
                }
            }
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("no selection to restore");
            }
        }
    }

    // comes from featureSelectionChanged
    @Override
    public void stateChanged(final ChangeEvent e) {
    }

    /**
     * DOCUMENT ME!
     *
     * @param   leuchte  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public TdtaStandortMastCustomBean getVirtualStandortForLeuchte(final TdtaLeuchtenCustomBean leuchte) {
        return leuchteToVirtualStandortMap.get(leuchte);
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("property of userobject changed refreshing table");
        }
        jttHitTable.repaint();
    }

    @Override
    public void allFeaturesRemoved(final FeatureCollectionEvent fce) {
    }

    @Override
    public void featureCollectionChanged() {
    }

    @Override
    public void featureReconsiderationRequested(final FeatureCollectionEvent fce) {
    }

    @Override
    public void featureSelectionChanged(final FeatureCollectionEvent fce) {
//        log.debug("featureSelectionChanged");
//        if (broker.getMapWidget().getLastMapMode() == null || broker.getMapWidget().getLastMapMode() == MapMode.SELECT) {
//            log.debug("Last map mode was null or select. Setting Back to it and enabling the veto Listener");
//            broker.getMapWidget().setToLastInteractionMode();
//            newlyAddedFeature = null;
//            tmpProcessStarted = false;
//            switchListener();
//        } else {
//            log.debug("last mode is not null nor select. the enabling of the listner was done in featureadded");
//        }
    }

    @Override
    public void featuresAdded(final FeatureCollectionEvent fce) {
//        log.debug("featuresAdded");
////        final FeatureCollection collection = fce.getFeatureCollection();
////        if (collection != null && collection.getFeatureCount() == 0) {
////            selectedFeature = null;
////        } else if (collection != null) {
////            selectedFeature = collection.getFeature(0);
////        }
////        if (!tmpProcessStarted) {
////            log.debug("No tmpProccess");
////            if (selectedFeature != null && collection != null && collection.getFeatureCount() > 0 && selectedFeature.equals(collection.getFeature(0))) {
////                log.debug("Selected Feature is already selected");
////            }
////        } else {
//        if(tmpProcessStarted)
//            log.debug("tmpProcessStarted");
//            if (fce.getFeatureCollection() != null) {
//                log.debug("collection != null");
//                final FeatureCollection collection = fce.getFeatureCollection();
//                for (Feature curFeature : collection.getAllFeatures()) {
//                    if (curFeature instanceof BaseEntity) {
//                        log.debug("BaseEntity added: ");
//                        if (broker.getMapWidget().getLastMapMode() != null && broker.getMapWidget().getLastMapMode() != MapMode.SELECT) {
//                            log.debug("LastMapMode is not select. Selecting new geometry and switching the listener back on");
//                            broker.getMapWidget().setToLastInteractionMode();
//                            broker.getMappingComponent().getFeatureCollection().select(newlyAddedFeature);
//                            tmpProcessStarted = false;
//                            newlyAddedFeature = null;
//                            switchListener();
//                        } else {
//                            log.debug("last mode is select the enabling of the veto listner will happen in the featureCollection changed event");
//                        }
//                        return;
//                    }
//                }
//                log.debug("No BaseEntity added");
//            }
////        }
    }

    @Override
    public void featuresChanged(final FeatureCollectionEvent fce) {
    }

    @Override
    public void featuresRemoved(final FeatureCollectionEvent fce) {
    }
//    public void switchListener() {
//
//        log.debug("isSwitch" + isSwitchTriggerEnabled);
//        log.debug("tmpProcessStarted" + tmpProcessStarted);
//        if (isSwitchTriggerEnabled && !tmpProcessStarted) {
//            isSwitchTriggerEnabled = false;
//            ((BelisBroker) getBroker()).setVetoCheckEnabled(!((BelisBroker) getBroker()).isVetoCheckEnabled());
//            if (broker.isFeatureSelectionChangeIgnoreRegistered(this)) {
//                broker.removeFeatureSelectionChangeIgnore(this);
//            } else {
//                broker.addFeatureSelectionChangeIgnore(this);
//            }
//        }
//    }
//    private void createModel(){
//        root.add(searchResults);
//        root.add(processedObjects);
//        treeTableModel = new CustomTreeTableModel(root);
//    }
    // ToDo give the model to BelisBroker
    // ToDo tree times this set of Methods (Model,Workbench,BelisBroker)
//    public void addNewStandort() {
//        treeTableModel.addNewStandort();
//    }
//
//    public void addNewLeuchte(Object relatedObject) {
//        treeTableModel.addNewLeuchte(relatedObject);
//    }
//
//    public void addNewMauerlasche() {
//        treeTableModel.addNewMauerlasche();
//    }
//
//    public void addNewSchaltstelle() {
//        treeTableModel.addNewSchaltstelle();
//    }
//
//    public void addNewLeitung() {
//        treeTableModel.addNewLeitung();
//    }
//
//    public Set getNewObjects() {
//        return treeTableModel.getNewObjects();
//    }
//
//    public Set getObjectsToRemove() {
//        return treeTableModel.getObjectsToRemove();
//    }
//
//    public void objectsRemoved() {
//        treeTableModel.objectsRemoved();
//    }
//
//    public void restoreRemovedObjects() {
//        treeTableModel.restoreRemovedObjects();
//    }
//
//    public Collection<BaseEntity> getSavedObjects() {
//        return treeTableModel.getSavedObjects();
//    }
//
//    public void getSavedObjects(Collection<BaseEntity> savedObjects) {
//        treeTableModel.setSavedObjects(savedObjects);
//    }
//    public void moveNewObjectsAfterSave(){
//        treeTableModel.moveNewObjectsAfterSave();
//    }
//    public void moveNewObjectsAfterSave(Set newlySaved) {
//        treeTableModel.moveNewObjectsAfterSave(newlySaved);
//    }
//
//    public void clearNewObjects() {
//        treeTableModel.clearNewObjects();
//    }
    // ToDo validate all entities before saving, at least if the strassenschluessel and the kennziffer are set.

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Set getSearchResults() {
        return searchResults;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  searchResult  DOCUMENT ME!
     */
    public void setSearchResults(final Set searchResult) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Search Results set");
        }
        this.searchResults = searchResult;
        firePropertyChange(PROP_SEARCH_RESULTS, null, searchResult);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  path  DOCUMENT ME!
     */
    public void expandPath(final TreePath path) {
        jttHitTable.expandPath(path);
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    class CreateNewBelisObjectListener extends CreateGeometryListener {

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new CreateNewBelisObjectListener object.
         *
         * @param  mc                    DOCUMENT ME!
         * @param  geometryFeatureClass  DOCUMENT ME!
         */
        public CreateNewBelisObjectListener(final MappingComponent mc, final Class geometryFeatureClass) {
            super(mc, geometryFeatureClass);
        }

        //~ Methods ------------------------------------------------------------

        @Override
        protected void finishGeometry(final AbstractNewFeature newFeature) {
            if (EventQueue.isDispatchThread()) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("is in EDT");
                }
            } else {
                LOG.fatal("is not in edt");
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("Pure new feature created for attachement: " + newFeature);
            }

            super.finishGeometry(newFeature);
            if ((newFeature != null) && (newFeature instanceof PureNewFeature) && checkConstraintForMapModeSwitch()) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("all prequisites fulfied for adding newly created geometry");
                }
                try {
                    tmpProcessStarted = true;
                    getBroker().addFeatureSelectionChangeIgnore(WorkbenchWidget.this);
                    ignoreFeatureSelection = true;
                    getBroker().setVetoCheckEnabled(false);
                    // newFeature.setPrimaryAnnotationVisible(false);
                    // broker.getMappingComponent().reconsiderFeature(newFeature);
                    final Object tmpObject = ((CustomMutableTreeTableNode)getSelectedTreeNode().getLastPathComponent())
                                .getUserObject();
                    StyledFeature currentSelectedFeature = null;
                    if (tmpObject instanceof StyledFeature) {
                        currentSelectedFeature = (StyledFeature)tmpObject;
                    } else if (tmpObject instanceof TdtaLeuchtenCustomBean) {
                        currentSelectedFeature = leuchteToVirtualStandortMap.get((TdtaLeuchtenCustomBean)tmpObject);
                        if (currentSelectedFeature == null) {
                            LOG.warn("Leuchte has no virtual standort.");
                        }
                    }
                    currentSelectedFeature.setGeometry(newFeature.getGeometry());
                    newlyAddedFeature = currentSelectedFeature;
                    getBroker().getMappingComponent().getFeatureCollection().addFeature(currentSelectedFeature);
                    currentSelectedFeature.setEditable(true);
                    btnAttachMode.setEnabled(false);
                    getBroker().getMapWidget().setToLastInteractionMode();
                    getBroker().getMappingComponent().getFeatureCollection().select(currentSelectedFeature);
                } finally {
                    getBroker().setVetoCheckEnabled(true);
                    getBroker().removeFeatureSelectionChangeIgnore(WorkbenchWidget.this);
                    ignoreFeatureSelection = false;
//                isSwitchTriggerEnabled = true;
//                if (!tmpProcessStarted) {
//                    log.debug("tmpProcess finished switching back");
//                    //ToDo why is the node multiple times selected I think
//                    //maybe there is a error earlier and this code could be simplified
//                    switchListener();
//                } else {
//                    log.debug("tmpProcess not finished --> switch will be done by listener");
//                }
                }
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Not all prequisites fulfied for adding newly created geometry");
                }
            }
        }
    }
}
