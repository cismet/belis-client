/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cismet.belis.gui.search;

/**
 *
 * @author spuhl
 */
public interface SearchController {
    
    void addSearchControl(SearchControl searchControl);
    void removeSearchControl(SearchControl searchControl);
    void fireSearchStarted();
    void fireSearchFinished();
    void enableSearch();
    void disableSearch();
    void setSearchEnabled(boolean isSearchEnabled);
}
