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

import de.cismet.belis.broker.CidsBroker;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class TkeyStrassenschluesselCustomBean extends BaseEntity {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            TkeyStrassenschluesselCustomBean.class);

    public static final String TABLE = "tkey_strassenschluessel";

    private static final String PROP__STRASSE = "strasse";
    private static final String PROP__PK = "pk";

    private static final String[] PROPERTY_NAMES = new String[] { PROP__ID, PROP__STRASSE, PROP__PK };

    //~ Instance fields --------------------------------------------------------

    private String strasse;
    private String pk;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbzweigdoseCustomBean object.
     */
    public TkeyStrassenschluesselCustomBean() {
    }

    /**
     * Creates a new TkeyStrassenschluesselCustomBean object.
     *
     * @param  pk  DOCUMENT ME!
     */
    public TkeyStrassenschluesselCustomBean(final String pk) {
        setPk(pk);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static TkeyStrassenschluesselCustomBean createNew() {
        return (TkeyStrassenschluesselCustomBean)createNew(TABLE);
    }

    @Override
    public String[] getPropertyNames() {
        return PROPERTY_NAMES;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getPk() {
        return pk;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  pk  DOCUMENT ME!
     */
    public void setPk(final String pk) {
        final String old = this.pk;
        this.pk = pk;
        this.propertyChangeSupport.firePropertyChange(PROP__PK, old, this.pk);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getStrasse() {
        return strasse;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  strasse  DOCUMENT ME!
     */
    public void setStrasse(final String strasse) {
        final String old = this.strasse;
        this.strasse = strasse;
        this.propertyChangeSupport.firePropertyChange(PROP__STRASSE, old, this.strasse);
    }

    @Override
    public int hashCode() {
        if (this.getPk() == null) {
            return System.identityHashCode(this);
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
