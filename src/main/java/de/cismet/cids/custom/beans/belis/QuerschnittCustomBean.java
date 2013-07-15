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

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class QuerschnittCustomBean extends BaseEntity {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(QuerschnittCustomBean.class);

    public static final String TABLE = "querschnitt";

    private static final String PROP__GROESSE = "groesse";

    private static final String[] PROPERTY_NAMES = new String[] { PROP__ID, PROP__GROESSE };

    //~ Instance fields --------------------------------------------------------

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
        return (QuerschnittCustomBean)createNew(TABLE);
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
    public Double getGroesse() {
        return groesse;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  groesse  DOCUMENT ME!
     */
    public void setGroesse(final Double groesse) {
        final Double old = this.groesse;
        this.groesse = groesse;
        this.propertyChangeSupport.firePropertyChange(PROP__GROESSE, old, this.groesse);
    }

    @Override
    public int hashCode() {
        if (this.getId() == null) {
            return System.identityHashCode(this);
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
        if (((this.getId() == null) && (other.getId() != null))
                    || ((this.getId() != null) && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.cismet.belisEE.entity.Querschnitt[id=" + getId() + "]";
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
