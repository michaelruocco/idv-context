package uk.co.idv.context.adapter.json.policy.method.otp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.identity.entities.identity.RequestedData;

public interface OtpPolicyMixin {

    @JsonIgnore
    RequestedData getRequestedData();

}
