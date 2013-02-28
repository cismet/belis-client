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

import java.io.Serializable;

import de.cismet.commons.server.interfaces.Geom;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public interface GeomToEntityIndex extends Serializable {

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    Long getEntityID();

    /**
     * DOCUMENT ME!
     *
     * @param  enityID  DOCUMENT ME!
     */
    void setEntityID(final Long enityID);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    Class getEntityClass();

    /**
     * DOCUMENT ME!
     *
     * @param  entityName  DOCUMENT ME!
     */
    void setEntityClass(final Class entityName);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    Geom getGeometry();

    /**
     * DOCUMENT ME!
     *
     * @param  geometry  DOCUMENT ME!
     */
    void setGeometry(final Geom geometry);

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
}
