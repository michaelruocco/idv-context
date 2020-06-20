package uk.co.idv.context.adapter.identity.find.external;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.ClearSystemProperty;
import org.junitpioneer.jupiter.SetSystemProperty;
import uk.co.idv.context.usecases.identity.find.external.data.Delay;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class SystemPropertyLoaderTest {

    private static final String PROPERTY_NAME = "my.delay";
    private static final long DEFAULT_VALUE = 1000;

    @Test
    @ClearSystemProperty(key = PROPERTY_NAME)
    void shouldReturnDefaultDelayIfValueNotSet() {
        Delay delay = SystemPropertyLoader.loadDelay(PROPERTY_NAME, DEFAULT_VALUE);

        assertThat(delay.getDuration()).isEqualTo(Duration.ofMillis(DEFAULT_VALUE));
    }

    @Test
    @SetSystemProperty(key = PROPERTY_NAME, value = "2000")
    void shouldReturnSpecificDelayIfValueSet() {
        Delay delay = SystemPropertyLoader.loadDelay(PROPERTY_NAME, DEFAULT_VALUE);

        assertThat(delay.getDuration()).isEqualTo(Duration.ofMillis(2000));
    }

}
