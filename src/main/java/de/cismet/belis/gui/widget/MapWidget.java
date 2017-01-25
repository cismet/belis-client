/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * KartenPanel.java
 *
 * Created on 16. März 2007, 12:04
 */
package de.cismet.belis.gui.widget;

import com.vividsolutions.jts.geom.Geometry;

import edu.umd.cs.piccolox.event.PNotification;
import edu.umd.cs.piccolox.event.PNotificationCenter;
import edu.umd.cs.piccolox.event.PSelectionEventHandler;

import org.jdom.Element;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JToolBar;

import de.cismet.belis.broker.BelisBroker;

import de.cismet.cismap.commons.features.Feature;
import de.cismet.cismap.commons.features.FeatureCollectionEvent;
import de.cismet.cismap.commons.features.FeatureCollectionListener;
import de.cismet.cismap.commons.features.PureNewFeature;
import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.gui.layerwidget.ActiveLayerModel;
import de.cismet.cismap.commons.gui.piccolo.PFeature;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.AttachFeatureListener;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.CreateGeometryListener;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.DeleteFeatureListener;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.FeatureMoveListener;

import de.cismet.commons.architecture.geometrySlot.GeometrySlotInformation;
import de.cismet.commons.architecture.interfaces.NoPermissionsWidget;

import de.cismet.tools.CurrentStackTrace;

import de.cismet.tools.configuration.ConfigurationManager;

import de.cismet.tools.gui.historybutton.JHistoryButton;

/**
 * DOCUMENT ME!
 *
 * @author   Puhl
 * @version  $Revision$, $Date$
 */
@org.openide.util.lookup.ServiceProvider(service = BelisWidget.class)
public class MapWidget extends BelisWidget implements FeatureCollectionListener, NoPermissionsWidget {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(MapWidget.class);

    //~ Enums ------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public enum MapMode {

        //~ Enum constants -----------------------------------------------------

        SELECT, PAN, ZOOM, MOVE_POLYGON, REMOVE_POLYGON, CUSTOM, MEASUREMENT
    }

    //~ Instance fields --------------------------------------------------------

    private MappingComponent mappingComponent;
    // private static final String WIDGET_NAME = "Karten Panel";
    private final ArrayList<JButton> customButtons = new ArrayList<JButton>();
    private MapMode currentMapMode = null;
    private MapMode lastMapMode = null;
    // private MappingComponent mapComponent;
    private final ActiveLayerModel mappingModel = new ActiveLayerModel(); // {
    private boolean isEditable = true;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.belis.gui.search.AddressSearchControl addressSearchControl;
    private javax.swing.JButton cmdAdd;
    private javax.swing.JButton cmdAddHandle;
    private javax.swing.JButton cmdBack;
    private javax.swing.JButton cmdForeground;
    private javax.swing.JButton cmdForward;
    private javax.swing.JButton cmdFullPoly;
    private javax.swing.JButton cmdFullPoly1;
    private javax.swing.JButton cmdMoveHandle;
    private javax.swing.JButton cmdMovePolygon;
    private javax.swing.JToggleButton cmdNewLinestring;
    private javax.swing.JButton cmdPan;
    private javax.swing.JButton cmdRemoveHandle;
    private javax.swing.JButton cmdRemovePolygon;
    private javax.swing.JButton cmdSelect;
    private javax.swing.JButton cmdSnap;
    private javax.swing.JButton cmdWmsBackground;
    private javax.swing.JButton cmdZoom;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JToolBar mapWidgetToolbar;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form KartenPanel.
     */
    public MapWidget() {
        setWidgetName("Karte");
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void setBroker(final BelisBroker broker) {
        super.setBroker(broker);
        mappingComponent = broker.getMappingComponent();
        initComponents();

        addressSearchControl.setMappingComponent(mappingComponent);
        broker.addSearchControl(addressSearchControl);
    }

    /**
     * DOCUMENT ME!
     */
    public void setToLastInteractionMode() {
        if (getLastMapMode() != null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Setting MapWidget to last interactionmode...");
            }
            switch (getLastMapMode()) {
                case SELECT: {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Select");
                    }
                    cmdSelectActionPerformed(null);
                    break;
                }
                case PAN: {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Pan");
                    }
                    cmdPanActionPerformed(null);
                    break;
                }
                case ZOOM: {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Zoom");
                    }
                    cmdZoomActionPerformed(null);
                    break;
                }
                case MOVE_POLYGON: {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Move polygon");
                    }
                    cmdMovePolygonActionPerformed(null);
                    break;
                }
                case REMOVE_POLYGON: {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Remove handle");
                    }
                    cmdRemovePolygonActionPerformed(null);
                    break;
                }
                case MEASUREMENT: {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("New LineString");
                    }
                    cmdNewLinestringActionPerformed(null);
                    break;
                }
                case CUSTOM: {
                    LOG.warn("Can't set to last interaction mode was custom");
                    break;
                }
            }
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("No last interaction mode. Setting mode to Select");
            }
            cmdSelect.doClick();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public MapMode getCurrentMapMode() {
        return currentMapMode;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  currentMapMode  DOCUMENT ME!
     */
    public void setCurrentMapMode(final MapMode currentMapMode) {
        this.currentMapMode = currentMapMode;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public MapMode getLastMapMode() {
        return lastMapMode;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  lastMapMode  DOCUMENT ME!
     */
    public void setLastMapMode(final MapMode lastMapMode) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("setLastMapModeTo: " + lastMapMode + " was: " + getLastMapMode());
        }
        this.lastMapMode = lastMapMode;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public JButton getCmdSelect() {
        return cmdSelect;
    }

    /**
     * DOCUMENT ME!
     *
     * @return    DOCUMENT ME!
     *
     * @Override  public void masterConfigure(Element e) { super.masterConfigure(e); super.configure(e); } // @Override
     *            // public void configure(Element e) { // } // @Override // public Element getConfiguration() throws
     *            NoWriteError { // Element conf = new Element("cismapActiveLayerConfiguration"); // return conf; // }
     *            };
     */
    public ActiveLayerModel getMappingModel() {
        return mappingModel;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  customButton  DOCUMENT ME!
     */
    public void addCustomButton(final JButton customButton) {
        if (customButton != null) {
            customButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        removeMainGroupSelection();
                        setLastMapMode(getCurrentMapMode());
                        setCurrentMapMode(MapMode.CUSTOM);
                    }
                });
            customButtons.add(customButton);
            mapWidgetToolbar.add(customButton);
        }
    }

    /**
     * DOCUMENT ME!
     */
    public void setInteractionMode() {
        final String currentInteractionMode = mappingComponent.getInteractionMode();
        if (LOG.isDebugEnabled()) {
            LOG.debug("CurrentInteractionMode: " + currentInteractionMode, new CurrentStackTrace());
        }
        if (currentInteractionMode != null) {
            if (currentInteractionMode.equals(MappingComponent.SELECT)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("InteractionMode set to SELCET");
                }
                cmdSelectActionPerformed(null);
            } else if (currentInteractionMode.equals(MappingComponent.PAN)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("InteractionMode set to PAN");
                }
                cmdPanActionPerformed(null);
            } else if (currentInteractionMode.equals(MappingComponent.NEW_POLYGON)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("InteractionMode set to NEW_POLYGON");
                    // Todo Not set beacuse of Belis
                }
                // cmdNewPolygonActionPerformed(null);
            } else if (currentInteractionMode.equals(MappingComponent.ZOOM)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("InteractionMode set to ZOOM");
                }
                cmdZoomActionPerformed(null);
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Unknown Interactionmode: " + currentInteractionMode);
                }
            }
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("InteractionMode == null");
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        mapWidgetToolbar = new javax.swing.JToolBar();
        cmdFullPoly = new javax.swing.JButton();
        cmdFullPoly1 = new javax.swing.JButton();
        cmdBack = new JHistoryButton();
        cmdForward = new JHistoryButton();
        addressSearchControl = new de.cismet.belis.gui.search.AddressSearchControl();
        jSeparator4 = new javax.swing.JSeparator();
        cmdWmsBackground = new javax.swing.JButton();
        cmdForeground = new javax.swing.JButton();
        cmdSnap = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JSeparator();
        cmdZoom = new javax.swing.JButton();
        cmdPan = new javax.swing.JButton();
        cmdSelect = new javax.swing.JButton();
        cmdMovePolygon = new javax.swing.JButton();
        cmdRemovePolygon = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JSeparator();
        cmdMoveHandle = new javax.swing.JButton();
        cmdAddHandle = new javax.swing.JButton();
        cmdRemoveHandle = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JSeparator();
        cmdNewLinestring = new javax.swing.JToggleButton();
        jPanel1 = new javax.swing.JPanel();
        cmdAdd = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        mapWidgetToolbar.setMaximumSize(new java.awt.Dimension(32769, 32769));
        mapWidgetToolbar.setMinimumSize(new java.awt.Dimension(300, 25));
        mapWidgetToolbar.setPreferredSize(new java.awt.Dimension(300, 28));

        cmdFullPoly.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/commons/architecture/resource/icon/toolbar/fullPoly.png"))); // NOI18N
        cmdFullPoly.setToolTipText("Zeige alle Flächen");
        cmdFullPoly.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 3, 1, 3));
        cmdFullPoly.setIconTextGap(8);
        cmdFullPoly.setMargin(new java.awt.Insets(10, 14, 10, 14));
        cmdFullPoly.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cmdFullPolyActionPerformed(evt);
                }
            });
        mapWidgetToolbar.add(cmdFullPoly);

        cmdFullPoly1.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/commons/architecture/resource/icon/toolbar/fullSelPoly.png"))); // NOI18N
        cmdFullPoly1.setToolTipText("Zoom zur ausgewählten Fläche");
        cmdFullPoly1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 3, 1, 3));
        cmdFullPoly1.setIconTextGap(8);
        cmdFullPoly1.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cmdFullPoly1ActionPerformed(evt);
                }
            });
        mapWidgetToolbar.add(cmdFullPoly1);

        cmdBack.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/commons/architecture/resource/icon/toolbar/back2.png"))); // NOI18N
        cmdBack.setToolTipText("Zurück");
        cmdBack.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 3, 1, 3));
        cmdBack.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cmdBackActionPerformed(evt);
                }
            });
        mapWidgetToolbar.add(cmdBack);

        cmdForward.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/commons/architecture/resource/icon/toolbar/fwd.png"))); // NOI18N
        cmdForward.setToolTipText("Vor");
        cmdForward.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 3, 1, 3));
        cmdForward.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cmdForwardActionPerformed(evt);
                }
            });
        mapWidgetToolbar.add(cmdForward);

        addressSearchControl.setMaximumSize(new java.awt.Dimension(290, 28));
        addressSearchControl.setMinimumSize(new java.awt.Dimension(290, 28));
        addressSearchControl.setName(""); // NOI18N
        addressSearchControl.setPreferredSize(new java.awt.Dimension(290, 28));
        mapWidgetToolbar.add(addressSearchControl);

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator4.setMaximumSize(new java.awt.Dimension(2, 32767));
        jSeparator4.setMinimumSize(new java.awt.Dimension(2, 10));
        jSeparator4.setPreferredSize(new java.awt.Dimension(2, 10));
        mapWidgetToolbar.add(jSeparator4);

        cmdWmsBackground.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/commons/architecture/resource/icon/toolbar/map.png")));    // NOI18N
        cmdWmsBackground.setToolTipText("Hintergrund an/aus");
        cmdWmsBackground.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 3, 1, 3));
        cmdWmsBackground.setSelectedIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/commons/architecture/resource/icon/toolbar/map_on.png"))); // NOI18N
        cmdWmsBackground.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cmdWmsBackgroundActionPerformed(evt);
                }
            });
        mapWidgetToolbar.add(cmdWmsBackground);

        cmdForeground.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/commons/architecture/resource/icon/toolbar/foreground.png")));    // NOI18N
        cmdForeground.setToolTipText("Vordergrund an/aus");
        cmdForeground.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        cmdForeground.setFocusable(false);
        cmdForeground.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdForeground.setSelected(true);
        cmdForeground.setSelectedIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/commons/architecture/resource/icon/toolbar/foreground_on.png"))); // NOI18N
        cmdForeground.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cmdForeground.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cmdForegroundActionPerformed(evt);
                }
            });
        mapWidgetToolbar.add(cmdForeground);

        cmdSnap.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/commons/architecture/resource/icon/toolbar/snap.png")));          // NOI18N
        cmdSnap.setToolTipText("Snapping an/aus");
        cmdSnap.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 3, 1, 3));
        cmdSnap.setSelected(true);
        cmdSnap.setSelectedIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/commons/architecture/resource/icon/toolbar/snap_selected.png"))); // NOI18N
        cmdSnap.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cmdSnapActionPerformed(evt);
                }
            });
        mapWidgetToolbar.add(cmdSnap);

        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator5.setMaximumSize(new java.awt.Dimension(2, 32767));
        jSeparator5.setMinimumSize(new java.awt.Dimension(2, 10));
        jSeparator5.setPreferredSize(new java.awt.Dimension(2, 10));
        mapWidgetToolbar.add(jSeparator5);

        cmdZoom.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/commons/architecture/resource/icon/toolbar/zoom.png")));          // NOI18N
        cmdZoom.setToolTipText("Zoomen");
        cmdZoom.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 3, 1, 3));
        cmdZoom.setSelectedIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/commons/architecture/resource/icon/toolbar/zoom_selected.png"))); // NOI18N
        cmdZoom.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cmdZoomActionPerformed(evt);
                }
            });
        mapWidgetToolbar.add(cmdZoom);

        cmdPan.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/commons/architecture/resource/icon/toolbar/move2.png")));          // NOI18N
        cmdPan.setToolTipText("Verschieben");
        cmdPan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 3, 1, 3));
        cmdPan.setSelectedIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/commons/architecture/resource/icon/toolbar/move2_selected.png"))); // NOI18N
        cmdPan.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cmdPanActionPerformed(evt);
                }
            });
        mapWidgetToolbar.add(cmdPan);

        cmdSelect.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/commons/architecture/resource/icon/toolbar/select.png")));          // NOI18N
        cmdSelect.setToolTipText("Auswählen");
        cmdSelect.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 3, 1, 3));
        cmdSelect.setSelected(true);
        cmdSelect.setSelectedIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/commons/architecture/resource/icon/toolbar/select_selected.png"))); // NOI18N
        cmdSelect.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cmdSelectActionPerformed(evt);
                }
            });
        mapWidgetToolbar.add(cmdSelect);

        cmdMovePolygon.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/commons/architecture/resource/icon/toolbar/movePoly.png")));          // NOI18N
        cmdMovePolygon.setToolTipText("Polygon verschieben");
        cmdMovePolygon.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 3, 1, 3));
        cmdMovePolygon.setSelectedIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/commons/architecture/resource/icon/toolbar/movePoly_selected.png"))); // NOI18N
        cmdMovePolygon.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cmdMovePolygonActionPerformed(evt);
                }
            });
        mapWidgetToolbar.add(cmdMovePolygon);

        cmdRemovePolygon.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/commons/architecture/resource/icon/toolbar/removePoly.png"))); // NOI18N
        cmdRemovePolygon.setToolTipText("Polygon entfernen");
        cmdRemovePolygon.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 3, 1, 3));
        cmdRemovePolygon.setSelectedIcon(new javax.swing.ImageIcon(
                getClass().getResource(
                    "/de/cismet/commons/architecture/resource/icon/toolbar/removePoly_selected.png")));           // NOI18N
        cmdRemovePolygon.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cmdRemovePolygonActionPerformed(evt);
                }
            });
        mapWidgetToolbar.add(cmdRemovePolygon);

        jSeparator6.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator6.setMaximumSize(new java.awt.Dimension(2, 32767));
        jSeparator6.setMinimumSize(new java.awt.Dimension(2, 10));
        jSeparator6.setPreferredSize(new java.awt.Dimension(2, 10));
        mapWidgetToolbar.add(jSeparator6);

        cmdMoveHandle.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/commons/architecture/resource/icon/toolbar/moveHandle.png"))); // NOI18N
        cmdMoveHandle.setToolTipText("Handle verschieben");
        cmdMoveHandle.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 3, 1, 3));
        cmdMoveHandle.setSelectedIcon(new javax.swing.ImageIcon(
                getClass().getResource(
                    "/de/cismet/commons/architecture/resource/icon/toolbar/moveHandle_selected.png")));           // NOI18N
        cmdMoveHandle.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cmdMoveHandleActionPerformed(evt);
                }
            });
        mapWidgetToolbar.add(cmdMoveHandle);

        cmdAddHandle.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/commons/architecture/resource/icon/toolbar/addHandle.png"))); // NOI18N
        cmdAddHandle.setToolTipText("Handle hinzufügen");
        cmdAddHandle.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 3, 1, 3));
        cmdAddHandle.setSelectedIcon(new javax.swing.ImageIcon(
                getClass().getResource(
                    "/de/cismet/commons/architecture/resource/icon/toolbar/addHandle_selected.png")));           // NOI18N
        cmdAddHandle.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cmdAddHandleActionPerformed(evt);
                }
            });
        mapWidgetToolbar.add(cmdAddHandle);

        cmdRemoveHandle.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/commons/architecture/resource/icon/toolbar/removeHandle.png"))); // NOI18N
        cmdRemoveHandle.setToolTipText("Handle entfernen");
        cmdRemoveHandle.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 3, 1, 3));
        cmdRemoveHandle.setSelectedIcon(new javax.swing.ImageIcon(
                getClass().getResource(
                    "/de/cismet/commons/architecture/resource/icon/toolbar/removeHandle_selected.png")));           // NOI18N
        cmdRemoveHandle.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cmdRemoveHandleActionPerformed(evt);
                }
            });
        mapWidgetToolbar.add(cmdRemoveHandle);

        jSeparator7.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator7.setMaximumSize(new java.awt.Dimension(2, 32767));
        jSeparator7.setMinimumSize(new java.awt.Dimension(2, 10));
        jSeparator7.setPreferredSize(new java.awt.Dimension(2, 10));
        mapWidgetToolbar.add(jSeparator7);

        cmdNewLinestring.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/16/newLine.png")));          // NOI18N
        cmdNewLinestring.setToolTipText("neue Linie");
        cmdNewLinestring.setBorderPainted(false);
        cmdNewLinestring.setContentAreaFilled(false);
        cmdNewLinestring.setFocusPainted(false);
        cmdNewLinestring.setFocusable(false);
        cmdNewLinestring.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdNewLinestring.setSelectedIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/16/newLine_selected.png"))); // NOI18N
        cmdNewLinestring.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cmdNewLinestring.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cmdNewLinestringActionPerformed(evt);
                }
            });
        mapWidgetToolbar.add(cmdNewLinestring);

        add(mapWidgetToolbar, java.awt.BorderLayout.NORTH);

        jPanel1.setMinimumSize(new java.awt.Dimension(50, 100));
        jPanel1.setPreferredSize(new java.awt.Dimension(50, 30));

        cmdAdd.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/commons/architecture/resource/icon/toolbar/layersman.png"))); // NOI18N
        cmdAdd.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        cmdAdd.setBorderPainted(false);
        cmdAdd.setFocusPainted(false);
        cmdAdd.setMinimumSize(new java.awt.Dimension(25, 25));
        cmdAdd.setPreferredSize(new java.awt.Dimension(25, 25));
        cmdAdd.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cmdAddActionPerformed(evt);
                }
            });

        final org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
                org.jdesktop.layout.GroupLayout.TRAILING,
                jPanel1Layout.createSequentialGroup().addContainerGap(892, Short.MAX_VALUE).add(
                    cmdAdd,
                    org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                    org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                    org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)));
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
                jPanel1Layout.createSequentialGroup().add(
                    cmdAdd,
                    org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                    org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                    org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).addContainerGap(75, Short.MAX_VALUE)));

        add(jPanel1, java.awt.BorderLayout.SOUTH);
    } // </editor-fold>//GEN-END:initComponents
    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cmdMovePolygonActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cmdMovePolygonActionPerformed
        removeHandleGroupSelection();
        removeMainGroupSelection();
        cmdMovePolygon.setSelected(true);
        mappingComponent.setInteractionMode(MappingComponent.MOVE_POLYGON);
        setLastMapMode(getCurrentMapMode());
        setCurrentMapMode(MapMode.MOVE_POLYGON);
    }                                                                                  //GEN-LAST:event_cmdMovePolygonActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cmdAddActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cmdAddActionPerformed
        mappingComponent.showInternalLayerWidget(!mappingComponent.isInternalLayerWidgetVisible(), 500);
    }                                                                          //GEN-LAST:event_cmdAddActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cmdRemoveHandleActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cmdRemoveHandleActionPerformed
        removeHandleGroupSelection();
        cmdRemoveHandle.setSelected(true);

        removeMainGroupSelection();
        cmdSelect.setSelected(true);
        mappingComponent.setInteractionMode(MappingComponent.SELECT);

        mappingComponent.setHandleInteractionMode(MappingComponent.REMOVE_HANDLE);
    } //GEN-LAST:event_cmdRemoveHandleActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cmdAddHandleActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cmdAddHandleActionPerformed
        removeHandleGroupSelection();
        cmdAddHandle.setSelected(true);

        removeMainGroupSelection();
        cmdSelect.setSelected(true);
        mappingComponent.setInteractionMode(MappingComponent.SELECT);

        mappingComponent.setHandleInteractionMode(MappingComponent.ADD_HANDLE);
    } //GEN-LAST:event_cmdAddHandleActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cmdMoveHandleActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cmdMoveHandleActionPerformed
        removeHandleGroupSelection();
        cmdMoveHandle.setSelected(true);

        removeMainGroupSelection();
        cmdSelect.setSelected(true);
        mappingComponent.setInteractionMode(MappingComponent.SELECT);

        mappingComponent.setHandleInteractionMode(MappingComponent.MOVE_HANDLE);
    } //GEN-LAST:event_cmdMoveHandleActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cmdRemovePolygonActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cmdRemovePolygonActionPerformed
        removeHandleGroupSelection();
        removeMainGroupSelection();
        cmdRemovePolygon.setSelected(true);
        mappingComponent.setInteractionMode(MappingComponent.REMOVE_POLYGON);
        setLastMapMode(getCurrentMapMode());
        setCurrentMapMode(MapMode.REMOVE_POLYGON);
    }                                                                                    //GEN-LAST:event_cmdRemovePolygonActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cmdSelectActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cmdSelectActionPerformed
        removeMainGroupSelection();
        cmdSelect.setSelected(true);
        setLastMapMode(getCurrentMapMode());
        mappingComponent.setInteractionMode(MappingComponent.SELECT);
        cmdMoveHandleActionPerformed(null);
        setCurrentMapMode(MapMode.SELECT);
    }                                                                             //GEN-LAST:event_cmdSelectActionPerformed

    /**
     * DOCUMENT ME!
     */
    private void removeHandleGroupSelection() {
        cmdRemoveHandle.setSelected(false);
        cmdAddHandle.setSelected(false);
        cmdMoveHandle.setSelected(false);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cmdPanActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cmdPanActionPerformed
        removeHandleGroupSelection();
        removeMainGroupSelection();
        cmdPan.setSelected(true);
        mappingComponent.setInteractionMode(MappingComponent.PAN);
        setLastMapMode(getCurrentMapMode());
        setCurrentMapMode(MapMode.PAN);
    }                                                                          //GEN-LAST:event_cmdPanActionPerformed

    /**
     * DOCUMENT ME!
     */
    public void setCustomMapMode() {
        removeHandleGroupSelection();
        removeMainGroupSelection();
        setLastMapMode(getCurrentMapMode());
        setCurrentMapMode(MapMode.CUSTOM);
    }

    /**
     * DOCUMENT ME!
     */
    public void removeMainGroupSelection() {
        cmdZoom.setSelected(false);
        cmdPan.setSelected(false);
        cmdSelect.setSelected(false);
        cmdMovePolygon.setSelected(false);
        cmdRemovePolygon.setSelected(false);
        cmdNewLinestring.setSelected(false);

        for (final JButton curButton : customButtons) {
            curButton.setSelected(false);
        }
    }
    /**
     * should be static and in MappingComponent.
     *
     * @param   modeToAssert  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean assertMappingComponentMode(final String modeToAssert) {
        final String currentInteractionMode = getBroker().getMappingComponent().getInteractionMode();
        if (currentInteractionMode != null) {
            if (currentInteractionMode.equals(modeToAssert)) {
                return true;
            } else {
                return false;
            }
        } else {
            if (modeToAssert == null) {
                return true;
            } else {
                return false;
            }
        }
    }
    /**
     * ToDo here new input events like belis create mode should be checked.
     *
     * @return  DOCUMENT ME!
     */
    public boolean assertMappingComponentIsInAnEditMode() {
        if (assertMappingComponentMode(MappingComponent.MOVE_POLYGON)
                    || assertMappingComponentMode(MappingComponent.ADD_HANDLE)
                    || assertMappingComponentMode(MappingComponent.ATTACH_POLYGON_TO_ALPHADATA)
                    || assertMappingComponentMode(MappingComponent.NEW_POLYGON)
                    || assertMappingComponentMode(MappingComponent.RAISE_POLYGON)
                    || assertMappingComponentMode(MappingComponent.REMOVE_POLYGON)
                    || assertMappingComponentMode(MappingComponent.ROTATE_POLYGON)
                    || assertMappingComponentMode(MappingComponent.SPLIT_POLYGON)) {
            return true;
        }
        return false;
    }

    @Override
    public synchronized void setWidgetEditable(final boolean isEditable) {
        if (this.isEditable == isEditable) {
            return;
        }
        this.isEditable = isEditable;
        if (LOG.isDebugEnabled()) {
            LOG.debug("MapPanel --> setComponentEditable");
        }
        // Refactor code duplication
        if (EventQueue.isDispatchThread()) {
            mappingComponent.setReadOnly(!isEditable);
            // TODO only change if the actualMode is not allowed
            if (!isEditable) {
                // mappingComponent.setInteractionMode(mappingComponent.PAN);
                // TODO is it really the best default mode ?
                // TODO look how to easily create events (or common)
                // ToDo LagIS same problem
                if (assertMappingComponentIsInAnEditMode()) {
                    LOG.warn(
                        "Application is not in edit mode, but mapping component is in an edit mode. Setting back to Select.");
                    removeMainGroupSelection();
                    cmdSelect.setSelected(true);
                    mappingComponent.setInteractionMode(MappingComponent.SELECT);
                    cmdMoveHandleActionPerformed(null);
                }
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("Anzahl Features in FeatureCollection:"
                            + mappingComponent.getFeatureCollection().getFeatureCount());
            }
            // ((DefaultFeatureCollection)mappingComponent.getFeatureCollection()).setAllFeaturesEditable(isEditable);
            // TODO TEST IT!!!!
            getBroker().getMappingComponent().setReadOnly(!isEditable);
            cmdMovePolygon.setVisible(isEditable);
            // this.cmdNewPolygon.setVisible(b);
            cmdRemovePolygon.setVisible(isEditable);
            // cmdAttachPolyToAlphadata.setVisible(isEditable);
            // cmdJoinPoly.setVisible(isEditable);
            jSeparator6.setVisible(isEditable);
            cmdMoveHandle.setVisible(isEditable);
            cmdAddHandle.setVisible(isEditable);
            cmdRemoveHandle.setVisible(isEditable);
            // cmdSplitPoly.setVisible(isEditable);
            // cmdRaisePolygon.setVisible(isEditable);
            jSeparator6.setVisible(isEditable);
        } else {
            EventQueue.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        mappingComponent.setReadOnly(!isEditable);
                        // TODO only change if the actualMode is not allowed
                        if (!isEditable) {
                            // mappingComponent.setInteractionMode(mappingComponent.PAN);
                            // TODO is it really the best default mode ?
                            // TODO look how to easily create events (or common)
                            if (assertMappingComponentIsInAnEditMode()) {
                                LOG.warn(
                                    "Application is not in edit mode, but mapping component is in an edit mode. Setting back to Select.");
                                removeMainGroupSelection();
                                cmdSelect.setSelected(true);
                                mappingComponent.setInteractionMode(MappingComponent.SELECT);
                                cmdMoveHandleActionPerformed(null);
                            }
                        }
                        if (LOG.isDebugEnabled()) {
                            LOG.debug(
                                "Anzahl Features in FeatureCollection:"
                                        + mappingComponent.getFeatureCollection().getFeatureCount());
                        }
                        // ((DefaultFeatureCollection)mappingComponent.getFeatureCollection()).setAllFeaturesEditable(isEditable);
                        // TODO TEST IT!!!!
                        getBroker().getMappingComponent().setReadOnly(!isEditable);
                        cmdMovePolygon.setVisible(isEditable);
                        // this.cmdNewPolygon.setVisible(b);
                        cmdRemovePolygon.setVisible(isEditable);
                        // cmdAttachPolyToAlphadata.setVisible(isEditable);
                        // cmdJoinPoly.setVisible(isEditable);
                        jSeparator6.setVisible(isEditable);
                        cmdMoveHandle.setVisible(isEditable);
                        cmdAddHandle.setVisible(isEditable);
                        cmdRemoveHandle.setVisible(isEditable);
                        // cmdSplitPoly.setVisible(isEditable);
                        // cmdRaisePolygon.setVisible(isEditable);
                        jSeparator6.setVisible(isEditable);
                    }
                });
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("MapPanel --> setComponentEditable finished");
        }
    }

    @Override
    public synchronized void clearComponent() {
    }

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cmdZoomActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cmdZoomActionPerformed
        removeHandleGroupSelection();
        removeMainGroupSelection();
        cmdZoom.setSelected(true);
        mappingComponent.setInteractionMode(MappingComponent.ZOOM);
        setLastMapMode(getCurrentMapMode());
        setCurrentMapMode(MapMode.ZOOM);
    }                                                                           //GEN-LAST:event_cmdZoomActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cmdSnapActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cmdSnapActionPerformed
        if (LOG.isDebugEnabled()) {
            LOG.debug("Set snapping Enabled: " + cmdSnap.isSelected());
        }
        cmdSnap.setSelected(!cmdSnap.isSelected());
        // TODO CHANGE CONFIG FILE ACTION
        // cismapPrefs.getGlobalPrefs().setSnappingEnabled(cmdSnap.isSelected());
        // cismapPrefs.getGlobalPrefs().setSnappingPreviewEnabled(cmdSnap.isSelected());
        mappingComponent.setSnappingEnabled(!mappingComponent.isReadOnly() && cmdSnap.isSelected());
        mappingComponent.setVisualizeSnappingEnabled(!mappingComponent.isReadOnly() && cmdSnap.isSelected());
        mappingComponent.setInGlueIdenticalPointsMode(cmdSnap.isSelected());
    } //GEN-LAST:event_cmdSnapActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cmdWmsBackgroundActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cmdWmsBackgroundActionPerformed
        if (mappingComponent.isBackgroundEnabled()) {
            mappingComponent.setBackgroundEnabled(false);
            cmdWmsBackground.setSelected(false);
        } else {
            mappingComponent.setBackgroundEnabled(true);
            cmdWmsBackground.setSelected(true);
            mappingComponent.queryServices();
        }
    }                                                                                    //GEN-LAST:event_cmdWmsBackgroundActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cmdForwardActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cmdForwardActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_cmdForwardActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cmdBackActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cmdBackActionPerformed
    }                                                                           //GEN-LAST:event_cmdBackActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cmdFullPoly1ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cmdFullPoly1ActionPerformed
        mappingComponent.zoomToSelectedNode();
    }                                                                                //GEN-LAST:event_cmdFullPoly1ActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cmdFullPolyActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cmdFullPolyActionPerformed
        mappingComponent.zoomToFullFeatureCollectionBounds();
    }                                                                               //GEN-LAST:event_cmdFullPolyActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cmdForegroundActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cmdForegroundActionPerformed
        if (mappingComponent.isFeatureCollectionVisible()) {
            mappingComponent.setFeatureCollectionVisibility(false);
            cmdForeground.setSelected(false);
        } else {
            mappingComponent.setFeatureCollectionVisibility(true);
            cmdForeground.setSelected(true);
        }
    }                                                                                 //GEN-LAST:event_cmdForegroundActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cmdNewLinestringActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cmdNewLinestringActionPerformed
        removeMainGroupSelection();
        cmdNewLinestring.setSelected(true);
        setLastMapMode(getCurrentMapMode());
        mappingComponent.setInteractionMode(MappingComponent.NEW_POLYGON);
        ((CreateGeometryListener)mappingComponent.getInputListener(MappingComponent.NEW_POLYGON)).setMode(
            CreateGeometryListener.LINESTRING);
        setCurrentMapMode(MapMode.MEASUREMENT);
    }                                                                                    //GEN-LAST:event_cmdNewLinestringActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  notfication  DOCUMENT ME!
     */
    public void featureDeleteRequested(final PNotification notfication) {
        try {
            final Object o = notfication.getObject();
            if (o instanceof DeleteFeatureListener) {
                final DeleteFeatureListener dfl = (DeleteFeatureListener)o;
                final PFeature pf = dfl.getFeatureRequestedForDeletion();
                pf.getFeature().setGeometry(null);
            }
        } catch (Exception ex) {
            LOG.warn("Fehler beim featuredeleteRequest", ex);
        }
    }

    /**
     * TODO MEssage to the user if a area could not be attached for example wfs areas.
     *
     * @param  notification  DOCUMENT ME!
     */
    public void attachFeatureRequested(final PNotification notification) {
        final Object o = notification.getObject();
        LOG.info("Try to attach Geometry");
        final AttachFeatureListener afl = (AttachFeatureListener)o;
        final PFeature pf = afl.getFeatureToAttach();
        if (pf.getFeature() instanceof PureNewFeature) {
            final Geometry g = pf.getFeature().getGeometry();
            final GeometrySlotInformation slotInfo = getBroker().assignGeometry(g);
            if (slotInfo != null) {
                slotInfo.getRefreshable().refresh(null);
                mappingComponent.getFeatureCollection().removeFeature(pf.getFeature());
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Geometrie: " + slotInfo.getOpenSlot().getGeometry() + " wird hinzugefügt");
                }
                slotInfo.getOpenSlot().setEditable(true);
                mappingComponent.getFeatureCollection().addFeature(slotInfo.getOpenSlot());
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Geometrie wurde an element: " + slotInfo.getSlotIdentifier() + " attached");
                }
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Geometrie wurde nicht attached");
                }
            }
        }
    }

    @Override
    public void refresh(final Object refreshObject) {
    }

    @Override
    public void masterConfigure(final Element parent) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("MapWidget masterConfigure");
        }
        final ConfigurationManager configManager = getBroker().getConfigManager();
        configManager.addConfigurable((ActiveLayerModel)mappingModel);
        configManager.configure(mappingModel);
        mappingComponent.preparationSetMappingModel(mappingModel);
        configManager.addConfigurable(mappingComponent);
        // ToDo should be no error (defaultValues) if there is no configuration :-)
        configManager.configure(mappingComponent);
        mappingComponent.setMappingModel(mappingModel);
        // setIsCoreWidget(true);

        initComponents();
        setWidgetEditable(false);
        mappingComponent.getFeatureCollection().addFeatureCollectionListener(this);
        mappingComponent.setBackgroundEnabled(true);
        PNotificationCenter.defaultCenter()
                .addListener(
                    this,
                    "attachFeatureRequested",
                    AttachFeatureListener.ATTACH_FEATURE_NOTIFICATION,
                    mappingComponent.getInputListener(MappingComponent.ATTACH_POLYGON_TO_ALPHADATA));
        PNotificationCenter.defaultCenter()
                .addListener(
                    this,
                    "featureDeleteRequested",
                    DeleteFeatureListener.FEATURE_DELETE_REQUEST_NOTIFICATION,
                    mappingComponent.getInputListener(MappingComponent.REMOVE_POLYGON));
        ((JHistoryButton)cmdForward).setDirection(JHistoryButton.DIRECTION_FORWARD);
        ((JHistoryButton)cmdBack).setDirection(JHistoryButton.DIRECTION_BACKWARD);
        ((JHistoryButton)cmdForward).setHistoryModel(mappingComponent);
        ((JHistoryButton)cmdBack).setHistoryModel(mappingComponent);
        mappingComponent.setBackground(getBackground());
        mappingComponent.setBackgroundEnabled(true);
        mappingComponent.setInternalLayerWidgetAvailable(true);
        this.add(BorderLayout.CENTER, mappingComponent);

        addressSearchControl.masterConfigure(parent);
    }
    // ToDo getConfiguration ??

    @Override
    public void configure(final Element parent) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Configure: " + this.getClass());
        }
        cmdSnap.setSelected(true);
//                    //TODO CHANGE CONFIG FILE ACTION

        mappingComponent.setSnappingEnabled(true);

        addressSearchControl.configure(parent);
    }

    @Override
    public void featuresRemoved(final FeatureCollectionEvent fce) {
    }

    @Override
    public void featuresChanged(final FeatureCollectionEvent fce) {
    }

    @Override
    public void featuresAdded(final FeatureCollectionEvent fce) {
    }

    @Override
    public void featureSelectionChanged(final FeatureCollectionEvent fce) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("FeatureSelection Changed");
        }
        final Collection<Feature> features = fce.getEventFeatures();
        getBroker().fireChangeEvent(features);
    }

    @Override
    public void featureReconsiderationRequested(final FeatureCollectionEvent fce) {
    }

    @Override
    public void allFeaturesRemoved(final FeatureCollectionEvent fce) {
    }

    @Override
    public void featureCollectionChanged() {
    }

    @Override
    public void guiObjectChanged(final Object changedObject) {
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public JToolBar getMapWidgetToolbar() {
        return mapWidgetToolbar;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  mapWidgetToollbar  DOCUMENT ME!
     */
    public void setMapWidgetToolbar(final JToolBar mapWidgetToollbar) {
        this.mapWidgetToolbar = mapWidgetToollbar;
    }
}
