/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.beans.belis;

import com.vividsolutions.jts.geom.Geometry;

import de.cismet.belis.broker.CidsBroker;

import de.cismet.belisEE.entity.Geom;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class GeomCustomBean extends BaseEntity implements Geom {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(GeomCustomBean.class);

    public static final String TABLE = "geom";

    private static final String PROP__GEO_FIELD = "geo_field";

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
        try {
            return (GeomCustomBean)CidsBean.createNewCidsBeanFromTableName(CidsBroker.BELIS_DOMAIN, TABLE);
        } catch (Exception ex) {
            LOG.error("error creating " + TABLE + " bean", ex);
            return null;
        }
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
    @Override
    public Geometry getGeomField() {
        return getGeo_field();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  geomField  val DOCUMENT ME!
     */
    @Override
    public void setGeomField(final Geometry geomField) {
        setGeo_field(geomField);
    }

    @Override
    public String getKeyString() {
        return "Geom [" + getId() + "]";
    }

    @Override
    public int hashCode() {
        if (this.getId() == null) {
            return super.hashCode();
        }
        return this.getId().hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof GeomCustomBean) {
            final GeomCustomBean anEntity = (GeomCustomBean)other;
            if (this == other) {
                return true;
            } else if ((other == null) || (!this.getClass().isAssignableFrom(other.getClass()))) {
                return false;
            } else if ((this.getId() == null) || (anEntity.getId() == null)) {
                return false;
            } else {
                return this.getId().equals(anEntity.getId());
            }
        } else {
            return false;
        }
    }
}
