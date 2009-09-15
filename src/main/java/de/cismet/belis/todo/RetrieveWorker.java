/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.belis.todo;

import de.cismet.belis.broker.BelisBroker;
import de.cismet.belisEE.bean.interfaces.ObjectKey;
import de.cismet.cismap.commons.BoundingBox;
import de.cismet.commons.server.entity.BaseEntity;
import java.util.Set;
import javax.swing.SwingWorker;

/**
 *
 * @author spuhl
 */
public class RetrieveWorker extends SwingWorker<Set<BaseEntity>, Void> {

    private final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(RetrieveWorker.class);
    private String strassenschluessel;
    private Short kennziffer;
    private Short laufendenummer;
    private BoundingBox boundingBox;
    private BelisBroker broker;

    public RetrieveWorker(BelisBroker broker, String strassenschluessel, String kennziffer, String laufendenummer) {
        this.broker = broker;
        this.strassenschluessel = strassenschluessel;
        try {
            log.debug("parsing String: " + kennziffer + " to Short");
            this.kennziffer = Short.parseShort(kennziffer);
        } catch (NumberFormatException ex) {
            log.info("Kennziffer is no Short: " + kennziffer, ex);
            this.kennziffer = null;
        }
        try {
            log.debug("parsing String: " + laufendenummer + " to Short");
            this.laufendenummer = Short.parseShort(laufendenummer);
        } catch (NumberFormatException ex) {
            log.info("Kennziffer is no Short: " + laufendenummer, ex);
            this.laufendenummer = null;
        }
    }

    public RetrieveWorker(BelisBroker broker, BoundingBox boundingBox) {
        this.broker = broker;
        this.boundingBox = boundingBox;
    }

    protected Set<BaseEntity> doInBackground() throws Exception {
        if (boundingBox != null) {
            return broker.search(boundingBox);
        } else {
            log.debug("Strassenschluessel: " + strassenschluessel);
            return broker.search(strassenschluessel, kennziffer, laufendenummer);
        }
    }

    protected void done() {
        broker.fireSearchFinished();
        if (isCancelled()) {
            log.warn("retrieveWorker is canceled --> nothing to do in method done()");
            return;
        }
//        try {
//                      Set<ObjectKey> result = get();
//                      if(result != null){
//                          log.debug("result != null. result size: "+result.size());
//                      } else {
//                          log.debug("result ==  null");
//                      }
//                    if (entities.size() > 0) {
//                        fireIntervalAdded(this, entities.size() - 1, entities.size() - 1);
//                        setSelectedItem(entities.get(0));
//                    }
//                    if (Strassenschluessel.class.isAssignableFrom(modelType)) {
//                        log.debug("StrassenschluesselComboBox");
//
//                    } else if (Kennziffer.class.isAssignableFrom(modelType)) {
//                        log.debug("KennzifferComboBox");
//                    } else {
//                        log.debug("Unbekannte Klasse");
        try {
            log.debug("Ergebniss: " + get());
        } catch (Exception ex) {
            log.error("Failure during processing RetrieveWorker results", ex);
            return;
        }
//                    }
//        } catch (Exception ex) {
//            log.error("Failure during processing RetrieveWorker results", ex);
//            return;
//        }
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    public BelisBroker getBroker() {
        return broker;
    }

    public Short getKennziffer() {
        return kennziffer;
    }

    public Short getLaufendenummer() {
        return laufendenummer;
    }

    public String getStrassenschluessel() {
        return strassenschluessel;
    }
}
