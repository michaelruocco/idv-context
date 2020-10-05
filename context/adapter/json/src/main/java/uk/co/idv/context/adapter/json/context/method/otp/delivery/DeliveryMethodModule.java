package uk.co.idv.context.adapter.json.context.method.otp.delivery;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.context.adapter.json.context.eligibility.EligibilityModule;
import uk.co.idv.context.adapter.json.context.method.otp.delivery.eligibility.SimSwapEligibilityModule;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethods;

import java.util.Arrays;

public class DeliveryMethodModule extends SimpleModule {

    public DeliveryMethodModule() {
        super("delivery-method-module", Version.unknownVersion());

        setMixInAnnotation(DeliveryMethod.class, DeliveryMethodMixin.class);
        setMixInAnnotation(DeliveryMethods.class, DeliveryMethodsMixin.class);

        addDeserializer(DeliveryMethod.class, new DeliveryMethodDeserializer());
        addDeserializer(DeliveryMethods.class, new DeliveryMethodsDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new EligibilityModule(),
                new SimSwapEligibilityModule()
        );
    }

}
