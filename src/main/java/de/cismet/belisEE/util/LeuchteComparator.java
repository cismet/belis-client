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
package de.cismet.belisEE.util;

import java.io.Serializable;

import java.util.Comparator;

import de.cismet.cids.custom.beans.belis.TdtaLeuchtenCustomBean;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public class LeuchteComparator implements Comparator<TdtaLeuchtenCustomBean>, Serializable {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(LeuchteComparator.class);

    //~ Methods ----------------------------------------------------------------

    @Override
    public int compare(final TdtaLeuchtenCustomBean l1, final TdtaLeuchtenCustomBean l2) {
        if ((l1 != null) && (l2 != null)) {
            if (l1.equals(l2)) {
                return 0;
            } else if ((((TdtaLeuchtenCustomBean)l1).getLeuchtennummer() != null)
                        && (((TdtaLeuchtenCustomBean)l2).getLeuchtennummer() != null)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("l1: " + ((TdtaLeuchtenCustomBean)l1).getLeuchtennummer() + " l2: "
                                + ((TdtaLeuchtenCustomBean)l2).getLeuchtennummer());
                }
                if (((TdtaLeuchtenCustomBean)l1).getLeuchtennummer()
                            == ((TdtaLeuchtenCustomBean)l2).getLeuchtennummer()) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Leuchtennummer are equal");
                    }
                    return 1;
                } else if (((TdtaLeuchtenCustomBean)l1).getLeuchtennummer()
                            > ((TdtaLeuchtenCustomBean)l2).getLeuchtennummer()) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("l1 Leuchtennummer greater l2 leuchtennummer");
                    }
                    return 1;
                } else {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("l2 Leuchtennummer greater l1 Leuchtennummer");
                    }
                    return -1;
                }
            } else if (((TdtaLeuchtenCustomBean)l1).getLeuchtennummer() != null) {
                return 1;
            } else if (((TdtaLeuchtenCustomBean)l2).getLeuchtennummer() != null) {
                return -1;
            } else {
                return 1;
            }
        } else if (l1 != null) {
            return 1;
        } else if (l2 != null) {
            return -1;
        } else {
            return 0;
        }
    }
}
