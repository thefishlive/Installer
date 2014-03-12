package io.github.thefishlive.installer.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import io.github.thefishlive.installer.Installer;
import io.github.thefishlive.installer.task.Task;

@ToString
@AllArgsConstructor
public class TaskExecuteEvent extends Event {

	@Getter private Task task;
	@Getter private Installer installer;
	
}
