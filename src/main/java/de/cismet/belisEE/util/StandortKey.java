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
package de.cismet.belisEE.util;

import java.io.Serializable;

import de.cismet.cids.custom.beans.belis.TdtaStandortMastCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyKennzifferCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyStrassenschluesselCustomBean;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public class StandortKey implements Serializable {

    //~ Instance fields --------------------------------------------------------

    private TkeyStrassenschluesselCustomBean strassenschluessel;

    private TkeyKennzifferCustomBean kennziffer;

    private Short laufendeNummer;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new StandortKey object.
     */
    public StandortKey() {
    }

    /**
     * Creates a new StandortKey object.
     *
     * @param  strassenschluessel  DOCUMENT ME!
     * @param  kennziffer          DOCUMENT ME!
     * @param  laufendeNummer      DOCUMENT ME!
     */
    public StandortKey(final TkeyStrassenschluesselCustomBean strassenschluessel,
            final TkeyKennzifferCustomBean kennziffer,
            final Short laufendeNummer) {
        this.strassenschluessel = strassenschluessel;
        this.kennziffer = kennziffer;
        this.laufendeNummer = laufendeNummer;
    }

    /**
     * Creates a new StandortKey object.
     *
     * @param  pk              DOCUMENT ME!
     * @param  kennziffer      DOCUMENT ME!
     * @param  laufendeNummer  DOCUMENT ME!
     */
    public StandortKey(final String pk, final Short kennziffer, final Short laufendeNummer) {
        this.strassenschluessel = new TkeyStrassenschluesselCustomBean();
        this.strassenschluessel.setPk(pk);
        this.kennziffer = new TkeyKennzifferCustomBean(kennziffer);
        this.laufendeNummer = laufendeNummer;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public TkeyKennzifferCustomBean getKennziffer() {
        return kennziffer;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  kennziffer  DOCUMENT ME!
     */
    public void setKennziffer(final TkeyKennzifferCustomBean kennziffer) {
        this.kennziffer = kennziffer;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Short getLaufendeNummer() {
        return laufendeNummer;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  laufendeNummer  DOCUMENT ME!
     */
    public void setLaufendeNummer(final Short laufendeNummer) {
        this.laufendeNummer = laufendeNummer;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public TkeyStrassenschluesselCustomBean getStrassenschluessel() {
        return strassenschluessel;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  strassenschluessel  DOCUMENT ME!
     */
    public void setStrassenschluessel(final TkeyStrassenschluesselCustomBean strassenschluessel) {
        this.strassenschluessel = strassenschluessel;
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        if ((getStrassenschluessel() != null) && (getStrassenschluessel().getPk() != null)) {
            hashCode = hashCode ^ getStrassenschluessel().getPk().hashCode();
        }
        if ((getKennziffer() != null) && (getKennziffer().getKennziffer() != null)) {
            hashCode = hashCode ^ getKennziffer().getKennziffer().hashCode();
        }
        if (getLaufendeNummer() != null) {
            hashCode = hashCode ^ getLaufendeNummer().hashCode();
        }
        return hashCode;
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof TdtaStandortMastCustomBean) {
            final StandortKey anEntity = (StandortKey)other;
            if (this == other) {
                return true;
            } else if ((other == null) || (!this.getClass().isAssignableFrom(other.getClass()))) {
                return false;
            } else if ((this.getStrassenschluessel() == null) || (this.getStrassenschluessel().getPk() == null)
                        || ((this.getKennziffer() == null) && (this.getKennziffer().getKennziffer() == null))
                        || (this.getLaufendeNummer() == null)
                        || (anEntity.getStrassenschluessel() == null)
                        || (anEntity.getStrassenschluessel().getPk() == null)
                        || ((anEntity.getKennziffer() == null) && (anEntity.getKennziffer().getKennziffer() == null))
                        || (anEntity.getLaufendeNummer() == null)) {
                return false;
            } else {
                return this.getStrassenschluessel().getPk().equals(anEntity.getStrassenschluessel().getPk())
                            && this.getKennziffer().getKennziffer().equals(anEntity.getKennziffer().getKennziffer())
                            && this.getLaufendeNummer().equals(this.getLaufendeNummer());
            }
        } else {
            return false;
        }
    }
}
