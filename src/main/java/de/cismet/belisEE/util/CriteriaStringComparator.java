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

import java.util.Comparator;

import de.cismet.commons.server.entity.BaseEntity;

import de.cismet.tools.gui.jtable.sorting.AlphanumComparator;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
//ToDo move to BaseEntityClass
public class CriteriaStringComparator implements Comparator<BaseEntity> {

    //~ Static fields/initializers ---------------------------------------------

    private static Comparator instance;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new CriteriaStringComparator object.
     */
    private CriteriaStringComparator() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static Comparator getInstance() {
        if (CriteriaStringComparator.instance == null) {
            synchronized (CriteriaStringComparator.class) {
                if (CriteriaStringComparator.instance == null) {
                    CriteriaStringComparator.instance = new CriteriaStringComparator();
                }
            }
        }
        return instance;
    }

    @Override
    public int compare(final BaseEntity o1, final BaseEntity o2) {
        if ((o1 != null) && (o2 != null)) {
            return AlphanumComparator.getInstance()
                        .compare(o1.getCompareCriteriaString(), o2.getCompareCriteriaString());
        } else if (o1 != null) {
            return 1;
        } else if (o2 != null) {
            return -1;
        } else {
            return 0;
        }
    }
}
