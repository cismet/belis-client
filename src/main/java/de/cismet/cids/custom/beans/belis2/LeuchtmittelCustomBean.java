/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.beans.belis2;

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

    //~ Instance fields --------------------------------------------------------

    private String hersteller;
    private String lichtfarbe;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new BauartCustomBean object.
     */
    public LeuchtmittelCustomBean() {
        addPropertyNames(
            new String[] {
                PROP__HERSTELLER,
                PROP__LICHTFARBE
            });
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
    public String toString() {
        return getHersteller() + " " + getLichtfarbe();
    }
}
