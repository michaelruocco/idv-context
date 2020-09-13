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

    private final DefaultCreateContextRequest contextRequest;
    private final Instant timestamp;

    @Override
    public IdvId getIdvId() {
        return contextRequest.getIdvId();
    }

    @Override
    public Aliases getAliases() {
        return contextRequest.getAliases();
    }

    @Override
    public String getChannelId() {
        return contextRequest.getChannelId();
    }

    @Override
    public String getActivityName() {
        return contextRequest.getActivityName();
    }

    @Override
    public Collection<String> getAliasTypes() {
        return contextRequest.getAliasTypes();
    }

}
