package uk.co.idv.context.adapter.stub.identity.find;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.ClearSystemProperty;
import org.junitpioneer.jupiter.SetSystemProperty;
import uk.co.idv.context.adapter.stub.identity.find.data.Delay;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.idv.context.adapter.stub.identity.find.SystemPropertyLoader.loadDelay;

class SystemPropertyLoaderTest {

    private static final String PROPERTY_NAME = "my.delay";
    private static final long DEFAULT_VALUE = 1000;

    @Test
    @ClearSystemProperty(key = PROPERTY_NAME)
    void shouldReturnDefaultDelayIfValueNotSet() {
        Delay delay = loadDelay(PROPERTY_NAME, DEFAULT_VALUE);

        assertThat(delay.getDuration()).isEqualTo(Duration.ofMillis(DEFAULT_VALUE));
    }

    @Test
    @SetSystemProperty(key = PROPERTY_NAME, value = "2000")
    void shouldReturnSpecificDelayIfValueSet() {
        Delay delay = loadDelay(PROPERTY_NAME, DEFAULT_VALUE);

        assertThat(delay.getDuration()).isEqualTo(Duration.ofMillis(2000));
    }

}
