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

import de.cismet.cids.custom.beans.belis2.WorkbenchEntity;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @author   Gilles Baatz
 * @version  $Revision$, $Date$
 */
public class ObjectToKeyStringConverter extends ObjectToStringConverter {

    //~ Instance fields --------------------------------------------------------

    private final String nullString;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ObjectToKeyStringConverter object.
     */
    public ObjectToKeyStringConverter() {
        this(null);
    }

    /**
     * Creates a new ObjectToKeyStringConverter object.
     *
     * @param  nullString  DOCUMENT ME!
     */
    public ObjectToKeyStringConverter(final String nullString) {
        this.nullString = nullString;
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public String getPreferredStringForItem(final Object item) {
        if ((item != null) && (item instanceof BaseEntity)) {
            return ((BaseEntity)item).toString();
        } else {
            return nullString;
        }
    }
}
