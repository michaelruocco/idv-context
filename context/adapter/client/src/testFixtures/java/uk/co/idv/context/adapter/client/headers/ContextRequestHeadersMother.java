package uk.co.idv.context.adapter.client.headers;

import java.util.UUID;

public class ContextRequestHeadersMother {

    public static  ContextRequestHeaders build() {
        return ContextRequestHeaders.builder()
                .channelId("default-channel")
                .correlationId(UUID.fromString("aeba4ba1-2d15-40cd-a85e-26b05447a86b"))
                .build();
    }

}
