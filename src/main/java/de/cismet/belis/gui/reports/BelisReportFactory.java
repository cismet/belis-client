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

import java.util.ArrayList;

import de.cismet.cids.client.tools.DevelopmentTools;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public class BelisReportFactory {

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param   args  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static void main(final String[] args) throws Exception {
        final CidsBean auftrag = DevelopmentTools.createCidsBeanFromRestfulConnectionOnLocalhost(
                "BELIS",
                "Bearbeiter",
                "WendlingM",
                "kif",
                "arbeitsauftrag",
                13);
        final ArrayList al = new ArrayList<CidsBean>();
        al.add(auftrag);
        DevelopmentTools.showReportForBeans("/de/cismet/belis/gui/search/reports/arbeitsauftrag.jasper", al);
        System.exit(0);
    }
}
