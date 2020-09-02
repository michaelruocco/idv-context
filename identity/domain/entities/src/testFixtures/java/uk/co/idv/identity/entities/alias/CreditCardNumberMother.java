package uk.co.idv.identity.entities.alias;


public interface CreditCardNumberMother {

    static CreditCardNumber creditCardNumber() {
        return withValue("4929111111111111");
    }

    static CreditCardNumber withValueEndingIn9() {
        return withValue("4929111111111119");
    }

    static CreditCardNumber withValue(String value) {
        return new CreditCardNumber(value);
    }

}
