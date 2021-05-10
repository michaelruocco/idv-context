package uk.co.idv.identity.entities.mobiledevices;

import uk.co.idv.identity.entities.identity.RequestedData;
import uk.co.idv.identity.entities.identity.RequestedDataItems;

public class MobileDevicesOnly extends RequestedData {

    public MobileDevicesOnly() {
        super(RequestedDataItems.MOBILE_DEVICES);
    }

}
