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
package de.cismet.commons2.architecture.layout;

import net.infonode.docking.RootWindow;
import net.infonode.docking.TabWindow;
import net.infonode.docking.View;
import net.infonode.docking.mouse.DockingWindowActionMouseButtonListener;
import net.infonode.docking.properties.RootWindowProperties;
import net.infonode.docking.theme.DockingWindowsTheme;
import net.infonode.docking.theme.ShapedGradientDockingTheme;
import net.infonode.docking.util.DockingUtil;
import net.infonode.docking.util.PropertiesUtil;
import net.infonode.docking.util.StringViewMap;
import net.infonode.gui.componentpainter.AlphaGradientComponentPainter;
import net.infonode.util.Direction;

import java.awt.BorderLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;

import de.cismet.belis.broker.BelisBroker;

import de.cismet.belis.gui.widget.AbstractWidget;
import de.cismet.belis.gui.widget.BelisWidget;

import de.cismet.tools.CurrentStackTrace;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public class LayoutManager {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(LayoutManager.class);
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private static final String LAYOUT_FILENAME = "belis.layout";
    private static final String DEFAULT_LAYOUT = "/de/cismet/commons/architecture/configuration/defaultBelis.layout";

    //~ Instance fields --------------------------------------------------------

    // private AdvancedPluginBroker brokerName;
    private StringViewMap viewMap = new StringViewMap();
    private final String layoutFolder;
    private String layoutFileName;
    private InputStream defaultLayout;
    private final BelisBroker broker;
    private RootWindow rootWindow;
    private boolean isInit = true;
    private ArrayList<View> views = new ArrayList<View>();
    private List<LayoutManagerListener> layoutManagerListener = new ArrayList<LayoutManagerListener>();

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new LayoutManager object.
     *
     * @param  layoutFolder  DOCUMENT ME!
     * @param  broker        DOCUMENT ME!
     */
    public LayoutManager(final String layoutFolder, final BelisBroker broker) {
        this.broker = broker;

        this.layoutFolder = layoutFolder;

        try {
            layoutFileName = layoutFolder + FILE_SEPARATOR + LAYOUT_FILENAME;
            LOG.info("LayoutFileName: " + layoutFileName);
        } catch (Exception ex) {
            LOG.warn("Error while loading LayoutFileName", ex);
        }
        try {
            final String defaultLayoutFileFromClasspath = DEFAULT_LAYOUT;
            if (LOG.isDebugEnabled()) {
                LOG.debug("defaultLayoutFileFromClasspath: " + defaultLayoutFileFromClasspath);
            }
            defaultLayout = getClass().getResourceAsStream(defaultLayoutFileFromClasspath);
            LOG.info("defaultLayoutFile: " + defaultLayout);
            LOG.info("defaultLayoutFile: " + defaultLayout.available());
            int current = 0;
            while ((current = defaultLayout.read()) != -1) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Layout raw: " + current);
                }
            }
        } catch (Exception ex) {
            LOG.warn("Error while loading defaultLayoutFile", ex);
            defaultLayout = null;
        }
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     */
    public void configureInfoNodeDocking() {
        try {
            createViewsForWidgets();
            rootWindow = DockingUtil.createRootWindow(viewMap, true);
            if (LOG.isDebugEnabled()) {
                LOG.debug("RootWindow created");
            }
            // broker.setRootWindow(rootWindow);
            // InfoNode configuration
            rootWindow.addTabMouseButtonListener(DockingWindowActionMouseButtonListener.MIDDLE_BUTTON_CLOSE_LISTENER);
            final DockingWindowsTheme theme = new ShapedGradientDockingTheme();
            rootWindow.getRootWindowProperties().addSuperObject(
                theme.getRootWindowProperties());

            final RootWindowProperties titleBarStyleProperties = PropertiesUtil
                        .createTitleBarStyleRootWindowProperties();

            rootWindow.getRootWindowProperties().addSuperObject(
                titleBarStyleProperties);

            rootWindow.getRootWindowProperties().getDockingWindowProperties().setUndockEnabled(true);
            final AlphaGradientComponentPainter x = new AlphaGradientComponentPainter(
                    java.awt.SystemColor.inactiveCaptionText,
                    java.awt.SystemColor.activeCaptionText,
                    java.awt.SystemColor.activeCaptionText,
                    java.awt.SystemColor.inactiveCaptionText);
            rootWindow.getRootWindowProperties().getDragRectangleShapedPanelProperties().setComponentPainter(x);
            broker.setTitleBarComponentpainter(broker.DEFAULT_MODE_COLOR);
            rootWindow.getRootWindowProperties()
                    .getTabWindowProperties()
                    .getTabbedPanelProperties()
                    .setPaintTabAreaShadow(true);
            rootWindow.getRootWindowProperties().getTabWindowProperties().getTabbedPanelProperties().setShadowSize(10);
            rootWindow.getRootWindowProperties()
                    .getTabWindowProperties()
                    .getTabbedPanelProperties()
                    .setShadowStrength(0.8f);
            if (LOG.isDebugEnabled()) {
                LOG.debug("broker: " + broker);
                LOG.debug("parent: " + broker.getParentComponent());
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("rootWindow: " + rootWindow);
            }

            broker.getParentComponent().add(rootWindow, BorderLayout.CENTER);
            fireInfoNodeDockingConfigured();
        } catch (Exception ex) {
            LOG.error("Error while configuring InfoNodeDocking: ", ex);
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void fireInfoNodeDockingConfigured() {
        for (final LayoutManagerListener l : layoutManagerListener) {
            l.infoNodeDockingConfigured();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  listener  DOCUMENT ME!
     */
    public void addLayoutManagerListener(final LayoutManagerListener listener) {
        layoutManagerListener.add(listener);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  listener  DOCUMENT ME!
     */
    public void removeLayoutManagerListener(final LayoutManagerListener listener) {
        layoutManagerListener.remove(listener);
    }

    /**
     * DOCUMENT ME!
     */
    private void createViewsForWidgets() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Create views for widget");
            // final ArrayList<View> createdViews = new ArrayList<View>();
        }

        // return createdViews;
        final Collection<BelisWidget> widgets = broker.getWidgets();
        if (widgets != null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Widgets count: " + widgets.size());
            }
            for (final BelisWidget curWidget : widgets) {
                try {
                    // ToDo proper solution for cast should be in architecture that there are no widgets which are not
                    // derived from AbstractWidget
                    final View tmpView = new View(curWidget.getWidgetName(),
                            curWidget.getWidgetIcon(),
                            (AbstractWidget)curWidget);
                    tmpView.getCustomTitleBarComponents().addAll(curWidget.getCustomButtons());
                    viewMap.addView(curWidget.getWidgetName(), tmpView);
                    views.add(tmpView);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Widget: " + curWidget.getWidgetName() + " added to viewMap");
                    }
                } catch (Exception ex) {
                    LOG.error("Error while adding Widget: " + curWidget.getWidgetName(), ex);
                }
            }
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("There are no widgets available");
            }
        }
    }
    /**
     * ToDo create default LayoutFile use.
     */
    public void doLayoutInfoNodeDefaultFile() {
        if (defaultLayout != null) {
            loadLayout(defaultLayout, true);
        } else {
            doLayoutInfoNodeDefault();
        }
        // aktenzeichenFloatingWindow = rootWindow.createFloatingWindow(new Point(406, 175), new Dimension(300, 613),
        // vAktenzeichenSuche); vDMS.restoreFocus(); vKarte.restoreFocus();
    }

    /**
     * DOCUMENT ME!
     */
    public void doLayoutInfoNodeDefault() {
        rootWindow.setWindow(
            new TabWindow(views.toArray(views.toArray(new View[views.size()]))));
    }

    /**
     * DOCUMENT ME!
     */
    public void saveLayout() {
        final JFileChooser fc = new JFileChooser(layoutFolder);
        fc.setFileFilter(new FileFilter() {

                @Override
                public boolean accept(final File f) {
                    return f.getName().toLowerCase().endsWith(".layout");
                }

                @Override
                public String getDescription() {
                    return "Layout";
                }
            });
        fc.setMultiSelectionEnabled(false);
        final int state = fc.showSaveDialog(broker.getParentComponent());
        if (LOG.isDebugEnabled()) {
            LOG.debug("state:" + state);
        }
        if (state == JFileChooser.APPROVE_OPTION) {
            final File file = fc.getSelectedFile();
            if (LOG.isDebugEnabled()) {
                LOG.debug("file:" + file);
            }
            String name = file.getAbsolutePath();
            name = name.toLowerCase();
            if (name.endsWith(".layout")) {
                saveLayout(name);
            } else {
                saveLayout(name + ".layout");
            }
        }
    }

    /**
     * DOCUMENT ME!
     */
    public void saveUserLayout() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Speichere StandaloneLayout nach: " + layoutFileName);
        }
        saveLayout(layoutFileName);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  file  DOCUMENT ME!
     */
    public void saveLayout(final String file) {
        broker.setTitleBarComponentpainter(broker.DEFAULT_MODE_COLOR);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Saving Layout.. to " + file);
        }
        final File layoutFile = new File(file);
        try {
            if (!layoutFile.exists()) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Saving Layout.. File does not exit");
                }
                final File belisDir = new File(layoutFolder);
                if (!belisDir.exists()) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Belis Directory angelegt");
                    }
                    belisDir.mkdir();
                }

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Saving Layout.. File does not exit");
                }
                layoutFile.createNewFile();
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Saving Layout.. File does exit");
                }
            }
            final FileOutputStream layoutOutput = new FileOutputStream(layoutFile);
            final ObjectOutputStream out = new ObjectOutputStream(layoutOutput);
            rootWindow.write(out);
            out.flush();
            out.close();
            if (LOG.isDebugEnabled()) {
                LOG.debug("Saving Layout.. to " + file + " successfull");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(broker.getParentComponent(),
                "Während dem Speichern des Layouts ist ein Fehler aufgetreten.",
                "Fehler",
                JOptionPane.INFORMATION_MESSAGE);
            LOG.error("A failure occured during writing the layout file", ex);
        }
    }

    /**
     * DOCUMENT ME!
     */
    public void loadUserLayout() {
        loadLayout(layoutFileName);
    }

    /**
     * DOCUMENT ME!
     */
    public void loadLayout() {
        final JFileChooser fc = new JFileChooser(layoutFolder);
        fc.setFileFilter(new FileFilter() {

                @Override
                public boolean accept(final File f) {
                    return f.getName().toLowerCase().endsWith(".layout");
                }

                @Override
                public String getDescription() {
                    return "Layout";
                }
            });
        fc.setMultiSelectionEnabled(false);
        final int state = fc.showOpenDialog(broker.getParentComponent());
        if (state == JFileChooser.APPROVE_OPTION) {
            final File file = fc.getSelectedFile();
            String name = file.getAbsolutePath();
            name = name.toLowerCase();
            if (name.endsWith(".layout")) {
                loadLayout(name);
            } else {
                // TODO Schwachsinn
                JOptionPane.showMessageDialog(broker.getParentComponent(),
                    "Das gewählte Dateiformat wird nicht unterstützt.\\nBitte wählen Sie eine Datei mit der Endung .layout",
                    "Fehler",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  in           DOCUMENT ME!
     * @param  defaultFile  DOCUMENT ME!
     */
    public void loadLayout(final ObjectInputStream in, final boolean defaultFile) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("load layout: ", new CurrentStackTrace());
        }
        try {
            rootWindow.read(in);
            in.close();
            rootWindow.getWindowBar(Direction.LEFT).setEnabled(true);
            rootWindow.getWindowBar(Direction.RIGHT).setEnabled(true);
            if (isInit) {
                final int count = viewMap.getViewCount();
                for (int i = 0; i < count; i++) {
                    final View current = viewMap.getViewAtIndex(i);
                    if (current.isUndocked()) {
                        current.dock();
                    }
                }
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("Loading Layout successfull");
            }
        } catch (IOException ex) {
            LOG.warn("loading of default layout failed. Loading programmed layout", ex);
            if (defaultFile) {
                if (isInit) {
                    LOG.warn("Loading programmed layout");
                    doLayoutInfoNodeDefault();
                } else {
                    JOptionPane.showMessageDialog(broker.getParentComponent(),
                        "Während dem Laden des Layouts ist ein Fehler aufgetreten.",
                        "Fehler",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                LOG.error("Layout File IO Exception --> loading default Layout", ex);
                if (isInit) {
                    if (defaultFile) {
                        doLayoutInfoNodeDefault();
                    } else {
                        doLayoutInfoNodeDefaultFile();
                    }
                } else {
                    JOptionPane.showMessageDialog(broker.getParentComponent(),
                        "Während dem Laden des Layouts ist ein Fehler aufgetreten.",
                        "Fehler",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  inputStream  DOCUMENT ME!
     * @param  defaultFile  DOCUMENT ME!
     */
    public void loadLayout(final InputStream inputStream, final boolean defaultFile) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("load layout from classpath: " + inputStream);
        }
        try {
            final ObjectInputStream in = new ObjectInputStream(inputStream);
            loadLayout(in, defaultFile);
        } catch (IOException ex) {
            LOG.warn("load of default layout file failed", ex);
            if (isInit) {
                LOG.warn("loading programmed layout", ex);
                doLayoutInfoNodeDefault();
            } else {
                JOptionPane.showMessageDialog(broker.getParentComponent(),
                    "Während dem Laden des Layouts ist ein Fehler aufgetreten.",
                    "Fehler",
                    JOptionPane.INFORMATION_MESSAGE);
                doLayoutInfoNodeDefault();
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  file         DOCUMENT ME!
     * @param  defaultFile  DOCUMENT ME!
     */
    public void loadLayout(final File file, final boolean defaultFile) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("load layout from file: " + file);
        }
        if (file.exists()) {
            try {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Layout File exists");
                }
                final FileInputStream layoutInput = new FileInputStream(file);
                final ObjectInputStream in = new ObjectInputStream(layoutInput);
                loadLayout(in, defaultFile);
            } catch (IOException ex) {
                LOG.error("Layout File IO Exception --> loading default Layout", ex);
                if (isInit) {
                    if (defaultFile) {
                        doLayoutInfoNodeDefault();
                    } else {
                        doLayoutInfoNodeDefaultFile();
                    }
                } else {
                    JOptionPane.showMessageDialog(broker.getParentComponent(),
                        "Während dem Laden des Layouts ist ein Fehler aufgetreten.",
                        "Fehler",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } else {
            if (isInit) {
                LOG.warn("Datei exitstiert nicht --> default layout (init)");
                SwingUtilities.invokeLater(new Runnable() {

                        @Override
                        public void run() {
                            // UGLY WINNING --> Gefixed durch IDW Version 1.5
                            // setupDefaultLayout();
                            // DeveloperUtil.createWindowLayoutFrame("nach setup1",rootWindow).setVisible(true);
                            if (defaultFile) {
                                doLayoutInfoNodeDefault();
                            } else {
                                doLayoutInfoNodeDefaultFile();
                            }
                            // DeveloperUtil.createWindowLayoutFrame("nach setup2",rootWindow).setVisible(true);
                        }
                    });
            } else {
                LOG.warn("Datei exitstiert nicht)");
                JOptionPane.showMessageDialog(broker.getParentComponent(),
                    "Das angegebene Layout konnte nicht gefunden werden.",
                    "Fehler",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  file  DOCUMENT ME!
     */
    public void loadLayout(final String file) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Load Layout.. from " + file);
        }
        final File layoutFile = new File(file);
        loadLayout(layoutFile, false);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  view  DOCUMENT ME!
     */
    public void addView(final View view) {
        viewMap.addView(view);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public RootWindow getRootWindow() {
        if (rootWindow == null) {
            LOG.warn("rootWindow == null");
        }
        return rootWindow;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public StringViewMap getViewMap() {
        return viewMap;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  rootWindow  DOCUMENT ME!
     */
    public void setRootWindow(final RootWindow rootWindow) {
        this.rootWindow = rootWindow;
    }
}
