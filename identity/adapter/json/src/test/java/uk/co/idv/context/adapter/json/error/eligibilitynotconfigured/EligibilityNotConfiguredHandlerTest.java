package uk.co.idv.context.adapter.json.error.eligibilitynotconfigured;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.context.usecases.eligibility.EligibilityNotConfiguredException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class EligibilityNotConfiguredHandlerTest {

    private final ErrorHandler handler = new EligibilityNotConfiguredHandler();

    @Test
    void shouldSupportEligibilityNotConfiguredException() {
        EligibilityNotConfiguredException exception = mock(EligibilityNotConfiguredException.class);

        assertThat(handler.supports(exception)).isTrue();
    }

    @Test
    void shouldNotSupportAnyOtherException() {
        Throwable other = new Throwable();

        assertThat(handler.supports(other)).isFalse();
    }

    @Test
    public void shouldReturnEligibilityNotConfiguredError() {
        EligibilityNotConfiguredException exception = new EligibilityNotConfiguredException("my-channel");

        ApiError error = handler.apply(exception);

        assertThat(error).isInstanceOf(EligibilityNotConfiguredError.class);
    }

    @Test
    public void shouldPopulateMessageWithAliases() {
        String channelId = "my-channel";
        Throwable exception = new EligibilityNotConfiguredException(channelId);

        ApiError error = handler.apply(exception);

        assertThat(error.getMessage()).contains(channelId);
    }

}
