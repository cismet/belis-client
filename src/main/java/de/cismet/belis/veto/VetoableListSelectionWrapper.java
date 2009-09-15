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
 *
 * @author spuhl
 */
public class VetoableListSelectionWrapper implements ListSelectionModel {

    private final ArrayList<VetoableListSelectionListener> vetoableListSelectionListener = new ArrayList<VetoableListSelectionListener>();
    private ListSelectionModel listSelectionModel = null;

    public void addVetoableTreeSelectionListener(final VetoableListSelectionListener listener) {
        vetoableListSelectionListener.add(listener);
    }

    public void removeVetoableTreeSelectionListener(final VetoableListSelectionListener listener) {
        vetoableListSelectionListener.remove(listener);
    }

    public VetoableListSelectionWrapper(ListSelectionModel listSelectionModel) {
        if (listSelectionModel != null) {
            this.listSelectionModel = listSelectionModel;
        } else {
            this.listSelectionModel = new DefaultListSelectionModel();
        }

    }

    @Override
    public void addListSelectionListener(ListSelectionListener x) {
        listSelectionModel.addListSelectionListener(x);
    }

    @Override
    public void addSelectionInterval(int index0, int index1) {
        try {
            for (VetoableListSelectionListener curListener : vetoableListSelectionListener) {
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
            for (VetoableListSelectionListener curListener : vetoableListSelectionListener) {
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
    public void insertIndexInterval(int index, int length, boolean before) {
        try {
            for (VetoableListSelectionListener curListener : vetoableListSelectionListener) {
                curListener.fireListSelectionVeto();
            }

            listSelectionModel.insertIndexInterval(index, length, before);
        } catch (final EventVetoedException e) {
            return;
        }
    }

    @Override
    public boolean isSelectedIndex(int index) {
        return listSelectionModel.isSelectedIndex(index);
    }

    @Override
    public boolean isSelectionEmpty() {
        return listSelectionModel.isSelectionEmpty();
    }

    @Override
    public void removeIndexInterval(int index0, int index1) {
        try {
            for (VetoableListSelectionListener curListener : vetoableListSelectionListener) {
                curListener.fireListSelectionVeto();
            }
            listSelectionModel.removeIndexInterval(index0, index1);
        } catch (final EventVetoedException e) {
            return;
        }
    }

    @Override
    public void removeListSelectionListener(ListSelectionListener x) {
        listSelectionModel.removeListSelectionListener(x);;
    }

    @Override
    public void removeSelectionInterval(int index0, int index1) {
         try {
            for (VetoableListSelectionListener curListener : vetoableListSelectionListener) {
                curListener.fireListSelectionVeto();
            }
            listSelectionModel.removeSelectionInterval(index0, index1);
        } catch (final EventVetoedException e) {
            return;
        }
    }

    @Override
    public void setAnchorSelectionIndex(int index) {
        try {
            for (VetoableListSelectionListener curListener : vetoableListSelectionListener) {
                curListener.fireListSelectionVeto();
            }
            listSelectionModel.setAnchorSelectionIndex(index);
        } catch (final EventVetoedException e) {
            return;
        }
    }

    @Override
    public void setLeadSelectionIndex(int index) {
        try {
            for (VetoableListSelectionListener curListener : vetoableListSelectionListener) {
                curListener.fireListSelectionVeto();
            }
            listSelectionModel.setLeadSelectionIndex(index);
        } catch (final EventVetoedException e) {
            return;
        }
    }

    @Override
    public void setSelectionInterval(int index0, int index1) {
        try {
            for (VetoableListSelectionListener curListener : vetoableListSelectionListener) {
                curListener.fireListSelectionVeto();
            }
            listSelectionModel.setSelectionInterval(index0, index1);
        } catch (final EventVetoedException e) {
            return;
        }
    }

    @Override
    public void setSelectionMode(int selectionMode) {
         try {
            for (VetoableListSelectionListener curListener : vetoableListSelectionListener) {
                curListener.fireListSelectionVeto();
            }
            listSelectionModel.setSelectionMode(selectionMode);
        } catch (final EventVetoedException e) {
            return;
        }
    }

    @Override
    public void setValueIsAdjusting(boolean valueIsAdjusting) {
        listSelectionModel.setValueIsAdjusting(valueIsAdjusting);
    }
}
