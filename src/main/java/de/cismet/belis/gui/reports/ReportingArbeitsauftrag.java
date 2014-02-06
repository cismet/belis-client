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

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.util.PBounds;

import org.apache.commons.collections.MultiHashMap;

import org.openide.util.Exceptions;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import java.io.IOException;

import java.net.URL;

import java.text.NumberFormat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;

import javax.swing.ImageIcon;

import de.cismet.belis.todo.CustomMutableTreeTableNode;

import de.cismet.cids.custom.beans.belis2.AbzweigdoseCustomBean;
import de.cismet.cids.custom.beans.belis2.ArbeitsauftragCustomBean;
import de.cismet.cids.custom.beans.belis2.ArbeitsprotokollCustomBean;
import de.cismet.cids.custom.beans.belis2.GeometrieCustomBean;
import de.cismet.cids.custom.beans.belis2.LeitungCustomBean;
import de.cismet.cids.custom.beans.belis2.MauerlascheCustomBean;
import de.cismet.cids.custom.beans.belis2.SchaltstelleCustomBean;
import de.cismet.cids.custom.beans.belis2.TdtaLeuchtenCustomBean;
import de.cismet.cids.custom.beans.belis2.TdtaStandortMastCustomBean;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cismap.commons.HeadlessMapProvider;
import de.cismet.cismap.commons.XBoundingBox;
import de.cismet.cismap.commons.features.DefaultStyledFeature;
import de.cismet.cismap.commons.features.DefaultXStyledFeature;
import de.cismet.cismap.commons.features.Feature;
import de.cismet.cismap.commons.gui.piccolo.CustomFixedWidthStroke;
import de.cismet.cismap.commons.gui.piccolo.FeatureAnnotationSymbol;
import de.cismet.cismap.commons.raster.wms.simple.SimpleWMS;
import de.cismet.cismap.commons.raster.wms.simple.SimpleWmsGetMapUrl;

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

    static {
        final Properties prop = new Properties();
        try {
            prop.load(ReportingArbeitsauftrag.class.getResourceAsStream("reporting.properties"));
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
    CidsBean orig;

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  aaBean  DOCUMENT ME!
     */
    void init(final CidsBean aaBean) {
        orig = aaBean;
        nummer = "Arbeitsauftrag: A" + String.valueOf(aaBean.getProperty("nummer"));
        angelegt = "angelegt von: " + String.valueOf(aaBean.getProperty("angelegt_von")) + " ("
                    + String.valueOf(aaBean.getProperty("angelegt_am")) + ")";
        final Object an = aaBean.getProperty("zugewiesen_an");
        zugewiesen_an = "zugewiesen an: " + ((an != null) ? String.valueOf(an) : "_____________");

        final List<CidsBean> positionen = aaBean.getBeanCollectionProperty("ar_protokolle");
        int zaehler = 0;
        for (final CidsBean position : positionen) {
            zaehler++;
            String veranlassungsnummer = (String)position.getProperty("veranlassungsnummer");
            if (veranlassungsnummer == null) {
                veranlassungsnummer = OHNE_VERANLASSUNG;
            }
            positionenNachVeranlassung.put(veranlassungsnummer, new ReportingPosition(zaehler, position));
        }
        final Iterator veranlassungenIt = positionenNachVeranlassung.keySet().iterator();
        while (veranlassungenIt.hasNext()) {
            final String key = (String)veranlassungenIt.next();
            final List value = (List)positionenNachVeranlassung.get(key);
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
        final ArrayList<Feature> allOriginalFeatures = new ArrayList<Feature>();
        final ArrayList<Feature> annotatingFeatures = new ArrayList<Feature>();
        final HeadlessMapProvider mapProvider = new HeadlessMapProvider();

        Geometry union = null;
        for (final ArbeitsprotokollCustomBean protokoll
                    : arbeitsauftragCustomBean.getAr_protokolle()) {
            final AbzweigdoseCustomBean abzweigdose = protokoll.getFk_abzweigdose();
            final LeitungCustomBean leitung = protokoll.getFk_leitung();
            final TdtaLeuchtenCustomBean leuchte = protokoll.getFk_leuchte();
            final TdtaStandortMastCustomBean standort = protokoll.getFk_standort();
            final MauerlascheCustomBean mauerlasche = protokoll.getFk_mauerlasche();
            final SchaltstelleCustomBean schaltstelle = protokoll.getFk_schaltstelle();
            final GeometrieCustomBean geometrie = protokoll.getFk_geometrie();
            final CustomMutableTreeTableNode node;
            if (abzweigdose != null) {
                allOriginalFeatures.add(abzweigdose);
            } else if (leitung != null) {
                allOriginalFeatures.add(leitung);
            } else if (leuchte != null) {
                allOriginalFeatures.add(leuchte);
            } else if (standort != null) {
                allOriginalFeatures.add(standort);
            } else if (mauerlasche != null) {
                allOriginalFeatures.add(mauerlasche);
            } else if (schaltstelle != null) {
                allOriginalFeatures.add(schaltstelle);
            } else if (geometrie != null) {
                allOriginalFeatures.add(geometrie);
            } else {
            }
            int position = 0;
            for (final Feature f : allOriginalFeatures) {
                position++;
                if (union == null) {
                    union = f.getGeometry().getEnvelope();
                } else {
                    union = union.getEnvelope().union(f.getGeometry().getEnvelope());
                }
                final DefaultXStyledFeature dsf = new DefaultXStyledFeature(
                        null,
                        "",
                        "",
                        null,
                        new CustomFixedWidthStroke(2f));
                dsf.setGeometry(f.getGeometry());
                dsf.setPrimaryAnnotation("  P" + position);
                dsf.setPrimaryAnnotationPaint(Color.black);

                final BufferedImage bi = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
                final Graphics2D graphics = (Graphics2D)bi.getGraphics();
                final FeatureAnnotationSymbol symb = new FeatureAnnotationSymbol(bi); // ((StyledFeature)

                symb.setSweetSpotX(0.5);
                symb.setSweetSpotY(0.5);
                dsf.setIconImage(new ImageIcon(bi));
                dsf.setAutoScale(false);
                final Font font = new Font("SansSerif", Font.PLAIN, 4);
                dsf.setPrimaryAnnotationFont(font);
                dsf.setFeatureAnnotationSymbol(symb);
                mapProvider.addFeature(dsf);
            }
        }
        union = union.getEnvelope().buffer(MAP_BUFFER);
        mapProvider.addLayer(s);

        union.setSRID(31466);
        final XBoundingBox bb = new XBoundingBox(union);

        mapProvider.setBoundingBox(bb);
        System.out.println(bb);

        mapProvider.addFeatures(allOriginalFeatures);

        try {
            final Image img = mapProvider.getImageAndWait(BASE_DPI, TARGET_DPI, MAP_WIDTH, MAP_HEIGHT);
            final String masstab = "1:"
                        + NumberFormat.getIntegerInstance().format(mapProvider.getImageScaleDenominator());
            map = (BufferedImage)img;
        } catch (Exception ex) {
            ex.printStackTrace();
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
