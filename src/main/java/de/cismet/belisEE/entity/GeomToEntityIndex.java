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

import de.cismet.cids.custom.beans.belis.GeomCustomBean;

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
    Integer getEntityID();

    /**
     * DOCUMENT ME!
     *
     * @param  enityID  DOCUMENT ME!
     */
    void setEntityID(final Integer enityID);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    Integer getEntityClassId();

    /**
     * DOCUMENT ME!
     *
     * @param  classId  DOCUMENT ME!
     */
    void setEntityClassId(final Integer classId);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    GeomCustomBean getGeometry();

    /**
     * DOCUMENT ME!
     *
     * @param  geometry  DOCUMENT ME!
     */
    void setGeometry(final GeomCustomBean geometry);

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
