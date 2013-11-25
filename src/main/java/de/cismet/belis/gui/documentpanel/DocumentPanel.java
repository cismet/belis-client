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

import org.jdesktop.observablecollections.ObservableList;

import java.awt.Cursor;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.IOException;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.TransferHandler;
import javax.swing.TransferHandler.TransferSupport;

import de.cismet.belis.gui.utils.UIUtils;

import de.cismet.cids.custom.beans.belis.DmsUrlCustomBean;

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

    static {
        // Prepare the icons
        BUSY_ICONS = new Icon[15];
        for (int i = 0; i < BUSY_ICONS.length; i++) {
            BUSY_ICONS[i] = new ImageIcon(DocumentPanel.class.getResource(ICON_RES_PATH + "busy-icon" + i + ".png"));
        }
        IDLE_ICON = new ImageIcon(DocumentPanel.class.getResource(ICON_RES_PATH + "idle-icon.png"));
    }

    //~ Instance fields --------------------------------------------------------

    // --
    // private final DefaultListModel docListModel;
    private final Timer busyIconTimer;
    private int busyIconIndex = 0;
    private SwingWorker<ImageIcon, Void> previewWorker;
    // private DocumentContainer currentEntity = null;
    private Collection<DmsUrlCustomBean> dokumente = null;
    private boolean inEditMode = false;

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
// public DocumentPanel(final Collection<DmsUrl> listFiles) {
// this();
// setDocumentList(listFiles);
// lstDocList.setSelectedIndex(lstDocList.getFirstVisibleIndex());
// }
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
    // --

//    public void setCurrentEntity(final DocumentContainer dc) {
//        this.currentEntity = dc;
//    }
//
//    public DocumentContainer getCurrentEntity() {
//        return this.currentEntity;
//    }
    /**
     * DOCUMENT ME!
     */
//    public void setDocumentList(final Collection<DmsUrl> urls) {
//        docListModel.removeAllElements();
//        for (final DmsUrl f : urls) {
//            docListModel.addElement(f);
//        }
//    }
    private void openSelectionInBrowser() {
        final Object sel = lstDocList.getSelectedValue();
        if (sel instanceof DmsUrlCustomBean) {
            final DmsUrlCustomBean dmsUrl = (DmsUrlCustomBean)sel;
            if (dmsUrl.getUrl() != null) {
                final URL u = dmsUrl.getUrl().getURL();
                if (u != null) {
                    UIUtils.openURL(u.toString());
                }
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  urlString  DOCUMENT ME!
     */
    private void addURLtoList(final String urlString) {
        if (log.isDebugEnabled()) {
            log.debug("addURLToList set: " + getDokumente());
        }
        final String description = JOptionPane.showInputDialog(
                DocumentPanel.this,
                "Welche Beschriftung soll der Link haben?",
                urlString);
        if ((description != null) && (description.length() > 0)) {
            // docListModel.addElement(DmsUrl.createDmsURLFromLink(urlString, description));
            dokumente.add(DmsUrlCustomBean.createDmsURLFromLink(urlString, description));
        } else {
            // cancel case
            return;
        }
        if (log.isDebugEnabled()) {
            log.debug("addURLToList: " + getDokumente());
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
                final File document = url.toFile();
                if (document != null) {
                    busyIconTimer.start();
                    final SwingWorker<ImageIcon, Void> oldWorker = previewWorker;
                    if (oldWorker != null) {
                        oldWorker.cancel(true);
                    }
                    previewWorker = new PreviewWorker(document);
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
            openSelectionInBrowser();
        }
    }                                                                          //GEN-LAST:event_lblPreviewMouseClicked

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void lstDocListMouseClicked(final java.awt.event.MouseEvent evt) { //GEN-FIRST:event_lstDocListMouseClicked
        if ((evt.getClickCount() > 1) && !evt.isPopupTrigger()) {
            openSelectionInBrowser();
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
                            for (final Object o : mp) {
                                if (o instanceof File) {
                                    final File f = (File)o;
                                    addURLtoList(f.toURI().toString());
                                }
                            }
                            return true;
                        }
                    }
                }
                for (int i = 0; i < flavors.length; ++i) {
                    if (flavors[i].equals(DataFlavor.stringFlavor)) {
                        // CAST
                        final String urls = (String)tr.getTransferData(DataFlavor.stringFlavor);
                        final StringTokenizer tokens = new StringTokenizer(urls);
                        while (tokens.hasMoreTokens()) {
                            final String urlString = tokens.nextToken();
                            addURLtoList(urlString);
                        }
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

        private final File document;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new PreviewWorker object.
         *
         * @param  document  DOCUMENT ME!
         */
        public PreviewWorker(final File document) {
            this.document = document;
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
            if ((document != null) && document.isFile() && !isCancelled()) {
                // setText-methods are threadsafe!
                lblPreview.setText("loading...");
                return UIUtils.loadPicture(document.getAbsolutePath(),
                        panPreviewScp.getWidth()
                                - INSET
                                - SHADOW_SIZE,
                        panPreviewScp.getHeight()
                                - INSET
                                - SHADOW_SIZE,
                        SHADOW_SIZE);
            }
            return null;
        }

        @Override
        protected void done() {
            if (!isCancelled()) {
                lblAbsolutePath.setText(document.getAbsolutePath());
                lblAbsolutePath.setToolTipText(document.getAbsolutePath());
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
}
