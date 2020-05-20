package de.neuland.assertj.logging;

import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Rule;


public class Log4jExpectedLoggingTest extends GenericExpectedLoggingTest<ExpectedLogging> {
    @Rule
    public ExpectedLogging logging = ExpectedLogging.forSource(TestLogSource.class);

    private TestLogSource logSource;

    @Before
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
