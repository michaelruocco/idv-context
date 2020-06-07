package uk.co.idv.context.entities.alias;

public class DebitCardNumberMother {

    public static DebitCardNumber build() {
        return withValue("4929222222222222");
    }

    public static DebitCardNumber withValue(String value) {
        return new DebitCardNumber(value);
    }

}
