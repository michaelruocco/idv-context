package uk.co.idv.context.entities.context;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;

import static org.assertj.core.api.Assertions.assertThat;

class IdentityContextRequestTest {

    @Test
    void shouldGetChannelFromInitialRequest() {
        CreateContextRequest initial = DefaultCreateContextRequestMother.build();

        CreateContextRequest request = IdentityCreateContextRequest.builder()
                .initial(initial)
                .build();

        assertThat(request.getChannel()).isEqualTo(initial.getChannel());
    }

    @Test
    void shouldGetActivityFromInitialRequest() {
        CreateContextRequest initial = DefaultCreateContextRequestMother.build();

        CreateContextRequest request = IdentityCreateContextRequest.builder()
                .initial(initial)
                .build();

        assertThat(request.getActivity()).isEqualTo(initial.getActivity());
    }

    @Test
    void shouldGetAliasFromInitialRequest() {
        CreateContextRequest initial = DefaultCreateContextRequestMother.build();

        CreateContextRequest request = IdentityCreateContextRequest.builder()
                .initial(initial)
                .build();

        assertThat(request.getAlias()).isEqualTo(initial.getAlias());
    }

    @Test
    void shouldSetIdentity() {
        Identity identity = IdentityMother.example();

        IdentityCreateContextRequest request = IdentityCreateContextRequest.builder()
                .identity(identity)
                .build();

        assertThat(request.getIdentity()).isEqualTo(identity);
    }

}
