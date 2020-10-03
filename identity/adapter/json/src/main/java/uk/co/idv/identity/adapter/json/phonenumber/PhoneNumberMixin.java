package uk.co.idv.identity.adapter.json.phonenumber;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.Optional;

public interface PhoneNumberMixin {

    @JsonIgnore
    boolean isMobile();

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    Optional<Instant> getLastUpdated();

}
