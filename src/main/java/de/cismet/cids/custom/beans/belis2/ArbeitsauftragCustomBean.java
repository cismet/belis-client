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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.cismet.belis.broker.CidsBroker;

import de.cismet.belis.commons.constants.BelisMetaClassConstants;

import de.cismet.belis2.server.search.NextArbeitsauftragNummerSearch;

import de.cismet.belisEE.util.EntityComparator;

import de.cismet.commons.server.entity.BaseEntity;
import de.cismet.commons.server.interfaces.DocumentContainer;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class ArbeitsauftragCustomBean extends BaseEntity implements DocumentContainer, WorkbenchEntity {

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
     * @return  DOCUMENT ME!
     */
    public List<ArbeitsprotokollCustomBean> getSortedProtokolle() {
        final List<ArbeitsprotokollCustomBean> sorted = new ArrayList<ArbeitsprotokollCustomBean>(ar_protokolle);
        Collections.sort((List<ArbeitsprotokollCustomBean>)sorted,
            new Comparator<ArbeitsprotokollCustomBean>() {

                @Override
                public int compare(final ArbeitsprotokollCustomBean o1, final ArbeitsprotokollCustomBean o2) {
                    if ((o1.getProtokollnummer() != null) && (o2.getProtokollnummer() != null)) {
                        return o1.getProtokollnummer().compareTo(o2.getProtokollnummer());
                    } else if (o1.getProtokollnummer() != null) {
                        return 1;
                    } else if (o2.getProtokollnummer() != null) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
        return sorted;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  ar_protokolle  DOCUMENT ME!
     */
    public void setAr_protokolle(final Collection<ArbeitsprotokollCustomBean> ar_protokolle) {
        final Collection<ArbeitsprotokollCustomBean> old = this.ar_protokolle;
        this.ar_protokolle = ar_protokolle;
        final List<ArbeitsprotokollCustomBean> sortedProtokolle = getSortedProtokolle();
        for (int index = 0; index < sortedProtokolle.size(); index++) {
            final ArbeitsprotokollCustomBean protokoll = sortedProtokolle.get(index);
            if (protokoll.getProtokollnummer() == null) {
                protokoll.setProtokollnummer(index + 1);
            }
        }
        this.propertyChangeSupport.firePropertyChange(PROP__AR_PROTOKOLLE, old, this.ar_protokolle);
    }

    @Override
    public Collection<DmsUrlCustomBean> getDokumente() {
        return ar_dokumente;
    }

    @Override
    public void setDokumente(final Collection<DmsUrlCustomBean> ar_dokumente) {
        this.ar_dokumente = ar_dokumente;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   o  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public int compareTo(final WorkbenchEntity o) {
        if (o instanceof ArbeitsauftragCustomBean) {
            final ArbeitsauftragCustomBean a = (ArbeitsauftragCustomBean)o;
            final int result = getKeyString().compareTo(a.getKeyString()) * -1;
            return (result == 0) ? 1 : result;
        } else {
            return EntityComparator.compareTypes(this, o);
        }
    }

    @Override
    public String getHumanReadablePosition() {
        return "";
    }

    @Override
    public String getKeyString() {
        final Collection<String> strings = new ArrayList<String>();
        strings.add("A");
        if (getNummer() != null) {
            strings.add(getNummer());
        }
        return CidsBroker.implode(strings.toArray(new String[0]), "");
    }
}
