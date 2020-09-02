package uk.co.idv.context.adapter.eligibility.external.data.phonenumber;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.eligibility.external.data.StubDataSupplier;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;
import uk.co.idv.identity.usecases.eligibility.external.data.AsyncDataLoadRequest;
import uk.co.idv.identity.usecases.eligibility.external.data.Delay;

@Slf4j
public class StubPhoneNumberSupplier extends StubDataSupplier<PhoneNumbers> {

    public StubPhoneNumberSupplier(AsyncDataLoadRequest request, Delay delay) {
        super(request.getAliases(), delay, new StubPhoneNumberFactory());
    }

}
