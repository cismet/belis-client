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

import java.awt.Color;
import java.awt.Image;

import java.beans.PropertyChangeEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import de.cismet.belis.broker.CidsBroker;

import de.cismet.belisEE.entity.Standort;

import de.cismet.belisEE.mapicons.MapIcons;

import de.cismet.belisEE.util.StandortKey;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cismap.commons.gui.piccolo.FeatureAnnotationSymbol;
import de.cismet.cismap.commons.tools.IconUtils;

import de.cismet.commons.server.entity.GeoBaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class TdtaStandortMastCustomBean extends GeoBaseEntity implements Standort {

    //~ Static fields/initializers ---------------------------------------------

    public static TkeyUnterhMastCustomBean DEFAULT_UNTERHALT;

    static {
        DEFAULT_UNTERHALT = new TkeyUnterhMastCustomBean();
//        DEFAULT_UNTERHALT.setPk((Integer)0);
//        DEFAULT_UNTERHALT.setUnterhaltMast("öffentl. Beleuchtung");
    }

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            TdtaStandortMastCustomBean.class);

    public static final String TABLE = "tdta_standort_mast";

    private static final String PROP__PLZ = "plz";
    private static final String PROP__FK_STRASSENSCHLUESSEL = "fk_strassenschluessel";
    private static final String PROP__FK_STADTBEZIRK = "fk_stadtbezirk";
    private static final String PROP__FK_MASTART = "fk_mastart";
    private static final String PROP__FK_KLASSIFIZIERUNG = "fk_klassifizierung";
    private static final String PROP__MASTANSTRICH = "mastanstrich";
    private static final String PROP__MASTSCHUTZ = "mastschutz";
    private static final String PROP__FK_UNTERHALTSPFLICHT_MAST = "fk_unterhaltspflicht_mast";
    private static final String PROP__FK_MASTTYP = "fk_masttyp";
    private static final String PROP__INBETRIEBNAHME_MAST = "inbetriebnahme_Mast";
    private static final String PROP__VERRECHNUNGSEINHEIT = "verrechnungseinheit";
    private static final String PROP__LETZTE_AENDERUNG = "letzte_aenderung";
    private static final String PROP__FK_GEOM = "fk_geom";
    private static final String PROP__IST_VIRTUELLER_STANDORT = "ist_virtueller_standort";
    private static final String PROP__BEMERKUNGEN = "bemerkungen";
    private static final String PROP__MONTAGEFIRMA = "montagefirma";
    private static final String PROP__STANDORTANGABE = "standortangabe";
    private static final String PROP__FK_KENNZIFFER = "fk_kennziffer";
    private static final String PROP__LFD_NUMMER = "lfd_nummer";
    private static final String PROP__HAUS_NR = "haus_nr";
    private static final String PROP__DOKUMENTE = "dokumente";
    private static final String PROP__LEUCHTEN = "leuchten";

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
            PROP__LEUCHTEN
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
    private Collection<TdtaLeuchteCustomBean> leuchten;

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
        try {
            final TdtaStandortMastCustomBean bean = (TdtaStandortMastCustomBean)CidsBean.createNewCidsBeanFromTableName(
                    CidsBroker.BELIS_DOMAIN,
                    TABLE);
            bean.setVirtuellerStandort(false);
            return bean;
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
    public String getPlz() {
        return plz;
    }

    @Override
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

    @Override
    public TkeyStrassenschluesselCustomBean getStrassenschluessel() {
        return getFk_strassenschluessel();
    }

    @Override
    public void setStrassenschluessel(final TkeyStrassenschluesselCustomBean fk_strassenschluessel) {
        setFk_strassenschluessel(fk_strassenschluessel);
    }

    @Override
    public TkeyKennzifferCustomBean getKennziffer() {
        return getFk_kennziffer();
    }

    @Override
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

    @Override
    public String getStandortangabe() {
        return standortangabe;
    }

    @Override
    public void setStandortangabe(final String standortangabe) {
        final String old = this.standortangabe;
        this.standortangabe = standortangabe;
        this.propertyChangeSupport.firePropertyChange(PROP__STANDORTANGABE, old, this.standortangabe);
    }

    @Override
    public TkeyBezirkCustomBean getStadtbezirk() {
        return getFk_stadtbezirk();
    }

    @Override
    public void setStadtbezirk(final TkeyBezirkCustomBean stadtbezirk) {
        setFk_stadtbezirk(stadtbezirk);
    }

    @Override
    public Date getMastanstrich() {
        return mastanstrich;
    }

    @Override
    public void setMastanstrich(final Date mastanstrich) {
        final Date old = this.mastanstrich;
        this.mastanstrich = mastanstrich;
        this.propertyChangeSupport.firePropertyChange(PROP__MASTANSTRICH, old, this.mastanstrich);
    }

    @Override
    public Date getMastschutz() {
        return mastschutz;
    }

    @Override
    public void setMastschutz(final Date mastschutz) {
        final Date old = this.mastschutz;
        this.mastschutz = mastschutz;
        this.propertyChangeSupport.firePropertyChange(PROP__MASTSCHUTZ, old, this.mastschutz);
    }

    @Override
    public boolean isVerrechnungseinheit() {
        return verrechnungseinheit;
    }

    @Override
    public void setVerrechnungseinheit(final boolean verrechnungseinheit) {
        final Boolean old = this.verrechnungseinheit;
        this.verrechnungseinheit = verrechnungseinheit;
        this.propertyChangeSupport.firePropertyChange(PROP__VERRECHNUNGSEINHEIT, old, this.verrechnungseinheit);
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

    @Override
    public Collection<TdtaLeuchteCustomBean> getLeuchten() {
        return leuchten;
    }

    @Override
    public void setLeuchten(final Collection<TdtaLeuchteCustomBean> leuchten) {
        final Collection<TdtaLeuchteCustomBean> old = this.leuchten;
        this.leuchten = leuchten;
        this.propertyChangeSupport.firePropertyChange(PROP__LEUCHTEN, old, this.leuchten);
    }

    @Override
    public String getMontagefirma() {
        return montagefirma;
    }

    @Override
    public void setMontagefirma(final String montagefirma) {
        final String old = this.montagefirma;
        this.montagefirma = montagefirma;
        this.propertyChangeSupport.firePropertyChange(PROP__MONTAGEFIRMA, old, this.montagefirma);
    }

    @Override
    public Boolean isVirtuellerStandort() {
        return getIst_virtueller_standort();
    }

    @Override
    public void setVirtuellerStandort(final Boolean virtualStandort) {
        setIst_virtueller_standort(virtualStandort);
    }

    @Override
    public boolean isStandortMast() {
        if ((getMastart() != null) || (getMasttyp() != null) || (getMastanstrich() != null) || (getMastschutz() != null)
                    || ((isVirtuellerStandort() == null)
                        || ((isVirtuellerStandort() != null) && !isVirtuellerStandort()))) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Integer getLaufendeNummer() {
        return getLfd_nummer();
    }

    @Override
    public void setLaufendeNummer(final Integer lfdNummer) {
        setLfd_nummer(lfdNummer);
    }

    @Override
    public String getHausnummer() {
        return getHaus_nr();
    }

    @Override
    public void setHausnummer(final String hausnummer) {
        setHaus_nr(hausnummer);
    }

    @Override
    public TkeyMastartCustomBean getMastart() {
        return getFk_mastart();
    }

    @Override
    public void setMastart(final TkeyMastartCustomBean mastart) {
        setFk_mastart(mastart);
    }

    @Override
    public TkeyKlassifizierungCustomBean getKlassifizierung() {
        return getFk_klassifizierung();
    }

    @Override
    public void setKlassifizierung(final TkeyKlassifizierungCustomBean klassifizierung) {
        setFk_klassifizierung(klassifizierung);
    }

    @Override
    public TkeyUnterhMastCustomBean getUnterhaltspflichtMast() {
        return getFk_unterhaltspflicht_mast();
    }

    @Override
    public void setUnterhaltspflichtMast(final TkeyUnterhMastCustomBean unterhaltspflichtMast) {
        setFk_unterhaltspflicht_mast(unterhaltspflichtMast);
    }

    @Override
    public TkeyMasttypCustomBean getMasttyp() {
        return getFk_masttyp();
    }

    @Override
    public void setMasttyp(final TkeyMasttypCustomBean masttyp) {
        setFk_masttyp(masttyp);
    }

    @Override
    public Date getInbetriebnahmeMast() {
        return getInbetriebnahme_mast();
    }

    @Override
    public void setInbetriebnahmeMast(final Date inbetriebnahmeMast) {
        setInbetriebnahme_mast(inbetriebnahmeMast);
    }

    @Override
    public Date getLetzteAenderung() {
        return getLetzte_aenderung();
    }

    @Override
    public void setLetzteAenderung(final Date letzteAenderung) {
        setLetzte_aenderung(letzteAenderung);
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
        return "de.cismet.belis.entity.Standort[id=" + getId() + "]";
    }

    @Override
    public StandortKey getStandortKey() {
        return new StandortKey(getStrassenschluessel(), getKennziffer(), getLaufendeNummer());
    }

    // new version
// @Override
// public String getKeyString() {
// String masttyp = "";
// String mastart = "";
// String lfd="";
// if (getMasttyp() != null && getMasttyp().getMasttyp() != null) {
// masttyp = getMasttyp().getMasttyp();
// }
// if (getMastart() != null && getMastart().getMastart() != null) {
// mastart = getMastart().getMastart();
// }
// if (getLaufendeNummer() != null) {
// lfd = getLaufendeNummer().toString();
// }
// if (mastart.length() > 0 && masttyp.length() > 0 && lfd.length() > 0) {
// return lfd+", "+mastart + ", " + masttyp;
// } else if (mastart.length() > 0 && lfd.length() > 0) {
// return lfd+", "+mastart;
// } else if (masttyp.length() > 0 && lfd.length() > 0) {
// return lfd+", "+masttyp;
// } else if (masttyp.length() > 0 && mastart.length() > 0) {
// return mastart+", "+masttyp;
// } else if (masttyp.length() > 0) {
// return masttyp;
// } else if (mastart.length() > 0) {
// return mastart;
// } else if (lfd.length() > 0) {
// return lfd;
// } else {
// return "";
// }
// }

    @Override
    public String getKeyString() {
        String masttyp = "";
        String mastart = "";
        if ((getMasttyp() != null) && (getMasttyp().getMasttyp() != null)) {
            masttyp = getMasttyp().getMasttyp();
        }
        if ((getMastart() != null) && (getMastart().getMastart() != null)) {
            mastart = getMastart().getMastart();
        }
        if ((mastart.length() > 0) && (masttyp.length() > 0)) {
            return mastart + ", " + masttyp;
        } else if (mastart.length() > 0) {
            return mastart;
        } else if (masttyp.length() > 0) {
            return masttyp;
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
                for (final TdtaLeuchteCustomBean l : getLeuchten()) {
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
        if (!isStandortMast() && (evt.getSource() instanceof TdtaLeuchteCustomBean)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Property of Leuchte has changed. Changing Standort property");
            }
            if ((evt.getPropertyName() != null)
                        && evt.getPropertyName().equals(TdtaLeuchteCustomBean.PROP_STRASSENSCHLUESSEL)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Strassenschluessel changed");
                }
                setStrassenschluessel((TkeyStrassenschluesselCustomBean)evt.getNewValue());
            } else if ((evt.getPropertyName() != null)
                        && evt.getPropertyName().equals(TdtaLeuchteCustomBean.PROP_KENNZIFFER)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Kennziffer changed");
                }
                setKennziffer((TkeyKennzifferCustomBean)evt.getNewValue());
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Unkown property. Nothing to change");
                }
            }
        } else if (evt.getSource().equals(this) && !evt.getPropertyName().equals(PROP_ID)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("this entity has changed and the property was not the id");
            }
            setWasModified(true);
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
}
