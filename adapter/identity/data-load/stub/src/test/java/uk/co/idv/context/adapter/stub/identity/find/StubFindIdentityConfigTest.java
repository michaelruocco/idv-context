package uk.co.idv.context.adapter.stub.identity.find;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.stub.identity.find.data.Delay;

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
    void shouldReturnNoAliasDelayIfNotSet() {
        ExecutorService executor = mock(ExecutorService.class);

        StubFindIdentityConfig config = StubFindIdentityConfig.build(executor);

        assertThat(config.getAliasDelay()).isEqualTo(NO_DELAY);
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
    void shouldReturnAliasDelay() {
        StubFindIdentityConfig config = StubFindIdentityConfig.builder()
                .aliasDelay(OTHER_DELAY)
                .build();

        assertThat(config.getAliasDelay()).isEqualTo(OTHER_DELAY);
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

}
