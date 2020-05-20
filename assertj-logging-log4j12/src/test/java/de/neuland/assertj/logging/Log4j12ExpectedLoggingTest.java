package de.neuland.assertj.logging;

import lombok.extern.log4j.Log4j;
import org.junit.Rule;

import static de.neuland.assertj.logging.TestAssertions.assertThat;


@Log4j
public class Log4j12ExpectedLoggingTest extends GenericExpectedLoggingTest<ExpectedLogging> {
    @Rule
    public ExpectedLogging logging = ExpectedLogging.forSource(Log4j12ExpectedLoggingTest.class);

    @Override
    ExpectedLogging expectedLoggingRule() {
        return logging;
    }

    @Override
    void logError(String message, Throwable detail) {
        LOG.error(message, detail);
    }

    @Override
    void logError(String message) {
        LOG.error(message);
    }

    @Override
    void logWarning(String message, Throwable detail) {
        LOG.warn(message, detail);
    }

    @Override
    void logWarning(String message) {
        LOG.warn(message);
    }

    @Override
    void logInfo(String message) {
        LOG.info(message);
    }
}
