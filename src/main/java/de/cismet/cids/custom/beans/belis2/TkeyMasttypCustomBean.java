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

import java.util.Collection;

import de.cismet.commons.server.entity.BaseEntity;
import de.cismet.commons.server.interfaces.DocumentContainer;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class TkeyMasttypCustomBean extends BaseEntity implements DocumentContainer {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(TkeyMasttypCustomBean.class);

    public static final String TABLE = "tkey_masttyp";

    public static final String PROP__MASTTYP = "masttyp";
    public static final String PROP__BEZEICHNUNG = "bezeichnung";
    public static final String PROP__LPH = "lph";
    public static final String PROP__HERSTELLER = "hersteller";
    public static final String PROP__WANDSTAERKE = "wandstaerke";
    public static final String PROP__DOKUMENTE = "dokumente";
    public static final String PROP__FOTO = "foto";

    //~ Instance fields --------------------------------------------------------

    private String masttyp;
    private String bezeichnung;
    private Double lph;
    private String hersteller;
    private Integer wandstaerke;
    private Collection<DmsUrlCustomBean> dokumente;
    private DmsUrlCustomBean foto;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbzweigdoseCustomBean object.
     */
    public TkeyMasttypCustomBean() {
        addPropertyNames(
            new String[] {
                PROP__MASTTYP,
                PROP__BEZEICHNUNG,
                PROP__LPH,
                PROP__HERSTELLER,
                PROP__WANDSTAERKE,
                PROP__DOKUMENTE,
                PROP__FOTO
            });
    }

    /**
     * Creates a new TkeyMasttypCustomBean object.
     *
     * @param  masttyp  DOCUMENT ME!
     */
    public TkeyMasttypCustomBean(final String masttyp) {
        setMasttyp(masttyp);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static TkeyMasttypCustomBean createNew() {
        return (TkeyMasttypCustomBean)createNew(TABLE);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getMasttyp() {
        return masttyp;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  masttyp  DOCUMENT ME!
     */
    public void setMasttyp(final String masttyp) {
        final String old = this.masttyp;
        this.masttyp = masttyp;
        this.propertyChangeSupport.firePropertyChange(PROP__MASTTYP, old, this.masttyp);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Double getLph() {
        return lph;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  lph  DOCUMENT ME!
     */
    public void setLph(final Double lph) {
        final Double old = this.lph;
        this.lph = lph;
        this.propertyChangeSupport.firePropertyChange(PROP__LPH, old, this.lph);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getHersteller() {
        return hersteller;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  hersteller  DOCUMENT ME!
     */
    public void setHersteller(final String hersteller) {
        final String old = this.hersteller;
        this.hersteller = hersteller;
        this.propertyChangeSupport.firePropertyChange(PROP__HERSTELLER, old, this.hersteller);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getWandstaerke() {
        return wandstaerke;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  wandstaerke  DOCUMENT ME!
     */
    public void setWandstaerke(final Integer wandstaerke) {
        final Integer old = this.wandstaerke;
        this.wandstaerke = wandstaerke;
        this.propertyChangeSupport.firePropertyChange(PROP__WANDSTAERKE, old, this.wandstaerke);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public Collection<DmsUrlCustomBean> getDokumente() {
        return dokumente;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  dokumente  DOCUMENT ME!
     */
    @Override
    public void setDokumente(final Collection<DmsUrlCustomBean> dokumente) {
        final Collection<DmsUrlCustomBean> old = this.dokumente;
        this.dokumente = dokumente;
        this.propertyChangeSupport.firePropertyChange(PROP__DOKUMENTE, old, this.dokumente);
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
     * @param  foto  DOCUMENT ME!
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
    public String getBezeichnung() {
        return bezeichnung;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  bezeichnung  foto DOCUMENT ME!
     */
    public void setBezeichnung(final String bezeichnung) {
        final String old = this.bezeichnung;
        this.bezeichnung = bezeichnung;
        this.propertyChangeSupport.firePropertyChange(PROP__BEZEICHNUNG, old, this.bezeichnung);
    }
}
