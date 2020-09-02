package uk.co.idv.policy.entities.policy;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

@Builder
@Data
public class DefaultPolicyRequest implements PolicyRequest {

    private final String channelId;
    private final String activityName;
    private final Collection<String> aliasTypes;

    public boolean isEmpty() {
        return StringUtils.isEmpty(channelId) &&
                StringUtils.isEmpty(activityName) &&
                isEmpty(aliasTypes);
    }

    private boolean isEmpty(Collection<String> values) {
        if (CollectionUtils.isEmpty(aliasTypes)) {
            return true;
        }
        return values.stream().allMatch(StringUtils::isEmpty);
    }

}
