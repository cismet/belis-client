/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cismet.belis.veto;

/**
 *
 * @author spuhl
 */
public interface VetoableListSelectionListener {
        void fireListSelectionVeto() throws EventVetoedException;
}
