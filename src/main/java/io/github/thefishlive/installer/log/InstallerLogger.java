package io.github.thefishlive.installer.log;

import lombok.Getter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.spi.AbstractLogger;

@SuppressWarnings("serial")
public class InstallerLogger extends AbstractLogger {

    @Getter
    private static final Logger infolog = LogManager.getLogger("installer");
    @Getter
    private static final Logger userlog = LogManager.getLogger("installer.user");
    @Getter
    private static final Logger debuglog = LogManager.getLogger("installer.debug");
    @Getter
    private static final Logger errorlog = LogManager.getLogger("error");

    @Getter
    private static final InstallerLogger log = new InstallerLogger("installer");

    public InstallerLogger(String name) {
        super(name);
    }

    @Override
    public void debug(Object message) {
        debuglog.debug(message);
    }

    @Override
    public void debug(Object message, Throwable t) {
        debuglog.debug(message, t);
    }

    @Override
    public void error(Object message) {
        errorlog.error(message);
    }

    @Override
    public void error(Object message, Throwable t) {
        errorlog.error(message, t);
    }

    @Override
    public void fatal(Object message) {
        errorlog.fatal(message);
    }

    @Override
    public void fatal(Object message, Throwable t) {
        errorlog.fatal(message, t);
    }

    @Override
    public void info(Object message) {
        infolog.info(message);
    }

    @Override
    public void info(Object message, Throwable t) {
        infolog.info(message, t);
    }

    @Override
    protected boolean isEnabled(Level level, Marker marker, Message data, Throwable t) {
        return isEnabled0(level);
    }

    @Override
    protected boolean isEnabled(Level level, Marker marker, Object data, Throwable t) {
        return isEnabled0(level);
    }

    @Override
    protected boolean isEnabled(Level level, Marker marker, String data) {
        return isEnabled0(level);
    }

    @Override
    protected boolean isEnabled(Level level, Marker marker, String data, Object... p1) {
        return isEnabled0(level);
    }

    @Override
    protected boolean isEnabled(Level level, Marker marker, String data, Throwable t) {
        return isEnabled0(level);
    }

    @Override
    public void log(Marker marker, String fqcn, Level level, Message data, Throwable t) {
        if (level.intLevel() == Level.DEBUG.intLevel() || level.intLevel() == Level.TRACE.intLevel()) {
            debuglog.log(level, marker, data, t);
        } else if (level.intLevel() == Level.INFO.intLevel()) {
            infolog.log(level, marker, data, t);
        } else if (level.intLevel() == Level.WARN.intLevel() || level.intLevel() == Level.ERROR.intLevel() || level.intLevel() == Level.FATAL.intLevel()) {
            errorlog.log(level, marker, data, t);
        }
    }

    @Override
    public void warn(Object message) {
        errorlog.warn(message);
    }

    @Override
    public void warn(Object message, Throwable t) {
        errorlog.warn(message, t);
    }

    public void user(Object message) {
        userlog.info(message);
    }

    public void user(Object message, Throwable t) {
        userlog.info(message, t);
    }

    private boolean isEnabled0(Level level) {
        if (level.intLevel() <= Level.WARN.intLevel()) {
            return errorlog.isEnabled(level);
        } else if (level.intLevel() <= Level.INFO.intLevel()) {
            return infolog.isEnabled(level);
        } else if (level.intLevel() <= Level.TRACE.intLevel()) {
            return debuglog.isEnabled(level);
        }

        return true;
    }

}
