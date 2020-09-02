package uk.co.idv.identity.adapter.json.error.aliastype;

import com.fasterxml.jackson.annotation.JsonIgnore;


public interface UnsupportedAliasTypeErrorMixin {

    @JsonIgnore
    String getType();

}
