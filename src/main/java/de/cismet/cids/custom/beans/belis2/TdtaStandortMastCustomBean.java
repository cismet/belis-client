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

import java.awt.Color;
import java.awt.Image;

import java.beans.PropertyChangeEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import de.cismet.belis.broker.CidsBroker;

import de.cismet.belis2.server.search.HighestLfdNummerSearch;

import de.cismet.belisEE.exception.ActionNotSuccessfulException;

import de.cismet.belisEE.mapicons.MapIcons;

import de.cismet.belisEE.util.StandortKey;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cismap.commons.gui.piccolo.FeatureAnnotationSymbol;
import de.cismet.cismap.commons.tools.IconUtils;

import de.cismet.commons.server.entity.GeoBaseEntity;
import de.cismet.commons.server.interfaces.DocumentContainer;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class TdtaStandortMastCustomBean extends GeoBaseEntity implements DocumentContainer {

    //~ Static fields/initializers ---------------------------------------------

    public static TkeyUnterhMastCustomBean DEFAULT_UNTERHALT;

    static {
        DEFAULT_UNTERHALT = TkeyUnterhMastCustomBean.createNew();
//        DEFAULT_UNTERHALT.setPk((Integer)0);
//        DEFAULT_UNTERHALT.setUnterhaltMast("öffentl. Beleuchtung");
    }

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            TdtaStandortMastCustomBean.class);

    public static final String TABLE = "tdta_standort_mast";

    public static final String PROP__PLZ = "plz";
    public static final String PROP__FK_STRASSENSCHLUESSEL = "fk_strassenschluessel";
    public static final String PROP__FK_STADTBEZIRK = "fk_stadtbezirk";
    public static final String PROP__FK_MASTART = "fk_mastart";
    public static final String PROP__FK_KLASSIFIZIERUNG = "fk_klassifizierung";
    public static final String PROP__MASTANSTRICH = "mastanstrich";
    public static final String PROP__MASTSCHUTZ = "mastschutz";
    public static final String PROP__FK_UNTERHALTSPFLICHT_MAST = "fk_unterhaltspflicht_mast";
    public static final String PROP__FK_MASTTYP = "fk_masttyp";
    public static final String PROP__INBETRIEBNAHME_MAST = "inbetriebnahme_Mast";
    public static final String PROP__VERRECHNUNGSEINHEIT = "verrechnungseinheit";
    public static final String PROP__LETZTE_AENDERUNG = "letzte_aenderung";
    public static final String PROP__FK_GEOM = "fk_geom";
    public static final String PROP__IST_VIRTUELLER_STANDORT = "ist_virtueller_standort";
    public static final String PROP__BEMERKUNGEN = "bemerkungen";
    public static final String PROP__MONTAGEFIRMA = "montagefirma";
    public static final String PROP__STANDORTANGABE = "standortangabe";
    public static final String PROP__FK_KENNZIFFER = "fk_kennziffer";
    public static final String PROP__LFD_NUMMER = "lfd_nummer";
    public static final String PROP__HAUS_NR = "haus_nr";
    public static final String PROP__DOKUMENTE = "dokumente";
    public static final String PROP__LEUCHTEN = "leuchten";
    public static final String PROP__GRUENDUNG = "gruendung";
    public static final String PROP__ELEK_PRUEFUNG = "elek_pruefung";
    public static final String PROP__ERDUNG = "erdung";
    public static final String PROP__MONTEUR = "monteur";
    public static final String PROP__STANDSICHERHEITSPRUEFUNG = "standsicherheitspruefung";
    public static final String PROP__VERFAHREN = "verfahren";
    public static final String PROP__FOTO = "foto";
    public static final String PROP__NAECHSTES_PRUEFDATUM = "naechstes_pruefdatum";
    public static final String PROP__ANSTRICHFARBE = "anstrichfarbe";
    public static final String PROP__REVISION = "revision";
    public static final String PROP__ANLAGENGRUPPE = "anlagengruppe";
    public static final String PROP__ANBAUTEN = "anbauten";

    private static final String[] PROPERTY_NAMES = new String[] {
            PROP__ID,
            PROP__PLZ,
            PROP__FK_STRASSENSCHLUESSEL,
            PROP__FK_STADTBEZIRK,
            PROP__FK_MASTART,
            PROP__FK_KLASSIFIZIERUNG,
            PROP__MASTANSTRICH,
            PROP__MASTSCHUTZ,
            PROP__FK_UNTERHALTSPFLICHT_MAST,
            PROP__FK_MASTTYP,
            PROP__INBETRIEBNAHME_MAST,
            PROP__VERRECHNUNGSEINHEIT,
            PROP__LETZTE_AENDERUNG,
            PROP__FK_GEOM,
            PROP__IST_VIRTUELLER_STANDORT,
            PROP__BEMERKUNGEN,
            PROP__MONTAGEFIRMA,
            PROP__STANDORTANGABE,
            PROP__FK_KENNZIFFER,
            PROP__LFD_NUMMER,
            PROP__HAUS_NR,
            PROP__DOKUMENTE,
            PROP__LEUCHTEN,
            PROP__GRUENDUNG,
            PROP__ELEK_PRUEFUNG,
            PROP__ERDUNG,
            PROP__MONTEUR,
            PROP__STANDSICHERHEITSPRUEFUNG,
            PROP__VERFAHREN,
            PROP__FOTO,
            PROP__NAECHSTES_PRUEFDATUM,
            PROP__ANSTRICHFARBE,
            PROP__REVISION,
            PROP__ANLAGENGRUPPE,
            PROP__ANBAUTEN
        };

    //~ Instance fields --------------------------------------------------------

    private String plz;
    private TkeyStrassenschluesselCustomBean fk_strassenschluessel;
    private TkeyBezirkCustomBean fk_Stadtbezirk;
    private TkeyMastartCustomBean fk_Mastart;
    private TkeyKlassifizierungCustomBean fk_klassifizierung;
    private Date mastanstrich;
    private Date mastschutz;
    private TkeyUnterhMastCustomBean fk_unterhaltspflicht_mast;
    private TkeyMasttypCustomBean fk_masttyp;
    private Date inbetriebnahme_mast;
    private Boolean verrechnungseinheit;
    private Date letzte_aenderung;
    private GeomCustomBean fk_geom;
    private Boolean ist_virtueller_standort;
    private String bemerkungen;
    private String montagefirma;
    private String standortangabe;
    private TkeyKennzifferCustomBean fk_kennziffer;
    private Integer lfd_nummer;
    private String haus_nr;
    private Collection<DmsUrlCustomBean> dokumente;
    private Collection<TdtaLeuchtenCustomBean> leuchten;

    private String gruendung;
    private Date elek_pruefung;
    private Boolean erdung;
    private String monteur;
    private Date standsicherheitspruefung;
    private String verfahren;
    private DmsUrlCustomBean foto;
    private Date naechstes_pruefdatum;
    private String anstrichfarbe;
    private Date revision;
    private AnlagengruppeCustomBean anlagengruppe;
    private String anbauten;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbzweigdoseCustomBean object.
     */
    public TdtaStandortMastCustomBean() {
    }

    /**
     * Creates a new TdtaStandortMastCustomBean object.
     *
     * @param  id  DOCUMENT ME!
     */
    public TdtaStandortMastCustomBean(final Integer id) {
        setId(id);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static TdtaStandortMastCustomBean createNew() {
        return (TdtaStandortMastCustomBean)createNew(TABLE);
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
    public TkeyStrassenschluesselCustomBean getFk_strassenschluessel() {
        return fk_strassenschluessel;
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
    public TkeyStrassenschluesselCustomBean getStrassenschluessel() {
        return getFk_strassenschluessel();
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
     * @return  DOCUMENT ME!
     */
    public TkeyKennzifferCustomBean getKennziffer() {
        return getFk_kennziffer();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  kennziffer  DOCUMENT ME!
     */
    public void setKennziffer(final TkeyKennzifferCustomBean kennziffer) {
        setFk_kennziffer(kennziffer);
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
    public String getStandortangabe() {
        return standortangabe;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  standortangabe  DOCUMENT ME!
     */
    public void setStandortangabe(final String standortangabe) {
        final String old = this.standortangabe;
        this.standortangabe = standortangabe;
        this.propertyChangeSupport.firePropertyChange(PROP__STANDORTANGABE, old, this.standortangabe);
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
    public Date getMastanstrich() {
        return mastanstrich;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  mastanstrich  DOCUMENT ME!
     */
    public void setMastanstrich(final Date mastanstrich) {
        final Date old = this.mastanstrich;
        this.mastanstrich = mastanstrich;
        this.propertyChangeSupport.firePropertyChange(PROP__MASTANSTRICH, old, this.mastanstrich);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Date getMastschutz() {
        return mastschutz;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  mastschutz  DOCUMENT ME!
     */
    public void setMastschutz(final Date mastschutz) {
        final Date old = this.mastschutz;
        this.mastschutz = mastschutz;
        this.propertyChangeSupport.firePropertyChange(PROP__MASTSCHUTZ, old, this.mastschutz);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Boolean isVerrechnungseinheit() {
        return getVerrechnungseinheit();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  verrechnungseinheit  DOCUMENT ME!
     */
    public void setVerrechnungseinheit(final Boolean verrechnungseinheit) {
        final Boolean old = this.verrechnungseinheit;
        this.verrechnungseinheit = verrechnungseinheit;
        this.propertyChangeSupport.firePropertyChange(PROP__VERRECHNUNGSEINHEIT, old, this.verrechnungseinheit);
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
    public TkeyBezirkCustomBean getFk_stadtbezirk() {
        return fk_Stadtbezirk;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_Stadtbezirk  DOCUMENT ME!
     */
    public void setFk_stadtbezirk(final TkeyBezirkCustomBean fk_Stadtbezirk) {
        final TkeyBezirkCustomBean old = this.fk_Stadtbezirk;
        this.fk_Stadtbezirk = fk_Stadtbezirk;
        this.propertyChangeSupport.firePropertyChange(PROP__FK_STADTBEZIRK, old, this.fk_Stadtbezirk);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public TkeyMastartCustomBean getFk_mastart() {
        return fk_Mastart;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_Mastart  DOCUMENT ME!
     */
    public void setFk_mastart(final TkeyMastartCustomBean fk_Mastart) {
        final TkeyMastartCustomBean old = this.fk_Mastart;
        this.fk_Mastart = fk_Mastart;
        this.propertyChangeSupport.firePropertyChange(PROP__FK_MASTART, old, this.fk_Mastart);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public TkeyKlassifizierungCustomBean getFk_klassifizierung() {
        return fk_klassifizierung;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_klassifizierung  DOCUMENT ME!
     */
    public void setFk_klassifizierung(final TkeyKlassifizierungCustomBean fk_klassifizierung) {
        final TkeyKlassifizierungCustomBean old = this.fk_klassifizierung;
        this.fk_klassifizierung = fk_klassifizierung;
        this.propertyChangeSupport.firePropertyChange(PROP__FK_KLASSIFIZIERUNG, old, this.fk_klassifizierung);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public TkeyUnterhMastCustomBean getFk_unterhaltspflicht_mast() {
        return fk_unterhaltspflicht_mast;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_unterhaltspflicht_mast  DOCUMENT ME!
     */
    public void setFk_unterhaltspflicht_mast(final TkeyUnterhMastCustomBean fk_unterhaltspflicht_mast) {
        final TkeyUnterhMastCustomBean old = this.fk_unterhaltspflicht_mast;
        this.fk_unterhaltspflicht_mast = fk_unterhaltspflicht_mast;
        this.propertyChangeSupport.firePropertyChange(
            PROP__FK_UNTERHALTSPFLICHT_MAST,
            old,
            this.fk_unterhaltspflicht_mast);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public TkeyMasttypCustomBean getFk_masttyp() {
        return fk_masttyp;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_masttyp  DOCUMENT ME!
     */
    public void setFk_masttyp(final TkeyMasttypCustomBean fk_masttyp) {
        final TkeyMasttypCustomBean old = this.fk_masttyp;
        this.fk_masttyp = fk_masttyp;
        this.propertyChangeSupport.firePropertyChange(PROP__FK_MASTTYP, old, this.fk_masttyp);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Date getInbetriebnahme_mast() {
        return inbetriebnahme_mast;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  inbetriebnahme_mast  DOCUMENT ME!
     */
    public void setInbetriebnahme_mast(final Date inbetriebnahme_mast) {
        final Date old = this.inbetriebnahme_mast;
        this.inbetriebnahme_mast = inbetriebnahme_mast;
        this.propertyChangeSupport.firePropertyChange(PROP__INBETRIEBNAHME_MAST, old, this.inbetriebnahme_mast);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Boolean getVerrechnungseinheit() {
        return verrechnungseinheit;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Date getLetzte_aenderung() {
        return letzte_aenderung;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  letzte_aenderung  DOCUMENT ME!
     */
    public void setLetzte_aenderung(final Date letzte_aenderung) {
        final Date old = this.letzte_aenderung;
        this.letzte_aenderung = letzte_aenderung;
        this.propertyChangeSupport.firePropertyChange(PROP__LETZTE_AENDERUNG, old, this.letzte_aenderung);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public GeomCustomBean getFk_geom() {
        return fk_geom;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_geom  DOCUMENT ME!
     */
    public void setFk_geom(final GeomCustomBean fk_geom) {
        final GeomCustomBean old = this.fk_geom;
        this.fk_geom = fk_geom;
        this.propertyChangeSupport.firePropertyChange(PROP__FK_GEOM, old, this.fk_geom);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Boolean getIst_virtueller_standort() {
        return ist_virtueller_standort;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  ist_virtueller_standort  DOCUMENT ME!
     */
    public void setIst_virtueller_standort(final Boolean ist_virtueller_standort) {
        final Boolean old = this.ist_virtueller_standort;
        this.ist_virtueller_standort = ist_virtueller_standort;
        this.propertyChangeSupport.firePropertyChange(PROP__IST_VIRTUELLER_STANDORT, old, this.ist_virtueller_standort);
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
    public String getHaus_nr() {
        return haus_nr;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  haus_nr  DOCUMENT ME!
     */
    public void setHaus_nr(final String haus_nr) {
        final String old = this.haus_nr;
        this.haus_nr = haus_nr;
        this.propertyChangeSupport.firePropertyChange(PROP__HAUS_NR, old, this.haus_nr);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Collection<TdtaLeuchtenCustomBean> getLeuchten() {
        return leuchten;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  leuchten  DOCUMENT ME!
     */
    public void setLeuchten(final Collection<TdtaLeuchtenCustomBean> leuchten) {
        final Collection<TdtaLeuchtenCustomBean> old = this.leuchten;
        this.leuchten = leuchten;
        this.propertyChangeSupport.firePropertyChange(PROP__LEUCHTEN, old, this.leuchten);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getMontagefirma() {
        return montagefirma;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  montagefirma  DOCUMENT ME!
     */
    public void setMontagefirma(final String montagefirma) {
        final String old = this.montagefirma;
        this.montagefirma = montagefirma;
        this.propertyChangeSupport.firePropertyChange(PROP__MONTAGEFIRMA, old, this.montagefirma);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getAnbauten() {
        return anbauten;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  anbauten  DOCUMENT ME!
     */
    public void setAnbauten(final String anbauten) {
        final String old = this.anbauten;
        this.anbauten = anbauten;
        this.propertyChangeSupport.firePropertyChange(PROP__ANBAUTEN, old, this.anbauten);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Boolean isVirtuellerStandort() {
        return getIst_virtueller_standort();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  virtualStandort  DOCUMENT ME!
     */
    public void setVirtuellerStandort(final Boolean virtualStandort) {
        setIst_virtueller_standort(virtualStandort);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean isStandortMast() {
        if ((getMastart() != null) || (getMasttyp() != null) || (getMastanstrich() != null) || (getMastschutz() != null)
                    || ((isVirtuellerStandort() == null)
                        || ((isVirtuellerStandort() != null) && !isVirtuellerStandort()))) {
            return true;
        } else {
            return false;
        }
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
     * @param  lfdNummer  DOCUMENT ME!
     */
    public void setLaufendeNummer(final Integer lfdNummer) {
        setLfd_nummer(lfdNummer);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getHausnummer() {
        return getHaus_nr();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  hausnummer  DOCUMENT ME!
     */
    public void setHausnummer(final String hausnummer) {
        setHaus_nr(hausnummer);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public TkeyMastartCustomBean getMastart() {
        return getFk_mastart();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  mastart  DOCUMENT ME!
     */
    public void setMastart(final TkeyMastartCustomBean mastart) {
        setFk_mastart(mastart);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public TkeyKlassifizierungCustomBean getKlassifizierung() {
        return getFk_klassifizierung();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  klassifizierung  DOCUMENT ME!
     */
    public void setKlassifizierung(final TkeyKlassifizierungCustomBean klassifizierung) {
        setFk_klassifizierung(klassifizierung);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public TkeyUnterhMastCustomBean getUnterhaltspflichtMast() {
        return getFk_unterhaltspflicht_mast();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  unterhaltspflichtMast  DOCUMENT ME!
     */
    public void setUnterhaltspflichtMast(final TkeyUnterhMastCustomBean unterhaltspflichtMast) {
        setFk_unterhaltspflicht_mast(unterhaltspflichtMast);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public TkeyMasttypCustomBean getMasttyp() {
        return getFk_masttyp();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  masttyp  DOCUMENT ME!
     */
    public void setMasttyp(final TkeyMasttypCustomBean masttyp) {
        setFk_masttyp(masttyp);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Date getInbetriebnahmeMast() {
        return getInbetriebnahme_mast();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  inbetriebnahmeMast  DOCUMENT ME!
     */
    public void setInbetriebnahmeMast(final Date inbetriebnahmeMast) {
        setInbetriebnahme_mast(inbetriebnahmeMast);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Date getLetzteAenderung() {
        return getLetzte_aenderung();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  letzteAenderung  DOCUMENT ME!
     */
    public void setLetzteAenderung(final Date letzteAenderung) {
        setLetzte_aenderung(letzteAenderung);
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
        if (other instanceof TdtaStandortMastCustomBean) {
            final TdtaStandortMastCustomBean anEntity = (TdtaStandortMastCustomBean)other;
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
        return "Standort[id=" + getId() + "]";
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public StandortKey getStandortKey() {
        return new StandortKey(getStrassenschluessel(), getKennziffer(), getLaufendeNummer());
    }

    @Override
    public String getKeyString() {
        final Collection<String> strings = new ArrayList<String>();
        if ((getMasttyp() != null) && (getMasttyp().getMasttyp() != null)) {
            strings.add(getMasttyp().getMasttyp());
        }
        if ((getMastart() != null) && (getMastart().getMastart() != null)) {
            strings.add(getMastart().getMastart());
        }
        if (getLaufendeNummer() != null) {
            strings.add(String.valueOf(getLaufendeNummer()));
        }
        return implode(strings.toArray(new String[0]), ", ");
    }

    /**
     * DOCUMENT ME!
     *
     * @param   stringArray  DOCUMENT ME!
     * @param   delimiter    DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private static String implode(final String[] stringArray, final String delimiter) {
        if (stringArray.length == 0) {
            return "";
        } else {
            final StringBuilder sb = new StringBuilder();
            sb.append(stringArray[0]);
            for (int index = 1; index < stringArray.length; index++) {
                sb.append(delimiter);
                sb.append(stringArray[index]);
            }
            return sb.toString();
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
        final List<Integer> nums = new ArrayList<Integer>(6);
//        if (mapIcon != null) {
//            return mapIcon;
//        } else {
//            mapIcon = FeatureAnnotationSymbol.newCenteredFeatureAnnotationSymbol(
//                    MapIcons.icoStandort, MapIcons.icoStandortSelected);
//            return mapIcon;
//        }
        if (isStandortMast()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Standort is Mast");
            }
            if (getLaufendeNummer() != null) {
                nums.add(getLaufendeNummer().intValue());
            }
            Image iconToUse = null;
            if ((getLeuchten() != null) && (getLeuchten().size() > 0)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("There are leuchten at this mast.");
                }
                iconToUse = MapIcons.icoMastWithLeuchte;
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("There are no leuchten at this mast.");
                }
                iconToUse = MapIcons.icoMast;
            }

            final Image i1 = IconUtils.mergeNumbersToIcon(iconToUse, nums, Color.RED);
//                Image i2 = IconUtils.mergeNumbersToIcon(MapIcons.icoMastWithLeuchteSelected, nums, Color.BLUE);

            return FeatureAnnotationSymbol.newCustomSweetSpotFeatureAnnotationSymbol(
                    i1,
                    null,
                    IconUtils.calcNewSweepSpotX(0.5, iconToUse, i1),
                    IconUtils.calcNewSweepSpotY(0.5, iconToUse, i1));
//            } else {
//
////                return FeatureAnnotationSymbol.newCenteredFeatureAnnotationSymbol(
////                        MapIcons.icoMast, null);
//            }
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Standort is no Mast. There fore must be Leuchte");
            }
            if (getLeuchten() != null) {
                if (getLeuchten().size() != 1) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Standort is Leuchte, but getLeuchten().size() is != 1! (It is "
                                    + getLeuchten().size() + ")");
                    }
                }
                for (final TdtaLeuchtenCustomBean l : getLeuchten()) {
                    if (l.getLaufendeNummer() != null) {
                        nums.add(l.getLaufendeNummer().intValue());
                        break;
                    }
                }
            }
            final Image i1 = IconUtils.mergeNumbersToIcon(MapIcons.icoLeuchte, nums, Color.RED);
//            Image i2 = IconUtils.mergeNumbersToIcon(MapIcons.icoLeuchteSelected, nums, Color.BLUE);
            return FeatureAnnotationSymbol.newCustomSweetSpotFeatureAnnotationSymbol(
                    i1,
                    null,
                    IconUtils.calcNewSweepSpotX(0.5, MapIcons.icoLeuchte, i1),
                    IconUtils.calcNewSweepSpotY(0.5, MapIcons.icoLeuchte, i1));
        }
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("PropertyChange Standort");
        }
        if (!isStandortMast() && (evt.getSource() instanceof TdtaLeuchtenCustomBean)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Property of Leuchte has changed. Changing Standort property");
            }
            if ((evt.getPropertyName() != null)
                        && evt.getPropertyName().equals(TdtaLeuchtenCustomBean.PROP__FK_STRASSENSCHLUESSEL)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Strassenschluessel changed");
                }
                setStrassenschluessel((TkeyStrassenschluesselCustomBean)evt.getNewValue());
            } else if ((evt.getPropertyName() != null)
                        && evt.getPropertyName().equals(TdtaLeuchtenCustomBean.PROP__FK_KENNZIFFER)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Kennziffer changed");
                }
                setKennziffer((TkeyKennzifferCustomBean)evt.getNewValue());
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Unkown property. Nothing to change");
                }
            }
        } else if ((evt.getSource() != null) && (evt.getSource() instanceof TdtaStandortMastCustomBean)) {
            super.propertyChange(evt);
            if (!evt.getPropertyName().equals(TdtaStandortMastCustomBean.PROP__LETZTE_AENDERUNG)) {
                setLetzteAenderung(new Date());
            }
        }
    }

    @Override
    public GeomCustomBean getGeometrie() {
        return getFk_geom();
    }

    @Override
    public void setGeometrie(final GeomCustomBean geometrie) {
        setFk_geom(geometrie);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getGruendung() {
        return gruendung;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  gruendung  DOCUMENT ME!
     */
    public void setGruendung(final String gruendung) {
        final String old = this.gruendung;
        this.gruendung = gruendung;
        this.propertyChangeSupport.firePropertyChange(PROP__GRUENDUNG, old, this.gruendung);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Date getElek_pruefung() {
        return elek_pruefung;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  elek_pruefung  DOCUMENT ME!
     */
    public void setElek_pruefung(final Date elek_pruefung) {
        final Date old = this.elek_pruefung;
        this.elek_pruefung = elek_pruefung;
        this.propertyChangeSupport.firePropertyChange(PROP__ELEK_PRUEFUNG, old, this.elek_pruefung);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Boolean getErdung() {
        return erdung;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  erdung  DOCUMENT ME!
     */
    public void setErdung(final Boolean erdung) {
        final Boolean old = this.erdung;
        this.erdung = erdung;
        this.propertyChangeSupport.firePropertyChange(PROP__ERDUNG, old, this.erdung);
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
    public Date getStandsicherheitspruefung() {
        return standsicherheitspruefung;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  standsicherheitspruefung  DOCUMENT ME!
     */
    public void setStandsicherheitspruefung(final Date standsicherheitspruefung) {
        final Date old = this.standsicherheitspruefung;
        this.standsicherheitspruefung = standsicherheitspruefung;
        this.propertyChangeSupport.firePropertyChange(
            PROP__STANDSICHERHEITSPRUEFUNG,
            old,
            this.standsicherheitspruefung);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getVerfahren() {
        return verfahren;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  verfahren  DOCUMENT ME!
     */
    public void setVerfahren(final String verfahren) {
        final String old = this.verfahren;
        this.verfahren = verfahren;
        this.propertyChangeSupport.firePropertyChange(PROP__VERFAHREN, old, this.verfahren);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public DmsUrlCustomBean getFoto() {
        return foto;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  foto  DOCUMENT ME!
     */
    public void setFoto(final DmsUrlCustomBean foto) {
        final DmsUrlCustomBean old = this.foto;
        this.foto = foto;
        this.propertyChangeSupport.firePropertyChange(PROP__FOTO, old, this.foto);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Date getNaechstes_pruefdatum() {
        return naechstes_pruefdatum;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  naechstes_pruefdatum  DOCUMENT ME!
     */
    public void setNaechstes_pruefdatum(final Date naechstes_pruefdatum) {
        final Date old = this.naechstes_pruefdatum;
        this.naechstes_pruefdatum = naechstes_pruefdatum;
        this.propertyChangeSupport.firePropertyChange(PROP__NAECHSTES_PRUEFDATUM, old, this.naechstes_pruefdatum);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getAnstrichfarbe() {
        return anstrichfarbe;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  anstrichfarbe  DOCUMENT ME!
     */
    public void setAnstrichfarbe(final String anstrichfarbe) {
        final String old = this.anstrichfarbe;
        this.anstrichfarbe = anstrichfarbe;
        this.propertyChangeSupport.firePropertyChange(PROP__ANSTRICHFARBE, old, this.anstrichfarbe);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Date getRevision() {
        return revision;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  revision  DOCUMENT ME!
     */
    public void setRevision(final Date revision) {
        final Date old = this.revision;
        this.revision = revision;
        this.propertyChangeSupport.firePropertyChange(PROP__REVISION, old, this.revision);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public AnlagengruppeCustomBean getAnlagengruppe() {
        return anlagengruppe;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  anlagengruppe  DOCUMENT ME!
     */
    public void setAnlagengruppe(final AnlagengruppeCustomBean anlagengruppe) {
        final AnlagengruppeCustomBean old = this.anlagengruppe;
        this.anlagengruppe = anlagengruppe;
        this.propertyChangeSupport.firePropertyChange(PROP__ANLAGENGRUPPE, old, this.anlagengruppe);
    }

    @Override
    public CidsBean persist() throws Exception {
        determineNextLaufendenummer(-1);
        setLeuchtenPropertiesDependingOnStandort();
        return super.persist();
    }

    /**
     * DOCUMENT ME!
     *
     * @param   minimalNumber  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    private void determineNextLaufendenummer(final Integer minimalNumber) throws ActionNotSuccessfulException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("determine next laufendenummer");
        }
        // ToDo would be cooler to use the objects itself as parameter;
        String strassenschluessel = null;
        if ((getStrassenschluessel() == null)
                    || ((strassenschluessel = getStrassenschluessel().getPk()) == null)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("strassenschluessel must be != null");
            }
        }
        Integer kennziffer = null;
        if ((getKennziffer() == null)
                    || ((kennziffer = getKennziffer().getKennziffer()) == null)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("kennziffer must be != null");
            }
        }
        if ((kennziffer != null) && (strassenschluessel != null)) {
            try {
                final List<Integer> highestNumbers = (List<Integer>)CidsBroker.getInstance()
                            .executeServerSearch(new HighestLfdNummerSearch(
                                        strassenschluessel,
                                        kennziffer));

                final Integer highestNumber = (highestNumbers.isEmpty()) ? null : highestNumbers.get(0);
                if ((highestNumber == null)) {
                    if (minimalNumber > -1) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("there is no highest laufende nummer using minimal: " + minimalNumber);
                        }
                        setLaufendeNummer(minimalNumber);
                    } else {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("there is no highest laufende nummer and no minimalNumber using 0.");
                        }
                        setLaufendeNummer(0);
                    }
                } else {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("the highest laufende nummer is: " + highestNumber);
                    }
                    if ((minimalNumber > -1) && (minimalNumber > highestNumber)) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Minimal " + minimalNumber
                                        + " is greater than highest number using minimal number");
                        }
                        setLaufendeNummer(minimalNumber);
                    } else {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Minimal is -1 or smaller than highest number: " + minimalNumber);
                            LOG.debug("using highestnumber +1 ");
                        }
                        // ToDo best way to add Short ?
                        setLaufendeNummer(highestNumber + ((short)1));
                    }
                }
                return;
            } catch (Exception ex) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Error while querying entity", ex);
                }
                throw new ActionNotSuccessfulException("Error while querying highest laufendenummer", ex);
            }
        }
        throw new ActionNotSuccessfulException(
            "Not possible to determine laufendenummer kennziffer and strassenschlüssel of standort must be set.");
    }

    /**
     * DOCUMENT ME!
     */
    private void setLeuchtenPropertiesDependingOnStandort() {
        final Collection<TdtaLeuchtenCustomBean> leuchten = getLeuchten();
        if (leuchten != null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Setting properties of Leuchte.");
            }
            // ToDo check if there is only one Leuchte per no mast standort
            for (final TdtaLeuchtenCustomBean curLeuchte : leuchten) {
                if (isStandortMast()) {
                    curLeuchte.setStrassenschluessel(getStrassenschluessel());
                    curLeuchte.setKennziffer(getKennziffer());
                }
                curLeuchte.setLaufendeNummer(getLaufendeNummer());
            }
        }
    }
}
