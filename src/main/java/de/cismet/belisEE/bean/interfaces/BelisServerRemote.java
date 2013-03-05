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

import java.util.Set;
import java.util.TreeSet;

import de.cismet.belisEE.exception.ActionNotSuccessfulException;

import de.cismet.belisEE.util.StandortKey;

import de.cismet.cids.custom.beans.belis.BauartCustomBean;
import de.cismet.cids.custom.beans.belis.GeomCustomBean;
import de.cismet.cids.custom.beans.belis.LeitungstypCustomBean;
import de.cismet.cids.custom.beans.belis.MaterialLeitungCustomBean;
import de.cismet.cids.custom.beans.belis.MaterialMauerlascheCustomBean;
import de.cismet.cids.custom.beans.belis.QuerschnittCustomBean;
import de.cismet.cids.custom.beans.belis.TdtaStandortMastCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyBezirkCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyDoppelkommandoCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyEnergielieferantCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyKennzifferCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyKlassifizierungCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyLeuchtentypCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyMastartCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyMasttypCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyStrassenschluesselCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyUnterhLeuchteCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyUnterhMastCustomBean;

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

    /**
     * GetAll.
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    Set<TkeyStrassenschluesselCustomBean> getAllStrassenschluessel() throws ActionNotSuccessfulException;
    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    Set<TkeyEnergielieferantCustomBean> getAllEnergielieferanten() throws ActionNotSuccessfulException;
    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    Set<TkeyKennzifferCustomBean> getAllKennziffer() throws ActionNotSuccessfulException;
    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    Set<TkeyBezirkCustomBean> getAllStadtbezirke() throws ActionNotSuccessfulException;
    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    Set<TkeyMastartCustomBean> getAllMastarten() throws ActionNotSuccessfulException;
    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    Set<TkeyMasttypCustomBean> getAllMasttypen() throws ActionNotSuccessfulException;
    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    Set<TkeyKlassifizierungCustomBean> getAllKlassifizierungen() throws ActionNotSuccessfulException;
    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    Set<TkeyUnterhMastCustomBean> getAllUnterhaltMast() throws ActionNotSuccessfulException;
    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    Set<TkeyUnterhLeuchteCustomBean> getAllUnterhaltLeuchte() throws ActionNotSuccessfulException;
    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    Set<MaterialLeitungCustomBean> getAllMaterialLeitung() throws ActionNotSuccessfulException;
    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    Set<LeitungstypCustomBean> getAllLeitungstypen() throws ActionNotSuccessfulException;
    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    Set<QuerschnittCustomBean> getAllQuerschnitte() throws ActionNotSuccessfulException;
    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    Set<MaterialMauerlascheCustomBean> getAllMaterialMauerlasche() throws ActionNotSuccessfulException;
    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    Set<BauartCustomBean> getAllBauarten() throws ActionNotSuccessfulException;
    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    Set<TkeyLeuchtentypCustomBean> getAllLeuchtentypen() throws ActionNotSuccessfulException;
    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    Set<TkeyDoppelkommandoCustomBean> getAllDoppelkommando() throws ActionNotSuccessfulException;

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
    TreeSet<BaseEntity> getObjectsByKey(String strassenschluessel, Short kennziffer, Short laufendeNummer)
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
    Set<TdtaStandortMastCustomBean> retrieveStandort(StandortKey key) throws ActionNotSuccessfulException;
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
    Set<BaseEntity> saveObjects(Set<BaseEntity> objectsToSave, String userString) throws ActionNotSuccessfulException;
    /**
     * DOCUMENT ME!
     *
     * @param   objectsToSave  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    Set<BaseEntity> refreshObjects(Set<BaseEntity> objectsToSave) throws ActionNotSuccessfulException;
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
    void deleteEntities(Set<BaseEntity> objectsToDelete, String userString) throws ActionNotSuccessfulException;
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
    TdtaStandortMastCustomBean determineNextLaufendenummer(TdtaStandortMastCustomBean standort, Short minimalNumber)
            throws ActionNotSuccessfulException;
}
