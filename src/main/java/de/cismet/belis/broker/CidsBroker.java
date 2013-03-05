/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.belis.broker;

import org.omg.CORBA.COMM_FAILURE;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.rmi.MarshalException;

import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.naming.InitialContext;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import de.cismet.belis.panels.EJBReconnectorPanel;

import de.cismet.belisEE.bean.interfaces.BelisServerRemote;

import de.cismet.belisEE.entity.Lock;

import de.cismet.belisEE.exception.ActionNotSuccessfulException;
import de.cismet.belisEE.exception.LockAlreadyExistsException;

import de.cismet.belisEE.util.StandortKey;

import de.cismet.cids.custom.beans.belis.BauartCustomBean;
import de.cismet.cids.custom.beans.belis.GeomCustomBean;
import de.cismet.cids.custom.beans.belis.LeitungstypCustomBean;
import de.cismet.cids.custom.beans.belis.MaterialLeitungCustomBean;
import de.cismet.cids.custom.beans.belis.MaterialMauerlascheCustomBean;
import de.cismet.cids.custom.beans.belis.QuerschnittCustomBean;
import de.cismet.cids.custom.beans.belis.TdtaStandortMastCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyBezirkCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyDoppelkommandoCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyEnergielieferantCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyKennzifferCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyKlassifizierungCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyLeuchtentypCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyMastartCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyMasttypCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyStrassenschluesselCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyUnterhLeuchteCustomBean;
import de.cismet.cids.custom.beans.belis.TkeyUnterhMastCustomBean;

import de.cismet.cismap.commons.BoundingBox;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class CidsBroker implements BelisServerRemote {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(CidsBroker.class);
    public static final String BELIS_DOMAIN = "BELIS";

    private static CidsBroker brokerInstance = null;
    private static JFrame parentFrame;
    private static JDialog brokenConnectionDialog;
    private static EJBReconnectorPanel EJBReconnectorPanel;
    private static JOptionPane brokenConnectionOptionPane;
    private static CidsBroker.EJBConnector EJBConnectorWorker;
    private static JFrame dialogOwner;
    private static InitialContext ic;

    //~ Instance fields --------------------------------------------------------

    protected ExecutorService execService = null;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new CidsBroker object.
     */
    public CidsBroker() {
        initBrokenConnectionDialog();
        try {
            execService = Executors.newCachedThreadPool();
            if (LOG.isDebugEnabled()) {
                LOG.debug("Lookup des belisEJB");
            }
//            System.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
            // TODO !!!! CONFIG FILE
            // System.setProperty("org.omg.CORBA.ORBInitialHost", "roberto");
//            System.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
            // ToDo Warning hardcoded must be removed
            // System.setProperty("org.omg.CORBA.ORBInitialPort", "5037");
            ic = new InitialContext();
            if (LOG.isDebugEnabled()) {
                LOG.debug("Initial Kontext komplett");
            }
//            belisEJBServerStub = (BelisServerRemote)ic.lookup(BelisServerRemote.class.getName());
            if (LOG.isDebugEnabled()) {
                LOG.debug("Lookup des belisEJB erfolgreich");
            }
        } catch (Throwable ex) {
            LOG.fatal("Fehler beim Verbinden mit Glassfish.\nFehler beim initialisieren/lookup des EJBs", ex);
            brokenConnectionDialog.setVisible(true);
        }
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     */
    private void initBrokenConnectionDialog() {
        if (dialogOwner == null) {
            EJBReconnectorPanel = new EJBReconnectorPanel();
            if ((parentFrame != null) && parentFrame.isVisible()) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("frame wurde gesetzt");
                }
                dialogOwner = parentFrame;
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("frame wurde nicht gesetzt");
                }
                dialogOwner = null;
            }
            brokenConnectionOptionPane = new JOptionPane(
                    "Es konnte keine Verbindung zum Belis Server hergestellt werden.\n Was Möchten Sie tun ?",
                    JOptionPane.QUESTION_MESSAGE,
                    JOptionPane.NO_OPTION,
                    null,
                    new Object[] { EJBReconnectorPanel });
            brokenConnectionDialog = new JDialog(dialogOwner,
                    "Fehler beim Verbinden mit dem Belis Server",
                    true);
            brokenConnectionDialog.setContentPane(brokenConnectionOptionPane);
            brokenConnectionDialog.setDefaultCloseOperation(
                JDialog.DO_NOTHING_ON_CLOSE);
            brokenConnectionDialog.addWindowListener(new WindowAdapter() {

                    @Override
                    public void windowClosing(final WindowEvent we) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("User hat versucht, das Panel zu schließen");
                        }
                    }
                });

            EJBReconnectorPanel.getBtnExitCancel().addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        if (EJBReconnectorPanel.getBtnExitCancel().getText().equals("Abbrechen")) {
                            EJBReconnectorPanel.getBtnExitCancel().setEnabled(false);
                            if ((EJBConnectorWorker != null) && !EJBConnectorWorker.isDone()) {
                                if (LOG.isDebugEnabled()) {
                                    LOG.debug("EjbConnector wird abgebrochen");
                                }
                                EJBConnectorWorker.cancel(false);
                                EJBConnectorWorker = null;
                            } else {
                                LOG.warn("EjbConnector läuft nicht");
                                EJBReconnectorPanel.resetPanel();
                            }
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("Verbindungsvorgang abgebrochen");
                            }
                        } else {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("Kein Verbindungsvorgang am laufen --> Belis wird beendet");
                            }
                            shutdownBelIS();
                        }
                    }
                });
            EJBReconnectorPanel.getBtnRetry().addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        EJBReconnectorPanel.getPb().setIndeterminate(true);
                        if (LOG.isDebugEnabled()) {
                            // ejbReconnectorPanel.getPb().setVisible(true);
                            LOG.debug("vor Thread Start");
                        }
//                        EJBConnectorWorker = new EJBroker.EJBConnector();
                        // EJBConnectorWorker.execute();
                        // BelisBroker.getInstance().execute(EJBConnectorWorker);
                        execute(EJBConnectorWorker);
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("nach Thread start");
                        }
                        // ejbReconnectorPanel.getLblMessage().setText("Versuche zu verbinden...");
                        brokenConnectionOptionPane.setMessage("Versuche zu verbinden...");
                        EJBReconnectorPanel.getBtnExitCancel().setText("Abbrechen");
                        EJBReconnectorPanel.getBtnRetry().setVisible(false);
                    }
                });
            brokenConnectionDialog.pack();
        }
        EJBReconnectorPanel.getPb().setIndeterminate(false);

        if ((parentFrame == null) || !parentFrame.isVisible()) {
            final Dimension oldDim = brokenConnectionDialog.getSize();
            final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            brokenConnectionDialog.setBounds((int)((screen.getWidth() / 2) - oldDim.getWidth()),
                (int)((screen.getHeight() / 2) - oldDim.getHeight()),
                (int)oldDim.getWidth(),
                (int)oldDim.getHeight());
        } else if ((parentFrame != null) && parentFrame.isVisible()) {
            brokenConnectionDialog.setLocationRelativeTo(parentFrame);
        }
        // ejbReconnectorPanel.getPb().setVisible(false);
    }

    /**
     * ToDo centralise.
     *
     * @param  workerThread  DOCUMENT ME!
     */
    public void execute(final SwingWorker workerThread) {
        try {
            execService.submit(workerThread);
            if (LOG.isDebugEnabled()) {
                LOG.debug("SwingWorker an Threadpool übermittelt");
            }
        } catch (Exception ex) {
            LOG.fatal("Fehler beim starten eines Swingworkers", ex);
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void shutdownBelIS() {
        if ((parentFrame != null) && parentFrame.isVisible()) {
            final int result = JOptionPane.showConfirmDialog(
                    brokenConnectionDialog,
                    "Sind Sie sicher, das Sie Belis beenden wollen?\nAlle nicht gespeicherten Änderungen gehen verloren!",
                    "Bestätigung",
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                ((JFrame)parentFrame).dispose();
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("beenden abgebrochen");
                }
            }
        } else {
            System.exit(1);
        }
    }
    /**
     * synchronized because the lookup take some time and multiple calls causes multiple lookups --> exception.
     *
     * @return  DOCUMENT ME!
     */
    public static synchronized CidsBroker getInstance() {
        if (brokerInstance == null) {
            brokerInstance = new CidsBroker();
        }
        return brokerInstance;
    }

    /**
     * private Integer blocker = new Integer(0); //TODO im Moment nur inital was wenn lagisEJB != null private void
     * isConnected(){ if(lagisEJB != null){ return; } else { synchronized(blocker){ if(lagisEJB == null){ boolean
     * shouldRetry = true; while(shouldRetry){ shouldRetry = JOptionPane.showConfirmDialog(null, "Es besteht keine
     * Verbindung zum Server möchten Sie es weiterversuchen ?\n(Nein beendet die Applikation und alle nichtgespeicherten
     * Daten gehen verloren)","Server Verbindung",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION; if (shouldRetry){
     * try { log.debug("Versuche Verbindung zum Server herzustellen"); InitialContext ic = new InitialContext();
     * log.debug("Initial Kontext komplett"); lagisEJB = (LagisServerRemote)
     * ic.lookup("de.cismet.lagisEE.bean.LagisServerRemote"); log.debug("Lookup des LagisEJB erfolgreich"); return; }
     * catch (Throwable ex) { log.fatal("Fehler beim initialisieren/lookup des EJBs",ex); } } else {
     * log.debug("Applikation wird auf Benutzerwunsch beendet --> Keine Serververbindung"); System.exit(10); } } } }; }
     * }.
     *
     * @param  causedEx  ex DOCUMENT ME!
     */
    private void handleEJBException(final Exception causedEx) {
        if ((causedEx != null) && (causedEx instanceof MarshalException)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("CausedException ist eine MarshalException");
            }
            final Throwable t = causedEx.getCause();
            if ((t != null) && (t instanceof COMM_FAILURE)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Exception ist eine Corba COMM FAILURE Exception");
                }
                if (brokenConnectionDialog.isVisible()) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Dialog ist bereist sichtbar --> gehe schlafen");
                    }
                    while (brokenConnectionDialog.isVisible()) {
                        try {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("Thread wartet auf wiederverbindung");
                            }
                            Thread.currentThread().sleep(500);
                        } catch (InterruptedException IntEx) {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("Schlafender Thread wurde unterbrochen", causedEx);
                            }
                            return;
                        }
                    }
                } else {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Dialog noch nicht sichtbar --> zeige an");
                    }
                    initBrokenConnectionDialog();
                    brokenConnectionDialog.setVisible(true);
                }
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Throwable ist keine Corba Comm Failure Exception --> rethrow");
                }
            }
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("CausedException ist keine MarshalException --> rethrow");
            }
        }
    }

    @Override
    public Set<TkeyStrassenschluesselCustomBean> getAllStrassenschluessel() throws ActionNotSuccessfulException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<TkeyEnergielieferantCustomBean> getAllEnergielieferanten() throws ActionNotSuccessfulException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<TkeyKennzifferCustomBean> getAllKennziffer() throws ActionNotSuccessfulException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<TkeyBezirkCustomBean> getAllStadtbezirke() throws ActionNotSuccessfulException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<TkeyMastartCustomBean> getAllMastarten() throws ActionNotSuccessfulException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<TkeyMasttypCustomBean> getAllMasttypen() throws ActionNotSuccessfulException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<TkeyKlassifizierungCustomBean> getAllKlassifizierungen() throws ActionNotSuccessfulException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<TkeyUnterhMastCustomBean> getAllUnterhaltMast() throws ActionNotSuccessfulException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<TkeyUnterhLeuchteCustomBean> getAllUnterhaltLeuchte() throws ActionNotSuccessfulException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<MaterialLeitungCustomBean> getAllMaterialLeitung() throws ActionNotSuccessfulException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<LeitungstypCustomBean> getAllLeitungstypen() throws ActionNotSuccessfulException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<QuerschnittCustomBean> getAllQuerschnitte() throws ActionNotSuccessfulException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<MaterialMauerlascheCustomBean> getAllMaterialMauerlasche() throws ActionNotSuccessfulException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<BauartCustomBean> getAllBauarten() throws ActionNotSuccessfulException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<TkeyLeuchtentypCustomBean> getAllLeuchtentypen() throws ActionNotSuccessfulException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<TkeyDoppelkommandoCustomBean> getAllDoppelkommando() throws ActionNotSuccessfulException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public TreeSet<BaseEntity> getObjectsByKey(final String strassenschluessel,
            final Short kennziffer,
            final Short laufendeNummer) throws ActionNotSuccessfulException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<TdtaStandortMastCustomBean> retrieveStandort(final StandortKey key) throws ActionNotSuccessfulException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<BaseEntity> saveObjects(final Set<BaseEntity> objectsToSave, final String userString)
            throws ActionNotSuccessfulException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<BaseEntity> refreshObjects(final Set<BaseEntity> objectsToSave) throws ActionNotSuccessfulException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteEntity(final BaseEntity objectsToDelete, final String userString)
            throws ActionNotSuccessfulException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteEntities(final Set<BaseEntity> objectsToDelete, final String userString)
            throws ActionNotSuccessfulException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public TreeSet<BaseEntity> getObjectsByBoundingBox(final BoundingBox bb) throws ActionNotSuccessfulException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object getObjectsByGeom(final GeomCustomBean geom) throws ActionNotSuccessfulException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean checkIfStandortExists(final TdtaStandortMastCustomBean standort)
            throws ActionNotSuccessfulException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public TdtaStandortMastCustomBean determineNextLaufendenummer(final TdtaStandortMastCustomBean standort,
            final Short minimalNumber) throws ActionNotSuccessfulException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Lock lockEntity(final Object objectToLock, final String userString) throws ActionNotSuccessfulException,
        LockAlreadyExistsException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<Lock> lockEntity(final Set<Object> objectsToLock, final String userString)
            throws ActionNotSuccessfulException, LockAlreadyExistsException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Lock isEntityLocked(final Object lockedObject) throws ActionNotSuccessfulException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Lock tryToLockEntity(final Object lockedObject, final String userString) throws ActionNotSuccessfulException,
        LockAlreadyExistsException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void unlockEntity(final Object objectToUnlock) throws ActionNotSuccessfulException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<Object> unlockEntity(final Set<Object> objectsToUnlock) throws ActionNotSuccessfulException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void unlockEntity(final Lock holdedLock) throws ActionNotSuccessfulException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    class EJBConnector extends SwingWorker<BelisServerRemote, Void> {

        //~ Instance fields ----------------------------------------------------

        private boolean hadErrors;
        private String errorMessage;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new EJBConnector object.
         */
        public EJBConnector() {
        }

        //~ Methods ------------------------------------------------------------

        @Override
        protected BelisServerRemote doInBackground() throws Exception {
            if (LOG.isDebugEnabled()) {
                LOG.debug("starte EJBConnectorjob");
            }
            try {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Lookup des BelisEJB");
                }
                if (ic != null) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("vor intial Kontext");
                    }
                    final InitialContext ic = new InitialContext();
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Initial Kontext komplett");
                    }
                }
                final BelisServerRemote tmpBelisEJB = (BelisServerRemote)ic.lookup(
                        "de.cismet.belisEE.bean.BelisServerRemote");
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Lookup des BelisEJB erfolgreich");
                }
                return tmpBelisEJB;
            } catch (Throwable ex) {
                LOG.fatal("Fehler beim Verbinden mit Glassfish.\nFehler beim initialisieren/lookup des EJBs", ex);
                return null;
            }
        }

        @Override
        protected void done() {
            try {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("EJBConnector done");
                }
                if (isCancelled()) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("EJBConnector canceled");
                    }
                    brokenConnectionOptionPane.setMessage(
                        "Es konnte keine Verbindung zum BelIS Server hergestellt werden.\n Was Möchten Sie tun ?");
                    EJBReconnectorPanel.resetPanel();
                    return;
                }
//                belisEJBServerStub = get();
//                if (belisEJBServerStub != null) {
//                    if (log.isDebugEnabled()) {
//                        log.debug("Verbinden mit Glassifsh und abrufen des BelisEJB erfogreich");
//                    }
//                    brokenConnectionDialog.setVisible(false);
//                    EJBReconnectorPanel.resetPanel();
//                } else {
//                    if (log.isDebugEnabled()) {
//                        log.debug("Verbinden mit Glassifsh und abrufen des BelisEJB nicht erfogreich");
//                    }
//                    EJBReconnectorPanel.resetPanel();
//                }
            } catch (Exception ex) {
                LOG.error("Fehler beim Verbinden mit Glassfish(done)");
            }
            brokenConnectionOptionPane.setMessage(
                "Es konnte keine Verbindung zum Belis Server hergestellt werden.\n Was Möchten Sie tun ?");
        }
    }
}
