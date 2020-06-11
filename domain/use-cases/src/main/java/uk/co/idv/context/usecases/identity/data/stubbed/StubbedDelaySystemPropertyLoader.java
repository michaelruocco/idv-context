package uk.co.idv.context.usecases.identity.data.stubbed;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
public class StubbedDelaySystemPropertyLoader {

    private StubbedDelaySystemPropertyLoader() {
        // utility class
    }

    public static Duration loadDelay(String propertyName, long defaultValue) {
        String delay = System.getProperty(propertyName, Long.toString(defaultValue));
        log.info("loaded {} {}ms", propertyName, delay);
        return Duration.ofMillis(Long.parseLong(delay));
    }

}
