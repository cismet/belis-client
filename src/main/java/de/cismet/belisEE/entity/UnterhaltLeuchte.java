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
public interface UnterhaltLeuchte extends Serializable {

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
    String getUnterhaltspflichtigeLeuchte();

    /**
     * DOCUMENT ME!
     *
     * @param  unterhaltspflichtigeLeuchte  DOCUMENT ME!
     */
    void setUnterhaltspflichtigeLeuchte(final String unterhaltspflichtigeLeuchte);
}
