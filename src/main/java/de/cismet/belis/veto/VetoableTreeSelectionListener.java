/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cismet.belis.veto;

import javax.swing.tree.TreePath;

/**
 *
 * @author spuhl
 */
public interface VetoableTreeSelectionListener
{
   /**
    * About to add a path to the selection
    *
    * @param path the path to add
    *
    * @throws EventVetoedException
    */
   void aboutToAddSelectionPath(TreePath path) throws EventVetoedException;

   /**
    * About to add paths to the selection
    *
    * @param paths the paths to add
    *
    * @throws EventVetoedException
    */
   void aboutToAddSelectionPaths(TreePath[] paths) throws EventVetoedException;

   /**
    * About to clear selection
    *
    * @throws EventVetoedException
    */
   void aboutToClearSelection() throws EventVetoedException;

   /**
    * About to remove a selection path
    *
    * @param path the path
    *
    * @throws EventVetoedException
    */
   void aboutRemoveSelectionPath(TreePath path) throws EventVetoedException;

   /**
    * About to remove multiple selection paths
    *
    * @param paths the paths
    *
    * @throws EventVetoedException
    */
   void aboutRemoveSelectionPaths(TreePath[] paths) throws EventVetoedException;

   /**
    * About to reset the row selection
    *
    * @throws EventVetoedException
    */
   void aboutToResetRowSelection() throws EventVetoedException;

   /**
    * About to set the selection mode
    *
    * @param mode the selection mode
    *
    * @throws EventVetoedException
    */
   void aboutToSetSelectionMode(int mode) throws EventVetoedException;

   /**
    * About to set the selection path
    *
    * @param path the path
    *
    * @throws EventVetoedException
    */
   void aboutToSetSelectionPath(TreePath path) throws EventVetoedException;

   /**
    * About to set the selection paths
    *
    * @param paths the paths
    *
    * @throws EventVetoedException
    */
   void aboutToSetSelectionPaths(TreePath[] paths) throws EventVetoedException;
}

