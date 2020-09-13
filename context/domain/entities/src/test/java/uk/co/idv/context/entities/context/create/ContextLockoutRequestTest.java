package uk.co.idv.context.entities.context.create;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class ContextLockoutRequestTest {

    @Test
    void shouldReturnContextRequest() {
        DefaultCreateContextRequest contextRequest = DefaultCreateContextRequestMother.build();

        ContextLockoutRequest request = ContextLockoutRequest.builder()
                .contextRequest(contextRequest)
                .build();

        assertThat(request.getContextRequest()).isEqualTo(contextRequest);
    }

    @Test
    void shouldReturnIdvIdFromContextRequest() {
        DefaultCreateContextRequest contextRequest = DefaultCreateContextRequestMother.build();

        ContextLockoutRequest request = ContextLockoutRequest.builder()
                .contextRequest(contextRequest)
                .build();

        assertThat(request.getIdvId()).isEqualTo(contextRequest.getIdvId());
    }

    @Test
    void shouldReturnAliasesFromContextRequest() {
        DefaultCreateContextRequest contextRequest = DefaultCreateContextRequestMother.build();

        ContextLockoutRequest request = ContextLockoutRequest.builder()
                .contextRequest(contextRequest)
                .build();

        assertThat(request.getAliases()).isEqualTo(contextRequest.getAliases());
    }

    @Test
    void shouldReturnChannelIdFromContextRequest() {
        DefaultCreateContextRequest contextRequest = DefaultCreateContextRequestMother.build();

        ContextLockoutRequest request = ContextLockoutRequest.builder()
                .contextRequest(contextRequest)
                .build();

        assertThat(request.getChannelId()).isEqualTo(contextRequest.getChannelId());
    }

    @Test
    void shouldReturnActivityNameFromContextRequest() {
        DefaultCreateContextRequest contextRequest = DefaultCreateContextRequestMother.build();

        ContextLockoutRequest request = ContextLockoutRequest.builder()
                .contextRequest(contextRequest)
                .build();

        assertThat(request.getActivityName()).isEqualTo(contextRequest.getActivityName());
    }

    @Test
    void shouldReturnAliasTypesFromContextRequest() {
        DefaultCreateContextRequest contextRequest = DefaultCreateContextRequestMother.build();

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
