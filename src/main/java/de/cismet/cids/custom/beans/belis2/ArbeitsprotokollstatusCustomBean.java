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

import de.cismet.belis.commons.constants.BelisMetaClassConstants;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class ArbeitsprotokollstatusCustomBean extends BaseEntity {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            ArbeitsprotokollstatusCustomBean.class);

    public static final String TABLE = BelisMetaClassConstants.MC_ARBEITSPROTOKOLLSTATUS;

    public static final String PROP__BEZEICHNUNG = "bezeichnung";
    public static final String PROP__SCHLUESSEL = "schluessel";

    //~ Instance fields --------------------------------------------------------

    private String bezeichnung;
    private String schluessel;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new VeranlassungCustomBean object.
     */
    public ArbeitsprotokollstatusCustomBean() {
        addPropertyNames(
            new String[] {
                PROP__BEZEICHNUNG,
                PROP__SCHLUESSEL
            });
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static ArbeitsprotokollstatusCustomBean createNew() {
        return (ArbeitsprotokollstatusCustomBean)createNew(TABLE);
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

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getSchluessel() {
        return schluessel;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  schluessel  DOCUMENT ME!
     */
    public void setSchluessel(final String schluessel) {
        final String old = this.schluessel;
        this.schluessel = schluessel;
        this.propertyChangeSupport.firePropertyChange(PROP__SCHLUESSEL, old, this.schluessel);
    }
}
