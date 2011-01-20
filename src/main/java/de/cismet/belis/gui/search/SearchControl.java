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
package de.cismet.belis.gui.search;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public interface SearchControl {

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     */
    void searchStarted();
    /**
     * DOCUMENT ME!
     */
    void searchFinished();
    /**
     * DOCUMENT ME!
     *
     * @param  isSearchEnabled  DOCUMENT ME!
     */
    void setSearchEnabled(boolean isSearchEnabled);
}
