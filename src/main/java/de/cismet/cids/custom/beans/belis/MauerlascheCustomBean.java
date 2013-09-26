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

import de.cismet.belisEE.mapicons.MapIcons;

import de.cismet.cismap.commons.gui.piccolo.FeatureAnnotationSymbol;

import de.cismet.commons.server.entity.GeoBaseEntity;
import de.cismet.commons.server.interfaces.DocumentContainer;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class MauerlascheCustomBean extends GeoBaseEntity implements DocumentContainer {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(MauerlascheCustomBean.class);

    public static final String TABLE = "mauerlasche";

    public static final String PROP__ERSTELLUNGSJAHR = "erstellungsjahr";
    public static final String PROP__LAUFENDE_NUMMER = "laufende_nummer";
    public static final String PROP__FK_GEOM = "fk_geom";
    public static final String PROP__FK_MATERIAL = "fk_material";
    public static final String PROP__FK_STRASSENSCHLUESSEL = "fk_strassenschluessel";
    public static final String PROP__DOKUMENTE = "dokumente";
    public static final String PROP__FOTO = "foto";
    public static final String PROP__MONTEUR = "monteur";
    public static final String PROP__PRUEFDATUM = "pruefdatum";
    public static final String PROP__BEMERKUNG = "bemerkung";

    private static final String[] PROPERTY_NAMES = new String[] {
            PROP__ID,
            PROP__ERSTELLUNGSJAHR,
            PROP__LAUFENDE_NUMMER,
            PROP__FK_GEOM,
            PROP__FK_MATERIAL,
            PROP__FK_STRASSENSCHLUESSEL,
            PROP__DOKUMENTE,
            PROP__FOTO,
            PROP__MONTEUR,
            PROP__PRUEFDATUM,
            PROP__BEMERKUNG
        };

    //~ Instance fields --------------------------------------------------------

    private Date erstellungsjahr;
    private Integer laufende_nummer;
    private GeomCustomBean fk_geom;
    private MaterialMauerlascheCustomBean fk_material;
    private TkeyStrassenschluesselCustomBean fk_strassenschluessel;
    private Collection<DmsUrlCustomBean> dokumente;
    private DmsUrlCustomBean foto;
    private String monteur;
    private Date pruefdatum;
    private String bemerkung;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbzweigdoseCustomBean object.
     */
    public MauerlascheCustomBean() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static MauerlascheCustomBean createNew() {
        return (MauerlascheCustomBean)createNew(TABLE);
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
    public Date getErstellungsjahr() {
        return erstellungsjahr;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  erstellungsjahr  DOCUMENT ME!
     */
    public void setErstellungsjahr(final Date erstellungsjahr) {
        final Date old = this.erstellungsjahr;
        this.erstellungsjahr = erstellungsjahr;
        this.propertyChangeSupport.firePropertyChange(PROP__ERSTELLUNGSJAHR, old, this.erstellungsjahr);
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
    public MaterialMauerlascheCustomBean getFk_material() {
        return fk_material;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_material  DOCUMENT ME!
     */
    public void setFk_material(final MaterialMauerlascheCustomBean fk_material) {
        final MaterialMauerlascheCustomBean old = this.fk_material;
        this.fk_material = fk_material;
        this.propertyChangeSupport.firePropertyChange(PROP__FK_MATERIAL, old, this.fk_material);
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
    public Date getPruefdatum() {
        return pruefdatum;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  pruefdatum  DOCUMENT ME!
     */
    public void setPruefdatum(final Date pruefdatum) {
        final Date old = this.pruefdatum;
        this.pruefdatum = pruefdatum;
        this.propertyChangeSupport.firePropertyChange(PROP__PRUEFDATUM, old, this.pruefdatum);
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
    public MaterialMauerlascheCustomBean getMaterial() {
        return getFk_material();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  material  DOCUMENT ME!
     */
    public void setMaterial(final MaterialMauerlascheCustomBean material) {
        setFk_material(material);
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
        if (other instanceof MauerlascheCustomBean) {
            final MauerlascheCustomBean anEntity = (MauerlascheCustomBean)other;
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
    public String getHumanReadablePosition() {
        if ((getStrassenschluessel() != null) && (getStrassenschluessel().getStrasse() != null)) {
            return getStrassenschluessel().getStrasse();
        } else {
            return super.getHumanReadablePosition();
        }
    }

    @Override
    public String toString() {
        return "de.cismet.belisEE.entity.Mauerlasche[id=" + getId() + "]";
    }

    @Override
    public FeatureAnnotationSymbol getPointAnnotationSymbol() {
        if (mapIcon != null) {
            return mapIcon;
        } else {
            mapIcon = FeatureAnnotationSymbol.newCenteredFeatureAnnotationSymbol(
                    MapIcons.icoMauerlasche,
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
}
