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

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public class BaseEntity extends CidsBean {

    //~ Static fields/initializers ---------------------------------------------

    protected static final String PROP__ID = "id";

    //~ Instance fields --------------------------------------------------------

    private boolean wasModified = false;
    private Integer id;

    //~ Methods ----------------------------------------------------------------

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

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean isWasModified() {
        return wasModified;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  wasModified  DOCUMENT ME!
     */
    public void setWasModified(final boolean wasModified) {
        this.wasModified = wasModified;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getKeyString() {
        return "";
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getCompareCriteriaString() {
        return getKeyString();
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getHumanReadablePosition() {
        return "";
    }
}
