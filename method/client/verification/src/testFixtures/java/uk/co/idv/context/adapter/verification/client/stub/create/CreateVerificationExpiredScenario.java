package uk.co.idv.context.adapter.verification.client.stub.create;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.adapter.verification.client.exception.ApiErrorClientException;
import uk.co.idv.context.adapter.verification.client.request.ClientCreateVerificationRequest;
import uk.co.idv.method.adapter.json.error.contextexpired.ContextExpiredError;
import uk.co.idv.method.entities.verification.Verification;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
public class CreateVerificationExpiredScenario implements CreateVerificationScenario {

    public static final UUID DEFAULT_ID = UUID.fromString("2b1f8ba4-00e7-4ad9-819f-5249af834f2e");
    public static final Instant DEFAULT_EXPIRY = Instant.parse("2021-01-04T23:24:07.385Z");

    private final UUID id;
    private final Instant expiry;

    public CreateVerificationExpiredScenario() {
        this(DEFAULT_ID, DEFAULT_EXPIRY);
    }

    @Override
    public boolean shouldExecute(ClientCreateVerificationRequest request) {
        return id.equals(request.getContextId());
    }

    @Override
    public Verification apply(ClientCreateVerificationRequest request) {
        throw new ApiErrorClientException(new ContextExpiredError(request.getContextId(), expiry));
    }

}
