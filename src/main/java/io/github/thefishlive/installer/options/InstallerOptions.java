package io.github.thefishlive.installer.options;

import lombok.Getter;
import lombok.Setter;

public class InstallerOptions {

    @Getter
    @Setter
    private static boolean debug = false;
    @Getter
    @Setter
    private static boolean threadedDownloads = false;

}
