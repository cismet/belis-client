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

import de.cismet.belisEE.entity.Abzweigdose;
import de.cismet.belisEE.entity.Leitung;
import de.cismet.belisEE.entity.Leuchte;
import de.cismet.belisEE.entity.Mauerlasche;
import de.cismet.belisEE.entity.Schaltstelle;
import de.cismet.belisEE.entity.Standort;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public class EntityComparator implements Comparator<BaseEntity>, Serializable {

    //~ Instance fields --------------------------------------------------------

    private Comparator<Leuchte> leuchteComparator;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new EntityComparator object.
     */
    public EntityComparator() {
    }

    /**
     * Creates a new EntityComparator object.
     *
     * @param  leuchteComparator  DOCUMENT ME!
     */
    public EntityComparator(final Comparator<Leuchte> leuchteComparator) {
        this.leuchteComparator = leuchteComparator;
    }

    //~ Methods ----------------------------------------------------------------

    // ToDo compare and equals of methods do not match fix it idea:
    // if equals is true -> return 0
    @Override
    public int compare(final BaseEntity o1, final BaseEntity o2) {
//        System.out.println("compare called: o1: "+o1+" o2: "+o2);
//        if(o1 != null && o1 instanceof Standort){
//            System.out.println(("o1 istStandortMast: ")+((Standort)o1).isStandortMast());
//            System.out.println("o1 has leuchten"+(((Standort)o1).getLeuchten() != null && ((Standort)o1).getLeuchten().size() > 0));
//        }
//        if(o2 != null && o2 instanceof Standort){
//            System.out.println(("o2 istStandortMast: ")+((Standort)o2).isStandortMast());
//            System.out.println("o2 has leuchten"+(((Standort)o2).getLeuchten() != null && ((Standort)o2).getLeuchten().size() > 0));
//        }
        final int result = compareImpl(o1, o2);
        // System.out.println("result: "+result);
        return result;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   o1  DOCUMENT ME!
     * @param   o2  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private int compareImpl(final BaseEntity o1, final BaseEntity o2) {
        if ((o1 != null) && (o2 != null)) {
            if (o1 instanceof Leuchte) {
                if (o2 instanceof Leuchte) {
                    return leuchteComparator.compare((Leuchte)o1, (Leuchte)o2);
                } else {
                    return 1;
                }
            } else if (o1 instanceof Standort) {
                if (o2 instanceof Standort) {
                    if (o1.equals(o2)) {
                        return 0;
                    } else if (!((Standort)o1).isStandortMast()) {
                        return -1;
                    } else if (!((Standort)o2).isStandortMast()) {
                        return 1;
                    } else if ((((Standort)o1).getLeuchten() != null) && (((Standort)o1).getLeuchten().size() == 0)) {
                        return -1;
                    } else if ((((Standort)o2).getLeuchten() != null) && (((Standort)o2).getLeuchten().size() == 0)) {
                        return 1;
                    } else if ((((Standort)o1).getStrassenschluessel() != null)
                                && (((Standort)o2).getStrassenschluessel() != null)) {
                        int result = CriteriaStringComparator.getInstance()
                                    .compare(((Standort)o1).getStrassenschluessel(),
                                        ((Standort)o2).getStrassenschluessel());
                        // ((Standort) o1).getLaufendeNummer().compareTo(((Standort) o2).getLaufendeNummer());
                        if (result == 0) {
                            System.out.println("Strassen sind gleich");
                            if ((((Standort)o1).getKennziffer() != null) && (((Standort)o2).getKennziffer() != null)) {
                                result = CriteriaStringComparator.getInstance()
                                            .compare(((Standort)o1).getKennziffer(), ((Standort)o2).getKennziffer());
                                if (result == 0) {
                                    System.out.println("Kennziffern sind gleich");
                                    if ((((Standort)o1).getLaufendeNummer() != null)
                                                && (((Standort)o2).getLaufendeNummer() != null)) {
                                        result = ((Standort)o1).getLaufendeNummer()
                                                    .compareTo(((Standort)o2).getLaufendeNummer());
                                        if (result == 0) {
                                            System.out.println("laufende Nummern sind gleich");
                                            System.out.println("the entity fields are equals odering dosen't matter");
                                            return 1;
                                        } else {
                                            System.out.println("laufende Nummern sind ungleich");
                                            return result;
                                        }
                                    } else if (((Standort)o1).getLaufendeNummer() != null) {
                                        return 1;
                                    } else if (((Standort)o2).getLaufendeNummer() != null) {
                                        return -1;
                                    } else {
                                        return -1;
                                    }
                                } else {
                                    System.out.println("Kennziffern sind ungleich");
                                    return result;
                                }
                            } else if (((Standort)o1).getKennziffer() != null) {
                                return 1;
                            } else if (((Standort)o2).getKennziffer() != null) {
                                return -1;
                            } else {
                                return -1;
                            }
                        } else {
                            System.out.println("Strassen sind ungleich");
                            return result;
                        }
                    } else if (((Standort)o1).getStrassenschluessel() != null) {
                        return 1;
                    } else if (((Standort)o2).getStrassenschluessel() != null) {
                        return -1;
                    } else {
                        return -1;
                    }
                } else if (o2 instanceof Leuchte) {
                    return -1;
                } else {
                    return 1;
                }
            } else if (o1 instanceof Leitung) {
                if (o2 instanceof Leitung) {
                    if (o1.equals(o2)) {
                        return 0;
                    } else {
                        return 1;
                    }
                } else if ((o2 instanceof Standort) || (o2 instanceof Leuchte)) {
                    return -1;
                } else {
                    return 1;
                }
            } else if (o1 instanceof Schaltstelle) {
                if (o2 instanceof Schaltstelle) {
                    if (o1.equals(o2)) {
                        return 0;
                    } else {
                        return 1;
                    }
                } else if ((o2 instanceof Standort) || (o2 instanceof Leuchte) || (o2 instanceof Leitung)) {
                    return -1;
                } else {
                    return 1;
                }
            } else if (o1 instanceof Mauerlasche) {
                if (o2 instanceof Mauerlasche) {
                    if (o1.equals(o2)) {
                        return 0;
                    } else {
                        return 1;
                    }
                } else if ((o2 instanceof Standort) || (o2 instanceof Leuchte) || (o2 instanceof Leitung)
                            || (o2 instanceof Schaltstelle)) {
                    return -1;
                } else {
                    return 1;
                }
            } else if (o1 instanceof Abzweigdose) {
                if (o2 instanceof Abzweigdose) {
                    if (o1.equals(o2)) {
                        return 0;
                    } else {
                        return 1;
                    }
                } else if ((o2 instanceof Standort) || (o2 instanceof Leuchte) || (o2 instanceof Leitung)
                            || (o2 instanceof Schaltstelle)
                            || (o2 instanceof Mauerlasche)) {
                    return -1;
                } else {
                    return 1;
                }
            } else {
                if (o1.equals(o2)) {
                    return 0;
                } else {
                    // if the case is not written down the order is does not matter.
                    return 1;
                }
            }
        } else if (o1 != null) {
            return 1;
        } else if (o2 != null) {
            return -1;
        } else {
            return 0;
        }
    }
}
