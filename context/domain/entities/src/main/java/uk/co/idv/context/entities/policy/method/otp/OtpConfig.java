package uk.co.idv.context.entities.policy.method.otp;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.policy.method.MethodConfig;

import java.time.Duration;

@Builder
@Data
public class OtpConfig implements MethodConfig {

    private final int maxNumberOfAttempts;
    private final Duration duration;
    private final PasscodeConfig passcodeConfig;

}
