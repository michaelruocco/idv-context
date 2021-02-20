package uk.co.idv.context.adapter.verification.client.header;

import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

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
                .channelId(mdc.get(HeaderConstants.CHANNEL_ID_NAME))
                .correlationId(UUID.fromString(mdc.get(HeaderConstants.CORRELATION_ID_NAME)))
                .build();
    }

    public String[] toArray() {
        return new String[] {
                HeaderConstants.CHANNEL_ID_NAME, channelId,
                HeaderConstants.CORRELATION_ID_NAME, correlationId.toString()
        };
    }

}
