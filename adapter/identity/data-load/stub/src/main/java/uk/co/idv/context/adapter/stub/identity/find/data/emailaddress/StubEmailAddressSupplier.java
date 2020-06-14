package uk.co.idv.context.adapter.stub.identity.find.data.emailaddress;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.stub.identity.find.data.StubDataSupplier;
import uk.co.idv.context.entities.emailaddress.EmailAddresses;
import uk.co.idv.context.usecases.identity.find.data.AsyncDataLoadRequest;
import uk.co.idv.context.usecases.identity.find.data.Delay;

@Slf4j
public class StubEmailAddressSupplier extends StubDataSupplier<EmailAddresses> {

    public StubEmailAddressSupplier(AsyncDataLoadRequest request, Delay delay) {
        super(request, delay, new StubEmailAddressFactory());
    }

}
