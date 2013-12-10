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
package de.cismet.belisEE.exception;

import java.util.ArrayList;

import de.cismet.cids.custom.beans.belis2.SperreCustomBean;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public class LockAlreadyExistsException extends Exception {

    //~ Instance fields --------------------------------------------------------

    private final ArrayList<SperreCustomBean> alreadyExisingLocks = new ArrayList<SperreCustomBean>();

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new LockAlreadyExistsException object.
     *
     * @param  message              DOCUMENT ME!
     * @param  alreadyExistingLock  DOCUMENT ME!
     */
    public LockAlreadyExistsException(final String message, final SperreCustomBean alreadyExistingLock) {
        super(message);
        alreadyExisingLocks.add(alreadyExistingLock);
    }

    /**
     * Creates a new LockAlreadyExistsException object.
     *
     * @param  message               DOCUMENT ME!
     * @param  alreadyExistingLocks  DOCUMENT ME!
     */
    public LockAlreadyExistsException(final String message, final ArrayList<SperreCustomBean> alreadyExistingLocks) {
        super(message);
        if (alreadyExistingLocks != null) {
            alreadyExisingLocks.addAll(alreadyExistingLocks);
        }
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public ArrayList<SperreCustomBean> getAlreadyExisingLocks() {
        return alreadyExisingLocks;
    }
}
