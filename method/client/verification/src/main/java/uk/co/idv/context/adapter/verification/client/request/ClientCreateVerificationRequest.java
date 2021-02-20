package uk.co.idv.context.adapter.verification.client.request;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.adapter.verification.client.header.ContextRequestHeaders;
import uk.co.idv.method.entities.verification.CreateVerificationRequest;

import java.util.UUID;

@Builder
@Data
public class ClientCreateVerificationRequest {

    private final ContextRequestHeaders headers;
    private final CreateVerificationRequest body;

    public String[] getHeadersArray() {
        return headers.toArray();
    }

    public UUID getContextId() {
        return body.getContextId();
    }

}
