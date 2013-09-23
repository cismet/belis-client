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

import java.util.Collection;
import java.util.Date;

import de.cismet.belis.commons.constants.BelisMetaClassConstants;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.commons.server.entity.GeoBaseEntity;
import de.cismet.commons.server.interfaces.DocumentContainer;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class VeranlassungCustomBean extends GeoBaseEntity implements DocumentContainer {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(VeranlassungCustomBean.class);

    public static final String TABLE = BelisMetaClassConstants.MC_VERANLASSUNG;

    public static final String PROP__AR_SCHALTSTELLEN = "ar_schaltstellen";
    public static final String PROP__AR_INFOBAUSTEINE = "ar_infobausteine";
    public static final String PROP__AR_STANDORTE = "ar_standorte";
    public static final String PROP__AR_MAUERLASCHEN = "ar_mauerlaschen";
    public static final String PROP__AR_LEITUNGEN = "ar_leitungen";
    public static final String PROP__AR_LEUCHTEN = "ar_leuchten";
    public static final String PROP__AR_ABZWEIGDOSEN = "ar_abzweigdosen";
    public static final String PROP__AR_DOKUMENTE = "ar_dokumente";
    public static final String PROP__FK_GEOMETRIE = "fk_geometrie";
    public static final String PROP__USERNAME = "username";
    public static final String PROP__NUMMER = "nummer";
    public static final String PROP__FK_AUFTRAG = "fk_auftrag";
    public static final String PROP__BEZEICHNUNG = "bezeichnung";
    public static final String PROP__BEMERKUNGEN = "bemerkungen";
    public static final String PROP__BESCHREIBUNG = "beschreibung";
    public static final String PROP__DATUM = "datum";
    public static final String PROP__FK_ART = "fk_art";

    private static final String[] PROPERTY_NAMES = new String[] {
            PROP__ID,
            PROP__AR_SCHALTSTELLEN,
            PROP__AR_INFOBAUSTEINE,
            PROP__AR_STANDORTE,
            PROP__AR_MAUERLASCHEN,
            PROP__AR_LEITUNGEN,
            PROP__AR_LEUCHTEN,
            PROP__AR_ABZWEIGDOSEN,
            PROP__AR_DOKUMENTE,
            PROP__FK_GEOMETRIE,
            PROP__USERNAME,
            PROP__NUMMER,
            PROP__FK_AUFTRAG,
            PROP__BEZEICHNUNG,
            PROP__BEMERKUNGEN,
            PROP__BESCHREIBUNG,
            PROP__DATUM,
            PROP__FK_ART
        };

    //~ Instance fields --------------------------------------------------------

    private Collection<SchaltstelleCustomBean> ar_schaltstellen;
    private Collection<InfobausteinCustomBean> ar_infobausteine;
    private Collection<TdtaStandortMastCustomBean> ar_standorte;
    private Collection<MauerlascheCustomBean> ar_mauerlaschen;
    private Collection<LeitungCustomBean> ar_leitungen;
    private Collection<TdtaLeuchtenCustomBean> ar_leuchten;
    private Collection<AbzweigdoseCustomBean> ar_abzweigdosen;
    private Collection<DmsUrlCustomBean> ar_dokumente;
    private GeomCustomBean fk_geometrie;
    private String username;
    private String nummer;
    private ArbeitsauftragCustomBean fk_auftrag;
    private String bezeichnung;
    private String bemerkungen;
    private String beschreibung;
    private Date datum;
    private VeranlassungsArtCustomBean fk_art;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new VeranlassungCustomBean object.
     */
    public VeranlassungCustomBean() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static VeranlassungCustomBean createNew() {
        return (VeranlassungCustomBean)createNew(TABLE);
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
        if (other instanceof VeranlassungCustomBean) {
            final VeranlassungCustomBean anEntity = (VeranlassungCustomBean)other;
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

    @Override
    public String toString() {
        return "de.cismet.belisEE.entity.Veranlassung[id=" + getId() + "]";
    }

    @Override
    public GeomCustomBean getGeometrie() {
        return getFk_geometrie();
    }

    @Override
    public void setGeometrie(final GeomCustomBean val) {
        setFk_geometrie(val);
    }

    @Override
    public Collection<DmsUrlCustomBean> getDokumente() {
        return getAr_dokumente();
    }

    @Override
    public void setDokumente(final Collection<DmsUrlCustomBean> urls) {
        setAr_dokumente(urls);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Collection<SchaltstelleCustomBean> getAr_schaltstellen() {
        return ar_schaltstellen;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  ar_schaltstellen  DOCUMENT ME!
     */
    public void setAr_schaltstellen(final Collection<SchaltstelleCustomBean> ar_schaltstellen) {
        final Collection<SchaltstelleCustomBean> old = this.ar_schaltstellen;
        this.ar_schaltstellen = ar_schaltstellen;
        this.propertyChangeSupport.firePropertyChange(PROP__AR_SCHALTSTELLEN, old, this.ar_schaltstellen);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Collection<InfobausteinCustomBean> getAr_infobausteine() {
        return ar_infobausteine;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  ar_infobausteine  DOCUMENT ME!
     */
    public void setAr_infobausteine(final Collection<InfobausteinCustomBean> ar_infobausteine) {
        final Collection<InfobausteinCustomBean> old = this.ar_infobausteine;
        this.ar_infobausteine = ar_infobausteine;
        this.propertyChangeSupport.firePropertyChange(PROP__AR_INFOBAUSTEINE, old, this.ar_infobausteine);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Collection<TdtaStandortMastCustomBean> getAr_standorte() {
        return ar_standorte;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  ar_standorte  DOCUMENT ME!
     */
    public void setAr_standorte(final Collection<TdtaStandortMastCustomBean> ar_standorte) {
        final Collection<TdtaStandortMastCustomBean> old = this.ar_standorte;
        this.ar_standorte = ar_standorte;
        this.propertyChangeSupport.firePropertyChange(PROP__AR_STANDORTE, old, this.ar_standorte);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Collection<MauerlascheCustomBean> getAr_mauerlaschen() {
        return ar_mauerlaschen;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  ar_mauerlaschen  DOCUMENT ME!
     */
    public void setAr_mauerlaschen(final Collection<MauerlascheCustomBean> ar_mauerlaschen) {
        final Collection<MauerlascheCustomBean> old = this.ar_mauerlaschen;
        this.ar_mauerlaschen = ar_mauerlaschen;
        this.propertyChangeSupport.firePropertyChange(PROP__AR_MAUERLASCHEN, old, this.ar_mauerlaschen);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Collection<LeitungCustomBean> getAr_leitungen() {
        return ar_leitungen;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  ar_leitungen  DOCUMENT ME!
     */
    public void setAr_leitungen(final Collection<LeitungCustomBean> ar_leitungen) {
        final Collection<LeitungCustomBean> old = this.ar_leitungen;
        this.ar_leitungen = ar_leitungen;
        this.propertyChangeSupport.firePropertyChange(PROP__AR_LEITUNGEN, old, this.ar_leitungen);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Collection<TdtaLeuchtenCustomBean> getAr_leuchten() {
        return ar_leuchten;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  ar_leuchten  DOCUMENT ME!
     */
    public void setAr_leuchten(final Collection<TdtaLeuchtenCustomBean> ar_leuchten) {
        final Collection<TdtaLeuchtenCustomBean> old = this.ar_leuchten;
        this.ar_leuchten = ar_leuchten;
        this.propertyChangeSupport.firePropertyChange(PROP__AR_LEUCHTEN, old, this.ar_leuchten);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Collection<AbzweigdoseCustomBean> getAr_abzweigdosen() {
        return ar_abzweigdosen;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  ar_abzweigdosen  DOCUMENT ME!
     */
    public void setAr_abzweigdosen(final Collection<AbzweigdoseCustomBean> ar_abzweigdosen) {
        final Collection<AbzweigdoseCustomBean> old = this.ar_abzweigdosen;
        this.ar_abzweigdosen = ar_abzweigdosen;
        this.propertyChangeSupport.firePropertyChange(PROP__AR_ABZWEIGDOSEN, old, this.ar_abzweigdosen);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Collection<DmsUrlCustomBean> getAr_dokumente() {
        return ar_dokumente;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  ar_dokumente  DOCUMENT ME!
     */
    public void setAr_dokumente(final Collection<DmsUrlCustomBean> ar_dokumente) {
        final Collection<DmsUrlCustomBean> old = this.ar_dokumente;
        this.ar_dokumente = ar_dokumente;
        this.propertyChangeSupport.firePropertyChange(PROP__AR_DOKUMENTE, old, this.ar_dokumente);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public GeomCustomBean getFk_geometrie() {
        return fk_geometrie;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_geometrie  DOCUMENT ME!
     */
    public void setFk_geometrie(final GeomCustomBean fk_geometrie) {
        final GeomCustomBean old = this.fk_geometrie;
        this.fk_geometrie = fk_geometrie;
        this.propertyChangeSupport.firePropertyChange(PROP__FK_GEOMETRIE, old, this.fk_geometrie);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getUsername() {
        return username;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  username  DOCUMENT ME!
     */
    public void setUsername(final String username) {
        final String old = this.username;
        this.username = username;
        this.propertyChangeSupport.firePropertyChange(PROP__USERNAME, old, this.username);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getNummer() {
        return nummer;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  nummer  DOCUMENT ME!
     */
    public void setNummer(final String nummer) {
        final String old = this.nummer;
        this.nummer = nummer;
        this.propertyChangeSupport.firePropertyChange(PROP__NUMMER, old, this.nummer);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public CidsBean getFk_auftrag() {
        return fk_auftrag;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_auftrag  DOCUMENT ME!
     */
    public void setFk_auftrag(final ArbeitsauftragCustomBean fk_auftrag) {
        final ArbeitsauftragCustomBean old = this.fk_auftrag;
        this.fk_auftrag = fk_auftrag;
        this.propertyChangeSupport.firePropertyChange(PROP__FK_AUFTRAG, old, this.fk_auftrag);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getBezeichnung() {
        return bezeichnung;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  bezeichnung  DOCUMENT ME!
     */
    public void setBezeichnung(final String bezeichnung) {
        final String old = this.bezeichnung;
        this.bezeichnung = bezeichnung;
        this.propertyChangeSupport.firePropertyChange(PROP__BEZEICHNUNG, old, this.bezeichnung);
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
    public String getBeschreibung() {
        return beschreibung;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  beschreibung  DOCUMENT ME!
     */
    public void setBeschreibung(final String beschreibung) {
        final String old = this.beschreibung;
        this.beschreibung = beschreibung;
        this.propertyChangeSupport.firePropertyChange(PROP__BESCHREIBUNG, old, this.beschreibung);
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
    public VeranlassungsArtCustomBean getFk_art() {
        return fk_art;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_art  DOCUMENT ME!
     */
    public void setFk_art(final VeranlassungsArtCustomBean fk_art) {
        final VeranlassungsArtCustomBean old = this.fk_art;
        this.fk_art = fk_art;
        this.propertyChangeSupport.firePropertyChange(PROP__FK_ART, old, this.fk_art);
    }
}