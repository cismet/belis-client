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
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import java.awt.BorderLayout;
import java.awt.Component;

import java.beans.PropertyChangeEvent;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;

import de.cismet.belis.broker.BelisBroker;
import de.cismet.belis.broker.CidsBroker;

import de.cismet.belis.gui.documentpanel.DocumentPanel;
import de.cismet.belis.gui.widget.detailWidgetPanels.LeuchtePanel;
import de.cismet.belis.gui.widget.detailWidgetPanels.MauerlaschePanel;
import de.cismet.belis.gui.widget.detailWidgetPanels.ObjectToKeyStringConverter;
import de.cismet.belis.gui.widget.detailWidgetPanels.ObjectToPkConverter;
import de.cismet.belis.gui.widget.detailWidgetPanels.SchaltstellePanel;
import de.cismet.belis.gui.widget.detailWidgetPanels.StandortPanel;

import de.cismet.belisEE.exception.ActionNotSuccessfulException;

import de.cismet.belisEE.util.CriteriaStringComparator;

import de.cismet.cids.custom.beans.belis.AbzweigdoseCustomBean;
import de.cismet.cids.custom.beans.belis.DmsUrlCustomBean;
import de.cismet.cids.custom.beans.belis.LeitungCustomBean;
import de.cismet.cids.custom.beans.belis.LeitungstypCustomBean;
import de.cismet.cids.custom.beans.belis.MaterialLeitungCustomBean;
import de.cismet.cids.custom.beans.belis.MaterialMauerlascheCustomBean;
import de.cismet.cids.custom.beans.belis.MauerlascheCustomBean;
import de.cismet.cids.custom.beans.belis.QuerschnittCustomBean;
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
    // private final GregorianCalendar calender = new GregorianCalendar();
    // private final Date smallestAllowedDate = new Date(0);
    private final String comboBoxNullValue = "Wert auswählen...";
    private Collection<Binding> validationState = new HashSet<Binding>();
    private Collection<LeitungstypCustomBean> leitungstypen = new HashSet<LeitungstypCustomBean>();
    private boolean isTriggerd = false;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbxLeitungLeitungstyp;
    private javax.swing.JComboBox cbxLeitungMaterial;
    private javax.swing.JComboBox cbxLeitungQuerschnitt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblAbzweigdose;
    private javax.swing.JLabel lblLeitungLeitungstyp;
    private javax.swing.JLabel lblLeitungMaterial;
    private javax.swing.JLabel lblLeitungQuerschnitt;
    private javax.swing.JPanel panAbzweidose;
    private javax.swing.JPanel panContent1;
    private de.cismet.belis.gui.documentpanel.DocumentPanel panDokumente;
    private javax.swing.JPanel panLeitung;
    private javax.swing.JPanel panMain;
    private javax.swing.JScrollPane scpMain;
    private javax.swing.JSeparator sprLeitung;
    private javax.swing.JSeparator sprLeuchte1;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
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
        bindingGroup.addBinding(binding);
        bindingGroup.bind();
        // ToDo ugly workaround
        if ((leitungstypen != null) && (leitungstypen.size() > 0)) {
            for (final LeitungstypCustomBean curLeitungstyp : leitungstypen) {
                if (curLeitungstyp.getId().equals(1L)) {
                    ((BelisBroker)broker).setLastLeitungstyp(curLeitungstyp);
                }
            }
        }
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
        initLeitungPanel();
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
    private void initLeitungPanel() {
        try {
            final Collection<MaterialLeitungCustomBean> material = CidsBroker.getInstance().getAllMaterialLeitung();
            createSortedCBoxModelFromCollection(material, cbxLeitungMaterial);
        } catch (ActionNotSuccessfulException ex) {
            cbxLeitungMaterial.setModel(new DefaultComboBoxModel());
        }
        cbxLeitungMaterial.setSelectedItem(null);

        try {
            leitungstypen = CidsBroker.getInstance().getAllLeitungstypen();
            createSortedCBoxModelFromCollection(leitungstypen, cbxLeitungLeitungstyp);
        } catch (ActionNotSuccessfulException ex) {
            cbxLeitungLeitungstyp.setModel(new DefaultComboBoxModel());
        }
        cbxLeitungLeitungstyp.setSelectedItem(null);

        try {
            final Collection<QuerschnittCustomBean> querschnitt = CidsBroker.getInstance().getAllQuerschnitte();
            createSortedCBoxModelFromCollection(querschnitt, cbxLeitungQuerschnitt);
        } catch (ActionNotSuccessfulException ex) {
            cbxLeitungQuerschnitt.setModel(new DefaultComboBoxModel());
        }
        cbxLeitungQuerschnitt.setSelectedItem(null);
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
            panMain.add(panLeitung, BorderLayout.CENTER);
            panLeitung.setVisible(true);
        } else if (currentEntity instanceof AbzweigdoseCustomBean) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("CurrentEntity is Abzweigdose");
            }
            panMain.add(panAbzweidose, BorderLayout.CENTER);
            panLeitung.setVisible(true);
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
        panLeitung.setVisible(visible);
        MauerlaschePanel.getInstance().setVisible(visible);
        SchaltstellePanel.getInstance().setVisible(visible);
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
        cbxLeitungLeitungstyp.setEnabled(isEditable);
        cbxLeitungMaterial.setEnabled(isEditable);
        cbxLeitungQuerschnitt.setEnabled(isEditable);

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
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        panLeitung = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblLeitungMaterial = new javax.swing.JLabel();
        lblLeitungLeitungstyp = new javax.swing.JLabel();
        lblLeitungQuerschnitt = new javax.swing.JLabel();
        cbxLeitungLeitungstyp = new javax.swing.JComboBox();
        cbxLeitungMaterial = new javax.swing.JComboBox();
        cbxLeitungQuerschnitt = new javax.swing.JComboBox();
        sprLeitung = new javax.swing.JSeparator();
        panAbzweidose = new javax.swing.JPanel();
        panContent1 = new javax.swing.JPanel();
        lblAbzweigdose = new javax.swing.JLabel();
        sprLeuchte1 = new javax.swing.JSeparator();
        panDokumente = new de.cismet.belis.gui.documentpanel.DocumentPanel();
        scpMain = new javax.swing.JScrollPane();
        panMain = new javax.swing.JPanel();

        jLabel1.setFont(new java.awt.Font("DejaVu Sans", 1, 13));                          // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/22/leitung.png"))); // NOI18N
        jLabel1.setText("Leitung");                                                        // NOI18N

        lblLeitungMaterial.setText("Material:"); // NOI18N

        lblLeitungLeitungstyp.setText("Leitungstyp:"); // NOI18N

        lblLeitungQuerschnitt.setText("Querschnitt:"); // NOI18N

        cbxLeitungLeitungstyp.setEnabled(false);
        cbxLeitungLeitungstyp.setRenderer(new DefaultListCellRenderer() {

                @Override
                public Component getListCellRendererComponent(
                        final JList list,
                        final Object value,
                        final int index,
                        final boolean isSelected,
                        final boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value == null) {
                        setText(comboBoxNullValue);
                    } else if (value instanceof de.cismet.cids.custom.beans.belis.LeitungstypCustomBean) {
                        final de.cismet.cids.custom.beans.belis.LeitungstypCustomBean lt =
                            (de.cismet.cids.custom.beans.belis.LeitungstypCustomBean)value;
                        setText(lt.getBezeichnung());
                    }
                    return this;
                }
            });

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.fk_leitungstyp}"),
                cbxLeitungLeitungstyp,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbxLeitungMaterial.setEnabled(false);
        cbxLeitungMaterial.setRenderer(new DefaultListCellRenderer() {

                @Override
                public Component getListCellRendererComponent(
                        final JList list,
                        final Object value,
                        final int index,
                        final boolean isSelected,
                        final boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value == null) {
                        setText(comboBoxNullValue);
                    } else if (value instanceof de.cismet.cids.custom.beans.belis.MaterialLeitungCustomBean) {
                        final de.cismet.cids.custom.beans.belis.MaterialLeitungCustomBean mt =
                            (de.cismet.cids.custom.beans.belis.MaterialLeitungCustomBean)value;
                        setText(mt.getBezeichnung());
                    }
                    return this;
                }
            });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.material}"),
                cbxLeitungMaterial,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbxLeitungQuerschnitt.setEnabled(false);
        cbxLeitungQuerschnitt.setRenderer(new DefaultListCellRenderer() {

                @Override
                public Component getListCellRendererComponent(
                        final JList list,
                        final Object value,
                        final int index,
                        final boolean isSelected,
                        final boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value == null) {
                        setText(comboBoxNullValue);
                    } else if (value instanceof de.cismet.cids.custom.beans.belis.QuerschnittCustomBean) {
                        final de.cismet.cids.custom.beans.belis.QuerschnittCustomBean qt =
                            (de.cismet.cids.custom.beans.belis.QuerschnittCustomBean)value;
                        if (qt.getGroesse() != null) {
                            setText(qt.getGroesse().toString());
                        } else {
                            setText("");
                        }
                    }
                    return this;
                }
            });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.querschnitt}"),
                cbxLeitungQuerschnitt,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        final javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                jPanel2Layout.createSequentialGroup().addContainerGap().addGroup(
                    jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                        lblLeitungLeitungstyp).addComponent(lblLeitungMaterial).addComponent(lblLeitungQuerschnitt))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                        cbxLeitungQuerschnitt,
                        0,
                        156,
                        Short.MAX_VALUE).addComponent(cbxLeitungMaterial, 0, 156, Short.MAX_VALUE).addComponent(
                        cbxLeitungLeitungstyp,
                        javax.swing.GroupLayout.Alignment.TRAILING,
                        0,
                        156,
                        Short.MAX_VALUE)).addContainerGap()));
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                jPanel2Layout.createSequentialGroup().addContainerGap().addGroup(
                    jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblLeitungLeitungstyp).addComponent(
                        cbxLeitungLeitungstyp,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        21,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblLeitungMaterial).addComponent(
                        cbxLeitungMaterial,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        22,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        lblLeitungQuerschnitt).addComponent(
                        cbxLeitungQuerschnitt,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        22,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap(
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE)));

        jPanel2Layout.linkSize(
            javax.swing.SwingConstants.VERTICAL,
            new java.awt.Component[] { cbxLeitungLeitungstyp, cbxLeitungMaterial, cbxLeitungQuerschnitt });

        final javax.swing.GroupLayout panLeitungLayout = new javax.swing.GroupLayout(panLeitung);
        panLeitung.setLayout(panLeitungLayout);
        panLeitungLayout.setHorizontalGroup(
            panLeitungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                javax.swing.GroupLayout.Alignment.TRAILING,
                panLeitungLayout.createSequentialGroup().addContainerGap().addGroup(
                    panLeitungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(
                        jPanel2,
                        javax.swing.GroupLayout.Alignment.LEADING,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        Short.MAX_VALUE).addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                        sprLeitung,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        270,
                        Short.MAX_VALUE)).addContainerGap()));
        panLeitungLayout.setVerticalGroup(
            panLeitungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                panLeitungLayout.createSequentialGroup().addContainerGap().addComponent(jLabel1).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
                    sprLeitung,
                    javax.swing.GroupLayout.PREFERRED_SIZE,
                    10,
                    javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
                    jPanel2,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE).addContainerGap()));

        final javax.swing.GroupLayout panContent1Layout = new javax.swing.GroupLayout(panContent1);
        panContent1.setLayout(panContent1Layout);
        panContent1Layout.setHorizontalGroup(
            panContent1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                411,
                Short.MAX_VALUE));
        panContent1Layout.setVerticalGroup(
            panContent1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                517,
                Short.MAX_VALUE));

        lblAbzweigdose.setFont(new java.awt.Font("DejaVu Sans", 1, 13));                       // NOI18N
        lblAbzweigdose.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/22/abzweigdose.png"))); // NOI18N
        lblAbzweigdose.setText("Abzweigdose/Zugkasten");                                       // NOI18N

        final javax.swing.GroupLayout panAbzweidoseLayout = new javax.swing.GroupLayout(panAbzweidose);
        panAbzweidose.setLayout(panAbzweidoseLayout);
        panAbzweidoseLayout.setHorizontalGroup(
            panAbzweidoseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                panAbzweidoseLayout.createSequentialGroup().addContainerGap().addGroup(
                    panAbzweidoseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                        panContent1,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        Short.MAX_VALUE).addComponent(lblAbzweigdose).addComponent(
                        sprLeuchte1,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        411,
                        Short.MAX_VALUE)).addContainerGap()));
        panAbzweidoseLayout.setVerticalGroup(
            panAbzweidoseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                panAbzweidoseLayout.createSequentialGroup().addContainerGap().addComponent(lblAbzweigdose)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
                    sprLeuchte1,
                    javax.swing.GroupLayout.PREFERRED_SIZE,
                    10,
                    javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
                    panContent1,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE).addContainerGap()));

        setLayout(new java.awt.BorderLayout());

        panMain.setLayout(new java.awt.BorderLayout());
        scpMain.setViewportView(panMain);

        add(scpMain, java.awt.BorderLayout.CENTER);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

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
