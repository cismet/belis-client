/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cismet.belis.gui.widget;

import de.cismet.commons.architecture.broker.AdvancedPluginBroker;
import de.cismet.commons.architecture.widget.DefaultWidget;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 *
 * @author spuhl
 */
public class SearchResultWidget extends DefaultWidget{

    
    private final Logger log = org.apache.log4j.Logger.getLogger(this.getClass());
    Set searchResults = null;
    public static final String PROP_SEARCH_RESULTS = "searchResults";
    
    public SearchResultWidget(AdvancedPluginBroker broker) {
        super(broker);
    }

    public Set getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(Set searchResult) {
        log.debug("Search Results set");
        this.searchResults = searchResult;
        firePropertyChange(PROP_SEARCH_RESULTS, null, searchResult);
    }        
    
}
