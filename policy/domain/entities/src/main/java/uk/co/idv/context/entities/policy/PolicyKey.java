package uk.co.idv.context.entities.policy;


import java.util.Collection;
import java.util.UUID;

import static java.util.Collections.singleton;
import static uk.co.idv.context.entities.policy.key.PolicyKeyConstants.ALL;

public interface PolicyKey {

    UUID getId();

    boolean appliesTo(PolicyRequest request);

    String getType();

    String getChannelId();

    int getPriority();

    Collection<String> getActivityNames();

    default Collection<String> getAliasTypes() {
        return singleton(ALL);
    }

    default boolean hasAliasType() {
        return false;
    }

}
