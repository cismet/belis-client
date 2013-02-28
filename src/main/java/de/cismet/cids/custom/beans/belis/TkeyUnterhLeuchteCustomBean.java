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

import de.cismet.belisEEold.entity.UnterhaltLeuchte;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public class TkeyUnterhLeuchteCustomBean extends BaseEntity implements UnterhaltLeuchte {

    //~ Instance fields --------------------------------------------------------

    private Short pk;
    private String unterhaltspflichtigeLeuchte;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new TkeyUnterhLeuchteCustomBean object.
     */
    public TkeyUnterhLeuchteCustomBean() {
    }

    /**
     * Creates a new TkeyUnterhLeuchteCustomBean object.
     *
     * @param  pk  DOCUMENT ME!
     */
    public TkeyUnterhLeuchteCustomBean(final Short pk) {
        this.pk = pk;
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public Short getPk() {
        return pk;
    }

    @Override
    public void setPk(final Short pk) {
        this.pk = pk;
    }

    @Override
    public String getUnterhaltspflichtigeLeuchte() {
        return unterhaltspflichtigeLeuchte;
    }

    @Override
    public void setUnterhaltspflichtigeLeuchte(final String unterhaltspflichtigeLeuchte) {
        this.unterhaltspflichtigeLeuchte = unterhaltspflichtigeLeuchte;
    }

//    public Object getGeom() {
//        return geom;
//    }
//
//    public void setGeom(Object geom) {
//        this.geom = geom;
//    }

    @Override
    public int hashCode() {
        if (this.getPk() == null) {
            return super.hashCode();
        }
        return this.getPk().hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof TkeyUnterhLeuchteCustomBean) {
            final TkeyUnterhLeuchteCustomBean anEntity = (TkeyUnterhLeuchteCustomBean)other;
            if (this == other) {
                return true;
            } else if ((other == null) || (!this.getClass().isAssignableFrom(other.getClass()))) {
                return false;
            } else if ((this.getPk() == null) || (anEntity.getPk() == null)) {
                return false;
            } else {
                return this.getPk().equals(anEntity.getPk());
            }
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "de.cismet.belis.entity.UnterhaltLeuchte[pk=" + pk + "]";
    }

    @Override
    public String getKeyString() {
        if (getUnterhaltspflichtigeLeuchte() != null) {
            return getUnterhaltspflichtigeLeuchte();
        } else {
            return "";
        }
    }
}
