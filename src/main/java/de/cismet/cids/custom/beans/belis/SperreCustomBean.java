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

import java.util.Date;

import de.cismet.belisEEold.entity.Lock;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class SperreCustomBean extends BaseEntity implements Lock {

    //~ Instance fields --------------------------------------------------------

    private String classId;
    private String objectId;
    private String userString;
    private String additionalInfo;
    private Date lockTimestamp;
    private Integer id;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new SperreCustomBean object.
     */
    public SperreCustomBean() {
    }

    /**
     * Creates a new SperreCustomBean object.
     *
     * @param  id  DOCUMENT ME!
     */
    public SperreCustomBean(final Integer id) {
        this.id = id;
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public String getClassId() {
        return classId;
    }

    @Override
    public void setClassId(final String classId) {
        this.classId = classId;
    }

    @Override
    public String getObjectId() {
        return objectId;
    }

    @Override
    public void setObjectId(final String objectId) {
        this.objectId = objectId;
    }

    @Override
    public String getUserString() {
        return userString;
    }

    @Override
    public void setUserString(final String userString) {
        this.userString = userString;
    }

    @Override
    public String getAdditionalInfo() {
        return additionalInfo;
    }

    @Override
    public void setAdditionalInfo(final String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(final Integer id) {
        this.id = id;
    }

    @Override
    public Date getTimestamp() {
        return lockTimestamp;
    }

    @Override
    public void setTimestamp(final Date timestamp) {
        this.lockTimestamp = timestamp;
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
        if (!(object instanceof SperreCustomBean)) {
            return false;
        }
        final SperreCustomBean other = (SperreCustomBean)object;
        if (((this.id == null) && (other.id != null)) || ((this.id != null) && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.cismet.belisEE.entity.Lock[id=" + id + "]";
    }

    @Override
    public String getKeyString() {
        return "de.cismet.belisEE.entity.Lock[id=" + id + "]";
    }
}
