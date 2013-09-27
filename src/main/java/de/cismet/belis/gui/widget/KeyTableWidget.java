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
package de.cismet.belis.gui.widget;

import Sirius.navigator.method.MethodManager;
import Sirius.navigator.resource.PropertyManager;
import Sirius.navigator.types.treenode.DefaultMetaTreeNode;
import Sirius.navigator.ui.ComponentRegistry;
import Sirius.navigator.ui.attributes.editor.AttributeEditor;
import Sirius.navigator.ui.tree.MetaCatalogueTree;

import Sirius.server.middleware.types.MetaObjectNode;
import Sirius.server.newuser.permission.PermissionHolder;

import org.apache.log4j.Logger;

import java.awt.CardLayout;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JPanel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import de.cismet.belis.broker.BelisBroker;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
@org.openide.util.lookup.ServiceProvider(service = BelisWidget.class)
public class KeyTableWidget extends BelisWidget {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger LOG = Logger.getLogger(KeyTableWidget.class);
    private static final String PAN_DESC_OR_EDIT = "panDescOrEdit";

    //~ Instance fields --------------------------------------------------------

    private JPanel panDescOrEdit;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTree jTree1;
    private javax.swing.JPanel pnlPureTreeNode;
    private javax.swing.JPanel pnlValues;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form MetaCatalogueTreeWidget.
     */
    public KeyTableWidget() {
        setWidgetName("Schlüsseltabellen");
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void setBroker(final BelisBroker broker) {
        super.setBroker(broker);
        initComponents();

        panDescOrEdit = (PropertyManager.getManager().isEditable())
            ? ComponentRegistry.getRegistry().getAttributeEditor()
            : ComponentRegistry.getRegistry().getDescriptionPane();

        pnlValues.add(panDescOrEdit, PAN_DESC_OR_EDIT);

        // ComponentRegistry.getRegistry().getAttributeEditor().setControlBarVisible(false);
        ComponentRegistry.getRegistry().getCatalogueTree().addTreeSelectionListener(new TreeSelectionListener() {

                @Override
                public void valueChanged(final TreeSelectionEvent e) {
                    if (panDescOrEdit instanceof AttributeEditor) {
                        final MetaCatalogueTree currentTree = broker.getComponentRegistry().getCatalogueTree();
                        final DefaultMetaTreeNode selectedNode = currentTree.getSelectedNode();
                        if ((selectedNode != null) && (selectedNode.getNode() instanceof MetaObjectNode)) {
                            final MetaObjectNode metaObjectNode = (MetaObjectNode)selectedNode.getNode();

                            if (MethodManager.getManager().checkPermission(
                                            metaObjectNode,
                                            PermissionHolder.WRITEPERMISSION)) {
                                broker.getComponentRegistry()
                                        .getAttributeEditor()
                                        .setTreeNode(currentTree.getSelectionPath(), selectedNode);

                                final Collection<DefaultMutableTreeNode> coll = new ArrayList<DefaultMutableTreeNode>();
                                coll.add(
                                    (DefaultMutableTreeNode)broker.getComponentRegistry().getAttributeEditor()
                                                .getTreeNode());

                                broker.getComponentRegistry().getCatalogueTree().removeTreeSelectionListener(this);
                                broker.getComponentRegistry().getCatalogueTree().setSelectedNodes(coll, true);
                                broker.getComponentRegistry().getCatalogueTree().addTreeSelectionListener(this);
                                final CardLayout cl = (CardLayout)(pnlValues.getLayout());
                                cl.show(pnlValues, PAN_DESC_OR_EDIT);
                            } else {
                                LOG.warn("insufficient permission to edit node " + selectedNode); // NOI18N
                            }
//                        } else {
//                            final CardLayout cl = (CardLayout)(pnlValues.getLayout());
//                            cl.show(pnlValues, "PURE_TREE_NODE");
                        }
                    }
                }
            });
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        final java.awt.GridBagConstraints gridBagConstraints;

        jSplitPane1 = new javax.swing.JSplitPane();
        pnlValues = new javax.swing.JPanel();
        pnlPureTreeNode = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = ComponentRegistry.getRegistry().getCatalogueTree();

        setLayout(new java.awt.GridBagLayout());

        jSplitPane1.setBorder(null);

        pnlValues.setLayout(new java.awt.CardLayout());
        pnlValues.add(pnlPureTreeNode, "PURE_TREE_NODE");

        jSplitPane1.setRightComponent(pnlValues);

        jScrollPane1.setViewportView(jTree1);

        jSplitPane1.setLeftComponent(jScrollPane1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jSplitPane1, gridBagConstraints);
    } // </editor-fold>//GEN-END:initComponents
}
