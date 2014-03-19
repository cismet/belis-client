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
import org.jdesktop.swingx.treetable.MutableTreeTableNode;

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
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
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

import de.cismet.cids.custom.beans.belis2.AbzweigdoseCustomBean;
import de.cismet.cids.custom.beans.belis2.ArbeitsauftragCustomBean;
import de.cismet.cids.custom.beans.belis2.ArbeitsprotokollCustomBean;
import de.cismet.cids.custom.beans.belis2.GeometrieCustomBean;
import de.cismet.cids.custom.beans.belis2.LeitungCustomBean;
import de.cismet.cids.custom.beans.belis2.MauerlascheCustomBean;
import de.cismet.cids.custom.beans.belis2.SchaltstelleCustomBean;
import de.cismet.cids.custom.beans.belis2.TdtaLeuchtenCustomBean;
import de.cismet.cids.custom.beans.belis2.TdtaStandortMastCustomBean;
import de.cismet.cids.custom.beans.belis2.VeranlassungCustomBean;

import de.cismet.cismap.commons.features.AbstractNewFeature;
import de.cismet.cismap.commons.features.Feature;
import de.cismet.cismap.commons.features.FeatureCollection;
import de.cismet.cismap.commons.features.FeatureCollectionEvent;
import de.cismet.cismap.commons.features.FeatureCollectionListener;
import de.cismet.cismap.commons.features.PureNewFeature;
import de.cismet.cismap.commons.features.StyledFeature;
import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.CreateGeometryListener;

import de.cismet.commons.architecture.interfaces.FeatureSelectionChangedListener;
import de.cismet.commons.architecture.validation.Validatable;

import de.cismet.commons.server.entity.BaseEntity;
import de.cismet.commons.server.entity.GeoBaseEntity;

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

    private static final Logger LOG = org.apache.log4j.Logger.getLogger(WorkbenchWidget.class);
    public static final String PROP_SELECTEDTREENODES = "selectedTreeNodes";

    public static final String PROP_TREE_TABLE_MODEL = "treeTableModel";
    public static final String BELIS_CREATE_MODE = "BELIS_CREATE_MODE";
    public static final int VIEW_MODE = 0;
    public static final int CREATE_MODE = 1;
    public static final int EDIT_MODE = 2;

    //~ Instance fields --------------------------------------------------------

    BindingGroup bindingGroup2 = new BindingGroup();

    private boolean ignoreFeatureSelection = false;
    private Collection<TreePath> selectedTreeNodes = null;
    private final CustomMutableTreeTableNode rootNode = new CustomMutableTreeTableNode(null, true);
    private final CustomMutableTreeTableNode searchResultsNode = new CustomMutableTreeTableNode(null, true);
    private final CustomMutableTreeTableNode newObjectsNode = new CustomMutableTreeTableNode(null, true);
    private final CustomMutableTreeTableNode editObjectsNode = new CustomMutableTreeTableNode(null, true);
    private CustomTreeTableModel treeTableModel = null;
    private HashMap<TdtaLeuchtenCustomBean, TdtaStandortMastCustomBean> leuchteToVirtualStandortMap = new HashMap();
    private JButton btnAttachMode = new JButton();
    private Collection<BaseEntity> currentSearchResults = new TreeSet(new ReverseComparator(new EntityComparator()));
    private Set<BaseEntity> objectsToPersist = new TreeSet(new ReverseComparator(new EntityComparator()));
    private final Collection<BaseEntity> objectsToDelete = new ArrayList<BaseEntity>();
    private int currentMode = 0;
    private boolean isSelectedOverMap = false;
    private TreePath selectedElement = null;
    private Set searchResults = null;
    private List<TreeSelectionListener> treeSelectionListener = new ArrayList<TreeSelectionListener>();
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

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public CustomMutableTreeTableNode getSearchResultsNode() {
        return searchResultsNode;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public CustomMutableTreeTableNode getNewObjectsNode() {
        return newObjectsNode;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public CustomMutableTreeTableNode getEditObjectsNode() {
        return editObjectsNode;
    }

    @Override
    public void setBroker(final BelisBroker broker) {
        super.setBroker(broker);

        initComponents();
        jttHitTable.setDragEnabled(true);

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

        treeTableModel = new CustomTreeTableModel(getBroker(), rootNode);

        searchResultsNode.setUserObject(CustomTreeTableModel.HIT_NODE);
        newObjectsNode.setUserObject(CustomTreeTableModel.NEW_OBJECT_NODE);
        editObjectsNode.setUserObject(CustomTreeTableModel.EDIT_OBJECT_NODE);

        treeTableModel.insertNodeIntoAsLastChild(searchResultsNode, rootNode);

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
                                            && veranlassungCustomBean.getAr_geometrien()
                                            .isEmpty();
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
        jttHitTable.setTransferHandler(new WorkbenchTransferHandler());
        jttHitTable.setDropMode(DropMode.ON);
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

    /**
     * DOCUMENT ME!
     *
     * @param  userObjects  DOCUMENT ME!
     */
    public void selectUserObjects(final Collection userObjects) {
        jttHitTable.getTreeSelectionModel().clearSelection();
        for (final Object userObject : userObjects) {
            final TreePath pathToEntity = getTreeTableModel().getPathForUserObject(userObject);
            jttHitTable.getTreeSelectionModel().addSelectionPath(pathToEntity);
        }
    }

    @Override
    public void setWidgetEditable(final boolean isEditable) {
        super.setWidgetEditable(isEditable);

        if (isEditable) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Setting " + getWidgetName() + "editable");
            }
            configureMapModeAccordingToSelection();
            if (getBroker().isInCreateMode()) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Configuring Workbench for CreateMode. ");
                }
                setCurrentMode(CREATE_MODE);

                if (LOG.isDebugEnabled()) {
                    LOG.debug("removing node:" + searchResultsNode);
                }
                treeTableModel.removeNodeFromParent(searchResultsNode);
                treeTableModel.insertNodeIntoAsLastChild(newObjectsNode, rootNode);
                treeTableModel.insertNodeIntoAsLastChild(searchResultsNode, rootNode);

                jttHitTable.expandPath(new TreePath(treeTableModel.getPathToRoot(newObjectsNode)));
                jttHitTable.getTreeSelectionModel()
                        .setSelectionPath(new TreePath(treeTableModel.getPathToRoot(newObjectsNode)));
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Configuring Workbench for EditMode");
                }
                setCurrentMode(EDIT_MODE);

                treeTableModel.removeNodeFromParent(searchResultsNode);
                treeTableModel.insertNodeIntoAsLastChild(editObjectsNode, rootNode);
                treeTableModel.insertNodeIntoAsLastChild(searchResultsNode, rootNode);
                final Collection<MutableTreeTableNode> nodes = new ArrayList<MutableTreeTableNode>();
                for (int i = 0; i < searchResultsNode.getChildCount(); i++) {
                    final MutableTreeTableNode node = (MutableTreeTableNode)searchResultsNode.getChildAt(i);
                    nodes.add(node);
                }
                for (final MutableTreeTableNode node : nodes) {
                    treeTableModel.insertNodeIntoAsLastChild(node, editObjectsNode);
                    objectsToPersist.add((BaseEntity)node.getUserObject());
                }
                jttHitTable.expandPath(new TreePath(treeTableModel.getPathToRoot(editObjectsNode)));
            }
            jttHitTable.expandPath(new TreePath(treeTableModel.getPathToRoot(searchResultsNode)));
        } else {
            if (getCurrentMode() == CREATE_MODE) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Was in create mode switching to view mode.");
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("removing node:" + newObjectsNode);
                }
                treeTableModel.removeNodeFromParent(newObjectsNode);
                treeTableModel.removeNodeFromParent(searchResultsNode);
                treeTableModel.insertNodeIntoAsLastChild(searchResultsNode, rootNode);

                jttHitTable.expandPath(new TreePath(treeTableModel.getPathToRoot(searchResultsNode)));
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Was in edit mode switching to view mode.");
                }

                treeTableModel.removeAllChildrenFromNode(editObjectsNode, false);
                treeTableModel.removeNodeFromParent(editObjectsNode);
                objectsToPersist.clear();
                if (treeTableModel.getPathForUserObject(searchResultsNode.getUserObject()) == null) {
                    treeTableModel.insertNodeIntoAsLastChild(searchResultsNode, rootNode);
                }
            }

            setCurrentMode(VIEW_MODE);
            refreshSearchObjects();
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
        bindingGroup2.addBinding(binding);
        bindingGroup2.bind();
    }

    @Override
    public void updateUIPropertyChange() {
        super.updateUIPropertyChange();
        jttHitTable.repaint();
    }

    /**
     * DOCUMENT ME!
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
                } else if (userObject instanceof GeometrieCustomBean) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Instance is Geometrie --> geometry == polygon");
                        LOG.debug(userObject);
                    }
                    ((CreateGeometryListener)getBroker().getMappingComponent().getInputListener(BELIS_CREATE_MODE))
                            .setMode(
                                CreateGeometryListener.POLYGON);
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

        jttHitTable.setDragEnabled(true);
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
                                        if (LOG.isDebugEnabled()) {
                                            LOG.debug("Selecting Mast in map.");
                                        }
                                        featuresToSelect.add((StyledFeature)parentMast);
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
                                        featuresToSelect.addAll(veranlassungCustomBean.getAr_geometrien());
                                    } else if (currentUserObject instanceof ArbeitsauftragCustomBean) {
                                        final ArbeitsauftragCustomBean arbeitsauftragCustomBean =
                                            (ArbeitsauftragCustomBean)currentUserObject;
                                        for (final ArbeitsprotokollCustomBean ap
                                                    : arbeitsauftragCustomBean.getAr_protokolle()) {
                                            selectArbeitsprotokollCustomBean(featuresToSelect, ap);
                                        }
                                    } else if (currentUserObject instanceof ArbeitsprotokollCustomBean) {
                                        final ArbeitsprotokollCustomBean apCustomBean = (ArbeitsprotokollCustomBean)
                                            currentUserObject;
                                        selectArbeitsprotokollCustomBean(featuresToSelect, apCustomBean);
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
                        fireTreeSelectionChangedEvent(e);
                    }
                }

                private void selectArbeitsprotokollCustomBean(final Collection<Feature> featuresToSelect,
                        final ArbeitsprotokollCustomBean ap) {
                    if (ap.getFk_abzweigdose() != null) {
                        featuresToSelect.add(ap.getFk_abzweigdose());
                    }
                    if (ap.getFk_geometrie() != null) {
                        featuresToSelect.add(ap.getFk_geometrie());
                    }
                    if (ap.getFk_leitung() != null) {
                        featuresToSelect.add(ap.getFk_leitung());
                    }
                    if (ap.getFk_leuchte() != null) {
                        featuresToSelect.add(ap.getFk_leuchte());
                    }
                    if (ap.getFk_mauerlasche() != null) {
                        featuresToSelect.add(ap.getFk_mauerlasche());
                    }
                    if (ap.getFk_schaltstelle() != null) {
                        featuresToSelect.add(ap.getFk_schaltstelle());
                    }
                    if (ap.getFk_standort() != null) {
                        featuresToSelect.add(ap.getFk_standort());
                    }
                }
            });
    }

    /**
     * DOCUMENT ME!
     *
     * @param  e  DOCUMENT ME!
     */
    private void fireTreeSelectionChangedEvent(final TreeSelectionEvent e) {
        for (final TreeSelectionListener listener : treeSelectionListener) {
            listener.valueChanged(e);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  listener  DOCUMENT ME!
     */
    public void addTreeSeleletionListener(final TreeSelectionListener listener) {
        treeSelectionListener.add(listener);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  listener  DOCUMENT ME!
     */
    public void removeTreeSeleletionListener(final TreeSelectionListener listener) {
        treeSelectionListener.remove(listener);
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
                            }
                            paths.add(path);
                        }
                    }
                }

                jttHitTable.getSelectionModel().clearSelection();
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
        }
    }

    /**
     * DOCUMENT ME!
     */
    public void refreshSearchObjects() {
        saveSelectedElementAndUnselectAll();
        refreshNode(searchResultsNode, currentSearchResults);
        restoreSelectedElementIfPossible();
    }

    /**
     * DOCUMENT ME!
     */
    public void refreshPersistObjects() {
        saveSelectedElementAndUnselectAll();
        refreshNode(newObjectsNode, objectsToPersist);
        restoreSelectedElementIfPossible();
    }

    /**
     * DOCUMENT ME!
     */
    public void refreshAll() {
        saveSelectedElementAndUnselectAll();
        refreshNode(newObjectsNode, objectsToPersist);
        refreshNode(searchResultsNode, currentSearchResults);
        restoreSelectedElementIfPossible();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  refreshNode  DOCUMENT ME!
     * @param  objects      DOCUMENT ME!
     */
    public void refreshNode(final CustomMutableTreeTableNode refreshNode, final Collection<BaseEntity> objects) {
        treeTableModel.removeAllChildrenFromNode(refreshNode, false);
        if (LOG.isDebugEnabled()) {
            LOG.debug("adding new SearchResults");
        }
        if (refreshNode != null) {
            for (final Object curObject : objects) {
                if (curObject instanceof TdtaStandortMastCustomBean) {
                    final CustomMutableTreeTableNode standortNode = new CustomMutableTreeTableNode(
                            curObject,
                            true);
                    final Collection<TdtaLeuchtenCustomBean> leuchten = ((TdtaStandortMastCustomBean)curObject)
                                .getLeuchten();
                    if (((TdtaStandortMastCustomBean)curObject).isStandortMast()) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Current Object is mast adding node to tree");
                        }
                        treeTableModel.insertNodeIntoAsLastChild(standortNode, refreshNode);
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
                            if (((TdtaStandortMastCustomBean)curObject).isStandortMast()) {
                                treeTableModel.insertNodeIntoAsLastChild(leuchteNode, standortNode);
                                ((TdtaStandortMastCustomBean)curObject).addPropertyChangeListener(
                                    curLeuchte);
                            } else {
                                treeTableModel.insertNodeIntoAsLastChild(leuchteNode, refreshNode);
                                leuchteToVirtualStandortMap.put(
                                    curLeuchte,
                                    (TdtaStandortMastCustomBean)curObject);
                            }
                        }
                    }
                } else if (curObject instanceof VeranlassungCustomBean) {
                    final VeranlassungCustomBean veranlassungCustomBean = (VeranlassungCustomBean)curObject;
                    final CustomMutableTreeTableNode veranlassungNode = new CustomMutableTreeTableNode(
                            veranlassungCustomBean,
                            true);

                    treeTableModel.insertNodeIntoAsLastChild(veranlassungNode, refreshNode);

                    final Set<BaseEntity> subEntities = new TreeSet(new EntityComparator());

                    subEntities.addAll(veranlassungCustomBean.getAr_standorte());
                    subEntities.addAll(veranlassungCustomBean.getAr_leuchten());
                    subEntities.addAll(veranlassungCustomBean.getAr_schaltstellen());
                    subEntities.addAll(veranlassungCustomBean.getAr_mauerlaschen());
                    subEntities.addAll(veranlassungCustomBean.getAr_leitungen());
                    subEntities.addAll(veranlassungCustomBean.getAr_abzweigdosen());
                    subEntities.addAll(veranlassungCustomBean.getAr_geometrien());
                    for (final BaseEntity subEntity : subEntities) {
                        treeTableModel.insertNodeIntoAsLastChild(new CustomMutableTreeTableNode(
                                subEntity,
                                false),
                            veranlassungNode);
                    }
                } else if (curObject instanceof ArbeitsauftragCustomBean) {
                    final ArbeitsauftragCustomBean arbeitsauftragCustomBean = (ArbeitsauftragCustomBean)curObject;
                    final CustomMutableTreeTableNode arbeitsauftragNode = new CustomMutableTreeTableNode(
                            arbeitsauftragCustomBean,
                            true);
                    treeTableModel.insertNodeIntoAsLastChild(arbeitsauftragNode, refreshNode);
                    for (final ArbeitsprotokollCustomBean protokoll
                                : arbeitsauftragCustomBean.getAr_protokolle()) {
                        final AbzweigdoseCustomBean abzweigdose = protokoll.getFk_abzweigdose();
                        final LeitungCustomBean leitung = protokoll.getFk_leitung();
                        final TdtaLeuchtenCustomBean leuchte = protokoll.getFk_leuchte();
                        final TdtaStandortMastCustomBean standort = protokoll.getFk_standort();
                        final MauerlascheCustomBean mauerlasche = protokoll.getFk_mauerlasche();
                        final SchaltstelleCustomBean schaltstelle = protokoll.getFk_schaltstelle();
                        final GeometrieCustomBean geometrie = protokoll.getFk_geometrie();
                        final CustomMutableTreeTableNode node;
                        if (abzweigdose != null) {
                            node = new CustomMutableTreeTableNode(abzweigdose, false);
                        } else if (leitung != null) {
                            node = new CustomMutableTreeTableNode(leitung, false);
                        } else if (leuchte != null) {
                            node = new CustomMutableTreeTableNode(leuchte, false);
                        } else if (standort != null) {
                            node = new CustomMutableTreeTableNode(standort, false);
                        } else if (mauerlasche != null) {
                            node = new CustomMutableTreeTableNode(mauerlasche, false);
                        } else if (schaltstelle != null) {
                            node = new CustomMutableTreeTableNode(schaltstelle, false);
                        } else if (geometrie != null) {
                            node = new CustomMutableTreeTableNode(geometrie, false);
                        } else {
                            node = null;
                        }
                        if (node != null) {
                            final CustomMutableTreeTableNode protokollNode = new CustomMutableTreeTableNode(
                                    protokoll,
                                    true);
                            treeTableModel.insertNodeIntoAsLastChild(protokollNode, arbeitsauftragNode);
                            treeTableModel.insertNodeIntoAsLastChild(node, protokollNode);
                        }
                    }
                } else {
                    final CustomMutableTreeTableNode foundObject = new CustomMutableTreeTableNode(
                            curObject,
                            true);
                    treeTableModel.insertNodeIntoAsLastChild(foundObject, refreshNode);
                }
            }
            try {
                jttHitTable.expandPath(new TreePath(treeTableModel.getPathToRoot(refreshNode)));
            } catch (final Exception ex) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug(ex, ex);
                }
            }
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("searchResults == null");
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  node  DOCUMENT ME!
     */
    public void clearPersistNode(final CustomMutableTreeTableNode node) {
    }

    /**
     * ToDo remove propretychangelistener.
     */
    public void removeSelectedEntity() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("remove Entity");
        }
        final TreePath pathToNodeToRemove = BelisBroker.getInstance().getWorkbenchWidget().getSelectedTreeNode();
        final Object entity = ((CustomMutableTreeTableNode)pathToNodeToRemove.getLastPathComponent()).getUserObject();

        if ((pathToNodeToRemove.getLastPathComponent() != null)
                    && (pathToNodeToRemove.getLastPathComponent() instanceof CustomMutableTreeTableNode)) {
            final CustomMutableTreeTableNode nodeToRemove = (CustomMutableTreeTableNode)
                pathToNodeToRemove.getLastPathComponent();
            saveSelectedElementAndUnselectAll();

            if ((nodeToRemove.getUserObject() != null) && (nodeToRemove.getParent() != null)
                        && (nodeToRemove.getParent() instanceof CustomMutableTreeTableNode)
                        && (((CustomMutableTreeTableNode)nodeToRemove.getParent()) != null)
                        && (((CustomMutableTreeTableNode)nodeToRemove.getParent()).getUserObject() != null)
                        && (((CustomMutableTreeTableNode)nodeToRemove.getParent()).getUserObject()
                            instanceof VeranlassungCustomBean)) {
                final VeranlassungCustomBean veranlassung = (VeranlassungCustomBean)
                    ((CustomMutableTreeTableNode)nodeToRemove.getParent()).getUserObject();
                if (nodeToRemove.getUserObject() instanceof TdtaLeuchtenCustomBean) {
                    veranlassung.getAr_leuchten().remove((TdtaLeuchtenCustomBean)nodeToRemove.getUserObject());
                    objectsToDelete.add((BaseEntity)nodeToRemove.getUserObject());
                } else if (nodeToRemove.getUserObject() instanceof TdtaStandortMastCustomBean) {
                    veranlassung.getAr_standorte().remove((TdtaStandortMastCustomBean)nodeToRemove.getUserObject());
                    objectsToDelete.add((BaseEntity)nodeToRemove.getUserObject());
                } else if (nodeToRemove.getUserObject() instanceof MauerlascheCustomBean) {
                    veranlassung.getAr_mauerlaschen().remove((MauerlascheCustomBean)nodeToRemove.getUserObject());
                    objectsToDelete.add((BaseEntity)nodeToRemove.getUserObject());
                } else if (nodeToRemove.getUserObject() instanceof LeitungCustomBean) {
                    veranlassung.getAr_leitungen().remove((LeitungCustomBean)nodeToRemove.getUserObject());
                    objectsToDelete.add((BaseEntity)nodeToRemove.getUserObject());
                } else if (nodeToRemove.getUserObject() instanceof AbzweigdoseCustomBean) {
                    veranlassung.getAr_abzweigdosen().remove((AbzweigdoseCustomBean)nodeToRemove.getUserObject());
                    objectsToDelete.add((BaseEntity)nodeToRemove.getUserObject());
                } else if (nodeToRemove.getUserObject() instanceof SchaltstelleCustomBean) {
                    veranlassung.getAr_schaltstellen().remove((SchaltstelleCustomBean)nodeToRemove.getUserObject());
                    objectsToDelete.add((BaseEntity)nodeToRemove.getUserObject());
                } else if (nodeToRemove.getUserObject() instanceof GeometrieCustomBean) {
                    veranlassung.getAr_geometrien().remove((GeometrieCustomBean)nodeToRemove.getUserObject());
                    objectsToDelete.add((BaseEntity)nodeToRemove.getUserObject());
                }
            } else if ((nodeToRemove.getUserObject() != null) && (nodeToRemove.getParent() != null)
                        && (nodeToRemove.getParent() instanceof CustomMutableTreeTableNode)
                        && (((CustomMutableTreeTableNode)nodeToRemove.getParent()) != null)
                        && (((CustomMutableTreeTableNode)nodeToRemove.getParent()).getUserObject() != null)
                        && (((CustomMutableTreeTableNode)nodeToRemove.getParent()).getUserObject()
                            instanceof ArbeitsprotokollCustomBean)) {
                final ArbeitsauftragCustomBean auftrag = (ArbeitsauftragCustomBean)
                    ((CustomMutableTreeTableNode)nodeToRemove.getParent()).getUserObject();
                final ArbeitsprotokollCustomBean protokoll = (ArbeitsprotokollCustomBean)
                    ((CustomMutableTreeTableNode)nodeToRemove.getParent()).getUserObject();
                auftrag.getAr_protokolle().remove(protokoll);
                objectsToDelete.add(protokoll);
            } else if ((nodeToRemove.getUserObject() != null)
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
                    LOG.debug("other mast leuchten ref: "
                                + ((TdtaStandortMastCustomBean)((CustomMutableTreeTableNode)nodeToRemove.getParent())
                                    .getUserObject()).getLeuchten());
                }
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
                    objectsToDelete.add(virtualStandort);
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
            if (!(entity instanceof TdtaLeuchtenCustomBean)) {
                objectsToDelete.add((BaseEntity)entity);
            }
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Can't remove object from Tree, because there is no path to the node.");
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
        if (LOG.isDebugEnabled()) {
            LOG.debug("setSearchResults");
        }
        this.currentSearchResults = currentSearchResults;
        refreshSearchObjects();
        // propertyChangeSupport.firePropertyChange(PROP_CURRENT_SEARCH_RESULTS, null, currentSearchResults);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Set<BaseEntity> getObjectsToPersist() {
        return objectsToPersist;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  objectsToPersist  DOCUMENT ME!
     */
    public void setObjectsToPersist(final Set objectsToPersist) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("setObjectsToPersist");
        }
        this.objectsToPersist = objectsToPersist;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Collection<BaseEntity> getObjectsToDelete() {
        return objectsToDelete;
    }

    /**
     * DOCUMENT ME!
     */
    public void clearNewNode() {
        saveSelectedElementAndUnselectAll();
        treeTableModel.removeAllChildrenFromNode(newObjectsNode, true);
        objectsToPersist.clear();
        leuchteToVirtualStandortMap.clear();
        restoreSelectedElementIfPossible();
    }

    /**
     * DOCUMENT ME!
     */
    public void clearEditNode() {
        saveSelectedElementAndUnselectAll();
        treeTableModel.removeAllChildrenFromNode(editObjectsNode, true);
        objectsToPersist.clear();
        leuchteToVirtualStandortMap.clear();
        restoreSelectedElementIfPossible();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  entity  DOCUMENT ME!
     */
    public void addNewEntity(final BaseEntity entity) {
        final CustomMutableTreeTableNode entityNode = new CustomMutableTreeTableNode(entity, true);
        objectsToPersist.add((BaseEntity)entityNode.getUserObject());
        treeTableModel.insertNodeIntoAsLastChild(entityNode, newObjectsNode);
        selectNode(entityNode);
    }

    /**
     * DOCUMENT ME!
     */
    public void addNewLeuchte() {
        final TdtaStandortMastCustomBean newStandort = TdtaStandortMastCustomBean.createNew();
        newStandort.addPropertyChangeListener(this);
        newStandort.setVirtuellerStandort(true);
        newStandort.setVerrechnungseinheit(true);
        // final CustomMutableTreeTableNode newStandortNode = new CustomMutableTreeTableNode(newStandort, true);
        objectsToPersist.add(newStandort);
        // treeTableModel.insertNodeIntoAsLastChild(newStandortNode, newObjectsNode);
        addNewLeuchte(newStandort);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  relatedObject  DOCUMENT ME!
     */
    public void addNewLeuchte(final Object relatedObject) {
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
            return;
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
        final CustomMutableTreeTableNode newLeuchteNode = new CustomMutableTreeTableNode(newLeuchte, false);
        if (parent.getGeometry() != null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(
                    "standort has geometry. Reconsidering feature because the icon must be switched from standort without leuchte to standort with leuchte.");
            }
            getBroker().getMappingComponent().getFeatureCollection().removeFeature(parent);
            getBroker().getMappingComponent().getFeatureCollection().addFeature(parent);
        }
        treeTableModel.insertNodeIntoAsLastChild(newLeuchteNode, nodeToAddLeuchte);
        selectNode(newLeuchteNode);
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
     * DOCUMENT ME!
     */
    public void addNewGeometrie() {
        final Object tmpObject = ((CustomMutableTreeTableNode)getSelectedTreeNode().getLastPathComponent())
                    .getUserObject();
        if ((tmpObject instanceof VeranlassungCustomBean) || (tmpObject instanceof ArbeitsauftragCustomBean)) {
            final GeometrieCustomBean newGeometrie = GeometrieCustomBean.createNew();
            newGeometrie.addPropertyChangeListener(getBroker());
            newGeometrie.addPropertyChangeListener(this);
            if (tmpObject instanceof VeranlassungCustomBean) {
                final CustomMutableTreeTableNode newGeometrieNode = new CustomMutableTreeTableNode(newGeometrie, true);
                final VeranlassungCustomBean selVeranlassung = ((VeranlassungCustomBean)tmpObject);
                selVeranlassung.getAr_geometrien().add(newGeometrie);
                treeTableModel.insertNodeIntoAsLastChild(
                    newGeometrieNode,
                    (CustomMutableTreeTableNode)getSelectedTreeNode().getLastPathComponent());
                selectNode(newGeometrieNode);
            } else if (tmpObject instanceof ArbeitsauftragCustomBean) {
                final ArbeitsprotokollCustomBean newProtokoll = ArbeitsprotokollCustomBean.createNew();
                final CustomMutableTreeTableNode newProtokollNode = new CustomMutableTreeTableNode(newProtokoll, true);
                final CustomMutableTreeTableNode newGeometrieNode = new CustomMutableTreeTableNode(newGeometrie, true);
                final ArbeitsauftragCustomBean selAuftrag = ((ArbeitsauftragCustomBean)tmpObject);
                newProtokoll.setFk_geometrie(newGeometrie);
                selAuftrag.getAr_protokolle().add(newProtokoll);
                treeTableModel.insertNodeIntoAsLastChild(
                    newProtokollNode,
                    (CustomMutableTreeTableNode)getSelectedTreeNode().getLastPathComponent());
                treeTableModel.insertNodeIntoAsLastChild(
                    newGeometrieNode,
                    newProtokollNode);
                selectNode(newProtokollNode);
            }
        }
    }

    /**
     * DOCUMENT ME!
     */
    public void saveSelectedElementAndUnselectAll() {
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug("storing selection");
            }
            selectedElement = jttHitTable.getTreeSelectionModel().getSelectionPath();
            jttHitTable.getTreeSelectionModel().clearSelection();
        } catch (Exception ex) {
            LOG.warn("Error while clearing selection", ex);
        }
    }

    /**
     * DOCUMENT ME!
     */
    public void restoreSelectedElementIfPossible() {
        if (selectedElement != null) {
            try {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("restoring selection");
                }
                jttHitTable.getTreeSelectionModel().setSelectionPath(selectedElement);
            } catch (Exception ex) {
                LOG.warn("Error while restoring selection (Maybe The objects doesn't exist anymore)", ex);
                try {
                    jttHitTable.getTreeSelectionModel().clearSelection();
                } catch (Exception ex2) {
                    LOG.warn("Error while clearing selection", ex2);
                }
            }
        }
    }

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
    }

    @Override
    public void featuresAdded(final FeatureCollectionEvent fce) {
    }

    @Override
    public void featuresChanged(final FeatureCollectionEvent fce) {
    }

    @Override
    public void featuresRemoved(final FeatureCollectionEvent fce) {
    }

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
                    getBroker().getMappingComponent().getFeatureCollection().addFeature(currentSelectedFeature);
                    currentSelectedFeature.setEditable(true);
                    btnAttachMode.setEnabled(false);
                    getBroker().getMapWidget().setToLastInteractionMode();
                    getBroker().getMappingComponent().getFeatureCollection().select(currentSelectedFeature);
                } finally {
                    getBroker().setVetoCheckEnabled(true);
                    getBroker().removeFeatureSelectionChangeIgnore(WorkbenchWidget.this);
                    ignoreFeatureSelection = false;
                }
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Not all prequisites fulfied for adding newly created geometry");
                }
            }
        }
    }
}
