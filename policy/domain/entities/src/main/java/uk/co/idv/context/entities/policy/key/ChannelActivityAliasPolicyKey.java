package uk.co.idv.context.entities.policy.key;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.policy.PolicyKey;
import uk.co.idv.context.entities.policy.PolicyRequest;

import java.util.Collection;
import java.util.UUID;

@Builder
@Data
public class ChannelActivityAliasPolicyKey implements PolicyKey {

    private final UUID id;
    private final int priority;
    private final String channelId;
    private final Collection<String> activityNames;
    private final Collection<String> aliasTypes;

    @Override
    public boolean appliesTo(PolicyRequest request) {
        return channelId.equals(request.getChannelId()) &&
                activityNames.contains(request.getActivityName()) &&
                aliasTypes.contains(request.getAliasType());
    }

    @Override
    public String getType() {
        return "channel-activity-alias";
    }

    @Override
    public Collection<String> getActivityNames() {
        return activityNames;
    }

    @Override
    public Collection<String> getAliasTypes() {
        return aliasTypes;
    }

    @Override
    public boolean hasAliasType() {
        return true;
    }

}
