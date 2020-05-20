package de.neuland.assertj.logging;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


/**
 * {@inheritDoc}
 */
public class ExpectedLogging extends GenericExpectedLogging<Log4j12LogEventCaptureAppender> {
    private final Logger logger;
    private ExpectedLogging(String loggingSource) {
        super(loggingSource);
        this.logger = getLogger();
    }

    public static ExpectedLogging forSource(Class<?> loggingSource) {
        return new ExpectedLogging(loggingSource.getCanonicalName());
    }

    @Override
    Log4j12LogEventCaptureAppender addCaptureAppender() {
        Log4j12LogEventCaptureAppender captureAppender = new Log4j12LogEventCaptureAppender();
        logger.addAppender(captureAppender);

        return captureAppender;
    }

    @Override
    void assertLoggerLevelIsAtLeastInfo() {
        final Level loggerLevel = logger.getLevel();
        if (loggerLevel == null || loggerLevel.isGreaterOrEqual(Level.INFO)) {
            logger.setLevel(Level.INFO);
        }
    }

    @Override
    void removeCaptureAppender(final Log4j12LogEventCaptureAppender appender) {
        logger.removeAppender(appender);
    }

    private Logger getLogger() {
        return LogManager.getLogger(loggingSource);
    }
}
