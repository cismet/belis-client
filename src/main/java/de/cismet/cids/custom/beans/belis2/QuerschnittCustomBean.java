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
public class QuerschnittCustomBean extends BaseEntity {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(QuerschnittCustomBean.class);

    public static final String TABLE = "querschnitt";

    public static final String PROP__GROESSE = "groesse";

    //~ Instance fields --------------------------------------------------------

    private Double groesse;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbzweigdoseCustomBean object.
     */
    public QuerschnittCustomBean() {
        addPropertyNames(
            new String[] { PROP__GROESSE });
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
}
