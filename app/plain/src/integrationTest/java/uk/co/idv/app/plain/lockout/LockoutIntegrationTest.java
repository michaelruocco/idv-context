package uk.co.idv.app.plain.lockout;

import org.junit.jupiter.api.Test;
import uk.co.idv.app.plain.Application;
import uk.co.idv.app.plain.TestHarness;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.IdentityMother;
import uk.co.idv.identity.entities.identity.IdentityNotFoundException;
import uk.co.idv.lockout.entities.DefaultExternalLockoutRequestMother;
import uk.co.idv.lockout.entities.ExternalLockoutRequest;
import uk.co.idv.lockout.entities.policy.NoLockoutPoliciesConfiguredException;
import uk.co.idv.lockout.entities.policy.hard.HardLockoutPolicyMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class LockoutIntegrationTest {

    private final TestHarness harness = new TestHarness();
    private final Application application = harness.getApplication();

    @Test
    void shouldThrowExceptionIfIdentityDoesNotExistOnLoadState() {
        ExternalLockoutRequest request = DefaultExternalLockoutRequestMother.build();

        Throwable error = catchThrowable(() -> application.loadLockoutState(request));

        assertThat(error).isInstanceOf(IdentityNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionIfIdentityDoesNotExistOnResetState() {
        ExternalLockoutRequest request = DefaultExternalLockoutRequestMother.build();

        Throwable error = catchThrowable(() -> application.resetLockoutState(request));

        assertThat(error).isInstanceOf(IdentityNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionOnLoadStateForIdentityThatDoesNotExist() {
        application.create(HardLockoutPolicyMother.build());
        ExternalLockoutRequest externalRequest = DefaultExternalLockoutRequestMother.build();

        Throwable error = catchThrowable(() -> application.loadLockoutState(externalRequest));

        assertThat(error)
                .isInstanceOf(IdentityNotFoundException.class)
                .hasMessage(externalRequest.getAliases().format());
    }

    @Test
    void shouldThrowExceptionOnLoadStateIfNoPoliciesConfigured() {
        Identity identity = IdentityMother.example();
        application.update(identity);
        ExternalLockoutRequest externalRequest = DefaultExternalLockoutRequestMother.withAlias(identity.getIdvId());

        Throwable error = catchThrowable(() -> application.loadLockoutState(externalRequest));

        assertThat(error).isInstanceOf(NoLockoutPoliciesConfiguredException.class);
    }

    @Test
    void shouldThrowExceptionOnResetStateForIdentityThatDoesNotExist() {
        application.create(HardLockoutPolicyMother.build());
        ExternalLockoutRequest externalRequest = DefaultExternalLockoutRequestMother.build();

        Throwable error = catchThrowable(() -> application.resetLockoutState(externalRequest));

        assertThat(error)
                .isInstanceOf(IdentityNotFoundException.class)
                .hasMessage(externalRequest.getAliases().format());
    }

    @Test
    void shouldThrowExceptionOnResetStateIfNoPoliciesConfigured() {
        Identity identity = IdentityMother.example();
        application.update(identity);
        ExternalLockoutRequest externalRequest = DefaultExternalLockoutRequestMother.withAlias(identity.getIdvId());

        Throwable error = catchThrowable(() -> application.resetLockoutState(externalRequest));

        assertThat(error).isInstanceOf(NoLockoutPoliciesConfiguredException.class);
    }

}
