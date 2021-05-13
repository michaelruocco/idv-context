package uk.co.idv.identity.adapter.json.mobiledevice;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.identity.entities.mobiledevice.MobileDevice;
import uk.co.idv.identity.entities.mobiledevice.MobileDevices;

public class MobileDeviceModule extends SimpleModule {

    public MobileDeviceModule() {
        super("mobile-device-module", Version.unknownVersion());

        setMixInAnnotation(MobileDevices.class, MobileDevicesMixin.class);

        addDeserializer(MobileDevices.class, new MobileDevicesDeserializer());
        addDeserializer(MobileDevice.class, new MobileDeviceDeserializer());
    }

}
