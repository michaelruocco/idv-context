package uk.co.idv.identity.entities.mobiledevice;

import java.util.Arrays;
import java.util.stream.Collectors;

public interface MobileDevicesMother {

    static MobileDevices empty() {
        return new MobileDevices();
    }

    static MobileDevices one() {
        return with(MobileDeviceMother.device1());
    }

    static MobileDevices two() {
        return with(MobileDeviceMother.device1(), MobileDeviceMother.device2());
    }

    static MobileDevices with(String... tokens) {
        return new MobileDevices(Arrays.stream(tokens).map(MobileDeviceMother::of).collect(Collectors.toList()));
    }

    static MobileDevices with(MobileDevice... devices) {
        return new MobileDevices(devices);
    }

}
