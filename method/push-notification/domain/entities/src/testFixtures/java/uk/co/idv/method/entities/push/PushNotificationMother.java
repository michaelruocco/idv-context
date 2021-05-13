package uk.co.idv.method.entities.push;

import java.util.Arrays;
import java.util.Collection;

public interface PushNotificationMother {

    static PushNotification build() {
        return builder().build();
    }

    static PushNotification withEmptyMobileDeviceTokens() {
        return withMobileDeviceTokens();
    }

    static PushNotification withMobileDeviceTokens(String... tokens) {
        return withMobileDeviceTokens(Arrays.asList(tokens));
    }

    static PushNotification withMobileDeviceTokens(Collection<String> tokens) {
        return builder().mobileDeviceTokens(tokens).build();
    }

    static PushNotification.PushNotificationBuilder builder() {
        return PushNotification.builder()
                .config(PushNotificationConfigMother.build())
                .mobileDeviceTokens(Arrays.asList("8cc1121057f63af3c57bbe", "2dd1121057f63af3c57ccf"));
    }

}
