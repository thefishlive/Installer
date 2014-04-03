package io.github.thefishlive.installer.download;

import java.io.File;
import java.net.URL;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class SimpleDownload extends Download {

	@Getter private URL downloadUrl;
	@Getter private File fileDest;

	@Override
	public Download clone() {
		Download download = new SimpleDownload(this.downloadUrl, this.fileDest);
		download.active = this.active;
		return download;
	}
}
