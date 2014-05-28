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

import de.cismet.belisEE.util.EntityComparator;

import de.cismet.cids.custom.tostringconverter.belis2.LeitungToStringConverter;

import de.cismet.commons.server.entity.GeoBaseEntity;
import de.cismet.commons.server.interfaces.DocumentContainer;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class LeitungCustomBean extends GeoBaseEntity implements WorkbenchEntity, DocumentContainer {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(LeitungCustomBean.class);

    public static final String TABLE = "leitung";

    public static final String PROP__FK_GEOM = "fk_geom";
    public static final String PROP__FK_MATERIAL = "fk_material";
    public static final String PROP__FK_QUERSCHNITT = "fk_querschnitt";
    public static final String PROP__FK_LEITUNGSTYP = "fk_leitungstyp";
    public static final String PROP__DOKUMENTE = "dokumente";

    private static final String[] PROPERTY_NAMES = new String[] {
            PROP__ID,
            PROP__FK_GEOM,
            PROP__FK_MATERIAL,
            PROP__FK_QUERSCHNITT,
            PROP__FK_LEITUNGSTYP,
            PROP__DOKUMENTE
        };

    //~ Instance fields --------------------------------------------------------

    private GeomCustomBean fk_geom;
    private MaterialLeitungCustomBean fk_material;
    private QuerschnittCustomBean fk_querschnitt;
    private LeitungstypCustomBean fk_leitungstyp;
    private Collection<DmsUrlCustomBean> dokumente;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbzweigdoseCustomBean object.
     */
    public LeitungCustomBean() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static LeitungCustomBean createNew() {
        return (LeitungCustomBean)createNew(TABLE);
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
    public MaterialLeitungCustomBean getFk_material() {
        return fk_material;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_material  DOCUMENT ME!
     */
    public void setFk_material(final MaterialLeitungCustomBean fk_material) {
        final MaterialLeitungCustomBean old = this.fk_material;
        this.fk_material = fk_material;
        this.propertyChangeSupport.firePropertyChange(PROP__FK_MATERIAL, old, this.fk_material);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public QuerschnittCustomBean getFk_querschnitt() {
        return fk_querschnitt;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_querschnitt  DOCUMENT ME!
     */
    public void setFk_querschnitt(final QuerschnittCustomBean fk_querschnitt) {
        final QuerschnittCustomBean old = this.fk_querschnitt;
        this.fk_querschnitt = fk_querschnitt;
        this.propertyChangeSupport.firePropertyChange(PROP__FK_QUERSCHNITT, old, this.fk_querschnitt);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public LeitungstypCustomBean getFk_leitungstyp() {
        return fk_leitungstyp;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fk_leitungstyp  DOCUMENT ME!
     */
    public void setFk_leitungstyp(final LeitungstypCustomBean fk_leitungstyp) {
        final LeitungstypCustomBean old = this.fk_leitungstyp;
        this.fk_leitungstyp = fk_leitungstyp;
        this.propertyChangeSupport.firePropertyChange(PROP__FK_LEITUNGSTYP, old, this.fk_leitungstyp);
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
    public MaterialLeitungCustomBean getMaterial() {
        return getFk_material();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  material  DOCUMENT ME!
     */
    public void setMaterial(final MaterialLeitungCustomBean material) {
        setFk_material(material);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public LeitungstypCustomBean getLeitungstyp() {
        return getFk_leitungstyp();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  leitungstyp  DOCUMENT ME!
     */
    public void setLeitungstyp(final LeitungstypCustomBean leitungstyp) {
        setFk_leitungstyp(leitungstyp);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public QuerschnittCustomBean getQuerschnitt() {
        return getFk_querschnitt();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  querschnitt  DOCUMENT ME!
     */
    public void setQuerschnitt(final QuerschnittCustomBean querschnitt) {
        setFk_querschnitt(querschnitt);
    }

    @Override
    public int getLineWidth() {
        return 4;
    }

    @Override
    public Paint getLinePaint() {
        if ((getLeitungstyp() != null) && (getLeitungstyp().getBezeichnung() != null)) {
            if (getLeitungstyp().getBezeichnung().equals("Erdkabel")) {
                return new Color(126, 46, 0, 255);
            } else if (getLeitungstyp().getBezeichnung().equals("Tragseil mit Freileitung")) {
                return new Color(102, 0, 102, 255);
            } else if (getLeitungstyp().getBezeichnung().equals("Tragseil")) {
                return new Color(0, 0, 255, 255);
            } else if (getLeitungstyp().getBezeichnung().equals("Freileitung")) {
                return new Color(255, 0, 0, 255);
            }
        }
        return Color.black;
    }

    @Override
    public String getKeyString() {
        String leitungstyp = "";
        if ((getLeitungstyp() != null) && (getLeitungstyp().getBezeichnung() != null)) {
            leitungstyp = getLeitungstyp().getBezeichnung();
        }
        return leitungstyp;
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
        if (o instanceof LeitungCustomBean) {
            final LeitungCustomBean l = (LeitungCustomBean)o;
            return 1;
        } else {
            return EntityComparator.compareTypes(this, o);
        }
    }

    @Override
    public String getHumanReadablePosition() {
        return "";
    }

    @Override
    public String toString() {
        return new LeitungToStringConverter().convert(this.getMetaObject());
    }
}
