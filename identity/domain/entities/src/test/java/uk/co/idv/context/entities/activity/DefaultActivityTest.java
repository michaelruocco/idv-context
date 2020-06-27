package uk.co.idv.context.entities.activity;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultActivityTest {

    @Test
    void shouldReturnName() {
        String name = "my-activity";

        Activity activity = DefaultActivityMother.withName(name);

        assertThat(activity.getName()).isEqualTo(name);
    }

    @Test
    void shouldReturnTimestamp() {
        Instant timestamp = Instant.now();

        Activity activity = DefaultActivityMother.withTimestamp(timestamp);

        assertThat(activity.getTimestamp()).isEqualTo(timestamp);
    }

}
