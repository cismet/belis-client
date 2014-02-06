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

import java.beans.PropertyChangeEvent;

import java.util.Collection;
import java.util.Date;

import de.cismet.belisEE.mapicons.MapIcons;

import de.cismet.cismap.commons.gui.piccolo.FeatureAnnotationSymbol;

import de.cismet.commons.server.entity.GeoBaseEntity;
import de.cismet.commons.server.interfaces.DocumentContainer;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class TdtaLeuchtenCustomBean extends GeoBaseEntity implements BasicEntity, DocumentContainer {

    //~ Static fields/initializers ---------------------------------------------

    public static final TkeyUnterhLeuchteCustomBean DEFAULT_UNTERHALT;
    public static final TkeyDoppelkommandoCustomBean DEFAULT_DOPPELKOMMANDO;

    static {
        TkeyUnterhLeuchteCustomBean unterhalt = null;
        try {
            unterhalt = TkeyUnterhLeuchteCustomBean.createNew();
            unterhalt.setPk((Integer)0);
            unterhalt.setUnterhaltspflichtigeLeuchte("Öffentl. Beleuchtung");
        } catch (final Exception ex) {
        } finally {
            DEFAULT_UNTERHALT = unterhalt;
        }
//        BelisBroker.setDefaultUnterhaltLeuchte(curUnterhaltLeuchte);
        TkeyDoppelkommandoCustomBean doppelkommando = null;
        try {
            doppelkommando = TkeyDoppelkommandoCustomBean.createNew();
            doppelkommando.setPk("12");
            doppelkommando.setBeschreibung("Ganznachtbetrieb (früher Nacht)");
        } catch (final Exception ex) {
        } finally {
            DEFAULT_DOPPELKOMMANDO = doppelkommando;
        }
//        BelisBroker.setDefaultDoppelkommando1(curDoppelkommando);
    }

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(TdtaLeuchtenCustomBean.class);

    public static final String TABLE = "tdta_leuchten";

    public static final String PROP__PLZ = "plz";
    public static final String PROP__FK_STRASSENSCHLUESSEL = "fk_strassenschluessel";
    public static final String PROP__FK_ENERGIELIEFERANT = "fk_energielieferant";
    public static final String PROP__RUNDSTEUEREMPFAENGER = "rundsteuerempfaenger";
    public static final String PROP__FK_LEUCHTTYP = "fk_leuchttyp";
    public static final String PROP__FK_UNTERHALTSPFLICHT_LEUCHTE = "fk_unterhaltspflicht_leuchte";
    public static final String PROP__ZAEHLER = "zaehler";
    public static final String PROP__FK_DK1 = "fk_dk1";
    public static final String PROP__FK_DK2 = "fk_dk2";
    public static final String PROP__INBETRIEBNAHME_LEUCHTE = "inbetriebnahme_leuchte";
    public static final String PROP__FK_STANDORT = "fk_standort";
    public static final String PROP__LFD_NUMMER = "lfd_nummer";
    public static final String PROP__ANZAHL_2DK = "anzahl_2dk";
    public static final String PROP__FK_KENNZIFFER = "fk_kennziffer";
    public static final String PROP__LEUCHTENNUMMER = "leuchtennummer";
    public static final String PROP__MONTAGEFIRMA_LEUCHTE = "montagefirma_leuchte";
    public static final String PROP__SCHALTSTELLE = "schaltstelle";
    public static final String PROP__ANZAHL_1DK = "anzahl_1dk";
    public static final String PROP__STADTBEZIRK = "stadtbezirk";
    public static final String PROP__BEMERKUNGEN = "bemerkungen";
    public static final String PROP__DOKUMENTE = "dokumente";
    public static final String PROP__ANSCHLUSSLEISTUNG_1DK = "anschlussleistung_1dk";
    public static final String PROP__ANSCHLUSSLEISTUNG_2DK = "anschlussleistung_2dk";
    public static final String PROP__KABELUEBERGANGSKASTEN_SK_II = "kabeluebergangskasten_sk_ii";
    public static final String PROP__LEUCHTMITTEL = "leuchtmittel";
    public static final String PROP__LEBENSDAUER = "lebensdauer";
    public static final String PROP__WECHSELDATUM = "wechseldatum";
    public static final String PROP__WARTUNGSZYKLUS = "wartungszyklus";
    public static final String PROP__WECHSELVORSCHALTGERAET = "wechselvorschaltgeraet";
    public static final String PROP__VORSCHALTGERAET = "vorschaltgeraet";
    public static final String PROP__MONTEUR = "monteur";
    public static final String PROP__NAECHSTER_WECHSEL = "naechster_wechsel";
    public static final String PROP__EINBAUDATUM = "einbaudatum";

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
            PROP__DOKUMENTE,
            PROP__ANSCHLUSSLEISTUNG_1DK,
            PROP__ANSCHLUSSLEISTUNG_2DK,
            PROP__KABELUEBERGANGSKASTEN_SK_II,
            PROP__LEUCHTMITTEL,
            PROP__LEBENSDAUER,
            PROP__WECHSELDATUM,
            PROP__WARTUNGSZYKLUS,
            PROP__WECHSELVORSCHALTGERAET,
            PROP__MONTEUR,
            PROP__NAECHSTER_WECHSEL,
            PROP__VORSCHALTGERAET,
            PROP__EINBAUDATUM
        };

    //~ Instance fields --------------------------------------------------------

    private String plz;
    private TkeyStrassenschluesselCustomBean fk_strassenschluessel;
    private TkeyEnergielieferantCustomBean fk_energielieferant;
    private RundsteuerempfaengerCustomBean rundsteuerempfaenger;
    private TkeyLeuchtentypCustomBean fk_leuchttyp;
    private TkeyUnterhLeuchteCustomBean fk_unterhaltspflicht_leuchte;
    private Boolean zaehler;
    private TkeyDoppelkommandoCustomBean fk_dk1;
    private TkeyDoppelkommandoCustomBean fk_dk2;
    private Date inbetriebnahme_leuchte;
    private TdtaStandortMastCustomBean fk_standort;
    private TdtaStandortMastCustomBean fk_standort_backlink;
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
    private Double anschlussleistung_1dk;
    private Double anschlussleistung_2dk;
    private Boolean kabeluebergangskasten_sk_ii;
    private LeuchtmittelCustomBean leuchtmittel;
    private Double lebensdauer;
    private Date wechseldatum;
    private Date wartungszyklus;
    private Date wechselvorschaltgeraet;
    private Date naechster_wechsel;
    private String vorschaltgeraet;
    private String monteur;
    private Date einbaudatum;

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
        return (TdtaLeuchtenCustomBean)createNew(TABLE);
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
    @Override
    public Collection<DmsUrlCustomBean> getDokumente() {
        return dokumente;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  dokumente  DOCUMENT ME!
     */
    @Override
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
    public RundsteuerempfaengerCustomBean getRundsteuerempfaenger() {
        return rundsteuerempfaenger;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  rundsteuerempfaenger  DOCUMENT ME!
     */
    public void setRundsteuerempfaenger(final RundsteuerempfaengerCustomBean rundsteuerempfaenger) {
        final RundsteuerempfaengerCustomBean old = this.rundsteuerempfaenger;
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
        if (fk_standort == null) {
            return getFk_standort_backlink();
        } else {
            return fk_standort;
        }
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
        return "Leuchte[id=" + getId() + "]";
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

    @Override
    public FeatureAnnotationSymbol getPointAnnotationSymbol() {
        if (mapIcon != null) {
            return mapIcon;
        } else {
            mapIcon = FeatureAnnotationSymbol.newCenteredFeatureAnnotationSymbol(
                    MapIcons.icoLeuchte,
                    null);
            return mapIcon;
        }
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if ((evt.getSource() != null) && (evt.getSource() instanceof TdtaStandortMastCustomBean)
                    && isParentMast((TdtaStandortMastCustomBean)evt.getSource())) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Property of parent Mast has changed. Changing this leuchte property");
            }
            if ((evt.getPropertyName() != null)
                        && evt.getPropertyName().equals(TdtaStandortMastCustomBean.PROP__FK_STRASSENSCHLUESSEL)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Strassenschluessel changed");
                }
                setFk_strassenschluessel((TkeyStrassenschluesselCustomBean)evt.getNewValue());
            } else if ((evt.getPropertyName() != null)
                        && evt.getPropertyName().equals(TdtaStandortMastCustomBean.PROP__FK_KENNZIFFER)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Kennziffer changed");
                }
                setKennziffer((TkeyKennzifferCustomBean)evt.getNewValue());
            } else if ((evt.getPropertyName() != null)
                        && evt.getPropertyName().equals(TdtaStandortMastCustomBean.PROP__LFD_NUMMER)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Laufende Nummer changed");
                }
                setLaufendeNummer((Integer)evt.getNewValue());
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Unkown property. Nothing to change");
                }
            }
        } else if ((evt.getSource() != null) && (evt.getSource() instanceof TdtaLeuchtenCustomBean)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Property not from parent mast");
            }
            try {
                super.propertyChange(evt);
            } catch (final Exception ex) {
                LOG.info("error while super.propertyChange(evt)", ex);
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

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Double getAnschlussleistung_1dk() {
        return anschlussleistung_1dk;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  anschlussleistung_1dk  DOCUMENT ME!
     */
    public void setAnschlussleistung_1dk(final Double anschlussleistung_1dk) {
        final Double old = this.anschlussleistung_1dk;
        this.anschlussleistung_1dk = anschlussleistung_1dk;
        this.propertyChangeSupport.firePropertyChange(PROP__ANSCHLUSSLEISTUNG_1DK, old, this.anschlussleistung_1dk);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Double getAnschlussleistung_2dk() {
        return anschlussleistung_2dk;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  anschlussleistung_2dk  DOCUMENT ME!
     */
    public void setAnschlussleistung_2dk(final Double anschlussleistung_2dk) {
        final Double old = this.anschlussleistung_2dk;
        this.anschlussleistung_2dk = anschlussleistung_2dk;
        this.propertyChangeSupport.firePropertyChange(PROP__ANSCHLUSSLEISTUNG_2DK, old, this.anschlussleistung_2dk);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Boolean getKabeluebergangskasten_sk_ii() {
        return kabeluebergangskasten_sk_ii;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  kabeluebergangskasten_sk_ii  DOCUMENT ME!
     */
    public void setKabeluebergangskasten_sk_ii(final Boolean kabeluebergangskasten_sk_ii) {
        final Boolean old = this.kabeluebergangskasten_sk_ii;
        this.kabeluebergangskasten_sk_ii = kabeluebergangskasten_sk_ii;
        this.propertyChangeSupport.firePropertyChange(
            PROP__KABELUEBERGANGSKASTEN_SK_II,
            old,
            this.kabeluebergangskasten_sk_ii);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public LeuchtmittelCustomBean getLeuchtmittel() {
        return leuchtmittel;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  leuchtmittel  DOCUMENT ME!
     */
    public void setLeuchtmittel(final LeuchtmittelCustomBean leuchtmittel) {
        final LeuchtmittelCustomBean old = this.leuchtmittel;
        this.leuchtmittel = leuchtmittel;
        this.propertyChangeSupport.firePropertyChange(PROP__LEUCHTMITTEL, old, this.leuchtmittel);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Double getLebensdauer() {
        return lebensdauer;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  lebensdauer  DOCUMENT ME!
     */
    public void setLebensdauer(final Double lebensdauer) {
        final Double old = this.lebensdauer;
        this.lebensdauer = lebensdauer;
        this.propertyChangeSupport.firePropertyChange(PROP__LEBENSDAUER, old, this.lebensdauer);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Date getWechseldatum() {
        return wechseldatum;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  wechseldatum  DOCUMENT ME!
     */
    public void setWechseldatum(final Date wechseldatum) {
        final Date old = this.wechseldatum;
        this.wechseldatum = wechseldatum;
        this.propertyChangeSupport.firePropertyChange(PROP__WECHSELDATUM, old, this.wechseldatum);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Date getWartungszyklus() {
        return wartungszyklus;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  wartungszyklus  DOCUMENT ME!
     */
    public void setWartungszyklus(final Date wartungszyklus) {
        final Date old = this.wartungszyklus;
        this.wartungszyklus = wartungszyklus;
        this.propertyChangeSupport.firePropertyChange(PROP__WARTUNGSZYKLUS, old, this.wartungszyklus);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Date getWechselvorschaltgeraet() {
        return wechselvorschaltgeraet;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  wechselvorschaltgeraet  DOCUMENT ME!
     */
    public void setWechselvorschaltgeraet(final Date wechselvorschaltgeraet) {
        final Date old = this.wechselvorschaltgeraet;
        this.wechselvorschaltgeraet = wechselvorschaltgeraet;
        this.propertyChangeSupport.firePropertyChange(PROP__WECHSELVORSCHALTGERAET, old, this.wechselvorschaltgeraet);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getVorschaltgeraet() {
        return vorschaltgeraet;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  vorschaltgeraet  DOCUMENT ME!
     */
    public void setVorschaltgeraet(final String vorschaltgeraet) {
        final String old = this.vorschaltgeraet;
        this.vorschaltgeraet = vorschaltgeraet;
        this.propertyChangeSupport.firePropertyChange(PROP__VORSCHALTGERAET, old, this.vorschaltgeraet);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Date getNaechster_wechsel() {
        return naechster_wechsel;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  naechster_wechsel  DOCUMENT ME!
     */
    public void setNaechster_wechsel(final Date naechster_wechsel) {
        final Date old = this.naechster_wechsel;
        this.naechster_wechsel = naechster_wechsel;
        this.propertyChangeSupport.firePropertyChange(PROP__NAECHSTER_WECHSEL, old, this.naechster_wechsel);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getMonteur() {
        return monteur;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  monteur  DOCUMENT ME!
     */
    public void setMonteur(final String monteur) {
        final String old = this.monteur;
        this.monteur = monteur;
        this.propertyChangeSupport.firePropertyChange(PROP__MONTEUR, old, this.monteur);
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
     * @return  DOCUMENT ME!
     */
    public TdtaStandortMastCustomBean getFk_standort_backlink() {
        return fk_standort_backlink;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_standort_backlink  DOCUMENT ME!
     */
    public void setFk_standort_backlink(final TdtaStandortMastCustomBean fk_standort_backlink) {
        this.fk_standort_backlink = fk_standort_backlink;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  einbaudatum  monteur DOCUMENT ME!
     */
    public void setEinbaudatum(final Date einbaudatum) {
        final Date old = this.einbaudatum;
        this.einbaudatum = einbaudatum;
        this.propertyChangeSupport.firePropertyChange(PROP__EINBAUDATUM, old, this.einbaudatum);
    }

    @Override
    public GeomCustomBean getGeometrie() {
        if (getFk_standort() == null) {
            return null;
        }
        return getFk_standort().getGeometrie();
    }

    @Override
    public void setGeometrie(final GeomCustomBean val) {
        if (getFk_standort() == null) {
            return;
        }
        getFk_standort().setGeometrie(val);
    }
}
