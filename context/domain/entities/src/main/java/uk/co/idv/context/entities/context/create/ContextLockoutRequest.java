package uk.co.idv.context.entities.context.create;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.IdvId;
import uk.co.idv.context.entities.lockout.LockoutRequest;

import java.time.Instant;

@Builder
@Data
public class ContextLockoutRequest implements LockoutRequest {

    private final IdentityCreateContextRequest identityRequest;
    private final Instant timestamp;

    @Override
    public IdvId getIdvId() {
        return identityRequest.getIdvId();
    }

    @Override
    public Alias getAlias() {
        return identityRequest.getAlias();
    }

    @Override
    public String getChannelId() {
        return identityRequest.getChannelId();
    }

    @Override
    public String getActivityName() {
        return identityRequest.getActivityName();
    }

    @Override
    public String getAliasType() {
        return identityRequest.getAliasType();
    }

}
