package uk.co.idv.context.adapter.json.error.policynotfound;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.context.usecases.policy.load.PolicyNotFoundException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class PolicyNotFoundHandlerTest {

    private final ErrorHandler handler = new PolicyNotFoundHandler();

    @Test
    void shouldSupportPolicyNotFoundException() {
        PolicyNotFoundException exception = mock(PolicyNotFoundException.class);

        assertThat(handler.supports(exception)).isTrue();
    }

    @Test
    void shouldNotSupportAnyOtherException() {
        Throwable other = new Throwable();

        assertThat(handler.supports(other)).isFalse();
    }

    @Test
    void shouldReturnPolicyNotFoundError() {
        PolicyNotFoundException exception = new PolicyNotFoundException(UUID.randomUUID());

        ApiError error = handler.apply(exception);

        assertThat(error).isInstanceOf(PolicyNotFoundError.class);
    }

    @Test
    void shouldPopulateMessageWithPolicyId() {
        UUID id = UUID.randomUUID();
        PolicyNotFoundException exception = new PolicyNotFoundException(id);

        ApiError error = handler.apply(exception);

        assertThat(error.getMessage()).isEqualTo(id.toString());
    }

}
