/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.belis.client;

import Sirius.navigator.DefaultNavigatorExceptionHandler;
import Sirius.navigator.connection.Connection;
import Sirius.navigator.connection.ConnectionFactory;
import Sirius.navigator.connection.ConnectionInfo;
import Sirius.navigator.connection.ConnectionSession;
import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.connection.proxy.ConnectionProxy;
import Sirius.navigator.exception.ConnectionException;
import Sirius.navigator.plugin.interfaces.FloatingPluginUI;

import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;

import net.infonode.docking.View;
import net.infonode.docking.util.StringViewMap;

import org.jdesktop.swingx.JXLoginPane;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.auth.DefaultUserNameStore;
import org.jdesktop.swingx.auth.LoginService;

import org.jdom.Element;

import java.awt.BorderLayout;
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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.URI;
import java.net.URL;

import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.PropertyResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import de.cismet.belis.broker.BelisBroker;
import de.cismet.belis.broker.CidsBroker;

import de.cismet.belis.util.BelisIcons;
import de.cismet.belis.util.JnlpSystemPropertyHelper;

import de.cismet.cids.client.tools.CallServerTunnel;

import de.cismet.cids.custom.navigatorstartuphooks.MotdStartUpHook;
import de.cismet.cids.custom.wunda_blau.startuphooks.MotdWundaStartupHook;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.navigatorstartuphooks.CidsServerMessageStartUpHook;

import de.cismet.cids.servermessage.CidsServerMessageNotifier;
import de.cismet.cids.servermessage.CidsServerMessageNotifierListener;
import de.cismet.cids.servermessage.CidsServerMessageNotifierListenerEvent;

import de.cismet.cismap.commons.BoundingBox;
import de.cismet.cismap.commons.gui.ClipboardWaitDialog;
import de.cismet.cismap.commons.gui.statusbar.StatusBar;
import de.cismet.cismap.commons.interaction.CismapBroker;

import de.cismet.commons2.architecture.layout.LayoutManager;
import de.cismet.commons2.architecture.layout.LayoutManagerListener;

import de.cismet.lookupoptions.gui.OptionsClient;
import de.cismet.lookupoptions.gui.OptionsDialog;

import de.cismet.netutil.Proxy;
import de.cismet.netutil.ProxyHandler;
import de.cismet.netutil.ProxyProperties;

import de.cismet.security.WebAccessManager;

import de.cismet.tools.StaticDebuggingTools;
import de.cismet.tools.StaticDecimalTools;

import de.cismet.tools.configuration.Configurable;
import de.cismet.tools.configuration.ConfigurationManager;
import de.cismet.tools.configuration.NoWriteError;

import de.cismet.tools.gui.StaticSwingTools;
import de.cismet.tools.gui.log4jquickconfig.Log4JQuickConfig;
import de.cismet.tools.gui.startup.StaticStartupTools;

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
    private static final String FIRST_START_IMAGE = "/de/cismet/belis/resource/icon/image/belis.jpg";
    private static Image applicationIcon = null;
    // ToDo maybe changeable would be cool for different authentication methods!!!
    private static BelisClient.WundaAuthentification wa = new BelisClient.WundaAuthentification();

    private static String DIRECTORYPATH_BELIS;
    private static String FILEPATH_SCREEN;

    private static JFrame SPLASH;

    //~ Instance fields --------------------------------------------------------

    private ClipboardWaitDialog clipboarder;
    private Dimension windowSize = null;
    private Point windowLocation = null;
    private boolean readyToShow = false;
    private ArrayList<JMenuItem> menues = new ArrayList<JMenuItem>();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JMenu menEdit;
    private javax.swing.JMenu menExtras;
    private javax.swing.JMenu menFile;
    private javax.swing.JMenu menHelp;
    private javax.swing.JMenu menHistory;
    private javax.swing.JMenu menSearch;
    private javax.swing.JMenu menWindow;
    private javax.swing.JMenuItem mniAbout;
    private javax.swing.JMenuItem mniBack;
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
    private javax.swing.JPanel toolbarPanel;
    private javax.swing.JPanel toolbarWrapperPanel;
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
                    close();
                }
            });
        try {
            clipboarder = new ClipboardWaitDialog(this, true);

            broker = BelisBroker.getInstance();
            final LayoutManager lm = new LayoutManager(DIRECTORYPATH_BELIS, broker);
            lm.addLayoutManagerListener(new LayoutManagerListener() {

                    @Override
                    public void infoNodeDockingConfigured() {
                        setWindowMenus();
                    }
                });

            broker.setLayoutManager(lm);
            broker.setFilterNormal(true);
            broker.setFilterVeranlassung(false);
            broker.setFilterArbeitsauftrag(false);
            broker.initComponentRegistry(this);
            broker.initMappingComponent();
            broker.lookupWidgets();
            broker.lookupProtokollWizards();
            initComponents();

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
            toolbarPanel.add(broker.getToolbar());
            setWindowSize();

            if (SPLASH != null) {
                SPLASH.dispose();
            }
            SPLASH = null;

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
            initTotd();
            initStartupHooks();
        } catch (final Exception ex) {
            LOG.fatal("Fatal Error in Abstract Plugin Constructor.", ex);
        }

        this.setIconImage(BelisIcons.applicationIcon.getImage());

        StaticSwingTools.tweakUI();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     */
    private void initTotd() {
        try {
            if (SessionManager.getConnection().hasConfigAttr(
                            SessionManager.getSession().getUser(),
                            "csm://"
                            + MotdWundaStartupHook.MOTD_MESSAGE_TOTD)) {
                CidsServerMessageNotifier.getInstance()
                        .subscribe(new CidsServerMessageNotifierListener() {

                                @Override
                                public void messageRetrieved(final CidsServerMessageNotifierListenerEvent event) {
                                    try {
                                        final String totd = (String)event.getMessage().getContent();
                                        broker.setTotd(totd);
                                        refreshTitle();
                                    } catch (final Exception ex) {
                                        LOG.warn(ex, ex);
                                    }
                                }
                            },
                            MotdWundaStartupHook.MOTD_MESSAGE_TOTD);
            }
        } catch (final ConnectionException ex) {
            LOG.warn("Konnte Rechte an csm://" + MotdWundaStartupHook.MOTD_MESSAGE_TOTD
                        + " nicht abfragen. Keine Titleleiste des Tages !",
                ex);
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void initStartupHooks() {
        try {
            new MotdStartUpHook().applicationStarted();
            new CidsServerMessageStartUpHook().applicationStarted();
        } catch (Exception ex) {
            LOG.error("Fehler beim Ausführen der StartupHooks: ", ex);
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void setWindowMenus() {
        final LayoutManager lman = BelisBroker.getInstance().getLayoutManager();
        final StringViewMap viewMap = lman.getViewMap();

        for (int i = 0; i < viewMap.getViewCount(); ++i) {
            final View view = viewMap.getViewAtIndex(i);

            final JMenuItem tmpMen = new JMenuItem(new DefaultWindowAction(view));
            menWindow.insert(tmpMen, i);
        }
    }

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
        if (LOG.isDebugEnabled()) {
            LOG.debug("Master Configure: " + getClass().getName());
        }
        try {
            try {
                try {
                    final String applicationName = JnlpSystemPropertyHelper.getProperty("ApplicationName");
                    this.setTitle(applicationName);
                    broker.setApplicatioName(applicationName);
                    refreshTitle();
                } catch (Exception ex) {
                    broker.setApplicatioName("Kein Name");
                    LOG.warn("Error while setting application title", ex);
                }
                broker.setParentComponent(panMain);
                broker.setConfigManager(configManager);
                configManager.addConfigurable(broker);
                configManager.addConfigurable(OptionsClient.getInstance());
                configManager.configure(broker);
            } catch (Exception ex) {
                LOG.warn("Error while retrieving broker instance", ex);
            }

            try {
                final StatusBar statusBar = new StatusBar(broker.getMappingComponent());
                DefaultNavigatorExceptionHandler.getInstance().addListener(statusBar.getExceptionHandlerListener());
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
            LOG.error("Fehler beim konfigurieren der Belis Applikation: ", ex);
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void refreshTitle() {
        if (SwingUtilities.isEventDispatchThread()) {
            final String totd = broker.getTotd();
            if ((totd == null) || totd.trim().isEmpty()) {
                setTitle(broker.getApplicationName());
            } else {
                setTitle(broker.getApplicationName() + " - " + totd);
            }
        } else {
            SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        refreshTitle();
                    }
                });
        }
    }

    /**
     * DOCUMENT ME!
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        panMain = new javax.swing.JPanel();
        toolbarWrapperPanel = new javax.swing.JPanel();
        toolbarPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
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
        menSearch = broker.getMetaSearchComponentFactory().getMenSearch();
        menHistory = new javax.swing.JMenu();
        mniBack = new javax.swing.JMenuItem();
        mniForward = new javax.swing.JMenuItem();
        mniHome = new javax.swing.JMenuItem();
        sepBeforePos = new javax.swing.JSeparator();
        sepAfterPos = new javax.swing.JSeparator();
        mniHistorySidebar = new javax.swing.JMenuItem();
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

        panMain.setBorder(null);
        panMain.setLayout(new java.awt.BorderLayout());

        toolbarWrapperPanel.setBorder(null);
        toolbarWrapperPanel.setLayout(new java.awt.BorderLayout());

        toolbarPanel.setBorder(null);
        toolbarPanel.setOpaque(false);
        toolbarPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));
        toolbarWrapperPanel.add(toolbarPanel, java.awt.BorderLayout.WEST);

        jPanel1.setBorder(null);
        jPanel1.setOpaque(false);
        toolbarWrapperPanel.add(jPanel1, java.awt.BorderLayout.CENTER);

        panMain.add(toolbarWrapperPanel, java.awt.BorderLayout.NORTH);

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

        menSearch.setText("Suche");
        mnuBar.add(menSearch);

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

        menExtras.setMnemonic('E');
        menExtras.setText("Extras");

        mniOptions.setText("Optionen");
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
        mniAbout.setText("Über BelIS");
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
        close();
    }                                                                            //GEN-LAST:event_mniCloseActionPerformed

    /**
     * DOCUMENT ME!
     */
    private void close() {
        cleanUp();
        dispose();
        System.exit(0);
    }

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
        final OptionsDialog od = new OptionsDialog(this, true);
        od.pack();
        StaticSwingTools.showDialog(od, false);
    }                                                                              //GEN-LAST:event_mniOptionsActionPerformed

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
                "Maßstab manuell auswählen",
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
        try {
            StaticStartupTools.saveScreenshotOfFrame(this, FILEPATH_SCREEN);
        } catch (Exception ex) {
            LOG.fatal("Fehler beim Capturen des App-Inhaltes", ex);
        }

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
                        broker.saveWorkbench();
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
                    LOG.debug("Es ist ein Fehler während dem abspeichern der Objekte aufgetreten", ex);
                }
                JOptionPane.showMessageDialog(
                    this,
                    "Es traten Fehler beim abspeichern der Objekte auf",
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE);
            }
            while (broker.isInEditMode()) {
                try {
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
        if (StaticDebuggingTools.checkHomeForFile("cismetBeansbindingDebuggingOn")) { // NOI18N
            System.setProperty("cismet.beansdebugging", "true");                      // NOI18N
        }
        Thread.setDefaultUncaughtExceptionHandler(DefaultNavigatorExceptionHandler.getInstance());

        try {
            final Plastic3DLookAndFeel lf = new Plastic3DLookAndFeel();
            javax.swing.UIManager.setLookAndFeel(lf);
        } catch (final Exception ex) {
            LOG.error("Fehler beim setzen des Look & Feels", ex);
        }

        final String fileSeparator = JnlpSystemPropertyHelper.getProperty("file.separator");
        final String directoryPath = JnlpSystemPropertyHelper.getProperty("user.home");
        final String directoryExtension = JnlpSystemPropertyHelper.getProperty("directory.extension");

        final String belisHomeName = ".belis"
                    + ((directoryExtension != null) ? directoryExtension : "");

        DIRECTORYPATH_BELIS = directoryPath + fileSeparator + belisHomeName;
        FILEPATH_SCREEN = DIRECTORYPATH_BELIS + fileSeparator + "belis.screen";

        configManager = new ConfigurationManager();
        configManager.setDefaultFileName(getPluginConfigurationFile());
        configManager.setFileName(getPluginConfigurationFile());
        configManager.setHome(directoryPath);
        configManager.setFolder(belisHomeName);
        configManager.setClassPathFolder(PLUGIN_CONFIGURATION_CLASSPATH);
        configManager.addConfigurable(wa);
        configManager.configure(wa);

        try {
            final File ghostFrameFile = new File(FILEPATH_SCREEN + ".png");

            if (!ghostFrameFile.exists()) {
                SPLASH = StaticStartupTools.showCustomGhostFrame(BelisClient.class.getResource(FIRST_START_IMAGE),
                        "belis [Startup]");
            } else {
                SPLASH = StaticStartupTools.showGhostFrame(FILEPATH_SCREEN, "belis [Startup]");
            }
            SPLASH.setLocationRelativeTo(null);
        } catch (Exception e) {
            LOG.warn("Problem beim Darstellen des Pre-Loading-Frame", e);
        }

        try {
            handleLogin();
        } catch (Exception ex) {
            LOG.error("Fehler beim Loginframe", ex);
            System.exit(2);
        }
//                }
//            };
//        t.setPriority(Thread.NORM_PRIORITY);
//        t.start();
    }

    /**
     * DOCUMENT ME!
     */
    public static void handleLogin() {
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

        final JXLoginPane.JXLoginDialog d = new JXLoginPane.JXLoginDialog(SPLASH, login);
        try {
            final String loginTitle = JnlpSystemPropertyHelper.getProperty("login.title");
            if (loginTitle != null) {
                d.setTitle(loginTitle);
            }
        } catch (final Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("no login title set", ex);
            }
        }

        d.setIconImage(applicationIcon);
        login.setPassword("".toCharArray());
        try {
            ((JXPanel)((JXPanel)login.getComponent(1)).getComponent(1)).getComponent(3).requestFocus();
        } catch (Exception skip) {
        }
        d.setIconImage(applicationIcon);
        d.setAlwaysOnTop(true);

        StaticSwingTools.showDialog(d, false);

        handleLoginStatus(d.getStatus(), usernames, login);
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
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class DefaultWindowAction extends AbstractAction {

        //~ Instance fields ----------------------------------------------------

        private View view;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new DefaultWindowAction object.
         *
         * @param  view  DOCUMENT ME!
         */
        public DefaultWindowAction(final View view) {
            super(view.getTitle(), view.getIcon());
            this.view = view;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public void actionPerformed(final ActionEvent e) {
            if (view.isClosable()) {
                view.close();
            } else {
                view.restore();
            }
        }
    }

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
        public static final String CONNECTION_CLASS = "Sirius.navigator.connection.RESTfulConnection";
        public static final String CONNECTION_PROXY_CLASS = "de.cismet.belis.broker.ConnectionProxyHandler";
        private static String standaloneDomain;

        //~ Instance fields ----------------------------------------------------

        private final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(WundaAuthentification.class);
        private String callserverhost;
        private String userString;
        private boolean compressionEnabled = false;
        private final ProxyProperties proxyProperties = new ProxyProperties();
        private BelisBroker broker;

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
                final String callServerURL = callserverhost;
                if (log.isDebugEnabled()) {
                    log.debug("callServerUrl:" + callServerURL);
                }
                final String domain = standaloneDomain;
                userString = name;
                if (log.isDebugEnabled()) {
                    log.debug("full qualified username: " + userString + "@" + standaloneDomain);
                }

                final Proxy proxy = ProxyHandler.getInstance().init(proxyProperties);

                final Connection connection = ConnectionFactory.getFactory()
                            .createConnection(CONNECTION_CLASS, callServerURL, proxy, compressionEnabled);
                final ConnectionSession connectionSession;
                final ConnectionProxy connectionProxy;
                final ConnectionInfo connectionInfo = new ConnectionInfo();
                connectionInfo.setCallserverURL(callServerURL);
                connectionInfo.setPassword(new String(password));
                connectionInfo.setUserDomain(domain);
                connectionInfo.setUsergroup(group);
                connectionInfo.setUsergroupDomain(domain);
                connectionInfo.setUsername(user);

                connectionSession = ConnectionFactory.getFactory().createSession(connection, connectionInfo, true);
                connectionProxy = ConnectionFactory.getFactory().createProxy(CONNECTION_PROXY_CLASS, connectionSession);

                SessionManager.init(connectionProxy);
                ClassCacheMultiple.setInstance(CidsBroker.BELIS_DOMAIN);

                CidsBroker.getInstance().setProxy(connectionProxy);
                final String tester = (group + "@" + domain).toLowerCase();
                if (log.isDebugEnabled()) {
                    log.debug("authentication: tester = " + tester);
                    log.debug("authentication: name = " + name);
                    log.debug("authentication: RM Plugin key = " + name + "@" + domain);
                    // setUserString(name); TODO update Configuration depending on username --> formaly after the
                    // handlelogin method --> test if its work!!!!
                }

                return true;
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
        /**
         * DOCUMENT ME!
         *
         * @param   from  DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         *
         * @throws  Exception  DOCUMENT ME!
         */
        private static InputStream getInputStreamFrom(final String from) throws Exception {
            if ((from.indexOf("http://") == 0) || (from.indexOf("https://") == 0)
                        || (from.indexOf("file:/") == 0)) {
                return new URL(from).openStream();
            } else {
                return new BufferedInputStream(new FileInputStream(from));
            }
        }
        @Override
        public void masterConfigure(final Element parent) {
            Boolean intranetUse = null;
            final String cfgFile = JnlpSystemPropertyHelper.getProperty("configFile");
            if (cfgFile != null) {
                String proxyConfig = null;
                try {
                    final AppProperties appProperties = new AppProperties(getInputStreamFrom(cfgFile));
                    if (appProperties.getCallserverUrl() != null) {
                        callserverhost = appProperties.getCallserverUrl();
                    } else {
                        log.fatal("Error while reading callserverhost can't authenticate");
                        System.exit(2);
                    }
                    try {
                        compressionEnabled = appProperties.isCompressionEnabled();
                    } catch (final Exception ex) {
                        log.warn("Error while parsing compressionEnabled", ex);
                    }
                    if (appProperties.getDomain() != null) {
                        standaloneDomain = appProperties.getDomain();
                    } else {
                        log.fatal("Error while reading userdomain can't authenticate");
                        System.exit(2);
                    }
                    try {
                        intranetUse = appProperties.isIntranetUseEnabled();
                    } catch (final Exception ex) {
                    }

                    proxyConfig = appProperties.getProxyConfig();
                } catch (final Exception ex) {
                    log.fatal("Error while reading config file", ex);
                    System.exit(2);
                }
                try {
                    final String cfgFileName = Paths.get(new URI(cfgFile).getPath()).getFileName().toString();
                    final String cfgDirname = cfgFile.substring(0, cfgFile.lastIndexOf(cfgFileName));
                    final String cfgProxy = ((proxyConfig != null) && !proxyConfig.isEmpty())
                        ? (cfgDirname + proxyConfig) : null;
                    if (proxyConfig != null) {
                        proxyProperties.load(getInputStreamFrom(cfgProxy));
                    }
                } catch (final Exception ex) {
                    LOG.error("error while loading proxy.config", ex);
                }
            } else { // no support for proxy
                try {
                    intranetUse = Boolean.parseBoolean(JnlpSystemPropertyHelper.getProperty("intranetUse", "false"));
                } catch (final Exception ex) {
                }
                try {
                    standaloneDomain = JnlpSystemPropertyHelper.getProperty("domain");
                } catch (final Exception ex) {
                    log.fatal("Error while reading userdomain can't authenticate", ex);
                    System.exit(2);
                    // TODO wenigstens den Nutzer benachrichtigen sonst ist es zu hard oder nur lesen modus -->
                    // besser!!!
                }
                try {
                    callserverhost = JnlpSystemPropertyHelper.getProperty("callserverhost");
                } catch (final Exception ex) {
                    log.fatal("Error while reading callserverhost can't authenticate", ex);
                    System.exit(2);
                    // TODO wenigstens den Nutzer benachrichtigen sonst ist es zu hard oder nur lesen modus -->
                    // besser!!!
                }
                try {
                    compressionEnabled = Boolean.parseBoolean(JnlpSystemPropertyHelper.getProperty(
                                "compressionEnabled"));
                } catch (final Exception ex) {
                    log.warn("Error while parsing compressionEnabled", ex);
                }
            }
            try {
                broker = BelisBroker.getInstance();
            } catch (Exception ex) {
                log.fatal("Error while configuring Login", ex);
                System.exit(2);
            }
            if (!Boolean.TRUE.equals(intranetUse)) {
                try {
                    WebAccessManager.getInstance().setTunnel(new CallServerTunnel("BELIS2"));
                } catch (Throwable e) {
                    LOG.error("problem initializing WebaccessManager", e);
                }
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static class AppProperties extends PropertyResourceBundle {

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new AppProperties object.
         *
         * @param   is  url DOCUMENT ME!
         *
         * @throws  Exception  DOCUMENT ME!
         */
        public AppProperties(final InputStream is) throws Exception {
            super(is);
        }

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public String getCallserverUrl() {
            return getString("callserverUrl");
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public boolean isCompressionEnabled() {
            return Boolean.parseBoolean(getString("compressionEnabled"));
        }
        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public String getProxyConfig() {
            return getString("proxy.config");
        }
        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public boolean isIntranetUseEnabled() {
            return Boolean.parseBoolean(getString("intranetUse"));
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public String getDomain() {
            return getString("domain");
        }
    }
}
