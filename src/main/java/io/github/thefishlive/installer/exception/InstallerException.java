package io.github.thefishlive.installer.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@SuppressWarnings("serial")
public class InstallerException extends Exception {

    public static final InstallerException INSTALLER_OUT_OF_DATE = new InstallerException(InstallerError.INSTALLER_OUT_OF_DATE);
    public static final InstallerException CONFIG_OUT_OF_DATE = new InstallerException(InstallerError.CONFIG_OUT_OF_DATE);
    public static final InstallerException PROFILES_FILE_DOES_NOT_EXIST = new InstallerException(InstallerError.PROFILES_FILE_DOES_NOT_EXIST);
    public static final InstallerException DOWNLOAD_NOT_SETUP = new InstallerException(InstallerError.DOWNLOAD_NOT_SETUP);
    @Getter
    private InstallerError error;

    public InstallerException(Throwable e) {
        super(e);
        this.error = InstallerError.UNKNOWN_ERROR;
    }

    private InstallerException(InstallerError error, Object... args) {
        super(String.format(error.getMessage(), args));
        this.error = error;
    }

    public InstallerException(String string) {
        super(string);
        this.error = InstallerError.UNKNOWN_ERROR;
    }

    public static InstallerException invalidChecksum(String file) {
        return new InstallerException(InstallerError.INVALID_CHECKSUM, file);
    }

    public static InstallerException missingChecksum(String file) {
        return new InstallerException(InstallerError.CHECKSUMS_MISSING_FOR_DOWNLOAD, file);
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static enum InstallerError {
        INSTALLER_OUT_OF_DATE("The installer version you are using is out of date, please update"),
        CONFIG_OUT_OF_DATE("The config you are using is out of date, specify a more upto date config"),
        PROFILES_FILE_DOES_NOT_EXIST("The config you are using is out of date, specify a more upto date config"),
        DOWNLOAD_NOT_SETUP("Downloads have not been initialized, have you run the setup yet?"),
        CHECKSUMS_MISSING_FOR_DOWNLOAD("Checksums file for compressed download %s is missing."),
        INVALID_CHECKSUM("Checksums file for compressed download %s is invalid."),
        UNKNOWN_ERROR("A unknown error has occurred"),;

        @Getter
        private String message;
    }
}
