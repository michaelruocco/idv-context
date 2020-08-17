package uk.co.idv.context.entities.lockout;

import lombok.Builder;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.IdvId;
import uk.co.idv.context.entities.lockout.attempt.Attempt;
import uk.co.idv.context.entities.lockout.policy.RecordAttemptRequest;

import java.time.Instant;

@Builder
public class DefaultRecordAttemptRequest implements RecordAttemptRequest {

    private final boolean sequenceComplete;
    private final boolean methodComplete;
    private final Attempt attempt;

    @Override
    public boolean isSequenceComplete() {
        return sequenceComplete;
    }

    @Override
    public boolean isMethodComplete() {
        return methodComplete;
    }

    @Override
    public Attempt getAttempt() {
        return attempt;
    }

    @Override
    public IdvId getIdvId() {
        return attempt.getIdvId();
    }

    @Override
    public Instant getTimestamp() {
        return attempt.getTimestamp();
    }

    @Override
    public Alias getAlias() {
        return attempt.getAlias();
    }

    @Override
    public String getChannelId() {
        return attempt.getChannelId();
    }

    @Override
    public String getActivityName() {
        return attempt.getActivityName();
    }

    @Override
    public String getAliasType() {
        return attempt.getAliasType();
    }

}
