package uk.co.idv.context.entities.lockout;

import lombok.Builder;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.IdvId;

import java.time.Instant;

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
    public String getAliasType() {
        return externalRequest.getAliasType();
    }

    @Override
    public Alias getAlias() {
        return externalRequest.getAlias();
    }

}
