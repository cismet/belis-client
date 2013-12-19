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
public class DoubleNumberFormatter extends NumberFormatter {

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new IntegerNumberFormatter object.
     */
    public DoubleNumberFormatter() {
        super(new DecimalFormat("#0.00"));
        setMinimum(Double.MIN_VALUE);
        setMaximum(Double.MAX_VALUE);
        setValueClass(Double.class);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public String valueToString(final Object doubleValue) throws ParseException {
        if (doubleValue == null) {
            return "";
        } else {
            return super.valueToString(doubleValue);
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
