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
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.table.AbstractTableModel;

import de.cismet.belis.broker.BelisBroker;
import de.cismet.belis.broker.CidsBroker;

import de.cismet.belis.gui.DateToStringConverter;
import de.cismet.belis.gui.widget.DetailWidget;

import de.cismet.cids.custom.beans.belis2.InfobausteinCustomBean;
import de.cismet.cids.custom.beans.belis2.InfobausteinTemplateCustomBean;
import de.cismet.cids.custom.beans.belis2.VeranlassungCustomBean;
import de.cismet.cids.custom.beans.belis2.VeranlassungsartCustomBean;

import de.cismet.cids.editors.DefaultBindableReferenceCombo;

/**
 * DOCUMENT ME!
 *
 * @author   Gilles Baatz
 * @version  $Revision$, $Date$
 */
public class VeranlassungPanel extends AbstractDetailWidgetPanel<VeranlassungCustomBean> {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(VeranlassungPanel.class);

    private static final Class[] COLUMN_CLASSES = {
            String.class,
            String.class
        };
    private static final String[] COLUMN_NAMES = { "Bezeichnung", "Wert" };

    //~ Instance fields --------------------------------------------------------

    private BelisBroker belisBroker = BelisBroker.getInstance();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddInfo;
    private javax.swing.JButton btnRemInfo;
    private javax.swing.JButton btnTemplate;
    private javax.swing.JComboBox cbxArt;
    private javax.swing.JComboBox cbxInfobausteineTemplate;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblArt;
    private javax.swing.JLabel lblBemerkungen;
    private javax.swing.JLabel lblBeschreibung;
    private javax.swing.JLabel lblBezeichnung;
    private javax.swing.JLabel lblDatum;
    private javax.swing.JLabel lblDatumValue;
    private javax.swing.JLabel lblInformationsbausteine;
    private javax.swing.JLabel lblNummer;
    private javax.swing.JLabel lblUser;
    private javax.swing.JLabel lblUserValue;
    private javax.swing.JLabel lblVeranlassung;
    private javax.swing.JPanel panContent;
    private javax.swing.JScrollPane scrBemerkungen;
    private javax.swing.JScrollPane scrBeschreibung;
    private javax.swing.JTable tblInfobausteine;
    private javax.swing.JTextArea txaBemerkungen;
    private javax.swing.JTextArea txaBeschreibung;
    private javax.swing.JTextField txtBezeichnung;
    private javax.swing.JTextField txtNummer;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form LeuchtePanel.
     */
    public VeranlassungPanel() {
        super("VERANLASSUNG_PANEL");
        initComponents();
        initComponentToLabelMap();
        initPanel();

        ((DefaultBindableReferenceCombo)cbxInfobausteineTemplate).setMetaClass(CidsBroker.getInstance()
                    .getBelisMetaClass(InfobausteinTemplateCustomBean.TABLE));
//        ((DefaultBindableReferenceCombo)cbxInfobausteineTemplate).setNullable(true);
//        ((DefaultBindableReferenceCombo)cbxInfobausteineTemplate).setRenderer(new DefaultListCellRenderer() {
//
//                @Override
//                public Component getListCellRendererComponent(final JList<? extends Object> list,
//                        final Object value,
//                        final int index,
//                        final boolean isSelected,
//                        final boolean cellHasFocus) {
//                    final Component comp = super.getListCellRendererComponent(
//                            list,
//                            value,
//                            index,
//                            isSelected,
//                            cellHasFocus);
//                    if (value instanceof InfobausteinTemplateCustomBean) {
//                        return comp;
//                    } else {
//                        ((JLabel)comp).setText("[keine] - freie Auswahl der Informationsbausteine");
//                        return comp;
//                    }
//                }
//            });

        fillComboBoxWithKeyTableValuesAndAddListener(cbxArt, VeranlassungsartCustomBean.TABLE);
        cbxArt.setSelectedItem(null);

        fillComboBoxWithKeyTableValuesAndAddListener(cbxInfobausteineTemplate, InfobausteinTemplateCustomBean.TABLE);
        cbxInfobausteineTemplate.setSelectedItem(null);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public JLabel getTabLabel() {
        return lblVeranlassung;
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

        lblVeranlassung = new javax.swing.JLabel();
        panContent = new javax.swing.JPanel();
        lblNummer = new javax.swing.JLabel();
        lblArt = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        lblDatum = new javax.swing.JLabel();
        lblBezeichnung = new javax.swing.JLabel();
        lblBeschreibung = new javax.swing.JLabel();
        lblBemerkungen = new javax.swing.JLabel();
        lblInformationsbausteine = new javax.swing.JLabel();
        txtNummer = new javax.swing.JTextField();
        cbxArt = new javax.swing.JComboBox();
        txtBezeichnung = new javax.swing.JTextField();
        scrBeschreibung = new javax.swing.JScrollPane();
        txaBeschreibung = new javax.swing.JTextArea();
        scrBemerkungen = new javax.swing.JScrollPane();
        txaBemerkungen = new javax.swing.JTextArea();
        lblUserValue = new javax.swing.JLabel();
        lblDatumValue = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblInfobausteine = new javax.swing.JTable();
        cbxInfobausteineTemplate = new DefaultBindableReferenceCombo();
        jPanel1 = new javax.swing.JPanel();
        btnAddInfo = new javax.swing.JButton();
        btnRemInfo = new javax.swing.JButton();
        btnTemplate = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();

        lblVeranlassung.setFont(new java.awt.Font("DejaVu Sans", 1, 13));                       // NOI18N
        lblVeranlassung.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/16/veranlassung.png"))); // NOI18N
        lblVeranlassung.setText("Veranlassung");                                                // NOI18N

        setLayout(new java.awt.GridBagLayout());

        panContent.setLayout(new java.awt.GridBagLayout());

        lblNummer.setText("Nummer:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblNummer, gridBagConstraints);

        lblArt.setText("Grund (Art):");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblArt, gridBagConstraints);

        lblUser.setText("User:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblUser, gridBagConstraints);

        lblDatum.setText("Datum:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblDatum, gridBagConstraints);

        lblBezeichnung.setText("Bezeichnung:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblBezeichnung, gridBagConstraints);

        lblBeschreibung.setText("Beschreibung:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblBeschreibung, gridBagConstraints);

        lblBemerkungen.setText("Bemerkungen:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblBemerkungen, gridBagConstraints);

        lblInformationsbausteine.setText("Informationsbausteine:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblInformationsbausteine, gridBagConstraints);

        txtNummer.setEnabled(false);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.nummer}"),
                txtNummer,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txtNummer, gridBagConstraints);

        cbxArt.setEnabled(false);
        cbxArt.setRenderer(new DefaultListCellRenderer() {

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
                    } else if (value instanceof de.cismet.cids.custom.beans.belis2.VeranlassungsartCustomBean) {
                        final de.cismet.cids.custom.beans.belis2.VeranlassungsartCustomBean el =
                            (de.cismet.cids.custom.beans.belis2.VeranlassungsartCustomBean)value;
                        setText(el.getBezeichnung());
                    }
                    return this;
                }
            });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.fk_art}"),
                cbxArt,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(cbxArt, gridBagConstraints);

        txtBezeichnung.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.bezeichnung}"),
                txtBezeichnung,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txtBezeichnung, gridBagConstraints);

        scrBeschreibung.setEnabled(false);

        txaBeschreibung.setColumns(20);
        txaBeschreibung.setRows(5);
        txaBeschreibung.setEnabled(false);
        txaBeschreibung.setMinimumSize(new java.awt.Dimension(240, 85));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.beschreibung}"),
                txaBeschreibung,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        scrBeschreibung.setViewportView(txaBeschreibung);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(scrBeschreibung, gridBagConstraints);

        scrBemerkungen.setEnabled(false);

        txaBemerkungen.setColumns(20);
        txaBemerkungen.setRows(5);
        txaBemerkungen.setEnabled(false);
        txaBemerkungen.setMinimumSize(new java.awt.Dimension(240, 85));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.bemerkungen}"),
                txaBemerkungen,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        scrBemerkungen.setViewportView(txaBemerkungen);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(scrBemerkungen, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.username}"),
                lblUserValue,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblUserValue, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.datum}"),
                lblDatumValue,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setConverter(new DateToStringConverter());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblDatumValue, gridBagConstraints);

        jScrollPane1.setPreferredSize(new java.awt.Dimension(100, 200));

        tblInfobausteine.setModel(new IBTableModel());
        tblInfobausteine.setMinimumSize(new java.awt.Dimension(60, 200));
        tblInfobausteine.setPreferredSize(new java.awt.Dimension(60, 200));
        jScrollPane1.setViewportView(tblInfobausteine);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(jScrollPane1, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${currentEntity.fk_infobaustein_template}"),
                cbxInfobausteineTemplate,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"),
                "");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(cbxInfobausteineTemplate, gridBagConstraints);

        jPanel1.setOpaque(false);

        btnAddInfo.setText("+");
        btnAddInfo.setMaximumSize(new java.awt.Dimension(29, 29));
        btnAddInfo.setMinimumSize(new java.awt.Dimension(29, 29));
        btnAddInfo.setPreferredSize(new java.awt.Dimension(29, 29));
        btnAddInfo.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnAddInfoActionPerformed(evt);
                }
            });
        jPanel1.add(btnAddInfo);

        btnRemInfo.setText("-");
        btnRemInfo.setMaximumSize(new java.awt.Dimension(29, 29));
        btnRemInfo.setMinimumSize(new java.awt.Dimension(29, 29));
        btnRemInfo.setPreferredSize(new java.awt.Dimension(29, 29));
        btnRemInfo.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnRemInfoActionPerformed(evt);
                }
            });
        jPanel1.add(btnRemInfo);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 5);
        panContent.add(jPanel1, gridBagConstraints);

        btnTemplate.setText("Vorlage hinzufügen");
        btnTemplate.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnTemplateActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(btnTemplate, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        add(panContent, gridBagConstraints);

        final javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                459,
                Short.MAX_VALUE));
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                228,
                Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        add(jPanel2, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnAddInfoActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnAddInfoActionPerformed
        final VeranlassungCustomBean template = (VeranlassungCustomBean)currentEntity;
        final Collection<InfobausteinCustomBean> ar_bausteine = template.getAr_infobausteine();
        ar_bausteine.add(InfobausteinCustomBean.createNew());
        ((AbstractTableModel)tblInfobausteine.getModel()).fireTableDataChanged();
    }                                                                              //GEN-LAST:event_btnAddInfoActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnRemInfoActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnRemInfoActionPerformed
        final VeranlassungCustomBean template = (VeranlassungCustomBean)currentEntity;
        final Collection<InfobausteinCustomBean> ar_bausteine = template.getAr_infobausteine();
        final int rowIndex = tblInfobausteine.getSelectedRow();
        final InfobausteinCustomBean baustein = ar_bausteine.toArray(new InfobausteinCustomBean[0])[rowIndex];
        ar_bausteine.remove(baustein);
        ((AbstractTableModel)tblInfobausteine.getModel()).fireTableDataChanged();
    }                                                                              //GEN-LAST:event_btnRemInfoActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnTemplateActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnTemplateActionPerformed
        final Object selectedItem = cbxInfobausteineTemplate.getSelectedItem();
//        currentEntity.getAr_infobausteine().clear();
        if (selectedItem instanceof InfobausteinTemplateCustomBean) {
            final InfobausteinTemplateCustomBean template = (InfobausteinTemplateCustomBean)selectedItem;
            currentEntity.setFk_infobaustein_template(template);
            final Collection<InfobausteinCustomBean> ar_bausteine = template.getAr_bausteine();
            for (final InfobausteinCustomBean infobausteinFromTemplate : ar_bausteine) {
                final InfobausteinCustomBean infobaustein = InfobausteinCustomBean.createNew();
                infobaustein.setSchluessel(infobausteinFromTemplate.getSchluessel());
                infobaustein.setBezeichnung(infobausteinFromTemplate.getBezeichnung());
                infobaustein.setPflichtfeld(infobausteinFromTemplate.getPflichtfeld());
                infobaustein.setWert(infobausteinFromTemplate.getWert());
                currentEntity.getAr_infobausteine().add(infobaustein);
            }
        }
        ((AbstractTableModel)tblInfobausteine.getModel()).fireTableDataChanged();
    }                                                                               //GEN-LAST:event_btnTemplateActionPerformed

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
        componentToLabelMap.put(txtNummer, lblNummer);
        componentToLabelMap.put(cbxArt, lblArt);
        componentToLabelMap.put(txtBezeichnung, lblBezeichnung);
        componentToLabelMap.put(txaBeschreibung, lblBeschreibung);
        componentToLabelMap.put(txaBemerkungen, lblBemerkungen);
        componentToLabelMap.put(lblUserValue, lblUser);
        componentToLabelMap.put(lblDatumValue, lblDatum);
        componentToLabelMap.put(lblInformationsbausteine, cbxInfobausteineTemplate);
    }

    @Override
    public void setElementsNull() {
    }

    @Override
    public void setPanelEditable(final boolean isEditable) {
        txtNummer.setEnabled(isEditable);
        cbxArt.setEnabled(isEditable);
        txtBezeichnung.setEnabled(isEditable);
        txaBeschreibung.setEnabled(isEditable);
        txaBemerkungen.setEnabled(isEditable);
        btnAddInfo.setEnabled(isEditable);
        btnRemInfo.setEnabled(isEditable);
        btnTemplate.setEnabled(isEditable);
        cbxInfobausteineTemplate.setEnabled(isEditable);
        tblInfobausteine.setEnabled(isEditable);
    }

    @Override
    public void setCurrentEntity(final VeranlassungCustomBean currentEntity) {
        super.setCurrentEntity(currentEntity);
        ((AbstractTableModel)tblInfobausteine.getModel()).fireTableDataChanged();
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
    class IBTableModel extends AbstractTableModel {

        //~ Methods ------------------------------------------------------------

        @Override
        public int getRowCount() {
            if (currentEntity != null) {
                return ((VeranlassungCustomBean)currentEntity).getAr_infobausteine().size();
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
                final InfobausteinCustomBean baustein = getRowObject(rowIndex);
                if (columnIndex == 0) {
                    return baustein.getBezeichnung();
                } else if (columnIndex == 1) {
                    return baustein.getWert();
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }

        @Override
        public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
            if (currentEntity != null) {
                final InfobausteinCustomBean baustein = getRowObject(rowIndex);
                if (columnIndex == 0) {
                    baustein.setBezeichnung((String)aValue);
                } else if (columnIndex == 1) {
                    baustein.setWert((String)aValue);
                }
            }
        }

        /**
         * DOCUMENT ME!
         *
         * @param   rowIndex  DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        private InfobausteinCustomBean getRowObject(final int rowIndex) {
            final VeranlassungCustomBean template = (VeranlassungCustomBean)currentEntity;
            final Collection<InfobausteinCustomBean> ar_bausteine = template.getAr_infobausteine();
            final InfobausteinCustomBean baustein = ar_bausteine.toArray(new InfobausteinCustomBean[0])[rowIndex];
            return baustein;
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
            if (columnIndex == 0) {
                return btnAddInfo.isEnabled();
            } else {
                return true;
            }
        }
    }
}
