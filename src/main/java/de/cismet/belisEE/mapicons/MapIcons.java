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
package de.cismet.belisEE.mapicons;

import java.awt.Color;
import java.awt.Image;

import java.io.Serializable;

import de.cismet.cismap.commons.tools.IconUtils;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public class MapIcons implements Serializable {

    //~ Static fields/initializers ---------------------------------------------

    public static transient Image icoMast = new javax.swing.ImageIcon(MapIcons.class.getResource(
                "/de/cismet/belisEE/mapicons/mast.png")).getImage();
    public static transient Image icoMastSelected = IconUtils.changeImageColor(icoMast, Color.BLUE, 1.0f);
    public static transient Image icoMastWithLeuchte = new javax.swing.ImageIcon(MapIcons.class.getResource(
                "/de/cismet/belisEE/mapicons/mastWithLeuchte.png")).getImage();
    public static transient Image icoMastWithLeuchteSelected = IconUtils.changeImageColor(
            icoMastWithLeuchte,
            Color.BLUE,
            1.0f);
    public static transient Image icoLeuchte = new javax.swing.ImageIcon(MapIcons.class.getResource(
                "/de/cismet/belisEE/mapicons/leuchte.png")).getImage();
    public static transient Image icoLeuchteSelected = IconUtils.changeImageColor(icoLeuchte, Color.BLUE, 1.0f);
    public static transient Image icoSchaltstelle = new javax.swing.ImageIcon(MapIcons.class.getResource(
                "/de/cismet/belisEE/mapicons/schaltstelle.png")).getImage();
    public static transient Image icoAbzweigdose = new javax.swing.ImageIcon(MapIcons.class.getResource(
                "/de/cismet/belisEE/mapicons/abzweigdose.png")).getImage();
    public static transient Image icoSchaltstelleSelected = IconUtils.changeImageColor(
            icoSchaltstelle,
            Color.BLUE,
            1.0f);
    public static transient Image icoMauerlasche = new javax.swing.ImageIcon(MapIcons.class.getResource(
                "/de/cismet/belisEE/mapicons/mauerlasche.png")).getImage();
    public static transient Image icoMauerlascheSelected = IconUtils.changeImageColor(icoMauerlasche, Color.BLUE, 1.0f);
}
