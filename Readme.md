# assertj-logging - assertj assertions for logging

_assertj-logging_'s intention is to provide an easy way to unit test expected logging for different logging implementations with [JUnit 4](https://junit.org/junit4/) and [AssertJ](https://assertj.github.io/doc/).

It currently supports
* [log4j 1.2](https://logging.apache.org/log4j/1.2/): _assertj-logging-log4j12_
* [log4j 2.x](https://logging.apache.org/log4j/2.x/): _assertj-logging-log4j_
* [logback](http://logback.qos.ch/): _assertj-logging-logback_


## Contents

- [Configuration](#configuration)
- [How it works](#how-it-works)
- [Usage](#usage)


## Configuration

### Gradle

_build.gradle_

```$groovy
dependencies {
    testImplementation 'de.neuland-bfi:assertj-logging-log4j:0.1'
}
```

### Maven

_pom.xml_

```$xml
<dependency>
    <groupId>de.neuland-bfi</groupId>
    <artifactId>assertj-logging-log4j</artifactId>
    <version>0.1</version>
    <scope>test</scope>
</dependency>
```

## How it works

_assert-logging_ provides a JUnit rule that adds an appender to capture log messages to the logger of the logging source (the class emitting log messages).

After execution of the code under test this rule can be fed to a set of AssertJ assertions to verify that the expected logging was emitted.

## Usage

```java
import org.junit.Rule;
import org.junit.Test;

import de.neuland.assertj.logging.ExpectedLogging;

import static de.neuland.assertj.logging.ExpectedLoggingAssertions.assertThat;

public class LoggingSourceTest {
    @Rule
    public ExpectedLogging logging = ExpectedLogging.forSource(LoggingSource.class);

    @Test
    public void shouldCaptureLogging() {
        // given
        String expectedMessage = "Error Message";

        // when
        new LoggingSource().doSomethingThatLogsErrorMessage();

        // then
        assertThat(logging).hasErrorMessage(expectedMessage);
    }
}
```

### Supported Log Levels

We consider _ERROR_, _WARNING_ and _INFO_ to be the test-worthy log levels.

Thus, _assertj-logging_ provides assertions for these log levels.

### Assertions

The following assertions are available:

```java
assertThat(logging).hasErrorMessage(String message);
assertThat(logging).hasNoErrorMessage();
assertThat(logging).hasErrorMessage(String message, Throwable throwable);
assertThat(logging).hasErrorMessageMatching(String regex);
assertThat(logging).hasErrorMessageMatching(String regex, Throwable throwable);

assertThat(logging).hasWarningMessage(String message);
assertThat(logging).hasNoWarningMessage();
assertThat(logging).hasWarningMessage(String message, Throwable throwable);
assertThat(logging).hasWarningMessageMatching(String regex);
assertThat(logging).hasWarningMessageMatching(String regex, Throwable throwable);

assertThat(logging).hasInfoMessage(String message);
assertThat(logging).hasNoInfoMessage();
assertThat(logging).hasInfoMessageMatching(String regex);
```

All assertions from AssertJ's [ListAssert](https://joel-costigliola.github.io/assertj/core/api/org/assertj/core/api/ListAssert.html) are available as well, e.g.

```java
assertThat(logging).isEmpty();
```

