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

import java.util.ArrayList;

import javax.swing.DefaultListSelectionModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public class VetoableListSelectionWrapper implements ListSelectionModel {

    //~ Instance fields --------------------------------------------------------

    private final ArrayList<VetoableListSelectionListener> vetoableListSelectionListener =
        new ArrayList<VetoableListSelectionListener>();
    private ListSelectionModel listSelectionModel = null;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new VetoableListSelectionWrapper object.
     *
     * @param  listSelectionModel  DOCUMENT ME!
     */
    public VetoableListSelectionWrapper(final ListSelectionModel listSelectionModel) {
        if (listSelectionModel != null) {
            this.listSelectionModel = listSelectionModel;
        } else {
            this.listSelectionModel = new DefaultListSelectionModel();
        }
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  listener  DOCUMENT ME!
     */
    public void addVetoableTreeSelectionListener(final VetoableListSelectionListener listener) {
        vetoableListSelectionListener.add(listener);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  listener  DOCUMENT ME!
     */
    public void removeVetoableTreeSelectionListener(final VetoableListSelectionListener listener) {
        vetoableListSelectionListener.remove(listener);
    }

    @Override
    public void addListSelectionListener(final ListSelectionListener x) {
        listSelectionModel.addListSelectionListener(x);
    }

    @Override
    public void addSelectionInterval(final int index0, final int index1) {
        try {
            for (final VetoableListSelectionListener curListener : vetoableListSelectionListener) {
                curListener.fireListSelectionVeto();
            }

            listSelectionModel.addSelectionInterval(index0, index1);
        } catch (final EventVetoedException e) {
            return;
        }
    }

    @Override
    public void clearSelection() {
        try {
            for (final VetoableListSelectionListener curListener : vetoableListSelectionListener) {
                curListener.fireListSelectionVeto();
            }

            listSelectionModel.clearSelection();
        } catch (final EventVetoedException e) {
            return;
        }
    }

    @Override
    public int getAnchorSelectionIndex() {
        return listSelectionModel.getAnchorSelectionIndex();
    }

    @Override
    public int getLeadSelectionIndex() {
        return listSelectionModel.getLeadSelectionIndex();
    }

    @Override
    public int getMaxSelectionIndex() {
        return listSelectionModel.getMaxSelectionIndex();
    }

    @Override
    public int getMinSelectionIndex() {
        return listSelectionModel.getMinSelectionIndex();
    }

    @Override
    public int getSelectionMode() {
        return listSelectionModel.getSelectionMode();
    }

    @Override
    public boolean getValueIsAdjusting() {
        return listSelectionModel.getValueIsAdjusting();
    }

    @Override
    public void insertIndexInterval(final int index, final int length, final boolean before) {
        try {
            for (final VetoableListSelectionListener curListener : vetoableListSelectionListener) {
                curListener.fireListSelectionVeto();
            }

            listSelectionModel.insertIndexInterval(index, length, before);
        } catch (final EventVetoedException e) {
            return;
        }
    }

    @Override
    public boolean isSelectedIndex(final int index) {
        return listSelectionModel.isSelectedIndex(index);
    }

    @Override
    public boolean isSelectionEmpty() {
        return listSelectionModel.isSelectionEmpty();
    }

    @Override
    public void removeIndexInterval(final int index0, final int index1) {
        try {
            for (final VetoableListSelectionListener curListener : vetoableListSelectionListener) {
                curListener.fireListSelectionVeto();
            }
            listSelectionModel.removeIndexInterval(index0, index1);
        } catch (final EventVetoedException e) {
            return;
        }
    }

    @Override
    public void removeListSelectionListener(final ListSelectionListener x) {
        listSelectionModel.removeListSelectionListener(x);
        ;
    }

    @Override
    public void removeSelectionInterval(final int index0, final int index1) {
        try {
            for (final VetoableListSelectionListener curListener : vetoableListSelectionListener) {
                curListener.fireListSelectionVeto();
            }
            listSelectionModel.removeSelectionInterval(index0, index1);
        } catch (final EventVetoedException e) {
            return;
        }
    }

    @Override
    public void setAnchorSelectionIndex(final int index) {
        try {
            for (final VetoableListSelectionListener curListener : vetoableListSelectionListener) {
                curListener.fireListSelectionVeto();
            }
            listSelectionModel.setAnchorSelectionIndex(index);
        } catch (final EventVetoedException e) {
            return;
        }
    }

    @Override
    public void setLeadSelectionIndex(final int index) {
        try {
            for (final VetoableListSelectionListener curListener : vetoableListSelectionListener) {
                curListener.fireListSelectionVeto();
            }
            listSelectionModel.setLeadSelectionIndex(index);
        } catch (final EventVetoedException e) {
            return;
        }
    }

    @Override
    public void setSelectionInterval(final int index0, final int index1) {
        try {
            for (final VetoableListSelectionListener curListener : vetoableListSelectionListener) {
                curListener.fireListSelectionVeto();
            }
            listSelectionModel.setSelectionInterval(index0, index1);
        } catch (final EventVetoedException e) {
            return;
        }
    }

    @Override
    public void setSelectionMode(final int selectionMode) {
        try {
            for (final VetoableListSelectionListener curListener : vetoableListSelectionListener) {
                curListener.fireListSelectionVeto();
            }
            listSelectionModel.setSelectionMode(selectionMode);
        } catch (final EventVetoedException e) {
            return;
        }
    }

    @Override
    public void setValueIsAdjusting(final boolean valueIsAdjusting) {
        listSelectionModel.setValueIsAdjusting(valueIsAdjusting);
    }
}
