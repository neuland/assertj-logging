package de.neuland.assertj.logging;

import lombok.Value;


/**
 * An abstraction of log events for different logging frameworks.
 */
@Value
public class LogEvent {
    LogLevel level;
    String message;
    Throwable throwable;

    public boolean matchesMessage(String regex) {
        return message != null && message.matches(regex) ;
    }
}
