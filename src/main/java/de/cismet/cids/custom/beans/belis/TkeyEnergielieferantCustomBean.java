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

import de.cismet.belisEE.entity.Energielieferant;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class TkeyEnergielieferantCustomBean extends BaseEntity implements Energielieferant {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            TkeyEnergielieferantCustomBean.class);

    public static final String TABLE = "tkey_energielieferant";

    private static final String PROP__ID = "id";
    private static final String PROP__ENERGIELIEFERANT = "Energielieferant";
    private static final String PROP__PK = "pk";

    private static final String[] PROPERTY_NAMES = new String[] { PROP__ID, PROP__ENERGIELIEFERANT, PROP__PK };

    //~ Instance fields --------------------------------------------------------

    private Integer id;
    private String energielieferant;
    private Short pk;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbzweigdoseCustomBean object.
     */
    public TkeyEnergielieferantCustomBean() {
    }

    /**
     * Creates a new TkeyEnergielieferantCustomBean object.
     *
     * @param  pk  DOCUMENT ME!
     */
    public TkeyEnergielieferantCustomBean(final Short pk) {
        setPk(pk);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static TkeyEnergielieferantCustomBean createNew() {
        try {
            return (TkeyEnergielieferantCustomBean)CidsBean.createNewCidsBeanFromTableName(
                    CidsBroker.BELIS_DOMAIN,
                    TABLE);
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
    public String getEnergielieferant() {
        return energielieferant;
    }

    @Override
    public void setEnergielieferant(final String energielieferant) {
        final String old = this.energielieferant;
        this.energielieferant = energielieferant;
        this.propertyChangeSupport.firePropertyChange(PROP__ENERGIELIEFERANT, old, this.energielieferant);
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
        if (other instanceof TkeyEnergielieferantCustomBean) {
            final TkeyEnergielieferantCustomBean anEntity = (TkeyEnergielieferantCustomBean)other;
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
        return "de.cismet.belis.entity.Energielieferant[pk=" + pk + "]";
    }

    @Override
    public String getKeyString() {
        if (getEnergielieferant() != null) {
            return getEnergielieferant();
        } else {
            return "";
        }
    }
}
