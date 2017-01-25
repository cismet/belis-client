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
public class ArbeitsprotokollaktionCustomBean extends BaseEntity {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            ArbeitsprotokollaktionCustomBean.class);

    public static final String TABLE = BelisMetaClassConstants.MC_ARBEITSPROTOKOLLAKTION;

    public static final String PROP__FK_PROTOKOLL = "fk_protokoll";
    public static final String PROP__AENDERUNG = "aenderung";
    public static final String PROP__ALT = "alt";
    public static final String PROP__NEU = "neu";

    //~ Instance fields --------------------------------------------------------

    private ArbeitsprotokollCustomBean fk_protokoll;
    private String aenderung;
    private String alt;
    private String neu;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ArbeitsprotokollCustomBean object.
     */
    public ArbeitsprotokollaktionCustomBean() {
        addPropertyNames(
            new String[] {
                PROP__FK_PROTOKOLL,
                PROP__AENDERUNG,
                PROP__ALT,
                PROP__NEU
            });
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static ArbeitsprotokollaktionCustomBean createNew() {
        return (ArbeitsprotokollaktionCustomBean)createNew(TABLE);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public ArbeitsprotokollCustomBean getFk_protokoll() {
        return fk_protokoll;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_protokoll  fk_mauerlasche DOCUMENT ME!
     */
    public void setFk_protokoll(final ArbeitsprotokollCustomBean fk_protokoll) {
        final ArbeitsprotokollCustomBean old = this.fk_protokoll;
        this.fk_protokoll = fk_protokoll;
        this.propertyChangeSupport.firePropertyChange(PROP__FK_PROTOKOLL, old, this.fk_protokoll);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getAenderung() {
        return aenderung;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  aenderung  material DOCUMENT ME!
     */
    public void setAenderung(final String aenderung) {
        final String old = this.aenderung;
        this.aenderung = aenderung;
        this.propertyChangeSupport.firePropertyChange(PROP__AENDERUNG, old, this.aenderung);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getAlt() {
        return alt;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  alt  fk_leuchte DOCUMENT ME!
     */
    public void setAlt(final String alt) {
        final String old = this.alt;
        this.alt = alt;
        this.propertyChangeSupport.firePropertyChange(PROP__ALT, old, this.alt);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getNeu() {
        return neu;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  neu  monteur DOCUMENT ME!
     */
    public void setNeu(final String neu) {
        final String old = this.neu;
        this.neu = neu;
        this.propertyChangeSupport.firePropertyChange(PROP__NEU, old, this.neu);
    }
}
