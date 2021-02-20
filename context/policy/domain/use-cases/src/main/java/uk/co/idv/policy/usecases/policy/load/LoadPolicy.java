package uk.co.idv.policy.usecases.policy.load;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.policy.entities.policy.Policies;
import uk.co.idv.policy.entities.policy.Policy;
import uk.co.idv.policy.entities.policy.PolicyNotFoundException;
import uk.co.idv.policy.entities.policy.key.PolicyKey;
import uk.co.idv.policy.entities.policy.PolicyRequest;
import uk.co.idv.policy.usecases.policy.PolicyRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
@RequiredArgsConstructor
@Slf4j
public class LoadPolicy<T extends Policy> {

    private final PolicyRepository<T> repository;
    private final PolicyKeyConverter keyConverter;

    public LoadPolicy(PolicyRepository<T> repository) {
        this(repository, new PolicyKeyConverter());
    }

    public T load(UUID id) {
        return repository.load(id).orElseThrow(() -> new PolicyNotFoundException(id));
    }

    public Policies<T> load(PolicyKey key) {
        Collection<PolicyRequest> requests = keyConverter.toPolicyRequests(key);
        return load(requests);
    }

    public Policies<T> load(PolicyRequest request) {
        if (request.isEmpty()) {
            return loadAll();
        }
        return load(Collections.singleton(request));
    }

    public Policies<T> load(Collection<PolicyRequest> requests) {
        Policies<T> allPolicies = loadAll();
        return new Policies<>(requests.stream()
                .map(allPolicies::getApplicable)
                .flatMap(Policies::stream)
                .sorted(Comparator.comparing(Policy::getPriority).reversed())
                .collect(Collectors.toList()));
    }

    public Policies<T> loadAll() {
        return repository.loadAll();
    }

}
