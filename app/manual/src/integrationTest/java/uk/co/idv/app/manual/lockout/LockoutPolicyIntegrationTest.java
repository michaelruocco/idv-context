package uk.co.idv.app.manual.lockout;

import org.junit.jupiter.api.Test;
import uk.co.idv.app.manual.Application;
import uk.co.idv.app.manual.TestHarness;
import uk.co.idv.app.manual.otp.AbcPolicyMother;
import uk.co.idv.app.manual.otp.GbRsaPolicyMother;
import uk.co.idv.lockout.entities.policy.LockoutPolicy;
import uk.co.idv.lockout.entities.policy.LockoutPolicyMother;
import uk.co.idv.lockout.entities.policy.hard.HardLockoutPolicyMother;
import uk.co.idv.lockout.entities.policy.hard.HardLockoutStateCalculator;
import uk.co.idv.lockout.entities.policy.hard.HardLockoutStateCalculatorMother;
import uk.co.idv.lockout.entities.policy.includeattempt.IncludeAllAttemptsPolicy;
import uk.co.idv.lockout.entities.policy.includeattempt.IncludeAttemptsWithinDurationPolicyMother;
import uk.co.idv.lockout.entities.policy.recordattempt.AlwaysRecordAttemptPolicy;
import uk.co.idv.lockout.entities.policy.recordattempt.RecordAttemptWhenMethodCompletePolicy;
import uk.co.idv.policy.entities.policy.Policies;
import uk.co.idv.policy.entities.policy.PolicyNotFoundException;
import uk.co.idv.policy.entities.policy.key.PolicyKey;
import uk.co.idv.policy.entities.policy.PolicyRequest;
import uk.co.idv.policy.entities.policy.PolicyRequestMother;
import uk.co.idv.policy.entities.policy.key.ChannelPolicyKeyMother;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static uk.co.idv.lockout.entities.policy.hard.HardLockoutStateCalculatorMother.withIncludeAttemptsPolicy;

class LockoutPolicyIntegrationTest {

    private final TestHarness harness = new TestHarness();
    private final Application application = harness.getApplication();

    @Test
    void shouldThrowExceptionIfPolicyNotFoundById() {
        UUID id = UUID.randomUUID();

        Throwable error = catchThrowable(() -> application.loadLockoutPolicy(id));

        assertThat(error)
                .isInstanceOf(PolicyNotFoundException.class)
                .hasMessage(id.toString());
    }

    @Test
    void shouldReturnEmptyPoliciesByKeyIfNoPoliciesFound() {
        PolicyKey key = ChannelPolicyKeyMother.build();

        Policies<LockoutPolicy> policies = application.loadLockoutPolicies(key);

        assertThat(policies).isEmpty();
    }

    @Test
    void shouldReturnEmptyPoliciesIfLoadAllWhenNoPoliciesSaved() {
        Policies<LockoutPolicy> policies = application.loadAllLockoutPolicies();

        assertThat(policies).isEmpty();
    }

    @Test
    void shouldLoadCreatedLockoutPolicyById() {
        LockoutPolicy expected = HardLockoutPolicyMother.build();
        application.create(expected);

        LockoutPolicy loaded = application.loadLockoutPolicy(expected.getId());

        assertThat(loaded).isEqualTo(expected);
    }

    @Test
    void shouldLoadCreatedLockoutPolicyByPolicyRequest() {
        LockoutPolicy expected = HardLockoutPolicyMother.build();
        application.create(expected);
        PolicyRequest request = PolicyRequestMother.build();

        Policies<LockoutPolicy> loaded = application.loadLockoutPolicies(request);

        assertThat(loaded).containsExactly(expected);
    }

    @Test
    void shouldLoadCreatedLockoutPolicyByKey() {
        LockoutPolicy expected = HardLockoutPolicyMother.build();
        application.create(expected);

        Policies<LockoutPolicy> loaded = application.loadLockoutPolicies(expected.getKey());

        assertThat(loaded).containsExactly(expected);
    }

    @Test
    void shouldLoadCreatedLockoutPolicyWhenAllPoliciesLoaded() {
        LockoutPolicy expected = HardLockoutPolicyMother.build();
        application.create(expected);

        Policies<LockoutPolicy> loaded = application.loadAllLockoutPolicies();

        assertThat(loaded).containsExactly(expected);
    }

    @Test
    void shouldThrowExceptionIfAttemptToUpdatePolicyThatDoesNotExist() {
        LockoutPolicy policy = HardLockoutPolicyMother.build();

        Throwable error = catchThrowable(() -> application.update(policy));

        assertThat(error)
                .isInstanceOf(PolicyNotFoundException.class)
                .hasMessage(policy.getId().toString());
    }

    @Test
    void shouldUpdatePolicy() {
        LockoutPolicy initial = HardLockoutPolicyMother.withMaxNumberOfAttempts(2);
        application.create(initial);
        LockoutPolicy update = HardLockoutPolicyMother.withMaxNumberOfAttempts(4);

        application.update(update);

        LockoutPolicy loaded = application.loadLockoutPolicy(update.getId());
        assertThat(loaded).isEqualTo(update);
    }

    @Test
    void shouldUpdateRecordAttemptPolicyWithinPolicy() {
        LockoutPolicy initial = HardLockoutPolicyMother.withRecordAttemptPolicy(new AlwaysRecordAttemptPolicy());
        application.create(initial);
        LockoutPolicy update = HardLockoutPolicyMother.withRecordAttemptPolicy(new RecordAttemptWhenMethodCompletePolicy());

        application.update(update);

        LockoutPolicy loaded = application.loadLockoutPolicy(update.getId());
        assertThat(loaded).isEqualTo(update);
    }

    @Test
    void shouldUpdateIncludeAttemptPolicyOnLockoutStateCalculatorWithinPolicy() {
        HardLockoutStateCalculator initialStateCalculator = withIncludeAttemptsPolicy(new IncludeAllAttemptsPolicy());
        LockoutPolicy initial = HardLockoutPolicyMother.withStateCalculator(initialStateCalculator);
        application.create(initial);
        HardLockoutStateCalculator updateStateCalculator = withIncludeAttemptsPolicy(IncludeAttemptsWithinDurationPolicyMother.build());
        LockoutPolicy update = HardLockoutPolicyMother.withStateCalculator(updateStateCalculator);

        application.update(update);

        LockoutPolicy loaded = application.loadLockoutPolicy(update.getId());
        assertThat(loaded).isEqualTo(update);
    }

    @Test
    void shouldDeletePolicy() {
        LockoutPolicy policy1 = HardLockoutPolicyMother.withId(UUID.randomUUID());
        LockoutPolicy policy2 = HardLockoutPolicyMother.withId(UUID.randomUUID());
        application.create(policy1);
        application.create(policy2);

        application.deleteLockoutPolicy(policy1.getId());

        Policies<LockoutPolicy> policies = application.loadAllLockoutPolicies();
        assertThat(policies).containsExactly(policy2);
    }

    @Test
    void shouldPopulateConfiguredPolicies() {
        application.populatePolicies(harness.getChannelAdapter());

        Policies<LockoutPolicy> policies = application.loadAllLockoutPolicies();

        assertThat(policies).containsExactlyInAnyOrder(
                AbcPolicyMother.abcLockoutPolicy(),
                GbRsaPolicyMother.gbRsaLockoutPolicy()
        );
    }

    @Test
    void shouldNotOverwriteExistingPoliciesWhenPopulatingConfiguredPolicies() {
        LockoutPolicy abcPolicy = AbcPolicyMother.abcLockoutPolicy();
        LockoutPolicy existingPolicy = LockoutPolicyMother.builder()
                .key(abcPolicy.getKey())
                .stateCalculator(HardLockoutStateCalculatorMother.build())
                .build();
        application.create(existingPolicy);

        application.populatePolicies(harness.getChannelAdapter());

        Policies<LockoutPolicy> policies = application.loadAllLockoutPolicies();
        assertThat(policies).containsExactlyInAnyOrder(
                existingPolicy,
                GbRsaPolicyMother.gbRsaLockoutPolicy()
        );
    }

}
