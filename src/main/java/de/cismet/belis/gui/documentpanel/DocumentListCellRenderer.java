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
package de.cismet.belis.gui.documentpanel;

import java.awt.Color;
import java.awt.Component;

import java.io.File;

import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JList;

import de.cismet.cids.custom.beans.belis.DmsUrlCustomBean;

/**
 * DOCUMENT ME!
 *
 * @author   srichter
 * @version  $Revision$, $Date$
 */
public class DocumentListCellRenderer extends DefaultListCellRenderer {

    //~ Static fields/initializers ---------------------------------------------

    private static final Map<String, ImageIcon> FILE_TYPE_ICONS;
    private static final String ICON_RES_PATH = "/de/cismet/belis/resource/icon/16/";
    private static final ImageIcon UNKNOWN_FORMAT = new ImageIcon(DocumentPanel.class.getResource(
                ICON_RES_PATH
                        + "unknown.png"));
    public static final String EXTENTION_PDF = "pdf";
    public static final String EXTENTION_JPG = "jpg";
    public static final String EXTENTION_JPEG = "jpeg";
    public static final String EXTENTION_GIF = "gif";
    public static final String EXTENTION_PNG = "png";
    public static final String EXTENTION_BMP = "bmp";
    public static final String EXTENTION_HTML = "html";
    public static final String EXTENTION_DOC = "doc";
    public static final String EXTENTION_XLS = "xls";

    static {
        final Class<DocumentPanel> c = DocumentPanel.class;
        FILE_TYPE_ICONS = new HashMap<String, ImageIcon>();
        FILE_TYPE_ICONS.put(EXTENTION_PDF, new ImageIcon(c.getResource(ICON_RES_PATH + "pdf.png")));
        FILE_TYPE_ICONS.put(EXTENTION_JPG, new ImageIcon(c.getResource(ICON_RES_PATH + "image.png")));
        FILE_TYPE_ICONS.put(EXTENTION_JPEG, new ImageIcon(c.getResource(ICON_RES_PATH + "image.png")));
        FILE_TYPE_ICONS.put(EXTENTION_GIF, new ImageIcon(c.getResource(ICON_RES_PATH + "image.png")));
        FILE_TYPE_ICONS.put(EXTENTION_BMP, new ImageIcon(c.getResource(ICON_RES_PATH + "image.png")));
        FILE_TYPE_ICONS.put(EXTENTION_PNG, new ImageIcon(c.getResource(ICON_RES_PATH + "image.png")));
        FILE_TYPE_ICONS.put(EXTENTION_HTML, new ImageIcon(c.getResource(ICON_RES_PATH + "html.png")));
        FILE_TYPE_ICONS.put(EXTENTION_DOC, new ImageIcon(c.getResource(ICON_RES_PATH + "doc.png")));
        FILE_TYPE_ICONS.put(EXTENTION_XLS, new ImageIcon(c.getResource(ICON_RES_PATH + "xls.png")));
    }

    //~ Instance fields --------------------------------------------------------

    private final Color colorOdd;
    private final Color colorEven;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new DocumentListCellRenderer object.
     */
    public DocumentListCellRenderer() {
        colorOdd = new Color(235, 235, 235);
        colorEven = new Color(215, 215, 215);
    }

    /**
     * Creates a new DocumentListCellRenderer object.
     *
     * @param  colorOdd   DOCUMENT ME!
     * @param  colorEven  DOCUMENT ME!
     */
    public DocumentListCellRenderer(final Color colorOdd, final Color colorEven) {
        this.colorOdd = colorOdd;
        this.colorEven = colorEven;
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public Component getListCellRendererComponent(final JList list,
            Object value,
            final int index,
            final boolean isSelected,
            final boolean cellHasFocus) {
        ImageIcon imageIcon = null;
        if (value instanceof DmsUrlCustomBean) {
            final DmsUrlCustomBean url = (DmsUrlCustomBean)value;
            final File file = url.toFile();
            value = url.getBeschreibung();
            if (file != null) {
                final String[] tmp = file.getName().split("\\.");
                if ((tmp != null) && (tmp.length > 0)) {
                    final String extension = tmp[tmp.length - 1];
                    imageIcon = FILE_TYPE_ICONS.get(extension);
                }
            }
        }
        final Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (!isSelected) {
            c.setBackground(((index % 2) == 1) ? colorOdd : colorEven);
        }
        setIcon((imageIcon != null) ? imageIcon : UNKNOWN_FORMAT);
        return c;
    }
}
