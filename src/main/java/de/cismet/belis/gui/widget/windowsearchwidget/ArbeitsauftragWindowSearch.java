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
package de.cismet.belis.gui.widget.windowsearchwidget;

import com.vividsolutions.jts.geom.Geometry;

import de.cismet.belis2.server.search.ArbeitsauftragSearchStatement;
import de.cismet.belis2.server.search.BelisSearchStatement;

import de.cismet.cids.tools.search.clientstuff.CidsWindowSearch;

/**
 * DOCUMENT ME!
 *
 * @author   mroncoroni
 * @version  $Revision$, $Date$
 */
@org.openide.util.lookup.ServiceProvider(service = CidsWindowSearch.class)
public class ArbeitsauftragWindowSearch extends BelisWindowSearch {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            ArbeitsauftragWindowSearch.class);

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chkAngelegtAmBis;
    private javax.swing.JCheckBox chkAngelegtAmVon;
    private javax.swing.JCheckBox chkAngelegtVon;
    private javax.swing.JCheckBox chkAuftragsnummer;
    private javax.swing.JCheckBox chkZugewiesenAn;
    private de.cismet.cids.editors.DefaultBindableDateChooser dcAngelegtAmBis;
    private de.cismet.cids.editors.DefaultBindableDateChooser dcAngelegtAmVon;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JPanel panActiveOnly1;
    private javax.swing.JPanel panAngelegtAm;
    private javax.swing.JPanel panAngelegtVon;
    private javax.swing.JPanel panAuftragsnummer;
    private javax.swing.JPanel panTest;
    private javax.swing.JPanel panZugewiesenAn;
    private javax.swing.JTextField txtAngelegtVon;
    private javax.swing.JTextField txtAuftragsnummer;
    private javax.swing.JTextField txtZugewiesenAn;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form LandParcelWindowSearch.
     */
    public ArbeitsauftragWindowSearch() {
        super(Mode.ARBEITSAUFTRAG, "Arbeitsaufträge");
        initComponents();
        initWithThisSpecificPanel(panTest);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    protected BelisSearchStatement createSearchStatement(final Geometry searchGeom) {
        final String angelegtAmVon = (chkAngelegtAmVon.isSelected()) ? dcAngelegtAmVon.getDate().toString() : null;
        final String angelegtAmBis = (chkAngelegtAmBis.isSelected()) ? dcAngelegtAmBis.getDate().toString() : null;
        final String angelegtVon = (chkAngelegtVon.isSelected()) ? txtAngelegtVon.getText() : null;
        final String zugewiesenAn = (chkZugewiesenAn.isSelected()) ? txtZugewiesenAn.getText() : null;
        final String auftragsNummer = (chkAuftragsnummer.isSelected()) ? txtAuftragsnummer.getText() : null;

        final ArbeitsauftragSearchStatement arbeitsauftragSearchStatement = new ArbeitsauftragSearchStatement();
        arbeitsauftragSearchStatement.setGeometry(searchGeom);
        arbeitsauftragSearchStatement.setActiveObjectsOnly(jComboBox1.getSelectedItem().equals("nur offene"));
        arbeitsauftragSearchStatement.setWorkedoffObjectsOnly(jComboBox1.getSelectedItem().equals("nur abgearbeitete"));
        arbeitsauftragSearchStatement.setAngelegtAm(angelegtAmVon, angelegtAmBis);
        arbeitsauftragSearchStatement.setAngelegtVon(angelegtVon);
        arbeitsauftragSearchStatement.setZugewiesenAn(zugewiesenAn);
        arbeitsauftragSearchStatement.setAuftragsNummer(auftragsNummer);

        return arbeitsauftragSearchStatement;
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

        panTest = new javax.swing.JPanel();
        panActiveOnly1 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox();
        panAuftragsnummer = new javax.swing.JPanel();
        chkAuftragsnummer = new javax.swing.JCheckBox();
        txtAuftragsnummer = new javax.swing.JTextField();
        panZugewiesenAn = new javax.swing.JPanel();
        chkZugewiesenAn = new javax.swing.JCheckBox();
        txtZugewiesenAn = new javax.swing.JTextField();
        panAngelegtAm = new javax.swing.JPanel();
        chkAngelegtAmVon = new javax.swing.JCheckBox();
        dcAngelegtAmVon = new de.cismet.cids.editors.DefaultBindableDateChooser();
        dcAngelegtAmBis = new de.cismet.cids.editors.DefaultBindableDateChooser();
        chkAngelegtAmBis = new javax.swing.JCheckBox();
        panAngelegtVon = new javax.swing.JPanel();
        chkAngelegtVon = new javax.swing.JCheckBox();
        txtAngelegtVon = new javax.swing.JTextField();

        panTest.setLayout(new java.awt.GridBagLayout());

        panActiveOnly1.setBorder(javax.swing.BorderFactory.createTitledBorder(
                org.openide.util.NbBundle.getMessage(
                    ArbeitsauftragWindowSearch.class,
                    "ArbeitsauftragWindowSearch.panActiveOnly1.border.title"))); // NOI18N
        panActiveOnly1.setLayout(new java.awt.GridBagLayout());

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(
                new String[] { "alle", "nur offene", "nur abgearbeitete" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panActiveOnly1.add(jComboBox1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panTest.add(panActiveOnly1, gridBagConstraints);

        panAuftragsnummer.setBorder(javax.swing.BorderFactory.createTitledBorder(
                org.openide.util.NbBundle.getMessage(
                    ArbeitsauftragWindowSearch.class,
                    "ArbeitsauftragWindowSearch.panAuftragsnummer.border.title"))); // NOI18N
        panAuftragsnummer.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            chkAuftragsnummer,
            org.openide.util.NbBundle.getMessage(
                ArbeitsauftragWindowSearch.class,
                "ArbeitsauftragWindowSearch.chkAuftragsnummer.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panAuftragsnummer.add(chkAuftragsnummer, gridBagConstraints);

        txtAuftragsnummer.setText(org.openide.util.NbBundle.getMessage(
                ArbeitsauftragWindowSearch.class,
                "ArbeitsauftragWindowSearch.txtAuftragsnummer.text")); // NOI18N

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                chkAuftragsnummer,
                org.jdesktop.beansbinding.ELProperty.create("${selected}"),
                txtAuftragsnummer,
                org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panAuftragsnummer.add(txtAuftragsnummer, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panTest.add(panAuftragsnummer, gridBagConstraints);

        panZugewiesenAn.setBorder(javax.swing.BorderFactory.createTitledBorder(
                org.openide.util.NbBundle.getMessage(
                    ArbeitsauftragWindowSearch.class,
                    "ArbeitsauftragWindowSearch.panZugewiesenAn.border.title"))); // NOI18N
        panZugewiesenAn.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            chkZugewiesenAn,
            org.openide.util.NbBundle.getMessage(
                ArbeitsauftragWindowSearch.class,
                "ArbeitsauftragWindowSearch.chkZugewiesenAn.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panZugewiesenAn.add(chkZugewiesenAn, gridBagConstraints);

        txtZugewiesenAn.setText(org.openide.util.NbBundle.getMessage(
                ArbeitsauftragWindowSearch.class,
                "ArbeitsauftragWindowSearch.txtZugewiesenAn.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                chkZugewiesenAn,
                org.jdesktop.beansbinding.ELProperty.create("${selected}"),
                txtZugewiesenAn,
                org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panZugewiesenAn.add(txtZugewiesenAn, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panTest.add(panZugewiesenAn, gridBagConstraints);

        panAngelegtAm.setBorder(javax.swing.BorderFactory.createTitledBorder(
                org.openide.util.NbBundle.getMessage(
                    ArbeitsauftragWindowSearch.class,
                    "ArbeitsauftragWindowSearch.panAngelegtAm.border.title"))); // NOI18N
        panAngelegtAm.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            chkAngelegtAmVon,
            org.openide.util.NbBundle.getMessage(
                ArbeitsauftragWindowSearch.class,
                "ArbeitsauftragWindowSearch.chkAngelegtAmVon.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panAngelegtAm.add(chkAngelegtAmVon, gridBagConstraints);

        dcAngelegtAmVon.setMaximumSize(new java.awt.Dimension(132, 25));
        dcAngelegtAmVon.setMinimumSize(new java.awt.Dimension(132, 25));
        dcAngelegtAmVon.setPreferredSize(new java.awt.Dimension(132, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                chkAngelegtAmVon,
                org.jdesktop.beansbinding.ELProperty.create("${selected}"),
                dcAngelegtAmVon,
                org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        panAngelegtAm.add(dcAngelegtAmVon, gridBagConstraints);

        dcAngelegtAmBis.setMaximumSize(new java.awt.Dimension(132, 25));
        dcAngelegtAmBis.setMinimumSize(new java.awt.Dimension(132, 25));
        dcAngelegtAmBis.setPreferredSize(new java.awt.Dimension(132, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                chkAngelegtAmBis,
                org.jdesktop.beansbinding.ELProperty.create("${selected}"),
                dcAngelegtAmBis,
                org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        panAngelegtAm.add(dcAngelegtAmBis, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            chkAngelegtAmBis,
            org.openide.util.NbBundle.getMessage(
                ArbeitsauftragWindowSearch.class,
                "ArbeitsauftragWindowSearch.chkAngelegtAmBis.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panAngelegtAm.add(chkAngelegtAmBis, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panTest.add(panAngelegtAm, gridBagConstraints);

        panAngelegtVon.setBorder(javax.swing.BorderFactory.createTitledBorder(
                org.openide.util.NbBundle.getMessage(
                    ArbeitsauftragWindowSearch.class,
                    "ArbeitsauftragWindowSearch.panAngelegtVon.border.title"))); // NOI18N
        panAngelegtVon.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            chkAngelegtVon,
            org.openide.util.NbBundle.getMessage(
                ArbeitsauftragWindowSearch.class,
                "ArbeitsauftragWindowSearch.chkAngelegtVon.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panAngelegtVon.add(chkAngelegtVon, gridBagConstraints);

        txtAngelegtVon.setText(org.openide.util.NbBundle.getMessage(
                ArbeitsauftragWindowSearch.class,
                "ArbeitsauftragWindowSearch.txtAngelegtVon.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                chkAngelegtVon,
                org.jdesktop.beansbinding.ELProperty.create("${selected}"),
                txtAngelegtVon,
                org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panAngelegtVon.add(txtAngelegtVon, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panTest.add(panAngelegtVon, gridBagConstraints);

        setLayout(null);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents
}
