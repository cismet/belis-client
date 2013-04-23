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

import de.cismet.belisEE.entity.Bauart;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class BauartCustomBean extends BaseEntity implements Bauart {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(BauartCustomBean.class);

    public static final String TABLE = "bauart";

    private static final String PROP__BEZEICHNUNG = "bezeichnung";

    private static final String[] PROPERTY_NAMES = new String[] { PROP__ID, PROP__BEZEICHNUNG };

    //~ Instance fields --------------------------------------------------------

    private String bezeichnung;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new BauartCustomBean object.
     */
    public BauartCustomBean() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static BauartCustomBean createNew() {
        try {
            return (BauartCustomBean)CidsBean.createNewCidsBeanFromTableName(CidsBroker.BELIS_DOMAIN, TABLE);
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
    public String getBezeichnung() {
        return bezeichnung;
    }

    @Override
    public void setBezeichnung(final String bezeichnung) {
        final String old = this.bezeichnung;
        this.bezeichnung = bezeichnung;
        this.propertyChangeSupport.firePropertyChange(PROP__BEZEICHNUNG, old, this.bezeichnung);
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
        if (!(object instanceof BauartCustomBean)) {
            return false;
        }
        final BauartCustomBean other = (BauartCustomBean)object;
        if (((this.getId() == null) && (other.getId() != null))
                    || ((this.getId() != null) && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.cismet.belisEE.entity.Bauart[id=" + getId() + "]";
    }

    @Override
    public String getKeyString() {
        if (getBezeichnung() != null) {
            return getBezeichnung();
        } else {
            return "";
        }
    }
}
