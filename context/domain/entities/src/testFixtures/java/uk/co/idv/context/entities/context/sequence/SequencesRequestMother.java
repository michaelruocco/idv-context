package uk.co.idv.context.entities.context.sequence;


import uk.co.idv.context.entities.policy.sequence.SequencePolicies;
import uk.co.idv.context.entities.policy.sequence.SequencePoliciesMother;
import uk.co.idv.context.entities.policy.sequence.SequencePolicy;
import uk.co.idv.identity.entities.identity.IdentityMother;

import java.util.UUID;

public interface SequencesRequestMother {

    static SequencesRequest build() {
        return builder().build();
    }

    static SequencesRequest withPolicies(SequencePolicy... policies) {
        return withPolicies(new SequencePolicies(policies));
    }

    static SequencesRequest withPolicies(SequencePolicies policies) {
        return builder().policies(policies).build();
    }

    static SequencesRequest.SequencesRequestBuilder builder() {
        return SequencesRequest.builder()
                .contextId(UUID.fromString("e94c247a-9961-4f70-8bc7-80c18cce3c8e"))
                .identity(IdentityMother.example())
                .policies(SequencePoliciesMother.build());
    }

}
