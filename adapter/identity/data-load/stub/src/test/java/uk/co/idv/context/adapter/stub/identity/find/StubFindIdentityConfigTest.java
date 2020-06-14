package uk.co.idv.context.adapter.stub.identity.find;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.usecases.identity.find.data.Delay;

import java.time.Duration;
import java.util.concurrent.ExecutorService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class StubFindIdentityConfigTest {

    private static final Delay NO_DELAY = new Delay(0);
    private static final Delay OTHER_DELAY = new Delay(5);

    @Test
    void shouldReturnExecutor() {
        ExecutorService executor = mock(ExecutorService.class);

        StubFindIdentityConfig config = StubFindIdentityConfig.build(executor);

        assertThat(config.getExecutor()).isEqualTo(executor);
    }

    @Test
    void shouldReturnNoPhoneNumberDelayIfNotSet() {
        ExecutorService executor = mock(ExecutorService.class);

        StubFindIdentityConfig config = StubFindIdentityConfig.build(executor);

        assertThat(config.getPhoneNumberDelay()).isEqualTo(NO_DELAY);
    }

    @Test
    void shouldReturnNoEmailAddressDelayIfNotSet() {
        ExecutorService executor = mock(ExecutorService.class);

        StubFindIdentityConfig config = StubFindIdentityConfig.build(executor);

        assertThat(config.getEmailAddressDelay()).isEqualTo(NO_DELAY);
    }

    @Test
    void shouldReturn2SecondTimeoutForAllChannelsIfNotSet() {
        ExecutorService executor = mock(ExecutorService.class);

        StubFindIdentityConfig config = StubFindIdentityConfig.build(executor);

        assertThat(config.getTimeout("any-channel-id")).isEqualTo(Duration.ofSeconds(2));
    }

    @Test
    void shouldReturnNumberDelay() {
        StubFindIdentityConfig config = StubFindIdentityConfig.builder()
                .phoneNumberDelay(OTHER_DELAY)
                .build();

        assertThat(config.getPhoneNumberDelay()).isEqualTo(OTHER_DELAY);
    }

    @Test
    void shouldReturnEmailAddressDelay() {
        StubFindIdentityConfig config = StubFindIdentityConfig.builder()
                .emailAddressDelay(OTHER_DELAY)
                .build();

        assertThat(config.getEmailAddressDelay()).isEqualTo(OTHER_DELAY);
    }

    @Test
    void shouldReturnTimeoutForAllChannelsIfSet() {
        Duration timeout = Duration.ofSeconds(5);

        StubFindIdentityConfig config = StubFindIdentityConfig.builder()
                .timeout(timeout)
                .build();

        assertThat(config.getTimeout("any-channel-id")).isEqualTo(timeout);
    }

}
