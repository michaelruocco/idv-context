package uk.co.idv.lockout.adapter.json.attempt;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Collection;

public interface AttemptMixin {

    @JsonIgnore
    Collection<String> getAliasTypes();

}
