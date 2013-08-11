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

import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.Binding.ValueResult;
import org.jdesktop.beansbinding.BindingGroup;

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
    protected org.jdesktop.beansbinding.BindingGroup bindingGroup;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new DefaultWidget object.
     */
    public BelisWidget() {
        setWidgetIcon((String)null);
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
        if (getBindingGroup() != null) {
            for (final Binding curBinding : getBindingGroup().getBindings()) {
                if (this.isAncestorOf((Component)curBinding.getTargetObject())) {
                    final ValueResult result = curBinding.getTargetValueForSource();
                    if ((result != null) && result.failed()
                                && (result.getFailure().getType() == Binding.SyncFailureType.VALIDATION_FAILED)) {
                        log.info("Validation of property " + curBinding.getSourceProperty() + "has failed: " + result);
                        log.info("Description: " + result.getFailure().getValidationResult().getDescription());
                        validationMessage = result.getFailure().getValidationResult().getDescription();
                        return Validatable.ERROR;
                    } else {
                        log.info("Validation of property " + curBinding.getSourceProperty() + "is valid: " + result);
                        try {
                            log.info("Check has failure: " + result.failed());
                            if (result.failed()) {
                                log.info("failure " + result.getFailure());
                                log.info("manual check: "
                                            + curBinding.getValidator().validate(
                                                curBinding.getTargetProperty().getValue(curBinding.getTargetObject())));
                            } else {
                                if (log.isDebugEnabled()) {
                                    log.debug("value: " + result.getValue());
                                }
                            }
                        } catch (Exception ex) {
                            if (log.isDebugEnabled()) {
                                log.debug("manual check failed");
                            }
                        }
                    }
                } else {
                    // log.debug("Validation is skipped because binding does not belong to currentPanel.");
                }
            }
        }
        validationMessage = "";
        return Validatable.VALID;
    }

    @Override
    public BindingGroup getBindingGroup() {
        return bindingGroup;
    }

    @Override
    public void setBindingGroup(final BindingGroup bindingGroup) {
        this.bindingGroup = bindingGroup;
    }

    @Override
    public void showAssistent(final Component parent) {
    }

    @Override
    public void updateUIPropertyChange() {
    }
}
