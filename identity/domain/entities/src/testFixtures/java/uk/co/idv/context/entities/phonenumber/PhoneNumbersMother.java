package uk.co.idv.context.entities.phonenumber;

public interface PhoneNumbersMother {

    static PhoneNumbers two() {
        return with(
                PhoneNumberMother.example(),
                PhoneNumberMother.example1()
        );
    }

    static PhoneNumbers one() {
        return with(PhoneNumberMother.example());
    }

    static PhoneNumbers empty() {
        return with();
    }

    static PhoneNumbers with(PhoneNumber... numbers) {
        return new PhoneNumbers(numbers);
    }

}
