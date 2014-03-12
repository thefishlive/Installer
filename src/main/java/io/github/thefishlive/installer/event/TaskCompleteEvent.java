package io.github.thefishlive.installer.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import io.github.thefishlive.installer.task.Task;

@ToString
@AllArgsConstructor
public class TaskCompleteEvent extends Event {

	@Getter private Task task;
	@Getter private Result result;
	
}
