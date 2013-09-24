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
package de.cismet.cids.custom.beans.belis;

import Sirius.navigator.connection.SessionManager;
import java.util.Collection;
import java.util.Date;

import de.cismet.belis.commons.constants.BelisMetaClassConstants;

import de.cismet.commons.server.entity.BaseEntity;
import java.util.Calendar;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class ArbeitsauftragCustomBean extends BaseEntity {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            ArbeitsauftragCustomBean.class);

    public static final String TABLE = BelisMetaClassConstants.MC_ARBEITSAUFTRAG;

    public static final String PROP__ANGELEGT_VON = "angelegt_von";
    public static final String PROP__ANGELEGT_AM = "angelegt_am";
    public static final String PROP__NUMMER = "nummer";
    public static final String PROP__N_PROTOKOLLE = "n_protokolle";
    public static final String PROP__N_VERANLASSUNGEN = "n_veranlassungen";

    private static final String[] PROPERTY_NAMES = new String[] {
            PROP__ID,
            PROP__ANGELEGT_VON,
            PROP__ANGELEGT_AM,
            PROP__NUMMER,
            PROP__N_PROTOKOLLE,
            PROP__N_VERANLASSUNGEN
        };

    //~ Instance fields --------------------------------------------------------

    private String angelegt_von;
    private Date angelegt_am;
    private Integer nummer;
    private Collection<ArbeitsprotokollCustomBean> n_protokolle;
    private Collection<VeranlassungCustomBean> n_veranlassungen;

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

    @Override
    public String toString() {
        return "de.cismet.belisEE.entity.Arbeitsauftrag[id=" + getId() + "]";
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
    public Integer getNummer() {
        return nummer;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  nummer  DOCUMENT ME!
     */
    public void setNummer(final Integer nummer) {
        final Integer old = this.nummer;
        this.nummer = nummer;
        this.propertyChangeSupport.firePropertyChange(PROP__NUMMER, old, this.nummer);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Collection<ArbeitsprotokollCustomBean> getN_protokolle() {
        return n_protokolle;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  n_protokolle  DOCUMENT ME!
     */
    public void setN_protokolle(final Collection<ArbeitsprotokollCustomBean> n_protokolle) {
        final Collection<ArbeitsprotokollCustomBean> old = this.n_protokolle;
        this.n_protokolle = n_protokolle;
        this.propertyChangeSupport.firePropertyChange(PROP__N_PROTOKOLLE, old, this.n_protokolle);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Collection<VeranlassungCustomBean> getN_veranlassungen() {
        return n_veranlassungen;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  n_veranlassungen  DOCUMENT ME!
     */
    public void setN_veranlassungen(final Collection<VeranlassungCustomBean> n_veranlassungen) {
        final Collection<VeranlassungCustomBean> old = this.n_veranlassungen;
        this.n_veranlassungen = n_veranlassungen;
        this.propertyChangeSupport.firePropertyChange(PROP__N_VERANLASSUNGEN, old, this.n_veranlassungen);
    }
}
