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

import de.cismet.cismap.commons.gui.piccolo.FeatureAnnotationSymbol;

import de.cismet.commons.server.entity.GeoBaseEntity;
import de.cismet.commons.server.interfaces.DocumentContainer;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class GeometrieCustomBean extends GeoBaseEntity implements DocumentContainer, WorkbenchEntity {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(GeometrieCustomBean.class);

    public static final String TABLE = "geometrie";

    public static final String PROP__DOKUMENTE = "dokumente";
    public static final String PROP__FK_GEOM = "fk_geom";

    private static final String[] PROPERTY_NAMES = new String[] { PROP__ID, PROP__DOKUMENTE, PROP__FK_GEOM };

    //~ Instance fields --------------------------------------------------------

    private Collection<DmsUrlCustomBean> dokumente;
    private GeomCustomBean fk_geom;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbzweigdoseCustomBean object.
     */
    public GeometrieCustomBean() {
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
        return "";
    }

    @Override
    public String getKeyString() {
        return "";
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
        if (o instanceof GeomCustomBean) {
            final GeomCustomBean g = (GeomCustomBean)o;
            return 1;
        } else {
            return EntityComparator.compareTypes(this, o);
        }
    }

    @Override
    public String toString() {
        return "Geometrie";
    }
}
