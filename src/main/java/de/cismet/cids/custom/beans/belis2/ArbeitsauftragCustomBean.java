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
package de.cismet.cids.custom.beans.belis2;

import Sirius.navigator.connection.SessionManager;

import java.sql.Date;

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import de.cismet.belis.broker.CidsBroker;

import de.cismet.belis.commons.constants.BelisMetaClassConstants;

import de.cismet.belis2.server.search.NextArbeitsauftragNummerSearch;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.commons.server.entity.BaseEntity;
import de.cismet.commons.server.interfaces.DocumentContainer;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class ArbeitsauftragCustomBean extends BaseEntity implements DocumentContainer {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            ArbeitsauftragCustomBean.class);

    public static final String TABLE = BelisMetaClassConstants.MC_ARBEITSAUFTRAG;

    public static final String PROP__ANGELEGT_VON = "angelegt_von";
    public static final String PROP__ANGELEGT_AM = "angelegt_am";
    public static final String PROP__NUMMER = "nummer";
    public static final String PROP__AR_PROTOKOLLE = "ar_protokolle";
    public static final String PROP__ZUGEWIESEN_AN = "zugewiesen_an";

    private static final String[] PROPERTY_NAMES = new String[] {
            PROP__ID,
            PROP__ANGELEGT_VON,
            PROP__ANGELEGT_AM,
            PROP__NUMMER,
            PROP__AR_PROTOKOLLE,
            PROP__ZUGEWIESEN_AN
        };

    //~ Instance fields --------------------------------------------------------

    private String angelegt_von;
    private String zugewiesen_an;
    private Date angelegt_am;
    private String nummer;
    private Collection<ArbeitsprotokollCustomBean> ar_protokolle;
    private Collection<DmsUrlCustomBean> ar_dokumente = new ArrayList<DmsUrlCustomBean>();

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new VeranlassungCustomBean object.
     */
    public ArbeitsauftragCustomBean() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static ArbeitsauftragCustomBean createNew() {
        final ArbeitsauftragCustomBean arbeitsauftragCustomBean = (ArbeitsauftragCustomBean)createNew(TABLE);
        arbeitsauftragCustomBean.setAngelegt_am(new Date(Calendar.getInstance().getTime().getTime()));
        arbeitsauftragCustomBean.setAngelegt_von(SessionManager.getSession().getUser().getName());

        try {
            final List<Long> nextNumber = (List<Long>)CidsBroker.getInstance()
                        .executeServerSearch(new NextArbeitsauftragNummerSearch());

            final Long number = (nextNumber.isEmpty()) ? null : nextNumber.get(0);
            final DecimalFormat df = new DecimalFormat("00000000");
            arbeitsauftragCustomBean.setNummer(df.format(number));
        } catch (final Exception ex) {
            LOG.error("", ex);
        }

        return arbeitsauftragCustomBean;
    }

    @Override
    public String[] getPropertyNames() {
        return PROPERTY_NAMES;
    }

    @Override
    public int hashCode() {
        if (this.getId() == null) {
            return System.identityHashCode(this);
        }
        return this.getId().hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof ArbeitsauftragCustomBean) {
            final ArbeitsauftragCustomBean anEntity = (ArbeitsauftragCustomBean)other;
            if (this == other) {
                return true;
            } else if (!this.getClass().isAssignableFrom(other.getClass())) {
                return false;
            } else if ((this.getId() == null) || (anEntity.getId() == null)) {
                return false;
            } else {
                return this.getId().equals(anEntity.getId());
            }
        } else {
            return false;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getAngelegt_von() {
        return angelegt_von;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  angelegt_von  DOCUMENT ME!
     */
    public void setAngelegt_von(final String angelegt_von) {
        final String old = this.angelegt_von;
        this.angelegt_von = angelegt_von;
        this.propertyChangeSupport.firePropertyChange(PROP__ANGELEGT_VON, old, this.angelegt_von);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Date getAngelegt_am() {
        return angelegt_am;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  angelegt_am  DOCUMENT ME!
     */
    public void setAngelegt_am(final Date angelegt_am) {
        final Date old = this.angelegt_am;
        this.angelegt_am = angelegt_am;
        this.propertyChangeSupport.firePropertyChange(PROP__ANGELEGT_AM, old, this.angelegt_am);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getZugewiesen_an() {
        return zugewiesen_an;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  zugewiesen_an  nummer DOCUMENT ME!
     */
    public void setZugewiesen_an(final String zugewiesen_an) {
        final String old = this.zugewiesen_an;
        this.zugewiesen_an = zugewiesen_an;
        this.propertyChangeSupport.firePropertyChange(PROP__ZUGEWIESEN_AN, old, this.zugewiesen_an);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getNummer() {
        return nummer;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  nummer  DOCUMENT ME!
     */
    public void setNummer(final String nummer) {
        final String old = this.nummer;
        this.nummer = nummer;
        this.propertyChangeSupport.firePropertyChange(PROP__NUMMER, old, this.nummer);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Collection<ArbeitsprotokollCustomBean> getAr_protokolle() {
        return ar_protokolle;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  ar_protokolle  DOCUMENT ME!
     */
    public void setAr_protokolle(final Collection<ArbeitsprotokollCustomBean> ar_protokolle) {
        final Collection<ArbeitsprotokollCustomBean> old = this.ar_protokolle;
        this.ar_protokolle = ar_protokolle;
        this.propertyChangeSupport.firePropertyChange(PROP__AR_PROTOKOLLE, old, this.ar_protokolle);
    }

    @Override
    public CidsBean persist() throws Exception {
//        determineNextNummer();
        LOG.fatal(this.getMOString());
        return super.persist();
    }

//    /**
//     * DOCUMENT ME!
//     *
//     * @param   minimalNumber  DOCUMENT ME!
//     *
//     * @throws  ActionNotSuccessfulException  DOCUMENT ME!
//     */
//    private void determineNextNummer() throws ActionNotSuccessfulException {
//        if (LOG.isDebugEnabled()) {
//            LOG.debug("determine next laufendenummer");
//        }
//        try {
//            final List<Integer> highestNumbers = (List<Integer>)CidsBroker.getInstance()
//                        .executeServerSearch(new HighestLfdNummerSearch(
//                                    strassenschluessel,
//                                    kennziffer));
//
//            final Integer highestNumber = (highestNumbers.isEmpty()) ? null : highestNumbers.get(0);
//            if ((highestNumber == null)) {
//                setNummer(0);
//            } else {
//                setNummer(highestNumber + 1);
//            }
//        } catch (Exception ex) {
//            if (LOG.isDebugEnabled()) {
//                LOG.debug("Error while querying entity", ex);
//            }
//            throw new ActionNotSuccessfulException("Error while querying highest nummer", ex);
//        }
//    }

    @Override
    public Collection<DmsUrlCustomBean> getDokumente() {
        return ar_dokumente;
    }

    @Override
    public void setDokumente(final Collection<DmsUrlCustomBean> ar_dokumente) {
        this.ar_dokumente = ar_dokumente;
    }

    @Override
    public String getKeyString() {
        final Collection<String> strings = new ArrayList<String>();
        strings.add("A");
        if (getNummer() != null) {
            strings.add(getNummer());
        }
        return implode(strings.toArray(new String[0]), "");
    }
}
