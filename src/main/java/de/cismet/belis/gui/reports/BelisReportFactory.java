/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cismet.belis.gui.reports;

import de.cismet.cids.client.tools.DevelopmentTools;
import de.cismet.cids.dynamics.CidsBean;

/**
 *
 * @author thorsten
 */
public class BelisReportFactory {
    public static void main(String[] args) throws Exception {
        
        CidsBean auftrag=DevelopmentTools.createCidsBeanFromRestfulConnectionOnLocalhost("BELIS","Bearbeiter","WendlingM","kif","arbeitsauftrag",13);
        //DevelopmentTools.showReportForBeans(null, null);
        
    }
}
