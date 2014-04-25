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
package de.cismet.belis.gui;

import org.jdesktop.beansbinding.Converter;

import java.sql.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public class DateToStringConverter extends Converter<java.sql.Date, String> {

    //~ Instance fields --------------------------------------------------------

    final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    //~ Methods ----------------------------------------------------------------

    @Override
    public String convertForward(final Date value) {
        if (value != null) {
            return dateFormat.format(value);
        } else {
            return null;
        }
    }

    @Override
    public Date convertReverse(final String value) {
        try {
            return new Date(dateFormat.parse(value.toString()).getTime());
        } catch (ParseException ex) {
            return null;
        }
    }
}
