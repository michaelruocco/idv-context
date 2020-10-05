package uk.co.idv.context.adapter.json.context.method.otp;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.context.adapter.json.context.method.otp.delivery.DeliveryMethodModule;
import uk.co.idv.context.adapter.json.policy.method.otp.OtpPolicyModule;
import uk.co.idv.method.entities.otp.Otp;

import java.util.Arrays;

public class OtpModule extends SimpleModule {

    public OtpModule() {
        super("otp-module", Version.unknownVersion());

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
