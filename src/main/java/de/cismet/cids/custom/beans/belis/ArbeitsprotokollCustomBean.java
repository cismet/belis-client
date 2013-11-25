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

import java.sql.Date;

import de.cismet.belis.commons.constants.BelisMetaClassConstants;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class ArbeitsprotokollCustomBean extends BaseEntity {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            ArbeitsprotokollCustomBean.class);

    public static final String TABLE = BelisMetaClassConstants.MC_ARBEITSPROTOKOLL;

    public static final String PROP__FK_MAUERLASCHE = "fk_mauerlasche";
    public static final String PROP__MATERIAL = "material";
    public static final String PROP__FK_LEUCHTE = "fk_leuchte";
    public static final String PROP__MONTEUR = "monteur";
    public static final String PROP__FK_LEITUNG = "fk_leitung";
    public static final String PROP__BEMERKUNG = "bemerkung";
    public static final String PROP__DEFEKT = "defekt";
    public static final String PROP__FK_STANDORT = "fk_standort";
    public static final String PROP__FK_ABZWEIGDOSE = "fk_abzweigdose";
    public static final String PROP__FK_SCHALTSTELLE = "fk_schaltstelle";
    public static final String PROP__DATUM = "datum";
    public static final String PROP__FK_STATUS = "fk_status";
    public static final String PROP__FK_ARBEITSAUFTRAG = "fk_arbeitsauftrag";

    private static final String[] PROPERTY_NAMES = new String[] {
            PROP__ID,
            PROP__FK_MAUERLASCHE,
            PROP__MATERIAL,
            PROP__FK_LEUCHTE,
            PROP__MONTEUR,
            PROP__FK_LEITUNG,
            PROP__BEMERKUNG,
            PROP__DEFEKT,
            PROP__FK_STANDORT,
            PROP__FK_ABZWEIGDOSE,
            PROP__FK_SCHALTSTELLE,
            PROP__DATUM,
            PROP__FK_STATUS,
            PROP__FK_ARBEITSAUFTRAG
        };

    //~ Instance fields --------------------------------------------------------

    private MauerlascheCustomBean fk_mauerlasche;
    private String material;
    private TdtaLeuchtenCustomBean fk_leuchte;
    private String monteur;
    private LeitungCustomBean fk_leitung;
    private String bemerkung;
    private String defekt;
    private TdtaStandortMastCustomBean fk_standort;
    private AbzweigdoseCustomBean fk_abzweigdose;
    private SchaltstelleCustomBean fk_schaltstelle;
    private Date datum;
    private ArbeitsprotokollstatusCustomBean fk_status;
    private ArbeitsauftragCustomBean fk_arbeitsauftrag;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ArbeitsprotokollCustomBean object.
     */
    public ArbeitsprotokollCustomBean() {
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

    @Override
    public String[] getPropertyNames() {
        return PROPERTY_NAMES;
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
        if (other instanceof ArbeitsprotokollCustomBean) {
            final ArbeitsprotokollCustomBean anEntity = (ArbeitsprotokollCustomBean)other;
            if (this == other) {
                return true;
            } else if (!this.getClass().isAssignableFrom(other.getClass())) {
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
        final LeitungCustomBean old = fk_leitung;
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
        final String old = bemerkung;
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
        final String old = defekt;
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
        final TdtaStandortMastCustomBean old = fk_standort;
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
        final AbzweigdoseCustomBean old = fk_abzweigdose;
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
        final SchaltstelleCustomBean old = fk_schaltstelle;
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
        final Date old = datum;
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
        final ArbeitsprotokollstatusCustomBean old = fk_status;
        this.fk_status = fk_status;
        this.propertyChangeSupport.firePropertyChange(PROP__FK_STATUS, old, this.fk_status);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public ArbeitsauftragCustomBean getFk_arbeitsauftrag() {
        return fk_arbeitsauftrag;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_arbeitsauftrag  DOCUMENT ME!
     */
    public void setFk_arbeitsauftrag(final ArbeitsauftragCustomBean fk_arbeitsauftrag) {
        final ArbeitsauftragCustomBean old = fk_arbeitsauftrag;
        this.fk_arbeitsauftrag = fk_arbeitsauftrag;
        this.propertyChangeSupport.firePropertyChange(PROP__FK_ARBEITSAUFTRAG, old, this.fk_arbeitsauftrag);
    }
}
