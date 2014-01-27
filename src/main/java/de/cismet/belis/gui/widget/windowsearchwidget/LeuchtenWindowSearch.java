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

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import de.cismet.belis.broker.CidsBroker;

import de.cismet.belis.gui.widget.detailWidgetPanels.ObjectToKeyStringConverter;

import de.cismet.belis2.server.search.BelisSearchStatement;
import de.cismet.belis2.server.search.LeuchteSearchStatement;

import de.cismet.cids.custom.beans.belis2.RundsteuerempfaengerCustomBean;
import de.cismet.cids.custom.beans.belis2.TkeyDoppelkommandoCustomBean;
import de.cismet.cids.custom.beans.belis2.TkeyLeuchtentypCustomBean;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultBindableReferenceCombo;

import de.cismet.cids.tools.search.clientstuff.CidsWindowSearch;

/**
 * DOCUMENT ME!
 *
 * @author   mroncoroni
 * @version  $Revision$, $Date$
 */
@org.openide.util.lookup.ServiceProvider(service = CidsWindowSearch.class)
public class LeuchtenWindowSearch extends BelisWindowSearch {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(LeuchtenWindowSearch.class);

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbAnschlussleistung1dk;
    private javax.swing.JComboBox cbAnschlussleistung2dk;
    private javax.swing.JCheckBox cbInbetriebnahmeLeuchteBis;
    private javax.swing.JCheckBox cbInbetriebnahmeLeuchteVon;
    private javax.swing.JComboBox cbLeuchtentyp;
    private javax.swing.JCheckBox cbNaechsterWechselBis;
    private javax.swing.JCheckBox cbNaechsterWechselVon;
    private javax.swing.JComboBox cbRundsteuerempfaenger;
    private javax.swing.JCheckBox cbWechseldatumBis;
    private javax.swing.JCheckBox cbWechseldatumVon;
    private javax.swing.JCheckBox chkAnschlussleistung1dk;
    private javax.swing.JCheckBox chkAnschlussleistung2dk;
    private javax.swing.JCheckBox chkLeuchtentyp;
    private javax.swing.JCheckBox chkRundsteuerempfaenger;
    private javax.swing.JCheckBox chkSchaltstelle;
    private de.cismet.cids.editors.DefaultBindableDateChooser dcInbetriebnahmeLeuchteBis;
    private de.cismet.cids.editors.DefaultBindableDateChooser dcInbetriebnahmeLeuchteVon;
    private de.cismet.cids.editors.DefaultBindableDateChooser dcNaechsterWechselBis;
    private de.cismet.cids.editors.DefaultBindableDateChooser dcNaechsterWechselVon;
    private de.cismet.cids.editors.DefaultBindableDateChooser dcWechseldatumBis;
    private de.cismet.cids.editors.DefaultBindableDateChooser dcWechseldatumVon;
    private javax.swing.JPanel panAnschlussleistung1dk;
    private javax.swing.JPanel panAnschlussleistung2dk;
    private javax.swing.JPanel panInbetriebnahmeLeuchte;
    private javax.swing.JPanel panLeuchtentyp;
    private javax.swing.JPanel panMain;
    private javax.swing.JPanel panNaechsterWechsel;
    private javax.swing.JPanel panRundsteuerempfaenger;
    private javax.swing.JPanel panSchaltstelle;
    private javax.swing.JPanel panWechseldatum;
    private javax.swing.JTextField txtSchaltstelle;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form LandParcelWindowSearch.
     */
    public LeuchtenWindowSearch() {
        super(Mode.LEUCHTE, "Leuchten");
        initComponents();
        initWithThisSpecificPanel(panMain);

        ((DefaultBindableReferenceCombo)cbAnschlussleistung1dk).setMetaClass(CidsBroker.getInstance().getBelisMetaClass(
                TkeyDoppelkommandoCustomBean.TABLE));
        ((DefaultBindableReferenceCombo)cbAnschlussleistung2dk).setMetaClass(CidsBroker.getInstance().getBelisMetaClass(
                TkeyDoppelkommandoCustomBean.TABLE));
        ((DefaultBindableReferenceCombo)cbLeuchtentyp).setMetaClass(CidsBroker.getInstance().getBelisMetaClass(
                TkeyLeuchtentypCustomBean.TABLE));
        ((DefaultBindableReferenceCombo)cbRundsteuerempfaenger).setMetaClass(CidsBroker.getInstance().getBelisMetaClass(
                RundsteuerempfaengerCustomBean.TABLE));

        AutoCompleteDecorator.decorate(cbLeuchtentyp, new ObjectToKeyStringConverter());
        AutoCompleteDecorator.decorate(cbRundsteuerempfaenger, new ObjectToKeyStringConverter());
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

        panMain = new javax.swing.JPanel();
        panInbetriebnahmeLeuchte = new javax.swing.JPanel();
        cbInbetriebnahmeLeuchteVon = new javax.swing.JCheckBox();
        dcInbetriebnahmeLeuchteVon = new de.cismet.cids.editors.DefaultBindableDateChooser();
        dcInbetriebnahmeLeuchteBis = new de.cismet.cids.editors.DefaultBindableDateChooser();
        cbInbetriebnahmeLeuchteBis = new javax.swing.JCheckBox();
        panWechseldatum = new javax.swing.JPanel();
        cbWechseldatumVon = new javax.swing.JCheckBox();
        dcWechseldatumVon = new de.cismet.cids.editors.DefaultBindableDateChooser();
        dcWechseldatumBis = new de.cismet.cids.editors.DefaultBindableDateChooser();
        cbWechseldatumBis = new javax.swing.JCheckBox();
        panNaechsterWechsel = new javax.swing.JPanel();
        cbNaechsterWechselVon = new javax.swing.JCheckBox();
        dcNaechsterWechselVon = new de.cismet.cids.editors.DefaultBindableDateChooser();
        dcNaechsterWechselBis = new de.cismet.cids.editors.DefaultBindableDateChooser();
        cbNaechsterWechselBis = new javax.swing.JCheckBox();
        panLeuchtentyp = new javax.swing.JPanel();
        cbLeuchtentyp = new DefaultBindableReferenceCombo();
        chkLeuchtentyp = new javax.swing.JCheckBox();
        panRundsteuerempfaenger = new javax.swing.JPanel();
        chkRundsteuerempfaenger = new javax.swing.JCheckBox();
        cbRundsteuerempfaenger = new DefaultBindableReferenceCombo();
        panSchaltstelle = new javax.swing.JPanel();
        chkSchaltstelle = new javax.swing.JCheckBox();
        txtSchaltstelle = new javax.swing.JTextField();
        panAnschlussleistung1dk = new javax.swing.JPanel();
        cbAnschlussleistung1dk = new DefaultBindableReferenceCombo();
        chkAnschlussleistung1dk = new javax.swing.JCheckBox();
        panAnschlussleistung2dk = new javax.swing.JPanel();
        cbAnschlussleistung2dk = new DefaultBindableReferenceCombo();
        chkAnschlussleistung2dk = new javax.swing.JCheckBox();

        panMain.setLayout(new java.awt.GridBagLayout());

        panInbetriebnahmeLeuchte.setBorder(javax.swing.BorderFactory.createTitledBorder(
                org.openide.util.NbBundle.getMessage(
                    LeuchtenWindowSearch.class,
                    "LeuchtenWindowSearch.panInbetriebnahmeLeuchte.border.title"))); // NOI18N
        panInbetriebnahmeLeuchte.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            cbInbetriebnahmeLeuchteVon,
            org.openide.util.NbBundle.getMessage(
                LeuchtenWindowSearch.class,
                "LeuchtenWindowSearch.cbInbetriebnahmeLeuchteVon.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panInbetriebnahmeLeuchte.add(cbInbetriebnahmeLeuchteVon, gridBagConstraints);

        dcInbetriebnahmeLeuchteVon.setMaximumSize(new java.awt.Dimension(132, 25));
        dcInbetriebnahmeLeuchteVon.setMinimumSize(new java.awt.Dimension(132, 25));
        dcInbetriebnahmeLeuchteVon.setPreferredSize(new java.awt.Dimension(132, 25));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                cbInbetriebnahmeLeuchteVon,
                org.jdesktop.beansbinding.ELProperty.create("${selected}"),
                dcInbetriebnahmeLeuchteVon,
                org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        panInbetriebnahmeLeuchte.add(dcInbetriebnahmeLeuchteVon, gridBagConstraints);

        dcInbetriebnahmeLeuchteBis.setMaximumSize(new java.awt.Dimension(132, 25));
        dcInbetriebnahmeLeuchteBis.setMinimumSize(new java.awt.Dimension(132, 25));
        dcInbetriebnahmeLeuchteBis.setPreferredSize(new java.awt.Dimension(132, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                cbInbetriebnahmeLeuchteBis,
                org.jdesktop.beansbinding.ELProperty.create("${selected}"),
                dcInbetriebnahmeLeuchteBis,
                org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        panInbetriebnahmeLeuchte.add(dcInbetriebnahmeLeuchteBis, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            cbInbetriebnahmeLeuchteBis,
            org.openide.util.NbBundle.getMessage(
                LeuchtenWindowSearch.class,
                "LeuchtenWindowSearch.cbInbetriebnahmeLeuchteBis.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panInbetriebnahmeLeuchte.add(cbInbetriebnahmeLeuchteBis, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMain.add(panInbetriebnahmeLeuchte, gridBagConstraints);

        panWechseldatum.setBorder(javax.swing.BorderFactory.createTitledBorder(
                org.openide.util.NbBundle.getMessage(
                    LeuchtenWindowSearch.class,
                    "LeuchtenWindowSearch.panWechseldatum.border.title"))); // NOI18N
        panWechseldatum.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            cbWechseldatumVon,
            org.openide.util.NbBundle.getMessage(
                LeuchtenWindowSearch.class,
                "LeuchtenWindowSearch.cbWechseldatumVon.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panWechseldatum.add(cbWechseldatumVon, gridBagConstraints);

        dcWechseldatumVon.setMaximumSize(new java.awt.Dimension(132, 25));
        dcWechseldatumVon.setMinimumSize(new java.awt.Dimension(132, 25));
        dcWechseldatumVon.setPreferredSize(new java.awt.Dimension(132, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                cbWechseldatumVon,
                org.jdesktop.beansbinding.ELProperty.create("${selected}"),
                dcWechseldatumVon,
                org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        panWechseldatum.add(dcWechseldatumVon, gridBagConstraints);

        dcWechseldatumBis.setMaximumSize(new java.awt.Dimension(132, 25));
        dcWechseldatumBis.setMinimumSize(new java.awt.Dimension(132, 25));
        dcWechseldatumBis.setPreferredSize(new java.awt.Dimension(132, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                cbWechseldatumBis,
                org.jdesktop.beansbinding.ELProperty.create("${selected}"),
                dcWechseldatumBis,
                org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        panWechseldatum.add(dcWechseldatumBis, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            cbWechseldatumBis,
            org.openide.util.NbBundle.getMessage(
                LeuchtenWindowSearch.class,
                "LeuchtenWindowSearch.cbWechseldatumBis.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panWechseldatum.add(cbWechseldatumBis, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMain.add(panWechseldatum, gridBagConstraints);

        panNaechsterWechsel.setBorder(javax.swing.BorderFactory.createTitledBorder(
                org.openide.util.NbBundle.getMessage(
                    LeuchtenWindowSearch.class,
                    "LeuchtenWindowSearch.panNaechsterWechsel.border.title"))); // NOI18N
        panNaechsterWechsel.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            cbNaechsterWechselVon,
            org.openide.util.NbBundle.getMessage(
                LeuchtenWindowSearch.class,
                "LeuchtenWindowSearch.cbNaechsterWechselVon.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panNaechsterWechsel.add(cbNaechsterWechselVon, gridBagConstraints);

        dcNaechsterWechselVon.setMaximumSize(new java.awt.Dimension(132, 25));
        dcNaechsterWechselVon.setMinimumSize(new java.awt.Dimension(132, 25));
        dcNaechsterWechselVon.setPreferredSize(new java.awt.Dimension(132, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                cbNaechsterWechselVon,
                org.jdesktop.beansbinding.ELProperty.create("${selected}"),
                dcNaechsterWechselVon,
                org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        panNaechsterWechsel.add(dcNaechsterWechselVon, gridBagConstraints);

        dcNaechsterWechselBis.setMaximumSize(new java.awt.Dimension(132, 25));
        dcNaechsterWechselBis.setMinimumSize(new java.awt.Dimension(132, 25));
        dcNaechsterWechselBis.setPreferredSize(new java.awt.Dimension(132, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                cbNaechsterWechselBis,
                org.jdesktop.beansbinding.ELProperty.create("${selected}"),
                dcNaechsterWechselBis,
                org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        panNaechsterWechsel.add(dcNaechsterWechselBis, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            cbNaechsterWechselBis,
            org.openide.util.NbBundle.getMessage(
                LeuchtenWindowSearch.class,
                "LeuchtenWindowSearch.cbNaechsterWechselBis.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panNaechsterWechsel.add(cbNaechsterWechselBis, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMain.add(panNaechsterWechsel, gridBagConstraints);

        panLeuchtentyp.setBorder(javax.swing.BorderFactory.createTitledBorder(
                org.openide.util.NbBundle.getMessage(
                    LeuchtenWindowSearch.class,
                    "LeuchtenWindowSearch.panLeuchtentyp.border.title"))); // NOI18N
        panLeuchtentyp.setLayout(new java.awt.GridBagLayout());

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                chkLeuchtentyp,
                org.jdesktop.beansbinding.ELProperty.create("${selected}"),
                cbLeuchtentyp,
                org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panLeuchtentyp.add(cbLeuchtentyp, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            chkLeuchtentyp,
            org.openide.util.NbBundle.getMessage(
                LeuchtenWindowSearch.class,
                "LeuchtenWindowSearch.chkLeuchtentyp.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panLeuchtentyp.add(chkLeuchtentyp, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMain.add(panLeuchtentyp, gridBagConstraints);

        panRundsteuerempfaenger.setBorder(javax.swing.BorderFactory.createTitledBorder(
                org.openide.util.NbBundle.getMessage(
                    LeuchtenWindowSearch.class,
                    "LeuchtenWindowSearch.panRundsteuerempfaenger.border.title"))); // NOI18N
        panRundsteuerempfaenger.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            chkRundsteuerempfaenger,
            org.openide.util.NbBundle.getMessage(
                LeuchtenWindowSearch.class,
                "LeuchtenWindowSearch.chkRundsteuerempfaenger.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panRundsteuerempfaenger.add(chkRundsteuerempfaenger, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                chkRundsteuerempfaenger,
                org.jdesktop.beansbinding.ELProperty.create("${selected}"),
                cbRundsteuerempfaenger,
                org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panRundsteuerempfaenger.add(cbRundsteuerempfaenger, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMain.add(panRundsteuerempfaenger, gridBagConstraints);

        panSchaltstelle.setBorder(javax.swing.BorderFactory.createTitledBorder(
                org.openide.util.NbBundle.getMessage(
                    LeuchtenWindowSearch.class,
                    "LeuchtenWindowSearch.panSchaltstelle.border.title"))); // NOI18N
        panSchaltstelle.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            chkSchaltstelle,
            org.openide.util.NbBundle.getMessage(
                LeuchtenWindowSearch.class,
                "LeuchtenWindowSearch.chkSchaltstelle.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panSchaltstelle.add(chkSchaltstelle, gridBagConstraints);

        txtSchaltstelle.setText(org.openide.util.NbBundle.getMessage(
                LeuchtenWindowSearch.class,
                "LeuchtenWindowSearch.txtSchaltstelle.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                chkSchaltstelle,
                org.jdesktop.beansbinding.ELProperty.create("${selected}"),
                txtSchaltstelle,
                org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panSchaltstelle.add(txtSchaltstelle, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMain.add(panSchaltstelle, gridBagConstraints);

        panAnschlussleistung1dk.setBorder(javax.swing.BorderFactory.createTitledBorder(
                org.openide.util.NbBundle.getMessage(
                    LeuchtenWindowSearch.class,
                    "LeuchtenWindowSearch.panAnschlussleistung1dk.border.title"))); // NOI18N
        panAnschlussleistung1dk.setLayout(new java.awt.GridBagLayout());

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                chkAnschlussleistung1dk,
                org.jdesktop.beansbinding.ELProperty.create("${selected}"),
                cbAnschlussleistung1dk,
                org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panAnschlussleistung1dk.add(cbAnschlussleistung1dk, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            chkAnschlussleistung1dk,
            org.openide.util.NbBundle.getMessage(
                LeuchtenWindowSearch.class,
                "LeuchtenWindowSearch.chkAnschlussleistung1dk.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panAnschlussleistung1dk.add(chkAnschlussleistung1dk, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMain.add(panAnschlussleistung1dk, gridBagConstraints);

        panAnschlussleistung2dk.setBorder(javax.swing.BorderFactory.createTitledBorder(
                org.openide.util.NbBundle.getMessage(
                    LeuchtenWindowSearch.class,
                    "LeuchtenWindowSearch.panAnschlussleistung2dk.border.title"))); // NOI18N
        panAnschlussleistung2dk.setLayout(new java.awt.GridBagLayout());

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                chkAnschlussleistung2dk,
                org.jdesktop.beansbinding.ELProperty.create("${selected}"),
                cbAnschlussleistung2dk,
                org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panAnschlussleistung2dk.add(cbAnschlussleistung2dk, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            chkAnschlussleistung2dk,
            org.openide.util.NbBundle.getMessage(
                LeuchtenWindowSearch.class,
                "LeuchtenWindowSearch.chkAnschlussleistung2dk.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panAnschlussleistung2dk.add(chkAnschlussleistung2dk, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMain.add(panAnschlussleistung2dk, gridBagConstraints);

        setLayout(null);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    @Override
    protected BelisSearchStatement createSearchStatement(final Geometry searchGeom) {
        final String inbetriebnahmeLeuchteVon = (cbInbetriebnahmeLeuchteVon.isSelected())
            ? dcInbetriebnahmeLeuchteVon.getDate().toString() : null;
        final String inbetriebnahmeLeuchteBis = (cbInbetriebnahmeLeuchteBis.isSelected())
            ? dcInbetriebnahmeLeuchteBis.getDate().toString() : null;
        final String wechseldatumVon = (cbWechseldatumVon.isSelected()) ? dcWechseldatumVon.getDate().toString() : null;
        final String wechseldatumBis = (cbWechseldatumBis.isSelected()) ? dcWechseldatumBis.getDate().toString() : null;
        final String naechsterWechselVon = (cbNaechsterWechselVon.isSelected())
            ? dcNaechsterWechselVon.getDate().toString() : null;
        final String naechsterWechselBis = (cbNaechsterWechselBis.isSelected())
            ? dcNaechsterWechselBis.getDate().toString() : null;

        final String schaltstelle = (chkSchaltstelle.isSelected()) ? txtSchaltstelle.getText() : null;
        final Integer leuchtentyp = (chkLeuchtentyp.isSelected())
            ? ((CidsBean)cbLeuchtentyp.getSelectedItem()).getMetaObject().getId() : null;
        final Integer rundsteuerempfaenger = (chkRundsteuerempfaenger.isSelected())
            ? ((CidsBean)cbRundsteuerempfaenger.getSelectedItem()).getMetaObject().getId() : null;
        final Integer anschlussleistung1dk = (chkAnschlussleistung1dk.isSelected())
            ? ((CidsBean)cbAnschlussleistung1dk.getSelectedItem()).getMetaObject().getId() : null;
        final Integer anschlussleistung2dk = (chkAnschlussleistung2dk.isSelected())
            ? ((CidsBean)cbAnschlussleistung2dk.getSelectedItem()).getMetaObject().getId() : null;

        final LeuchteSearchStatement leuchteSearchStatement = new LeuchteSearchStatement();
        leuchteSearchStatement.setInbetriebnahme_leuchte(inbetriebnahmeLeuchteVon, inbetriebnahmeLeuchteBis);
        leuchteSearchStatement.setWechseldatum(wechseldatumVon, wechseldatumBis);
        leuchteSearchStatement.setNaechster_wechsel(naechsterWechselVon, naechsterWechselBis);
        leuchteSearchStatement.setFk_leuchttyp(leuchtentyp);
        leuchteSearchStatement.setRundsteuerempfaenger(rundsteuerempfaenger);
        leuchteSearchStatement.setSchaltstelle(schaltstelle);
        leuchteSearchStatement.setFk_dk1(anschlussleistung1dk);
        leuchteSearchStatement.setFk_dk1(anschlussleistung2dk);

        leuchteSearchStatement.setGeometry(searchGeom);
        return leuchteSearchStatement;
    }
}
