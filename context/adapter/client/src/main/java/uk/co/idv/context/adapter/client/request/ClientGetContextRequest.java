package uk.co.idv.context.adapter.client.request;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.adapter.client.headers.ContextRequestHeaders;

import java.util.UUID;

@Builder
@Data
public class ClientGetContextRequest {

    private final ContextRequestHeaders headers;
    private final UUID id;

    public String[] getHeadersArray() {
        return headers.toArray();
    }

}
