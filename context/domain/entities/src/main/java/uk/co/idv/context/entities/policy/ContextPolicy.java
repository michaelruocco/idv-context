package uk.co.idv.context.entities.policy;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.policy.sequence.SequencePolicies;
import uk.co.idv.identity.entities.identity.RequestedData;
import uk.co.idv.policy.entities.policy.Policy;
import uk.co.idv.policy.entities.policy.PolicyRequest;
import uk.co.idv.policy.entities.policy.key.PolicyKey;

import java.util.UUID;

@Builder
@Data
public class ContextPolicy implements Policy, RequestedDataProvider {

    private final PolicyKey key;
    private final SequencePolicies sequencePolicies;

    @Override
    public UUID getId() {
        return key.getId();
    }

    @Override
    public RequestedData getRequestedData() {
        return sequencePolicies.getRequestedData();
    }

    @Override
    public boolean appliesTo(PolicyRequest request) {
        return key.appliesTo(request);
    }

    @Override
    public int getPriority() {
        return key.getPriority();
    }

}
