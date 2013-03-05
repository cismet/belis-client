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

import de.cismet.belisEE.entity.Kennziffer;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class TkeyKennzifferCustomBean extends BaseEntity implements Kennziffer {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            TkeyKennzifferCustomBean.class);

    public static final String TABLE = "tkey_kennziffer";

    private static final String PROP__ID = "id";
    private static final String PROP__BESCHREIBUNG = "Beschreibung";
    private static final String PROP__KENNZIFFER = "Kennziffer";

    private static final String[] PROPERTY_NAMES = new String[] { PROP__ID, PROP__BESCHREIBUNG, PROP__KENNZIFFER };

    //~ Instance fields --------------------------------------------------------

    private Integer id;
    private String beschreibung;
    private Short kennziffer;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbzweigdoseCustomBean object.
     */
    public TkeyKennzifferCustomBean() {
    }

    /**
     * Creates a new TkeyKennzifferCustomBean object.
     *
     * @param  kennziffer  DOCUMENT ME!
     */
    public TkeyKennzifferCustomBean(final Short kennziffer) {
        setKennziffer(kennziffer);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static TkeyKennzifferCustomBean createNew() {
        try {
            return (TkeyKennzifferCustomBean)CidsBean.createNewCidsBeanFromTableName(CidsBroker.BELIS_DOMAIN, TABLE);
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
    public Short getKennziffer() {
        return kennziffer;
    }

    @Override
    public void setKennziffer(final Short kennziffer) {
        final Short old = this.kennziffer;
        this.kennziffer = kennziffer;
        this.propertyChangeSupport.firePropertyChange(PROP__KENNZIFFER, old, this.kennziffer);
    }

    @Override
    public String getBeschreibung() {
        return beschreibung;
    }

    @Override
    public void setBeschreibung(final String beschreibung) {
        final String old = this.beschreibung;
        this.beschreibung = beschreibung;
        this.propertyChangeSupport.firePropertyChange(PROP__BESCHREIBUNG, old, this.beschreibung);
    }

    @Override
    public int hashCode() {
        if (this.getKennziffer() == null) {
            return super.hashCode();
        }
        return this.getKennziffer().hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof TkeyKennzifferCustomBean) {
            final TkeyKennzifferCustomBean anEntity = (TkeyKennzifferCustomBean)other;
            if (this == other) {
                return true;
            } else if ((other == null) || (!this.getClass().isAssignableFrom(other.getClass()))) {
                return false;
            } else if ((this.getKennziffer() == null) || (anEntity.getKennziffer() == null)) {
                return false;
            } else {
                return this.getKennziffer().equals(anEntity.getKennziffer());
            }
        } else {
            return false;
        }
    }

    @Override
    public String getKeyString() {
        if ((getKennziffer() != null) && (getBeschreibung() != null)) {
            return getKennziffer() + " - " + getBeschreibung();
        } else if (getKennziffer() != null) {
            return getKennziffer() + " - Keine Beschreibung vorhanden.";
        } else if (getBeschreibung() != null) {
            return "Keine ID - " + getBeschreibung();
        } else {
            return "";
        }
    }

    @Override
    public String getCompareCriteriaString() {
        if (getBeschreibung() != null) {
            return getBeschreibung();
        } else {
            return "";
        }
    }
}
