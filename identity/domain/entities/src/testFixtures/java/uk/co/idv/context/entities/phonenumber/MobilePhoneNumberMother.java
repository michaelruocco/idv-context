package uk.co.idv.context.entities.phonenumber;

public interface MobilePhoneNumberMother {

    static PhoneNumber mobile() {
        return withNumber("+447089111111");
    }

    static PhoneNumber mobile1() {
        return withNumber("+447089121212");
    }

    static PhoneNumber withNumber(String number) {
        return new MobilePhoneNumber(number);
    }

}
