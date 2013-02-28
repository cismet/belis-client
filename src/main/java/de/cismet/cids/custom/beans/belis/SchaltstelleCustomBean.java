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

import de.cismet.belisEEold.entity.Schaltstelle;

import de.cismet.cismap.commons.gui.piccolo.FeatureAnnotationSymbol;

import de.cismet.commons.server.entity.GeoBaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class SchaltstelleCustomBean extends GeoBaseEntity implements Schaltstelle {

    //~ Instance fields --------------------------------------------------------

    protected Set<DmsUrlCustomBean> dokumente;

    private Long id;
    private TkeyStrassenschluesselCustomBean strassenschluessel;
    private Short laufendeNummer;
    private String hausnummer;
    private String schaltstellenNummer;
    private String zusaetzlicheStandortbezeichnung;
    private BauartCustomBean bauart;
    private String bemerkung;
    private Date erstellungsjahr;

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
    public String getHausnummer() {
        return hausnummer;
    }

    @Override
    public void setHausnummer(final String hausnummer) {
        this.hausnummer = hausnummer;
        getPropertyChangeSupport().firePropertyChange(PROP_HAUSNUMMER, null, getHausnummer());
    }

    @Override
    public String getSchaltstellenNummer() {
        return schaltstellenNummer;
    }

    @Override
    public void setSchaltstellenNummer(final String schaltstellenNummer) {
        final String oldSchaltstellenNummer = getSchaltstellenNummer();
        this.schaltstellenNummer = schaltstellenNummer;
        getPropertyChangeSupport().firePropertyChange(
            PROP_SCHALTSTELLENNUMMER,
            oldSchaltstellenNummer,
            getSchaltstellenNummer());
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
    public BauartCustomBean getBauart() {
        return bauart;
    }

    @Override
    public void setBauart(final BauartCustomBean bauart) {
        this.bauart = bauart;
        getPropertyChangeSupport().firePropertyChange(PROP_BAUART, null, getBauart());
    }

    @Override
    public String getBemerkung() {
        return bemerkung;
    }

    @Override
    public void setBemerkung(final String bemerkung) {
        this.bemerkung = bemerkung;
        getPropertyChangeSupport().firePropertyChange(PROP_BEMERKUNG, null, getBemerkung());
    }

    @Override
    public String getZusaetzlicheStandortbezeichnung() {
        return zusaetzlicheStandortbezeichnung;
    }

    @Override
    public void setZusaetzlicheStandortbezeichnung(final String zusaetzlicheStandortbeschreibung) {
        this.zusaetzlicheStandortbezeichnung = zusaetzlicheStandortbeschreibung;
        getPropertyChangeSupport().firePropertyChange(
            PROP_ZUSAETZLICHE_STANDORTBEZEICHNUNG,
            null,
            getZusaetzlicheStandortbezeichnung());
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
        if (other instanceof SchaltstelleCustomBean) {
            final SchaltstelleCustomBean anEntity = (SchaltstelleCustomBean)other;
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
        return "de.cismet.belisEE.entity.Schaltstelle[id=" + id + "]";
    }

    @Override
    public String getKeyString() {
        String schaltellennummer = "";
        if (getSchaltstellenNummer() != null) {
            schaltellennummer = getSchaltstellenNummer();
        }
        return schaltellennummer;
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
    public FeatureAnnotationSymbol getPointAnnotationSymbol() {
        if (mapIcon != null) {
            return mapIcon;
        } else {
            mapIcon = FeatureAnnotationSymbol.newCenteredFeatureAnnotationSymbol(
                    MapIcons.icoSchaltstelle,
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
