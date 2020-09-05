package uk.co.idv.context.entities.policy.sequence;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.policy.RequestedDataProvider;
import uk.co.idv.context.entities.policy.method.MethodPolicies;
import uk.co.idv.identity.entities.identity.RequestedData;

@Builder
@Data
public class SequencePolicy implements RequestedDataProvider {

    private final String name;
    private final MethodPolicies methodPolicies;

    @Override
    public RequestedData getRequestedData() {
        return methodPolicies.getRequestedData();
    }

}
