package de.neuland.assertj.logging;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.List;


/**
 * The {@code ExpectedLogging} rule allows you to verify that your code
 * logs messages in the underlying logging framework.
 *
 * <h3>Usage</h3>
 *
 * <pre> public class SimpleLoggingTest {
 *     &#064;Rule
 *     public ExpectedLogging logging = ExpectedLogging.forSource(LoggingSource.class);
 *
 *     &#064;Test
 *     public void shouldLogError() {
 *          String message = "Error Message";
 *
 *          new LoggingSource().doSomethingThatLogsAnError();
 *
 *          LoggingAssertions.assertThat(logging).hasErrorMessage(message);
 *     }
 * }</pre>
 *
 * <p>
 * You have to add the {@code ExpectedLogging} rule to your test.
 * This doesn't affect your existing tests.
 * After executing the method that is expected to log,
 * you can verify this with assertj assertions defined in {@code ExpectedLoggingAssert}
 */
public abstract class GenericExpectedLogging<APPENDER extends LogEventCaptureAppender> implements BeforeEachCallback, AfterEachCallback {
    final String loggingSource;
    private final ThreadLocal<APPENDER> threadLocalAppender = new ThreadLocal<>();

    GenericExpectedLogging(String loggingSource) {
        this.loggingSource = loggingSource;
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        APPENDER captureAppender = addCaptureAppender();
        assertLoggerLevelIsAtLeastInfo();

        threadLocalAppender.set(captureAppender);
    }

    @Override
    public void afterEach(ExtensionContext context) {
        removeCaptureAppender(threadLocalAppender.get());
    }

    abstract APPENDER addCaptureAppender();
    abstract void assertLoggerLevelIsAtLeastInfo();
    abstract void removeCaptureAppender(APPENDER appender);

    public List<LogEvent> getLogEvents() {
        return threadLocalAppender.get().getLogEvents();
    }
}
