package uk.co.idv.context.entities.policy;

import uk.co.idv.context.entities.policy.sequence.SequencePolicies;

public interface ContextPolicyMother {

    static ContextPolicy build() {
        return new ContextPolicy(new SequencePolicies());
    }

}
