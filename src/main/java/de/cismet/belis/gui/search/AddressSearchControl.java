/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.belis.gui.search;

import de.cismet.belis.broker.BelisBroker;
import de.cismet.belis.todo.RetrieveWorker;
import de.cismet.belis.util.BelisIcons;
import de.cismet.belisEE.util.EntityComparator;
import de.cismet.belisEE.util.LeuchteComparator;
import de.cismet.cismap.commons.BoundingBox;
import de.cismet.cismap.commons.wfsforms.WFSFormAdress;
import de.cismet.cismap.commons.wfsforms.WFSFormAdressListener;
import de.cismet.cismap.commons.wfsforms.WFSFormQuery;
import de.cismet.commons.architecture.util.ArchitectureUtils;
import de.cismet.tools.configuration.Configurable;
import de.cismet.tools.configuration.NoWriteError;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.Vector;
import org.apache.commons.collections.comparators.ReverseComparator;
import org.jdom.Element;

/**
 *
 * @author spuhl
 */
public class AddressSearchControl extends WFSFormAdress implements Configurable, SearchControl, WFSFormAdressListener {

    final ArrayList<AddressChangedListener> addressListener = new ArrayList<AddressChangedListener>();
    protected static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(AddressSearchControl.class);
    private BelisBroker broker;

    public AddressSearchControl(final BelisBroker broker) {
        super();
        this.broker = broker;
        this.setOpaque(false);
        panEmpty.setOpaque(false);
        panNr.setOpaque(false);
        panStr.setOpaque(false);
        chkVisualize.setSelected(false);
        chkVisualize.setVisible(false);
        chkLockScale.setSelected(false);
        chkLockScale.setVisible(false);
        jLabel1.setVisible(false);
        jLabel2.setVisible(false);
        cmdOk.setText("");
        cmdOk.setBorder(null);
        cmdOk.setBorderPainted(false);
        //cmdOk.setSize(23, 23);
        cmdOk.setPreferredSize(new Dimension(225, 23));
        cmdOk.setFocusable(false);
        GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        add(cmdOk, gridBagConstraints);        
        cmdOk.setFont(new java.awt.Font("DejaVu Sans", 1, 13)); // NOI18N
        cmdOk.setText("Straße, Hausnummer");       
        cmdOk.setIcon(BelisIcons.icoSearch22);
        addWFSFormAddressListner(this);
//        cmdOk.addActionListener(new ActionListener() {
//
//            //ToDo start search via BelisBroker
//            public void actionPerformed(ActionEvent e) {
//                log.debug("AddressSearch started --> searching db for geometries");
//                broker.fireSearchStarted();
//                broker.execute(new RetrieveWorker());
//            }
//        });

    }

//    public void addAddressChangedListener(AddressChangedListener listener) {
//        addressListener.add(listener);
//    }
//
//    public void removeAddressChangedListener(AddressChangedListener listener) {
//        addressListener.remove(listener);
//    }
    public void configure(Element parent) {
    }

    public Element getConfiguration() throws NoWriteError {
        return null;
    }

    public void masterConfigure(Element parent) {
        log.info("Address Search Panel wird konfiguriert");
        try {
            Element searchConf = parent.getChild("ApplicationSpecificConfiguration").getChild("SearchPanel").getChild("AddressSearch");
            Vector<WFSFormQuery> queryVector = new Vector<WFSFormQuery>();
            List queries = searchConf.getChildren("Query");
            for (Object oq : queries) {
                Element q = (Element) oq;
                WFSFormQuery query = new WFSFormQuery();
                query.setComponentName(q.getAttribute("componentName").getValue());
                query.setDisplayTextProperty(q.getAttribute("displayTextProperty").getValue());
                query.setExtentProperty(q.getAttribute("extentProperty").getValue());
                query.setFilename(q.getAttribute("queryFile").getValue());
                query.setWfsQueryString(readFileFromClassPathAsString(query.getFilename()));
                query.setId(q.getAttribute("id").getValue());
                query.setIdProperty(q.getAttribute("idProperty").getValue());
                query.setServerUrl(q.getAttribute("server").getValue());
                query.setTitle(q.getAttribute("title").getValue());
                query.setType(q.getAttribute("type").getValue());
                try {
                    query.setPropertyPrefix(q.getAttribute("propertyPrefix").getValue());
                } catch (Exception skip) {
                    query.setPropertyPrefix(null);
                }
                try {
                    query.setPropertyNamespace(q.getAttribute("propertyNamespace").getValue());
                } catch (Exception skip) {
                    query.setPropertyNamespace(null);
                }
                try {
                    query.setPositionProperty(q.getAttribute("positionProperty").getValue());
                } catch (Exception skip) {
                    query.setPositionProperty(null);
                }


                //optional
                if (q.getAttribute("queryPlaceholder") != null) {
                    query.setQueryPlaceholder(q.getAttribute("queryPlaceholder").getValue());
                }
                queryVector.add(query);
            }
            setQueries(queryVector);

        } catch (Exception ex) {
            log.error("Fehler beim einlesen der Address Abfragen (Straße/Hausnummer)", ex);
        }
    }

    //ToDo util of Broker ??
    private String readFileFromClassPathAsString(String filePath)
            throws java.io.IOException {
        InputStream is = getClass().getResourceAsStream(filePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuffer fileData = new StringBuffer(1000);
        char[] buf = new char[1024];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();
        return fileData.toString();
    }

    public void searchFinished() {
        setSearchEnabled(true);
    }

    public void searchStarted() {
        setSearchEnabled(false);
    }

    //ToDo Inferface Enablelable or something
    public void setSearchEnabled(boolean isSearchEnabled) {
        ArchitectureUtils.enableContainerRecursivley(panEmpty, isSearchEnabled);
        ArchitectureUtils.enableContainerRecursivley(panNr, isSearchEnabled);
        ArchitectureUtils.enableContainerRecursivley(panStr, isSearchEnabled);
        chkVisualize.setEnabled(isSearchEnabled);
        chkLockScale.setEnabled(isSearchEnabled);
        cmdOk.setEnabled(isSearchEnabled);
    }
//    //ToDo redundant code other searchpanels
//    class RetrieveWorker extends SwingWorker<Set, Void> {
//
//        private BoundingBox searchBB;
//
//        RetrieveWorker(BoundingBox searchBB) {
//            this.searchBB = searchBB;
//        }
//
//        protected Set doInBackground() throws Exception {
//            //return ((BelisBroker)BrokerLookup.getInstance().getBrokerForName(brokerName)).search();
//            //ToDo make proper
//            //return EJBroker.getInstance().getObjectsByBoundingBox("LINESTRING("+((BelisBroker)BrokerLookup.getInstance().getBrokerForName(brokerName)).getMappingComponent().getCurrentBoundingBox()+")");
//            //return broker.search(broker.getMappingComponent().getCurrentBoundingBox());
//            return broker.search(searchBB);
//        }
//
//        protected void done() {
//            broker.fireSearchFinished();
//            if (isCancelled()) {
//                log.warn("retrieveWorker is canceled --> nothing to do in method done()");
//                return;
//            }
//            try {
//                log.debug("Ergebniss: " + get());
//            } catch (Exception ex) {
//                log.error("Failure during processing RetrieveWorker results", ex);
//                return;
//            }
//        }
//    }

    public void wfsFormAddressPositioned(BoundingBox addressBB) {
        log.debug("AddressSearch started --> searching db for geometries boundingbox: "+addressBB);
        broker.fireSearchStarted();
        broker.setCurrentSearchResults(new TreeSet(new ReverseComparator(new EntityComparator(new ReverseComparator(new LeuchteComparator())))));
        broker.setLastSearch(new RetrieveWorker(broker,addressBB));
        broker.execute(new RetrieveWorker(broker,addressBB));
    }

    public void wfsFormAdressNrSelected() {
    }

    public void wfsFormAdressStreetSelected() {
        
    }
}
