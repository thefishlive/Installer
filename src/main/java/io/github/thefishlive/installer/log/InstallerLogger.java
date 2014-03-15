package io.github.thefishlive.installer.log;

import io.github.thefishlive.installer.InstallerUtils;
import lombok.Getter;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class InstallerLogger extends Logger {

	@Getter private static final Logger infolog = Logger.getLogger("installer");
	@Getter private static final Logger userlog = Logger.getLogger("installer.user");
	@Getter private static final Logger debuglog = Logger.getLogger("installer.debug");
	@Getter private static final Logger errorlog = Logger.getLogger("error");
	
	static {
		if (InstallerUtils.isDebug()) {
			debuglog.setLevel(Level.INFO);
		}
	}
	
	@Getter private static final InstallerLogger log = new InstallerLogger("installer");
	
	public InstallerLogger(String name) {
		super(name);
	}

	@Override
	public void debug(Object message) {
		debuglog.debug(message);
	}

	@Override
	public void info(Object message) {
		infolog.info(message);
	}

	public void user(Object message) {
		userlog.info(message);
	}

	@Override
	public void warn(Object message) {
		errorlog.warn(message);
	}
	
	@Override
	public void error(Object message) {
		errorlog.error(message);
	}

	@Override
	public void fatal(Object message) {
		errorlog.fatal(message);
	}

	@Override
	public void debug(Object message, Throwable t) {
		debuglog.debug(message, t);
	}

	@Override
	public void info(Object message, Throwable t) {
		infolog.info(message, t);
	}

	public void user(Object message, Throwable t) {
		userlog.info(message, t);
	}
	
	@Override
	public void warn(Object message, Throwable t) {
		errorlog.warn(message, t);
	}

	@Override
	public void error(Object message, Throwable t) {
		errorlog.error(message, t);
	}

	@Override
	public void fatal(Object message, Throwable t) {
		errorlog.fatal(message, t);
	}

}
