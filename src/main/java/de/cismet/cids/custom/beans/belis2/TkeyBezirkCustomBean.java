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

import de.cismet.cids.custom.tostringconverter.belis2.TkeyBezirkToStringConverter;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class TkeyBezirkCustomBean extends BaseEntity {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(TkeyBezirkCustomBean.class);

    public static final String TABLE = "tkey_bezirk";

    public static final String PROP__BEZIRK = "bezirk";
    public static final String PROP__PK = "pk";

    //~ Instance fields --------------------------------------------------------

    private String bezirk;
    private Integer pk;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbzweigdoseCustomBean object.
     */
    public TkeyBezirkCustomBean() {
        addPropertyNames(
            new String[] {
                PROP__BEZIRK,
                PROP__PK
            });
    }

    /**
     * Creates a new TkeyBezirkCustomBean object.
     *
     * @param  pk  DOCUMENT ME!
     */
    public TkeyBezirkCustomBean(final Integer pk) {
        setPk(pk);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static TkeyBezirkCustomBean createNew() {
        return (TkeyBezirkCustomBean)createNew(TABLE);
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
    public String getBezirk() {
        return bezirk;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  bezirk  DOCUMENT ME!
     */
    public void setBezirk(final String bezirk) {
        final String old = this.bezirk;
        this.bezirk = bezirk;
        this.propertyChangeSupport.firePropertyChange(PROP__BEZIRK, old, this.bezirk);
    }

//    @Override
//    public String getCompareCriteriaString() {
//        // Workaround: 'BISHER NICHT ZUGEORDNET' is ordered on the first place
//        if (getPk().equals(10)) {
//            return "00000";
//        } else {
//            return getKeyString();
//        }
//    }
}
