package uk.co.idv.method.entities.push;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import uk.co.idv.identity.entities.mobiledevice.MobileDevices;
import uk.co.idv.method.entities.eligibility.Eligibility;
import uk.co.idv.method.entities.eligibility.Eligible;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.MethodConfig;
import uk.co.idv.method.entities.push.eligibility.NoMobileDevicesRegistered;

@Builder(toBuilder = true)
@Data
public class PushNotification implements Method {

    @Getter(AccessLevel.NONE)
    private final PushNotificationConfig config;

    @Builder.Default
    private final MobileDevices mobileDevices = new MobileDevices();

    @Override
    public String getName() {
        return PushNotificationName.NAME;
    }

    @Override
    public Eligibility getEligibility() {
        if (mobileDevices.isEmpty()) {
            return new NoMobileDevicesRegistered();
        }
        return new Eligible();
    }

    @Override
    public MethodConfig getConfig() {
        return config;
    }
}
