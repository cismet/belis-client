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

import de.cismet.belisEEold.entity.Klassifizierung;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class TkeyKlassifizierungCustomBean extends BaseEntity implements Klassifizierung {

    //~ Instance fields --------------------------------------------------------

    private Short pk;
    private String klassifizierung;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new TkeyKlassifizierungCustomBean object.
     */
    public TkeyKlassifizierungCustomBean() {
    }

    /**
     * Creates a new TkeyKlassifizierungCustomBean object.
     *
     * @param  pk  DOCUMENT ME!
     */
    public TkeyKlassifizierungCustomBean(final Short pk) {
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
    public String getKlassifizierung() {
        return klassifizierung;
    }

    @Override
    public void setKlassifizierung(final String klassifizierung) {
        this.klassifizierung = klassifizierung;
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
        if (other instanceof TkeyKlassifizierungCustomBean) {
            final TkeyKlassifizierungCustomBean anEntity = (TkeyKlassifizierungCustomBean)other;
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
        return "de.cismet.belis.entity.Klassifizierung[pk=" + pk + "]";
    }

    @Override
    public String getKeyString() {
        if (getKlassifizierung() != null) {
            return getKlassifizierung();
        } else {
            return "";
        }
    }
}
