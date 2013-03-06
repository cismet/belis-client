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

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import org.openide.util.Exceptions;

import java.io.*;

import java.util.*;

import de.cismet.belis.broker.BelisBroker;
import de.cismet.belis.broker.CidsBroker;

import de.cismet.cids.client.tools.DevelopmentTools;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

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

    public static final String ORB_SERVER = "cubert";
    public static final String ORB_PORT = "3700";
    private static PrintStream printStream;

    //~ Instance fields --------------------------------------------------------

    private final ConnectionProxy proxy;

    //~ Constructors -----------------------------------------------------------

// private Map<Integer, String> ejbFlurstueckStrings = new HashMap<Integer, String>();
// private Map<Integer, String> mosFlurstueckStrings = new HashMap<Integer, String>();

    // private final Set<Key> allFlurstueckKeys = new HashSet<Key>();

    /**
     * Creates a new BrokerTester object.
     */
    public BrokerTester() {
        proxy = initProxy();
        SessionManager.init(proxy);
        // initEJBroker();
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

            final BrokerTester tester = new BrokerTester();
            final CidsBroker broker = CidsBroker.getInstance();

            tester.createCB("abzweigdose", 2);
            tester.createCB("bauart", 2);
            tester.createCB("dms_url", 2);
            tester.createCB("geom", 2);
            tester.createCB("geom_to_entity_index", 2);
            tester.createCB("jt_abzweigdose_dokument", 2);
            tester.createCB("jt_leitung_dokument", 2);
            tester.createCB("jt_leuchte_dokument", 2);
            tester.createCB("jt_mauerlasche_dokument", 2);
            tester.createCB("jt_schaltstelle_dokument", 2);
            tester.createCB("jt_standort_dokument", 2);
            tester.createCB("leitung", 2);
            tester.createCB("leitungstyp", 2);
            tester.createCB("material_leitung", 2);
            tester.createCB("material_mauerlasche", 2);
            tester.createCB("mauerlasche", 2);
            tester.createCB("querschnitt", 2);
            tester.createCB("schaltstelle", 2);
            tester.createCB("sperre", 2);
            tester.createCB("tdta_leuchten", 2);
            tester.createCB("tdta_standort_mast", 2);
            tester.createCB("tkey_bezirk", 2);
            tester.createCB("tkey_doppelkommando", 2);
            tester.createCB("tkey_energielieferant", 2);
            tester.createCB("tkey_kennziffer", 2);
            tester.createCB("tkey_klassifizierung", 2);
            tester.createCB("tkey_leuchtentyp", 2);
            tester.createCB("tkey_mastart", 2);
            tester.createCB("tkey_masttyp", 2);
            tester.createCB("tkey_strassenschluessel", 2);
//            tester.createCB("tkey_strschl", 2);
            tester.createCB("tkey_unterh_leuchte", 2);
            tester.createCB("tkey_unterh_mast", 2);
            tester.createCB("url", 2);
            tester.createCB("url_base", 2);

//            final FlurstueckCustomBean bean = (FlurstueckCustomBean)tester.createCB("flurstueck", 20562);
//            bean.getVertraegeQuerverweise();

//            tester.prepareAllFlurstueckKeys(broker);
//
//            final float execTimeEjb = tester.testPerfEjb(broker);
////            tester.print("ejb in " + execTimeEjb);
//            LOG.fatal("ejb in " + execTimeEjb);
//
//            final float execTimeMos = tester.testPerfMos();
////            tester.print("mos in " + execTimeMos);
//            LOG.fatal("mos in " + execTimeMos);
//
//            tester.compareStrings();
        } catch (Exception ex) {
            LOG.fatal(ex, ex);
        } finally {
            System.exit(0);
        }
    }

//    /**
//     * DOCUMENT ME!
//     */
//    private void compareStrings() {
//        final Set<Integer> set = ejbFlurstueckStrings.keySet();
//        final List<Integer> list = Arrays.asList((Integer[])set.toArray(new Integer[0]));
//        Collections.sort(list, new Comparator<Integer>() {
//
//                @Override
//                public int compare(final Integer o1, final Integer o2) {
//                    return o1 - o2;
//                }
//            });
//
//        int count = 0;
//        int notEquals = 0;
//        for (final Integer key : list) {
//            count++;
//            final String ejbString = ejbFlurstueckStrings.get(key);
//            final String mosString = mosFlurstueckStrings.get(key);
//            if (!ejbString.equals(mosString)) {
//                LOG.fatal(count + "/" + ejbFlurstueckStrings.size() + ": not equals (" + ++notEquals + ")");
//                setSoutToFile("/home/jruiz/LagisTest/" + key + "_EJB.txt");
//                print(ejbString);
//                setSoutToFile("/home/jruiz/LagisTest/" + key + "_MOS.txt");
//                print(mosString);
//            } else {
//                LOG.fatal(count + "/" + ejbFlurstueckStrings.size() + ": equals");
//            }
//        }
//    }

    /**
     * /** * DOCUMENT ME!
     *
     * @param   tablename  broker DOCUMENT ME!
     * @param   id         DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
// private void prepareAllFlurstueckKeys(final CidsBroker broker) {
// LOG.fatal("vorbereiten, sammeln der Flurstuecksschluessel");
//
// allFlurstueckKeys.clear();
//
// int count = 0;
//
// final Collection<GemarkungCustomBean> gemarkungsKeys = broker.getGemarkungsKeys();
// for (final Key gemarkungsKey : gemarkungsKeys) {
////        final Key gemarkungsKey = gemarkungsKeys.toArray(new Key[0])[0];
//            LOG.fatal("gemarkungsKey: " + gemarkungsKey);
//            final Collection<Key> flurKeys = broker.getDependingKeysForKey(gemarkungsKey);
//            for (final Key flurKey : flurKeys) {
//                LOG.fatal("flurKey: " + flurKey);
//                final Collection<Key> flurstueckKeys = broker.getDependingKeysForKey(flurKey);
//                for (final Key flurstueckKey : flurstueckKeys) {
//                    count++;
//                    LOG.fatal(allFlurstueckKeys.size() + ": " + flurstueckKey);
//                    final int from = 0;
//                    final int to = 10; // > 20333 für alle
//                    if ((count > from) && (count <= to)) {
//                        allFlurstueckKeys.add(flurstueckKey);
//                    }
//                    if (count >= (to - from)) {
//                        return;
//                    }
//                }
//            }
//        }
//    }

    /**
     * DOCUMENT ME!
     *
     * @param   tablename  broker DOCUMENT ME!
     * @param   id         DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
//    private float testPerfEjb(final CidsBroker broker) {
//        LOG.fatal("performance Start EJB");
//
//        final long startTimeEjb = System.nanoTime();
//
//        for (final Key flurstueckKey : allFlurstueckKeys) {
//            final FlurstueckCustomBean flurstueck = broker.retrieveFlurstueck((FlurstueckSchluesselCustomBean)
//                    flurstueckKey);
//            final String flurstueckString = EjbObjectsToStringTester.getStringOf(flurstueck);
//            LOG.fatal(ejbFlurstueckStrings.size() + 1 + "/" + allFlurstueckKeys.size() + ": " + flurstueckKey);
//            ejbFlurstueckStrings.put(flurstueck.getId(), flurstueckString);
////            print(flurstueckString);
//        }
//
//        final long stopTimeEjb = System.nanoTime();
//
//        return (stopTimeEjb - startTimeEjb) / 1000000000f;
//    }

    /**
     * DOCUMENT ME!
     *
     * @param   tablename  DOCUMENT ME!
     * @param   id         DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
//    private float testPerfMos() {
//        final MetaClass mc = ClassCacheMultiple.getMetaClass(CALLSERVER_DOMAIN, "flurstueck");
//
//        LOG.fatal("performance Start MOS");
//        final long startTimeMos = System.nanoTime();
//        try {
//            for (final Key flurstueckKey : allFlurstueckKeys) {
//                final Integer flId = ((FlurstueckSchluesselCustomBean)flurstueckKey).getId();
////            final Integer flId = 1776;
//                final String query = "SELECT " + mc.getID() + "," + mc.getPrimaryKey() + " FROM " + mc.getTableName()
//                            + " WHERE fk_flurstueck_schluessel = " + flId + ";";
//                final MetaObject[] mos = SessionManager.getConnection()
//                            .getMetaObjectByQuery(SessionManager.getSession().getUser(), query);
//                if (mos.length > 0) {
//                    final FlurstueckCustomBean flurstueckCustomBean = (FlurstueckCustomBean)createCB(
//                            "flurstueck",
//                            mos[0].getId());
////                    final FlurstueckCustomBean flurstueckCustomBean = (FlurstueckCustomBean)mos[0].getBean();
//                    final String flurstueckString = CustomBeanToStringTester.getStringOf(flurstueckCustomBean);
//                    LOG.fatal(mosFlurstueckStrings.size() + 1 + "/" + allFlurstueckKeys.size() + ": "
//                                + flurstueckCustomBean.toString());
//                    mosFlurstueckStrings.put(flurstueckCustomBean.getId(), flurstueckString);
////                    print(CustomBeanToStringTester.getStringOf(flurstueckString));
//                }
//            }
//        } catch (final ConnectionException ex) {
//            LOG.fatal("fehler bei testPerfCids", ex);
//        }
//        final long stopTimeMos = System.nanoTime();
//        return (stopTimeMos - startTimeMos) / 1000000000f;
//    }

//    /**
//     * DOCUMENT ME!
//     *
//     * @param  cidsBean  DOCUMENT ME!
//     */
//    private void printCidsBeanInfos(final CidsBean cidsBean) {
//        if (cidsBean != null) {
//            LOG.fatal(cidsBean.getClass().getSimpleName());
//            if (LOG.isDebugEnabled()) {
//                LOG.debug(cidsBean.getMOString());
//            }
//        } else {
//            if (LOG.isDebugEnabled()) {
//                LOG.debug("cidsbean is null");
//            }
//        }
//    }

    /**
     * DOCUMENT ME!
     *
     * @param   tablename  DOCUMENT ME!
     * @param   id         DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public CidsBean createCB(final String tablename, final int id) {
        try {
            final MetaClass mc = ClassCacheMultiple.getMetaClass(CidsBroker.BELIS_DOMAIN, tablename);
            // final MetaObject mo = proxy.getMetaObject(id, mc.getId(), CidsBroker.BELIS_DOMAIN);
            final String url = "SELECT " + mc.getId() + ", id FROM " + tablename + " WHERE id = (select min(id) from "
                        + tablename + ") ;";
            final MetaObject[] mos = proxy.getMetaObjectByQuery(SessionManager.getSession().getUser(),
                    url,
                    CidsBroker.BELIS_DOMAIN);
            if (mos.length > 0) {
                final CidsBean cidsBean = mos[0].getBean();
                return cidsBean;
            } else {
                return null;
            }
        } catch (Exception ex) {
            LOG.error(ex, ex);
        }
        return null;
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

//    /**
//     * DOCUMENT ME!
//     */
//    private void initEJBroker() {
//        CidsBroker.getInstance();
//    }

//    /**
//     * DOCUMENT ME!
//     *
//     * @param  broker  DOCUMENT ME!
//     */
//    private void testEJBroker(final CidsBroker broker) {
//    }

//    /**
//     * DOCUMENT ME!
//     *
//     * @param  broker  DOCUMENT ME!
//     */
//    private void testRoEJBroker(final LagisServerLocal broker) {
//        try {
//            print("=== getAllAnlageklassen() ===");
//            print(EjbObjectsToStringTester.getStringOf(broker.getAllAnlageklassen()));
//        } catch (Exception ex) {
//            print(ex);
//        }
//        try {
//            print("=== getAllBaumKategorien() ===");
//            print(EjbObjectsToStringTester.getStringOf(broker.getAllBaumKategorien()));
//        } catch (Exception ex) {
//            print(ex);
//        }
//        try {
//            print("=== getAllBaumMerkmale() ===");
//            print(EjbObjectsToStringTester.getStringOf(broker.getAllBaumMerkmale()));
//        } catch (Exception ex) {
//            print(ex);
//        }
//        try {
//            print("=== getAllBebauungen() ===");
//            print(EjbObjectsToStringTester.getStringOf(
//                    EjbObjectsToStringTester.getStringOf(broker.getAllBebauungen())));
//        } catch (Exception ex) {
//            print(ex);
//        }
//        try {
//            print("=== getAllBeschlussarten() ===");
//            print(EjbObjectsToStringTester.getStringOf(broker.getAllBeschlussarten()));
//        } catch (Exception ex) {
//            print(ex);
//        }
//        try {
//            print("=== getAllFlaechennutzungen() ===");
//            print(EjbObjectsToStringTester.getStringOf(broker.getAllFlaechennutzungen()));
//        } catch (Exception ex) {
//            print(ex);
//        }
//        try {
//            print("=== getAllFlurstueckArten() ===");
//            print(EjbObjectsToStringTester.getStringOf(broker.getAllFlurstueckArten()));
//        } catch (Exception ex) {
//            print(ex);
//        }
//        try {
//            print("=== getAllHistoryEntries() ===");
//            print(EjbObjectsToStringTester.getStringOf(broker.getAllHistoryEntries(null)));
//        } catch (Exception ex) {
//            print(ex);
//        }
//        try {
//            print("=== getAllKostenarten() ===");
//            print(EjbObjectsToStringTester.getStringOf(broker.getAllKostenarten()));
//        } catch (Exception ex) {
//            print(ex);
//        }
//        try {
//            print("=== getAllMiPaKategorien() ===");
//            print(EjbObjectsToStringTester.getStringOf(broker.getAllMiPaKategorien()));
//        } catch (Exception ex) {
//            print(ex);
//        }
//        try {
//            print("=== getAllMiPaMerkmale() ===");
//            print(EjbObjectsToStringTester.getStringOf(broker.getAllMiPaMerkmale()));
//        } catch (Exception ex) {
//            print(ex);
//        }
//        try {
//            print("=== getAllNutzungsarten() ===");
//            print(EjbObjectsToStringTester.getStringOf(broker.getAllNutzungsarten()));
//        } catch (Exception ex) {
//            print(ex);
//        }
//        try {
//            print("=== getAllRebeArten() ===");
//            print(EjbObjectsToStringTester.getStringOf(broker.getAllRebeArten()));
//        } catch (Exception ex) {
//            print(ex);
//        }
//        try {
//            print("=== getAllVertragsarten() ===");
//            print(EjbObjectsToStringTester.getStringOf(broker.getAllVertragsarten()));
//        } catch (Exception ex) {
//            print(ex);
//        }
//        try {
//            print("=== getAllVerwaltendeDienstellen() ===");
//            print(EjbObjectsToStringTester.getStringOf(broker.getAllVerwaltendeDienstellen()));
//        } catch (Exception ex) {
//            print(ex);
//        }
//        try {
//            print("=== getAllVerwaltenungsgebraeuche() ===");
//            print(EjbObjectsToStringTester.getStringOf(broker.getAllVerwaltenungsgebraeuche()));
//        } catch (Exception ex) {
//            print(ex);
//        }
//        try {
//            print("=== getBaumForKey(FlurstueckSchluesselCustomBean) ===");
//            print(EjbObjectsToStringTester.getStringOf(broker.getBaumForKey(null)));
//        } catch (Exception ex) {
//            print(ex);
//        }
//        try {
//            print("=== getCrossReferencesForBaum(Baum) ===");
//            print(EjbObjectsToStringTester.getStringOf(broker.getCrossReferencesForBaum(null)));
//        } catch (Exception ex) {
//            print(ex);
//        }
//        try {
//            print("=== getCrossReferencesForMiPa(MiPa) ===");
//            print(EjbObjectsToStringTester.getStringOf(broker.getCrossReferencesForMiPa(null)));
//        } catch (Exception ex) {
//            print(ex);
//        }
//        try {
//            print("=== getCrossReferencesForVertrag(Vertrag) ===");
//            print(EjbObjectsToStringTester.getStringOf(broker.getCrossReferencesForVertrag(null)));
//        } catch (Exception ex) {
//            print(ex);
//        }
//        try {
//            print("=== getCrossreferencesForBaeume(Set<Baum>) ===");
//            print(EjbObjectsToStringTester.getStringOf(broker.getCrossreferencesForBaeume(null)));
//        } catch (Exception ex) {
//            print(ex);
//        }
//        try {
//            print("=== getCrossreferencesForMiPas(Set<MiPa>) ===");
//            print(EjbObjectsToStringTester.getStringOf(broker.getCrossreferencesForMiPas(null)));
//        } catch (Exception ex) {
//            print(ex);
//        }
//        try {
//            print("=== getCrossreferencesForVertraege(Set<Vertrag>) ===");
//            print(EjbObjectsToStringTester.getStringOf(broker.getCrossreferencesForVertraege(null)));
//        } catch (Exception ex) {
//            print(ex);
//        }
//        try {
//            print("=== getDependingKeysForKey(Key) ===");
//            print(EjbObjectsToStringTester.getStringOf(broker.getDependingKeysForKey(null)));
//        } catch (Exception ex) {
//            print(ex);
//        }
//        try {
//            print("=== getFlurstueckSchluesselByAktenzeichen(String) ===");
//            print(EjbObjectsToStringTester.getStringOf(broker.getFlurstueckSchluesselByAktenzeichen(null)));
//        } catch (Exception ex) {
//            print(ex);
//        }
//        try {
//            print("=== getFlurstueckSchluesselForWFSFlurstueck(WfsFlurstueck) ===");
//            print(EjbObjectsToStringTester.getStringOf(broker.getFlurstueckSchluesselForWFSFlurstueck(null)));
//        } catch (Exception ex) {
//            print(ex);
//        }
//        try {
//            print("=== getGemarkungsKeys() ===");
//            print(EjbObjectsToStringTester.getStringOf(broker.getGemarkungsKeys()));
//        } catch (Exception ex) {
//            print(ex);
//        }
//        try {
//            print("=== getHistoryEntries(FlurstueckSchluesselCustomBean, HistoryLevel, HistoryType, int) ===");
//            print(EjbObjectsToStringTester.getStringOf(broker.getHistoryEntries(null, null, null, 0)));
//        } catch (Exception ex) {
//            print(ex);
//        }
//        try {
//            print("=== getMiPaForKey(FlurstueckSchluesselCustomBean) ===");
//            print(EjbObjectsToStringTester.getStringOf(broker.getMiPaForKey(null)));
//        } catch (Exception ex) {
//            print(ex);
//        }
//        try {
//            print("=== getVertraegeForKey(FlurstueckSchluesselCustomBean) ===");
//            print(EjbObjectsToStringTester.getStringOf(broker.getVertraegeForKey(null)));
//        } catch (Exception ex) {
//            print(ex);
//        }
//        try {
//            print("=== getCurrentDate() ===");
//            print(EjbObjectsToStringTester.getStringOf(broker.getCurrentDate()));
//        } catch (Exception ex) {
//            print(ex);
//        }
//        try {
//            print("=== hasFlurstueckSucccessors(FlurstueckSchluesselCustomBean) ===");
//            print(EjbObjectsToStringTester.getStringOf(broker.hasFlurstueckSucccessors(null)));
//        } catch (ActionNotSuccessfulException ex) {
//            print(ex);
//        }
//        try {
//            print("=== isFlurstueckHistoric(FlurstueckSchluesselCustomBean) ===");
//            print(EjbObjectsToStringTester.getStringOf(broker.isFlurstueckHistoric(null)));
//        } catch (Exception ex) {
//            print(ex);
//        }
//        try {
//            print("=== isLocked(FlurstueckSchluesselCustomBean) ===");
//            print(EjbObjectsToStringTester.getStringOf(broker.isLocked(null)));
//        } catch (Exception ex) {
//            print(ex);
//        }
//    }

    /**
     * DOCUMENT ME!
     *
     * @param  string  DOCUMENT ME!
     */
    private void print(final String string) {
        LOG.error(string, new Exception());
        System.out.println(string);
        printStream.println(string);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  exception  DOCUMENT ME!
     */
    private void print(final Exception exception) {
        LOG.error(exception.getMessage(), exception);
        // System.err.print(exception.getStackTrace());
    }

    /**
     * DOCUMENT ME!
     *
     * @param  file  DOCUMENT ME!
     */
    private static void setSoutToFile(final String file) {
        try {
            printStream = new PrintStream(
                    new BufferedOutputStream(new FileOutputStream(
                            new File(file))),
                    true);
        } catch (FileNotFoundException ex) {
            LOG.fatal("error setting sout to " + file, ex);
        }
    }
}
