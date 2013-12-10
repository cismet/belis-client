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

import de.cismet.cids.custom.beans.belis2.AbzweigdoseCustomBean;
import de.cismet.cids.custom.beans.belis2.ArbeitsauftragCustomBean;
import de.cismet.cids.custom.beans.belis2.ArbeitsprotokollCustomBean;
import de.cismet.cids.custom.beans.belis2.GeometrieCustomBean;
import de.cismet.cids.custom.beans.belis2.LeitungCustomBean;
import de.cismet.cids.custom.beans.belis2.MauerlascheCustomBean;
import de.cismet.cids.custom.beans.belis2.SchaltstelleCustomBean;
import de.cismet.cids.custom.beans.belis2.TdtaLeuchtenCustomBean;
import de.cismet.cids.custom.beans.belis2.TdtaStandortMastCustomBean;
import de.cismet.cids.custom.beans.belis2.VeranlassungCustomBean;

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
                if (userObject instanceof TdtaStandortMastCustomBean) {
                    if (((TdtaStandortMastCustomBean)userObject).isStandortMast()) {
                        setText("");
                    } else {
                        setText("Standort");
                    }
                    setIcon(BelisIcons.icoStandort16);
                } else if (userObject instanceof TdtaLeuchtenCustomBean) {
                    setText("Leuchte");
                    setIcon(BelisIcons.icoLeuchte16);
                } else if (userObject instanceof MauerlascheCustomBean) {
                    setText("Mauerlasche");
                    setIcon(BelisIcons.icoMauerlasche16);
                } else if (userObject instanceof SchaltstelleCustomBean) {
                    setText("Schaltstelle");
                    setIcon(BelisIcons.icoSchaltstelle16);
                } else if (userObject instanceof LeitungCustomBean) {
                    setText("Leitung");
                    setIcon(BelisIcons.icoLeitung16);
                } else if (userObject instanceof AbzweigdoseCustomBean) {
                    setText("Abzweigdose/Zugkasten");
                    setIcon(BelisIcons.icoAbzweigdose16);
                } else if (userObject instanceof VeranlassungCustomBean) {
                    setText("Veranlassung");
                    setIcon(BelisIcons.icoVeranlassung16);
                } else if (userObject instanceof ArbeitsauftragCustomBean) {
                    setText("Arbeitsauftrag");
                    setIcon(BelisIcons.icoArbeitsauftrag16);
                } else if (userObject instanceof ArbeitsprotokollCustomBean) {
                    setText("Arbeitsprotokoll");
                    setIcon(BelisIcons.icoArbeitsprotokoll16);
                } else if (userObject instanceof GeometrieCustomBean) {
                    setText("Geometrie");
                    setIcon(BelisIcons.icoGeometrie16);
                } else if (userObject instanceof String) {
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
                    } else if (userObject.equals(CustomTreeTableModel.EDIT_OBJECT_NODE)) {
                        setText(((CustomMutableTreeTableNode)value).getChildCount() + " Objekte zum Bearbeiten");
                        // setToolTipText("Neue Objekte");
                        setIcon(BelisIcons.icoEditObjects16);
                    } else {
                        setText("Unbekannter Typ");
                        setToolTipText("Unbekannter Typ");
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
