package uk.co.idv.identity.adapter.eligibility.external.data.phonenumber;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.common.usecases.async.Delay;
import uk.co.idv.identity.adapter.eligibility.external.data.StubDataSupplier;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;
import uk.co.idv.identity.usecases.eligibility.external.data.AsyncDataLoadRequest;

@Slf4j
public class StubPhoneNumberSupplier extends StubDataSupplier<PhoneNumbers> {

    public StubPhoneNumberSupplier(AsyncDataLoadRequest request, Delay delay) {
        super(request.getAliases(), delay, new StubPhoneNumberFactory());
    }

}
