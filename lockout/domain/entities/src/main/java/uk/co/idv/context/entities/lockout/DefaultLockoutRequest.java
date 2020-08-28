package uk.co.idv.context.entities.lockout;

import lombok.Builder;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.IdvId;

import java.time.Instant;
import java.util.Collection;

@Builder
public class DefaultLockoutRequest implements LockoutRequest {

    private final IdvId idvId;
    private final Instant timestamp;
    private final ExternalLockoutRequest externalRequest;

    @Override
    public IdvId getIdvId() {
        return idvId;
    }

    @Override
    public Instant getTimestamp() {
        return timestamp;
    }

    @Override
    public String getChannelId() {
        return externalRequest.getChannelId();
    }

    @Override
    public String getActivityName() {
        return externalRequest.getActivityName();
    }

    @Override
    public Collection<String> getAliasTypes() {
        return externalRequest.getAliasTypes();
    }

    @Override
    public Aliases getAliases() {
        return externalRequest.getAliases();
    }

}
