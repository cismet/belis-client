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

import de.cismet.cids.custom.beans.belis2.ArbeitsprotokollCustomBean;

import de.cismet.commons.server.entity.GeoBaseEntity;

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
    public ReportingPosition(final int nummer, final ArbeitsprotokollCustomBean protokoll) {
        final GeoBaseEntity entity = protokoll.getChildEntity();
        name = "Position " + String.valueOf(nummer) + ": ";
        if (entity != null) {
            name += entity;
        }

        final Collection<AbstractArbeitsprotokollWizard> wizardActions = BelisBroker.getInstance()
                    .getWizardsActionsForEntity(protokoll.getChildType());
        if (wizardActions != null) {
            for (final AbstractArbeitsprotokollWizard action : wizardActions) {
                moeglicheAktionen.add(new ReportingAction(String.valueOf(action.getAction().getValue(Action.NAME))));
            }
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
