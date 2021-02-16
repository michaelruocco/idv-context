package uk.co.idv.method.entities.verification;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class GetVerificationRequestTest {

    @Test
    void shouldReturnContextId() {
        UUID contextId = UUID.randomUUID();

        GetVerificationRequest request = GetVerificationRequest.builder()
                .contextId(contextId)
                .build();

        assertThat(request.getContextId()).isEqualTo(contextId);
    }

    @Test
    void shouldReturnVerificationId() {
        UUID verificationId = UUID.randomUUID();

        GetVerificationRequest request = GetVerificationRequest.builder()
                .verificationId(verificationId)
                .build();

        assertThat(request.getVerificationId()).isEqualTo(verificationId);
    }

}
