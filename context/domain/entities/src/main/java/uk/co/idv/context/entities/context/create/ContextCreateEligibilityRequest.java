package uk.co.idv.context.entities.context.create;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.identity.entities.identity.FindIdentityRequest;
import uk.co.idv.identity.entities.identity.RequestedData;
import uk.co.idv.context.entities.policy.ContextPolicy;


@Data
@Builder
public class ContextCreateEligibilityRequest implements FindIdentityRequest {

    private final CreateContextRequest request;
    private final ContextPolicy policy;

    @Override
    public Aliases getAliases() {
        return request.getAliases();
    }

    @Override
    public Channel getChannel() {
        return request.getChannel();
    }

    @Override
    public RequestedData getRequestedData() {
        return policy.getRequestedData();
    }

}
