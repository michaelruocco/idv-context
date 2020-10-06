package uk.co.idv.method.adapter.json.otp.delivery;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Optional;

public interface LastUpdatedConfigMixin {

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    Optional<Long> getMinDaysSinceUpdate();

}
