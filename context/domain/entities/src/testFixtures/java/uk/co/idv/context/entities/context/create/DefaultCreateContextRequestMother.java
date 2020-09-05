package uk.co.idv.context.entities.context.create;

import uk.co.idv.context.entities.policy.ContextPolicyMother;
import uk.co.idv.identity.entities.identity.IdentityMother;

public interface DefaultCreateContextRequestMother {

    static DefaultCreateContextRequest build() {
        return builder().build();
    }

    static DefaultCreateContextRequest.DefaultCreateContextRequestBuilder builder() {
        return DefaultCreateContextRequest.builder()
                .initial(FacadeCreateContextRequestMother.build())
                .identity(IdentityMother.example())
                .policy(ContextPolicyMother.build());
    }

}
