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
public interface SearchController {

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  searchControl  DOCUMENT ME!
     */
    void addSearchControl(SearchControl searchControl);
    /**
     * DOCUMENT ME!
     *
     * @param  searchControl  DOCUMENT ME!
     */
    void removeSearchControl(SearchControl searchControl);
    /**
     * DOCUMENT ME!
     */
    void fireSearchStarted();
    /**
     * DOCUMENT ME!
     */
    void fireSearchFinished();
    /**
     * DOCUMENT ME!
     */
    void enableSearch();
    /**
     * DOCUMENT ME!
     */
    void disableSearch();
    /**
     * DOCUMENT ME!
     *
     * @param  isSearchEnabled  DOCUMENT ME!
     */
    void setSearchEnabled(boolean isSearchEnabled);
}
