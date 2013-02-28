/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.commons.server.interfaces;

import org.postgis.Geometry;

import de.cismet.commons.server.entity.BaseEntity;
/**
 * DOCUMENT ME!
 *
 * @version             $Revision$, $Date$
 * @NamedNativeQueries  ({ @NamedNativeQuery(name="Geom.getAllGeometriesByBoundingBox",query="SELECT id FROM geom WHERE
 *                      id = ?") })
 */
public class Geom extends BaseEntity {

    //~ Instance fields --------------------------------------------------------

    private Integer id;

    private Geometry geomField;

    private transient Boolean isEditable = false;
    private transient Boolean isHidden = false;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new Geom object.
     */
    public Geom() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getId() {
        return id;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  val  DOCUMENT ME!
     */
    public void setId(final Integer val) {
        this.id = val;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Geometry getGeomField() {
        return geomField;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  val  DOCUMENT ME!
     */
    public void setGeomField(final Geometry val) {
        this.geomField = val;
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
        if (other instanceof Geom) {
            final Geom anEntity = (Geom)other;
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
