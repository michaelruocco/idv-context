package uk.co.idv.context.usecases.identity.data.stubbed;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.ClearSystemProperty;
import org.junitpioneer.jupiter.SetSystemProperty;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.idv.context.usecases.identity.data.stubbed.StubbedDelaySystemPropertyLoader.loadDelay;

class StubbedDelaySystemPropertyLoaderTest {

    private static final String PROPERTY_NAME = "my.delay";
    private static final long DEFAULT_VALUE = 1000;

    @Test
    @ClearSystemProperty(key = PROPERTY_NAME)
    void shouldReturnDefaultDelayIfValueNotSet() {
        Duration delay = loadDelay(PROPERTY_NAME, DEFAULT_VALUE);

        assertThat(delay.toMillis()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @SetSystemProperty(key = PROPERTY_NAME, value = "2000")
    void shouldReturnSpecificDelayIfValueSet() {
        Duration delay = loadDelay(PROPERTY_NAME, DEFAULT_VALUE);

        assertThat(delay.toMillis()).isEqualTo(2000);
    }

}
