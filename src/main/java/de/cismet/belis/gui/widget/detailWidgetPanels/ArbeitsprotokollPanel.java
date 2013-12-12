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

import java.util.Collection;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;

import de.cismet.belis.arbeitsprotokollwizard.AbstractArbeitsprotokollWizard;

import de.cismet.belis.broker.BelisBroker;

import de.cismet.belis.util.RendererTools;

import de.cismet.cids.custom.beans.belis2.ArbeitsprotokollCustomBean;
import de.cismet.cids.custom.beans.belis2.ArbeitsprotokollstatusCustomBean;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @author   Gilles Baatz
 * @version  $Revision$, $Date$
 */
public class ArbeitsprotokollPanel extends AbstractDetailWidgetPanel<ArbeitsprotokollCustomBean> {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ArbeitsprotokollPanel.class);

    //~ Instance fields --------------------------------------------------------

    private BelisBroker belisBroker = BelisBroker.getInstance();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbxStatus;
    private org.jdesktop.swingx.JXDatePicker dapDatum;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
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
        cbxStatus = new javax.swing.JComboBox();
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

        lblArbeitsprotokoll.setFont(new java.awt.Font("DejaVu Sans", 1, 13)); // NOI18N
        lblArbeitsprotokoll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/belis/resource/icon/16/arbeitsprotokoll.png"))); // NOI18N
        lblArbeitsprotokoll.setText("Arbeitsprotokoll"); // NOI18N

        setLayout(new java.awt.GridBagLayout());

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
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panDetails.add(dapDatum, gridBagConstraints);

        cbxStatus.setEnabled(false);
        cbxStatus.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                final JList list,final Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if(value == null){
                    setText(comboBoxNullValue);
                } else if (value instanceof de.cismet.cids.custom.beans.belis2.ArbeitsprotokollstatusCustomBean) {
                    final de.cismet.cids.custom.beans.belis2.ArbeitsprotokollstatusCustomBean el = (de.cismet.cids.custom.beans.belis2.ArbeitsprotokollstatusCustomBean)value;
                    setText(el.getBezeichnung());
                }
                return this;
            }
        });

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${currentEntity.fk_status}"), cbxStatus, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
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

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${currentEntity.bemerkung}"), txaBemerkungen, org.jdesktop.beansbinding.BeanProperty.create("text"));
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

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${currentEntity.material}"), txaMaterial, org.jdesktop.beansbinding.BeanProperty.create("text"));
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

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${currentEntity.monteur}"), txfMonteur, org.jdesktop.beansbinding.BeanProperty.create("text"));
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
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAktionen.add(panActions, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
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

        jSplitPane1.setRightComponent(jPanel2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jSplitPane1, gridBagConstraints);

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    @Override
    final void initPanel() {
        fillComboBoxWithKeyTableValuesAndAddListener(cbxStatus, ArbeitsprotokollstatusCustomBean.TABLE);
        cbxStatus.setSelectedItem(null);
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
        RendererTools.setEditable(dapDatum, isEditable);
        RendererTools.setEditable(cbxStatus, isEditable);
        RendererTools.setEditable(txaBemerkungen, isEditable);
        RendererTools.setEditable(txfMonteur, isEditable);
        RendererTools.setEditable(txaMaterial, isEditable);
    }

    @Override
    public void setCurrentEntity(final ArbeitsprotokollCustomBean currentEntity) {
        super.setCurrentEntity(currentEntity);
        final BaseEntity subEntity;
        if (currentEntity != null) {
            if (currentEntity.getFk_abzweigdose() != null) {
                subEntity = currentEntity.getFk_abzweigdose();
            } else if (currentEntity.getFk_geometrie() != null) {
                subEntity = currentEntity.getFk_geometrie();
            } else if (currentEntity.getFk_leitung() != null) {
                subEntity = currentEntity.getFk_leitung();
            } else if (currentEntity.getFk_leuchte() != null) {
                subEntity = currentEntity.getFk_leuchte();
            } else if (currentEntity.getFk_mauerlasche() != null) {
                subEntity = currentEntity.getFk_mauerlasche();
            } else if (currentEntity.getFk_schaltstelle() != null) {
                subEntity = currentEntity.getFk_schaltstelle();
            } else if (currentEntity.getFk_standort() != null) {
                subEntity = currentEntity.getFk_standort();
            } else {
                subEntity = null;
            }
        } else {
            subEntity = null;
        }

        final Collection<AbstractArbeitsprotokollWizard> wizardsActionsForEntity = BelisBroker.getInstance()
                    .getWizardsActionsForEntity(subEntity);
        panActions.removeAll();
        if (wizardsActionsForEntity != null) {
            for (final AbstractArbeitsprotokollWizard wizard : wizardsActionsForEntity) {
                panActions.add(new JButton(wizard.getAction()));
            }
        }
        validate();
        repaint();
    }

    @Override
    protected BindingGroup getBindingGroup() {
        return null;
    }
}
