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

import edu.umd.cs.piccolox.event.PNotification;
import edu.umd.cs.piccolox.event.PNotificationCenter;

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
import javax.swing.event.ChangeEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import de.cismet.belis.broker.BelisBroker;

import de.cismet.belis.gui.renderer.WorkbenchTreeTableRenderer;

import de.cismet.belis.todo.CustomMutableTreeTableNode;
import de.cismet.belis.todo.CustomTreeTableModel;

import de.cismet.belis.util.BelisIcons;

import de.cismet.belisEE.util.EntityComparator;
import de.cismet.belisEE.util.LeuchteComparator;

import de.cismet.cids.custom.beans.belis.AbzweigdoseCustomBean;
import de.cismet.cids.custom.beans.belis.LeitungCustomBean;
import de.cismet.cids.custom.beans.belis.MauerlascheCustomBean;
import de.cismet.cids.custom.beans.belis.SchaltstelleCustomBean;
import de.cismet.cids.custom.beans.belis.TdtaLeuchteCustomBean;
import de.cismet.cids.custom.beans.belis.TdtaStandortMastCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyUnterhLeuchteCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyUnterhMastCustomBean;

import de.cismet.cismap.commons.features.Feature;
import de.cismet.cismap.commons.features.FeatureCollection;
import de.cismet.cismap.commons.features.FeatureCollectionEvent;
import de.cismet.cismap.commons.features.FeatureCollectionListener;
import de.cismet.cismap.commons.features.PureNewFeature;
import de.cismet.cismap.commons.features.StyledFeature;
import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.CreateGeometryListener;

import de.cismet.commons.architecture.broker.AdvancedPluginBroker;
import de.cismet.commons.architecture.interfaces.FeatureSelectionChangedListener;
import de.cismet.commons.architecture.validation.Validatable;

import de.cismet.commons.server.entity.BaseEntity;
import de.cismet.commons.server.entity.GeoBaseEntity;

import de.cismet.commons2.architecture.widget.MapWidget.MapMode;

import de.cismet.tools.CurrentStackTrace;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public class WorkbenchWidget extends SearchResultWidget implements TreeSelectionListener,
    FeatureSelectionChangedListener,
    PropertyChangeListener,
    FeatureCollectionListener {

    //~ Static fields/initializers ---------------------------------------------

    // ToDo idea insert the results seperated --> user don't have to wait to long
    private static final Logger log = org.apache.log4j.Logger.getLogger(WorkbenchWidget.class);
    public static final String PROP_SELECTEDTREENODE = "selectedTreeNode";
    // private final DefaultMutableTreeTableNode root = new DefaultMutableTreeTableNode(null, true);
// private final DefaultMutableTreeTableNode searchResults = new DefaultMutableTreeTableNode(null, true);
// private final DefaultMutableTreeTableNode processedObjects = new DefaultMutableTreeTableNode(null, true);
    public static final String PROP_TREE_TABLE_MODEL = "treeTableModel";
    private static final String TEMP_FEATURE_CREATED_MODE = "temporayFeatureInMapCreated";
    public static final String BELIS_CREATE_MODE = "BELIS_CREATE_MODE";
    public static final String PROP_CURRENT_SEARCH_RESULTS = "currentSearchResults";
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
    // Variables declaration - do not modify
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXTreeTable jttHitTable;
    private javax.swing.JPanel panMain;

    private boolean ignoreFeatureSelection = false;
//    protected TreePath[] selectedTreeNodes = null;
//    public static final String PROP_SELECTEDTREENODE = "selectedTreeNodes";
    private TreePath selectedTreeNode = null;
    private final CustomMutableTreeTableNode rootNode = new CustomMutableTreeTableNode(null, true);
    private final CustomMutableTreeTableNode searchResultsNode = new CustomMutableTreeTableNode(null, true);
    private final CustomMutableTreeTableNode newObjectsNode = new CustomMutableTreeTableNode(null, true);
    private CustomTreeTableModel treeTableModel = null;
    private HashMap<TdtaLeuchteCustomBean, TdtaStandortMastCustomBean> leuchteToVirtualStandortMap = new HashMap();
    private HashMap<TdtaStandortMastCustomBean, ArrayList<TdtaLeuchteCustomBean>> leuchtenRemovedFromMastMap =
        new HashMap<TdtaStandortMastCustomBean, ArrayList<TdtaLeuchteCustomBean>>();
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

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form HitWidget.
     *
     * @param  broker  DOCUMENT ME!
     */
    public WorkbenchWidget(final BelisBroker broker) {
        super(broker);
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
            log.warn("No feature Collection to set Listener on.");
        }
        jttHitTable.getTreeSelectionModel().addTreeSelectionListener(this);

        // DefaultTreeTableModel vModel = new DefaultTreeTableModel(root);
        // jttHitTable.setTreeTableModel(vModel);
        jttHitTable.setEditable(false); //
//        jttHitTable.addFocusListener(new FocusListener() {
//
//            @Override
//            public void focusGained(FocusEvent e) {
//                throw new UnsupportedOperationException("Not supported yet.");
//            }
//
//            @Override
//            public void focusLost(FocusEvent e) {
//                log.info("focus lost");
//                if(Validatable.VALID != ((BelisBroker)broker).getDetailWidget().getStatus()){
//                    log.debug("Details not valid consuming event");
//                    e.
//                }
//            }
//        });
        // jttHitTable.getMouseListeners()

//        jttHitTable.addMouseListener(new MouseListener() {
//
//            @Override
//            public void mouseClicked(MouseEvent e) {
//            }
//
//            @Override
//            public void mousePressed(MouseEvent e) {
//                log.debug("pressed");
//                final int clickedRow = jttHitTable.rowAtPoint(e.getPoint());
//                log.debug("clickedRow: " + clickedRow);
//                log.debug("selectedRow: " + jttHitTable.getSelectedRow());
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent e) {
////                final int clickedRow = jttHitTable.rowAtPoint(e.getPoint());
////                log.debug("clickedRow: "+clickedRow);
////                log.debug("selectedRow: "+jttHitTable.getSelectedRow());
//                //e.consume();
//                //jttHitTable.getR
//            }
//
//            @Override
//            public void mouseEntered(MouseEvent e) {
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//            }
//        });
        jttHitTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jttHitTable.setTreeCellRenderer(new WorkbenchTreeTableRenderer());
        treeTableModel = new CustomTreeTableModel(broker, rootNode, searchResultsNode, newObjectsNode);
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
                            TdtaStandortMastCustomBean virtualStandort = null;
                            if (userObj instanceof GeoBaseEntity) {
                                return ((GeoBaseEntity)userObj).getGeometry() == null;
                            } else if ((userObj instanceof TdtaLeuchteCustomBean)
                                        && ((virtualStandort = leuchteToVirtualStandortMap.get(
                                                        (TdtaLeuchteCustomBean)userObj))
                                            != null)) {
                                return virtualStandort.getGeometry() == null;
                            }
                        }
                    } catch (Exception ex) {
                        log.error("Exception in Highlighter: ", ex);
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
                            if (((BelisBroker)broker).isVetoCheckEnabled()
                                && (((BelisBroker)broker).isInCreateMode() || ((BelisBroker)broker).isInEditMode())
                                && (((MouseEvent)event).getSource() instanceof JXTreeTable)
                                && ((((MouseEvent)event).getID() == MouseEvent.MOUSE_PRESSED)
                                    || (((MouseEvent)event).getID() == MouseEvent.MOUSE_CLICKED)
                                    || (((MouseEvent)event).getID() == MouseEvent.MOUSE_RELEASED)
                                    || (((MouseEvent)event).getID() == MouseEvent.MOUSE_DRAGGED))) {
                                if (log.isDebugEnabled()) {
                                    log.debug("eventDispatched: Mouse event intercepted from JXTreeTable in edit mode");
                                }
                                final int clickedRow = jttHitTable.rowAtPoint(((MouseEvent)event).getPoint());
                                final int selectedRow = jttHitTable.getSelectedRow();
                                if (log.isDebugEnabled()) {
                                    log.debug("eventDispatched: clickedRow: " + clickedRow);
                                    log.debug("eventDispatched: selectedRow: " + selectedRow);
                                }
                                if (clickedRow != selectedRow) {
                                    if (!((BelisBroker)broker).validateWidgets()) {
                                        if (log.isDebugEnabled()) {
                                            log.debug(
                                                "eventDispatched: One or more widgets are invalid. Informing user.");
                                        }
                                        final int anwser = ((BelisBroker)broker).askUser();
                                        if (anwser == JOptionPane.YES_OPTION) {
                                            if (log.isDebugEnabled()) {
                                                log.debug("User wants to cancel changes.");
                                            }
                                        } else {
                                            if (log.isDebugEnabled()) {
                                                log.debug("User wants to correct validation, consuming event.");
                                            }
                                            ((MouseEvent)event).consume();
                                        }
                                    } else {
                                        if (log.isDebugEnabled()) {
                                            log.debug("eventDispatched: Not consuming event. All Widgets are valid");
                                        }
                                    }
                                } else {
                                    if (log.isDebugEnabled()) {
                                        log.debug(
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
                    if (((BelisBroker)broker).isVetoCheckEnabled()
                                && (((BelisBroker)broker).isInCreateMode() || ((BelisBroker)broker).isInEditMode())
                                && (((KeyEvent)event).getSource() instanceof JXTreeTable)
                                && ((((KeyEvent)event).getID() == KeyEvent.KEY_PRESSED)
                                    || (((KeyEvent)event).getID() == KeyEvent.KEY_RELEASED)
                                    || (((KeyEvent)event).getID() == KeyEvent.KEY_TYPED))) {
                        if (log.isDebugEnabled()) {
                            log.debug("eventDispatched: Key event intercepted from JXTreeTable in edit mode");
                        }
                        final int keyCode = ((KeyEvent)event).getKeyCode();
                        if (log.isDebugEnabled()) {
                            log.debug("eventDispatched: keycode: " + keyCode);
                        }
                        if ((keyCode == KeyEvent.VK_DOWN) || (keyCode == KeyEvent.VK_UP)) {
                            if (!((BelisBroker)broker).validateWidgets()) {
                                if (log.isDebugEnabled()) {
                                    log.debug("eventDispatched: One or more widgets are invalid. Informing user.");
                                }
                                final int anwser = ((BelisBroker)broker).askUser();
                                if (anwser == JOptionPane.YES_OPTION) {
                                    if (log.isDebugEnabled()) {
                                        log.debug("User wants to cancel changes.");
                                    }
                                } else {
                                    if (log.isDebugEnabled()) {
                                        log.debug("User wants to correct validation, consuming event.");
                                    }
                                    ((KeyEvent)event).consume();
                                }
                            } else {
                                if (log.isDebugEnabled()) {
                                    log.debug("eventDispatched: Not consuming event. All Widgets are valid");
                                }
                            }
                        } else {
                            if (log.isDebugEnabled()) {
                                log.debug("eventDispatched: neither up nor down arrow key");
                            }
                        }
                    }
                }
            }, AWTEvent.KEY_EVENT_MASK);
    }

    //~ Methods ----------------------------------------------------------------

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
    public TreePath getSelectedTreeNode() {
        return selectedTreeNode;
    }

    /**
     * Set the value of selectedTreeNode.
     *
     * @param  selectedTreeNode  new value of selectedTreeNode
     */
    public void setSelectedTreeNode(final TreePath selectedTreeNode) {
        final TreePath oldSelectedTreeNode = this.selectedTreeNode;
        if ((this.selectedTreeNode != null) && (selectedTreeNode != null)) {
            if (log.isDebugEnabled()) {
                log.debug("both treenodes != null");
            }
            if (this.selectedTreeNode.equals(selectedTreeNode)) {
                if (log.isDebugEnabled()) {
                    log.debug("treebnodes equals");
                }
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("treebnodes not equals");
                }
            }
        } else {
            if ((this.selectedTreeNode == null) && (selectedTreeNode == null)) {
                if (log.isDebugEnabled()) {
                    log.debug("both treenodes are null");
                }
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("one of the treenodes is null");
                }
            }
        }
        this.selectedTreeNode = selectedTreeNode;
        firePropertyChange(PROP_SELECTEDTREENODE, oldSelectedTreeNode, selectedTreeNode);
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
            if (pathToRoot != null) {
                if (log.isDebugEnabled()) {
                    log.debug("selecting: " + pathToRoot);
                }
                jttHitTable.getTreeSelectionModel().setSelectionPath(pathToRoot);
            }
        }
        // DefaultListSelectionModel
    }

    @Override
    public void setWidgetEditable(final boolean isEditable) {
        super.setWidgetEditable(isEditable);
        if (isEditable) {
            if (log.isDebugEnabled()) {
                log.debug("Setting " + getWidgetName() + "editable");
            }
            configureMapModeAccordingToSelection();
            if (((BelisBroker)broker).isInCreateMode()) {
                if (log.isDebugEnabled()) {
                    log.debug("Configuring Workbench for CreateMode. Removing search hits.");
                }
                setCurrentMode(CREATE_MODE);
                log.fatal("removing node:" + searchResultsNode);
                treeTableModel.removeNodeFromParent(searchResultsNode);
                treeTableModel.insertNodeInto(newObjectsNode, rootNode, rootNode.getChildCount());
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("Configuring Workbench for EditMode");
                }
                setCurrentMode(EDIT_MODE);
                // treeTableModel.removeNodeFromParent(newObjectsNode);
            }
        } else {
            if (getCurrentMode() == CREATE_MODE) {
                if (log.isDebugEnabled()) {
                    log.debug("Was in create mode switching to view mode.");
                }
                log.fatal("removing node:" + newObjectsNode);
                treeTableModel.removeNodeFromParent(newObjectsNode);
                treeTableModel.insertNodeInto(searchResultsNode, rootNode, rootNode.getChildCount());
                jttHitTable.expandPath(new TreePath(treeTableModel.getPathToRoot(searchResultsNode)));
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("Was in edit mode switching to view mode.");
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
        broker.getMappingComponent().addPropertyChangeListener(this);
        if (log.isDebugEnabled()) {
//        PNotificationCenter.defaultCenter().addListener(this,
//                null,
//                CreateGeometryListener.GEOMETRY_CREATED_NOTIFICATION,
//                null);
            log.debug("Configure binding TreeTableModel <--> SearchResults");
        }
        final org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                broker,
                org.jdesktop.beansbinding.ELProperty.create("${currentSearchResults}"),
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentSearchResults}"));
        // binding.setConverter(new SearchResultConverter());
        bindingGroup2.addBinding(binding);
//        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, broker, org.jdesktop.beansbinding.ELProperty.create("${newObjects}"), jttHitTable, org.jdesktop.beansbinding.ELProperty.create("${treeTableModel.newObjects}"));
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
//                ((BelisBroker) broker).setVetoCheckEnabled(false);
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
//                ((BelisBroker) broker).setVetoCheckEnabled(true);
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
     * mode"); // if(!((BelisBroker)broker).isVetoCheckEnabled()){ // log.debug("vetomode already disabled"); //
     * if(!isAlreadyDisabled){ // isAlreadyDisabled=true; // } // //only for savety //
     * //((BelisBroker)broker).setVetoCheckEnabled(false); // } else { // log.debug("disabling vetomode"); // //ToDo it
     * would be cool to make belis widegts for the core widgets so no dump casting. //
     * ((BelisBroker)broker).setVetoCheckEnabled(false); // } // } else { // if(isAlreadyDisabled){ // log.info("not
     * enabling veto check because there was an action before which disabled it"); // } else { // log.debug("reanabling
     * vetocheck and reseting flag"); // ((BelisBroker)broker).setVetoCheckEnabled(true); // isAlreadyDisabled=false; //
     * } // } ((BelisBroker)broker).setVetoCheckEnabled(isEnabled); }
     *
     * @return  DOCUMENT ME!
     */
    public boolean checkConstraintForMapModeSwitch() {
        TdtaStandortMastCustomBean virtualStandort = null;

        if (broker.isInEditMode()) {
            if (log.isDebugEnabled()) {
                log.debug("Application is in Editmode");
            }
            if ((getSelectedTreeNode() != null) && (getSelectedTreeNode().getLastPathComponent() != null)) {
                if (getSelectedTreeNode().getLastPathComponent() instanceof CustomMutableTreeTableNode) {
                    if (log.isDebugEnabled()) {
                        log.debug("Chosen object is CustomMutableTreeTableNode");
                    }
                    final Object userObject = ((CustomMutableTreeTableNode)getSelectedTreeNode().getLastPathComponent())
                                .getUserObject();
                    if (userObject != null) {
                        if (log.isDebugEnabled()) {
                            log.debug("userObject != null");
                        }
                        if (userObject instanceof GeoBaseEntity) {
                            if (log.isDebugEnabled()) {
                                log.debug("UserObject is instance of GeoBaseEntity");
                            }
                            if (((GeoBaseEntity)userObject).getGeometry() == null) {
                                if (log.isDebugEnabled()) {
                                    log.debug("Geometry is null swicht mode of Mapping Component");
                                }
                                return true;
                                    // broker.get.removeMainGroupSelection();
                            } else {
                                if (log.isDebugEnabled()) {
                                    log.debug("Geometry != null nothing to do");
                                }
                            }
                        } else if ((userObject instanceof TdtaLeuchteCustomBean)
                                    && ((virtualStandort = leuchteToVirtualStandortMap.get(
                                                    (TdtaLeuchteCustomBean)userObject))
                                        != null)
                                    && (virtualStandort instanceof TdtaStandortMastCustomBean)
                                    && !virtualStandort.isStandortMast()) {
                            if (log.isDebugEnabled()) {
                                log.debug("UserObject is Leuchte with virtual standort");
                            }
                            if (virtualStandort.getGeometry() == null) {
                                if (log.isDebugEnabled()) {
                                    log.debug(
                                        "Geometry of virtual standort is null, swithcing mode of Mapping Compoenent");
                                }
                                return true;
                            } else {
                                if (log.isDebugEnabled()) {
                                    log.debug("Geometry of virtual standort != null nothing to do");
                                }
                            }
                        } else {
                            if (log.isDebugEnabled()) {
                                log.debug("UserObject is not instance of GeoBaseEntity or Leuchte.");
                            }
                        }
                    } else {
                        if (log.isDebugEnabled()) {
                            log.debug("userObject == null");
                        }
                    }
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("No instance of CustomMutableTreeNode");
                    }
                }
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("LastPathComponent == null");
                }
            }
        } else {
            if (log.isDebugEnabled()) {
                log.debug("Application is not in Editmode");
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
            log.warn("Error while accessing user object. ", ex);
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
            log.warn("Error while accessing user object. ", ex);
            return null;
        }
    }
    /**
     * ToDo setSelected dosen't work.
     */
    private void addButtonToMapWidget() {
        if (log.isDebugEnabled()) {
            log.debug("adding attachMode button to MapWidget");
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
        broker.getMapWidget().addCustomButton(btnAttachMode);
    }

    /**
     * DOCUMENT ME!
     */
    private void configureMapModeAccordingToSelection() {
        if (checkConstraintForMapModeSwitch()) {
            try {
                ((BelisBroker)broker).setVetoCheckEnabled(false);
                broker.addFeatureSelectionChangeIgnore(this);
                ignoreFeatureSelection = true;
                if (log.isDebugEnabled()) {
                    log.debug("Constraints for ModeSwitch fullfied --> switching.", new CurrentStackTrace());
                }
                btnAttachMode.setEnabled(true);
                broker.getMapWidget().setLastMapMode(broker.getMapWidget().getCurrentMapMode());
                broker.getMapWidget().removeMainGroupSelection();
                broker.getMappingComponent().setInteractionMode(BELIS_CREATE_MODE);
                btnAttachMode.setSelected(true);
                final Object userObject = ((CustomMutableTreeTableNode)getSelectedTreeNode().getLastPathComponent())
                            .getUserObject();
                if (userObject instanceof TdtaLeuchteCustomBean) {
                    if (log.isDebugEnabled()) {
                        log.debug("Instance is Leuchte --> geometry == point");
                    }
                    ((CreateGeometryListener)broker.getMappingComponent().getInputListener(BELIS_CREATE_MODE)).setMode(
                        CreateGeometryListener.POINT);
                } else if (userObject instanceof TdtaStandortMastCustomBean) {
                    if (log.isDebugEnabled()) {
                        log.debug("Instance is Standort --> geometry == point");
                        log.debug(userObject);
                    }
                    ((CreateGeometryListener)broker.getMappingComponent().getInputListener(BELIS_CREATE_MODE)).setMode(
                        CreateGeometryListener.POINT);
                } else if (userObject instanceof MauerlascheCustomBean) {
                    if (log.isDebugEnabled()) {
                        log.debug("Instance is Mauerlasche --> geometry == point");
                        log.debug(userObject);
                    }
                    ((CreateGeometryListener)broker.getMappingComponent().getInputListener(BELIS_CREATE_MODE)).setMode(
                        CreateGeometryListener.POINT);
                } else if (userObject instanceof SchaltstelleCustomBean) {
                    if (log.isDebugEnabled()) {
                        log.debug("Instance is Schaltstelle --> geometry == point");
                        log.debug(userObject);
                    }
                    ((CreateGeometryListener)broker.getMappingComponent().getInputListener(BELIS_CREATE_MODE)).setMode(
                        CreateGeometryListener.POINT);
                } else if (userObject instanceof LeitungCustomBean) {
                    if (log.isDebugEnabled()) {
                        log.debug("Instance is Leitung --> geometry == line");
                        log.debug(userObject);
                    }
                    ((CreateGeometryListener)broker.getMappingComponent().getInputListener(BELIS_CREATE_MODE)).setMode(
                        CreateGeometryListener.LINESTRING);
                } else if (userObject instanceof AbzweigdoseCustomBean) {
                    if (log.isDebugEnabled()) {
                        log.debug("Instance is Abzweigdose --> geometry == point");
                        log.debug(userObject);
                    }
                    ((CreateGeometryListener)broker.getMappingComponent().getInputListener(BELIS_CREATE_MODE)).setMode(
                        CreateGeometryListener.POINT);
                }
            } catch (Exception ex) {
                log.error("Error while configuring map. Setting map back: ", ex);
                ((BelisBroker)broker).setVetoCheckEnabled(true);
            } finally {
                ((BelisBroker)broker).setVetoCheckEnabled(true);
                broker.removeFeatureSelectionChangeIgnore(this);
                ignoreFeatureSelection = false;
            }
        } else {
            try {
                ((BelisBroker)broker).setVetoCheckEnabled(false);
                if (log.isDebugEnabled()) {
                    log.debug("Constraints for ModeSwitch not fullfied");
                }
                if (broker.getMappingComponent().getInteractionMode().equals(BELIS_CREATE_MODE)) {
                    if (log.isDebugEnabled()) {
                        log.debug("Is already in: " + BELIS_CREATE_MODE + " mode. Switching back");
                    }
                    // ToDo save last mode; check if the map is in this mode --> switch to select TODO maybe an
                    // intelligent mode switching policy
                    btnAttachMode.setEnabled(false);
                    if ((broker.getMappingComponent().getInteractionMode() != null)
                                && broker.getMappingComponent().getInteractionMode().equals(BELIS_CREATE_MODE)) {
                        if (log.isDebugEnabled()) {
                            log.debug(
                                "False map mode setting is at the moment temp feature create. Mode will be set to lastmode: "
                                        + broker.getMapWidget().getLastMapMode());
                        }
                        broker.getMapWidget().setToLastInteractionMode();
                        // broker.getMappingComponent().setInteractionMode(MappingComponent.SELECT);
                    }
                }
            } finally {
                ((BelisBroker)broker).setVetoCheckEnabled(true);
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
        if (log.isDebugEnabled()) {
            log.debug("JTreeTable selection changend: " + e.getPaths());
        }
        try {
            try {
                if (log.isDebugEnabled()) {
                    log.debug("DetailWidget is valid: "
                                + (Validatable.VALID == ((BelisBroker)broker).getDetailWidget().getStatus()));
                }
            } catch (Exception ex) {
                if (log.isDebugEnabled()) {
                    log.debug("Error while checking validation state of detailWidget: ", ex);
                }
            }

            if (e.isAddedPath()) {
                if (log.isDebugEnabled()) {
                    log.debug("Path is added");
                }
                setSelectedTreeNode(e.getPath());
                configureMapModeAccordingToSelection();
                // Set feature selection

                if (isSelectedOverMap) {
                    if (log.isDebugEnabled()) {
                        log.debug("feature was selected over map. No need to select it in map.");
                    }
                } else {
                    ((BelisBroker)broker).setVetoCheckEnabled(false);
                    if (log.isDebugEnabled()) {
                        log.debug("feature was selected over table. Going to select feature in map.");
                    }

                    // ToDo method for extraction bad performance
                    final Object currentUserObject = getUserObjectForTreePath(e.getPath());
                    if ((currentUserObject != null) && (currentUserObject instanceof StyledFeature)
                                && (((StyledFeature)currentUserObject).getGeometry() != null)) {
                        if (log.isDebugEnabled()) {
                            log.debug(
                                "UserObject != null and instance of StyledFeature and geometry available --> select Feature");
                        }
                        ignoreFeatureSelection = true;
                        broker.addFeatureSelectionChangeIgnore(this);
                        broker.getMappingComponent().getFeatureCollection().select((StyledFeature)currentUserObject);
                    } else if (isParentNodeMast(e.getPath().getLastPathComponent())) {
                        if (log.isDebugEnabled()) {
                            log.debug("Leuchte from mast is selected in table.");
                        }
                        final TdtaStandortMastCustomBean parentMast = getParentMast(e.getPath().getLastPathComponent());
                        if ((broker.getMappingComponent().getFeatureCollection().getSelectedFeatures() != null)
                                    && (broker.getMappingComponent().getFeatureCollection().getSelectedFeatures()
                                        .size() == 1)
                                    && broker.getMappingComponent().getFeatureCollection().getSelectedFeatures()
                                    .iterator().next().equals(parentMast)) {
                            if (log.isDebugEnabled()) {
                                log.debug("Doing nothing mast is already selected");
                            }
                        } else {
                            if (log.isDebugEnabled()) {
                                log.debug("Selecting Mast in map.");
                            }
                            ignoreFeatureSelection = true;
                            broker.addFeatureSelectionChangeIgnore(this);
                            broker.getMappingComponent().getFeatureCollection().select((StyledFeature)parentMast);
                        }
                    } else if (isNodeHaengeLeuchte(e.getPath().getLastPathComponent())) {
                        if (log.isDebugEnabled()) {
                            log.debug(
                                "current selected node is haengeleuchte. Selecting corresponding standort in map: ");
                        }
                        broker.getMappingComponent()
                                .getFeatureCollection()
                                .select(leuchteToVirtualStandortMap.get(currentUserObject));
                    } else {
                        if (log.isDebugEnabled()) {
                            log.debug("no geometry to select --> unselect");
                        }
                        broker.addFeatureSelectionChangeIgnore(this);
                        broker.getMappingComponent().getFeatureCollection().unselectAll();
                    }
                }
                return;
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("Path is removed");
                    // ToDo if a current selected node is removed
                }
            }
            setSelectedTreeNode(null);
            configureMapModeAccordingToSelection();
        } finally {
            ((BelisBroker)broker).setVetoCheckEnabled(true);
            broker.removeFeatureSelectionChangeIgnore(this);
            ignoreFeatureSelection = false;
            isSelectedOverMap = false;
        }
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
            if ((userObject != null) && (userObject instanceof TdtaLeuchteCustomBean)) {
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
            if (log.isDebugEnabled()) {
                log.debug("Exception why checking if node is mast, therefore is no mast", ex);
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
            if (log.isDebugEnabled()) {
                log.debug("instance of Leuchte: "
                            + (((CustomMutableTreeTableNode)node).getUserObject() instanceof TdtaLeuchteCustomBean));
            }
            if (log.isDebugEnabled()) {
                log.debug("Parent is searchnode: "
                            + ((CustomMutableTreeTableNode)((CustomMutableTreeTableNode)node).getParent()).equals(
                                searchResultsNode));
            }
            return (((CustomMutableTreeTableNode)node).getUserObject() instanceof TdtaLeuchteCustomBean)
                        && (((CustomMutableTreeTableNode)((CustomMutableTreeTableNode)node).getParent()).equals(
                                searchResultsNode)
                            || ((CustomMutableTreeTableNode)((CustomMutableTreeTableNode)node).getParent()).equals(
                                newObjectsNode));
        } catch (Exception ex) {
            if (log.isDebugEnabled()) {
                log.debug("Exception why checking if node is haengeleuchte, therefore is no haengeleuchte", ex);
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
            if (log.isDebugEnabled()) {
                log.debug("Exception while getting parent mast. Does not exist", ex);
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
            if (features.size() == 0) {
                return;
            }
            try {
                ((BelisBroker)broker).setVetoCheckEnabled(false);
                for (final Feature feature : features) {
                    if (log.isDebugEnabled()) {
                        log.debug("currentFeature: " + feature);
                    }
                    if ((feature instanceof GeoBaseEntity)
                                && broker.getMappingComponent().getFeatureCollection().isSelected(feature)) {
                        // TODO Refactor Name int index = tableModel.getIndexOfReBe((ReBe) feature); int displayedIndex
                        // = ((JXTable) tReBe).getFilters().convertRowIndexToView(index); if (index != -1 &&
                        // LagisBroker.getInstance().getMappingComponent().getFeatureCollection().isSelected(feature)) {
                        // //tReBe.changeSelection(((JXTable)tReBe).getFilters().convertRowIndexToView(index),0,false,false);
                        // tReBe.getSelectionModel().addSelectionInterval(displayedIndex, displayedIndex); Rectangle tmp
                        // = tReBe.getCellRect(displayedIndex, 0, true); if (tmp != null) {
                        // tReBe.scrollRectToVisible(tmp); } } else {
                        // tReBe.getSelectionModel().removeSelectionInterval(displayedIndex, displayedIndex); }
                        TreePath path = null;
                        if ((feature instanceof TdtaStandortMastCustomBean)
                                    && !((TdtaStandortMastCustomBean)feature).isStandortMast()
                                    && (((TdtaStandortMastCustomBean)feature).getLeuchten() != null)
                                    && (((TdtaStandortMastCustomBean)feature).getLeuchten().size() > 0)) {
                            if (log.isDebugEnabled()) {
                                log.debug("virtual Standort selected, selecting depending leuchte");
                            }
                            path = treeTableModel.getPathForUserObject(((TdtaStandortMastCustomBean)feature)
                                            .getLeuchten().iterator().next());
                        } else {
                            path = treeTableModel.getPathForUserObject(feature);
                        }

                        if (path != null) {
                            if (log.isDebugEnabled()) {
                                log.debug("Path is available");
                            }
                            // jttHitTable.getSelectionMapper().setViewSelectionModel(arg0);
                            // jttHitTable.getTreeSelectionModel().removeTreeSelectionListener(this);
                            // ToDo dosen't work always
                            try {
                                if (log.isDebugEnabled()) {
                                    log.debug("selected over map", new CurrentStackTrace());
                                }
                                isSelectedOverMap = true;
                                jttHitTable.getTreeSelectionModel().setSelectionPath(path);
                            } catch (Exception ex) {
                                log.warn("couldn't set selection", ex);
                            }
                            // jttHitTable.getTreeSelectionModel().addTreeSelectionListener(this);
                            final Rectangle tmp = jttHitTable.getCellRect(jttHitTable.getSelectedRow(), 0, true);
                            if (tmp != null) {
                                jttHitTable.scrollRectToVisible(tmp);
                            }
                        } else {
                            if (log.isDebugEnabled()) {
                                log.debug("No Path for feature available");
                            }
                            jttHitTable.getSelectionMapper().getViewSelectionModel().clearSelection();
                        }
                    } else {
                        if (log.isDebugEnabled()) {
                            log.debug("No GeoBaseEntity or is already selected");
                        }
                        jttHitTable.getSelectionMapper().getViewSelectionModel().clearSelection();
                    }
                }
            } finally {
                ((BelisBroker)broker).setVetoCheckEnabled(true);
            }
        } else {
            if (log.isDebugEnabled()) {
                log.debug("FeatureSelection ignored", new CurrentStackTrace());
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  artifactType  DOCUMENT ME!
     */
    public void refreshTreeArtifacts(final int artifactType) {
        if (log.isDebugEnabled()) {
            log.debug("Is in EDT: " + EventQueue.isDispatchThread());
        }
        if (log.isDebugEnabled()) {
            log.debug("refreshTreeArtifacts");
        }
        saveSelectedElementAndUnselectAll();
        switch (artifactType) {
            case REFRESH_SEARCH_RESULTS: {
                if (log.isDebugEnabled()) {
                    log.debug("Refreshing searchResults");
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
                if (log.isDebugEnabled()) {
                    log.debug("adding new SearchResults");
                }
                if (searchResultsNode != null) {
                    for (final Object curObject : currentSearchResults) {
                        if (curObject instanceof TdtaStandortMastCustomBean) {
                            final CustomMutableTreeTableNode standortNode = new CustomMutableTreeTableNode(
                                    curObject,
                                    true);
                            final Collection<TdtaLeuchteCustomBean> leuchten = ((TdtaStandortMastCustomBean)curObject)
                                        .getLeuchten();
                            if (searchResultsNode != null) {
                                if (((TdtaStandortMastCustomBean)curObject).isStandortMast()) {
                                    if (log.isDebugEnabled()) {
                                        log.debug("Current Object is mast adding node to tree");
                                    }
                                    treeTableModel.insertNodeIntoAsLastChild(standortNode, searchResultsNode);
                                    ((TdtaStandortMastCustomBean)curObject).addPropertyChangeListener(this);
                                } else {
                                    if (log.isDebugEnabled()) {
                                        log.debug(
                                            "Current Object is standort for Hängeleuchte (virtual) not adding to tree");
                                    }
                                }
                                if (leuchten != null) {
                                    for (final TdtaLeuchteCustomBean curLeuchte : leuchten) {
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
                        } else {
                            final CustomMutableTreeTableNode foundObject = new CustomMutableTreeTableNode(
                                    curObject,
                                    true);
                            treeTableModel.insertNodeIntoAsLastChild(foundObject, searchResultsNode);
                        }
                    }
                    jttHitTable.expandPath(new TreePath(treeTableModel.getPathToRoot(searchResultsNode)));
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("searchResults == null");
                    }
                }
//                } else {
//                    log.debug("children enum == null");
//                }

                break;
            }
            case REFRESH_NEW_OBJECTS: {
                if (log.isDebugEnabled()) {
                    log.debug("Refreshing newObjects (newly created & newly saved)");
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
                    log.debug("remove all children of new objects");
                }
                treeTableModel.removeAllChildrenFromNode(newObjectsNode, false);
                if (log.isDebugEnabled()) {
                    log.debug("adding new Objects to node");
                }
                if (newObjects != null) {
                    for (final Object curObject : newObjects) {
                        if (curObject instanceof TdtaStandortMastCustomBean) {
                            final CustomMutableTreeTableNode standortNode = new CustomMutableTreeTableNode(
                                    curObject,
                                    true);
                            ((TdtaStandortMastCustomBean)curObject).addPropertyChangeListener(this);
                            final Collection<TdtaLeuchteCustomBean> leuchten = ((TdtaStandortMastCustomBean)curObject)
                                        .getLeuchten();
                            if (newObjectsNode != null) {
                                treeTableModel.insertNodeIntoAsLastChild(standortNode, newObjectsNode);
                                if (leuchten != null) {
                                    for (final TdtaLeuchteCustomBean curLeuchte : leuchten) {
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
                        } else {
                            final CustomMutableTreeTableNode foundObject = new CustomMutableTreeTableNode(
                                    curObject,
                                    true);
                            treeTableModel.insertNodeIntoAsLastChild(foundObject, newObjectsNode);
                        }
                    }
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("new objects == null");
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
                if (log.isDebugEnabled()) {
                    log.debug("refresh all");
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
                if (log.isDebugEnabled()) {
                    log.debug("Clear new objects");
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
                if (log.isDebugEnabled()) {
                    log.debug("Type is unkown, nothing to refresh.");
                }

                break;
            }
        }
        restoreSelectedElementIfPossible();
    }
    /**
     * ToDo remove propretychangelistener.
     *
     * @param  entity  DOCUMENT ME!
     */
    public void removeEntity(final Object entity) {
        if (log.isDebugEnabled()) {
            log.debug("remove Entity");
        }
        if (entity != null) {
            final TreePath pathToNodeToRemove = treeTableModel.getPathForUserObject(entity);
            if ((pathToNodeToRemove != null) && (pathToNodeToRemove.getLastPathComponent() != null)
                        && (pathToNodeToRemove.getLastPathComponent() instanceof CustomMutableTreeTableNode)) {
                final CustomMutableTreeTableNode nodeToRemove = (CustomMutableTreeTableNode)
                    pathToNodeToRemove.getLastPathComponent();
                saveSelectedElementAndUnselectAll();
                log.fatal("removing node:" + nodeToRemove);
                if (log.isDebugEnabled()) {
                    log.debug("instance leuchte: " + (nodeToRemove.getUserObject() instanceof TdtaLeuchteCustomBean));
                }
                if (log.isDebugEnabled()) {
                    log.debug("parent != null: " + (nodeToRemove.getParent() != null));
                }
                if (log.isDebugEnabled()) {
                    log.debug("parent instance standort: "
                                + (nodeToRemove.getParent() instanceof TdtaStandortMastCustomBean));
                }
                if (log.isDebugEnabled()) {
                    log.debug("parent childcoutn" + nodeToRemove.getParent().getChildCount());
                }
                // ToDo getParentMast for Leuchte Method
                if ((nodeToRemove.getUserObject() != null)
                            && (nodeToRemove.getUserObject() instanceof TdtaLeuchteCustomBean)
                            && (nodeToRemove.getParent() != null)
                            && (nodeToRemove.getParent() instanceof CustomMutableTreeTableNode)
                            && (((CustomMutableTreeTableNode)nodeToRemove.getParent()) != null)
                            && (((CustomMutableTreeTableNode)nodeToRemove.getParent()).getUserObject() != null)
                            && (((CustomMutableTreeTableNode)nodeToRemove.getParent()).getUserObject()
                                instanceof TdtaStandortMastCustomBean)) {
                    if (log.isDebugEnabled()) {
                        log.debug("Leuchte is removed from Mast");
                    }
                    final TdtaStandortMastCustomBean mast = (TdtaStandortMastCustomBean)
                        ((CustomMutableTreeTableNode)nodeToRemove.getParent()).getUserObject();
                    if (log.isDebugEnabled()) {
                        log.debug("Leuchte: " + (TdtaLeuchteCustomBean)nodeToRemove.getUserObject() + " from Mast: "
                                    + mast);
                    }
                    mast.getLeuchten().remove((TdtaLeuchteCustomBean)nodeToRemove.getUserObject());
                    if (log.isDebugEnabled()) {
                        log.debug("Leuchten of Mast: " + mast.getLeuchten());
                    }
                    if (log.isDebugEnabled()) {
                        log.debug("other mast leuchten ref: "
                                    + ((TdtaStandortMastCustomBean)
                                        ((CustomMutableTreeTableNode)nodeToRemove.getParent()).getUserObject())
                                    .getLeuchten());
                    }
                    ArrayList<TdtaLeuchteCustomBean> alreadyRemovedLeuchten = leuchtenRemovedFromMastMap.get(mast);
                    if (alreadyRemovedLeuchten == null) {
                        alreadyRemovedLeuchten = new ArrayList<TdtaLeuchteCustomBean>();
                        leuchtenRemovedFromMastMap.put(mast, alreadyRemovedLeuchten);
                    }
                    alreadyRemovedLeuchten.add((TdtaLeuchteCustomBean)nodeToRemove.getUserObject());
                    if (mast.getLeuchten().size() == 0) {
                        if (log.isDebugEnabled()) {
                            log.debug("The leucht was the last leuchte of the mast refreshing icon");
                        }
                        broker.getMappingComponent()
                                .getFeatureCollection()
                                .reconsiderFeature(((TdtaStandortMastCustomBean)
                                        ((CustomMutableTreeTableNode)nodeToRemove.getParent()).getUserObject()));
                    }
                } else if (isNodeHaengeLeuchte(nodeToRemove)) {
                    if (log.isDebugEnabled()) {
                        log.debug("Node which will be removed is a haengeleuchte. Removing also virtual Standort");
                    }
                    final TdtaStandortMastCustomBean virtualStandort = leuchteToVirtualStandortMap.get(
                            (TdtaLeuchteCustomBean)nodeToRemove.getUserObject());
                    if (virtualStandort != null) {
                        broker.getMappingComponent().getFeatureCollection().removeFeature(virtualStandort);
                        removedObjects.add(virtualStandort);
                    } else {
                        log.warn("No virtual standort found for leuchte");
                    }
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("Neither Mast Leuchte nor Hängeleuchte.");
                    }
                }
                treeTableModel.removeNodeFromParent(nodeToRemove);
                // removedNodes.add(nodeToRemove);
                if ((entity instanceof GeoBaseEntity) && (((GeoBaseEntity)entity).getGeometrie() != null)) {
                    if (log.isDebugEnabled()) {
                        log.debug("Entity has a geometry. Removing geometry from map");
                    }
                    broker.getMappingComponent().getFeatureCollection().removeFeature((GeoBaseEntity)entity);
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
                if (!(entity instanceof TdtaLeuchteCustomBean)) {
                    removedObjects.add(entity);
                }
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("Can't remove object from Tree, because there is no path to the node.");
                }
            }
            newObjects.remove(entity);
        } else {
            if (log.isDebugEnabled()) {
                log.debug("Can't remove object. The userobject == null.");
            }
        }
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
        if (log.isDebugEnabled()) {
            log.debug("setSearchResults");
        }
        this.currentSearchResults = currentSearchResults;
        if (!((BelisBroker)broker).isInCreateMode()) {
            if ((currentSearchResults != null) && (currentSearchResults.size() == 0)) {
                if (log.isDebugEnabled()) {
                    log.debug("0 Search results selecting search node");
                }
                selectNode(searchResultsNode);
            }
            refreshTreeArtifacts(REFRESH_SEARCH_RESULTS);
        } else {
            if (log.isDebugEnabled()) {
                log.debug("nothing to refresh because is in Create Mode");
            }
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
        if (log.isDebugEnabled()) {
            log.debug("setNewObjects");
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
        if (log.isDebugEnabled()) {
            log.debug("restoreRemovedObjects");
        }
        // ToDo I think this is no longer needed because if the search result modification is canceld --> refresh
        // And new objects dosen't matter because they will be eraesd
        if (leuchtenRemovedFromMastMap.keySet().size() > 0) {
            if (log.isDebugEnabled()) {
                log.debug("restoring removed leuchten");
            }
            for (final TdtaStandortMastCustomBean curMast : leuchtenRemovedFromMastMap.keySet()) {
                curMast.getLeuchten().addAll(leuchtenRemovedFromMastMap.get(curMast));
            }
        }
        leuchtenRemovedFromMastMap.clear();
        if (!((BelisBroker)broker).isInCreateMode()) {
            refreshTreeArtifacts(REFRESH_SEARCH_RESULTS);
            removedObjects.clear();
        }
    }
    /**
     * ToDo find a better name.
     */
    public void objectsRemoved() {
        if (log.isDebugEnabled()) {
            log.debug("objectsRemoved");
        }
        if (!((BelisBroker)broker).isInCreateMode()) {
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
        final TdtaStandortMastCustomBean newStandort = new TdtaStandortMastCustomBean();
        newStandort.setVerrechnungseinheit(true);

        if (((BelisBroker)broker).getDefaultUnterhaltMast() != null) {
            newStandort.setUnterhaltspflichtMast(((BelisBroker)broker).getDefaultUnterhaltMast());
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
        final TdtaStandortMastCustomBean newStandort = new TdtaStandortMastCustomBean();
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
                                .getUserObject() instanceof TdtaLeuchteCustomBean))
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
                if (log.isDebugEnabled()) {
                    log.debug("Related object is Leuchte (sibling)");
                }
            } else if ((pathToRelatedObejct != null) && (pathToRelatedObejct.getLastPathComponent() != null)
                        && (pathToRelatedObejct.getLastPathComponent() instanceof CustomMutableTreeTableNode)
                        && (((CustomMutableTreeTableNode)pathToRelatedObejct.getLastPathComponent()).getUserObject()
                            instanceof TdtaStandortMastCustomBean)) {
                if (log.isDebugEnabled()) {
                    log.debug("Related object is Standort (parent)");
                }
                parent = (TdtaStandortMastCustomBean)
                    ((CustomMutableTreeTableNode)pathToRelatedObejct.getLastPathComponent()).getUserObject();
                nodeToAddLeuchte = ((CustomMutableTreeTableNode)pathToRelatedObejct.getLastPathComponent());
            } else if (((pathToRelatedObejct == null) && (relatedObject != null)
                            && (relatedObject instanceof TdtaStandortMastCustomBean)
                            && ((((TdtaStandortMastCustomBean)relatedObject).isVirtuellerStandort() != null)
                                && ((TdtaStandortMastCustomBean)relatedObject).isVirtuellerStandort()))
                        || !((TdtaStandortMastCustomBean)relatedObject).isStandortMast()) {
                if (log.isDebugEnabled()) {
                    log.debug("Leuchte has virtual standort will be added directly to tree");
                }
                nodeToAddLeuchte = newObjectsNode;
                parent = (TdtaStandortMastCustomBean)relatedObject;
            } else {
                log.warn("Can't add Leuchte relatedObject is neither Leuchte nor Standort.");
            }
        } catch (Exception ex) {
            log.error("Error while trying to get node for related object", ex);
        }
        if ((nodeToAddLeuchte == null) || (parent == null)) {
            log.warn("Can't add Leuchte no node or standort found. Returning.");
            return null;
        }
        final TdtaLeuchteCustomBean newLeuchte = new TdtaLeuchteCustomBean();
        if (((BelisBroker)broker).getDefaultUnterhaltLeuchte() != null) {
            newLeuchte.setUnterhaltspflichtLeuchte(((BelisBroker)broker).getDefaultUnterhaltLeuchte());
        }
        if (((BelisBroker)broker).getDefaultDoppelkommando1() != null) {
            newLeuchte.setDk1(((BelisBroker)broker).getDefaultDoppelkommando1());
        }
        newLeuchte.addPropertyChangeListener(this);
        newLeuchte.addPropertyChangeListener((BelisBroker)broker);
        if (!parent.isStandortMast()) {
            if (log.isDebugEnabled()) {
                log.debug("Adding propterychange listener for virtual standort");
            }
            newLeuchte.addPropertyChangeListener(parent);
            leuchteToVirtualStandortMap.put(newLeuchte, parent);
            newLeuchte.setLeuchtennummer((Integer)0);
        } else {
            parent.addPropertyChangeListener(newLeuchte);
            newLeuchte.setLeuchtennummer(getNextLeuchtennummer(parent));
        }
        newLeuchte.setStandort(parent);
        // ToDo must also be set if these attributes are changed in the parent standort
        newLeuchte.setStrassenschluessel(parent.getStrassenschluessel());
        newLeuchte.setKennziffer(parent.getKennziffer());
        newLeuchte.setLaufendeNummer(parent.getLaufendeNummer());
        // ToDo attention if a new Leuchte is added to an already existing Standort it will be saved automatically
        // newObjects.add(newLeuchte);
        final CustomMutableTreeTableNode newLeuchteNode = new CustomMutableTreeTableNode(newLeuchte, false);
        // nodeToAddLeuchte.add(newLeuchteNode);
        // newLeuchteNode.setParent(nodeToAddLeuchte);
        Collection<TdtaLeuchteCustomBean> tmpLeuchten = parent.getLeuchten();
        boolean reconsiderFeature = false;
        if (tmpLeuchten == null) {
            if (log.isDebugEnabled()) {
                log.debug("standort hatte vorher noch keine leuchten");
            }
            reconsiderFeature = true;
            tmpLeuchten = new TreeSet(new LeuchteComparator());
            parent.setLeuchten(tmpLeuchten);
        }
        tmpLeuchten.add(newLeuchte);
        if (parent.getGeometry() != null) {
            if (log.isDebugEnabled()) {
                log.debug(
                    "standort has geometry. Reconsidering feature because the icon must be switched from standort without leuchte to standort with leuchte.");
            }
            broker.getMappingComponent().getFeatureCollection().reconsiderFeature(parent);
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
            for (final TdtaLeuchteCustomBean curLeuchte : standort.getLeuchten()) {
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
        final MauerlascheCustomBean newMauerlasche = new MauerlascheCustomBean();
        newMauerlasche.setStrassenschluessel(((BelisBroker)broker).getLastMauerlascheStrassenschluessel());
        newMauerlasche.addPropertyChangeListener((BelisBroker)broker);
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
        final SchaltstelleCustomBean newSchaltstelle = new SchaltstelleCustomBean();
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
        final LeitungCustomBean newLeitung = new LeitungCustomBean();
        newLeitung.setLeitungstyp(((BelisBroker)broker).getLastLeitungstyp());
        newLeitung.addPropertyChangeListener((BelisBroker)broker);
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
    public CustomMutableTreeTableNode addNewAbzweigdose() {
        final AbzweigdoseCustomBean newAbzweigdose = new AbzweigdoseCustomBean();
        final CustomMutableTreeTableNode newAbzweigdoseNode = new CustomMutableTreeTableNode(newAbzweigdose, true);
        newObjects.add(newAbzweigdoseNode.getUserObject());
        treeTableModel.insertNodeIntoAsLastChild(newAbzweigdoseNode, newObjectsNode);
        return newAbzweigdoseNode;
    }

    /**
     * DOCUMENT ME!
     */
    public void saveSelectedElementAndUnselectAll() {
        if (log.isDebugEnabled()) {
            log.debug("storing selection");
        }
        try {
            final TreePath tmpSelectedElement = jttHitTable.getTreeSelectionModel().getSelectionPath();
            jttHitTable.getTreeSelectionModel().clearSelection();
            selectedElement = tmpSelectedElement;
        } catch (Exception ex) {
            log.warn("Error while clearing selection");
        }
    }

    /**
     * DOCUMENT ME!
     */
    public void restoreSelectedElementIfPossible() {
        if (selectedElement != null) {
            if (log.isDebugEnabled()) {
                log.debug("restoring selection");
            }
            try {
                jttHitTable.getTreeSelectionModel().setSelectionPath(selectedElement);
            } catch (Exception ex) {
                log.warn("Error while restoring selection (Maybe The objects doesn't exist anymore)", ex);
                try {
                    jttHitTable.getTreeSelectionModel().clearSelection();
                } catch (Exception ex2) {
                    log.warn("Error while clearing selection", ex2);
                }
            }
        } else {
            if (log.isDebugEnabled()) {
                log.debug("no selection to restore");
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
    public TdtaStandortMastCustomBean getVirtualStandortForLeuchte(final TdtaLeuchteCustomBean leuchte) {
        return leuchteToVirtualStandortMap.get(leuchte);
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if (log.isDebugEnabled()) {
            log.debug("property of userobject changed refreshing table");
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
//            ((BelisBroker) broker).setVetoCheckEnabled(!((BelisBroker) broker).isVetoCheckEnabled());
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
        protected void finishGeometry(final PureNewFeature newFeature) {
            if (EventQueue.isDispatchThread()) {
                if (log.isDebugEnabled()) {
                    log.debug("is in EDT");
                }
            } else {
                log.fatal("is not in edt");
            }
            if (log.isDebugEnabled()) {
                log.debug("Pure new feature created for attachement: " + newFeature);
            }

            if ((newFeature != null) && (newFeature instanceof PureNewFeature) && checkConstraintForMapModeSwitch()) {
                if (log.isDebugEnabled()) {
                    log.debug("all prequisites fulfied for adding newly created geometry");
                }
                try {
                    tmpProcessStarted = true;
                    broker.addFeatureSelectionChangeIgnore(WorkbenchWidget.this);
                    ignoreFeatureSelection = true;
                    ((BelisBroker)broker).setVetoCheckEnabled(false);
                    // newFeature.setPrimaryAnnotationVisible(false);
                    // broker.getMappingComponent().reconsiderFeature(newFeature);
                    final Object tmpObject = ((CustomMutableTreeTableNode)getSelectedTreeNode().getLastPathComponent())
                                .getUserObject();
                    StyledFeature currentSelectedFeature = null;
                    if (tmpObject instanceof StyledFeature) {
                        currentSelectedFeature = (StyledFeature)tmpObject;
                    } else if (tmpObject instanceof TdtaLeuchteCustomBean) {
                        currentSelectedFeature = leuchteToVirtualStandortMap.get((TdtaLeuchteCustomBean)tmpObject);
                        if (currentSelectedFeature == null) {
                            log.warn("Leuchte has no virtual standort.");
                        }
                    }
                    currentSelectedFeature.setGeometry(newFeature.getGeometry());
                    // broker.getMappingComponent().getFeatureCollection().removeFeature(newFeature);
                    newlyAddedFeature = currentSelectedFeature;
                    broker.getMappingComponent().getFeatureCollection().addFeature(currentSelectedFeature);
                    btnAttachMode.setEnabled(false);
                    broker.getMapWidget().setToLastInteractionMode();
                    broker.getMappingComponent().getFeatureCollection().select(currentSelectedFeature);
                } finally {
                    ((BelisBroker)broker).setVetoCheckEnabled(true);
                    broker.removeFeatureSelectionChangeIgnore(WorkbenchWidget.this);
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
                if (log.isDebugEnabled()) {
                    log.debug("Not all prequisites fulfied for adding newly created geometry");
                }
            }
        }
    }
}
