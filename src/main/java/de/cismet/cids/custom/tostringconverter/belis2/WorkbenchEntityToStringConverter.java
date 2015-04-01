/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 *  Copyright (C) 2010 thorsten
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.cismet.cids.custom.tostringconverter.belis2;

import de.cismet.cids.tools.CustomToStringConverter;

import de.cismet.commons.server.entity.WorkbenchEntity;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public class WorkbenchEntityToStringConverter extends CustomToStringConverter {

    //~ Methods ----------------------------------------------------------------

    @Override
    public String createString() {
        final String keyString = ((WorkbenchEntity)cidsBean).getKeyString();
        final String humanReadablePosition = ((WorkbenchEntity)cidsBean).getHumanReadablePosition();
        if (!keyString.isEmpty() || !humanReadablePosition.isEmpty()) {
            return " -" + ((!keyString.isEmpty()) ? (" " + keyString) : "")
                        + ((!humanReadablePosition.isEmpty()) ? (" (" + humanReadablePosition + ")") : "");
        } else {
            return "";
        }
    }
}
