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
public class TeamCustomBean extends BaseEntity {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(TeamCustomBean.class);

    public static final String TABLE = "Team";

    public static final String PROP__NAME = "name";

    //~ Instance fields --------------------------------------------------------

    private String name;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new TeamCustomBean object.
     */
    public TeamCustomBean() {
        addPropertyNames(
            new String[] { PROP__NAME });
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static TeamCustomBean createNew() {
        return (TeamCustomBean)createNew(TABLE);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getName() {
        return name;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  name  DOCUMENT ME!
     */
    public void setName(final String name) {
        final String old = this.name;
        this.name = name;
        this.propertyChangeSupport.firePropertyChange(PROP__NAME, old, this.name);
    }
}
