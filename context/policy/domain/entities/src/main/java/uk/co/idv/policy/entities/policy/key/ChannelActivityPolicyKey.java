package uk.co.idv.policy.entities.policy.key;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.policy.entities.policy.PolicyRequest;

import java.util.Collection;
import java.util.UUID;

@Builder
@Data
public class ChannelActivityPolicyKey implements PolicyKey {

    public static final String TYPE = "channel-activity";

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
        return TYPE;
    }

}
