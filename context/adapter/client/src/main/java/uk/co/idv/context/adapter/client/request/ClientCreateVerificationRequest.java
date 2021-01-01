package uk.co.idv.context.adapter.client.request;

import lombok.Builder;
import lombok.Getter;
import uk.co.idv.context.adapter.client.headers.ContextRequestHeaders;
import uk.co.idv.context.entities.verification.CreateVerificationRequest;

import java.util.UUID;

@Builder
public class ClientCreateVerificationRequest {

    private final ContextRequestHeaders headers;

    @Getter
    private final CreateVerificationRequest body;

    public String[] getHeadersArray() {
        return headers.toArray();
    }

    public UUID getContextId() {
        return body.getContextId();
    }

}
