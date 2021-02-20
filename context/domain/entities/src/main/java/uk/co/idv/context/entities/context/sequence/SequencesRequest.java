package uk.co.idv.context.entities.context.sequence;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.context.sequence.stage.StagesRequest;
import uk.co.idv.context.entities.policy.sequence.SequencePolicies;
import uk.co.idv.context.entities.policy.sequence.SequencePolicy;
import uk.co.idv.identity.entities.identity.Identity;

import java.util.UUID;

@Builder
@Data
public class SequencesRequest {

    private final UUID contextId;
    private final Identity identity;
    private final SequencePolicies policies;

    public StagesRequest toStagesRequest(SequencePolicy policy) {
        return StagesRequest.builder()
                .contextId(contextId)
                .identity(identity)
                .policies(policy.getStagePolicies())
                .build();
    }

}
