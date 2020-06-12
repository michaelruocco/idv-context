package uk.co.idv.context.adapter.identity.data.stub.emailaddress;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.identity.data.stub.Delay;
import uk.co.idv.context.adapter.identity.data.stub.StubDataSupplier;
import uk.co.idv.context.entities.emailaddress.EmailAddresses;
import uk.co.idv.context.usecases.identity.FindIdentityRequest;


@Slf4j
public class StubEmailAddressSupplier extends StubDataSupplier<EmailAddresses> {

    public StubEmailAddressSupplier(FindIdentityRequest request, Delay delay) {
        super(request, delay, new StubEmailAddressFactory());
    }

}
