/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.belis.gui.reports;

import com.vividsolutions.jts.geom.Geometry;

import org.apache.commons.collections.MultiHashMap;

import org.openide.util.Exceptions;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import java.io.IOException;

import java.net.URL;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;

import de.cismet.belis.broker.BelisBroker;

import de.cismet.cids.custom.beans.belis2.ArbeitsauftragCustomBean;
import de.cismet.cids.custom.beans.belis2.ArbeitsprotokollCustomBean;

import de.cismet.cismap.commons.HeadlessMapProvider;
import de.cismet.cismap.commons.XBoundingBox;
import de.cismet.cismap.commons.features.DefaultXStyledFeature;
import de.cismet.cismap.commons.features.Feature;
import de.cismet.cismap.commons.gui.piccolo.CustomFixedWidthStroke;
import de.cismet.cismap.commons.gui.piccolo.FeatureAnnotationSymbol;
import de.cismet.cismap.commons.raster.wms.simple.SimpleWMS;
import de.cismet.cismap.commons.raster.wms.simple.SimpleWmsGetMapUrl;

import de.cismet.commons.server.entity.GeoBaseEntity;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public class ReportingArbeitsauftrag {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ReportingArbeitsauftrag.class);

    private static String OVERVIEWMAP_URL;
    private static int OVERVIEWMAP_HEIGHT;
    private static int OVERVIEWMAP_WIDTH;
    private static double OVERVIEWMAP_BUFFER;

    private static String POSITIONMAP_URL;
    private static int POSITIONMAP_HEIGHT;
    private static int POSITIONMAP_WIDTH;
    private static double POSITIONMAP_BUFFER;

    private static int BASE_DPI;
    private static int TARGET_DPI;
    private static Font FONT = new Font("SansSerif", Font.PLAIN, 16);

    static {
        final Properties prop = new Properties();
        try {
            prop.load(ReportingArbeitsauftrag.class.getResourceAsStream(
                    "/de/cismet/belis/reports/reporting.properties"));
            OVERVIEWMAP_URL = prop.getProperty("map.overview.url");
            OVERVIEWMAP_WIDTH = Integer.parseInt(prop.getProperty("map.overview.width"));
            OVERVIEWMAP_HEIGHT = Integer.parseInt(prop.getProperty("map.overview.height"));
            OVERVIEWMAP_BUFFER = Double.parseDouble(prop.getProperty("map.overview.buffer"));
            POSITIONMAP_URL = prop.getProperty("map.position.url");
            POSITIONMAP_WIDTH = Integer.parseInt(prop.getProperty("map.position.width"));
            POSITIONMAP_HEIGHT = Integer.parseInt(prop.getProperty("map.position.height"));
            POSITIONMAP_BUFFER = Double.parseDouble(prop.getProperty("map.position.buffer"));
            BASE_DPI = Integer.parseInt(prop.getProperty("base.dpi"));
            TARGET_DPI = Integer.parseInt(prop.getProperty("target.dpi"));
        } catch (Exception ex) {
            LOG.error("Error during intializing of BelisReportingParameters. No Report will be available");
        }
    }

    public static final String OHNE_VERANLASSUNG = "OHNE";

    //~ Instance fields --------------------------------------------------------

    String nummer;
    String angelegt;
    String zugewiesen_an;
    BufferedImage map;
    String link = "http://www.cismet.de";
    ArrayList<ReportingVeranlassung> veranlassungen = new ArrayList<ReportingVeranlassung>();
    MultiHashMap positionenNachVeranlassung = new MultiHashMap();
    ArbeitsauftragCustomBean orig;
    private HeadlessMapProvider mapProvider = new HeadlessMapProvider();
    private final Map<Integer, DefaultXStyledFeature> positionFeatureMap =
        new HashMap<Integer, DefaultXStyledFeature>();
    private ArrayList<GeoBaseEntity> allOriginalFeatures;
    private final SimpleWMS overviewMap = new SimpleWMS(new SimpleWmsGetMapUrl(OVERVIEWMAP_URL));
    private final SimpleWMS positionMap = new SimpleWMS(new SimpleWmsGetMapUrl(POSITIONMAP_URL));

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  aaBean  DOCUMENT ME!
     */
    public void init(final ArbeitsauftragCustomBean aaBean) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        orig = aaBean;
        nummer = "Arbeitsauftrag: A" + aaBean.getNummer();
        angelegt = aaBean.getAngelegt_von() + " (" + dateFormat.format(aaBean.getAngelegt_am()) + ")";
        final String an = aaBean.getZugewiesen_an();
        zugewiesen_an = ((an != null) ? an : "_____________");

        initMap();

        final List<String> keys = new ArrayList<String>();
        final Collection<ArbeitsprotokollCustomBean> positionen = aaBean.getSortedProtokolle();
        for (final ArbeitsprotokollCustomBean position : positionen) {
            mapProvider = new HeadlessMapProvider();
            mapProvider.setCenterMapOnResize(true);
            mapProvider.addLayer(positionMap);
            mapProvider.addFeatures(allOriginalFeatures);

            String veranlassungsnummer = position.getVeranlassungsnummer();
            if (veranlassungsnummer == null) {
                veranlassungsnummer = OHNE_VERANLASSUNG;
            }
            if (position.getProtokollnummer() != null) {
                mapProvider.addFeature(positionFeatureMap.get(position.getProtokollnummer()));

                positionenNachVeranlassung.put(
                    veranlassungsnummer,
                    new ReportingPosition(
                        position.getProtokollnummer(),
                        position,
                        getPositionImageFrom(position.getChildEntity())));
                positionFeatureMap.get(position.getProtokollnummer()).setPrimaryAnnotationVisible(false);
                if (!keys.contains(veranlassungsnummer)) {
                    keys.add(veranlassungsnummer);
                }
            }
        }

//        Collections.sort(keys);
        for (final String key : keys) {
            final Collection value = (Collection)positionenNachVeranlassung.get(key);
            veranlassungen.add(new ReportingVeranlassung(key, value));
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void initMap() {
        final ArbeitsauftragCustomBean arbeitsauftragCustomBean = (ArbeitsauftragCustomBean)orig;
        allOriginalFeatures = new ArrayList<GeoBaseEntity>();
        final ArrayList<Feature> annotatingFeatures = new ArrayList<Feature>();
        mapProvider.setCenterMapOnResize(true);

        final FeatureAnnotationSymbol symb = new FeatureAnnotationSymbol(new BufferedImage(
                    10,
                    10,
                    BufferedImage.TYPE_INT_ARGB)); // ((StyledFeature)
        symb.setSweetSpotX(0.5);
        symb.setSweetSpotY(0.5);

        Geometry union = null;
        for (final ArbeitsprotokollCustomBean protokoll
                    : arbeitsauftragCustomBean.getSortedProtokolle()) {
            final GeoBaseEntity entity = protokoll.getChildEntity();

            if (entity != null) {
                allOriginalFeatures.add(entity);
                final Geometry geom = entity.getGeometry();
                if (geom != null) {
                    if (union == null) {
                        union = geom.getEnvelope();
                    } else {
                        union = union.getEnvelope().union(geom.getEnvelope());
                    }

                    final DefaultXStyledFeature dsf = new DefaultXStyledFeature(
                            null,
                            "",
                            "",
                            null,
                            new CustomFixedWidthStroke(2f, BelisBroker.getInstance().getMappingComponent()));
                    dsf.setGeometry(geom);
                    dsf.setPrimaryAnnotation("  P" + protokoll.getProtokollnummer());
                    dsf.setPrimaryAnnotationPaint(Color.black);
                    dsf.setPrimaryAnnotationHalo(Color.WHITE);
                    dsf.setAutoScale(true);

                    // unsichtbar
                    dsf.setLinePaint(new Color(0, 0, 0, 1));
                    dsf.setFillingPaint(new Color(0, 0, 0, 1));
                    dsf.setTransparency(1);
                    dsf.setPrimaryAnnotationFont(FONT);
                    dsf.setFeatureAnnotationSymbol(symb);

                    positionFeatureMap.put(protokoll.getProtokollnummer(), dsf);

                    annotatingFeatures.add(dsf);
                }
            }
        }
        if (union != null) {
            union = union.getEnvelope().buffer(OVERVIEWMAP_BUFFER);
            union.setSRID(31466);
        }

        mapProvider.addLayer(overviewMap);
        mapProvider.addFeatures(allOriginalFeatures);
        mapProvider.addFeatures(annotatingFeatures);

        final XBoundingBox bb = new XBoundingBox(union);
        mapProvider.setBoundingBox(bb);

        try {
            map = (BufferedImage)mapProvider.getImageAndWait(
                    BASE_DPI,
                    TARGET_DPI,
                    OVERVIEWMAP_WIDTH,
                    OVERVIEWMAP_HEIGHT);
        } catch (final Exception ex) {
            LOG.error(ex, ex);
            map = getFallbackMap();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   bean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private BufferedImage getPositionImageFrom(final GeoBaseEntity bean) {
        if (bean.getGeometry() != null) {
            final Geometry geom = bean.getGeometry().getEnvelope().buffer(POSITIONMAP_BUFFER);
            geom.setSRID(31466);
            try {
                mapProvider.setBoundingBox(new XBoundingBox(geom));
                return (BufferedImage)mapProvider.getImageAndWait(
                        BASE_DPI,
                        TARGET_DPI,
                        POSITIONMAP_WIDTH,
                        POSITIONMAP_HEIGHT);
            } catch (Exception ex) {
                LOG.error(ex, ex);
                return getFallbackMap();
            }
        } else {
            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private BufferedImage getFallbackMap() {
        try {
            return ImageIO.read(new URL("http://lorempixel.com/554/219/abstract/"));
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getNummer() {
        return nummer;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getAngelegt() {
        return angelegt;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public BufferedImage getMap() {
        return map;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getZugewiesen_an() {
        return zugewiesen_an;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public ArrayList<ReportingVeranlassung> getVeranlassungen() {
        return veranlassungen;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public MultiHashMap getPositionenNachVeranlassung() {
        return positionenNachVeranlassung;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getLink() {
        return link;
    }
}
