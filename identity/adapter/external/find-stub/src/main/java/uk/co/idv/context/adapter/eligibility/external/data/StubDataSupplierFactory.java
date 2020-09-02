package uk.co.idv.context.adapter.eligibility.external.data;

import lombok.Builder;
import uk.co.idv.context.adapter.eligibility.external.data.emailaddress.StubEmailAddressSupplier;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;
import uk.co.idv.identity.usecases.eligibility.external.data.AsyncDataLoadRequest;
import uk.co.idv.identity.usecases.eligibility.external.data.DataSupplierFactory;
import uk.co.idv.context.adapter.eligibility.external.data.phonenumber.StubPhoneNumberSupplier;
import uk.co.idv.identity.usecases.eligibility.external.data.Delay;

import java.time.Duration;
import java.util.function.Supplier;

@Builder
public class StubDataSupplierFactory implements DataSupplierFactory {

    private final Duration phoneNumberDelay;
    private final Duration emailAddressDelay;

    @Override
    public Supplier<PhoneNumbers> phoneNumberSupplier(AsyncDataLoadRequest request) {
        return new StubPhoneNumberSupplier(request, new Delay(phoneNumberDelay));
    }

    @Override
    public Supplier<EmailAddresses> emailAddressSupplier(AsyncDataLoadRequest request) {
        return new StubEmailAddressSupplier(request, new Delay(emailAddressDelay));
    }

}
