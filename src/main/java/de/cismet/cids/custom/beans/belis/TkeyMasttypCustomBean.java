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

import de.cismet.belis.broker.CidsBroker;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class TkeyMasttypCustomBean extends BaseEntity {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(TkeyMasttypCustomBean.class);

    public static final String TABLE = "tkey_masttyp";

    private static final String PROP__MASTTYP = "masttyp";
    private static final String PROP__LPH = "lph";
    private static final String PROP__HERSTELLER = "hersteller";
    private static final String PROP__WANDSTAERKE = "wandstaerke";
//    private static final String PROP__FOTO = "foto";

    private static final String[] PROPERTY_NAMES = new String[] {
            PROP__ID,
            PROP__MASTTYP,
            PROP__LPH,
            PROP__HERSTELLER,
            PROP__WANDSTAERKE /*, PROP__FOTO*/
        };

    //~ Instance fields --------------------------------------------------------

    private String masttyp;
    private Integer lph;
    private String hersteller;
    private Integer wandstaerke;
//    private FotoCustomBean foto;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbzweigdoseCustomBean object.
     */
    public TkeyMasttypCustomBean() {
    }

    /**
     * Creates a new TkeyMasttypCustomBean object.
     *
     * @param  masttyp  DOCUMENT ME!
     */
    public TkeyMasttypCustomBean(final String masttyp) {
        setMasttyp(masttyp);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static TkeyMasttypCustomBean createNew() {
        return (TkeyMasttypCustomBean)createNew(TABLE);
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
    public String getMasttyp() {
        return masttyp;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  masttyp  DOCUMENT ME!
     */
    public void setMasttyp(final String masttyp) {
        final String old = this.masttyp;
        this.masttyp = masttyp;
        this.propertyChangeSupport.firePropertyChange(PROP__MASTTYP, old, this.masttyp);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getLph() {
        return lph;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  lph  DOCUMENT ME!
     */
    public void setLph(final Integer lph) {
        final Integer old = this.lph;
        this.lph = lph;
        this.propertyChangeSupport.firePropertyChange(PROP__LPH, old, this.lph);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getHersteller() {
        return hersteller;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  hersteller  DOCUMENT ME!
     */
    public void setHersteller(final String hersteller) {
        final String old = this.hersteller;
        this.hersteller = hersteller;
        this.propertyChangeSupport.firePropertyChange(PROP__HERSTELLER, old, this.hersteller);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getWandstaerke() {
        return wandstaerke;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  wandstaerke  DOCUMENT ME!
     */
    public void setWandstaerke(final Integer wandstaerke) {
        final Integer old = this.wandstaerke;
        this.wandstaerke = wandstaerke;
        this.propertyChangeSupport.firePropertyChange(PROP__WANDSTAERKE, old, this.wandstaerke);
    }

    @Override
    public int hashCode() {
        if (this.getMasttyp() == null) {
            return System.identityHashCode(this);
        }
        return this.getMasttyp().hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof TkeyMasttypCustomBean) {
            final TkeyMasttypCustomBean anEntity = (TkeyMasttypCustomBean)other;
            if (this == other) {
                return true;
            } else if ((other == null) || (!this.getClass().isAssignableFrom(other.getClass()))) {
                return false;
            } else if ((this.getMasttyp() == null) || (anEntity.getMasttyp() == null)) {
                return false;
            } else {
                return this.getMasttyp().equals(anEntity.getMasttyp());
            }
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "de.cismet.belis.entity.Masttyp[masttyp=" + masttyp + "]";
    }

    @Override
    public String getKeyString() {
        if (getMasttyp() != null) {
            return getMasttyp();
        } else {
            return "";
        }
    }
}
