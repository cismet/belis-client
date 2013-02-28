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

import de.cismet.belisEEold.entity.Masttyp;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class TkeyMasttypCustomBean extends BaseEntity implements Masttyp {

    //~ Instance fields --------------------------------------------------------

    private String masttyp;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new TkeyMasttypCustomBean object.
     */
    public TkeyMasttypCustomBean() {
    }

    /**
     * Creates a new TkeyMasttypCustomBean object.
     *
     * @param  masttyp  DOCUMENT ME!
     */
    public TkeyMasttypCustomBean(final String masttyp) {
        this.masttyp = masttyp;
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public String getMasttyp() {
        return masttyp;
    }

    @Override
    public void setMasttyp(final String masttyp) {
        this.masttyp = masttyp;
    }

    @Override
    public int hashCode() {
        if (this.getMasttyp() == null) {
            return super.hashCode();
        }
        return this.getMasttyp().hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof TkeyMasttypCustomBean) {
            final TkeyMasttypCustomBean anEntity = (TkeyMasttypCustomBean)other;
            if (this == other) {
                return true;
            } else if ((other == null) || (!this.getClass().isAssignableFrom(other.getClass()))) {
                return false;
            } else if ((this.getMasttyp() == null) || (anEntity.getMasttyp() == null)) {
                return false;
            } else {
                return this.getMasttyp().equals(anEntity.getMasttyp());
            }
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "de.cismet.belis.entity.Masttyp[masttyp=" + masttyp + "]";
    }

    @Override
    public String getKeyString() {
        if (getMasttyp() != null) {
            return getMasttyp();
        } else {
            return "";
        }
    }
}
