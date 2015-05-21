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

import de.cismet.cids.custom.tostringconverter.belis2.TkeyStrassenschluesselToStringConverter;

import de.cismet.commons.server.entity.BaseEntity;

import de.cismet.tools.gui.jtable.sorting.AlphanumComparator;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class TkeyStrassenschluesselCustomBean extends BaseEntity implements Comparable<BaseEntity> {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            TkeyStrassenschluesselCustomBean.class);

    public static final String TABLE = "tkey_strassenschluessel";

    public static final String PROP__STRASSE = "strasse";
    public static final String PROP__PK = "pk";

    //~ Instance fields --------------------------------------------------------

    private String strasse;
    private String pk;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbzweigdoseCustomBean object.
     */
    public TkeyStrassenschluesselCustomBean() {
        addPropertyNames(
            new String[] {
                PROP__STRASSE,
                PROP__PK
            });
    }

    /**
     * Creates a new TkeyStrassenschluesselCustomBean object.
     *
     * @param  pk  DOCUMENT ME!
     */
    public TkeyStrassenschluesselCustomBean(final String pk) {
        setPk(pk);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static TkeyStrassenschluesselCustomBean createNew() {
        return (TkeyStrassenschluesselCustomBean)createNew(TABLE);
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
    public String getStrasse() {
        return strasse;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  strasse  DOCUMENT ME!
     */
    public void setStrasse(final String strasse) {
        final String old = this.strasse;
        this.strasse = strasse;
        this.propertyChangeSupport.firePropertyChange(PROP__STRASSE, old, this.strasse);
    }

    @Override
    public String toString() {
        return new TkeyStrassenschluesselToStringConverter().convert(metaObject);
    }

    @Override
    public int compareTo(final BaseEntity o) {
        if (o instanceof TkeyStrassenschluesselCustomBean) {
            return AlphanumComparator.getInstance().compare(this.pk, ((TkeyStrassenschluesselCustomBean)o).getPk());
        } else {
            return 1;
        }
    }
}
