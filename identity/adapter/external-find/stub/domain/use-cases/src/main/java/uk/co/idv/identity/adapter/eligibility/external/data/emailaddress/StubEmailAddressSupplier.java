package uk.co.idv.identity.adapter.eligibility.external.data.emailaddress;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.common.entities.async.Delay;
import uk.co.idv.identity.adapter.eligibility.external.data.StubDataSupplier;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.idv.identity.usecases.eligibility.external.data.AsyncDataLoadRequest;

@Slf4j
public class StubEmailAddressSupplier extends StubDataSupplier<EmailAddresses> {

    public StubEmailAddressSupplier(AsyncDataLoadRequest request, Delay delay) {
        super(request.getAliases(), delay, new StubEmailAddressFactory());
    }

}
