package uk.co.idv.method.adapter.json.push;

import com.fasterxml.jackson.databind.Module;
import uk.co.idv.method.adapter.json.method.MethodMapping;
import uk.co.idv.method.adapter.json.push.policy.PushNotificationPolicyModule;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.policy.MethodPolicy;
import uk.co.idv.method.entities.push.PushNotification;
import uk.co.idv.method.entities.push.PushNotificationName;
import uk.co.idv.method.entities.push.policy.PushNotificationPolicy;

import java.util.Arrays;
import java.util.Collection;

public class PushNotificationMapping implements MethodMapping {

    @Override
    public String getName() {
        return PushNotificationName.NAME;
    }

    @Override
    public Class<? extends Method> getMethodType() {
        return PushNotification.class;
    }

    @Override
    public Class<? extends MethodPolicy> getPolicyType() {
        return PushNotificationPolicy.class;
    }

    public Collection<Module> getModules() {
        return Arrays.asList(new PushNotificationModule(), new PushNotificationPolicyModule());
    }

}
