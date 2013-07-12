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
public class TkeyMastartCustomBean extends BaseEntity {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(TkeyMastartCustomBean.class);

    public static final String TABLE = "tkey_mastart";

    private static final String PROP__MASTART = "mastart";
    private static final String PROP__PK = "pk";

    private static final String[] PROPERTY_NAMES = new String[] { PROP__ID, PROP__MASTART, PROP__PK };

    //~ Instance fields --------------------------------------------------------

    private String mastart;
    private String pk;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbzweigdoseCustomBean object.
     */
    public TkeyMastartCustomBean() {
    }

    /**
     * Creates a new TkeyMastartCustomBean object.
     *
     * @param  pk  DOCUMENT ME!
     */
    public TkeyMastartCustomBean(final String pk) {
        setPk(pk);
    }

    /**
     * Creates a new TkeyMastartCustomBean object.
     *
     * @param  pk       DOCUMENT ME!
     * @param  mastart  DOCUMENT ME!
     */
    public TkeyMastartCustomBean(final String pk, final String mastart) {
        setPk(pk);
        setMastart(mastart);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static TkeyMastartCustomBean createNew() {
        try {
            return (TkeyMastartCustomBean)CidsBean.createNewCidsBeanFromTableName(CidsBroker.BELIS_DOMAIN, TABLE);
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
    public String getMastart() {
        return mastart;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  mastart  DOCUMENT ME!
     */
    public void setMastart(final String mastart) {
        final String old = this.mastart;
        this.mastart = mastart;
        this.propertyChangeSupport.firePropertyChange(PROP__MASTART, old, this.mastart);
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
