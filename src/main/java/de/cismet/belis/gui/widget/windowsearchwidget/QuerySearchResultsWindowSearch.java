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
package de.cismet.belis.gui.widget.windowsearchwidget;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.downloadmanager.CsvExportSearchDownload;
import Sirius.navigator.search.CidsSearchExecutor;

import Sirius.server.localserver.attribute.MemberAttributeInfo;
import Sirius.server.middleware.types.MetaClass;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;

import de.cismet.belis.broker.BelisBroker;
import de.cismet.belis.broker.CidsBroker;

import de.cismet.cids.custom.beans.belis2.AbzweigdoseCustomBean;
import de.cismet.cids.custom.beans.belis2.ArbeitsauftragCustomBean;
import de.cismet.cids.custom.beans.belis2.LeitungCustomBean;
import de.cismet.cids.custom.beans.belis2.MauerlascheCustomBean;
import de.cismet.cids.custom.beans.belis2.SchaltstelleCustomBean;
import de.cismet.cids.custom.beans.belis2.TdtaLeuchtenCustomBean;
import de.cismet.cids.custom.beans.belis2.TdtaStandortMastCustomBean;
import de.cismet.cids.custom.beans.belis2.VeranlassungCustomBean;

import de.cismet.cids.search.QuerySearchResultsAction;
import de.cismet.cids.search.QuerySearchResultsActionPanel;

import de.cismet.cids.server.search.MetaObjectNodeServerSearch;
import de.cismet.cids.server.search.builtin.CsvExportSearchStatement;
import de.cismet.cids.server.search.builtin.QueryEditorSearch;

import de.cismet.tools.gui.StaticSwingTools;
import de.cismet.tools.gui.downloadmanager.DownloadManager;
import de.cismet.tools.gui.downloadmanager.DownloadManagerDialog;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
@org.openide.util.lookup.ServiceProvider(service = BelisWindowSearch.class)
public class QuerySearchResultsWindowSearch extends JPanel implements BelisWindowSearch {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            QuerySearchResultsWindowSearch.class);

    //~ Instance fields --------------------------------------------------------

    final List<QuerySearchResultsAction> actions = new ArrayList();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.cids.search.QuerySearchResultsActionPanel querySearchResultsActionPanel1;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form QuerySearchWindowSearch.
     */
    public QuerySearchResultsWindowSearch() {
        actions.add(new QuerySearchResultsAction() {

                @Override
                public String getName() {
                    return "als Suchergebniss laden";
                }

                @Override
                public void doAction() {
                    final MetaClass metaClassToSearchWith = getMetaClass();
                    final MetaClass metaClassToLoad;
                    final String tableName = metaClassToSearchWith.getTableName().toLowerCase();
                    if (tableName.equals("view_leuchten")) {
                        metaClassToLoad = CidsBroker.getInstance().getBelisMetaClass(TdtaLeuchtenCustomBean.TABLE);
                    } else if (tableName.equals("view_standorte")) {
                        metaClassToLoad = CidsBroker.getInstance().getBelisMetaClass(TdtaStandortMastCustomBean.TABLE);
                    } else if (tableName.equals("view_leitungen")) {
                        metaClassToLoad = CidsBroker.getInstance().getBelisMetaClass(LeitungCustomBean.TABLE);
                    } else if (tableName.equals("view_mauerlaschen")) {
                        metaClassToLoad = CidsBroker.getInstance().getBelisMetaClass(MauerlascheCustomBean.TABLE);
                    } else if (tableName.equals("view_schaltstellen")) {
                        metaClassToLoad = CidsBroker.getInstance().getBelisMetaClass(SchaltstelleCustomBean.TABLE);
                    } else if (tableName.equals("view_abzweigdosen")) {
                        metaClassToLoad = CidsBroker.getInstance().getBelisMetaClass(AbzweigdoseCustomBean.TABLE);
                    } else if (tableName.equals("view_veranlassungen")) {
                        metaClassToLoad = CidsBroker.getInstance().getBelisMetaClass(VeranlassungCustomBean.TABLE);
                    } else if (tableName.equals("view_arbeitsauftraege")) {
                        metaClassToLoad = CidsBroker.getInstance().getBelisMetaClass(ArbeitsauftragCustomBean.TABLE);
                    } else {
                        metaClassToLoad = metaClassToSearchWith;
                    }

                    final QueryEditorSearch search = new QueryEditorSearch(
                            SessionManager.getSession().getUser().getDomain(),
                            metaClassToSearchWith.getTableName(),
                            getWhereCause(),
                            metaClassToLoad.getId());

                    CidsSearchExecutor.searchAndDisplayResultsWithDialog(search);
                }
            });
        actions.add(new QuerySearchResultsAction() {

                @Override
                public String getName() {
                    return "nach CSV exportieren";
                }

                @Override
                public void doAction() {
                    final String title = getMetaClass().getName();

                    if (DownloadManagerDialog.showAskingForUserTitle(
                                    StaticSwingTools.getParentFrame(QuerySearchResultsWindowSearch.this))) {
                        final List<String> header = new ArrayList<String>(getMaiNames().size());
                        final List<String> fields = new ArrayList<String>(getMaiNames().size());
                        for (final MemberAttributeInfo mai : getMais()) {
                            header.add(getMaiNames().get(mai.getFieldName()));
                            fields.add(mai.getFieldName());
                        }
                        final CsvExportSearchStatement search = new CsvExportSearchStatement(
                                getMetaClass().getTableName(),
                                CidsBroker.BELIS_DOMAIN,
                                fields,
                                getWhereCause());
                        search.setDateFormat("dd.MM.yyyy");
                        search.setBooleanFormat(new String[] { "nein", "ja" });
                        DownloadManager.instance()
                                .add(
                                    new CsvExportSearchDownload(
                                        search,
                                        title,
                                        DownloadManagerDialog.getJobname(),
                                        title,
                                        header));
                        final DownloadManagerDialog downloadManagerDialog = DownloadManagerDialog.instance(
                                StaticSwingTools.getParentFrame(QuerySearchResultsWindowSearch.this));
                        downloadManagerDialog.pack();
                        StaticSwingTools.showDialog(downloadManagerDialog);
                    }
                }
            });
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
        final java.awt.GridBagConstraints gridBagConstraints;

        querySearchResultsActionPanel1 = new de.cismet.cids.search.QuerySearchResultsActionPanel(this.actions);

        setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(querySearchResultsActionPanel1, gridBagConstraints);
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public QuerySearchResultsActionPanel getQuerySearchResultsActionPanel() {
        return querySearchResultsActionPanel1;
    }

    @Override
    public JComponent getSearchWindowComponent() {
        BelisBroker.getInstance().setQuerySearchResultsWindowSearch(this);
        return this;
    }

    @Override
    public MetaObjectNodeServerSearch getServerSearch() {
        return null;
    }

    @Override
    public ImageIcon getIcon() {
        return null;
    }

    @Override
    public String getName() {
        return "Experten-Suche";
    }
}
