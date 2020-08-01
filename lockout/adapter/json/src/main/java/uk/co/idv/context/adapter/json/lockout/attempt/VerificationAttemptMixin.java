package uk.co.idv.context.adapter.json.lockout.attempt;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface VerificationAttemptMixin {

    @JsonIgnore
    String getAliasType();

}
