package uk.co.idv.app.manual.lockout;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.adapter.eligibility.external.StubExternalFindIdentityConfig;
import uk.co.idv.identity.config.DefaultIdentityConfig;
import uk.co.idv.identity.config.ExternalFindIdentityConfig;
import uk.co.idv.identity.config.IdentityConfig;
import uk.co.idv.lockout.config.LockoutConfig;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.IdentityMother;
import uk.co.idv.lockout.entities.DefaultExternalLockoutRequestMother;
import uk.co.idv.lockout.entities.ExternalLockoutRequest;
import uk.co.idv.lockout.entities.policy.hard.HardLockoutPolicyMother;
import uk.co.idv.identity.usecases.identity.IdentityService;
import uk.co.idv.identity.usecases.identity.find.IdentityNotFoundException;
import uk.co.idv.lockout.usecases.LockoutFacade;
import uk.co.idv.lockout.usecases.policy.LockoutPolicyService;
import uk.co.idv.lockout.usecases.policy.NoLockoutPoliciesConfiguredException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class LockoutIntegrationTest {

    private final ExternalFindIdentityConfig findIdentityConfig = StubExternalFindIdentityConfig.build();
    private final IdentityConfig identityConfig = DefaultIdentityConfig.build(findIdentityConfig);
    private final IdentityService identityService = identityConfig.identityService();

    private final LockoutConfigBuilder lockoutConfigBuilder = LockoutConfigBuilder.builder()
            .identityConfig(identityConfig)
            .build();

    private final LockoutConfig lockoutConfig = lockoutConfigBuilder.build();
    private final LockoutPolicyService policyService = lockoutConfig.getPolicyService();
    private final LockoutFacade lockoutFacade = lockoutConfig.getFacade();

    @Test
    void shouldThrowExceptionIfIdentityDoesNotExistOnLoadState() {
        ExternalLockoutRequest request = DefaultExternalLockoutRequestMother.build();

        Throwable error = catchThrowable(() -> lockoutFacade.loadState(request));

        assertThat(error).isInstanceOf(IdentityNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionIfIdentityDoesNotExistOnResetState() {
        ExternalLockoutRequest request = DefaultExternalLockoutRequestMother.build();

        Throwable error = catchThrowable(() -> lockoutFacade.resetState(request));

        assertThat(error).isInstanceOf(IdentityNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionOnLoadStateForIdentityThatDoesNotExist() {
        policyService.create(HardLockoutPolicyMother.build());
        ExternalLockoutRequest externalRequest = DefaultExternalLockoutRequestMother.build();

        Throwable error = catchThrowable(() -> lockoutFacade.loadState(externalRequest));

        assertThat(error)
                .isInstanceOf(IdentityNotFoundException.class)
                .hasMessage(externalRequest.getAliases().format());
    }

    @Test
    void shouldThrowExceptionOnLoadStateIfNoPoliciesConfigured() {
        Identity identity = IdentityMother.example();
        identityService.update(identity);
        ExternalLockoutRequest externalRequest = DefaultExternalLockoutRequestMother.withAlias(identity.getIdvId());

        Throwable error = catchThrowable(() -> lockoutFacade.loadState(externalRequest));

        assertThat(error).isInstanceOf(NoLockoutPoliciesConfiguredException.class);
    }

    @Test
    void shouldThrowExceptionOnResetStateForIdentityThatDoesNotExist() {
        policyService.create(HardLockoutPolicyMother.build());
        ExternalLockoutRequest externalRequest = DefaultExternalLockoutRequestMother.build();

        Throwable error = catchThrowable(() -> lockoutFacade.resetState(externalRequest));

        assertThat(error)
                .isInstanceOf(IdentityNotFoundException.class)
                .hasMessage(externalRequest.getAliases().format());
    }

    @Test
    void shouldThrowExceptionOnResetStateIfNoPoliciesConfigured() {
        Identity identity = IdentityMother.example();
        identityService.update(identity);
        ExternalLockoutRequest externalRequest = DefaultExternalLockoutRequestMother.withAlias(identity.getIdvId());

        Throwable error = catchThrowable(() -> lockoutFacade.resetState(externalRequest));

        assertThat(error).isInstanceOf(NoLockoutPoliciesConfiguredException.class);
    }

}
