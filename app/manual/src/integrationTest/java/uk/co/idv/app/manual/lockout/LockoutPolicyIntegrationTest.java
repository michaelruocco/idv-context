package uk.co.idv.app.manual.lockout;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.config.lockout.LockoutConfig;
import uk.co.idv.context.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.context.entities.lockout.policy.hard.HardLockoutPolicyMother;
import uk.co.idv.context.entities.policy.Policies;
import uk.co.idv.context.entities.policy.PolicyKey;
import uk.co.idv.context.entities.policy.PolicyRequest;
import uk.co.idv.context.entities.policy.PolicyRequestMother;
import uk.co.idv.context.entities.policy.key.ChannelPolicyKeyMother;
import uk.co.idv.context.usecases.lockout.policy.LockoutPolicyService;
import uk.co.idv.context.usecases.policy.load.PolicyNotFoundException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class LockoutPolicyIntegrationTest {

    private final LockoutConfigBuilder lockoutConfigBuilder = LockoutConfigBuilder.builder().build();
    private final LockoutConfig lockoutConfig = lockoutConfigBuilder.build();

    private final LockoutPolicyService policyService = lockoutConfig.policyService();

    @Test
    void shouldThrowExceptionIfPolicyNotFoundById() {
        LockoutPolicy policy = HardLockoutPolicyMother.build();

        Throwable error = catchThrowable(() -> policyService.load(policy.getId()));

        assertThat(error)
                .isInstanceOf(PolicyNotFoundException.class)
                .hasMessage(policy.getId().toString());
    }

    @Test
    void shouldReturnEmptyPoliciesByKeyIfNoPoliciesFound() {
        PolicyKey key = ChannelPolicyKeyMother.build();

        Policies<LockoutPolicy> policies = policyService.load(key);

        assertThat(policies).isEmpty();
    }

    @Test
    void shouldReturnEmptyPoliciesIfLoadAllWhenNoPoliciesSaved() {
        Policies<LockoutPolicy> policies = policyService.loadAll();

        assertThat(policies).isEmpty();
    }

    @Test
    void shouldLoadCreatedLockoutPolicyById() {
        LockoutPolicy expected = HardLockoutPolicyMother.build();
        policyService.create(expected);

        LockoutPolicy loaded = policyService.load(expected.getId());

        assertThat(loaded).isEqualTo(expected);
    }

    @Test
    void shouldLoadCreatedLockoutPolicyByPolicyRequest() {
        LockoutPolicy expected = HardLockoutPolicyMother.build();
        policyService.create(expected);
        PolicyRequest request = PolicyRequestMother.build();

        Policies<LockoutPolicy> loaded = policyService.load(request);

        assertThat(loaded).containsExactly(expected);
    }

    @Test
    void shouldLoadCreatedLockoutPolicyByKey() {
        LockoutPolicy expected = HardLockoutPolicyMother.build();
        policyService.create(expected);

        Policies<LockoutPolicy> loaded = policyService.load(expected.getKey());

        assertThat(loaded).containsExactly(expected);
    }

    @Test
    void shouldLoadCreatedLockoutPolicyWhenAllPoliciesLoaded() {
        LockoutPolicy expected = HardLockoutPolicyMother.build();
        policyService.create(expected);

        Policies<LockoutPolicy> loaded = policyService.loadAll();

        assertThat(loaded).containsExactly(expected);
    }

    @Test
    void shouldThrowExceptionIfAttemptToUpdatePolicyThatDoesNotExist() {
        LockoutPolicy policy = HardLockoutPolicyMother.build();

        Throwable error = catchThrowable(() -> policyService.update(policy));

        assertThat(error)
                .isInstanceOf(PolicyNotFoundException.class)
                .hasMessage(policy.getId().toString());
    }

    @Test
    void shouldUpdatePolicy() {
        LockoutPolicy initial = HardLockoutPolicyMother.withMaxNumberOfAttempts(2);
        policyService.create(initial);
        LockoutPolicy update = HardLockoutPolicyMother.withMaxNumberOfAttempts(4);

        policyService.update(update);

        LockoutPolicy loaded = policyService.load(update.getId());
        assertThat(loaded).isEqualTo(update);
    }

    @Test
    void shouldDeletePolicy() {
        LockoutPolicy policy1 = HardLockoutPolicyMother.withId(UUID.randomUUID());
        LockoutPolicy policy2 = HardLockoutPolicyMother.withId(UUID.randomUUID());
        policyService.create(policy1);
        policyService.create(policy2);

        policyService.delete(policy1.getId());

        Policies<LockoutPolicy> policies = policyService.loadAll();
        assertThat(policies).containsExactly(policy2);
    }

}
