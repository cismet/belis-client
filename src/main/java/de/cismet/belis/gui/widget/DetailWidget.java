/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * EntityDetailWidget.java
 *
 * Created on 23. März 2009, 11:27
 */
package de.cismet.belis.gui.widget;

import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Converter;
import org.jdesktop.beansbinding.Validator;
import org.jdesktop.beansbinding.Validator.Result;

import java.awt.BorderLayout;

import java.beans.PropertyChangeEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import de.cismet.belis.broker.BelisBroker;
import de.cismet.belis.broker.CidsBroker;

import de.cismet.belis.gui.documentpanel.DocumentPanel;
import de.cismet.belis.gui.widget.detailWidgetPanels.AbzweigdosePanel;
import de.cismet.belis.gui.widget.detailWidgetPanels.LeitungPanel;
import de.cismet.belis.gui.widget.detailWidgetPanels.LeuchtePanel;
import de.cismet.belis.gui.widget.detailWidgetPanels.MauerlaschePanel;
import de.cismet.belis.gui.widget.detailWidgetPanels.SchaltstellePanel;
import de.cismet.belis.gui.widget.detailWidgetPanels.StandortPanel;

import de.cismet.belisEE.util.CriteriaStringComparator;

import de.cismet.cids.custom.beans.belis.AbzweigdoseCustomBean;
import de.cismet.cids.custom.beans.belis.DmsUrlCustomBean;
import de.cismet.cids.custom.beans.belis.LeitungCustomBean;
import de.cismet.cids.custom.beans.belis.LeitungstypCustomBean;
import de.cismet.cids.custom.beans.belis.MauerlascheCustomBean;
import de.cismet.cids.custom.beans.belis.SchaltstelleCustomBean;
import de.cismet.cids.custom.beans.belis.TdtaLeuchtenCustomBean;
import de.cismet.cids.custom.beans.belis.TdtaStandortMastCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyStrassenschluesselCustomBean;

import de.cismet.commons.server.interfaces.DocumentContainer;

import de.cismet.commons2.architecture.widget.DefaultWidget;

import de.cismet.tools.CurrentStackTrace;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public class DetailWidget extends DefaultWidget {

    //~ Static fields/initializers ---------------------------------------------

    public static final String PROP_CURRENT_ENTITY = "currentEntity";
    public static final Short ANZAHL_DK_NULL_VALUE = 0;
    private static final Calendar earliestDate = new GregorianCalendar(1950, Calendar.JANUARY, 1);

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(DetailWidget.class);

    //~ Instance fields --------------------------------------------------------

    protected Object currentEntity = null;
    private Collection<TkeyStrassenschluesselCustomBean> allStrassenschluessel;
    // ToDo configurable
    private int maxStringLength = 250;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.belis.gui.documentpanel.DocumentPanel panDokumente;
    private javax.swing.JPanel panMain;
    private javax.swing.JScrollPane scpMain;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new DetailWidget object.
     *
     * @param  broker  DOCUMENT ME!
     */
    public DetailWidget(final BelisBroker broker) {
        super(broker);
        initComponents();
        initContent();
        // binding
        final org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.dokumente}"),
                panDokumente,
                org.jdesktop.beansbinding.BeanProperty.create("dokumente"));
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();
        bindingGroup.addBinding(binding);
        bindingGroup.bind();
        // panMain.add(panStandort,BorderLayout.CENTER);
        // setAllPanelsVisible(false);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("PropertyChange: " + evt);
            LOG.debug("PropertyChange: " + evt.getPropertyName());
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("PropertyChange: " + evt.getOldValue());
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("PropertyChange: " + evt.getNewValue());
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void initContent() {
        try {
            allStrassenschluessel = CidsBroker.getInstance().getAllStrassenschluessel();
            if (LOG.isDebugEnabled()) {
                LOG.debug("Strassenschluessel size: " + allStrassenschluessel.size());
            }
        } catch (Exception ex) {
            LOG.error("Error while initializing all strassenschlussel.");
            allStrassenschluessel = new HashSet();
        }
        initAbzweigdosePanel();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  col  DOCUMENT ME!
     * @param  box  DOCUMENT ME!
     */
    private void createSortedCBoxModelFromCollection(final Collection<?> col, final JComboBox box) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("sorting collection: " + col);
        }
        try {
            if (box != null) {
                if (col != null) {
                    final Object[] objArr = col.toArray();
                    Arrays.sort(objArr, CriteriaStringComparator.getInstance());
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("sorted Collection:" + objArr);
                    }
                    box.setModel(new DefaultComboBoxModel(objArr));
                } else {
                    box.setModel(new DefaultComboBoxModel());
                }
            }
        } catch (Exception ex) {
            LOG.error("error while sorting collection", ex);
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void initAbzweigdosePanel() {
    }

    /**
     * Get the value of currentEntity.
     *
     * @return  the value of currentEntity
     */
    public Object getCurrentEntity() {
        return currentEntity;
    }

    /**
     * Set the value of currentEntity TODO change parameter type to DocumentContainer or BaseEntity.
     *
     * @param  currentEntity  new value of currentEntity
     */
    public void setCurrentEntity(final Object currentEntity) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("setCurrentEntity", new CurrentStackTrace());
        }
        final Object oldCurrentEntity = this.currentEntity;
        this.currentEntity = currentEntity;
        // Attention there is another block for the visiblity of the document panel
        // this must be here because the set must be created before it is set in the documentpanel
        if ((currentEntity != null) && (currentEntity instanceof DocumentContainer)
                    && (((DocumentContainer)currentEntity).getDokumente() == null)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Entity is DocumentContainer and Set == null. Creating Set");
            }
            ((DocumentContainer)currentEntity).setDokumente(new ArrayList<DmsUrlCustomBean>());

//            ((DocumentContainer)currentEntity).setDokumente(new TreeSet(
//                    new ReverseComparator(new EntityComparator(new ReverseComparator(new LeuchteComparator())))));
        }
        firePropertyChange(PROP_CURRENT_ENTITY, oldCurrentEntity, currentEntity);
        bindingGroup.unbind();
        bindingGroup.bind();
        panMain.removeAll();
        // TODO: TESTING ONLY...to REMOVE!
        // panMain.add(docPanel, BorderLayout.SOUTH);
        setAllPanelsVisible(false);
        // panMain.removeAll();
        // panMain.remove(panStandort);
        // panMain.remove(panLeuchte);
        // panMain.setLayout(new BorderLayout());
        if (currentEntity == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("current Entity is null");
            }
            StandortPanel.getInstance().setVisible(false);
            LeuchtePanel.getInstance().setVisible(false);
            this.repaint();
            return;
        }
        // validateTest();
        if (currentEntity instanceof TdtaStandortMastCustomBean) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("CurrentEntity is Standort");
            }
            // panMain.add(panStandort,BorderLayout.CENTER);
            final StandortPanel standortPanel = StandortPanel.getInstance();
            standortPanel.setCurrentEntity((TdtaStandortMastCustomBean)currentEntity);
            standortPanel.setElementsNull();

            panMain.add(standortPanel, BorderLayout.CENTER);
            standortPanel.setVisible(true);
        } else if (currentEntity instanceof TdtaLeuchtenCustomBean) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("CurrentEntity is Leuchte");
            }
            // panStandort.setVisible(true);
            // panMain.add(panLeuchte,BorderLayout.CENTER);
            panMain.add(LeuchtePanel.getInstance(), BorderLayout.CENTER);
            LOG.info("ParentNode: " + ((TdtaLeuchtenCustomBean)currentEntity).getStandort());
            if (((BelisBroker)broker).getWorkbenchWidget().isParentNodeMast(
                            ((BelisBroker)broker).getWorkbenchWidget().getSelectedTreeNode().getLastPathComponent())) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("ParentNode ist Mast");
                }
                LeuchtePanel.getInstance().setInheritedMastPropertiesEnabled(false);
            } else {
                if (broker.isInEditMode()) {
                    LeuchtePanel.getInstance().setInheritedMastPropertiesEnabled(true);
                }
            }
            LeuchtePanel.getInstance().setCurrentEntity((TdtaLeuchtenCustomBean)currentEntity);
            LeuchtePanel.getInstance().setVisible(true);
            LeuchtePanel.getInstance().setElementsNull();
//            docPanel.setFileList();
        } else if (currentEntity instanceof LeitungCustomBean) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("CurrentEntity is Leitung");
            }
            final LeitungPanel leitungPanel = LeitungPanel.getInstance();

            leitungPanel.setCurrentEntity((LeitungCustomBean)currentEntity);
            leitungPanel.setElementsNull();

            panMain.add(leitungPanel, BorderLayout.CENTER);
            leitungPanel.setVisible(true);
        } else if (currentEntity instanceof AbzweigdoseCustomBean) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("CurrentEntity is Abzweigdose");
            }
            panMain.add(AbzweigdosePanel.getInstance(), BorderLayout.CENTER);
            AbzweigdosePanel.getInstance().setVisible(true);
        } else if (currentEntity instanceof MauerlascheCustomBean) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("CurrentEntity is Mauerlasche");
            }

            final MauerlaschePanel mauerlaschePanel = MauerlaschePanel.getInstance();

            mauerlaschePanel.setCurrentEntity((MauerlascheCustomBean)currentEntity);
            mauerlaschePanel.setElementsNull();

            panMain.add(mauerlaschePanel, BorderLayout.CENTER);
            mauerlaschePanel.setVisible(true);
        } else if (currentEntity instanceof SchaltstelleCustomBean) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("CurrentEntity is Schaltstelle");
            }

            final SchaltstellePanel schaltstellePanel = SchaltstellePanel.getInstance();

            schaltstellePanel.setCurrentEntity((SchaltstelleCustomBean)currentEntity);
            schaltstellePanel.setElementsNull();

            panMain.add(schaltstellePanel, BorderLayout.CENTER);
            schaltstellePanel.setVisible(true);
        } else {
            LOG.warn("no panel for entity available");
        }
        if (currentEntity instanceof DocumentContainer) {
            final DocumentContainer dc = (DocumentContainer)currentEntity;
            panMain.add(panDokumente, BorderLayout.SOUTH);
            panDokumente.setVisible(true);
        } else {
            panDokumente.setVisible(false);
        }
        this.repaint();
    }
    /**
     * private void validateTest() { for (Binding curBinding : bindingGroup.getBindings()) { if (currentEntity
     * instanceof Standort && panStandort.isAncestorOf((Component) curBinding.getTargetObject())) { log.debug("checking
     * binding"); if (curBinding.getValidator() != null) { try { log.debug("Validator available. Property to check: " +
     * curBinding.getTargetProperty()); log.debug("Property value: " +
     * curBinding.getTargetProperty().getValue(curBinding.getTargetObject())); final Result valResult =
     * curBinding.getValidator().validate(curBinding.getTargetProperty().getValue(curBinding.getTargetObject()));
     * log.debug("Validation result: " + valResult); if (valResult != null) { for (BindingListener bindingListener :
     * bindingGroup.getBindingListeners()) { bindingListener.syncFailed(curBinding,
     * SyncFailure.validationFailure(valResult)); } } } catch (Exception ex) { log.error("Error while validating: ",
     * ex); } } } } }
     *
     * @param  visible  DOCUMENT ME!
     */
    private void setAllPanelsVisible(final boolean visible) {
        StandortPanel.getInstance().setVisible(visible);
        LeuchtePanel.getInstance().setVisible(visible);
        LeitungPanel.getInstance().setVisible(visible);
        MauerlaschePanel.getInstance().setVisible(visible);
        SchaltstellePanel.getInstance().setVisible(visible);
        AbzweigdosePanel.getInstance().setVisible(visible);
    }

    @Override
    public void clearComponent() {
        super.clearComponent();
        setCurrentEntity(null);
        // setAllPanelsVisible(false);
    }

    @Override
    public void setWidgetEditable(final boolean isEditable) {
        super.setWidgetEditable(isEditable);
        // Standort fields
        StandortPanel.getInstance().setPanelEditable(isEditable);

        // Leuchte fields
        LeuchtePanel.getInstance().setPanelEditable(isEditable);

        // Leitungs fields
        LeitungPanel.getInstance().setPanelEditable(isEditable);

        // Mauerlasche fields
        MauerlaschePanel.getInstance().setPanelEditable(isEditable);

        // Schaltstelle fields
        SchaltstellePanel.getInstance().setPanelEditable(isEditable);

        // doc panel
        panDokumente.setEditable(isEditable);
    }

    /**
     * DOCUMENT ME!
     */
    private void commitEdits() {
        if (currentEntity != null) {
            if (currentEntity instanceof TdtaLeuchtenCustomBean) {
                LeuchtePanel.getInstance().commitEdits();
            } else if (currentEntity instanceof TdtaStandortMastCustomBean) {
                StandortPanel.getInstance().commitEdits();
            } else if (currentEntity instanceof SchaltstelleCustomBean) {
                SchaltstellePanel.getInstance().commitEdits();
            } else if (currentEntity instanceof MauerlascheCustomBean) {
                MauerlaschePanel.getInstance().commitEdits();
            }
        }
    }

    /**
     * Creates new form EntityDetailWidget.
     */
    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panDokumente = new de.cismet.belis.gui.documentpanel.DocumentPanel();
        scpMain = new javax.swing.JScrollPane();
        panMain = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        panMain.setLayout(new java.awt.BorderLayout());
        scpMain.setViewportView(panMain);

        add(scpMain, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public DocumentPanel getPanDokumente() {
        return panDokumente;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  panDokumente  DOCUMENT ME!
     */
    public void setPanDokumente(final DocumentPanel panDokumente) {
        this.panDokumente = panDokumente;
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
    public int getStatus() {
        commitEdits();
        return super.getStatus();
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    class ShortIntegerConverter extends Converter<Short, Integer> {

        //~ Methods ------------------------------------------------------------

        @Override
        public Integer convertForward(final Short value) {
            if (value != null) {
                return value.intValue();
            } else {
                return null;
            }
        }

        @Override
        public Short convertReverse(final Integer value) {
            if (value != null) {
                return value.shortValue();
            } else {
                return null;
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public class NotNullValidator extends Validator {

        //~ Instance fields ----------------------------------------------------

        String elementname = "Element";

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new NotNullValidator object.
         */
        public NotNullValidator() {
        }

        /**
         * Creates a new NotNullValidator object.
         *
         * @param  elementname  DOCUMENT ME!
         */
        public NotNullValidator(final String elementname) {
            this.elementname = elementname;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public Result validate(final Object value) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("NotNullValidatorcheck: " + value);
            }
            if (value != null) {
                return null;
            } else {
                return new Result("code", elementname + " muss gesetzt werden.");
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public class StringMaxLengthValidator extends Validator<String> {

        //~ Instance fields ----------------------------------------------------

        private int maxLength;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new StringMaxLengthValidator object.
         */
        public StringMaxLengthValidator() {
            this.maxLength = maxStringLength;
        }

        /**
         * Creates a new StringMaxLengthValidator object.
         *
         * @param  maxLength  DOCUMENT ME!
         */
        public StringMaxLengthValidator(final int maxLength) {
            this.maxLength = maxLength;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public Result validate(final String value) {
            if ((value != null) && (value.length() > maxLength)) {
                return new Result("code", "Der Text darf nicht länger als " + maxLength + " Zeichen sein.");
            }
            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public class PLZValidator extends Validator<String> {

        //~ Methods ------------------------------------------------------------

        @Override
        public Result validate(final String value) {
            if (value != null) {
                try {
                    if (Integer.parseInt(value) > -1) {
                        if (value.length() == 5) {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("plz valide");
                            }
                            return null;
                        } else {
                            return new Result("code", "Postleitzahl muss genau 5 Stellen haben.");
                        }
                    } else {
                        return new Result("code", "Postleitzahl darf nicht negativ sein.");
                    }
                } catch (NumberFormatException ex) {
                    if (value.equals("")) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Postleitzahl is empty", ex);
                        }
                    } else {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Postleitzahl no number", ex);
                        }
                        return new Result("code", "Postleitzahl darf nur aus Ziffern bestehen.");
                    }
                }
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("plz valide");
            }
            return null;
        }
    }
    /**
     * ToDo JXDatePicker Visualisierung.
     *
     * @version  $Revision$, $Date$
     */
    public class DateValidator extends Validator<Date> {

        //~ Methods ------------------------------------------------------------

        @Override
        public Result validate(final Date value) {
            if ((value != null) && (value.compareTo(earliestDate.getTime()) < 0)) {
                return new Result("code", "Datum muss nach dem 01.01.1950 sein.");
            }
            return null;
        }
    }
}
