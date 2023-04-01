package de.neuland.assertj.logging;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class JavaUtilLoggingLogEventCaptureAppender extends Handler implements LogEventCaptureAppender {

    private final List<LogEvent> logEvents = new ArrayList<>();
    private static final String ACCEPTED_LEVELS = String.format( "%s, %s or %s", Level.INFO.getName(), Level.WARNING.getName(),
                                                                 Level.SEVERE.getName() );

    @Override
    public List<LogEvent> getLogEvents() {
        return logEvents;
    }

    @Override
    public void publish( LogRecord logRecord ) {
        LogLevel level;
        Level logRecordLevel = logRecord.getLevel();
        if ( logRecordLevel.equals( Level.INFO ) ) {
            level = LogLevel.INFO;
        } else if ( logRecordLevel.equals( Level.WARNING ) ) {
            level = LogLevel.WARNING;
        } else if ( logRecordLevel.equals( Level.SEVERE ) ) {
            level = LogLevel.ERROR;
        } else {
            throw new IllegalStateException( "Unexpected value: " + logRecord.getLevel() + ", should be one of " + ACCEPTED_LEVELS );
        }
        logEvents.add( new LogEvent( level, logRecord.getMessage(), logRecord.getThrown() ) );
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() throws SecurityException {
        logEvents.clear();
    }
}
