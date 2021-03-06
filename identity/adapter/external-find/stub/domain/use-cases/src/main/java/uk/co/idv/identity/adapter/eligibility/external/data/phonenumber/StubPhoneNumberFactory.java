package uk.co.idv.identity.adapter.eligibility.external.data.phonenumber;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.identity.adapter.eligibility.external.data.StubDataFactory;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbersMother;

@Slf4j
public class StubPhoneNumberFactory implements StubDataFactory<PhoneNumbers> {

    @Override
    public String getName() {
        return PhoneNumbers.class.getSimpleName();
    }

    @Override
    public PhoneNumbers getPopulatedData() {
        return PhoneNumbersMother.two();
    }

    @Override
    public PhoneNumbers getEmptyData() {
        return new PhoneNumbers();
    }

}
