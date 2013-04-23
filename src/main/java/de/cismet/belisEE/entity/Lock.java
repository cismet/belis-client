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

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public interface Lock extends Serializable {

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    Integer getClassId();

    /**
     * DOCUMENT ME!
     *
     * @param  classId  DOCUMENT ME!
     */
    void setClassId(final Integer classId);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    Integer getObjectId();

    /**
     * DOCUMENT ME!
     *
     * @param  objectId  DOCUMENT ME!
     */
    void setObjectId(final Integer objectId);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    String getUserString();

    /**
     * DOCUMENT ME!
     *
     * @param  userString  DOCUMENT ME!
     */
    void setUserString(final String userString);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    String getAdditionalInfo();

    /**
     * DOCUMENT ME!
     *
     * @param  additionalInfo  DOCUMENT ME!
     */
    void setAdditionalInfo(final String additionalInfo);

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

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    Date getTimestamp();

    /**
     * DOCUMENT ME!
     *
     * @param  timestamp  DOCUMENT ME!
     */
    void setTimestamp(final Date timestamp);
}
