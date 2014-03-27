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
public class SperreEntityCustomBean extends BaseEntity {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(SperreEntityCustomBean.class);

    public static final String TABLE = "sperre_entity";

    public static final String PROP__OBJECT_ID = "object_id";
    public static final String PROP__CLASS_ID = "class_id";
    public static final String PROP__FK_SPERRE = "fk_sperre";

    private static final String[] PROPERTY_NAMES = new String[] {
            PROP__ID,
            PROP__OBJECT_ID,
            PROP__CLASS_ID,
            PROP__FK_SPERRE
        };

    //~ Instance fields --------------------------------------------------------

    private Integer object_id;
    private Integer class_id;
    private SperreCustomBean fk_sperre;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbzweigdoseCustomBean object.
     */
    public SperreEntityCustomBean() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static SperreEntityCustomBean createNew() {
        return (SperreEntityCustomBean)createNew(TABLE);
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
    public SperreCustomBean getFk_sperre() {
        return fk_sperre;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_sperre  DOCUMENT ME!
     */
    public void setFk_sperre(final SperreCustomBean fk_sperre) {
        final SperreCustomBean old = this.fk_sperre;
        this.fk_sperre = fk_sperre;
        this.propertyChangeSupport.firePropertyChange(PROP__FK_SPERRE, old, this.fk_sperre);
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
}
