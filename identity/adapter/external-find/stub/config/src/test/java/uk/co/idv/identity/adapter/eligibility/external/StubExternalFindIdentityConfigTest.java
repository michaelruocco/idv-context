package uk.co.idv.identity.adapter.eligibility.external;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.ExecutorService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class StubExternalFindIdentityConfigTest {

    private static final Duration OTHER_DELAY = Duration.ofMillis(5);

    @Test
    void shouldReturnExecutor() {
        ExecutorService executor = mock(ExecutorService.class);

        StubExternalFindIdentityConfig config = StubExternalFindIdentityConfig.builder()
                .executor(executor)
                .build();

        assertThat(config.getExecutor()).isEqualTo(executor);
    }

    @Test
    void shouldReturnNumberDelay() {
        StubExternalFindIdentityConfig config = StubExternalFindIdentityConfig.builder()
                .phoneNumberDelay(OTHER_DELAY)
                .build();

        assertThat(config.getPhoneNumberDelay()).isEqualTo(OTHER_DELAY);
    }

    @Test
    void shouldReturnEmailAddressDelay() {
        StubExternalFindIdentityConfig config = StubExternalFindIdentityConfig.builder()
                .emailAddressDelay(OTHER_DELAY)
                .build();

        assertThat(config.getEmailAddressDelay()).isEqualTo(OTHER_DELAY);
    }

    @Test
    void shouldReturnTimeoutIfSet() {
        Duration timeout = Duration.ofSeconds(5);

        StubExternalFindIdentityConfig config = StubExternalFindIdentityConfig.builder()
                .timeout(timeout)
                .build();

        assertThat(config.getTimeout()).isEqualTo(timeout);
    }

}
