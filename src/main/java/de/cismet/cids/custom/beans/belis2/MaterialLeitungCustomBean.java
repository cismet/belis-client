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
public class MaterialLeitungCustomBean extends BaseEntity {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            MaterialLeitungCustomBean.class);

    public static final String TABLE = "material_leitung";

    public static final String PROP__BEZEICHNUNG = "bezeichnung";

    //~ Instance fields --------------------------------------------------------

    private String bezeichnung;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbzweigdoseCustomBean object.
     */
    public MaterialLeitungCustomBean() {
        addPropertyNames(
            new String[] { PROP__BEZEICHNUNG });
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static MaterialLeitungCustomBean createNew() {
        return (MaterialLeitungCustomBean)createNew(TABLE);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getBezeichnung() {
        return bezeichnung;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  bezeichnung  DOCUMENT ME!
     */
    public void setBezeichnung(final String bezeichnung) {
        final String old = this.bezeichnung;
        this.bezeichnung = bezeichnung;
        this.propertyChangeSupport.firePropertyChange(PROP__BEZEICHNUNG, old, this.bezeichnung);
    }
}
