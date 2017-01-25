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
package de.cismet.belis.gui.widget.detailWidgetPanels;

import org.jdesktop.swingx.autocomplete.ObjectToStringConverter;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @author   Gilles Baatz
 * @version  $Revision$, $Date$
 */
public class ObjectToPkConverter extends ObjectToStringConverter {

    //~ Instance fields --------------------------------------------------------

    private final String pk;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ObjectToPkConverter object.
     *
     * @param  pk  DOCUMENT ME!
     */
    public ObjectToPkConverter(final String pk) {
        this.pk = pk;
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public String getPreferredStringForItem(final Object item) {
        if ((item != null) && (item instanceof BaseEntity) && (((CidsBean)item).getProperty(pk) != null)) {
            return ((CidsBean)item).getProperty(pk).toString();
        } else {
            return null;
        }
    }
}
//    /*         * AutoCompleteEditor.java       */
//    public class AutoCompleteEditor extends BasicComboBoxEditor {
//
//        private JTextField editor = null;
//
//        public AutoCompleteEditor(JComboBox combo, boolean caseSensitive, ObjectToStringConverter converter) {
//            super();
//            editor = new AutoCompleteEditorComponent(combo, caseSensitive, converter);
//        }
//
//        /**
//         * overrides BasicComboBox's getEditorComponent to return custom TextField
//        (AutoCompleteEditorComponent)          */
//        public Component getEditorComponent() {
//            return editor;
//        }
//    }
//    /*         * AutoCompleteEditorComponent.java      */
//    public class AutoCompleteEditorComponent extends JTextField {
//
//        JComboBox combo = null;
//        boolean caseSensitive = false;
//        ObjectToStringConverter converter;
//
//        public AutoCompleteEditorComponent(JComboBox combo, boolean caseSensitive, final ObjectToStringConverter converter) {
//            super();
//            this.combo = combo;
//            this.caseSensitive = caseSensitive;
//            this.converter = converter;
//        }
//
//        /**
//         * overwritten to return custom PlainDocument which does the work*/
//        protected Document createDefaultModel() {
//            return new PlainDocument() {
//
//                public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
//                    if (str == null || str.length() == 0 || converter == null) {
//                        return;
//                    }
//                    int size = combo.getItemCount();
//                    String text = getText(0, getLength());
//                    for (int i = 0; i < size; i++) {
//                        String item = converter.getPreferredStringForItem(combo.getItemAt(i));
//                        if (getLength() + str.length() > item.length()) {
//                            continue;
//                        }
//                        if (!caseSensitive) {
//                            if ((text + str).equalsIgnoreCase(item)) {
//                                combo.setSelectedIndex(i);
//                                //if (!combo.isPopupVisible())
//                                //    combo.setPopupVisible(true);
//                                super.remove(0, getLength());
//                                super.insertString(0, item, a);
//                                return;
//                            } else if (item.substring(0, getLength() + str.length()).equalsIgnoreCase(text + str)) {
//                                combo.setSelectedIndex(i);
//                                if (!combo.isPopupVisible()) {
//                                    combo.setPopupVisible(true);
//                                }
//                                super.remove(0, getLength());
//                                super.insertString(0, item, a);
//                                return;
//                            }
//                        } else if (caseSensitive) {
//                            if ((text + str).equals(item)) {
//                                combo.setSelectedIndex(i);
//                                if (!combo.isPopupVisible()) {
//                                    combo.setPopupVisible(true);
//                                }
//                                super.remove(0, getLength());
//                                super.insertString(0, item, a);
//                                return;
//                            } else if (item.substring(0, getLength() + str.length()).equals(text + str)) {
//                                combo.setSelectedIndex(i);
//                                if (!combo.isPopupVisible()) {
//                                    combo.setPopupVisible(true);
//                                }
//                                super.remove(0, getLength());
//                                super.insertString(0, item, a);
//                                return;
//                            }
//                        }
//                    }
//                }
//            };
//        }
//    }
