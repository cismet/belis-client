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

import java.io.Serializable;

import java.util.Collection;

import de.cismet.belisEE.exception.ActionNotSuccessfulException;
import de.cismet.belisEE.exception.LockAlreadyExistsException;

import de.cismet.cids.custom.beans.belis.SperreCustomBean;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public interface lockEnabled extends Serializable {

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param   objectToLock  DOCUMENT ME!
     * @param   userString    DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     * @throws  LockAlreadyExistsException    DOCUMENT ME!
     */
    SperreCustomBean lockEntity(BaseEntity objectToLock, String userString) throws ActionNotSuccessfulException,
        LockAlreadyExistsException;
    /**
     * DOCUMENT ME!
     *
     * @param   objectsToLock  DOCUMENT ME!
     * @param   userString     DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     * @throws  LockAlreadyExistsException    DOCUMENT ME!
     */
    Collection<SperreCustomBean> lockEntity(Collection<BaseEntity> objectsToLock, String userString)
            throws ActionNotSuccessfulException, LockAlreadyExistsException;
    /**
     * DOCUMENT ME!
     *
     * @param   lockedObject  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    SperreCustomBean isEntityLocked(BaseEntity lockedObject) throws ActionNotSuccessfulException;
    /**
     * DOCUMENT ME!
     *
     * @param   lockedObject  DOCUMENT ME!
     * @param   userString    DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     * @throws  LockAlreadyExistsException    DOCUMENT ME!
     */
    SperreCustomBean tryToLockEntity(BaseEntity lockedObject, String userString) throws ActionNotSuccessfulException,
        LockAlreadyExistsException;
    /**
     * DOCUMENT ME!
     *
     * @param   objectToUnlock  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    void unlockEntity(BaseEntity objectToUnlock) throws ActionNotSuccessfulException;
    /**
     * DOCUMENT ME!
     *
     * @param   objectsToUnlock  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    Collection<Object> unlockEntity(Collection<? extends BaseEntity> objectsToUnlock)
            throws ActionNotSuccessfulException;
    /**
     * DOCUMENT ME!
     *
     * @param   holdedLock  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    void unlockEntity(SperreCustomBean holdedLock) throws ActionNotSuccessfulException;
}
