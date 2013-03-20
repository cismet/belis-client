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
package de.cismet.belis.gui.widget;

import org.apache.log4j.Logger;

import java.util.Set;

import de.cismet.belis.broker.BelisBroker;

import de.cismet.commons.architecture.broker.AdvancedPluginBroker;

import de.cismet.commons2.architecture.widget.DefaultWidget;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public class SearchResultWidget extends DefaultWidget {

    //~ Static fields/initializers ---------------------------------------------

    public static final String PROP_SEARCH_RESULTS = "searchResults";

    //~ Instance fields --------------------------------------------------------

    Set searchResults = null;

    private final Logger log = org.apache.log4j.Logger.getLogger(this.getClass());

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new SearchResultWidget object.
     *
     * @param  broker  DOCUMENT ME!
     */
    public SearchResultWidget(final BelisBroker broker) {
        super(broker);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Set getSearchResults() {
        return searchResults;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  searchResult  DOCUMENT ME!
     */
    public void setSearchResults(final Set searchResult) {
        if (log.isDebugEnabled()) {
            log.debug("Search Results set");
        }
        this.searchResults = searchResult;
        firePropertyChange(PROP_SEARCH_RESULTS, null, searchResult);
    }
}
