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

import java.sql.Date;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import javax.imageio.ImageIO;

import de.cismet.cids.custom.beans.belis2.AbzweigdoseCustomBean;
import de.cismet.cids.custom.beans.belis2.ArbeitsauftragCustomBean;
import de.cismet.cids.custom.beans.belis2.ArbeitsprotokollCustomBean;
import de.cismet.cids.custom.beans.belis2.GeometrieCustomBean;
import de.cismet.cids.custom.beans.belis2.LeitungCustomBean;
import de.cismet.cids.custom.beans.belis2.MauerlascheCustomBean;
import de.cismet.cids.custom.beans.belis2.SchaltstelleCustomBean;
import de.cismet.cids.custom.beans.belis2.TdtaLeuchtenCustomBean;
import de.cismet.cids.custom.beans.belis2.TdtaStandortMastCustomBean;

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
    private static String MAP_URL;
    private static int MAP_HEIGHT;
    private static int MAP_WIDTH;
    private static int BASE_DPI;
    private static int TARGET_DPI;
    private static double MAP_BUFFER;
    private static Font FONT = new Font("SansSerif", Font.PLAIN, 16);

    static {
        final Properties prop = new Properties();
        try {
            prop.load(ReportingArbeitsauftrag.class.getResourceAsStream(
                    "/de/cismet/belis/reports/reporting.properties"));
            MAP_URL = prop.getProperty("map.url");
            MAP_WIDTH = Integer.parseInt(prop.getProperty("map.width"));
            MAP_HEIGHT = Integer.parseInt(prop.getProperty("map.height"));
            BASE_DPI = Integer.parseInt(prop.getProperty("base.dpi"));
            TARGET_DPI = Integer.parseInt(prop.getProperty("target.dpi"));
            MAP_BUFFER = Double.parseDouble(prop.getProperty("map.buffer"));
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
//HashMap<Object, ArrayList<>
    ArbeitsauftragCustomBean orig;

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

        final Collection<ArbeitsprotokollCustomBean> positionen = aaBean.getAr_protokolle();
        int zaehler = 0;
        for (final ArbeitsprotokollCustomBean position : positionen) {
            zaehler++;
            String veranlassungsnummer = position.getVeranlassungsnummer();
            if (veranlassungsnummer == null) {
                veranlassungsnummer = OHNE_VERANLASSUNG;
            }
            positionenNachVeranlassung.put(veranlassungsnummer, new ReportingPosition(zaehler, position));
        }
        final Iterator veranlassungenIt = positionenNachVeranlassung.keySet().iterator();
        while (veranlassungenIt.hasNext()) {
            final String key = (String)veranlassungenIt.next();
            final Collection value = (Collection)positionenNachVeranlassung.get(key);
            veranlassungen.add(new ReportingVeranlassung(key, value));
        }
        initMap();
    }

    /**
     * DOCUMENT ME!
     */
    private void initMap() {
        final SimpleWMS s = new SimpleWMS(new SimpleWmsGetMapUrl(MAP_URL));
        final ArbeitsauftragCustomBean arbeitsauftragCustomBean = (ArbeitsauftragCustomBean)orig;
        final ArrayList<GeoBaseEntity> allOriginalFeatures = new ArrayList<GeoBaseEntity>();
        final ArrayList<Feature> annotatingFeatures = new ArrayList<Feature>();
        final HeadlessMapProvider mapProvider = new HeadlessMapProvider();
        final FeatureAnnotationSymbol symb = new FeatureAnnotationSymbol(new BufferedImage(
                    10,
                    10,
                    BufferedImage.TYPE_INT_ARGB)); // ((StyledFeature)
        symb.setSweetSpotX(0.5);
        symb.setSweetSpotY(0.5);

        int position = 0;
        Geometry union = null;
        for (final ArbeitsprotokollCustomBean protokoll
                    : arbeitsauftragCustomBean.getAr_protokolle()) {
            position++;

            final AbzweigdoseCustomBean abzweigdose = protokoll.getFk_abzweigdose();
            final LeitungCustomBean leitung = protokoll.getFk_leitung();
            final TdtaLeuchtenCustomBean leuchte = protokoll.getFk_leuchte();
            final TdtaStandortMastCustomBean standort = protokoll.getFk_standort();
            final MauerlascheCustomBean mauerlasche = protokoll.getFk_mauerlasche();
            final SchaltstelleCustomBean schaltstelle = protokoll.getFk_schaltstelle();
            final GeometrieCustomBean geometrie = protokoll.getFk_geometrie();
            final GeoBaseEntity entity;
            if (abzweigdose != null) {
                entity = abzweigdose;
            } else if (leitung != null) {
                entity = leitung;
            } else if (leuchte != null) {
                entity = leuchte;
            } else if (standort != null) {
                entity = standort;
            } else if (mauerlasche != null) {
                entity = mauerlasche;
            } else if (schaltstelle != null) {
                entity = schaltstelle;
            } else if (geometrie != null) {
                entity = geometrie;
            } else {
                entity = null;
            }

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
                            new CustomFixedWidthStroke(2f));
                    dsf.setGeometry(geom);
                    dsf.setPrimaryAnnotation("  P" + position);
                    dsf.setPrimaryAnnotationPaint(Color.black);
                    dsf.setPrimaryAnnotationHalo(Color.WHITE);
                    dsf.setAutoScale(true);

                    // unsichtbar
                    dsf.setLinePaint(new Color(0, 0, 0, 1));
                    dsf.setFillingPaint(new Color(0, 0, 0, 1));
                    dsf.setTransparency(1);
                    dsf.setPrimaryAnnotationFont(FONT);
                    dsf.setFeatureAnnotationSymbol(symb);

                    annotatingFeatures.add(dsf);
                }
            }
        }
        if (union != null) {
            union = union.getEnvelope().buffer(MAP_BUFFER);
            union.setSRID(31466);
        }

        final XBoundingBox bb = new XBoundingBox(union);
        mapProvider.setBoundingBox(bb);
        mapProvider.addLayer(s);
        mapProvider.addFeatures(allOriginalFeatures);
        mapProvider.addFeatures(annotatingFeatures);

        try {
            map = (BufferedImage)mapProvider.getImageAndWait(BASE_DPI, TARGET_DPI, MAP_WIDTH, MAP_HEIGHT);
        } catch (Exception ex) {
            LOG.error(ex, ex);
            fallbackMap();
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void fallbackMap() {
        try {
            map = ImageIO.read(new URL("http://lorempixel.com/554/219/abstract/"));
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
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
