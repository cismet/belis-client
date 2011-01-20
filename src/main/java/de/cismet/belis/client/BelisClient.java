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
package de.cismet.belis.client;

import Sirius.navigator.plugin.context.PluginContext;
import Sirius.navigator.plugin.interfaces.PluginMethod;
import Sirius.navigator.plugin.interfaces.PluginProperties;
import Sirius.navigator.plugin.interfaces.PluginUI;

import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;

import org.jdom.Element;

import java.awt.EventQueue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JComponent;

import de.cismet.belis.util.BelisIcons;

import de.cismet.commons.architecture.plugin.AbstractPlugin;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
public class BelisClient extends AbstractPlugin {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(BelisClient.class);

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new BelisClient object.
     */
    public BelisClient() {
        super(null);
        // ToDo move to pluginArchitectureCommons
        this.setIconImage(BelisIcons.applicationIcon.getImage());
    }

    /**
     * Creates a new BelisClient object.
     *
     * @param  context  DOCUMENT ME!
     */
    public BelisClient(final PluginContext context) {
        super(context);
        this.setIconImage(BelisIcons.applicationIcon.getImage());
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public String getPluginConfigurationFile() {
        return "defaultBelisConfiguration.xml";
    }

    /**
     * DOCUMENT ME!
     *
     * @param  args  the command line arguments
     */
    public static void main(final String[] args) {
        try {
            final Plastic3DLookAndFeel lf = new Plastic3DLookAndFeel();
            // TODO NACH MESSE
            // Plastic3DLookAndFeel lf = new Plastic3DLookAndFeel();
            // lf.set3DEnabled(true);
            javax.swing.UIManager.setLookAndFeel(lf);
        } catch (Exception ex) {
            log.error("Fehler beim setzen des Look & Feels");
        }
        final Thread t = new Thread() {

                @Override
                public void run() {
                    final BelisClient bc = new BelisClient();
                    ;
                    EventQueue.invokeLater(new Runnable() {

                            @Override
                            public void run() {
                                // ((JFrame) bc).setVisible(true);
                            }
                        });
                }
            };
        t.setPriority(Thread.NORM_PRIORITY);
        t.start();

//        java.awt.EventQueue.invokeLater(new Runnable() {
//
//            public void run() {
////                //ToDo should be done by LayoutManager --> bug workaround
//                try {
//                    Plastic3DLookAndFeel lf = new Plastic3DLookAndFeel();
//                    //TODO NACH MESSE
//                    //Plastic3DLookAndFeel lf = new Plastic3DLookAndFeel();
//                    //lf.set3DEnabled(true);
//                    javax.swing.UIManager.setLookAndFeel(lf);
//                } catch (Exception ex) {
//                    log.error("Fehler beim setzen des Look & Feels");
//                }
//                new BelisClient();
//            }
//        });
    }

    @Override
    public void masterConfigure(final Element parent) {
        super.masterConfigure(parent);
    }

    @Override
    public PluginUI getUI(final String id) {
        return this;
    }

    @Override
    public Iterator getUIs() {
        final LinkedList ll = new LinkedList();
        ll.add(this);
        return ll.iterator();
    }

    @Override
    public Collection getButtons() {
        return Arrays.asList(broker.getToolbar().getComponents());
    }
}
