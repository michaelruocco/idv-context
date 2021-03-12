package uk.co.idv.method.adapter.json.verification;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface CompleteVerificationResultMixin {

    @JsonIgnore
    boolean isVerificationComplete();

    @JsonIgnore
    boolean isVerificationSuccessful();

}
