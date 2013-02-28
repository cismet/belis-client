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

import de.cismet.belisEEold.entity.Querschnitt;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class QuerschnittCustomBean extends BaseEntity implements Querschnitt {

    //~ Instance fields --------------------------------------------------------

    private Long id;
    private Double groesse;

    //~ Methods ----------------------------------------------------------------

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    public Double getGroesse() {
        return groesse;
    }

    @Override
    public void setGroesse(final Double groesse) {
        this.groesse = groesse;
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
