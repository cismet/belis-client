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
import Sirius.navigator.exception.ConnectionException;

import Sirius.server.ServerExitError;
import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;
import Sirius.server.newuser.UserException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;

import de.cismet.belis.broker.CidsBroker;

import de.cismet.belis.commons.constants.ArbeitsauftragPropertyConstants;
import de.cismet.belis.commons.constants.BelisMetaClassConstants;

import de.cismet.belis.gui.reports.BelisReporter;
import de.cismet.belis.gui.reports.ReportingArbeitsauftrag;

import de.cismet.cids.custom.beans.belis2.ArbeitsauftragCustomBean;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.gui.layerwidget.ActiveLayerModel;
import de.cismet.cismap.commons.interaction.CismapBroker;

import de.cismet.tools.configuration.ConfigurationManager;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class ReportGenerator {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ReportGenerator.class);
    public static final String CONNECTION_CLASS = "Sirius.navigator.connection.RESTfulConnection";
    public static final String CONNECTION_PROXY_CLASS =
        "Sirius.navigator.connection.proxy.DefaultConnectionProxyHandler";

    //~ Instance fields --------------------------------------------------------

    private final Properties properties;
    private final ConnectionProxy proxy;
    private final MetaClass mcArbeitsauftrag;
    private final MappingComponent mappingComponent;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ReportGenerator object.
     *
     * @throws  ConnectionException  DOCUMENT ME!
     * @throws  UserException        DOCUMENT ME!
     * @throws  Exception            DOCUMENT ME!
     */
    public ReportGenerator() throws ConnectionException, UserException, Exception {
        properties = createProperties("reportgenerator.properties");
        proxy = createProxy(properties);
        SessionManager.init(proxy);
        CidsBroker.getInstance().setProxy(proxy);
        mcArbeitsauftrag = CidsBean.getMetaClassFromTableName(
                BelisMetaClassConstants.DOMAIN,
                BelisMetaClassConstants.MC_ARBEITSAUFTRAG);
        ClassCacheMultiple.setInstance("BELIS2");

        mappingComponent = createMap();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  args  DOCUMENT ME!
     */
    public static void main(final String[] args) {
        try {
            if (args == null) {
                throw new RuntimeException("args is null");
            } else if (args.length <= 0) {
                throw new RuntimeException("args <= 0");
            }
            final String auftragsnummer = args[0];
            final String filepath = args[1];
            final ReportGenerator rg = new ReportGenerator();
            rg.doGenerate(auftragsnummer, filepath);
            System.exit(0);
        } catch (final Exception ex) {
            System.out.println("ERROR: " + ex);
            LOG.error(ex, ex);
            System.exit(1);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private MappingComponent createMap() {
        final ConfigurationManager configManager = new ConfigurationManager();
        configManager.setDefaultFileName("defaultBelisConfiguration.xml");
        configManager.setClassPathFolder("/de/cismet/commons/architecture/configuration/");

        final MappingComponent mapC = new MappingComponent(true);
        mapC.setMappingModel(new ActiveLayerModel());
        CismapBroker.getInstance().setMappingComponent(mapC);
        configManager.configure(mapC);
        return mapC;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   auftragsnummer  DOCUMENT ME!
     * @param   filePath        DOCUMENT ME!
     *
     * @throws  ConnectionException  DOCUMENT ME!
     * @throws  JRException          DOCUMENT ME!
     */
    private void doGenerate(final String auftragsnummer, final String filePath) throws ConnectionException,
        JRException {
        final String query = ""
                    + "SELECT " + mcArbeitsauftrag.getID() + ", " + mcArbeitsauftrag.getTableName() + "."
                    + mcArbeitsauftrag.getPrimaryKey() + " "
                    + " FROM " + mcArbeitsauftrag.getTableName()
                    + " WHERE " + ArbeitsauftragPropertyConstants.PROP__NUMMER + " like '" + auftragsnummer + "'";
        final MetaObject[] mos = proxy.getMetaObjectByQuery(SessionManager.getSession().getUser(), query, "BELIS2");
        final ArbeitsauftragCustomBean arbeitsauftrag = (ArbeitsauftragCustomBean)mos[0].getBean();

        final File fileToSaveTo = new File(filePath);
        final JasperPrint jasperPrint;

        final ArrayList<ReportingArbeitsauftrag> repAuftraege = new ArrayList<ReportingArbeitsauftrag>(1);
        final ReportingArbeitsauftrag ra = new ReportingArbeitsauftrag();
        ra.init(arbeitsauftrag);
        repAuftraege.add(ra);
        final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(repAuftraege);
        final HashMap parameters = new HashMap();
        final JasperReport jasperReport;
        jasperReport = (JasperReport)JRLoader.loadObject(BelisReporter.class.getResourceAsStream(
                    "/de/cismet/belis/reports/arbeitsauftraege.jasper"));
        jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        JasperExportManager.exportReportToPdfFile(jasperPrint, fileToSaveTo.getPath());
    }

    /**
     * DOCUMENT ME!
     *
     * @param   configFile  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ServerExitError   DOCUMENT ME!
     * @throws  RuntimeException  DOCUMENT ME!
     */
    private Properties createProperties(final String configFile) throws ServerExitError {
        try {
            return new Properties(configFile);
        } catch (final FileNotFoundException ex) {
            final String message = "given configFile does not exist: " + configFile; // NOI18N
            LOG.fatal(message, ex);
            throw new RuntimeException(message, ex);
        } catch (final IOException ex) {
            final String message = "error while reading config: " + configFile;      // NOI18N
            LOG.fatal(message, ex);
            throw new RuntimeException(message, ex);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   properties  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ConnectionException  DOCUMENT ME!
     * @throws  UserException        DOCUMENT ME!
     */
    private static ConnectionProxy createProxy(final Properties properties) throws ConnectionException, UserException {
        final Connection connection = ConnectionFactory.getFactory()
                    .createConnection(CONNECTION_CLASS, properties.getCallserverUrl());

        final ConnectionInfo connectionInfo = new ConnectionInfo();
        connectionInfo.setCallserverURL(properties.getCallserverUrl());
        connectionInfo.setPassword(properties.getPassword());
        connectionInfo.setUserDomain("BELIS2");
        connectionInfo.setUsergroup(properties.getGroupName());
        connectionInfo.setUsergroupDomain("BELIS2");
        connectionInfo.setUsername(properties.getUserName());

        final ConnectionSession session = ConnectionFactory.getFactory()
                    .createSession(connection, connectionInfo, true);
        return ConnectionFactory.getFactory().createProxy(CONNECTION_PROXY_CLASS, session);
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    class Properties extends java.util.PropertyResourceBundle {

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new Properties object.
         *
         * @param   configFile  DOCUMENT ME!
         *
         * @throws  FileNotFoundException  DOCUMENT ME!
         * @throws  IOException            DOCUMENT ME!
         */
        public Properties(final String configFile) throws FileNotFoundException, IOException {
            super(new FileInputStream(configFile));
        }

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public final String getCallserverUrl() {
            return this.getString("callserverUrl"); // NOI18N
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public final String getUserName() {
            return this.getString("userName"); // NOI18N
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public final String getPassword() {
            return this.getString("password"); // NOI18N
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public final String getGroupName() {
            return this.getString("groupName"); // NOI18N
        }
    }
}
