/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.belis.gui.reports;

import Sirius.navigator.connection.SessionManager;

import Sirius.server.middleware.types.MetaObject;
import Sirius.server.middleware.types.MetaObjectNode;

import org.openide.util.Exceptions;

import java.util.ArrayList;
import java.util.Collection;

import de.cismet.belis.broker.CidsBroker;

import de.cismet.belis2.server.search.VeranlassungSearchStatement;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public class ReportingVeranlassung {

    //~ Instance fields --------------------------------------------------------

    String nummer;
    String bezeichnung = null;
    String beschreibung = null;
    String bemerkung = null;
    ArrayList<ReportingPosition> positionen;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ReportingVeranlassung object.
     *
     * @param  veranlassungsKey     DOCUMENT ME!
     * @param  reportingPositionen  DOCUMENT ME!
     */
    public ReportingVeranlassung(final String veranlassungsKey, final Collection reportingPositionen) {
        final int numberPos = ((reportingPositionen != null) ? reportingPositionen.size() : 0);
        if (veranlassungsKey.equals(ReportingArbeitsauftrag.OHNE_VERANLASSUNG)) {
            nummer = "ohne Veranlassung: ";
            bezeichnung = "manuell angelegte Positionen";
        } else {
            try {
                nummer = "Aus Veranlassung " + veranlassungsKey + ": ";
                final VeranlassungSearchStatement veranlassungSearchStatement = new VeranlassungSearchStatement();
                veranlassungSearchStatement.setNummer(veranlassungsKey);
                veranlassungSearchStatement.setActiveObjectsOnly(false);
                final Collection<MetaObjectNode> c = CidsBroker.getInstance()
                            .executeServerSearch(veranlassungSearchStatement);
                if ((c != null) && (c.size() == 1)) {
                    final MetaObjectNode mon = (MetaObjectNode)c.toArray()[0];
                    final MetaObject metaObject = SessionManager.getProxy()
                                .getMetaObject(mon.getObjectId(),
                                    mon.getClassId(),
                                    mon.getDomain());
                    bezeichnung = String.valueOf(metaObject.getBean().getProperty("bezeichnung"));
                    beschreibung = String.valueOf(metaObject.getBean().getProperty("beschreibung"));
                    bemerkung = String.valueOf(metaObject.getBean().getProperty("bemerkungen"));
                }
            } catch (Exception ex) {
                Exceptions.printStackTrace(ex);
            }
        }

        nummer += numberPos + ((numberPos == 1) ? " Position" : " Positionen");
        positionen = new ArrayList<ReportingPosition>(reportingPositionen);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getNummer() {
        return nummer;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  nummer  DOCUMENT ME!
     */
    public void setNummer(final String nummer) {
        this.nummer = nummer;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getBezeichnung() {
        return bezeichnung;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  bezeichnung  DOCUMENT ME!
     */
    public void setBezeichnung(final String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getBeschreibung() {
        return beschreibung;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  beschreibung  DOCUMENT ME!
     */
    public void setBeschreibung(final String beschreibung) {
        this.beschreibung = beschreibung;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getBemerkung() {
        return bemerkung;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  bemerkung  DOCUMENT ME!
     */
    public void setBemerkung(final String bemerkung) {
        this.bemerkung = bemerkung;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public ArrayList<ReportingPosition> getPositionen() {
        return positionen;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  positionen  DOCUMENT ME!
     */
    public void setPositionen(final ArrayList<ReportingPosition> positionen) {
        this.positionen = positionen;
    }
}
