package io.github.thefishlive.installer;

import java.util.HashMap;
import java.util.Map;

import com.google.common.eventbus.EventBus;

import lombok.Getter;
import io.github.thefishlive.installer.download.Download;
import io.github.thefishlive.installer.download.DownloadSet;
import io.github.thefishlive.installer.event.PhaseChangeEvent;
import io.github.thefishlive.installer.exception.InstallerException;
import io.github.thefishlive.installer.log.InstallerLogger;
import io.github.thefishlive.installer.task.Task;
import io.github.thefishlive.installer.task.TaskSet;

public abstract class Installer {

	@Getter private Map<InstallPhase, PhaseAction<Task>> tasks = new HashMap<>();
	@Getter private InstallPhase phase;
	@Getter private EventBus bus;
	
	public Installer() {
		bus = new EventBus();
		updatePhase(InstallPhase.SETUP);
		
		tasks.put(InstallPhase.PRE_DOWNLOAD, new TaskSet());
		tasks.put(InstallPhase.DOWNLOAD, new DownloadSet());
		tasks.put(InstallPhase.POST_DOWNLOAD, new TaskSet());
	}
	
	private void updatePhase(InstallPhase phase) {
		this.phase = phase;
		bus.post(new PhaseChangeEvent(phase, this));
		InstallerLogger.getLog().info("Moving to install phase " + phase.name().toLowerCase());
	}
	
	public void addDownload(Download download) {
		if (phase.ordinal() >= InstallPhase.DOWNLOAD.ordinal()) {
			throw new IllegalStateException("Cannot add download after files have been downloaded");
		}
		
		tasks.get(InstallPhase.DOWNLOAD).add(download);
	}
	
	public void addTask(Task task) {
		addTask(InstallPhase.PRE_DOWNLOAD, task);
	}
	
	public void addTask(InstallPhase phase, Task task) {
		tasks.get(phase).add(task);
	}
	
	public boolean perform() throws InstallerException {
		
		for (InstallPhase phase : InstallPhase.values()) {
			if (!tasks.containsKey(phase)) {
				continue;
			}
			
			updatePhase(phase);
			if (!tasks.get(phase).perform(this)) {
				return false;
			}
		}
		
		updatePhase(InstallPhase.COMPLETE);
		return true;
	}

	public PhaseAction<Task> getTasks(InstallPhase phase) {
		return tasks.get(phase);
	}
	
	public int getAmountOfTasks(InstallPhase phase) {
		if (!tasks.containsKey(phase)) {
			return -1;
		}
		
		return tasks.get(phase).tasks();
	}
	
}
