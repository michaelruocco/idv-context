package uk.co.idv.context.adapter.json.error.policynotfound;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.context.usecases.policy.load.PolicyNotFoundException;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class PolicyNotFoundHandlerTest {

    private final ErrorHandler handler = new PolicyNotFoundHandler();

    @Test
    void shouldSupportPolicyNotFoundException() {
        PolicyNotFoundException exception = mock(PolicyNotFoundException.class);

        assertThat(handler.apply(exception)).isPresent();
    }

    @Test
    void shouldNotSupportAnyOtherException() {
        Throwable other = new Throwable();

        assertThat(handler.apply(other)).isEmpty();
    }

    @Test
    void shouldReturnPolicyNotFoundError() {
        PolicyNotFoundException exception = new PolicyNotFoundException(UUID.randomUUID());

        Optional<ApiError> error = handler.apply(exception);

        assertThat(error).containsInstanceOf(PolicyNotFoundError.class);
    }

    @Test
    void shouldPopulateMessageWithPolicyId() {
        UUID id = UUID.randomUUID();
        PolicyNotFoundException exception = new PolicyNotFoundException(id);

        Optional<ApiError> error = handler.apply(exception);

        assertThat(error).isPresent();
        assertThat(error.get().getMessage()).isEqualTo(id.toString());
    }

}
