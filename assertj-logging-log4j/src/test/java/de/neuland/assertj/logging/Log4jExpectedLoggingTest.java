package de.neuland.assertj.logging;

import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;


public class Log4jExpectedLoggingTest extends GenericExpectedLoggingTest<ExpectedLogging> {
    @RegisterExtension
    private final ExpectedLogging logging = ExpectedLogging.forSource(TestLogSource.class);

    private TestLogSource logSource;

    @BeforeEach
    public void setup() {
        logSource = new TestLogSource();
    }

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
        return logSource.logger();
    }
}
