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
import java.util.Collection;
import java.util.HashSet;

import de.cismet.cids.custom.beans.belis2.SperreCustomBean;

import de.cismet.tools.collections.HashArrayList;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public class LockAlreadyExistsException extends Exception {

    //~ Instance fields --------------------------------------------------------

    private final Collection<SperreCustomBean> alreadyExisingLocks = new ArrayList<SperreCustomBean>();

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new LockAlreadyExistsException object.
     *
     * @param  message               DOCUMENT ME!
     * @param  alreadyExistingLocks  DOCUMENT ME!
     */
    public LockAlreadyExistsException(final String message,
            final Collection<SperreCustomBean> alreadyExistingLocks) {
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
    public Collection<SperreCustomBean> getAlreadyExisingLocks() {
        return alreadyExisingLocks;
    }
}
