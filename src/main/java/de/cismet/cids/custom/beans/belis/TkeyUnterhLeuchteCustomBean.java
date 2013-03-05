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

import de.cismet.belisEE.entity.UnterhaltLeuchte;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public class TkeyUnterhLeuchteCustomBean extends BaseEntity implements UnterhaltLeuchte {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            TkeyUnterhLeuchteCustomBean.class);

    public static final String TABLE = "tkey_unterh_leuchte";

    private static final String PROP__ID = "id";
    private static final String PROP__UNTERHALTSPFLICHTIGER_LEUCHTE = "Unterhaltspflichtiger_Leuchte";
    private static final String PROP__PK = "pk";

    private static final String[] PROPERTY_NAMES = new String[] {
            PROP__ID,
            PROP__UNTERHALTSPFLICHTIGER_LEUCHTE,
            PROP__PK
        };

    //~ Instance fields --------------------------------------------------------

    private Integer id;
    private String Unterhaltspflichtiger_Leuchte;
    private Short pk;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbzweigdoseCustomBean object.
     */
    public TkeyUnterhLeuchteCustomBean() {
    }

    /**
     * Creates a new TkeyUnterhLeuchteCustomBean object.
     *
     * @param  pk  DOCUMENT ME!
     */
    public TkeyUnterhLeuchteCustomBean(final Short pk) {
        setPk(pk);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static TkeyUnterhLeuchteCustomBean createNew() {
        try {
            return (TkeyUnterhLeuchteCustomBean)CidsBean.createNewCidsBeanFromTableName(CidsBroker.BELIS_DOMAIN, TABLE);
        } catch (Exception ex) {
            LOG.error("error creating " + TABLE + " bean", ex);
            return null;
        }
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
    public Integer getId() {
        return id;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  id  DOCUMENT ME!
     */
    public void setId(final Integer id) {
        final Integer old = this.id;
        this.id = id;
        this.propertyChangeSupport.firePropertyChange(PROP__ID, old, this.id);
    }

    @Override
    public Short getPk() {
        return pk;
    }

    @Override
    public void setPk(final Short pk) {
        final Short old = this.pk;
        this.pk = pk;
        this.propertyChangeSupport.firePropertyChange(PROP__PK, old, this.pk);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getUnterhaltspflichtiger_Leuchte() {
        return Unterhaltspflichtiger_Leuchte;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  Unterhaltspflichtiger_Leuchte  DOCUMENT ME!
     */
    public void setUnterhaltspflichtiger_Leuchte(final String Unterhaltspflichtiger_Leuchte) {
        final String old = this.Unterhaltspflichtiger_Leuchte;
        this.Unterhaltspflichtiger_Leuchte = Unterhaltspflichtiger_Leuchte;
        this.propertyChangeSupport.firePropertyChange(
            PROP__UNTERHALTSPFLICHTIGER_LEUCHTE,
            old,
            this.Unterhaltspflichtiger_Leuchte);
    }

    @Override
    public String getUnterhaltspflichtigeLeuchte() {
        return getUnterhaltspflichtiger_Leuchte();
    }

    @Override
    public void setUnterhaltspflichtigeLeuchte(final String unterhaltspflichtigeLeuchte) {
        setUnterhaltspflichtiger_Leuchte(unterhaltspflichtigeLeuchte);
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
        if (other instanceof TkeyUnterhLeuchteCustomBean) {
            final TkeyUnterhLeuchteCustomBean anEntity = (TkeyUnterhLeuchteCustomBean)other;
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
        return "de.cismet.belis.entity.UnterhaltLeuchte[pk=" + pk + "]";
    }

    @Override
    public String getKeyString() {
        if (getUnterhaltspflichtigeLeuchte() != null) {
            return getUnterhaltspflichtigeLeuchte();
        } else {
            return "";
        }
    }
}
