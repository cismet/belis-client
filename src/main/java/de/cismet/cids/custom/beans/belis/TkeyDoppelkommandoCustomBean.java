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

import de.cismet.belisEE.entity.Doppelkommando;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class TkeyDoppelkommandoCustomBean extends BaseEntity implements Doppelkommando {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            TkeyDoppelkommandoCustomBean.class);

    public static final String TABLE = "tkey_doppelkommando";

    private static final String PROP__BESCHREIBUNG = "beschreibung";
    private static final String PROP__PK = "pk";

    private static final String[] PROPERTY_NAMES = new String[] { PROP__ID, PROP__BESCHREIBUNG, PROP__PK };

    //~ Instance fields --------------------------------------------------------

    private String beschreibung;
    private String pk;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbzweigdoseCustomBean object.
     */
    public TkeyDoppelkommandoCustomBean() {
    }

    /**
     * Creates a new TkeyDoppelkommandoCustomBean object.
     *
     * @param  pk  DOCUMENT ME!
     */
    public TkeyDoppelkommandoCustomBean(final String pk) {
        setPk(pk);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static TkeyDoppelkommandoCustomBean createNew() {
        try {
            return (TkeyDoppelkommandoCustomBean)CidsBean.createNewCidsBeanFromTableName(
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

    @Override
    public String getPk() {
        return pk;
    }

    @Override
    public void setPk(final String pk) {
        final String old = this.pk;
        this.pk = pk;
        this.propertyChangeSupport.firePropertyChange(PROP__PK, old, this.pk);
    }

    @Override
    public String getBeschreibung() {
        return beschreibung;
    }

    @Override
    public void setBeschreibung(final String beschreibung) {
        this.beschreibung = beschreibung;
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
        if (other instanceof TkeyDoppelkommandoCustomBean) {
            final TkeyDoppelkommandoCustomBean anEntity = (TkeyDoppelkommandoCustomBean)other;
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
        return "de.cismet.belis.entity.Doppelkommando[pk=" + pk + "]";
    }

    @Override
    public String getKeyString() {
        if (getBeschreibung() != null) {
            return getBeschreibung();
        } else {
            return "";
        }
    }
}
