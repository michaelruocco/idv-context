package uk.co.idv.context.adapter.client.request;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.client.headers.ContextRequestHeaders;
import uk.co.idv.context.entities.verification.CreateVerificationRequest;


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
    void shouldReturnHeadersArrayFromHeaders() {
        ContextRequestHeaders headers = mock(ContextRequestHeaders.class);
        String[] headersArray = new String[0];
        given(headers.toArray()).willReturn(headersArray);

        ClientCreateVerificationRequest request = ClientCreateVerificationRequest.builder()
                .headers(headers)
                .build();

        assertThat(request.getHeadersArray()).isEqualTo(headersArray);
    }

}
