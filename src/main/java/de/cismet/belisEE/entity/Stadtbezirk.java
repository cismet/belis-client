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
 * @version  $Revision$, $Date$
 */
public interface Stadtbezirk extends Serializable {

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    Integer getPk();

    /**
     * DOCUMENT ME!
     *
     * @param  pk  DOCUMENT ME!
     */
    void setPk(final Integer pk);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    String getBezirk();

    /**
     * DOCUMENT ME!
     *
     * @param  bezirk  DOCUMENT ME!
     */
    void setBezirk(final String bezirk);
}
