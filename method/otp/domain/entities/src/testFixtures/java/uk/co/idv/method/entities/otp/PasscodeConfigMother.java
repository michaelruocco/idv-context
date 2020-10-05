package uk.co.idv.method.entities.otp;


import java.time.Duration;

public interface PasscodeConfigMother {

    static PasscodeConfig build() {
        return builder().build();
    }

    static PasscodeConfig.PasscodeConfigBuilder builder() {
        return PasscodeConfig.builder()
                .maxNumberOfDeliveries(2)
                .duration(Duration.ofMinutes(2))
                .length(8);
    }

}
