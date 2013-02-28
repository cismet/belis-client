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

import java.awt.Color;
import java.awt.Paint;

import java.beans.PropertyChangeEvent;

import java.util.Set;

import de.cismet.belisEEold.entity.Leitung;

import de.cismet.commons.server.entity.GeoBaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class LeitungCustomBean extends GeoBaseEntity implements Leitung {

    //~ Instance fields --------------------------------------------------------

    protected Set<DmsUrlCustomBean> dokumente;

    private Long id;
    private MaterialLeitungCustomBean material;
    private LeitungstypCustomBean leitungstyp;
    private QuerschnittCustomBean querschnitt;

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
    public MaterialLeitungCustomBean getMaterial() {
        return material;
    }

    @Override
    public void setMaterial(final MaterialLeitungCustomBean material) {
        this.material = material;
        getPropertyChangeSupport().firePropertyChange(PROP_MATERIAL, null, getMaterial());
    }

    @Override
    public LeitungstypCustomBean getLeitungstyp() {
        return leitungstyp;
    }

    @Override
    public void setLeitungstyp(final LeitungstypCustomBean leitungstyp) {
        final LeitungstypCustomBean oldLeitungstyp = getLeitungstyp();
        this.leitungstyp = leitungstyp;
        getPropertyChangeSupport().firePropertyChange(PROP_LEITUNGSTYP, oldLeitungstyp, getLeitungstyp());
    }

    @Override
    public QuerschnittCustomBean getQuerschnitt() {
        return querschnitt;
    }

    @Override
    public void setQuerschnitt(final QuerschnittCustomBean querschnitt) {
        this.querschnitt = querschnitt;
        getPropertyChangeSupport().firePropertyChange(PROP_QUERSCHNITT, null, getQuerschnitt());
    }
    // ToDo change the style of the leitung depending to the attributes
    @Override
    public int hashCode() {
        if (this.getId() == null) {
            return super.hashCode();
        }
        return this.getId().hashCode();
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
    public boolean equals(final Object other) {
        if (other instanceof LeitungCustomBean) {
            final LeitungCustomBean anEntity = (LeitungCustomBean)other;
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
    public String getKeyString() {
        String leitungstyp = "";
        if ((getLeitungstyp() != null) && (getLeitungstyp().getBezeichnung() != null)) {
            leitungstyp = getLeitungstyp().getBezeichnung();
        }
        return leitungstyp;
    }

    @Override
    public String toString() {
        return "de.cismet.belisEE.entity.Leitung[id=" + id + "]";
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
