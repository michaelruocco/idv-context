package uk.co.idv.context.entities.context.sequence.stage;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.policy.sequence.stage.StagePolicies;
import uk.co.idv.context.entities.policy.sequence.stage.StagePolicy;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.method.entities.method.MethodsRequest;

import java.util.UUID;

@Builder
@Data
public class StagesRequest {

    private final UUID contextId;
    private final Identity identity;
    private final StagePolicies policies;

    public MethodsRequest toMethodsRequest(StagePolicy policy) {
        return MethodsRequest.builder()
                .contextId(contextId)
                .identity(identity)
                .policies(policy.getMethodPolicies())
                .build();
    }

}
