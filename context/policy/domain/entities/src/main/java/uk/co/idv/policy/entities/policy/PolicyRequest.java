package uk.co.idv.policy.entities.policy;


import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

public interface PolicyRequest {

    String getChannelId();

    String getActivityName();

    Collection<String> getAliasTypes();

    default boolean isEmpty() {
        return StringUtils.isEmpty(getChannelId()) &&
                StringUtils.isEmpty(getActivityName()) &&
                isEmpty(getAliasTypes());
    }

    private static boolean isEmpty(Collection<String> values) {
        if (CollectionUtils.isEmpty(values)) {
            return true;
        }
        return values.stream().allMatch(StringUtils::isEmpty);
    }

}
