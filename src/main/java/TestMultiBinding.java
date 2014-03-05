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

import org.jdesktop.swingx.JXLoginPane;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.auth.LoginService;

import java.awt.Frame;

import java.util.Collection;

import javax.swing.JDialog;
import javax.swing.SwingWorker;

import de.cismet.belis.broker.BelisBroker;
import de.cismet.belis.broker.CidsBroker;

import de.cismet.belis.gui.widget.DetailWidget;

import de.cismet.belis2.server.search.ArbeitsauftragSearchStatement;

import de.cismet.cids.custom.beans.belis2.ArbeitsauftragCustomBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

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
public class TestMultiBinding {

    //~ Static fields/initializers ---------------------------------------------

    private static final transient org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(TestReport.class);

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new TestReport object.
     */
    public TestMultiBinding() {
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
     * @param  args  DOCUMENT ME!
     */
    public static void main(final String[] args) {
        Log4JQuickConfig.configure4LumbermillOnLocalhost();

//        final String callServerURL = args[0];
//        final String domain = args[1];
//        final String idsString = args[2];

        final String callServerURL = "rmi://localhost/callServer";
        final String domain = "BELIS2";
        final String idString = "00006024";

        final TestMultiBinding test = new TestMultiBinding();
        test.doLogin(callServerURL, domain);
        test.doMultiBinding(idString);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  id  DOCUMENT ME!
     */
    public void doMultiBinding(final String id) {
        CidsBroker.getInstance().setProxy(SessionManager.getProxy());

        final DetailWidget detail = new DetailWidget();
        detail.setEnabled(true);
        BelisBroker.getInstance().setDetailWidget(detail);
        final JDialog dialog;
        dialog = new JDialog();
        dialog.setContentPane(detail.getArbeitsauftragPanel());
        StaticSwingTools.showDialog(dialog);

        new SwingWorker<ArbeitsauftragCustomBean, Void>() {

                @Override
                protected ArbeitsauftragCustomBean doInBackground() throws Exception {
                    final ArbeitsauftragSearchStatement searchStatement = new ArbeitsauftragSearchStatement();
                    searchStatement.setAuftragsNummer(id);
                    try {
                        final Collection<MetaObjectNode> mons = SessionManager.getProxy()
                                    .customServerSearch(SessionManager.getSession().getUser(), searchStatement);
                        for (final MetaObjectNode mon : mons) {
                            final ArbeitsauftragCustomBean bean = (ArbeitsauftragCustomBean)CidsBroker
                                        .getInstance().getMetaObject(
                                        mon.getClassId(),
                                        mon.getObjectId(),
                                        "BELIS2").getBean();
                            return bean;
                        }
                    } catch (final Exception ex) {
                        LOG.error(ex, ex);
                    }
                    return null;
                }

                @Override
                protected void done() {
                    try {
                        final ArbeitsauftragCustomBean arbeitsauftragBean = get();

                        detail.getArbeitsauftragPanel().setCurrentEntity(arbeitsauftragBean);
                        detail.getArbeitsauftragPanel().setPanelEditable(true);
                        dialog.pack();
                    } catch (final Exception ex) {
                        LOG.error(ex, ex);
                    }
                }
            }.execute();
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
