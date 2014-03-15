package io.github.thefishlive.installer.download;

import io.github.thefishlive.installer.Installer;
import io.github.thefishlive.installer.PhaseAction;
import io.github.thefishlive.installer.event.Event.Result;
import io.github.thefishlive.installer.event.TaskCompleteEvent;
import io.github.thefishlive.installer.event.TaskExecuteEvent;
import io.github.thefishlive.installer.exception.InstallerException;
import io.github.thefishlive.installer.log.InstallerLogger;
import io.github.thefishlive.installer.task.Task;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("serial")
public class DownloadSet extends HashSet<Download> implements PhaseAction<Task> {

	private boolean downloaded = false;
	
	@Override
	public boolean perform(Installer installer) throws InstallerException {
		Iterator<Download> itr = iterator();
		
		while(itr.hasNext()) {
			Download download = itr.next();
			download.setup();
		}
		
		itr = iterator();
		
		while(itr.hasNext()) {
			Download download = itr.next();
			InstallerLogger.getLog().debug("Downloading " + download.getFileDest().getName() + " (" + download.getDownloadUrl() + ")");
			installer.getBus().post(new TaskExecuteEvent(download, installer));
			
			try {
				download.perform(installer);
				installer.getBus().post(new TaskCompleteEvent(download, Result.SUCCESS));
			} catch (InstallerException ex) {
				installer.getBus().post(new TaskCompleteEvent(download, Result.FAIL));
				throw ex;
			}
		}

		downloaded = true;
		
		itr = iterator();
		
		while(itr.hasNext()) {
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
	
	public List<File> getDownloadedFiles() {
		if (!downloaded) {
			return new ArrayList<File>();
		}
		
		List<File> files = new ArrayList<File>();
		Iterator<Download> itr = iterator();
		
		while(itr.hasNext()) {
			files.add(itr.next().getFileDest());
		}
		
		return files;
		
	}

	@Override
	public int tasks() {
		return size();
	}
}
