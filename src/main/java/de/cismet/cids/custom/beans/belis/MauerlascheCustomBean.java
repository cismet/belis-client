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

import java.util.Date;
import java.util.Set;

import de.cismet.belisEE.mapicons.MapIcons;

import de.cismet.belisEEold.entity.Mauerlasche;

import de.cismet.cismap.commons.gui.piccolo.FeatureAnnotationSymbol;

import de.cismet.commons.server.entity.GeoBaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class MauerlascheCustomBean extends GeoBaseEntity implements Mauerlasche {

    //~ Instance fields --------------------------------------------------------

    protected Set<DmsUrlCustomBean> dokumente;

    private Long id;
    private TkeyStrassenschluesselCustomBean strassenschluessel;
    private Short laufendeNummer;
    private Date erstellungsjahr;
    private MaterialMauerlascheCustomBean material;

    //~ Methods ----------------------------------------------------------------

    @Override
    public Set<DmsUrlCustomBean> getDokumente() {
        return dokumente;
    }

    @Override
    public void setDokumente(final Set<DmsUrlCustomBean> dokumente) {
        this.dokumente = dokumente;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
        getPropertyChangeSupport().firePropertyChange(PROP_ID, null, getId());
    }

    @Override
    public Date getErstellungsjahr() {
        return erstellungsjahr;
    }

    @Override
    public void setErstellungsjahr(final Date erstellungsjahr) {
        this.erstellungsjahr = erstellungsjahr;
        getPropertyChangeSupport().firePropertyChange(PROP_ERSTELLUNGSJAHR, null, getErstellungsjahr());
    }

    @Override
    public Short getLaufendeNummer() {
        return laufendeNummer;
    }

    @Override
    public void setLaufendeNummer(final Short laufendeNummer) {
        this.laufendeNummer = laufendeNummer;
        getPropertyChangeSupport().firePropertyChange(PROP_LAUFENDE_NUMMER, null, getLaufendeNummer());
    }

    @Override
    public TkeyStrassenschluesselCustomBean getStrassenschluessel() {
        return strassenschluessel;
    }

    @Override
    public void setStrassenschluessel(final TkeyStrassenschluesselCustomBean strassenschluessel) {
        final TkeyStrassenschluesselCustomBean oldStrassenschluessel = getStrassenschluessel();
        this.strassenschluessel = strassenschluessel;
        getPropertyChangeSupport().firePropertyChange(
            PROP_STRASSENSCHLUESSEL,
            oldStrassenschluessel,
            strassenschluessel);
    }

    @Override
    public MaterialMauerlascheCustomBean getMaterial() {
        return material;
    }

    @Override
    public void setMaterial(final MaterialMauerlascheCustomBean material) {
        this.material = material;
        getPropertyChangeSupport().firePropertyChange(PROP_MATERIAL, null, getMaterial());
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
        if (evt.getSource().equals(this) && !evt.getPropertyName().equals(PROP_ID)) {
            System.out.println("this entity has changed and the property was not the id");
            setWasModified(true);
        }
    }
}
