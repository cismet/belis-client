/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.belis.broker;

/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/

import Sirius.navigator.connection.Connection;
import Sirius.navigator.connection.ConnectionFactory;
import Sirius.navigator.connection.ConnectionInfo;
import Sirius.navigator.connection.ConnectionSession;
import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.connection.proxy.ConnectionProxy;

import Sirius.server.localserver.attribute.Attribute;
import Sirius.server.middleware.types.MetaClass;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.cismet.cids.custom.beans.belis2.MauerlascheCustomBean;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.tools.gui.log4jquickconfig.Log4JQuickConfig;

import static de.cismet.belis.broker.CidsBroker.BELIS_DOMAIN;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class CsvExportBackend {

    //~ Static fields/initializers ---------------------------------------------

    private static final transient Logger LOG = Logger.getLogger(CsvExportBackend.class);
    private static final String CSV_EXPORT = "CsvExport";
    private static final String CSV_EXPORT_KEYVALUE_SEPARATOR = ":";
    private static final String CSV_EXPORT_SEPARATOR = ",";
    private static final String CSV_SEPARATOR = ";";
    private static CsvExportBackend INSTANCE;

    public static final String CONNECTION_CLASS = "Sirius.navigator.connection.RESTfulConnection";
    public static final String CONNECTION_PROXY_CLASS =
        "Sirius.navigator.connection.proxy.DefaultConnectionProxyHandler";

    public static final String CALLSERVER_URL = "http://localhost:9917/callserver/binary";
    public static final String CALLSERVER_DOMAIN = CidsBroker.BELIS_DOMAIN;
    public static final String CALLSERVER_USER = "WendlingM";
    public static final String CALLSERVER_PASSWORD = "kif";
    public static final String CALLSERVER_GROUP = "Bearbeiter";

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    //~ Instance fields --------------------------------------------------------

    private final Multimap<MetaClass, String> mcPropkeyMap = ArrayListMultimap.create();

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new CsvExportBackend object.
     */
    private CsvExportBackend() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static CsvExportBackend getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CsvExportBackend();
        }
        return INSTANCE;
    }

    /**
     * DOCUMENT ME!
     */
    public void init() {
        try {
            final MetaClass[] metaClasses = CidsBroker.getInstance()
                        .getProxy()
                        .getClasses(SessionManager.getSession().getUser().getDomain());
            for (final MetaClass metaClass : metaClasses) {
                final Collection<Attribute> csvExport = metaClass.getAttributeByName(CSV_EXPORT);
                if (!csvExport.isEmpty()) {
                    for (final Attribute attr : csvExport) {
                        final String[] props = ((String)attr.getValue()).split(CSV_EXPORT_SEPARATOR);
                        for (final String prop : props) {
                            mcPropkeyMap.put(metaClass, prop.trim());
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("Error while retrieving classes", e);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   bean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Object[] getFields(final CidsBean bean) {
        final MetaClass metaClass = bean.getMetaObject().getMetaClass();
        final List<String> propkeys = (List<String>)mcPropkeyMap.get(metaClass);
        final Object[] fields = new Object[propkeys.size()];
        for (int i = 0; i < propkeys.size(); i++) {
            final String propkey = propkeys.get(i);
            final String[] propkeyArr = propkey.split(CSV_EXPORT_KEYVALUE_SEPARATOR);
            final Object o = bean.getProperty(propkeyArr[1]);
            fields[i] = o;
        }
        return fields;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   beans  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public List<Object[]> getFields(final Collection<CidsBean> beans) {
        final List<Object[]> ret = new ArrayList<Object[]>(beans.size());
        for (final CidsBean bean : beans) {
            ret.add(getFields(bean));
        }
        return ret;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   metaClass  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String[] getFieldNames(final MetaClass metaClass) {
        final List<String> propkeys = (List<String>)mcPropkeyMap.get(metaClass);
        final String[] fieldNames = new String[propkeys.size()];
        for (int i = 0; i < propkeys.size(); i++) {
            final String propkey = propkeys.get(i);
            final String[] propkeyArr = propkey.split(":");
            fieldNames[i] = propkeyArr[0];
        }
        return fieldNames;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   beans  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Multimap<MetaClass, CidsBean> getGroupedBeans(final Collection<CidsBean> beans) {
        final Multimap<MetaClass, CidsBean> mcBeansMap = HashMultimap.create();
        for (final CidsBean bean : beans) {
            final MetaClass mc = bean.getMetaObject().getMetaClass();
            mcBeansMap.put(mc, bean);
        }
        return mcBeansMap;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   beans  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Map<MetaClass, String> toCsvStrings(final Collection<CidsBean> beans) {
        final Multimap<MetaClass, CidsBean> groupedBeansMap = getGroupedBeans(beans);

        final Map<MetaClass, String> csvMap = new HashMap<MetaClass, String>();
        for (final MetaClass metaClass : groupedBeansMap.keys()) {
            final Collection<CidsBean> groupedBeans = groupedBeansMap.get(metaClass);
            final List<Object[]> fields = getFields(groupedBeans);

            final String[] fieldNames = getFieldNames(metaClass);
            if (fieldNames.length > 0) {
                final String header = CidsBroker.implode(fieldNames, CSV_SEPARATOR);
                final StringBuffer csv = new StringBuffer(header);
                for (final Object[] field : fields) {
                    final String[] stringField = new String[field.length];
                    for (int i = 0; i < field.length; i++) {
                        final Object prop = field[i];
                        final String string;
                        if (prop == null) {
                            string = null;
                        } else if (prop instanceof Boolean) {
                            string = ((Boolean)prop) ? "ja" : "nein";
                        } else if (prop instanceof Date) {
                            string = DATE_FORMAT.format((Date)prop);
                        } else {
                            string = prop.toString();
                        }
                        stringField[i] = (string != null) ? ("\"" + string + "\"") : null;
                    }
                    final String row = CidsBroker.implode(stringField, CSV_SEPARATOR);
                    csv.append("\n").append(row);
                }
                csvMap.put(metaClass, csv.toString());
            }
        }
        return csvMap;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    private static ConnectionProxy initProxy() throws Exception {
        final Connection connection = ConnectionFactory.getFactory().createConnection(CONNECTION_CLASS, CALLSERVER_URL);

        final ConnectionInfo connectionInfo = new ConnectionInfo();
        connectionInfo.setCallserverURL(CALLSERVER_URL);
        connectionInfo.setPassword(CALLSERVER_PASSWORD);
        connectionInfo.setUserDomain(CALLSERVER_DOMAIN);
        connectionInfo.setUsergroup(CALLSERVER_GROUP);
        connectionInfo.setUsergroupDomain(CALLSERVER_DOMAIN);
        connectionInfo.setUsername(CALLSERVER_USER);

        final ConnectionSession session = ConnectionFactory.getFactory()
                    .createSession(connection, connectionInfo, true);
        return ConnectionFactory.getFactory().createProxy(CONNECTION_PROXY_CLASS, session);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  args  DOCUMENT ME!
     */
    public static void main(final String[] args) {
        try {
            Log4JQuickConfig.configure4LumbermillOnLocalhost();
            CidsBroker.getInstance().setProxy(initProxy());
            SessionManager.init(CidsBroker.getInstance().getProxy());

            final MetaClass metaclass = CidsBroker.getInstance()
                        .getMetaClass(MauerlascheCustomBean.TABLE, BELIS_DOMAIN);

            final Collection<CidsBean> beans = CidsBroker.getInstance()
                        .getBeanCollectionForQuery(""
                            + "SELECT " + metaclass.getID() + ", " + metaclass.getPrimaryKey() + " FROM "
                            + metaclass.getTableName() + " LIMIT 10",
                            CidsBroker.BELIS_DOMAIN);

            final CsvExportBackend test = new CsvExportBackend();
            test.init();
            final Map<MetaClass, String> map = test.toCsvStrings(beans);
            for (final MetaClass mc : map.keySet()) {
                LOG.fatal("=========");
                LOG.fatal(mc.getTableName());
                LOG.fatal("---------");
                LOG.fatal(map.get(mc));
                LOG.fatal("=========");
            }
            System.exit(0);
        } catch (Exception ex) {
            LOG.fatal(ex, ex);
            System.exit(1);
        }
    }
}
