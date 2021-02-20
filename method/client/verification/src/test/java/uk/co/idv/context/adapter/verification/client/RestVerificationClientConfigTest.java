package uk.co.idv.context.adapter.verification.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class RestVerificationClientConfigTest {

    @Test
    void shouldReturnBaseUri() {
        String baseUri = "http://localhost:8080";

        RestVerificationClientConfig config = RestVerificationClientConfig.builder()
                .baseUri(baseUri)
                .build();

        assertThat(config.getBaseUri()).isEqualTo(baseUri);
    }

    @Test
    void shouldReturnObjectMapper() {
        ObjectMapper mapper = mock(ObjectMapper.class);

        RestVerificationClientConfig config = RestVerificationClientConfig.builder()
                .mapper(mapper)
                .build();

        assertThat(config.getMapper()).isEqualTo(mapper);
    }

}
