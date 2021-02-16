package uk.co.idv.method.adapter.json.verification;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.Instant;

public interface CompleteVerificationRequestMixin {

    @JsonIgnore
    Instant forceGetTimestamp();

}
