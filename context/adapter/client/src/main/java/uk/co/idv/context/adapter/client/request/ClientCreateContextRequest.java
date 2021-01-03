package uk.co.idv.context.adapter.client.request;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.adapter.client.headers.ContextRequestHeaders;
import uk.co.idv.context.entities.context.create.FacadeCreateContextRequest;

@Builder
@Data
public class ClientCreateContextRequest {

    private final ContextRequestHeaders headers;
    private final FacadeCreateContextRequest body;

    public String[] getHeadersArray() {
        return headers.toArray();
    }

}
