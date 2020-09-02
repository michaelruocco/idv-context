package uk.co.idv.context.entities.context.create;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.lockout.entities.LockoutRequest;

import java.time.Instant;
import java.util.Collection;

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
    public Aliases getAliases() {
        return identityRequest.getAliases();
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
    public Collection<String> getAliasTypes() {
        return identityRequest.getAliasTypes();
    }

}
