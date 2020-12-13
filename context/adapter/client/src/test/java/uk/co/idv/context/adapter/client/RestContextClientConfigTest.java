package uk.co.idv.context.adapter.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class RestContextClientConfigTest {

    @Test
    void shouldReturnBaseUri() {
        String baseUri = "http://localhost:8080";

        RestContextClientConfig config = RestContextClientConfig.builder()
                .baseUri(baseUri)
                .build();

        assertThat(config.getBaseUri()).isEqualTo(baseUri);
    }

    @Test
    void shouldReturnObjectMapper() {
        ObjectMapper mapper = mock(ObjectMapper.class);

        RestContextClientConfig config = RestContextClientConfig.builder()
                .mapper(mapper)
                .build();

        assertThat(config.getMapper()).isEqualTo(mapper);
    }

}
