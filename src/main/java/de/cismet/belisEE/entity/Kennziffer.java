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
public interface Kennziffer extends Serializable {

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    Integer getKennziffer();

    /**
     * DOCUMENT ME!
     *
     * @param  kennziffer  DOCUMENT ME!
     */
    void setKennziffer(final Integer kennziffer);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    String getBeschreibung();

    /**
     * DOCUMENT ME!
     *
     * @param  beschreibung  DOCUMENT ME!
     */
    void setBeschreibung(final String beschreibung);
}
