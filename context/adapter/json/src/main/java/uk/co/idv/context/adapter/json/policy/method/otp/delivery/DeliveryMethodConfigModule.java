package uk.co.idv.context.adapter.json.policy.method.otp.delivery;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.context.adapter.json.policy.method.otp.delivery.email.EmailDeliveryMethodConfigModule;
import uk.co.idv.context.adapter.json.policy.method.otp.delivery.phone.PhoneDeliveryMethodConfigModule;
import uk.co.idv.context.entities.policy.method.otp.delivery.DeliveryMethodConfig;

import java.util.Arrays;

public class DeliveryMethodConfigModule extends SimpleModule {

    public DeliveryMethodConfigModule() {
        super("delivery-method-config-module", Version.unknownVersion());

        addDeserializer(DeliveryMethodConfig.class, new DeliveryMethodConfigDeserializer());
        setMixInAnnotation(DeliveryMethodConfig.class, DeliveryMethodConfigMixin.class);
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new EmailDeliveryMethodConfigModule(),
                new PhoneDeliveryMethodConfigModule()
        );
    }

}
