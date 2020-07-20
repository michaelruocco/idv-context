package uk.co.idv.context.policy;


import java.util.Collection;

public interface PolicyKey {

    String ALL = "all";

    boolean appliesTo(PolicyRequest request);

    String getType();

    String getChannelId();

    int getPriority();

    Collection<String> getActivityNames();

    Collection<String> getAliasTypes();

}
