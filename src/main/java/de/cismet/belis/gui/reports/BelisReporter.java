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

import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JRViewer;

import java.awt.EventQueue;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;

import de.cismet.belis.broker.BelisBroker;
import de.cismet.belis.broker.CidsBroker;

import de.cismet.cids.client.tools.DevelopmentTools;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.gui.layerwidget.ActiveLayerModel;
import de.cismet.cismap.commons.gui.printing.JasperDownload;
import de.cismet.cismap.commons.interaction.CismapBroker;

import de.cismet.tools.configuration.ConfigurationManager;

import de.cismet.tools.gui.downloadmanager.DownloadManagerDialog;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public class BelisReporter {

    //~ Static fields/initializers ---------------------------------------------

    private static final transient org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(BelisReporter.class);

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param   args  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static void main(final String[] args) throws Exception {
        try {
            javax.swing.UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
            final ConfigurationManager configManager = new ConfigurationManager();

            configManager.setFolder(".belis2");
            configManager.setFileName("configurationPlugin.xml");
            configManager.setDefaultFileName("defaultCismapProperties.xml");
            final MappingComponent mapC = new MappingComponent(true);
            mapC.setMappingModel(new ActiveLayerModel());
            CismapBroker.getInstance().setMappingComponent(mapC);
            configManager.configure(mapC);

            final CidsBean[] beans = DevelopmentTools.createCidsBeansFromRMIConnectionOnLocalhost(
                    "BELIS2",
                    "Bearbeiter",
                    "WendlingM",
                    "kif",
                    "arbeitsauftrag",
                    "id in (18,21)",
                    2);
            System.out.println("geladen");
//        System.out.println(beans[0].getMOString());
            System.out.println(beans[0].getProperty("angelegt_am"));
            System.out.println("---------");
            BelisBroker.getInstance().lookupProtokollWizards();
            CidsBroker.getInstance().setProxy(SessionManager.getProxy());

            final ReportingArbeitsauftrag ra = new ReportingArbeitsauftrag();
            ra.init(beans[0]);
            final ReportingArbeitsauftrag ra2 = new ReportingArbeitsauftrag();
            ra2.init(beans[1]);
//        List l=(List)ra.getPositionenNachVeranlassung().get(ReportingArbeitsauftrag.OHNE_VERANLASSUNG);
            final List l = (List)ra.getVeranlassungen();
            final ArrayList a = new ArrayList<Object>();
            a.add(ra);
            a.add(ra2);
            final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(a);

            final HashMap parameters = new HashMap();

            final JasperReport jasperReport;
            final JasperPrint jasperPrint;
            try {
//            jasperReport = (JasperReport) JRLoader.loadObject(BelisReporter.class.getResourceAsStream(
//                    "/de/cismet/cids/custom/reports/wunda_blau/mauer-katasterblatt.jasper"));

                jasperReport = (JasperReport)JRLoader.loadObject(new File(
                            "/home/jruiz/git/belis-client/src/main/resources/de/cismet/belis/reports/arbeitsauftraege.jasper"));

                jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

                EventQueue.invokeLater(new Runnable() {

                        @Override
                        public void run() {
                            final JRViewer aViewer = new JRViewer(jasperPrint);
                            final JFrame aFrame = new JFrame("Druckvorschau");
                            aFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            aFrame.getContentPane().add(aViewer);
                            final java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
                            aFrame.setSize((int)(screenSize.width * 0.8), (int)(screenSize.height * 0.9f));
                            final java.awt.Insets insets = aFrame.getInsets();
                            aFrame.setSize(
                                aFrame.getWidth()
                                        + insets.left
                                        + insets.right,
                                aFrame.getHeight()
                                        + insets.top
                                        + insets.bottom
                                        + 20);
                            aFrame.setLocation(
                                (screenSize.width - aFrame.getWidth())
                                        / 2,
                                (screenSize.height - aFrame.getHeight())
                                        / 2);
                            aViewer.setFitPageZoomRatio();
                            aFrame.setVisible(true);
                        }
                    });
            } catch (JRException ex) {
                ex.printStackTrace();

                LOG.error("Could not generate katasterblatt report for mauern.", ex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   arbeitsauftraege  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static JasperDownload getArbeitsauftragsReport(final List<CidsBean> arbeitsauftraege) throws Exception {
        final ArrayList<ReportingArbeitsauftrag> repAuftraege = new ArrayList<ReportingArbeitsauftrag>(
                arbeitsauftraege.size());
        for (final CidsBean aa : arbeitsauftraege) {
            final ReportingArbeitsauftrag ra = new ReportingArbeitsauftrag();
            ra.init(aa);
            repAuftraege.add(ra);
        }
        final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(repAuftraege);
        final HashMap parameters = new HashMap();
        final JasperReport jasperReport;
        final JasperPrint jasperPrint;
        jasperReport = (JasperReport)JRLoader.loadObject(BelisReporter.class.getResourceAsStream(
                    "/de/cismet/belis/reports/arbeitsauftraege.jasper"));
        jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        final String jobname = DownloadManagerDialog.getJobname();
        return new JasperDownload(jasperPrint, jobname, "Belis-AA", "belis.aa");
    }
}
