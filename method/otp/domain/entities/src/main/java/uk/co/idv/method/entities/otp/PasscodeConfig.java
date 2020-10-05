package uk.co.idv.method.entities.otp;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;

@Builder
@Data
public class PasscodeConfig {

    private final int length;
    private final Duration duration;
    private final int maxNumberOfDeliveries;

}
