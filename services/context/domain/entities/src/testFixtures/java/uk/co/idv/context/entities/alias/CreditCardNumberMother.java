package uk.co.idv.context.entities.alias;


public class CreditCardNumberMother {

    public static CreditCardNumber build() {
        return withValue("4929111111111111");
    }

    public static CreditCardNumber withValue(String value) {
        return new CreditCardNumber(value);
    }

}
