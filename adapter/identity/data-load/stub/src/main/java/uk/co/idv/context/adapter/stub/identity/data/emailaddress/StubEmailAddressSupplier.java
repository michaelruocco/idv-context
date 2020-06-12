package uk.co.idv.context.adapter.stub.identity.data.emailaddress;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.stub.identity.data.Delay;
import uk.co.idv.context.adapter.stub.identity.data.StubDataSupplier;
import uk.co.idv.context.entities.emailaddress.EmailAddresses;
import uk.co.idv.context.usecases.identity.FindIdentityRequest;

@Slf4j
public class StubEmailAddressSupplier extends StubDataSupplier<EmailAddresses> {

    public StubEmailAddressSupplier(FindIdentityRequest request, Delay delay) {
        super(request, delay, new StubEmailAddressFactory());
    }

}
