package uk.co.idv.context.entities.policy.sequence;

import uk.co.idv.context.entities.context.sequence.nextmethods.InOrderNextMethodsPolicy;
import uk.co.idv.method.entities.policy.MethodPoliciesMother;
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
                .nextMethodsPolicy(new InOrderNextMethodsPolicy())
                .methodPolicies(MethodPoliciesMother.with(methodPolicy))
                .build();
    }

    static SequencePolicy.SequencePolicyBuilder builder() {
        return SequencePolicy.builder()
                .name("default-sequence")
                .nextMethodsPolicy(new InOrderNextMethodsPolicy())
                .methodPolicies(MethodPoliciesMother.build());
    }

}
