package uk.co.idv.context.entities.context.sequence.stage;


import uk.co.idv.context.entities.policy.sequence.stage.StagePolicies;
import uk.co.idv.context.entities.policy.sequence.stage.StagePoliciesMother;
import uk.co.idv.context.entities.policy.sequence.stage.StagePolicy;
import uk.co.idv.identity.entities.identity.IdentityMother;

import java.util.UUID;

public interface StagesRequestMother {

    static StagesRequest build() {
        return builder().build();
    }

    static StagesRequest withPolicies(StagePolicy... policies) {
        return withPolicies(new StagePolicies(policies));
    }

    static StagesRequest withPolicies(StagePolicies policies) {
        return builder().policies(policies).build();
    }

    static StagesRequest.StagesRequestBuilder builder() {
        return StagesRequest.builder()
                .contextId(UUID.fromString("e94c247a-9961-4f70-8bc7-80c18cce3c8e"))
                .identity(IdentityMother.example())
                .policies(StagePoliciesMother.build());
    }

}
