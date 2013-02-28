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
import java.util.Date;
import java.util.List;
import java.util.Set;

import de.cismet.belisEE.mapicons.MapIcons;

import de.cismet.belisEE.util.StandortKey;

import de.cismet.belisEEold.entity.Standort;

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

    //~ Instance fields --------------------------------------------------------

    protected Set<DmsUrlCustomBean> dokumente;

    private String plz;
    private TkeyStrassenschluesselCustomBean strassenschluessel;
    private TkeyKennzifferCustomBean kennziffer;
    private Short laufendeNummer;
    private String standortangabe;
    private String hausnummer;
    private TkeyBezirkCustomBean stadtbezirk;
    private TkeyMastartCustomBean mastart;
    private TkeyKlassifizierungCustomBean klassifizierung;
    private Date mastanstrich;
    private Date mastschutz;
    private TkeyUnterhMastCustomBean unterhaltspflichtMast;
    private TkeyMasttypCustomBean masttyp;
    private String montagefirma;
    private Date inbetriebnahmeMast;
    private boolean verrechnungseinheit;
    private String bemerkungen;
    private Date letzteAenderung;
    private Long id;
    private Set<TdtaLeuchteCustomBean> leuchten;
    private Boolean virtuellerStandort;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new TdtaStandortMastCustomBean object.
     */
    public TdtaStandortMastCustomBean() {
        super();
        setVirtuellerStandort(false);
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

    @Override
    public Set<DmsUrlCustomBean> getDokumente() {
        return dokumente;
    }

    @Override
    public void setDokumente(final Set<DmsUrlCustomBean> dokumente) {
        this.dokumente = dokumente;
    }

    @Override
    public Boolean isVirtuellerStandort() {
        return virtuellerStandort;
    }

    @Override
    public void setVirtuellerStandort(final Boolean virtualStandort) {
        this.virtuellerStandort = virtualStandort;
        getPropertyChangeSupport().firePropertyChange(PROP_VIRTUELLER_STANDORT, null, isVirtuellerStandort());
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
    public Set<TdtaLeuchteCustomBean> getLeuchten() {
        return leuchten;
    }

    @Override
    public void setLeuchten(final Set<TdtaLeuchteCustomBean> leuchten) {
        this.leuchten = leuchten;
        getPropertyChangeSupport().firePropertyChange(PROP_LEUCHTEN, null, getLeuchten());
    }

    @Override
    public String getPlz() {
        return plz;
    }

    @Override
    public void setPlz(final String plz) {
        this.plz = plz;
        getPropertyChangeSupport().firePropertyChange(PROP_PLZ, null, getPlz());
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
    public void setLaufendeNummer(final Short lfdNummer) {
        this.laufendeNummer = lfdNummer;
        getPropertyChangeSupport().firePropertyChange(PROP_LAUFENDENUMMER, null, getLaufendeNummer());
    }

    @Override
    public String getStandortangabe() {
        return standortangabe;
    }

    @Override
    public void setStandortangabe(final String standortangabe) {
        this.standortangabe = standortangabe;
        getPropertyChangeSupport().firePropertyChange(PROP_STANDORTANGABE, null, getStandortangabe());
    }

    @Override
    public String getHausnummer() {
        return hausnummer;
    }

    @Override
    public void setHausnummer(final String hausnummer) {
        this.hausnummer = hausnummer;
        getPropertyChangeSupport().firePropertyChange(PROP_HAUSNUMMER, null, getHausnummer());
    }

    @Override
    public TkeyBezirkCustomBean getStadtbezirk() {
        return stadtbezirk;
    }

    @Override
    public void setStadtbezirk(final TkeyBezirkCustomBean stadtbezirk) {
        this.stadtbezirk = stadtbezirk;
        getPropertyChangeSupport().firePropertyChange(PROP_STADTBEZIRK, null, getStadtbezirk());
    }

    @Override
    public TkeyMastartCustomBean getMastart() {
        return mastart;
    }

    @Override
    public void setMastart(final TkeyMastartCustomBean mastart) {
        final TkeyMastartCustomBean oldMastart = getMastart();
        this.mastart = mastart;
        getPropertyChangeSupport().firePropertyChange(PROP_MASTART, oldMastart, mastart);
    }

    @Override
    public TkeyKlassifizierungCustomBean getKlassifizierung() {
        return klassifizierung;
    }

    @Override
    public void setKlassifizierung(final TkeyKlassifizierungCustomBean klassifizierung) {
        this.klassifizierung = klassifizierung;
        getPropertyChangeSupport().firePropertyChange(PROP_KLASSIFIZIERUNG, null, getKlassifizierung());
    }

    @Override
    public Date getMastanstrich() {
        return mastanstrich;
    }

    @Override
    public void setMastanstrich(final Date mastanstrich) {
        this.mastanstrich = mastanstrich;
        getPropertyChangeSupport().firePropertyChange(PROP_MASTANSTRICH, null, getMastanstrich());
    }

    @Override
    public Date getMastschutz() {
        return mastschutz;
    }

    @Override
    public void setMastschutz(final Date mastschutz) {
        this.mastschutz = mastschutz;
        getPropertyChangeSupport().firePropertyChange(PROP_MASTSCHUTZ, null, getMastschutz());
    }

    @Override
    public TkeyUnterhMastCustomBean getUnterhaltspflichtMast() {
        return unterhaltspflichtMast;
    }

    @Override
    public void setUnterhaltspflichtMast(final TkeyUnterhMastCustomBean unterhaltspflichtMast) {
        this.unterhaltspflichtMast = unterhaltspflichtMast;
        getPropertyChangeSupport().firePropertyChange(PROP_UNTERHALTSPFLICHT, null, getUnterhaltspflichtMast());
    }

    @Override
    public TkeyMasttypCustomBean getMasttyp() {
        return masttyp;
    }

    @Override
    public void setMasttyp(final TkeyMasttypCustomBean masttyp) {
        final TkeyMasttypCustomBean oldMasttyp = getMasttyp();
        this.masttyp = masttyp;
        getPropertyChangeSupport().firePropertyChange(PROP_MASTTYP, oldMasttyp, getMasttyp());
    }

    @Override
    public String getMontagefirma() {
        return montagefirma;
    }

    @Override
    public void setMontagefirma(final String montagefirma) {
        this.montagefirma = montagefirma;
        getPropertyChangeSupport().firePropertyChange(PROP_MONTAGEFIRMA, null, getMontagefirma());
    }

    @Override
    public Date getInbetriebnahmeMast() {
        return inbetriebnahmeMast;
    }

    @Override
    public void setInbetriebnahmeMast(final Date inbetriebnahmeMast) {
        this.inbetriebnahmeMast = inbetriebnahmeMast;
        getPropertyChangeSupport().firePropertyChange(PROP_INBETRIEBNAHME_MAST, null, getInbetriebnahmeMast());
    }

    @Override
    public boolean isVerrechnungseinheit() {
        return verrechnungseinheit;
    }

    @Override
    public void setVerrechnungseinheit(final boolean verrechnungseinheit) {
        this.verrechnungseinheit = verrechnungseinheit;
        getPropertyChangeSupport().firePropertyChange(PROP_VERRECHNUNGSEINHEIT, null, isVerrechnungseinheit());
    }

    @Override
    public String getBemerkungen() {
        return bemerkungen;
    }

    @Override
    public void setBemerkungen(final String bemerkungen) {
        this.bemerkungen = bemerkungen;
        getPropertyChangeSupport().firePropertyChange(PROP_BEMERKUNG, null, getBemerkungen());
    }

    @Override
    public Date getLetzteAenderung() {
        return letzteAenderung;
    }

    @Override
    public void setLetzteAenderung(final Date letzteAenderung) {
        this.letzteAenderung = letzteAenderung;
        getPropertyChangeSupport().firePropertyChange(PROP_LETZTE_AENDERUNG, null, getLetzteAenderung());
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long id) {
        final Long oldId = getId();
        this.id = id;
        getPropertyChangeSupport().firePropertyChange(PROP_ID, oldId, id);
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
