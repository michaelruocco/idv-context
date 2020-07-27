package uk.co.idv.context.entities.lockout.attempt;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.IdvId;
import uk.co.idv.context.entities.policy.PolicyRequest;

import java.time.Instant;
import java.util.UUID;

@Builder
@Data
public class VerificationAttempt implements PolicyRequest {

    private final String channelId;
    private final String activityName;
    private final Alias alias;
    private final IdvId idvId;

    private final UUID contextId;
    private final String methodName;
    private final UUID verificationId;
    private final Instant timestamp;
    private final boolean successful;

    @Override
    public String getAliasType() {
        return alias.getType();
    }

    public boolean hasAlias(Alias alias) {
        return this.alias.equals(alias);
    }

}
