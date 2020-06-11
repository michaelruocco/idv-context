package uk.co.idv.context.entities.phonenumber;

public interface PhoneNumbersMother {

    static PhoneNumbers mobileAndOther() {
        return with(
                MobilePhoneNumberMother.mobile(),
                OtherPhoneNumberMother.other()
        );
    }

    static PhoneNumbers empty() {
        return with();
    }

    static PhoneNumbers with(PhoneNumber... numbers) {
        return new PhoneNumbers(numbers);
    }

}
