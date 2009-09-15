/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.belis.gui.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

/**
 * Good looking JTable
 * 
 * @author srichter
 */
public class StyleTable extends JTable {

    public static final Color COLOR_TXT_BACK = new Color(230, 230, 230);
    public static final Color COLOR_TBL_SECOND = new Color(210, 210, 210);

    @Override
    public void paint(final Graphics g) {
        super.paint(g);
        paintEmptyRows(g);
    }

    protected void paintEmptyRows(final Graphics g) {
        final int rowCount = getRowCount();
        final java.awt.Rectangle bounds = g.getClipBounds();
        if (rowCount * rowHeight < bounds.height) {
            for (int i = rowCount; i <= bounds.height / rowHeight; ++i) {
                g.setColor(colorForRow(i));
                g.fillRect(bounds.x, i * rowHeight, bounds.width, rowHeight);
            }
        }
    }

    /**
     * Changes the behavior of a table in a JScrollPane to be more like
     * the behavior of JList, which expands to fill the available space.
     * JTable normally restricts its size to just what's needed by its
     * model.
     */
    @Override
    public boolean getScrollableTracksViewportHeight() {
        final Container parent = getParent();
        if (parent instanceof JViewport) {
            final JViewport view = (JViewport) parent;
            return (view.getHeight() > getPreferredSize().height);
        }
        return false;
    }

    /**
     * Calculate Background color for a given row
     */
    protected Color colorForRow(final int row) {
        return (row % 2 == 0) ? COLOR_TXT_BACK : COLOR_TBL_SECOND;
    }

    @Override
    public Component prepareRenderer(final TableCellRenderer renderer, final int row, final int column) {
        final Component c = super.prepareRenderer(renderer, row, column);
        if (isCellSelected(row, column) == false) {
            c.setBackground(colorForRow(row));
            c.setForeground(UIManager.getColor("Table.foreground"));
        } else {
            c.setBackground(UIManager.getColor("Table.selectionBackground"));
            c.setForeground(UIManager.getColor("Table.selectionForeground"));
        }
        return c;
    }
}
