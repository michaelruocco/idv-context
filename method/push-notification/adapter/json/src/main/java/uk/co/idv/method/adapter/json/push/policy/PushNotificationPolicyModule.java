package uk.co.idv.method.adapter.json.push.policy;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import uk.co.idv.method.adapter.json.policy.MethodPolicyMixin;
import uk.co.idv.method.entities.push.PushNotificationConfig;
import uk.co.idv.method.entities.push.policy.PushNotificationPolicy;

import java.util.Collections;

public class PushNotificationPolicyModule extends SimpleModule {

    public PushNotificationPolicyModule() {
        super("push-notification-policy-module", Version.unknownVersion());

        setMixInAnnotation(PushNotificationPolicy.class, MethodPolicyMixin.class);

        addDeserializer(PushNotificationPolicy.class, new PushNotificationPolicyDeserializer());
        addDeserializer(PushNotificationConfig.class, new PushNotificationConfigDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Collections.singleton(new JavaTimeModule());
    }

}
