package uk.co.idv.method.entities.push;


import uk.co.idv.identity.entities.mobiledevice.MobileDevice;
import uk.co.idv.identity.entities.mobiledevice.MobileDevices;
import uk.co.idv.identity.entities.mobiledevice.MobileDevicesMother;

public interface PushNotificationMother {

    static PushNotification build() {
        return builder().build();
    }

    static PushNotification withEmptyMobileDevices() {
        return withMobileDevices(MobileDevicesMother.empty());
    }

    static PushNotification withMobileDevices(MobileDevice mobileDevice) {
        return withMobileDevices(MobileDevicesMother.with(mobileDevice));
    }

    static PushNotification withMobileDevices(MobileDevice... mobileDevices) {
        return builder().mobileDevices(MobileDevicesMother.with(mobileDevices)).build();
    }

    static PushNotification withMobileDevices(MobileDevices mobileDevices) {
        return builder().mobileDevices(mobileDevices).build();
    }

    static PushNotification.PushNotificationBuilder builder() {
        return PushNotification.builder()
                .config(PushNotificationConfigMother.build())
                .mobileDevices(MobileDevicesMother.two());
    }

}
