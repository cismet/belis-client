/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * AbstractWidget.java
 *
 * Created on 20. November 2007, 09:25
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package de.cismet.belis.gui.widget;

import org.apache.log4j.Logger;

import java.beans.PropertyChangeEvent;

import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import de.cismet.belis.broker.BelisBroker;

import de.cismet.commons.architecture.interfaces.Widget;
import de.cismet.commons.architecture.validation.ValidationStateChangedListener;

/**
 * DOCUMENT ME!
 *
 * @author   Sebastian Puhl
 * @version  $Revision$, $Date$
 */
public abstract class AbstractWidget extends JPanel implements Widget {

    //~ Static fields/initializers ---------------------------------------------

    public static final String PROP_WIDGET_NAME = "widgetName";
    protected static final Icon DEFAULT_ICON = new javax.swing.ImageIcon(AbstractWidget.class.getResource(
                "/de/cismet/commons/architecture/resource/icon/cismetlogo16.png"));
    public static final String PROP_WIDGET_ICON = "widgetIcon";

    //~ Instance fields --------------------------------------------------------

    private BelisBroker broker;
    private String widgetName = "Fenstername";
    private Icon widgetIcon;

    private final Logger log = org.apache.log4j.Logger.getLogger(this.getClass());
    private final ArrayList<ValidationStateChangedListener> validationListeners =
        new ArrayList<ValidationStateChangedListener>();
    // Widget properties
    private UpdateWorker currentWorker;
    // Edit properties
    private boolean isCoreWidget = false;
    private boolean isReadOnlyWidget = true;
    // Validation

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new instance of AbstractWidget.
     */
    public AbstractWidget() {
    }

    //~ Methods ----------------------------------------------------------------

    // Idea isEditable property

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public BelisBroker getBroker() {
        return broker;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  broker  DOCUMENT ME!
     */
    public void setBroker(final BelisBroker broker) {
        this.broker = broker;
    }

    // TODO Refactor why a abstract class ? better a default Widget ?
    // CLASS BUILD BECAUSE NEED TO BE A COMPONENT --> NOT POSSIBLE WITH INTERFACES
    @Override
    public String getWidgetName() {
        return widgetName;
    }

    @Override
    public void setWidgetName(final String widgetName) {
        final String oldWidgetName = getWidgetName();
        this.widgetName = widgetName;
        firePropertyChange(PROP_WIDGET_NAME, oldWidgetName, widgetName);
    }

    @Override
    public Icon getWidgetIcon() {
        if (widgetIcon != null) {
            return widgetIcon;
        } else {
            widgetIcon = DEFAULT_ICON;
            return DEFAULT_ICON;
        }
    }

    @Override
    public void setWidgetIcon(final String iconName) {
        try {
            setWidgetIcon(new javax.swing.ImageIcon(getClass().getResource(iconName)));
        } catch (Exception ex) {
            log.warn("Fehler beim setzen des Icons: ", ex);
            setWidgetIcon(DEFAULT_ICON);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  widgetIcon  DOCUMENT ME!
     */
    public void setWidgetIcon(final Icon widgetIcon) {
        final Icon oldWidgetIcon = getWidgetIcon();
        this.widgetIcon = widgetIcon;
        firePropertyChange(PROP_WIDGET_NAME, oldWidgetIcon, widgetIcon);
    }

    @Override
    public boolean isCoreWidget() {
        return isCoreWidget;
    }

    @Override
    public void setIsCoreWidget(final boolean isCoreWidget) {
        this.isCoreWidget = isCoreWidget;
    }

    @Override
    public boolean isReadOnlyWidget() {
        return isReadOnlyWidget;
    }

    @Override
    public void setReadOnlyWidget(final boolean isReadOnlyWidget) {
        this.isReadOnlyWidget = isReadOnlyWidget;
    }

    @Override
    public void objectChanged(final Object changedObject) {
        if ((currentWorker != null) && !currentWorker.isDone()) {
            currentWorker.cancel(true);
        }
        currentWorker = new UpdateWorker(changedObject);
        broker.execute(currentWorker);
    }

    @Override
    public void removeValidationStateChangedListener(final ValidationStateChangedListener l) {
        validationListeners.remove(l);
    }

    @Override
    public void addValidationStateChangedListener(final ValidationStateChangedListener l) {
        validationListeners.add(l);
    }

    @Override
    public void fireValidationStateChanged(final Object validatedObject) {
        for (final ValidationStateChangedListener listener : validationListeners) {
            listener.validationStateChanged(validatedObject);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  changedObject  DOCUMENT ME!
     */
    public abstract void guiObjectChanged(Object changedObject);

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if (log.isDebugEnabled()) {
            log.debug("PropertyChange in Widget: " + getWidgetName());
        }
        updateUIPropertyChange();
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    class UpdateWorker extends SwingWorker<Void, Void> {

        //~ Instance fields ----------------------------------------------------

        private Object changedObject;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new UpdateWorker object.
         *
         * @param  changedObject  DOCUMENT ME!
         */
        UpdateWorker(final Object changedObject) {
            this.changedObject = changedObject;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        protected Void doInBackground() throws Exception {
            guiObjectChanged(changedObject);
            return null;
        }

        @Override
        protected void done() {
            if (isCancelled()) {
                log.warn("UpdateWorker is canceled --> nothing to do in method done()");
                return;
            }
            try {
                guiObjectChanged(changedObject);
                broker.fireChangeFinished(AbstractWidget.this);
            } catch (Exception ex) {
                log.error("Failure during processing UpdateWorker results", ex);
                return;
            }
        }
    }
}
