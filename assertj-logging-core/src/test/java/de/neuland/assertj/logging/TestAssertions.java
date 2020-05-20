package de.neuland.assertj.logging;

import org.assertj.core.api.Assertions;


public class TestAssertions extends Assertions {
    public static ExpectedLoggingAssert assertThat(GenericExpectedLogging<?> actual) {
        return new ExpectedLoggingAssert(actual);
    }
}
