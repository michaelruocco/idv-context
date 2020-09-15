package uk.co.idv.context.entities.policy.sequence;

import uk.co.idv.context.entities.policy.method.MethodPoliciesMother;

public interface SequencePolicyMother {

    static SequencePolicy build() {
        return builder().build();
    }

    static SequencePolicy withName(String name) {
        return builder().name(name).build();
    }

    static SequencePolicy.SequencePolicyBuilder builder() {
        return SequencePolicy.builder()
                .name("default-sequence")
                .methodPolicies(MethodPoliciesMother.build());
    }

}
