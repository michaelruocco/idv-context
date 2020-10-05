package uk.co.idv.method.entities.otp;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.method.entities.method.MethodConfig;

import java.time.Duration;

@Builder
@Data
public class OtpConfig implements MethodConfig {

    private final int maxNumberOfAttempts;
    private final Duration duration;
    private final PasscodeConfig passcodeConfig;

}
