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

import de.cismet.tools.gui.jtable.sorting.AlphanumComparator;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class TkeyKennzifferCustomBean extends BaseEntity implements Comparable<TkeyKennzifferCustomBean> {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            TkeyKennzifferCustomBean.class);

    public static final String TABLE = "tkey_kennziffer";

    public static final String PROP__BESCHREIBUNG = "beschreibung";
    public static final String PROP__KENNZIFFER = "kennziffer";

    //~ Instance fields --------------------------------------------------------

    private String beschreibung;
    private Integer kennziffer;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbzweigdoseCustomBean object.
     */
    public TkeyKennzifferCustomBean() {
        addPropertyNames(
            new String[] {
                PROP__BESCHREIBUNG,
                PROP__KENNZIFFER
            });
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static TkeyKennzifferCustomBean createNew() {
        return (TkeyKennzifferCustomBean)createNew(TABLE);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   kennziffer  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static TkeyKennzifferCustomBean createNew(final Integer kennziffer) {
        final TkeyKennzifferCustomBean newBean = createNew();
        newBean.setKennziffer(kennziffer);
        return newBean;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getKennziffer() {
        return kennziffer;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  kennziffer  DOCUMENT ME!
     */
    public void setKennziffer(final Integer kennziffer) {
        final Integer old = this.kennziffer;
        this.kennziffer = kennziffer;
        this.propertyChangeSupport.firePropertyChange(PROP__KENNZIFFER, old, this.kennziffer);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getBeschreibung() {
        return beschreibung;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  beschreibung  DOCUMENT ME!
     */
    public void setBeschreibung(final String beschreibung) {
        final String old = this.beschreibung;
        this.beschreibung = beschreibung;
        this.propertyChangeSupport.firePropertyChange(PROP__BESCHREIBUNG, old, this.beschreibung);
    }

    @Override
    public int compareTo(final TkeyKennzifferCustomBean o) {
        if ((o != null)) {
            return AlphanumComparator.getInstance().compare(getKennziffer(), o.getKennziffer());
        } else {
            return 1;
        }
    }
}
