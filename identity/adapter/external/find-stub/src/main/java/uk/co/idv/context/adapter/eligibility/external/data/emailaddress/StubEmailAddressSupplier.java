package uk.co.idv.context.adapter.eligibility.external.data.emailaddress;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.eligibility.external.data.StubDataSupplier;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.idv.identity.usecases.eligibility.external.data.AsyncDataLoadRequest;
import uk.co.idv.identity.usecases.eligibility.external.data.Delay;

@Slf4j
public class StubEmailAddressSupplier extends StubDataSupplier<EmailAddresses> {

    public StubEmailAddressSupplier(AsyncDataLoadRequest request, Delay delay) {
        super(request.getAliases(), delay, new StubEmailAddressFactory());
    }

}
