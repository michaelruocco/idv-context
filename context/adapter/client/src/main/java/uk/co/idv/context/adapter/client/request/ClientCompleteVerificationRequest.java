package uk.co.idv.context.adapter.client.request;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.adapter.client.headers.ContextRequestHeaders;
import uk.co.idv.context.entities.verification.CompleteVerificationRequest;

import java.util.UUID;

@Builder
@Data
public class ClientCompleteVerificationRequest {

    private final ContextRequestHeaders headers;
    private final CompleteVerificationRequest body;

    public String[] getHeadersArray() {
        return headers.toArray();
    }

    public UUID getContextId() {
        return body.getContextId();
    }

}
