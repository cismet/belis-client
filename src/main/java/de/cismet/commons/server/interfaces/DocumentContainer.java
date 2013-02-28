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
package de.cismet.commons.server.interfaces;

import java.util.Set;

import de.cismet.cids.custom.beans.belis.DmsUrlCustomBean;

/**
 * DOCUMENT ME!
 *
 * @author   srichter
 * @version  $Revision$, $Date$
 */
public interface DocumentContainer {

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    Set<DmsUrlCustomBean> getDokumente();

    /**
     * DOCUMENT ME!
     *
     * @param  urls  DOCUMENT ME!
     */
    void setDokumente(Set<DmsUrlCustomBean> urls);
}
