package uk.co.idv.context.entities.context.create;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;
import uk.co.idv.context.entities.policy.ContextPolicy;
import uk.co.idv.context.entities.policy.ContextPolicyMother;

import static org.assertj.core.api.Assertions.assertThat;

class IdentityCreateContextRequestTest {

    @Test
    void shouldReturnInitialRequest() {
        CreateContextRequest initial = DefaultCreateContextRequestMother.build();

        IdentityCreateContextRequest request = IdentityCreateContextRequest.builder()
                .initial(initial)
                .build();

        assertThat(request.getInitial()).isEqualTo(initial);
    }

    @Test
    void shouldReturnChannelFromInitialRequest() {
        CreateContextRequest initial = DefaultCreateContextRequestMother.build();

        CreateContextRequest request = IdentityCreateContextRequest.builder()
                .initial(initial)
                .build();

        assertThat(request.getChannel()).isEqualTo(initial.getChannel());
    }

    @Test
    void shouldReturnChannelIdFromInitialRequest() {
        CreateContextRequest initial = DefaultCreateContextRequestMother.build();

        CreateContextRequest request = IdentityCreateContextRequest.builder()
                .initial(initial)
                .build();

        assertThat(request.getChannelId()).isEqualTo(initial.getChannelId());
    }

    @Test
    void shouldReturnActivityFromInitialRequest() {
        CreateContextRequest initial = DefaultCreateContextRequestMother.build();

        CreateContextRequest request = IdentityCreateContextRequest.builder()
                .initial(initial)
                .build();

        assertThat(request.getActivity()).isEqualTo(initial.getActivity());
    }

    @Test
    void shouldReturnAliasesFromInitialRequest() {
        CreateContextRequest initial = DefaultCreateContextRequestMother.build();

        CreateContextRequest request = IdentityCreateContextRequest.builder()
                .initial(initial)
                .build();

        assertThat(request.getAliases()).isEqualTo(initial.getAliases());
    }

    @Test
    void shouldReturnIdentity() {
        Identity identity = IdentityMother.example();

        IdentityCreateContextRequest request = IdentityCreateContextRequest.builder()
                .identity(identity)
                .build();

        assertThat(request.getIdentity()).isEqualTo(identity);
    }

    @Test
    void shouldReturnIdvIdFromIdentity() {
        Identity identity = IdentityMother.example();

        IdentityCreateContextRequest request = IdentityCreateContextRequest.builder()
                .identity(identity)
                .build();

        assertThat(request.getIdvId()).isEqualTo(identity.getIdvId());
    }

    @Test
    void shouldReturnPolicy() {
        ContextPolicy policy = ContextPolicyMother.build();

        IdentityCreateContextRequest request = IdentityCreateContextRequest.builder()
                .policy(policy)
                .build();

        assertThat(request.getPolicy()).isEqualTo(policy);
    }

}
