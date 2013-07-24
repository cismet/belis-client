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
package de.cismet.belisEE.bean.interfaces;

import java.util.Collection;
import java.util.TreeSet;

import de.cismet.belisEE.exception.ActionNotSuccessfulException;

import de.cismet.belisEE.util.StandortKey;

import de.cismet.cids.custom.beans.belis.GeomCustomBean;
import de.cismet.cids.custom.beans.belis.TdtaStandortMastCustomBean;

import de.cismet.cismap.commons.BoundingBox;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public interface BelisServerRemote extends lockEnabled {

    //~ Methods ----------------------------------------------------------------

    public Collection getAll(String className) throws ActionNotSuccessfulException;
    
    /**
     * DOCUMENT ME!
     *
     * @param   strassenschluessel  DOCUMENT ME!
     * @param   kennziffer          DOCUMENT ME!
     * @param   laufendeNummer      DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    TreeSet<BaseEntity> getObjectsByKey(String strassenschluessel, Integer kennziffer, Integer laufendeNummer)
            throws ActionNotSuccessfulException;
    /**
     * DOCUMENT ME!
     *
     * @param   key  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    Collection<TdtaStandortMastCustomBean> retrieveStandort(StandortKey key) throws ActionNotSuccessfulException;
    /**
     * DOCUMENT ME!
     *
     * @param   objectsToSave  DOCUMENT ME!
     * @param   userString     DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    Collection<BaseEntity> saveObjects(Collection<BaseEntity> objectsToSave, String userString)
            throws ActionNotSuccessfulException;
    /**
     * DOCUMENT ME!
     *
     * @param   objectsToSave  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    Collection<BaseEntity> refreshObjects(Collection<BaseEntity> objectsToSave) throws ActionNotSuccessfulException;
    /**
     * DOCUMENT ME!
     *
     * @param   objectsToDelete  DOCUMENT ME!
     * @param   userString       DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    void deleteEntity(BaseEntity objectsToDelete, String userString) throws ActionNotSuccessfulException;
    /**
     * DOCUMENT ME!
     *
     * @param   objectsToDelete  DOCUMENT ME!
     * @param   userString       DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    void deleteEntities(Collection<BaseEntity> objectsToDelete, String userString) throws ActionNotSuccessfulException;
    /**
     * by Geometry.
     *
     * @param   bb  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    TreeSet<BaseEntity> getObjectsByBoundingBox(BoundingBox bb) throws ActionNotSuccessfulException;
    /**
     * DOCUMENT ME!
     *
     * @param   geom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    Object getObjectsByGeom(GeomCustomBean geom) throws ActionNotSuccessfulException;
    /**
     * DOCUMENT ME!
     *
     * @param   standort  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    boolean checkIfStandortExists(TdtaStandortMastCustomBean standort) throws ActionNotSuccessfulException;
    /**
     * DOCUMENT ME!
     *
     * @param   standort       DOCUMENT ME!
     * @param   minimalNumber  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    TdtaStandortMastCustomBean determineNextLaufendenummer(TdtaStandortMastCustomBean standort, Integer minimalNumber)
            throws ActionNotSuccessfulException;
}
