package uk.co.idv.method.adapter.json.error.contextexpired;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.Instant;
import java.util.UUID;


public interface ContextExpiredErrorMixin {

    @JsonIgnore
    UUID getId();

    @JsonIgnore
    Instant getExpiry();

}
