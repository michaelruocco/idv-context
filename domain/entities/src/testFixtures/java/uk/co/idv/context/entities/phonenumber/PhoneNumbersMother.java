package uk.co.idv.context.entities.phonenumber;

import java.util.Arrays;

public interface PhoneNumbersMother {

    static PhoneNumbers mobileAndOther() {
        return with(
                MobilePhoneNumberMother.mobile(),
                OtherPhoneNumberMother.other()
        );
    }

    static PhoneNumbers with(PhoneNumber... numbers) {
        return new PhoneNumbers(Arrays.asList(numbers));
    }

}
