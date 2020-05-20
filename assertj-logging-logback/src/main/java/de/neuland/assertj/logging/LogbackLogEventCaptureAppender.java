package de.neuland.assertj.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxy;
import ch.qos.logback.core.read.ListAppender;

import java.util.ArrayList;
import java.util.List;


public class LogbackLogEventCaptureAppender extends ListAppender<ILoggingEvent> implements LogEventCaptureAppender {
    private final List<LogEvent> logEvents = new ArrayList<>();

    public LogbackLogEventCaptureAppender() {
        super();
    }

    @Override
    public List<LogEvent> getLogEvents() {
        return logEvents;
    }

    @Override
    protected void append(ILoggingEvent event) {
        logEvents.add(new LogEvent(level(event),
                                   message(event),
                                   throwable(event)
        ));
    }

    private LogLevel level(ILoggingEvent event) {
        Level level = event.getLevel();
        if (Level.ERROR == level) {
            return LogLevel.ERROR;
        }
        if (Level.WARN == level) {
            return LogLevel.WARNING;
        }
        if (Level.INFO == level) {
            return LogLevel.INFO;
        }
        return LogLevel.IGNORED;
    }

    private String message(ILoggingEvent event) {
        return event.getFormattedMessage();
    }

    private Throwable throwable(ILoggingEvent event) {
        if (event.getThrowableProxy() == null) {
            return null;
        }
        return ((ThrowableProxy) event.getThrowableProxy()).getThrowable();
    }
}
