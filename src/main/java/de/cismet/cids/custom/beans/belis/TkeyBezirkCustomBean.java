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

import de.cismet.belisEE.entity.Stadtbezirk;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class TkeyBezirkCustomBean extends BaseEntity implements Stadtbezirk {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(TkeyBezirkCustomBean.class);

    public static final String TABLE = "tkey_bezirk";

    private static final String PROP__ID = "id";
    private static final String PROP__BEZIRK = "Bezirk";
    private static final String PROP__PK = "pk";

    private static final String[] PROPERTY_NAMES = new String[] { PROP__ID, PROP__BEZIRK, PROP__PK };

    //~ Instance fields --------------------------------------------------------

    private Integer id;
    private String bezirk;
    private Short pk;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbzweigdoseCustomBean object.
     */
    public TkeyBezirkCustomBean() {
    }

    /**
     * Creates a new TkeyBezirkCustomBean object.
     *
     * @param  pk  DOCUMENT ME!
     */
    public TkeyBezirkCustomBean(final Short pk) {
        setPk(pk);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static TkeyBezirkCustomBean createNew() {
        try {
            return (TkeyBezirkCustomBean)CidsBean.createNewCidsBeanFromTableName(CidsBroker.BELIS_DOMAIN, TABLE);
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

    @Override
    public String getBezirk() {
        return bezirk;
    }

    @Override
    public void setBezirk(final String bezirk) {
        final String old = this.bezirk;
        this.bezirk = bezirk;
        this.propertyChangeSupport.firePropertyChange(PROP__BEZIRK, old, this.bezirk);
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
        if (other instanceof TkeyBezirkCustomBean) {
            final TkeyBezirkCustomBean anEntity = (TkeyBezirkCustomBean)other;
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
        return "de.cismet.belis.entity.Stadtbezirk[pk=" + pk + "]";
    }

    @Override
    public String getKeyString() {
        if (getBezirk() != null) {
            return getBezirk();
        } else {
            return "";
        }
    }
}
