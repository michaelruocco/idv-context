package uk.co.idv.context.lockout.policy;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.policy.Policy;
import uk.co.idv.context.entities.policy.PolicyKey;
import uk.co.idv.context.entities.policy.PolicyRequest;
import uk.co.idv.context.lockout.attempt.VerificationAttempts;

import java.util.UUID;

@RequiredArgsConstructor
@Data
public class LockoutPolicy implements Policy {

    private final PolicyKey key;
    private final AttemptsFilter attemptsFilter;
    private final LockoutStateCalculator stateCalculator;
    private final RecordAttemptPolicy recordAttemptPolicy;

    public LockoutState calculateState(LockoutStateRequest attempts) {
        return stateCalculator.calculate(attempts);
    }

    public VerificationAttempts reset(LockoutStateRequest request) {
        return attemptsFilter.filter(request);
    }

    public boolean shouldRecordAttempt(RecordAttemptRequest request) {
        return recordAttemptPolicy.shouldRecordAttempt(request);
    }

    public UUID getId() {
        return key.getId();
    }

    public boolean appliesTo(PolicyRequest request) {
        return key.appliesTo(request);
    }

    public int getPriority() {
        return key.getPriority();
    }

}
