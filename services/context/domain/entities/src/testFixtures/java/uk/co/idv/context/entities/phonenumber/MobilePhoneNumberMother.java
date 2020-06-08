package uk.co.idv.context.entities.phonenumber;

public interface MobilePhoneNumberMother {

    static PhoneNumber mobile() {
        return withNumber("+447089111111");
    }

    static PhoneNumber withNumber(String number) {
        return new MobilePhoneNumber(number);
    }

}
