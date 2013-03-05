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
        DEFAULT_UNTERHALT.setPk((short)0);
        DEFAULT_UNTERHALT.setUnterhaltMast("öffentl. Beleuchtung");
    }

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            TdtaStandortMastCustomBean.class);

    public static final String TABLE = "tdta_standort_mast";

    private static final String PROP__ID = "id";
    private static final String PROP__PLZ = "PLZ";
    private static final String PROP__STRASSENSCHLUESSEL = "strassenschluessel";
    private static final String PROP__FK_STADTBEZIRK = "fk_Stadtbezirk";
    private static final String PROP__FK_MASTART = "fk_Mastart";
    private static final String PROP__FK_KLASSIFIZIERUNG = "fk_Klassifizierung";
    private static final String PROP__MASTANSTRICH = "Mastanstrich";
    private static final String PROP__MASTSCHUTZ = "Mastschutz";
    private static final String PROP__FK_UNTERHALTSPFLICHT_MAST = "fk_Unterhaltspflicht_Mast";
    private static final String PROP__FK_MASTTYP = "fk_Masttyp";
    private static final String PROP__INBETRIEBNAHME_MAST = "Inbetriebnahme_Mast";
    private static final String PROP__VERRECHNUNGSEINHEIT = "Verrechnungseinheit";
    private static final String PROP__LETZTE_AENDERUNG = "letzte_aenderung";
    private static final String PROP__FK_GEOM = "fk_geom";
    private static final String PROP__IST_VIRTUELLER_STANDORT = "ist_virtueller_standort";
    private static final String PROP__BEMERKUNGEN = "Bemerkungen";
    private static final String PROP__MONTAGEFIRMA = "Montagefirma";
    private static final String PROP__STANDORTANGABE = "Standortangabe";
    private static final String PROP__KENNZIFFER = "Kennziffer";
    private static final String PROP__STADTBEZIRK = "Stadtbezirk";
    private static final String PROP__LFD_NUMMER = "lfd_Nummer";
    private static final String PROP__HAUS_NR = "Haus_Nr";
    private static final String PROP__DOKUMENTE = "dokumente";
    private static final String PROP__LEUCHTEN = "leuchten";

    private static final String[] PROPERTY_NAMES = new String[] { PROP__ID, };

    //~ Instance fields --------------------------------------------------------

    private Long id;
    private String plz;
    private TkeyStrassenschluesselCustomBean strassenschluessel;
    private Short fk_Stadtbezirk;
    private TkeyMastartCustomBean fk_Mastart;
    private TkeyKlassifizierungCustomBean fk_Klassifizierung;
    private Date mastanstrich;
    private Date mastschutz;
    private TkeyUnterhMastCustomBean fk_Unterhaltspflicht_Mast;
    private TkeyMasttypCustomBean fk_Masttyp;
    private Date inbetriebnahme_Mast;
    private Boolean verrechnungseinheit;
    private Date letzte_aenderung;
    private GeomCustomBean fk_geom;
    private Boolean ist_virtueller_standort;
    private String bemerkungen;
    private String montagefirma;
    private String standortangabe;
    private TkeyKennzifferCustomBean kennziffer;
    private TkeyBezirkCustomBean stadtbezirk;
    private Short lfd_Nummer;
    private String haus_Nr;
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
    public TdtaStandortMastCustomBean(final Long id) {
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
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long id) {
        final Long old = this.id;
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
        return stadtbezirk;
    }

    @Override
    public void setStadtbezirk(final TkeyBezirkCustomBean stadtbezirk) {
        final TkeyBezirkCustomBean old = this.stadtbezirk;
        this.stadtbezirk = stadtbezirk;
        this.propertyChangeSupport.firePropertyChange(PROP__STADTBEZIRK, old, this.stadtbezirk);
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
    public Short getFk_Stadtbezirk() {
        return fk_Stadtbezirk;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_Stadtbezirk  DOCUMENT ME!
     */
    public void setFk_Stadtbezirk(final Short fk_Stadtbezirk) {
        final Short old = this.fk_Stadtbezirk;
        this.fk_Stadtbezirk = fk_Stadtbezirk;
        this.propertyChangeSupport.firePropertyChange(PROP__FK_STADTBEZIRK, old, this.fk_Stadtbezirk);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public TkeyMastartCustomBean getFk_Mastart() {
        return fk_Mastart;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_Mastart  DOCUMENT ME!
     */
    public void setFk_Mastart(final TkeyMastartCustomBean fk_Mastart) {
        final TkeyMastartCustomBean old = this.fk_Mastart;
        this.fk_Mastart = fk_Mastart;
        this.propertyChangeSupport.firePropertyChange(PROP__FK_MASTART, old, this.fk_Mastart);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public TkeyKlassifizierungCustomBean getFk_Klassifizierung() {
        return fk_Klassifizierung;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_Klassifizierung  DOCUMENT ME!
     */
    public void setFk_Klassifizierung(final TkeyKlassifizierungCustomBean fk_Klassifizierung) {
        final TkeyKlassifizierungCustomBean old = this.fk_Klassifizierung;
        this.fk_Klassifizierung = fk_Klassifizierung;
        this.propertyChangeSupport.firePropertyChange(PROP__FK_KLASSIFIZIERUNG, old, this.fk_Klassifizierung);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public TkeyUnterhMastCustomBean getFk_Unterhaltspflicht_Mast() {
        return fk_Unterhaltspflicht_Mast;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_Unterhaltspflicht_Mast  DOCUMENT ME!
     */
    public void setFk_Unterhaltspflicht_Mast(final TkeyUnterhMastCustomBean fk_Unterhaltspflicht_Mast) {
        final TkeyUnterhMastCustomBean old = this.fk_Unterhaltspflicht_Mast;
        this.fk_Unterhaltspflicht_Mast = fk_Unterhaltspflicht_Mast;
        this.propertyChangeSupport.firePropertyChange(
            PROP__FK_UNTERHALTSPFLICHT_MAST,
            old,
            this.fk_Unterhaltspflicht_Mast);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public TkeyMasttypCustomBean getFk_Masttyp() {
        return fk_Masttyp;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_Masttyp  DOCUMENT ME!
     */
    public void setFk_Masttyp(final TkeyMasttypCustomBean fk_Masttyp) {
        final TkeyMasttypCustomBean old = this.fk_Masttyp;
        this.fk_Masttyp = fk_Masttyp;
        this.propertyChangeSupport.firePropertyChange(PROP__FK_MASTTYP, old, this.fk_Masttyp);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Date getInbetriebnahme_Mast() {
        return inbetriebnahme_Mast;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  inbetriebnahme_Mast  DOCUMENT ME!
     */
    public void setInbetriebnahme_Mast(final Date inbetriebnahme_Mast) {
        final Date old = this.inbetriebnahme_Mast;
        this.inbetriebnahme_Mast = inbetriebnahme_Mast;
        this.propertyChangeSupport.firePropertyChange(PROP__INBETRIEBNAHME_MAST, old, this.inbetriebnahme_Mast);
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
    public Short getLfd_Nummer() {
        return lfd_Nummer;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  lfd_Nummer  DOCUMENT ME!
     */
    public void setLfd_Nummer(final Short lfd_Nummer) {
        final Short old = this.lfd_Nummer;
        this.lfd_Nummer = lfd_Nummer;
        this.propertyChangeSupport.firePropertyChange(PROP__LFD_NUMMER, old, this.lfd_Nummer);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getHaus_Nr() {
        return haus_Nr;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  haus_Nr  DOCUMENT ME!
     */
    public void setHaus_Nr(final String haus_Nr) {
        final String old = this.haus_Nr;
        this.haus_Nr = haus_Nr;
        this.propertyChangeSupport.firePropertyChange(PROP__HAUS_NR, old, this.haus_Nr);
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
    public Short getLaufendeNummer() {
        return getLfd_Nummer();
    }

    @Override
    public void setLaufendeNummer(final Short lfdNummer) {
        setLfd_Nummer(lfdNummer);
    }

    @Override
    public String getHausnummer() {
        return getHaus_Nr();
    }

    @Override
    public void setHausnummer(final String hausnummer) {
        setHaus_Nr(hausnummer);
    }

    @Override
    public TkeyMastartCustomBean getMastart() {
        return getFk_Mastart();
    }

    @Override
    public void setMastart(final TkeyMastartCustomBean mastart) {
        setFk_Mastart(mastart);
    }

    @Override
    public TkeyKlassifizierungCustomBean getKlassifizierung() {
        return getFk_Klassifizierung();
    }

    @Override
    public void setKlassifizierung(final TkeyKlassifizierungCustomBean klassifizierung) {
        setFk_Klassifizierung(klassifizierung);
    }

    @Override
    public TkeyUnterhMastCustomBean getUnterhaltspflichtMast() {
        return getFk_Unterhaltspflicht_Mast();
    }

    @Override
    public void setUnterhaltspflichtMast(final TkeyUnterhMastCustomBean unterhaltspflichtMast) {
        setFk_Unterhaltspflicht_Mast(unterhaltspflichtMast);
    }

    @Override
    public TkeyMasttypCustomBean getMasttyp() {
        return getFk_Masttyp();
    }

    @Override
    public void setMasttyp(final TkeyMasttypCustomBean masttyp) {
        setFk_Masttyp(masttyp);
    }

    @Override
    public Date getInbetriebnahmeMast() {
        return getInbetriebnahme_Mast();
    }

    @Override
    public void setInbetriebnahmeMast(final Date inbetriebnahmeMast) {
        setInbetriebnahme_Mast(inbetriebnahmeMast);
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
            System.out.println("Standort is Mast");
            if (getLaufendeNummer() != null) {
                nums.add(getLaufendeNummer().intValue());
            }
            Image iconToUse = null;
            if ((getLeuchten() != null) && (getLeuchten().size() > 0)) {
                System.out.println("There are leuchten at this mast.");
                iconToUse = MapIcons.icoMastWithLeuchte;
            } else {
                System.out.println("There are no leuchten at this mast.");
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
            System.out.println("Standort is no Mast. There fore must be Leuchte");
            if (getLeuchten() != null) {
                if (getLeuchten().size() != 1) {
                    System.out.println("Standort is Leuchte, but getLeuchten().size() is != 1! (It is "
                                + getLeuchten().size() + ")");
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
        System.out.println("PropertyChange Standort");
        if (!isStandortMast() && (evt.getSource() instanceof TdtaLeuchteCustomBean)) {
            System.out.println("Property of Leuchte has changed. Changing Standort property");
            if ((evt.getPropertyName() != null)
                        && evt.getPropertyName().equals(TdtaLeuchteCustomBean.PROP_STRASSENSCHLUESSEL)) {
                System.out.println("Strassenschluessel changed");
                setStrassenschluessel((TkeyStrassenschluesselCustomBean)evt.getNewValue());
            } else if ((evt.getPropertyName() != null)
                        && evt.getPropertyName().equals(TdtaLeuchteCustomBean.PROP_KENNZIFFER)) {
                System.out.println("Kennziffer changed");
                setKennziffer((TkeyKennzifferCustomBean)evt.getNewValue());
            } else {
                System.out.println("Unkown property. Nothing to change");
            }
        } else if (evt.getSource().equals(this) && !evt.getPropertyName().equals(PROP_ID)) {
            System.out.println("this entity has changed and the property was not the id");
            setWasModified(true);
        }
    }
}
