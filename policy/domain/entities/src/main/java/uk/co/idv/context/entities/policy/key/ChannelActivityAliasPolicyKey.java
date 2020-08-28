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

    public static final String TYPE = "channel-activity-alias";

    private final UUID id;
    private final int priority;
    private final String channelId;
    private final Collection<String> activityNames;
    private final Collection<String> aliasTypes;

    @Override
    public boolean appliesTo(PolicyRequest request) {
        return channelId.equals(request.getChannelId()) &&
                activityNames.contains(request.getActivityName()) &&
                appliesToAliasTypes(request.getAliasTypes());
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public boolean hasAliasType() {
        return true;
    }

    private boolean appliesToAliasTypes(Collection<String> typesToCheck) {
        return typesToCheck.stream().anyMatch(this::appliesToAliasType);
    }

    private boolean appliesToAliasType(String typeToCheck) {
        return aliasTypes.stream().anyMatch(type -> type.equals(typeToCheck));
    }

}
