package io.github.thefishlive.installer.download;

import io.github.thefishlive.installer.Installer;
import io.github.thefishlive.installer.event.TaskCompleteEvent;
import io.github.thefishlive.installer.event.TaskExecuteEvent;
import io.github.thefishlive.installer.event.Event.Result;
import io.github.thefishlive.installer.exception.InstallerException;
import io.github.thefishlive.installer.log.InstallerLogger;

import java.util.concurrent.CountDownLatch;

public class DownloadWorker implements Runnable {

	private CountDownLatch latch;
	private Download download;
	private Installer installer;

	public DownloadWorker(CountDownLatch latch, Installer installer, Download download) {
		this.latch = latch;
		this.download = download;
		this.installer = installer;
	}
	
	@Override
	public void run() {
		InstallerLogger.getLog().debug("Downloading " + download.getFileDest().getName() + " (" + download.getDownloadUrl() + ")");
		InstallerLogger.getLog().trace("Remote: " + download.getDownloadUrl().toString());
		InstallerLogger.getLog().trace("Local:  " + download.getFileDest().getAbsolutePath());
		
		installer.getBus().post(new TaskExecuteEvent(download, installer));
		
		try {
			download.perform(installer);
			installer.getBus().post(new TaskCompleteEvent(download, Result.SUCCESS));
			InstallerLogger.getLog().debug("Download complete for " + download.getFileDest().getName());
			latch.countDown();
		} catch (InstallerException ex) {
			installer.getBus().post(new TaskCompleteEvent(download, Result.FAIL));
			//throw ex;
		}
	}

}
