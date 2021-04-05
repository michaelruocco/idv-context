package uk.co.idv.context.adapter.verification.client.header;

import java.util.UUID;

public interface ContextRequestHeadersMother {

    static IdvRequestHeaders build() {
        return IdvRequestHeaders.builder()
                .channelId("default-channel")
                .correlationId(UUID.fromString("aeba4ba1-2d15-40cd-a85e-26b05447a86b"))
                .build();
    }

}
