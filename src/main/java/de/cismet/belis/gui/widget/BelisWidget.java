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
package de.cismet.belis.gui.widget;

import org.jdom.Element;

import java.awt.Component;

import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.JComponent;

import de.cismet.commons.architecture.validation.Validatable;

import de.cismet.tools.configuration.NoWriteError;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public class BelisWidget extends AbstractWidget {

    //~ Instance fields --------------------------------------------------------

    protected final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());
    protected String validationMessage = "";

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new DefaultWidget object.
     */
    public BelisWidget() {
        setWidgetIcon((Icon)null);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void objectChanged(final Object changedObject) {
    }

    // Proper implementation --> class all Container something and calls the clear Method
    @Override
    public void clearComponent() {
        if (log.isDebugEnabled()) {
            log.debug(getName() + " Widget cleared");
        }
    }

    @Override
    public void setWidgetEditable(final boolean isEditable) {
        if (log.isDebugEnabled()) {
            log.debug(getName() + " Widget setEditable: " + isEditable);
        }
    }

    @Override
    public void refresh(final Object refreshedObject) {
        if (log.isDebugEnabled()) {
            log.debug(getName() + " Widget refreshed");
        }
    }

    @Override
    public void configure(final Element parent) {
    }

    // ToDo proper defaultConfiguration
    @Override
    public Element getConfiguration() throws NoWriteError {
        return null;
    }

    @Override
    public void masterConfigure(final Element parent) {
        clearComponent();
    }

    @Override
    public void guiObjectChanged(final Object changedObject) {
    }

    @Override
    public ArrayList<JComponent> getCustomButtons() {
        return new ArrayList<JComponent>();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  changedObject  DOCUMENT ME!
     */
    public void backgroundObjectChanged(final Object changedObject) {
    }

    // ToDo print the right messages from the validators
    @Override
    public String getValidationMessage() {
        return validationMessage;
    }

    @Override
    public int getStatus() {
        return Validatable.VALID;
    }

    @Override
    public void showAssistent(final Component parent) {
    }

    @Override
    public void updateUIPropertyChange() {
    }
}
