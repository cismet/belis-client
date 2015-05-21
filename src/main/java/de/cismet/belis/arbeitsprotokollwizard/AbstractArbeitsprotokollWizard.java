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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.text.SimpleDateFormat;

import java.util.Collection;
import java.util.Date;

import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import de.cismet.cids.custom.beans.belis2.ArbeitsauftragCustomBean;
import de.cismet.cids.custom.beans.belis2.ArbeitsprotokollCustomBean;
import de.cismet.cids.custom.beans.belis2.ArbeitsprotokollaktionCustomBean;

import de.cismet.commons.server.entity.WorkbenchFeatureEntity;

import de.cismet.tools.gui.StaticSwingTools;

import static de.cismet.belis.arbeitsprotokollwizard.LeuchteLeuchtenerneuerungWizard.LOG;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public abstract class AbstractArbeitsprotokollWizard extends JPanel {

    //~ Instance fields --------------------------------------------------------

    private ArbeitsauftragCustomBean arbeitsauftrag;
    private Collection<ArbeitsprotokollCustomBean> protokolle;

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public abstract ArbeitsprotokollCustomBean.ChildType getEntityClass();

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public abstract String getTitle();

    /**
     * DOCUMENT ME!
     *
     * @param  protokolle  DOCUMENT ME!
     */
    public void setProtokolle(final Collection<ArbeitsprotokollCustomBean> protokolle) {
        this.protokolle = protokolle;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Collection<ArbeitsprotokollCustomBean> getProtokolle() {
        return protokolle;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public ArbeitsauftragCustomBean getArbeitsauftrag() {
        return arbeitsauftrag;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  arbeitsauftrag  DOCUMENT ME!
     */
    public void setArbeitsauftrag(final ArbeitsauftragCustomBean arbeitsauftrag) {
        this.arbeitsauftrag = arbeitsauftrag;
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
     * @param  listener  DOCUMENT ME!
     */
    protected void executeAktion(final PropertyChangeListener listener) {
        final Collection<ArbeitsprotokollCustomBean> protokolle = getProtokolle();
        final int numOf = protokolle.size();
        listener.propertyChange(new PropertyChangeEvent(this, "start", null, numOf));
        new SwingWorker<Void, Void>() {

                @Override
                protected Void doInBackground() throws Exception {
                    int index = 0;
                    for (final ArbeitsprotokollCustomBean protokoll : protokolle) {
                        try {
                            listener.propertyChange(new PropertyChangeEvent(this, "progress", null, index));
                            executeAktion(protokoll);
                        } catch (final Exception ex) {
                            LOG.error(ex, ex);
                        }
                        index++;
                    }
                    return null;
                }

                @Override
                protected void done() {
                    try {
                        get();
                    } catch (final Exception ex) {
                        LOG.warn(ex, ex);
                    } finally {
                        listener.propertyChange(new PropertyChangeEvent(this, "done", null, numOf));
                    }
                }
            }.execute();
    }

    /**
     * DOCUMENT ME!
     *
     * @param   desc      DOCUMENT ME!
     * @param   entity    DOCUMENT ME!
     * @param   property  DOCUMENT ME!
     * @param   newValue  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    protected static ArbeitsprotokollaktionCustomBean createAktion(final String desc,
            final WorkbenchFeatureEntity entity,
            final String property,
            final Object newValue) throws Exception {
        final Object oldValue = entity.getProperty(property);
        entity.setProperty(property, newValue);

        final ArbeitsprotokollaktionCustomBean aktion = ArbeitsprotokollaktionCustomBean.createNew();
        aktion.setAenderung(desc);
        aktion.setAlt(valueToString(oldValue));
        aktion.setNeu(valueToString(newValue));
        return aktion;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   value  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private static String valueToString(final Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof Date) {
            final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            return dateFormat.format(value);
        } else if (value instanceof Boolean) {
            return (Boolean)value ? "Ja" : "Nein";
        } else {
            return value.toString();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   protokoll  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    protected abstract void executeAktion(final ArbeitsprotokollCustomBean protokoll) throws Exception;

    /**
     * DOCUMENT ME!
     */
    protected void showDialog() {
        final ArbeitsprotokollDialog dialog = new ArbeitsprotokollDialog(this, null, true);
        StaticSwingTools.showDialog(dialog);
//        final Collection<ArbeitsprotokollCustomBean> protokolle = BelisBroker.getInstance()
//                    .getDetailWidget()
//                    .getArbeitsauftragPanel()
//                    .getSelectedProtokolle();
//        BelisBroker.getInstance().getDetailWidget().getArbeitsauftragPanel().setSelectedProtokolle(protokolle);
    }

    /**
     * DOCUMENT ME!
     */
    protected void clear() {
    }
}
