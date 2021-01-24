package uk.co.idv.context.entities.policy.sequence;

import uk.co.idv.context.entities.policy.sequence.stage.StagePoliciesMother;
import uk.co.idv.method.entities.method.fake.policy.FakeMethodPolicyMother;
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
                .stagePolicies(StagePoliciesMother.withMethodPolicy(methodPolicy))
                .build();
    }

    static SequencePolicy.SequencePolicyBuilder builder() {
        return SequencePolicy.builder()
                .name("default-sequence")
                .stagePolicies(StagePoliciesMother.withMethodPolicy(FakeMethodPolicyMother.build()));
    }

}
