package uk.co.idv.context.entities.context.lockout;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.lockout.entities.attempt.Attempt;
import uk.co.idv.lockout.entities.policy.RecordAttemptRequest;
import uk.co.idv.method.entities.result.Result;

import java.time.Instant;
import java.util.Collection;

@Data
@Builder
public class ContextRecordAttemptRequest implements RecordAttemptRequest {

    private final boolean sequenceComplete;
    private final boolean methodComplete;
    private final Context context;
    private final Result result;

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
        return Attempt.builder()
                .activityName(context.getActivityName())
                .aliases(context.getAliases())
                .channelId(context.getChannelId())
                .idvId(context.getIdvId())
                .contextId(context.getId())
                .methodName(result.getMethodName())
                .successful(result.isSuccessful())
                .timestamp(result.getTimestamp())
                .verificationId(result.getVerificationId())
                .build();
    }

    @Override
    public IdvId getIdvId() {
        return context.getIdvId();
    }

    @Override
    public Instant getTimestamp() {
        return result.getTimestamp();
    }

    @Override
    public Aliases getAliases() {
        return context.getAliases();
    }

    @Override
    public String getChannelId() {
        return context.getChannelId();
    }

    @Override
    public String getActivityName() {
        return context.getActivityName();
    }

    @Override
    public Collection<String> getAliasTypes() {
        return context.getAliasTypes();
    }

}
