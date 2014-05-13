package io.github.thefishlive.installer.task;

import io.github.thefishlive.installer.Installer;
import io.github.thefishlive.installer.exception.InstallerException;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ToString
public abstract class Task implements Comparable<Task> {

    private List<String> dependencies = new ArrayList<String>();
    @Getter
    private String name;

    public Task(String name) {
        this.name = name;
    }

    public abstract boolean perform(Installer installer) throws InstallerException;

    public Task addDependency(String... task) {
        dependencies.addAll(Arrays.asList(task));
        return this;
    }

    @Override
    public int compareTo(Task task) {
        if (dependencies.contains(task.getName())) {
            return 1;
        } else if (task.dependencies.contains(getName())) {
            return -1;
        }

        return 1;
    }
}
