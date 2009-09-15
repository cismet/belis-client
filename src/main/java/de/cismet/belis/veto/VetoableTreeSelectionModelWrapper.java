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
 *
 * @author spuhl
 */
public class VetoableTreeSelectionModelWrapper implements TreeSelectionModel {

    private final ArrayList<VetoableTreeSelectionListener> m_vetoableTreeSelectionListeners = new ArrayList<VetoableTreeSelectionListener>();
    private TreeSelectionModel m_treeSelectionModel = null;

    public VetoableTreeSelectionModelWrapper(TreeSelectionModel treeSelectionModel) {
        if (treeSelectionModel != null) {
            m_treeSelectionModel = treeSelectionModel;
        } else {
            m_treeSelectionModel = new DefaultTreeSelectionModel();
        }

    }

    /**
     * {@inheritDoc}
     */
    public void addTreeSelectionListener(final TreeSelectionListener listener) {
        m_treeSelectionModel.addTreeSelectionListener(listener);
    }

    /**
     * {@inheritDoc}
     */
    public void removeTreeSelectionListener(final TreeSelectionListener listener) {
        m_treeSelectionModel.removeTreeSelectionListener(listener);
    }

    /**
     * Add a vetoable tree selection listener
     *
     * @param listener the listener
     */
    public void addVetoableTreeSelectionListener(final VetoableTreeSelectionListener listener) {
        m_vetoableTreeSelectionListeners.add(listener);
    }

    /**
     * Remove a vetoable tree selection listener
     *
     * @param listener the listener
     */
    public void removeVetoableTreeSelectionListener(final VetoableTreeSelectionListener listener) {
        m_vetoableTreeSelectionListeners.remove(listener);
    }

    /**
     * {@inheritDoc}
     */
    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        m_treeSelectionModel.addPropertyChangeListener(listener);
    }

    /**
     * {@inheritDoc}
     */
    public void removePropertyChangeListener(final PropertyChangeListener listener) {
        m_treeSelectionModel.removePropertyChangeListener(listener);
    }

    /**
     * {@inheritDoc}
     */
    public void addSelectionPath(final TreePath path) {
        try {
            for (VetoableTreeSelectionListener curListener : m_vetoableTreeSelectionListeners) {
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
    public void addSelectionPaths(final TreePath[] paths) {
        try {
            for (VetoableTreeSelectionListener curListener : m_vetoableTreeSelectionListeners) {
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
    public void clearSelection() {
        try {
            for (VetoableTreeSelectionListener curListener : m_vetoableTreeSelectionListeners) {
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
    public TreePath getLeadSelectionPath() {
        return m_treeSelectionModel.getLeadSelectionPath();
    }

    /**
     * {@inheritDoc}
     */
    public int getLeadSelectionRow() {
        return m_treeSelectionModel.getLeadSelectionRow();
    }

    /**
     * {@inheritDoc}
     */
    public int getMaxSelectionRow() {
        return m_treeSelectionModel.getMaxSelectionRow();
    }

    /**
     * {@inheritDoc}
     */
    public int getMinSelectionRow() {
        return m_treeSelectionModel.getMinSelectionRow();
    }

    /**
     * {@inheritDoc}
     */
    public RowMapper getRowMapper() {
        return m_treeSelectionModel.getRowMapper();
    }

    /**
     * {@inheritDoc}
     */
    public int getSelectionCount() {
        return m_treeSelectionModel.getSelectionCount();
    }

    public int getSelectionMode() {
        return m_treeSelectionModel.getSelectionMode();
    }

    /**
     * {@inheritDoc}
     */
    public TreePath getSelectionPath() {
        return m_treeSelectionModel.getSelectionPath();
    }

    /**
     * {@inheritDoc}
     */
    public TreePath[] getSelectionPaths() {
        return m_treeSelectionModel.getSelectionPaths();
    }

    /**
     * {@inheritDoc}
     */
    public int[] getSelectionRows() {
        return m_treeSelectionModel.getSelectionRows();
    }

    /**
     * {@inheritDoc}
     */
    public boolean isPathSelected(final TreePath path) {
        return m_treeSelectionModel.isPathSelected(path);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isRowSelected(final int row) {
        return m_treeSelectionModel.isRowSelected(row);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isSelectionEmpty() {
        return m_treeSelectionModel.isSelectionEmpty();
    }

    /**
     * {@inheritDoc}
     */
    public void removeSelectionPath(final TreePath path) {
        try {
            for (VetoableTreeSelectionListener curListener : m_vetoableTreeSelectionListeners) {
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
    public void removeSelectionPaths(final TreePath[] paths) {
        try {
            for (VetoableTreeSelectionListener curListener : m_vetoableTreeSelectionListeners) {
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
    public void resetRowSelection() {
        try {
            for (VetoableTreeSelectionListener curListener : m_vetoableTreeSelectionListeners) {
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
    public void setRowMapper(final RowMapper newMapper) {
        m_treeSelectionModel.setRowMapper(newMapper);
    }

    /**
     * {@inheritDoc}
     */
    public void setSelectionMode(final int mode) {
        try {
            for (VetoableTreeSelectionListener curListener : m_vetoableTreeSelectionListeners) {
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
    public void setSelectionPath(final TreePath path) {
        try {
            for (VetoableTreeSelectionListener curListener : m_vetoableTreeSelectionListeners) {
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
    public void setSelectionPaths(final TreePath[] paths) {
        try {
            for (VetoableTreeSelectionListener curListener : m_vetoableTreeSelectionListeners) {
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
