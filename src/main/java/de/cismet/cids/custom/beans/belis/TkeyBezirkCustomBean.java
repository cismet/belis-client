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

import de.cismet.belisEEold.entity.Stadtbezirk;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class TkeyBezirkCustomBean extends BaseEntity implements Stadtbezirk {

    //~ Instance fields --------------------------------------------------------

    private Short pk;
    private String bezirk;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new TkeyBezirkCustomBean object.
     */
    public TkeyBezirkCustomBean() {
    }

    /**
     * Creates a new TkeyBezirkCustomBean object.
     *
     * @param  pk  DOCUMENT ME!
     */
    public TkeyBezirkCustomBean(final Short pk) {
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
    public String getBezirk() {
        return bezirk;
    }

    @Override
    public void setBezirk(final String bezirk) {
        this.bezirk = bezirk;
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
        if (other instanceof TkeyBezirkCustomBean) {
            final TkeyBezirkCustomBean anEntity = (TkeyBezirkCustomBean)other;
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
        return "de.cismet.belis.entity.Stadtbezirk[pk=" + pk + "]";
    }

    @Override
    public String getKeyString() {
        if (getBezirk() != null) {
            return getBezirk();
        } else {
            return "";
        }
    }
}
