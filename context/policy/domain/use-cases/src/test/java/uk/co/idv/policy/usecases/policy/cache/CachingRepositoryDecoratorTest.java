package uk.co.idv.policy.usecases.policy.cache;

import org.awaitility.core.ConditionTimeoutException;
import org.junit.jupiter.api.Test;
import uk.co.idv.policy.entities.policy.FakePolicyMother;
import uk.co.idv.policy.entities.policy.Policies;
import uk.co.idv.policy.entities.policy.PoliciesMother;
import uk.co.idv.policy.entities.policy.Policy;
import uk.co.idv.policy.usecases.policy.PolicyRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class CachingRepositoryDecoratorTest {

    private final PolicyRepository<Policy> repository = mock(PolicyRepository.class);
    private final CacheController cacheController = mock(CacheController.class);
    private final Map<UUID, Policy> cache = new HashMap<>();

    private final CachingRepositoryDecorator<Policy> cachingDecorator = new CachingRepositoryDecorator<>(repository, cacheController, cache);

    @Test
    void shouldLoadValuesFromRepositoryOnRefresh() {
        Policies<Policy> policies = givenPoliciesLoadedFromRepository();

        cachingDecorator.refresh();

        assertThat(cache.values()).containsExactlyElementsOf(policies);
    }

    @Test
    void shouldRemoveStaleEntriesFromCacheOnRefresh() {
        givenPolicyAlreadyInCache();
        Policies<Policy> policies = givenPoliciesLoadedFromRepository();

        cachingDecorator.refresh();

        assertThat(cache.values()).containsExactlyElementsOf(policies);
    }

    @Test
    void shouldReturnEmptyOptionalIfIdNotPresentInCache() {
        UUID id = UUID.randomUUID();

        Optional<Policy> policy = cachingDecorator.load(id);

        assertThat(policy).isEmpty();
    }

    @Test
    void shouldReturnPolicyIfPresentInCache() {
        Policy expectedPolicy = givenPolicyAlreadyInCache();

        Optional<Policy> policy = cachingDecorator.load(expectedPolicy.getId());

        assertThat(policy).contains(expectedPolicy);
    }

    @Test
    void shouldReturnEmptyPoliciesIfIdNonePresentInCache() {
        Policies<Policy> policies = cachingDecorator.loadAll();

        assertThat(policies).isEmpty();
    }

    @Test
    void shouldReturnAllPoliciesPresentInCache() {
        Policy expectedPolicy = givenPolicyAlreadyInCache();

        Policies<Policy> policies = cachingDecorator.loadAll();

        assertThat(policies).containsExactly(expectedPolicy);
    }

    @Test
    void shouldNotUpdateCacheOrSavePolicyIfCacheDoesNotCompleteUpdating() {
        Throwable expectedError = givenErrorWaitingForCacheUpdateToComplete();
        Policy policy = FakePolicyMother.build();

        Throwable error = catchThrowable(() -> cachingDecorator.save(policy));

        assertThat(error).isEqualTo(expectedError);
        assertThat(cache).isEmpty();
        verify(repository, never()).save(any(Policy.class));
    }

    @Test
    void shouldNotSavePolicy() {
        Policy policy = FakePolicyMother.build();

        cachingDecorator.save(policy);

        assertThat(cache.values()).containsExactly(policy);
        verify(repository).save(policy);
    }

    @Test
    void shouldNotUpdateCacheOrDeletePolicyIfCacheDoesNotCompleteUpdating() {
        Policy cachedPolicy = givenPolicyAlreadyInCache();
        Throwable expectedError = givenErrorWaitingForCacheUpdateToComplete();

        Throwable error = catchThrowable(() -> cachingDecorator.delete(cachedPolicy.getId()));

        assertThat(error).isEqualTo(expectedError);
        assertThat(cache.values()).containsExactly(cachedPolicy);
        verify(repository, never()).delete(any(UUID.class));
    }

    @Test
    void shouldDeletePolicy() {
        Policy cachedPolicy = givenPolicyAlreadyInCache();

        cachingDecorator.delete(cachedPolicy.getId());

        assertThat(cache).isEmpty();
        verify(repository).delete(cachedPolicy.getId());
    }

    private Policies<Policy> givenPoliciesLoadedFromRepository() {
        Policies<Policy> policies = PoliciesMother.singleFakePolicy();
        given(repository.loadAll()).willReturn(policies);
        return policies;
    }

    private Policy givenPolicyAlreadyInCache() {
        Policy policy = FakePolicyMother.withId(UUID.randomUUID());
        cache.put(policy.getId(), policy);
        return policy;
    }

    private Throwable givenErrorWaitingForCacheUpdateToComplete() {
        Throwable error = new ConditionTimeoutException("error");
        doThrow(error).when(cacheController).waitUntilUpdateComplete();
        return error;
    }

}
