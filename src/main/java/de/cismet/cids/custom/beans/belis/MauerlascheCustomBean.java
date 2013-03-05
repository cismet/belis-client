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

import java.beans.PropertyChangeEvent;

import java.util.Collection;
import java.util.Date;

import de.cismet.belis.broker.CidsBroker;

import de.cismet.belisEE.entity.Mauerlasche;

import de.cismet.belisEE.mapicons.MapIcons;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cismap.commons.gui.piccolo.FeatureAnnotationSymbol;

import de.cismet.commons.server.entity.GeoBaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class MauerlascheCustomBean extends GeoBaseEntity implements Mauerlasche {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(MauerlascheCustomBean.class);

    public static final String TABLE = "mauerlasche";

    private static final String PROP__ID = "id";
    private static final String PROP__ERSTELLUNGSJAHR = "erstellungsjahr";
    private static final String PROP__LAUFENDE_NUMMER = "laufende_nummer";
    private static final String PROP__FK_GEOM = "fk_geom";
    private static final String PROP__FK_MATERIAL = "fk_material";
    private static final String PROP__FK_STRASSENSCHLUESSEL = "fk_strassenschluessel";
    private static final String PROP__DOKUMENTE = "dokumente";
//    private static final String PROP__FOTO = "foto";
    private static final String PROP__MONTEUR = "monteur";
    private static final String PROP__PRUEFDATUM = "pruefdatum";
    private static final String PROP__BEMERKUNG = "bemerkung";

    private static final String[] PROPERTY_NAMES = new String[] {
            PROP__ID,
            PROP__ERSTELLUNGSJAHR,
            PROP__LAUFENDE_NUMMER,
            PROP__FK_GEOM,
            PROP__FK_MATERIAL,
            PROP__FK_STRASSENSCHLUESSEL,
            PROP__DOKUMENTE, /*PROP__FOTO, */
            PROP__MONTEUR,
            PROP__PRUEFDATUM,
            PROP__BEMERKUNG
        };

    //~ Instance fields --------------------------------------------------------

    private Long id;
    private Date erstellungsjahr;
    private Short laufende_nummer;
    private GeomCustomBean fk_geom;
    private MaterialMauerlascheCustomBean fk_material;
    private TkeyStrassenschluesselCustomBean fk_strassenschluessel;
    private Collection<DmsUrlCustomBean> dokumente;
    // private FotoCustomBean foto;
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
        try {
            return (MauerlascheCustomBean)CidsBean.createNewCidsBeanFromTableName(CidsBroker.BELIS_DOMAIN, TABLE);
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
    public Date getErstellungsjahr() {
        return erstellungsjahr;
    }

    @Override
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
    public Short getLaufende_nummer() {
        return laufende_nummer;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  laufende_nummer  DOCUMENT ME!
     */
    public void setLaufende_nummer(final Short laufende_nummer) {
        final Short old = this.laufende_nummer;
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

    @Override
    public Short getLaufendeNummer() {
        return getLaufende_nummer();
    }

    @Override
    public void setLaufendeNummer(final Short laufendeNummer) {
        setLaufende_nummer(laufendeNummer);
    }

    @Override
    public TkeyStrassenschluesselCustomBean getStrassenschluessel() {
        return getFk_strassenschluessel();
    }

    @Override
    public void setStrassenschluessel(final TkeyStrassenschluesselCustomBean strassenschluessel) {
        setFk_strassenschluessel(strassenschluessel);
    }

    @Override
    public MaterialMauerlascheCustomBean getMaterial() {
        return getFk_material();
    }

    @Override
    public void setMaterial(final MaterialMauerlascheCustomBean material) {
        setFk_material(material);
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
        return "de.cismet.belisEE.entity.Mauerlasche[id=" + id + "]";
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
    public void propertyChange(final PropertyChangeEvent evt) {
        super.propertyChange(evt);
        if (evt.getSource().equals(this) && !evt.getPropertyName().equals(PROP__ID)) {
            System.out.println("this entity has changed and the property was not the id");
            setWasModified(true);
        }
    }
}
