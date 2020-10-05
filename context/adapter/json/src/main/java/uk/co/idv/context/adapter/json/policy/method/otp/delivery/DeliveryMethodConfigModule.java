package uk.co.idv.context.adapter.json.policy.method.otp.delivery;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.context.adapter.json.policy.method.otp.delivery.email.EmailDeliveryMethodConfigModule;
import uk.co.idv.context.adapter.json.policy.method.otp.delivery.phone.PhoneDeliveryMethodConfigModule;
import uk.co.idv.method.entities.otp.policy.delivery.DeliveryMethodConfig;
import uk.co.idv.method.entities.otp.policy.delivery.DeliveryMethodConfigs;

import java.util.Arrays;

public class DeliveryMethodConfigModule extends SimpleModule {

    public DeliveryMethodConfigModule() {
        super("delivery-method-config-module", Version.unknownVersion());

        setMixInAnnotation(DeliveryMethodConfig.class, DeliveryMethodConfigMixin.class);
        setMixInAnnotation(DeliveryMethodConfigs.class, DeliveryMethodConfigsMixin.class);

        addDeserializer(DeliveryMethodConfig.class, new DeliveryMethodConfigDeserializer());
        addDeserializer(DeliveryMethodConfigs.class, new DeliveryMethodConfigsDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new EmailDeliveryMethodConfigModule(),
                new PhoneDeliveryMethodConfigModule()
        );
    }

}
