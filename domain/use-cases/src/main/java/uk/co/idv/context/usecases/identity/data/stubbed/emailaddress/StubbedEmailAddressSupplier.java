package uk.co.idv.context.usecases.identity.data.stubbed.emailaddress;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.emailaddress.EmailAddresses;
import uk.co.idv.context.usecases.identity.LoadIdentityRequest;
import uk.co.idv.context.usecases.identity.data.stubbed.Sleeper;
import uk.co.idv.context.usecases.identity.data.stubbed.StubbedDataSupplier;

import java.time.Duration;

import static uk.co.idv.context.usecases.identity.data.stubbed.StubbedDelaySystemPropertyLoader.loadDelay;

@Slf4j
public class StubbedEmailAddressSupplier extends StubbedDataSupplier<EmailAddresses> {

    public StubbedEmailAddressSupplier(LoadIdentityRequest request) {
        super(request, defaultDelay(), new StubbedEmailAddressFactory(), new Sleeper());
    }

    private static Duration defaultDelay() {
        return loadDelay("stubbed.email.address.load.delay", 1500);
    }

}
