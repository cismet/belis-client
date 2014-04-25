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

import java.util.Collection;

import de.cismet.cids.custom.beans.belis2.DmsUrlCustomBean;

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
    Collection<DmsUrlCustomBean> getDokumente();

    /**
     * DOCUMENT ME!
     *
     * @param  urls  DOCUMENT ME!
     */
    void setDokumente(Collection<DmsUrlCustomBean> urls);
}
