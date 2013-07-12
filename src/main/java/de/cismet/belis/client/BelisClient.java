/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.belis.client;

import Sirius.navigator.connection.Connection;
import Sirius.navigator.connection.ConnectionFactory;
import Sirius.navigator.connection.ConnectionInfo;
import Sirius.navigator.connection.ConnectionSession;
import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.connection.proxy.ConnectionProxy;
import Sirius.navigator.event.CatalogueActivationListener;
import Sirius.navigator.event.CatalogueSelectionListener;
import Sirius.navigator.plugin.interfaces.FloatingPluginUI;
import Sirius.navigator.resource.PropertyManager;
import Sirius.navigator.search.dynamic.SearchDialog;
import Sirius.navigator.types.treenode.RootTreeNode;
import Sirius.navigator.ui.ComponentRegistry;
import Sirius.navigator.ui.DescriptionPane;
import Sirius.navigator.ui.DescriptionPaneFS;
import Sirius.navigator.ui.LayoutedContainer;
import Sirius.navigator.ui.MutableMenuBar;
import Sirius.navigator.ui.MutablePopupMenu;
import Sirius.navigator.ui.MutableToolBar;
import Sirius.navigator.ui.attributes.AttributeViewer;
import Sirius.navigator.ui.attributes.editor.AttributeEditor;
import Sirius.navigator.ui.tree.MetaCatalogueTree;
import Sirius.navigator.ui.tree.SearchResultsTree;

import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;

import org.jdesktop.swingx.JXLoginPane;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.auth.DefaultUserNameStore;
import org.jdesktop.swingx.auth.LoginService;

import org.jdom.Element;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import de.cismet.belis.broker.BelisBroker;
import de.cismet.belis.broker.CidsBroker;

import de.cismet.belis.gui.widget.ExtendedNavigatorAttributeEditorGui;

import de.cismet.belis.util.BelisIcons;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cismap.commons.BoundingBox;
import de.cismet.cismap.commons.gui.ClipboardWaitDialog;
import de.cismet.cismap.commons.gui.statusbar.StatusBar;
import de.cismet.cismap.commons.interaction.CismapBroker;

import de.cismet.tools.StaticDecimalTools;

import de.cismet.tools.configuration.Configurable;
import de.cismet.tools.configuration.ConfigurationManager;
import de.cismet.tools.configuration.NoWriteError;

import de.cismet.tools.gui.DefaultPopupMenuListener;
import de.cismet.tools.gui.log4jquickconfig.Log4JQuickConfig;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public class BelisClient extends javax.swing.JFrame implements FloatingPluginUI, Configurable {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(BelisClient.class);
    private static final String PLUGIN_CONFIGURATION_CLASSPATH = "/de/cismet/commons/architecture/configuration/";

    protected static BelisBroker broker = null;

    private static ConfigurationManager configManager;

    private static Image banner = new javax.swing.ImageIcon(BelisClient.class.getResource(
                "/de/cismet/belis/resource/icon/image/login.png")).getImage();
    private static Image applicationIcon = null;
    // ToDo maybe changeable would be cool for different authentication methods!!!
    private static BelisClient.WundaAuthentification wa = new BelisClient.WundaAuthentification();
    private static boolean isLoginEnabled = true;

    //~ Instance fields --------------------------------------------------------

    private ClipboardWaitDialog clipboarder;
    private Dimension windowSize = null;
    private Point windowLocation = null;
    private boolean readyToShow = false;
    private ArrayList<JMenuItem> menues = new ArrayList<JMenuItem>();
    private String brokerName;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JMenu menBookmarks;
    private javax.swing.JMenu menEdit;
    private javax.swing.JMenu menExtras;
    private javax.swing.JMenu menFile;
    private javax.swing.JMenu menHelp;
    private javax.swing.JMenu menHistory;
    private javax.swing.JMenu menWindow;
    private javax.swing.JMenuItem mniAbout;
    private javax.swing.JMenuItem mniAddBookmark;
    private javax.swing.JMenuItem mniBack;
    private javax.swing.JMenuItem mniBookmarkManager;
    private javax.swing.JMenuItem mniBookmarkSidebar;
    private javax.swing.JMenuItem mniClippboard;
    private javax.swing.JMenuItem mniClose;
    private javax.swing.JMenuItem mniForward;
    private javax.swing.JMenuItem mniGotoPoint;
    private javax.swing.JMenuItem mniHistorySidebar;
    private javax.swing.JMenuItem mniHome;
    private javax.swing.JMenuItem mniLisences;
    private javax.swing.JMenuItem mniLoadLayout;
    private javax.swing.JMenuItem mniLockLayout;
    private javax.swing.JMenuItem mniNews;
    private javax.swing.JMenuItem mniOnlineHelp;
    private javax.swing.JMenuItem mniOptions;
    private javax.swing.JMenuItem mniPrint;
    private javax.swing.JMenuItem mniRefresh;
    private javax.swing.JMenuItem mniResetWindowLayout;
    private javax.swing.JMenuItem mniSaveLayout;
    private javax.swing.JMenuItem mniScale;
    private javax.swing.JMenuItem mniVersions;
    private javax.swing.JMenuBar mnuBar;
    private javax.swing.JPanel panMain;
    private javax.swing.JSeparator sepAfterPos;
    private javax.swing.JSeparator sepBeforePos;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbstractPlugin object.
     */
    public BelisClient() {
        this.addWindowListener(new WindowAdapter() {

                @Override
                public void windowClosing(final WindowEvent e) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("windowClosing(): beende Application");
                        LOG.debug("windowClosing(): Check if there unsaved changes.");
                    }
                    cleanUp();
                    dispose();
                    // needed because the frames default closing op must be "do nothing"!
                    System.exit(0);
                }
            });
        try {
            initComponents();

            final SearchResultsTree searchResultsTree = new SearchResultsTree();
            final MutableToolBar toolBar = new MutableToolBar();
            final MutableMenuBar menuBar = new MutableMenuBar();
            final LayoutedContainer container = new LayoutedContainer(toolBar, menuBar, true);
            final AttributeViewer attributeViewer = new AttributeViewer();
            final AttributeEditor attributeEditor = new ExtendedNavigatorAttributeEditorGui();
            final SearchDialog searchDialog = null;

            final DescriptionPane descriptionPane = new DescriptionPaneFS();
            final MutablePopupMenu popupMenu = new MutablePopupMenu();

            final Collection<Component> toRemoveComponents = new ArrayList<Component>();
            for (final Component component : popupMenu.getComponents()) {
                if ((component instanceof JSeparator)
                            || ((component instanceof JMenuItem)
                                && (((JMenuItem)component).getActionCommand() != null)
                                && (((JMenuItem)component).getActionCommand().equals("cmdSearch")
                                    || ((JMenuItem)component).getActionCommand().equals("treecommand")))) {
                    toRemoveComponents.add(component);
                }
            }
            for (final Component toRemoveComponent : toRemoveComponents) {
                popupMenu.remove(toRemoveComponent);
            }

            final DefaultPopupMenuListener cataloguePopupMenuListener = new DefaultPopupMenuListener(popupMenu);
            final RootTreeNode rootTreeNode = new RootTreeNode(SessionManager.getProxy().getRoots());
            final MetaCatalogueTree metaCatalogueTree = new MetaCatalogueTree(
                    rootTreeNode,
                    PropertyManager.getManager().isEditable(),
                    true,
                    5);
            final CatalogueSelectionListener catalogueSelectionListener = new CatalogueSelectionListener(
                    attributeViewer,
                    descriptionPane);
            final CatalogueActivationListener catalogueActivationListener = new CatalogueActivationListener(
                    metaCatalogueTree,
                    attributeViewer,
                    descriptionPane);

            metaCatalogueTree.addMouseListener(cataloguePopupMenuListener);
            metaCatalogueTree.addTreeSelectionListener(catalogueSelectionListener);
            metaCatalogueTree.addComponentListener(catalogueActivationListener);

            ComponentRegistry.registerComponents((JFrame)this,
                container,
                menuBar,
                toolBar,
                popupMenu,
                metaCatalogueTree,
                searchResultsTree,
                attributeViewer,
                attributeEditor,
                searchDialog,
                descriptionPane);

            clipboarder = new ClipboardWaitDialog(this, true);

            configurePlugin();

            menues.add(menFile);
            menues.add(menEdit);
            menues.add(menHistory);
            menues.add(menExtras);
            menues.add(menWindow);
            menues.add(menHelp);

            final KeyStroke configLoggerKeyStroke = KeyStroke.getKeyStroke('L', InputEvent.CTRL_MASK);
            final Action configAction = new AbstractAction() {

                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        java.awt.EventQueue.invokeLater(new Runnable() {

                                @Override
                                public void run() {
                                    Log4JQuickConfig.getSingletonInstance().setVisible(true);
                                }
                            });
                    }
                };
            getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(configLoggerKeyStroke, "CONFIGLOGGING");
            getRootPane().getActionMap().put("CONFIGLOGGING", configAction);
            constructionDone();
            add(broker.getToolbar(), BorderLayout.NORTH);
            setWindowSize();
            final Runnable startupComplete = new Runnable() {

                    @Override
                    public void run() {
                        try {
                            broker.getLayoutManager().loadUserLayout();
                        } catch (Exception ex) {
                            LOG.error("Error while loading layout", ex);
                        }
                        setReadyToShow(true);
                        broker.getMappingComponent().unlock();
                    }
                };
            if (EventQueue.isDispatchThread()) {
                startupComplete.run();
            } else {
                EventQueue.invokeLater(startupComplete);
            }
        } catch (final Exception ex) {
            LOG.fatal("Fatal Error in Abstract Plugin Constructor.", ex);
            System.out.println("Fatal Error in Abstract Plugin Constructor.");
            ex.printStackTrace();
        }

        this.setIconImage(BelisIcons.applicationIcon.getImage());
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean isReadyToShow() {
        return readyToShow;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  readyToShow  DOCUMENT ME!
     */
    public void setReadyToShow(final boolean readyToShow) {
        this.readyToShow = readyToShow;
    }

    /**
     * DOCUMENT ME!
     */
    private void configurePlugin() {
        configManager.addConfigurable(this);
        configManager.configure(this);
    }

    /**
     * DOCUMENT ME!
     */
    private void constructionDone() {
        broker.pluginConstructionDone();
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getBrokerName() {
        return brokerName;
    }

    @Override
    public JComponent getComponent() {
        return panMain;
    }

    @Override
    public void hidden() {
    }

    @Override
    public void moved() {
    }

    @Override
    public void resized() {
    }

    @Override
    public void shown() {
    }

    @Override
    public Collection getMenus() {
        return menues;
    }

    @Override
    public void floatingStarted() {
    }

    @Override
    public void floatingStopped() {
    }

    @Override
    public String getId() {
        return broker.getApplicationName();
    }

    @Override
    public void configure(final Element parent) {
        final Element prefs = parent.getChild("cismapPluginUIPreferences");
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug("writing windowSize into Configuration");
            }
            final Element window = prefs.getChild("window");
            final int windowHeight = window.getAttribute("height").getIntValue();
            final int windowWidth = window.getAttribute("width").getIntValue();
            final int windowX = window.getAttribute("x").getIntValue();
            final int windowY = window.getAttribute("y").getIntValue();
            final boolean windowMaximised = window.getAttribute("max").getBooleanValue();
            windowSize = new Dimension(windowWidth, windowHeight);
            windowLocation = new Point(windowX, windowY);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Fenstergröße: Breite " + windowWidth + " Höhe " + windowHeight);
            }
            if (windowMaximised) {
                this.setExtendedState(MAXIMIZED_BOTH);
            } else {
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("Fenstergröße erfolgreich eingelesen");
            }
        } catch (Throwable t) {
            LOG.error("Fehler beim Laden der Fenstergröße", t);
        }
    }

    @Override
    public Element getConfiguration() throws NoWriteError {
        final Element ret = new Element("cismapPluginUIPreferences");
        final Element window = new Element("window");
        final int windowHeight = this.getHeight();
        final int windowWidth = this.getWidth();
        final int windowX = (int)this.getLocation().getX();
        final int windowY = (int)this.getLocation().getY();
        final boolean windowMaximised = (this.getExtendedState() == MAXIMIZED_BOTH);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Fenstergröße: Breite " + windowWidth + " Höhe " + windowHeight);
        }
        window.setAttribute("height", "" + windowHeight);
        window.setAttribute("width", "" + windowWidth);
        window.setAttribute("x", "" + windowX);
        window.setAttribute("y", "" + windowY);
        window.setAttribute("max", "" + windowMaximised);
        ret.addContent(window);
        return ret;
    }

    @Override
    public void masterConfigure(final Element parent) {
        System.out.println("Master Configure: " + getClass().getName());
        try {
            try {
                broker = BelisBroker.getInstance();
                try {
                    final String applicationName = parent.getChild("Configuration").getChildText("ApplicationName");
                    this.setTitle(applicationName);
                    broker.setApplicatioName(applicationName);
                } catch (Exception ex) {
                    broker.setApplicatioName("Kein Name");
                    LOG.warn("Error while setting application title", ex);
                }
                broker.setParentComponent(panMain);
                broker.setConfigManager(configManager);
                configManager.addConfigurable(broker);
                configManager.configure(broker);
            } catch (Exception ex) {
                LOG.warn("Error while retrieving broker instance", ex);
            }

            try {
                final StatusBar statusBar = new StatusBar(broker.getMappingComponent());
                broker.setStatusBar(statusBar);
                broker.getMappingComponent().getFeatureCollection().addFeatureCollectionListener(statusBar);
                CismapBroker.getInstance().addStatusListener(statusBar);
                // panStatusbar.add(statusBar);
                if (broker.isStatusBarEnabled()) {
                    add(statusBar, BorderLayout.SOUTH);
                }
            } catch (Exception ex) {
                LOG.error("Error whil configuring the statusbar: ", ex);
            }
        } catch (Exception ex) {
            LOG.error("Fehler beim konfigurieren der Lagis Applikation: ", ex);
        }
    }

    /**
     * DOCUMENT ME!
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        panMain = new javax.swing.JPanel();
        mnuBar = new javax.swing.JMenuBar();
        menFile = new javax.swing.JMenu();
        mniSaveLayout = new javax.swing.JMenuItem();
        mniLoadLayout = new javax.swing.JMenuItem();
        mniLockLayout = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JSeparator();
        mniClippboard = new javax.swing.JMenuItem();
        mniPrint = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JSeparator();
        mniClose = new javax.swing.JMenuItem();
        menEdit = new javax.swing.JMenu();
        mniRefresh = new javax.swing.JMenuItem();
        menHistory = new javax.swing.JMenu();
        mniBack = new javax.swing.JMenuItem();
        mniForward = new javax.swing.JMenuItem();
        mniHome = new javax.swing.JMenuItem();
        sepBeforePos = new javax.swing.JSeparator();
        sepAfterPos = new javax.swing.JSeparator();
        mniHistorySidebar = new javax.swing.JMenuItem();
        menBookmarks = new javax.swing.JMenu();
        mniAddBookmark = new javax.swing.JMenuItem();
        mniBookmarkManager = new javax.swing.JMenuItem();
        mniBookmarkSidebar = new javax.swing.JMenuItem();
        menExtras = new javax.swing.JMenu();
        mniOptions = new javax.swing.JMenuItem();
        jSeparator12 = new javax.swing.JSeparator();
        mniGotoPoint = new javax.swing.JMenuItem();
        jSeparator13 = new javax.swing.JSeparator();
        mniScale = new javax.swing.JMenuItem();
        menWindow = new javax.swing.JMenu();
        jSeparator14 = new javax.swing.JSeparator();
        mniResetWindowLayout = new javax.swing.JMenuItem();
        menHelp = new javax.swing.JMenu();
        mniOnlineHelp = new javax.swing.JMenuItem();
        mniNews = new javax.swing.JMenuItem();
        mniVersions = new javax.swing.JMenuItem();
        mniLisences = new javax.swing.JMenuItem();
        mniAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(800, 600));

        panMain.setLayout(new java.awt.BorderLayout());
        getContentPane().add(panMain, java.awt.BorderLayout.CENTER);

        menFile.setMnemonic('D');
        menFile.setText("Datei");

        mniSaveLayout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_S,
                java.awt.event.InputEvent.CTRL_MASK));
        mniSaveLayout.setText("Aktuelles Layout speichern");
        mniSaveLayout.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    mniSaveLayoutActionPerformed(evt);
                }
            });
        menFile.add(mniSaveLayout);

        mniLoadLayout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_O,
                java.awt.event.InputEvent.CTRL_MASK));
        mniLoadLayout.setText("Layout laden");
        mniLoadLayout.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    mniLoadLayoutActionPerformed(evt);
                }
            });
        menFile.add(mniLoadLayout);

        mniLockLayout.setText("Layout sperren");
        mniLockLayout.setEnabled(false);
        mniLockLayout.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    mniLockLayoutActionPerformed(evt);
                }
            });
        menFile.add(mniLockLayout);

        jSeparator8.setEnabled(false);
        menFile.add(jSeparator8);

        mniClippboard.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_C,
                java.awt.event.InputEvent.CTRL_MASK));
        mniClippboard.setText("Bild der Karte in die Zwischenablage kopieren");
        mniClippboard.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    mniClippboardActionPerformed(evt);
                }
            });
        menFile.add(mniClippboard);

        mniPrint.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_P,
                java.awt.event.InputEvent.CTRL_MASK));
        mniPrint.setText("Drucken");
        mniPrint.setEnabled(false);
        menFile.add(mniPrint);

        jSeparator9.setEnabled(false);
        menFile.add(jSeparator9);

        mniClose.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_F4,
                java.awt.event.InputEvent.ALT_MASK));
        mniClose.setText("Beenden");
        mniClose.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    mniCloseActionPerformed(evt);
                }
            });
        menFile.add(mniClose);

        mnuBar.add(menFile);

        menEdit.setMnemonic('B');
        menEdit.setText("Bearbeiten");

        mniRefresh.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        mniRefresh.setText("Neu laden");
        mniRefresh.setEnabled(false);
        mniRefresh.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    mniRefreshActionPerformed(evt);
                }
            });
        menEdit.add(mniRefresh);

        mnuBar.add(menEdit);

        menHistory.setMnemonic('C');
        menHistory.setText("Chronik");

        mniBack.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_LEFT,
                java.awt.event.InputEvent.CTRL_MASK));
        mniBack.setText("Zurück");
        mniBack.setEnabled(false);
        mniBack.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    mniBackActionPerformed(evt);
                }
            });
        menHistory.add(mniBack);

        mniForward.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_RIGHT,
                java.awt.event.InputEvent.CTRL_MASK));
        mniForward.setText("Vor");
        mniForward.setEnabled(false);
        mniForward.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    mniForwardActionPerformed(evt);
                }
            });
        menHistory.add(mniForward);

        mniHome.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_HOME, 0));
        mniHome.setText("Home");
        mniHome.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    mniHomeActionPerformed(evt);
                }
            });
        menHistory.add(mniHome);

        sepBeforePos.setEnabled(false);
        menHistory.add(sepBeforePos);

        sepAfterPos.setEnabled(false);
        menHistory.add(sepAfterPos);

        mniHistorySidebar.setText("In eigenem Fenster anzeigen");
        mniHistorySidebar.setEnabled(false);
        menHistory.add(mniHistorySidebar);

        mnuBar.add(menHistory);

        menBookmarks.setMnemonic('L');
        menBookmarks.setText("Lesezeichen");

        mniAddBookmark.setText("Lesezeichen hinzufügen");
        mniAddBookmark.setEnabled(false);
        menBookmarks.add(mniAddBookmark);

        mniBookmarkManager.setText("Lesezeichen Manager");
        mniBookmarkManager.setEnabled(false);
        menBookmarks.add(mniBookmarkManager);

        mniBookmarkSidebar.setText("Lesezeichen in eigenem Fenster öffnen");
        mniBookmarkSidebar.setEnabled(false);
        menBookmarks.add(mniBookmarkSidebar);

        mnuBar.add(menBookmarks);

        menExtras.setMnemonic('E');
        menExtras.setText("Extras");

        mniOptions.setText("Optionen");
        mniOptions.setEnabled(false);
        mniOptions.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    mniOptionsActionPerformed(evt);
                }
            });
        menExtras.add(mniOptions);

        jSeparator12.setEnabled(false);
        menExtras.add(jSeparator12);

        mniGotoPoint.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_G,
                java.awt.event.InputEvent.CTRL_MASK));
        mniGotoPoint.setText("Gehe zu ...");
        mniGotoPoint.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    mniGotoPointActionPerformed(evt);
                }
            });
        menExtras.add(mniGotoPoint);

        jSeparator13.setEnabled(false);
        menExtras.add(jSeparator13);

        mniScale.setText("Maßstab verändern");
        mniScale.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    mniScaleActionPerformed(evt);
                }
            });
        menExtras.add(mniScale);

        mnuBar.add(menExtras);

        menWindow.setMnemonic('F');
        menWindow.setText("Fenster");

        jSeparator14.setEnabled(false);
        menWindow.add(jSeparator14);

        mniResetWindowLayout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_R,
                java.awt.event.InputEvent.CTRL_MASK));
        mniResetWindowLayout.setText("Fensteranordnung zurücksetzen");
        mniResetWindowLayout.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    mniResetWindowLayoutActionPerformed(evt);
                }
            });
        menWindow.add(mniResetWindowLayout);

        mnuBar.add(menWindow);

        menHelp.setMnemonic('H');
        menHelp.setText("Hilfe");

        mniOnlineHelp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        mniOnlineHelp.setText("Online Hilfe");
        mniOnlineHelp.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    mniOnlineHelpActionPerformed(evt);
                }
            });
        menHelp.add(mniOnlineHelp);

        mniNews.setText("News");
        mniNews.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    mniNewsActionPerformed(evt);
                }
            });
        menHelp.add(mniNews);

        mniVersions.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_V,
                java.awt.event.InputEvent.ALT_MASK
                        | java.awt.event.InputEvent.CTRL_MASK));
        mniVersions.setText("Versionsinformationen");
        mniVersions.setEnabled(false);
        menHelp.add(mniVersions);

        mniLisences.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_L,
                java.awt.event.InputEvent.ALT_MASK
                        | java.awt.event.InputEvent.CTRL_MASK));
        mniLisences.setText("Lizenzinformationen");
        mniLisences.setEnabled(false);
        menHelp.add(mniLisences);

        mniAbout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_A,
                java.awt.event.InputEvent.ALT_MASK
                        | java.awt.event.InputEvent.CTRL_MASK));
        mniAbout.setText("Über LaGIS");
        mniAbout.setEnabled(false);
        menHelp.add(mniAbout);

        mnuBar.add(menHelp);

        setJMenuBar(mnuBar);

        pack();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void mniSaveLayoutActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_mniSaveLayoutActionPerformed
        broker.getLayoutManager().saveLayout();
    }                                                                                 //GEN-LAST:event_mniSaveLayoutActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void mniLoadLayoutActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_mniLoadLayoutActionPerformed
        broker.getLayoutManager().loadLayout();
    }                                                                                 //GEN-LAST:event_mniLoadLayoutActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void mniLockLayoutActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_mniLockLayoutActionPerformed
    }                                                                                 //GEN-LAST:event_mniLockLayoutActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void mniClippboardActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_mniClippboardActionPerformed
        final Thread t = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        SwingUtilities.invokeLater(new Runnable() {

                                @Override
                                public void run() {
                                    clipboarder.setLocationRelativeTo(BelisClient.this);
                                    clipboarder.setVisible(true);
                                }
                            });

                        final ImageSelection imgSel = new ImageSelection(broker.getMappingComponent().getImage());
                        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(imgSel, null);
                        SwingUtilities.invokeLater(new Runnable() {

                                @Override
                                public void run() {
                                    clipboarder.dispose();
                                }
                            });
                    }
                });
        t.start();
    } //GEN-LAST:event_mniClippboardActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void mniCloseActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_mniCloseActionPerformed
        this.dispose();
    }                                                                            //GEN-LAST:event_mniCloseActionPerformed
    /**
     * ToDo.
     *
     * @param  evt  DOCUMENT ME!
     */
    private void mniRefreshActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_mniRefreshActionPerformed
    }                                                                              //GEN-LAST:event_mniRefreshActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void mniBackActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_mniBackActionPerformed
        if ((broker.getMappingComponent() != null) && broker.getMappingComponent().isBackPossible()) {
            broker.getMappingComponent().back(true);
        }
    }                                                                           //GEN-LAST:event_mniBackActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void mniForwardActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_mniForwardActionPerformed
        if ((broker.getMappingComponent() != null) && broker.getMappingComponent().isForwardPossible()) {
            broker.getMappingComponent().forward(true);
        }
    }                                                                              //GEN-LAST:event_mniForwardActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void mniHomeActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_mniHomeActionPerformed
        if (broker.getMappingComponent() != null) {
            broker.getMappingComponent().gotoInitialBoundingBox();
        }
    }                                                                           //GEN-LAST:event_mniHomeActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void mniOptionsActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_mniOptionsActionPerformed
// TODO add your handling code here:
    } //GEN-LAST:event_mniOptionsActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void mniGotoPointActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_mniGotoPointActionPerformed
        final BoundingBox c = broker.getMappingComponent().getCurrentBoundingBox();
        final double x = (c.getX1() + c.getX2()) / 2;
        final double y = (c.getY1() + c.getY2()) / 2;
        final String s = JOptionPane.showInputDialog(
                this,
                "Zentriere auf folgendem Punkt: x,y",
                StaticDecimalTools.round(x)
                        + ","
                        + StaticDecimalTools.round(y));
        try {
            final String[] sa = s.split(",");
            final Double gotoX = new Double(sa[0]);
            final Double gotoY = new Double(sa[1]);
            final BoundingBox bb = new BoundingBox(gotoX, gotoY, gotoX, gotoY);
            broker.getMappingComponent()
                    .gotoBoundingBox(bb, true, false, broker.getMappingComponent().getAnimationDuration());
        } catch (Exception skip) {
        }
    }                                                                                //GEN-LAST:event_mniGotoPointActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void mniScaleActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_mniScaleActionPerformed
        final String s = JOptionPane.showInputDialog(
                this,
                "Maßstab_manuell_auswählen",
                ((int)broker.getMappingComponent().getScaleDenominator())
                        + "");
        try {
            final Integer i = new Integer(s);
            broker.getMappingComponent()
                    .gotoBoundingBoxWithHistory(broker.getMappingComponent().getBoundingBoxFromScale(i));
        } catch (Exception skip) {
        }
    }                                                                            //GEN-LAST:event_mniScaleActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void mniResetWindowLayoutActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_mniResetWindowLayoutActionPerformed
        broker.getLayoutManager().doLayoutInfoNodeDefaultFile();
    }                                                                                        //GEN-LAST:event_mniResetWindowLayoutActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void mniOnlineHelpActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_mniOnlineHelpActionPerformed
    }                                                                                 //GEN-LAST:event_mniOnlineHelpActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void mniNewsActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_mniNewsActionPerformed
        // openUrlInExternalBrowser(newsURL);
    } //GEN-LAST:event_mniNewsActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public BelisBroker getBroker() {
        return broker;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  broker  DOCUMENT ME!
     */
    public void setBroker(final BelisBroker broker) {
        this.broker = broker;
    }

    @Override
    public void dispose() {
        setVisible(false);
        LOG.info("Dispose(): Application gets shutted down");
        broker.getLayoutManager().saveUserLayout();
        super.dispose();
        System.exit(0);
    }

    /**
     * DOCUMENT ME!
     */
    protected void cleanUp() {
        if (broker.isInEditMode()) {
            try {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Application is in Editmode --> ask user if he wants to save");
                }
                final int answer = JOptionPane.showConfirmDialog(
                        this,
                        "Wollen Sie die gemachten Änderungen speichern",
                        "Belis Änderungen",
                        JOptionPane.YES_NO_OPTION);
                if (answer == JOptionPane.YES_OPTION) {
                    final boolean isValid = broker.validateWidgets();
                    if (isValid) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("All changes are valid" + isValid);
                        }
                        broker.save();
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Änderungen wurden gespeichert");
                        }
                    } else {
                        LOG.warn("not all changes are valid --> can't save");
                    }
                }
            } catch (Exception ex) {
                if (LOG.isDebugEnabled()) {
                    // TODO saveCurrentFlurstueck wirft keine Exception, prüfen an welchen Stellen die Methode benutzt
                    // wird und sicherstellen das keine Probleme durch eine geworfene Exception auftreten
                    LOG.debug("Es ist ein Fehler wärend dem abspeichern des Flurstuecks aufgetreten", ex);
                }
                JOptionPane.showMessageDialog(
                    this,
                    "Es traten Fehler beim abspeichern des Flurstuecks auf",
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE);
            }
            while (broker.isInEditMode()) {
                try {
                    // TODO Progressbar & !!! Regeneriert sich nicht nach einem Server neustart
                    broker.releaseLock();
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Sperre konnte erfolgreich gelöst werden");
                    }
                    break;
                } catch (Exception ex) {
                    Logger.getLogger(BelisClient.class.getName()).log(Level.SEVERE, null, ex);
                    // ToDo make generic
                    final int answer = JOptionPane.showConfirmDialog(
                            this,
                            "Sperre konnte nicht entfernt werden. Möchten Sie es erneut probieren?",
                            "Belis Änderungen",
                            JOptionPane.YES_NO_OPTION);
                    if (answer == JOptionPane.NO_OPTION) {
                        break;
                    }
                }
            }
        }
        configManager.writeConfiguration();
    }

    /**
     * DOCUMENT ME!
     */
    private void setWindowSize() {
        if ((windowSize != null) && (windowLocation != null)) {
            this.setSize(windowSize);
            this.setLocation(windowLocation);
        } else {
            this.pack();
        }
    }

    @Override
    public void setVisible(final boolean visible) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("No Plugin super.setVisible: " + visible);
        }
        super.setVisible(visible);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static String getPluginConfigurationFile() {
        return "defaultBelisConfiguration.xml";
    }

    @Override
    public Collection getButtons() {
        return Arrays.asList(broker.getToolbar().getComponents());
    }

    /**
     * DOCUMENT ME!
     *
     * @param  args  DOCUMENT ME!
     */
    public static void main(final String[] args) {
        try {
            final Plastic3DLookAndFeel lf = new Plastic3DLookAndFeel();
            javax.swing.UIManager.setLookAndFeel(lf);
        } catch (Exception ex) {
            LOG.error("Fehler beim setzen des Look & Feels");
        }
        final Thread t = new Thread() {

                @Override
                public void run() {
                    configManager = new ConfigurationManager();
                    configManager.setDefaultFileName(getPluginConfigurationFile());
                    configManager.setFileName(getPluginConfigurationFile());
                    configManager.setClassPathFolder(PLUGIN_CONFIGURATION_CLASSPATH);
                    configManager.initialiseLocalConfigurationClasspath();

//                    final LoginManager loginManager = new LoginManager();
//
//                    configManager.addConfigurable(loginManager);
//                    configManager.configure(loginManager);
                    configManager.addConfigurable(wa);
                    configManager.configure(wa);
                    try {
                        handleLogin();
                    } catch (Exception ex) {
                        LOG.error("Fehler beim Loginframe", ex);
                        System.exit(2);
                    }
                }
            };
        t.setPriority(Thread.NORM_PRIORITY);
        t.start();
    }
    /**
     * DOCUMENT ME!
     */
    public static void handleLogin() {
        if (isLoginEnabled) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Intialisiere Loginframe");
            }
            final DefaultUserNameStore usernames = new DefaultUserNameStore();
            // ToDo for every
            final Preferences appPrefs = Preferences.userNodeForPackage(BelisClient.class);
            usernames.setPreferences(appPrefs.node("login"));

            final JXLoginPane login = new JXLoginPane(wa, null, usernames) {

                    @Override
                    protected Image createLoginBanner() {
                        return getBannerImage();
                    }
                };

            String u = null;
            try {
                u = usernames.getUserNames()[usernames.getUserNames().length - 1];
            } catch (Exception skip) {
            }
            if (u != null) {
                login.setUserName(u);
            }
            final JXLoginPane.JXLoginDialog d = new JXLoginPane.JXLoginDialog((JFrame)null, login);

            d.setIconImage(applicationIcon);
            login.setPassword("".toCharArray());
            try {
                ((JXPanel)((JXPanel)login.getComponent(1)).getComponent(1)).getComponent(3).requestFocus();
            } catch (Exception skip) {
            }
            d.setIconImage(applicationIcon);
            d.setAlwaysOnTop(true);

            d.setVisible(true);

            handleLoginStatus(d.getStatus(), usernames, login);
        } else {
            LOG.info("Login is disabled. Attention writing is possible.");
            // ToDo maybe should be also configurable
            broker.setCoreReadOnlyMode(false);
            broker.setFullReadOnlyMode(false);
            broker.showMainApplication();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static Image getBannerImage() {
        return banner;
    }
    // TODO VERDIS COPY
    // obsolete because for failed logins --> only for saving the username

    /**
     * DOCUMENT ME!
     *
     * @param  status     DOCUMENT ME!
     * @param  usernames  DOCUMENT ME!
     * @param  login      DOCUMENT ME!
     */
    private static void handleLoginStatus(final JXLoginPane.Status status,
            final DefaultUserNameStore usernames,
            final JXLoginPane login) {
        if (status == JXLoginPane.Status.SUCCEEDED) {
            // Damit wird sichergestellt, dass dieser als erstes vorgeschlagen wird
            usernames.removeUserName(login.getUserName());
            usernames.saveUserNames();
            usernames.addUserName((login.getUserName()));
            usernames.saveUserNames();
            if (LOG.isDebugEnabled()) {
                // Added for RM Plugin functionalty 22.07.2007 Sebastian Puhl
                LOG.debug("Login erfolgreich");
            }

            final BelisClient client = new BelisClient();
            client.setVisible(true);
        } else {
            LOG.warn("Login fehlgeschlagen");
            System.exit(0);
        }
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * best place ??
     *
     * @version  $Revision$, $Date$
     */
    class ImageSelection implements Transferable {

        //~ Instance fields ----------------------------------------------------

        private Image image;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new ImageSelection object.
         *
         * @param  image  DOCUMENT ME!
         */
        public ImageSelection(final Image image) {
            this.image = image;
        }

        //~ Methods ------------------------------------------------------------

        // Returns supported flavors
        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[] { DataFlavor.imageFlavor };
        }

        // Returns true if flavor is supported
        @Override
        public boolean isDataFlavorSupported(final DataFlavor flavor) {
            return DataFlavor.imageFlavor.equals(flavor);
        }

        // Returns image
        @Override
        public Object getTransferData(final DataFlavor flavor) throws UnsupportedFlavorException, IOException {
            if (!DataFlavor.imageFlavor.equals(flavor)) {
                throw new UnsupportedFlavorException(flavor);
            }
            return image;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public static class WundaAuthentification extends LoginService implements Configurable {

        //~ Static fields/initializers -----------------------------------------

        // TODO steht auch so in VERDIS schlecht für ÄNDERUNGEN !!!!!
        public static final String CONNECTION_CLASS = "Sirius.navigator.connection.RMIConnection";
//        public static final String CONNECTION_PROXY_CLASS =
//            "Sirius.navigator.connection.proxy.DefaultConnectionProxyHandler";
        public static final String CONNECTION_PROXY_CLASS = "de.cismet.belis.broker.ConnectionProxyHandler";
        // private String standaloneDomain;
        private static String standaloneDomain;

        //~ Instance fields ----------------------------------------------------

        private final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(WundaAuthentification.class);
        private String callserverhost;
        private String userString;
        private BelisBroker broker;
        // private String userDependingConfigurationFile;
        // private UserDependingConfigurationManager configManager;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new WundaAuthentification object.
         */
        public WundaAuthentification() {
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public boolean authenticate(final String name, final char[] password, final String server) throws Exception {
            try {
                if (log.isDebugEnabled()) {
                    log.debug("Authentication for :" + name);
                }

                System.setProperty("sun.rmi.transport.connectionTimeout", "15");
                final String user = name.split("@")[0];
                final String group = name.split("@")[1];

                broker.setAccountName(name);
                final String callServerURL = "rmi://" + callserverhost + "/callServer";
                if (log.isDebugEnabled()) {
                    log.debug("callServerUrl:" + callServerURL);
                }
                final String domain = standaloneDomain;
                userString = name;
                if (log.isDebugEnabled()) {
                    log.debug("full qualified username: " + userString + "@" + standaloneDomain);
                }

                final Connection connection = ConnectionFactory.getFactory()
                            .createConnection(CONNECTION_CLASS, callServerURL);
                final ConnectionSession session;
                final ConnectionProxy proxy;
                final ConnectionInfo connectionInfo = new ConnectionInfo();
                connectionInfo.setCallserverURL(callServerURL);
                connectionInfo.setPassword(new String(password));
                connectionInfo.setUserDomain(domain);
                connectionInfo.setUsergroup(group);
                connectionInfo.setUsergroupDomain(domain);
                connectionInfo.setUsername(user);

                session = ConnectionFactory.getFactory().createSession(connection, connectionInfo, true);
                proxy = ConnectionFactory.getFactory().createProxy(CONNECTION_PROXY_CLASS, session);
                // proxy = ConnectionFactory.getFactory().createProxy(CONNECTION_CLASS,CONNECTION_PROXY_CLASS,
                // connectionInfo,false);
                SessionManager.init(proxy);
                ClassCacheMultiple.setInstance(CidsBroker.BELIS_DOMAIN);

                CidsBroker.getInstance().setProxy(proxy);
                final String tester = (group + "@" + domain).toLowerCase();
                if (log.isDebugEnabled()) {
                    log.debug("authentication: tester = " + tester);
                    log.debug("authentication: name = " + name);
                    log.debug("authentication: RM Plugin key = " + name + "@" + domain);
                    // setUserString(name); TODO update Configuration depending on username --> formaly after the
                    // handlelogin method --> test if its work!!!!
                }

//                configManager.setCurrentUser(userString + "@" + standaloneDomain);
//                //zweimal wegen userdepending konfiguration
//                configManager.configure(this);

                final Boolean permission = broker.getPermissions().get(tester);
                PropertyManager.getManager().setEditable(permission);
                if (log.isDebugEnabled()) {
                    log.debug("Permissions Hashmap: " + broker.getPermissions());
                }
                if (log.isDebugEnabled()) {
                    log.debug("Permission: " + permission);
                }
                if ((permission != null) && permission) {
                    if (log.isDebugEnabled()) {
                        log.debug("Authentication successfull user has granted readwrite access");
                    }
                    broker.setCoreReadOnlyMode(false);
                    broker.setFullReadOnlyMode(false);
                    // loginWasSuccessful = true;
                    return true;
                } else if (permission != null) {
                    if (log.isDebugEnabled()) {
                        log.debug("Authentication successfull user has granted readonly access");
                    }
                    // loginWasSuccessful = true;
                    return true;
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("authentication else false: no permission available");
                    }
                    // loginWasSuccessful = false;
                    return false;
                }
            } catch (Throwable t) {
                log.error("Fehler beim Anmelden", t);
                return false;
            }
        }

        @Override
        public void configure(final Element parent) {
        }

        @Override
        public Element getConfiguration() throws NoWriteError {
            return null;
        }

        @Override
        public void masterConfigure(final Element parent) {
            try {
                final Element login = parent.getChild("Login").getChild("Standalone");
                try {
                    if (log.isDebugEnabled()) {
                        log.debug("Userdomain: " + login.getAttribute("userdomainname").getValue());
                    }
                    standaloneDomain = login.getAttribute("userdomainname").getValue();
                } catch (Exception ex) {
                    log.fatal("Error while reading userdomain can't authenticate", ex);
                    System.exit(2);
                    // TODO wenigstens den Nutzer benachrichtigen sonst ist es zu hard oder nur lesen modus -->
                    // besser!!!
                }
                try {
                    if (log.isDebugEnabled()) {
                        log.debug("Callserverhost: " + login.getAttribute("callserverhost").getValue());
                    }
                    callserverhost = login.getAttribute("callserverhost").getValue();
                } catch (Exception ex) {
                    log.fatal("Error while reading callserverhost can't authenticate", ex);
                    System.exit(2);
                    // TODO wenigstens den Nutzer benachrichtigen sonst ist es zu hard oder nur lesen modus -->
                    // besser!!!
                }

                broker = BelisBroker.getInstance();

                final Element userPermissions = parent.getChild("Login").getChild("Permissions");
                final HashMap<String, Boolean> permissions = new HashMap<String, Boolean>();
                final List<Element> xmlPermissions = userPermissions.getChildren();
                for (final Element currentPermission : xmlPermissions) {
                    try {
                        final String isReadWriteAllowedString = currentPermission.getChildText("ReadWrite");
                        boolean isReadWriteAllowed = false;
                        if (isReadWriteAllowedString != null) {
                            if (isReadWriteAllowedString.equals("true")) {
                                isReadWriteAllowed = true;
                            }
                        }
                        final String userGroup = currentPermission.getChildText("UserGroup");
                        final String userDomain = currentPermission.getChildText("UserDomain");
                        final String permissionString = userGroup + "@" + userDomain;
                        LOG.info("Permissions für: login=*@" + permissionString + " readWriteAllowed="
                                    + isReadWriteAllowed
                                    + "(boolean)/" + isReadWriteAllowedString + "(String)");
                        if (permissionString != null) {
                            permissions.put(permissionString.toLowerCase(), isReadWriteAllowed);
                        }
                    } catch (Exception ex) {
                        LOG.warn("Error while reading user right can't authenticate", ex);
                    }
                }
                broker.setPermissions(permissions);
            } catch (Exception ex) {
                log.fatal("Error while configuring Login", ex);
                System.exit(2);
            }
        }
    }
}
