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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.Action;

import de.cismet.belis.arbeitsprotokollwizard.AbstractArbeitsprotokollWizard;

import de.cismet.belis.broker.BelisBroker;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.commons.server.entity.BaseEntity;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public class ReportingPosition {

    //~ Instance fields --------------------------------------------------------

    String name;
    List<ReportingAction> moeglicheAktionen = new ArrayList<ReportingAction>();
    List<ReportingStatus> moeglicheStati = new ArrayList<ReportingStatus>();

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ReportingPosition object.
     *
     * @param  nummer     DOCUMENT ME!
     * @param  protokoll  DOCUMENT ME!
     */
    public ReportingPosition(final int nummer, final CidsBean protokoll) {
        CidsBean entity = null;
        name = "Position " + String.valueOf(nummer) + ": ";
        if (protokoll.getProperty("fk_leuchte") != null) {
            name += "Leuchte " + String.valueOf(protokoll.getProperty("fk_leuchte.lfd_nummer")) + ", "
                        + String.valueOf(protokoll.getProperty("fk_leuchte.fk_leuchttyp.leuchtentyp"));
            entity = (CidsBean)protokoll.getProperty("fk_leuchte");
        } else if (protokoll.getProperty("fk_geometrie") != null) {
            name += "Allgemeine Geometrie";
        } else if (protokoll.getProperty("fk_standort") != null) {
            name += "Standort ";
            entity = (CidsBean)protokoll.getProperty("fk_standort");
        } else if (protokoll.getProperty("fk_abzweigdose") != null) {
            name += "Abzweigdose ";
            entity = (CidsBean)protokoll.getProperty("fk_abzweigdose");
        } else if (protokoll.getProperty("fk_leitung") != null) {
            name += "Leitung ";
            entity = (CidsBean)protokoll.getProperty("fk_leitung");
        } else if (protokoll.getProperty("fk_mauerlasche") != null) {
            name += "Mauerlasche ";
            entity = (CidsBean)protokoll.getProperty("fk_leitung");
        } else if (protokoll.getProperty("fk_schaltstelle") != null) {
            name += "Schaltstelle";
            entity = (CidsBean)protokoll.getProperty("fk_schaltstelle");
        }
        final Collection<AbstractArbeitsprotokollWizard> wizardActions = BelisBroker.getInstance()
                    .getWizardsActionsForEntity((BaseEntity)entity);
        for (final AbstractArbeitsprotokollWizard action : wizardActions) {
            moeglicheAktionen.add(new ReportingAction(String.valueOf(action.getAction().getValue(Action.NAME))));
        }
        final Collection<AbstractArbeitsprotokollWizard> defaultActions = BelisBroker.getInstance()
                    .getWizardsActionsForEntity(null);
        for (final AbstractArbeitsprotokollWizard action : defaultActions) {
            moeglicheAktionen.add(new ReportingAction(String.valueOf(action.getAction().getValue(Action.NAME))));
        }
        moeglicheStati.add(new ReportingStatus("offen"));
        moeglicheStati.add(new ReportingStatus("erledigt"));
        moeglicheStati.add(new ReportingStatus("Fehlmeldung"));
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getName() {
        return name;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public List<ReportingAction> getMoeglicheAktionen() {
        return moeglicheAktionen;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public List<ReportingStatus> getMoeglicheStati() {
        return moeglicheStati;
    }
}
