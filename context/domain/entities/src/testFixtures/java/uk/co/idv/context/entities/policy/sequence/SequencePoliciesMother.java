package uk.co.idv.context.entities.policy.sequence;

import uk.co.idv.method.entities.policy.MethodPolicy;

public interface SequencePoliciesMother {

    static SequencePolicies build() {
        return withSequencePolicy(SequencePolicyMother.build());
    }

    static SequencePolicies withSequencePolicy(SequencePolicy... sequencePolicies) {
        return new SequencePolicies(sequencePolicies);
    }

    static SequencePolicies withMethodPolicy(MethodPolicy methodPolicy) {
        return new SequencePolicies(SequencePolicyMother.with(methodPolicy));
    }

}
