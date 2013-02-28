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

import de.cismet.belisEEold.entity.Energielieferant;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class TkeyEnergielieferantCustomBean extends BaseEntity implements Energielieferant {

    //~ Instance fields --------------------------------------------------------

    private Short pk;
    private String energielieferant;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new TkeyEnergielieferantCustomBean object.
     */
    public TkeyEnergielieferantCustomBean() {
    }

    /**
     * Creates a new TkeyEnergielieferantCustomBean object.
     *
     * @param  pk  DOCUMENT ME!
     */
    public TkeyEnergielieferantCustomBean(final Short pk) {
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
    public String getEnergielieferant() {
        return energielieferant;
    }

    @Override
    public void setEnergielieferant(final String energielieferant) {
        this.energielieferant = energielieferant;
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
        if (other instanceof TkeyEnergielieferantCustomBean) {
            final TkeyEnergielieferantCustomBean anEntity = (TkeyEnergielieferantCustomBean)other;
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
        return "de.cismet.belis.entity.Energielieferant[pk=" + pk + "]";
    }

    @Override
    public String getKeyString() {
        if (getEnergielieferant() != null) {
            return getEnergielieferant();
        } else {
            return "";
        }
    }
}
