package de.neuland.assertj.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@inheritDoc}
 */
public class ExpectedLogging extends GenericExpectedLogging<JavaUtilLoggingLogEventCaptureAppender> {

    private final Logger logger;

    private ExpectedLogging( String loggingSource ) {
        super( loggingSource );
        logger = getCoreLogger();
    }


    public static ExpectedLogging forSource( Class<?> loggingSource ) {
        return new ExpectedLogging( loggingSource.getCanonicalName() );
    }


    @Override
    JavaUtilLoggingLogEventCaptureAppender addCaptureAppender() {
        JavaUtilLoggingLogEventCaptureAppender appender = new JavaUtilLoggingLogEventCaptureAppender();
        logger.addHandler( appender );
        return appender;
    }

    @Override
    void assertLoggerLevelIsAtLeastInfo() {
        if ( logger.getLevel() == null || logger.getLevel().intValue() < Level.INFO.intValue() ) {
            logger.setLevel( Level.INFO );
        }
    }

    @Override
    void removeCaptureAppender( JavaUtilLoggingLogEventCaptureAppender appender ) {
        logger.removeHandler( appender );
    }

    private Logger getCoreLogger() {
        return Logger.getLogger( loggingSource );
    }
}
