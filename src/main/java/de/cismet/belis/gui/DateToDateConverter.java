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

import java.util.Date;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public class DateToDateConverter extends Converter<java.sql.Date, Date> {

    //~ Methods ----------------------------------------------------------------

    @Override
    public Date convertForward(final java.sql.Date value) {
        if (value != null) {
            return new Date(value.getTime());
        } else {
            return null;
        }
    }

    @Override
    public java.sql.Date convertReverse(final Date value) {
        if (value != null) {
            return new java.sql.Date(value.getTime());
        } else {
            return null;
        }
    }
}
