package uk.co.idv.identity.entities.mobiledevice;

public interface MobileDeviceMother {

    static MobileDevice device1() {
        return of("8cc1121057f63af3c57bbe");
    }

    static MobileDevice device2() {
        return of("2dd1121057f63af3c57ccf");
    }

    static MobileDevice of(String token) {
        return new MobileDevice(token);
    }

}
