package uk.co.idv.context.entities.context;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class NextMethodsRequestTest {

    @Test
    void shouldReturnContextId() {
        UUID contextId = UUID.randomUUID();

        NextMethodsRequest request = NextMethodsRequest.builder()
                .contextId(contextId)
                .build();

        assertThat(request.getContextId()).isEqualTo(contextId);
    }

    @Test
    void shouldReturnMethodName() {
        String methodName = "method-name";

        NextMethodsRequest request = NextMethodsRequest.builder()
                .methodName(methodName)
                .build();

        assertThat(request.getMethodName()).isEqualTo(methodName);
    }

}
