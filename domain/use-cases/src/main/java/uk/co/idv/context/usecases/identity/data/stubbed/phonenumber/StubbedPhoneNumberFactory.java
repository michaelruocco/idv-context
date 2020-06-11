package uk.co.idv.context.usecases.identity.data.stubbed.phonenumber;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.phonenumber.MobilePhoneNumber;
import uk.co.idv.context.entities.phonenumber.OtherPhoneNumber;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;
import uk.co.idv.context.usecases.identity.data.stubbed.StubbedDataFactory;

@Slf4j
public class StubbedPhoneNumberFactory implements StubbedDataFactory<PhoneNumbers> {

    @Override
    public String getName() {
        return PhoneNumbers.class.getSimpleName();
    }

    @Override
    public PhoneNumbers getPopulatedData() {
        return new PhoneNumbers(
                new MobilePhoneNumber("+447809111111"),
                new OtherPhoneNumber("+441604222222")
        );
    }

    @Override
    public PhoneNumbers getEmptyData() {
        return new PhoneNumbers();
    }

}
