package io.github.thefishlive.installer;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class InstallerUtils {

	private InstallerUtils() {}
	
	public static File getAppData() {
		String userHomeDir = System.getProperty("user.home", ".");
		String osType = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
		
		if (osType.contains("win") && System.getenv("APPDATA") != null) {
			return new File(System.getenv("APPDATA"));
		} else if (osType.contains("mac")) {
			return new File(new File(userHomeDir, "Library"), "Application Support");
		} else {
			return new File(userHomeDir);
		}
	}

	public static void delete(File file) throws IOException {
		if (file.isDirectory()) {
			if (file.list().length == 0) {

				file.delete();
			} else {
				String files[] = file.list();

				for (String temp : files) {
					File fileDelete = new File(file, temp);
					delete(fileDelete);
				}
				
				if (file.list().length == 0) {
					file.delete();
				}
			}
		} else {
			file.delete();
		}
	}
}
