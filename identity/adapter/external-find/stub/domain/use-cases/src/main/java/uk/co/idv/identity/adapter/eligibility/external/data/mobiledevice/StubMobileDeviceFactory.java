package uk.co.idv.identity.adapter.eligibility.external.data.mobiledevice;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.identity.adapter.eligibility.external.data.StubDataFactory;
import uk.co.idv.identity.entities.mobiledevice.MobileDevices;
import uk.co.idv.identity.entities.mobiledevice.MobileDevicesMother;

@Slf4j
public class StubMobileDeviceFactory implements StubDataFactory<MobileDevices> {

    @Override
    public String getName() {
        return MobileDevices.class.getSimpleName();
    }

    @Override
    public MobileDevices getPopulatedData() {
        return MobileDevicesMother.two();
    }

    @Override
    public MobileDevices getEmptyData() {
        return MobileDevicesMother.empty();
    }

}
