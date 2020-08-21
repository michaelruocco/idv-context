package uk.co.idv.context.adapter.json.lockout.attempt;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface AttemptMixin {

    @JsonIgnore
    String getAliasType();

}
