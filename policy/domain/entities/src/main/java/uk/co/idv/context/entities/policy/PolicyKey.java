package uk.co.idv.context.entities.policy;


import java.util.Collection;
import java.util.UUID;

public interface PolicyKey {

    UUID getId();

    boolean appliesTo(PolicyRequest request);

    String getType();

    String getChannelId();

    int getPriority();

    Collection<String> getActivityNames();

    Collection<String> getAliasTypes();

    boolean hasAliasType();

}
