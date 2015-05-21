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

import java.sql.Timestamp;

import java.util.Collection;

import de.cismet.belisEE.mapicons.MapIcons;

import de.cismet.belisEE.util.EntityComparator;

import de.cismet.cids.custom.tostringconverter.belis2.SchaltstelleToStringConverter;

import de.cismet.cismap.commons.gui.piccolo.FeatureAnnotationSymbol;

import de.cismet.commons.server.entity.WorkbenchEntity;
import de.cismet.commons.server.entity.WorkbenchFeatureEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class SchaltstelleCustomBean extends WorkbenchFeatureEntity {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(SchaltstelleCustomBean.class);

    public static final String TABLE = "schaltstelle";

    public static final String PROP__ERSTELLUNGSJAHR = "erstellungsjahr";
    public static final String PROP__LAUFENDE_NUMMER = "laufende_nummer";
    public static final String PROP__FK_GEOM = "fk_geom";
    public static final String PROP__FK_BAUART = "fk_bauart";
    public static final String PROP__FK_STRASSENSCHLUESSEL = "fk_strassenschluessel";
    public static final String PROP__BEMERKUNG = "bemerkung";
    public static final String PROP__SCHALTSTELLEN_NUMMER = "schaltstellen_nummer";
    public static final String PROP__ZUSAETZLICHE_STANDORTBEZEICHNUNG = "zusaetzliche_standortbezeichnung";
    public static final String PROP__HAUS_NUMMER = "haus_nummer";
    public static final String PROP__DOKUMENTE = "dokumente";
    public static final String PROP__PRUEFDATUM = "pruefdatum";
    public static final String PROP__FOTO = "foto";
    public static final String PROP__MONTEUR = "monteur";
    public static final String PROP__RUNDSTEUEREMPFAENGER = "rundsteuerempfaenger";
    public static final String PROP__EINBAUDATUM_RS = "einbaudatum_rs";

    //~ Instance fields --------------------------------------------------------

    private Timestamp erstellungsjahr;
    private Integer laufende_nummer;
    private GeomCustomBean fk_geom;
    private BauartCustomBean fk_bauart;
    private TkeyStrassenschluesselCustomBean fk_strassenschluessel;
    private String bemerkung;
    private String schaltstellen_nummer;
    private String zusaetzliche_standortbezeichnung;
    private String haus_nummer;
    private Collection<DmsUrlCustomBean> dokumente;
    private Timestamp pruefdatum;
    private DmsUrlCustomBean foto;
    private String monteur;
    private RundsteuerempfaengerCustomBean rundsteuerempfaenger;
    private Timestamp einbaudatum_rs;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbzweigdoseCustomBean object.
     */
    public SchaltstelleCustomBean() {
        addPropertyNames(
            new String[] {
                PROP__ERSTELLUNGSJAHR,
                PROP__LAUFENDE_NUMMER,
                PROP__FK_GEOM,
                PROP__FK_BAUART,
                PROP__FK_STRASSENSCHLUESSEL,
                PROP__BEMERKUNG,
                PROP__SCHALTSTELLEN_NUMMER,
                PROP__ZUSAETZLICHE_STANDORTBEZEICHNUNG,
                PROP__HAUS_NUMMER,
                PROP__DOKUMENTE,
                PROP__PRUEFDATUM,
                PROP__FOTO,
                PROP__MONTEUR,
                PROP__RUNDSTEUEREMPFAENGER,
                PROP__EINBAUDATUM_RS
            });
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static SchaltstelleCustomBean createNew() {
        return (SchaltstelleCustomBean)createNew(TABLE);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getLaufende_nummer() {
        return laufende_nummer;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  laufende_nummer  DOCUMENT ME!
     */
    public void setLaufende_nummer(final Integer laufende_nummer) {
        final Integer old = this.laufende_nummer;
        this.laufende_nummer = laufende_nummer;
        this.propertyChangeSupport.firePropertyChange(PROP__LAUFENDE_NUMMER, old, this.laufende_nummer);
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
    public BauartCustomBean getFk_bauart() {
        return fk_bauart;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_bauart  DOCUMENT ME!
     */
    public void setFk_bauart(final BauartCustomBean fk_bauart) {
        final BauartCustomBean old = this.fk_bauart;
        this.fk_bauart = fk_bauart;
        this.propertyChangeSupport.firePropertyChange(PROP__FK_BAUART, old, this.fk_bauart);
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
    public String getSchaltstellen_nummer() {
        return schaltstellen_nummer;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  schaltstellen_nummer  DOCUMENT ME!
     */
    public void setSchaltstellen_nummer(final String schaltstellen_nummer) {
        final String old = this.schaltstellen_nummer;
        this.schaltstellen_nummer = schaltstellen_nummer;
        this.propertyChangeSupport.firePropertyChange(PROP__SCHALTSTELLEN_NUMMER, old, this.schaltstellen_nummer);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getZusaetzliche_standortbezeichnung() {
        return zusaetzliche_standortbezeichnung;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  zusaetzliche_standortbezeichnung  DOCUMENT ME!
     */
    public void setZusaetzliche_standortbezeichnung(final String zusaetzliche_standortbezeichnung) {
        final String old = this.zusaetzliche_standortbezeichnung;
        this.zusaetzliche_standortbezeichnung = zusaetzliche_standortbezeichnung;
        this.propertyChangeSupport.firePropertyChange(
            PROP__ZUSAETZLICHE_STANDORTBEZEICHNUNG,
            old,
            this.zusaetzliche_standortbezeichnung);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getHaus_nummer() {
        return haus_nummer;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  haus_nummer  DOCUMENT ME!
     */
    public void setHaus_nummer(final String haus_nummer) {
        final String old = this.haus_nummer;
        this.haus_nummer = haus_nummer;
        this.propertyChangeSupport.firePropertyChange(PROP__HAUS_NUMMER, old, this.haus_nummer);
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
    public Timestamp getPruefdatum() {
        return pruefdatum;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  pruefdatum  DOCUMENT ME!
     */
    public void setPruefdatum(final Timestamp pruefdatum) {
        final Timestamp old = this.pruefdatum;
        this.pruefdatum = pruefdatum;
        this.propertyChangeSupport.firePropertyChange(PROP__PRUEFDATUM, old, this.pruefdatum);
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
    public Timestamp getErstellungsjahr() {
        return erstellungsjahr;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  erstellungsjahr  DOCUMENT ME!
     */
    public void setErstellungsjahr(final Timestamp erstellungsjahr) {
        final Timestamp old = this.erstellungsjahr;
        this.erstellungsjahr = erstellungsjahr;
        this.propertyChangeSupport.firePropertyChange(PROP__ERSTELLUNGSJAHR, old, this.erstellungsjahr);
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
    public RundsteuerempfaengerCustomBean getRundsteuerempfaenger() {
        return rundsteuerempfaenger;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  rundsteuerempfaenger  zusaetzlicheStandortbeschreibung DOCUMENT ME!
     */
    public void setRundsteuerempfaenger(final RundsteuerempfaengerCustomBean rundsteuerempfaenger) {
        final RundsteuerempfaengerCustomBean old = this.rundsteuerempfaenger;
        this.rundsteuerempfaenger = rundsteuerempfaenger;
        this.propertyChangeSupport.firePropertyChange(PROP__RUNDSTEUEREMPFAENGER, old, this.rundsteuerempfaenger);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Timestamp getEinbaudatum_rs() {
        return einbaudatum_rs;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  einbaudatum_rs  rundsteuerempfaenger zusaetzlicheStandortbeschreibung DOCUMENT ME!
     */
    public void setEinbaudatum_rs(final Timestamp einbaudatum_rs) {
        final Timestamp old = this.einbaudatum_rs;
        this.einbaudatum_rs = einbaudatum_rs;
        this.propertyChangeSupport.firePropertyChange(PROP__EINBAUDATUM_RS, old, this.einbaudatum_rs);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getLaufendeNummer() {
        return getLaufende_nummer();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  laufendeNummer  DOCUMENT ME!
     */
    public void setLaufendeNummer(final Integer laufendeNummer) {
        setLaufende_nummer(laufendeNummer);
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
     * @param  strassenschluessel  DOCUMENT ME!
     */
    public void setStrassenschluessel(final TkeyStrassenschluesselCustomBean strassenschluessel) {
        setFk_strassenschluessel(strassenschluessel);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getHausnummer() {
        return getHaus_nummer();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  hausnummer  DOCUMENT ME!
     */
    public void setHausnummer(final String hausnummer) {
        setHaus_nummer(hausnummer);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getSchaltstellenNummer() {
        return getSchaltstellen_nummer();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  schaltstellenNummer  DOCUMENT ME!
     */
    public void setSchaltstellenNummer(final String schaltstellenNummer) {
        setSchaltstellen_nummer(schaltstellenNummer);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public BauartCustomBean getBauart() {
        return getFk_bauart();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  bauart  DOCUMENT ME!
     */
    public void setBauart(final BauartCustomBean bauart) {
        setFk_bauart(bauart);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getZusaetzlicheStandortbezeichnung() {
        return getZusaetzliche_standortbezeichnung();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  zusaetzlicheStandortbeschreibung  DOCUMENT ME!
     */
    public void setZusaetzlicheStandortbezeichnung(final String zusaetzlicheStandortbeschreibung) {
        setZusaetzliche_standortbezeichnung(zusaetzlicheStandortbeschreibung);
    }

    @Override
    public FeatureAnnotationSymbol getPointAnnotationSymbol() {
        if (mapIcon != null) {
            return mapIcon;
        } else {
            mapIcon = FeatureAnnotationSymbol.newCenteredFeatureAnnotationSymbol(
                    MapIcons.icoSchaltstelle,
                    null);
            return mapIcon;
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
     * @param   o  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public int compareTo(final WorkbenchEntity o) {
        if (o instanceof SchaltstelleCustomBean) {
            final SchaltstelleCustomBean s = (SchaltstelleCustomBean)o;
            return 1;
        } else {
            return EntityComparator.compareTypes(this, o);
        }
    }

    @Override
    public String getKeyString() {
        return new SchaltstelleToStringConverter().getKeyString(this);
    }

    @Override
    public String getHumanReadablePosition() {
        return new SchaltstelleToStringConverter().getHumanReadablePosition(this);
    }
}
