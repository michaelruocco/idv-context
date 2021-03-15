package uk.co.idv.context.usecases.context.verification;

import lombok.Builder;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.lockout.ContextRecordAttemptRequest;
import uk.co.idv.method.entities.verification.CompleteVerificationRequest;
import uk.co.idv.method.entities.verification.CompleteVerificationResult;
import uk.co.idv.method.entities.verification.Verification;
import uk.co.idv.context.usecases.context.ContextRepository;
import uk.co.idv.context.usecases.context.FindContext;
import uk.co.idv.context.usecases.context.lockout.ContextLockoutService;

import java.time.Clock;
import java.util.UUID;

@Builder
public class CompleteVerification {

    private final FindContext findContext;
    private final ContextRepository repository;
    private final ContextLockoutService lockoutService;
    private final Clock clock;

    public CompleteVerificationResult complete(CompleteVerificationRequest request) {
        UUID contextId = request.getContextId();
        Context context = findContext.find(contextId);
        CompleteVerificationRequest requestWithTimestamp = request.withTimestampIfNotProvided(clock.instant());
        Context updated = context.completeVerification(requestWithTimestamp);
        Verification verification = updated.getVerification(request.getId());
        lockoutService.recordAttemptIfRequired(toRecordAttemptRequest(context, updated, verification));
        repository.save(updated);
        return toResult(verification, updated);
    }

    private ContextRecordAttemptRequest toRecordAttemptRequest(Context original, Context updated, Verification verification) {
        return ContextRecordAttemptRequest.builder()
                .result(verification.toResult())
                .context(updated)
                .methodComplete(updated.hasMoreCompletedMethodsThan(original))
                .sequenceComplete(updated.hasMoreCompletedSequencesThan(original))
                .build();
    }

    private CompleteVerificationResult toResult(Verification verification, Context updated) {
        return CompleteVerificationResult.builder()
                .verification(verification)
                .contextComplete(updated.isComplete())
                .contextSuccessful(updated.isSuccessful())
                .build();
    }

}
