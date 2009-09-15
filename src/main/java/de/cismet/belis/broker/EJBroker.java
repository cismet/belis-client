/*
 * EJBroker.java
 *
 * Created on 19. April 2007, 08:28
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package de.cismet.belis.broker;

import de.cismet.belisEE.bean.interfaces.BelisServerRemote;

import de.cismet.belisEE.entity.Bauart;
import de.cismet.belisEE.entity.Doppelkommando;
import de.cismet.belisEE.entity.Energielieferant;
import de.cismet.belisEE.entity.Kennziffer;
import de.cismet.belisEE.entity.Klassifizierung;
import de.cismet.belisEE.entity.Leitungstyp;
import de.cismet.belisEE.entity.Leuchtentyp;
import de.cismet.belisEE.entity.Lock;
import de.cismet.belisEE.entity.Mastart;
import de.cismet.belisEE.entity.Masttyp;
import de.cismet.belisEE.entity.MaterialLeitung;
import de.cismet.belisEE.entity.MaterialMauerlasche;
import de.cismet.belisEE.entity.Querschnitt;
import de.cismet.belisEE.entity.Standort;
import de.cismet.belis.panels.EJBReconnectorPanel;
import de.cismet.belisEE.entity.UnterhaltMast;
import de.cismet.belisEE.exception.ActionNotSuccessfulException;
import de.cismet.belisEE.util.StandortKey;
import de.cismet.belisEE.bean.interfaces.ObjectKey;
import de.cismet.belisEE.entity.Stadtbezirk;
import de.cismet.belisEE.entity.Strassenschluessel;
import de.cismet.belisEE.entity.UnterhaltLeuchte;
import de.cismet.belisEE.exception.LockAlreadyExistsException;
import de.cismet.cismap.commons.BoundingBox;
import de.cismet.commons.server.entity.BaseEntity;
import de.cismet.commons.server.interfaces.Geom;
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
import javax.ejb.EJBException;
import javax.naming.InitialContext;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import org.omg.CORBA.COMM_FAILURE;

/**
 *
 * @author Puhl
 */
//ToDo handle NoSuchEJBException
//ToDo Benutzernamen in Server auslagern --> hat hier nichts verloren
public final class EJBroker implements  BelisServerRemote {

    private static EJBroker brokerInstance = null;
    private static BelisServerRemote belisEJBServerStub;
    private final static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(EJBroker.class);
    private static JFrame parentFrame;
    private static JDialog brokenConnectionDialog;
    private static EJBReconnectorPanel EJBReconnectorPanel;
    private static JOptionPane brokenConnectionOptionPane;
    private static EJBConnector EJBConnectorWorker;
    private static JFrame dialogOwner;
    private static InitialContext ic;  
    protected ExecutorService execService = null;
    
    
    /** Creates a new instance of EJBroker */
    private EJBroker() {
        initBrokenConnectionDialog();
        try {
            execService = Executors.newCachedThreadPool();
            log.debug("Lookup des belisEJB");
//            System.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
            //TODO !!!! CONFIG FILE
            //System.setProperty("org.omg.CORBA.ORBInitialHost", "roberto");
//            System.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
            //ToDo Warning hardcoded must be removed
            //System.setProperty("org.omg.CORBA.ORBInitialPort", "5037");
            ic = new InitialContext();

            log.debug("Initial Kontext komplett");
            belisEJBServerStub = (BelisServerRemote) ic.lookup(BelisServerRemote.class.getName());
                                                                
            log.debug("Lookup des belisEJB erfolgreich");
        } catch (Throwable ex) {
            log.fatal("Fehler beim Verbinden mit Glassfish.\nFehler beim initialisieren/lookup des EJBs", ex);
            brokenConnectionDialog.setVisible(true);

        }
    }

    private final void initBrokenConnectionDialog() {
        if (dialogOwner == null) {
            EJBReconnectorPanel = new EJBReconnectorPanel();
            if (parentFrame != null && parentFrame.isVisible()) {
                log.debug("frame wurde gesetzt");
                dialogOwner = parentFrame;
            } else {
                log.debug("frame wurde nicht gesetzt");
                dialogOwner = null;
            }
            brokenConnectionOptionPane = new JOptionPane(
                    "Es konnte keine Verbindung zum Belis Server hergestellt werden.\n Was Möchten Sie tun ?",
                    JOptionPane.QUESTION_MESSAGE, JOptionPane.NO_OPTION, null, new Object[]{EJBReconnectorPanel});
            brokenConnectionDialog = new JDialog(dialogOwner,
                    "Fehler beim Verbinden mit dem Belis Server",
                    true);
            brokenConnectionDialog.setContentPane(brokenConnectionOptionPane);
            brokenConnectionDialog.setDefaultCloseOperation(
                    JDialog.DO_NOTHING_ON_CLOSE);
            brokenConnectionDialog.addWindowListener(new WindowAdapter() {

                @Override
                public void windowClosing(WindowEvent we) {
                    log.debug("User hat versucht, das Panel zu schließen");
                }
            });

            EJBReconnectorPanel.getBtnExitCancel().addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    if (EJBReconnectorPanel.getBtnExitCancel().getText().equals("Abbrechen")) {
                        EJBReconnectorPanel.getBtnExitCancel().setEnabled(false);
                        if (EJBConnectorWorker != null && !EJBConnectorWorker.isDone()) {
                            log.debug("EjbConnector wird abgebrochen");
                            EJBConnectorWorker.cancel(false);
                            EJBConnectorWorker = null;
                        } else {
                            log.warn("EjbConnector läuft nicht");
                            EJBReconnectorPanel.resetPanel();
                        }
                        log.debug("Verbindungsvorgang abgebrochen");
                    } else {
                        log.debug("Kein Verbindungsvorgang am laufen --> Belis wird beendet");
                        shutdownBelIS();
                    }
                }
            });
            EJBReconnectorPanel.getBtnRetry().addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    EJBReconnectorPanel.getPb().setIndeterminate(true);
                    //ejbReconnectorPanel.getPb().setVisible(true);
                    log.debug("vor Thread Start");
                    EJBConnectorWorker = new EJBConnector();
                    //EJBConnectorWorker.execute();
                    //BelisBroker.getInstance().execute(EJBConnectorWorker);
                    execute(EJBConnectorWorker);
                    log.debug("nach Thread start");
                    //ejbReconnectorPanel.getLblMessage().setText("Versuche zu verbinden...");
                    brokenConnectionOptionPane.setMessage("Versuche zu verbinden...");
                    EJBReconnectorPanel.getBtnExitCancel().setText("Abbrechen");
                    EJBReconnectorPanel.getBtnRetry().setVisible(false);

                }
            });
            brokenConnectionDialog.pack();
        }
        EJBReconnectorPanel.getPb().setIndeterminate(false);

        if (parentFrame == null || !parentFrame.isVisible()) {
            Dimension oldDim = brokenConnectionDialog.getSize();
            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            brokenConnectionDialog.setBounds((int) (screen.getWidth() / 2 - oldDim.getWidth()), (int) (screen.getHeight() / 2 - oldDim.getHeight()), (int) oldDim.getWidth(), (int) oldDim.getHeight());
        } else if (parentFrame != null && parentFrame.isVisible()) {
            brokenConnectionDialog.setLocationRelativeTo(parentFrame);
        }
    //ejbReconnectorPanel.getPb().setVisible(false);
    }
    
    
    //ToDo centralise
    public void execute(SwingWorker workerThread) {
        try {
            execService.submit(workerThread);
            log.debug("SwingWorker an Threadpool übermittelt");
        } catch (Exception ex) {
            log.fatal("Fehler beim starten eines Swingworkers", ex);
        }
    }

    private void shutdownBelIS() {
        if (parentFrame != null && parentFrame.isVisible()) {
            int result = JOptionPane.showConfirmDialog(brokenConnectionDialog, "Sind Sie sicher, das Sie Belis beenden wollen?\nAlle nicht gespeicherten Änderungen gehen verloren!", "Bestätigung", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                ((JFrame) parentFrame).dispose();
            } else {
                log.debug("beenden abgebrochen");
            }
        } else {
            System.exit(1);
        }
    }

//synchronized because the lookup take some time and multiple calls causes multiple lookups --> exception
    public synchronized static EJBroker getInstance() {
        if (brokerInstance == null) {
            brokerInstance = new EJBroker();
        }
        return brokerInstance;
    }

//    private Integer blocker = new Integer(0);
//    //TODO im Moment nur inital was wenn lagisEJB != null
//    private void isConnected(){
//        if(lagisEJB != null){
//            return;
//        } else {
//            synchronized(blocker){
//                if(lagisEJB == null){
//                    boolean shouldRetry = true;
//                    while(shouldRetry){
//                        shouldRetry = JOptionPane.showConfirmDialog(null,
//                                "Es besteht keine Verbindung zum Server möchten Sie es weiterversuchen ?\n(Nein beendet die Applikation und alle nichtgespeicherten Daten gehen verloren)","Server Verbindung",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
//                        if (shouldRetry){
//                            try {
//                                log.debug("Versuche Verbindung zum Server herzustellen");
//                                InitialContext ic = new InitialContext();
//                                log.debug("Initial Kontext komplett");
//                                lagisEJB = (LagisServerRemote) ic.lookup("de.cismet.lagisEE.bean.LagisServerRemote");
//                                log.debug("Lookup des LagisEJB erfolgreich");
//                                return;
//                            } catch (Throwable ex) {
//                                log.fatal("Fehler beim initialisieren/lookup des EJBs",ex);
//                            }
//                        } else {
//                            log.debug("Applikation wird auf Benutzerwunsch beendet --> Keine Serververbindung");
//                            System.exit(10);
//                        }
//                    }
//                }
//
//            };
//        }
//    }
   
     private void handleEJBException(final EJBException ex) {
        Exception causedEx = ex.getCausedByException();
        if (causedEx != null && causedEx instanceof MarshalException) {
            log.debug("CausedException ist eine MarshalException");
            Throwable t = causedEx.getCause();
            if (t != null && t instanceof COMM_FAILURE) {
                log.debug("Exception ist eine Corba COMM FAILURE Exception");
                if (brokenConnectionDialog.isVisible()) {
                    log.debug("Dialog ist bereist sichtbar --> gehe schlafen");
                    while (brokenConnectionDialog.isVisible()) {
                        try {
                            log.debug("Thread wartet auf wiederverbindung");
                            Thread.currentThread().sleep(500);
                        } catch (InterruptedException IntEx) {
                            log.debug("Schlafender Thread wurde unterbrochen", ex);
                            return;
                        }
                    }
                } else {
                    log.debug("Dialog noch nicht sichtbar --> zeige an");
                    initBrokenConnectionDialog();
                    brokenConnectionDialog.setVisible(true);
                }
            } else {
                log.debug("Throwable ist keine Corba Comm Failure Exception --> rethrow");
                throw ex;
            }
        } else {
            log.debug("CausedException ist keine MarshalException --> rethrow");
            throw ex;
        }
    }
    

    class EJBConnector extends SwingWorker<BelisServerRemote, Void> {

        private boolean hadErrors;
        private String errorMessage;

        public EJBConnector() {
        }

        protected BelisServerRemote doInBackground() throws Exception {
            log.debug("starte EJBConnectorjob");
            try {
                log.debug("Lookup des BelisEJB");
                if (ic != null) {
                    log.debug("vor intial Kontext");
                    InitialContext ic = new InitialContext();
                    log.debug("Initial Kontext komplett");
                }
                BelisServerRemote tmpBelisEJB = (BelisServerRemote) ic.lookup("de.cismet.belisEE.bean.BelisServerRemote");
                log.debug("Lookup des BelisEJB erfolgreich");
                return tmpBelisEJB;
            } catch (Throwable ex) {
                log.fatal("Fehler beim Verbinden mit Glassfish.\nFehler beim initialisieren/lookup des EJBs", ex);
                return null;
            }
        }

        @Override
        protected void done() {
            try {
                log.debug("EJBConnector done");
                if (isCancelled()) {
                    log.debug("EJBConnector canceled");
                    brokenConnectionOptionPane.setMessage("Es konnte keine Verbindung zum BelIS Server hergestellt werden.\n Was Möchten Sie tun ?");
                    EJBReconnectorPanel.resetPanel();
                    return;
                }
                belisEJBServerStub = get();
                if (belisEJBServerStub != null) {
                    log.debug("Verbinden mit Glassifsh und abrufen des BelisEJB erfogreich");
                    brokenConnectionDialog.setVisible(false);
                    EJBReconnectorPanel.resetPanel();
                } else {
                    log.debug("Verbinden mit Glassifsh und abrufen des BelisEJB nicht erfogreich");
                    EJBReconnectorPanel.resetPanel();
                }
            } catch (Exception ex) {
                log.error("Fehler beim Verbinden mit Glassfish(done)");

            }
            brokenConnectionOptionPane.setMessage("Es konnte keine Verbindung zum Belis Server hergestellt werden.\n Was Möchten Sie tun ?");
        }
    }
    
    public Set<Standort> retrieveStandort(StandortKey key) throws ActionNotSuccessfulException{
         try {
            return belisEJBServerStub.retrieveStandort(key);
        } catch (EJBException ex) {
            log.debug("Exception ist eine EJBException");
            handleEJBException(ex);
            return retrieveStandort(key);
        }
    }

    public Set<Kennziffer> getAllKennziffer() throws ActionNotSuccessfulException{
        try {
            return belisEJBServerStub.getAllKennziffer();
        } catch (EJBException ex) {
            log.debug("Exception ist eine EJBException");
            handleEJBException(ex);
            return getAllKennziffer();
        }
    }

    public Set<Strassenschluessel> getAllStrassenschluessel() throws ActionNotSuccessfulException{
        try {
            return belisEJBServerStub.getAllStrassenschluessel();
        } catch (EJBException ex) {
            log.debug("Exception ist eine EJBException");
            handleEJBException(ex);
            return getAllStrassenschluessel();
        }
    }

    public Set<Energielieferant> getAllEnergielieferanten() throws ActionNotSuccessfulException{
         try {
            return belisEJBServerStub.getAllEnergielieferanten();
        } catch (EJBException ex) {
            log.debug("Exception ist eine EJBException");
            handleEJBException(ex);
            return getAllEnergielieferanten();
        }
    }

    public Set<Stadtbezirk> getAllStadtbezirke() throws ActionNotSuccessfulException{        
         try {
            return belisEJBServerStub.getAllStadtbezirke();
        } catch (EJBException ex) {
            log.debug("Exception ist eine EJBException");
            handleEJBException(ex);
            return getAllStadtbezirke();
        }
    }

    public Set<Klassifizierung> getAllKlassifizierungen() throws ActionNotSuccessfulException {
        try {
            return belisEJBServerStub.getAllKlassifizierungen();
        } catch (EJBException ex) {
            log.debug("Exception ist eine EJBException");
            handleEJBException(ex);
            return getAllKlassifizierungen();
        }
    }

    public Set<Mastart> getAllMastarten() throws ActionNotSuccessfulException {
        try {
            return belisEJBServerStub.getAllMastarten();
        } catch (EJBException ex) {
            log.debug("Exception ist eine EJBException");
            handleEJBException(ex);
            return getAllMastarten();
        }
    }

    public Set<Masttyp> getAllMasttypen() throws ActionNotSuccessfulException {
        try {
            return belisEJBServerStub.getAllMasttypen();
        } catch (EJBException ex) {
            log.debug("Exception ist eine EJBException");
            handleEJBException(ex);
            return getAllMasttypen();
        }
    }
    
    public Set<MaterialLeitung> getAllMaterialLeitung() throws ActionNotSuccessfulException{
        try {
            return belisEJBServerStub.getAllMaterialLeitung();
        } catch (EJBException ex) {
            log.debug("Exception ist eine EJBException");
            handleEJBException(ex);
            return getAllMaterialLeitung();
        }
    }
    public Set<Leitungstyp> getAllLeitungstypen() throws ActionNotSuccessfulException{
         try {
            return belisEJBServerStub.getAllLeitungstypen();
        } catch (EJBException ex) {
            log.debug("Exception ist eine EJBException");
            handleEJBException(ex);
            return getAllLeitungstypen();
        }
    }
    
    public Set<Querschnitt> getAllQuerschnitte() throws ActionNotSuccessfulException{
         try {
            return belisEJBServerStub.getAllQuerschnitte();
        } catch (EJBException ex) {
            log.debug("Exception ist eine EJBException");
            handleEJBException(ex);
            return getAllQuerschnitte();
        }
    }

    public Set<UnterhaltMast> getAllUnterhaltMast() throws ActionNotSuccessfulException {
        try {
            return belisEJBServerStub.getAllUnterhaltMast();
        } catch (EJBException ex) {
            log.debug("Exception ist eine EJBException");
            handleEJBException(ex);
            return getAllUnterhaltMast();
        }
    }
    
   
    public Set<UnterhaltLeuchte> getAllUnterhaltLeuchte() throws ActionNotSuccessfulException{
        try {
            return belisEJBServerStub.getAllUnterhaltLeuchte();
        } catch (EJBException ex) {
            log.debug("Exception ist eine EJBException");
            handleEJBException(ex);
            return getAllUnterhaltLeuchte();
        }
    }
    
    public Set<MaterialMauerlasche> getAllMaterialMauerlasche() throws ActionNotSuccessfulException {
        try {
            return belisEJBServerStub.getAllMaterialMauerlasche();
        } catch (EJBException ex) {
            log.debug("Exception ist eine EJBException");
            handleEJBException(ex);
            return getAllMaterialMauerlasche();
        }
    }
    
    public Set<Bauart> getAllBauarten() throws ActionNotSuccessfulException{
        try {
            return belisEJBServerStub.getAllBauarten();
        } catch (EJBException ex) {
            log.debug("Exception ist eine EJBException");
            handleEJBException(ex);
            return getAllBauarten();
        }
    }

    public Set<Leuchtentyp> getAllLeuchtentypen() throws ActionNotSuccessfulException {
        try {
            return belisEJBServerStub.getAllLeuchtentypen();
        } catch (EJBException ex) {
            log.debug("Exception ist eine EJBException");
            handleEJBException(ex);
            return getAllLeuchtentypen();
        }
    }

    public Set<Doppelkommando> getAllDoppelkommando() throws ActionNotSuccessfulException {
        try {
            return belisEJBServerStub.getAllDoppelkommando();
        } catch (EJBException ex) {
            log.debug("Exception ist eine EJBException");
            handleEJBException(ex);
            return getAllDoppelkommando();
        }
    }



    public TreeSet<BaseEntity> getObjectsByKey(String strassenschluessel, Short kennziffer, Short laufendeNummer) throws ActionNotSuccessfulException {
         try {
            return belisEJBServerStub.getObjectsByKey(strassenschluessel,kennziffer,laufendeNummer);
        } catch (EJBException ex) {
            log.debug("Exception ist eine EJBException");
            handleEJBException(ex);
            return getObjectsByKey(strassenschluessel,kennziffer,laufendeNummer);
        }
    }

    public Lock isEntityLocked(Object lockedObject) throws ActionNotSuccessfulException {
         try {
            return belisEJBServerStub.isEntityLocked(lockedObject);
        } catch (EJBException ex) {
            log.debug("Exception ist eine EJBException");
            handleEJBException(ex);
            return isEntityLocked(lockedObject);
        }
    }

    public Lock lockEntity(Object objectToLock, String userString) throws ActionNotSuccessfulException, LockAlreadyExistsException {
        try {
            return lockEntity(objectToLock, userString);
        } catch (EJBException ex) {
            log.debug("Exception ist eine EJBException");
            handleEJBException(ex);
            return lockEntity(objectToLock, userString);
        }
    }

    public Set<Lock> lockEntity(Set<Object> objectsToLock, String userString) throws ActionNotSuccessfulException, LockAlreadyExistsException {
        try {
            return belisEJBServerStub.lockEntity(objectsToLock,userString);
        } catch (EJBException ex) {
            log.debug("Exception ist eine EJBException");
            handleEJBException(ex);
            return lockEntity(objectsToLock,userString);
        }
    }

    public Lock tryToLockEntity(Object lockedObject, String userString) throws ActionNotSuccessfulException, LockAlreadyExistsException {
        try {
            return belisEJBServerStub.tryToLockEntity(lockedObject,userString);
        } catch (EJBException ex) {
            log.debug("Exception ist eine EJBException");
            handleEJBException(ex);
            return tryToLockEntity(lockedObject,userString);
        }
    }

    public void unlockEntity(Object objectToUnlock) throws ActionNotSuccessfulException {
        try {
            belisEJBServerStub.unlockEntity(objectToUnlock);
        } catch (EJBException ex) {
            log.debug("Exception ist eine EJBException");
            handleEJBException(ex);
            unlockEntity(objectToUnlock);
        }
    }

    public Set<Object> unlockEntity(Set<Object> objectsToUnlock) throws ActionNotSuccessfulException {
        try {
            return belisEJBServerStub.unlockEntity(objectsToUnlock);
        } catch (EJBException ex) {
            log.debug("Exception ist eine EJBException");
            handleEJBException(ex);
            return unlockEntity(objectsToUnlock);
        }
    }

    public void unlockEntity(Lock holdedLock) throws ActionNotSuccessfulException {
        try {
            belisEJBServerStub.unlockEntity(holdedLock);
        } catch (EJBException ex) {
            log.debug("Exception ist eine EJBException");
            handleEJBException(ex);
            unlockEntity(holdedLock);
        }
    }

    public Set<BaseEntity> refreshObjects(Set<BaseEntity> objectsToSave) throws ActionNotSuccessfulException {
        try {
            return belisEJBServerStub.refreshObjects(objectsToSave);
        } catch (EJBException ex) {
            log.debug("Exception ist eine EJBException");
            handleEJBException(ex);
            return refreshObjects(objectsToSave);
        }
    }

    
    
    public Set<BaseEntity> saveObjects(Set<BaseEntity> objectsToSave, String userString) throws ActionNotSuccessfulException {
        try {
            return belisEJBServerStub.saveObjects(objectsToSave, userString);
        } catch (EJBException ex) {
            log.debug("Exception ist eine EJBException");
            handleEJBException(ex);
            return saveObjects(objectsToSave, userString);
        }
    }

    //public Set getObjectsByBoundingBox(String geometryText) throws ActionNotSuccessfulException {        
    public TreeSet<BaseEntity> getObjectsByBoundingBox(BoundingBox bb) throws ActionNotSuccessfulException {
        try {
            return belisEJBServerStub.getObjectsByBoundingBox(bb);
        } catch (EJBException ex) {
            log.debug("Exception ist eine EJBException");
            handleEJBException(ex);
            return getObjectsByBoundingBox(bb);
        }
    }

    public Object getObjectsByGeom(Geom geom) throws ActionNotSuccessfulException {
        try {
            return belisEJBServerStub.getObjectsByGeom(geom);
        } catch (EJBException ex) {
            log.debug("Exception ist eine EJBException");
            handleEJBException(ex);
            return getObjectsByGeom(geom);
        }
    }

    public void deleteEntities(Set<BaseEntity> entitiesToRemove,String userString) throws ActionNotSuccessfulException {
        try {
            belisEJBServerStub.deleteEntities(entitiesToRemove,userString);
        } catch (EJBException ex) {
            log.debug("Exception ist eine EJBException");
            handleEJBException(ex);
             deleteEntities(entitiesToRemove,userString);
        }
    }

    public void deleteEntity(BaseEntity entityToRemove,String userString) throws ActionNotSuccessfulException {
        try {
            belisEJBServerStub.deleteEntity(entityToRemove,userString);
        } catch (EJBException ex) {
            log.debug("Exception ist eine EJBException");
            handleEJBException(ex);
             deleteEntity(entityToRemove,userString);
        }
    }
    
    
}
