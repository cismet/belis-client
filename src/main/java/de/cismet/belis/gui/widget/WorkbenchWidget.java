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

import Sirius.server.middleware.types.MetaObject;

import org.apache.commons.collections.comparators.ReverseComparator;
import org.apache.log4j.Logger;

import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.ComponentAdapter;
import org.jdesktop.swingx.decorator.HighlightPredicate;
import org.jdesktop.swingx.decorator.Highlighter;
import org.jdesktop.swingx.treetable.AbstractMutableTreeTableNode;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.jdesktop.swingx.treetable.MutableTreeTableNode;

import org.jdom.Element;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import de.cismet.belis.broker.BelisBroker;
import de.cismet.belis.broker.CidsBroker;

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

import de.cismet.commons.server.entity.BaseEntity;
import de.cismet.commons.server.entity.WorkbenchEntity;
import de.cismet.commons.server.entity.WorkbenchFeatureEntity;

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
    private final HashMap<TdtaLeuchtenCustomBean, TdtaStandortMastCustomBean> leuchteToVirtualStandortMap =
        new HashMap();
    private final JButton btnAttachMode = new JButton();
    private Collection<WorkbenchEntity> currentSearchResults = new TreeSet(new ReverseComparator(
                new EntityComparator()));
    private Set<WorkbenchEntity> objectsToPersist = new TreeSet(new ReverseComparator(new EntityComparator()));
    private final Collection<WorkbenchEntity> objectsToDelete = new ArrayList<WorkbenchEntity>();
    private int currentMode = 0;
    private boolean isSelectedOverMap = false;
    private TreePath selectedElement = null;
    private Set searchResults = null;
    private final boolean basicEditEnabled;
    private final boolean veranlassungEditEnabled;
    private final boolean arbeitsauftragEditEnabled;
    private final boolean basicCreateEnabled;
    private final boolean veranlassungCreateEnabled;
    private final boolean arbeitsauftragCreateEnabled;
    private boolean treeSelectionVeto = false;

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

        basicEditEnabled = CidsBroker.getInstance().checkForEditBasic();
        veranlassungEditEnabled = CidsBroker.getInstance().checkForEditVeranlassung();
        arbeitsauftragEditEnabled = CidsBroker.getInstance().checkForEditArbeitsauftrag();
        basicCreateEnabled = CidsBroker.getInstance().checkForCreateBasic();
        veranlassungCreateEnabled = CidsBroker.getInstance().checkForCreateVeranlassung();
        arbeitsauftragCreateEnabled = CidsBroker.getInstance().checkForCreateArbeitsauftrag();
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
                            } else if (userObj instanceof WorkbenchFeatureEntity) {
                                return ((WorkbenchFeatureEntity)userObj).getGeometry() == null;
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

        final Highlighter noGeometryHighlighter = new ColorHighlighter(noGeometryPredicate, BelisBroker.red, null);
        jttHitTable.addHighlighter(noGeometryHighlighter);
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
        this.selectedTreeNodes = selectedTreeNodes;

        firePropertyChange(PROP_SELECTEDTREENODES, oldSelectedTreeNodes, selectedTreeNodes);

        // abbruch
        if (isTreeSelectionVeto()) {
            setTreeSelectionVeto(false);
            SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        jttHitTable.getTreeSelectionModel()
                                .setSelectionPaths(oldSelectedTreeNodes.toArray(new TreePath[0]));
                    }
                });
        }

        if (selectedTreeNodes != null) {
            SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            configureMapModeAccordingToSelection();

                            if (isSelectedOverMap) {
                                if (LOG.isDebugEnabled()) {
                                    LOG.debug("feature was selected over map. No need to select it in map.");
                                }
                            } else {
                                if (LOG.isDebugEnabled()) {
                                    LOG.debug(
                                        "feature was selected over table. Going to select feature in map.");
                                }

                                // ToDo method for extraction bad performance
                                final Collection<Feature> featuresToSelect = new ArrayList();
                                for (final TreePath path : selectedTreeNodes) {
                                    final Object currentUserObject = getUserObjectForTreePath(path);
                                    if ((currentUserObject != null)
                                                && (currentUserObject instanceof WorkbenchFeatureEntity)
                                                && (((WorkbenchFeatureEntity)currentUserObject).getGeometry() != null)) {
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
                                        featuresToSelect.add(
                                            leuchteToVirtualStandortMap.get((TdtaLeuchtenCustomBean)currentUserObject));
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
                                        for (final ArbeitsprotokollCustomBean apCustomBean
                                                    : arbeitsauftragCustomBean.getAr_protokolle()) {
                                            if (apCustomBean.getChildEntity() != null) {
                                                featuresToSelect.add(apCustomBean.getChildEntity());
                                            }
                                        }
                                    } else if (currentUserObject instanceof ArbeitsprotokollCustomBean) {
                                        final ArbeitsprotokollCustomBean apCustomBean = (ArbeitsprotokollCustomBean)
                                            currentUserObject;
                                        if (apCustomBean.getChildEntity() != null) {
                                            featuresToSelect.add(apCustomBean.getChildEntity());
                                        }
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
                                                        .removeFeatures(featuresToSelect);
                                                getBroker().getMappingComponent()
                                                        .getFeatureCollection()
                                                        .addFeatures(featuresToSelect);

                                                getBroker().getMappingComponent()
                                                        .getFeatureCollection()
                                                        .select(featuresToSelect);
                                                ignoreFeatureSelection = false;
                                            }
                                        };
                                    SwingUtilities.invokeLater(runnable);
                                }
                            }
                        } finally {
                            getBroker().removeFeatureSelectionChangeIgnore(WorkbenchWidget.this);
                            isSelectedOverMap = false;
                        }
                    }
                });
        }
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

//    /**
//     * DOCUMENT ME!
//     *
//     * @param  nodeToSelect  DOCUMENT ME!
//     */
//    public void selectNode(final CustomMutableTreeTableNode nodeToSelect) {
//        if (nodeToSelect != null) {
//            final TreePath pathToRoot = new TreePath(treeTableModel.getPathToRoot(nodeToSelect));
//            if (LOG.isDebugEnabled()) {
//                LOG.debug("selecting: " + pathToRoot);
//            }
//            jttHitTable.getTreeSelectionModel().setSelectionPath(pathToRoot);
//        }
//    }

    /**
     * DOCUMENT ME!
     *
     * @param  userObjects  DOCUMENT ME!
     */
    public void selectUserObjects(final Collection userObjects) {
//        jttHitTable.getTreeSelectionModel().removeTreeSelectionListener(this);
        try {
            jttHitTable.getTreeSelectionModel().clearSelection();
            for (final Object userObject : userObjects) {
                final Collection<TreePath> pathsToEntity = getTreeTableModel().getPathsForUserObject(userObject);
                for (final TreePath pathToEntity : pathsToEntity) {
                    jttHitTable.getTreeSelectionModel().addSelectionPath(pathToEntity);
                }
            }
            setSelectedTreeNodes(Arrays.asList(jttHitTable.getTreeSelectionModel().getSelectionPaths()));
        } finally {
//            jttHitTable.getTreeSelectionModel().addTreeSelectionListener(this);
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
                try {
                    treeTableModel.removeNodeFromParent(searchResultsNode);
                } catch (final Exception ex) {
                    LOG.warn("could not remove node from parent", ex);
                }
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

                final Collection<MutableTreeTableNode> expandedNodes = new ArrayList<MutableTreeTableNode>();
                final Collection<Object> seletedUserObjects = new ArrayList<Object>();

                if (jttHitTable.getTreeSelectionModel().getSelectionPaths() != null) {
                    for (final TreePath selPath : jttHitTable.getTreeSelectionModel().getSelectionPaths()) {
                        seletedUserObjects.add(((CustomMutableTreeTableNode)selPath.getLastPathComponent())
                                    .getUserObject());
                    }
                }

                final Collection<MutableTreeTableNode> nodes = new ArrayList<MutableTreeTableNode>();
                for (int i = 0; i < searchResultsNode.getChildCount(); i++) {
                    final MutableTreeTableNode node = (MutableTreeTableNode)searchResultsNode.getChildAt(i);
                    final boolean isBasicNode = (node.getUserObject() instanceof TdtaStandortMastCustomBean)
                                || (node.getUserObject() instanceof TdtaLeuchtenCustomBean)
                                || (node.getUserObject() instanceof MauerlascheCustomBean)
                                || (node.getUserObject() instanceof SchaltstelleCustomBean)
                                || (node.getUserObject() instanceof LeitungCustomBean)
                                || (node.getUserObject() instanceof AbzweigdoseCustomBean);
                    final boolean isVeranlassungNode = node.getUserObject() instanceof VeranlassungCustomBean;
                    final boolean isArbeitsauftragNode = node.getUserObject() instanceof ArbeitsauftragCustomBean;
                    if ((isBasicNode && basicEditEnabled) || (isVeranlassungNode && veranlassungEditEnabled)
                                || (isArbeitsauftragNode && arbeitsauftragEditEnabled)) {
                        nodes.add(node);
                        final TreePath path = new TreePath(treeTableModel.getPathToRoot(node));
                        if (jttHitTable.isExpanded(path)) {
                            expandedNodes.add(node);
                        }
                    }
                }

                try {
                    treeTableModel.removeNodeFromParent(searchResultsNode);
                } catch (final Exception ex) {
                    LOG.warn("could not remove node from parent", ex);
                }
                treeTableModel.insertNodeIntoAsLastChild(editObjectsNode, rootNode);
                treeTableModel.insertNodeIntoAsLastChild(searchResultsNode, rootNode);
                for (final MutableTreeTableNode node : nodes) {
                    treeTableModel.insertNodeIntoAsLastChild(node, editObjectsNode);
                    objectsToPersist.add((WorkbenchEntity)node.getUserObject());
                }
                for (final MutableTreeTableNode expandedNode : expandedNodes) {
                    jttHitTable.expandPath(new TreePath(treeTableModel.getPathToRoot(expandedNode)));
                }
                selectUserObjects(seletedUserObjects);
                jttHitTable.expandPath(new TreePath(treeTableModel.getPathToRoot(editObjectsNode)));
            }
            jttHitTable.expandPath(new TreePath(treeTableModel.getPathToRoot(searchResultsNode)));
        } else {
            final Collection<MutableTreeTableNode> expandedNodes = new ArrayList<MutableTreeTableNode>();
            final Collection<MutableTreeTableNode> seletedNodes = new ArrayList<MutableTreeTableNode>();

            if (jttHitTable.getTreeSelectionModel().getSelectionPaths() != null) {
                for (final TreePath selPath : jttHitTable.getTreeSelectionModel().getSelectionPaths()) {
                    seletedNodes.add((MutableTreeTableNode)selPath.getLastPathComponent());
                }
            }

            if (getCurrentMode() == CREATE_MODE) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Was in create mode switching to view mode.");
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("removing node:" + newObjectsNode);
                }
                try {
                    treeTableModel.removeNodeFromParent(newObjectsNode);
                } catch (final Exception ex) {
                    LOG.warn("could not remove node from parent", ex);
                }
                try {
                    treeTableModel.removeNodeFromParent(searchResultsNode);
                } catch (final Exception ex) {
                    LOG.warn("could not remove node from parent", ex);
                }
                treeTableModel.insertNodeIntoAsLastChild(searchResultsNode, rootNode);

                jttHitTable.expandPath(new TreePath(treeTableModel.getPathToRoot(searchResultsNode)));
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Was in edit mode switching to view mode.");
                }

                treeTableModel.removeAllChildrenFromNode(editObjectsNode, false);
                try {
                    treeTableModel.removeNodeFromParent(editObjectsNode);
                } catch (final Exception ex) {
                    LOG.warn("could not remove node from parent", ex);
                }
                objectsToPersist.clear();
                if (treeTableModel.getPathForUserObject(searchResultsNode.getUserObject()) == null) {
                    treeTableModel.insertNodeIntoAsLastChild(searchResultsNode, rootNode);
                }
            }

            setCurrentMode(VIEW_MODE);
            refreshSearchObjects();

            for (final MutableTreeTableNode expandedNode : expandedNodes) {
                jttHitTable.expandPath(new TreePath(treeTableModel.getPathToRoot(expandedNode)));
            }
            final Collection<TreePath> paths = new ArrayList<TreePath>();
            for (final MutableTreeTableNode selecteNode : seletedNodes) {
                if (selecteNode != null) {
                    try {
                        paths.add(new TreePath(treeTableModel.getPathToRoot(selecteNode)));
                    } catch (final NullPointerException npe) {
                        // node  has no root anymore
                    }
                }
            }
            if (paths.isEmpty()) {
                jttHitTable.getTreeSelectionModel().clearSelection();
            } else {
                jttHitTable.getTreeSelectionModel().setSelectionPaths(paths.toArray(new TreePath[0]));
            }
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
                        } else if (userObject instanceof WorkbenchFeatureEntity) {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("UserObject is instance of GeoBaseEntity");
                            }
                            if (((WorkbenchFeatureEntity)userObject).getGeometry() == null) {
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
     *
     * @return  DOCUMENT ME!
     */
    public boolean isTreeSelectionVeto() {
        return treeSelectionVeto;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  treeSelectionVeto  DOCUMENT ME!
     */
    public void setTreeSelectionVeto(final boolean treeSelectionVeto) {
        this.treeSelectionVeto = treeSelectionVeto;
    }

    /**
     * DOCUMENT ME!
     */
    private void configureMapModeAccordingToSelection() {
        if (checkConstraintForMapModeSwitch()) {
            try {
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
            } finally {
                getBroker().removeFeatureSelectionChangeIgnore(this);
                ignoreFeatureSelection = false;
            }
        } else {
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

        final Collection<TreePath> paths = new ArrayList<TreePath>();
        for (int i = 0; i < jttHitTable.getSelectedRowCount(); i++) {
            paths.add(jttHitTable.getPathForRow(jttHitTable.getSelectedRows()[i]));
        }

        if (!paths.isEmpty()) {
            setSelectedTreeNodes(paths);
        } else {
            setSelectedTreeNodes(null);
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
            final Collection<TreePath> paths = new ArrayList<TreePath>();

            for (final Feature feature : features) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("currentFeature: " + feature);
                }
                if ((feature instanceof WorkbenchFeatureEntity)
                            && getBroker().getMappingComponent().getFeatureCollection().isSelected(feature)) {
                    final TreePath path;
                    if ((feature instanceof TdtaStandortMastCustomBean)
                                && !((TdtaStandortMastCustomBean)feature).isStandortMast()
                                && (((TdtaStandortMastCustomBean)feature).getLeuchten() != null)
                                && (((TdtaStandortMastCustomBean)feature).getLeuchten().size() > 0)) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("virtual Standort selected, selecting depending leuchte");
                        }
                        path = treeTableModel.getPathForUserObject(((TdtaStandortMastCustomBean)feature).getLeuchten()
                                        .iterator().next());
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
        }
    }

    /**
     * DOCUMENT ME!
     */
    public void refreshSearchObjects() {
        refreshNode(searchResultsNode, currentSearchResults);
    }

    /**
     * DOCUMENT ME!
     */
    public void refreshPersistObjects() {
        if (BelisBroker.getInstance().isInCreateMode()) {
            refreshNode(newObjectsNode, objectsToPersist);
        } else {
            refreshNode(editObjectsNode, objectsToPersist);
        }
    }

    /**
     * DOCUMENT ME!
     */
    public void refreshAll() {
        if (BelisBroker.getInstance().isInCreateMode()) {
            refreshNode(newObjectsNode, objectsToPersist);
        } else {
            refreshNode(editObjectsNode, objectsToPersist);
        }
        refreshNode(searchResultsNode, currentSearchResults);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  entity  DOCUMENT ME!
     */
    public void reloadSearchResultEntity(final WorkbenchEntity entity) {
        final MetaObject mo = CidsBroker.getInstance()
                    .getMetaObject(entity.getMetaObject().getClassID(),
                        entity.getMetaObject().getID(),
                        CidsBroker.BELIS_DOMAIN);
        final WorkbenchEntity reloadedEntity = (WorkbenchEntity)mo.getBean();

        final Collection<WorkbenchEntity> newSearchResults = new ArrayList<WorkbenchEntity>();
        for (final WorkbenchEntity currentSearchResultEntity : currentSearchResults) {
            if (currentSearchResultEntity.equals(entity)) {
                newSearchResults.add(reloadedEntity);
            } else {
                newSearchResults.add(currentSearchResultEntity);
            }
        }
        setCurrentSearchResults(newSearchResults);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  refreshNode  DOCUMENT ME!
     * @param  objects      DOCUMENT ME!
     */
    public void refreshNode(final CustomMutableTreeTableNode refreshNode, final Collection<WorkbenchEntity> objects) {
        if (refreshNode != null) {
            final String before = (String)refreshNode.getUserObject();
            try {
                refreshNode.setUserObject("wird geladen...");
                treeTableModel.removeAllChildrenFromNode(refreshNode, false);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("adding new SearchResults");
                }
                for (final Object curObject : objects) {
                    if (curObject instanceof TdtaStandortMastCustomBean) {
                        final TdtaStandortMastCustomBean standort = (TdtaStandortMastCustomBean)curObject;
                        standort.setEditAllowed(basicEditEnabled);

                        final CustomMutableTreeTableNode standortNode = new CustomMutableTreeTableNode(
                                curObject,
                                true);
                        if (standort.isStandortMast()) {
                            treeTableModel.insertNodeIntoAsLastChild(standortNode, refreshNode);
                            standort.addPropertyChangeListener(WorkbenchWidget.this);
                        }

                        for (final TdtaLeuchtenCustomBean leuchte : standort.getLeuchten()) {
                            leuchte.setEditAllowed(basicEditEnabled);
                            final CustomMutableTreeTableNode leuchteNode = new CustomMutableTreeTableNode(
                                    leuchte,
                                    false);
                            leuchte.addPropertyChangeListener(WorkbenchWidget.this);
                            if (((TdtaStandortMastCustomBean)curObject).isStandortMast()) {
                                treeTableModel.insertNodeIntoAsLastChild(leuchteNode, standortNode);
                                standort.addPropertyChangeListener(leuchte);
                            } else {
                                treeTableModel.insertNodeIntoAsLastChild(leuchteNode, refreshNode);
                                leuchteToVirtualStandortMap.put(leuchte, standort);
                            }
                        }
                    } else if (curObject instanceof VeranlassungCustomBean) {
                        final VeranlassungCustomBean veranlassungCustomBean = (VeranlassungCustomBean)curObject;
                        veranlassungCustomBean.setEditAllowed(veranlassungEditEnabled);

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
                            subEntity.setEditAllowed((subEntity instanceof GeometrieCustomBean)
                                        && veranlassungEditEnabled);
                            treeTableModel.insertNodeIntoAsLastChild(new CustomMutableTreeTableNode(
                                    subEntity,
                                    false),
                                veranlassungNode);
                        }
                    } else if (curObject instanceof ArbeitsauftragCustomBean) {
                        final ArbeitsauftragCustomBean arbeitsauftragCustomBean = (ArbeitsauftragCustomBean)curObject;
                        arbeitsauftragCustomBean.setEditAllowed(arbeitsauftragEditEnabled);

                        final CustomMutableTreeTableNode arbeitsauftragNode = new CustomMutableTreeTableNode(
                                arbeitsauftragCustomBean,
                                true);
                        treeTableModel.insertNodeIntoAsLastChild(arbeitsauftragNode, refreshNode);
                        for (final ArbeitsprotokollCustomBean protokoll
                                    : arbeitsauftragCustomBean.getSortedProtokolle()) {
                            protokoll.setEditAllowed(arbeitsauftragEditEnabled);
                            final CustomMutableTreeTableNode childNode;
                            final WorkbenchFeatureEntity childEntity = protokoll.getChildEntity();
                            if (childEntity != null) {
                                childEntity.setEditAllowed(basicEditEnabled);
                                childNode = new CustomMutableTreeTableNode(childEntity, false);

                                final CustomMutableTreeTableNode protokollNode = new CustomMutableTreeTableNode(
                                        protokoll,
                                        true);
                                treeTableModel.insertNodeIntoAsLastChild(protokollNode, arbeitsauftragNode);
                                treeTableModel.insertNodeIntoAsLastChild(childNode, protokollNode);
                            }
                        }
                    } else if (curObject instanceof WorkbenchFeatureEntity) {
                        final WorkbenchFeatureEntity entity = (WorkbenchFeatureEntity)curObject;
                        entity.setEditAllowed(basicEditEnabled);

                        final CustomMutableTreeTableNode foundObject = new CustomMutableTreeTableNode(
                                entity,
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
            } finally {
                refreshNode.setUserObject(before);
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
                    && (pathToNodeToRemove.getLastPathComponent() instanceof CustomMutableTreeTableNode)
                    && (entity instanceof WorkbenchEntity)) {
            final CustomMutableTreeTableNode nodeToRemove = (CustomMutableTreeTableNode)
                pathToNodeToRemove.getLastPathComponent();

            final CustomMutableTreeTableNode parentNode =
                ((nodeToRemove.getParent() != null) && (nodeToRemove.getParent() instanceof CustomMutableTreeTableNode))
                ? (CustomMutableTreeTableNode)nodeToRemove.getParent() : null;
            final Object parentEntity = (parentNode != null) ? parentNode.getUserObject() : null;
            final WorkbenchEntity parentWorkbenchEntity = (parentEntity instanceof WorkbenchEntity)
                ? (WorkbenchEntity)parentEntity : null;

            final WorkbenchEntity workbenchEntity = (WorkbenchEntity)entity;
            if (parentEntity instanceof VeranlassungCustomBean) {
                final VeranlassungCustomBean veranlassung = (VeranlassungCustomBean)workbenchEntity;
                if (workbenchEntity instanceof TdtaLeuchtenCustomBean) {
                    veranlassung.getAr_leuchten().remove((TdtaLeuchtenCustomBean)workbenchEntity);
                } else if (workbenchEntity instanceof TdtaStandortMastCustomBean) {
                    veranlassung.getAr_standorte().remove((TdtaStandortMastCustomBean)workbenchEntity);
                } else if (workbenchEntity instanceof MauerlascheCustomBean) {
                    veranlassung.getAr_mauerlaschen().remove((MauerlascheCustomBean)workbenchEntity);
                } else if (workbenchEntity instanceof LeitungCustomBean) {
                    veranlassung.getAr_leitungen().remove((LeitungCustomBean)workbenchEntity);
                } else if (workbenchEntity instanceof AbzweigdoseCustomBean) {
                    veranlassung.getAr_abzweigdosen().remove((AbzweigdoseCustomBean)workbenchEntity);
                } else if (workbenchEntity instanceof SchaltstelleCustomBean) {
                    veranlassung.getAr_schaltstellen().remove((SchaltstelleCustomBean)workbenchEntity);
                } else if (workbenchEntity instanceof GeometrieCustomBean) {
                    veranlassung.getAr_geometrien().remove((GeometrieCustomBean)workbenchEntity);
                }
            } else if ((workbenchEntity instanceof ArbeitsprotokollCustomBean)
                        && (parentWorkbenchEntity instanceof ArbeitsauftragCustomBean)) {
                final ArbeitsauftragCustomBean auftrag = (ArbeitsauftragCustomBean)parentWorkbenchEntity;
                final ArbeitsprotokollCustomBean protokoll = (ArbeitsprotokollCustomBean)workbenchEntity;
                auftrag.getAr_protokolle().remove(protokoll);
                objectsToDelete.add(protokoll);
            } else if ((workbenchEntity instanceof TdtaLeuchtenCustomBean)
                        && (parentWorkbenchEntity instanceof TdtaStandortMastCustomBean)) {
                final TdtaStandortMastCustomBean mast = (TdtaStandortMastCustomBean)parentWorkbenchEntity;
                final TdtaLeuchtenCustomBean leuchte = (TdtaLeuchtenCustomBean)workbenchEntity;
                objectsToDelete.add(leuchte);
                if (mast.getLeuchten().isEmpty()) {
                    getBroker().getMappingComponent().getFeatureCollection().reconsiderFeature(mast);
                }
            } else if (workbenchEntity instanceof TdtaStandortMastCustomBean) {
                for (final WorkbenchEntity leuchte : ((TdtaStandortMastCustomBean)workbenchEntity).getLeuchten()) {
                    objectsToDelete.add(leuchte);
                }
                objectsToDelete.add(workbenchEntity);
            } else if (isNodeHaengeLeuchte(nodeToRemove)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Node which will be removed is a haengeleuchte. Removing also virtual Standort");
                }
                final TdtaStandortMastCustomBean virtualStandort = leuchteToVirtualStandortMap.get(
                        (TdtaLeuchtenCustomBean)workbenchEntity);
                if (virtualStandort != null) {
                    getBroker().getMappingComponent().getFeatureCollection().removeFeature(virtualStandort);
                    objectsToDelete.add(virtualStandort);
                } else {
                    LOG.warn("No virtual standort found for leuchte");
                }
            } else {
                objectsToDelete.add((WorkbenchEntity)entity);
            }
            try {
                treeTableModel.removeNodeFromParent(nodeToRemove);
            } catch (final Exception ex) {
                LOG.warn("could not remove node from parent", ex);
            }
            // removedNodes.add(nodeToRemove);
            if ((workbenchEntity instanceof WorkbenchFeatureEntity)
                        && (((WorkbenchFeatureEntity)entity).getGeometrie() != null)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Entity has a geometry. Removing geometry from map");
                }
                getBroker().getMappingComponent().getFeatureCollection().removeFeature((WorkbenchFeatureEntity)entity);
            } else if ((workbenchEntity instanceof ArbeitsprotokollCustomBean)
                        && (((ArbeitsprotokollCustomBean)entity).getChildEntity().getGeometrie() != null)) {
                getBroker().getMappingComponent()
                        .getFeatureCollection()
                        .removeFeature(((ArbeitsprotokollCustomBean)entity).getChildEntity());
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
    public Set<WorkbenchEntity> getObjectsToPersist() {
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
    public Collection<WorkbenchEntity> getObjectsToDelete() {
        return objectsToDelete;
    }

    /**
     * DOCUMENT ME!
     */
    public void clearNewNode() {
//        saveSelectedElementAndUnselectAll();
        treeTableModel.removeAllChildrenFromNode(newObjectsNode, true);
        objectsToPersist.clear();
        leuchteToVirtualStandortMap.clear();
//        restoreSelectedElementIfPossible();
    }

    /**
     * DOCUMENT ME!
     */
    public void clearEditNode() {
//        saveSelectedElementAndUnselectAll();
        treeTableModel.removeAllChildrenFromNode(editObjectsNode, true);
        objectsToPersist.clear();
        leuchteToVirtualStandortMap.clear();
//        restoreSelectedElementIfPossible();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  entity  DOCUMENT ME!
     */
    public void addNewEntity(final WorkbenchEntity entity) {
        final boolean isBasic = (entity instanceof AbzweigdoseCustomBean) || (entity instanceof MauerlascheCustomBean)
                    || (entity instanceof LeitungCustomBean) || (entity instanceof SchaltstelleCustomBean)
                    || (entity instanceof TdtaLeuchtenCustomBean) || (entity instanceof TdtaStandortMastCustomBean);
        final boolean isVeranlassung = (entity instanceof VeranlassungCustomBean);
        final boolean isArbeitsauftrag = (entity instanceof ArbeitsauftragCustomBean);
        final boolean allowed = (isBasic && basicCreateEnabled) || (isVeranlassung && veranlassungCreateEnabled)
                    || (isArbeitsauftrag && arbeitsauftragCreateEnabled);

        if (allowed) {
            final CustomMutableTreeTableNode entityNode = new CustomMutableTreeTableNode(entity, true);
            objectsToPersist.add((WorkbenchEntity)entityNode.getUserObject());
            treeTableModel.insertNodeIntoAsLastChild(entityNode, newObjectsNode);
            selectUserObjects(Arrays.asList(entity));
        } else {
            LOG.info("keine Rechte diese Objekt zu erzeugen");
        }
    }

    /**
     * DOCUMENT ME!
     */
    public void addNewLeuchte() {
        if (CidsBroker.getInstance().checkForCreateBasic()) {
            final TdtaStandortMastCustomBean newStandort = TdtaStandortMastCustomBean.createNew();
            newStandort.addPropertyChangeListener(this);
            newStandort.setVirtuellerStandort(true);
            newStandort.setVerrechnungseinheit(true);
            // final CustomMutableTreeTableNode newStandortNode = new CustomMutableTreeTableNode(newStandort, true);
            objectsToPersist.add(newStandort);
            // treeTableModel.insertNodeIntoAsLastChild(newStandortNode, newObjectsNode);
            addNewLeuchte(newStandort);
        } else {
            LOG.info("keine Rechte diese Objekt zu erzeugen");
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  relatedObject  DOCUMENT ME!
     */
    public void addNewLeuchte(final Object relatedObject) {
        if (CidsBroker.getInstance().checkForCreateBasic()) {
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
                            && (((CustomMutableTreeTableNode)pathToRelatedObejct.getParentPath()
                                    .getLastPathComponent()).getUserObject() instanceof TdtaStandortMastCustomBean)) {
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
                            && (((CustomMutableTreeTableNode)pathToRelatedObejct.getLastPathComponent())
                                .getUserObject()
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
            selectUserObjects(Arrays.asList(newLeuchte));
        } else {
            LOG.info("keine Rechte diese Objekt zu erzeugen");
        }
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
        if (CidsBroker.getInstance().checkForEditVeranlassung()) {
            final Object tmpObject = ((CustomMutableTreeTableNode)getSelectedTreeNode().getLastPathComponent())
                        .getUserObject();
            if ((tmpObject instanceof VeranlassungCustomBean) || (tmpObject instanceof ArbeitsauftragCustomBean)) {
                final GeometrieCustomBean newGeometrie = GeometrieCustomBean.createNew();
                newGeometrie.addPropertyChangeListener(getBroker());
                newGeometrie.addPropertyChangeListener(this);
                if (tmpObject instanceof VeranlassungCustomBean) {
                    final CustomMutableTreeTableNode newGeometrieNode = new CustomMutableTreeTableNode(
                            newGeometrie,
                            true);
                    final VeranlassungCustomBean selVeranlassung = ((VeranlassungCustomBean)tmpObject);
                    selVeranlassung.getAr_geometrien().add(newGeometrie);
                    treeTableModel.insertNodeIntoAsLastChild(
                        newGeometrieNode,
                        (CustomMutableTreeTableNode)getSelectedTreeNode().getLastPathComponent());
                    selectUserObjects(Arrays.asList(newGeometrie));
                } else if (tmpObject instanceof ArbeitsauftragCustomBean) {
                    final ArbeitsprotokollCustomBean newProtokoll = ArbeitsprotokollCustomBean.createNew();
                    final CustomMutableTreeTableNode newProtokollNode = new CustomMutableTreeTableNode(
                            newProtokoll,
                            true);
                    final CustomMutableTreeTableNode newGeometrieNode = new CustomMutableTreeTableNode(
                            newGeometrie,
                            true);
                    final ArbeitsauftragCustomBean selAuftrag = ((ArbeitsauftragCustomBean)tmpObject);
                    newProtokoll.setProtokollnummer(selAuftrag.getAr_protokolle().size() + 1);
                    newProtokoll.setFk_geometrie(newGeometrie);
                    selAuftrag.getAr_protokolle().add(newProtokoll);
                    treeTableModel.insertNodeIntoAsLastChild(
                        newProtokollNode,
                        (CustomMutableTreeTableNode)getSelectedTreeNode().getLastPathComponent());
                    treeTableModel.insertNodeIntoAsLastChild(
                        newGeometrieNode,
                        newProtokollNode);
                    selectUserObjects(Arrays.asList(newProtokoll));
                }
            }
        } else {
            LOG.info("keine Rechte diese Objekt zu erzeugen");
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
