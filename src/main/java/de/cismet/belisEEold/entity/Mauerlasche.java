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
package de.cismet.belisEEold.entity;

import java.io.Serializable;

import java.util.Date;
import java.util.Set;

import de.cismet.cids.custom.beans.belis.MaterialMauerlascheCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyStrassenschluesselCustomBean;

import de.cismet.commons.server.interfaces.DocumentContainer;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public interface Mauerlasche extends Serializable, DocumentContainer {

    //~ Static fields/initializers ---------------------------------------------

    String PROP_ID = "Mauerlasche.id";

    String PROP_STRASSENSCHLUESSEL = "Mauerlasche.strassenschluessel";

    String PROP_LAUFENDE_NUMMER = "Mauerlasche.laufendeNummer";

    String PROP_ERSTELLUNGSJAHR = "Mauerlasche.erstellungsjahr";

    String PROP_MATERIAL = "Mauerlasche.material";

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    Long getId();

    /**
     * DOCUMENT ME!
     *
     * @param  id  DOCUMENT ME!
     */
    void setId(final Long id);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    Date getErstellungsjahr();

    /**
     * DOCUMENT ME!
     *
     * @param  erstellungsjahr  DOCUMENT ME!
     */
    void setErstellungsjahr(final Date erstellungsjahr);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    Short getLaufendeNummer();

    /**
     * DOCUMENT ME!
     *
     * @param  laufendeNummer  DOCUMENT ME!
     */
    void setLaufendeNummer(final Short laufendeNummer);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    TkeyStrassenschluesselCustomBean getStrassenschluessel();

    /**
     * DOCUMENT ME!
     *
     * @param  strassenschluessel  DOCUMENT ME!
     */
    void setStrassenschluessel(final TkeyStrassenschluesselCustomBean strassenschluessel);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    MaterialMauerlascheCustomBean getMaterial();

    /**
     * DOCUMENT ME!
     *
     * @param  material  DOCUMENT ME!
     */
    void setMaterial(final MaterialMauerlascheCustomBean material);
}
