package uk.co.idv.context.adapter.verification.client.header;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class IdvRequestHeadersTest {

    private static final String CHANNEL_ID = "my-channel-id";
    private static final String CORRELATION_ID = "11556b28-59d4-4f81-a6b8-9fb8e213ad07";
    private static final String AUTHORIZATION = "Bearer a.b.c";

    @Test
    void shouldPopulateChannelId() {
        IdvRequestHeaders headers = IdvRequestHeaders.builder()
                .channelId(CHANNEL_ID)
                .build();

        String channelId = headers.getChannelId();

        assertThat(channelId).isEqualTo(CHANNEL_ID);
    }

    @Test
    void shouldPopulateRandomCorrelationIdIfNotProvided() {
        IdvRequestHeaders headers = IdvRequestHeaders.builder()
                .build();

        UUID correlationId = headers.getCorrelationId();

        assertThat(correlationId).isNotNull();
    }

    @Test
    void shouldPopulateAuthorization() {
        IdvRequestHeaders headers = IdvRequestHeaders.builder()
                .authorization(AUTHORIZATION)
                .build();

        Optional<String> authorization = headers.getAuthorization();

        assertThat(authorization).contains(AUTHORIZATION);
    }

    @Test
    void shouldPopulateChannelIdFromMap() {
        Map<String, String> values = Map.of(
                IdvHeaderConstants.CHANNEL_ID_NAME, CHANNEL_ID
        );
        IdvRequestHeaders headers = IdvRequestHeaders.build(values);

        String channelId = headers.getChannelId();

        assertThat(channelId).isEqualTo(CHANNEL_ID);
    }

    @Test
    void shouldPopulateCorrelationIdFromMap() {
        Map<String, String> values = Map.of(
                IdvHeaderConstants.CORRELATION_ID_NAME, CORRELATION_ID
        );
        IdvRequestHeaders headers = IdvRequestHeaders.build(values);

        UUID correlationId = headers.getCorrelationId();

        assertThat(correlationId).isEqualTo(UUID.fromString(CORRELATION_ID));
    }

    @Test
    void shouldPopulateRandomCorrelationIdFromMapIfNotPresent() {
        Map<String, String> values = Map.of();
        IdvRequestHeaders headers = IdvRequestHeaders.build(values);

        UUID correlationId = headers.getCorrelationId();

        assertThat(correlationId).isNotNull();
    }

    @Test
    void shouldAuthorizationFromMapIfPresent() {
        Map<String, String> values = Map.of(
                IdvHeaderConstants.AUTHORIZATION_NAME, AUTHORIZATION
        );
        IdvRequestHeaders headers = IdvRequestHeaders.build(values);

        Optional<String> authorization = headers.getAuthorization();

        assertThat(authorization).contains(AUTHORIZATION);
    }

    @Test
    void shouldNotPopulateAuthorizationFromMapIfNotPresent() {
        Map<String, String> values = Map.of();
        IdvRequestHeaders headers = IdvRequestHeaders.build(values);

        Optional<String> authorization = headers.getAuthorization();

        assertThat(authorization).isEmpty();
    }

    @Test
    void shouldReturnKeysAndValuesAsArray() {
        Map<String, String> values = Map.of(
                IdvHeaderConstants.CHANNEL_ID_NAME, CHANNEL_ID,
                IdvHeaderConstants.CORRELATION_ID_NAME, CORRELATION_ID
        );

        IdvRequestHeaders headers = IdvRequestHeaders.build(values);

        assertThat(headers.toArray()).containsExactly(
                IdvHeaderConstants.CHANNEL_ID_NAME,
                CHANNEL_ID,
                IdvHeaderConstants.CORRELATION_ID_NAME,
                CORRELATION_ID
        );
    }

    @Test
    void shouldReturnKeysAndValuesWithAuthorizationPresentAsArray() {
        Map<String, String> values = Map.of(
                IdvHeaderConstants.CHANNEL_ID_NAME, CHANNEL_ID,
                IdvHeaderConstants.CORRELATION_ID_NAME, CORRELATION_ID,
                IdvHeaderConstants.AUTHORIZATION_NAME, AUTHORIZATION
        );

        IdvRequestHeaders headers = IdvRequestHeaders.build(values);

        assertThat(headers.toArray()).containsExactly(
                IdvHeaderConstants.CHANNEL_ID_NAME,
                CHANNEL_ID,
                IdvHeaderConstants.CORRELATION_ID_NAME,
                CORRELATION_ID,
                IdvHeaderConstants.AUTHORIZATION_NAME,
                AUTHORIZATION
        );
    }

}
