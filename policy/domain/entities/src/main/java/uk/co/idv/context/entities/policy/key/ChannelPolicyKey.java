package uk.co.idv.context.entities.policy.key;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.policy.PolicyKey;
import uk.co.idv.context.entities.policy.PolicyRequest;

import java.util.Collection;
import java.util.UUID;

import static java.util.Collections.singleton;
import static uk.co.idv.context.entities.policy.key.PolicyKeyConstants.ALL;

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
