package uk.co.idv.context.adapter.json.lockout.attempt;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Collection;

public interface AttemptMixin {

    @JsonIgnore
    Collection<String> getAliasTypes();

}
