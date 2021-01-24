package uk.co.idv.context.entities.policy.sequence.stage;

import uk.co.idv.method.entities.policy.MethodPolicies;
import uk.co.idv.method.entities.policy.MethodPoliciesMother;
import uk.co.idv.method.entities.policy.MethodPolicy;

public interface StagePolicyMother {

    static StagePolicy build() {
        return builder().build();
    }

    static StagePolicy with(MethodPolicy methodPolicy) {
        return builder().methodPolicies(MethodPoliciesMother.with(methodPolicy)).build();
    }

    static StagePolicy with(MethodPolicies methodPolicies) {
        return builder().methodPolicies(methodPolicies).build();
    }

    static StagePolicy with(StageType type) {
        return builder().type(type).build();
    }

    static StagePolicy.StagePolicyBuilder builder() {
        return StagePolicy.builder()
                .methodPolicies(MethodPoliciesMother.build())
                .type(AllMethodsStageTypeMother.build());
    }

}
