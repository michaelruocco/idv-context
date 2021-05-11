package uk.co.idv.method.adapter.json.push;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.method.adapter.json.eligibility.EligibilityModule;
import uk.co.idv.method.adapter.json.method.MethodMixin;
import uk.co.idv.method.adapter.json.push.policy.PushNotificationPolicyModule;
import uk.co.idv.method.entities.push.PushNotification;

import java.util.Arrays;

public class PushNotificationModule extends SimpleModule {

    public PushNotificationModule() {
        super("push-notification-module", Version.unknownVersion());

        setMixInAnnotation(PushNotification.class, MethodMixin.class);

        addDeserializer(PushNotification.class, new PushNotificationDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new EligibilityModule(),
                new PushNotificationPolicyModule()
        );
    }

}
