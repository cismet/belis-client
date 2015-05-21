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

import de.cismet.cids.custom.tostringconverter.belis2.TkeyKlassifizierungToStringConverter;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class TkeyKlassifizierungCustomBean extends BaseEntity {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            TkeyKlassifizierungCustomBean.class);

    public static final String TABLE = "tkey_klassifizierung";

    public static final String PROP__KLASSIFIZIERUNG = "klassifizierung";
    public static final String PROP__PK = "pk";

    //~ Instance fields --------------------------------------------------------

    private String klassifizierung;
    private Integer pk;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbzweigdoseCustomBean object.
     */
    public TkeyKlassifizierungCustomBean() {
        addPropertyNames(
            new String[] {
                PROP__KLASSIFIZIERUNG,
                PROP__PK
            });
    }

    /**
     * Creates a new TkeyKlassifizierungCustomBean object.
     *
     * @param  pk  DOCUMENT ME!
     */
    public TkeyKlassifizierungCustomBean(final Integer pk) {
        setPk(pk);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static TkeyKlassifizierungCustomBean createNew() {
        return (TkeyKlassifizierungCustomBean)createNew(TABLE);
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
    public String getKlassifizierung() {
        return klassifizierung;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  klassifizierung  DOCUMENT ME!
     */
    public void setKlassifizierung(final String klassifizierung) {
        final String old = this.klassifizierung;
        this.klassifizierung = klassifizierung;
        this.propertyChangeSupport.firePropertyChange(PROP__KLASSIFIZIERUNG, old, this.klassifizierung);
    }

    @Override
    public String toString() {
        return new TkeyKlassifizierungToStringConverter().convert(metaObject);
    }

//    @Override
//    public String getCompareCriteriaString() {
//        // Workaround: 'keine Angabe' is ordered on the first place
//        if (getPk().equals(0)) {
//            return "00000";
//        } else {
//            return getKeyString();
//        }
//    }
}
