package uk.co.idv.context.usecases.identity.data.stubbed.phonenumber;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;
import uk.co.idv.context.usecases.identity.LoadIdentityRequest;
import uk.co.idv.context.usecases.identity.data.stubbed.Sleeper;
import uk.co.idv.context.usecases.identity.data.stubbed.StubbedDataSupplier;

import java.time.Duration;

import static uk.co.idv.context.usecases.identity.data.stubbed.StubbedDelaySystemPropertyLoader.loadDelay;

@Slf4j
public class StubbedPhoneNumberSupplier extends StubbedDataSupplier<PhoneNumbers> {

    public StubbedPhoneNumberSupplier(LoadIdentityRequest request) {
        super(request, defaultDelay(), new StubbedPhoneNumberFactory(), new Sleeper());
    }

    private static Duration defaultDelay() {
        return loadDelay("stubbed.phone.number.load.delay", 500);
    }

}
