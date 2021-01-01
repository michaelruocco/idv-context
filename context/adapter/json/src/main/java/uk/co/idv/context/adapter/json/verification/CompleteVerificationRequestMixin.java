package uk.co.idv.context.adapter.json.verification;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.Instant;

public interface CompleteVerificationRequestMixin {

    @JsonIgnore
    Instant forceGetTimestamp();

}
