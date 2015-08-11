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

import Sirius.navigator.connection.SessionManager;

import java.sql.Date;

import java.text.DecimalFormat;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import de.cismet.belis.broker.CidsBroker;

import de.cismet.belis.commons.constants.BelisMetaClassConstants;

import de.cismet.belis2.server.search.NextVeranlassungNummerSearch;

import de.cismet.cids.custom.tostringconverter.belis2.VeranlassungToStringConverter;

import de.cismet.commons.server.entity.WorkbenchEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class VeranlassungCustomBean extends WorkbenchEntity {

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
    public static final String PROP__AR_GEOMETRIEN = "ar_geometrien";
    public static final String PROP__USERNAME = "username";
    public static final String PROP__NUMMER = "nummer";
    public static final String PROP__BEZEICHNUNG = "bezeichnung";
    public static final String PROP__BEMERKUNGEN = "bemerkungen";
    public static final String PROP__BESCHREIBUNG = "beschreibung";
    public static final String PROP__DATUM = "datum";
    public static final String PROP__FK_ART = "fk_art";
    public static final String PROP__FK_INFOBAUSTEIN_TEMPLATE = "fk_infobaustein_template";

    //~ Instance fields --------------------------------------------------------

    private Collection<SchaltstelleCustomBean> ar_schaltstellen;
    private Collection<InfobausteinCustomBean> ar_infobausteine;
    private Collection<TdtaStandortMastCustomBean> ar_standorte;
    private Collection<MauerlascheCustomBean> ar_mauerlaschen;
    private Collection<LeitungCustomBean> ar_leitungen;
    private Collection<TdtaLeuchtenCustomBean> ar_leuchten;
    private Collection<AbzweigdoseCustomBean> ar_abzweigdosen;
    private Collection<DmsUrlCustomBean> ar_dokumente;
    private Collection<GeometrieCustomBean> ar_geometrien;
    private Collection<ArbeitsprotokollCustomBean> n_protokolle;
    private String username;
    private String nummer;
    private String bezeichnung;
    private String bemerkungen;
    private String beschreibung;
    private Date datum;
    private VeranlassungsartCustomBean fk_art;
    private InfobausteinTemplateCustomBean fk_infobaustein_template;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new VeranlassungCustomBean object.
     */
    public VeranlassungCustomBean() {
        addPropertyNames(
            new String[] {
                PROP__AR_SCHALTSTELLEN,
                PROP__AR_INFOBAUSTEINE,
                PROP__AR_STANDORTE,
                PROP__AR_MAUERLASCHEN,
                PROP__AR_LEITUNGEN,
                PROP__AR_LEUCHTEN,
                PROP__AR_ABZWEIGDOSEN,
                PROP__AR_DOKUMENTE,
                PROP__AR_GEOMETRIEN,
                PROP__USERNAME,
                PROP__NUMMER,
                PROP__BEZEICHNUNG,
                PROP__BEMERKUNGEN,
                PROP__BESCHREIBUNG,
                PROP__DATUM,
                PROP__FK_ART,
                PROP__FK_INFOBAUSTEIN_TEMPLATE
            });
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static VeranlassungCustomBean createNew() {
        final VeranlassungCustomBean veranlassungCustomBean = (VeranlassungCustomBean)createNew(TABLE);
        veranlassungCustomBean.setDatum(new Date(Calendar.getInstance().getTime().getTime()));
        veranlassungCustomBean.setUsername(SessionManager.getSession().getUser().getName());

        try {
            final List<Long> nextNumber = (List<Long>)CidsBroker.getInstance()
                        .executeServerSearch(new NextVeranlassungNummerSearch());

            final Long number = (nextNumber.isEmpty()) ? null : nextNumber.get(0);
            final DecimalFormat df = new DecimalFormat("00000000");
            veranlassungCustomBean.setNummer(df.format(number));
        } catch (final Exception ex) {
            LOG.error("", ex);
        }

        return veranlassungCustomBean;
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
    public Collection<GeometrieCustomBean> getAr_geometrien() {
        return ar_geometrien;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  ar_geometrien  DOCUMENT ME!
     */
    public void setAr_geometrien(final Collection<GeometrieCustomBean> ar_geometrien) {
        final Collection<GeometrieCustomBean> old = this.ar_geometrien;
        this.ar_geometrien = ar_geometrien;
        this.propertyChangeSupport.firePropertyChange(PROP__AR_GEOMETRIEN, old, this.ar_geometrien);
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
    public VeranlassungsartCustomBean getFk_art() {
        return fk_art;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_art  DOCUMENT ME!
     */
    public void setFk_art(final VeranlassungsartCustomBean fk_art) {
        final VeranlassungsartCustomBean old = this.fk_art;
        this.fk_art = fk_art;
        this.propertyChangeSupport.firePropertyChange(PROP__FK_ART, old, this.fk_art);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public InfobausteinTemplateCustomBean getFk_infobaustein_template() {
        return fk_infobaustein_template;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_infobaustein_template  fk_art DOCUMENT ME!
     */
    public void setFk_infobaustein_template(final InfobausteinTemplateCustomBean fk_infobaustein_template) {
        final InfobausteinTemplateCustomBean old = this.fk_infobaustein_template;
        this.fk_infobaustein_template = fk_infobaustein_template;
        this.propertyChangeSupport.firePropertyChange(
            PROP__FK_INFOBAUSTEIN_TEMPLATE,
            old,
            this.fk_infobaustein_template);
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
        if (o instanceof VeranlassungCustomBean) {
            final VeranlassungCustomBean v = (VeranlassungCustomBean)o;
            return getKeyString().compareTo(v.getKeyString()) * -1;
        } else {
            return 1;
        }
    }

    @Override
    public String getHumanReadablePosition() {
        return new VeranlassungToStringConverter().getHumanReadablePosition(this);
    }

    @Override
    public String getKeyString() {
        return new VeranlassungToStringConverter().getKeyString(this);
    }
}
