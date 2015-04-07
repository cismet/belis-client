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

import de.cismet.cids.custom.tostringconverter.belis2.MauerlascheToStringConverter;

import de.cismet.cismap.commons.gui.piccolo.FeatureAnnotationSymbol;

import de.cismet.commons.server.entity.WorkbenchEntity;
import de.cismet.commons.server.entity.WorkbenchFeatureEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class MauerlascheCustomBean extends WorkbenchFeatureEntity {

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

    //~ Instance fields --------------------------------------------------------

    private Timestamp erstellungsjahr;
    private Integer laufende_nummer;
    private GeomCustomBean fk_geom;
    private MaterialMauerlascheCustomBean fk_material;
    private TkeyStrassenschluesselCustomBean fk_strassenschluessel;
    private Collection<DmsUrlCustomBean> dokumente;
    private DmsUrlCustomBean foto;
    private String monteur;
    private Timestamp pruefdatum;
    private String bemerkung;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbzweigdoseCustomBean object.
     */
    public MauerlascheCustomBean() {
        addPropertyNames(
            new String[] {
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
            });
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
    public String getHumanReadablePosition() {
        if ((getStrassenschluessel() != null) && (getStrassenschluessel().getStrasse() != null)) {
            return getStrassenschluessel().getStrasse();
        } else {
            return "";
        }
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

    /**
     * DOCUMENT ME!
     *
     * @param   o  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public int compareTo(final WorkbenchEntity o) {
        if (o instanceof MauerlascheCustomBean) {
            final MauerlascheCustomBean m = (MauerlascheCustomBean)o;
            if (getStrassenschluessel() != null) {
                if (m.getStrassenschluessel() == null) {
                    return 1;
                } else {
                    final int compStrSchl = getStrassenschluessel().compareTo(m.getStrassenschluessel());
                    if (compStrSchl != 0) {
                        return compStrSchl;
                    }
                }
            } else {
                return -1;
            }

            if (getLaufende_nummer() != null) {
                if (m.getLaufende_nummer() == null) {
                    return 1;
                } else {
                    final int compLaufNummer = getLaufende_nummer().compareTo(m.getLaufende_nummer());
                    if (compLaufNummer != 0) {
                        return compLaufNummer;
                    } else {
                        if (getId() == null) {
                            return 1;
                        } else {
                            return getId().compareTo(m.getId());
                        }
                    }
                }
            } else {
                return -1;
            }
        } else {
            return EntityComparator.compareTypes(this, o);
        }
    }

    @Override
    public String getKeyString() {
        String laufendeNummer = "";
        if (getLaufende_nummer() != null) {
            laufendeNummer = getLaufende_nummer().toString();
        }
        return laufendeNummer;
    }

    @Override
    public String toString() {
        return new MauerlascheToStringConverter().convert(this.getMetaObject());
    }
}
