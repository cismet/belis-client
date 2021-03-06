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

import org.jdesktop.beansbinding.Converter;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

/**
 * DOCUMENT ME!
 *
 * @author   daniel
 * @version  $Revision$, $Date$
 */
public class RundsteuerempfaengerEditor extends javax.swing.JPanel implements RequestsFullSizeComponent,
    CidsBeanRenderer {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger LOG = Logger.getLogger(RundsteuerempfaengerEditor.class);

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cboProgramm;
    private javax.swing.JLabel lblAnschlusswert;
    private javax.swing.JLabel lblHerrstellerRs;
    private javax.swing.JLabel lblProgramm;
    private javax.swing.JLabel lblRsTyp;
    private javax.swing.JPanel pnlCard2;
    private javax.swing.JFormattedTextField txtAnschlusswert;
    private javax.swing.JTextField txtHerrstellerRs;
    private javax.swing.JTextField txtRsTyp;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form MauerEditor.
     */
    /**
     * Creates a new MauerEditor object.
     */
    public RundsteuerempfaengerEditor() {
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
        lblHerrstellerRs = new javax.swing.JLabel();
        txtHerrstellerRs = new javax.swing.JTextField();
        lblRsTyp = new javax.swing.JLabel();
        txtRsTyp = new javax.swing.JTextField();
        lblAnschlusswert = new javax.swing.JLabel();
        txtAnschlusswert = new javax.swing.JFormattedTextField();
        lblProgramm = new javax.swing.JLabel();
        cboProgramm = new javax.swing.JComboBox();

        setMaximumSize(new java.awt.Dimension(1190, 1625));
        setMinimumSize(new java.awt.Dimension(807, 485));
        setVerifyInputWhenFocusTarget(false);
        setLayout(new java.awt.CardLayout());

        pnlCard2.setOpaque(false);
        pnlCard2.setLayout(new java.awt.GridBagLayout());

        lblHerrstellerRs.setText(org.openide.util.NbBundle.getMessage(
                RundsteuerempfaengerEditor.class,
                "RundsteuerempfaengerEditor.lblHerrstellerRs.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 5);
        pnlCard2.add(lblHerrstellerRs, gridBagConstraints);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.herrsteller_rs}"),
                txtHerrstellerRs,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 0);
        pnlCard2.add(txtHerrstellerRs, gridBagConstraints);

        lblRsTyp.setText(org.openide.util.NbBundle.getMessage(
                RundsteuerempfaengerEditor.class,
                "RundsteuerempfaengerEditor.lblRsTyp.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 5);
        pnlCard2.add(lblRsTyp, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.rs_typ}"),
                txtRsTyp,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 0);
        pnlCard2.add(txtRsTyp, gridBagConstraints);

        lblAnschlusswert.setText(org.openide.util.NbBundle.getMessage(
                RundsteuerempfaengerEditor.class,
                "RundsteuerempfaengerEditor.lblAnschlusswert.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 5);
        pnlCard2.add(lblAnschlusswert, gridBagConstraints);

        txtAnschlusswert.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                new de.cismet.belis.gui.utils.DoubleNumberFormatter()));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.anschlusswert}"),
                txtAnschlusswert,
                org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 0);
        pnlCard2.add(txtAnschlusswert, gridBagConstraints);

        lblProgramm.setText(org.openide.util.NbBundle.getMessage(
                RundsteuerempfaengerEditor.class,
                "RundsteuerempfaengerEditor.lblProgramm.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 5);
        pnlCard2.add(lblProgramm, gridBagConstraints);

        cboProgramm.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A", "B" }));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.programm}"),
                cboProgramm,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 0);
        pnlCard2.add(cboProgramm, gridBagConstraints);

        add(pnlCard2, "card2");

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

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
        return "Rundsteuerempfänger";
    }

    @Override
    public void setTitle(final String title) {
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    class CharacterToStringConverter extends Converter<Character, String> {

        //~ Methods ------------------------------------------------------------

        @Override
        public String convertForward(final Character value) {
            if (value != null) {
                return value.toString();
            } else {
                return null;
            }
        }

        @Override
        public Character convertReverse(final String value) {
            try {
                return new Character(value.charAt(0));
            } catch (final Exception ex) {
                return null;
            }
        }
    }
}
