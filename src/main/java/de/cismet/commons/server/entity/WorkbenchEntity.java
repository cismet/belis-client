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

    private Boolean isDeleted;

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
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isDeleted  DOCUMENT ME!
     */
    public void setIsDeleted(final Boolean isDeleted) {
        final Boolean old = this.isDeleted;
        this.isDeleted = isDeleted;
        this.propertyChangeSupport.firePropertyChange(PROP__IS_DELETED, old, this.isDeleted);
    }
}
