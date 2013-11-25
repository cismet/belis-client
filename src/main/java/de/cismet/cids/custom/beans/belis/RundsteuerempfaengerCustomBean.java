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
    private Integer anschlusswert;
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
    public Integer getAnschlusswert() {
        return anschlusswert;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  anschlusswert  fk_geom DOCUMENT ME!
     */
    public void setAnschlusswert(final Integer anschlusswert) {
        final Integer old = this.anschlusswert;
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
    public int hashCode() {
        if (this.getId() == null) {
            return System.identityHashCode(this);
        }
        return this.getId().hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof RundsteuerempfaengerCustomBean) {
            final RundsteuerempfaengerCustomBean anEntity = (RundsteuerempfaengerCustomBean)other;
            if (this == other) {
                return true;
            } else if ((other == null) || (!this.getClass().isAssignableFrom(other.getClass()))) {
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
    public String getKeyString() {
        if (getRs_typ() != null) {
            return getRs_typ();
        } else {
            return "";
        }
    }
}
