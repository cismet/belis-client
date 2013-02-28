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

import de.cismet.cids.custom.beans.belis.LeitungstypCustomBean;
import de.cismet.cids.custom.beans.belis.MaterialLeitungCustomBean;
import de.cismet.cids.custom.beans.belis.QuerschnittCustomBean;

import de.cismet.commons.server.interfaces.DocumentContainer;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public interface Leitung extends Serializable, DocumentContainer {

    //~ Static fields/initializers ---------------------------------------------

    String PROP_ID = "Leitung.id";
    String PROP_MATERIAL = "Leitung.material";
    String PROP_LEITUNGSTYP = "Leitung.Leitungstyp";
    String PROP_QUERSCHNITT = "Leitung.querschnitt";

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
    MaterialLeitungCustomBean getMaterial();

    /**
     * DOCUMENT ME!
     *
     * @param  material  DOCUMENT ME!
     */
    void setMaterial(final MaterialLeitungCustomBean material);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    LeitungstypCustomBean getLeitungstyp();

    /**
     * DOCUMENT ME!
     *
     * @param  leitungstyp  DOCUMENT ME!
     */
    void setLeitungstyp(final LeitungstypCustomBean leitungstyp);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    QuerschnittCustomBean getQuerschnitt();

    /**
     * DOCUMENT ME!
     *
     * @param  querschnitt  DOCUMENT ME!
     */
    void setQuerschnitt(final QuerschnittCustomBean querschnitt);
}
