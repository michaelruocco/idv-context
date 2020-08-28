package uk.co.idv.context.entities.policy;


import java.util.Collection;

public interface PolicyRequest {

    String getChannelId();

    String getActivityName();

    Collection<String> getAliasTypes();

}
