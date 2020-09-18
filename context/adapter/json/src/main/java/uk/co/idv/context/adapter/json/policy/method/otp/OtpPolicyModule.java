package uk.co.idv.context.adapter.json.policy.method.otp;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.context.adapter.json.policy.method.otp.delivery.DeliveryMethodConfigModule;
import uk.co.idv.context.entities.policy.method.otp.OtpConfig;
import uk.co.idv.context.entities.policy.method.otp.OtpPolicy;
import uk.co.idv.context.entities.policy.method.otp.PasscodeConfig;

import java.util.Collections;

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
        return Collections.singleton(new DeliveryMethodConfigModule());
    }

}
