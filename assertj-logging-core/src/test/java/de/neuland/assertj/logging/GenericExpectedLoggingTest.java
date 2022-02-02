package de.neuland.assertj.logging;

import org.junit.jupiter.api.Test;

import static de.neuland.assertj.logging.LogLevel.*;
import static de.neuland.assertj.logging.TestAssertions.assertThat;


abstract class GenericExpectedLoggingTest<RULE extends GenericExpectedLogging<?>> {
    abstract RULE expectedLoggingRule();

    abstract void logError(String message);

    abstract void logError(String message, Throwable detail);

    abstract void logWarning(String message);

    abstract void logWarning(String message, Throwable detail);

    abstract void logInfo(String message);

    @Test
    void shouldCaptureLogging() {
        // given
        String message = "Error Message";

        // when
        logError(message);

        // then
        assertThatExpectedLogging().isNotEmpty();
    }

    @Test
    void shouldHaveErrorMessage() {
        // given
        String message = "Error Message";

        // when
        logError(message);

        // then
        final GenericExpectedLogging<?> logging = expectedLoggingRule();
        assertThat(logging).hasErrorMessage(message);
    }

    @Test
    void shouldNotHaveErrorMessage() {
        // given
        String infoMessage = "Info Message";
        String warningMessage = "Warning Message";

        // when
        logInfo(infoMessage);
        logWarning(warningMessage);

        // then
        assertThatExpectedLogging().hasNoErrorMessage();
    }

    @Test
    void shouldHaveErrorMessageMatchingRegularExpression() {
        // given
        String message = "Error Message";

        // when
        logError(message);

        // then
        final GenericExpectedLogging<?> logging = expectedLoggingRule();
        assertThat(logging).hasErrorMessageMatching("^Error.*");
        assertThat(logging).hasErrorMessageMatching(".*Message$");
    }

    @Test
    void shouldHaveErrorMessageMatchingRegularExpressionWithThrowable() {
        // given
        String message = "Error Message";
        Throwable throwable = new RuntimeException("Error Cause");

        // when
        logError(message, throwable);

        // then
        final GenericExpectedLogging<?> logging = expectedLoggingRule();
        assertThat(logging).hasErrorMessageMatching("^Error.*", throwable);
        assertThat(logging).hasErrorMessageMatching(".*Message$", throwable);
    }

    @Test
    void shouldHaveErrorMessageWithThrowable() {
        // given
        String message = "Error Message";
        Throwable throwable = new RuntimeException("Error Cause");

        // when
        logError(message, throwable);

        // then
        assertThatExpectedLogging().hasErrorMessage(message, throwable);
    }

    @Test
    void shouldHaveWarningMessage() {
        // given
        String message = "Warning Message";

        // when
        logWarning(message);

        // then
        assertThatExpectedLogging().hasWarningMessage(message);
    }

    @Test
    void shouldNotHaveWarningMessage() {
        // given
        String infoMessage = "Info Message";
        String errorMessage = "Error Message";

        // when
        logInfo(infoMessage);
        logError(errorMessage);

        // then
        assertThatExpectedLogging().hasNoWarningMessage();
    }

    @Test
    void shouldHaveWarningMessageWithThrowable() {
        // given
        String message = "Warning Message";
        Throwable throwable = new RuntimeException("Warning Cause");

        // when
        logWarning(message, throwable);

        // then
        assertThatExpectedLogging().hasWarningMessage(message, throwable);
    }

    @Test
    void shouldHaveWarningMessageMatchingRegularExpression() {
        // given
        String message = "Warning Message";

        // when
        logWarning(message);

        // then
        final GenericExpectedLogging<?> logging = expectedLoggingRule();
        assertThat(logging).hasWarningMessageMatching("^Warning.*");
        assertThat(logging).hasWarningMessageMatching(".*Message$");
    }

    @Test
    void shouldHaveWarningMessageMatchingRegularExpressionWithThrowable() {
        // given
        String message = "Warning Message";
        Throwable throwable = new RuntimeException("Warning Cause");

        // when
        logWarning(message, throwable);

        // then
        final GenericExpectedLogging<?> logging = expectedLoggingRule();
        assertThat(logging).hasWarningMessageMatching("^Warning.*", throwable);
        assertThat(logging).hasWarningMessageMatching(".*Message$", throwable);
    }

    @Test
    void shouldHaveInfoMessage() {
        // given
        String message = "Info Message";

        // when
        logInfo(message);

        // then
        assertThatExpectedLogging().hasInfoMessage(message);
    }

    @Test
    void shouldNotHaveInfoMessage() {
        // given
        String warningMessage = "Warning Message";
        String errorMessage = "Error Message";

        // when
        logWarning(warningMessage);
        logError(errorMessage);

        // then
        assertThatExpectedLogging().hasNoInfoMessage();
    }

    @Test
    void shouldHaveInfoMessageMatchingRegularExpression() {
        // given
        String message = "Info Message";

        // when
        logInfo(message);

        // then
        final GenericExpectedLogging<?> logging = expectedLoggingRule();
        assertThat(logging).hasInfoMessageMatching("^Info.*");
        assertThat(logging).hasInfoMessageMatching(".*Message$");
    }

    @Test
    void shouldContainErrorMessages() {
        // given
        String message1 = "Error message one";
        String message2 = "Error message two";

        // when
        logError(message1);
        logError(message2);

        // then
        assertThatExpectedLogging().errorMessages()
                                   .containsExactly(new LogEvent(ERROR, message1, null),
                                                    new LogEvent(ERROR, message2, null));
    }

    @Test
    void shouldContainWarningMessages() {
        // given
        String message1 = "Warning message one";
        String message2 = "Warning message two";

        // when
        logWarning(message1);
        logWarning(message2);

        // then
        assertThatExpectedLogging().warningMessages()
                                   .containsExactly(new LogEvent(WARNING, message1, null),
                                                    new LogEvent(WARNING, message2, null));
    }

    @Test
    void shouldContainInfoMessages() {
        // given
        String message1 = "Info message one";
        String message2 = "Info message two";

        // when
        logInfo(message1);
        logInfo(message2);

        // then
        assertThatExpectedLogging().infoMessages()
                                   .containsExactly(new LogEvent(INFO, message1, null),
                                                    new LogEvent(INFO, message2, null));
    }

    private ExpectedLoggingAssert assertThatExpectedLogging() {
        return assertThat(expectedLoggingRule());
    }
}
