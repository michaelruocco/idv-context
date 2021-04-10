package uk.co.idv.context.adapter.verification.client.stub.complete;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.adapter.verification.client.exception.ApiErrorClientException;
import uk.co.idv.context.adapter.verification.client.request.ClientCompleteVerificationRequest;
import uk.co.idv.method.adapter.json.error.notnextmethod.NotNextMethodError;
import uk.co.idv.method.entities.verification.CompleteVerificationResult;

import java.util.UUID;

@RequiredArgsConstructor
public class CompleteVerificationAlreadyCompleteScenario implements CompleteVerificationScenario {

    public static final UUID DEFAULT_ID = UUID.fromString("d4ed1345-ad9f-47a0-a827-0fc9c6c30d1d");

    private final UUID id;

    public CompleteVerificationAlreadyCompleteScenario() {
        this(DEFAULT_ID);
    }

    @Override
    public boolean shouldExecute(ClientCompleteVerificationRequest request) {
        return id.equals(request.getContextId());
    }

    @Override
    public CompleteVerificationResult apply(ClientCompleteVerificationRequest request) {
        throw new ApiErrorClientException(new NotNextMethodError("stubbed-method-name"));
    }

}
