package uk.co.idv.context.adapter.json.context.eligibility;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Optional;

public interface EligibilityMixin {

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    Optional<String> getReason();

}
