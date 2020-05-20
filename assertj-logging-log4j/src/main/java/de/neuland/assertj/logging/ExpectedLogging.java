package de.neuland.assertj.logging;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import static java.lang.String.format;


/**
 * {@inheritDoc}
 */
public class ExpectedLogging extends GenericExpectedLogging<Log4jLogEventCaptureAppender> {
    private final Logger logger;

    private ExpectedLogging(String loggingSource) {
        super(loggingSource);
        logger = getCoreLogger();
    }

    public static ExpectedLogging forSource(Class<?> loggingSource) {
        return new ExpectedLogging(loggingSource.getCanonicalName());
    }

    @Override
    Log4jLogEventCaptureAppender addCaptureAppender() {
        Log4jLogEventCaptureAppender captureAppender = new Log4jLogEventCaptureAppender();
        addAppenderUpdatingConfiguration(captureAppender);

        return captureAppender;
    }

    private void addAppenderUpdatingConfiguration(final Log4jLogEventCaptureAppender captureAppender) {
        // we must update the configuration in order to set the logger level in assertLoggerLevelIsAtLeastInfo()
        // so logger.addAppender(captureAppender) does not suffice
        logger.getContext().getConfiguration().addLoggerAppender(logger, captureAppender);
    }

    @Override
    void assertLoggerLevelIsAtLeastInfo() {
        if (logger.getLevel() == null || logger.getLevel().isMoreSpecificThan(Level.INFO)) {
            logger.setLevel(Level.INFO);
//            logger.getContext().getConfiguration().addLoggerFilter(logger, new AcceptAllLogEventsFilter());
        }
    }

    @Override
    void removeCaptureAppender(final Log4jLogEventCaptureAppender appender) {
        logger.removeAppender(appender);
    }

    private Logger getCoreLogger() {
        org.apache.logging.log4j.Logger apiLogger = LogManager.getLogger(loggingSource);
        if (isNotCoreLogger(apiLogger)) {
            throw new IllegalStateException(format("Log events can only be captured for %s not for %s",
                    Logger.class.getName(),
                    apiLogger.getClass().getName()));
        }
        return (Logger) apiLogger;
    }

    private boolean isNotCoreLogger(org.apache.logging.log4j.Logger apiLogger) {
        return !Logger.class.isAssignableFrom(apiLogger.getClass());
    }
}
