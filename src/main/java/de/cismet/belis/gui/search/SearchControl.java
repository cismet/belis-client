/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cismet.belis.gui.search;

/**
 *
 * @author spuhl
 */
public interface SearchControl {
    
    void searchStarted();
    void searchFinished();
    void setSearchEnabled(boolean isSearchEnabled);
}
