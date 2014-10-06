/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.belis.gui.search;

import org.jdom.Element;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import de.cismet.belis.broker.BelisBroker;

import de.cismet.belis.util.BelisIcons;

import de.cismet.cismap.commons.BoundingBox;
import de.cismet.cismap.commons.wfsforms.WFSFormAdress;
import de.cismet.cismap.commons.wfsforms.WFSFormAdressListener;
import de.cismet.cismap.commons.wfsforms.WFSFormQuery;

import de.cismet.commons.architecture.util.ArchitectureUtils;

import de.cismet.tools.configuration.Configurable;
import de.cismet.tools.configuration.NoWriteError;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class AddressSearchControl extends WFSFormAdress implements Configurable, SearchControl, WFSFormAdressListener {

    //~ Static fields/initializers ---------------------------------------------

    protected static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(AddressSearchControl.class);

    //~ Instance fields --------------------------------------------------------

    final ArrayList<AddressChangedListener> addressListener = new ArrayList<AddressChangedListener>();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JComboBox _cboNr;
    protected javax.swing.JComboBox _cboStreets;
    protected javax.swing.JCheckBox _chkLockScale;
    protected javax.swing.JCheckBox _chkVisualize;
    protected javax.swing.JButton _cmdOk;
    private javax.swing.JLabel _jLabel1;
    protected javax.swing.JLabel _jLabel2;
    protected javax.swing.JPanel _panEmpty;
    protected javax.swing.JProgressBar _prbNr;
    protected javax.swing.JProgressBar _prbStreets;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AddressSearchControl object.
     */
    public AddressSearchControl() {
        super();

        initComponents();

        addWFSFormAddressListner(this);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void configure(final Element parent) {
    }

    @Override
    public Element getConfiguration() throws NoWriteError {
        return null;
    }

    @Override
    public void masterConfigure(final Element parent) {
        log.info("Address Search Panel wird konfiguriert");
        try {
            final Element searchConf = parent.getChild("ApplicationSpecificConfiguration")
                        .getChild("SearchPanel")
                        .getChild("AddressSearch");
            final Vector<WFSFormQuery> queryVector = new Vector<WFSFormQuery>();
            final List queries = searchConf.getChildren("Query");
            for (final Object oq : queries) {
                final Element q = (Element)oq;
                final WFSFormQuery query = new WFSFormQuery();
                query.setComponentName(q.getAttribute("componentName").getValue());
                query.setDisplayTextProperty(q.getAttribute("displayTextProperty").getValue());
                query.setExtentProperty(q.getAttribute("extentProperty").getValue());
                query.setFilename(q.getAttribute("queryFile").getValue());
                query.setWfsQueryString(readFileFromClassPathAsString(query.getFilename()));
                query.setId(q.getAttribute("id").getValue());
                query.setIdProperty(q.getAttribute("idProperty").getValue());
                query.setServerUrl(q.getAttribute("server").getValue());
                query.setTitle(q.getAttribute("title").getValue());
                query.setType(q.getAttribute("type").getValue());
                try {
                    query.setPropertyPrefix(q.getAttribute("propertyPrefix").getValue());
                } catch (Exception skip) {
                    query.setPropertyPrefix(null);
                }
                try {
                    query.setPropertyNamespace(q.getAttribute("propertyNamespace").getValue());
                } catch (Exception skip) {
                    query.setPropertyNamespace(null);
                }
                try {
                    query.setPositionProperty(q.getAttribute("positionProperty").getValue());
                } catch (Exception skip) {
                    query.setPositionProperty(null);
                }

                // optional
                if (q.getAttribute("queryPlaceholder") != null) {
                    query.setQueryPlaceholder(q.getAttribute("queryPlaceholder").getValue());
                }
                queryVector.add(query);
            }
            setQueries(queryVector);
        } catch (Exception ex) {
            log.error("Fehler beim einlesen der Address Abfragen (Straße/Hausnummer)", ex);
        }
    }
    /**
     * ToDo util of Broker ??
     *
     * @param   filePath  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  java.io.IOException  DOCUMENT ME!
     */
    private String readFileFromClassPathAsString(final String filePath) throws java.io.IOException {
        final InputStream is = getClass().getResourceAsStream(filePath);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        final StringBuffer fileData = new StringBuffer(1000);
        char[] buf = new char[1024];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
            final String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();
        return fileData.toString();
    }

    @Override
    public void searchFinished() {
//        setSearchEnabled(true);
    }

    @Override
    public void searchStarted() {
    }

    // ToDo Inferface Enablelable or something
    @Override
    public void setSearchEnabled(final boolean isSearchEnabled) {
        ArchitectureUtils.enableContainerRecursivley(_panEmpty, isSearchEnabled);
        _chkVisualize.setEnabled(isSearchEnabled);
        _chkLockScale.setEnabled(isSearchEnabled);
        _cmdOk.setEnabled(isSearchEnabled);
    }

    @Override
    public void wfsFormAddressPositioned(final BoundingBox addressBB) {
        if (log.isDebugEnabled()) {
            log.debug("AddressSearch started --> searching db for geometries boundingbox: " + addressBB);
        }
        getMappingComponent().gotoBoundingBoxWithHistory(addressBB);
    }

    @Override
    public void wfsFormAdressNrSelected() {
    }

    @Override
    public void wfsFormAdressStreetSelected() {
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        _prbStreets = prbStreets;
        _prbNr = prbNr;
        _chkLockScale = chkLockScale;
        _jLabel2 = jLabel2;
        _jLabel1 = jLabel1;
        _panEmpty = panEmpty;
        _cmdOk = cmdOk;
        _chkVisualize = chkVisualize;
        _cboNr = cboNr;
        _cboStreets = cboStreets;

        _prbStreets.setBorderPainted(false);
        _prbStreets.setMaximumSize(new java.awt.Dimension(32767, 5));
        _prbStreets.setMinimumSize(new java.awt.Dimension(10, 5));
        _prbStreets.setPreferredSize(new java.awt.Dimension(150, 5));
        _prbStreets.setVisible(false);

        _prbNr.setBorderPainted(false);
        _prbNr.setMaximumSize(new java.awt.Dimension(32767, 5));
        _prbNr.setMinimumSize(new java.awt.Dimension(10, 5));
        _prbNr.setPreferredSize(new java.awt.Dimension(150, 5));
        _prbNr.setVisible(false);

        _chkLockScale.setToolTipText(org.openide.util.NbBundle.getMessage(
                AddressSearchControl.class,
                "AddressSearchControl._chkLockScale.toolTipText")); // NOI18N
        _chkLockScale.setVisible(false);

        _jLabel2.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cismap/commons/gui/res/fixMapScale.png"))); // NOI18N
        _jLabel2.setToolTipText(org.openide.util.NbBundle.getMessage(
                AddressSearchControl.class,
                "AddressSearchControl._jLabel2.toolTipText"));                                 // NOI18N
        _jLabel2.setVisible(false);

        _jLabel1.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cismap/commons/gui/res/markPoint.png"))); // NOI18N
        _jLabel1.setToolTipText(org.openide.util.NbBundle.getMessage(
                AddressSearchControl.class,
                "AddressSearchControl._jLabel1.toolTipText"));                               // NOI18N
        jLabel1.setVisible(false);

        _panEmpty.setMinimumSize(new java.awt.Dimension(1, 1));
        _panEmpty.setOpaque(false);

        final javax.swing.GroupLayout _panEmptyLayout = new javax.swing.GroupLayout(_panEmpty);
        _panEmpty.setLayout(_panEmptyLayout);
        _panEmptyLayout.setHorizontalGroup(
            _panEmptyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                1,
                Short.MAX_VALUE));
        _panEmptyLayout.setVerticalGroup(
            _panEmptyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                1,
                Short.MAX_VALUE));

        _panEmpty.setVisible(false);

        setBorder(null);
        setMaximumSize(new java.awt.Dimension(290, 28));
        setMinimumSize(new java.awt.Dimension(290, 28));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(290, 28));
        addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    formActionPerformed(evt);
                }
            });

        _cmdOk.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/16/dirrection--arrow.png"))); // NOI18N
        _cmdOk.setToolTipText(org.openide.util.NbBundle.getMessage(
                AddressSearchControl.class,
                "AddressSearchControl._cmdOk.toolTipText"));                                         // NOI18N
        _cmdOk.setBorder(null);
        _cmdOk.setBorderPainted(false);
        _cmdOk.setFocusable(false);
        _cmdOk.setMaximumSize(new java.awt.Dimension(22, 22));
        _cmdOk.setMinimumSize(new java.awt.Dimension(22, 22));
        _cmdOk.setPreferredSize(new java.awt.Dimension(22, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 0);
        add(_cmdOk, gridBagConstraints);
        _cmdOk.setText(null);

        _chkVisualize.setToolTipText(org.openide.util.NbBundle.getMessage(
                AddressSearchControl.class,
                "AddressSearchControl._chkVisualize.toolTipText"));                                  // NOI18N
        _chkVisualize.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/16/markPointDisabled.png"))); // NOI18N
        _chkVisualize.setSelectedIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/belis/resource/icon/16/markPoint.png")));         // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 3);
        add(_chkVisualize, gridBagConstraints);
        _chkVisualize.setSelected(false);

        _cboNr.setEditable(true);
        _cboNr.setEnabled(false);
        _cboNr.setMaximumSize(new java.awt.Dimension(50, 19));
        _cboNr.setMinimumSize(new java.awt.Dimension(50, 19));
        _cboNr.setPreferredSize(new java.awt.Dimension(50, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 0);
        add(_cboNr, gridBagConstraints);

        _cboStreets.setEnabled(false);
        _cboStreets.setMaximumSize(new java.awt.Dimension(32767, 19));
        _cboStreets.setMinimumSize(new java.awt.Dimension(41, 19));
        _cboStreets.setPreferredSize(new java.awt.Dimension(41, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 0);
        add(_cboStreets, gridBagConstraints);
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void formActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_formActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_formActionPerformed
}
