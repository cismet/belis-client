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
package de.cismet.belis.todo;

import java.util.Set;

import javax.swing.SwingWorker;

import de.cismet.belis.broker.BelisBroker;

import de.cismet.belisEE.bean.interfaces.ObjectKey;

import de.cismet.cismap.commons.BoundingBox;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public class RetrieveWorker extends SwingWorker<Set<BaseEntity>, Void> {

    //~ Instance fields --------------------------------------------------------

    private final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(RetrieveWorker.class);
    private String strassenschluessel;
    private Short kennziffer;
    private Short laufendenummer;
    private BoundingBox boundingBox;
    private BelisBroker broker;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new RetrieveWorker object.
     *
     * @param  broker       DOCUMENT ME!
     * @param  boundingBox  DOCUMENT ME!
     */
    public RetrieveWorker(final BelisBroker broker, final BoundingBox boundingBox) {
        this.broker = broker;
        this.boundingBox = boundingBox;
    }

    /**
     * Creates a new RetrieveWorker object.
     *
     * @param  broker              DOCUMENT ME!
     * @param  strassenschluessel  DOCUMENT ME!
     * @param  kennziffer          DOCUMENT ME!
     * @param  laufendenummer      DOCUMENT ME!
     */
    public RetrieveWorker(final BelisBroker broker,
            final String strassenschluessel,
            final String kennziffer,
            final String laufendenummer) {
        this.broker = broker;
        this.strassenschluessel = strassenschluessel;
        try {
            if (log.isDebugEnabled()) {
                log.debug("parsing String: " + kennziffer + " to Short");
            }
            this.kennziffer = Short.parseShort(kennziffer);
        } catch (NumberFormatException ex) {
            log.info("Kennziffer is no Short: " + kennziffer, ex);
            this.kennziffer = null;
        }
        try {
            if (log.isDebugEnabled()) {
                log.debug("parsing String: " + laufendenummer + " to Short");
            }
            this.laufendenummer = Short.parseShort(laufendenummer);
        } catch (NumberFormatException ex) {
            log.info("Kennziffer is no Short: " + laufendenummer, ex);
            this.laufendenummer = null;
        }
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    protected Set<BaseEntity> doInBackground() throws Exception {
        if (boundingBox != null) {
            return broker.search(boundingBox);
        } else {
            if (log.isDebugEnabled()) {
                log.debug("Strassenschluessel: " + strassenschluessel);
            }
            return broker.search(strassenschluessel, kennziffer, laufendenummer);
        }
    }

    @Override
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
            if (log.isDebugEnabled()) {
                log.debug("Ergebniss: " + get());
            }
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

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public BelisBroker getBroker() {
        return broker;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Short getKennziffer() {
        return kennziffer;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Short getLaufendenummer() {
        return laufendenummer;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getStrassenschluessel() {
        return strassenschluessel;
    }
}
