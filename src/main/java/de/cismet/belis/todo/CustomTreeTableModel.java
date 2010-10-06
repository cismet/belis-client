/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.belis.todo;

import de.cismet.belis.broker.BelisBroker;
import de.cismet.belisEE.entity.Leuchte;
import de.cismet.belisEE.entity.Standort;
import de.cismet.belisEE.util.BelisEEUtils;
import de.cismet.belisEE.util.EntityComparator;
import de.cismet.belisEE.util.LeuchteComparator;
import de.cismet.cismap.commons.features.Feature;
import de.cismet.commons.architecture.broker.AdvancedPluginBroker;
import de.cismet.commons.server.entity.BaseEntity;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.tree.TreePath;
import org.apache.commons.collections.comparators.ReverseComparator;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import org.jdesktop.swingx.treetable.MutableTreeTableNode;
import org.jdesktop.swingx.treetable.TreeTableNode;

/**
 *
 * @author spuhl
 */
public class CustomTreeTableModel extends DefaultTreeTableModel {

    public static String HIT_NODE = "CustomTreeTableModel.Hits";
    public static String NEW_OBJECT_NODE = "CustomTreeTableModel.newObject";
    //ToDo disabled Functionality 04.05.2009
    //public static String PROCESSED_NODE = "CustomTreeTableModel.Processed";
    protected final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());
    //private final ArrayList<CustomMutableTreeTableNode> removedNodes = new ArrayList();    
    private CustomMutableTreeTableNode rootNode = null;
    private CustomMutableTreeTableNode searchResultsNode = null;
    private CustomMutableTreeTableNode newObjectsNode = null;
    //ToDo disabled Functionality 04.05.2009
    //private final CustomMutableTreeTableNode processedObjectsNode = new CustomMutableTreeTableNode(null, true);
    AdvancedPluginBroker broker = null;

    public CustomTreeTableModel(AdvancedPluginBroker broker, CustomMutableTreeTableNode rootNode, CustomMutableTreeTableNode searchResultsNode, CustomMutableTreeTableNode newObjectsNode) {
        super(rootNode);
        this.broker = broker;
        this.rootNode = rootNode;
        this.searchResultsNode = searchResultsNode;
        this.newObjectsNode = newObjectsNode;
        searchResultsNode.setUserObject(HIT_NODE);
        newObjectsNode.setUserObject(NEW_OBJECT_NODE);
        //ToDo disabled Functionality 04.05.2009
        //processedObjectsNode.setUserObject(PROCESSED_NODE);

        setRoot(rootNode);
        insertNodeIntoAsLastChild(searchResultsNode, rootNode);
        //insertNodeIntoAsLastChild(newObjectsNode, rootNode);
        //ToDo disabled Functionality 04.05.2009
        //insertNodeInto(processedObjectsNode, rootNode, rootNode.getChildCount());
    }
//
//    public void restoreRemovedObjects(){
////        if(removedNodes.size() >0){
////            for(CustomMutableTreeTableNode curNode:removedNodes){
////                final TreePath pathToParent = nodeToParentPath.get(curNode);
////                if(pathToParent != null && pathToParent.getLastPathComponent() != null && pathToParent.getLastPathComponent() instanceof CustomMutableTreeTableNode){
////                    final CustomMutableTreeTableNode parentNode = (CustomMutableTreeTableNode) pathToParent.getLastPathComponent();
////                    insertNodeInto(curNode, parentNode , parentNode.getChildCount());
////                } else {
////                    log.warn("Can't insert node either path to parent = null or no customMutableTreeNode.");
////                }
////            }
////        } else {
////            log.debug("There are no nodes to restore");
////        }
//        refreshTreeArtifacts(REFRESH_NEW_OBJECTS);
//        refreshTreeArtifacts(REFRESH_SEARCH_RESULTS);
//        //ToDo what to do when error occurs
//        removedNodes.clear();
//        objectsToRemove.clear();
//    }

    public Set getAllUserObjects() {
        Set allObjects = new TreeSet(new ReverseComparator(new EntityComparator(new ReverseComparator(new LeuchteComparator()))));
        for (TreeTableNode curNode : getAllNodes(rootNode)) {
            if (curNode.getUserObject() != null && curNode.getUserObject() instanceof BaseEntity) {
                allObjects.add(curNode.getUserObject());
            }
        }
        return allObjects;
    }

    public short getNextHighestLaufendeNummerForStandort(Standort standort) {
        short max = -1;
        if (standort != null && standort.getKennziffer() != null && standort.getKennziffer().getKennziffer() != null && standort.getStrassenschluessel() != null) {
            Set allUserObjects = getAllUserObjects();
            if (allUserObjects != null) {                
                for (Object curObject : allUserObjects) {
                    if (!standort.equals(curObject) && curObject instanceof Standort) {
                        final Standort curStandort = ((Standort)curObject);
                        if (curStandort.getKennziffer() != null && curStandort.getKennziffer().getKennziffer() != null && curStandort.getKennziffer().getKennziffer().equals(standort.getKennziffer().getKennziffer()) && curStandort.getStrassenschluessel() != null && curStandort.getStrassenschluessel().equals(standort.getStrassenschluessel())) {
                        if(curStandort.getLaufendeNummer() != null && curStandort.getLaufendeNummer() > max){
                            max = curStandort.getLaufendeNummer();
                        }                        
                    } else {                      
                    }
                    }
                }
            } else {
                return -1;
            }
        } else {
            return -1;
        }
        if(max != -1){
            return (short)(max+1);
        } else {
            return max;
        }

    }

    public Set<TreeTableNode> getAllNodes(final TreeTableNode node) {
        final HashSet allNodes = new HashSet();
        if (node != null) {
            allNodes.add(node);
            Enumeration<TreeTableNode> children = (Enumeration<TreeTableNode>) node.children();
            while (children.hasMoreElements()) {
                allNodes.addAll(getAllNodes(children.nextElement()));
            }
        }
        return allNodes;
    }
    //ToDo badName

    public CustomMutableTreeTableNode cloneNodeWithSameUserObjects(CustomMutableTreeTableNode nodeToClone) {
        if (nodeToClone != null) {
            final CustomMutableTreeTableNode cloneNode = new CustomMutableTreeTableNode(nodeToClone.getUserObject(), nodeToClone.getAllowsChildren());
            final int childIndexCount = nodeToClone.getChildCount();
            for (int curChildIndex = 0; curChildIndex < childIndexCount; curChildIndex++) {
                final CustomMutableTreeTableNode curChild = (CustomMutableTreeTableNode) nodeToClone.getChildAt(0);
                if (curChild != null) {
                    CustomMutableTreeTableNode clonedChild = cloneNodeWithSameUserObjects(curChild);
                    clonedChild.setParent(cloneNode);
                    cloneNode.add(clonedChild);
                }
            }
            return cloneNode;
        } else {
            return null;
        }
    }

    public int getColumnCount() {
        return 3;
    }

    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Art";
            case 1:
                return "Kennzeichnung";
            default:
                return "Position";
        }
    }

    public Object getValueAt(Object aObject, int aColumn) {

//                (TreeNode) vTreeNode = (TreeNode)aObject;
//		Component vComponent = vTreeNode.getComponent();

        switch (aColumn) {
            case 1:
                if (aObject instanceof TreeTableNode) {
                    if (((TreeTableNode) aObject).getUserObject() instanceof BaseEntity) {
                        return ((BaseEntity) ((TreeTableNode) aObject).getUserObject()).getKeyString();
                    }
                }
                return "";
            case 2:
                if (aObject instanceof TreeTableNode) {
                    if (((TreeTableNode) aObject).getUserObject() instanceof BaseEntity) {
                        return ((BaseEntity) ((TreeTableNode) aObject).getUserObject()).getHumanReadablePosition();
                    }
                }
                return "";
            default:
                if (aObject instanceof TreeTableNode) {
                    return ((TreeTableNode) aObject).getUserObject();
                } else {
                    return "";
                }
        }
    }

//    private synchronized void clearNewObjects(){
//        final ArrayList objectsToRemove = new ArrayList();
//        if(newObjects.size() >0){
//            for(Object curObject:newObjects){
//                
//            }
//        }
//    }
    //First Path (could be more search and processing)
    public TreePath getPathForUserObject(Object userObject) {
        if (userObject != null) {
            final Set<TreeTableNode> allNodes = getAllNodes(getRoot());
            if (allNodes != null) {
                for (TreeTableNode curNode : allNodes) {
                    if (curNode.getUserObject() != null && curNode.getUserObject().equals(userObject)) {
                        //allObjects.add(curNode.getUserObject());                                                
                        return new TreePath(getPathToRoot(curNode));
                    }
                }
            }
        }
        return null;
    }

    //ToDo refactor bad performance if there are a lot of already saved objects
    public void removeAllChildrenFromNode(CustomMutableTreeTableNode node, boolean onlyWihtoutID) {
        log.debug("removeAllChildrenFromNode");
        final ArrayList<CustomMutableTreeTableNode> nodesToRemove = new ArrayList<CustomMutableTreeTableNode>();
        final int childCount = node.getChildCount();
        log.debug("Childcount of node: " + childCount);
        if (node != null && childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                final CustomMutableTreeTableNode curNode = (CustomMutableTreeTableNode) node.getChildAt(i);
                if (onlyWihtoutID && curNode.getUserObject() != null) {
                    log.debug("checking id");
                    try {
                        if (BelisEEUtils.getEntityId(curNode.getUserObject()) == null) {
                            try {
                                log.debug("ID of entity is null. Will be removed.");
                                nodesToRemove.add(curNode);
                                if (curNode.getUserObject() instanceof Feature && ((Feature) curNode.getUserObject()).getGeometry() != null) {
                                    log.debug("Current object is feature and has a geometry, will be removed from map.");
                                    broker.getMappingComponent().getFeatureCollection().removeFeature((Feature) curNode.getUserObject());
                                } else if (curNode.getUserObject() instanceof Leuchte && ((BelisBroker) broker).getWorkbenchWidget().isNodeHaengeLeuchte(curNode)) {
                                    log.debug("node is haengeleuchte removing parent from map");
                                    broker.getMappingComponent().getFeatureCollection().removeFeature(((BelisBroker) broker).getWorkbenchWidget().getVirtualStandortForLeuchte((Leuchte) curNode.getUserObject()));
                                }
                            } catch (Exception ex) {
                                log.warn("error while removing geometry from Map", ex);
                                nodesToRemove.add(curNode);
                            }
                        }
                    } catch (Exception ex) {
                        log.debug("Object has no id field. Will be removed", ex);
                        nodesToRemove.add(curNode);
                    }
                } else {
                    log.debug("Either no id check or userObject is null");
                    nodesToRemove.add(curNode);
                }
            }
            for (CustomMutableTreeTableNode curNode : nodesToRemove) {
                //ToDo why can this be null ?? 
                if (curNode != null) {
                    log.fatal("removing node:" + curNode);
                    removeNodeFromParent(curNode);
                }
                log.debug("node is removed from tree");
            }
        }
    }

    public void insertNodeIntoAsLastChild(MutableTreeTableNode newChild, MutableTreeTableNode parent) {
        if (parent != null) {
            super.insertNodeInto(newChild, parent, parent.getChildCount());
        } else {
            log.warn("node not inserted, because parent is null");
        }
    }
    //ToDo disabled Functionality 04.05.2009
//    public void moveNewObjectsAfterSave() {
//        refreshTreeArtifacts(REFRESH_PROCESSED_OBJECTS);
//    }
}
