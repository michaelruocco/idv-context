package uk.co.idv.method.adapter.json.otp;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.method.adapter.json.method.MethodMixin;
import uk.co.idv.method.adapter.json.otp.delivery.DeliveryMethodModule;
import uk.co.idv.method.adapter.json.otp.policy.OtpPolicyModule;
import uk.co.idv.method.entities.otp.Otp;

import java.util.Arrays;

public class OtpModule extends SimpleModule {

    public OtpModule() {
        super("otp-module", Version.unknownVersion());

        setMixInAnnotation(Otp.class, MethodMixin.class);

        addDeserializer(Otp.class, new OtpDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new DeliveryMethodModule(),
                new OtpPolicyModule()
        );
    }

}
