package io.github.thefishlive.installer.download;

import io.github.thefishlive.installer.Installer;
import io.github.thefishlive.installer.PhaseAction;
import io.github.thefishlive.installer.exception.InstallerException;
import io.github.thefishlive.installer.log.InstallerLogger;
import io.github.thefishlive.installer.options.InstallerOptions;
import io.github.thefishlive.installer.task.Task;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@SuppressWarnings("serial")
public class DownloadSet extends HashSet<Download> implements PhaseAction<Task> {

    private boolean setup = false;
    private boolean downloaded = false;

    public boolean setup() throws InstallerException {
        Iterator<Download> itr = iterator();
        setup = true;

        while (itr.hasNext()) {
            try {
                Download download = itr.next();
                download.setup();
            } catch (InstallerException ex) {
                setup = false;
                throw ex;
            }
        }

        return setup;
    }

    @Override
    public boolean perform(Installer installer) throws InstallerException {

        setup(); // TODO extract to own task maybe

        if (!setup) {
            throw InstallerException.DOWNLOAD_NOT_SETUP; // Should never be called.
        }

        CountDownLatch latch = new CountDownLatch(size());
        InstallerLogger.getLog().debug("Starting downloads (" + size() + ")");
        Iterator<Download> itr = iterator();

        // Profiling
        long starttime = System.currentTimeMillis();

        while (itr.hasNext()) {
            Download download = itr.next();
            DownloadWorker worker = new DownloadWorker(latch, installer, download.clone());

            // Either run parallel downloads or sequential depending on mode selected.
            if (InstallerOptions.isThreadedDownloads()) {
                new Thread(worker, "Download-" + download.getFileDest().getName()).start();
            } else {
                worker.run();
            }
        }

        try {
            latch.await(); // Wait for downloads to be finished;
            InstallerLogger.getLog().debug("All downloads tasks complete");
        } catch (InterruptedException e) {
            throw new InstallerException(e);
        }

        InstallerLogger.getLog().debug("Downloads took " + (System.currentTimeMillis() - starttime) + " milliseconds to download."); // TODO get as low as possible :)
        downloaded = true;
        itr = iterator();

        while (itr.hasNext()) { // cleanup resources left by downloads.
            try {
                Download download = itr.next();
                download.close();
            } catch (IOException ex) {
                throw new InstallerException(ex);
            }
        }

        return true;
    }

    @Override
    public boolean add(Task action) {
        return add((Download) action);
    }

    @Override
    public int tasks() {
        return size();
    }

    public List<File> getDownloadedFiles() {
        if (!downloaded) {
            return new ArrayList<File>();
        }

        List<File> files = new ArrayList<File>();
        Iterator<Download> itr = iterator();

        while (itr.hasNext()) {
            files.add(itr.next().getFileDest());
        }

        return files;

    }
}
