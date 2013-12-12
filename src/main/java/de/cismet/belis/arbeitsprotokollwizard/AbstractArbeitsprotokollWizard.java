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

import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EventListener;

import javax.swing.Action;
import javax.swing.JPanel;

import de.cismet.cids.custom.beans.belis2.ArbeitsprotokollCustomBean;
import de.cismet.cids.custom.beans.belis2.ArbeitsprotokollaktionCustomBean;

import de.cismet.tools.gui.StaticSwingTools;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public abstract class AbstractArbeitsprotokollWizard extends JPanel {

    //~ Instance fields --------------------------------------------------------

    private ArbeitsprotokollCustomBean protokoll;
    private Collection<ActionListener> listeners = new ArrayList<ActionListener>();

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public abstract Class getEntityClass();

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public abstract String getTitle();

    /**
     * DOCUMENT ME!
     *
     * @param  protokoll  DOCUMENT ME!
     */
    public void setProtokoll(final ArbeitsprotokollCustomBean protokoll) {
        this.protokoll = protokoll;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public ArbeitsprotokollCustomBean getProtokoll() {
        return protokoll;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public abstract Action getAction();

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected abstract Collection<ArbeitsprotokollaktionCustomBean> executeAktionen();

    /**
     * DOCUMENT ME!
     */
    protected void showDialog() {
        final ArbeitsprotokollDialog dialog = new ArbeitsprotokollDialog(this, null, true);
        StaticSwingTools.showDialog(dialog);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  listener  DOCUMENT ME!
     */
    public void addListener(final ActionListener listener) {
        listeners.add(listener);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  listener  DOCUMENT ME!
     */
    public void removeListener(final ActionListener listener) {
        listeners.remove(listener);
    }
}
