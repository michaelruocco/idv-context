package uk.co.idv.context.adapter.stub.identity;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.stub.identity.data.Delay;

import java.util.concurrent.ExecutorService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class StubIdentityFinderConfigTest {

    private static final Delay NO_DELAY = new Delay(0);
    private static final Delay OTHER_DELAY = new Delay(5);

    @Test
    void shouldReturnExecutor() {
        ExecutorService executor = mock(ExecutorService.class);

        StubIdentityFinderConfig config = StubIdentityFinderConfig.build(executor);

        assertThat(config.getExecutor()).isEqualTo(executor);
    }

    @Test
    void shouldReturnNoAliasDelayIfNotSet() {
        ExecutorService executor = mock(ExecutorService.class);

        StubIdentityFinderConfig config = StubIdentityFinderConfig.build(executor);

        assertThat(config.getAliasDelay()).isEqualTo(NO_DELAY);
    }

    @Test
    void shouldReturnNoPhoneNumberDelayIfNotSet() {
        ExecutorService executor = mock(ExecutorService.class);

        StubIdentityFinderConfig config = StubIdentityFinderConfig.build(executor);

        assertThat(config.getPhoneNumberDelay()).isEqualTo(NO_DELAY);
    }

    @Test
    void shouldReturnNoEmailAddressDelayIfNotSet() {
        ExecutorService executor = mock(ExecutorService.class);

        StubIdentityFinderConfig config = StubIdentityFinderConfig.build(executor);

        assertThat(config.getEmailAddressDelay()).isEqualTo(NO_DELAY);
    }

    @Test
    void shouldReturnAliasDelay() {
        StubIdentityFinderConfig config = StubIdentityFinderConfig.builder()
                .aliasDelay(OTHER_DELAY)
                .build();

        assertThat(config.getAliasDelay()).isEqualTo(OTHER_DELAY);
    }

    @Test
    void shouldReturnNumberDelay() {
        StubIdentityFinderConfig config = StubIdentityFinderConfig.builder()
                .phoneNumberDelay(OTHER_DELAY)
                .build();

        assertThat(config.getPhoneNumberDelay()).isEqualTo(OTHER_DELAY);
    }

    @Test
    void shouldReturnEmailAddressDelay() {
        StubIdentityFinderConfig config = StubIdentityFinderConfig.builder()
                .emailAddressDelay(OTHER_DELAY)
                .build();

        assertThat(config.getEmailAddressDelay()).isEqualTo(OTHER_DELAY);
    }
}
