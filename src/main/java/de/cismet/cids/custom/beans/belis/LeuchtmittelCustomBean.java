/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.beans.belis;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class LeuchtmittelCustomBean extends BaseEntity {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(LeuchtmittelCustomBean.class);

    public static final String TABLE = "leuchtmittel";

    public static final String PROP__HERSTELLER = "hersteller";
    public static final String PROP__LICHTFARBE = "lichtfarbe";

    private static final String[] PROPERTY_NAMES = new String[] { PROP__ID, PROP__HERSTELLER, PROP__LICHTFARBE };

    //~ Instance fields --------------------------------------------------------

    private String hersteller;
    private String lichtfarbe;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new BauartCustomBean object.
     */
    public LeuchtmittelCustomBean() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static LeuchtmittelCustomBean createNew() {
        return (LeuchtmittelCustomBean)createNew(TABLE);
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
    public String getHersteller() {
        return hersteller;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  hersteller  geo_field DOCUMENT ME!
     */
    public void setHersteller(final String hersteller) {
        final String old = this.hersteller;
        this.hersteller = hersteller;
        this.propertyChangeSupport.firePropertyChange(PROP__HERSTELLER, old, this.hersteller);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getLichtfarbe() {
        return lichtfarbe;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  lichtfarbe  DOCUMENT ME!
     */
    public void setLichtfarbe(final String lichtfarbe) {
        final String old = this.lichtfarbe;
        this.lichtfarbe = lichtfarbe;
        this.propertyChangeSupport.firePropertyChange(PROP__LICHTFARBE, old, this.lichtfarbe);
    }

    @Override
    public String getKeyString() {
        return getHersteller() + " " + getLichtfarbe();
    }

    @Override
    public int hashCode() {
        if (this.getId() == null) {
            return System.identityHashCode(this);
        }
        return this.getId().hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof LeuchtmittelCustomBean) {
            final LeuchtmittelCustomBean anEntity = (LeuchtmittelCustomBean)other;
            if (this == other) {
                return true;
            } else if ((other == null) || (!this.getClass().isAssignableFrom(other.getClass()))) {
                return false;
            } else if ((this.getId() == null) || (anEntity.getId() == null)) {
                return false;
            } else {
                return this.getId().equals(anEntity.getId());
            }
        } else {
            return false;
        }
    }
}
