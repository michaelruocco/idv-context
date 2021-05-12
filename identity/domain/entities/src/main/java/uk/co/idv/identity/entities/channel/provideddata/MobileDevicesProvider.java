package uk.co.idv.identity.entities.channel.provideddata;

import uk.co.idv.identity.entities.mobiledevice.MobileDevices;

public interface MobileDevicesProvider {

    default MobileDevices getMobileDevices() {
        return new MobileDevices();
    }

}
