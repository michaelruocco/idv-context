package uk.co.idv.context.entities.lockout.policy;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import uk.co.idv.context.entities.policy.Policy;
import uk.co.idv.context.entities.policy.PolicyKey;
import uk.co.idv.context.entities.policy.PolicyRequest;
import uk.co.idv.context.entities.lockout.attempt.VerificationAttempts;

import java.util.UUID;

@Builder
@Data
public class LockoutPolicy implements Policy {

    private final PolicyKey key;
    private final LockoutStateCalculator stateCalculator;
    private final RecordAttemptPolicy recordAttemptPolicy;

    @Getter(AccessLevel.NONE)
    private final AttemptsFilter attemptsFilter;

    public LockoutState calculateState(LockoutStateRequest attempts) {
        return stateCalculator.calculate(attempts);
    }

    public VerificationAttempts reset(LockoutStateRequest request) {
        return attemptsFilter.filter(request);
    }

    public boolean shouldRecordAttempt(RecordAttemptRequest request) {
        return recordAttemptPolicy.shouldRecordAttempt(request);
    }

    @Override
    public UUID getId() {
        return key.getId();
    }

    @Override
    public boolean appliesTo(PolicyRequest request) {
        return key.appliesTo(request);
    }

    @Override
    public int getPriority() {
        return key.getPriority();
    }

}
