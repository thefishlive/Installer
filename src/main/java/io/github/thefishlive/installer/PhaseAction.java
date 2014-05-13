package io.github.thefishlive.installer;

import io.github.thefishlive.installer.exception.InstallerException;
import io.github.thefishlive.installer.task.Task;

public interface PhaseAction<T extends Task> {

    public boolean perform(Installer installer) throws InstallerException;

    public boolean add(T action);

    public int tasks();

}
