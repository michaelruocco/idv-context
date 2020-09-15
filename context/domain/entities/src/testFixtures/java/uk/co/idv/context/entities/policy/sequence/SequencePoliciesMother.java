package uk.co.idv.context.entities.policy.sequence;

public interface SequencePoliciesMother {

    static SequencePolicies build() {
        return with(SequencePolicyMother.build());
    }

    static SequencePolicies with(SequencePolicy... policies) {
        return new SequencePolicies(policies);
    }

}
