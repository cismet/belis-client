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

import Sirius.server.middleware.types.MetaObjectNode;

import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JRViewer;

import org.jdesktop.swingx.JXLoginPane;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.auth.LoginService;

import java.awt.Frame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UnsupportedLookAndFeelException;

import de.cismet.belis.broker.BelisBroker;
import de.cismet.belis.broker.CidsBroker;

import de.cismet.belis.gui.reports.BelisReporter;
import de.cismet.belis.gui.reports.ReportingArbeitsauftrag;

import de.cismet.belis2.server.search.ArbeitsauftragSearchStatement;

import de.cismet.cids.custom.beans.belis2.ArbeitsauftragCustomBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.utils.jasperreports.ReportSwingWorkerDialog;

import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.gui.layerwidget.ActiveLayerModel;
import de.cismet.cismap.commons.interaction.CismapBroker;

import de.cismet.tools.configuration.ConfigurationManager;

import de.cismet.tools.gui.StaticSwingTools;
import de.cismet.tools.gui.log4jquickconfig.Log4JQuickConfig;

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
public class TestReport {

    //~ Static fields/initializers ---------------------------------------------

    private static final transient org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(TestReport.class);

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new TestReport object.
     */
    public TestReport() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  callServerURL  DOCUMENT ME!
     * @param  domain         DOCUMENT ME!
     */
    public void doLogin(final String callServerURL, final String domain) {
        final CidsAuthentification cidsAuth = new CidsAuthentification(callServerURL, domain);
        final JXLoginPane login = new JXLoginPane(cidsAuth);

        final JXLoginPane.JXLoginDialog loginDialog = new JXLoginPane.JXLoginDialog((Frame)null, login);

        login.setPassword("".toCharArray());

        try {
            ((JXPanel)((JXPanel)login.getComponent(1)).getComponent(1)).getComponent(3).requestFocus();
        } catch (final Exception ex) {
            LOG.info("could nor request focus", ex);
        }
        StaticSwingTools.showDialog(loginDialog);

        if (loginDialog.getStatus() != JXLoginPane.Status.SUCCEEDED) {
            System.exit(0);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  nummern  ids DOCUMENT ME!
     */
    public void generateReport(final Collection<String> nummern) {
        try {
            final ReportSwingWorkerDialog dialog = new ReportSwingWorkerDialog(StaticSwingTools.getParentFrame(null),
                    true);

            javax.swing.UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
            final ConfigurationManager configManager = new ConfigurationManager();

            configManager.setFolder(".belis2");
            configManager.setFileName("configurationPlugin.xml");
            configManager.setDefaultFileName("defaultCismapProperties.xml");

            final MappingComponent mapC = new MappingComponent(true);
            mapC.setMappingModel(new ActiveLayerModel());
            CismapBroker.getInstance().setMappingComponent(mapC);
            configManager.configure(mapC);

            CidsBroker.getInstance().setProxy(SessionManager.getProxy());

            new SwingWorker<JasperPrint, Void>() {

                    @Override
                    protected JasperPrint doInBackground() throws Exception {
                        SwingUtilities.invokeLater(new Runnable() {

                                @Override
                                public void run() {
                                    StaticSwingTools.showDialog(dialog);
                                }
                            });

                        final ArrayList a = new ArrayList<Object>();

                        for (final String nummer : nummern) {
                            final ArbeitsauftragSearchStatement searchStatement = new ArbeitsauftragSearchStatement();
                            searchStatement.setAuftragsNummer(nummer);
                            searchStatement.setActiveObjectsOnly(false);
                            searchStatement.setWorkedoffObjectsOnly(false);
                            try {
                                final Collection<MetaObjectNode> mons = SessionManager.getProxy()
                                            .customServerSearch(SessionManager.getSession().getUser(), searchStatement);
                                for (final MetaObjectNode mon : mons) {
                                    final ArbeitsauftragCustomBean bean = (ArbeitsauftragCustomBean)CidsBroker
                                                .getInstance()
                                                .getMetaObject(
                                                        mon.getClassId(),
                                                        mon.getObjectId(),
                                                        "BELIS2")
                                                .getBean();
                                    final ReportingArbeitsauftrag ra = new ReportingArbeitsauftrag();
                                    ra.init(bean);
                                    a.add(ra);
                                }
                            } catch (final Exception ex) {
                                LOG.error(ex, ex);
                            }
                        }

                        BelisBroker.getInstance().lookupProtokollWizards();
                        final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(a);

                        final HashMap parameters = new HashMap();

                        final JasperReport jasperReport;
                        try {
                            jasperReport = (JasperReport)JRLoader.loadObject(BelisReporter.class.getResourceAsStream(
                                        "/de/cismet/belis/reports/arbeitsauftraege.jasper"));

                            return JasperFillManager.fillReport(jasperReport, parameters, dataSource);
                        } catch (Exception e) {
                            LOG.error(e, e);
                            return null;
                        }
                    }

                    @Override
                    protected void done() {
                        try {
                            dialog.setVisible(false);
                            final JasperPrint jasperPrint = get();
                            final JRViewer aViewer = new JRViewer(jasperPrint);
                            final JFrame aFrame = new JFrame("Druckvorschau");
                            aFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            aFrame.getContentPane().add(aViewer);
                            final java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
                            aFrame.setSize((int)(screenSize.width * 0.8), (int)(screenSize.height * 0.9f));
                            final java.awt.Insets insets = aFrame.getInsets();
                            aFrame.setSize(
                                aFrame.getWidth()
                                        + insets.left
                                        + insets.right,
                                aFrame.getHeight()
                                        + insets.top
                                        + insets.bottom
                                        + 20);
                            aFrame.setLocation(
                                (screenSize.width - aFrame.getWidth())
                                        / 2,
                                (screenSize.height - aFrame.getHeight())
                                        / 2);
                            aViewer.setFitPageZoomRatio();
                            aFrame.setVisible(true);
                        } catch (final Exception ex) {
                            LOG.error(ex, ex);
                        } finally {
                            dialog.dispose();
                        }
                    }
                }.execute();
        } catch (UnsupportedLookAndFeelException ex) {
            LOG.error(ex, ex);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  args  DOCUMENT ME!
     */
    public static void main(final String[] args) {
        Log4JQuickConfig.configure4LumbermillOnLocalhost();

        final String callServerURL = args[0];
        final String domain = args[1];
        final String idsString = args[2];

//        final String callServerURL = "rmi://localhost/callServer";
//        final String domain = "BELIS2";
//        final String idsString = "00006024;00006025;00006029";

        final Collection<String> ids = new ArrayList<String>();
        for (final String idString : idsString.split(";")) {
            ids.add(idString);
        }

        final TestReport test = new TestReport();
        test.doLogin(callServerURL, domain);
        test.generateReport(ids);
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    class CidsAuthentification extends LoginService {

        //~ Static fields/initializers -----------------------------------------

        public static final String CONNECTION_PROXY_CLASS =
            "Sirius.navigator.connection.proxy.DefaultConnectionProxyHandler";
        public static final String CONNECTION_CLASS = "Sirius.navigator.connection.RMIConnection";

        //~ Instance fields ----------------------------------------------------

        private final String callServerURL;
        private final String domain;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new CidsAuthentification object.
         *
         * @param  callServerURL  DOCUMENT ME!
         * @param  domain         DOCUMENT ME!
         */
        public CidsAuthentification(final String callServerURL, final String domain) {
            this.callServerURL = callServerURL;
            this.domain = domain;
        }

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @param   name      DOCUMENT ME!
         * @param   password  DOCUMENT ME!
         * @param   server    DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         *
         * @throws  Exception  DOCUMENT ME!
         */
        @Override
        public boolean authenticate(final String name, final char[] password, final String server) throws Exception {
            System.setProperty("sun.rmi.transport.connectionTimeout", "15");
            final String[] split = name.split("@");
            final String user = (split.length > 1) ? split[0] : name;
            final String group = (split.length > 1) ? split[1] : null;

            final String connectionclass = "Sirius.navigator.connection.RMIConnection";

            try {
                final Connection connection = ConnectionFactory.getFactory()
                            .createConnection(connectionclass, callServerURL);
                final ConnectionInfo connectionInfo = new ConnectionInfo();
                connectionInfo.setCallserverURL(callServerURL);
                connectionInfo.setPassword(new String(password));
                connectionInfo.setUserDomain(domain);
                connectionInfo.setUsergroup(group);
                connectionInfo.setUsergroupDomain(domain);
                connectionInfo.setUsername(user);
                final ConnectionSession session = ConnectionFactory.getFactory()
                            .createSession(connection, connectionInfo, true);
                final ConnectionProxy proxy = ConnectionFactory.getFactory()
                            .createProxy(CONNECTION_PROXY_CLASS, session);
                SessionManager.init(proxy);

                ClassCacheMultiple.setInstance(domain);
                return true;
            } catch (Throwable t) {
                LOG.error("Fehler beim Anmelden", t);
                return false;
            }
        }
    }
}
