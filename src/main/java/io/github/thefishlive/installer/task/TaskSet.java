package io.github.thefishlive.installer.task;

import io.github.thefishlive.installer.Installer;
import io.github.thefishlive.installer.PhaseAction;
import io.github.thefishlive.installer.event.TaskCompleteEvent;
import io.github.thefishlive.installer.event.TaskExecuteEvent;
import io.github.thefishlive.installer.event.Event.Result;
import io.github.thefishlive.installer.exception.InstallerException;
import io.github.thefishlive.installer.log.InstallerLogger;

import java.util.AbstractSet;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import lombok.ToString;

@ToString
public class TaskSet extends AbstractSet<Task> implements PhaseAction<Task> {

	private Set<Task> tasks = null;
	
	public TaskSet() {
		tasks = new HashSet<>();
	}
	
	public boolean perform(Installer installer) throws InstallerException {
		Iterator<Task> itr = iterator();
		
		while(itr.hasNext()) {
			Task task = itr.next();
			installer.getBus().post(new TaskExecuteEvent(task, installer));
			InstallerLogger.getLog().debug("Executing " + task.getName());
			
			if (!task.perform(installer)) {
				installer.getBus().post(new TaskCompleteEvent(task, Result.FAIL));
				return false;
			}
			installer.getBus().post(new TaskCompleteEvent(task, Result.SUCCESS));
		}
		
		return true;
	}
	
	@Override
	public int tasks() {
		return size();
	}

	@Override
	public Iterator<Task> iterator() {
		Task[] tasks = this.tasks.toArray(new Task[size()]);
		Arrays.sort(tasks);
		return new TaskIterator(tasks);
	}

	@Override
	public int size() {
		return tasks.size();
	}
	
	@Override
	public boolean add(Task task) {
		return tasks.add(task);
	}

	public class TaskIterator implements Iterator<Task> {

		private int pos;
		private Task[] tasks;
		
		public TaskIterator(Task[] tasks) {
			this.tasks = tasks;
		}
		
		@Override
		public boolean hasNext() {
			return pos < tasks.length;
		}

		@Override
		public Task next() {
			return tasks[pos++];
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Cannot remove task whilst iterating");
		}
		
	}

}
