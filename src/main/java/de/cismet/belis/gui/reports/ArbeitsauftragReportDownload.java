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

import Sirius.navigator.connection.SessionManager;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import de.cismet.belis2.server.actions.ArbeitsauftragsReportRetrieverAction;

import de.cismet.cids.custom.beans.belis2.ArbeitsauftragCustomBean;


import de.cismet.tools.gui.downloadmanager.AbstractCancellableDownload;
import de.cismet.tools.gui.downloadmanager.DownloadManagerDialog;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class ArbeitsauftragReportDownload extends AbstractCancellableDownload {

    //~ Instance fields --------------------------------------------------------

    private byte[] fileContent;
    private final ArbeitsauftragCustomBean arbeitsauftrag;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ArbeitsauftraegeReportDownload object.
     *
     * @param  arbeitsauftrag  DOCUMENT ME!
     */
    public ArbeitsauftragReportDownload(final ArbeitsauftragCustomBean arbeitsauftrag) {
        this.arbeitsauftrag = arbeitsauftrag;
        final String jobname = DownloadManagerDialog.getJobname();

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
            final Object ret = SessionManager.getProxy()
                        .executeTask(
                            ArbeitsauftragsReportRetrieverAction.TASK_NAME,
                            "BELIS2",
                            arbeitsauftrag.getNummer());

            if (ret instanceof Exception) {
                throw (Exception)ret;
            }

            fileContent = (byte[])ret;
        } catch (final Exception ex) {
            error(ex);
        }

        if (fileContent != null) {
            try {
                if (!Thread.interrupted()) {
                    writeFile();
                } else {
                    log.info("Download was interuppted");
                    deleteFile();
                    return;
                }
            } catch (final FileNotFoundException ex) {
                error(ex);
            } catch (final IOException ex) {
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
     * @throws  FileNotFoundException  DOCUMENT ME!
     * @throws  IOException            DOCUMENT ME!
     */
    protected void writeFile() throws FileNotFoundException, IOException {
        final OutputStream os = new BufferedOutputStream(new FileOutputStream(new File(fileToSaveTo.getPath())));
        os.write(fileContent);
        os.flush();
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
