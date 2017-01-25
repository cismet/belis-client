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
package de.cismet.belis.gui.reports;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.cismet.cids.custom.beans.belis2.ArbeitsauftragCustomBean;

import de.cismet.tools.gui.downloadmanager.AbstractCancellableDownload;
import de.cismet.tools.gui.downloadmanager.DownloadManagerDialog;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class ArbeitsauftraegeReportDownload extends AbstractCancellableDownload {

    //~ Instance fields --------------------------------------------------------

    protected JasperPrint jasperPrint;

    private final List<ArbeitsauftragCustomBean> arbeitsauftraege;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ArbeitsauftraegeReportDownload object.
     *
     * @param  arbeitsauftraege  DOCUMENT ME!
     */
    public ArbeitsauftraegeReportDownload(final List<ArbeitsauftragCustomBean> arbeitsauftraege) {
        this.arbeitsauftraege = arbeitsauftraege;
        final String jobname = DownloadManagerDialog.getInstance().getJobName();

        this.directory = jobname;
        this.title = "Belis-AA";

        status = State.WAITING;
        determineDestinationFile("belis.aa", ".pdf");
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void run() {
        if (status != State.WAITING) {
            return;
        }

        status = State.RUNNING;
        stateChanged();

        try {
            final ArrayList<ReportingArbeitsauftrag> repAuftraege = new ArrayList<ReportingArbeitsauftrag>(
                    arbeitsauftraege.size());
            for (final ArbeitsauftragCustomBean aa : arbeitsauftraege) {
                final ReportingArbeitsauftrag ra = new ReportingArbeitsauftrag();
                ra.init(aa);
                repAuftraege.add(ra);
            }
            final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(repAuftraege);
            final HashMap parameters = new HashMap();
            final JasperReport jasperReport;
            jasperReport = (JasperReport)JRLoader.loadObject(BelisReporter.class.getResourceAsStream(
                        "/de/cismet/belis/reports/arbeitsauftraege.jasper"));
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        } catch (JRException ex) {
            error(ex);
        }

        if (jasperPrint != null) {
            try {
                if (!Thread.interrupted()) {
                    exportReportFile();
                } else {
                    log.info("Download was interuppted");
                    deleteFile();
                    return;
                }
            } catch (JRException ex) {
                error(ex);
            }
        }

        if (status == State.RUNNING) {
            status = State.COMPLETED;
            stateChanged();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @throws  JRException  DOCUMENT ME!
     */
    protected void exportReportFile() throws JRException {
        JasperExportManager.exportReportToPdfFile(jasperPrint, fileToSaveTo.getPath());
    }

    /**
     * DOCUMENT ME!
     */
    private void deleteFile() {
        if (fileToSaveTo.exists() && fileToSaveTo.isFile()) {
            fileToSaveTo.delete();
        }
    }
}
