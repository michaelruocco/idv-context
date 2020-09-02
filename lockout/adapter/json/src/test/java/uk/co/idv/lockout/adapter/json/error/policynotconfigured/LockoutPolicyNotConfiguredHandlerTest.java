package uk.co.idv.lockout.adapter.json.error.policynotconfigured;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.adapter.json.error.ApiError;
import uk.co.idv.identity.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.context.entities.policy.PolicyRequest;
import uk.co.idv.lockout.usecases.policy.NoLockoutPoliciesConfiguredException;
import uk.co.idv.lockout.usecases.policy.NoLockoutPoliciesConfiguredExceptionMother;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class LockoutPolicyNotConfiguredHandlerTest {

    private final ErrorHandler handler = new LockoutPolicyNotConfiguredHandler();

    @Test
    void shouldConvertNoLockoutPoliciesConfiguredExceptionToError() {
        NoLockoutPoliciesConfiguredException exception = NoLockoutPoliciesConfiguredExceptionMother.build();

        Optional<ApiError> error = handler.apply(exception);

        assertThat(error).containsInstanceOf(LockoutPolicyNotConfiguredError.class);
    }

    @Test
    void shouldPopulateMetaWithChannelIdFromPolicyRequest() {
        NoLockoutPoliciesConfiguredException exception = NoLockoutPoliciesConfiguredExceptionMother.build();

        Optional<ApiError> error = handler.apply(exception);

        PolicyRequest request = exception.getRequest();
        assertThat(error).map(ApiError::getMeta)
                .map(meta -> meta.get("channelId"))
                .contains(request.getChannelId());
    }

    @Test
    void shouldPopulateMetaWithActivityNameFromPolicyRequest() {
        NoLockoutPoliciesConfiguredException exception = NoLockoutPoliciesConfiguredExceptionMother.build();

        Optional<ApiError> error = handler.apply(exception);

        PolicyRequest request = exception.getRequest();
        assertThat(error).map(ApiError::getMeta)
                .map(meta -> meta.get("activityName"))
                .contains(request.getActivityName());
    }

    @Test
    void shouldPopulateMetaWithAliasTypeFromPolicyRequest() {
        NoLockoutPoliciesConfiguredException exception = NoLockoutPoliciesConfiguredExceptionMother.build();

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
