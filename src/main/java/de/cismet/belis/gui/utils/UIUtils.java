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

import org.jdesktop.swingx.graphics.ShadowRenderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import de.cismet.security.WebDavClient;

import de.cismet.tools.gui.documents.DefaultDocument;
import de.cismet.tools.gui.jtable.sorting.AlphanumComparator;

/**
 * DOCUMENT ME!
 *
 * @author   flughafen
 * @version  $Revision$, $Date$
 */
public class UIUtils {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(UIUtils.class);

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
     * @param   in           DOCUMENT ME!
     * @param   shadowPixel  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static BufferedImage generateShadow(final Image in, final int shadowPixel) {
        final BufferedImage input;
        if (in instanceof BufferedImage) {
            input = (BufferedImage)in;
        } else {
            final BufferedImage temp = new BufferedImage(in.getWidth(null),
                    in.getHeight(null),
                    BufferedImage.TYPE_4BYTE_ABGR);
            final Graphics tg = temp.createGraphics();
            tg.drawImage(in, 0, 0, null);
            tg.dispose();
            input = temp;
        }

        final ShadowRenderer renderer = new ShadowRenderer(shadowPixel, 0.5f, Color.BLACK);
        final BufferedImage shadow = renderer.createShadow(input);
        final BufferedImage result = new BufferedImage(input.getWidth() + (2 * shadowPixel),
                input.getHeight()
                        + (2 * shadowPixel),
                BufferedImage.TYPE_4BYTE_ABGR);
        final Graphics2D rg = result.createGraphics();
        rg.drawImage(shadow, 0, 0, null);
        rg.drawImage(input, 0, 0, null);
        rg.dispose();
        return result;
    }

    /**
     * Starts a background thread with loads the picture from the url, resizes it to the given maximums, adds a
     * dropshadow of the given length and then sets the whole picture on a given JLabel.
     *
     * <p>Can be called from ANY thread, no matter if EDT or not!</p>
     *
     * @param  bildURL     DOCUMENT ME!
     * @param  maxPixelX   DOCUMENT ME!
     * @param  maxPixelY   DOCUMENT ME!
     * @param  shadowSize  DOCUMENT ME!
     * @param  toSet       DOCUMENT ME!
     */
    public static void loadPictureAndSet(final String bildURL,
            final int maxPixelX,
            final int maxPixelY,
            final int shadowSize,
            final JLabel toSet) {
        final Thread loader = new Thread() {

                @Override
                public void run() {
                    final ImageIcon finBild = loadPicture(bildURL, maxPixelX, maxPixelY, shadowSize);
                    if (finBild != null) {
                        EventQueue.invokeLater(new Runnable() {

                                @Override
                                public void run() {
                                    if (finBild != null) {
                                        toSet.setIcon(finBild);
                                    } else {
                                        toSet.setVisible(false);
                                    }
                                }
                            });
                    }
                }
            };
        loader.start();
    }

    /**
     * Starts a background thread with loads the picture from the url, resizes it to the given maximums, adds a
     * dropshadow of the given length and then sets the whole picture on a given JButton.
     *
     * <p>Can be called from ANY thread, no matter if EDT or not!</p>
     *
     * @param  bildURL     DOCUMENT ME!
     * @param  maxPixelX   DOCUMENT ME!
     * @param  maxPixelY   DOCUMENT ME!
     * @param  shadowSize  DOCUMENT ME!
     * @param  toSet       DOCUMENT ME!
     */
    public static void loadPictureAndSet(final String bildURL,
            final int maxPixelX,
            final int maxPixelY,
            final int shadowSize,
            final JButton toSet) {
        final Thread loader = new Thread() {

                @Override
                public void run() {
                    final ImageIcon finBild = loadPicture(bildURL, maxPixelX, maxPixelY, shadowSize);
                    if (finBild != null) {
                        EventQueue.invokeLater(new Runnable() {

                                @Override
                                public void run() {
                                    if (finBild != null) {
                                        toSet.setIcon(finBild);
                                    } else {
                                        toSet.setVisible(false);
                                    }
                                }
                            });
                    }
                }
            };
        loader.start();
    }

    /**
     * Starts a background thread with loads the picture from the url, resizes it to the given maximums, adds a
     * dropshadow of the given length.
     *
     * @param   bildURL     DOCUMENT ME!
     * @param   maxPixelX   DOCUMENT ME!
     * @param   maxPixelY   DOCUMENT ME!
     * @param   shadowSize  DOCUMENT ME!
     *
     * @return  ImageIcon with the loaded picture
     */
    public static ImageIcon loadPicture(final String bildURL,
            final int maxPixelX,
            final int maxPixelY,
            final int shadowSize) {
        ImageIcon bild = null;
        if ((bildURL != null) && (bildURL.length() > 0)) {
            final String urlString = bildURL.trim();
            Image buffImage = new DefaultDocument(urlString, urlString).getPreview(maxPixelX, maxPixelY);
            if (buffImage != null) {
                if (shadowSize > 0) {
                    // Static2DTools.getFasterScaledInstance(buffImage, width, height,
                    // RenderingHints.VALUE_INTERPOLATION_BICUBIC, true)
                    buffImage = generateShadow(buffImage, shadowSize);
                }
                bild = new ImageIcon(buffImage);
            }
        }
        return bild;
    }

    /**
     * Starts a background thread with loads the picture from the url, resizes it to the given maximums, adds a
     * dropshadow of the given length.
     *
     * @param   bildURL          DOCUMENT ME!
     * @param   maxPixelX        DOCUMENT ME!
     * @param   maxPixelY        DOCUMENT ME!
     * @param   shadowSize       DOCUMENT ME!
     * @param   webDavClient     DOCUMENT ME!
     * @param   webDavdirectory  DOCUMENT ME!
     * @param   parent           DOCUMENT ME!
     *
     * @return  ImageIcon with the loaded picture
     */
    public static ImageIcon loadPicture(final String bildURL,
            final int maxPixelX,
            final int maxPixelY,
            final int shadowSize,
            final WebDavClient webDavClient,
            final String webDavdirectory,
            final Component parent) {
        ImageIcon bild = null;
        if ((bildURL != null) && (bildURL.length() > 0)) {
            final String urlString = bildURL.trim();
            Image buffImage = new DefaultDocument(urlString, urlString, webDavClient, parent, webDavdirectory)
                        .getPreview(maxPixelX, maxPixelY);
            if (buffImage != null) {
                if (shadowSize > 0) {
                    // Static2DTools.getFasterScaledInstance(buffImage, width, height,
                    // RenderingHints.VALUE_INTERPOLATION_BICUBIC, true)
                    buffImage = generateShadow(buffImage, shadowSize);
                }
                bild = new ImageIcon(buffImage);
            }
        }
        return bild;
    }

    /**
     * Adds a mouse listener to the given component, so that the cursor will change on mouse entered/exited.
     *
     * <p>Hint: Uses the awt.Cursor.XXX constants!</p>
     *
     * @param   toDecorate    DOCUMENT ME!
     * @param   mouseEntered  DOCUMENT ME!
     * @param   mouseExited   DOCUMENT ME!
     *
     * @return  the listener that was added
     */
    public static MouseListener decorateComponentWithMouseOverCursorChange(final JComponent toDecorate,
            final int mouseEntered,
            final int mouseExited) {
        final MouseListener toAdd = new MouseAdapter() {

                @Override
                public void mouseEntered(final MouseEvent e) {
                    toDecorate.setCursor(new Cursor(mouseEntered));
                }

                @Override
                public void mouseExited(final MouseEvent e) {
                    toDecorate.setCursor(new Cursor(mouseExited));
                }
            };
        toDecorate.addMouseListener(toAdd);
        return toAdd;
    }

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
            log.warn("das 1te Mal ging schief.Fehler beim Oeffnen von:" + gotoUrl + "\nLetzter Versuch", e2);
            try {
                gotoUrl = gotoUrl.replaceAll("\\\\", "/");
                gotoUrl = gotoUrl.replaceAll(" ", "%20");
                de.cismet.tools.BrowserLauncher.openURL("file:///" + gotoUrl);
            } catch (Exception e3) {
                log.error("Auch das 2te Mal ging schief.Fehler beim Oeffnen von:file://" + gotoUrl, e3);
            }
        }
    }

    /**
     * Makes the parameter table alphanumerically sortable.
     *
     * @param  tbl  DOCUMENT ME!
     */
    public static final void decorateTableWithSorter(final JTable tbl) {
        final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tbl.getModel());
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
