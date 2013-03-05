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

import de.cismet.belisEE.entity.Querschnitt;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class QuerschnittCustomBean extends BaseEntity implements Querschnitt {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(QuerschnittCustomBean.class);

    public static final String TABLE = "querschnitt";

    private static final String PROP__ID = "id";
    private static final String PROP__GROESSE = "groesse";

    private static final String[] PROPERTY_NAMES = new String[] { PROP__ID, PROP__GROESSE };

    //~ Instance fields --------------------------------------------------------

    private Long id;
    private Double groesse;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbzweigdoseCustomBean object.
     */
    public QuerschnittCustomBean() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static QuerschnittCustomBean createNew() {
        try {
            return (QuerschnittCustomBean)CidsBean.createNewCidsBeanFromTableName(CidsBroker.BELIS_DOMAIN, TABLE);
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
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long id) {
        final Long old = this.id;
        this.id = id;
        this.propertyChangeSupport.firePropertyChange(PROP__ID, old, this.id);
    }

    @Override
    public Double getGroesse() {
        return groesse;
    }

    @Override
    public void setGroesse(final Double groesse) {
        final Double old = this.groesse;
        this.groesse = groesse;
        this.propertyChangeSupport.firePropertyChange(PROP__GROESSE, old, this.groesse);
    }

    @Override
    public int hashCode() {
        if (this.getId() == null) {
            return super.hashCode();
        }
        return this.getId().hashCode();
    }

    @Override
    public boolean equals(final Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QuerschnittCustomBean)) {
            return false;
        }
        final QuerschnittCustomBean other = (QuerschnittCustomBean)object;
        if (((this.id == null) && (other.id != null)) || ((this.id != null) && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.cismet.belisEE.entity.Querschnitt[id=" + id + "]";
    }

    @Override
    public String getKeyString() {
        if (getGroesse() != null) {
            return getGroesse().toString();
        } else {
            return "";
        }
    }
}
