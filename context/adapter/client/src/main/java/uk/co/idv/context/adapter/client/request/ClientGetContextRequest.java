package uk.co.idv.context.adapter.client.request;

import lombok.Builder;
import lombok.Getter;
import uk.co.idv.context.adapter.client.headers.ContextRequestHeaders;

import java.util.UUID;

@Builder
public class ClientGetContextRequest {

    private final ContextRequestHeaders headers;

    @Getter
    private final UUID id;

    public String[] getHeadersArray() {
        return headers.toArray();
    }

}
