/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.belis.gui.reports;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public class ReportingStatus {

    //~ Instance fields --------------------------------------------------------

    String name;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ReportingStatus object.
     *
     * @param  name  DOCUMENT ME!
     */
    public ReportingStatus(final String name) {
        this.name = name;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getName() {
        return name;
    }
}
