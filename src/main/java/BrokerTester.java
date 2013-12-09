/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/

import Sirius.navigator.connection.*;
import Sirius.navigator.connection.proxy.ConnectionProxy;

import java.sql.Date;

import java.util.Collection;

import de.cismet.belis.broker.CidsBroker;

import de.cismet.belis.commons.constants.ArbeitsauftragPropertyConstants;
import de.cismet.belis.commons.constants.ArbeitsprotokollPropertyConstants;
import de.cismet.belis.commons.constants.VeranlassungPropertyConstants;

import de.cismet.cids.custom.beans.belis.ArbeitsauftragCustomBean;
import de.cismet.cids.custom.beans.belis.ArbeitsprotokollCustomBean;
import de.cismet.cids.custom.beans.belis.ArbeitsprotokollstatusCustomBean;
import de.cismet.cids.custom.beans.belis.InfobausteinCustomBean;
import de.cismet.cids.custom.beans.belis.VeranlassungCustomBean;
import de.cismet.cids.custom.beans.belis.VeranlassungsartCustomBean;

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

    public static final String CONNECTION_CLASS = "Sirius.navigator.connection.RMIConnection";
    public static final String CONNECTION_PROXY_CLASS =
        "Sirius.navigator.connection.proxy.DefaultConnectionProxyHandler";

    public static final String CALLSERVER_URL = "rmi://localhost/callServer";
    public static final String CALLSERVER_DOMAIN = CidsBroker.BELIS_DOMAIN;
    public static final String CALLSERVER_USER = "admin";
    public static final String CALLSERVER_PASSWORD = "krissenich";
    public static final String CALLSERVER_GROUP = "Administratoren";

    //~ Constructors -----------------------------------------------------------

// private Map<Integer, String> ejbFlurstueckStrings = new HashMap<Integer, String>();
// private Map<Integer, String> mosFlurstueckStrings = new HashMap<Integer, String>();

    // private final Set<Key> allFlurstueckKeys = new HashSet<Key>();

    /**
     * Creates a new BrokerTester object.
     */
    public BrokerTester() {
        final ConnectionProxy proxy = initProxy();
        SessionManager.init(proxy);

        runTest();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     */
    private void runTest() {
        final CidsBroker broker = CidsBroker.getInstance();

        final VeranlassungCustomBean veranlassungBean = VeranlassungCustomBean.createNew();
        final VeranlassungsartCustomBean veranlassungsartBean = VeranlassungsartCustomBean.createNew();
        final ArbeitsauftragCustomBean arbeitsauftragBean = ArbeitsauftragCustomBean.createNew();
        final ArbeitsprotokollCustomBean arbeitsprotokollBean = ArbeitsprotokollCustomBean.createNew();
        final ArbeitsprotokollstatusCustomBean arbeitsprotokollstatusBean = ArbeitsprotokollstatusCustomBean
                    .createNew();
        final InfobausteinCustomBean infobausteinBean = InfobausteinCustomBean.createNew();

        try {
            veranlassungBean.setProperty(VeranlassungPropertyConstants.PROP__FK_ART, veranlassungsartBean);
            veranlassungBean.setProperty(VeranlassungPropertyConstants.PROP__FK_AUFTRAG, arbeitsauftragBean);
            veranlassungBean.setProperty(VeranlassungPropertyConstants.PROP__DATUM, new Date(0));
            ((Collection)veranlassungBean.getProperty(VeranlassungPropertyConstants.PROP__AR_INFOBAUSTEINE)).add(
                infobausteinBean);

            arbeitsprotokollBean.setProperty(
                ArbeitsprotokollPropertyConstants.PROP__FK_STATUS,
                arbeitsprotokollstatusBean);

            ((Collection)arbeitsauftragBean.getProperty(ArbeitsauftragPropertyConstants.PROP__N_PROTOKOLLE)).add(
                arbeitsprotokollBean);
        } catch (Exception ex) {
            LOG.fatal("error setting property", ex);
        }

        LOG.fatal(veranlassungBean.getMOString());
    }

    /**
     * DOCUMENT ME!
     *
     * @param  args  DOCUMENT ME!
     */
    public static void main(final String[] args) {
        try {
            Log4JQuickConfig.configure4LumbermillOnLocalhost();

            final BrokerTester tester = new BrokerTester();
        } catch (Exception ex) {
            LOG.fatal(ex, ex);
        } finally {
            System.exit(0);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private ConnectionProxy initProxy() {
        try {
            final Connection connection = ConnectionFactory.getFactory()
                        .createConnection(CONNECTION_CLASS, CALLSERVER_URL);

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
        } catch (Exception ex) {
            LOG.fatal(ex, ex);
            return null;
        }
    }
}
