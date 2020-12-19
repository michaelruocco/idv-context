package uk.co.idv.context.entities.context.create;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.IdentityMother;
import uk.co.idv.context.entities.policy.ContextPolicy;
import uk.co.idv.context.entities.policy.ContextPolicyMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class ServiceCreateContextRequestTest {

    @Test
    void shouldReturnInitialRequest() {
        CreateContextRequest initial = FacadeCreateContextRequestMother.build();

        ServiceCreateContextRequest request = ServiceCreateContextRequest.builder()
                .initial(initial)
                .build();

        assertThat(request.getInitial()).isEqualTo(initial);
    }

    @Test
    void shouldReturnChannelFromInitialRequest() {
        CreateContextRequest initial = FacadeCreateContextRequestMother.build();

        CreateContextRequest request = ServiceCreateContextRequest.builder()
                .initial(initial)
                .build();

        assertThat(request.getChannel()).isEqualTo(initial.getChannel());
    }

    @Test
    void shouldReturnChannelIdFromInitialRequest() {
        CreateContextRequest initial = FacadeCreateContextRequestMother.build();

        CreateContextRequest request = ServiceCreateContextRequest.builder()
                .initial(initial)
                .build();

        assertThat(request.getChannelId()).isEqualTo(initial.getChannelId());
    }

    @Test
    void shouldReturnActivityFromInitialRequest() {
        CreateContextRequest initial = FacadeCreateContextRequestMother.build();

        CreateContextRequest request = ServiceCreateContextRequest.builder()
                .initial(initial)
                .build();

        assertThat(request.getActivity()).isEqualTo(initial.getActivity());
    }

    @Test
    void shouldReturnAliasesFromInitialRequest() {
        CreateContextRequest initial = FacadeCreateContextRequestMother.build();

        CreateContextRequest request = ServiceCreateContextRequest.builder()
                .initial(initial)
                .build();

        assertThat(request.getAliases()).isEqualTo(initial.getAliases());
    }

    @Test
    void shouldReturnIdentity() {
        Identity identity = IdentityMother.example();

        ServiceCreateContextRequest request = ServiceCreateContextRequest.builder()
                .identity(identity)
                .build();

        assertThat(request.getIdentity()).isEqualTo(identity);
    }

    @Test
    void shouldReturnIdvIdFromIdentity() {
        Identity identity = IdentityMother.example();

        ServiceCreateContextRequest request = ServiceCreateContextRequest.builder()
                .identity(identity)
                .build();

        assertThat(request.getIdvId()).isEqualTo(identity.getIdvId());
    }

    @Test
    void shouldReturnPolicy() {
        ContextPolicy policy = ContextPolicyMother.build();

        ServiceCreateContextRequest request = ServiceCreateContextRequest.builder()
                .policy(policy)
                .build();

        assertThat(request.getPolicy()).isEqualTo(policy);
    }

    @Test
    void shouldReturnSequencePoliciesFromPolicy() {
        ContextPolicy policy = ContextPolicyMother.build();

        ServiceCreateContextRequest request = ServiceCreateContextRequest.builder()
                .policy(policy)
                .build();

        assertThat(request.getSequencePolicies()).isEqualTo(policy.getSequencePolicies());
    }

    @Test
    void shouldReturnProtectSensitiveDataFromPolicy() {
        ContextPolicy policy = ContextPolicyMother.build();

        ServiceCreateContextRequest request = ServiceCreateContextRequest.builder()
                .policy(policy)
                .build();

        assertThat(request.isProtectSensitiveData()).isEqualTo(policy.isProtectSensitiveData());
    }

    @Test
    void shouldRequestWithUpdatedChannel() {
        CreateContextRequest request = ServiceCreateContextRequestMother.build();
        Channel channel = mock(Channel.class);

        CreateContextRequest updatedRequest = request.withChannel(channel);

        assertThat(updatedRequest)
                .usingRecursiveComparison()
                .ignoringFields("initial.channel")
                .isEqualTo(request);
        assertThat(updatedRequest.getChannel()).isEqualTo(channel);
    }

}
