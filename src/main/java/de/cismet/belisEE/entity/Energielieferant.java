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
public interface Energielieferant extends Serializable {

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    Short getPk();

    /**
     * DOCUMENT ME!
     *
     * @param  pk  DOCUMENT ME!
     */
    void setPk(final Short pk);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    String getEnergielieferant();

    /**
     * DOCUMENT ME!
     *
     * @param  energielieferant  DOCUMENT ME!
     */
    void setEnergielieferant(final String energielieferant);
}
