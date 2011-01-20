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
package de.cismet.belis.gui.renderer;

import org.apache.log4j.Logger;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import de.cismet.belis.todo.CustomMutableTreeTableNode;
import de.cismet.belis.todo.CustomTreeTableModel;

import de.cismet.belis.util.BelisIcons;

import de.cismet.belisEE.entity.Abzweigdose;
import de.cismet.belisEE.entity.Leitung;
import de.cismet.belisEE.entity.Leuchte;
import de.cismet.belisEE.entity.Mauerlasche;
import de.cismet.belisEE.entity.Schaltstelle;
import de.cismet.belisEE.entity.Standort;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public class WorkbenchTreeTableRenderer extends DefaultTreeCellRenderer {

    //~ Instance fields --------------------------------------------------------

    private final Logger log = org.apache.log4j.Logger.getLogger(this.getClass());

    //~ Methods ----------------------------------------------------------------

    @Override
    public Component getTreeCellRendererComponent(final JTree tree,
            final Object value,
            final boolean sel,
            final boolean expanded,
            final boolean leaf,
            final int row,
            final boolean hasFocus) {
        final Component test = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        if (value != null) {
            if (value instanceof CustomMutableTreeTableNode) {
                final Object userObject = ((CustomMutableTreeTableNode)value).getUserObject();
                if (userObject instanceof Standort) {
                    if (((Standort)userObject).isStandortMast()) {
                        setText("Mast");
                        // setToolTipText("Mast");
                    } else {
                        setText("Standort");
                        // setToolTipText("Standort");
                    }
                    setIcon(BelisIcons.icoStandort16);
                } else if (userObject instanceof Leuchte) {
                    setText("Leuchte");
                    // setToolTipText("Leuchte");
                    setIcon(BelisIcons.icoLeuchte16);
                } else if (userObject instanceof Mauerlasche) {
                    setText("Mauerlasche");
                    // setToolTipText("Mauerlasche");
                    setIcon(BelisIcons.icoMauerlasche16);
                } else if (userObject instanceof Schaltstelle) {
                    setText("Schaltstelle");
                    // setToolTipText("Schaltstelle");
                    setIcon(BelisIcons.icoSchaltstelle16);
                } else if (userObject instanceof Leitung) {
                    setText("Leitung");
                    // setToolTipText("Leitung");
                    // ToDo Leitung icon
                    setIcon(BelisIcons.icoLeitung16);
                } else if (userObject instanceof Abzweigdose) {
                    setText("Abzweigdose/Zugkasten");
                    // setToolTipText("Leitung");
                    // ToDo Leitung icon
                    setIcon(BelisIcons.icoAbzweigdose16);
                } else if (userObject instanceof String) {
                    if (userObject != null) {
                        if (userObject.equals(CustomTreeTableModel.HIT_NODE)) {
                            setText(((CustomMutableTreeTableNode)value).getChildCount() + " Suchergebnisse");
                            // setToolTipText("Suchergebnisse");
                            setIcon(BelisIcons.icoSuchergebnisse16);
//ToDo disabled Functionality 04.05.2009
                            // } else if(userObject.equals(CustomTreeTableModel.PROCESSED_NODE)){
//                            setText(((CustomMutableTreeTableNode)value).getChildCount()+" Bearbeitete Objekte");
//                            setIcon(BelisIcons.icoBearbeiteteObjekte16);
                        } else if (userObject.equals(CustomTreeTableModel.NEW_OBJECT_NODE)) {
                            setText(((CustomMutableTreeTableNode)value).getChildCount() + " Neue Objekte");
                            // setToolTipText("Neue Objekte");
                            setIcon(BelisIcons.icoNewObjects16);
                        } else {
                            setText("Unbekannter Typ");
                            setToolTipText("Unbekannter Typ");
                        }
                    } else {
                        setText("");
                    }
                } else {
                    setText("Unbekannter Typ");
                    // setToolTipText("Unbekannter Typ");
                }
            } else {
                setText("Unbekannter Typ");
                // setToolTipText("Unbekannter Typ");
            }
        } else {
            setText("");
            // setToolTipText("");
        }
//              setDisabledIcon(null);
//                setIcon(null);
//                setLeafIcon(null);
//                setClosedIcon(null);
//                setOpenIcon(null);
//                setText("test");
        return this;
    }
}
