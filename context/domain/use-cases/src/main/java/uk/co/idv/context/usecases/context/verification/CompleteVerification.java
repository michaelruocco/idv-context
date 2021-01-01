package uk.co.idv.context.usecases.context.verification;

import lombok.Builder;
import uk.co.idv.context.entities.context.CompleteVerificationResponse;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.lockout.ContextRecordAttemptRequest;
import uk.co.idv.context.entities.verification.CompleteVerificationRequest;
import uk.co.idv.context.entities.verification.Verification;
import uk.co.idv.context.usecases.context.ContextRepository;
import uk.co.idv.context.usecases.context.FindContext;
import uk.co.idv.context.usecases.context.lockout.ContextLockoutService;

import java.time.Clock;
import java.util.UUID;

//TODO test
@Builder
public class CompleteVerification {

    private final FindContext findContext;
    private final ContextRepository repository;
    private final ContextLockoutService lockoutService;
    private final Clock clock;

    public Verification complete(CompleteVerificationRequest request) {
        UUID contextId = request.getContextId();
        Context context = findContext.find(contextId);
        CompleteVerificationRequest requestWithTimestamp = request.withTimestampIfNotProvided(clock.instant());
        CompleteVerificationResponse response = context.completeVerification(requestWithTimestamp);
        lockoutService.recordAttemptIfRequired(toRecordAttemptRequest(response));
        repository.save(response.getUpdated());
        return response.getVerification();
    }

    private ContextRecordAttemptRequest toRecordAttemptRequest(CompleteVerificationResponse response) {
        return ContextRecordAttemptRequest.builder()
                .result(response.getResult())
                .context(response.getUpdated())
                .methodComplete(response.isMethodCompletedByResult())
                .sequenceComplete(response.isSequenceCompletedByResult())
                .build();
    }

}
