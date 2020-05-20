package de.neuland.assertj.logging;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Logger;

@Log4j2
public class TestLogSource {
    Logger logger() {
        return LOG;
    }
}
