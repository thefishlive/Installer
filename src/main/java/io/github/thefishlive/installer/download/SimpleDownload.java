package io.github.thefishlive.installer.download;

import java.io.File;
import java.net.URL;

import lombok.Getter;

public class SimpleDownload extends Download {

	@Getter private URL downloadUrl;
	@Getter private File fileDest;

	public SimpleDownload(URL url, File dest) {
		this.downloadUrl = url;
		this.fileDest = dest;
	}

}
