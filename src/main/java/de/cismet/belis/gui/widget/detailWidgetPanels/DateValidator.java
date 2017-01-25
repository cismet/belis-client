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

import org.jdesktop.beansbinding.Validator;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * DOCUMENT ME!
 *
 * @author   Gilles Baatz
 * @version  $Revision$, $Date$
 */
public class DateValidator extends Validator<Date> {

    //~ Static fields/initializers ---------------------------------------------

    private static final Calendar earliestDate = new GregorianCalendar(1950, Calendar.JANUARY, 1);

    //~ Methods ----------------------------------------------------------------

    @Override
    public Validator.Result validate(final Date value) {
        if ((value != null) && (value.compareTo(earliestDate.getTime()) < 0)) {
            return new Validator.Result("code", "Datum muss nach dem 01.01.1950 sein.");
        }
        return null;
    }
}
