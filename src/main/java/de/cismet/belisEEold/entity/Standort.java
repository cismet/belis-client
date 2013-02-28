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
package de.cismet.belisEEold.entity;

import java.beans.PropertyChangeListener;

import java.io.Serializable;

import java.util.Date;
import java.util.Set;

import de.cismet.belisEE.util.StandortKey;

import de.cismet.cids.custom.beans.belis.TdtaLeuchteCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyBezirkCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyKennzifferCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyKlassifizierungCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyMastartCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyMasttypCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyStrassenschluesselCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyUnterhMastCustomBean;

import de.cismet.commons.server.interfaces.DocumentContainer;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public interface Standort extends Serializable, PropertyChangeListener, DocumentContainer {

    //~ Static fields/initializers ---------------------------------------------

    String PROP_PLZ = "Standort.plz";
    String PROP_STRASSENSCHLUESSEL = "Standort.strassenschluessel";
    String PROP_KENNZIFFER = "Standort.Kennziffer";
    String PROP_LAUFENDENUMMER = "Standort.laufendeNummer";
    String PROP_STANDORTANGABE = "Standort.standortangabe";
    String PROP_HAUSNUMMER = "Standort.hausnummer";
    String PROP_STADTBEZIRK = "Standort.stadtbezirk";
    String PROP_MASTART = "Standort.mastart";
    String PROP_KLASSIFIZIERUNG = "Standort.klassifizierung";
    String PROP_MASTANSTRICH = "Standort.mastanstrich";
    String PROP_MASTSCHUTZ = "Standort.mastschutz";
    String PROP_UNTERHALTSPFLICHT = "Standort.unterhaltspflichtMast";
    String PROP_MASTTYP = "Standort.masttyp";
    String PROP_MONTAGEFIRMA = "Standort.montagefirma";
    String PROP_INBETRIEBNAHME_MAST = "Standort.inbetriebnahmeMast";
    String PROP_VERRECHNUNGSEINHEIT = "Standort.verrechnungseinheit";
    String PROP_BEMERKUNG = "Standort.bemerkungen";
    String PROP_LETZTE_AENDERUNG = "Standort.letzteAenderung";

    String PROP_ID = "Standort.id"; // ToDo document limitations
    String PROP_LEUCHTEN = "Standort.leuchten";
    String PROP_VIRTUELLER_STANDORT = "Standort.virtuellerStandort";

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    Boolean isVirtuellerStandort();

    /**
     * DOCUMENT ME!
     *
     * @param  virtualStandort  DOCUMENT ME!
     */
    void setVirtuellerStandort(final Boolean virtualStandort);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    boolean isStandortMast();

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    Set<TdtaLeuchteCustomBean> getLeuchten();

    /**
     * DOCUMENT ME!
     *
     * @param  leuchten  DOCUMENT ME!
     */
    void setLeuchten(final Set<TdtaLeuchteCustomBean> leuchten);

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
     * @param  lfdNummer  DOCUMENT ME!
     */
    void setLaufendeNummer(final Short lfdNummer);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    String getStandortangabe();

    /**
     * DOCUMENT ME!
     *
     * @param  standortangabe  DOCUMENT ME!
     */
    void setStandortangabe(final String standortangabe);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    String getHausnummer();

    /**
     * DOCUMENT ME!
     *
     * @param  hausnummer  DOCUMENT ME!
     */
    void setHausnummer(final String hausnummer);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    TkeyBezirkCustomBean getStadtbezirk();

    /**
     * DOCUMENT ME!
     *
     * @param  stadtbezirk  DOCUMENT ME!
     */
    void setStadtbezirk(final TkeyBezirkCustomBean stadtbezirk);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    TkeyMastartCustomBean getMastart();

    /**
     * DOCUMENT ME!
     *
     * @param  mastart  DOCUMENT ME!
     */
    void setMastart(final TkeyMastartCustomBean mastart);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    TkeyKlassifizierungCustomBean getKlassifizierung();

    /**
     * DOCUMENT ME!
     *
     * @param  klassifizierung  DOCUMENT ME!
     */
    void setKlassifizierung(final TkeyKlassifizierungCustomBean klassifizierung);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    Date getMastanstrich();

    /**
     * DOCUMENT ME!
     *
     * @param  mastanstrich  DOCUMENT ME!
     */
    void setMastanstrich(final Date mastanstrich);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    Date getMastschutz();

    /**
     * DOCUMENT ME!
     *
     * @param  mastschutz  DOCUMENT ME!
     */
    void setMastschutz(final Date mastschutz);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    TkeyUnterhMastCustomBean getUnterhaltspflichtMast();

    /**
     * DOCUMENT ME!
     *
     * @param  unterhaltspflichtMast  DOCUMENT ME!
     */
    void setUnterhaltspflichtMast(final TkeyUnterhMastCustomBean unterhaltspflichtMast);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    TkeyMasttypCustomBean getMasttyp();

    /**
     * DOCUMENT ME!
     *
     * @param  masttyp  DOCUMENT ME!
     */
    void setMasttyp(final TkeyMasttypCustomBean masttyp);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    String getMontagefirma();

    /**
     * DOCUMENT ME!
     *
     * @param  montagefirma  DOCUMENT ME!
     */
    void setMontagefirma(final String montagefirma);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    Date getInbetriebnahmeMast();

    /**
     * DOCUMENT ME!
     *
     * @param  inbetriebnahmeMast  DOCUMENT ME!
     */
    void setInbetriebnahmeMast(final Date inbetriebnahmeMast);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    boolean isVerrechnungseinheit();

    /**
     * DOCUMENT ME!
     *
     * @param  verrechnungseinheit  DOCUMENT ME!
     */
    void setVerrechnungseinheit(final boolean verrechnungseinheit);

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
    Date getLetzteAenderung();

    /**
     * DOCUMENT ME!
     *
     * @param  letzteAenderung  DOCUMENT ME!
     */
    void setLetzteAenderung(final Date letzteAenderung);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    Long getId();

    /**
     * DOCUMENT ME!
     *
     * @param  id  DOCUMENT ME!
     */
    void setId(final Long id);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    StandortKey getStandortKey();
}
