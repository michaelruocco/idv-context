package uk.co.idv.context.adapter.verification.client.stub.complete;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.adapter.verification.client.exception.ApiErrorClientException;
import uk.co.idv.context.adapter.verification.client.request.ClientCompleteVerificationRequest;
import uk.co.idv.method.adapter.json.error.contextexpired.ContextExpiredError;
import uk.co.idv.method.entities.verification.CompleteVerificationResult;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
public class CompleteVerificationExpiredScenario implements CompleteVerificationScenario {

    public static final UUID DEFAULT_ID = UUID.fromString("5c4d85e1-4084-4aee-9175-1f01d56aa4f5");
    public static final Instant DEFAULT_EXPIRY = Instant.parse("2021-01-04T23:24:07.385Z");

    private final UUID id;
    private final Instant expiry;

    public CompleteVerificationExpiredScenario() {
        this(DEFAULT_ID, DEFAULT_EXPIRY);
    }

    @Override
    public boolean shouldExecute(ClientCompleteVerificationRequest request) {
        return id.equals(request.getContextId());
    }

    @Override
    public CompleteVerificationResult apply(ClientCompleteVerificationRequest request) {
        throw new ApiErrorClientException(new ContextExpiredError(request.getContextId(), expiry));
    }

}
