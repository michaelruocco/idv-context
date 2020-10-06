package uk.co.idv.method.adapter.json.otp.policy;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import uk.co.idv.method.adapter.json.otp.policy.delivery.DeliveryMethodConfigModule;
import uk.co.idv.method.entities.otp.OtpConfig;
import uk.co.idv.method.entities.otp.PasscodeConfig;
import uk.co.idv.method.entities.otp.policy.OtpPolicy;

import java.util.Arrays;

public class OtpPolicyModule extends SimpleModule {

    public OtpPolicyModule() {
        super("otp-policy-module", Version.unknownVersion());

        setMixInAnnotation(OtpPolicy.class, OtpPolicyMixin.class);

        addDeserializer(OtpPolicy.class, new OtpPolicyDeserializer());
        addDeserializer(OtpConfig.class, new OtpConfigDeserializer());
        addDeserializer(PasscodeConfig.class, new PasscodeConfigDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new DeliveryMethodConfigModule(),
                new JavaTimeModule()
        );
    }

}
