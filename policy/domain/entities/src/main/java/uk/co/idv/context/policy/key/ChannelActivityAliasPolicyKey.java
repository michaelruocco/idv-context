package uk.co.idv.context.policy.key;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.policy.PolicyRequest;

import java.util.Collection;

@Builder
@Data
public class ChannelActivityAliasPolicyKey implements PolicyKey {

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

}
