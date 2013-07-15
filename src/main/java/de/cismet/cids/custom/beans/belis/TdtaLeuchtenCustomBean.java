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

import java.beans.PropertyChangeEvent;

import java.util.Collection;
import java.util.Date;

import de.cismet.belis.broker.CidsBroker;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class TdtaLeuchtenCustomBean extends BaseEntity {

    //~ Static fields/initializers ---------------------------------------------

    public static final TkeyUnterhLeuchteCustomBean DEFAULT_UNTERHALT;
    public static final TkeyDoppelkommandoCustomBean DEFAULT_DOPPELKOMMANDO;

    public static final String PROP_STRASSENSCHLUESSEL = "Leuchte.strassenschluessel";
    public static final String PROP_KENNZIFFER = "Leuchte.Kennziffer";
    public static final String PROP_LEUCHTENNUMMER = "Leuchte.leuchtennummer";

    static {
        DEFAULT_UNTERHALT = TkeyUnterhLeuchteCustomBean.createNew();
        DEFAULT_UNTERHALT.setPk((Integer)0);
        DEFAULT_UNTERHALT.setUnterhaltspflichtigeLeuchte("Öffentl. Beleuchtung");

        DEFAULT_DOPPELKOMMANDO = TkeyDoppelkommandoCustomBean.createNew();
        DEFAULT_DOPPELKOMMANDO.setPk("12");
        DEFAULT_DOPPELKOMMANDO.setBeschreibung("Ganznachtbetrieb (früher Nacht)");
    }

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(TdtaLeuchtenCustomBean.class);

    public static final String TABLE = "tdta_leuchten";

    private static final String PROP__PLZ = "plz";
    private static final String PROP__FK_STRASSENSCHLUESSEL = "fk_strassenschluessel";
    private static final String PROP__FK_ENERGIELIEFERANT = "fk_energielieferant";
    private static final String PROP__RUNDSTEUEREMPFAENGER = "rundsteuerempfaenger";
    private static final String PROP__FK_LEUCHTTYP = "fk_leuchttyp";
    private static final String PROP__FK_UNTERHALTSPFLICHT_LEUCHTE = "fk_unterhaltspflicht_leuchte";
    private static final String PROP__ZAEHLER = "zaehler";
    private static final String PROP__FK_DK1 = "fk_dk1";
    private static final String PROP__FK_DK2 = "fk_dk2";
    private static final String PROP__INBETRIEBNAHME_LEUCHTE = "inbetriebnahme_leuchte";
    private static final String PROP__FK_STANDORT = "fk_standort";
    private static final String PROP__LFD_NUMMER = "lfd_nummer";
    private static final String PROP__ANZAHL_2DK = "anzahl_2dk";
    private static final String PROP__FK_KENNZIFFER = "fk_kennziffer";
    private static final String PROP__LEUCHTENNUMMER = "leuchtennummer";
    private static final String PROP__MONTAGEFIRMA_LEUCHTE = "montagefirma_leuchte";
    private static final String PROP__SCHALTSTELLE = "schaltstelle";
    private static final String PROP__ANZAHL_1DK = "anzahl_1dk";
    private static final String PROP__STADTBEZIRK = "stadtbezirk";
    private static final String PROP__BEMERKUNGEN = "bemerkungen";
    private static final String PROP__DOKUMENTE = "dokumente";

    private static final String[] PROPERTY_NAMES = new String[] {
            PROP__ID,
            PROP__PLZ,
            PROP__FK_STRASSENSCHLUESSEL,
            PROP__FK_ENERGIELIEFERANT,
            PROP__RUNDSTEUEREMPFAENGER,
            PROP__FK_LEUCHTTYP,
            PROP__FK_UNTERHALTSPFLICHT_LEUCHTE,
            PROP__ZAEHLER,
            PROP__FK_DK1,
            PROP__FK_DK2,
            PROP__INBETRIEBNAHME_LEUCHTE,
            PROP__FK_STANDORT,
            PROP__LFD_NUMMER,
            PROP__ANZAHL_2DK,
            PROP__FK_KENNZIFFER,
            PROP__LEUCHTENNUMMER,
            PROP__MONTAGEFIRMA_LEUCHTE,
            PROP__SCHALTSTELLE,
            PROP__ANZAHL_1DK,
            PROP__STADTBEZIRK,
            PROP__BEMERKUNGEN,
            PROP__DOKUMENTE
        };

    //~ Instance fields --------------------------------------------------------

    private String plz;
    private TkeyStrassenschluesselCustomBean fk_strassenschluessel;
    private TkeyEnergielieferantCustomBean fk_energielieferant;
    private String rundsteuerempfaenger;
    private TkeyLeuchtentypCustomBean fk_leuchttyp;
    private TkeyUnterhLeuchteCustomBean fk_unterhaltspflicht_leuchte;
    private Boolean zaehler;
    private TkeyDoppelkommandoCustomBean fk_dk1;
    private TkeyDoppelkommandoCustomBean fk_dk2;
    private Date inbetriebnahme_leuchte;
    private TdtaStandortMastCustomBean fk_standort;
    private Integer lfd_nummer;
    private TkeyKennzifferCustomBean fk_kennziffer;
    private Integer leuchtennummer;
    private String montagefirma_leuchte;
    private String schaltstelle;
    private Integer anzahl_1dk;
    private Integer anzahl_2dk;
    private TkeyBezirkCustomBean fk_stadtbezirk;
    private String bemerkungen;
    private Collection<DmsUrlCustomBean> dokumente;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbzweigdoseCustomBean object.
     */
    public TdtaLeuchtenCustomBean() {
    }

    /**
     * Creates a new TdtaLeuchteCustomBean object.
     *
     * @param  id  DOCUMENT ME!
     */
    public TdtaLeuchtenCustomBean(final Integer id) {
        setId(id);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static TdtaLeuchtenCustomBean createNew() {
        try {
            return (TdtaLeuchtenCustomBean)CidsBean.createNewCidsBeanFromTableName(CidsBroker.BELIS_DOMAIN, TABLE);
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
    public Collection<DmsUrlCustomBean> getDokumente() {
        return dokumente;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  dokumente  DOCUMENT ME!
     */
    public void setDokumente(final Collection<DmsUrlCustomBean> dokumente) {
        final Collection<DmsUrlCustomBean> old = this.dokumente;
        this.dokumente = dokumente;
        this.propertyChangeSupport.firePropertyChange(PROP__DOKUMENTE, old, this.dokumente);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public TkeyBezirkCustomBean getStadtbezirk() {
        return getFk_stadtbezirk();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  stadtbezirk  DOCUMENT ME!
     */
    public void setStadtbezirk(final TkeyBezirkCustomBean stadtbezirk) {
        setFk_stadtbezirk(stadtbezirk);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public TkeyBezirkCustomBean getFk_stadtbezirk() {
        return fk_stadtbezirk;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  stadtbezirk  DOCUMENT ME!
     */
    public void setFk_stadtbezirk(final TkeyBezirkCustomBean stadtbezirk) {
        final TkeyBezirkCustomBean old = this.fk_stadtbezirk;
        this.fk_stadtbezirk = stadtbezirk;
        this.propertyChangeSupport.firePropertyChange(PROP__STADTBEZIRK, old, this.fk_stadtbezirk);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getPlz() {
        return plz;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  plz  DOCUMENT ME!
     */
    public void setPlz(final String plz) {
        final String old = this.plz;
        this.plz = plz;
        this.propertyChangeSupport.firePropertyChange(PROP__PLZ, old, this.plz);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public TkeyStrassenschluesselCustomBean getStrassenschluessel() {
        return getFk_strassenschluessel();
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public TkeyStrassenschluesselCustomBean getFk_strassenschluessel() {
        return fk_strassenschluessel;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_strassenschluessel  DOCUMENT ME!
     */
    public void setStrassenschluessel(final TkeyStrassenschluesselCustomBean fk_strassenschluessel) {
        setFk_strassenschluessel(fk_strassenschluessel);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_strassenschluessel  DOCUMENT ME!
     */
    public void setFk_strassenschluessel(final TkeyStrassenschluesselCustomBean fk_strassenschluessel) {
        final TkeyStrassenschluesselCustomBean old = this.fk_strassenschluessel;
        this.fk_strassenschluessel = fk_strassenschluessel;
        this.propertyChangeSupport.firePropertyChange(PROP__FK_STRASSENSCHLUESSEL, old, this.fk_strassenschluessel);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public TkeyKennzifferCustomBean getKennziffer() {
        return getFk_kennziffer();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_kennziffer  DOCUMENT ME!
     */
    public void setKennziffer(final TkeyKennzifferCustomBean fk_kennziffer) {
        setFk_kennziffer(fk_kennziffer);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public TkeyKennzifferCustomBean getFk_kennziffer() {
        return fk_kennziffer;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_kennziffer  DOCUMENT ME!
     */
    public void setFk_kennziffer(final TkeyKennzifferCustomBean fk_kennziffer) {
        final TkeyKennzifferCustomBean old = this.fk_kennziffer;
        this.fk_kennziffer = fk_kennziffer;
        this.propertyChangeSupport.firePropertyChange(PROP__FK_KENNZIFFER, old, this.fk_kennziffer);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getLeuchtennummer() {
        return leuchtennummer;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  leuchtennummer  DOCUMENT ME!
     */
    public void setLeuchtennummer(final Integer leuchtennummer) {
        final Integer old = this.leuchtennummer;
        this.leuchtennummer = leuchtennummer;
        this.propertyChangeSupport.firePropertyChange(PROP__LEUCHTENNUMMER, old, this.leuchtennummer);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getSchaltstelle() {
        return schaltstelle;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  schaltstelle  DOCUMENT ME!
     */
    public void setSchaltstelle(final String schaltstelle) {
        final String old = this.schaltstelle;
        this.schaltstelle = schaltstelle;
        this.propertyChangeSupport.firePropertyChange(PROP__SCHALTSTELLE, old, this.schaltstelle);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getRundsteuerempfaenger() {
        return rundsteuerempfaenger;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  rundsteuerempfaenger  DOCUMENT ME!
     */
    public void setRundsteuerempfaenger(final String rundsteuerempfaenger) {
        final String old = this.rundsteuerempfaenger;
        this.rundsteuerempfaenger = rundsteuerempfaenger;
        this.propertyChangeSupport.firePropertyChange(PROP__RUNDSTEUEREMPFAENGER, old, this.rundsteuerempfaenger);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Boolean getZaehler() {
        return zaehler;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  zaehler  DOCUMENT ME!
     */
    public void setZaehler(final Boolean zaehler) {
        final Boolean old = this.zaehler;
        this.zaehler = zaehler;
        this.propertyChangeSupport.firePropertyChange(PROP__ZAEHLER, old, this.zaehler);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public TkeyDoppelkommandoCustomBean getDk1() {
        return getFk_dk1();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  dk1  DOCUMENT ME!
     */
    public void setDk1(final TkeyDoppelkommandoCustomBean dk1) {
        setFk_dk1(dk1);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public TkeyDoppelkommandoCustomBean getFk_dk1() {
        return fk_dk1;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_dk1  DOCUMENT ME!
     */
    public void setFk_dk1(final TkeyDoppelkommandoCustomBean fk_dk1) {
        final TkeyDoppelkommandoCustomBean old = this.fk_dk1;
        this.fk_dk1 = fk_dk1;
        this.propertyChangeSupport.firePropertyChange(PROP__FK_DK1, old, this.fk_dk1);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public TkeyDoppelkommandoCustomBean getDk2() {
        return getFk_dk2();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  dk2  DOCUMENT ME!
     */
    public void setDk2(final TkeyDoppelkommandoCustomBean dk2) {
        setFk_dk2(dk2);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public TkeyDoppelkommandoCustomBean getFk_dk2() {
        return fk_dk2;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  dk2  DOCUMENT ME!
     */
    public void setFk_dk2(final TkeyDoppelkommandoCustomBean dk2) {
        final TkeyDoppelkommandoCustomBean old = this.fk_dk2;
        this.fk_dk2 = dk2;
        this.propertyChangeSupport.firePropertyChange(PROP__FK_DK2, old, this.fk_dk2);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public TkeyEnergielieferantCustomBean getFk_energielieferant() {
        return fk_energielieferant;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_energielieferant  DOCUMENT ME!
     */
    public void setFk_energielieferant(final TkeyEnergielieferantCustomBean fk_energielieferant) {
        final TkeyEnergielieferantCustomBean old = this.fk_energielieferant;
        this.fk_energielieferant = fk_energielieferant;
        this.propertyChangeSupport.firePropertyChange(PROP__FK_ENERGIELIEFERANT, old, this.fk_energielieferant);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public TkeyLeuchtentypCustomBean getFk_leuchttyp() {
        return fk_leuchttyp;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_leuchttyp  DOCUMENT ME!
     */
    public void setFk_leuchttyp(final TkeyLeuchtentypCustomBean fk_leuchttyp) {
        final TkeyLeuchtentypCustomBean old = this.fk_leuchttyp;
        this.fk_leuchttyp = fk_leuchttyp;
        this.propertyChangeSupport.firePropertyChange(PROP__FK_LEUCHTTYP, old, this.fk_leuchttyp);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public TkeyUnterhLeuchteCustomBean getFk_unterhaltspflicht_leuchte() {
        return fk_unterhaltspflicht_leuchte;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_unterhaltspflicht_leuchte  DOCUMENT ME!
     */
    public void setFk_unterhaltspflicht_leuchte(final TkeyUnterhLeuchteCustomBean fk_unterhaltspflicht_leuchte) {
        final TkeyUnterhLeuchteCustomBean old = this.fk_unterhaltspflicht_leuchte;
        this.fk_unterhaltspflicht_leuchte = fk_unterhaltspflicht_leuchte;
        this.propertyChangeSupport.firePropertyChange(
            PROP__FK_UNTERHALTSPFLICHT_LEUCHTE,
            old,
            this.fk_unterhaltspflicht_leuchte);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Date getInbetriebnahme_leuchte() {
        return inbetriebnahme_leuchte;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  inbetriebnahme_leuchte  DOCUMENT ME!
     */
    public void setInbetriebnahme_leuchte(final Date inbetriebnahme_leuchte) {
        final Date old = this.inbetriebnahme_leuchte;
        this.inbetriebnahme_leuchte = inbetriebnahme_leuchte;
        this.propertyChangeSupport.firePropertyChange(PROP__INBETRIEBNAHME_LEUCHTE, old, this.inbetriebnahme_leuchte);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public TdtaStandortMastCustomBean getFk_standort() {
        return fk_standort;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_standort  DOCUMENT ME!
     */
    public void setFk_standort(final TdtaStandortMastCustomBean fk_standort) {
        final TdtaStandortMastCustomBean old = this.fk_standort;
        this.fk_standort = fk_standort;
        this.propertyChangeSupport.firePropertyChange(PROP__FK_STANDORT, old, this.fk_standort);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getLfd_nummer() {
        return lfd_nummer;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  lfd_nummer  DOCUMENT ME!
     */
    public void setLfd_nummer(final Integer lfd_nummer) {
        final Integer old = this.lfd_nummer;
        this.lfd_nummer = lfd_nummer;
        this.propertyChangeSupport.firePropertyChange(PROP__LFD_NUMMER, old, this.lfd_nummer);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getAnzahl_2dk() {
        return anzahl_2dk;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  anzahl_2dk  DOCUMENT ME!
     */
    public void setAnzahl_2dk(final Integer anzahl_2dk) {
        final Integer old = this.anzahl_2dk;
        this.anzahl_2dk = anzahl_2dk;
        this.propertyChangeSupport.firePropertyChange(PROP__ANZAHL_2DK, old, this.anzahl_2dk);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getMontagefirma_leuchte() {
        return montagefirma_leuchte;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  montagefirma_leuchte  DOCUMENT ME!
     */
    public void setMontagefirma_leuchte(final String montagefirma_leuchte) {
        final String old = this.montagefirma_leuchte;
        this.montagefirma_leuchte = montagefirma_leuchte;
        this.propertyChangeSupport.firePropertyChange(PROP__MONTAGEFIRMA_LEUCHTE, old, this.montagefirma_leuchte);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getAnzahl_1dk() {
        return anzahl_1dk;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  anzahl_1dk  DOCUMENT ME!
     */
    public void setAnzahl_1dk(final Integer anzahl_1dk) {
        final Integer old = this.anzahl_1dk;
        this.anzahl_1dk = anzahl_1dk;
        this.propertyChangeSupport.firePropertyChange(PROP__ANZAHL_1DK, old, this.anzahl_1dk);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getBemerkungen() {
        return bemerkungen;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  bemerkungen  DOCUMENT ME!
     */
    public void setBemerkungen(final String bemerkungen) {
        final String old = this.bemerkungen;
        this.bemerkungen = bemerkungen;
        this.propertyChangeSupport.firePropertyChange(PROP__BEMERKUNGEN, old, this.bemerkungen);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public TdtaStandortMastCustomBean getStandort() {
        return getFk_standort();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  standort  DOCUMENT ME!
     */
    public void setStandort(final TdtaStandortMastCustomBean standort) {
        setFk_standort(standort);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getLaufendeNummer() {
        return getLfd_nummer();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  laufendeNummer  DOCUMENT ME!
     */
    public void setLaufendeNummer(final Integer laufendeNummer) {
        setLfd_nummer(laufendeNummer);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public TkeyEnergielieferantCustomBean getEnergielieferant() {
        return getFk_energielieferant();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  energielieferant  DOCUMENT ME!
     */
    public void setEnergielieferant(final TkeyEnergielieferantCustomBean energielieferant) {
        setFk_energielieferant(energielieferant);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public TkeyLeuchtentypCustomBean getLeuchtentyp() {
        return getFk_leuchttyp();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  leuchtentyp  DOCUMENT ME!
     */
    public void setLeuchtentyp(final TkeyLeuchtentypCustomBean leuchtentyp) {
        setFk_leuchttyp(leuchtentyp);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public TkeyUnterhLeuchteCustomBean getUnterhaltspflichtLeuchte() {
        return getFk_unterhaltspflicht_leuchte();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  unterhaltspflichtLeuchte  DOCUMENT ME!
     */
    public void setUnterhaltspflichtLeuchte(final TkeyUnterhLeuchteCustomBean unterhaltspflichtLeuchte) {
        setFk_unterhaltspflicht_leuchte(unterhaltspflichtLeuchte);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getAnzahl1DK() {
        return getAnzahl_1dk();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  anzahl1DK  DOCUMENT ME!
     */
    public void setAnzahl1DK(final Integer anzahl1DK) {
        setAnzahl_1dk(anzahl1DK);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getAnzahl2DK() {
        return getAnzahl_2dk();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  anzahl2DK  DOCUMENT ME!
     */
    public void setAnzahl2DK(final Integer anzahl2DK) {
        setAnzahl_2dk(anzahl2DK);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getMontageFirmaLeuchte() {
        return getMontagefirma_leuchte();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  montageFirmaLeuchte  DOCUMENT ME!
     */
    public void setMontageFirmaLeuchte(final String montageFirmaLeuchte) {
        setMontagefirma_leuchte(montageFirmaLeuchte);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Date getInbetriebnahmeLeuchte() {
        return getInbetriebnahme_leuchte();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  inbetriebnahmeLeuchte  DOCUMENT ME!
     */
    public void setInbetriebnahmeLeuchte(final Date inbetriebnahmeLeuchte) {
        setInbetriebnahme_leuchte(inbetriebnahmeLeuchte);
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
        if (other instanceof TdtaLeuchtenCustomBean) {
            final TdtaLeuchtenCustomBean anEntity = (TdtaLeuchtenCustomBean)other;
            if (this == other) {
                return true;
            } else if ((other == null) || (!this.getClass().isAssignableFrom(other.getClass()))) {
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
        return "de.cismet.belis.entity.Leuchte[id=" + getId() + "]";
    }

    @Override
    public String getKeyString() {
        String leuchtennummer = "";
        String leuchtentyp = "";
        if (getLaufendeNummer() != null) {
            leuchtennummer = getLaufendeNummer().toString();
        }
        if ((getLeuchtentyp() != null) && (getLeuchtentyp().getLeuchtentyp() != null)) {
            leuchtentyp = getLeuchtentyp().getLeuchtentyp();
        }
        if ((leuchtennummer.length() > 0) && (leuchtentyp.length() > 0)) {
            return leuchtennummer + ", " + leuchtentyp;
        } else if (leuchtennummer.length() > 0) {
            return leuchtennummer;
        } else if (leuchtentyp.length() > 0) {
            return leuchtentyp;
        } else {
            return "";
        }
    }

    @Override
    public String getHumanReadablePosition() {
        if ((getStrassenschluessel() != null) && (getStrassenschluessel().getStrasse() != null)) {
            return getStrassenschluessel().getStrasse();
        } else {
            return super.getHumanReadablePosition();
        }
    }

//    @Override
//    public FeatureAnnotationSymbol getPointAnnotationSymbol() {
//        if (mapIcon != null) {
//            return mapIcon;
//        } else {
//            mapIcon = FeatureAnnotationSymbol.newCenteredFeatureAnnotationSymbol(
//                    MapIcons.icoLeuchte, MapIcons.icoLeuchteSelected);
//            return mapIcon;
//        }
//    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if ((evt.getSource() != null) && (evt.getSource() instanceof TdtaStandortMastCustomBean)
                    && isParentMast((TdtaStandortMastCustomBean)evt.getSource())) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Property of parent Mast has changed. Changing this leuchte property");
            }
            if ((evt.getPropertyName() != null)
                        && evt.getPropertyName().equals(TdtaStandortMastCustomBean.PROP_STRASSENSCHLUESSEL)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Strassenschluessel changed");
                }
                setFk_strassenschluessel((TkeyStrassenschluesselCustomBean)evt.getNewValue());
            } else if ((evt.getPropertyName() != null)
                        && evt.getPropertyName().equals(TdtaStandortMastCustomBean.PROP_KENNZIFFER)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Kennziffer changed");
                }
                setKennziffer((TkeyKennzifferCustomBean)evt.getNewValue());
            } else if ((evt.getPropertyName() != null)
                        && evt.getPropertyName().equals(TdtaStandortMastCustomBean.PROP_LAUFENDENUMMER)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Laufende Nummer changed");
                }
                setLaufendeNummer((Integer)evt.getNewValue());
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Unkown property. Nothing to change");
                }
            }
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Property not from parent mast");
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   standort  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean isParentMast(final TdtaStandortMastCustomBean standort) {
        return (standort != null) && (standort.getLeuchten() != null) && standort.getLeuchten().contains(this);
    }
}
