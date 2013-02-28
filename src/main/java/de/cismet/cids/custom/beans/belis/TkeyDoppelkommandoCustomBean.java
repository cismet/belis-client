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

import de.cismet.belisEEold.entity.Doppelkommando;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class TkeyDoppelkommandoCustomBean extends BaseEntity implements Doppelkommando {

    //~ Instance fields --------------------------------------------------------

    private String pk;
    private String beschreibung;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new TkeyDoppelkommandoCustomBean object.
     */
    public TkeyDoppelkommandoCustomBean() {
    }

    /**
     * Creates a new TkeyDoppelkommandoCustomBean object.
     *
     * @param  pk  DOCUMENT ME!
     */
    public TkeyDoppelkommandoCustomBean(final String pk) {
        this.pk = pk;
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
    public String getBeschreibung() {
        return beschreibung;
    }

    @Override
    public void setBeschreibung(final String beschreibung) {
        this.beschreibung = beschreibung;
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
        if (other instanceof TkeyDoppelkommandoCustomBean) {
            final TkeyDoppelkommandoCustomBean anEntity = (TkeyDoppelkommandoCustomBean)other;
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
        return "de.cismet.belis.entity.Doppelkommando[pk=" + pk + "]";
    }

    @Override
    public String getKeyString() {
        if (getBeschreibung() != null) {
            return getBeschreibung();
        } else {
            return "";
        }
    }
}
