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

import de.cismet.belisEEold.entity.UnterhaltMast;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class TkeyUnterhMastCustomBean extends BaseEntity implements UnterhaltMast {

    //~ Instance fields --------------------------------------------------------

    private Short pk;
    private String unterhaltMast;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new TkeyUnterhMastCustomBean object.
     */
    public TkeyUnterhMastCustomBean() {
    }

    /**
     * Creates a new TkeyUnterhMastCustomBean object.
     *
     * @param  pk  DOCUMENT ME!
     */
    public TkeyUnterhMastCustomBean(final Short pk) {
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
    public String getUnterhaltMast() {
        return unterhaltMast;
    }

    @Override
    public void setUnterhaltMast(final String unterhaltMast) {
        this.unterhaltMast = unterhaltMast;
    }

    @Override
    public int hashCode() {
        if (this.getPk() == null) {
            return super.hashCode();
        }
        return this.getPk().hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof TkeyUnterhMastCustomBean) {
            final TkeyUnterhMastCustomBean anEntity = (TkeyUnterhMastCustomBean)other;
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
        return "de.cismet.belis.entity.UnterhaltMast[pk=" + pk + "]";
    }

    @Override
    public String getKeyString() {
        if (getUnterhaltMast() != null) {
            return getUnterhaltMast();
        } else {
            return "";
        }
    }
}
