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

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public class ActionNotSuccessfulException extends Exception {

    //~ Instance fields --------------------------------------------------------

    private Object exceptionObject;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ActionNotSuccessfulException object.
     *
     * @param  message  DOCUMENT ME!
     */
    public ActionNotSuccessfulException(final String message) {
        super(message);
    }

    /**
     * Creates a new ActionNotSuccessfulException object.
     *
     * @param  message  DOCUMENT ME!
     * @param  cause    DOCUMENT ME!
     */
    public ActionNotSuccessfulException(final String message, final Throwable cause) {
        super(message, cause);
    }
    /**
     * Creates a new ActionNotSuccessfulException object.
     *
     * @param  message          DOCUMENT ME!
     * @param  exceptionObject  DOCUMENT ME!
     */
    public ActionNotSuccessfulException(final String message, final Object exceptionObject) {
        super(message);
        this.exceptionObject = exceptionObject;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Object getExceptionObject() {
        return exceptionObject;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  exceptionObject  DOCUMENT ME!
     */
    public void setExceptionObject(final Object exceptionObject) {
        this.exceptionObject = exceptionObject;
    }
}
