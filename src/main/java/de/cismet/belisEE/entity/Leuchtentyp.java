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

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public interface Leuchtentyp extends Serializable {

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    String getLeuchtentyp();

    /**
     * DOCUMENT ME!
     *
     * @param  leuchtentyp  DOCUMENT ME!
     */
    void setLeuchtentyp(final String leuchtentyp);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    String getFabrikat();

    /**
     * DOCUMENT ME!
     *
     * @param  fabrikat  DOCUMENT ME!
     */
    void setFabrikat(final String fabrikat);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    Double getBestueckung();

    /**
     * DOCUMENT ME!
     *
     * @param  bestueckung  DOCUMENT ME!
     */
    void setBestueckung(final Double bestueckung);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    Double getLeistung();

    /**
     * DOCUMENT ME!
     *
     * @param  leistung  DOCUMENT ME!
     */
    void setLeistung(final Double leistung);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    Double getLeistungBrutto();

    /**
     * DOCUMENT ME!
     *
     * @param  leistungBrutto  DOCUMENT ME!
     */
    void setLeistungBrutto(final Double leistungBrutto);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    String getLampe();

    /**
     * DOCUMENT ME!
     *
     * @param  lampe  DOCUMENT ME!
     */
    void setLampe(final String lampe);
}
