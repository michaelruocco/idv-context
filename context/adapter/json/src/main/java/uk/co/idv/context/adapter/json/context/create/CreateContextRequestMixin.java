package uk.co.idv.context.adapter.json.context.create;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Collection;

public interface CreateContextRequestMixin {

    @JsonIgnore
    String getChannelId();

    @JsonIgnore
    Collection<String> getAliasTypes();

    @JsonIgnore
    String getActivityName();

}
