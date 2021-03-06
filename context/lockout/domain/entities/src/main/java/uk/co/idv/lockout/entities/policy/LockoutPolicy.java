package uk.co.idv.lockout.entities.policy;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.lockout.entities.LockoutRequest;
import uk.co.idv.policy.entities.policy.Policy;
import uk.co.idv.policy.entities.policy.key.PolicyKey;
import uk.co.idv.policy.entities.policy.PolicyRequest;
import uk.co.idv.lockout.entities.attempt.Attempts;

import java.util.UUID;

@Builder
@Data
@Slf4j
public class LockoutPolicy implements Policy {

    private final PolicyKey key;
    private final LockoutStateCalculator stateCalculator;
    private final RecordAttemptPolicy recordAttemptPolicy;

    @Getter(AccessLevel.NONE)
    private final AttemptsFilter attemptsFilter;

    @Builder.Default
    @Getter(AccessLevel.NONE)
    private final LockoutRequestConverter converter = new LockoutRequestConverter();

    @Override
    public int getPriority() {
        return key.getPriority();
    }

    @Override
    public UUID getId() {
        return key.getId();
    }

    @Override
    public boolean appliesTo(PolicyRequest request) {
        return key.appliesTo(request);
    }

    public LockoutState calculateState(LockoutRequest request, Attempts attempts) {
        return calculateState(converter.toLockoutStateRequest(request, attempts));
    }

    public LockoutState calculateState(LockoutStateRequest request) {
        return stateCalculator.calculate(request);
    }

    public ResetResult resetState(LockoutRequest request, Attempts attempts) {
        return resetState(converter.toLockoutStateRequest(request, attempts));
    }

    public ResetResult resetState(LockoutStateRequest request) {
        Attempts applicable = attemptsFilter.filter(request);
        LockoutStateRequest resetRequest = request.removeAttempts(applicable);
        return ResetResult.builder()
                .state(calculateState(resetRequest))
                .attemptsToRemove(applicable)
                .build();
    }

    public boolean shouldRecordAttempt(RecordAttemptRequest request) {
        return recordAttemptPolicy.shouldRecordAttempt(request);
    }

}
