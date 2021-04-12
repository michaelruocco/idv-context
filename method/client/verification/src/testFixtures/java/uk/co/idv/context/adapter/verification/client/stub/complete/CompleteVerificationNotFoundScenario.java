package uk.co.idv.context.adapter.verification.client.stub.complete;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.adapter.verification.client.exception.ApiErrorClientException;
import uk.co.idv.context.adapter.verification.client.request.ClientCompleteVerificationRequest;
import uk.co.idv.method.adapter.json.error.contextnotfound.ContextNotFoundError;
import uk.co.idv.method.entities.verification.CompleteVerificationResult;

import java.util.UUID;

@RequiredArgsConstructor
public class CompleteVerificationNotFoundScenario implements CompleteVerificationScenario {

    public static final UUID DEFAULT_ID = UUID.fromString("a75efe63-867d-4e5d-b68e-0b47f2def1ab");

    private final UUID id;

    public CompleteVerificationNotFoundScenario() {
        this(DEFAULT_ID);
    }

    @Override
    public boolean shouldExecute(ClientCompleteVerificationRequest request) {
        return id.equals(request.getContextId());
    }

    @Override
    public CompleteVerificationResult apply(ClientCompleteVerificationRequest request) {
        throw new ApiErrorClientException(new ContextNotFoundError(request.getContextId().toString()));
    }

}
