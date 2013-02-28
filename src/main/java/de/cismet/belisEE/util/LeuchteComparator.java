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

import de.cismet.belisEEold.entity.Leuchte;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public class LeuchteComparator implements Comparator<Leuchte>, Serializable {

    //~ Methods ----------------------------------------------------------------

    @Override
    public int compare(final Leuchte l1, final Leuchte l2) {
        if ((l1 != null) && (l2 != null)) {
            if (l1.equals(l2)) {
                return 0;
            } else if ((((Leuchte)l1).getLeuchtennummer() != null) && (((Leuchte)l2).getLeuchtennummer() != null)) {
                System.out.println("l1: " + ((Leuchte)l1).getLeuchtennummer() + " l2: "
                            + ((Leuchte)l2).getLeuchtennummer());
                if (((Leuchte)l1).getLeuchtennummer() == ((Leuchte)l2).getLeuchtennummer()) {
                    System.out.println("Leuchtennummer are equal");
                    return 1;
                } else if (((Leuchte)l1).getLeuchtennummer() > ((Leuchte)l2).getLeuchtennummer()) {
                    System.out.println("l1 Leuchtennummer greater l2 leuchtennummer");
                    return 1;
                } else {
                    System.out.println("l2 Leuchtennummer greater l1 Leuchtennummer");
                    return -1;
                }
            } else if (((Leuchte)l1).getLeuchtennummer() != null) {
                return 1;
            } else if (((Leuchte)l2).getLeuchtennummer() != null) {
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
