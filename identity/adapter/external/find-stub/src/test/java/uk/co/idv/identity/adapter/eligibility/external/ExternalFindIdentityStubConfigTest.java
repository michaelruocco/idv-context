package uk.co.idv.identity.adapter.eligibility.external;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.ExecutorService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class ExternalFindIdentityStubConfigTest {

    private static final Duration NO_DELAY = Duration.ZERO;
    private static final Duration OTHER_DELAY = Duration.ofMillis(5);

    @Test
    void shouldReturnExecutor() {
        ExecutorService executor = mock(ExecutorService.class);

        ExternalFindIdentityStubConfig config = ExternalFindIdentityStubConfig.build(executor);

        assertThat(config.getExecutor()).isEqualTo(executor);
    }

    @Test
    void shouldReturnNoPhoneNumberDelayIfNotSet() {
        ExecutorService executor = mock(ExecutorService.class);

        ExternalFindIdentityStubConfig config = ExternalFindIdentityStubConfig.build(executor);

        assertThat(config.getPhoneNumberDelay()).isEqualTo(NO_DELAY);
    }

    @Test
    void shouldReturnNoEmailAddressDelayIfNotSet() {
        ExecutorService executor = mock(ExecutorService.class);

        ExternalFindIdentityStubConfig config = ExternalFindIdentityStubConfig.build(executor);

        assertThat(config.getEmailAddressDelay()).isEqualTo(NO_DELAY);
    }

    @Test
    void shouldReturn2SecondTimeoutForAllChannelsIfNotSet() {
        ExecutorService executor = mock(ExecutorService.class);

        ExternalFindIdentityStubConfig config = ExternalFindIdentityStubConfig.build(executor);

        assertThat(config.getTimeout("any-channel-id")).isEqualTo(Duration.ofSeconds(2));
    }

    @Test
    void shouldReturnNumberDelay() {
        ExternalFindIdentityStubConfig config = ExternalFindIdentityStubConfig.builder()
                .phoneNumberDelay(OTHER_DELAY)
                .build();

        assertThat(config.getPhoneNumberDelay()).isEqualTo(OTHER_DELAY);
    }

    @Test
    void shouldReturnEmailAddressDelay() {
        ExternalFindIdentityStubConfig config = ExternalFindIdentityStubConfig.builder()
                .emailAddressDelay(OTHER_DELAY)
                .build();

        assertThat(config.getEmailAddressDelay()).isEqualTo(OTHER_DELAY);
    }

    @Test
    void shouldReturnTimeoutForAllChannelsIfSet() {
        Duration timeout = Duration.ofSeconds(5);

        ExternalFindIdentityStubConfig config = ExternalFindIdentityStubConfig.builder()
                .timeout(timeout)
                .build();

        assertThat(config.getTimeout("any-channel-id")).isEqualTo(timeout);
    }

}
