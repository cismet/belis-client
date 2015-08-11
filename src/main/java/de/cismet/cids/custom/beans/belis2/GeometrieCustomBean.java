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

import java.awt.Color;
import java.awt.Paint;

import java.util.Collection;

import de.cismet.belisEE.mapicons.MapIcons;

import de.cismet.belisEE.util.EntityComparator;

import de.cismet.cids.custom.tostringconverter.belis2.GeometrieToStringConverter;

import de.cismet.cismap.commons.gui.piccolo.FeatureAnnotationSymbol;

import de.cismet.commons.server.entity.WorkbenchEntity;
import de.cismet.commons.server.entity.WorkbenchFeatureEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class GeometrieCustomBean extends WorkbenchFeatureEntity {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(GeometrieCustomBean.class);

    public static final String TABLE = "geometrie";

    public static final String PROP__AR_DOKUMENTE = "ar_dokumente";
    public static final String PROP__FK_GEOM = "fk_geom";
    public static final String PROP__BEZEICHNUNG = "bezeichnung";

    //~ Instance fields --------------------------------------------------------

    private Collection<DmsUrlCustomBean> ar_dokumente;
    private String bezeichnung;
    private GeomCustomBean fk_geom;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbzweigdoseCustomBean object.
     */
    public GeometrieCustomBean() {
        addPropertyNames(
            new String[] {
                PROP__AR_DOKUMENTE,
                PROP__FK_GEOM,
                PROP__BEZEICHNUNG
            });
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static GeometrieCustomBean createNew() {
        return (GeometrieCustomBean)createNew(TABLE);
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
     * @param  dokumente  DOCUMENT ME!
     */
    public void setAr_dokumente(final Collection<DmsUrlCustomBean> dokumente) {
        final Collection<DmsUrlCustomBean> old = this.ar_dokumente;
        this.ar_dokumente = dokumente;
        this.propertyChangeSupport.firePropertyChange(PROP__AR_DOKUMENTE, old, this.ar_dokumente);
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
    @Override
    public Collection<DmsUrlCustomBean> getDokumente() {
        return getAr_dokumente();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  dokumente  DOCUMENT ME!
     */
    @Override
    public void setDokumente(final Collection<DmsUrlCustomBean> dokumente) {
        setAr_dokumente(dokumente);
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

    @Override
    public FeatureAnnotationSymbol getPointAnnotationSymbol() {
        if (mapIcon != null) {
            return mapIcon;
        } else {
            mapIcon = FeatureAnnotationSymbol.newCenteredFeatureAnnotationSymbol(
                    MapIcons.icoAbzweigdose,
                    null);
            return mapIcon;
        }
    }

    @Override
    public Paint getFillingPaint() {
        return new Color(Color.BLUE.getRed() / 255f,
                Color.BLUE.getGreen()
                        / 255f,
                Color.BLUE.getBlue()
                        / 255f,
                getTransparency());
    }

    @Override
    public float getTransparency() {
        return 0.5f;
    }

    @Override
    public GeomCustomBean getGeometrie() {
        return getFk_geom();
    }

    @Override
    public void setGeometrie(final GeomCustomBean geometrie) {
        setFk_geom(geometrie);
    }

    @Override
    public String getHumanReadablePosition() {
        return new GeometrieToStringConverter().getHumanReadablePosition(this);
    }

    @Override
    public String getKeyString() {
        return new GeometrieToStringConverter().getKeyString(this);
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
        if (o instanceof GeometrieCustomBean) {
            final GeometrieCustomBean g = (GeometrieCustomBean)o;
            return 1;
        } else {
            return EntityComparator.compareTypes(this, o);
        }
    }
}
