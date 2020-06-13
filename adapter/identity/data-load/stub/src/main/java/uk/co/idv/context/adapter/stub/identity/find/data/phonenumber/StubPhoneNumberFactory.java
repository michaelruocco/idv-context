package uk.co.idv.context.adapter.stub.identity.find.data.phonenumber;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.stub.identity.find.data.StubDataFactory;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;
import uk.co.idv.context.entities.phonenumber.PhoneNumbersMother;

@Slf4j
public class StubPhoneNumberFactory implements StubDataFactory<PhoneNumbers> {

    @Override
    public String getName() {
        return PhoneNumbers.class.getSimpleName();
    }

    @Override
    public PhoneNumbers getPopulatedData() {
        return PhoneNumbersMother.mobileAndOther();
    }

    @Override
    public PhoneNumbers getEmptyData() {
        return new PhoneNumbers();
    }

}
