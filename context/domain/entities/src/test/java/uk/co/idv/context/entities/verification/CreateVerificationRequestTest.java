package uk.co.idv.context.entities.verification;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CreateVerificationRequestTest {

    @Test
    void shouldReturnContextId() {
        UUID contextId = UUID.randomUUID();

        CreateVerificationRequest request = CreateVerificationRequest.builder()
                .contextId(contextId)
                .build();

        assertThat(request.getContextId()).isEqualTo(contextId);
    }

    @Test
    void shouldReturnMethodName() {
        String methodName = "method-name";

        CreateVerificationRequest request = CreateVerificationRequest.builder()
                .methodName(methodName)
                .build();

        assertThat(request.getMethodName()).isEqualTo(methodName);
    }

}
