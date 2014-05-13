package io.github.thefishlive.installer.event;

import io.github.thefishlive.installer.InstallPhase;
import io.github.thefishlive.installer.Installer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class PhaseChangeEvent extends Event {

    @Getter
    private InstallPhase newPhase;
    @Getter
    private Installer installer;

}
