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

import javax.swing.JLabel;

import de.cismet.belis.broker.BelisBroker;

import de.cismet.belis.util.RendererTools;

import de.cismet.cids.custom.beans.belis2.LeitungCustomBean;
import de.cismet.cids.custom.beans.belis2.LeitungstypCustomBean;
import de.cismet.cids.custom.beans.belis2.MaterialLeitungCustomBean;
import de.cismet.cids.custom.beans.belis2.QuerschnittCustomBean;

/**
 * DOCUMENT ME!
 *
 * @author   Gilles Baatz
 * @version  $Revision$, $Date$
 */
public final class LeitungPanel extends AbstractDetailWidgetPanel<LeitungCustomBean> {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(LeitungPanel.class);

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbxLeitungLeitungstyp;
    private javax.swing.JComboBox cbxLeitungMaterial;
    private javax.swing.JComboBox cbxLeitungQuerschnitt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblLeitungLeitungstyp;
    private javax.swing.JLabel lblLeitungMaterial;
    private javax.swing.JLabel lblLeitungQuerschnitt;
    private javax.swing.JPanel panContent;
    private javax.swing.JPanel panSpacer0;
    private javax.swing.JPanel panSpacer1;
    private javax.swing.JPanel panSpacerBottom;
    private javax.swing.JPanel panSpacerBottom1;
    private javax.swing.JPanel panSpacerLeft;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form LeitungPanel.
     */
    public LeitungPanel() {
        super("LEITUNG_PANEL");
        initComponents();
        initComponentToLabelMap();
        initPanel();
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public JLabel getTabLabel() {
        return jLabel1;
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

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        panContent = new javax.swing.JPanel();
        panSpacer0 = new javax.swing.JPanel();
        panSpacer1 = new javax.swing.JPanel();
        lblLeitungLeitungstyp = new javax.swing.JLabel();
        cbxLeitungLeitungstyp = BelisBroker.createKeyTableComboBox(LeitungstypCustomBean.TABLE);
        lblLeitungMaterial = new javax.swing.JLabel();
        cbxLeitungMaterial = BelisBroker.createKeyTableComboBox(MaterialLeitungCustomBean.TABLE);
        lblLeitungQuerschnitt = new javax.swing.JLabel();
        cbxLeitungQuerschnitt = BelisBroker.createKeyTableComboBox(QuerschnittCustomBean.TABLE);
        panSpacerBottom = new javax.swing.JPanel();
        panSpacerLeft = new javax.swing.JPanel();
        panSpacerBottom1 = new javax.swing.JPanel();

        jLabel1.setFont(new java.awt.Font("DejaVu Sans", 1, 13));                          // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/16/leuchte.png"))); // NOI18N
        jLabel1.setText("Leitung");                                                        // NOI18N

        setLayout(new java.awt.GridBagLayout());

        jPanel1.setLayout(new java.awt.GridBagLayout());

        panContent.setLayout(new java.awt.GridBagLayout());

        panSpacer0.setMinimumSize(DIMENSION_KEYSPACER);
        panSpacer0.setPreferredSize(DIMENSION_KEYSPACER);

        final javax.swing.GroupLayout panSpacer0Layout = new javax.swing.GroupLayout(panSpacer0);
        panSpacer0.setLayout(panSpacer0Layout);
        panSpacer0Layout.setHorizontalGroup(
            panSpacer0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                0,
                Short.MAX_VALUE));
        panSpacer0Layout.setVerticalGroup(
            panSpacer0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                0,
                Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panContent.add(panSpacer0, gridBagConstraints);

        panSpacer1.setName(""); // NOI18N
        panSpacer1.setPreferredSize(new java.awt.Dimension(420, 0));

        final javax.swing.GroupLayout panSpacer1Layout = new javax.swing.GroupLayout(panSpacer1);
        panSpacer1.setLayout(panSpacer1Layout);
        panSpacer1Layout.setHorizontalGroup(
            panSpacer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                0,
                Short.MAX_VALUE));
        panSpacer1Layout.setVerticalGroup(
            panSpacer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                0,
                Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        panContent.add(panSpacer1, gridBagConstraints);

        lblLeitungLeitungstyp.setText("Leitungstyp:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblLeitungLeitungstyp, gridBagConstraints);

        cbxLeitungLeitungstyp.setEnabled(false);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.fk_leitungstyp}"),
                cbxLeitungLeitungstyp,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(cbxLeitungLeitungstyp, gridBagConstraints);

        lblLeitungMaterial.setText("Material:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblLeitungMaterial, gridBagConstraints);

        cbxLeitungMaterial.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.material}"),
                cbxLeitungMaterial,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(cbxLeitungMaterial, gridBagConstraints);

        lblLeitungQuerschnitt.setText("Querschnitt:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblLeitungQuerschnitt, gridBagConstraints);

        cbxLeitungQuerschnitt.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.querschnitt}"),
                cbxLeitungQuerschnitt,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(cbxLeitungQuerschnitt, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel1.add(panContent, gridBagConstraints);

        panSpacerBottom.setMinimumSize(DIMENSION_CONTENTSPACER);
        panSpacerBottom.setPreferredSize(DIMENSION_CONTENTSPACER);

        final javax.swing.GroupLayout panSpacerBottomLayout = new javax.swing.GroupLayout(panSpacerBottom);
        panSpacerBottom.setLayout(panSpacerBottomLayout);
        panSpacerBottomLayout.setHorizontalGroup(
            panSpacerBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                0,
                Short.MAX_VALUE));
        panSpacerBottomLayout.setVerticalGroup(
            panSpacerBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                0,
                Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(panSpacerBottom, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        add(jPanel1, gridBagConstraints);

        final javax.swing.GroupLayout panSpacerLeftLayout = new javax.swing.GroupLayout(panSpacerLeft);
        panSpacerLeft.setLayout(panSpacerLeftLayout);
        panSpacerLeftLayout.setHorizontalGroup(
            panSpacerLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                0,
                Short.MAX_VALUE));
        panSpacerLeftLayout.setVerticalGroup(
            panSpacerLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                0,
                Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        add(panSpacerLeft, gridBagConstraints);

        final javax.swing.GroupLayout panSpacerBottom1Layout = new javax.swing.GroupLayout(panSpacerBottom1);
        panSpacerBottom1.setLayout(panSpacerBottom1Layout);
        panSpacerBottom1Layout.setHorizontalGroup(
            panSpacerBottom1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                0,
                Short.MAX_VALUE));
        panSpacerBottom1Layout.setVerticalGroup(
            panSpacerBottom1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                0,
                Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        add(panSpacerBottom1, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    @Override
    void initPanel() {
        bindingGroup.addBindingListener(new PanelBindingListener());
    }

    @Override
    void initComponentToLabelMap() {
        componentToLabelMap.put(cbxLeitungLeitungstyp, lblLeitungLeitungstyp);
        componentToLabelMap.put(cbxLeitungMaterial, lblLeitungMaterial);
        componentToLabelMap.put(cbxLeitungQuerschnitt, lblLeitungQuerschnitt);
    }

    @Override
    public void setElementsNull() {
        // does nothing atm
    }

    @Override
    public void setPanelEditable(final boolean isEditable) {
//        cbxLeitungLeitungstyp.setEnabled(isEditable);
//        cbxLeitungMaterial.setEnabled(isEditable);
//        cbxLeitungQuerschnitt.setEnabled(isEditable);

        RendererTools.setEditable(cbxLeitungLeitungstyp, isEditable);
        RendererTools.setEditable(cbxLeitungMaterial, isEditable);
        RendererTools.setEditable(cbxLeitungQuerschnitt, isEditable);
    }

    @Override
    protected BindingGroup getBindingGroup() {
        return null;
    }

    @Override
    protected void commitEdits() {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
                                                                       // Tools | Templates.
    }
}
