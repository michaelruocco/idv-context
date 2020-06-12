package uk.co.idv.context.adapter.identity.data.stub.phonenumber;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.identity.data.stub.Delay;
import uk.co.idv.context.adapter.identity.data.stub.StubDataSupplier;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;
import uk.co.idv.context.usecases.identity.FindIdentityRequest;

@Slf4j
public class StubPhoneNumberSupplier extends StubDataSupplier<PhoneNumbers> {

    public StubPhoneNumberSupplier(FindIdentityRequest request, Delay delay) {
        super(request, delay, new StubPhoneNumberFactory());
    }

}
