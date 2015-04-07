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
 * @version  $Revision$, $Date$
 */
public class TkeyEnergielieferantCustomBean extends BaseEntity {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            TkeyEnergielieferantCustomBean.class);

    public static final String TABLE = "tkey_energielieferant";

    public static final String PROP__ENERGIELIEFERANT = "energielieferant";
    public static final String PROP__PK = "pk";

    //~ Instance fields --------------------------------------------------------

    private String energielieferant;
    private Integer pk;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbzweigdoseCustomBean object.
     */
    public TkeyEnergielieferantCustomBean() {
        addPropertyNames(
            new String[] {
                PROP__ENERGIELIEFERANT,
                PROP__PK
            });
    }

    /**
     * Creates a new TkeyEnergielieferantCustomBean object.
     *
     * @param  pk  DOCUMENT ME!
     */
    public TkeyEnergielieferantCustomBean(final Integer pk) {
        setPk(pk);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static TkeyEnergielieferantCustomBean createNew() {
        return (TkeyEnergielieferantCustomBean)createNew(TABLE);
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
    public String getEnergielieferant() {
        return energielieferant;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  energielieferant  DOCUMENT ME!
     */
    public void setEnergielieferant(final String energielieferant) {
        final String old = this.energielieferant;
        this.energielieferant = energielieferant;
        this.propertyChangeSupport.firePropertyChange(PROP__ENERGIELIEFERANT, old, this.energielieferant);
    }

    @Override
    public String toString() {
        if (getEnergielieferant() != null) {
            return getEnergielieferant();
        } else {
            return "";
        }
    }
}
