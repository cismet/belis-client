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
package de.cismet.belis.arbeitsprotokollwizard;

import java.util.Collection;

import javax.swing.JPanel;

import de.cismet.cids.custom.beans.belis2.ArbeitsprotokollCustomBean;
import de.cismet.cids.custom.beans.belis2.ArbeitsprotokollaktionCustomBean;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
//@org.openide.util.lookup.ServiceProvider(service = AbstractArbeitsprotokollWizard.class)
public abstract class AbstractArbeitsprotokollWizard<T extends BaseEntity> extends JPanel {

    //~ Instance fields --------------------------------------------------------

    private T entity;

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  entity  DOCUMENT ME!
     */
    public void setEntity(final T entity) {
        this.entity = entity;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public T getEntity() {
        return entity;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public abstract String getProtokollname();

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public abstract Collection<ArbeitsprotokollaktionCustomBean> getAktionen();
}
