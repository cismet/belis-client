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
import java.util.Date;

import de.cismet.commons.server.entity.BaseEntity;
import de.cismet.commons.server.interfaces.DocumentContainer;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class TkeyLeuchtentypCustomBean extends BaseEntity implements DocumentContainer {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            TkeyLeuchtentypCustomBean.class);

    public static final String TABLE = "tkey_leuchtentyp";

    public static final String PROP__LEUCHTENTYP = "leuchtentyp";
    public static final String PROP__BESTUECKUNG = "bestueckung";
    public static final String PROP__LEISTUNG = "leistung";
    public static final String PROP__LEISTUNG_BRUTTO = "leistung_brutto";
    public static final String PROP__FABRIKAT = "fabrikat";
    public static final String PROP__LAMPE = "lampe";
    public static final String PROP__LEISTUNG2STUFE = "leistung2stufe";
    public static final String PROP__VORSCHALTGERAET = "vorschaltgeraet";
    public static final String PROP__EINBAU_VORSCHALTGERAET = "einbau_vorschaltgeraet";
    public static final String PROP__LEISTUNG_REDUZIERT = "leistung_reduziert";
    public static final String PROP__LEISTUNG_BRUTTO_REDUZIERT = "leistung_brutto_reduziert";
    public static final String PROP__FOTO = "foto";
    public static final String PROP__DOKUMENTE = "dokumente";
    public static final String PROP__TYPENBEZEICHNUNG = "typenbezeichnung";

    private static final String[] PROPERTY_NAMES = new String[] {
            PROP__ID,
            PROP__LEUCHTENTYP,
            PROP__BESTUECKUNG,
            PROP__LEISTUNG,
            PROP__LEISTUNG_BRUTTO,
            PROP__FABRIKAT,
            PROP__LAMPE,
            PROP__LEISTUNG2STUFE,
            PROP__VORSCHALTGERAET,
            PROP__EINBAU_VORSCHALTGERAET,
            PROP__LEISTUNG_REDUZIERT,
            PROP__LEISTUNG_BRUTTO_REDUZIERT,
            PROP__FOTO,
            PROP__DOKUMENTE,
            PROP__TYPENBEZEICHNUNG
        };

    //~ Instance fields --------------------------------------------------------

    private String leuchtentyp;
    private Double bestueckung;
    private Double leistung;
    private Double leistung_brutto;
    private String fabrikat;
    private String lampe;
    private Date einbaudatum;
    private Double leistung2stufe;
    private String vorschaltgeraet;
    private Date einbau_vorschaltgeraet;
    private Double leistung_reduziert;
    private Double leistung_brutto_reduziert;
    private DmsUrlCustomBean foto;
    private Collection<DmsUrlCustomBean> dokumente;
    private String typenbezeichnung;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbzweigdoseCustomBean object.
     */
    public TkeyLeuchtentypCustomBean() {
    }

    /**
     * Creates a new TkeyLeuchtentypCustomBean object.
     *
     * @param  leuchtentyp  DOCUMENT ME!
     */
    public TkeyLeuchtentypCustomBean(final String leuchtentyp) {
        final String old = this.leuchtentyp;
        this.leuchtentyp = leuchtentyp;
        this.propertyChangeSupport.firePropertyChange(PROP__LEUCHTENTYP, old, this.leuchtentyp);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static TkeyLeuchtentypCustomBean createNew() {
        return (TkeyLeuchtentypCustomBean)createNew(TABLE);
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
    public Double getLeistung_brutto() {
        return leistung_brutto;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  Leistung_brutto  DOCUMENT ME!
     */
    public void setLeistung_brutto(final Double Leistung_brutto) {
        final Double old = this.leistung_brutto;
        this.leistung_brutto = Leistung_brutto;
        this.propertyChangeSupport.firePropertyChange(PROP__LEISTUNG_BRUTTO, old, this.leistung_brutto);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Double getLeistung2stufe() {
        return leistung2stufe;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  leistung2stufe  DOCUMENT ME!
     */
    public void setLeistung2stufe(final Double leistung2stufe) {
        final Double old = this.leistung2stufe;
        this.leistung2stufe = leistung2stufe;
        this.propertyChangeSupport.firePropertyChange(PROP__LEISTUNG2STUFE, old, this.leistung2stufe);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getLeuchtentyp() {
        return leuchtentyp;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  leuchtentyp  DOCUMENT ME!
     */
    public void setLeuchtentyp(final String leuchtentyp) {
        final String old = this.leuchtentyp;
        this.leuchtentyp = leuchtentyp;
        this.propertyChangeSupport.firePropertyChange(PROP__LEUCHTENTYP, old, this.leuchtentyp);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getFabrikat() {
        return fabrikat;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fabrikat  DOCUMENT ME!
     */
    public void setFabrikat(final String fabrikat) {
        final String old = this.fabrikat;
        this.fabrikat = fabrikat;
        this.propertyChangeSupport.firePropertyChange(PROP__FABRIKAT, old, this.fabrikat);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Double getBestueckung() {
        return bestueckung;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  bestueckung  DOCUMENT ME!
     */
    public void setBestueckung(final Double bestueckung) {
        final Double old = this.bestueckung;
        this.bestueckung = bestueckung;
        this.propertyChangeSupport.firePropertyChange(PROP__BESTUECKUNG, old, this.bestueckung);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Double getLeistung() {
        return leistung;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  leistung  DOCUMENT ME!
     */
    public void setLeistung(final Double leistung) {
        final Double old = this.leistung;
        this.leistung = leistung;
        this.propertyChangeSupport.firePropertyChange(PROP__LEISTUNG, old, this.leistung);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getLampe() {
        return lampe;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  lampe  DOCUMENT ME!
     */
    public void setLampe(final String lampe) {
        final String old = this.lampe;
        this.lampe = lampe;
        this.propertyChangeSupport.firePropertyChange(PROP__LAMPE, old, this.lampe);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Double getLeistungBrutto() {
        return getLeistung_brutto();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  leistungBrutto  DOCUMENT ME!
     */
    public void setLeistungBrutto(final Double leistungBrutto) {
        setLeistung_brutto(leistungBrutto);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getVorschaltgeraet() {
        return vorschaltgeraet;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  vorschaltgeraet  DOCUMENT ME!
     */
    public void setVorschaltgeraet(final String vorschaltgeraet) {
        final String old = this.vorschaltgeraet;
        this.vorschaltgeraet = vorschaltgeraet;
        this.propertyChangeSupport.firePropertyChange(PROP__VORSCHALTGERAET, old, this.vorschaltgeraet);
    }

    @Override
    public String toString() {
        if (getLeuchtentyp() != null) {
            return getLeuchtentyp() + ((getFabrikat() != null) ? (" " + getFabrikat()) : "");
        } else {
            return "";
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Date getEinbau_vorschaltgeraet() {
        return einbau_vorschaltgeraet;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  einbau_vorschaltgeraet  DOCUMENT ME!
     */
    public void setEinbau_vorschaltgeraet(final Date einbau_vorschaltgeraet) {
        final Date old = this.einbau_vorschaltgeraet;
        this.einbau_vorschaltgeraet = einbau_vorschaltgeraet;
        this.propertyChangeSupport.firePropertyChange(PROP__EINBAU_VORSCHALTGERAET, old, this.einbau_vorschaltgeraet);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Double getLeistung_reduziert() {
        return leistung_reduziert;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  leistung_reduziert  DOCUMENT ME!
     */
    public void setLeistung_reduziert(final Double leistung_reduziert) {
        final Double old = this.leistung_reduziert;
        this.leistung_reduziert = leistung_reduziert;
        this.propertyChangeSupport.firePropertyChange(PROP__LEISTUNG_REDUZIERT, old, this.leistung_reduziert);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Double getLeistung_brutto_reduziert() {
        return leistung_brutto_reduziert;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  leistung_brutto_reduziert  DOCUMENT ME!
     */
    public void setLeistung_brutto_reduziert(final Double leistung_brutto_reduziert) {
        final Double old = this.leistung_brutto_reduziert;
        this.leistung_brutto_reduziert = leistung_brutto_reduziert;
        this.propertyChangeSupport.firePropertyChange(
            PROP__LEISTUNG_BRUTTO_REDUZIERT,
            old,
            this.leistung_brutto_reduziert);
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
    public String getTypenbezeichnung() {
        return typenbezeichnung;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  typenbezeichnung  bemerkung DOCUMENT ME!
     */
    public void setTypenbezeichnung(final String typenbezeichnung) {
        final String old = this.typenbezeichnung;
        this.typenbezeichnung = typenbezeichnung;
        this.propertyChangeSupport.firePropertyChange(PROP__TYPENBEZEICHNUNG, old, this.typenbezeichnung);
    }
}
