package uk.co.idv.context.entities.lockout.policy;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.lockout.LockoutRequest;
import uk.co.idv.context.entities.policy.Policy;
import uk.co.idv.context.entities.policy.PolicyKey;
import uk.co.idv.context.entities.policy.PolicyRequest;
import uk.co.idv.context.entities.lockout.attempt.Attempts;

import java.util.UUID;

@Builder
@Data
@Slf4j
public class LockoutPolicy implements Policy {

    private final PolicyKey key;
    private final LockoutStateCalculator stateCalculator;
    private final RecordAttemptPolicy recordAttemptPolicy;

    @Getter(AccessLevel.NONE)
    private final LockoutRequestConverter converter;

    @Getter(AccessLevel.NONE)
    private final AttemptsFilter attemptsFilter;

    public LockoutState calculateState(LockoutRequest request, Attempts attempts) {
        return calculateState(converter.toLockoutStateRequest(request, attempts));
    }

    public LockoutState calculateState(LockoutStateRequest request) {
        return stateCalculator.calculate(request);
    }

    public LockoutState resetState(LockoutRequest request, Attempts attempts) {
        return resetState(converter.toLockoutStateRequest(request, attempts));
    }

    public LockoutState resetState(LockoutStateRequest request) {
        Attempts applicable = attemptsFilter.filter(request);
        LockoutStateRequest resetRequest = request.removeAttempts(applicable);
        return calculateState(resetRequest);
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
