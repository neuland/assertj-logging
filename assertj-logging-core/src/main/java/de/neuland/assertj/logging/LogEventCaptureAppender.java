package de.neuland.assertj.logging;

import java.util.List;


/**
 * Logging framework specific appender for capturing log events.
 */
public interface LogEventCaptureAppender {
    List<LogEvent> getLogEvents();
}
