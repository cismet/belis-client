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

import javax.swing.SwingWorker;

import de.cismet.belis.broker.BelisBroker;

import de.cismet.cismap.commons.BoundingBox;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public class RetrieveWorker extends SwingWorker<Void, Void> {

    //~ Instance fields --------------------------------------------------------

    private final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(RetrieveWorker.class);
    private String strassenschluessel;
    private Integer kennziffer;
    private Integer laufendenummer;
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

    //~ Methods ----------------------------------------------------------------

// /**
// * Creates a new RetrieveWorker object.
// *
// * @param  broker              DOCUMENT ME!
// * @param  strassenschluessel  DOCUMENT ME!
// * @param  kennziffer          DOCUMENT ME!
// * @param  laufendenummer      DOCUMENT ME!
// */
// public RetrieveWorker(final BelisBroker broker,
// final String strassenschluessel,
// final String kennziffer,
// final String laufendenummer) {
// this.broker = broker;
// this.strassenschluessel = strassenschluessel;
// try {
// if (log.isDebugEnabled()) {
// log.debug("parsing String: " + kennziffer + " to Short");
// }
// this.kennziffer = Integer.parseInt(kennziffer);
// } catch (NumberFormatException ex) {
// log.info("Kennziffer is no Short: " + kennziffer, ex);
// this.kennziffer = null;
// }
// try {
// if (log.isDebugEnabled()) {
// log.debug("parsing String: " + laufendenummer + " to Short");
// }
// this.laufendenummer = Integer.parseInt(laufendenummer);
// } catch (NumberFormatException ex) {
// log.info("Kennziffer is no Short: " + laufendenummer, ex);
// this.laufendenummer = null;
// }
// }

    @Override
    protected Void doInBackground() throws Exception {
//        if (boundingBox != null) {
        broker.search(boundingBox);
//        } else {
//            broker.search(strassenschluessel, kennziffer, laufendenummer);
//        }
        return null;
    }

    @Override
    protected void done() {
        broker.fireSearchFinished();
        if (isCancelled()) {
            log.warn("retrieveWorker is canceled --> nothing to do in method done()");
            return;
        }
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
    public Integer getKennziffer() {
        return kennziffer;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Integer getLaufendenummer() {
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
