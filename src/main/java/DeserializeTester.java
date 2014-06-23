/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/

import Sirius.navigator.connection.*;
import Sirius.navigator.connection.proxy.ConnectionProxy;
import Sirius.navigator.exception.ConnectionException;
import Sirius.navigator.search.dynamic.SearchDialog;
import Sirius.navigator.types.treenode.RootTreeNode;
import Sirius.navigator.ui.DescriptionPane;
import Sirius.navigator.ui.DescriptionPaneFS;
import Sirius.navigator.ui.LayoutedContainer;
import Sirius.navigator.ui.MutableMenuBar;
import Sirius.navigator.ui.MutablePopupMenu;
import Sirius.navigator.ui.MutableToolBar;
import Sirius.navigator.ui.attributes.AttributeViewer;
import Sirius.navigator.ui.attributes.editor.AttributeEditor;
import Sirius.navigator.ui.tree.SearchResultsTree;

import Sirius.server.middleware.types.Node;

import org.jdesktop.swingx.auth.LoginService;

import java.awt.Component;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import de.cismet.belis.broker.CidsBroker;

import de.cismet.belis.client.BelisClient;

import de.cismet.belis.gui.widget.ExtendedNavigatorAttributeEditorGui;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.tools.gui.DefaultPopupMenuListener;
import de.cismet.tools.gui.StaticSwingTools;
import de.cismet.tools.gui.log4jquickconfig.Log4JQuickConfig;

/*
 * Copyright (C) 2012 cismet GmbH
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class BrokerTester {

    //~ Static fields/initializers ---------------------------------------------

    private static final transient org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(BrokerTester.class);

    public static final String CONNECTION_CLASS = "Sirius.navigator.connection.RESTfulConnection";
    public static final String CONNECTION_PROXY_CLASS =
        "Sirius.navigator.connection.proxy.DefaultConnectionProxyHandler";

    public static final String CALLSERVER_URL = "http://localhost:9917/callserver/binary";
//    public static final String CALLSERVER_URL = "https://geoportal.wuppertal.de:8084/callserver/binary";
    public static final String CALLSERVER_DOMAIN = CidsBroker.BELIS_DOMAIN;
    public static final String CALLSERVER_USER = "";
//    public static final String CALLSERVER_PASSWORD = "";
    public static final String CALLSERVER_PASSWORD = "";
    public static final String CALLSERVER_GROUP = "Bearbeiter";

    //~ Constructors -----------------------------------------------------------

// private Map<Integer, String> ejbFlurstueckStrings = new HashMap<Integer, String>();
// private Map<Integer, String> mosFlurstueckStrings = new HashMap<Integer, String>();

    // private final Set<Key> allFlurstueckKeys = new HashSet<Key>();

    /**
     * Creates a new BrokerTester object.
     */
    public BrokerTester() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    private static void initComponentRegistry() throws Exception {
        final SearchResultsTree searchResultsTree = new SearchResultsTree();
        final MutableToolBar toolBar = new MutableToolBar();
        final MutableMenuBar menuBar = new MutableMenuBar();
        final LayoutedContainer container = new LayoutedContainer(toolBar, menuBar, true);
        final AttributeViewer attributeViewer = new AttributeViewer();
        final AttributeEditor attributeEditor = new ExtendedNavigatorAttributeEditorGui();
        final SearchDialog searchDialog = null;

        final DescriptionPane descriptionPane = new DescriptionPaneFS();
        final MutablePopupMenu popupMenu = new MutablePopupMenu();

        final Collection<Component> toRemoveComponents = new ArrayList<Component>();
        for (final Component component : popupMenu.getComponents()) {
            if ((component instanceof JSeparator)
                        || ((component instanceof JMenuItem)
                            && (((JMenuItem)component).getActionCommand() != null)
                            && (((JMenuItem)component).getActionCommand().equals("cmdSearch")
                                || ((JMenuItem)component).getActionCommand().equals("treecommand")))) {
                toRemoveComponents.add(component);
            }
        }
        for (final Component toRemoveComponent : toRemoveComponents) {
            popupMenu.remove(toRemoveComponent);
        }

        final DefaultPopupMenuListener cataloguePopupMenuListener = new DefaultPopupMenuListener(popupMenu);
        final Node[] roots = SessionManager.getProxy().getRoots("BELIS2");
        while (true) {
            final RootTreeNode rootTreeNode = new RootTreeNode(roots);
            LOG.fatal("test: " + rootTreeNode.getChildCount() + " / " + roots.length, new Exception());
            Thread.sleep(1000);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   frame  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    private static void runTest(final JFrame frame) throws Exception {
        try {
//            final JXLoginPane login = new JXLoginPane(new WundaAuthentification(), null, null);
//            final JXLoginPane.JXLoginDialog d = new JXLoginPane.JXLoginDialog((JFrame)null, login);
//            StaticSwingTools.showDialog(d);

            SessionManager.init(initProxy());
            ClassCacheMultiple.setInstance(CALLSERVER_DOMAIN);

            initComponentRegistry();

            final TreeNodesDialog dialog = new TreeNodesDialog(frame, true);
            new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (final InterruptedException ex) {
                            LOG.fatal("error while sleeping", ex);
                        }
                        dialog.dispose();
                    }
                }).start();
            StaticSwingTools.showDialog(dialog);

            SessionManager.destroy();
            Thread.sleep(500);
        } catch (final ConnectionException ex) {
            LOG.fatal("getRoots()", ex);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  args  DOCUMENT ME!
     */
    public static void main(final String[] args) {
        Log4JQuickConfig.configure4LumbermillOnLocalhost();

        final JFrame frame = new JFrame();
        frame.setVisible(true);
        new Thread(
            new Runnable() {

                @Override
                public void run() {
                    while (true) {
                        try {
                            runTest(frame);
                        } catch (Exception ex) {
                            LOG.fatal("error while test", ex);
                            System.exit(0);
                        }
                    }
                }
            }).start();
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

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public static class WundaAuthentification extends LoginService {

        //~ Instance fields ----------------------------------------------------

        private final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(
                BelisClient.WundaAuthentification.class);

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new WundaAuthentification object.
         */
        public WundaAuthentification() {
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public boolean authenticate(final String name, final char[] password, final String server) throws Exception {
            try {
                final String user = CALLSERVER_USER;
                final String group = CALLSERVER_GROUP;

                final Connection connection = ConnectionFactory.getFactory()
                            .createConnection(CONNECTION_CLASS, CALLSERVER_URL);
                final ConnectionSession session;
                final ConnectionProxy proxy;
                final ConnectionInfo connectionInfo = new ConnectionInfo();
                connectionInfo.setCallserverURL(CALLSERVER_URL);
                connectionInfo.setPassword(CALLSERVER_PASSWORD);
                connectionInfo.setUserDomain(CALLSERVER_DOMAIN);
                connectionInfo.setUsergroup(group);
                connectionInfo.setUsergroupDomain(CALLSERVER_DOMAIN);
                connectionInfo.setUsername(user);

                session = ConnectionFactory.getFactory().createSession(connection, connectionInfo, true);
                proxy = ConnectionFactory.getFactory().createProxy(CONNECTION_PROXY_CLASS, session);
                SessionManager.init(proxy);
                ClassCacheMultiple.setInstance(CALLSERVER_DOMAIN);

                CidsBroker.getInstance().setProxy(proxy);
                return true;
            } catch (Throwable t) {
                log.error("Fehler beim Anmelden", t);
                return false;
            }
        }
    }
}
