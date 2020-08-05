package uk.co.idv.context.entities.policy;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Builder
@Data
public class DefaultPolicyRequest implements PolicyRequest {

    private final String channelId;
    private final String activityName;
    private final String aliasType;

    public boolean isEmpty() {
        return StringUtils.isEmpty(getChannelId()) &&
                StringUtils.isEmpty(getActivityName()) &&
                StringUtils.isEmpty(getAliasType());
    }

}
