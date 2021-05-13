package uk.co.idv.identity.adapter.eligibility.external.data.mobiledevice;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.common.entities.async.Delay;
import uk.co.idv.identity.adapter.eligibility.external.data.StubDataSupplier;
import uk.co.idv.identity.entities.mobiledevice.MobileDevices;
import uk.co.idv.identity.usecases.eligibility.external.data.AsyncDataLoadRequest;

@Slf4j
public class StubMobileDeviceSupplier extends StubDataSupplier<MobileDevices> {

    public StubMobileDeviceSupplier(AsyncDataLoadRequest request, Delay delay) {
        super(request.getAliases(), delay, new StubMobileDeviceFactory());
    }

}
