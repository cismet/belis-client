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
/*
 * DocumentPanel.java
 *
 * Created on 15.05.2009, 09:09:49
 */
package de.cismet.belis.gui.documentpanel;

import org.apache.log4j.Logger;

import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.error.ErrorInfo;

import java.awt.Cursor;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.TransferHandler;
import javax.swing.TransferHandler.TransferSupport;

import de.cismet.belis.gui.utils.UIUtils;

import de.cismet.cids.custom.beans.belis2.DmsUrlCustomBean;

import de.cismet.commons.security.WebDavClient;
import de.cismet.commons.security.WebDavHelper;

import de.cismet.netutil.Proxy;

import de.cismet.tools.CismetThreadPool;
import de.cismet.tools.PasswordEncrypter;

import de.cismet.tools.gui.StaticSwingTools;
import de.cismet.tools.gui.WaitDialog;
import de.cismet.tools.gui.downloadmanager.DownloadManager;
import de.cismet.tools.gui.downloadmanager.DownloadManagerDialog;
import de.cismet.tools.gui.downloadmanager.WebDavDownload;

/**
 * DOCUMENT ME!
 *
 * @author   srichter
 * @version  $Revision$, $Date$
 */
public final class DocumentPanel extends javax.swing.JPanel {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger log = org.apache.log4j.Logger.getLogger(DocumentPanel.class);
    private static final String ICON_RES_PATH = "/de/cismet/belis/resource/icon/16/";
    private static final ImageIcon NO_PREVIEW = new ImageIcon(DocumentPanel.class.getResource(
                "/de/cismet/belis/resource/icon/16/nopreview.png"));
    private static final ExecutorService THREAD_EXECUTOR = Executors.newCachedThreadPool();
    public static final int SHADOW_SIZE = 4;
    public static final int INSET = 55;
    public static final int ANIMATION_RATE = 30;
    public static final String EXTENSIONS = "\\.(jpg|jpeg|gif|png|pdf|html|doc|xls|txt)";
    private static final Icon IDLE_ICON;
    private static final Icon[] BUSY_ICONS;
    private static final String WEB_DAV_USER;
    private static final String WEB_DAV_PASSWORD;
    private static final String WEB_DAV_DIRECTORY;

    static {
        // Prepare the icons
        BUSY_ICONS = new Icon[15];
        for (int i = 0; i < BUSY_ICONS.length; i++) {
            BUSY_ICONS[i] = new ImageIcon(DocumentPanel.class.getResource(ICON_RES_PATH + "busy-icon" + i + ".png"));
        }
        IDLE_ICON = new ImageIcon(DocumentPanel.class.getResource(ICON_RES_PATH + "idle-icon.png"));

        String pass = null;
        String user = null;
        String webDavRoot = null;
        try {
            final ResourceBundle bundle = ResourceBundle.getBundle("WebDavBelis");
            pass = bundle.getString("password");
            user = bundle.getString("username");
            webDavRoot = bundle.getString("url");

            if ((pass != null) && pass.startsWith(PasswordEncrypter.CRYPT_PREFIX)) {
                pass = PasswordEncrypter.decryptString(pass);
            }
        } catch (final Exception ex) {
        } finally {
            WEB_DAV_PASSWORD = pass;
            WEB_DAV_USER = user;
            WEB_DAV_DIRECTORY = webDavRoot;
        }
    }

    //~ Instance fields --------------------------------------------------------

    private Collection<DmsUrlCustomBean> removeNewAddedFotoBean = new ArrayList<DmsUrlCustomBean>();

    // --
    // private final DefaultListModel docListModel;
    private final Timer busyIconTimer;
    private int busyIconIndex = 0;
    private SwingWorker<ImageIcon, Void> previewWorker;
    private Collection<DmsUrlCustomBean> dokumente = null;
    private boolean inEditMode = false;
    private WebDavClient webDavClient;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblAbsolutePath;
    private javax.swing.JLabel lblPreview;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JList lstDocList;
    private javax.swing.JMenuItem miDelete;
    private javax.swing.JPanel panList;
    private javax.swing.JPanel panPreviewIntern;
    private javax.swing.JPanel panPreviewScp;
    private javax.swing.JPanel panStatus;
    private javax.swing.JPopupMenu popMenu;
    private javax.swing.JScrollPane scpDocList;
    private javax.swing.JScrollPane scpPreview;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new DocumentPanel object.
     */
    /**
     * Creates a new DocumentPanel object.
     */
    public DocumentPanel() {
        // docListModel = new DocumentListModel();
        initComponents();
        // Enable "delete"-key to remove selected items from list
        final Action deleteAction = new AbstractAction() {

                @Override
                public void actionPerformed(final ActionEvent e) {
                    if (inEditMode) {
                        deleteSelectedListItems();
                    } else {
                        if (log.isDebugEnabled()) {
                            log.debug("Can not remove document because it not in edit mode.");
                        }
                    }
                }
            };

        final InputMap im = getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        final KeyStroke delete = KeyStroke.getKeyStroke("DELETE");
        im.put(delete, "delete");
        getActionMap().put("delete", deleteAction);
        // Drag and Drop for the list
        lstDocList.setTransferHandler(new DocListTransferHandler());
        lstDocList.setDragEnabled(true);
        // Hand cursor on mouseover for the preview label
        UIUtils.decorateComponentWithMouseOverCursorChange(lblPreview, Cursor.HAND_CURSOR, Cursor.DEFAULT_CURSOR);
        // Set ListCellRenderer that recognizes important filetypes
        lstDocList.setCellRenderer(new DocumentListCellRenderer());
        // Configure the spinner animation
        final int busyAnimationRate = ANIMATION_RATE;
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {

                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        busyIconIndex = (busyIconIndex + 1) % BUSY_ICONS.length;
                        lblStatus.setIcon(BUSY_ICONS[busyIconIndex]);
                    }
                });
        lblStatus.setIcon(IDLE_ICON);
        this.webDavClient = new WebDavClient(Proxy.fromPreferences(), WEB_DAV_USER, WEB_DAV_PASSWORD);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Collection<DmsUrlCustomBean> getDokumente() {
        return dokumente;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  dokumente  DOCUMENT ME!
     */
    public void setDokumente(final Collection<DmsUrlCustomBean> dokumente) {
        if (log.isDebugEnabled()) {
            log.debug("setDokumente");
        }
        this.dokumente = dokumente;
        firePropertyChange("DocumentPanel.Dokumente", null, dokumente);
        bindingGroup.unbind();
        bindingGroup.bind();
    }

    /**
     * DOCUMENT ME!
     */
    private void downloadSelection() {
        final Object sel = lstDocList.getSelectedValue();
        if (sel instanceof DmsUrlCustomBean) {
            final DmsUrlCustomBean bean = (DmsUrlCustomBean)sel;
            if (DownloadManagerDialog.showAskingForUserTitle(this)) {
                final String jobname = DownloadManagerDialog.getJobname();
                final String name = bean.getDescription();
                final String file = bean.toUri().getPath().substring(bean.toUri().getPath().lastIndexOf("/") + 1);
                final String fserverName = bean.toUri()
                            .getPath()
                            .substring(bean.toUri().getPath().lastIndexOf("/") + 1);
                String extension = "";
                if (fserverName.lastIndexOf(".") != -1) {
                    extension = fserverName.substring(fserverName.lastIndexOf("."));
                }
                String filename = name;

                if (name.lastIndexOf(".") != -1) {
                    filename = name.substring(0, name.lastIndexOf("."));
                }

                DownloadManager.instance()
                        .add(new WebDavDownload(
                                webDavClient,
                                WEB_DAV_DIRECTORY
                                + WebDavHelper.encodeURL(file),
                                jobname,
                                filename
                                + extension,
                                filename,
                                extension));
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   urlString  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private String addURLtoList(final String urlString) {
        if (log.isDebugEnabled()) {
            log.debug("addURLToList set: " + getDokumente());
        }
        final String docName = urlString.substring(urlString.lastIndexOf("/") + 1);
        final String description = JOptionPane.showInputDialog(
                DocumentPanel.this,
                "Welche Beschriftung soll der Link haben?",
                docName);
        if ((description != null) && (description.length() > 0)) {
            // docListModel.addElement(DmsUrl.createDmsURLFromLink(urlString, description));
            if (log.isDebugEnabled()) {
                log.debug("addURLToList: " + getDokumente());
            }
            return description;
        } else {
            // cancel case
            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fileList  DOCUMENT ME!
     */
    public void addFiles(final List<DocumentStruct> fileList) {
        if ((fileList != null) && (fileList.size() > 0)) {
            final WaitDialog wd = new WaitDialog(
                    StaticSwingTools.getParentFrame(this),
                    true,
                    "Speichere Dokument",
                    null);
            CismetThreadPool.execute(new DocumentUploadWorker(fileList, wd));
            StaticSwingTools.showDialog(wd);
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void deleteSelectedListItems() {
        if (log.isDebugEnabled()) {
            log.debug("deleteSelectedListItems: " + getDokumente());
        }
        for (final Object sel : lstDocList.getSelectedValuesList()) {
            dokumente.remove(sel);
        }
        final SwingWorker<?, ?> sw = previewWorker;
        if (sw != null) {
            sw.cancel(true);
        }
        lblPreview.setIcon(null);
        lblPreview.setText("");
        lstDocList.setSelectedIndex(lstDocList.getFirstVisibleIndex());
        if (log.isDebugEnabled()) {
            log.debug("deleteSelectedListItems: " + getDokumente());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        popMenu = new javax.swing.JPopupMenu();
        miDelete = new javax.swing.JMenuItem();
        panStatus = new javax.swing.JPanel();
        lblAbsolutePath = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        panList = new javax.swing.JPanel();
        scpDocList = new javax.swing.JScrollPane();
        lstDocList = new javax.swing.JList();
        panPreviewScp = new javax.swing.JPanel();
        scpPreview = new javax.swing.JScrollPane();
        panPreviewIntern = new javax.swing.JPanel();
        lblPreview = new javax.swing.JLabel();

        miDelete.setText("Löschen");
        miDelete.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    miDeleteActionPerformed(evt);
                }
            });
        popMenu.add(miDelete);

        setLayout(new java.awt.GridBagLayout());

        panStatus.setMaximumSize(new java.awt.Dimension(15, 25));
        panStatus.setMinimumSize(new java.awt.Dimension(15, 25));
        panStatus.setPreferredSize(new java.awt.Dimension(15, 25));
        panStatus.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
        panStatus.add(lblAbsolutePath);
        panStatus.add(lblStatus);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(panStatus, gridBagConstraints);

        panList.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createTitledBorder("Dokumente"),
                javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        panList.setLayout(new java.awt.BorderLayout());

        scpDocList.setMaximumSize(new java.awt.Dimension(200, 250));
        scpDocList.setMinimumSize(new java.awt.Dimension(200, 250));
        scpDocList.setPreferredSize(new java.awt.Dimension(200, 250));

        final org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create(
                "${dokumente}");
        final org.jdesktop.swingbinding.JListBinding jListBinding = org.jdesktop.swingbinding.SwingBindings
                    .createJListBinding(
                        org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                        this,
                        eLProperty,
                        lstDocList);
        bindingGroup.addBinding(jListBinding);

        lstDocList.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(final java.awt.event.MouseEvent evt) {
                    lstDocListMouseClicked(evt);
                }
                @Override
                public void mousePressed(final java.awt.event.MouseEvent evt) {
                    lstDocListMousePressed(evt);
                }
                @Override
                public void mouseReleased(final java.awt.event.MouseEvent evt) {
                    lstDocListMouseReleased(evt);
                }
            });
        lstDocList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {

                @Override
                public void valueChanged(final javax.swing.event.ListSelectionEvent evt) {
                    lstDocListValueChanged(evt);
                }
            });
        scpDocList.setViewportView(lstDocList);

        panList.add(scpDocList, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        add(panList, gridBagConstraints);

        panPreviewScp.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createTitledBorder("Vorschau"),
                javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        panPreviewScp.setLayout(new java.awt.BorderLayout());

        scpPreview.setMaximumSize(new java.awt.Dimension(225, 250));
        scpPreview.setMinimumSize(new java.awt.Dimension(225, 250));
        scpPreview.setPreferredSize(new java.awt.Dimension(225, 250));

        panPreviewIntern.setLayout(new java.awt.GridBagLayout());

        lblPreview.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPreview.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblPreview.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(final java.awt.event.MouseEvent evt) {
                    lblPreviewMouseClicked(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        panPreviewIntern.add(lblPreview, gridBagConstraints);

        scpPreview.setViewportView(panPreviewIntern);

        panPreviewScp.add(scpPreview, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        add(panPreviewScp, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void lstDocListValueChanged(final javax.swing.event.ListSelectionEvent evt) { //GEN-FIRST:event_lstDocListValueChanged
        lblPreview.setIcon(null);
        lblPreview.setText("");
        final Object toCast = lstDocList.getSelectedValue();
        if (toCast != null) {
            if (toCast instanceof DmsUrlCustomBean) {
                final DmsUrlCustomBean url = (DmsUrlCustomBean)toCast;
                final String document = url.toUri().getPath().substring(url.toUri().getPath().lastIndexOf("/") + 1);
                if (document != null) {
                    busyIconTimer.start();
                    final SwingWorker<ImageIcon, Void> oldWorker = previewWorker;
                    if (oldWorker != null) {
                        oldWorker.cancel(true);
                    }
                    previewWorker = new PreviewWorker(document, url.toUri().toString());
                    THREAD_EXECUTOR.execute(previewWorker);
                }
            }
        }
    }                                                                                     //GEN-LAST:event_lstDocListValueChanged

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void lblPreviewMouseClicked(final java.awt.event.MouseEvent evt) { //GEN-FIRST:event_lblPreviewMouseClicked
        if (!evt.isPopupTrigger()) {
            downloadSelection();
        }
    }                                                                          //GEN-LAST:event_lblPreviewMouseClicked

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void lstDocListMouseClicked(final java.awt.event.MouseEvent evt) { //GEN-FIRST:event_lstDocListMouseClicked
        if ((evt.getClickCount() > 1) && !evt.isPopupTrigger()) {
            downloadSelection();
        }
    }                                                                          //GEN-LAST:event_lstDocListMouseClicked

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void miDeleteActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_miDeleteActionPerformed
        deleteSelectedListItems();
    }                                                                            //GEN-LAST:event_miDeleteActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void lstDocListMousePressed(final java.awt.event.MouseEvent evt) { //GEN-FIRST:event_lstDocListMousePressed
        if (evt.isPopupTrigger() && !dokumente.isEmpty() && inEditMode) {
            popMenu.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }                                                                          //GEN-LAST:event_lstDocListMousePressed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void lstDocListMouseReleased(final java.awt.event.MouseEvent evt) { //GEN-FIRST:event_lstDocListMouseReleased
        if (evt.isPopupTrigger() && !dokumente.isEmpty() && inEditMode) {
            popMenu.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }                                                                           //GEN-LAST:event_lstDocListMouseReleased

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public JList getLstDocList() {
        return lstDocList;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  lstDocList  DOCUMENT ME!
     */
    public void setLstDocList(final JList lstDocList) {
        this.lstDocList = lstDocList;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  editable  DOCUMENT ME!
     */
    public void setEditable(final boolean editable) {
        inEditMode = editable;
    }

    @Override
    public void setOpaque(final boolean isOpaque) {
        super.setOpaque(isOpaque);
        if (panList != null) {
            panList.setOpaque(isOpaque);
        }
        if (panStatus != null) {
            panStatus.setOpaque(isOpaque);
        }
        if (panPreviewIntern != null) {
            panPreviewIntern.setOpaque(isOpaque);
        }
        if (panPreviewScp != null) {
            panPreviewScp.setOpaque(isOpaque);
        }
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    final class DocListTransferHandler extends TransferHandler {

        //~ Methods ------------------------------------------------------------

        @Override
        public int getSourceActions(final JComponent c) {
            if (log.isDebugEnabled()) {
                log.debug("getSourceAction");
            }
//---> Drag disabled
//            if (c == lstDocList) {
//                return DnDConstants.ACTION_COPY;
//            }
            return DnDConstants.ACTION_NONE;
        }

        @Override
        protected Transferable createTransferable(final JComponent c) {
            if (log.isDebugEnabled()) {
                log.debug("createTransferable");
            }
            if (c == lstDocList) {
                return new Transferable() {

                        @Override
                        public DataFlavor[] getTransferDataFlavors() {
                            return new DataFlavor[] { DataFlavor.javaFileListFlavor };
                        }

                        @Override
                        public boolean isDataFlavorSupported(final DataFlavor flavor) {
                            return DataFlavor.javaFileListFlavor.equals(flavor);
                        }

                        @Override
                        public Object getTransferData(final DataFlavor flavor) throws UnsupportedFlavorException,
                            IOException {
                            final Object[] vals = lstDocList.getSelectedValues();
                            final List<DmsUrlCustomBean> urlList = new ArrayList<DmsUrlCustomBean>();
                            for (final Object o : vals) {
                                if (o instanceof DmsUrlCustomBean) {
                                    urlList.add((DmsUrlCustomBean)o);
                                }
                            }
                            return urlList;
                        }
                    };
            }
            return super.createTransferable(c);
        }

        @Override
        public boolean canImport(final TransferSupport support) {
            if (!inEditMode) {
                if (log.isDebugEnabled()) {
                    log.debug("Application is not in edit mode, no drag & drop possible");
                }
                return false;
            }
            final DataFlavor[] flavs = support.getDataFlavors();
            for (final DataFlavor df : flavs) {
                if (df.equals(DataFlavor.javaFileListFlavor) || df.equals(DataFlavor.stringFlavor)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean importData(final TransferSupport e) {
            try {
                final Transferable tr = e.getTransferable();
                final DataFlavor[] flavors = tr.getTransferDataFlavors();
                for (int i = 0; i < flavors.length; ++i) {
                    if (flavors[i].equals(DataFlavor.javaFileListFlavor)) {
                        // CAST
                        final Object possibleFileList = tr.getTransferData(flavors[i]);
                        if (possibleFileList instanceof List) {
                            final List mp = (List)possibleFileList;
//                            lstDocList.setEnabled(false);
                            // TODO add to list, erst File -> DmsUrl, dann adden
                            final List<DocumentStruct> docList = new ArrayList<DocumentStruct>();
                            for (final Object o : mp) {
                                if (o instanceof File) {
                                    final File f = (File)o;
                                    final String desc = addURLtoList(f.toURI().toString());

                                    if (desc != null) {
                                        docList.add(new DocumentStruct(desc, f));
                                    }
                                }
                            }
                            addFiles(docList);
                            return true;
                        }
                    }
                }
                for (int i = 0; i < flavors.length; ++i) {
                    if (flavors[i].equals(DataFlavor.stringFlavor)) {
                        // CAST
                        final List<DocumentStruct> docList = new ArrayList<DocumentStruct>();
                        final String urls = (String)tr.getTransferData(DataFlavor.stringFlavor);
                        final StringTokenizer tokens = new StringTokenizer(urls);
                        while (tokens.hasMoreTokens()) {
                            final String urlString = tokens.nextToken();
                            try {
                                final File f = new File(new URL(urlString).toURI());

                                if (f.exists()) {
                                    final String desc = addURLtoList(urlString);

                                    if (desc != null) {
                                        docList.add(new DocumentStruct(desc, f));
                                    }
                                }
                            } catch (MalformedURLException ex) {
                                log.error("malformed url", ex);
                            }
                        }

                        addFiles(docList);
                        return true;
                    }
                }
            } catch (Throwable t) {
            }
            // Ein Problem ist aufgetreten
            return false;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    final class PreviewWorker extends SwingWorker<ImageIcon, Void> {

        //~ Instance fields ----------------------------------------------------

        private final String document;
        private final String absPath;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new PreviewWorker object.
         *
         * @param  document  DOCUMENT ME!
         * @param  absPath   DOCUMENT ME!
         */
        public PreviewWorker(final String document, final String absPath) {
            this.document = document;
            this.absPath = absPath;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        protected ImageIcon doInBackground() throws Exception {
            // smoothen fast list selection, only starting the procedure for a definite selection
            try {
                Thread.sleep(100);
            } catch (InterruptedException ie) {
                // ignore
            }

            if ((document != null) && !isCancelled()) {
                lblPreview.setText("loading...");
                // setText-methods are threadsafe!
                return UIUtils.loadPicture(
                        document,
                        panPreviewScp.getWidth()
                                - INSET
                                - SHADOW_SIZE,
                        panPreviewScp.getHeight()
                                - INSET
                                - SHADOW_SIZE,
                        SHADOW_SIZE,
                        webDavClient,
                        WEB_DAV_DIRECTORY,
                        DocumentPanel.this);
            }
            return null;
        }

        @Override
        protected void done() {
            if (!isCancelled()) {
                lblAbsolutePath.setText("");
                lblAbsolutePath.setToolTipText("");
                ImageIcon icon = null;
                try {
                    icon = get();
                } catch (InterruptedException ex) {
                    // todo/nothing
                } catch (ExecutionException ex) {
                    // todo/nothing
                }
                if (document != null) {
                    if (icon != null) {
                        lblPreview.setSize(icon.getIconHeight() + 1, icon.getIconWidth() + 1);
                        lblPreview.setIcon(icon);
                        lblPreview.setText("");
                    } else if (document != null) {
                        lblPreview.setIcon(NO_PREVIEW);
                        lblPreview.setSize(NO_PREVIEW.getIconWidth(), NO_PREVIEW.getIconHeight());
                        lblPreview.setText("<html>Could not create preview.<br>Click to open File!</html>");
                    }
                } else {
                    lblPreview.setIcon(null);
                    lblPreview.setText("");
                    lblPreview.setSize(0, 0);
                }
                busyIconTimer.stop();
                lblStatus.setIcon(IDLE_ICON);
            }
            previewWorker = null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class DocumentStruct {

        //~ Instance fields ----------------------------------------------------

        private String name;
        private File file;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new DocumentStruct object.
         *
         * @param  name  DOCUMENT ME!
         * @param  file  DOCUMENT ME!
         */
        public DocumentStruct(final String name, final File file) {
            this.name = name;
            this.file = file;
        }

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @return  the name
         */
        public String getName() {
            return name;
        }

        /**
         * DOCUMENT ME!
         *
         * @param  name  the name to set
         */
        public void setName(final String name) {
            this.name = name;
        }

        /**
         * DOCUMENT ME!
         *
         * @return  the file
         */
        public File getFile() {
            return file;
        }

        /**
         * DOCUMENT ME!
         *
         * @param  file  the file to set
         */
        public void setFile(final File file) {
            this.file = file;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    final class DocumentUploadWorker extends SwingWorker<Collection<DmsUrlCustomBean>, Void> {

        //~ Static fields/initializers -----------------------------------------

        private static final String FILE_PREFIX = "DOC-";

        //~ Instance fields ----------------------------------------------------

        private final Collection<DocumentStruct> docs;
        private final WaitDialog wd;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new ImageUploadWorker object.
         *
         * @param  docs  fotos DOCUMENT ME!
         * @param  wd    DOCUMENT ME!
         */
        public DocumentUploadWorker(final Collection<DocumentStruct> docs, final WaitDialog wd) {
            this.docs = docs;
            this.wd = wd;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        protected Collection<DmsUrlCustomBean> doInBackground() throws Exception {
            try {
                final Collection<DmsUrlCustomBean> newBeans = new ArrayList<DmsUrlCustomBean>();
                for (final DocumentStruct doc : docs) {
                    final File imageFile = doc.getFile();
                    final String webFileName = WebDavHelper.generateWebDAVFileName(FILE_PREFIX, imageFile);
                    WebDavHelper.uploadFileToWebDAV(
                        webFileName,
                        imageFile,
                        WEB_DAV_DIRECTORY,
                        webDavClient,
                        DocumentPanel.this);
                    newBeans.add(DmsUrlCustomBean.createDmsURLFromLink(WEB_DAV_DIRECTORY + webFileName, doc.getName()));
                }
                return newBeans;
            } finally {
                while (!wd.isVisible()) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        // nothing to do
                    }
                }

                wd.setVisible(false);
                wd.dispose();
            }
        }

        @Override
        protected void done() {
            try {
                final Collection<DmsUrlCustomBean> newBeans = get();

                if (!newBeans.isEmpty()) {
                    dokumente.addAll(newBeans);
                    removeNewAddedFotoBean.addAll(newBeans);
                }
            } catch (InterruptedException ex) {
                log.warn(ex, ex);
            } catch (Exception ex) {
                log.error(ex, ex);
                final ErrorInfo ei = new ErrorInfo(
                        "Fehler",
                        "Beim Hochladen des Dokumentes ist ein Fehler aufgetreten.",
                        null,
                        null,
                        ex,
                        Level.SEVERE,
                        null);
                JXErrorPane.showDialog(DocumentPanel.this, ei);
            } finally {
            }
        }
    }
}
