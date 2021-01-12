package de.neuland.assertj.logging;

import org.assertj.core.api.Condition;
import org.assertj.core.api.ListAssert;

import static org.assertj.core.api.Assertions.tuple;


/**
 * Assertion methods for de.neuland.assertj.logging.ExpectedLogging.
 *
 * <p>
 * To create an instance of this class, invoke <code>de.neuland.assertj.logging.ExpectedLoggingAssertions#assertThat(ExpectedLogging)</code>.
 * </p>
 */
public class ExpectedLoggingAssert extends ListAssert<LogEvent> {
    public ExpectedLoggingAssert(GenericExpectedLogging<?> actual) {
        super(actual.getLogEvents());
    }

    public ExpectedLoggingAssert hasErrorMessage(String actualMessage) {
        return hasMessageWithLevel(LogLevel.ERROR, actualMessage);
    }

    public ExpectedLoggingAssert hasErrorMessage(String message, Throwable throwable) {
        return hasMessageWithLevelAndThrowable(LogLevel.ERROR, message, throwable);
    }

    public ExpectedLoggingAssert hasErrorMessageMatching(String regex) {
        return hasMessageMatching(LogLevel.ERROR, regex);
    }

    public ExpectedLoggingAssert hasErrorMessageMatching(String regex, Throwable throwable) {
        return hasMessageMatching(LogLevel.ERROR, regex, throwable);
    }

    public ExpectedLoggingAssert hasWarningMessage(String actualMessage) {
        return hasMessageWithLevel(LogLevel.WARNING, actualMessage);
    }

    public ExpectedLoggingAssert hasWarningMessage(String actualMessage, Throwable actualDetail) {
        return hasMessageWithLevelAndThrowable(LogLevel.WARNING, actualMessage, actualDetail);
    }

    public ExpectedLoggingAssert hasWarningMessageMatching(String regex) {
        return hasMessageMatching(LogLevel.WARNING, regex);
    }

    public ExpectedLoggingAssert hasWarningMessageMatching(String regex, Throwable throwable) {
        return hasMessageMatching(LogLevel.WARNING, regex, throwable);
    }

    public ExpectedLoggingAssert hasInfoMessage(String actualMessage) {
        return hasMessageWithLevel(LogLevel.INFO, actualMessage);
    }

    public ExpectedLoggingAssert hasInfoMessageMatching(String regex) {
        return hasMessageMatching(LogLevel.INFO, regex);
    }

    private ExpectedLoggingAssert hasMessageMatching(final LogLevel logLevel, final String regex) {
        withFailMessage("Expected %s message matching '%s'.\nBut only found:\n%s",
                        logLevel,
                        regex,
                        joinLogEvents())
                .haveAtLeastOne(new Condition<LogEvent>() {
                    @Override public boolean matches(LogEvent logEvent) {
                        return logEvent.getLevel() == logLevel &&
                               logEvent.matchesMessage(regex);
                    }
                });

        return this;
    }

    public ExpectedLoggingAssert hasMessageMatching(final LogLevel logLevel,
                                                    final String regex,
                                                    final Throwable throwable) {
        withFailMessage("Expected %s message matching '%s'.\nBut only found:\n%s",
                        logLevel,
                        regex,
                        joinLogEvents())
                .haveAtLeastOne(new Condition<LogEvent>() {
                    @Override public boolean matches(LogEvent logEvent) {
                        return logEvent.getLevel() == logLevel &&
                               logEvent.matchesMessage(regex) &&
                               logEvent.getThrowable() == throwable;
                    }
                });

        return this;
    }

    public ListAssert<LogEvent> errorMessages() {
        return messagesWithLevel(LogLevel.ERROR);
    }

    public ListAssert<LogEvent> warningMessages() {
        return messagesWithLevel(LogLevel.WARNING);
    }

    public ListAssert<LogEvent> infoMessages() {
        return messagesWithLevel(LogLevel.INFO);
    }

    private ListAssert<LogEvent> messagesWithLevel(LogLevel logLevel) {
        //noinspection RedundantCast: cast required for assertj 2.9.0
        return (ListAssert<LogEvent>) filteredOn(new LogLevelFilter(logLevel));
    }

    private ExpectedLoggingAssert hasMessageWithLevelAndThrowable(LogLevel logLevel,
                                                                  String actualMessage,
                                                                  Throwable actualThrowable) {
        extracting("message", "level", "throwable")
                .contains(tuple(actualMessage, logLevel, actualThrowable));

        return this;
    }

    private ExpectedLoggingAssert hasMessageWithLevel(LogLevel logLevel, String actualMessage) {
        extracting("level", "message")
                .contains(tuple(logLevel, actualMessage));

        return this;
    }

    private String joinLogEvents() {
        if (actual == null || actual.size() <= 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < actual.size(); i++) {
            sb.append(actual.get(i).toString());
            if (i != actual.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    private static class LogLevelFilter extends Condition<LogEvent> {
        private final LogLevel logLevel;

        LogLevelFilter(LogLevel logLevel) {
            this.logLevel = logLevel;
        }

        @Override
        public boolean matches(LogEvent logEvent) {
            return logLevel.equals(logEvent.getLevel());
        }
    }
}
