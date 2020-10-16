package uk.co.idv.policy.entities.policy;


import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public interface MockPolicyMother {

    static Policy policy() {
        return mock(Policy.class);
    }

    static Policy applicableTo(PolicyRequest request) {
        Policy policy = policy();
        given(policy.appliesTo(request)).willReturn(true);
        return policy;
    }

    static Policy notApplicableTo(PolicyRequest request) {
        Policy policy = policy();
        given(policy.appliesTo(request)).willReturn(false);
        return policy;
    }

    static Policy withPriority(int priority) {
        Policy policy = policy();
        given(policy.getPriority()).willReturn(priority);
        return policy;
    }

    static Policy withId(UUID id) {
        Policy policy = policy();
        given(policy.getId()).willReturn(id);
        return policy;
    }

}
