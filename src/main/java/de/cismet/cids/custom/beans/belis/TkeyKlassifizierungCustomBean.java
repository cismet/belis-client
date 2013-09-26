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

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class TkeyKlassifizierungCustomBean extends BaseEntity {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            TkeyKlassifizierungCustomBean.class);

    public static final String TABLE = "tkey_klassifizierung";

    public static final String PROP__KLASSIFIZIERUNG = "klassifizierung";
    public static final String PROP__PK = "pk";

    private static final String[] PROPERTY_NAMES = new String[] { PROP__ID, PROP__KLASSIFIZIERUNG, PROP__PK };

    //~ Instance fields --------------------------------------------------------

    private String klassifizierung;
    private Integer pk;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbzweigdoseCustomBean object.
     */
    public TkeyKlassifizierungCustomBean() {
    }

    /**
     * Creates a new TkeyKlassifizierungCustomBean object.
     *
     * @param  pk  DOCUMENT ME!
     */
    public TkeyKlassifizierungCustomBean(final Integer pk) {
        setPk(pk);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static TkeyKlassifizierungCustomBean createNew() {
        return (TkeyKlassifizierungCustomBean)createNew(TABLE);
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
    public String getKlassifizierung() {
        return klassifizierung;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  klassifizierung  DOCUMENT ME!
     */
    public void setKlassifizierung(final String klassifizierung) {
        final String old = this.klassifizierung;
        this.klassifizierung = klassifizierung;
        this.propertyChangeSupport.firePropertyChange(PROP__KLASSIFIZIERUNG, old, this.klassifizierung);
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
        if (other instanceof TkeyKlassifizierungCustomBean) {
            final TkeyKlassifizierungCustomBean anEntity = (TkeyKlassifizierungCustomBean)other;
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
        return getKeyString();
    }

    @Override
    public String getKeyString() {
        if (getKlassifizierung() != null) {
            return getKlassifizierung().toLowerCase(Locale.GERMAN);
        } else {
            return "";
        }
    }

    @Override
    public String getCompareCriteriaString() {
        // Workaround: 'keine Angabe' is ordered on the first place
        if (getPk().equals(0)) {
            return "00000";
        } else {
            return getKeyString();
        }
    }
}
