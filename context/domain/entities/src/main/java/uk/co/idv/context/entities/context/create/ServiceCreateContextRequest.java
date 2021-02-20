package uk.co.idv.context.entities.context.create;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.activity.entities.Activity;
import uk.co.idv.context.entities.policy.sequence.SequencePolicies;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.context.entities.policy.ContextPolicy;

import java.util.Collection;

@Builder(toBuilder = true)
@Data
public class ServiceCreateContextRequest implements CreateContextRequest {

    private final CreateContextRequest initial;
    private final ContextPolicy policy;
    private final Identity identity;

    @Override
    public CreateContextRequest withChannel(Channel channel) {
        return toBuilder().initial(initial.withChannel(channel)).build();
    }

    @Override
    public Channel getChannel() {
        return initial.getChannel();
    }

    @Override
    public Aliases getAliases() {
        return initial.getAliases();
    }

    @Override
    public String getChannelId() {
        return initial.getChannelId();
    }

    @Override
    public Collection<String> getAliasTypes() {
        return initial.getAliasTypes();
    }

    @Override
    public Activity getActivity() {
        return initial.getActivity();
    }

    public IdvId getIdvId() {
        return identity.getIdvId();
    }

    public SequencePolicies getSequencePolicies() {
        return policy.getSequencePolicies();
    }

    public boolean isProtectSensitiveData() {
        return policy.isProtectSensitiveData();
    }

}
