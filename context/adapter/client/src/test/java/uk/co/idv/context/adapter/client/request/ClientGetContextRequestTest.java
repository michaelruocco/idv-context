package uk.co.idv.context.adapter.client.request;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.client.headers.ContextRequestHeaders;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ClientGetContextRequestTest {

    @Test
    void shouldReturnId() {
        UUID id = UUID.randomUUID();

        ClientGetContextRequest request = ClientGetContextRequest.builder()
                .id(id)
                .build();

        assertThat(request.getId()).isEqualTo(id);
    }

    @Test
    void shouldReturnHeaders() {
        ContextRequestHeaders headers = mock(ContextRequestHeaders.class);

        ClientGetContextRequest request = ClientGetContextRequest.builder()
                .headers(headers)
                .build();

        assertThat(request.getHeaders()).isEqualTo(headers);
    }

    @Test
    void shouldReturnHeadersArrayFromHeaders() {
        ContextRequestHeaders headers = mock(ContextRequestHeaders.class);
        String[] headersArray = new String[0];
        given(headers.toArray()).willReturn(headersArray);

        ClientGetContextRequest request = ClientGetContextRequest.builder()
                .headers(headers)
                .build();

        assertThat(request.getHeadersArray()).isEqualTo(headersArray);
    }

}
