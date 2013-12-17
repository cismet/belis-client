/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.belis.broker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.swing.tree.TreePath;

import de.cismet.belis.gui.widget.WorkbenchWidget;

import de.cismet.belis.todo.CustomMutableTreeTableNode;

import de.cismet.cids.custom.beans.belis2.AbzweigdoseCustomBean;
import de.cismet.cids.custom.beans.belis2.ArbeitsauftragCustomBean;
import de.cismet.cids.custom.beans.belis2.ArbeitsprotokollCustomBean;
import de.cismet.cids.custom.beans.belis2.BasicEntity;
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
 * @version  $Revision$, $Date$
 */
public class EntityClipboard {

    //~ Static fields/initializers ---------------------------------------------

    private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(EntityClipboard.class);

    //~ Instance fields --------------------------------------------------------

    private Collection<CidsBean> clipboardBeans = new ArrayList<CidsBean>();
    private List<EntityClipboardListener> listeners = new ArrayList<EntityClipboardListener>();
    private final BelisBroker broker;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new EntityClipboard object.
     *
     * @param  broker  DOCUMENT ME!
     */
    public EntityClipboard(final BelisBroker broker) {
        this.broker = broker;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param   listener  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean addListener(final EntityClipboardListener listener) {
        return listeners.add(listener);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   listener  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean removeListener(final EntityClipboardListener listener) {
        return listeners.remove(listener);
    }

    /**
     * DOCUMENT ME!
     */
    protected void fireClipboardChanged() {
        for (final EntityClipboardListener listener : listeners) {
            listener.clipboardChanged();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Collection<CidsBean> getClipboardBeans() {
        return clipboardBeans;
    }

    /**
     * DOCUMENT ME!
     */
    public void paste() {
        if (isPastable()) {
            try {
                final CustomMutableTreeTableNode selectedNode = getSelectedNodeForPaste();
                final Object selectedObject = selectedNode.getUserObject();
                if (selectedObject instanceof ArbeitsauftragCustomBean) {
                    final ArbeitsauftragCustomBean arbeitsauftragCustomBean = (ArbeitsauftragCustomBean)selectedObject;
                    for (final CidsBean clipboardBean : clipboardBeans) {
                        if (clipboardBean instanceof VeranlassungCustomBean) {
                            final Collection<CidsBean> allBasics = new HashSet<CidsBean>();
                            final VeranlassungCustomBean veranlassungCustomBean = (VeranlassungCustomBean)clipboardBean;
                            allBasics.addAll(veranlassungCustomBean.getAr_abzweigdosen());
                            allBasics.addAll(veranlassungCustomBean.getAr_leitungen());
                            allBasics.addAll(veranlassungCustomBean.getAr_leuchten());
                            allBasics.addAll(veranlassungCustomBean.getAr_mauerlaschen());
                            allBasics.addAll(veranlassungCustomBean.getAr_schaltstellen());
                            allBasics.addAll(veranlassungCustomBean.getAr_geometrien());
                            allBasics.addAll(veranlassungCustomBean.getAr_standorte());
                            for (final CidsBean basic : allBasics) {
                                final ArbeitsprotokollCustomBean protokoll = broker.createProtokollFromBasic(basic);
                                protokoll.setFk_veranlassung(veranlassungCustomBean);
                                broker.addNewProtokollToAuftragNode(selectedNode, protokoll, basic);
                                arbeitsauftragCustomBean.getN_protokolle().add(protokoll);
                            }
                        } else if ((clipboardBean instanceof BasicEntity)
                                    || (clipboardBean instanceof GeometrieCustomBean)) {
                            final ArbeitsprotokollCustomBean protokoll = broker.createProtokollFromBasic(clipboardBean);
                            broker.addNewProtokollToAuftragNode(selectedNode, protokoll, clipboardBean);
                            arbeitsauftragCustomBean.getN_protokolle().add(protokoll);
                        }
                    }
                } else if (selectedObject instanceof VeranlassungCustomBean) {
                    final VeranlassungCustomBean veranlassungCustomBean = (VeranlassungCustomBean)selectedObject;

                    for (final CidsBean clipboardBean : clipboardBeans) {
                        if (clipboardBean instanceof TdtaStandortMastCustomBean) {
                            final Collection<TdtaStandortMastCustomBean> standorte =
                                veranlassungCustomBean.getAr_standorte();
                            if (!standorte.contains((TdtaStandortMastCustomBean)clipboardBean)) {
                                standorte.add((TdtaStandortMastCustomBean)clipboardBean);
                                broker.addNewBasicToVeranlassungNode(selectedNode, clipboardBean);
                            }
                        } else if (clipboardBean instanceof TdtaLeuchtenCustomBean) {
                            final Collection<TdtaLeuchtenCustomBean> leuchten = veranlassungCustomBean.getAr_leuchten();
                            if (!leuchten.contains((TdtaLeuchtenCustomBean)clipboardBean)) {
                                leuchten.add((TdtaLeuchtenCustomBean)clipboardBean);
                                broker.addNewBasicToVeranlassungNode(selectedNode, clipboardBean);
                            }
                        } else if (clipboardBean instanceof LeitungCustomBean) {
                            final Collection<LeitungCustomBean> leitungen = veranlassungCustomBean.getAr_leitungen();
                            if (!leitungen.contains((LeitungCustomBean)clipboardBean)) {
                                leitungen.add((LeitungCustomBean)clipboardBean);
                                broker.addNewBasicToVeranlassungNode(selectedNode, clipboardBean);
                            }
                        } else if (clipboardBean instanceof MauerlascheCustomBean) {
                            final Collection<MauerlascheCustomBean> mauerlaschen =
                                veranlassungCustomBean.getAr_mauerlaschen();
                            if (!mauerlaschen.contains((MauerlascheCustomBean)clipboardBean)) {
                                mauerlaschen.add((MauerlascheCustomBean)clipboardBean);
                                broker.addNewBasicToVeranlassungNode(selectedNode, clipboardBean);
                            }
                        } else if (clipboardBean instanceof AbzweigdoseCustomBean) {
                            final Collection<AbzweigdoseCustomBean> abzweigdosen =
                                veranlassungCustomBean.getAr_abzweigdosen();
                            if (!abzweigdosen.contains((AbzweigdoseCustomBean)clipboardBean)) {
                                abzweigdosen.add((AbzweigdoseCustomBean)clipboardBean);
                                broker.addNewBasicToVeranlassungNode(selectedNode, clipboardBean);
                            }
                        } else if (clipboardBean instanceof SchaltstelleCustomBean) {
                            final Collection<SchaltstelleCustomBean> schaltstellen =
                                veranlassungCustomBean.getAr_schaltstellen();
                            if (!schaltstellen.contains((SchaltstelleCustomBean)clipboardBean)) {
                                schaltstellen.add((SchaltstelleCustomBean)clipboardBean);
                                broker.addNewBasicToVeranlassungNode(selectedNode, clipboardBean);
                            }
                        } else if (clipboardBean instanceof GeometrieCustomBean) {
                            final Collection<GeometrieCustomBean> geometrien =
                                veranlassungCustomBean.getAr_geometrien();
                            if (!geometrien.contains((GeometrieCustomBean)clipboardBean)) {
                                geometrien.add((GeometrieCustomBean)clipboardBean);
                                broker.addNewBasicToVeranlassungNode(selectedNode, clipboardBean);
                            }
                        }
                    }
                }
                clear();

                broker.getWorkbenchWidget().refreshTreeArtifacts(WorkbenchWidget.REFRESH_NEW_OBJECTS);
            } catch (Exception ex) {
                LOG.error("error while pasting bean", ex);
            }
        }
    }
    /**
     * DOCUMENT ME!
     */
    private void clear() {
        clipboardBeans.clear();
        fireClipboardChanged();
    }

    /**
     * DOCUMENT ME!
     *
     * @param   clipboardBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public CidsBean createPastedBean(final CidsBean clipboardBean) throws Exception {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean isPastable() {
        return (broker.isInCreateMode() || broker.isInEditMode())
                    && !clipboardBeans.isEmpty()
                    && (getSelectedNodeForPaste() != null);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean isCopyable() {
        return !getSelectedBeansForCopy().isEmpty();
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private Collection<CidsBean> getSelectedBeansForCopy() {
        final Collection<CidsBean> beans = new ArrayList<CidsBean>();
        final Collection<TreePath> paths = broker.getWorkbenchWidget().getSelectedTreeNodes();
        if (paths != null) {
            for (final TreePath path : paths) {
                final CustomMutableTreeTableNode node = (CustomMutableTreeTableNode)path.getLastPathComponent();
                if (node != null) {
                    final Object object = node.getUserObject();
                    if ((broker.isFilterVeranlassung() && (object instanceof VeranlassungCustomBean))
                                || (broker.isFilterNormal()
                                    && ((object instanceof BasicEntity) || (object instanceof GeometrieCustomBean)))) {
                        if (object instanceof TdtaLeuchtenCustomBean) {
//                            final TreePath parentPath = path.getParentPath();
//                            final TdtaStandortMastCustomBean mastBean;
//                            if (parentPath != null) {
//                                final Object parentObject =
//                                    ((CustomMutableTreeTableNode)parentPath.getLastPathComponent()).getUserObject();
//                                if (parentObject instanceof TdtaStandortMastCustomBean) {
//                                    mastBean = (TdtaStandortMastCustomBean)parentObject;
//                                } else {
//                                    mastBean = null;
//                                }
//                            } else {
//                                mastBean = null;
//                            }
//                            ((TdtaLeuchtenCustomBean)object).setFk_standort(mastBean);
                        }
                        beans.add((CidsBean)object);
                    }
                }
            }
        }
        return beans;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private CustomMutableTreeTableNode getSelectedNodeForPaste() {
//        CidsBean selectedBean = null;
        final Collection<TreePath> paths = broker.getWorkbenchWidget().getSelectedTreeNodes();
        if ((paths != null) && (paths.size() == 1)) {
            final CustomMutableTreeTableNode node = (CustomMutableTreeTableNode)paths.iterator().next()
                        .getLastPathComponent();
            if (node != null) {
                final Object object = node.getUserObject();
                if (object instanceof ArbeitsauftragCustomBean) {
                    return node;
                        // selectedBean = (ArbeitsauftragCustomBean)object;
                } else if (object instanceof VeranlassungCustomBean) {
                    return node;
                        // selectedBean = (VeranlassungCustomBean)object;
                }
            }
        }
        return null;
            // return selectedBean;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean copy() {
        if (isCopyable()) {
            final Collection<CidsBean> selectedBeans = getSelectedBeansForCopy();
            if ((selectedBeans != null) && !selectedBeans.isEmpty()) {
                try {
                    clipboardBeans.clear();
                    for (final CidsBean cidsBean : selectedBeans) {
                        this.clipboardBeans.add(cidsBean);
                    }
                    fireClipboardChanged();
                    return true;
                } catch (Exception ex) {
                    LOG.error("error while copying or cutting cidsbean", ex);
                    clear();
                    return false;
                }
            } else {
                return false;
            }
        }
        return false;
    }
}
