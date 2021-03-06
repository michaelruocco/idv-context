package uk.co.idv.policy.entities.policy.key.channel;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.policy.entities.policy.PolicyRequest;
import uk.co.idv.policy.entities.policy.key.PolicyKey;

import java.util.Collection;
import java.util.UUID;

import static java.util.Collections.singleton;
import static uk.co.idv.policy.entities.policy.key.PolicyKeyConstants.ALL;

@Builder
@Data
public class ChannelPolicyKey implements PolicyKey {

    public static final String TYPE = "channel";

    private final UUID id;
    private final int priority;
    private final String channelId;

    @Override
    public boolean appliesTo(PolicyRequest request) {
        return channelId.equals(request.getChannelId());
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public Collection<String> getActivityNames() {
        return singleton(ALL);
    }

}
