package uk.co.idv.context.entities.activity;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class LoginTest {

    @Test
    void shouldReturnName() {
        Activity activity = LoginMother.login();

        assertThat(activity.getName()).isEqualTo("login");
    }

    @Test
    void shouldReturnTimestamp() {
        Instant timestamp = Instant.now();

        Activity activity = LoginMother.withTimestamp(timestamp);

        assertThat(activity.getTimestamp()).isEqualTo(timestamp);
    }

}
