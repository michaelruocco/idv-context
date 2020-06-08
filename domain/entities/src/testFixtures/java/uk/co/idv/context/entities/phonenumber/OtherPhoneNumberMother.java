package uk.co.idv.context.entities.phonenumber;

public interface OtherPhoneNumberMother {

    static PhoneNumber other() {
        return withNumber("+441604222222");
    }

    static PhoneNumber withNumber(String number) {
        return new OtherPhoneNumber(number);
    }

}
