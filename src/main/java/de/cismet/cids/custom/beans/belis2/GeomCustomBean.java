/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.beans.belis2;

import com.vividsolutions.jts.geom.Geometry;

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

    private static final String[] PROPERTY_NAMES = new String[] { PROP__ID, PROP__GEO_FIELD };

    //~ Instance fields --------------------------------------------------------

    private Geometry geo_field;

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
}
