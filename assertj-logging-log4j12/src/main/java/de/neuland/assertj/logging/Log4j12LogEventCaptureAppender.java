package de.neuland.assertj.logging;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

import java.util.ArrayList;
import java.util.List;


public class Log4j12LogEventCaptureAppender extends AppenderSkeleton implements LogEventCaptureAppender {
    private final List<LogEvent> logEvents = new ArrayList<>();

    @Override
    public List<LogEvent> getLogEvents() {
        return logEvents;
    }

    @Override
    public void append(LoggingEvent event) {
        logEvents.add(new LogEvent(logLevel(event),
                                   message(event),
                                   throwable(event)));
    }

    @Override
    public void close() {
    }

    @Override
    public boolean requiresLayout() {
        return false;
    }

    private LogLevel logLevel(LoggingEvent event) {
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

    private String message(LoggingEvent event) {
        return event.getRenderedMessage();
    }

    private Throwable throwable(LoggingEvent event) {
        return event.getThrowableInformation() != null
            ? event.getThrowableInformation().getThrowable()
            : null;
    }
}
