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

import Sirius.server.localserver.attribute.Attribute;
import Sirius.server.localserver.attribute.ClassAttribute;
import Sirius.server.localserver.attribute.MemberAttributeInfo;
import Sirius.server.middleware.types.MetaClass;

import com.google.common.base.CaseFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;

import de.cismet.belis.broker.BelisBroker;
import de.cismet.belis.broker.CidsBroker;
import de.cismet.belis.broker.CsvExportBackend;

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

import static de.cismet.belis.broker.CsvExportBackend.CSV_EXPORT_KEYVALUE_SEPARATOR;

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
                        final List<String> header = new ArrayList<String>(getAttributeNames().size());
                        final List<String> fields = new ArrayList<String>(getAttributeNames().size());
                        for (final String attrKey : getAttributeKeys()) {
                            final MemberAttributeInfo mai = (MemberAttributeInfo)getMetaClass()
                                        .getMemberAttributeInfos().get(attrKey);
                            header.add(getAttributeNames().get(attrKey));
                            fields.add(mai.getFieldName());
                        }
                        final CsvExportSearchStatement search = new CsvExportSearchStatement(
                                getMetaClass().getTableName(),
                                CidsBroker.BELIS_DOMAIN,
                                fields,
                                getWhereCause(),
                                "id");
                        search.setDateFormat("dd.MM.yy");
                        search.setBooleanFormat(new String[] { "nein", "ja" });
                        DownloadManager.instance()
                                .add(
                                    new CsvExportSearchDownload(
                                        search,
                                        title,
                                        DownloadManagerDialog.getInstance().getJobName(),
                                        title,
                                        header));
                        final DownloadManagerDialog downloadManagerDialog = DownloadManagerDialog.getInstance();
                        StaticSwingTools.showDialog(
                            StaticSwingTools.getParentFrame(QuerySearchResultsWindowSearch.this),
                            downloadManagerDialog,
                            true);
                    }
                }
            });
        initComponents();
        querySearchResultsActionPanel1.setDateFormat("dd.MM.yy");

        final HashMap<String, String> test = new HashMap<String, String>();

        final HashMap<MetaClass, MetaClass> mcs = new HashMap<MetaClass, MetaClass>();
        mcs.put(CidsBroker.getInstance().getBelisMetaClass("view_standorte"),
            CidsBroker.getInstance().getBelisMetaClass(TdtaStandortMastCustomBean.TABLE));
        mcs.put(CidsBroker.getInstance().getBelisMetaClass("view_leuchten"),
            CidsBroker.getInstance().getBelisMetaClass(TdtaLeuchtenCustomBean.TABLE));
        mcs.put(CidsBroker.getInstance().getBelisMetaClass("view_mauerlaschen"),
            CidsBroker.getInstance().getBelisMetaClass(MauerlascheCustomBean.TABLE));
        mcs.put(CidsBroker.getInstance().getBelisMetaClass("view_leitungen"),
            CidsBroker.getInstance().getBelisMetaClass(LeitungCustomBean.TABLE));
        mcs.put(CidsBroker.getInstance().getBelisMetaClass("view_schaltstellen"),
            CidsBroker.getInstance().getBelisMetaClass(SchaltstelleCustomBean.TABLE));
        mcs.put(CidsBroker.getInstance().getBelisMetaClass("view_abzweigdosen"),
            CidsBroker.getInstance().getBelisMetaClass(AbzweigdoseCustomBean.TABLE));

        for (final MetaClass mcView : mcs.keySet()) {
            final MetaClass mcEnt = mcs.get(mcView);
            final List<String> rawKeys = (List<String>)CsvExportBackend.getInstance().getMcPropkeyMap().get(mcEnt);
            final HashMap<String, String> fields = new HashMap<String, String>(rawKeys.size() * 2);
            final List<String> orderedAttributeNames = new ArrayList<String>();
            for (final String rawKey : rawKeys) {
                final String[] propkeyArr = rawKey.split(CSV_EXPORT_KEYVALUE_SEPARATOR);
                final String fieldKey = propkeyArr[1];
                final String fieldValue = propkeyArr[0];
                orderedAttributeNames.add(fieldKey);
                fields.put(fieldKey, fieldValue);
                orderedAttributeNames.add(fieldKey + ".id");
                fields.put(fieldKey + ".id", fieldValue);
            }

            final HashMap<String, String> attrNames = new HashMap<String, String>();
            final HashMap<String, String> attrKeyMap = new HashMap<String, String>();
            for (final Object o : mcView.getMemberAttributeInfos().entrySet()) {
                final MemberAttributeInfo mai = (MemberAttributeInfo)((java.util.Map.Entry)o).getValue();
                final String attrKey = mai.getKey().toString();
                final String attrName = mai.getName();
                final String fieldValue = fields.get(attrName);

                attrKeyMap.put(attrName, attrKey);

                if (fieldValue != null) {
                    attrNames.put(attrKey, fieldValue);
                }
            }
            orderedAttributeNames.retainAll(attrKeyMap.keySet());

            final List<String> orderedAttributeKeys = new ArrayList<String>();
            for (final String orderedAttributeName : orderedAttributeNames) {
                final String attrKey = attrKeyMap.get(orderedAttributeName);
                orderedAttributeKeys.add(attrKey);
            }

            querySearchResultsActionPanel1.setDefaultAttributeOrder(mcView, orderedAttributeKeys);
            querySearchResultsActionPanel1.addDefaultAttributeNames(attrNames);
        }
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
