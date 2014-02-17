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
public class RundsteuerempfaengerCustomBean extends BaseEntity {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            RundsteuerempfaengerCustomBean.class);

    public static final String TABLE = "rundsteuerempfaenger";

    public static final String PROP__HERRSTELLER_RS = "herrsteller_rs";
    public static final String PROP__RS_TYP = "rs_typ";
    public static final String PROP__ANSCHLUSSWERT = "anschlusswert";
    public static final String PROP__PROGRAMM = "programm";
    public static final String PROP__FOTO = "foto";

    private static final String[] PROPERTY_NAMES = new String[] {
            PROP__ID,
            PROP__HERRSTELLER_RS,
            PROP__RS_TYP,
            PROP__ANSCHLUSSWERT,
            PROP__PROGRAMM,
            PROP__FOTO
        };

    //~ Instance fields --------------------------------------------------------

    private String herrsteller_rs;
    private String rs_typ;
    private Double anschlusswert;
    private String programm;
    private DmsUrlCustomBean foto;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbzweigdoseCustomBean object.
     */
    public RundsteuerempfaengerCustomBean() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static RundsteuerempfaengerCustomBean createNew() {
        return (RundsteuerempfaengerCustomBean)createNew(TABLE);
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
    public DmsUrlCustomBean getFoto() {
        return foto;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  foto  fk_geom DOCUMENT ME!
     */
    public void setFoto(final DmsUrlCustomBean foto) {
        final DmsUrlCustomBean old = this.foto;
        this.foto = foto;
        this.propertyChangeSupport.firePropertyChange(PROP__FOTO, old, this.foto);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getHerrsteller_rs() {
        return herrsteller_rs;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  herrsteller_rs  fk_geom DOCUMENT ME!
     */
    public void setHerrsteller_rs(final String herrsteller_rs) {
        final String old = this.herrsteller_rs;
        this.herrsteller_rs = herrsteller_rs;
        this.propertyChangeSupport.firePropertyChange(PROP__HERRSTELLER_RS, old, this.herrsteller_rs);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getRs_typ() {
        return rs_typ;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  rs_typ  fk_geom DOCUMENT ME!
     */
    public void setRs_typ(final String rs_typ) {
        final String old = this.rs_typ;
        this.rs_typ = rs_typ;
        this.propertyChangeSupport.firePropertyChange(PROP__RS_TYP, old, this.rs_typ);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Double getAnschlusswert() {
        return anschlusswert;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  anschlusswert  fk_geom DOCUMENT ME!
     */
    public void setAnschlusswert(final Double anschlusswert) {
        final Double old = this.anschlusswert;
        this.anschlusswert = anschlusswert;
        this.propertyChangeSupport.firePropertyChange(PROP__ANSCHLUSSWERT, old, this.anschlusswert);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getProgramm() {
        return programm;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  programm  fk_geom DOCUMENT ME!
     */
    public void setProgramm(final String programm) {
        final String old = this.programm;
        this.programm = programm;
        this.propertyChangeSupport.firePropertyChange(PROP__PROGRAMM, old, this.programm);
    }

    @Override
    public String toString() {
        if (getRs_typ() != null) {
            return getRs_typ();
        } else {
            return "";
        }
    }
}
