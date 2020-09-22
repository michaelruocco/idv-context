package uk.co.idv.app.spring.policy;

import lombok.RequiredArgsConstructor;
import uk.co.idv.policy.entities.policy.DefaultPolicyRequest;
import uk.co.idv.policy.entities.policy.Policies;
import uk.co.idv.policy.entities.policy.Policy;
import uk.co.idv.policy.usecases.policy.PolicyService;

import java.util.Collections;
import java.util.UUID;

@RequiredArgsConstructor
public class LoadPolicy<P extends Policy> {

    private final PolicyService<P> service;

    public P load(UUID id) {
        return service.load(id);
    }

    public Policies<P> load(String channelId,
                            String activityName,
                            String aliasType) {
        DefaultPolicyRequest request = DefaultPolicyRequest.builder()
                .channelId(channelId)
                .activityName(activityName)
                .aliasTypes(Collections.singleton(aliasType))
                .build();
        if (request.isEmpty()) {
            return service.loadAll();
        }
        return service.load(request);
    }

}
