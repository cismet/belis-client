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

import java.text.DecimalFormat;
import java.text.ParseException;

import javax.swing.text.NumberFormatter;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class IntegerNumberFormatter extends NumberFormatter {

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new IntegerNumberFormatter object.
     */
    public IntegerNumberFormatter() {
        super(new DecimalFormat("#0"));
        setMinimum(Integer.MIN_VALUE);
        setMaximum(Integer.MAX_VALUE);
        setValueClass(Integer.class);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public String valueToString(final Object integer) throws ParseException {
        if (integer == null) {
            return "";
        } else {
            return super.valueToString(integer);
        }
    }
    @Override
    public Object stringToValue(final String text) throws ParseException {
        if ("".equals(text)) {
            return null;
        }
        return super.stringToValue(text);
    }
}
