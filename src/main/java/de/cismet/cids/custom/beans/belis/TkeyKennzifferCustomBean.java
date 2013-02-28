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

import de.cismet.belisEEold.entity.Kennziffer;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class TkeyKennzifferCustomBean extends BaseEntity implements Kennziffer {

    //~ Instance fields --------------------------------------------------------

    private Short kennziffer;
    private String beschreibung;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new TkeyKennzifferCustomBean object.
     */
    public TkeyKennzifferCustomBean() {
    }

    /**
     * Creates a new TkeyKennzifferCustomBean object.
     *
     * @param  kennziffer  DOCUMENT ME!
     */
    public TkeyKennzifferCustomBean(final Short kennziffer) {
        this.kennziffer = kennziffer;
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public Short getKennziffer() {
        return kennziffer;
    }

    @Override
    public void setKennziffer(final Short kennziffer) {
        this.kennziffer = kennziffer;
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
        if (this.getKennziffer() == null) {
            return super.hashCode();
        }
        return this.getKennziffer().hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof TkeyKennzifferCustomBean) {
            final TkeyKennzifferCustomBean anEntity = (TkeyKennzifferCustomBean)other;
            if (this == other) {
                return true;
            } else if ((other == null) || (!this.getClass().isAssignableFrom(other.getClass()))) {
                return false;
            } else if ((this.getKennziffer() == null) || (anEntity.getKennziffer() == null)) {
                return false;
            } else {
                return this.getKennziffer().equals(anEntity.getKennziffer());
            }
        } else {
            return false;
        }
    }

    @Override
    public String getKeyString() {
        if ((getKennziffer() != null) && (getBeschreibung() != null)) {
            return getKennziffer() + " - " + getBeschreibung();
        } else if (getKennziffer() != null) {
            return getKennziffer() + " - Keine Beschreibung vorhanden.";
        } else if (getBeschreibung() != null) {
            return "Keine ID - " + getBeschreibung();
        } else {
            return "";
        }
    }

    @Override
    public String getCompareCriteriaString() {
        if (getBeschreibung() != null) {
            return getBeschreibung();
        } else {
            return "";
        }
    }
}
