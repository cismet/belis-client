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

import de.cismet.belisEE.entity.Leuchte;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class TdtaLeuchteCustomBean extends BaseEntity implements Leuchte {

    //~ Static fields/initializers ---------------------------------------------

    public static final TkeyUnterhLeuchteCustomBean DEFAULT_UNTERHALT;
    public static final TkeyDoppelkommandoCustomBean DEFAULT_DOPPELKOMMANDO;

    static {
        DEFAULT_UNTERHALT = new TkeyUnterhLeuchteCustomBean();
        DEFAULT_UNTERHALT.setPk((short)0);
        DEFAULT_UNTERHALT.setUnterhaltspflichtigeLeuchte("Öffentl. Beleuchtung");

        DEFAULT_DOPPELKOMMANDO = new TkeyDoppelkommandoCustomBean();
        DEFAULT_DOPPELKOMMANDO.setPk("12");
        DEFAULT_DOPPELKOMMANDO.setBeschreibung("Ganznachtbetrieb (früher Nacht)");
    }

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(TdtaLeuchteCustomBean.class);

    public static final String TABLE = "tdta_leuchten";

    private static final String PROP__ID = "id";
    private static final String PROP__PLZ = "PLZ";
    private static final String PROP__STRASSENSCHLUESSEL = "strassenschluessel";
    private static final String PROP__FK_ENERGIELIEFERANT = "fk_Energielieferant";
    private static final String PROP__RUNDSTEUEREMPFAENGER = "rundsteuerempfaenger";
    private static final String PROP__FK_LEUCHTTYP = "fk_Leuchttyp";
    private static final String PROP__FK_UNTERHALTSPFLICHT_LEUCHTE = "fk_Unterhaltspflicht_Leuchte";
    private static final String PROP__ZAEHLER = "zaehler";
    private static final String PROP__DK1 = "DK1";
    private static final String PROP__DK2 = "DK2";
    private static final String PROP__INBETRIEBNAHME_LEUCHTE = "Inbetriebnahme_Leuchte";
    private static final String PROP__STANDORT_ID = "standort_id";
    private static final String PROP__LFD_NUMMER = "lfd_Nummer";
    private static final String PROP__ANZAHL_2DK = "Anzahl_2DK";
    private static final String PROP__KENNZIFFER = "Kennziffer";
    private static final String PROP__LEUCHTENNUMMER = "Leuchtennummer";
    private static final String PROP__MONTAGEFIRMA_LEUCHTE = "Montagefirma_Leuchte";
    private static final String PROP__SCHALTSTELLE = "Schaltstelle";
    private static final String PROP__ANZAHL_1DK = "Anzahl_1DK";
    private static final String PROP__STADTBEZIRK = "Stadtbezirk";
    private static final String PROP__BEMERKUNGEN = "Bemerkungen";
    private static final String PROP__DOKUMENTE = "dokumente";

    private static final String[] PROPERTY_NAMES = new String[] {
            PROP__ID,
            PROP__PLZ,
            PROP__STRASSENSCHLUESSEL,
            PROP__FK_ENERGIELIEFERANT,
            PROP__RUNDSTEUEREMPFAENGER,
            PROP__FK_LEUCHTTYP,
            PROP__FK_UNTERHALTSPFLICHT_LEUCHTE,
            PROP__ZAEHLER,
            PROP__DK1,
            PROP__DK2,
            PROP__INBETRIEBNAHME_LEUCHTE,
            PROP__STANDORT_ID,
            PROP__LFD_NUMMER,
            PROP__ANZAHL_2DK,
            PROP__KENNZIFFER,
            PROP__LEUCHTENNUMMER,
            PROP__MONTAGEFIRMA_LEUCHTE,
            PROP__SCHALTSTELLE,
            PROP__ANZAHL_1DK,
            PROP__STADTBEZIRK,
            PROP__BEMERKUNGEN,
            PROP__DOKUMENTE
        };

    //~ Instance fields --------------------------------------------------------

    private Integer id;
    private String plz;
    private TkeyStrassenschluesselCustomBean strassenschluessel;
    private TkeyEnergielieferantCustomBean fk_energielieferant;
    private String rundsteuerempfaenger;
    private TkeyLeuchtentypCustomBean fk_Leuchttyp;
    private TkeyUnterhLeuchteCustomBean fk_Unterhaltspflicht_Leuchte;
    private Boolean zaehler;
    private TkeyDoppelkommandoCustomBean dk1;
    private TkeyDoppelkommandoCustomBean dk2;
    private Date inbetriebnahme_Leuchte;
    private TdtaStandortMastCustomBean standort_id;
    private Integer lfd_Nummer;
    private TkeyKennzifferCustomBean kennziffer;
    private Short leuchtennummer;
    private String montagefirma_Leuchte;
    private String schaltstelle;
    private Short anzahl_1DK;
    private Short anzahl_2DK;
    private Short stadtbezirk;
    private String bemerkungen;
    private Collection<DmsUrlCustomBean> dokumente;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbzweigdoseCustomBean object.
     */
    public TdtaLeuchteCustomBean() {
    }

    /**
     * Creates a new TdtaLeuchteCustomBean object.
     *
     * @param  id  DOCUMENT ME!
     */
    public TdtaLeuchteCustomBean(final Integer id) {
        setId(id);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static TdtaLeuchteCustomBean createNew() {
        try {
            return (TdtaLeuchteCustomBean)CidsBean.createNewCidsBeanFromTableName(CidsBroker.BELIS_DOMAIN, TABLE);
        } catch (Exception ex) {
            LOG.error("error creating " + TABLE + " bean", ex);
            return null;
        }
    }

    @Override
    public String[] getPropertyNames() {
        return PROPERTY_NAMES;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(final Integer id) {
        final Integer old = this.id;
        this.id = id;
        this.propertyChangeSupport.firePropertyChange(PROP__ID, old, this.id);
    }

    @Override
    public Collection<DmsUrlCustomBean> getDokumente() {
        return dokumente;
    }

    @Override
    public void setDokumente(final Collection<DmsUrlCustomBean> dokumente) {
        final Collection<DmsUrlCustomBean> old = this.dokumente;
        this.dokumente = dokumente;
        this.propertyChangeSupport.firePropertyChange(PROP__DOKUMENTE, old, this.dokumente);
    }

    @Override
    public Short getStadtbezirk() {
        return stadtbezirk;
    }

    @Override
    public void setStadtbezirk(final Short stadtbezirk) {
        final Short old = this.stadtbezirk;
        this.stadtbezirk = stadtbezirk;
        this.propertyChangeSupport.firePropertyChange(PROP__STADTBEZIRK, old, this.stadtbezirk);
    }

    @Override
    public String getPlz() {
        return plz;
    }

    @Override
    public void setPlz(final String plz) {
        final String old = this.plz;
        this.plz = plz;
        this.propertyChangeSupport.firePropertyChange(PROP__PLZ, old, this.plz);
    }

    @Override
    public TkeyStrassenschluesselCustomBean getStrassenschluessel() {
        return strassenschluessel;
    }

    @Override
    public void setStrassenschluessel(final TkeyStrassenschluesselCustomBean strassenschluessel) {
        final TkeyStrassenschluesselCustomBean old = this.strassenschluessel;
        this.strassenschluessel = strassenschluessel;
        this.propertyChangeSupport.firePropertyChange(PROP__STRASSENSCHLUESSEL, old, this.strassenschluessel);
    }

    @Override
    public TkeyKennzifferCustomBean getKennziffer() {
        return kennziffer;
    }

    @Override
    public void setKennziffer(final TkeyKennzifferCustomBean kennziffer) {
        final TkeyKennzifferCustomBean old = this.kennziffer;
        this.kennziffer = kennziffer;
        this.propertyChangeSupport.firePropertyChange(PROP__KENNZIFFER, old, this.kennziffer);
    }

    @Override
    public Short getLeuchtennummer() {
        return leuchtennummer;
    }

    @Override
    public void setLeuchtennummer(final Short leuchtennummer) {
        final Short old = this.leuchtennummer;
        this.leuchtennummer = leuchtennummer;
        this.propertyChangeSupport.firePropertyChange(PROP__LEUCHTENNUMMER, old, this.leuchtennummer);
    }

    @Override
    public String getSchaltstelle() {
        return schaltstelle;
    }

    @Override
    public void setSchaltstelle(final String schaltstelle) {
        final String old = this.schaltstelle;
        this.schaltstelle = schaltstelle;
        this.propertyChangeSupport.firePropertyChange(PROP__SCHALTSTELLE, old, this.schaltstelle);
    }

    @Override
    public String getRundsteuerempfaenger() {
        return rundsteuerempfaenger;
    }

    @Override
    public void setRundsteuerempfaenger(final String rundsteuerempfaenger) {
        final String old = this.rundsteuerempfaenger;
        this.rundsteuerempfaenger = rundsteuerempfaenger;
        this.propertyChangeSupport.firePropertyChange(PROP__RUNDSTEUEREMPFAENGER, old, this.rundsteuerempfaenger);
    }

    @Override
    public boolean getZaehler() {
        return zaehler;
    }

    @Override
    public void setZaehler(final boolean zaehler) {
        final Boolean old = this.zaehler;
        this.zaehler = zaehler;
        this.propertyChangeSupport.firePropertyChange(PROP__ZAEHLER, old, this.zaehler);
    }

    @Override
    public TkeyDoppelkommandoCustomBean getDk1() {
        return dk1;
    }

    @Override
    public void setDk1(final TkeyDoppelkommandoCustomBean dk1) {
        final TkeyDoppelkommandoCustomBean old = this.dk1;
        this.dk1 = dk1;
        this.propertyChangeSupport.firePropertyChange(PROP__DK1, old, this.dk1);
    }

    @Override
    public TkeyDoppelkommandoCustomBean getDk2() {
        return dk2;
    }

    @Override
    public void setDk2(final TkeyDoppelkommandoCustomBean dk2) {
        final TkeyDoppelkommandoCustomBean old = this.dk2;
        this.dk2 = dk2;
        this.propertyChangeSupport.firePropertyChange(PROP__DK2, old, this.dk2);
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
    public TkeyLeuchtentypCustomBean getFk_Leuchttyp() {
        return fk_Leuchttyp;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_Leuchttyp  DOCUMENT ME!
     */
    public void setFk_Leuchttyp(final TkeyLeuchtentypCustomBean fk_Leuchttyp) {
        final TkeyLeuchtentypCustomBean old = this.fk_Leuchttyp;
        this.fk_Leuchttyp = fk_Leuchttyp;
        this.propertyChangeSupport.firePropertyChange(PROP__FK_LEUCHTTYP, old, this.fk_Leuchttyp);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public TkeyUnterhLeuchteCustomBean getFk_Unterhaltspflicht_Leuchte() {
        return fk_Unterhaltspflicht_Leuchte;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_Unterhaltspflicht_Leuchte  DOCUMENT ME!
     */
    public void setFk_Unterhaltspflicht_Leuchte(final TkeyUnterhLeuchteCustomBean fk_Unterhaltspflicht_Leuchte) {
        final TkeyUnterhLeuchteCustomBean old = this.fk_Unterhaltspflicht_Leuchte;
        this.fk_Unterhaltspflicht_Leuchte = fk_Unterhaltspflicht_Leuchte;
        this.propertyChangeSupport.firePropertyChange(
            PROP__FK_UNTERHALTSPFLICHT_LEUCHTE,
            old,
            this.fk_Unterhaltspflicht_Leuchte);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Date getInbetriebnahme_Leuchte() {
        return inbetriebnahme_Leuchte;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  inbetriebnahme_Leuchte  DOCUMENT ME!
     */
    public void setInbetriebnahme_Leuchte(final Date inbetriebnahme_Leuchte) {
        final Date old = this.inbetriebnahme_Leuchte;
        this.inbetriebnahme_Leuchte = inbetriebnahme_Leuchte;
        this.propertyChangeSupport.firePropertyChange(PROP__INBETRIEBNAHME_LEUCHTE, old, this.inbetriebnahme_Leuchte);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public TdtaStandortMastCustomBean getStandort_id() {
        return standort_id;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  standort_id  DOCUMENT ME!
     */
    public void setStandort_id(final TdtaStandortMastCustomBean standort_id) {
        final TdtaStandortMastCustomBean old = this.standort_id;
        this.standort_id = standort_id;
        this.propertyChangeSupport.firePropertyChange(PROP__STANDORT_ID, old, this.standort_id);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getLfd_Nummer() {
        return lfd_Nummer;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  lfd_Nummer  DOCUMENT ME!
     */
    public void setLfd_Nummer(final Integer lfd_Nummer) {
        final Integer old = this.lfd_Nummer;
        this.lfd_Nummer = lfd_Nummer;
        this.propertyChangeSupport.firePropertyChange(PROP__LFD_NUMMER, old, this.lfd_Nummer);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Short getAnzahl_2DK() {
        return anzahl_2DK;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  Anzahl_2DK  DOCUMENT ME!
     */
    public void setAnzahl_2DK(final Short Anzahl_2DK) {
        final Short old = this.anzahl_2DK;
        this.anzahl_2DK = Anzahl_2DK;
        this.propertyChangeSupport.firePropertyChange(PROP__ANZAHL_2DK, old, this.anzahl_2DK);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getMontagefirma_Leuchte() {
        return montagefirma_Leuchte;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  montagefirma_Leuchte  DOCUMENT ME!
     */
    public void setMontagefirma_Leuchte(final String montagefirma_Leuchte) {
        final String old = this.montagefirma_Leuchte;
        this.montagefirma_Leuchte = montagefirma_Leuchte;
        this.propertyChangeSupport.firePropertyChange(PROP__MONTAGEFIRMA_LEUCHTE, old, this.montagefirma_Leuchte);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Short getAnzahl_1DK() {
        return anzahl_1DK;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  anzahl_1DK  DOCUMENT ME!
     */
    public void setAnzahl_1DK(final Short anzahl_1DK) {
        final Short old = this.anzahl_1DK;
        this.anzahl_1DK = anzahl_1DK;
        this.propertyChangeSupport.firePropertyChange(PROP__ANZAHL_1DK, old, this.anzahl_1DK);
    }

    @Override
    public String getBemerkungen() {
        return bemerkungen;
    }

    @Override
    public void setBemerkungen(final String bemerkungen) {
        final String old = this.bemerkungen;
        this.bemerkungen = bemerkungen;
        this.propertyChangeSupport.firePropertyChange(PROP__BEMERKUNGEN, old, this.bemerkungen);
    }

    @Override
    public TdtaStandortMastCustomBean getStandort() {
        return getStandort_id();
    }

    @Override
    public void setStandort(final TdtaStandortMastCustomBean standort) {
        setStandort_id(standort);
    }

    @Override
    public Integer getLaufendeNummer() {
        return getLfd_Nummer();
    }

    @Override
    public void setLaufendeNummer(final Integer laufendeNummer) {
        setLfd_Nummer(laufendeNummer);
    }

    @Override
    public TkeyEnergielieferantCustomBean getEnergielieferant() {
        return getFk_energielieferant();
    }

    @Override
    public void setEnergielieferant(final TkeyEnergielieferantCustomBean energielieferant) {
        setFk_energielieferant(energielieferant);
    }

    @Override
    public TkeyLeuchtentypCustomBean getLeuchtentyp() {
        return getFk_Leuchttyp();
    }

    @Override
    public void setLeuchtentyp(final TkeyLeuchtentypCustomBean leuchtentyp) {
        setFk_Leuchttyp(leuchtentyp);
    }

    @Override
    public TkeyUnterhLeuchteCustomBean getUnterhaltspflichtLeuchte() {
        return getFk_Unterhaltspflicht_Leuchte();
    }

    @Override
    public void setUnterhaltspflichtLeuchte(final TkeyUnterhLeuchteCustomBean unterhaltspflichtLeuchte) {
        setFk_Unterhaltspflicht_Leuchte(unterhaltspflichtLeuchte);
    }

    @Override
    public Short getAnzahl1DK() {
        return getAnzahl_1DK();
    }

    @Override
    public void setAnzahl1DK(final Short anzahl1DK) {
        setAnzahl_1DK(anzahl1DK);
    }

    @Override
    public Short getAnzahl2DK() {
        return getAnzahl_2DK();
    }

    @Override
    public void setAnzahl2DK(final Short anzahl2DK) {
        setAnzahl_2DK(anzahl2DK);
    }

    @Override
    public String getMontageFirmaLeuchte() {
        return getMontagefirma_Leuchte();
    }

    @Override
    public void setMontageFirmaLeuchte(final String montageFirmaLeuchte) {
        setMontagefirma_Leuchte(montageFirmaLeuchte);
    }

    @Override
    public Date getInbetriebnahmeLeuchte() {
        return getInbetriebnahme_Leuchte();
    }

    @Override
    public void setInbetriebnahmeLeuchte(final Date inbetriebnahmeLeuchte) {
        setInbetriebnahme_Leuchte(inbetriebnahmeLeuchte);
    }

    @Override
    public int hashCode() {
        if (this.getId() == null) {
            return super.hashCode();
        }
        return this.getId().hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof TdtaLeuchteCustomBean) {
            final TdtaLeuchteCustomBean anEntity = (TdtaLeuchteCustomBean)other;
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
        return "de.cismet.belis.entity.Leuchte[id=" + id + "]";
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
            System.out.println("Property of parent Mast has changed. Changing this leuchte property");
            if ((evt.getPropertyName() != null)
                        && evt.getPropertyName().equals(TdtaStandortMastCustomBean.PROP_STRASSENSCHLUESSEL)) {
                System.out.println("Strassenschluessel changed");
                setStrassenschluessel((TkeyStrassenschluesselCustomBean)evt.getNewValue());
            } else if ((evt.getPropertyName() != null)
                        && evt.getPropertyName().equals(TdtaStandortMastCustomBean.PROP_KENNZIFFER)) {
                System.out.println("Kennziffer changed");
                setKennziffer((TkeyKennzifferCustomBean)evt.getNewValue());
            } else if ((evt.getPropertyName() != null)
                        && evt.getPropertyName().equals(TdtaStandortMastCustomBean.PROP_LAUFENDENUMMER)) {
                System.out.println("Laufende Nummer changed");
                setLaufendeNummer((Integer)evt.getNewValue());
            } else {
                System.out.println("Unkown property. Nothing to change");
            }
        } else {
            System.out.println("Property not from parent mast");
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
