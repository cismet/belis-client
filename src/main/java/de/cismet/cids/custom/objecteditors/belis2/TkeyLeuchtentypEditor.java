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
package de.cismet.cids.custom.objecteditors.belis2;

import Sirius.navigator.ui.RequestsFullSizeComponent;

import org.apache.log4j.Logger;

import de.cismet.belis.commons.constants.BelisMetaClassConstants;

import de.cismet.belis.gui.documentpanel.DocumentPanel;

import de.cismet.cids.client.tools.DevelopmentTools;

import de.cismet.cids.custom.beans.belis2.TkeyLeuchtentypCustomBean;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

/**
 * DOCUMENT ME!
 *
 * @author   daniel
 * @version  $Revision$, $Date$
 */
public class TkeyLeuchtentypEditor extends javax.swing.JPanel implements RequestsFullSizeComponent, CidsBeanRenderer {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger LOG = Logger.getLogger(TkeyLeuchtentypEditor.class);

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblBestueckung;
    private javax.swing.JLabel lblDokumente;
    private javax.swing.JLabel lblFabrikat;
    private javax.swing.JLabel lblLampe;
    private javax.swing.JLabel lblLeistung;
    private javax.swing.JLabel lblLeistungBrutto;
    private javax.swing.JLabel lblLeistungReduziert;
    private javax.swing.JLabel lblLeistungReduziertBrutto;
    private javax.swing.JLabel lblLeuchtentyp;
    private javax.swing.JLabel lblTypenbezeichnung;
    private javax.swing.JLabel lblVorschaltgeraet;
    private javax.swing.JPanel pnlCard2;
    private javax.swing.JSpinner spinBestueckung;
    private javax.swing.JSpinner spinLeistung;
    private javax.swing.JSpinner spinLeistungBrutto;
    private javax.swing.JSpinner spinLeistungReduziert;
    private javax.swing.JSpinner spinLeistungReduziertBrutto;
    private javax.swing.JTextField txtFabrikat;
    private javax.swing.JTextField txtLampe;
    private javax.swing.JTextField txtLeuchtentyp;
    private javax.swing.JTextField txtTypenbezeichnung;
    private javax.swing.JTextField txtVorschaltgeraet;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form MauerEditor.
     */
    /**
     * Creates a new MauerEditor object.
     */
    public TkeyLeuchtentypEditor() {
        initComponents();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        pnlCard2 = new javax.swing.JPanel();
        lblLeuchtentyp = new javax.swing.JLabel();
        txtLeuchtentyp = new javax.swing.JTextField();
        lblFabrikat = new javax.swing.JLabel();
        txtFabrikat = new javax.swing.JTextField();
        lblTypenbezeichnung = new javax.swing.JLabel();
        txtTypenbezeichnung = new javax.swing.JTextField();
        lblBestueckung = new javax.swing.JLabel();
        spinBestueckung = new javax.swing.JSpinner();
        lblLeistung = new javax.swing.JLabel();
        spinLeistung = new javax.swing.JSpinner();
        lblLeistungBrutto = new javax.swing.JLabel();
        spinLeistungBrutto = new javax.swing.JSpinner();
        lblLeistungReduziert = new javax.swing.JLabel();
        spinLeistungReduziert = new javax.swing.JSpinner();
        lblLeistungReduziertBrutto = new javax.swing.JLabel();
        spinLeistungReduziertBrutto = new javax.swing.JSpinner();
        lblLampe = new javax.swing.JLabel();
        txtLampe = new javax.swing.JTextField();
        lblVorschaltgeraet = new javax.swing.JLabel();
        txtVorschaltgeraet = new javax.swing.JTextField();
        lblDokumente = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(1190, 1625));
        setMinimumSize(new java.awt.Dimension(807, 485));
        setVerifyInputWhenFocusTarget(false);
        setLayout(new java.awt.CardLayout());

        pnlCard2.setOpaque(false);
        pnlCard2.setLayout(new java.awt.GridBagLayout());

        lblLeuchtentyp.setText(org.openide.util.NbBundle.getMessage(TkeyLeuchtentypEditor.class, "TkeyLeuchtentypEditor.lblLeuchtentyp.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 5);
        pnlCard2.add(lblLeuchtentyp, gridBagConstraints);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.leuchtentyp}"), txtLeuchtentyp, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 0);
        pnlCard2.add(txtLeuchtentyp, gridBagConstraints);

        lblFabrikat.setText(org.openide.util.NbBundle.getMessage(TkeyLeuchtentypEditor.class, "TkeyLeuchtentypEditor.lblFabrikat.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 5);
        pnlCard2.add(lblFabrikat, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.fabrikat}"), txtFabrikat, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 0);
        pnlCard2.add(txtFabrikat, gridBagConstraints);

        lblTypenbezeichnung.setText(org.openide.util.NbBundle.getMessage(TkeyLeuchtentypEditor.class, "TkeyLeuchtentypEditor.lblTypenbezeichnung.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 5);
        pnlCard2.add(lblTypenbezeichnung, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.typbenbezeichnung}"), txtTypenbezeichnung, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 0);
        pnlCard2.add(txtTypenbezeichnung, gridBagConstraints);

        lblBestueckung.setText(org.openide.util.NbBundle.getMessage(TkeyLeuchtentypEditor.class, "TkeyLeuchtentypEditor.lblBestueckung.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 5);
        pnlCard2.add(lblBestueckung, gridBagConstraints);

        spinBestueckung.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(1.0d)));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bestueckung}"), spinBestueckung, org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setSourceNullValue(0);
        binding.setSourceUnreadableValue(0);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 0);
        pnlCard2.add(spinBestueckung, gridBagConstraints);

        lblLeistung.setText(org.openide.util.NbBundle.getMessage(TkeyLeuchtentypEditor.class, "TkeyLeuchtentypEditor.lblLeistung.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 5);
        pnlCard2.add(lblLeistung, gridBagConstraints);

        spinLeistung.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(1.0d)));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.leistung}"), spinLeistung, org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setSourceNullValue(0);
        binding.setSourceUnreadableValue(0);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 0);
        pnlCard2.add(spinLeistung, gridBagConstraints);

        lblLeistungBrutto.setText(org.openide.util.NbBundle.getMessage(TkeyLeuchtentypEditor.class, "TkeyLeuchtentypEditor.lblLeistungBrutto.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 5);
        pnlCard2.add(lblLeistungBrutto, gridBagConstraints);

        spinLeistungBrutto.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(1.0d)));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.leistung_brutto}"), spinLeistungBrutto, org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setSourceNullValue(0);
        binding.setSourceUnreadableValue(0);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 0);
        pnlCard2.add(spinLeistungBrutto, gridBagConstraints);

        lblLeistungReduziert.setText(org.openide.util.NbBundle.getMessage(TkeyLeuchtentypEditor.class, "TkeyLeuchtentypEditor.lblLeistungReduziert.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 5);
        pnlCard2.add(lblLeistungReduziert, gridBagConstraints);

        spinLeistungReduziert.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(1.0d)));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.leistung_reduziert}"), spinLeistungReduziert, org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setSourceNullValue(0);
        binding.setSourceUnreadableValue(0);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 0);
        pnlCard2.add(spinLeistungReduziert, gridBagConstraints);

        lblLeistungReduziertBrutto.setText(org.openide.util.NbBundle.getMessage(TkeyLeuchtentypEditor.class, "TkeyLeuchtentypEditor.lblLeistungReduziertBrutto.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 5);
        pnlCard2.add(lblLeistungReduziertBrutto, gridBagConstraints);

        spinLeistungReduziertBrutto.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(1.0d)));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.leistung_brutto_reduziert}"), spinLeistungReduziertBrutto, org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setSourceNullValue(0);
        binding.setSourceUnreadableValue(0);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 0);
        pnlCard2.add(spinLeistungReduziertBrutto, gridBagConstraints);

        lblLampe.setText(org.openide.util.NbBundle.getMessage(TkeyLeuchtentypEditor.class, "TkeyLeuchtentypEditor.lblLampe.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 5);
        pnlCard2.add(lblLampe, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.lampe}"), txtLampe, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 0);
        pnlCard2.add(txtLampe, gridBagConstraints);

        lblVorschaltgeraet.setText(org.openide.util.NbBundle.getMessage(TkeyLeuchtentypEditor.class, "TkeyLeuchtentypEditor.lblVorschaltgeraet.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 5);
        pnlCard2.add(lblVorschaltgeraet, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.vorschaltgeraet}"), txtVorschaltgeraet, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 0);
        pnlCard2.add(txtVorschaltgeraet, gridBagConstraints);

        lblDokumente.setText(org.openide.util.NbBundle.getMessage(TkeyLeuchtentypEditor.class, "TkeyLeuchtentypEditor.lblDokumente.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 5);
        pnlCard2.add(lblDokumente, gridBagConstraints);

        add(pnlCard2, "card2");

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        bindingGroup.unbind();
        if (cidsBean != null) {
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                cidsBean);
            this.cidsBean = cidsBean;
            bindingGroup.bind();
        }
    }

    @Override
    public void dispose() {
        bindingGroup.unbind();
    }

    @Override
    public String getTitle() {
        return "Leuchtentyp";
    }

    @Override
    public void setTitle(final String title) {
    }

    /**
     * DOCUMENT ME!
     *
     * @param   args  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static void main(final String[] args) throws Exception {
        DevelopmentTools.createEditorInFrameFromRMIConnectionOnLocalhost(
            BelisMetaClassConstants.DOMAIN,
            "Bearbeiter",
            "JoettenK",
            "jkbelis",
            TkeyLeuchtentypCustomBean.TABLE,
            1,
            1280,
            1024);
    }
}
