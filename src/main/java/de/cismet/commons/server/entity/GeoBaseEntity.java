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
package de.cismet.commons.server.entity;

import com.vividsolutions.jts.geom.Geometry;

import org.postgis.PGgeometry;

import java.awt.Color;
import java.awt.Paint;

import de.cismet.cismap.commons.gui.piccolo.FeatureAnnotationSymbol;
import de.cismet.cismap.commons.jtsgeometryfactories.PostGisGeometryFactory;

import de.cismet.commons.server.interfaces.Geom;
import de.cismet.commons.server.interfaces.GeometrySlot;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
//ToDo!!! why is the supperclass not working in the testeeapplication the field are inherited right
public class GeoBaseEntity extends BaseEntity implements GeometrySlot {

    //~ Instance fields --------------------------------------------------------

    protected transient FeatureAnnotationSymbol mapIcon = null;

    protected Geom geometrie;
//

    private Boolean isEditable = false;
    private Boolean isHidden = false;
    // workaround
    private Boolean modifiable = true;

    private boolean isSelectable = true;

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Geom getGeometrie() {
        return geometrie;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  val  DOCUMENT ME!
     */
    public void setGeometrie(final Geom val) {
        this.geometrie = val;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean isSelectable() {
        return isSelectable;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isSelectable  DOCUMENT ME!
     */
    public void setSelectable(final boolean isSelectable) {
        this.isSelectable = isSelectable;
    }

    @Override
    public boolean canBeSelected() {
        return isSelectable;
    }

    @Override
    public Geometry getGeometry() {
        if ((getGeometrie() != null) && (getGeometrie().getGeomField() != null)) {
            return PostGisGeometryFactory.createJtsGeometry(getGeometrie().getGeomField());
        } else {
            return null;
        }
    }

    @Override
    public void hide(final boolean hiding) {
        isHidden = hiding;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Boolean isModifiable() {
        return modifiable;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  modifiable  DOCUMENT ME!
     */
    public void setModifiable(final Boolean modifiable) {
        this.modifiable = modifiable;
    }

    @Override
    public boolean isEditable() {
        if (!isModifiable()) {
            return false;
        }
        if (isEditable != null) {
            return isEditable;
        } else {
            return false;
        }
    }

    // Temporary because transient attribute seems to cause nullpointer exception
    @Override
    public boolean isHidden() {
        return false;
    }

    @Override
    public void setEditable(final boolean editable) {
        isEditable = editable;
    }
//
    @Override
    public void setGeometry(final Geometry geom) {
        if (getGeometrie() == null) {
            setGeometrie(new Geom());
        }

        try {
            if (geom == null) {
                getGeometrie().setGeomField(null);
            } else {
                getGeometrie().setGeomField(PGgeometry.geomFromString(
                        PostGisGeometryFactory.getPostGisCompliantDbString(geom)));
            }
        } catch (Exception ex) {
            System.out.println("Fehler beim setzend er Geometrie");
            ex.printStackTrace();
        }
    }
//
    @Override
    public Paint getFillingPaint() {
        return Color.GRAY;
    }

    @Override
    public Paint getLinePaint() {
        return Color.BLACK;
    }

    @Override
    public int getLineWidth() {
        return 1;
    }

    @Override
    public FeatureAnnotationSymbol getPointAnnotationSymbol() {
        return null;
    }

    @Override
    public float getTransparency() {
        return 1f;
    }

    @Override
    public boolean isHighlightingEnabled() {
        return true;
    }

    // ToDo implement correct
    @Override
    public void setFillingPaint(final Paint fillingStyle) {
    }

    @Override
    public void setHighlightingEnabled(final boolean enabled) {
    }

    @Override
    public void setLinePaint(final Paint linePaint) {
    }

    @Override
    public void setLineWidth(final int width) {
    }

    @Override
    public void setPointAnnotationSymbol(final FeatureAnnotationSymbol featureAnnotationSymbol) {
    }

    @Override
    public void setTransparency(final float transparrency) {
    }

    @Override
    public String getKeyString() {
        return "";
    }

    @Override
    public void setCanBeSelected(final boolean selectable) {
    }
}
