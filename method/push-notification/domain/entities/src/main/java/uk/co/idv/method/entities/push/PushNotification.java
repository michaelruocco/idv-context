package uk.co.idv.method.entities.push;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import uk.co.idv.method.entities.eligibility.Eligibility;
import uk.co.idv.method.entities.eligibility.Eligible;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.MethodConfig;
import uk.co.idv.method.entities.push.eligibility.NoMobileDevicesRegistered;

import java.util.Collection;
import java.util.Collections;

@Builder(toBuilder = true)
@Data
public class PushNotification implements Method {

    @Getter(AccessLevel.NONE)
    private final PushNotificationConfig config;

    @Builder.Default
    private final Collection<String> mobileDeviceTokens = Collections.emptyList();

    @Override
    public String getName() {
        return PushNotificationName.NAME;
    }

    @Override
    public Eligibility getEligibility() {
        if (mobileDeviceTokens.isEmpty()) {
            return new NoMobileDevicesRegistered();
        }
        return new Eligible();
    }

    @Override
    public MethodConfig getConfig() {
        return config;
    }
}
