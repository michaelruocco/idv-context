package uk.co.idv.context.adapter.verification.client.request;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.verification.client.header.IdvRequestHeaders;
import uk.co.idv.method.entities.verification.CreateVerificationRequest;


import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ClientCreateVerificationRequestTest {

    @Test
    void shouldReturnBody() {
        CreateVerificationRequest body = mock(CreateVerificationRequest.class);

        ClientCreateVerificationRequest request = ClientCreateVerificationRequest.builder()
                .body(body)
                .build();

        assertThat(request.getBody()).isEqualTo(body);
    }

    @Test
    void shouldReturnContextIdFromBody() {
        UUID contextId = UUID.randomUUID();
        CreateVerificationRequest body = mock(CreateVerificationRequest.class);
        given(body.getContextId()).willReturn(contextId);

        ClientCreateVerificationRequest request = ClientCreateVerificationRequest.builder()
                .body(body)
                .build();

        assertThat(request.getContextId()).isEqualTo(contextId);
    }

    @Test
    void shouldReturnMethodNameFromBody() {
        String methodName = "method-name";
        CreateVerificationRequest body = mock(CreateVerificationRequest.class);
        given(body.getMethodName()).willReturn(methodName);

        ClientCreateVerificationRequest request = ClientCreateVerificationRequest.builder()
                .body(body)
                .build();

        assertThat(request.getMethodName()).isEqualTo(methodName);
    }

    @Test
    void shouldReturnHeaders() {
        IdvRequestHeaders headers = mock(IdvRequestHeaders.class);

        ClientCreateVerificationRequest request = ClientCreateVerificationRequest.builder()
                .headers(headers)
                .build();

        assertThat(request.getHeaders()).isEqualTo(headers);
    }

    @Test
    void shouldReturnHeadersArrayFromHeaders() {
        IdvRequestHeaders headers = mock(IdvRequestHeaders.class);
        String[] headersArray = new String[0];
        given(headers.toArray()).willReturn(headersArray);

        ClientCreateVerificationRequest request = ClientCreateVerificationRequest.builder()
                .headers(headers)
                .build();

        assertThat(request.getHeadersArray()).isEqualTo(headersArray);
    }

}
