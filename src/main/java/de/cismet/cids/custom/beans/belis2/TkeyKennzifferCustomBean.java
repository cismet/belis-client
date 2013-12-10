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
public class TkeyKennzifferCustomBean extends BaseEntity {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            TkeyKennzifferCustomBean.class);

    public static final String TABLE = "tkey_kennziffer";

    public static final String PROP__BESCHREIBUNG = "Beschreibung";
    public static final String PROP__KENNZIFFER = "Kennziffer";

    private static final String[] PROPERTY_NAMES = new String[] { PROP__ID, PROP__BESCHREIBUNG, PROP__KENNZIFFER };

    //~ Instance fields --------------------------------------------------------

    private String beschreibung;
    private Integer kennziffer;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbzweigdoseCustomBean object.
     */
    public TkeyKennzifferCustomBean() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static TkeyKennzifferCustomBean createNew() {
        return (TkeyKennzifferCustomBean)createNew(TABLE);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   kennziffer  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static TkeyKennzifferCustomBean createNew(final Integer kennziffer) {
        final TkeyKennzifferCustomBean newBean = createNew();
        newBean.setKennziffer(kennziffer);
        return newBean;
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
    public Integer getKennziffer() {
        return kennziffer;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  kennziffer  DOCUMENT ME!
     */
    public void setKennziffer(final Integer kennziffer) {
        final Integer old = this.kennziffer;
        this.kennziffer = kennziffer;
        this.propertyChangeSupport.firePropertyChange(PROP__KENNZIFFER, old, this.kennziffer);
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
    public int hashCode() {
        if (this.getKennziffer() == null) {
            return System.identityHashCode(this);
        }
        return this.getKennziffer().hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof TkeyKennzifferCustomBean) {
            final TkeyKennzifferCustomBean anEntity = (TkeyKennzifferCustomBean)other;
            if (this == other) {
                return true;
            } else if ((other == null) || (!this.getClass().isAssignableFrom(other.getClass()))) {
                return false;
            } else if ((this.getKennziffer() == null) || (anEntity.getKennziffer() == null)) {
                return false;
            } else {
                return this.getKennziffer().equals(anEntity.getKennziffer());
            }
        } else {
            return false;
        }
    }

    @Override
    public String getKeyString() {
        if ((getKennziffer() != null) && (getBeschreibung() != null)) {
            return getKennziffer() + " - " + getBeschreibung();
        } else if (getKennziffer() != null) {
            return getKennziffer() + " - Keine Beschreibung vorhanden.";
        } else if (getBeschreibung() != null) {
            return "Keine ID - " + getBeschreibung();
        } else {
            return "";
        }
    }
}
