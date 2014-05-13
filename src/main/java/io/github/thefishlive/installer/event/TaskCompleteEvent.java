package io.github.thefishlive.installer.event;

import io.github.thefishlive.installer.task.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class TaskCompleteEvent extends Event {

    @Getter
    private Task task;
    @Getter
    private Result result;

}
