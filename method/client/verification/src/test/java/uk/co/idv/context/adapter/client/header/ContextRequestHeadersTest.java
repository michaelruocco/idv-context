package uk.co.idv.context.adapter.client.header;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ContextRequestHeadersTest {

    private static final String CHANNEL_ID = "channel-id";
    private static final String CORRELATION_ID = "11556b28-59d4-4f81-a6b8-9fb8e213ad07";

    @Test
    void shouldPopulateChannelId() {
        ContextRequestHeaders headers = ContextRequestHeaders.build(CHANNEL_ID);

        assertThat(headers.getChannelId()).isEqualTo(CHANNEL_ID);
    }

    @Test
    void shouldPopulateRandomCorrelationId() {
        ContextRequestHeaders headers = ContextRequestHeaders.build(CHANNEL_ID);

        assertThat(headers.getCorrelationId()).isNotNull();
    }

    @Test
    void shouldPopulateChannelIdFromMdcMap() {
        Map<String, String> mdc = Map.of(
                HeaderConstants.CHANNEL_ID_NAME, CHANNEL_ID,
                HeaderConstants.CORRELATION_ID_NAME, CORRELATION_ID
        );

        ContextRequestHeaders headers = ContextRequestHeaders.build(mdc);

        assertThat(headers.getChannelId()).isEqualTo(CHANNEL_ID);
    }

    @Test
    void shouldPopulateCorrelationIdFromMdcMap() {
        Map<String, String> mdc = Map.of(
                HeaderConstants.CHANNEL_ID_NAME, CHANNEL_ID,
                HeaderConstants.CORRELATION_ID_NAME, CORRELATION_ID
        );

        ContextRequestHeaders headers = ContextRequestHeaders.build(mdc);

        assertThat(headers.getCorrelationId()).isEqualTo(UUID.fromString(CORRELATION_ID));
    }

    @Test
    void shouldReturnAllKeysAndValuesAsArray() {
        Map<String, String> mdc = Map.of(
                HeaderConstants.CHANNEL_ID_NAME, CHANNEL_ID,
                HeaderConstants.CORRELATION_ID_NAME, CORRELATION_ID
        );

        ContextRequestHeaders headers = ContextRequestHeaders.build(mdc);

        assertThat(headers.toArray()).containsExactly(
                HeaderConstants.CHANNEL_ID_NAME,
                CHANNEL_ID,
                HeaderConstants.CORRELATION_ID_NAME,
                CORRELATION_ID
        );
    }

}
