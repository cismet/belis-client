/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.belis.util;

/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class DateTools {

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param   date  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static final Date getEndOfDay(final Date date) {
        final Calendar caledar = new GregorianCalendar();
        caledar.setTime(date);
        caledar.set(Calendar.HOUR_OF_DAY, 23);
        caledar.set(Calendar.MINUTE, 59);
        caledar.set(Calendar.SECOND, 59);
        caledar.set(Calendar.MILLISECOND, 999);
        return caledar.getTime();
    }
}
