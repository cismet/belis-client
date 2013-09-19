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
package de.cismet.cids.custom.beans.belis;

import de.cismet.belis.commons.constants.BelisMetaClassConstants;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class InfobausteinCustomBean extends BaseEntity {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            InfobausteinCustomBean.class);

    public static final String TABLE = BelisMetaClassConstants.MC_INFOBAUSTEIN;

    public static final String PROP__BEZEICHNUNG = "bezeichnung";
    public static final String PROP__SCHLUESSEL = "schluessel";
    public static final String PROP__PFLICHTFELD = "pflichtfeld";
    public static final String PROP__WERT = "wert";

    private static final String[] PROPERTY_NAMES = new String[] {
            PROP__ID,
            PROP__BEZEICHNUNG,
            PROP__SCHLUESSEL,
            PROP__PFLICHTFELD,
            PROP__WERT
        };

    //~ Instance fields --------------------------------------------------------

    private String bezeichnung;
    private String schluessel;
    private Boolean pflichtfeld;
    private String wert;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new VeranlassungCustomBean object.
     */
    public InfobausteinCustomBean() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static InfobausteinCustomBean createNew() {
        return (InfobausteinCustomBean)createNew(TABLE);
    }

    @Override
    public String[] getPropertyNames() {
        return PROPERTY_NAMES;
    }

    @Override
    public int hashCode() {
        if (this.getId() == null) {
            return System.identityHashCode(this);
        }
        return this.getId().hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof InfobausteinCustomBean) {
            final InfobausteinCustomBean anEntity = (InfobausteinCustomBean)other;
            if (this == other) {
                return true;
            } else if (!this.getClass().isAssignableFrom(other.getClass())) {
                return false;
            } else if ((this.getId() == null) || (anEntity.getId() == null)) {
                return false;
            } else {
                return this.getId().equals(anEntity.getId());
            }
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "de.cismet.belisEE.entity.Veranlassungsart[id=" + getId() + "]";
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

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Boolean getPflichtfeld() {
        return pflichtfeld;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  pflichtfeld  DOCUMENT ME!
     */
    public void setPflichtfeld(final Boolean pflichtfeld) {
        final Boolean old = this.pflichtfeld;
        this.pflichtfeld = pflichtfeld;
        this.propertyChangeSupport.firePropertyChange(PROP__PFLICHTFELD, old, this.pflichtfeld);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getWert() {
        return wert;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  wert  DOCUMENT ME!
     */
    public void setWert(final String wert) {
        final String old = this.wert;
        this.wert = wert;
        this.propertyChangeSupport.firePropertyChange(PROP__WERT, old, this.wert);
    }
}
