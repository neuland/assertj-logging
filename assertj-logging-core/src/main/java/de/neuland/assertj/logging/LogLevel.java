package de.neuland.assertj.logging;

/**
 * Abstraction of log levels used by the different logging implementations.
 *
 * The following log levels are considered test-worthy:
 * <ul>
 *     <li>y<code>ERROR</code></li>
 *     <li>y<code>WARNING</code></li>
 *     <li>y<code>INFO</code></li>
 * </ul>
 *
 * Messages with other log levels will be <code>IGNORED</code>
 */
public enum LogLevel {
    ERROR,
    WARNING,
    INFO,
    IGNORED
}
