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

import de.cismet.cids.custom.beans.belis.AbzweigdoseCustomBean;
import de.cismet.cids.custom.beans.belis.LeitungCustomBean;
import de.cismet.cids.custom.beans.belis.MauerlascheCustomBean;
import de.cismet.cids.custom.beans.belis.SchaltstelleCustomBean;
import de.cismet.cids.custom.beans.belis.TdtaLeuchtenCustomBean;
import de.cismet.cids.custom.beans.belis.TdtaStandortMastCustomBean;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public class EntityComparator implements Comparator<BaseEntity>, Serializable {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(EntityComparator.class);

    //~ Instance fields --------------------------------------------------------

    private Comparator<TdtaLeuchtenCustomBean> leuchteComparator;

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
    public EntityComparator(final Comparator<TdtaLeuchtenCustomBean> leuchteComparator) {
        this.leuchteComparator = leuchteComparator;
    }

    //~ Methods ----------------------------------------------------------------

    // ToDo compare and equals of methods do not match fix it idea:
    // if equals is true -> return 0
    @Override
    public int compare(final BaseEntity o1, final BaseEntity o2) {
        final int result = compareImpl(o1, o2);
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
            if (o1 instanceof TdtaLeuchtenCustomBean) {
                if (o2 instanceof TdtaLeuchtenCustomBean) {
                    return leuchteComparator.compare((TdtaLeuchtenCustomBean)o1, (TdtaLeuchtenCustomBean)o2);
                } else {
                    return 1;
                }
            } else if (o1 instanceof TdtaStandortMastCustomBean) {
                if (o2 instanceof TdtaStandortMastCustomBean) {
                    if (o1.equals(o2)) {
                        return 0;
                    } else if (!((TdtaStandortMastCustomBean)o1).isStandortMast()) {
                        return -1;
                    } else if (!((TdtaStandortMastCustomBean)o2).isStandortMast()) {
                        return 1;
                    } else if ((((TdtaStandortMastCustomBean)o1).getLeuchten() != null)
                                && (((TdtaStandortMastCustomBean)o1).getLeuchten().size() == 0)) {
                        return -1;
                    } else if ((((TdtaStandortMastCustomBean)o2).getLeuchten() != null)
                                && (((TdtaStandortMastCustomBean)o2).getLeuchten().size() == 0)) {
                        return 1;
                    } else if ((((TdtaStandortMastCustomBean)o1).getStrassenschluessel() != null)
                                && (((TdtaStandortMastCustomBean)o2).getStrassenschluessel() != null)) {
                        int result = CriteriaStringComparator.getInstance()
                                    .compare(((TdtaStandortMastCustomBean)o1).getStrassenschluessel(),
                                        ((TdtaStandortMastCustomBean)o2).getStrassenschluessel());
                        // ((TdtaStandortMastCustomBean)
                        // o1).getLaufendeNummer().compareTo(((TdtaStandortMastCustomBean) o2).getLaufendeNummer());
                        if (result == 0) {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("Strassen sind gleich");
                            }
                            if ((((TdtaStandortMastCustomBean)o1).getKennziffer() != null)
                                        && (((TdtaStandortMastCustomBean)o2).getKennziffer() != null)) {
                                result = CriteriaStringComparator.getInstance()
                                            .compare(((TdtaStandortMastCustomBean)o1).getKennziffer(),
                                                    ((TdtaStandortMastCustomBean)o2).getKennziffer());
                                if (result == 0) {
                                    if (LOG.isDebugEnabled()) {
                                        LOG.debug("Kennziffern sind gleich");
                                    }
                                    if ((((TdtaStandortMastCustomBean)o1).getLaufendeNummer() != null)
                                                && (((TdtaStandortMastCustomBean)o2).getLaufendeNummer() != null)) {
                                        result = ((TdtaStandortMastCustomBean)o1).getLaufendeNummer()
                                                    .compareTo(((TdtaStandortMastCustomBean)o2).getLaufendeNummer());
                                        if (result == 0) {
                                            if (LOG.isDebugEnabled()) {
                                                LOG.debug("laufende Nummern sind gleich");
                                                LOG.debug("the entity fields are equals odering dosen't matter");
                                            }
                                            return 1;
                                        } else {
                                            if (LOG.isDebugEnabled()) {
                                                LOG.debug("laufende Nummern sind ungleich");
                                            }
                                            return result;
                                        }
                                    } else if (((TdtaStandortMastCustomBean)o1).getLaufendeNummer() != null) {
                                        return 1;
                                    } else if (((TdtaStandortMastCustomBean)o2).getLaufendeNummer() != null) {
                                        return -1;
                                    } else {
                                        return -1;
                                    }
                                } else {
                                    if (LOG.isDebugEnabled()) {
                                        LOG.debug("Kennziffern sind ungleich");
                                    }
                                    return result;
                                }
                            } else if (((TdtaStandortMastCustomBean)o1).getKennziffer() != null) {
                                return 1;
                            } else if (((TdtaStandortMastCustomBean)o2).getKennziffer() != null) {
                                return -1;
                            } else {
                                return -1;
                            }
                        } else {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("Strassen sind ungleich");
                            }
                            return result;
                        }
                    } else if (((TdtaStandortMastCustomBean)o1).getStrassenschluessel() != null) {
                        return 1;
                    } else if (((TdtaStandortMastCustomBean)o2).getStrassenschluessel() != null) {
                        return -1;
                    } else {
                        return -1;
                    }
                } else if (o2 instanceof TdtaLeuchtenCustomBean) {
                    return -1;
                } else {
                    return 1;
                }
            } else if (o1 instanceof LeitungCustomBean) {
                if (o2 instanceof LeitungCustomBean) {
                    if (o1.equals(o2)) {
                        return 0;
                    } else {
                        return 1;
                    }
                } else if ((o2 instanceof TdtaStandortMastCustomBean) || (o2 instanceof TdtaLeuchtenCustomBean)) {
                    return -1;
                } else {
                    return 1;
                }
            } else if (o1 instanceof SchaltstelleCustomBean) {
                if (o2 instanceof SchaltstelleCustomBean) {
                    if (o1.equals(o2)) {
                        return 0;
                    } else {
                        return 1;
                    }
                } else if ((o2 instanceof TdtaStandortMastCustomBean) || (o2 instanceof TdtaLeuchtenCustomBean)
                            || (o2 instanceof LeitungCustomBean)) {
                    return -1;
                } else {
                    return 1;
                }
            } else if (o1 instanceof MauerlascheCustomBean) {
                if (o2 instanceof MauerlascheCustomBean) {
                    if (o1.equals(o2)) {
                        return 0;
                    } else {
                        return 1;
                    }
                } else if ((o2 instanceof TdtaStandortMastCustomBean) || (o2 instanceof TdtaLeuchtenCustomBean)
                            || (o2 instanceof LeitungCustomBean)
                            || (o2 instanceof SchaltstelleCustomBean)) {
                    return -1;
                } else {
                    return 1;
                }
            } else if (o1 instanceof AbzweigdoseCustomBean) {
                if (o2 instanceof AbzweigdoseCustomBean) {
                    if (o1.equals(o2)) {
                        return 0;
                    } else {
                        return 1;
                    }
                } else if ((o2 instanceof TdtaStandortMastCustomBean) || (o2 instanceof TdtaLeuchtenCustomBean)
                            || (o2 instanceof LeitungCustomBean)
                            || (o2 instanceof SchaltstelleCustomBean)
                            || (o2 instanceof MauerlascheCustomBean)) {
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
