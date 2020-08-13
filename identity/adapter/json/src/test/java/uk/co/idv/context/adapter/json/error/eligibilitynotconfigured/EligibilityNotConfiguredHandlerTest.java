package uk.co.idv.context.adapter.json.error.eligibilitynotconfigured;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.context.usecases.eligibility.EligibilityNotConfiguredException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class EligibilityNotConfiguredHandlerTest {

    private final ErrorHandler handler = new EligibilityNotConfiguredHandler();

    @Test
    void shouldSupportEligibilityNotConfiguredException() {
        EligibilityNotConfiguredException exception = new EligibilityNotConfiguredException("my-channel");

        assertThat(handler.apply(exception)).isPresent();
    }

    @Test
    void shouldNotSupportAnyOtherException() {
        Throwable other = new Throwable();

        assertThat(handler.apply(other)).isEmpty();
    }

    @Test
    void shouldReturnEligibilityNotConfiguredError() {
        EligibilityNotConfiguredException exception = new EligibilityNotConfiguredException("my-channel");

        Optional<ApiError> error = handler.apply(exception);

        assertThat(error).containsInstanceOf(EligibilityNotConfiguredError.class);
    }

    @Test
    void shouldPopulateMessageWithAliases() {
        String channelId = "my-channel";
        Throwable exception = new EligibilityNotConfiguredException(channelId);

        Optional<ApiError> error = handler.apply(exception);

        assertThat(error).isPresent();
        assertThat(error.get().getMessage()).contains(channelId);
    }

}
