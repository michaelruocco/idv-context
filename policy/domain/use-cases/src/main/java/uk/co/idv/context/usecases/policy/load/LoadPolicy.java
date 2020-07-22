package uk.co.idv.context.usecases.policy.load;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.policy.Policies;
import uk.co.idv.context.entities.policy.Policy;
import uk.co.idv.context.entities.policy.PolicyKey;
import uk.co.idv.context.entities.policy.PolicyRequest;
import uk.co.idv.context.usecases.policy.PolicyRepository;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
@Slf4j
public class LoadPolicy<T extends Policy> {

    private final PolicyRepository<T> repository;
    private final PolicyKeyConverter keyConverter;

    public T load(UUID id) {
        return repository.load(id).orElseThrow(() -> new PolicyNotFoundException(id));
    }

    public T load(PolicyRequest request) {
        Policies<T> allPolicies = loadAll();
        Policies<T> applicablePolicies = allPolicies.getApplicable(request);
        return applicablePolicies.getHighestPriority();
    }

    public Policies<T> load(PolicyKey key) {
        Policies<T> allPolicies = loadAll();
        Collection<PolicyRequest> requests = keyConverter.toPolicyRequests(key);
        return new Policies<>(requests.stream()
                .map(allPolicies::getApplicable)
                .flatMap(Policies::stream)
                .collect(Collectors.toList()));
    }

    public Policies<T> loadAll() {
        return repository.loadAll();
    }

}
