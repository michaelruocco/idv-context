package uk.co.idv.context.entities.context.create;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class ContextLockoutRequestTest {

    @Test
    void shouldReturnIdentityRequest() {
        DefaultCreateContextRequest identityRequest = DefaultCreateContextRequestMother.build();

        ContextLockoutRequest request = ContextLockoutRequest.builder()
                .identityRequest(identityRequest)
                .build();

        assertThat(request.getIdvId()).isEqualTo(identityRequest.getIdvId());
    }

    @Test
    void shouldReturnIdvIdFromIdentityRequest() {
        DefaultCreateContextRequest identityRequest = DefaultCreateContextRequestMother.build();

        ContextLockoutRequest request = ContextLockoutRequest.builder()
                .identityRequest(identityRequest)
                .build();

        assertThat(request.getIdvId()).isEqualTo(identityRequest.getIdvId());
    }

    @Test
    void shouldReturnAliasesFromIdentityRequest() {
        DefaultCreateContextRequest identityRequest = DefaultCreateContextRequestMother.build();

        ContextLockoutRequest request = ContextLockoutRequest.builder()
                .identityRequest(identityRequest)
                .build();

        assertThat(request.getAliases()).isEqualTo(identityRequest.getAliases());
    }

    @Test
    void shouldReturnChannelIdFromIdentityRequest() {
        DefaultCreateContextRequest identityRequest = DefaultCreateContextRequestMother.build();

        ContextLockoutRequest request = ContextLockoutRequest.builder()
                .identityRequest(identityRequest)
                .build();

        assertThat(request.getChannelId()).isEqualTo(identityRequest.getChannelId());
    }

    @Test
    void shouldReturnActivityNameFromIdentityRequest() {
        DefaultCreateContextRequest identityRequest = DefaultCreateContextRequestMother.build();

        ContextLockoutRequest request = ContextLockoutRequest.builder()
                .identityRequest(identityRequest)
                .build();

        assertThat(request.getActivityName()).isEqualTo(identityRequest.getActivityName());
    }

    @Test
    void shouldReturnAliasTypesFromIdentityRequest() {
        DefaultCreateContextRequest identityRequest = DefaultCreateContextRequestMother.build();

        ContextLockoutRequest request = ContextLockoutRequest.builder()
                .identityRequest(identityRequest)
                .build();

        assertThat(request.getAliasTypes()).isEqualTo(identityRequest.getAliasTypes());
    }

    @Test
    void shouldReturnTimestamp() {
        Instant timestamp = Instant.now();

        ContextLockoutRequest request = ContextLockoutRequest.builder()
                .timestamp(timestamp)
                .build();

        assertThat(request.getTimestamp()).isEqualTo(timestamp);
    }
}
