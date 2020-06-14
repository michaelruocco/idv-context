package uk.co.idv.context.adapter.stub.identity.find.data.phonenumber;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.stub.identity.find.data.StubDataSupplier;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;
import uk.co.idv.context.usecases.identity.FindIdentityRequest;
import uk.co.idv.context.usecases.identity.find.data.Delay;

@Slf4j
public class StubPhoneNumberSupplier extends StubDataSupplier<PhoneNumbers> {

    public StubPhoneNumberSupplier(FindIdentityRequest request, Delay delay) {
        super(request, delay, new StubPhoneNumberFactory());
    }

}
