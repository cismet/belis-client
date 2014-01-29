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
package de.cismet.cids.custom.beans.belis2;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public class TkeyUnterhLeuchteCustomBean extends BaseEntity {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            TkeyUnterhLeuchteCustomBean.class);

    public static final String TABLE = "tkey_unterh_leuchte";

    public static final String PROP__UNTERHALTSPFLICHTIGER_LEUCHTE = "unterhaltspflichtiger_leuchte";
    public static final String PROP__PK = "pk";

    private static final String[] PROPERTY_NAMES = new String[] {
            PROP__ID,
            PROP__UNTERHALTSPFLICHTIGER_LEUCHTE,
            PROP__PK
        };

    //~ Instance fields --------------------------------------------------------

    private Integer id;
    private String unterhaltspflichtiger_leuchte;
    private Integer pk;

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
    public TkeyUnterhLeuchteCustomBean(final Integer pk) {
        setPk(pk);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static TkeyUnterhLeuchteCustomBean createNew() {
        return (TkeyUnterhLeuchteCustomBean)createNew(TABLE);
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
    public String getUnterhaltspflichtiger_leuchte() {
        return unterhaltspflichtiger_leuchte;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  Unterhaltspflichtiger_Leuchte  DOCUMENT ME!
     */
    public void setUnterhaltspflichtiger_leuchte(final String Unterhaltspflichtiger_Leuchte) {
        final String old = this.unterhaltspflichtiger_leuchte;
        this.unterhaltspflichtiger_leuchte = Unterhaltspflichtiger_Leuchte;
        this.propertyChangeSupport.firePropertyChange(
            PROP__UNTERHALTSPFLICHTIGER_LEUCHTE,
            old,
            this.unterhaltspflichtiger_leuchte);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getUnterhaltspflichtigeLeuchte() {
        return getUnterhaltspflichtiger_leuchte();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  unterhaltspflichtigeLeuchte  DOCUMENT ME!
     */
    public void setUnterhaltspflichtigeLeuchte(final String unterhaltspflichtigeLeuchte) {
        setUnterhaltspflichtiger_leuchte(unterhaltspflichtigeLeuchte);
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
        return getUnterhaltspflichtigeLeuchte();
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
