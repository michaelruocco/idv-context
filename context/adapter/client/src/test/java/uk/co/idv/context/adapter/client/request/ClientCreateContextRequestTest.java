package uk.co.idv.context.adapter.client.request;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.client.headers.ContextRequestHeaders;
import uk.co.idv.context.entities.context.create.FacadeCreateContextRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ClientCreateContextRequestTest {

    @Test
    void shouldReturnBody() {
        FacadeCreateContextRequest body = mock(FacadeCreateContextRequest.class);

        ClientCreateContextRequest request = ClientCreateContextRequest.builder()
                .body(body)
                .build();

        assertThat(request.getBody()).isEqualTo(body);
    }

    @Test
    void shouldReturnHeaders() {
        ContextRequestHeaders headers = mock(ContextRequestHeaders.class);

        ClientCreateContextRequest request = ClientCreateContextRequest.builder()
                .headers(headers)
                .build();

        assertThat(request.getHeaders()).isEqualTo(headers);
    }

    @Test
    void shouldReturnHeadersArrayFromHeaders() {
        ContextRequestHeaders headers = mock(ContextRequestHeaders.class);
        String[] headersArray = new String[0];
        given(headers.toArray()).willReturn(headersArray);

        ClientCreateContextRequest request = ClientCreateContextRequest.builder()
                .headers(headers)
                .build();

        assertThat(request.getHeadersArray()).isEqualTo(headersArray);
    }

}
