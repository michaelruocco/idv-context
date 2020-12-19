package uk.co.idv.context.entities.context.create;

import uk.co.idv.context.entities.policy.ContextPolicyMother;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.IdentityMother;

public interface ServiceCreateContextRequestMother {

    static ServiceCreateContextRequest build() {
        return builder().build();
    }

    static ServiceCreateContextRequest withIdentity(Identity identity) {
        return builder().identity(identity).build();
    }

    static ServiceCreateContextRequest.ServiceCreateContextRequestBuilder builder() {
        return ServiceCreateContextRequest.builder()
                .initial(FacadeCreateContextRequestMother.build())
                .identity(IdentityMother.example())
                .policy(ContextPolicyMother.build());
    }

}
