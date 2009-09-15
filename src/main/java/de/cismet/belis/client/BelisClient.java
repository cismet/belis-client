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
import de.cismet.belis.util.BelisIcons;
import de.cismet.commons.architecture.plugin.AbstractPlugin;
import java.awt.EventQueue;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JComponent;
import org.jdom.Element;

/**
 *
 * @author spuhl
 */
public class BelisClient extends AbstractPlugin {

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(BelisClient.class);

    public BelisClient() {
        super(null);
        //ToDo move to pluginArchitectureCommons
        this.setIconImage(BelisIcons.applicationIcon.getImage());
    }

    public BelisClient(PluginContext context) {
        super(context);
        this.setIconImage(BelisIcons.applicationIcon.getImage());
    }

    @Override
    public String getPluginConfigurationFile() {
        return "defaultBelisConfiguration.xml";
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            Plastic3DLookAndFeel lf = new Plastic3DLookAndFeel();
            //TODO NACH MESSE
            //Plastic3DLookAndFeel lf = new Plastic3DLookAndFeel();
            //lf.set3DEnabled(true);
            javax.swing.UIManager.setLookAndFeel(lf);
        } catch (Exception ex) {
            log.error("Fehler beim setzen des Look & Feels");
        }
        Thread t = new Thread() {

            public void run() {
                final BelisClient bc = new BelisClient();
                ;
                EventQueue.invokeLater(new Runnable() {

                    public void run() {
                        //((JFrame) bc).setVisible(true);
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
    public void masterConfigure(Element parent) {
        super.masterConfigure(parent);

    }
    
    @Override
    public PluginUI getUI(String id) {
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
