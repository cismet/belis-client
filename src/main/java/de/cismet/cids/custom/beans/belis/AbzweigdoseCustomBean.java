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

import de.cismet.belis.broker.CidsBroker;

import de.cismet.belisEE.entity.Abzweigdose;

import de.cismet.belisEE.mapicons.MapIcons;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cismap.commons.gui.piccolo.FeatureAnnotationSymbol;

import de.cismet.commons.server.entity.GeoBaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class AbzweigdoseCustomBean extends GeoBaseEntity implements Abzweigdose {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(AbzweigdoseCustomBean.class);

    public static final String TABLE = "abzweigdose";

    private static final String PROP__DOKUMENTE = "dokumente";
    private static final String PROP__FK_GEOM = "fk_geom";

    private static final String[] PROPERTY_NAMES = new String[] { PROP__ID, PROP__DOKUMENTE, PROP__FK_GEOM };

    //~ Instance fields --------------------------------------------------------

    private Collection<DmsUrlCustomBean> dokumente;
    private GeomCustomBean fk_geom;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbzweigdoseCustomBean object.
     */
    public AbzweigdoseCustomBean() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static AbzweigdoseCustomBean createNew() {
        try {
            return (AbzweigdoseCustomBean)CidsBean.createNewCidsBeanFromTableName(CidsBroker.BELIS_DOMAIN, TABLE);
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
    public Collection<DmsUrlCustomBean> getDokumente() {
        return dokumente;
    }

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
    public int hashCode() {
        if (this.getId() == null) {
            return super.hashCode();
        }
        return this.getId().hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof AbzweigdoseCustomBean) {
            final AbzweigdoseCustomBean anEntity = (AbzweigdoseCustomBean)other;
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
    public String toString() {
        return "de.cismet.belisEE.entity.Abzweigdose[id=" + getId() + "]";
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
    public GeomCustomBean getGeometrie() {
        return getFk_geom();
    }

    @Override
    public void setGeometrie(final GeomCustomBean geometrie) {
        setFk_geom(geometrie);
    }
}
