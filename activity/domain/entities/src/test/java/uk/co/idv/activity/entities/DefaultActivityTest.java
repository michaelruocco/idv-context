package uk.co.idv.activity.entities;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultActivityTest {

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
