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

import java.util.Date;
import java.util.Set;

import de.cismet.belisEEold.entity.Leuchte;

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

    //~ Instance fields --------------------------------------------------------

    protected Set<DmsUrlCustomBean> dokumente;

    private Short stadtbezirk;
    private String plz;
    private TkeyStrassenschluesselCustomBean strassenschluessel;
    private TkeyKennzifferCustomBean kennziffer;
    private Short laufendeNummer;
    private Short leuchtennummer;
    private String schaltstelle;
    private TkeyEnergielieferantCustomBean energielieferant;
    private String rundsteuerempfaenger;
    private TkeyLeuchtentypCustomBean leuchtentyp;
    private TkeyUnterhLeuchteCustomBean unterhaltspflichtLeuchte;
    private boolean zaehler;
    private TkeyDoppelkommandoCustomBean dk1;
    private Short anzahl1DK;
    private TkeyDoppelkommandoCustomBean dk2;
    private Short anzahl2DK;
    private String montageFirmaLeuchte;
    private Date inbetriebnahmeLeuchte;
    private String bemerkungen;
    private Integer id;
    private TdtaStandortMastCustomBean standort;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new TdtaLeuchteCustomBean object.
     */
    public TdtaLeuchteCustomBean() {
        super();
    }

    /**
     * Creates a new TdtaLeuchteCustomBean object.
     *
     * @param  id  DOCUMENT ME!
     */
    public TdtaLeuchteCustomBean(final Integer id) {
        this.id = id;
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public Set<DmsUrlCustomBean> getDokumente() {
        return dokumente;
    }

    @Override
    public void setDokumente(final Set<DmsUrlCustomBean> dokumente) {
        this.dokumente = dokumente;
    }

    @Override
    public TdtaStandortMastCustomBean getStandort() {
        return standort;
    }

    @Override
    public void setStandort(final TdtaStandortMastCustomBean standort) {
        this.standort = standort;
    }

    @Override
    public Short getStadtbezirk() {
        return stadtbezirk;
    }

    @Override
    public void setStadtbezirk(final Short stadtbezirk) {
        this.stadtbezirk = stadtbezirk;
    }

    @Override
    public String getPlz() {
        return plz;
    }

    @Override
    public void setPlz(final String plz) {
        this.plz = plz;
    }

    @Override
    public TkeyStrassenschluesselCustomBean getStrassenschluessel() {
        return strassenschluessel;
    }

    @Override
    public void setStrassenschluessel(final TkeyStrassenschluesselCustomBean strassenschluessel) {
        final TkeyStrassenschluesselCustomBean oldStrassenschluessel = getStrassenschluessel();
        this.strassenschluessel = strassenschluessel;
        getPropertyChangeSupport().firePropertyChange(
            PROP_STRASSENSCHLUESSEL,
            oldStrassenschluessel,
            strassenschluessel);
    }

    @Override
    public TkeyKennzifferCustomBean getKennziffer() {
        return kennziffer;
    }

    @Override
    public void setKennziffer(final TkeyKennzifferCustomBean kennziffer) {
        final TkeyKennzifferCustomBean oldKennziffer = getKennziffer();
        this.kennziffer = kennziffer;
        getPropertyChangeSupport().firePropertyChange(PROP_KENNZIFFER, oldKennziffer, kennziffer);
    }

    @Override
    public Short getLaufendeNummer() {
        return laufendeNummer;
    }

    @Override
    public void setLaufendeNummer(final Short laufendeNummer) {
        this.laufendeNummer = laufendeNummer;
    }

    @Override
    public Short getLeuchtennummer() {
        return leuchtennummer;
    }

    @Override
    public void setLeuchtennummer(final Short leuchtennummer) {
        final Short oldLeuchtenummer = getLeuchtennummer();
        this.leuchtennummer = leuchtennummer;
        getPropertyChangeSupport().firePropertyChange(PROP_LEUCHTENNUMMER, oldLeuchtenummer, leuchtennummer);
    }

    @Override
    public String getSchaltstelle() {
        return schaltstelle;
    }

    @Override
    public void setSchaltstelle(final String schaltstelle) {
        this.schaltstelle = schaltstelle;
    }

    @Override
    public TkeyEnergielieferantCustomBean getEnergielieferant() {
        return energielieferant;
    }

    @Override
    public void setEnergielieferant(final TkeyEnergielieferantCustomBean energielieferant) {
        this.energielieferant = energielieferant;
    }

    @Override
    public String getRundsteuerempfaenger() {
        return rundsteuerempfaenger;
    }

    @Override
    public void setRundsteuerempfaenger(final String rundsteuerempfaenger) {
        this.rundsteuerempfaenger = rundsteuerempfaenger;
    }

    @Override
    public TkeyLeuchtentypCustomBean getLeuchtentyp() {
        return leuchtentyp;
    }

    @Override
    public void setLeuchtentyp(final TkeyLeuchtentypCustomBean leuchtentyp) {
        final TkeyLeuchtentypCustomBean oldLeuchtenTyp = getLeuchtentyp();
        this.leuchtentyp = leuchtentyp;
        getPropertyChangeSupport().firePropertyChange(PROP_LEUCHTENTYP, oldLeuchtenTyp, leuchtentyp);
    }

    @Override
    public TkeyUnterhLeuchteCustomBean getUnterhaltspflichtLeuchte() {
        return unterhaltspflichtLeuchte;
    }

    @Override
    public void setUnterhaltspflichtLeuchte(final TkeyUnterhLeuchteCustomBean unterhaltspflichtLeuchte) {
        this.unterhaltspflichtLeuchte = unterhaltspflichtLeuchte;
    }

    @Override
    public boolean getZaehler() {
        return zaehler;
    }

    @Override
    public void setZaehler(final boolean zaehler) {
        this.zaehler = zaehler;
    }

    @Override
    public TkeyDoppelkommandoCustomBean getDk1() {
        return dk1;
    }

    @Override
    public void setDk1(final TkeyDoppelkommandoCustomBean dk1) {
        this.dk1 = dk1;
    }

    @Override
    public Short getAnzahl1DK() {
        return anzahl1DK;
    }

    @Override
    public void setAnzahl1DK(final Short anzahl1DK) {
        this.anzahl1DK = anzahl1DK;
    }

    @Override
    public TkeyDoppelkommandoCustomBean getDk2() {
        return dk2;
    }

    @Override
    public void setDk2(final TkeyDoppelkommandoCustomBean dk2) {
        this.dk2 = dk2;
    }

    @Override
    public Short getAnzahl2DK() {
        return anzahl2DK;
    }

    @Override
    public void setAnzahl2DK(final Short anzahl2DK) {
        this.anzahl2DK = anzahl2DK;
    }

    @Override
    public String getMontageFirmaLeuchte() {
        return montageFirmaLeuchte;
    }

    @Override
    public void setMontageFirmaLeuchte(final String montageFirmaLeuchte) {
        this.montageFirmaLeuchte = montageFirmaLeuchte;
    }

    @Override
    public Date getInbetriebnahmeLeuchte() {
        return inbetriebnahmeLeuchte;
    }

    @Override
    public void setInbetriebnahmeLeuchte(final Date inbetriebnahmeLeuchte) {
        this.inbetriebnahmeLeuchte = inbetriebnahmeLeuchte;
    }

    @Override
    public String getBemerkungen() {
        return bemerkungen;
    }

    @Override
    public void setBemerkungen(final String bemerkungen) {
        this.bemerkungen = bemerkungen;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(final Integer id) {
        this.id = id;
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
                setLaufendeNummer((Short)evt.getNewValue());
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
