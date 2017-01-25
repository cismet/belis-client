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

import de.cismet.belis.todo.CustomMutableTreeTableNode;

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

import de.cismet.commons.server.entity.WorkbenchEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class EntityClipboard {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(EntityClipboard.class);

    //~ Instance fields --------------------------------------------------------

    private final Collection<WorkbenchEntity> clipboardBeans = new ArrayList<WorkbenchEntity>();
    private final List<EntityClipboardListener> listeners = new ArrayList<EntityClipboardListener>();
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
    public Collection<WorkbenchEntity> getClipboardBeans() {
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
                if ((selectedObject instanceof ArbeitsauftragCustomBean)
                            && CidsBroker.getInstance().checkForEditArbeitsauftrag()) {
                    final ArbeitsauftragCustomBean arbeitsauftragCustomBean = (ArbeitsauftragCustomBean)selectedObject;
                    for (final WorkbenchEntity clipboardBean : clipboardBeans) {
                        if (clipboardBean instanceof VeranlassungCustomBean) {
                            final Collection<WorkbenchEntity> allBasics = new HashSet<WorkbenchEntity>();
                            final VeranlassungCustomBean veranlassungCustomBean = (VeranlassungCustomBean)clipboardBean;
                            allBasics.addAll(veranlassungCustomBean.getAr_abzweigdosen());
                            allBasics.addAll(veranlassungCustomBean.getAr_leitungen());
                            allBasics.addAll(veranlassungCustomBean.getAr_leuchten());
                            allBasics.addAll(veranlassungCustomBean.getAr_mauerlaschen());
                            allBasics.addAll(veranlassungCustomBean.getAr_schaltstellen());
                            allBasics.addAll(veranlassungCustomBean.getAr_geometrien());
                            allBasics.addAll(veranlassungCustomBean.getAr_standorte());
                            for (final WorkbenchEntity basic : allBasics) {
                                final ArbeitsprotokollCustomBean protokoll = broker.createProtokollFromBasic(basic);
                                protokoll.setVeranlassungsnummer(veranlassungCustomBean.getNummer());
                                protokoll.setProtokollnummer(arbeitsauftragCustomBean.getAr_protokolle().size() + 1);
                                broker.addNewProtokollToAuftragNode(selectedNode, protokoll, basic);
                                arbeitsauftragCustomBean.getAr_protokolle().add(protokoll);
                            }
                        } else {
                            final ArbeitsprotokollCustomBean protokoll = broker.createProtokollFromBasic(clipboardBean);
                            protokoll.setProtokollnummer(arbeitsauftragCustomBean.getAr_protokolle().size() + 1);
                            broker.addNewProtokollToAuftragNode(selectedNode, protokoll, clipboardBean);
                            arbeitsauftragCustomBean.getAr_protokolle().add(protokoll);
                        }
                    }
                } else if ((selectedObject instanceof VeranlassungCustomBean)
                            && CidsBroker.getInstance().checkForEditVeranlassung()) {
                    final VeranlassungCustomBean veranlassungCustomBean = (VeranlassungCustomBean)selectedObject;

                    for (final WorkbenchEntity clipboardBean : clipboardBeans) {
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
    public WorkbenchEntity createPastedBean(final WorkbenchEntity clipboardBean) throws Exception {
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
    private Collection<WorkbenchEntity> getSelectedBeansForCopy() {
        final Collection<WorkbenchEntity> beans = new ArrayList<WorkbenchEntity>();
        final Collection<TreePath> paths = broker.getWorkbenchWidget().getSelectedTreeNodes();
        if (paths != null) {
            for (final TreePath path : paths) {
                final CustomMutableTreeTableNode node = (CustomMutableTreeTableNode)path.getLastPathComponent();
                if (node != null) {
                    final Object object = node.getUserObject();
                    if ((broker.isFilterVeranlassung() && (object instanceof VeranlassungCustomBean))
                                || (broker.isFilterNormal()
                                    && ((object instanceof WorkbenchEntity)
                                        || (object instanceof GeometrieCustomBean)))) {
                        beans.add((WorkbenchEntity)object);
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
                if ((object instanceof ArbeitsauftragCustomBean)
                            && CidsBroker.getInstance().checkForEditArbeitsauftrag()) {
                    return node;
                        // selectedBean = (ArbeitsauftragCustomBean)object;
                } else if ((object instanceof VeranlassungCustomBean)
                            && CidsBroker.getInstance().checkForEditVeranlassung()) {
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
            final Collection<WorkbenchEntity> selectedBeans = getSelectedBeansForCopy();
            if ((selectedBeans != null) && !selectedBeans.isEmpty()) {
                try {
                    clipboardBeans.clear();
                    for (final WorkbenchEntity cidsBean : selectedBeans) {
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
