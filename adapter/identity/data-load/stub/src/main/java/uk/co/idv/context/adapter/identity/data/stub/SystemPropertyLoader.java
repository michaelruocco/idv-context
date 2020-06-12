package uk.co.idv.context.adapter.identity.data.stub;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
public class SystemPropertyLoader {

    private SystemPropertyLoader() {
        // utility class
    }

    public static Delay loadDelay(String propertyName, long defaultValue) {
        return new Delay(loadDuration(propertyName, defaultValue));
    }

    public static Duration loadDuration(String propertyName, long defaultValue) {
        String delay = System.getProperty(propertyName, Long.toString(defaultValue));
        log.info("loaded {} {}ms", propertyName, delay);
        return Duration.ofMillis(Long.parseLong(delay));
    }

}
