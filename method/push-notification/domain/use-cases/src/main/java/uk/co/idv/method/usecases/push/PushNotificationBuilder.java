package uk.co.idv.method.usecases.push;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.method.entities.method.MethodsRequest;
import uk.co.idv.method.entities.policy.MethodPolicy;
import uk.co.idv.method.entities.push.PushNotification;
import uk.co.idv.method.entities.push.policy.PushNotificationPolicy;
import uk.co.idv.method.usecases.MethodBuilder;

@Slf4j
public class PushNotificationBuilder implements MethodBuilder {

    @Override
    public boolean supports(MethodPolicy policy) {
        return policy instanceof PushNotificationPolicy;
    }

    @Override
    public PushNotification build(MethodsRequest request, MethodPolicy policy) {
        PushNotificationPolicy pushPolicy = (PushNotificationPolicy) policy;
        return PushNotification.builder()
                .config(pushPolicy.getConfig())
                .mobileDeviceTokens(request.getMobileDeviceTokens())
                .build();
    }

}
