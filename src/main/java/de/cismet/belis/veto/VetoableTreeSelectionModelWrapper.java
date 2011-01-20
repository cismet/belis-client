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

import java.beans.PropertyChangeListener;

import java.util.ArrayList;

import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.RowMapper;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public class VetoableTreeSelectionModelWrapper implements TreeSelectionModel {

    //~ Instance fields --------------------------------------------------------

    private final ArrayList<VetoableTreeSelectionListener> m_vetoableTreeSelectionListeners =
        new ArrayList<VetoableTreeSelectionListener>();
    private TreeSelectionModel m_treeSelectionModel = null;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new VetoableTreeSelectionModelWrapper object.
     *
     * @param  treeSelectionModel  DOCUMENT ME!
     */
    public VetoableTreeSelectionModelWrapper(final TreeSelectionModel treeSelectionModel) {
        if (treeSelectionModel != null) {
            m_treeSelectionModel = treeSelectionModel;
        } else {
            m_treeSelectionModel = new DefaultTreeSelectionModel();
        }
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTreeSelectionListener(final TreeSelectionListener listener) {
        m_treeSelectionModel.addTreeSelectionListener(listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeTreeSelectionListener(final TreeSelectionListener listener) {
        m_treeSelectionModel.removeTreeSelectionListener(listener);
    }

    /**
     * Add a vetoable tree selection listener.
     *
     * @param  listener  the listener
     */
    public void addVetoableTreeSelectionListener(final VetoableTreeSelectionListener listener) {
        m_vetoableTreeSelectionListeners.add(listener);
    }

    /**
     * Remove a vetoable tree selection listener.
     *
     * @param  listener  the listener
     */
    public void removeVetoableTreeSelectionListener(final VetoableTreeSelectionListener listener) {
        m_vetoableTreeSelectionListeners.remove(listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        m_treeSelectionModel.addPropertyChangeListener(listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removePropertyChangeListener(final PropertyChangeListener listener) {
        m_treeSelectionModel.removePropertyChangeListener(listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addSelectionPath(final TreePath path) {
        try {
            for (final VetoableTreeSelectionListener curListener : m_vetoableTreeSelectionListeners) {
                curListener.aboutToAddSelectionPath(path);
            }

            m_treeSelectionModel.addSelectionPath(path);
        } catch (final EventVetoedException e) {
            return;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addSelectionPaths(final TreePath[] paths) {
        try {
            for (final VetoableTreeSelectionListener curListener : m_vetoableTreeSelectionListeners) {
                curListener.aboutToAddSelectionPaths(paths);
            }

            m_treeSelectionModel.addSelectionPaths(paths);
        } catch (final EventVetoedException e) {
            return;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearSelection() {
        try {
            for (final VetoableTreeSelectionListener curListener : m_vetoableTreeSelectionListeners) {
                curListener.aboutToClearSelection();
            }

            m_treeSelectionModel.clearSelection();
        } catch (final EventVetoedException e) {
            return;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TreePath getLeadSelectionPath() {
        return m_treeSelectionModel.getLeadSelectionPath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLeadSelectionRow() {
        return m_treeSelectionModel.getLeadSelectionRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMaxSelectionRow() {
        return m_treeSelectionModel.getMaxSelectionRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMinSelectionRow() {
        return m_treeSelectionModel.getMinSelectionRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RowMapper getRowMapper() {
        return m_treeSelectionModel.getRowMapper();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSelectionCount() {
        return m_treeSelectionModel.getSelectionCount();
    }

    @Override
    public int getSelectionMode() {
        return m_treeSelectionModel.getSelectionMode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TreePath getSelectionPath() {
        return m_treeSelectionModel.getSelectionPath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TreePath[] getSelectionPaths() {
        return m_treeSelectionModel.getSelectionPaths();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int[] getSelectionRows() {
        return m_treeSelectionModel.getSelectionRows();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPathSelected(final TreePath path) {
        return m_treeSelectionModel.isPathSelected(path);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRowSelected(final int row) {
        return m_treeSelectionModel.isRowSelected(row);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSelectionEmpty() {
        return m_treeSelectionModel.isSelectionEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeSelectionPath(final TreePath path) {
        try {
            for (final VetoableTreeSelectionListener curListener : m_vetoableTreeSelectionListeners) {
                curListener.aboutRemoveSelectionPath(path);
            }

            m_treeSelectionModel.removeSelectionPath(path);
        } catch (final EventVetoedException e) {
            return;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeSelectionPaths(final TreePath[] paths) {
        try {
            for (final VetoableTreeSelectionListener curListener : m_vetoableTreeSelectionListeners) {
                curListener.aboutRemoveSelectionPaths(paths);
            }

            m_treeSelectionModel.removeSelectionPaths(paths);
        } catch (final EventVetoedException e) {
            return;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetRowSelection() {
        try {
            for (final VetoableTreeSelectionListener curListener : m_vetoableTreeSelectionListeners) {
                curListener.aboutToResetRowSelection();
            }

            m_treeSelectionModel.resetRowSelection();
        } catch (final EventVetoedException e) {
            return;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRowMapper(final RowMapper newMapper) {
        m_treeSelectionModel.setRowMapper(newMapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSelectionMode(final int mode) {
        try {
            for (final VetoableTreeSelectionListener curListener : m_vetoableTreeSelectionListeners) {
                curListener.aboutToSetSelectionMode(mode);
            }

            m_treeSelectionModel.setSelectionMode(mode);
        } catch (final EventVetoedException e) {
            return;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSelectionPath(final TreePath path) {
        try {
            for (final VetoableTreeSelectionListener curListener : m_vetoableTreeSelectionListeners) {
                curListener.aboutToSetSelectionPath(path);
            }

            m_treeSelectionModel.setSelectionPath(path);
        } catch (final EventVetoedException e) {
            return;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSelectionPaths(final TreePath[] paths) {
        try {
            for (final VetoableTreeSelectionListener curListener : m_vetoableTreeSelectionListeners) {
                curListener.aboutToSetSelectionPaths(paths);
            }

            m_treeSelectionModel.setSelectionPaths(paths);
        } catch (final EventVetoedException e) {
            return;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return m_treeSelectionModel.toString();
    }
}
