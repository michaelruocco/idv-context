package uk.co.idv.context.adapter.client.request;

import lombok.Builder;
import lombok.Getter;
import uk.co.idv.context.adapter.client.headers.ContextRequestHeaders;
import uk.co.idv.context.entities.result.FacadeRecordResultRequest;

@Builder
public class ClientRecordContextResultRequest {

    private final ContextRequestHeaders headers;

    @Getter
    private final FacadeRecordResultRequest body;

    public String[] getHeadersArray() {
        return headers.toArray();
    }

}
