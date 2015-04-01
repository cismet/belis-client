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
public class TkeyDoppelkommandoCustomBean extends BaseEntity {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            TkeyDoppelkommandoCustomBean.class);

    public static final String TABLE = "tkey_doppelkommando";

    public static final String PROP__BESCHREIBUNG = "beschreibung";
    public static final String PROP__PK = "pk";

    //~ Instance fields --------------------------------------------------------

    private String beschreibung;
    private String pk;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbzweigdoseCustomBean object.
     */
    public TkeyDoppelkommandoCustomBean() {
        addPropertyNames(
            new String[] {
                PROP__BESCHREIBUNG,
                PROP__PK
            });
    }

    /**
     * Creates a new TkeyDoppelkommandoCustomBean object.
     *
     * @param  pk  DOCUMENT ME!
     */
    public TkeyDoppelkommandoCustomBean(final String pk) {
        setPk(pk);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static TkeyDoppelkommandoCustomBean createNew() {
        return (TkeyDoppelkommandoCustomBean)createNew(TABLE);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getPk() {
        return pk;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  pk  DOCUMENT ME!
     */
    public void setPk(final String pk) {
        final String old = this.pk;
        this.pk = pk;
        this.propertyChangeSupport.firePropertyChange(PROP__PK, old, this.pk);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getBeschreibung() {
        return beschreibung;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  beschreibung  DOCUMENT ME!
     */
    public void setBeschreibung(final String beschreibung) {
        final String old = this.beschreibung;
        this.beschreibung = beschreibung;
        this.propertyChangeSupport.firePropertyChange(PROP__BESCHREIBUNG, old, this.beschreibung);
    }

    @Override
    public String toString() {
        if ((getPk() != null) && (getBeschreibung() != null)) {
            return getPk() + " - " + getBeschreibung();
        } else if (getPk() != null) {
            return getPk() + " - Keine Beschreibung vorhanden.";
        } else if (getBeschreibung() != null) {
            return "Keine PK - " + getBeschreibung();
        } else {
            return "";
        }
    }
}
