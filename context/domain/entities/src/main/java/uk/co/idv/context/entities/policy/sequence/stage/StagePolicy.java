package uk.co.idv.context.entities.policy.sequence.stage;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.identity.entities.identity.RequestedData;
import uk.co.idv.method.entities.policy.MethodPolicies;
import uk.co.idv.method.entities.policy.RequestedDataProvider;

@Data
@Builder
public class StagePolicy implements RequestedDataProvider {

    private final StageType type;
    private final MethodPolicies methodPolicies;

    public String getTypeName() {
        return type.getName();
    }

    public RequestedData getRequestedData() {
        return methodPolicies.getRequestedData();
    }

    public StageType getType() {
        return type;
    }

}
