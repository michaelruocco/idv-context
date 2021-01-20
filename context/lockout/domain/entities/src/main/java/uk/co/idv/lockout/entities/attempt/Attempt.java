package uk.co.idv.lockout.entities.attempt;

import lombok.Builder;
import lombok.Data;
import lombok.With;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.policy.entities.policy.PolicyRequest;

import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

@Builder
@Data
public class Attempt implements PolicyRequest {

    @With
    private final IdvId idvId;

    private final String channelId;
    private final String activityName;
    private final Aliases aliases;

    private final UUID contextId;
    private final String methodName;
    private final UUID verificationId;
    private final Instant timestamp;
    private final boolean successful;

    @Override
    public Collection<String> getAliasTypes() {
        return aliases.getTypes();
    }

    public boolean hasAtLeastOneAlias(Aliases aliasesToCheck) {
        return this.aliases.containsOneOf(aliasesToCheck);
    }

    public boolean occurredBetween(Instant start, Instant end) {
        return timestamp.isAfter(start) && timestamp.isBefore(end);
    }

}
