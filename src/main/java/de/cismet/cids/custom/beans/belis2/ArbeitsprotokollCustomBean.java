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

import java.sql.Date;

import java.util.Collection;

import de.cismet.belis.broker.CidsBroker;

import de.cismet.belis.commons.constants.BelisMetaClassConstants;

import de.cismet.belis2.server.search.VeranlassungsschluesselSearch;

import de.cismet.belisEE.util.EntityComparator;

import de.cismet.commons.server.entity.BaseEntity;
import de.cismet.commons.server.entity.GeoBaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class ArbeitsprotokollCustomBean extends BaseEntity implements WorkbenchEntity {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            ArbeitsprotokollCustomBean.class);

    public static final String TABLE = BelisMetaClassConstants.MC_ARBEITSPROTOKOLL;

    public static final String PROP__MATERIAL = "material";
    public static final String PROP__MONTEUR = "monteur";
    public static final String PROP__BEMERKUNG = "bemerkung";
    public static final String PROP__DEFEKT = "defekt";
    public static final String PROP__DATUM = "datum";
    public static final String PROP__FK_STATUS = "fk_status";
    public static final String PROP__FK_MAUERLASCHE = "fk_mauerlasche";
    public static final String PROP__FK_LEUCHTE = "fk_leuchte";
    public static final String PROP__FK_LEITUNG = "fk_leitung";
    public static final String PROP__FK_STANDORT = "fk_standort";
    public static final String PROP__FK_ABZWEIGDOSE = "fk_abzweigdose";
    public static final String PROP__FK_SCHALTSTELLE = "fk_schaltstelle";
    public static final String PROP__FK_GEOMETRIE = "fk_geometrie";
    public static final String PROP__VERANLASSUNGSNUMMER = "veranlassungsnummer";
    public static final String PROP__VERANLASSUNGSSCHLUESSEL = "veranlassungsschluessel";
    public static final String PROP__N_AKTIONEN = "n_aktionen";

    private static final String[] PROPERTY_NAMES = new String[] {
            PROP__ID,
            PROP__MATERIAL,
            PROP__MONTEUR,
            PROP__BEMERKUNG,
            PROP__DEFEKT,
            PROP__DATUM,
            PROP__FK_STATUS,
            PROP__FK_STANDORT,
            PROP__FK_MAUERLASCHE,
            PROP__FK_LEUCHTE,
            PROP__FK_LEITUNG,
            PROP__FK_ABZWEIGDOSE,
            PROP__FK_SCHALTSTELLE,
            PROP__FK_GEOMETRIE,
            PROP__VERANLASSUNGSNUMMER,
            PROP__VERANLASSUNGSSCHLUESSEL,
            PROP__N_AKTIONEN
        };

    //~ Enums ------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public enum ChildType {

        //~ Enum constants -----------------------------------------------------

        MAUERLASCHE, LEUCHTE, LEITUNG, STANDORT, ABZWEIGDOSE, SCHALTSTELLE, GEOMETRIE
    }

    //~ Instance fields --------------------------------------------------------

    private String material;
    private String monteur;
    private String bemerkung;
    private String defekt;
    private Date datum;
    private ArbeitsprotokollstatusCustomBean fk_status;
    private MauerlascheCustomBean fk_mauerlasche;
    private TdtaLeuchtenCustomBean fk_leuchte;
    private LeitungCustomBean fk_leitung;
    private TdtaStandortMastCustomBean fk_standort;
    private AbzweigdoseCustomBean fk_abzweigdose;
    private SchaltstelleCustomBean fk_schaltstelle;
    private GeometrieCustomBean fk_geometrie;
    private String veranlassungsnummer;
    private String veranlassungsschluessel;

    private Collection<ArbeitsprotokollaktionCustomBean> n_aktionen;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ArbeitsprotokollCustomBean object.
     */
    public ArbeitsprotokollCustomBean() {
        addPropertyChangeListener(this);
        refreshVeranlassungsschlussel(getVeranlassungsnummer());
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static ArbeitsprotokollCustomBean createNew() {
        return (ArbeitsprotokollCustomBean)createNew(TABLE);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public String[] getPropertyNames() {
        return PROPERTY_NAMES;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public MauerlascheCustomBean getFk_mauerlasche() {
        return fk_mauerlasche;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_mauerlasche  DOCUMENT ME!
     */
    public void setFk_mauerlasche(final MauerlascheCustomBean fk_mauerlasche) {
        final MauerlascheCustomBean old = this.fk_mauerlasche;
        this.fk_mauerlasche = fk_mauerlasche;
        this.propertyChangeSupport.firePropertyChange(PROP__FK_MAUERLASCHE, old, this.fk_mauerlasche);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getMaterial() {
        return material;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  material  DOCUMENT ME!
     */
    public void setMaterial(final String material) {
        final String old = this.material;
        this.material = material;
        this.propertyChangeSupport.firePropertyChange(PROP__MATERIAL, old, this.material);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public TdtaLeuchtenCustomBean getFk_leuchte() {
        return fk_leuchte;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public GeoBaseEntity getChildEntity() {
        if (getFk_leuchte() != null) {
            return getFk_leuchte();
        } else if (getFk_geometrie() != null) {
            return getFk_geometrie();
        } else if (getFk_standort() != null) {
            return getFk_standort();
        } else if (getFk_abzweigdose() != null) {
            return getFk_abzweigdose();
        } else if (getFk_leitung() != null) {
            return getFk_leitung();
        } else if (getFk_mauerlasche() != null) {
            return getFk_mauerlasche();
        } else if (getFk_schaltstelle() != null) {
            return getFk_schaltstelle();
        } else {
            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_leuchte  DOCUMENT ME!
     */
    public void setFk_leuchte(final TdtaLeuchtenCustomBean fk_leuchte) {
        final TdtaLeuchtenCustomBean old = this.fk_leuchte;
        this.fk_leuchte = fk_leuchte;
        this.propertyChangeSupport.firePropertyChange(PROP__FK_LEUCHTE, old, this.fk_leuchte);
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
    public LeitungCustomBean getFk_leitung() {
        return fk_leitung;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_leitung  DOCUMENT ME!
     */
    public void setFk_leitung(final LeitungCustomBean fk_leitung) {
        final LeitungCustomBean old = this.fk_leitung;
        this.fk_leitung = fk_leitung;
        this.propertyChangeSupport.firePropertyChange(PROP__FK_LEITUNG, old, this.fk_leitung);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getBemerkung() {
        return bemerkung;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  bemerkung  DOCUMENT ME!
     */
    public void setBemerkung(final String bemerkung) {
        final String old = this.bemerkung;
        this.bemerkung = bemerkung;
        this.propertyChangeSupport.firePropertyChange(PROP__BEMERKUNG, old, this.bemerkung);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getDefekt() {
        return defekt;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  defekt  DOCUMENT ME!
     */
    public void setDefekt(final String defekt) {
        final String old = this.defekt;
        this.defekt = defekt;
        this.propertyChangeSupport.firePropertyChange(PROP__DEFEKT, old, this.defekt);
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
    public AbzweigdoseCustomBean getFk_abzweigdose() {
        return fk_abzweigdose;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_abzweigdose  DOCUMENT ME!
     */
    public void setFk_abzweigdose(final AbzweigdoseCustomBean fk_abzweigdose) {
        final AbzweigdoseCustomBean old = this.fk_abzweigdose;
        this.fk_abzweigdose = fk_abzweigdose;
        this.propertyChangeSupport.firePropertyChange(PROP__FK_ABZWEIGDOSE, old, this.fk_abzweigdose);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public SchaltstelleCustomBean getFk_schaltstelle() {
        return fk_schaltstelle;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_schaltstelle  DOCUMENT ME!
     */
    public void setFk_schaltstelle(final SchaltstelleCustomBean fk_schaltstelle) {
        final SchaltstelleCustomBean old = this.fk_schaltstelle;
        this.fk_schaltstelle = fk_schaltstelle;
        this.propertyChangeSupport.firePropertyChange(PROP__FK_SCHALTSTELLE, old, this.fk_schaltstelle);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Date getDatum() {
        return datum;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  datum  DOCUMENT ME!
     */
    public void setDatum(final Date datum) {
        final Date old = this.datum;
        this.datum = datum;
        this.propertyChangeSupport.firePropertyChange(PROP__DATUM, old, this.datum);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public ArbeitsprotokollstatusCustomBean getFk_status() {
        return fk_status;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_status  DOCUMENT ME!
     */
    public void setFk_status(final ArbeitsprotokollstatusCustomBean fk_status) {
        final ArbeitsprotokollstatusCustomBean old = this.fk_status;
        this.fk_status = fk_status;
        this.propertyChangeSupport.firePropertyChange(PROP__FK_STATUS, old, this.fk_status);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getVeranlassungsnummer() {
        return veranlassungsnummer;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  veranlassungsnummer  fk_arbeitsauftrag DOCUMENT ME!
     */
    public void setVeranlassungsnummer(final String veranlassungsnummer) {
        final String old = this.veranlassungsnummer;
        this.veranlassungsnummer = veranlassungsnummer;
        this.propertyChangeSupport.firePropertyChange(PROP__VERANLASSUNGSNUMMER, old, this.veranlassungsnummer);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getVeranlassungsschluessel() {
        return veranlassungsschluessel;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  veranlassungsschluessel  veranlassungsschluessel DOCUMENT ME!
     */
    public void setVeranlassungsschluessel(final String veranlassungsschluessel) {
        final String old = this.veranlassungsschluessel;
        this.veranlassungsschluessel = veranlassungsschluessel;
        this.propertyChangeSupport.firePropertyChange(PROP__VERANLASSUNGSSCHLUESSEL, old, this.veranlassungsschluessel);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public GeometrieCustomBean getFk_geometrie() {
        return fk_geometrie;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_geometrie  fk_arbeitsauftrag DOCUMENT ME!
     */
    public void setFk_geometrie(final GeometrieCustomBean fk_geometrie) {
        final GeometrieCustomBean old = this.fk_geometrie;
        this.fk_geometrie = fk_geometrie;
        this.propertyChangeSupport.firePropertyChange(PROP__FK_GEOMETRIE, old, this.fk_geometrie);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Collection<ArbeitsprotokollaktionCustomBean> getN_aktionen() {
        return n_aktionen;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  n_aktionen  fk_arbeitsauftrag DOCUMENT ME!
     */
    public void setN_aktionen(final Collection<ArbeitsprotokollaktionCustomBean> n_aktionen) {
        final Collection<ArbeitsprotokollaktionCustomBean> old = this.n_aktionen;
        this.n_aktionen = n_aktionen;
        this.propertyChangeSupport.firePropertyChange(PROP__N_AKTIONEN, old, this.n_aktionen);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public ChildType getChildType() {
        if (getFk_abzweigdose() != null) {
            return ChildType.ABZWEIGDOSE;
        } else if (getFk_leitung() != null) {
            return ChildType.LEITUNG;
        } else if (getFk_leuchte() != null) {
            return ChildType.LEUCHTE;
        } else if (getFk_mauerlasche() != null) {
            return ChildType.MAUERLASCHE;
        } else if (getFk_schaltstelle() != null) {
            return ChildType.SCHALTSTELLE;
        } else if (getFk_geometrie() != null) {
            return ChildType.GEOMETRIE;
        } else if (getFk_standort() != null) {
            return ChildType.STANDORT;
        } else {
            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public String getKeyString() {
        final String subfix;
        if (getVeranlassungsschluessel() != null) {
            subfix = " (" + getVeranlassungsschluessel() + ")";
        } else {
            subfix = "";
        }

        if (getFk_abzweigdose() != null) {
            return "Abzweigdose" + subfix;
        } else if (getFk_leitung() != null) {
            return "Leitung" + subfix;
        } else if (getFk_leuchte() != null) {
            return "Leuchte" + subfix;
        } else if (getFk_mauerlasche() != null) {
            return "Mauerlasche" + subfix;
        } else if (getFk_schaltstelle() != null) {
            return "Schaltstelle" + subfix;
        } else if (getFk_geometrie() != null) {
            return "Geometrie" + subfix;
        } else if (getFk_standort() != null) {
            final TdtaStandortMastCustomBean standort = getFk_standort();
            if (standort.isStandortMast()) {
                return "Mast" + subfix;
            } else {
                return "Standort" + subfix;
            }
        } else {
            return "";
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public String getHumanReadablePosition() {
        if (getFk_abzweigdose() != null) {
            return getFk_abzweigdose().getHumanReadablePosition();
        } else if (getFk_leitung() != null) {
            return getFk_leitung().getHumanReadablePosition();
        } else if (getFk_leuchte() != null) {
            return getFk_leuchte().getHumanReadablePosition();
        } else if (getFk_mauerlasche() != null) {
            return getFk_mauerlasche().getHumanReadablePosition();
        } else if (getFk_schaltstelle() != null) {
            return getFk_schaltstelle().getHumanReadablePosition();
        } else if (getFk_standort() != null) {
            return getFk_standort().getHumanReadablePosition();
        } else {
            return "";
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   o  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public int compareTo(final WorkbenchEntity o) {
        if (o instanceof ArbeitsprotokollCustomBean) {
            final ArbeitsprotokollCustomBean p = (ArbeitsprotokollCustomBean)o;

            // TODOJean sortieren nach untertypen
            return 1;
        } else {
            return EntityComparator.compareTypes(this, o);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  veranlassungsnummer  DOCUMENT ME!
     */
    private void refreshVeranlassungsschlussel(final String veranlassungsnummer) {
        String schluessel = null;
        try {
            final Collection col = CidsBroker.getInstance()
                        .executeServerSearch(new VeranlassungsschluesselSearch(veranlassungsnummer));
            if ((col != null) && !col.isEmpty()) {
                final Object item = col.iterator().next();
                if ((item != null) && (item instanceof String)) {
                    schluessel = (String)item;
                }
            }
        } catch (final Exception ex) {
            LOG.error(ex, ex);
        }
        setVeranlassungsschluessel(schluessel);
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if (!evt.getPropertyName().equals(PROP__VERANLASSUNGSSCHLUESSEL) && (metaObject != null)) {
            super.propertyChange(evt);
        }
        if (evt.getSource().equals(this) && evt.getPropertyName().equals(PROP__VERANLASSUNGSNUMMER)
                    && ((evt.getNewValue() == null) || (evt.getNewValue() instanceof String))) {
            refreshVeranlassungsschlussel((String)evt.getNewValue());
        }
    }
}
