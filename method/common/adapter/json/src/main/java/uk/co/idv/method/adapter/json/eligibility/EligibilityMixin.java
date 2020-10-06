package uk.co.idv.method.adapter.json.eligibility;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Optional;

public interface EligibilityMixin {

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    Optional<String> getReason();

}
