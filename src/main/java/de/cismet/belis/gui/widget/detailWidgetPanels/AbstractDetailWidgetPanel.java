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
package de.cismet.belis.gui.widget.detailWidgetPanels;

import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.BindingListener;
import org.jdesktop.beansbinding.PropertyStateEvent;

import java.awt.Color;
import java.awt.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.cismet.belis.broker.BelisBroker;

import de.cismet.cids.custom.beans.belis2.TdtaStandortMastCustomBean;

import de.cismet.commons.architecture.validation.Validatable;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @author   Gilles Baatz
 * @version  $Revision$, $Date$
 */
public abstract class AbstractDetailWidgetPanel<T extends BaseEntity> extends JPanel {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            AbstractDetailWidgetPanel.class);
    public static final String PROP_CURRENT_ENTITY = "currentEntity";

    //~ Instance fields --------------------------------------------------------

    public final String PANEL_CARD_NAME;
    protected T currentEntity = null;
    boolean isTriggerd = false;
    final HashMap<JComponent, JComponent> componentToLabelMap = new HashMap<JComponent, JComponent>();
    private String validationMessage;
    private boolean editable = true;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbstractDetailWidgetPanel object.
     *
     * @param  panelCardName  DOCUMENT ME!
     */
    public AbstractDetailWidgetPanel(final String panelCardName) {
        PANEL_CARD_NAME = panelCardName;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public abstract JLabel getTabLabel();

    /**
     * DOCUMENT ME!
     */
    abstract void initPanel();

    /**
     * DOCUMENT ME!
     */
    abstract void initComponentToLabelMap();

    /**
     * DOCUMENT ME!
     */
    public abstract void setElementsNull();

    /**
     * DOCUMENT ME!
     *
     * @param  isEditable  DOCUMENT ME!
     */
    public abstract void setPanelEditable(final boolean isEditable);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public T getCurrentEntity() {
        return currentEntity;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  currentEntity  DOCUMENT ME!
     */
    public void setCurrentEntity(final T currentEntity) {
        final T oldCurrentEntity = this.currentEntity;
        if (getBindingGroup() != null) {
            getBindingGroup().unbind();
        }
        this.currentEntity = currentEntity;
        if (getBindingGroup() != null) {
            getBindingGroup().bind();
        }
        try {
            firePropertyChange(PROP_CURRENT_ENTITY, oldCurrentEntity, currentEntity);
        } catch (final Exception ex) {
            LOG.error(ex, ex);
        }
    }

    /**
     * DOCUMENT ME!
     */
    protected abstract void commitEdits();

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected abstract BindingGroup getBindingGroup();

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public int getStatus() {
//        if (validationState.size() != 0) {
//            log.info("There are bindings which are not valid. Errorcount: "+validationState.size());
//            return Validatable.ERROR;
//        }
        if (getBindingGroup() != null) {
            commitEdits();

            for (final Binding curBinding : getBindingGroup().getBindings()) {
                if (this.isAncestorOf((Component)curBinding.getTargetObject())) {
//            Validator currentValidator = curBinding.getValidator();
//            if(currentValidator != null){
//                log.debug("Validator of Binding != null. Validating Property: "+curBinding.getSourceProperty());
//
//            }
                    boolean err = false;
                    String errMessage = null;
                    Binding.ValueResult result;
                    try {
                        result = curBinding.getTargetValueForSource();
                    } catch (final Exception ex) {
                        LOG.error(ex, ex);
                        err = true;
                        result = null;
                        errMessage = ex.getMessage();
                    }
                    if (err) {
                        LOG.info("Validation of property " + curBinding.getSourceProperty() + "has failed: " + result);
                        LOG.info("Description: " + errMessage);
                        validationMessage = errMessage;
                        return Validatable.ERROR;
                    } else if ((result != null) && result.failed()
                                && (result.getFailure().getType() == Binding.SyncFailureType.VALIDATION_FAILED)) {
                        LOG.info("Validation of property " + curBinding.getSourceProperty() + "has failed: " + result);
                        LOG.info("Description: " + result.getFailure().getValidationResult().getDescription());
                        validationMessage = result.getFailure().getValidationResult().getDescription();
                        return Validatable.ERROR;
                    } else {
                        LOG.info("Validation of property " + curBinding.getSourceProperty() + "is valid: " + result);
                        try {
                            LOG.info("Check has failure: " + result.failed());
                            if (result.failed()) {
                                LOG.info("failure " + result.getFailure());
                                LOG.info("manual check: "
                                            + curBinding.getValidator().validate(
                                                curBinding.getTargetProperty().getValue(curBinding.getTargetObject())));
                            } else {
                                if (LOG.isDebugEnabled()) {
                                    LOG.debug("value: " + result.getValue());
                                }
                            }
                        } catch (Exception ex) {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("manual check failed");
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

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getValidationMessage() {
        return validationMessage;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  editable  DOCUMENT ME!
     */
    public void setEditable(final boolean editable) {
        this.editable = editable;
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    class PanelBindingListener implements BindingListener {

        //~ Instance fields ----------------------------------------------------

        private Collection<Binding> validationState = new HashSet<Binding>();

        //~ Methods ------------------------------------------------------------

        @Override
        public void bindingBecameBound(final Binding binding) {
        }

        @Override
        public void bindingBecameUnbound(final Binding binding) {
        }

        @Override
        public void syncWarning(final Binding binding, final Binding.SyncFailure failure) {
        }

        @Override
        public void syncFailed(final Binding binding, final Binding.SyncFailure failure) {
            // log.debug("syncFailed");
            final Object target = binding.getTargetObject();
            // log.debug("Target: " + target);
            if (target instanceof JComponent) { // && !(target instanceof JComboBox)) {
                final JComponent c = (JComponent)target;
                final JComponent associatedLabel = componentToLabelMap.get(c);
                if (associatedLabel != null) {
                    associatedLabel.setForeground(Color.red);
                } else {
                    c.setForeground(Color.red);
                }
                try {
                    if (associatedLabel != null) {
                        associatedLabel.setToolTipText(failure.getValidationResult().getDescription());
                    }
                    c.setToolTipText(failure.getValidationResult().getDescription());
                } catch (Exception ex) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Error while setting tooltip", ex);
                    }
                    c.setToolTipText(null);
                }
            } else {
                LOG.error("keine JCOmponent");
            }
            if ((currentEntity instanceof TdtaStandortMastCustomBean)
                        && BelisBroker.getInstance().getDetailWidget().getStandortPanel().isAncestorOf(
                            (Component)binding.getTargetObject())) {
                validationState.add(binding);
                // log.debug("Validation state changed. Errorcount: "+validationState.size());
            }
            // ToDo message with complete text to user;
            // lblStrassenschluesselValidation.setIcon(BelisIcons.icoCancel22);
        }

        @Override
        public void synced(final Binding binding) {
            // log.debug("synced: source: "+binding.getSourceObject()+" target: "+binding.getTargetObject(),new
            // CurrentStackTrace()); log.debug("sync: " + cbxLeuchteStrassenschluessel.getSelectedItem());
            // lblStrassenschluesselValidation.setIcon(BelisIcons.icoAccept22);
            final Object target = binding.getTargetObject();
            if (target instanceof JComponent) { // && !(target instanceof JComboBox)) {
                final JComponent c = (JComponent)target;
                final JComponent associatedLabel = componentToLabelMap.get(c);
                if (associatedLabel != null) {
                    associatedLabel.setForeground(Color.black);
                } else {
                    c.setForeground(Color.black);
                }
                c.setToolTipText(null);
            } else {
                LOG.error("keine JCOmponent");
            }
            if ((currentEntity instanceof TdtaStandortMastCustomBean)
                        && BelisBroker.getInstance().getDetailWidget().getStandortPanel().isAncestorOf(
                            (Component)binding.getTargetObject())) {
                validationState.remove(binding);
                // log.debug("Validation state changed. Errorcount: "+validationState.size());
            }
//                String bindingName = binding.getName();
//                if (bindingName != null) {
//                    Collection<String> additionalValidationBindings = validationDependencies.get(binding.getName());
//                    log.debug("for binding " + bindingName + "-->> dependencies: " + additionalValidationBindings);
//                    if (additionalValidationBindings != null) {
//
//                        for (String name : additionalValidationBindings) {
//                            Binding b = bindingGroup.getBinding(name);
//                            if (b != 06694null) {
//                                log.debug("CheckAgain: " + name);
//                                b.saveAndNotify();
//                            }
//                        }
//                    }
//
//                }
        }

        @Override
        public void sourceChanged(final Binding binding, final PropertyStateEvent event) {
        }

        @Override
        public void targetChanged(final Binding binding, final PropertyStateEvent event) {
        }
    }
}
