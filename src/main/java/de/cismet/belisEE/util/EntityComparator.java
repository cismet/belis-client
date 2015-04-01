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

import de.cismet.cids.custom.beans.belis2.AbzweigdoseCustomBean;
import de.cismet.cids.custom.beans.belis2.ArbeitsauftragCustomBean;
import de.cismet.cids.custom.beans.belis2.ArbeitsprotokollCustomBean;
import de.cismet.cids.custom.beans.belis2.LeitungCustomBean;
import de.cismet.cids.custom.beans.belis2.MauerlascheCustomBean;
import de.cismet.cids.custom.beans.belis2.SchaltstelleCustomBean;
import de.cismet.cids.custom.beans.belis2.TdtaLeuchtenCustomBean;
import de.cismet.cids.custom.beans.belis2.TdtaStandortMastCustomBean;
import de.cismet.cids.custom.beans.belis2.VeranlassungCustomBean;

import de.cismet.commons.server.entity.WorkbenchEntity;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public class EntityComparator implements Comparator<WorkbenchEntity>, Serializable {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(EntityComparator.class);

    //~ Methods ----------------------------------------------------------------

    @Override
    public int compare(final WorkbenchEntity o1, final WorkbenchEntity o2) {
        if ((o1 != null) && (o2 != null)) {
            if (o1.equals(o2)) {
                return 0;
            } else {
                return o1.compareTo(o2);
            }
        } else if (o1 != null) {
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   o1  DOCUMENT ME!
     * @param   o2  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static int compareTypes(final WorkbenchEntity o1, final WorkbenchEntity o2) {
        int compare = 0;
        compare += (o1 instanceof VeranlassungCustomBean) ? 9 : 0;
        compare += (o1 instanceof ArbeitsauftragCustomBean) ? 8 : 0;
        compare += (o1 instanceof ArbeitsprotokollCustomBean) ? 7 : 0;
        compare += (o1 instanceof TdtaStandortMastCustomBean) ? 6 : 0;
        compare += (o1 instanceof TdtaLeuchtenCustomBean) ? 5 : 0;
        compare += (o1 instanceof SchaltstelleCustomBean) ? 4 : 0;
        compare += (o1 instanceof MauerlascheCustomBean) ? 3 : 0;
        compare += (o1 instanceof LeitungCustomBean) ? 2 : 0;
        compare += (o1 instanceof AbzweigdoseCustomBean) ? 1 : 0;

        compare -= (o2 instanceof VeranlassungCustomBean) ? 9 : 0;
        compare -= (o2 instanceof ArbeitsauftragCustomBean) ? 8 : 0;
        compare += (o1 instanceof ArbeitsprotokollCustomBean) ? 7 : 0;
        compare -= (o2 instanceof TdtaStandortMastCustomBean) ? 6 : 0;
        compare -= (o2 instanceof TdtaLeuchtenCustomBean) ? 5 : 0;
        compare -= (o2 instanceof SchaltstelleCustomBean) ? 4 : 0;
        compare -= (o2 instanceof MauerlascheCustomBean) ? 3 : 0;
        compare -= (o2 instanceof LeitungCustomBean) ? 2 : 0;
        compare -= (o2 instanceof AbzweigdoseCustomBean) ? 1 : 0;

        return (compare == 0) ? 1 : compare;
    }
}
