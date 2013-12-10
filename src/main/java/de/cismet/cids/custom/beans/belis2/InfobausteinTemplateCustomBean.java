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

import java.util.Collection;

import de.cismet.belis.commons.constants.BelisMetaClassConstants;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class InfobausteinTemplateCustomBean extends BaseEntity {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            InfobausteinTemplateCustomBean.class);

    public static final String TABLE = BelisMetaClassConstants.MC_INFOBAUSTEIN_TEMPLATE;

    public static final String PROP__BEZEICHNUNG = "bezeichnung";
    public static final String PROP__SCHLUESSEL = "schluessel";
    public static final String PROP__AR_BAUSTEINE = "ar_bausteine";

    private static final String[] PROPERTY_NAMES = new String[] {
            PROP__ID,
            PROP__BEZEICHNUNG,
            PROP__SCHLUESSEL,
            PROP__AR_BAUSTEINE
        };

    //~ Instance fields --------------------------------------------------------

    private String bezeichnung;
    private String schluessel;
    private Collection<InfobausteinCustomBean> ar_bausteine;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new VeranlassungCustomBean object.
     */
    public InfobausteinTemplateCustomBean() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static InfobausteinTemplateCustomBean createNew() {
        return (InfobausteinTemplateCustomBean)createNew(TABLE);
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
        if (other instanceof InfobausteinTemplateCustomBean) {
            final InfobausteinTemplateCustomBean anEntity = (InfobausteinTemplateCustomBean)other;
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
    public Collection<InfobausteinCustomBean> getAr_bausteine() {
        return ar_bausteine;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  ar_bausteine  DOCUMENT ME!
     */
    public void setAr_bausteine(final Collection<InfobausteinCustomBean> ar_bausteine) {
        final Collection<InfobausteinCustomBean> old = this.ar_bausteine;
        this.ar_bausteine = ar_bausteine;
        this.propertyChangeSupport.firePropertyChange(PROP__AR_BAUSTEINE, old, this.ar_bausteine);
    }
}
