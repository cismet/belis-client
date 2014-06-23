/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/

import Sirius.navigator.connection.*;
import Sirius.navigator.connection.proxy.ConnectionProxy;

import Sirius.server.middleware.types.MetaObject;
import Sirius.server.middleware.types.MetaObjectNode;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;

import java.io.File;

import java.util.Collection;

import de.cismet.belis.arbeitsprotokollwizard.ArbeitsprotokollDialog;
import de.cismet.belis.arbeitsprotokollwizard.LeuchteLeuchtmittelwechselElekpruefungWizard;

import de.cismet.belis.broker.CidsBroker;

import de.cismet.belis2.server.search.ArbeitsauftragSearchStatement;

import de.cismet.cids.custom.beans.belis2.ArbeitsauftragCustomBean;

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
public class DeserializeTester {

    //~ Static fields/initializers ---------------------------------------------

    private static final transient org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(BrokerTester.class);

    public static final String CONNECTION_CLASS = "Sirius.navigator.connection.RESTfulConnection";
    public static final String CONNECTION_PROXY_CLASS =
        "Sirius.navigator.connection.proxy.DefaultConnectionProxyHandler";

    public static final String CALLSERVER_URL = "http://localhost:9917/callserver/binary";
    public static final String CALLSERVER_DOMAIN = CidsBroker.BELIS_DOMAIN;
    public static final String CALLSERVER_USER = "*";
    public static final String CALLSERVER_PASSWORD = "*";
    public static final String CALLSERVER_GROUP = "Bearbeiter";

    //~ Constructors -----------------------------------------------------------

    // private final Set<Key> allFlurstueckKeys = new HashSet<Key>();

    /**
     * Creates a new BrokerTester object.
     */
    public DeserializeTester() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  args  DOCUMENT ME!
     */
    public static void main(final String[] args) {
        try {
            Log4JQuickConfig.configure4LumbermillOnLocalhost();
            LOG.setLevel(Level.WARN);
            CidsBroker.getInstance().setProxy(DeserializeTester.initProxy());
            SessionManager.init(CidsBroker.getInstance().getProxy());

            final DeserializeTester test = new DeserializeTester();
            test.doTest();
            System.exit(0);
        } catch (Exception ex) {
            LOG.fatal(ex, ex);
            System.exit(1);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    private void doTest() throws Exception {
        final ArbeitsauftragSearchStatement arbeitsauftragSearchStatement = new ArbeitsauftragSearchStatement();
        arbeitsauftragSearchStatement.setAuftragsNummer("%5015");
        LOG.fatal("execute search");
        final Collection col = CidsBroker.getInstance().executeServerSearch(arbeitsauftragSearchStatement);
        final Object object = col.iterator().next();
        final MetaObjectNode mon = (MetaObjectNode)object;
        final int classid = mon.getClassId();
        final int objectid = mon.getObjectId();
        LOG.fatal("construct metaobject");
        final MetaObject mo = CidsBroker.getInstance().getMetaObject(classid, objectid, "BELIS2");
        LOG.fatal("construct bean");
        final ArbeitsauftragCustomBean aa = (ArbeitsauftragCustomBean)mo.getBean();
        LOG.fatal("protokollaktion");
        final LeuchteLeuchtmittelwechselElekpruefungWizard wizard = new LeuchteLeuchtmittelwechselElekpruefungWizard();
        final ArbeitsprotokollDialog dialog = new ArbeitsprotokollDialog(wizard, null, true);
        wizard.setProtokolle(aa.getAr_protokolle());
        StaticSwingTools.showDialog(dialog);
        LOG.fatal("save debugstring to to \"/home/jruiz/test.html\"");
        FileUtils.writeStringToFile(new File("/home/jruiz/test.html"), mo.getDebugString());
        LOG.fatal("start to persist");
        aa.persist();
        LOG.fatal("persisted");
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
}
