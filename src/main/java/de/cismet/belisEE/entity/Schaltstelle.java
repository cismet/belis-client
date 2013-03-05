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

import java.io.Serializable;

import java.util.Date;

import de.cismet.cids.custom.beans.belis.BauartCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyStrassenschluesselCustomBean;

import de.cismet.commons.server.interfaces.DocumentContainer;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public interface Schaltstelle extends Serializable, DocumentContainer {

    //~ Methods ----------------------------------------------------------------

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
    Short getLaufendeNummer();

    /**
     * DOCUMENT ME!
     *
     * @param  laufendeNummer  DOCUMENT ME!
     */
    void setLaufendeNummer(Short laufendeNummer);

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
    String getSchaltstellenNummer();

    /**
     * DOCUMENT ME!
     *
     * @param  schaltstellenNummer  DOCUMENT ME!
     */
    void setSchaltstellenNummer(final String schaltstellenNummer);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    Date getErstellungsjahr();

    /**
     * DOCUMENT ME!
     *
     * @param  erstellungsjahr  DOCUMENT ME!
     */
    void setErstellungsjahr(final Date erstellungsjahr);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    BauartCustomBean getBauart();

    /**
     * DOCUMENT ME!
     *
     * @param  bauart  DOCUMENT ME!
     */
    void setBauart(final BauartCustomBean bauart);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    String getBemerkung();

    /**
     * DOCUMENT ME!
     *
     * @param  bemerkung  DOCUMENT ME!
     */
    void setBemerkung(final String bemerkung);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    String getZusaetzlicheStandortbezeichnung();

    /**
     * DOCUMENT ME!
     *
     * @param  zusaetzlicheStandortbeschreibung  DOCUMENT ME!
     */
    void setZusaetzlicheStandortbezeichnung(final String zusaetzlicheStandortbeschreibung);
}
