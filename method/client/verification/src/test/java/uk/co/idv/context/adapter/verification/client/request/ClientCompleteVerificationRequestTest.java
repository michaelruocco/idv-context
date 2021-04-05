package uk.co.idv.context.adapter.verification.client.request;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.verification.client.header.IdvRequestHeaders;
import uk.co.idv.method.entities.verification.CompleteVerificationRequest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ClientCompleteVerificationRequestTest {

    @Test
    void shouldReturnBody() {
        CompleteVerificationRequest body = mock(CompleteVerificationRequest.class);

        ClientCompleteVerificationRequest request = ClientCompleteVerificationRequest.builder()
                .body(body)
                .build();

        assertThat(request.getBody()).isEqualTo(body);
    }

    @Test
    void shouldReturnContextIdFromBody() {
        UUID contextId = UUID.randomUUID();
        CompleteVerificationRequest body = mock(CompleteVerificationRequest.class);
        given(body.getContextId()).willReturn(contextId);

        ClientCompleteVerificationRequest request = ClientCompleteVerificationRequest.builder()
                .body(body)
                .build();

        assertThat(request.getContextId()).isEqualTo(contextId);
    }

    @Test
    void shouldReturnHeaders() {
        IdvRequestHeaders headers = mock(IdvRequestHeaders.class);

        ClientCompleteVerificationRequest request = ClientCompleteVerificationRequest.builder()
                .headers(headers)
                .build();

        assertThat(request.getHeaders()).isEqualTo(headers);
    }

    @Test
    void shouldReturnHeadersArrayFromHeaders() {
        IdvRequestHeaders headers = mock(IdvRequestHeaders.class);
        String[] headersArray = new String[0];
        given(headers.toArray()).willReturn(headersArray);

        ClientCompleteVerificationRequest request = ClientCompleteVerificationRequest.builder()
                .headers(headers)
                .build();

        assertThat(request.getHeadersArray()).isEqualTo(headersArray);
    }

}
