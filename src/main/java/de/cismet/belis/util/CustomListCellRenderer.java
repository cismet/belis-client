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
package de.cismet.belis.util;

/**
 *
 * @author daniel
 */

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class CustomListCellRenderer extends DefaultListCellRenderer {

    //~ Instance fields --------------------------------------------------------

    ColorLabel renderer = new ColorLabel();
    private ListCellRenderer r;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new CustomListCellRenderer object.
     *
     * @param  r  DOCUMENT ME!
     */
    public CustomListCellRenderer(final ListCellRenderer r) {
        this.r = r;
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public Component getListCellRendererComponent(final JList list,
            final Object value,
            final int index,
            final boolean isSelected,
            final boolean cellHasFocus) {
        final Component c = r.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (c instanceof JLabel) {
//            ((JLabel)c).setEnabled(true);
//            ((JLabel)c).setForeground(Color.BLACK);
//            ((JLabel)c).setOpaque(false);
//            ((JLabel)c).setUI((new JLabel()).getUI());
            renderer.setIcon(((JLabel)c).getIcon());
            renderer.setText(((JLabel)c).getText());
            return renderer;
        }
//        if (value != null) {
//            renderer.setText(value.toString());
//        } else {
//            renderer.setText("");
//        }
        return c;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public ListCellRenderer getInnerRenderer() {
        return r;
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    static class ColorLabel extends JLabel {

        //~ Methods ------------------------------------------------------------

        @Override
        public void setForeground(final Color fg) {
            // ignore
        }
        @Override
        public void setBackground(final Color fg) {
            // ignore
        }
        /**
         * DOCUMENT ME!
         *
         * @param  bg  DOCUMENT ME!
         */
        public void setBackground_internal(final Color bg) {
            super.setBackground(bg);
        }
        /**
         * DOCUMENT ME!
         *
         * @param  fg  DOCUMENT ME!
         */
        public void setForeground_internal(final Color fg) {
            super.setForeground(fg);
        }
    }
}
