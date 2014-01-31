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

import de.cismet.belis.broker.BelisBroker;

import de.cismet.belis2.server.search.BelisSearchStatement;
import de.cismet.belis2.server.search.VeranlassungSearchStatement;

import de.cismet.cids.custom.beans.belis2.VeranlassungsartCustomBean;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.tools.search.clientstuff.CidsWindowSearch;

/**
 * DOCUMENT ME!
 *
 * @author   mroncoroni
 * @version  $Revision$, $Date$
 */
@org.openide.util.lookup.ServiceProvider(service = CidsWindowSearch.class)
public class VeranlassungWindowSearch extends BelisWindowSearch {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            VeranlassungWindowSearch.class);

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbGrund;
    private javax.swing.JCheckBox chkActiveOnly;
    private javax.swing.JCheckBox chkAngelegtVon;
    private javax.swing.JCheckBox chkBemerkung;
    private javax.swing.JCheckBox chkBeschreibung;
    private javax.swing.JCheckBox chkDatumBis;
    private javax.swing.JCheckBox chkDatumVon;
    private javax.swing.JCheckBox chkGrund;
    private javax.swing.JCheckBox chkInfoBaustein;
    private de.cismet.cids.editors.DefaultBindableDateChooser dcDatumBis;
    private de.cismet.cids.editors.DefaultBindableDateChooser dcDatumVon;
    private javax.swing.JPanel panActiveOnly;
    private javax.swing.JPanel panAngelegtVon;
    private javax.swing.JPanel panBemerkung;
    private javax.swing.JPanel panBeschreibung;
    private javax.swing.JPanel panDatum;
    private javax.swing.JPanel panGrund;
    private javax.swing.JPanel panInfoBaustein;
    private javax.swing.JPanel panMain;
    private javax.swing.JTextField txtAngelegtVon;
    private javax.swing.JTextField txtBemerkung;
    private javax.swing.JTextField txtBeschreibung;
    private javax.swing.JTextField txtInfoBaustein;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form LandParcelWindowSearch.
     */
    public VeranlassungWindowSearch() {
        super(Mode.VERANLASSUNG, "Veranlassung");
        initComponents();
        initWithThisSpecificPanel(panMain);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    protected BelisSearchStatement createSearchStatement(final Geometry searchGeom) {
        final String datumVon = (chkDatumVon.isSelected()) ? dcDatumVon.getDate().toString() : null;
        final String datumBis = (chkDatumBis.isSelected()) ? dcDatumBis.getDate().toString() : null;
        final Integer grundId = (chkGrund.isSelected()) ? ((CidsBean)cbGrund.getSelectedItem()).getMetaObject().getId()
                                                        : null;
        final String angelegtVon = (chkAngelegtVon.isSelected()) ? txtAngelegtVon.getText() : null;

        final VeranlassungSearchStatement veranlassungSearchStatement = new VeranlassungSearchStatement();
        veranlassungSearchStatement.setGeometry(searchGeom);
        veranlassungSearchStatement.setActiveObjectsOnly(chkActiveOnly.isSelected());
        veranlassungSearchStatement.setDatum(datumVon, datumBis);
        veranlassungSearchStatement.setGrund_id(grundId);
        veranlassungSearchStatement.setAngelegtVon(angelegtVon);

        return veranlassungSearchStatement;
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

        panMain = new javax.swing.JPanel();
        panActiveOnly = new javax.swing.JPanel();
        chkActiveOnly = new javax.swing.JCheckBox();
        panDatum = new javax.swing.JPanel();
        chkDatumVon = new javax.swing.JCheckBox();
        dcDatumVon = new de.cismet.cids.editors.DefaultBindableDateChooser();
        dcDatumBis = new de.cismet.cids.editors.DefaultBindableDateChooser();
        chkDatumBis = new javax.swing.JCheckBox();
        panAngelegtVon = new javax.swing.JPanel();
        chkAngelegtVon = new javax.swing.JCheckBox();
        txtAngelegtVon = new javax.swing.JTextField();
        panGrund = new javax.swing.JPanel();
        cbGrund = BelisBroker.createKeyTableComboBox(VeranlassungsartCustomBean.TABLE);
        chkGrund = new javax.swing.JCheckBox();
        panBeschreibung = new javax.swing.JPanel();
        chkBeschreibung = new javax.swing.JCheckBox();
        txtBeschreibung = new javax.swing.JTextField();
        panBemerkung = new javax.swing.JPanel();
        chkBemerkung = new javax.swing.JCheckBox();
        txtBemerkung = new javax.swing.JTextField();
        panInfoBaustein = new javax.swing.JPanel();
        chkInfoBaustein = new javax.swing.JCheckBox();
        txtInfoBaustein = new javax.swing.JTextField();

        panMain.setLayout(new java.awt.GridBagLayout());

        panActiveOnly.setBorder(javax.swing.BorderFactory.createTitledBorder(
                org.openide.util.NbBundle.getMessage(
                    VeranlassungWindowSearch.class,
                    "VeranlassungWindowSearch.panActiveOnly.border.title"))); // NOI18N
        panActiveOnly.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            chkActiveOnly,
            org.openide.util.NbBundle.getMessage(
                VeranlassungWindowSearch.class,
                "VeranlassungWindowSearch.chkActiveOnly.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panActiveOnly.add(chkActiveOnly, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMain.add(panActiveOnly, gridBagConstraints);

        panDatum.setBorder(javax.swing.BorderFactory.createTitledBorder(
                org.openide.util.NbBundle.getMessage(
                    VeranlassungWindowSearch.class,
                    "VeranlassungWindowSearch.panDatum.border.title"))); // NOI18N
        panDatum.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            chkDatumVon,
            org.openide.util.NbBundle.getMessage(
                VeranlassungWindowSearch.class,
                "VeranlassungWindowSearch.chkDatumVon.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panDatum.add(chkDatumVon, gridBagConstraints);

        dcDatumVon.setMaximumSize(new java.awt.Dimension(132, 25));
        dcDatumVon.setMinimumSize(new java.awt.Dimension(132, 25));
        dcDatumVon.setPreferredSize(new java.awt.Dimension(132, 25));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                chkDatumVon,
                org.jdesktop.beansbinding.ELProperty.create("${selected}"),
                dcDatumVon,
                org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        panDatum.add(dcDatumVon, gridBagConstraints);

        dcDatumBis.setMaximumSize(new java.awt.Dimension(132, 25));
        dcDatumBis.setMinimumSize(new java.awt.Dimension(132, 25));
        dcDatumBis.setPreferredSize(new java.awt.Dimension(132, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                chkDatumBis,
                org.jdesktop.beansbinding.ELProperty.create("${selected}"),
                dcDatumBis,
                org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        panDatum.add(dcDatumBis, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            chkDatumBis,
            org.openide.util.NbBundle.getMessage(
                VeranlassungWindowSearch.class,
                "VeranlassungWindowSearch.chkDatumBis.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panDatum.add(chkDatumBis, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMain.add(panDatum, gridBagConstraints);
        panDatum.getAccessibleContext()
                .setAccessibleName(org.openide.util.NbBundle.getMessage(
                        VeranlassungWindowSearch.class,
                        "VeranlassungWindowSearch.panDatum.AccessibleContext.accessibleName")); // NOI18N

        panAngelegtVon.setBorder(javax.swing.BorderFactory.createTitledBorder(
                org.openide.util.NbBundle.getMessage(
                    VeranlassungWindowSearch.class,
                    "VeranlassungWindowSearch.panAngelegtVon.border.title"))); // NOI18N
        panAngelegtVon.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            chkAngelegtVon,
            org.openide.util.NbBundle.getMessage(
                VeranlassungWindowSearch.class,
                "VeranlassungWindowSearch.chkAngelegtVon.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panAngelegtVon.add(chkAngelegtVon, gridBagConstraints);

        txtAngelegtVon.setText(org.openide.util.NbBundle.getMessage(
                VeranlassungWindowSearch.class,
                "VeranlassungWindowSearch.txtAngelegtVon.text")); // NOI18N

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
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMain.add(panAngelegtVon, gridBagConstraints);

        panGrund.setBorder(javax.swing.BorderFactory.createTitledBorder(
                org.openide.util.NbBundle.getMessage(
                    VeranlassungWindowSearch.class,
                    "VeranlassungWindowSearch.panGrund.border.title"))); // NOI18N
        panGrund.setLayout(new java.awt.GridBagLayout());

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                chkGrund,
                org.jdesktop.beansbinding.ELProperty.create("${selected}"),
                cbGrund,
                org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panGrund.add(cbGrund, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            chkGrund,
            org.openide.util.NbBundle.getMessage(
                VeranlassungWindowSearch.class,
                "VeranlassungWindowSearch.chkGrund.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panGrund.add(chkGrund, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMain.add(panGrund, gridBagConstraints);

        panBeschreibung.setBorder(javax.swing.BorderFactory.createTitledBorder(
                org.openide.util.NbBundle.getMessage(
                    VeranlassungWindowSearch.class,
                    "VeranlassungWindowSearch.panBeschreibung.border.title"))); // NOI18N
        panBeschreibung.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            chkBeschreibung,
            org.openide.util.NbBundle.getMessage(
                VeranlassungWindowSearch.class,
                "VeranlassungWindowSearch.chkBeschreibung.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panBeschreibung.add(chkBeschreibung, gridBagConstraints);

        txtBeschreibung.setText(org.openide.util.NbBundle.getMessage(
                VeranlassungWindowSearch.class,
                "VeranlassungWindowSearch.txtBeschreibung.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                chkBeschreibung,
                org.jdesktop.beansbinding.ELProperty.create("${selected}"),
                txtBeschreibung,
                org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panBeschreibung.add(txtBeschreibung, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMain.add(panBeschreibung, gridBagConstraints);

        panBemerkung.setBorder(javax.swing.BorderFactory.createTitledBorder(
                org.openide.util.NbBundle.getMessage(
                    VeranlassungWindowSearch.class,
                    "VeranlassungWindowSearch.panBemerkung.border.title"))); // NOI18N
        panBemerkung.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            chkBemerkung,
            org.openide.util.NbBundle.getMessage(
                VeranlassungWindowSearch.class,
                "VeranlassungWindowSearch.chkBemerkung.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panBemerkung.add(chkBemerkung, gridBagConstraints);

        txtBemerkung.setText(org.openide.util.NbBundle.getMessage(
                VeranlassungWindowSearch.class,
                "VeranlassungWindowSearch.txtBemerkung.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                chkBemerkung,
                org.jdesktop.beansbinding.ELProperty.create("${selected}"),
                txtBemerkung,
                org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panBemerkung.add(txtBemerkung, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMain.add(panBemerkung, gridBagConstraints);

        panInfoBaustein.setBorder(javax.swing.BorderFactory.createTitledBorder(
                org.openide.util.NbBundle.getMessage(
                    VeranlassungWindowSearch.class,
                    "VeranlassungWindowSearch.panInfoBaustein.border.title"))); // NOI18N
        panInfoBaustein.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            chkInfoBaustein,
            org.openide.util.NbBundle.getMessage(
                VeranlassungWindowSearch.class,
                "VeranlassungWindowSearch.chkInfoBaustein.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panInfoBaustein.add(chkInfoBaustein, gridBagConstraints);

        txtInfoBaustein.setText(org.openide.util.NbBundle.getMessage(
                VeranlassungWindowSearch.class,
                "VeranlassungWindowSearch.txtInfoBaustein.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                chkInfoBaustein,
                org.jdesktop.beansbinding.ELProperty.create("${selected}"),
                txtInfoBaustein,
                org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panInfoBaustein.add(txtInfoBaustein, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMain.add(panInfoBaustein, gridBagConstraints);

        setLayout(null);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents
}
