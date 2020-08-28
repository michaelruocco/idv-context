package uk.co.idv.context.entities.lockout;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.IdvId;
import uk.co.idv.context.entities.lockout.attempt.Attempt;
import uk.co.idv.context.entities.lockout.policy.RecordAttemptRequest;

import java.time.Instant;
import java.util.Collection;

@Builder
@Data
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
    public Aliases getAliases() {
        return attempt.getAliases();
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
    public Collection<String> getAliasTypes() {
        return attempt.getAliasTypes();
    }

}
