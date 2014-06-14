package io.github.thefishlive.installer.crash;

import com.google.common.collect.Lists;
import io.github.thefishlive.crash.CrashReportSection;
import io.github.thefishlive.crash.CrashReport;
import io.github.thefishlive.installer.log.InstallerLogger;
import io.github.thefishlive.upload.UploadTargets;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.List;

public class CrashReporter {

    private static final Logger log = InstallerLogger.getLog();
    private static List<CrashReportSection> sections = Lists.newArrayList();

    public static CrashReport buildCrashReport(String message, Throwable exception) {
        CrashReport report = new CrashReport(message, exception);

        for (CrashReportSection section : sections) {
            report.addCrashReportSection(section);
        }

        return report;
    }

    public static void uploadCrashReport(String reason, Throwable thrown) {
        try {
            CrashReport report = CrashReporter.buildCrashReport(reason, thrown);
            String url = report.upload(UploadTargets.GIST);

            Desktop.getDesktop().browse(URI.create(url));
            InstallerLogger.getUserlog().warn("Uploaded crash report to Github.");
        } catch (IOException | ReflectiveOperationException e) {
            log.fatal("A error occurred whilst uploading crash report to Github");
            log.fatal("Exception: " + e.getMessage());
            log.fatal("Stacktrace: ", e);
        }

    }

    public static void registerSection(CrashReportSection section) {
        sections.add(section);
    }
}
