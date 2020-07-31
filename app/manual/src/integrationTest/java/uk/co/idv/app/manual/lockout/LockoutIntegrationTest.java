package uk.co.idv.app.manual.lockout;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.config.lockout.LockoutConfig;
import uk.co.idv.context.config.lockout.repository.LockoutRepositoryConfig;
import uk.co.idv.context.config.lockout.repository.inmemory.InMemoryLockoutRepositoryConfig;
import uk.co.idv.context.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.context.entities.lockout.policy.hard.HardLockoutPolicyMother;
import uk.co.idv.context.entities.policy.Policies;
import uk.co.idv.context.entities.policy.PolicyKey;
import uk.co.idv.context.entities.policy.PolicyRequest;
import uk.co.idv.context.entities.policy.PolicyRequestMother;
import uk.co.idv.context.entities.policy.key.ChannelPolicyKeyMother;
import uk.co.idv.context.usecases.lockout.LockoutPolicyService;
import uk.co.idv.context.usecases.policy.load.PolicyNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class LockoutIntegrationTest {

    private final LockoutRepositoryConfig repositoryConfig = new InMemoryLockoutRepositoryConfig();
    private final LockoutConfig lockoutConfig = new LockoutConfig(repositoryConfig.policyRepository());

    private final LockoutPolicyService policyService = lockoutConfig.policyService();

    @Test
    void shouldThrowExceptionIfPolicyNotFoundById() {
        LockoutPolicy policy = HardLockoutPolicyMother.hard();

        Throwable error = catchThrowable(() -> policyService.load(policy.getId()));

        assertThat(error)
                .isInstanceOf(PolicyNotFoundException.class)
                .hasMessage(policy.getId().toString());
    }

    @Test
    void shouldThrowExceptionIfPolicyNotFoundByPolicyRequest() {
        PolicyRequest request = PolicyRequestMother.build();

        Throwable error = catchThrowable(() -> policyService.load(request));

        assertThat(error)
                .isInstanceOf(PolicyNotFoundException.class)
                .hasMessage(request.toString());
    }

    @Test
    void shouldReturnEmptyPoliciesByKeyIfNoPoliciesFound() {
        PolicyKey key = ChannelPolicyKeyMother.defaultChannelKey();

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
        LockoutPolicy expected = HardLockoutPolicyMother.hard();
        policyService.create(expected);

        LockoutPolicy loaded = policyService.load(expected.getId());

        assertThat(loaded).isEqualTo(expected);
    }

    @Test
    void shouldLoadCreatedLockoutPolicyByPolicyRequest() {
        LockoutPolicy expected = HardLockoutPolicyMother.hard();
        policyService.create(expected);
        PolicyRequest request = PolicyRequestMother.build();

        LockoutPolicy loaded = policyService.load(request);

        assertThat(loaded).isEqualTo(expected);
    }

    @Test
    void shouldLoadCreatedLockoutPolicyByKey() {
        LockoutPolicy expected = HardLockoutPolicyMother.hard();
        policyService.create(expected);

        Policies<LockoutPolicy> loaded = policyService.load(expected.getKey());

        assertThat(loaded).containsExactly(expected);
    }

    @Test
    void shouldLoadCreatedLockoutPolicyWhenAllPoliciesLoaded() {
        LockoutPolicy expected = HardLockoutPolicyMother.hard();
        policyService.create(expected);

        Policies<LockoutPolicy> loaded = policyService.loadAll();

        assertThat(loaded).containsExactly(expected);
    }

    @Test
    void shouldThrowExceptionIfAttemptToUpdatePolicyThatDoesNotExist() {
        LockoutPolicy policy = HardLockoutPolicyMother.hard();

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

}
