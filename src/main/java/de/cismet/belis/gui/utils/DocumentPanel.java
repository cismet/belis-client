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
package de.cismet.belis.gui.utils;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.error.ErrorInfo;
import org.jdesktop.swingx.graphics.ShadowRenderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultListCellRenderer;
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

import de.cismet.belis.commons.constants.BelisMetaClassConstants;

import de.cismet.belis2.server.action.BelisWebDavTunnelAction;

import de.cismet.cids.client.tools.WebDavTunnelHelper;

import de.cismet.cids.custom.beans.belis2.DmsUrlCustomBean;

import de.cismet.cismap.commons.gui.printing.BackgroundTaskDownload;

import de.cismet.connectioncontext.ConnectionContext;

import de.cismet.tools.CismetThreadPool;
import de.cismet.tools.Static2DTools;

import de.cismet.tools.gui.StaticSwingTools;
import de.cismet.tools.gui.WaitDialog;
import de.cismet.tools.gui.downloadmanager.DownloadManager;
import de.cismet.tools.gui.downloadmanager.DownloadManagerDialog;

/**
 * DOCUMENT ME!
 *
 * @author   srichter
 * @version  $Revision$, $Date$
 */
public final class DocumentPanel extends javax.swing.JPanel {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger LOG = org.apache.log4j.Logger.getLogger(DocumentPanel.class);
    private static final String ICON_RES_PATH = "/de/cismet/belis/resource/icon/16/";
    private static final ImageIcon NO_PREVIEW = new ImageIcon(DocumentPanel.class.getResource(
                "/de/cismet/belis/resource/icon/16/nopreview.png"));
    private static final ExecutorService THREAD_EXECUTOR = Executors.newCachedThreadPool();
    public static final int SHADOW_SIZE = 4;
    public static final int INSET = 55;
    public static final int ANIMATION_RATE = 30;
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

    private static final String THUMBNAIL_SUFFIX_WITHOUT_EXTENSION = ".thumbnail";

    private static final ImageIcon UNKNOWN_FORMAT = new ImageIcon(DocumentPanel.class.getResource(
                ICON_RES_PATH
                        + "unknown.png"));
    public static final String EXTENTION_PDF = "pdf";
    public static final String EXTENTION_JPG = "jpg";
    public static final String EXTENTION_JPEG = "jpeg";
    public static final String EXTENTION_GIF = "gif";
    public static final String EXTENTION_PNG = "png";
    public static final String EXTENTION_BMP = "bmp";
    public static final String EXTENTION_HTML = "html";
    public static final String EXTENTION_DOC = "doc";
    public static final String EXTENTION_XLS = "xls";
    private static final Map<String, ImageIcon> FILE_TYPE_ICONS;

    static {
        final Class<DocumentPanel> c = DocumentPanel.class;
        FILE_TYPE_ICONS = new HashMap<>();
        FILE_TYPE_ICONS.put(EXTENTION_PDF, new ImageIcon(c.getResource(ICON_RES_PATH + "pdf.png")));
        FILE_TYPE_ICONS.put(EXTENTION_JPG, new ImageIcon(c.getResource(ICON_RES_PATH + "image.png")));
        FILE_TYPE_ICONS.put(EXTENTION_JPEG, new ImageIcon(c.getResource(ICON_RES_PATH + "image.png")));
        FILE_TYPE_ICONS.put(EXTENTION_GIF, new ImageIcon(c.getResource(ICON_RES_PATH + "image.png")));
        FILE_TYPE_ICONS.put(EXTENTION_BMP, new ImageIcon(c.getResource(ICON_RES_PATH + "image.png")));
        FILE_TYPE_ICONS.put(EXTENTION_PNG, new ImageIcon(c.getResource(ICON_RES_PATH + "image.png")));
        FILE_TYPE_ICONS.put(EXTENTION_HTML, new ImageIcon(c.getResource(ICON_RES_PATH + "html.png")));
        FILE_TYPE_ICONS.put(EXTENTION_DOC, new ImageIcon(c.getResource(ICON_RES_PATH + "doc.png")));
        FILE_TYPE_ICONS.put(EXTENTION_XLS, new ImageIcon(c.getResource(ICON_RES_PATH + "xls.png")));
    }

    //~ Instance fields --------------------------------------------------------

    // --
    // private final DefaultListModel docListModel;
    private final Timer busyIconTimer;
    private int busyIconIndex = 0;
    private SwingWorker<ImageIcon, Void> previewWorker;
    private Collection<DmsUrlCustomBean> dokumente = null;
    private boolean inEditMode = false;

    // Variables declaration - do not modify
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
    // End of variables declaration

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new DocumentPanel object.
     */
    /**
     * Creates a new DocumentPanel object.
     */
    public DocumentPanel() {
        initComponents();
        // Enable "delete"-key to remove selected items from list
        final Action deleteAction = new AbstractAction() {

                @Override
                public void actionPerformed(final ActionEvent e) {
                    if (inEditMode) {
                        deleteSelectedListItems();
                    } else {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Can not remove document because it not in edit mode.");
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
        decorateComponentWithMouseOverCursorChange(lblPreview, Cursor.HAND_CURSOR, Cursor.DEFAULT_CURSOR);
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
     * @param   in           DOCUMENT ME!
     * @param   shadowPixel  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static BufferedImage generateShadow(final Image in, final int shadowPixel) {
        final BufferedImage input;
        if (in instanceof BufferedImage) {
            input = (BufferedImage)in;
        } else {
            final BufferedImage temp = new BufferedImage(in.getWidth(null),
                    in.getHeight(null),
                    BufferedImage.TYPE_4BYTE_ABGR);
            final Graphics tg = temp.createGraphics();
            tg.drawImage(in, 0, 0, null);
            tg.dispose();
            input = temp;
        }

        final ShadowRenderer renderer = new ShadowRenderer(shadowPixel, 0.5f, Color.BLACK);
        final BufferedImage shadow = renderer.createShadow(input);
        final BufferedImage result = new BufferedImage(input.getWidth() + (2 * shadowPixel),
                input.getHeight()
                        + (2 * shadowPixel),
                BufferedImage.TYPE_4BYTE_ABGR);
        final Graphics2D rg = result.createGraphics();
        rg.drawImage(shadow, 0, 0, null);
        rg.drawImage(input, 0, 0, null);
        rg.dispose();
        return result;
    }

    /**
     * Starts a background thread with loads the picture from the url, resizes it to the given maximums, adds a
     * dropshadow of the given length.
     *
     * @param   bildURL     DOCUMENT ME!
     * @param   maxPixelX   DOCUMENT ME!
     * @param   maxPixelY   DOCUMENT ME!
     * @param   shadowSize  DOCUMENT ME!
     *
     * @return  ImageIcon with the loaded picture
     */
    public static ImageIcon loadPicture(final String bildURL,
            final int maxPixelX,
            final int maxPixelY,
            final int shadowSize) {
        if ((bildURL != null) && (bildURL.length() > 0)) {
            final String urlString = bildURL.trim();
            final Image buffImage = loadPreview(
                    urlString,
                    maxPixelX,
                    maxPixelY,
                    ConnectionContext.createDummy());
            if (buffImage != null) {
                if (shadowSize > 0) {
                    return new ImageIcon(generateShadow(buffImage, shadowSize));
                } else {
                    return new ImageIcon(buffImage);
                }
            }
        }
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   filenameFullsize   DOCUMENT ME!
     * @param   width              DOCUMENT ME!
     * @param   height             DOCUMENT ME!
     * @param   connectionContext  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private static Image loadPreview(final String filenameFullsize,
            final int width,
            final int height,
            final ConnectionContext connectionContext) {
        ImageIcon imageIcon = null;
        final String extension = extractExtension(filenameFullsize);
        if ((extension != null) && extension.matches("jpg|jpeg|gif|png|bmp")) { // NOI18N

            final WebDavTunnelHelper webDavClient = new WebDavTunnelHelper("BELIS2", BelisWebDavTunnelAction.TASK_NAME);
            try {
                final String filenameThumbnail = extendFilenameWithThumbnail(filenameFullsize);
                if (webDavClient.checkFileOnWebDAV(filenameThumbnail, connectionContext)) {
                    imageIcon = new ImageIcon(ImageIO.read(
                                webDavClient.getFileFromWebDAV(filenameThumbnail, connectionContext)));
                } else if (webDavClient.checkFileOnWebDAV(filenameFullsize, connectionContext)) {
                    imageIcon = new ImageIcon(ImageIO.read(
                                webDavClient.getFileFromWebDAV(filenameFullsize, connectionContext)));
                } else {
                    return null;
                }
            } catch (final Exception ex) {
                LOG.warn(ex, ex);
                try {
                    imageIcon = new ImageIcon(filenameFullsize);
                } catch (Exception ex2) {
                    LOG.error(ex2, ex2);
                    return null;
                }
            }
        }

        if ((imageIcon != null) && (imageIcon.getImage() != null) && (imageIcon.getImage().getWidth(null) > 0)) {
            final BufferedImage in;
            try {
                in = Static2DTools.toCompatibleImage(imageIcon.getImage());
            } catch (Exception ex) {
                LOG.error(ex, ex);
                return null;
            }
            // BufferedImage out=Static2DTools.getFasterScaledInstance(in, width, height,
            // RenderingHints.VALUE_INTERPOLATION_BILINEAR, true);
            int newHeight = 0;
            int newWidth = 0;
            final double widthToHeightRatio = (double)imageIcon.getIconWidth() / (double)imageIcon.getIconHeight();
            if ((widthToHeightRatio / ((double)width / (double)height)) < 1) {
                // height is the critical value and must be shrinked. in german: bestimmer ;-)
                newHeight = height;
                newWidth = (int)(height * widthToHeightRatio);
            } else {
                // width is the critical value and must be shrinked. in german: bestimmer ;-)
                newWidth = width;
                newHeight = (int)((double)width / (double)widthToHeightRatio);
            }
            return Static2DTools.getFasterScaledInstance(
                    in,
                    newWidth,
                    newHeight,
                    RenderingHints.VALUE_INTERPOLATION_BICUBIC,
                    true);
        }

        return null;
    }

    /**
     * Adds a mouse listener to the given component, so that the cursor will change on mouse entered/exited.
     *
     * <p>Hint: Uses the awt.Cursor.XXX constants!</p>
     *
     * @param   toDecorate    DOCUMENT ME!
     * @param   mouseEntered  DOCUMENT ME!
     * @param   mouseExited   DOCUMENT ME!
     *
     * @return  the listener that was added
     */
    public static MouseListener decorateComponentWithMouseOverCursorChange(final JComponent toDecorate,
            final int mouseEntered,
            final int mouseExited) {
        final MouseListener toAdd = new MouseAdapter() {

                @Override
                public void mouseEntered(final MouseEvent e) {
                    toDecorate.setCursor(new Cursor(mouseEntered));
                }

                @Override
                public void mouseExited(final MouseEvent e) {
                    toDecorate.setCursor(new Cursor(mouseExited));
                }
            };
        toDecorate.addMouseListener(toAdd);
        return toAdd;
    }

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
        if (LOG.isDebugEnabled()) {
            LOG.debug("setDokumente");
        }
        this.dokumente = dokumente;
        firePropertyChange("DocumentPanel.Dokumente", null, dokumente);
        bindingGroup.unbind();
        bindingGroup.bind();
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private WebDavTunnelHelper getWebdavHelper() {
        final WebDavTunnelHelper webdavHelper = new WebDavTunnelHelper(
                BelisMetaClassConstants.DOMAIN,
                BelisWebDavTunnelAction.TASK_NAME);
        return webdavHelper;
    }
    /**
     * DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    private void downloadSelection() {
        final Object sel = lstDocList.getSelectedValue();
        if (sel instanceof DmsUrlCustomBean) {
            final DmsUrlCustomBean bean = (DmsUrlCustomBean)sel;
            if (DownloadManagerDialog.getInstance().showAskingForUserTitleDialog(this)) {
                final String jobname = DownloadManagerDialog.getInstance().getJobName();
                final String description = bean.getDescription();
                final String filenameFullsize = (bean.getUrl() != null) ? bean.getUrl().getObject_name() : null;
                if ((description != null) && (filenameFullsize != null)) {
                    final String extension = extractExtension(filenameFullsize);

                    final WebDavTunnelHelper webdavHelper = getWebdavHelper();
                    final BackgroundTaskDownload.DownloadTask swingWorkerBackgroundTask =
                        new BackgroundTaskDownload.DownloadTask() {

                            @Override
                            public void download(final File fileToSaveTo) throws Exception {
                                try(final FileOutputStream out = new FileOutputStream(fileToSaveTo)) {
                                    final String filenameThumbnail = extendFilenameWithThumbnail(
                                            filenameFullsize,
                                            "jpg");
                                    if (webdavHelper.checkFileOnWebDAV(
                                                    filenameFullsize,
                                                    ConnectionContext.createDummy())) {
                                        IOUtils.copyLarge(webdavHelper.getFileFromWebDAV(
                                                filenameFullsize,
                                                ConnectionContext.createDummy()),
                                            out);
                                    } else if (webdavHelper.checkFileOnWebDAV(
                                                    filenameThumbnail,
                                                    ConnectionContext.createDummy())) {
                                        final InputStream isTemplate = webdavHelper.getFileFromWebDAV(
                                                filenameThumbnail,
                                                ConnectionContext.createDummy());
                                        IOUtils.copyLarge(isTemplate, out);
                                    } else {
                                        throw new Exception("file not found on webdav");
                                    }
                                } catch (final Exception ex) {
                                    LOG.error(ex, ex);
                                    throw ex;
                                }
                            }
                        };

                    DownloadManager.instance()
                            .add(new BackgroundTaskDownload(
                                    swingWorkerBackgroundTask,
                                    description,
                                    jobname,
                                    description,
                                    (extension != null) ? String.format(".%s", extension) : null));
                }
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
        if (LOG.isDebugEnabled()) {
            LOG.debug("addURLToList set: " + getDokumente());
        }
        final String docName = urlString.substring(urlString.lastIndexOf("/") + 1);
        final String description = JOptionPane.showInputDialog(
                DocumentPanel.this,
                "Welche Beschriftung soll der Link haben?",
                docName);
        if ((description != null) && (description.length() > 0)) {
            // docListModel.addElement(DmsUrl.createDmsURLFromLink(urlString, description));
            if (LOG.isDebugEnabled()) {
                LOG.debug("addURLToList: " + getDokumente());
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
        if (LOG.isDebugEnabled()) {
            LOG.debug("deleteSelectedListItems: " + getDokumente());
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
        if (LOG.isDebugEnabled()) {
            LOG.debug("deleteSelectedListItems: " + getDokumente());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
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
    } // </editor-fold>

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void lstDocListValueChanged(final javax.swing.event.ListSelectionEvent evt) {
        lblPreview.setIcon(null);
        lblPreview.setText("");
        final Object toCast = lstDocList.getSelectedValue();
        if (toCast != null) {
            if (toCast instanceof DmsUrlCustomBean) {
                final DmsUrlCustomBean url = (DmsUrlCustomBean)toCast;
                final String file = (url.getUrl() != null) ? url.getUrl().getObject_name() : null;
                if (file != null) {
                    busyIconTimer.start();
                    final SwingWorker<ImageIcon, Void> oldWorker = previewWorker;
                    if (oldWorker != null) {
                        oldWorker.cancel(true);
                    }
                    previewWorker = new PreviewWorker(file);
                    THREAD_EXECUTOR.execute(previewWorker);
                }
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void lblPreviewMouseClicked(final java.awt.event.MouseEvent evt) {
        if (!evt.isPopupTrigger()) {
            downloadSelection();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void lstDocListMouseClicked(final java.awt.event.MouseEvent evt) {
        if ((evt.getClickCount() > 1) && !evt.isPopupTrigger()) {
            downloadSelection();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void miDeleteActionPerformed(final java.awt.event.ActionEvent evt) {
        deleteSelectedListItems();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void lstDocListMousePressed(final java.awt.event.MouseEvent evt) {
        if (evt.isPopupTrigger() && !dokumente.isEmpty() && inEditMode) {
            popMenu.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void lstDocListMouseReleased(final java.awt.event.MouseEvent evt) {
        if (evt.isPopupTrigger() && !dokumente.isEmpty() && inEditMode) {
            popMenu.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }

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

    /**
     * DOCUMENT ME!
     *
     * @param   filename  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static String extendFilenameWithThumbnail(final String filename) {
        return extendFilenameWithThumbnail(filename, extractExtension(filename));
    }

    /**
     * DOCUMENT ME!
     *
     * @param   filename   DOCUMENT ME!
     * @param   extension  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static String extendFilenameWithThumbnail(final String filename, final String extension) {
        return (filename != null)
            ? String.format(
                "%s%s%s",
                filename,
                THUMBNAIL_SUFFIX_WITHOUT_EXTENSION,
                (extension != null) ? String.format(".%s", extension) : "") : null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   filename  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static String extractExtension(final String filename) {
        return (filename != null) ? filename.substring(filename.lastIndexOf(".") + 1).toLowerCase() : null;
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
            if (LOG.isDebugEnabled()) {
                LOG.debug("getSourceAction");
            }
//---> Drag disabled
//            if (c == lstDocList) {
//                return DnDConstants.ACTION_COPY;
//            }
            return DnDConstants.ACTION_NONE;
        }

        @Override
        protected Transferable createTransferable(final JComponent c) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("createTransferable");
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
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Application is not in edit mode, no drag & drop possible");
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
                                LOG.error("malformed url", ex);
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

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new PreviewWorker object.
         *
         * @param  document  DOCUMENT ME!
         */
        public PreviewWorker(final String document) {
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

            if ((document != null) && !isCancelled()) {
                lblPreview.setText("loading...");
                // setText-methods are threadsafe!
                return loadPicture(
                        document,
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

                if (icon != null) {
                    lblPreview.setSize(icon.getIconHeight() + 1, icon.getIconWidth() + 1);
                    lblPreview.setIcon(icon);
                    lblPreview.setText("");
                } else if (document != null) {
                    lblPreview.setIcon(NO_PREVIEW);
                    lblPreview.setSize(NO_PREVIEW.getIconWidth(), NO_PREVIEW.getIconHeight());
                    lblPreview.setText("<html>Could not create preview.<br>Click to open File!</html>");
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
                final WebDavTunnelHelper webdavHelper = new WebDavTunnelHelper(
                        "BELIS2",
                        BelisWebDavTunnelAction.TASK_NAME);
                final Collection<DmsUrlCustomBean> newBeans = new ArrayList<>();
                for (final DocumentStruct doc : docs) {
                    final File fileFullsize = doc.getFile();
                    final String filenameFullsize = WebDavTunnelHelper.generateWebDAVFileName(
                            FILE_PREFIX,
                            fileFullsize);
                    webdavHelper.uploadFileToWebDAV(
                        filenameFullsize,
                        fileFullsize,
                        DocumentPanel.this,
                        ConnectionContext.createDummy());

                    newBeans.add(DmsUrlCustomBean.createDmsURLFromLink(filenameFullsize, doc.getName()));

                    try {
                        final BufferedImage img = ImageIO.read(new BufferedInputStream(
                                    new FileInputStream(fileFullsize)));
                        final double ratio = img.getWidth() / (double)img.getHeight();

                        final int newHeight = (int)(300 / ratio);
                        final Image scaledImg = img.getScaledInstance(300, newHeight, Image.SCALE_SMOOTH);
                        final BufferedImage thumbnail = new BufferedImage(300, newHeight, BufferedImage.TYPE_INT_RGB);
                        thumbnail.createGraphics().drawImage(scaledImg, 0, 0, null);

                        final String[] fileNameSplit = filenameFullsize.split("\\.");
                        final String endung = fileNameSplit[fileNameSplit.length - 1];

                        final File fileThumbnail = File.createTempFile(filenameFullsize, endung);
                        ImageIO.write(thumbnail, endung, new FileOutputStream(fileThumbnail));

                        final String filenameThumbnail = extendFilenameWithThumbnail(filenameFullsize);
                        webdavHelper.uploadFileToWebDAV(
                            filenameThumbnail,
                            fileThumbnail,
                            DocumentPanel.this,
                            ConnectionContext.createDummy());
                    } catch (final Exception ex) {
                        LOG.error(ex, ex);
                    }
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
                }
            } catch (InterruptedException ex) {
                LOG.warn(ex, ex);
            } catch (Exception ex) {
                LOG.error(ex, ex);
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

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    class DocumentListCellRenderer extends DefaultListCellRenderer {

        //~ Instance fields ----------------------------------------------------

        private final Color colorOdd;
        private final Color colorEven;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new DocumentListCellRenderer object.
         */
        public DocumentListCellRenderer() {
            colorOdd = new Color(235, 235, 235);
            colorEven = new Color(215, 215, 215);
        }

        /**
         * Creates a new DocumentListCellRenderer object.
         *
         * @param  colorOdd   DOCUMENT ME!
         * @param  colorEven  DOCUMENT ME!
         */
        public DocumentListCellRenderer(final Color colorOdd, final Color colorEven) {
            this.colorOdd = colorOdd;
            this.colorEven = colorEven;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public Component getListCellRendererComponent(final JList list,
                Object value,
                final int index,
                final boolean isSelected,
                final boolean cellHasFocus) {
            ImageIcon imageIcon = null;
            if (value instanceof DmsUrlCustomBean) {
                final DmsUrlCustomBean url = (DmsUrlCustomBean)value;
                final String filename = (url.getUrl() != null) ? url.getUrl().getObject_name() : null;
                value = url.getBeschreibung();
                if (filename != null) {
                    final String extension = extractExtension(filename);
                    if (extension != null) {
                        imageIcon = FILE_TYPE_ICONS.get(extension);
                    }
                }
            }
            final Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (!isSelected) {
                c.setBackground(((index % 2) == 1) ? colorOdd : colorEven);
            }
            setIcon((imageIcon != null) ? imageIcon : UNKNOWN_FORMAT);
            return c;
        }
    }
}
