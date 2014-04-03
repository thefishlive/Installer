package io.github.thefishlive.installer.download;

import io.github.thefishlive.installer.Installer;
import io.github.thefishlive.installer.exception.InstallerException;
import io.github.thefishlive.installer.task.Task;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import lombok.Getter;

import com.google.common.io.ByteStreams;

public abstract class Download extends Task implements Closeable, Cloneable {

	@Getter private int filesize;
	
	private URL checksumUrl;
	private InputStream download;
	private OutputStream local;
	protected boolean active = true;

	public Download() {
		super("download");
	}
	
	public abstract URL getDownloadUrl();
	
	public abstract File getFileDest();

	public abstract Download clone();
	
	public URL getChecksumUrl() throws IOException {
		if (checksumUrl == null) {
			checksumUrl = URI.create(getDownloadUrl().toExternalForm() + ".sha1").toURL();
		}
		
		return checksumUrl;
	}
	
	public boolean setup() throws InstallerException {
		URL url = getDownloadUrl();
		File dest = getFileDest();
		
		if (dest.exists()) {
			dest.delete();
		}
		
		try {
			URLConnection con = url.openConnection();
			
			download = con.getInputStream();
			filesize = con.getContentLength();
			local = new FileOutputStream(dest);
		} catch (IOException e) {
			active = false;
			throw new InstallerException(e);
		}
		
		active = true;
		return active;
	}
	
	@Override
	public boolean perform(Installer install) throws InstallerException {
		if (!active || download == null ) {
			return true;
		}
		
		try {
			ByteStreams.copy(download, local);
		} catch (IOException e) {
			throw new InstallerException(e);
		}
		
		return true;
	}
	
	@Override
	public void close() throws IOException {
		download.close();
		local.flush();
		local.close();
	}
	
}
