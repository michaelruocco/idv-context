package uk.co.idv.context.adapter.identity.find.external.data;

import lombok.Builder;
import uk.co.idv.context.entities.emailaddress.EmailAddresses;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;
import uk.co.idv.context.usecases.identity.find.data.AsyncDataLoadRequest;
import uk.co.idv.context.usecases.identity.find.data.DataSupplierFactory;
import uk.co.idv.context.adapter.identity.find.external.data.phonenumber.StubPhoneNumberSupplier;
import uk.co.idv.context.adapter.identity.find.external.data.emailaddress.StubEmailAddressSupplier;
import uk.co.idv.context.usecases.identity.find.data.Delay;

import java.util.function.Supplier;

@Builder
public class StubDataSupplierFactory implements DataSupplierFactory {

    private final Delay phoneNumberDelay;
    private final Delay emailAddressDelay;

    @Override
    public Supplier<PhoneNumbers> phoneNumberSupplier(AsyncDataLoadRequest request) {
        return new StubPhoneNumberSupplier(request, phoneNumberDelay);
    }

    @Override
    public Supplier<EmailAddresses> emailAddressSupplier(AsyncDataLoadRequest request) {
        return new StubEmailAddressSupplier(request, emailAddressDelay);
    }

}
