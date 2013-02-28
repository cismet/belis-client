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

import de.cismet.belisEEold.entity.Mastart;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class TkeyMastartCustomBean extends BaseEntity implements Mastart {

    //~ Instance fields --------------------------------------------------------

    private String pk;
    private String mastart;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new TkeyMastartCustomBean object.
     */
    public TkeyMastartCustomBean() {
    }

    /**
     * Creates a new TkeyMastartCustomBean object.
     *
     * @param  pk  DOCUMENT ME!
     */
    public TkeyMastartCustomBean(final String pk) {
        this.pk = pk;
    }

    /**
     * Creates a new TkeyMastartCustomBean object.
     *
     * @param  pk       DOCUMENT ME!
     * @param  mastart  DOCUMENT ME!
     */
    public TkeyMastartCustomBean(final String pk, final String mastart) {
        this.pk = pk;
        this.mastart = mastart;
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public String getPk() {
        return pk;
    }

    @Override
    public void setPk(final String pk) {
        this.pk = pk;
    }

    @Override
    public String getMastart() {
        return mastart;
    }

    @Override
    public void setMastart(final String mastart) {
        this.mastart = mastart;
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
        if (other instanceof TkeyMastartCustomBean) {
            final TkeyMastartCustomBean anEntity = (TkeyMastartCustomBean)other;
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
        return "de.cismet.belis.entity.Mastart[pk=" + pk + "]";
    }

    @Override
    public String getKeyString() {
        if (getMastart() != null) {
            return getMastart();
        } else {
            return "";
        }
    }
}
