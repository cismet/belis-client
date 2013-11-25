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

import java.awt.Color;
import java.awt.Paint;

import javax.swing.JOptionPane;

import de.cismet.cids.custom.beans.belis.GeomCustomBean;

import de.cismet.cismap.commons.CrsTransformer;
import de.cismet.cismap.commons.gui.piccolo.FeatureAnnotationSymbol;
import de.cismet.cismap.commons.interaction.CismapBroker;

import de.cismet.commons.server.interfaces.GeometrySlot;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
//ToDo!!! why is the supperclass not working in the testeeapplication the field are inherited right
public abstract class GeoBaseEntity extends BaseEntity implements GeometrySlot {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(GeoBaseEntity.class);

    //~ Instance fields --------------------------------------------------------

    protected transient FeatureAnnotationSymbol mapIcon = null;

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
    public abstract GeomCustomBean getGeometrie();

    /**
     * DOCUMENT ME!
     *
     * @param  val  DOCUMENT ME!
     */
    public abstract void setGeometrie(final GeomCustomBean val);

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
            return getGeometrie().getGeomField();
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

    /**
     * DOCUMENT ME!
     *
     * @param  geom  DOCUMENT ME!
     */
    public static void transformToDefaultCrsNeeded(Geometry geom) {
        if (geom == null) {
            return;
        }

        // Srid des solefeatures prüfen
        int srid = geom.getSRID();
        final int defaultSrid = CrsTransformer.extractSridFromCrs(CismapBroker.getInstance().getDefaultCrs());
        if (srid == CismapBroker.getInstance().getDefaultCrsAlias()) {
            srid = defaultSrid;
        }
        // gegebenenfalls transformieren
        if (srid != defaultSrid) {
            final int ans = JOptionPane.showConfirmDialog(
                    null,
                    "Die angegebene Geometrie befindet sich nicht im Standard-CRS. Soll die Geometrie konvertiert werden?",
                    "Geometrie konvertieren?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (ans == JOptionPane.YES_OPTION) {
                geom = CrsTransformer.transformToDefaultCrs(geom);
                geom.setSRID(CismapBroker.getInstance().getDefaultCrsAlias());
            }
        } else {
            geom.setSRID(CismapBroker.getInstance().getDefaultCrsAlias());
        }
    }

    @Override
    public void setGeometry(final Geometry geom) {
        transformToDefaultCrsNeeded(geom);

        if (getGeometrie() == null) {
            setGeometrie(GeomCustomBean.createNew());
        }

        try {
            getGeometrie().setGeomField(geom);
        } catch (Exception ex) {
            LOG.warn("Fehler beim setzend er Geometrie", ex);
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
