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
import org.jdesktop.swingx.JXTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import de.cismet.belis.gui.DateToStringConverter;

import de.cismet.belis.util.RendererTools;

import de.cismet.cids.custom.beans.belis2.ArbeitsauftragCustomBean;
import de.cismet.cids.custom.beans.belis2.ArbeitsprotokollCustomBean;
import de.cismet.cids.custom.beans.belis2.ArbeitsprotokollstatusCustomBean;
import de.cismet.cids.custom.beans.belis2.TdtaStandortMastCustomBean;
import de.cismet.cids.custom.beans.belis2.VeranlassungCustomBean;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @author   Gilles Baatz
 * @version  $Revision$, $Date$
 */
public class ArbeitsauftragPanel extends AbstractDetailWidgetPanel<ArbeitsauftragCustomBean>
        implements ListSelectionListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ArbeitsauftragPanel.class);

    private static final String[] COLUMN_NAMES = {
            "Herkunft",
            "Fachobjekt",
            "Kennzeichnung",
            "Bearbeiter",
            "Position",
            "Status"
        };

    private static final Class[] COLUMN_CLASSES = {
            String.class,
            String.class,
            String.class,
            String.class,
            String.class,
            String.class,
        };

    //~ Instance fields --------------------------------------------------------

    private boolean isEditable = false;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.belis.gui.widget.detailWidgetPanels.ArbeitsprotokollPanel arbeitsprotokollPanel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblArbeitsauftrag;
    private javax.swing.JLabel lblDatum;
    private javax.swing.JLabel lblDatumValue;
    private javax.swing.JLabel lblNummer;
    private javax.swing.JLabel lblNummerValue;
    private javax.swing.JLabel lblUser;
    private javax.swing.JLabel lblUserValue;
    private javax.swing.JLabel lblZugewiesenAn;
    private javax.swing.JTextField txtZugewiesenAn;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form LeuchtePanel.
     */
    public ArbeitsauftragPanel() {
        super("ARBEITSAUFTRAG_PANEL");
        initComponents();
        initComponentToLabelMap();
        initPanel();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public JLabel getTabLabel() {
        return lblArbeitsauftrag;
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

        lblArbeitsauftrag = new javax.swing.JLabel();
        lblUserValue = new javax.swing.JLabel();
        lblDatumValue = new javax.swing.JLabel();
        lblDatum = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblNummer = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        arbeitsprotokollPanel1 = new de.cismet.belis.gui.widget.detailWidgetPanels.ArbeitsprotokollPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new JXTable();
        jSeparator1 = new javax.swing.JSeparator();
        lblZugewiesenAn = new javax.swing.JLabel();
        txtZugewiesenAn = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        lblNummerValue = new javax.swing.JLabel();

        lblArbeitsauftrag.setFont(new java.awt.Font("DejaVu Sans", 1, 13));                       // NOI18N
        lblArbeitsauftrag.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/16/arbeitsauftrag.png"))); // NOI18N
        lblArbeitsauftrag.setText("Arbeitsauftrag");                                              // NOI18N

        setLayout(new java.awt.GridBagLayout());

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.angelegt_von}"),
                lblUserValue,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(lblUserValue, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.angelegt_am}"),
                lblDatumValue,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setConverter(new DateToStringConverter());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(lblDatumValue, gridBagConstraints);

        lblDatum.setText("angelegt am:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(lblDatum, gridBagConstraints);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Protokolle:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 6.0;
        gridBagConstraints.insets = new java.awt.Insets(20, 5, 5, 5);
        add(jLabel2, gridBagConstraints);

        lblNummer.setText("Auftragsnummer:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(lblNummer, gridBagConstraints);

        lblUser.setText("angelegt von:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(lblUser, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        add(arbeitsprotokollPanel1, gridBagConstraints);

        jScrollPane2.setMinimumSize(new java.awt.Dimension(17, 120));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(450, 120));

        jTable1.setModel(new ProtokolleTableModel());
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(jTable1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 6.0;
        add(jScrollPane2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 6.0;
        add(jSeparator1, gridBagConstraints);

        lblZugewiesenAn.setText("Zugewiesen an:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(lblZugewiesenAn, gridBagConstraints);

        txtZugewiesenAn.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.zugewiesen_an}"),
                txtZugewiesenAn,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(txtZugewiesenAn, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        add(jPanel1, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.nummer}"),
                lblNummerValue,
                org.jdesktop.beansbinding.BeanProperty.create("text"),
                "nummer");
        binding.setSourceNullValue("00000000");
        binding.setSourceUnreadableValue("00000000");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(lblNummerValue, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     */
    @Override
    final void initPanel() {
    }

    /**
     * DOCUMENT ME!
     *
     * @param  protokoll  DOCUMENT ME!
     */
    public void setSelectedProtokoll(final ArbeitsprotokollCustomBean protokoll) {
        final int index = ((ProtokolleTableModel)jTable1.getModel()).getIndexByProtokoll(protokoll);
        final int viewIndex = jTable1.convertRowIndexToView(index);
        jTable1.getSelectionModel().addSelectionInterval(viewIndex, viewIndex);
        jTable1.scrollRectToVisible(jTable1.getCellRect(viewIndex, 0, true));
    }

    /**
     * DOCUMENT ME!
     */
    @Override
    public void commitEdits() {
    }

    /**
     * DOCUMENT ME!
     */
    @Override
    final void initComponentToLabelMap() {
        componentToLabelMap.put(lblUserValue, lblUser);
        componentToLabelMap.put(lblDatumValue, lblDatum);

        jTable1.getSelectionModel().addListSelectionListener(this);
    }

    /**
     * DOCUMENT ME!
     */
    @Override
    public void setElementsNull() {
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isEditable  DOCUMENT ME!
     */
    @Override
    public void setPanelEditable(final boolean isEditable) {
        RendererTools.setEditable(txtZugewiesenAn, isEditable);
        arbeitsprotokollPanel1.setPanelEditable(isEditable);
        this.isEditable = isEditable;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    protected BindingGroup getBindingGroup() {
        return bindingGroup;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  e  DOCUMENT ME!
     */
    @Override
    public void valueChanged(final ListSelectionEvent e) {
        final int[] selection = jTable1.getSelectedRows();
        final int[] modelSelection = new int[selection.length];

        ArbeitsprotokollCustomBean protokoll = null;
        for (int index = 0; index < selection.length; ++index) {
            modelSelection[index] = jTable1.convertRowIndexToModel(selection[index]);
            protokoll = ((ProtokolleTableModel)jTable1.getModel()).getProtokollByIndex(modelSelection[index]);
        }

        if (protokoll != null) {
            arbeitsprotokollPanel1.setCurrentEntity(protokoll);
            arbeitsprotokollPanel1.setPanelEditable(isEditable);
        } else {
            arbeitsprotokollPanel1.setCurrentEntity(null);
            arbeitsprotokollPanel1.setPanelEditable(false);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  currentEntity  DOCUMENT ME!
     */
    @Override
    public void setCurrentEntity(final ArbeitsauftragCustomBean currentEntity) {
        super.setCurrentEntity(currentEntity);
        LOG.fatal(currentEntity.getMOString());
        ((ProtokolleTableModel)jTable1.getModel()).clear();
        if (currentEntity != null) {
            ((ProtokolleTableModel)jTable1.getModel()).addProtokolle(currentEntity.getAr_protokolle());
        }
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    class ProtokolleTableModel extends AbstractTableModel {

        //~ Instance fields ----------------------------------------------------

        private List<ArbeitsprotokollCustomBean> protokolle = new ArrayList<ArbeitsprotokollCustomBean>();

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        @Override
        public int getRowCount() {
            if (protokolle == null) {
                return 0;
            }
            return protokolle.size();
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        @Override
        public int getColumnCount() {
            return COLUMN_NAMES.length;
        }

        /**
         * DOCUMENT ME!
         *
         * @param   rowIndex     DOCUMENT ME!
         * @param   columnIndex  DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        @Override
        public Object getValueAt(final int rowIndex, final int columnIndex) {
            final ArbeitsprotokollCustomBean protokoll = getProtokollByIndex(rowIndex);
            if (protokoll == null) {
                return null;
            }

            final VeranlassungCustomBean veranlassungCustomBean = null; // protokoll.getFk_veranlassung();
            final ArbeitsprotokollstatusCustomBean status = protokoll.getFk_status();
            final BaseEntity entity;
            final String entityName;
            if (protokoll.getFk_abzweigdose() != null) {
                entity = protokoll.getFk_abzweigdose();
                entityName = "Abzweigdose";
            } else if (protokoll.getFk_geometrie() != null) {
                entity = protokoll.getFk_geometrie();
                entityName = "Geometrie";
            } else if (protokoll.getFk_leitung() != null) {
                entity = protokoll.getFk_leitung();
                entityName = "Leitung";
            } else if (protokoll.getFk_leuchte() != null) {
                entity = protokoll.getFk_leuchte();
                entityName = "Leuchte";
            } else if (protokoll.getFk_mauerlasche() != null) {
                entity = protokoll.getFk_mauerlasche();
                entityName = "Mauerlasche";
            } else if (protokoll.getFk_schaltstelle() != null) {
                entity = protokoll.getFk_schaltstelle();
                entityName = "Schaltstelle";
            } else if (protokoll.getFk_standort() != null) {
                entity = protokoll.getFk_standort();
                if (((TdtaStandortMastCustomBean)entity).isStandortMast()) {
                    entityName = "Mast";
                } else {
                    entityName = "Standort";
                }
            } else {
                entity = null;
                entityName = null;
            }

            switch (columnIndex) {
                case 0: {
                    if (veranlassungCustomBean == null) {
                        return null;
                    } else {
                        return veranlassungCustomBean.getKeyString();
                    }
                }
                case 1: {
                    if (entityName != null) {
                        return entityName;
                    } else {
                        return null;
                    }
                }
                case 2: {
                    if (entity != null) {
                        return entity.getKeyString();
                    } else {
                        return null;
                    }
                }
                case 3: {
                    return protokoll.getMonteur();
                }
                case 4: {
                    if (entity != null) {
                        return entity.getHumanReadablePosition();
                    } else {
                        return null;
                    }
                }
                case 5: {
                    if (status != null) {
                        return status.getBezeichnung();
                    } else {
                        return null;
                    }
                }
                default: {
                    return null;
                }
            }
        }

        /**
         * DOCUMENT ME!
         */
        public void clear() {
            protokolle.clear();
        }

        /**
         * DOCUMENT ME!
         *
         * @param  protokoll  DOCUMENT ME!
         */
        public void addProtokoll(final ArbeitsprotokollCustomBean protokoll) {
            protokolle.add(protokoll);
            fireTableDataChanged();
        }

        /**
         * DOCUMENT ME!
         *
         * @param  protokolle  DOCUMENT ME!
         */
        public void addProtokolle(final Collection<ArbeitsprotokollCustomBean> protokolle) {
            this.protokolle.addAll(protokolle);
            fireTableDataChanged();
        }

        /**
         * DOCUMENT ME!
         *
         * @param  protokoll  DOCUMENT ME!
         */
        public void removeProtokoll(final ArbeitsprotokollCustomBean protokoll) {
            protokolle.remove(protokoll);
            fireTableDataChanged();
        }

        /**
         * DOCUMENT ME!
         *
         * @param   columnIndex  DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        @Override
        public String getColumnName(final int columnIndex) {
            return COLUMN_NAMES[columnIndex];
        }

        /**
         * DOCUMENT ME!
         *
         * @param   columnIndex  DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        @Override
        public Class<?> getColumnClass(final int columnIndex) {
            return COLUMN_CLASSES[columnIndex];
        }

        /**
         * DOCUMENT ME!
         *
         * @param   protokoll  DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public int getIndexByProtokoll(final ArbeitsprotokollCustomBean protokoll) {
            if (protokolle == null) {
                return -1;
            }
            try {
                return protokolle.indexOf(protokoll);
            } catch (Exception e) {
                LOG.error("error in getIndexByProtokoll(). will return -1", e);
                return -1;
            }
        }

        /**
         * DOCUMENT ME!
         *
         * @param   modelIndex  DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public ArbeitsprotokollCustomBean getProtokollByIndex(final int modelIndex) {
            if (protokolle == null) {
                return null;
            }
            try {
                return (ArbeitsprotokollCustomBean)protokolle.get(modelIndex);
            } catch (Exception e) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("ArbeitsprotokollCustomBean at index " + modelIndex + " not found. will return null", e);
                }
                return null;
            }
        }
    }
}
