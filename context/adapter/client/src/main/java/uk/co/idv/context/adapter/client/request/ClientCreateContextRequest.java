package uk.co.idv.context.adapter.client.request;

import lombok.Builder;
import lombok.Getter;
import uk.co.idv.context.adapter.client.headers.ContextRequestHeaders;
import uk.co.idv.context.entities.context.create.FacadeCreateContextRequest;

@Builder
public class ClientCreateContextRequest {

    private final ContextRequestHeaders headers;

    @Getter
    private final FacadeCreateContextRequest body;

    public String[] getHeadersArray() {
        return headers.toArray();
    }

}
