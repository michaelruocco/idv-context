package uk.co.idv.context.policy.key;

import uk.co.idv.context.policy.PolicyRequest;

import java.util.Collection;

public interface PolicyKey {

    boolean appliesTo(PolicyRequest request);

    String getType();

    String getChannelId();

    int getPriority();

    Collection<String> getActivityNames();

    default boolean hasActivityNames() {
        return !getActivityNames().isEmpty();
    }

    Collection<String> getAliasTypes();

    default boolean hasAliasTypes() {
        return !getAliasTypes().isEmpty();
    }

}
