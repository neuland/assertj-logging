package de.neuland.assertj.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.String.format;

/**
 * {@inheritDoc}
 */
public class ExpectedLogging extends GenericExpectedLogging<LogbackLogEventCaptureAppender> {
    private final Logger logger;

    private ExpectedLogging(String loggingSource) {
        super(loggingSource);
        logger = getLogbackLogger();
    }

    public static ExpectedLogging forSource(Class<?> loggingSource) {
        return new ExpectedLogging(loggingSource.getCanonicalName());
    }

    @Override
    LogbackLogEventCaptureAppender addCaptureAppender() {
        LogbackLogEventCaptureAppender captureAppender = new LogbackLogEventCaptureAppender();
        logger.addAppender(captureAppender);
        captureAppender.start();

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
    void removeCaptureAppender(final LogbackLogEventCaptureAppender appender) {
        appender.stop();
        logger.detachAppender(appender);
    }

    private Logger getLogbackLogger() {
        org.slf4j.Logger slf4jLogger = LoggerFactory.getLogger(loggingSource);
        if (isNotLogbackLogger(slf4jLogger)) {
            throw new IllegalStateException(format("Log events can only be captured for %s not for %s",
                    Logger.class.getName(),
                    slf4jLogger.getClass().getName()));
        }
        return (Logger) slf4jLogger;
    }

    private boolean isNotLogbackLogger(org.slf4j.Logger slf4jLogger) {
        return !Logger.class.isAssignableFrom(slf4jLogger.getClass());
    }
}

