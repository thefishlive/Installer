package io.github.thefishlive.installer.download;

import lombok.Getter;

import java.io.File;
import java.net.URL;

public class InternalDownload extends Download {

    @Getter
    private final URL downloadUrl;
    @Getter
    private final File fileDest;

    public InternalDownload(Download download) {
        this.downloadUrl = download.getDownloadUrl();
        this.fileDest = download.getFileDest();

        this.filesize = download.filesize;
        this.download = download.download;
        this.local = download.local;
        this.active = download.active;
    }

}
