package uk.co.idv.context.entities.policy.sequence;

import uk.co.idv.context.entities.policy.method.MethodPoliciesMother;
import uk.co.idv.method.entities.policy.MethodPolicy;

public interface SequencePolicyMother {

    static SequencePolicy build() {
        return builder().build();
    }

    static SequencePolicy withName(String name) {
        return builder().name(name).build();
    }

    static SequencePolicy with(MethodPolicy methodPolicy) {
        return builder()
                .name(methodPolicy.getName())
                .methodPolicies(MethodPoliciesMother.with(methodPolicy))
                .build();
    }

    static SequencePolicy.SequencePolicyBuilder builder() {
        return SequencePolicy.builder()
                .name("default-sequence")
                .methodPolicies(MethodPoliciesMother.build());
    }

}
