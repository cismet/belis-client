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
package de.cismet.belis.gui.utils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import de.cismet.tools.gui.jtable.sorting.AlphanumComparator;

/**
 * DOCUMENT ME!
 *
 * @author   flughafen
 * @version  $Revision$, $Date$
 */
public class UIUtils {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(UIUtils.class);

    //~ Enums ------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public enum DateDiff {

        //~ Enum constants -----------------------------------------------------

        MILLISECOND, SECOND, MINUTE, HOUR, DAY, WEEK, MONTH, YEAR
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  url  DOCUMENT ME!
     */
    public static void openURL(final String url) {
        if (url == null) {
            return;
        }
        String gotoUrl = url;
        try {
            de.cismet.tools.BrowserLauncher.openURL(gotoUrl);
        } catch (Exception e2) {
            LOG.warn("das 1te Mal ging schief.Fehler beim Oeffnen von:" + gotoUrl + "\nLetzter Versuch", e2);
            try {
                gotoUrl = gotoUrl.replaceAll("\\\\", "/");
                gotoUrl = gotoUrl.replaceAll(" ", "%20");
                de.cismet.tools.BrowserLauncher.openURL("file:///" + gotoUrl);
            } catch (Exception e3) {
                LOG.error("Auch das 2te Mal ging schief.Fehler beim Oeffnen von:file://" + gotoUrl, e3);
            }
        }
    }

    /**
     * Makes the parameter table alphanumerically sortable.
     *
     * @param  tbl  DOCUMENT ME!
     */
    public static final void decorateTableWithSorter(final JTable tbl) {
        final TableRowSorter<TableModel> sorter = new TableRowSorter<>(tbl.getModel());
//        sorter.setSortsOnUpdates(true);
        for (int i = 0; i < tbl.getColumnCount(); ++i) {
            sorter.setComparator(i, AlphanumComparator.getInstance());
        }
        tbl.setRowSorter(sorter);
        tbl.getTableHeader().addMouseListener(new TableHeaderUnsortMouseAdapter(tbl));
    }
}

/**
 * MouseAdapter for remove sorting from the table when perfoming a right-clck on the header.
 *
 * @author   srichter
 * @version  $Revision$, $Date$
 */
final class TableHeaderUnsortMouseAdapter extends MouseAdapter {

    //~ Instance fields --------------------------------------------------------

    private JTable tbl;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new TableHeaderUnsortMouseAdapter object.
     *
     * @param  tbl  DOCUMENT ME!
     */
    public TableHeaderUnsortMouseAdapter(final JTable tbl) {
        this.tbl = tbl;
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void mousePressed(final MouseEvent e) {
        if (e.isPopupTrigger()) {
            tbl.getRowSorter().setSortKeys(null);
        }
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
        if (e.isPopupTrigger()) {
            tbl.getRowSorter().setSortKeys(null);
        }
    }
}
