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
package de.cismet.belis.veto;

import javax.swing.tree.TreePath;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public interface VetoableTreeSelectionListener {

    //~ Methods ----------------------------------------------------------------

    /**
     * About to add a path to the selection.
     *
     * @param   path  the path to add
     *
     * @throws  EventVetoedException  DOCUMENT ME!
     */
    void aboutToAddSelectionPath(TreePath path) throws EventVetoedException;

    /**
     * About to add paths to the selection.
     *
     * @param   paths  the paths to add
     *
     * @throws  EventVetoedException  DOCUMENT ME!
     */
    void aboutToAddSelectionPaths(TreePath[] paths) throws EventVetoedException;

    /**
     * About to clear selection.
     *
     * @throws  EventVetoedException  DOCUMENT ME!
     */
    void aboutToClearSelection() throws EventVetoedException;

    /**
     * About to remove a selection path.
     *
     * @param   path  the path
     *
     * @throws  EventVetoedException  DOCUMENT ME!
     */
    void aboutRemoveSelectionPath(TreePath path) throws EventVetoedException;

    /**
     * About to remove multiple selection paths.
     *
     * @param   paths  the paths
     *
     * @throws  EventVetoedException  DOCUMENT ME!
     */
    void aboutRemoveSelectionPaths(TreePath[] paths) throws EventVetoedException;

    /**
     * About to reset the row selection.
     *
     * @throws  EventVetoedException  DOCUMENT ME!
     */
    void aboutToResetRowSelection() throws EventVetoedException;

    /**
     * About to set the selection mode.
     *
     * @param   mode  the selection mode
     *
     * @throws  EventVetoedException  DOCUMENT ME!
     */
    void aboutToSetSelectionMode(int mode) throws EventVetoedException;

    /**
     * About to set the selection path.
     *
     * @param   path  the path
     *
     * @throws  EventVetoedException  DOCUMENT ME!
     */
    void aboutToSetSelectionPath(TreePath path) throws EventVetoedException;

    /**
     * About to set the selection paths.
     *
     * @param   paths  the paths
     *
     * @throws  EventVetoedException  DOCUMENT ME!
     */
    void aboutToSetSelectionPaths(TreePath[] paths) throws EventVetoedException;
}
