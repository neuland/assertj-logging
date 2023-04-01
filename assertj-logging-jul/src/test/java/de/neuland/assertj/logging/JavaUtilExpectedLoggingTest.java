package de.neuland.assertj.logging;

import java.util.logging.Level;

import org.junit.jupiter.api.extension.RegisterExtension;

import lombok.extern.java.Log;

@Log
public class JavaUtilExpectedLoggingTest extends GenericExpectedLoggingTest<ExpectedLogging> {

    @RegisterExtension
    private final ExpectedLogging logging = ExpectedLogging.forSource( JavaUtilExpectedLoggingTest.class );

    @Override
    ExpectedLogging expectedLoggingRule() {
        return logging;
    }

    @Override
    void logError( String message ) {
        LOG.severe( message );
    }

    @Override
    void logError( String message, Throwable detail ) {
        LOG.log( Level.SEVERE, message, detail );
    }

    @Override
    void logWarning( String message ) {
        LOG.log( Level.WARNING, message );
    }

    @Override
    void logWarning( String message, Throwable detail ) {
        LOG.log( Level.WARNING, message, detail );
    }

    @Override
    void logInfo( String message ) {
        LOG.log( Level.INFO, message );
    }

}
