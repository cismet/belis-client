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
package de.cismet.commons.server.entity;

import de.cismet.commons.server.interfaces.DocumentContainer;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public abstract class WorkbenchEntity extends BaseEntity implements Comparable<WorkbenchEntity>, DocumentContainer {

    //~ Static fields/initializers ---------------------------------------------

    protected static final String PROP__IS_DELETED = "is_deleted";

    //~ Instance fields --------------------------------------------------------

    private Boolean is_deleted;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new WorkbenchEntity object.
     */
    public WorkbenchEntity() {
        addPropertyName(PROP__IS_DELETED);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public abstract String getHumanReadablePosition();
    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public abstract String getKeyString();

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Boolean getIs_deleted() {
        return is_deleted;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  is_deleted  DOCUMENT ME!
     */
    public void setIs_deleted(final Boolean is_deleted) {
        final Boolean old = this.is_deleted;
        this.is_deleted = is_deleted;
        this.propertyChangeSupport.firePropertyChange(PROP__IS_DELETED, old, this.is_deleted);
    }
}
