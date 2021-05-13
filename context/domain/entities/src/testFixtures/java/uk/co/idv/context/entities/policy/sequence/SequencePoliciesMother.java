package uk.co.idv.context.entities.policy.sequence;

import uk.co.idv.method.entities.policy.MethodPolicy;

public interface SequencePoliciesMother {

    static SequencePolicies empty() {
        return withSequencePolicies();
    }

    static SequencePolicies build() {
        return withSequencePolicies(SequencePolicyMother.build());
    }

    static SequencePolicies withSequencePolicies(SequencePolicy... sequencePolicies) {
        return new SequencePolicies(sequencePolicies);
    }

    static SequencePolicies withMethodPolicy(MethodPolicy methodPolicy) {
        return new SequencePolicies(SequencePolicyMother.with(methodPolicy));
    }

}
