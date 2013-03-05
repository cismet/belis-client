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

import java.util.Set;

import de.cismet.belisEE.entity.Lock;

import de.cismet.belisEE.exception.ActionNotSuccessfulException;
import de.cismet.belisEE.exception.LockAlreadyExistsException;

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
    Lock lockEntity(Object objectToLock, String userString) throws ActionNotSuccessfulException,
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
    Set<Lock> lockEntity(Set<Object> objectsToLock, String userString) throws ActionNotSuccessfulException,
        LockAlreadyExistsException;
    /**
     * DOCUMENT ME!
     *
     * @param   lockedObject  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    Lock isEntityLocked(Object lockedObject) throws ActionNotSuccessfulException;
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
    Lock tryToLockEntity(Object lockedObject, String userString) throws ActionNotSuccessfulException,
        LockAlreadyExistsException;
    /**
     * DOCUMENT ME!
     *
     * @param   objectToUnlock  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    void unlockEntity(Object objectToUnlock) throws ActionNotSuccessfulException;
    /**
     * DOCUMENT ME!
     *
     * @param   objectsToUnlock  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    Set<Object> unlockEntity(Set<Object> objectsToUnlock) throws ActionNotSuccessfulException;
    /**
     * DOCUMENT ME!
     *
     * @param   holdedLock  DOCUMENT ME!
     *
     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
     */
    void unlockEntity(Lock holdedLock) throws ActionNotSuccessfulException;
}
