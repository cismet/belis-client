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

import de.cismet.belis.broker.CidsBroker;

import de.cismet.belis.server.search.BelisSearchStatement;
import de.cismet.belis.server.search.StandortSearchStatement;

import de.cismet.cids.custom.beans.belis.AnlagengruppeCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyKlassifizierungCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyMastartCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyMasttypCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyUnterhMastCustomBean;

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
public class StandortWindowSearch extends BelisWindowSearch {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(StandortWindowSearch.class);

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbAnlagengruppe;
    private javax.swing.JCheckBox cbElekPruefungBis;
    private javax.swing.JCheckBox cbElekPruefungVon;
    private javax.swing.JComboBox cbKlassifizierung;
    private javax.swing.JCheckBox cbMastanstrichBis;
    private javax.swing.JCheckBox cbMastanstrichVon;
    private javax.swing.JComboBox cbMastart;
    private javax.swing.JCheckBox cbMastschutzBis;
    private javax.swing.JCheckBox cbMastschutzVon;
    private javax.swing.JComboBox cbMasttyp;
    private javax.swing.JCheckBox cbStandsicherheitspruefungBis;
    private javax.swing.JCheckBox cbStandsicherheitspruefungVon;
    private javax.swing.JComboBox cbUnterhaltspflichtMast;
    private javax.swing.JCheckBox chkAnlagengruppe;
    private javax.swing.JCheckBox chkKlassifizierung;
    private javax.swing.JCheckBox chkMastart;
    private javax.swing.JCheckBox chkMasttyp;
    private javax.swing.JCheckBox chkUnterhaltspflichtMast;
    private de.cismet.cids.editors.DefaultBindableDateChooser dcElekPruefungBis;
    private de.cismet.cids.editors.DefaultBindableDateChooser dcElekPruefungVon;
    private de.cismet.cids.editors.DefaultBindableDateChooser dcMastanstrichBis;
    private de.cismet.cids.editors.DefaultBindableDateChooser dcMastanstrichVon;
    private de.cismet.cids.editors.DefaultBindableDateChooser dcMastschutzBis;
    private de.cismet.cids.editors.DefaultBindableDateChooser dcMastschutzVon;
    private de.cismet.cids.editors.DefaultBindableDateChooser dcStandsicherheitspruefungBis;
    private de.cismet.cids.editors.DefaultBindableDateChooser dcStandsicherheitspruefungVon;
    private javax.swing.JPanel panAnlagengruppe;
    private javax.swing.JPanel panElekPruefung;
    private javax.swing.JPanel panKlassifizierung;
    private javax.swing.JPanel panMain;
    private javax.swing.JPanel panMastanstrich;
    private javax.swing.JPanel panMastart;
    private javax.swing.JPanel panMastschutz;
    private javax.swing.JPanel panMasttyp;
    private javax.swing.JPanel panStandsicherheitspruefung;
    private javax.swing.JPanel panUnterhaltspflichtMast;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form LandParcelWindowSearch.
     */
    public StandortWindowSearch() {
        super(Mode.STANDORT, "Masten");
        initComponents();
        initWithThisSpecificPanel(panMain);

        ((DefaultBindableReferenceCombo)cbAnlagengruppe).setMetaClass(CidsBroker.getInstance().getBelisMetaClass(
                AnlagengruppeCustomBean.TABLE));
        ((DefaultBindableReferenceCombo)cbMastart).setMetaClass(CidsBroker.getInstance().getBelisMetaClass(
                TkeyMastartCustomBean.TABLE));
        ((DefaultBindableReferenceCombo)cbMasttyp).setMetaClass(CidsBroker.getInstance().getBelisMetaClass(
                TkeyMasttypCustomBean.TABLE));
        ((DefaultBindableReferenceCombo)cbKlassifizierung).setMetaClass(CidsBroker.getInstance().getBelisMetaClass(
                TkeyKlassifizierungCustomBean.TABLE));
        ((DefaultBindableReferenceCombo)cbUnterhaltspflichtMast).setMetaClass(CidsBroker.getInstance()
                    .getBelisMetaClass(TkeyUnterhMastCustomBean.TABLE));
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
        panMastschutz = new javax.swing.JPanel();
        cbMastschutzVon = new javax.swing.JCheckBox();
        dcMastschutzVon = new de.cismet.cids.editors.DefaultBindableDateChooser();
        dcMastschutzBis = new de.cismet.cids.editors.DefaultBindableDateChooser();
        cbMastschutzBis = new javax.swing.JCheckBox();
        panMastanstrich = new javax.swing.JPanel();
        cbMastanstrichVon = new javax.swing.JCheckBox();
        dcMastanstrichVon = new de.cismet.cids.editors.DefaultBindableDateChooser();
        dcMastanstrichBis = new de.cismet.cids.editors.DefaultBindableDateChooser();
        cbMastanstrichBis = new javax.swing.JCheckBox();
        panElekPruefung = new javax.swing.JPanel();
        cbElekPruefungVon = new javax.swing.JCheckBox();
        dcElekPruefungVon = new de.cismet.cids.editors.DefaultBindableDateChooser();
        dcElekPruefungBis = new de.cismet.cids.editors.DefaultBindableDateChooser();
        cbElekPruefungBis = new javax.swing.JCheckBox();
        panStandsicherheitspruefung = new javax.swing.JPanel();
        cbStandsicherheitspruefungVon = new javax.swing.JCheckBox();
        dcStandsicherheitspruefungVon = new de.cismet.cids.editors.DefaultBindableDateChooser();
        dcStandsicherheitspruefungBis = new de.cismet.cids.editors.DefaultBindableDateChooser();
        cbStandsicherheitspruefungBis = new javax.swing.JCheckBox();
        panMastart = new javax.swing.JPanel();
        cbMastart = new DefaultBindableReferenceCombo();
        chkMastart = new javax.swing.JCheckBox();
        panMasttyp = new javax.swing.JPanel();
        cbMasttyp = new DefaultBindableReferenceCombo();
        chkMasttyp = new javax.swing.JCheckBox();
        panKlassifizierung = new javax.swing.JPanel();
        cbKlassifizierung = new DefaultBindableReferenceCombo();
        chkKlassifizierung = new javax.swing.JCheckBox();
        panAnlagengruppe = new javax.swing.JPanel();
        cbAnlagengruppe = new DefaultBindableReferenceCombo();
        chkAnlagengruppe = new javax.swing.JCheckBox();
        panUnterhaltspflichtMast = new javax.swing.JPanel();
        cbUnterhaltspflichtMast = new DefaultBindableReferenceCombo();
        chkUnterhaltspflichtMast = new javax.swing.JCheckBox();

        panMain.setLayout(new java.awt.GridBagLayout());

        panMastschutz.setBorder(javax.swing.BorderFactory.createTitledBorder(
                org.openide.util.NbBundle.getMessage(
                    StandortWindowSearch.class,
                    "StandortWindowSearch.panMastschutz.border.title"))); // NOI18N
        panMastschutz.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            cbMastschutzVon,
            org.openide.util.NbBundle.getMessage(
                StandortWindowSearch.class,
                "StandortWindowSearch.cbMastschutzVon.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panMastschutz.add(cbMastschutzVon, gridBagConstraints);

        dcMastschutzVon.setMaximumSize(new java.awt.Dimension(132, 25));
        dcMastschutzVon.setMinimumSize(new java.awt.Dimension(132, 25));
        dcMastschutzVon.setPreferredSize(new java.awt.Dimension(132, 25));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                cbMastschutzVon,
                org.jdesktop.beansbinding.ELProperty.create("${selected}"),
                dcMastschutzVon,
                org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        panMastschutz.add(dcMastschutzVon, gridBagConstraints);

        dcMastschutzBis.setMaximumSize(new java.awt.Dimension(132, 25));
        dcMastschutzBis.setMinimumSize(new java.awt.Dimension(132, 25));
        dcMastschutzBis.setPreferredSize(new java.awt.Dimension(132, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                cbMastschutzBis,
                org.jdesktop.beansbinding.ELProperty.create("${selected}"),
                dcMastschutzBis,
                org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        panMastschutz.add(dcMastschutzBis, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            cbMastschutzBis,
            org.openide.util.NbBundle.getMessage(
                StandortWindowSearch.class,
                "StandortWindowSearch.cbMastschutzBis.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panMastschutz.add(cbMastschutzBis, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMain.add(panMastschutz, gridBagConstraints);

        panMastanstrich.setBorder(javax.swing.BorderFactory.createTitledBorder(
                org.openide.util.NbBundle.getMessage(
                    StandortWindowSearch.class,
                    "StandortWindowSearch.panMastanstrich.border.title"))); // NOI18N
        panMastanstrich.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            cbMastanstrichVon,
            org.openide.util.NbBundle.getMessage(
                StandortWindowSearch.class,
                "StandortWindowSearch.cbMastanstrichVon.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panMastanstrich.add(cbMastanstrichVon, gridBagConstraints);

        dcMastanstrichVon.setMaximumSize(new java.awt.Dimension(132, 25));
        dcMastanstrichVon.setMinimumSize(new java.awt.Dimension(132, 25));
        dcMastanstrichVon.setPreferredSize(new java.awt.Dimension(132, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                cbMastanstrichVon,
                org.jdesktop.beansbinding.ELProperty.create("${selected}"),
                dcMastanstrichVon,
                org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        panMastanstrich.add(dcMastanstrichVon, gridBagConstraints);

        dcMastanstrichBis.setMaximumSize(new java.awt.Dimension(132, 25));
        dcMastanstrichBis.setMinimumSize(new java.awt.Dimension(132, 25));
        dcMastanstrichBis.setPreferredSize(new java.awt.Dimension(132, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                cbMastanstrichBis,
                org.jdesktop.beansbinding.ELProperty.create("${selected}"),
                dcMastanstrichBis,
                org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        panMastanstrich.add(dcMastanstrichBis, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            cbMastanstrichBis,
            org.openide.util.NbBundle.getMessage(
                StandortWindowSearch.class,
                "StandortWindowSearch.cbMastanstrichBis.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panMastanstrich.add(cbMastanstrichBis, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMain.add(panMastanstrich, gridBagConstraints);

        panElekPruefung.setBorder(javax.swing.BorderFactory.createTitledBorder(
                org.openide.util.NbBundle.getMessage(
                    StandortWindowSearch.class,
                    "StandortWindowSearch.panElekPruefung.border.title"))); // NOI18N
        panElekPruefung.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            cbElekPruefungVon,
            org.openide.util.NbBundle.getMessage(
                StandortWindowSearch.class,
                "StandortWindowSearch.cbElekPruefungVon.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panElekPruefung.add(cbElekPruefungVon, gridBagConstraints);

        dcElekPruefungVon.setMaximumSize(new java.awt.Dimension(132, 25));
        dcElekPruefungVon.setMinimumSize(new java.awt.Dimension(132, 25));
        dcElekPruefungVon.setPreferredSize(new java.awt.Dimension(132, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                cbElekPruefungVon,
                org.jdesktop.beansbinding.ELProperty.create("${selected}"),
                dcElekPruefungVon,
                org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        panElekPruefung.add(dcElekPruefungVon, gridBagConstraints);

        dcElekPruefungBis.setMaximumSize(new java.awt.Dimension(132, 25));
        dcElekPruefungBis.setMinimumSize(new java.awt.Dimension(132, 25));
        dcElekPruefungBis.setPreferredSize(new java.awt.Dimension(132, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                cbElekPruefungBis,
                org.jdesktop.beansbinding.ELProperty.create("${selected}"),
                dcElekPruefungBis,
                org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        panElekPruefung.add(dcElekPruefungBis, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            cbElekPruefungBis,
            org.openide.util.NbBundle.getMessage(
                StandortWindowSearch.class,
                "StandortWindowSearch.cbElekPruefungBis.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panElekPruefung.add(cbElekPruefungBis, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMain.add(panElekPruefung, gridBagConstraints);

        panStandsicherheitspruefung.setBorder(javax.swing.BorderFactory.createTitledBorder(
                org.openide.util.NbBundle.getMessage(
                    StandortWindowSearch.class,
                    "StandortWindowSearch.panStandsicherheitspruefung.border.title"))); // NOI18N
        panStandsicherheitspruefung.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            cbStandsicherheitspruefungVon,
            org.openide.util.NbBundle.getMessage(
                StandortWindowSearch.class,
                "StandortWindowSearch.cbStandsicherheitspruefungVon.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panStandsicherheitspruefung.add(cbStandsicherheitspruefungVon, gridBagConstraints);

        dcStandsicherheitspruefungVon.setMaximumSize(new java.awt.Dimension(132, 25));
        dcStandsicherheitspruefungVon.setMinimumSize(new java.awt.Dimension(132, 25));
        dcStandsicherheitspruefungVon.setPreferredSize(new java.awt.Dimension(132, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                cbStandsicherheitspruefungVon,
                org.jdesktop.beansbinding.ELProperty.create("${selected}"),
                dcStandsicherheitspruefungVon,
                org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        panStandsicherheitspruefung.add(dcStandsicherheitspruefungVon, gridBagConstraints);

        dcStandsicherheitspruefungBis.setMaximumSize(new java.awt.Dimension(132, 25));
        dcStandsicherheitspruefungBis.setMinimumSize(new java.awt.Dimension(132, 25));
        dcStandsicherheitspruefungBis.setPreferredSize(new java.awt.Dimension(132, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                cbStandsicherheitspruefungBis,
                org.jdesktop.beansbinding.ELProperty.create("${selected}"),
                dcStandsicherheitspruefungBis,
                org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        panStandsicherheitspruefung.add(dcStandsicherheitspruefungBis, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            cbStandsicherheitspruefungBis,
            org.openide.util.NbBundle.getMessage(
                StandortWindowSearch.class,
                "StandortWindowSearch.cbStandsicherheitspruefungBis.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panStandsicherheitspruefung.add(cbStandsicherheitspruefungBis, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMain.add(panStandsicherheitspruefung, gridBagConstraints);

        panMastart.setBorder(javax.swing.BorderFactory.createTitledBorder(
                org.openide.util.NbBundle.getMessage(
                    StandortWindowSearch.class,
                    "StandortWindowSearch.panMastart.border.title"))); // NOI18N
        panMastart.setLayout(new java.awt.GridBagLayout());

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                chkMastart,
                org.jdesktop.beansbinding.ELProperty.create("${selected}"),
                cbMastart,
                org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panMastart.add(cbMastart, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            chkMastart,
            org.openide.util.NbBundle.getMessage(StandortWindowSearch.class, "StandortWindowSearch.chkMastart.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panMastart.add(chkMastart, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMain.add(panMastart, gridBagConstraints);

        panMasttyp.setBorder(javax.swing.BorderFactory.createTitledBorder(
                org.openide.util.NbBundle.getMessage(
                    StandortWindowSearch.class,
                    "StandortWindowSearch.panMasttyp.border.title"))); // NOI18N
        panMasttyp.setLayout(new java.awt.GridBagLayout());

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                chkMasttyp,
                org.jdesktop.beansbinding.ELProperty.create("${selected}"),
                cbMasttyp,
                org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panMasttyp.add(cbMasttyp, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            chkMasttyp,
            org.openide.util.NbBundle.getMessage(StandortWindowSearch.class, "StandortWindowSearch.chkMasttyp.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panMasttyp.add(chkMasttyp, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMain.add(panMasttyp, gridBagConstraints);

        panKlassifizierung.setBorder(javax.swing.BorderFactory.createTitledBorder(
                org.openide.util.NbBundle.getMessage(
                    StandortWindowSearch.class,
                    "StandortWindowSearch.panKlassifizierung.border.title"))); // NOI18N
        panKlassifizierung.setLayout(new java.awt.GridBagLayout());

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                chkKlassifizierung,
                org.jdesktop.beansbinding.ELProperty.create("${selected}"),
                cbKlassifizierung,
                org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panKlassifizierung.add(cbKlassifizierung, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            chkKlassifizierung,
            org.openide.util.NbBundle.getMessage(
                StandortWindowSearch.class,
                "StandortWindowSearch.chkKlassifizierung.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panKlassifizierung.add(chkKlassifizierung, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMain.add(panKlassifizierung, gridBagConstraints);

        panAnlagengruppe.setBorder(javax.swing.BorderFactory.createTitledBorder(
                org.openide.util.NbBundle.getMessage(
                    StandortWindowSearch.class,
                    "StandortWindowSearch.panAnlagengruppe.border.title"))); // NOI18N
        panAnlagengruppe.setLayout(new java.awt.GridBagLayout());

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                chkAnlagengruppe,
                org.jdesktop.beansbinding.ELProperty.create("${selected}"),
                cbAnlagengruppe,
                org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panAnlagengruppe.add(cbAnlagengruppe, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            chkAnlagengruppe,
            org.openide.util.NbBundle.getMessage(
                StandortWindowSearch.class,
                "StandortWindowSearch.chkAnlagengruppe.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panAnlagengruppe.add(chkAnlagengruppe, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMain.add(panAnlagengruppe, gridBagConstraints);

        panUnterhaltspflichtMast.setBorder(javax.swing.BorderFactory.createTitledBorder(
                org.openide.util.NbBundle.getMessage(
                    StandortWindowSearch.class,
                    "StandortWindowSearch.panUnterhaltspflichtMast.border.title"))); // NOI18N
        panUnterhaltspflichtMast.setLayout(new java.awt.GridBagLayout());

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                chkUnterhaltspflichtMast,
                org.jdesktop.beansbinding.ELProperty.create("${selected}"),
                cbUnterhaltspflichtMast,
                org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panUnterhaltspflichtMast.add(cbUnterhaltspflichtMast, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            chkUnterhaltspflichtMast,
            org.openide.util.NbBundle.getMessage(
                StandortWindowSearch.class,
                "StandortWindowSearch.chkUnterhaltspflichtMast.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panUnterhaltspflichtMast.add(chkUnterhaltspflichtMast, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMain.add(panUnterhaltspflichtMast, gridBagConstraints);

        setLayout(null);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    @Override
    protected BelisSearchStatement createSearchStatement(final Geometry searchGeom) {
        final String mastschutz_von = (cbMastschutzVon.isSelected()) ? dcMastschutzVon.getDate().toString() : null;
        final String mastschutz_bis = (cbMastschutzBis.isSelected()) ? dcMastschutzBis.getDate().toString() : null;
        final String mastanstrich_von = (cbMastanstrichVon.isSelected()) ? dcMastanstrichVon.getDate().toString()
                                                                         : null;
        final String mastanstrich_bis = (cbMastanstrichBis.isSelected()) ? dcMastanstrichBis.getDate().toString()
                                                                         : null;
        final String elek_pruefung_von = (cbElekPruefungVon.isSelected()) ? dcElekPruefungVon.getDate().toString()
                                                                          : null;
        final String elek_pruefung_bis = (cbElekPruefungBis.isSelected()) ? dcElekPruefungBis.getDate().toString()
                                                                          : null;
        final String standsicherheitspruefung_von = (cbStandsicherheitspruefungVon.isSelected())
            ? dcStandsicherheitspruefungVon.getDate().toString() : null;
        final String standsicherheitspruefung_bis = (cbStandsicherheitspruefungBis.isSelected())
            ? dcStandsicherheitspruefungBis.getDate().toString() : null;

        final Integer mastart = (chkMastart.isSelected())
            ? ((CidsBean)cbMastart.getSelectedItem()).getMetaObject().getId() : null;
        final Integer masttyp = (chkMasttyp.isSelected())
            ? ((CidsBean)cbMasttyp.getSelectedItem()).getMetaObject().getId() : null;
        final Integer klassifizierung = (chkKlassifizierung.isSelected())
            ? ((CidsBean)cbKlassifizierung.getSelectedItem()).getMetaObject().getId() : null;
        final Integer anlagengruppe = (chkAnlagengruppe.isSelected())
            ? ((CidsBean)cbAnlagengruppe.getSelectedItem()).getMetaObject().getId() : null;
        final Integer unterhaltspflicht_mast = (chkUnterhaltspflichtMast.isSelected())
            ? ((CidsBean)cbUnterhaltspflichtMast.getSelectedItem()).getMetaObject().getId() : null;

        final StandortSearchStatement standortSearchStatement = new StandortSearchStatement();

        standortSearchStatement.setMastschutz(mastschutz_von, mastschutz_bis);
        standortSearchStatement.setMastanstrich(mastanstrich_von, mastanstrich_bis);
        standortSearchStatement.setElek_pruefung(elek_pruefung_von, elek_pruefung_bis);
        standortSearchStatement.setStandsicherheitspruefung(standsicherheitspruefung_von, standsicherheitspruefung_bis);
        standortSearchStatement.setKlassifizierung_id(klassifizierung);
        standortSearchStatement.setAnlagengruppe_id(anlagengruppe);
        standortSearchStatement.setUnterhaltspflicht_mast_id(unterhaltspflicht_mast);
        standortSearchStatement.setMasttyp_id(masttyp);
        standortSearchStatement.setMastart_id(mastart);

        standortSearchStatement.setGeometry(searchGeom);
        return standortSearchStatement;
    }
}