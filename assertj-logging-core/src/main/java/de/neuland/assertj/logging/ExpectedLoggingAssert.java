package de.neuland.assertj.logging;

import org.assertj.core.api.Condition;
import org.assertj.core.api.ListAssert;

import java.util.ArrayList;
import java.util.List;

import static de.neuland.assertj.logging.LogLevel.*;
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
        return hasMessageWithLevel(ERROR, actualMessage);
    }

    public ExpectedLoggingAssert hasErrorMessage(String message, Throwable throwable) {
        return hasMessageWithLevelAndThrowable(ERROR, message, throwable);
    }

    public ExpectedLoggingAssert hasErrorMessageMatching(String regex) {
        return hasMessageMatching(ERROR, regex);
    }

    public ExpectedLoggingAssert hasErrorMessageMatching(String regex, Throwable throwable) {
        return hasMessageMatching(ERROR, regex, throwable);
    }

    public ExpectedLoggingAssert hasNoErrorMessage() {
        return hasNoMessageWithLevel(errorMessages(), ERROR);
    }

    public ListAssert<LogEvent> errorMessages() {
        return messagesWithLevel(ERROR);
    }

    public ExpectedLoggingAssert hasWarningMessage(String actualMessage) {
        return hasMessageWithLevel(WARNING, actualMessage);
    }

    public ExpectedLoggingAssert hasWarningMessage(String actualMessage, Throwable actualDetail) {
        return hasMessageWithLevelAndThrowable(WARNING, actualMessage, actualDetail);
    }

    public ExpectedLoggingAssert hasWarningMessageMatching(String regex) {
        return hasMessageMatching(WARNING, regex);
    }

    public ExpectedLoggingAssert hasWarningMessageMatching(String regex, Throwable throwable) {
        return hasMessageMatching(WARNING, regex, throwable);
    }

    public ExpectedLoggingAssert hasNoWarningMessage() {
        return hasNoMessageWithLevel(warningMessages(), WARNING);
    }

    public ListAssert<LogEvent> warningMessages() {
        return messagesWithLevel(WARNING);
    }

    public ExpectedLoggingAssert hasInfoMessage(String actualMessage) {
        return hasMessageWithLevel(INFO, actualMessage);
    }

    public ExpectedLoggingAssert hasInfoMessageMatching(String regex) {
        return hasMessageMatching(INFO, regex);
    }

    public ListAssert<LogEvent> hasNoInfoMessage() {
        return hasNoMessageWithLevel(infoMessages(), INFO);
    }

    public ListAssert<LogEvent> infoMessages() {
        return messagesWithLevel(INFO);
    }

    private ExpectedLoggingAssert hasMessageMatching(final LogLevel logLevel, final String regex) {
        withFailMessage("Expected %s message matching '%s'.\nBut only found:\n%s",
                        logLevel,
                        regex,
                        joinLogEvents())
                        .haveAtLeastOne(new Condition<>() {
                            @Override public boolean matches(LogEvent logEvent) {
                                return logEvent.getLevel() == logLevel &&
                                       logEvent.matchesMessage(regex);
                            }
                        });

        return this;
    }

    private ExpectedLoggingAssert hasMessageMatching(final LogLevel logLevel,
                                                    final String regex,
                                                    final Throwable throwable) {
        withFailMessage("Expected %s message matching '%s'.\nBut only found:\n%s",
                        logLevel,
                        regex,
                        joinLogEvents())
                        .haveAtLeastOne(new Condition<>() {
                            @Override public boolean matches(LogEvent logEvent) {
                                return logEvent.getLevel() == logLevel &&
                                       logEvent.matchesMessage(regex) &&
                                       logEvent.getThrowable() == throwable;
                            }
                        });

        return this;
    }

    private ListAssert<LogEvent> messagesWithLevel(LogLevel logLevel) {
        return filteredOn(new LogLevelFilter(logLevel));
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

    private ExpectedLoggingAssert hasNoMessageWithLevel(ListAssert<LogEvent> logEvents, LogLevel logLevel) {
        logEvents.withFailMessage("Expected no %s message.\nBut found:\n%s",
                                  logLevel,
                                  joinLogEvents(logLevel))
                 .isEmpty();
        return this;
    }

    private String joinLogEvents() {
        return joinLogEvents(actual);
    }

    private String joinLogEvents(LogLevel logLevel) {
        return joinLogEvents(filterActual(new LogLevelFilter(logLevel)));
    }

    private List<? extends LogEvent> filterActual(LogLevelFilter logLevelFilter) {
        List<? extends LogEvent> result = new ArrayList<>(actual);
        for (LogEvent logEvent : actual) {
            if (!logLevelFilter.matches(logEvent)) {
                result.remove(logEvent);
            }
        }
        return result;
    }

    private String joinLogEvents(List<? extends LogEvent> actual) {
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
