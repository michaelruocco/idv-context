package uk.co.idv.context.entities.context.create;

import uk.co.idv.context.entities.policy.ContextPolicyMother;

public interface ContextCreateEligibilityRequestMother {

    static ContextCreateEligibilityRequest build() {
        return builder().build();
    }

    static ContextCreateEligibilityRequest.ContextCreateEligibilityRequestBuilder builder() {
        return ContextCreateEligibilityRequest.builder()
                .request(DefaultCreateContextRequestMother.build())
                .policy(ContextPolicyMother.build());
    }

}
