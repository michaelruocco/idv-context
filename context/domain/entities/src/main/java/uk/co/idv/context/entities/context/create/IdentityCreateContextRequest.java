package uk.co.idv.context.entities.context.create;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.activity.Activity;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.IdvId;
import uk.co.idv.context.entities.channel.Channel;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.policy.ContextPolicy;

@Builder
@Data
public class IdentityCreateContextRequest implements CreateContextRequest {

    private final CreateContextRequest initial;
    private final ContextPolicy policy;
    private final Identity identity;

    @Override
    public Channel getChannel() {
        return initial.getChannel();
    }

    @Override
    public String getChannelId() {
        return initial.getChannelId();
    }

    @Override
    public Alias getAlias() {
        return initial.getAlias();
    }

    @Override
    public Activity getActivity() {
        return initial.getActivity();
    }

    public IdvId getIdvId() {
        return identity.getIdvId();
    }

}
