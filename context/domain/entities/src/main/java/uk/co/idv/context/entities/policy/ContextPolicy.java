package uk.co.idv.context.entities.policy;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.policy.sequence.SequencePolicies;
import uk.co.idv.identity.entities.identity.RequestedData;

@RequiredArgsConstructor
public class ContextPolicy implements RequestedDataProvider {

    private final SequencePolicies sequencePolicies;

    @Override
    public RequestedData getRequestedData() {
        return sequencePolicies.getRequestedData();
    }

}
