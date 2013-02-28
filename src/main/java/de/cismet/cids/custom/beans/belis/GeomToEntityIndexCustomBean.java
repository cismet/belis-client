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

import de.cismet.belisEEold.entity.GeomToEntityIndex;

import de.cismet.commons.server.interfaces.Geom;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class GeomToEntityIndexCustomBean implements GeomToEntityIndex {

    //~ Instance fields --------------------------------------------------------

    private Long id;
    private Class entityClass;
    private Long entityID;

    private Geom geometry;

    //~ Methods ----------------------------------------------------------------

    @Override
    public Long getEntityID() {
        return entityID;
    }

    @Override
    public void setEntityID(final Long enityID) {
        this.entityID = enityID;
    }

    @Override
    public Class getEntityClass() {
        return entityClass;
    }

    @Override
    public void setEntityClass(final Class entityName) {
        this.entityClass = entityName;
    }

    @Override
    public Geom getGeometry() {
        return geometry;
    }

    @Override
    public void setGeometry(final Geom geometry) {
        this.geometry = geometry;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
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
