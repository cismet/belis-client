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
package de.cismet.cids.custom.beans.belis;

import de.cismet.belis.broker.CidsBroker;

import de.cismet.belisEE.entity.GeomToEntityIndex;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class GeomToEntityIndexCustomBean extends CidsBean implements GeomToEntityIndex {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            GeomToEntityIndexCustomBean.class);

    public static final String TABLE = "geom_to_entity_index";

    private static final String PROP__ID = "id";
    private static final String PROP__ENTITYCLASS = "entityclass";
    private static final String PROP__ENTITYID = "entityid";
    private static final String PROP__FK_GEOM = "fk_geom";

    private static final String[] PROPERTY_NAMES = new String[] {
            PROP__ID,
            PROP__ENTITYCLASS,
            PROP__ENTITYID,
            PROP__FK_GEOM
        };

    //~ Instance fields --------------------------------------------------------

    private Long id;
    private String entityclass;
    private Long entityid;
    private GeomCustomBean fk_geom;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbzweigdoseCustomBean object.
     */
    public GeomToEntityIndexCustomBean() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static GeomToEntityIndexCustomBean createNew() {
        try {
            return (GeomToEntityIndexCustomBean)CidsBean.createNewCidsBeanFromTableName(CidsBroker.BELIS_DOMAIN, TABLE);
        } catch (Exception ex) {
            LOG.error("error creating " + TABLE + " bean", ex);
            return null;
        }
    }

    @Override
    public String[] getPropertyNames() {
        return PROPERTY_NAMES;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long id) {
        final Long old = this.id;
        this.id = id;
        this.propertyChangeSupport.firePropertyChange(PROP__ID, old, this.id);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getEntityclass() {
        return entityclass;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  entityclass  DOCUMENT ME!
     */
    public void setEntityclass(final String entityclass) {
        final String old = this.entityclass;
        this.entityclass = entityclass;
        this.propertyChangeSupport.firePropertyChange(PROP__ENTITYCLASS, old, this.entityclass);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Long getEntityid() {
        return entityid;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  entityid  DOCUMENT ME!
     */
    public void setEntityid(final Long entityid) {
        final Long old = this.entityid;
        this.entityid = entityid;
        this.propertyChangeSupport.firePropertyChange(PROP__ENTITYID, old, this.entityid);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public GeomCustomBean getFk_geom() {
        return fk_geom;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_geom  DOCUMENT ME!
     */
    public void setFk_geom(final GeomCustomBean fk_geom) {
        final GeomCustomBean old = this.fk_geom;
        this.fk_geom = fk_geom;
        this.propertyChangeSupport.firePropertyChange(PROP__FK_GEOM, old, this.fk_geom);
    }

    @Override
    public Long getEntityID() {
        return getEntityid();
    }

    @Override
    public void setEntityID(final Long enityID) {
        setEntityid(enityID);
    }

    @Override
    public Class getEntityClass() {
        try {
            return Class.forName(getEntityclass());
        } catch (ClassNotFoundException ex) {
            LOG.error(ex, ex);
            return null;
        }
    }

    @Override
    public void setEntityClass(final Class entityClass) {
        setEntityclass(entityClass.getCanonicalName());
    }

    @Override
    public GeomCustomBean getGeometry() {
        return getFk_geom();
    }

    @Override
    public void setGeometry(final GeomCustomBean geometry) {
        setFk_geom(geometry);
    }

    @Override
    public int hashCode() {
        if (this.getId() == null) {
            return super.hashCode();
        }
        return this.getId().hashCode();
    }

    @Override
    public boolean equals(final Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GeomToEntityIndexCustomBean)) {
            return false;
        }
        final GeomToEntityIndexCustomBean other = (GeomToEntityIndexCustomBean)object;
        if (((this.id == null) && (other.id != null)) || ((this.id != null) && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.cismet.belisEE.entity.GeomToEntityIndex[id=" + id + "]";
    }
}
