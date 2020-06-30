package uk.co.idv.context.adapter.json.error.aliastype;

import com.fasterxml.jackson.annotation.JsonIgnore;


public interface UnsupportedAliasTypeErrorMixin {

    @JsonIgnore
    String getType();

}
