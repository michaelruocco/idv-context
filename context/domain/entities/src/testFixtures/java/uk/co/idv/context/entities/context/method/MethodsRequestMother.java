package uk.co.idv.context.entities.context.method;


import uk.co.idv.context.entities.policy.method.MethodPolicies;
import uk.co.idv.context.entities.policy.method.MethodPoliciesMother;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.IdentityMother;
import uk.co.idv.method.entities.policy.MethodPolicy;

import java.util.UUID;

public interface MethodsRequestMother {

    static MethodsRequest build() {
        return builder().build();
    }

    static MethodsRequest withPolicies(MethodPolicy... policies) {
        return withPolicies(new MethodPolicies(policies));
    }

    static MethodsRequest withPolicies(MethodPolicies policies) {
        return builder().policies(policies).build();
    }

    static MethodsRequest withIdentity(Identity identity) {
        return builder().identity(identity).build();
    }

    static MethodsRequest.MethodsRequestBuilder builder() {
        return MethodsRequest.builder()
                .contextId(UUID.fromString("e94c247a-9961-4f70-8bc7-80c18cce3c8e"))
                .identity(IdentityMother.example())
                .policies(MethodPoliciesMother.build());
    }

}
