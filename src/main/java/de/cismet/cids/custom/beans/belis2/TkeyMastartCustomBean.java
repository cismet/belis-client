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

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class TkeyMastartCustomBean extends BaseEntity {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(TkeyMastartCustomBean.class);

    public static final String TABLE = "tkey_mastart";

    public static final String PROP__MASTART = "mastart";
    public static final String PROP__PK = "pk";

    //~ Instance fields --------------------------------------------------------

    private String mastart;
    private String pk;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbzweigdoseCustomBean object.
     */
    public TkeyMastartCustomBean() {
        addPropertyNames(
            new String[] {
                PROP__MASTART,
                PROP__PK
            });
    }

    /**
     * Creates a new TkeyMastartCustomBean object.
     *
     * @param  pk  DOCUMENT ME!
     */
    public TkeyMastartCustomBean(final String pk) {
        setPk(pk);
    }

    /**
     * Creates a new TkeyMastartCustomBean object.
     *
     * @param  pk       DOCUMENT ME!
     * @param  mastart  DOCUMENT ME!
     */
    public TkeyMastartCustomBean(final String pk, final String mastart) {
        setPk(pk);
        setMastart(mastart);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static TkeyMastartCustomBean createNew() {
        return (TkeyMastartCustomBean)createNew(TABLE);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getPk() {
        return pk;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  pk  DOCUMENT ME!
     */
    public void setPk(final String pk) {
        final String old = this.pk;
        this.pk = pk;
        this.propertyChangeSupport.firePropertyChange(PROP__PK, old, this.pk);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getMastart() {
        return mastart;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  mastart  DOCUMENT ME!
     */
    public void setMastart(final String mastart) {
        final String old = this.mastart;
        this.mastart = mastart;
        this.propertyChangeSupport.firePropertyChange(PROP__MASTART, old, this.mastart);
    }
}
