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

import java.util.Locale;

import de.cismet.cids.custom.tostringconverter.belis2.TkeyUnterhMastToStringConverter;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class TkeyUnterhMastCustomBean extends BaseEntity {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            TkeyUnterhMastCustomBean.class);

    public static final String TABLE = "tkey_unterh_mast";

    public static final String PROP__UNTERHALT_MAST = "unterhalt_mast";
    public static final String PROP__PK = "pk";

    //~ Instance fields --------------------------------------------------------

    private String unterhalt_mast;
    private Integer pk;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbzweigdoseCustomBean object.
     */
    public TkeyUnterhMastCustomBean() {
        addPropertyNames(
            new String[] {
                PROP__UNTERHALT_MAST,
                PROP__PK
            });
    }

    /**
     * Creates a new TkeyUnterhMastCustomBean object.
     *
     * @param  pk  DOCUMENT ME!
     */
    public TkeyUnterhMastCustomBean(final Integer pk) {
        setPk(pk);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static TkeyUnterhMastCustomBean createNew() {
        return (TkeyUnterhMastCustomBean)createNew(TABLE);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getPk() {
        return pk;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  pk  DOCUMENT ME!
     */
    public void setPk(final Integer pk) {
        final Integer old = this.pk;
        this.pk = pk;
        this.propertyChangeSupport.firePropertyChange(PROP__PK, old, this.pk);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getUnterhalt_mast() {
        return unterhalt_mast;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  Unterhalt_Mast  DOCUMENT ME!
     */
    public void setUnterhalt_mast(final String Unterhalt_Mast) {
        final String old = this.unterhalt_mast;
        this.unterhalt_mast = Unterhalt_Mast;
        this.propertyChangeSupport.firePropertyChange(PROP__UNTERHALT_MAST, old, this.unterhalt_mast);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getUnterhaltMast() {
        return getUnterhalt_mast();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  unterhaltMast  DOCUMENT ME!
     */
    public void setUnterhaltMast(final String unterhaltMast) {
        setUnterhalt_mast(unterhaltMast);
    }

    @Override
    public String toString() {
        return new TkeyUnterhMastToStringConverter().convert(metaObject);
    }
}
