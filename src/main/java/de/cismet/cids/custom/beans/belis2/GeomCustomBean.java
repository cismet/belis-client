/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.beans.belis2;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.WKTWriter;

import de.cismet.cismap.commons.CrsTransformer;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class GeomCustomBean extends BaseEntity {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(GeomCustomBean.class);

    public static final String TABLE = "geom";

    public static final String PROP__GEO_FIELD = "geo_field";
    public static final String PROP__WGS84_WKT = "wgs84_wkt";

    private static final String[] PROPERTY_NAMES = new String[] { PROP__ID, PROP__GEO_FIELD, PROP__WGS84_WKT };

    //~ Instance fields --------------------------------------------------------

    private final WKTWriter WKT_WRITER = new WKTWriter();
    private final int SRID_WGS84 = 4326;

    private Geometry geo_field;
    private String wgs84_wkt;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new BauartCustomBean object.
     */
    public GeomCustomBean() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static GeomCustomBean createNew() {
        return (GeomCustomBean)createNew(TABLE);
    }

    @Override
    public String[] getPropertyNames() {
        return PROPERTY_NAMES;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Geometry getGeo_field() {
        return geo_field;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  geo_field  DOCUMENT ME!
     */
    public void setGeo_field(final Geometry geo_field) {
        final Geometry old = this.geo_field;
        this.geo_field = geo_field;
        this.propertyChangeSupport.firePropertyChange(PROP__GEO_FIELD, old, this.geo_field);

        if (geo_field == null) {
            setWgs84_wkt(null);
        } else {
            final String crs = CrsTransformer.createCrsFromSrid(SRID_WGS84);
            final Geometry transformedGeom = CrsTransformer.transformToGivenCrs(geo_field, crs);
            transformedGeom.setSRID(SRID_WGS84);
            setWgs84_wkt(WKT_WRITER.write(transformedGeom));
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Geometry getGeomField() {
        return getGeo_field();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  geomField  val DOCUMENT ME!
     */
    public void setGeomField(final Geometry geomField) {
        setGeo_field(geomField);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getWgs84_wkt() {
        return wgs84_wkt;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  wgs84_wkt  DOCUMENT ME!
     */
    private void setWgs84_wkt(final String wgs84_wkt) {
        final String old = this.wgs84_wkt;
        this.wgs84_wkt = wgs84_wkt;
        this.propertyChangeSupport.firePropertyChange(PROP__WGS84_WKT, old, this.wgs84_wkt);
    }
}
