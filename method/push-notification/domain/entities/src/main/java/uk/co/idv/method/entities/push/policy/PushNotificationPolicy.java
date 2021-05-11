package uk.co.idv.method.entities.push.policy;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.identity.entities.identity.RequestedData;
import uk.co.idv.identity.entities.mobiledevice.MobileDevicesOnly;
import uk.co.idv.method.entities.policy.MethodPolicy;
import uk.co.idv.method.entities.push.PushNotificationConfig;
import uk.co.idv.method.entities.push.PushNotificationName;

@Builder
@Data
public class PushNotificationPolicy implements MethodPolicy {

    private final PushNotificationConfig config;

    @Override
    public String getName() {
        return PushNotificationName.NAME;
    }

    @Override
    public RequestedData getRequestedData() {
        return new MobileDevicesOnly();
    }

}
