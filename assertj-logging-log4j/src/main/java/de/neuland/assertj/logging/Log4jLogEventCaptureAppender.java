package de.neuland.assertj.logging;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.appender.AbstractAppender;

import java.util.ArrayList;
import java.util.List;


public class Log4jLogEventCaptureAppender extends AbstractAppender implements LogEventCaptureAppender {
    private final List<de.neuland.assertj.logging.LogEvent> logEvents = new ArrayList<>();

    public Log4jLogEventCaptureAppender() {
        super(Log4jLogEventCaptureAppender.class.getName(),
              null,
              null,
              false,
              null);
    }

    @Override
    public List<LogEvent> getLogEvents() {
        return logEvents;
    }

    @Override
    public boolean isStarted() {
        return true;
    }

    @Override
    public void append(org.apache.logging.log4j.core.LogEvent event) {
        logEvents.add(new LogEvent(level(event),
                                   message(event),
                                   throwable(event)));
    }

    private LogLevel level(org.apache.logging.log4j.core.LogEvent event) {
        Level level = event.getLevel();
        if (Level.ERROR.equals(level)) {
            return LogLevel.ERROR;
        }
        if (Level.WARN.equals(level)) {
            return LogLevel.WARNING;
        }
        if (Level.INFO.equals(level)) {
            return LogLevel.INFO;
        }
        return LogLevel.IGNORED;
    }

    private String message(org.apache.logging.log4j.core.LogEvent event) {
        if (event.getMessage() == null) {
            return null;
        }
        return event.getMessage().getFormattedMessage();
    }

    private Throwable throwable(org.apache.logging.log4j.core.LogEvent event) {
        return event.getThrown();
    }
}
