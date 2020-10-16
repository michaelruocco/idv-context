package uk.co.idv.policy.entities.policy.key;


import uk.co.idv.policy.entities.policy.PolicyRequest;

import java.util.Collection;
import java.util.UUID;

import static java.util.Collections.singleton;

public interface PolicyKey {

    UUID getId();

    boolean appliesTo(PolicyRequest request);

    String getType();

    String getChannelId();

    int getPriority();

    Collection<String> getActivityNames();

    default Collection<String> getAliasTypes() {
        return singleton(PolicyKeyConstants.ALL);
    }

    default boolean hasAliasType() {
        return false;
    }

}
