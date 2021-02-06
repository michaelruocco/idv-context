package uk.co.idv.identity.adapter.json.error.eligibilitynotconfigured;

import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.error.ApiError;
import uk.co.idv.common.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.identity.entities.eligibility.EligibilityNotConfiguredException;
import uk.co.idv.identity.entities.eligibility.EligibilityNotConfiguredExceptionMother;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class EligibilityNotConfiguredHandlerTest {

    private final ErrorHandler handler = new EligibilityNotConfiguredHandler();

    @Test
    void shouldConvertEligibilityNotConfiguredExceptionToError() {
        EligibilityNotConfiguredException exception = EligibilityNotConfiguredExceptionMother.build();

        Optional<ApiError> error = handler.apply(exception);

        assertThat(error).containsInstanceOf(EligibilityNotConfiguredError.class);
    }

    @Test
    void shouldPopulateErrorMessageWithExceptionMessage() {
        EligibilityNotConfiguredException exception = EligibilityNotConfiguredExceptionMother.build();

        Optional<ApiError> error = handler.apply(exception);

        String expectedMessage = String.format("eligibility not configured for channel %s", exception.getMessage());
        assertThat(error).get().hasFieldOrPropertyWithValue("message", expectedMessage);
    }

    @Test
    void shouldNotSupportAnyOtherException() {
        Throwable other = new Throwable();

        Optional<ApiError> error = handler.apply(other);

        assertThat(error).isEmpty();
    }

}
