package uk.co.idv.context.entities.context.create;

import uk.co.idv.context.entities.policy.ContextPolicyMother;
import uk.co.idv.identity.entities.identity.IdentityMother;

public interface DefaultCreateContextRequestMother {

    static ServiceCreateContextRequest build() {
        return builder().build();
    }

    static ServiceCreateContextRequest.ServiceCreateContextRequestBuilder builder() {
        return ServiceCreateContextRequest.builder()
                .initial(FacadeCreateContextRequestMother.build())
                .identity(IdentityMother.example())
                .policy(ContextPolicyMother.build());
    }

}
