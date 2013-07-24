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

import java.util.Locale;

import de.cismet.belis.broker.CidsBroker;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class TkeyUnterhMastCustomBean extends BaseEntity {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            TkeyUnterhMastCustomBean.class);

    public static final String TABLE = "tkey_unterh_mast";

    private static final String PROP__UNTERHALT_MAST = "unterhalt_mast";
    private static final String PROP__PK = "pk";

    private static final String[] PROPERTY_NAMES = new String[] { PROP__ID, PROP__UNTERHALT_MAST, PROP__PK };

    //~ Instance fields --------------------------------------------------------

    private String unterhalt_mast;
    private Integer pk;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbzweigdoseCustomBean object.
     */
    public TkeyUnterhMastCustomBean() {
    }

    /**
     * Creates a new TkeyUnterhMastCustomBean object.
     *
     * @param  pk  DOCUMENT ME!
     */
    public TkeyUnterhMastCustomBean(final Integer pk) {
        setPk(pk);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static TkeyUnterhMastCustomBean createNew() {
        return (TkeyUnterhMastCustomBean)createNew(TABLE);
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
    public Integer getPk() {
        return pk;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  pk  DOCUMENT ME!
     */
    public void setPk(final Integer pk) {
        final Integer old = this.pk;
        this.pk = pk;
        this.propertyChangeSupport.firePropertyChange(PROP__PK, old, this.pk);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getUnterhalt_mast() {
        return unterhalt_mast;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  Unterhalt_Mast  DOCUMENT ME!
     */
    public void setUnterhalt_mast(final String Unterhalt_Mast) {
        final String old = this.unterhalt_mast;
        this.unterhalt_mast = Unterhalt_Mast;
        this.propertyChangeSupport.firePropertyChange(PROP__UNTERHALT_MAST, old, this.unterhalt_mast);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getUnterhaltMast() {
        return getUnterhalt_mast();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  unterhaltMast  DOCUMENT ME!
     */
    public void setUnterhaltMast(final String unterhaltMast) {
        setUnterhalt_mast(unterhaltMast);
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
        if (other instanceof TkeyUnterhMastCustomBean) {
            final TkeyUnterhMastCustomBean anEntity = (TkeyUnterhMastCustomBean)other;
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
        return "de.cismet.belis.entity.UnterhaltMast[pk=" + pk + "]";
    }

    @Override
    public String getKeyString() {
        if (getUnterhaltMast() != null) {
            return getUnterhaltMast().toLowerCase(Locale.GERMAN);
        } else {
            return "";
        }
    }
}
