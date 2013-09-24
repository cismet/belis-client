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
import java.util.List;

import javax.swing.tree.TreePath;

import de.cismet.belis.gui.widget.WorkbenchWidget;

import de.cismet.belis.todo.CustomMutableTreeTableNode;

import de.cismet.cids.custom.beans.belis.AbzweigdoseCustomBean;
import de.cismet.cids.custom.beans.belis.LeitungCustomBean;
import de.cismet.cids.custom.beans.belis.MauerlascheCustomBean;
import de.cismet.cids.custom.beans.belis.SchaltstelleCustomBean;
import de.cismet.cids.custom.beans.belis.TdtaLeuchtenCustomBean;
import de.cismet.cids.custom.beans.belis.TdtaStandortMastCustomBean;
import de.cismet.cids.custom.beans.belis.VeranlassungCustomBean;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.custom.beans.belis.ArbeitsauftragCustomBean;

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
                final VeranlassungCustomBean veranlassungCustomBean = getSelectedVeranlassungBeanForPaste();
                for (final CidsBean clipboardBean : clipboardBeans) {
                    if (clipboardBean instanceof TdtaStandortMastCustomBean) {
                        final Collection<TdtaStandortMastCustomBean> standorte =
                            veranlassungCustomBean.getAr_standorte();
                        if (!standorte.contains((TdtaStandortMastCustomBean)clipboardBean)) {
                            standorte.add((TdtaStandortMastCustomBean)clipboardBean);
                        }
                    } else if (clipboardBean instanceof TdtaLeuchtenCustomBean) {
                        final Collection<TdtaLeuchtenCustomBean> leuchten = veranlassungCustomBean.getAr_leuchten();
                        if (!leuchten.contains((TdtaLeuchtenCustomBean)clipboardBean)) {
                            leuchten.add((TdtaLeuchtenCustomBean)clipboardBean);
                        }
                    } else if (clipboardBean instanceof LeitungCustomBean) {
                        final Collection<LeitungCustomBean> leitungen = veranlassungCustomBean.getAr_leitungen();
                        if (!leitungen.contains((LeitungCustomBean)clipboardBean)) {
                            leitungen.add((LeitungCustomBean)clipboardBean);
                        }
                    } else if (clipboardBean instanceof MauerlascheCustomBean) {
                        final Collection<MauerlascheCustomBean> mauerlaschen =
                            veranlassungCustomBean.getAr_mauerlaschen();
                        if (!mauerlaschen.contains((MauerlascheCustomBean)clipboardBean)) {
                            mauerlaschen.add((MauerlascheCustomBean)clipboardBean);
                        }
                    } else if (clipboardBean instanceof AbzweigdoseCustomBean) {
                        final Collection<AbzweigdoseCustomBean> abzweigdosen =
                            veranlassungCustomBean.getAr_abzweigdosen();
                        if (!abzweigdosen.contains((AbzweigdoseCustomBean)clipboardBean)) {
                            abzweigdosen.add((AbzweigdoseCustomBean)clipboardBean);
                        }
                    } else if (clipboardBean instanceof SchaltstelleCustomBean) {
                        final Collection<SchaltstelleCustomBean> schaltstellen =
                            veranlassungCustomBean.getAr_schaltstellen();
                        if (!schaltstellen.contains((SchaltstelleCustomBean)clipboardBean)) {
                            schaltstellen.add((SchaltstelleCustomBean)clipboardBean);
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
                    && (getSelectedVeranlassungBeanForPaste() != null);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean isCopyable() {
        return !isBasicSelectionEmpty();
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean isBasicSelectionEmpty() {
        return getSelectedBeansForCopy().isEmpty();
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
                    if ((object instanceof VeranlassungCustomBean)
                                || (object instanceof TdtaLeuchtenCustomBean)
                                || (object instanceof TdtaStandortMastCustomBean)
                                || (object instanceof AbzweigdoseCustomBean)
                                || (object instanceof MauerlascheCustomBean)
                                || (object instanceof LeitungCustomBean)
                                || (object instanceof SchaltstelleCustomBean)) {
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
    private ArbeitsauftragCustomBean getSelectedArbeitsauftragBeanForPaste() {
        ArbeitsauftragCustomBean arbeitsauftragCustomBean = null;
        final Collection<TreePath> paths = broker.getWorkbenchWidget().getSelectedTreeNodes();
        if ((paths != null) && (paths.size() == 1)) {
            final CustomMutableTreeTableNode node = (CustomMutableTreeTableNode)paths.iterator().next()
                        .getLastPathComponent();
            if (node != null) {
                final Object object = node.getUserObject();
                if (object instanceof ArbeitsauftragCustomBean) {
                    arbeitsauftragCustomBean = (ArbeitsauftragCustomBean)object;
                }
            }
        }
        return arbeitsauftragCustomBean;
    }
    
    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private VeranlassungCustomBean getSelectedVeranlassungBeanForPaste() {
        VeranlassungCustomBean veranlassungCustomBean = null;
        final Collection<TreePath> paths = broker.getWorkbenchWidget().getSelectedTreeNodes();
        if ((paths != null) && (paths.size() == 1)) {
            final CustomMutableTreeTableNode node = (CustomMutableTreeTableNode)paths.iterator().next()
                        .getLastPathComponent();
            if (node != null) {
                final Object object = node.getUserObject();
                if (object instanceof VeranlassungCustomBean) {
                    veranlassungCustomBean = (VeranlassungCustomBean)object;
                }
            }
        }
        return veranlassungCustomBean;
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
