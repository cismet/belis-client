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

import de.cismet.belisEEold.entity.Strassenschluessel;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class TkeyStrassenschluesselCustomBean extends BaseEntity implements Strassenschluessel {

    //~ Instance fields --------------------------------------------------------

    private String pk;
    private String strasse;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new TkeyStrassenschluesselCustomBean object.
     */
    public TkeyStrassenschluesselCustomBean() {
    }

    /**
     * Creates a new TkeyStrassenschluesselCustomBean object.
     *
     * @param  pk  DOCUMENT ME!
     */
    public TkeyStrassenschluesselCustomBean(final String pk) {
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
    public String getStrasse() {
        return strasse;
    }

    @Override
    public void setStrasse(final String strasse) {
        this.strasse = strasse;
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
        if (other instanceof TkeyStrassenschluesselCustomBean) {
            final TkeyStrassenschluesselCustomBean anEntity = (TkeyStrassenschluesselCustomBean)other;
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
        return "de.cismet.belis.entity.Strassenschlüssel[pk=" + pk + "]";
    }

    @Override
    public String getKeyString() {
//        if(getPk() != null && getStrasse() != null){
//            return getPk()+" - "+getStrasse();
//        } else if(getPk() != null){
//            return getPk()+" - Kein Straßennamen vorhanden.";
//        } else if(getStrasse() != null){
//            return "Keine ID - "+getStrasse();
//        } else {
//            return "";
//        }
        if (getStrasse() != null) {
            return getStrasse();
        } else {
            return "";
        }
    }

    @Override
    public String getCompareCriteriaString() {
        if (getStrasse() != null) {
            return getStrasse();
        } else {
            return "";
        }
    }
}
