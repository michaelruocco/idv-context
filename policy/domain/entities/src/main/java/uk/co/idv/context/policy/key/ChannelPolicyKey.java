package uk.co.idv.context.policy.key;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.policy.PolicyKey;
import uk.co.idv.context.policy.PolicyRequest;

import java.util.Collection;

import static java.util.Collections.singleton;

@Builder
@Data
public class ChannelPolicyKey implements PolicyKey {

    private final int priority;
    private final String channelId;

    @Override
    public boolean appliesTo(PolicyRequest request) {
        return channelId.equals(request.getChannelId());
    }

    @Override
    public String getType() {
        return "channel";
    }

    @Override
    public Collection<String> getActivityNames() {
        return singleton(ALL);
    }

    @Override
    public Collection<String> getAliasTypes() {
        return singleton(ALL);
    }

}
