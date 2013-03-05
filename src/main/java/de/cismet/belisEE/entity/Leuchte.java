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
package de.cismet.belisEE.entity;

import java.beans.PropertyChangeListener;

import java.io.Serializable;

import java.util.Date;

import de.cismet.cids.custom.beans.belis.TdtaStandortMastCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyDoppelkommandoCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyEnergielieferantCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyKennzifferCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyLeuchtentypCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyStrassenschluesselCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyUnterhLeuchteCustomBean;

import de.cismet.commons.server.interfaces.DocumentContainer;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public interface Leuchte extends Serializable, PropertyChangeListener, DocumentContainer {

    //~ Instance fields --------------------------------------------------------

    String PROP_STRASSENSCHLUESSEL = "Leuchte.strassenschluessel";
    String PROP_KENNZIFFER = "Leuchte.Kennziffer";
    String PROP_LEUCHTENNUMMER = "Leuchte.leuchtennummer";

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    TdtaStandortMastCustomBean getStandort();

    /**
     * DOCUMENT ME!
     *
     * @param  standort  DOCUMENT ME!
     */
    void setStandort(final TdtaStandortMastCustomBean standort);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    Short getStadtbezirk();

    /**
     * DOCUMENT ME!
     *
     * @param  stadtbezirk  DOCUMENT ME!
     */
    void setStadtbezirk(final Short stadtbezirk);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    String getPlz();

    /**
     * DOCUMENT ME!
     *
     * @param  plz  DOCUMENT ME!
     */
    void setPlz(final String plz);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    TkeyStrassenschluesselCustomBean getStrassenschluessel();

    /**
     * DOCUMENT ME!
     *
     * @param  strassenschluessel  DOCUMENT ME!
     */
    void setStrassenschluessel(final TkeyStrassenschluesselCustomBean strassenschluessel);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    TkeyKennzifferCustomBean getKennziffer();

    /**
     * DOCUMENT ME!
     *
     * @param  kennziffer  DOCUMENT ME!
     */
    void setKennziffer(final TkeyKennzifferCustomBean kennziffer);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    Short getLaufendeNummer();

    /**
     * DOCUMENT ME!
     *
     * @param  laufendeNummer  DOCUMENT ME!
     */
    void setLaufendeNummer(final Short laufendeNummer);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    Short getLeuchtennummer();

    /**
     * DOCUMENT ME!
     *
     * @param  leuchtennummer  DOCUMENT ME!
     */
    void setLeuchtennummer(final Short leuchtennummer);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    String getSchaltstelle();

    /**
     * DOCUMENT ME!
     *
     * @param  schaltstelle  DOCUMENT ME!
     */
    void setSchaltstelle(final String schaltstelle);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    TkeyEnergielieferantCustomBean getEnergielieferant();

    /**
     * DOCUMENT ME!
     *
     * @param  energielieferant  DOCUMENT ME!
     */
    void setEnergielieferant(final TkeyEnergielieferantCustomBean energielieferant);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    String getRundsteuerempfaenger();

    /**
     * DOCUMENT ME!
     *
     * @param  rundsteuerempfaenger  DOCUMENT ME!
     */
    void setRundsteuerempfaenger(final String rundsteuerempfaenger);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    TkeyLeuchtentypCustomBean getLeuchtentyp();

    /**
     * DOCUMENT ME!
     *
     * @param  leuchtentyp  DOCUMENT ME!
     */
    void setLeuchtentyp(final TkeyLeuchtentypCustomBean leuchtentyp);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    TkeyUnterhLeuchteCustomBean getUnterhaltspflichtLeuchte();

    /**
     * DOCUMENT ME!
     *
     * @param  unterhaltspflichtLeuchte  DOCUMENT ME!
     */
    void setUnterhaltspflichtLeuchte(final TkeyUnterhLeuchteCustomBean unterhaltspflichtLeuchte);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    boolean getZaehler();

    /**
     * DOCUMENT ME!
     *
     * @param  zaehler  DOCUMENT ME!
     */
    void setZaehler(final boolean zaehler);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    TkeyDoppelkommandoCustomBean getDk1();

    /**
     * DOCUMENT ME!
     *
     * @param  dk1  DOCUMENT ME!
     */
    void setDk1(final TkeyDoppelkommandoCustomBean dk1);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    Short getAnzahl1DK();

    /**
     * DOCUMENT ME!
     *
     * @param  anzahl1DK  DOCUMENT ME!
     */
    void setAnzahl1DK(final Short anzahl1DK);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    TkeyDoppelkommandoCustomBean getDk2();

    /**
     * DOCUMENT ME!
     *
     * @param  dk2  DOCUMENT ME!
     */
    void setDk2(final TkeyDoppelkommandoCustomBean dk2);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    Short getAnzahl2DK();

    /**
     * DOCUMENT ME!
     *
     * @param  anzahl2DK  DOCUMENT ME!
     */
    void setAnzahl2DK(final Short anzahl2DK);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    String getMontageFirmaLeuchte();

    /**
     * DOCUMENT ME!
     *
     * @param  montageFirmaLeuchte  DOCUMENT ME!
     */
    void setMontageFirmaLeuchte(final String montageFirmaLeuchte);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    Date getInbetriebnahmeLeuchte();

    /**
     * DOCUMENT ME!
     *
     * @param  inbetriebnahmeLeuchte  DOCUMENT ME!
     */
    void setInbetriebnahmeLeuchte(final Date inbetriebnahmeLeuchte);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    String getBemerkungen();

    /**
     * DOCUMENT ME!
     *
     * @param  bemerkungen  DOCUMENT ME!
     */
    void setBemerkungen(final String bemerkungen);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    Integer getId();

    /**
     * DOCUMENT ME!
     *
     * @param  id  DOCUMENT ME!
     */
    void setId(final Integer id);
}
