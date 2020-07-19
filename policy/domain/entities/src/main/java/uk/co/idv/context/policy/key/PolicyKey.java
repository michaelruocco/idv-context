package uk.co.idv.context.policy.key;

import uk.co.idv.context.policy.PolicyRequest;

import java.util.Collection;

public interface PolicyKey {

    boolean appliesTo(PolicyRequest request);

    String getChannelId();

    int getPriority();

    Collection<String> getActivityNames();

    Collection<String> getAliasTypes();

}
