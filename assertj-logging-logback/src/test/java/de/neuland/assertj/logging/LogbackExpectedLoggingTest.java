package de.neuland.assertj.logging;

import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.slf4j.Logger;


@Slf4j
public class LogbackExpectedLoggingTest extends GenericExpectedLoggingTest<ExpectedLogging> {
    @Rule
    public ExpectedLogging logging = ExpectedLogging.forSource(LogbackExpectedLoggingTest.class);

    @Override
    ExpectedLogging expectedLoggingRule() {
        return logging;
    }

    @Override
    void logError(String message, Throwable throwable) {
        logger().error(message, throwable);
    }

    @Override
    void logError(String message) {
        logger().error(message);
    }

    @Override
    void logWarning(String message, Throwable throwable) {
        logger().warn(message, throwable);
    }

    @Override
    void logWarning(String message) {
        logger().warn(message);
    }

    @Override
    void logInfo(String message) {
        logger().info(message);
    }

    private Logger logger() {
        return LOG;
    }
}
