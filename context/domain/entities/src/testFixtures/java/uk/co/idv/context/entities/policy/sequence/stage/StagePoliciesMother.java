package uk.co.idv.context.entities.policy.sequence.stage;

import uk.co.idv.method.entities.policy.MethodPolicy;

public interface StagePoliciesMother {

    static StagePolicies empty() {
        return with();
    }

    static StagePolicies build() {
        return with(StagePolicyMother.build());
    }

    static StagePolicies with(StageType type) {
        return new StagePolicies(StagePolicyMother.with(type));
    }

    static StagePolicies with(StagePolicy... stagePolicies) {
        return new StagePolicies(stagePolicies);
    }

    static StagePolicies withMethodPolicy(MethodPolicy methodPolicy) {
        return new StagePolicies(StagePolicyMother.with(methodPolicy));
    }

}
