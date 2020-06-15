package uk.co.idv.context.adapter.identity.service.find.data.phonenumber;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.identity.service.find.data.StubDataSupplier;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;
import uk.co.idv.context.usecases.identity.service.find.data.AsyncDataLoadRequest;
import uk.co.idv.context.usecases.identity.service.find.data.Delay;

@Slf4j
public class StubPhoneNumberSupplier extends StubDataSupplier<PhoneNumbers> {

    public StubPhoneNumberSupplier(AsyncDataLoadRequest request, Delay delay) {
        super(request, delay, new StubPhoneNumberFactory());
    }

}
