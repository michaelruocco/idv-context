package uk.co.idv.app.manual.lockout;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.adapter.json.error.ApiError;
import uk.co.idv.identity.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.identity.adapter.json.error.policynotfound.PolicyNotFoundError;
import uk.co.idv.context.config.lockout.LockoutConfig;
import uk.co.idv.context.usecases.policy.load.PolicyNotFoundException;
import uk.co.idv.context.usecases.policy.load.PolicyNotFoundExceptionMother;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class LockoutErrorHandlerIntegrationTest {

    private final LockoutConfig config = LockoutConfig.builder()
            .build();

    private final ErrorHandler handler = config.errorHandler();

    @Test
    void shouldHandlePolicyNotFoundException() {
        PolicyNotFoundException exception = PolicyNotFoundExceptionMother.build();

        Optional<ApiError> error = handler.apply(exception);

        assertThat(error).containsInstanceOf(PolicyNotFoundError.class);
    }

    @Test
    void shouldReturnEmptyIfExceptionNotSupported() {
        Throwable throwable = new Throwable("error message");

        Optional<ApiError> error = handler.apply(throwable);

        assertThat(error).isEmpty();
    }

}
