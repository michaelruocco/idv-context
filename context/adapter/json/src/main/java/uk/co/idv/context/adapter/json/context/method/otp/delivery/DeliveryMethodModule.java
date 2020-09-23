package uk.co.idv.context.adapter.json.context.method.otp.delivery;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.context.adapter.json.context.eligibility.EligibilityModule;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethod;

import java.util.Collections;

public class DeliveryMethodModule extends SimpleModule {

    public DeliveryMethodModule() {
        super("delivery-method-module", Version.unknownVersion());

        setMixInAnnotation(DeliveryMethod.class, DeliveryMethodMixin.class);

        addDeserializer(DeliveryMethod.class, new DeliveryMethodDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Collections.singleton(new EligibilityModule());
    }

}
