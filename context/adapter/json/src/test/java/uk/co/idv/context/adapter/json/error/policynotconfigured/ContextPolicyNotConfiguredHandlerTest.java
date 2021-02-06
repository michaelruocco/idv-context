package uk.co.idv.context.adapter.json.error.policynotconfigured;

import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.error.ApiError;
import uk.co.idv.common.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.context.entities.policy.NoContextPoliciesConfiguredException;
import uk.co.idv.context.entities.policy.NoContextPoliciesConfiguredExceptionMother;
import uk.co.idv.policy.entities.policy.PolicyRequest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ContextPolicyNotConfiguredHandlerTest {

    private final ErrorHandler handler = new ContextPolicyNotConfiguredHandler();

    @Test
    void shouldConvertNoContextPoliciesConfiguredExceptionToError() {
        NoContextPoliciesConfiguredException exception = NoContextPoliciesConfiguredExceptionMother.build();

        Optional<ApiError> error = handler.apply(exception);

        assertThat(error).containsInstanceOf(ContextPolicyNotConfiguredError.class);
    }

    @Test
    void shouldPopulateMetaWithChannelIdFromPolicyRequest() {
        NoContextPoliciesConfiguredException exception = NoContextPoliciesConfiguredExceptionMother.build();

        Optional<ApiError> error = handler.apply(exception);

        PolicyRequest request = exception.getRequest();
        assertThat(error).map(ApiError::getMeta)
                .map(meta -> meta.get("channelId"))
                .contains(request.getChannelId());
    }

    @Test
    void shouldPopulateMetaWithActivityNameFromPolicyRequest() {
        NoContextPoliciesConfiguredException exception = NoContextPoliciesConfiguredExceptionMother.build();

        Optional<ApiError> error = handler.apply(exception);

        PolicyRequest request = exception.getRequest();
        assertThat(error).map(ApiError::getMeta)
                .map(meta -> meta.get("activityName"))
                .contains(request.getActivityName());
    }

    @Test
    void shouldPopulateMetaWithAliasTypeFromPolicyRequest() {
        NoContextPoliciesConfiguredException exception = NoContextPoliciesConfiguredExceptionMother.build();

        Optional<ApiError> error = handler.apply(exception);

        PolicyRequest request = exception.getRequest();
        assertThat(error).map(ApiError::getMeta)
                .map(meta -> meta.get("aliasTypes"))
                .contains(request.getAliasTypes());
    }

    @Test
    void shouldNotSupportAnyOtherException() {
        Throwable other = new Throwable();

        Optional<ApiError> error = handler.apply(other);

        assertThat(error).isEmpty();
    }

}
