package uk.co.idv.context.entities.context;

import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;

public interface IdentityCreateContextRequestMother {

    static IdentityCreateContextRequest withInitial(CreateContextRequest initial) {
        return builder().initial(initial).build();
    }

    static IdentityCreateContextRequest withIdentity(Identity identity) {
        return builder().identity(identity).build();
    }

    static IdentityCreateContextRequest build() {
        return builder().build();
    }

    static IdentityCreateContextRequest.IdentityCreateContextRequestBuilder builder() {
        return IdentityCreateContextRequest.builder()
                .initial(DefaultCreateContextRequestMother.build())
                .identity(IdentityMother.example());
    }

}
