package uk.co.idv.context.entities.context;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.activity.Activity;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.channel.Channel;
import uk.co.idv.context.entities.identity.Identity;

@Builder
@Data
public class IdentityCreateContextRequest implements CreateContextRequest {

    private final CreateContextRequest initial;
    private final Identity identity;

    @Override
    public Channel getChannel() {
        return initial.getChannel();
    }

    @Override
    public Alias getAlias() {
        return initial.getAlias();
    }

    @Override
    public Activity getActivity() {
        return initial.getActivity();
    }

}
