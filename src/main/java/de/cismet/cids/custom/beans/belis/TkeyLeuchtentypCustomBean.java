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

import java.util.Date;

import de.cismet.belis.broker.CidsBroker;

import de.cismet.belisEE.entity.Leuchtentyp;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class TkeyLeuchtentypCustomBean extends BaseEntity implements Leuchtentyp {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            TkeyLeuchtentypCustomBean.class);

    public static final String TABLE = "tkey_leuchtentyp";

    private static final String PROP__LEUCHTENTYP = "leuchtentyp";
    private static final String PROP__BESTUECKUNG = "bestueckung";
    private static final String PROP__LEISTUNG = "leistung";
    private static final String PROP__LEISTUNG_BRUTTO = "leistung_brutto";
    private static final String PROP__FABRIKAT = "fabrikat";
    private static final String PROP__LAMPE = "lampe";
    private static final String PROP__EINBAUDATUM = "einbaudatum";
    private static final String PROP__LEISTUNG2STUFE = "leistung2stufe";

    private static final String[] PROPERTY_NAMES = new String[] {
            PROP__ID,
            PROP__LEUCHTENTYP,
            PROP__BESTUECKUNG,
            PROP__LEISTUNG,
            PROP__LEISTUNG_BRUTTO,
            PROP__FABRIKAT,
            PROP__LAMPE,
            PROP__EINBAUDATUM,
            PROP__LEISTUNG2STUFE
        };

    //~ Instance fields --------------------------------------------------------

    private String leuchtentyp;
    private Double bestueckung;
    private Double leistung;
    private Double leistung_brutto;
    private String fabrikat;
    private String lampe;
    private Date einbaudatum;
    private Double leistung2stufe;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbzweigdoseCustomBean object.
     */
    public TkeyLeuchtentypCustomBean() {
    }

    /**
     * Creates a new TkeyLeuchtentypCustomBean object.
     *
     * @param  leuchtentyp  DOCUMENT ME!
     */
    public TkeyLeuchtentypCustomBean(final String leuchtentyp) {
        final String old = this.leuchtentyp;
        this.leuchtentyp = leuchtentyp;
        this.propertyChangeSupport.firePropertyChange(PROP__LEUCHTENTYP, old, this.leuchtentyp);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static TkeyLeuchtentypCustomBean createNew() {
        try {
            return (TkeyLeuchtentypCustomBean)CidsBean.createNewCidsBeanFromTableName(CidsBroker.BELIS_DOMAIN, TABLE);
        } catch (Exception ex) {
            LOG.error("error creating " + TABLE + " bean", ex);
            return null;
        }
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
    public Double getLeistung_brutto() {
        return leistung_brutto;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  Leistung_brutto  DOCUMENT ME!
     */
    public void setLeistung_brutto(final Double Leistung_brutto) {
        final Double old = this.leistung_brutto;
        this.leistung_brutto = Leistung_brutto;
        this.propertyChangeSupport.firePropertyChange(PROP__LEISTUNG_BRUTTO, old, this.leistung_brutto);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Date getEinbaudatum() {
        return einbaudatum;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  einbaudatum  DOCUMENT ME!
     */
    public void setEinbaudatum(final Date einbaudatum) {
        final Date old = this.einbaudatum;
        this.einbaudatum = einbaudatum;
        this.propertyChangeSupport.firePropertyChange(PROP__EINBAUDATUM, old, this.einbaudatum);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Double getLeistung2stufe() {
        return leistung2stufe;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  leistung2stufe  DOCUMENT ME!
     */
    public void setLeistung2stufe(final Double leistung2stufe) {
        final Double old = this.leistung2stufe;
        this.leistung2stufe = leistung2stufe;
        this.propertyChangeSupport.firePropertyChange(PROP__LEISTUNG2STUFE, old, this.leistung2stufe);
    }

    @Override
    public String getLeuchtentyp() {
        return leuchtentyp;
    }

    @Override
    public void setLeuchtentyp(final String leuchtentyp) {
        final String old = this.leuchtentyp;
        this.leuchtentyp = leuchtentyp;
        this.propertyChangeSupport.firePropertyChange(PROP__LEUCHTENTYP, old, this.leuchtentyp);
    }

    @Override
    public String getFabrikat() {
        return fabrikat;
    }

    @Override
    public void setFabrikat(final String fabrikat) {
        final String old = this.fabrikat;
        this.fabrikat = fabrikat;
        this.propertyChangeSupport.firePropertyChange(PROP__FABRIKAT, old, this.fabrikat);
    }

    @Override
    public Double getBestueckung() {
        return bestueckung;
    }

    @Override
    public void setBestueckung(final Double bestueckung) {
        final Double old = this.bestueckung;
        this.bestueckung = bestueckung;
        this.propertyChangeSupport.firePropertyChange(PROP__BESTUECKUNG, old, this.bestueckung);
    }

    @Override
    public Double getLeistung() {
        return leistung;
    }

    @Override
    public void setLeistung(final Double leistung) {
        final Double old = this.leistung;
        this.leistung = leistung;
        this.propertyChangeSupport.firePropertyChange(PROP__LEISTUNG, old, this.leistung);
    }

    @Override
    public String getLampe() {
        return lampe;
    }

    @Override
    public void setLampe(final String lampe) {
        final String old = this.lampe;
        this.lampe = lampe;
        this.propertyChangeSupport.firePropertyChange(PROP__LAMPE, old, this.lampe);
    }

    @Override
    public Double getLeistungBrutto() {
        return getLeistung_brutto();
    }

    @Override
    public void setLeistungBrutto(final Double leistungBrutto) {
        setLeistung_brutto(leistungBrutto);
    }

    @Override
    public int hashCode() {
        if (this.getLeuchtentyp() == null) {
            return System.identityHashCode(this);
        }
        return this.getLeuchtentyp().hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof TkeyLeuchtentypCustomBean) {
            final TkeyLeuchtentypCustomBean anEntity = (TkeyLeuchtentypCustomBean)other;
            if (this == other) {
                return true;
            } else if ((other == null) || (!this.getClass().isAssignableFrom(other.getClass()))) {
                return false;
            } else if ((this.getLeuchtentyp() == null) || (anEntity.getLeuchtentyp() == null)) {
                return false;
            } else {
                return this.getLeuchtentyp().equals(anEntity.getLeuchtentyp());
            }
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "de.cismet.belis.entity.Leuchtentyp[leuchtentyp=" + leuchtentyp + "]";
    }

    @Override
    public String getKeyString() {
        if (getLeuchtentyp() != null) {
            return getLeuchtentyp();
        } else {
            return "";
        }
    }
}
