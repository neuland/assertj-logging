package de.neuland.assertj.logging;

public class ExpectedLoggingAssertions {
    public static ExpectedLoggingAssert assertThat(ExpectedLogging actual) {
        return new ExpectedLoggingAssert(actual);
    }
}
