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

import java.sql.Date;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class SperreCustomBean extends BaseEntity {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(SperreCustomBean.class);

    public static final String TABLE = "sperre";

    public static final String PROP__LOCK_TIMESTAMP = "lock_timestamp";
    public static final String PROP__USER_STRING = "user_string";
    public static final String PROP__ADDITIONAL_INFO = "additional_info";
    public static final String PROP__OBJECT_ID = "object_id";
    public static final String PROP__CLASS_ID = "class_id";

    private static final String[] PROPERTY_NAMES = new String[] {
            PROP__ID,
            PROP__LOCK_TIMESTAMP,
            PROP__USER_STRING,
            PROP__ADDITIONAL_INFO,
            PROP__OBJECT_ID,
            PROP__CLASS_ID
        };

    //~ Instance fields --------------------------------------------------------

    private Date lock_timestamp;
    private String user_string;
    private String additional_info;
    private Integer object_id;
    private Integer class_id;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbzweigdoseCustomBean object.
     */
    public SperreCustomBean() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static SperreCustomBean createNew() {
        return (SperreCustomBean)createNew(TABLE);
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
    public Date getLock_timestamp() {
        return lock_timestamp;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  lock_timestamp  DOCUMENT ME!
     */
    public void setLock_timestamp(final Date lock_timestamp) {
        final Date old = this.lock_timestamp;
        this.lock_timestamp = lock_timestamp;
        this.propertyChangeSupport.firePropertyChange(PROP__LOCK_TIMESTAMP, old, this.lock_timestamp);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getUser_string() {
        return user_string;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  user_string  DOCUMENT ME!
     */
    public void setUser_string(final String user_string) {
        final String old = this.user_string;
        this.user_string = user_string;
        this.propertyChangeSupport.firePropertyChange(PROP__USER_STRING, old, this.user_string);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getAdditional_info() {
        return additional_info;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  additional_info  DOCUMENT ME!
     */
    public void setAdditional_info(final String additional_info) {
        final String old = this.additional_info;
        this.additional_info = additional_info;
        this.propertyChangeSupport.firePropertyChange(PROP__ADDITIONAL_INFO, old, this.additional_info);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getObject_id() {
        return object_id;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  object_id  DOCUMENT ME!
     */
    public void setObject_id(final Integer object_id) {
        final Integer old = this.object_id;
        this.object_id = object_id;
        this.propertyChangeSupport.firePropertyChange(PROP__OBJECT_ID, old, this.object_id);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getClass_id() {
        return class_id;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  class_id  DOCUMENT ME!
     */
    public void setClass_id(final Integer class_id) {
        final Integer old = this.class_id;
        this.class_id = class_id;
        this.propertyChangeSupport.firePropertyChange(PROP__CLASS_ID, old, this.class_id);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getClassId() {
        return getClass_id();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  classId  DOCUMENT ME!
     */
    public void setClassId(final Integer classId) {
        setClass_id(classId);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getObjectId() {
        return getObject_id();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  objectId  DOCUMENT ME!
     */
    public void setObjectId(final Integer objectId) {
        setObject_id(objectId);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getUserString() {
        return getUser_string();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  userString  DOCUMENT ME!
     */
    public void setUserString(final String userString) {
        setUser_string(userString);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getAdditionalInfo() {
        return getAdditional_info();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  additionalInfo  DOCUMENT ME!
     */
    public void setAdditionalInfo(final String additionalInfo) {
        setAdditional_info(additionalInfo);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Date getTimestamp() {
        return getLock_timestamp();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  timestamp  DOCUMENT ME!
     */
    public void setTimestamp(final Date timestamp) {
        setLock_timestamp(timestamp);
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
        if (!(object instanceof SperreCustomBean)) {
            return false;
        }
        final SperreCustomBean other = (SperreCustomBean)object;
        if (((this.getId() == null) && (other.getId() != null))
                    || ((this.getId() != null) && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }
}