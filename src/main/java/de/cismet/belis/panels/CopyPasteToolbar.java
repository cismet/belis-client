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
package de.cismet.belis.panels;

import Sirius.server.middleware.types.MetaClass;

import java.awt.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.tree.TreePath;

import de.cismet.belis.broker.BelisBroker;
import de.cismet.belis.broker.CsvExportBackend;
import de.cismet.belis.broker.EntityClipboard;
import de.cismet.belis.broker.EntityClipboardListener;

import de.cismet.belis.todo.CustomMutableTreeTableNode;

import de.cismet.cids.custom.beans.belis2.ArbeitsauftragCustomBean;
import de.cismet.cids.custom.beans.belis2.ArbeitsprotokollCustomBean;
import de.cismet.cids.custom.beans.belis2.TdtaLeuchtenCustomBean;
import de.cismet.cids.custom.beans.belis2.TdtaStandortMastCustomBean;
import de.cismet.cids.custom.beans.belis2.VeranlassungCustomBean;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.commons.architecture.interfaces.Editable;

import de.cismet.tools.gui.StaticSwingTools;
import de.cismet.tools.gui.downloadmanager.ByteArrayDownload;
import de.cismet.tools.gui.downloadmanager.DownloadManager;
import de.cismet.tools.gui.downloadmanager.DownloadManagerDialog;

/**
 * DOCUMENT ME!
 *
 * @author   Gilles Baatz
 * @version  $Revision$, $Date$
 */
public class CopyPasteToolbar extends javax.swing.JPanel implements Editable, EntityClipboardListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(CopyPasteToolbar.class);

    //~ Instance fields --------------------------------------------------------

    private BelisBroker broker;
    private EntityClipboard entityClipboard;
    private final EntityClipboardListener cbListener = new EntityClipboardListener() {

            @Override
            public void clipboardChanged() {
                btnCopy.setEnabled(entityClipboard.isCopyable());
                btnPaste.setEnabled(entityClipboard.isPastable());
            }
        };

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCopy;
    private javax.swing.JButton btnExportCsv;
    private javax.swing.JButton btnPaste;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form EditButtonsPanel.
     *
     * @param  broker  DOCUMENT ME!
     */
    public CopyPasteToolbar(final BelisBroker broker) {
        this.broker = broker;
        initComponents();

        broker.getEntityClipboard().addListener(this);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        btnCopy = new javax.swing.JButton();
        btnPaste = new javax.swing.JButton();
        btnExportCsv = new javax.swing.JButton();

        setOpaque(false);
        setLayout(new java.awt.GridBagLayout());

        btnCopy.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/16/copy.png"))); // NOI18N
        btnCopy.setEnabled(false);
        btnCopy.setFocusable(false);
        btnCopy.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnCopyActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        add(btnCopy, gridBagConstraints);

        btnPaste.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/16/paste.png"))); // NOI18N
        btnPaste.setEnabled(false);
        btnPaste.setFocusable(false);
        btnPaste.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnPasteActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        add(btnPaste, gridBagConstraints);

        btnExportCsv.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/16/table-import.png"))); // NOI18N
        btnExportCsv.setText(org.openide.util.NbBundle.getMessage(
                CopyPasteToolbar.class,
                "CopyPasteToolbar.btnExportCsv.text"));                                         // NOI18N
        btnExportCsv.setFocusable(false);
        btnExportCsv.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnExportCsvActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        add(btnExportCsv, gridBagConstraints);
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnCopyActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnCopyActionPerformed
        try {
            broker.getEntityClipboard().copy();
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Fehler beim Kopieren in die Zwischenablage", ex);
            }
        }
    }                                                                           //GEN-LAST:event_btnCopyActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnPasteActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnPasteActionPerformed
        try {
            broker.getEntityClipboard().paste();
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Fehler beim Einfügen aus der Zwischenablage", ex);
            }
        }
    }                                                                            //GEN-LAST:event_btnPasteActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnExportCsvActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnExportCsvActionPerformed
        final CsvExportBackend backend = CsvExportBackend.getInstance();

        final Collection<TreePath> paths = broker.getWorkbenchWidget().getSelectedTreeNodes();
        if (paths != null) {
            // hauptobjekte auch aus protokolle, veranlassungen, arbeitsaufträgen heraus
            final Collection<CidsBean> mainBeans = new ArrayList<CidsBean>();
            for (final TreePath path : paths) {
                final CustomMutableTreeTableNode node = (CustomMutableTreeTableNode)path.getLastPathComponent();
                if (node != null) {
                    final Object object = node.getUserObject();
                    if (object instanceof ArbeitsauftragCustomBean) {
                        final ArbeitsauftragCustomBean auftrag = (ArbeitsauftragCustomBean)object;
                        for (final ArbeitsprotokollCustomBean protokoll : auftrag.getAr_protokolle()) {
                            final CidsBean childBean = protokoll.getChildEntity();
                            if (!mainBeans.contains(childBean)) {
                                mainBeans.add(childBean);
                            }
                        }
                    } else if (object instanceof ArbeitsprotokollCustomBean) {
                        final ArbeitsprotokollCustomBean protokoll = (ArbeitsprotokollCustomBean)object;
                        final CidsBean childBean = protokoll.getChildEntity();
                        if (!mainBeans.contains(childBean)) {
                            mainBeans.add(childBean);
                        }
                    } else if (object instanceof VeranlassungCustomBean) {
                        final VeranlassungCustomBean veranlassung = (VeranlassungCustomBean)object;
                        final Collection<CidsBean> allChildBeans = new ArrayList<CidsBean>();
                        allChildBeans.addAll(veranlassung.getAr_abzweigdosen());
                        allChildBeans.addAll(veranlassung.getAr_leitungen());
                        allChildBeans.addAll(veranlassung.getAr_leuchten());
                        allChildBeans.addAll(veranlassung.getAr_mauerlaschen());
                        allChildBeans.addAll(veranlassung.getAr_schaltstellen());
                        allChildBeans.addAll(veranlassung.getAr_standorte());
                        for (final CidsBean childBean : allChildBeans) {
                            if (!mainBeans.contains(childBean)) {
                                mainBeans.add(childBean);
                            }
                        }
                    } else if (object instanceof CidsBean) {
                        if (!mainBeans.contains((CidsBean)object)) {
                            mainBeans.add((CidsBean)object);
                        }
                    }
                }
            }

            // Sonderbehandlung: leuchten erzeugen auch standort und umgekehrt
            final Collection<CidsBean> beans = new ArrayList<CidsBean>();
            for (final CidsBean mainBean : mainBeans) {
                if (mainBean instanceof TdtaStandortMastCustomBean) {
                    final TdtaStandortMastCustomBean standort = (TdtaStandortMastCustomBean)mainBean;
                    if (!beans.contains(standort)) {
                        beans.add(standort);
                    }

                    if (standort.getLeuchten() != null) {
                        for (final CidsBean cidsBean : standort.getLeuchten()) {
                            if (!beans.contains(cidsBean)) {
                                beans.add(cidsBean);
                            }
                        }
                    }
                } else if (mainBean instanceof TdtaLeuchtenCustomBean) {
                    final TdtaLeuchtenCustomBean leuchte = (TdtaLeuchtenCustomBean)mainBean;
                    if (!beans.contains(leuchte)) {
                        beans.add(leuchte);
                    }

                    final TdtaStandortMastCustomBean standort = leuchte.getFk_standort();
                    if (standort != null) {
                        if (!beans.contains(standort)) {
                            beans.add(standort);
                        }
                    }
                } else {
                    if (!beans.contains(mainBean)) {
                        beans.add(mainBean);
                    }
                }
            }

            final Map<MetaClass, String> csvStringMap = backend.toCsvStrings(beans);
            if (!csvStringMap.isEmpty()) {
                final JDialog downloadManager = DownloadManagerDialog.instance((Component)StaticSwingTools
                                .getParentFrame(
                                    this));
                for (final MetaClass metaClass : csvStringMap.keySet()) {
                    final String title = metaClass.getName();
                    final String body = csvStringMap.get(metaClass);

                    DownloadManager.instance()
                            .add(new ByteArrayDownload(
                                    body.getBytes(),
                                    title,
                                    DownloadManagerDialog.getJobname(),
                                    title,
                                    ".csv"));
                }
                downloadManager.pack();
                StaticSwingTools.showDialog(downloadManager);
            }
        }
    } //GEN-LAST:event_btnExportCsvActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public EntityClipboardListener getClipboardListener() {
        return cbListener;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  entityClipboard  DOCUMENT ME!
     */
    public void setClipboard(final EntityClipboard entityClipboard) {
        this.entityClipboard = entityClipboard;
    }

    @Override
    public void setWidgetEditable(final boolean isEditable) {
        if (!isEditable) {
            setAllButtonsEnabled(isEditable);
//        } else {
//            checkSetButtonState();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isEnabled  DOCUMENT ME!
     */
    private void setAllButtonsEnabled(final boolean isEnabled) {
        btnCopy.setEnabled(isEnabled);
        btnPaste.setEnabled(isEnabled);
    }

    @Override
    public final void clipboardChanged() {
        btnCopy.setEnabled(broker.getEntityClipboard().isCopyable());
        btnPaste.setEnabled(broker.getEntityClipboard().isPastable());
    }
}
