package uk.co.idv.context.entities.policy.key;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.policy.PolicyKey;
import uk.co.idv.context.entities.policy.PolicyRequest;

import java.util.Collection;
import java.util.UUID;

@Builder
@Data
public class ChannelActivityPolicyKey implements PolicyKey {

    private final UUID id;
    private final int priority;
    private final String channelId;
    private final Collection<String> activityNames;

    @Override
    public boolean appliesTo(PolicyRequest request) {
        return channelId.equals(request.getChannelId()) &&
                activityNames.contains(request.getActivityName());
    }

    @Override
    public String getType() {
        return "channel-activity";
    }

    @Override
    public Collection<String> getActivityNames() {
        return activityNames;
    }

}
