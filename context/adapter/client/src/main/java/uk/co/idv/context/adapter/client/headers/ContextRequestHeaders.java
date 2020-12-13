package uk.co.idv.context.adapter.client.headers;

import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

import static uk.co.idv.context.adapter.client.headers.HeaderConstants.CHANNEL_ID_NAME;
import static uk.co.idv.context.adapter.client.headers.HeaderConstants.CORRELATION_ID_NAME;

@Builder
@Data
public class ContextRequestHeaders {

    private final String channelId;
    private final UUID correlationId;

    public static ContextRequestHeaders build(String channelId) {
        return ContextRequestHeaders.builder()
                .channelId(channelId)
                .correlationId(UUID.randomUUID())
                .build();
    }

    public static ContextRequestHeaders build(Map<String, String> mdc) {
        return ContextRequestHeaders.builder()
                .channelId(mdc.get(CHANNEL_ID_NAME))
                .correlationId(UUID.fromString(mdc.get(CORRELATION_ID_NAME)))
                .build();
    }

    public String[] toArray() {
        return new String[] {
                CHANNEL_ID_NAME, channelId,
                CORRELATION_ID_NAME, correlationId.toString()
        };
    }

}
