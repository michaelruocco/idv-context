package uk.co.idv.context.entities.context.create;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class ContextLockoutRequestTest {

    @Test
    void shouldReturnContextRequest() {
        ServiceCreateContextRequest contextRequest = ServiceCreateContextRequestMother.build();

        ContextLockoutRequest request = ContextLockoutRequest.builder()
                .contextRequest(contextRequest)
                .build();

        assertThat(request.getContextRequest()).isEqualTo(contextRequest);
    }

    @Test
    void shouldReturnIdvIdFromContextRequest() {
        ServiceCreateContextRequest contextRequest = ServiceCreateContextRequestMother.build();

        ContextLockoutRequest request = ContextLockoutRequest.builder()
                .contextRequest(contextRequest)
                .build();

        assertThat(request.getIdvId()).isEqualTo(contextRequest.getIdvId());
    }

    @Test
    void shouldReturnAliasesFromContextRequest() {
        ServiceCreateContextRequest contextRequest = ServiceCreateContextRequestMother.build();

        ContextLockoutRequest request = ContextLockoutRequest.builder()
                .contextRequest(contextRequest)
                .build();

        assertThat(request.getAliases()).isEqualTo(contextRequest.getAliases());
    }

    @Test
    void shouldReturnChannelIdFromContextRequest() {
        ServiceCreateContextRequest contextRequest = ServiceCreateContextRequestMother.build();

        ContextLockoutRequest request = ContextLockoutRequest.builder()
                .contextRequest(contextRequest)
                .build();

        assertThat(request.getChannelId()).isEqualTo(contextRequest.getChannelId());
    }

    @Test
    void shouldReturnActivityNameFromContextRequest() {
        ServiceCreateContextRequest contextRequest = ServiceCreateContextRequestMother.build();

        ContextLockoutRequest request = ContextLockoutRequest.builder()
                .contextRequest(contextRequest)
                .build();

        assertThat(request.getActivityName()).isEqualTo(contextRequest.getActivityName());
    }

    @Test
    void shouldReturnAliasTypesFromContextRequest() {
        ServiceCreateContextRequest contextRequest = ServiceCreateContextRequestMother.build();

        ContextLockoutRequest request = ContextLockoutRequest.builder()
                .contextRequest(contextRequest)
                .build();

        assertThat(request.getAliasTypes()).isEqualTo(contextRequest.getAliasTypes());
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
