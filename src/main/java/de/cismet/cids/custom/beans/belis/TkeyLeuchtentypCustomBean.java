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

import de.cismet.belisEEold.entity.Leuchtentyp;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class TkeyLeuchtentypCustomBean extends BaseEntity implements Leuchtentyp {

    //~ Instance fields --------------------------------------------------------

    private String leuchtentyp;
    private String fabrikat;
    private Double bestueckung;
    private Double leistung;
    private Double leistungBrutto;
    private String lampe;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new TkeyLeuchtentypCustomBean object.
     */
    public TkeyLeuchtentypCustomBean() {
    }

    /**
     * Creates a new TkeyLeuchtentypCustomBean object.
     *
     * @param  leuchtentyp  DOCUMENT ME!
     */
    public TkeyLeuchtentypCustomBean(final String leuchtentyp) {
        this.leuchtentyp = leuchtentyp;
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public String getLeuchtentyp() {
        return leuchtentyp;
    }

    @Override
    public void setLeuchtentyp(final String leuchtentyp) {
        this.leuchtentyp = leuchtentyp;
    }

    @Override
    public String getFabrikat() {
        return fabrikat;
    }

    @Override
    public void setFabrikat(final String fabrikat) {
        this.fabrikat = fabrikat;
    }

    @Override
    public Double getBestueckung() {
        return bestueckung;
    }

    @Override
    public void setBestueckung(final Double bestueckung) {
        this.bestueckung = bestueckung;
    }

    @Override
    public Double getLeistung() {
        return leistung;
    }

    @Override
    public void setLeistung(final Double leistung) {
        this.leistung = leistung;
    }

    @Override
    public Double getLeistungBrutto() {
        return leistungBrutto;
    }

    @Override
    public void setLeistungBrutto(final Double leistungBrutto) {
        this.leistungBrutto = leistungBrutto;
    }

    @Override
    public String getLampe() {
        return lampe;
    }

    @Override
    public void setLampe(final String lampe) {
        this.lampe = lampe;
    }

    @Override
    public int hashCode() {
        if (this.getLeuchtentyp() == null) {
            return super.hashCode();
        }
        return this.getLeuchtentyp().hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof TkeyLeuchtentypCustomBean) {
            final TkeyLeuchtentypCustomBean anEntity = (TkeyLeuchtentypCustomBean)other;
            if (this == other) {
                return true;
            } else if ((other == null) || (!this.getClass().isAssignableFrom(other.getClass()))) {
                return false;
            } else if ((this.getLeuchtentyp() == null) || (anEntity.getLeuchtentyp() == null)) {
                return false;
            } else {
                return this.getLeuchtentyp().equals(anEntity.getLeuchtentyp());
            }
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "de.cismet.belis.entity.Leuchtentyp[leuchtentyp=" + leuchtentyp + "]";
    }

    @Override
    public String getKeyString() {
        if (getLeuchtentyp() != null) {
            return getLeuchtentyp();
        } else {
            return "";
        }
    }
}
