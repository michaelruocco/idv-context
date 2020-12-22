package uk.co.idv.method.adapter.json.otp.policy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.Duration;

public interface OtpConfigMixin {

    @JsonIgnore
    int getPasscodeLength();

    @JsonIgnore
    Duration getPasscodeDuration();

    @JsonIgnore
    int getMaxNumberOfPasscodeDeliveries();

}
