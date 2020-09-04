package uk.co.idv.context.entities.context.create;

import uk.co.idv.identity.entities.identity.DefaultIdentity;
import uk.co.idv.identity.entities.identity.IdentityMother;

public interface DefaultCreateContextRequestMother {

    static DefaultCreateContextRequest withInitial(CreateContextRequest initial) {
        return builder().initial(initial).build();
    }

    static DefaultCreateContextRequest withIdentity(DefaultIdentity identity) {
        return builder().identity(identity).build();
    }

    static DefaultCreateContextRequest build() {
        return builder().build();
    }

    static DefaultCreateContextRequest.DefaultCreateContextRequestBuilder builder() {
        return DefaultCreateContextRequest.builder()
                .initial(FacadeCreateContextRequestMother.build())
                .identity(IdentityMother.example());
    }

}
