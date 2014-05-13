package io.github.thefishlive.installer.download;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;
import java.net.URL;

@AllArgsConstructor
public class SimpleDownload extends Download {

    @Getter
    private URL downloadUrl;
    @Getter
    private File fileDest;

}
