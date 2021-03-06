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

import org.jdesktop.beansbinding.BindingGroup;

import java.awt.Component;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.AbstractTableModel;

import de.cismet.belis.arbeitsprotokollwizard.AbstractArbeitsprotokollWizard;

import de.cismet.belis.broker.BelisBroker;

import de.cismet.belis.gui.DateToDateConverter;

import de.cismet.belis.util.RendererTools;

import de.cismet.cids.custom.beans.belis2.ArbeitsauftragCustomBean;
import de.cismet.cids.custom.beans.belis2.ArbeitsprotokollCustomBean;
import de.cismet.cids.custom.beans.belis2.ArbeitsprotokollaktionCustomBean;
import de.cismet.cids.custom.beans.belis2.ArbeitsprotokollstatusCustomBean;

import de.cismet.cids.utils.multibean.EmbeddedMultiBeanDisplay;
import de.cismet.cids.utils.multibean.MultiBeanHelper;

/**
 * DOCUMENT ME!
 *
 * @author   Gilles Baatz
 * @version  $Revision$, $Date$
 */
public class ArbeitsprotokollPanel extends AbstractDetailWidgetPanel<ArbeitsprotokollCustomBean> {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ArbeitsprotokollPanel.class);

    private static final Class[] COLUMN_CLASSES = {
            String.class,
            String.class,
            String.class
        };
    private static final String[] COLUMN_NAMES = { "Änderung", "von", "zu" };

    //~ Instance fields --------------------------------------------------------

    private MultiBeanHelper mbh = new MultiBeanHelper();
    private SwingWorker previousSwingworker = null;
    private ArbeitsauftragCustomBean currentArbeitsauftrag;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbxStatus;
    private org.jdesktop.swingx.JXDatePicker dapDatum;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel lblArbeitsprotokoll;
    private javax.swing.JLabel lblBemerkung;
    private javax.swing.JLabel lblDatum;
    private javax.swing.JLabel lblMaterial;
    private javax.swing.JLabel lblMonteur;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JPanel panActions;
    private javax.swing.JPanel panAktionen;
    private javax.swing.JPanel panDetails;
    private javax.swing.JScrollPane scrBemerkungen;
    private javax.swing.JScrollPane scrMaterial;
    private javax.swing.JTable tblInfobausteine;
    private javax.swing.JTextArea txaBemerkungen;
    private javax.swing.JTextArea txaMaterial;
    private javax.swing.JTextField txfMonteur;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form ArbeitsprotokollPanel.
     */
    public ArbeitsprotokollPanel() {
        super("ARBEITSROTOKOLL_PANEL");
        initComponents();
        initComponentToLabelMap();
        initPanel();

        EmbeddedMultiBeanDisplay.registerComponentForProperty(
            txfMonteur,
            ArbeitsprotokollCustomBean.PROP__MONTEUR,
            mbh);
        EmbeddedMultiBeanDisplay.registerComponentForProperty(dapDatum, ArbeitsprotokollCustomBean.PROP__DATUM, mbh);
        EmbeddedMultiBeanDisplay.registerComponentForProperty(
            cbxStatus,
            ArbeitsprotokollCustomBean.PROP__FK_STATUS,
            mbh);
        EmbeddedMultiBeanDisplay.registerComponentForProperty(
            txaBemerkungen,
            ArbeitsprotokollCustomBean.PROP__BEMERKUNG,
            mbh);
        EmbeddedMultiBeanDisplay.registerComponentForProperty(
            txaMaterial,
            ArbeitsprotokollCustomBean.PROP__MATERIAL,
            mbh);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public JLabel getTabLabel() {
        return lblArbeitsprotokoll;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        lblArbeitsprotokoll = new javax.swing.JLabel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panDetails = new javax.swing.JPanel();
        lblMonteur = new javax.swing.JLabel();
        lblDatum = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        lblBemerkung = new javax.swing.JLabel();
        lblMaterial = new javax.swing.JLabel();
        dapDatum = new org.jdesktop.swingx.JXDatePicker();
        cbxStatus = BelisBroker.createKeyTableComboBox(ArbeitsprotokollstatusCustomBean.TABLE);
        scrBemerkungen = new javax.swing.JScrollPane();
        txaBemerkungen = new javax.swing.JTextArea();
        scrMaterial = new javax.swing.JScrollPane();
        txaMaterial = new javax.swing.JTextArea();
        txfMonteur = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        panAktionen = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        panActions = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblInfobausteine = new javax.swing.JTable();

        lblArbeitsprotokoll.setFont(new java.awt.Font("DejaVu Sans", 1, 13));                       // NOI18N
        lblArbeitsprotokoll.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/16/arbeitsprotokoll.png"))); // NOI18N
        lblArbeitsprotokoll.setText("Arbeitsprotokoll");                                            // NOI18N

        setLayout(new java.awt.GridBagLayout());

        jSplitPane1.setResizeWeight(1.0);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel1.setText("Arbeitsprotokoll Details");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel1.add(jLabel1, gridBagConstraints);

        panDetails.setLayout(new java.awt.GridBagLayout());

        lblMonteur.setText("Monteur:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panDetails.add(lblMonteur, gridBagConstraints);

        lblDatum.setText("Datum:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panDetails.add(lblDatum, gridBagConstraints);

        lblStatus.setText("Status:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panDetails.add(lblStatus, gridBagConstraints);

        lblBemerkung.setText("Bemerkung:");
        lblBemerkung.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panDetails.add(lblBemerkung, gridBagConstraints);

        lblMaterial.setText("Material:");
        lblMaterial.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panDetails.add(lblMaterial, gridBagConstraints);

        dapDatum.setEnabled(false);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.datum}"),
                dapDatum,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setConverter(new DateToDateConverter());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panDetails.add(dapDatum, gridBagConstraints);

        cbxStatus.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.fk_status}"),
                cbxStatus,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panDetails.add(cbxStatus, gridBagConstraints);

        scrBemerkungen.setEnabled(false);

        txaBemerkungen.setEditable(false);
        txaBemerkungen.setColumns(20);
        txaBemerkungen.setRows(5);
        txaBemerkungen.setMinimumSize(new java.awt.Dimension(240, 85));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.bemerkung}"),
                txaBemerkungen,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        scrBemerkungen.setViewportView(txaBemerkungen);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panDetails.add(scrBemerkungen, gridBagConstraints);

        scrMaterial.setEnabled(false);

        txaMaterial.setEditable(false);
        txaMaterial.setColumns(20);
        txaMaterial.setRows(5);
        txaMaterial.setMinimumSize(new java.awt.Dimension(240, 85));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.material}"),
                txaMaterial,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        scrMaterial.setViewportView(txaMaterial);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panDetails.add(scrMaterial, gridBagConstraints);

        txfMonteur.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.monteur}"),
                txfMonteur,
                org.jdesktop.beansbinding.BeanProperty.create("text"),
                "");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panDetails.add(txfMonteur, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel1.add(panDetails, gridBagConstraints);

        jSplitPane1.setLeftComponent(jPanel1);

        jPanel2.setMaximumSize(new java.awt.Dimension(500, 2147483647));
        jPanel2.setMinimumSize(new java.awt.Dimension(500, 120));
        jPanel2.setPreferredSize(new java.awt.Dimension(500, 297));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        panAktionen.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        panAktionen.add(jSeparator2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAktionen.add(panActions, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel2.add(panAktionen, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel2.setText("Aktionen");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel2.add(jLabel2, gridBagConstraints);

        jScrollPane1.setPreferredSize(new java.awt.Dimension(100, 200));

        tblInfobausteine.setModel(new AktionenTableModel());
        tblInfobausteine.setMinimumSize(new java.awt.Dimension(60, 200));
        jScrollPane1.setViewportView(tblInfobausteine);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 4.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jScrollPane1, gridBagConstraints);

        jSplitPane1.setRightComponent(jPanel2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jSplitPane1, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    @Override
    final void initPanel() {
    }

    /**
     * DOCUMENT ME!
     */
    @Override
    public void commitEdits() {
    }

    @Override
    final void initComponentToLabelMap() {
    }

    @Override
    public void setElementsNull() {
    }

    @Override
    public void setPanelEditable(final boolean isEditable) {
        setEditable(isEditable);
        RendererTools.setEditable(dapDatum, isEditable);
        RendererTools.setEditable(cbxStatus, isEditable);
        RendererTools.setEditable(txaBemerkungen, isEditable);
        RendererTools.setEditable(txfMonteur, isEditable);
        RendererTools.setEditable(txaMaterial, isEditable);
        for (final Component comp : panActions.getComponents()) {
            comp.setEnabled(isEditable);
        }
    }

    /**
     * DOCUMENT ME!
     */
    public void refreshAktionen() {
        SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    ((AktionenTableModel)tblInfobausteine.getModel()).fireTableDataChanged();
                    validate();
                    repaint();
                }
            });
    }

    /**
     * DOCUMENT ME!
     *
     * @param  currentArbeitsauftrag  DOCUMENT ME!
     * @param  currentEntities        DOCUMENT ME!
     */
    public void setCurrentEntities(final ArbeitsauftragCustomBean currentArbeitsauftrag,
            final Collection<ArbeitsprotokollCustomBean> currentEntities) {
        final ArbeitsprotokollCustomBean dummyBean = ArbeitsprotokollCustomBean.createNew();
        setCurrentEntity(dummyBean);
        this.currentArbeitsauftrag = currentArbeitsauftrag;
        if ((previousSwingworker != null) && !previousSwingworker.isDone()) {
            previousSwingworker.cancel(true);
        }
        previousSwingworker = new SwingWorker<ArbeitsprotokollCustomBean, Void>() {

                @Override
                protected ArbeitsprotokollCustomBean doInBackground() throws Exception {
                    mbh.setDummyBean(dummyBean);
                    mbh.setBeans((Collection)currentEntities);
                    return dummyBean;
                }

                @Override
                protected void done() {
                    ArbeitsprotokollCustomBean dummyBean = null;
                    try {
                        dummyBean = get();
                        if (mbh.getBeans().equals(currentEntities)) {
                            ((AktionenTableModel)tblInfobausteine.getModel()).fireTableDataChanged();
                        }
                        boolean allSame = true;
                        ArbeitsprotokollCustomBean.ChildType allSameChildType = null;
                        boolean first = true;
                        for (final ArbeitsprotokollCustomBean protokoll : currentEntities) {
                            if (first) {
                                allSameChildType = protokoll.getChildType();
                                first = false;
                            }
                            if (protokoll.getChildType() != allSameChildType) {
                                allSame = false;
                            }
                        }

                        refreshWizards(allSame ? allSameChildType : null);

                        validate();
                        repaint();
                    } catch (InterruptedException ex) {
                        mbh.setDummyBean(null);
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("canceled");
                        }
                    } catch (final Exception ex) {
                        LOG.warn(ex, ex);
                    }
                }
            };
        previousSwingworker.execute();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  childType  DOCUMENT ME!
     */
    public void refreshWizards(final ArbeitsprotokollCustomBean.ChildType childType) {
        final Collection<AbstractArbeitsprotokollWizard> allWizards = new ArrayList<AbstractArbeitsprotokollWizard>();
        if (childType != null) {
            final Collection<AbstractArbeitsprotokollWizard> entityWizards = BelisBroker.getInstance()
                        .getWizardsActionsForEntity(childType);
            if (entityWizards != null) {
                allWizards.addAll(entityWizards);
            }
        }
        allWizards.addAll(BelisBroker.getInstance().getWizardsActionsForEntity(null));
        panActions.removeAll();
        if (mbh.getDummyBean() != null) {
//            if (currentEntity.getN_aktionen().isEmpty()) {
            for (final AbstractArbeitsprotokollWizard wizard : allWizards) {
                final JButton actionButton = new JButton(wizard.getAction());
                panActions.add(actionButton);
                actionButton.setEnabled(!isEditable());
                wizard.setArbeitsauftrag(currentArbeitsauftrag);
                wizard.setProtokolle((Collection)mbh.getBeans());
            }
//            } else {
//            }
        }
    }

    @Override
    public void setCurrentEntity(final ArbeitsprotokollCustomBean currentEntity) {
        super.setCurrentEntity(currentEntity);

        if (currentEntity != null) {
            refreshWizards(currentEntity.getChildType());
        } else {
            refreshWizards(null);
        }

        validate();
        repaint();
        ((AktionenTableModel)tblInfobausteine.getModel()).fireTableDataChanged();
    }

    @Override
    protected BindingGroup getBindingGroup() {
        return bindingGroup;
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    class AktionenTableModel extends AbstractTableModel {

        //~ Methods ------------------------------------------------------------

        @Override
        public int getRowCount() {
            if ((currentEntity != null) && (((ArbeitsprotokollCustomBean)currentEntity).getN_aktionen() != null)) {
                return ((ArbeitsprotokollCustomBean)currentEntity).getN_aktionen().size();
            } else {
                return 0;
            }
        }

        @Override
        public int getColumnCount() {
            return COLUMN_NAMES.length;
        }

        @Override
        public Object getValueAt(final int rowIndex, final int columnIndex) {
            if (currentEntity != null) {
                final ArbeitsprotokollaktionCustomBean aktion = getRowObject(rowIndex);
                if (columnIndex == 0) {
                    return aktion.getAenderung();
                } else if (columnIndex == 1) {
                    return aktion.getAlt();
                } else if (columnIndex == 2) {
                    return aktion.getNeu();
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }

        /**
         * DOCUMENT ME!
         *
         * @param   rowIndex  DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        private ArbeitsprotokollaktionCustomBean getRowObject(final int rowIndex) {
            final ArbeitsprotokollCustomBean protokoll = (ArbeitsprotokollCustomBean)currentEntity;
            protokoll.getN_aktionen();
            final Collection<ArbeitsprotokollaktionCustomBean> protokolle = protokoll.getN_aktionen();
            final ArbeitsprotokollaktionCustomBean aktion =
                protokolle.toArray(new ArbeitsprotokollaktionCustomBean[0])[rowIndex];
            return aktion;
        }

        @Override
        public Class<?> getColumnClass(final int columnIndex) {
            return COLUMN_CLASSES[columnIndex];
        }

        @Override
        public String getColumnName(final int columnIndex) {
            return COLUMN_NAMES[columnIndex];
        }

        @Override
        public boolean isCellEditable(final int rowIndex, final int columnIndex) {
            return false;
        }
    }
}
