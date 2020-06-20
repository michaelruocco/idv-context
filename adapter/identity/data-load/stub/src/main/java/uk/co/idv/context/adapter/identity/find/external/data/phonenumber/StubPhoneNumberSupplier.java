package uk.co.idv.context.adapter.identity.find.external.data.phonenumber;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.identity.find.external.data.StubDataSupplier;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;
import uk.co.idv.context.usecases.identity.find.external.data.AsyncDataLoadRequest;
import uk.co.idv.context.usecases.identity.find.external.data.Delay;

@Slf4j
public class StubPhoneNumberSupplier extends StubDataSupplier<PhoneNumbers> {

    public StubPhoneNumberSupplier(AsyncDataLoadRequest request, Delay delay) {
        super(request.getAliases(), delay, new StubPhoneNumberFactory());
    }

}
