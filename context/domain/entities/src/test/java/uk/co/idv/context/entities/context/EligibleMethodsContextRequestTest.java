package uk.co.idv.context.entities.context;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class EligibleMethodsContextRequestTest {

    @Test
    void shouldReturnContextId() {
        UUID contextId = UUID.randomUUID();

        EligibleMethodsContextRequest request = EligibleMethodsContextRequest.builder()
                .contextId(contextId)
                .build();

        assertThat(request.getContextId()).isEqualTo(contextId);
    }

    @Test
    void shouldReturnMethodName() {
        String methodName = "method-name";

        EligibleMethodsContextRequest request = EligibleMethodsContextRequest.builder()
                .methodName(methodName)
                .build();

        assertThat(request.getMethodName()).isEqualTo(methodName);
    }

}
