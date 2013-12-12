/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.belis.util;

import org.jdesktop.swingx.JXDatePicker;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.cismet.cids.editors.DefaultBindableCheckboxField;
import de.cismet.cids.editors.DefaultBindableColorChooser;
import de.cismet.cids.editors.DefaultBindableDateChooser;

/**
 * Contains some methods to set gui components to read only.
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class RendererTools {

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  tf  DOCUMENT ME!
     */
    public static void makeReadOnly(final JTextField tf) {
        tf.setBorder(null);
        tf.setOpaque(false);
        tf.setEditable(false);
        tf.setEnabled(true);
//        tf.setForeground(new Color(60, 60, 60));
        tf.setDisabledTextColor(tf.getForeground());
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cb  DOCUMENT ME!
     */
    public static void makeReadOnly(final JComboBox cb) {
        cb.setEnabled(false);
        cb.setRenderer(new CustomListCellRenderer(cb.getRenderer()));
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cb  DOCUMENT ME!
     */
    public static void makeReadOnly(final DefaultBindableDateChooser cb) {
        cb.setEditable(false);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  ta  DOCUMENT ME!
     */
    public static void makeReadOnly(final JTextArea ta) {
        ta.setEditable(false);
//        ta.setForeground(new Color(76, 76, 76));
        ta.setDisabledTextColor(ta.getForeground());
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cb  DOCUMENT ME!
     */
    public static void makeReadOnly(final JCheckBox cb) {
        cb.setEnabled(false);
        cb.setForeground(Color.BLACK);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cc  DOCUMENT ME!
     */
    public static void makeReadOnly(final DefaultBindableColorChooser cc) {
        cc.setReadOnly(true);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cc  DOCUMENT ME!
     */
    public static void makeReadOnly(final DefaultBindableCheckboxField cc) {
        cc.setReadOnly(true);
        cc.setForeground(Color.BLACK);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  rb  cc DOCUMENT ME!
     */
    public static void makeReadOnly(final JRadioButton rb) {
        rb.setEnabled(false);
        rb.setForeground(new Color(76, 76, 76));
    }

    /**
     * DOCUMENT ME!
     *
     * @param  b  cc DOCUMENT ME!
     */
    public static void makeReadOnly(final JButton b) {
        b.setEnabled(false);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  dp  rb cc DOCUMENT ME!
     */
    public static void makeReadOnly(final JXDatePicker dp) {
        dp.setEnabled(true);
        dp.setEditable(false);
        dp.setForeground(Color.BLACK);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  js  rb cc DOCUMENT ME!
     */
    public static void makeReadOnly(final JSpinner js) {
        js.setEnabled(false);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  tab  rb cc DOCUMENT ME!
     */
    public static void makeReadOnly(final JTable tab) {
        tab.setEnabled(false);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  b  cc DOCUMENT ME!
     */
    public static void makeWritable(final JButton b) {
        b.setEnabled(true);
//        rb.setForeground(new Color(76, 76, 76));
    }

    /**
     * DOCUMENT ME!
     *
     * @param  dp  rb cc DOCUMENT ME!
     */
    public static void makeWritable(final JXDatePicker dp) {
        dp.setEditable(true);
        dp.setEnabled(true);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  js  rb cc DOCUMENT ME!
     */
    public static void makeWritable(final JSpinner js) {
        js.setEnabled(true);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  tf  DOCUMENT ME!
     */
    public static void makeWritable(final JTextField tf) {
        tf.setBorder(new JTextField().getBorder());
        tf.setOpaque(true);
        tf.setEditable(true);
        tf.setEnabled(true);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cb  DOCUMENT ME!
     */
    public static void makeWritable(final JComboBox cb) {
        cb.setEnabled(true);
        if (cb.getRenderer() instanceof CustomListCellRenderer) {
            cb.setRenderer(((CustomListCellRenderer)cb.getRenderer()).getInnerRenderer());
        }
//        cb.setRenderer((new JComboBox()).getRenderer());
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cb  DOCUMENT ME!
     */
    public static void makeWritable(final DefaultBindableDateChooser cb) {
        cb.setEditable(true);
        cb.setEnabled(true);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  ta  DOCUMENT ME!
     */
    public static void makeWritable(final JTextArea ta) {
        ta.setEditable(true);
        ta.setEnabled(true);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cb  DOCUMENT ME!
     */
    public static void makeWritable(final JCheckBox cb) {
        cb.setEnabled(true);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cc  DOCUMENT ME!
     */
    public static void makeWritable(final DefaultBindableColorChooser cc) {
        cc.setReadOnly(false);
        cc.setEnabled(true);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cc  DOCUMENT ME!
     */
    public static void makeWritable(final DefaultBindableCheckboxField cc) {
        cc.setReadOnly(false);
        cc.setEnabled(true);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  tab  cc DOCUMENT ME!
     */
    public static void makeWritable(final JTable tab) {
        tab.setEnabled(true);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  tf        DOCUMENT ME!
     * @param  editable  DOCUMENT ME!
     */
    public static void setEditable(final JTextField tf, final boolean editable) {
        if (editable) {
            makeWritable(tf);
        } else {
            makeReadOnly(tf);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cb        DOCUMENT ME!
     * @param  editable  DOCUMENT ME!
     */
    public static void setEditable(final JComboBox cb, final boolean editable) {
        if (editable) {
            makeWritable(cb);
        } else {
            makeReadOnly(cb);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cb        DOCUMENT ME!
     * @param  editable  DOCUMENT ME!
     */
    public static void setEditable(final DefaultBindableDateChooser cb, final boolean editable) {
        if (editable) {
            makeWritable(cb);
        } else {
            makeReadOnly(cb);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  ta        DOCUMENT ME!
     * @param  editable  DOCUMENT ME!
     */
    public static void setEditable(final JTextArea ta, final boolean editable) {
        if (editable) {
            makeWritable(ta);
        } else {
            makeReadOnly(ta);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cb        DOCUMENT ME!
     * @param  editable  DOCUMENT ME!
     */
    public static void setEditable(final JCheckBox cb, final boolean editable) {
        if (editable) {
            makeWritable(cb);
        } else {
            makeReadOnly(cb);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cc        DOCUMENT ME!
     * @param  editable  DOCUMENT ME!
     */
    public static void setEditable(final DefaultBindableColorChooser cc, final boolean editable) {
        if (editable) {
            makeWritable(cc);
        } else {
            makeReadOnly(cc);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cc        DOCUMENT ME!
     * @param  editable  DOCUMENT ME!
     */
    public static void setEditable(final DefaultBindableCheckboxField cc, final boolean editable) {
        if (editable) {
            makeWritable(cc);
        } else {
            makeReadOnly(cc);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  b         cc DOCUMENT ME!
     * @param  editable  DOCUMENT ME!
     */
    public static void setEditable(final JButton b, final boolean editable) {
        if (editable) {
            makeWritable(b);
        } else {
            makeReadOnly(b);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  dp        cc DOCUMENT ME!
     * @param  editable  DOCUMENT ME!
     */
    public static void setEditable(final JXDatePicker dp, final boolean editable) {
        if (editable) {
            makeWritable(dp);
        } else {
            makeReadOnly(dp);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  js        cc DOCUMENT ME!
     * @param  editable  DOCUMENT ME!
     */
    public static void setEditable(final JSpinner js, final boolean editable) {
        if (editable) {
            makeWritable(js);
        } else {
            makeReadOnly(js);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  tab       cc DOCUMENT ME!
     * @param  editable  DOCUMENT ME!
     */
    public static void setEditable(final JTable tab, final boolean editable) {
        if (editable) {
            makeWritable(tab);
        } else {
            makeReadOnly(tab);
        }
    }
//
//    /**
//     * DOCUMENT ME!
//     *
//     * @param  rb  cc DOCUMENT ME!
//     */
//    public static void setEditable(final JRadioButton rb, final boolean editable) {
//        if (editable) {
//            makeWritable(rb);
//        } else {
//            makeReadOnly(rb);
//        }
//    }
}
