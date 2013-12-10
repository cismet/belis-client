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

import org.apache.log4j.Logger;

import org.jdesktop.swingx.JXTreeTable;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.TransferHandler;
import javax.swing.tree.TreePath;

import de.cismet.belis.broker.BelisBroker;

import de.cismet.belis.client.BelisClient;

import de.cismet.belis.todo.CustomMutableTreeTableNode;
import de.cismet.belis.todo.CustomTreeTableModel;

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

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
class WorkbenchTransferHandler extends TransferHandler {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger LOG = Logger.getLogger(WorkbenchTransferHandler.class);

    //~ Instance fields --------------------------------------------------------

    private DataFlavor nodesFlavor;
    private DataFlavor[] flavors = new DataFlavor[1];

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new TreeTransferHandler object.
     */
    public WorkbenchTransferHandler() {
        try {
            final String mimeType = DataFlavor.javaJVMLocalObjectMimeType
                        + ";class=\"" + javax.swing.tree.TreePath[].class.getName()
                        + "\"";
            nodesFlavor = new DataFlavor(mimeType);
            flavors[0] = nodesFlavor;
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFound: " + e.getMessage());
        }
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public boolean canImport(final TransferHandler.TransferSupport support) {
        if (!support.isDrop() || !support.isDataFlavorSupported(nodesFlavor)) {
            return false;
        }
        support.setShowDropLocation(true);
        final JXTreeTable.DropLocation dl = (JXTreeTable.DropLocation)support.getDropLocation();
        final JXTreeTable tree = (JXTreeTable)support.getComponent();
        final int dropRow = dl.getRow();
        final TreePath targetPath = tree.getPathForRow(dropRow);
        final Object userObject = ((CustomMutableTreeTableNode)targetPath.getLastPathComponent()).getUserObject();
        if (userObject instanceof VeranlassungCustomBean) {
            return true;
        } else if (userObject instanceof ArbeitsauftragCustomBean) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected Transferable createTransferable(final JComponent c) {
        final JXTreeTable tree = (JXTreeTable)c;
        final Collection<TreePath> paths = new ArrayList<TreePath>();
        for (int i = 0; i < tree.getSelectedRowCount(); i++) {
            paths.add(tree.getPathForRow(tree.getSelectedRows()[i]));
        }

        if (!paths.isEmpty()) {
            final List<TreePath> toTransfer = new ArrayList<TreePath>();
            for (final TreePath path : paths) {
                final Object object = ((CustomMutableTreeTableNode)path.getLastPathComponent()).getUserObject();
                if (!((object instanceof AbzweigdoseCustomBean)
                                || (object instanceof MauerlascheCustomBean)
                                || (object instanceof LeitungCustomBean)
                                || (object instanceof TdtaLeuchtenCustomBean)
                                || (object instanceof TdtaStandortMastCustomBean)
                                || (object instanceof SchaltstelleCustomBean)
                                || (object instanceof VeranlassungCustomBean)
                                || (object instanceof GeometrieCustomBean))) {
                    return null;
                }
                toTransfer.add(copy(path));
            }
            final TreePath[] nodes = toTransfer.toArray(new TreePath[toTransfer.size()]);
            return new NodesTransferable(nodes);
        } else {
            return null;
        }
    }

    /**
     * Copy used in createTransferable.
     *
     * @param   path  path the path to copy
     *
     * @return  A copy of the given TreePath
     */
    private TreePath copy(final TreePath path) {
        return new TreePath(path.getPath());
    }

    @Override
    public int getSourceActions(final JComponent c) {
        return COPY;
    }

    @Override
    public boolean importData(final TransferHandler.TransferSupport support) {
        if (!canImport(support)) {
            return false;
        }
        final JXTreeTable.DropLocation dl = (JXTreeTable.DropLocation)support.getDropLocation();
        final JXTreeTable tree = (JXTreeTable)support.getComponent();
        final int dropRow = dl.getRow();
        final int[] selRows = tree.getSelectedRows();

        try {
            final TreePath path = tree.getPathForRow(dropRow);
            final Object userObject = ((CustomMutableTreeTableNode)path.getLastPathComponent()).getUserObject();
            if (userObject instanceof VeranlassungCustomBean) {
                final VeranlassungCustomBean veranlassungCustomBean = (VeranlassungCustomBean)userObject;
                final CustomMutableTreeTableNode dropNode = (CustomMutableTreeTableNode)path.getLastPathComponent();
                for (final int selRow : selRows) {
                    final CidsBean clipboardBean = (CidsBean)
                        ((CustomMutableTreeTableNode)tree.getPathForRow(selRow).getLastPathComponent()).getUserObject();

                    final CustomMutableTreeTableNode newNode = new CustomMutableTreeTableNode(clipboardBean, true);
                    if (clipboardBean instanceof TdtaStandortMastCustomBean) {
                        final Collection<TdtaStandortMastCustomBean> standorte =
                            veranlassungCustomBean.getAr_standorte();
                        if (!standorte.contains((TdtaStandortMastCustomBean)clipboardBean)) {
                            standorte.add((TdtaStandortMastCustomBean)clipboardBean);
                            ((CustomTreeTableModel)tree.getTreeTableModel()).insertNodeIntoAsLastChild(
                                newNode,
                                dropNode);
                        }
                    } else if (clipboardBean instanceof TdtaLeuchtenCustomBean) {
                        final Collection<TdtaLeuchtenCustomBean> leuchten = veranlassungCustomBean.getAr_leuchten();
                        if (!leuchten.contains((TdtaLeuchtenCustomBean)clipboardBean)) {
                            leuchten.add((TdtaLeuchtenCustomBean)clipboardBean);
                            ((CustomTreeTableModel)tree.getTreeTableModel()).insertNodeIntoAsLastChild(
                                newNode,
                                dropNode);
                        }
                    } else if (clipboardBean instanceof LeitungCustomBean) {
                        final Collection<LeitungCustomBean> leitungen = veranlassungCustomBean.getAr_leitungen();
                        if (!leitungen.contains((LeitungCustomBean)clipboardBean)) {
                            leitungen.add((LeitungCustomBean)clipboardBean);
                            ((CustomTreeTableModel)tree.getTreeTableModel()).insertNodeIntoAsLastChild(
                                newNode,
                                dropNode);
                        }
                    } else if (clipboardBean instanceof MauerlascheCustomBean) {
                        final Collection<MauerlascheCustomBean> mauerlaschen =
                            veranlassungCustomBean.getAr_mauerlaschen();
                        if (!mauerlaschen.contains((MauerlascheCustomBean)clipboardBean)) {
                            mauerlaschen.add((MauerlascheCustomBean)clipboardBean);
                            ((CustomTreeTableModel)tree.getTreeTableModel()).insertNodeIntoAsLastChild(
                                newNode,
                                dropNode);
                        }
                    } else if (clipboardBean instanceof AbzweigdoseCustomBean) {
                        final Collection<AbzweigdoseCustomBean> abzweigdosen =
                            veranlassungCustomBean.getAr_abzweigdosen();
                        if (!abzweigdosen.contains((AbzweigdoseCustomBean)clipboardBean)) {
                            abzweigdosen.add((AbzweigdoseCustomBean)clipboardBean);
                            ((CustomTreeTableModel)tree.getTreeTableModel()).insertNodeIntoAsLastChild(
                                newNode,
                                dropNode);
                        }
                    } else if (clipboardBean instanceof SchaltstelleCustomBean) {
                        final Collection<SchaltstelleCustomBean> schaltstellen =
                            veranlassungCustomBean.getAr_schaltstellen();
                        if (!schaltstellen.contains((SchaltstelleCustomBean)clipboardBean)) {
                            schaltstellen.add((SchaltstelleCustomBean)clipboardBean);
                            ((CustomTreeTableModel)tree.getTreeTableModel()).insertNodeIntoAsLastChild(
                                newNode,
                                dropNode);
                        }
                    } else if (clipboardBean instanceof GeometrieCustomBean) {
                        final Collection<GeometrieCustomBean> geometrien = veranlassungCustomBean.getAr_geometrien();
                        if (!geometrien.contains((GeometrieCustomBean)clipboardBean)) {
                            geometrien.add((GeometrieCustomBean)clipboardBean);
                            ((CustomTreeTableModel)tree.getTreeTableModel()).insertNodeIntoAsLastChild(
                                newNode,
                                dropNode);
                        }
                    }
                }
            } else {
                final ArbeitsauftragCustomBean arbeitsauftragCustomBean = (ArbeitsauftragCustomBean)userObject;
                for (final int selRow : selRows) {
                    final CidsBean clipboardBean = (CidsBean)
                        ((CustomMutableTreeTableNode)tree.getPathForRow(selRow).getLastPathComponent()).getUserObject();
                    if ((clipboardBean instanceof TdtaStandortMastCustomBean)
                                || (clipboardBean instanceof TdtaLeuchtenCustomBean)
                                || (clipboardBean instanceof LeitungCustomBean)
                                || (clipboardBean instanceof MauerlascheCustomBean)
                                || (clipboardBean instanceof AbzweigdoseCustomBean)
                                || (clipboardBean instanceof SchaltstelleCustomBean)
                                || (clipboardBean instanceof GeometrieCustomBean)) {
                        final ArbeitsprotokollCustomBean protokoll = ArbeitsprotokollCustomBean.createNew();
                        if (clipboardBean instanceof TdtaStandortMastCustomBean) {
                            protokoll.setFk_standort((TdtaStandortMastCustomBean)clipboardBean);
                        } else if (clipboardBean instanceof TdtaLeuchtenCustomBean) {
                            protokoll.setFk_leuchte((TdtaLeuchtenCustomBean)clipboardBean);
                        } else if (clipboardBean instanceof LeitungCustomBean) {
                            protokoll.setFk_leitung((LeitungCustomBean)clipboardBean);
                        } else if (clipboardBean instanceof MauerlascheCustomBean) {
                            protokoll.setFk_mauerlasche((MauerlascheCustomBean)clipboardBean);
                        } else if (clipboardBean instanceof AbzweigdoseCustomBean) {
                            protokoll.setFk_abzweigdose((AbzweigdoseCustomBean)clipboardBean);
                        } else if (clipboardBean instanceof SchaltstelleCustomBean) {
                            protokoll.setFk_schaltstelle((SchaltstelleCustomBean)clipboardBean);
                        } else if (clipboardBean instanceof GeometrieCustomBean) {
                            protokoll.setFk_geometrie((GeometrieCustomBean)clipboardBean);
                        }
                        arbeitsauftragCustomBean.getN_protokolle().add(protokoll);

                        final CustomMutableTreeTableNode dropNode = (CustomMutableTreeTableNode)
                            path.getLastPathComponent();
                        final CustomMutableTreeTableNode newProtokollNode = new CustomMutableTreeTableNode(
                                protokoll,
                                true);
                        final CustomMutableTreeTableNode newBasicNode = new CustomMutableTreeTableNode(
                                clipboardBean,
                                true);
                        ((CustomTreeTableModel)tree.getTreeTableModel()).insertNodeIntoAsLastChild(
                            newProtokollNode,
                            dropNode);
                        ((CustomTreeTableModel)tree.getTreeTableModel()).insertNodeIntoAsLastChild(
                            newBasicNode,
                            newProtokollNode);
                    } else if (clipboardBean instanceof VeranlassungCustomBean) {
                        final VeranlassungCustomBean veranlassungCustomBean = (VeranlassungCustomBean)clipboardBean;
                        final Collection<CidsBean> allBasics = new ArrayList<CidsBean>();
                        allBasics.addAll(veranlassungCustomBean.getAr_abzweigdosen());
                        allBasics.addAll(veranlassungCustomBean.getAr_leitungen());
                        allBasics.addAll(veranlassungCustomBean.getAr_leuchten());
                        allBasics.addAll(veranlassungCustomBean.getAr_mauerlaschen());
                        allBasics.addAll(veranlassungCustomBean.getAr_schaltstellen());
                        allBasics.addAll(veranlassungCustomBean.getAr_standorte());
                        allBasics.addAll(veranlassungCustomBean.getAr_geometrien());

                        final CustomMutableTreeTableNode dropNode = (CustomMutableTreeTableNode)
                            path.getLastPathComponent();

                        for (final CidsBean basic : allBasics) {
                            final ArbeitsprotokollCustomBean protokoll = ArbeitsprotokollCustomBean.createNew();
                            final CustomMutableTreeTableNode newProtokollNode = new CustomMutableTreeTableNode(
                                    protokoll,
                                    true);
                            final CustomMutableTreeTableNode newBasicNode = new CustomMutableTreeTableNode(basic, true);
                            ((CustomTreeTableModel)tree.getTreeTableModel()).insertNodeIntoAsLastChild(
                                newProtokollNode,
                                dropNode);
                            ((CustomTreeTableModel)tree.getTreeTableModel()).insertNodeIntoAsLastChild(
                                newBasicNode,
                                newProtokollNode);
                            if (basic instanceof TdtaStandortMastCustomBean) {
                                protokoll.setFk_standort((TdtaStandortMastCustomBean)basic);
                            } else if (basic instanceof TdtaLeuchtenCustomBean) {
                                protokoll.setFk_leuchte((TdtaLeuchtenCustomBean)basic);
                            } else if (basic instanceof LeitungCustomBean) {
                                protokoll.setFk_leitung((LeitungCustomBean)basic);
                            } else if (basic instanceof MauerlascheCustomBean) {
                                protokoll.setFk_mauerlasche((MauerlascheCustomBean)basic);
                            } else if (basic instanceof AbzweigdoseCustomBean) {
                                protokoll.setFk_abzweigdose((AbzweigdoseCustomBean)basic);
                            } else if (basic instanceof SchaltstelleCustomBean) {
                                protokoll.setFk_schaltstelle((SchaltstelleCustomBean)basic);
                            } else if (basic instanceof GeometrieCustomBean) {
                                protokoll.setFk_geometrie((GeometrieCustomBean)basic);
                            }
                            arbeitsauftragCustomBean.getN_protokolle().add(protokoll);
                        }
                    }
                }
            }
            tree.expandPath(path.getParentPath());
            return true;
        } catch (Exception ex) {
            LOG.error("error while pasting bean", ex);
            return false;
        }
    }

    @Override
    public String toString() {
        return getClass().getName();
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public class NodesTransferable implements Transferable {

        //~ Instance fields ----------------------------------------------------

        TreePath[] nodes;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new NodesTransferable object.
         *
         * @param  nodes  DOCUMENT ME!
         */
        public NodesTransferable(final TreePath[] nodes) {
            this.nodes = nodes;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public Object getTransferData(final DataFlavor flavor) throws UnsupportedFlavorException {
            if (!isDataFlavorSupported(flavor)) {
                throw new UnsupportedFlavorException(flavor);
            }
            return nodes;
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return flavors;
        }

        @Override
        public boolean isDataFlavorSupported(final DataFlavor flavor) {
            return nodesFlavor.equals(flavor);
        }
    }
}
