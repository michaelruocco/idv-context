package uk.co.idv.context.entities.context.create;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class ContextLockoutRequestTest {

    @Test
    void shouldReturnIdentityRequest() {
        IdentityCreateContextRequest identityRequest = IdentityCreateContextRequestMother.build();

        ContextLockoutRequest request = ContextLockoutRequest.builder()
                .identityRequest(identityRequest)
                .build();

        assertThat(request.getIdvId()).isEqualTo(identityRequest.getIdvId());
    }

    @Test
    void shouldReturnIdvIdFromIdentityRequest() {
        IdentityCreateContextRequest identityRequest = IdentityCreateContextRequestMother.build();

        ContextLockoutRequest request = ContextLockoutRequest.builder()
                .identityRequest(identityRequest)
                .build();

        assertThat(request.getIdvId()).isEqualTo(identityRequest.getIdvId());
    }

    @Test
    void shouldReturnAliasFromIdentityRequest() {
        IdentityCreateContextRequest identityRequest = IdentityCreateContextRequestMother.build();

        ContextLockoutRequest request = ContextLockoutRequest.builder()
                .identityRequest(identityRequest)
                .build();

        assertThat(request.getAlias()).isEqualTo(identityRequest.getAlias());
    }

    @Test
    void shouldReturnChannelIdFromIdentityRequest() {
        IdentityCreateContextRequest identityRequest = IdentityCreateContextRequestMother.build();

        ContextLockoutRequest request = ContextLockoutRequest.builder()
                .identityRequest(identityRequest)
                .build();

        assertThat(request.getChannelId()).isEqualTo(identityRequest.getChannelId());
    }

    @Test
    void shouldReturnActivityNameFromIdentityRequest() {
        IdentityCreateContextRequest identityRequest = IdentityCreateContextRequestMother.build();

        ContextLockoutRequest request = ContextLockoutRequest.builder()
                .identityRequest(identityRequest)
                .build();

        assertThat(request.getActivityName()).isEqualTo(identityRequest.getActivityName());
    }

    @Test
    void shouldReturnAliasTypeFromIdentityRequest() {
        IdentityCreateContextRequest identityRequest = IdentityCreateContextRequestMother.build();

        ContextLockoutRequest request = ContextLockoutRequest.builder()
                .identityRequest(identityRequest)
                .build();

        assertThat(request.getAliasType()).isEqualTo(identityRequest.getAliasType());
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
